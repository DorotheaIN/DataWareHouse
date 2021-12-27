from typing_extensions import runtime
import pandas as pd
import time 
import numpy as np
import re
import json


DEBUG = False

class Filter():
    # 输入imdb电影tsv文件路径和待筛选文件路径以及输出路径
    def __init__(self,imdb_movie_path,amazon_info_path,movies_output_path,asin_output_path) -> None:
        self.movie_output_path = movies_output_path
        self.asin_output_path = asin_output_path
        pd.options.display.max_rows = 10
        self.movies = pd.read_csv(imdb_movie_path,sep='\t',header=0,low_memory=False,index_col=0)
        self.data = pd.read_csv(amazon_info_path,header=0,low_memory=False,encoding='ISO-8859-1')
        self.data.fillna('NULL',inplace=True)
        self.movies.fillna('NULL',inplace=True)
        self.dataDict = self.data.to_dict(orient = 'records')
        self.failList = [] # 记录Amazon标记movie但IMDB找不到的电影
        self.movieList = [] # 记录所有电影
        self.movieAsins = [] # 记录所有电影的asin（版本被展开）
        self.nothingAsins = [] # 记录所有下架商品的asin  

    # 删除标题中冗余信息
    def dealBracket(self,title):
        surpuls = [" DVD"," VHS"] # 商品名称常见冗余
        for i in surpuls:
            title = re.sub(i," ",title)
        title = re.sub(u"\\(.*?\\)|\\{.*?}|\\[.*?]","",title)
        title = re.sub(r"\s+", " ", title) # 合并空格
        title = title.strip() # 去除头尾空格
        return title

    # 标题拆分
    def dealCut(self,name,item):
        # 查找string中包含的四位数字
        years = re.findall(r'\b\d{4}\b', name)
        for i in years:
            if(self.isYear(i)):
                # 如果是表示年份的，用-替换
                name = name.replace(i,'-')
        # 将名字按照-:/分割符划分,并集合化
        names = set(re.split(r'[-:/]',name))
        # 存放标题化后的name fragment
        changes = []
        for i in names:
            changes.append(i.title())
        # 集合化
        changes = set(changes)
        # 返回names和changes的并集
        return names.union(changes)

    # 判断是否是合法年份
    def isYear(x):
        x = int(x)
        if(x>1894 & x<2022):
            return True
        else:
            return False

    # 对比导演
    def compareDirector(self,temp,dir):
        if((temp.iloc[0,4] == "NULL") | (dir == "") | (dir == "*")):
            return False
        dirsIMDB = set(temp.iloc[0,4].split(','))
        dir = dir.replace("'",",")
        dirsAmazon = set(dir.split(','))
        if(DEBUG):
            print(dirsIMDB)
            print(dirsAmazon)
        if((dirsIMDB & dirsAmazon)!=set()):# 导演信息有交集
            return True
        else:
            return False
    

    # 对比imdb某电影和Amazon某产品对应的电影
    def compareRow(self,temp,dir):
        return self.compareDirector(temp,dir)

    # 在IMDB电影数据中查找Amazon标题
    def searchName(self,name):
        temp = self.movies.loc[(self.movies["primaryTitle"]==name) | (self.movies["originalTitle"]==name)]
        return temp
    
    # 添加电影asin到list
    def addAsinList(self,asins):
        asins = asins.replace("'"," ")
        asins = asins.split(",")
        for a in asins:
            a = a.strip()
            self.movieAsins.append(a)

    # 处理找不到的电影
    def consultMovieNotFound(self,name,videoName,item):
        self.failList.append(name)
        asin = item["asinRelative"][1:-1]
        self.addAsinList(asin)
        self.movieList.append({
            "asinRelative":item["asinRelative"],
            "genres":"NULL",
            "tconst":"NULL"
        })
        if(videoName == "NULL"):
            self.data.loc[self.data["asinRelative"]==item["asinRelative"],"videoName"] = name

    # 处理找到的电影
    def consultMovieFound(self,samename,maxindex,item):
        date = item["releaseDate"]
        actor = item["actor"]
        writer = item["writers"]
        asin = item["asinRelative"][1:-1]
        self.addAsinList(asin)
        self.movieList.append({
            "asinRelative":item["asinRelative"],
            "genres":samename.iloc[maxindex,3],
            "tconst":samename.index[maxindex]
        })
        print(samename)
        self.data.loc[self.data["asinRelative"]==item["asinRelative"],"videoName"] = samename.iloc[maxindex,0]
        if(date == "NULL"):
            self.data.loc[self.data["asinRelative"]==item["asinRelative"],"releaseDate"] = samename.iloc[maxindex,5]
        if(actor == "[]"):
            self.data.loc[self.data["asinRelative"]==item["asinRelative"],"actor"] = samename.iloc[maxindex,7]
        if(writer == "[]"):
            self.data.loc[self.data["asinRelative"]==item["asinRelative"],"writers"] = samename.iloc[maxindex,8]

    def judgeSame(self,samename,item):
        directors = item["director"][1:-1] 
        rows = samename.shape[0] # 获得行数
        # 行数为零
        if(rows == 0):
            print("[Error]")
            return False
        maxindex = -1
        for index in range(0,rows):
            temp = samename[index:index+1]
            # 对比导演
            result = self.compareRow(temp,directors)
            if(DEBUG):
                print(temp)
                print(result)
            if(result):# 吻合
                maxindex = index
                break
        if(maxindex == -1):# 没有导演吻合电影
            return False
        else:# 在IMDB有对应电影
            # 处理找到的IMDB电影
            self.consultMovieFound(samename,maxindex,item)
            if(DEBUG):
                index = maxindex
                print(samename[index:index+1])
            return True

    # start
    def dealWith(self,item):
        name = item["productName"]
        videoName = item["videoName"]
        isMovie = item["labelMovie"] 
        # asin是下架商品网页
        if(name=="NULL"):
            self.nothingAsins.append(item["asinRelative"])
            return
        # 有指向的电影名
        if(videoName != "NULL"):
            # 用电影名查找IMDB
            samename = self.searchName(videoName)
            if(samename.shape[0]==0):# 查到0条数据
                # 用商品名查找IMDB
                samename = self.searchName(name)
        else:
            # 用商品名查找
            samename = self.searchName(name)
        if(samename.shape[0]==0):# 查找到0条数据
            # 处理商品名：删除冗余信息
            samename = self.searchName(self.dealBracket(name))
            if(samename.shape[0]==0):# 依旧查找到0条
                # 处理商品名：再将处理分隔符将商品名划为多个碎片名
                tempNames = self.dealCut(self.dealBracket(name),item)
                # 用每一个碎片名在IMDB查找
                for i in tempNames:
                    i = self.dealBracket(i)
                    samename = self.searchName(i)
                    if(samename.shape[0]!=0):
                        # 判断是否是指向同一部电影
                        if(self.judgeSame(samename,item)):# 找到IMDB对应电影
                            return
                # 没找到
                if(isMovie == 1):# Amazon标记电影但是IMDB没有
                    if(DEBUG):
                        print("[Movie & Can't find] "+name)
                    self.consultMovieNotFound(name,videoName,item)
                    return
                else: # 不是电影
                    return
        else:# 找到数据
            if(self.judgeSame(samename,item)==False):# IMDB未找到
                if(isMovie == 1):# Amazon标记电影但是IMDB没有
                    if(DEBUG):
                        print("[Movie & Find But Not Sure The One] " + name)
                    self.consultMovieNotFound(name,videoName,item)
                    return
                else:
                    if(DEBUG):
                        print("[Find But Not Movie] " + name)


    def saveResult(self):
        form = pd.DataFrame(self.movieList)
        if(DEBUG):
            print(self.movieList)
            print(self.data)
            print(form)
        form = pd.merge(self.data,form,how="inner",on="asinRelative")
        form.to_csv('E:\\yze\\third\\courses\\DataWare\\allmovies.csv')
        with open(self.asin_output_path,'w') as file_obj:
            result = json.dumps(self.movieAsins,sort_keys=True, indent = 4)
            file_obj.write(result)
        print(form)
        print("The num of movie:")
        print(len(self.movieList)) # 电影数量
        print("The num of movie pages:")
        print(len(self.movieAsins)) # 电影商品网页数量
        print("The num of movie no found")
        print(len(self.failList)) # 没找到的电影数量

    def run(self):
        for item in self.dataDict:
            self.dealWith(item)
        self.saveResult()













