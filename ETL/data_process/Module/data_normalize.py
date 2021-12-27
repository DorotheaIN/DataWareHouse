import pandas as pd
import json
import re
import calendar


class Normalizer():
    def __init__(self,input_path,output_path):
        # data_path = 'E:\\yze\\third\\courses\\DataWare\\allmovies.csv'
        self.output_path = output_path
        self.data = pd.read_csv(input_path,index_col=0,header=0,low_memory=False)
        self.data.fillna('NULL',inplace=True)
        self.dataDict = self.data.to_dict(orient = 'records')

    def asinNormalize(asins):
        result = ""
        asins = asins.replace("'"," ")
        asins = asins.split(",")
        for i in asins:
            i = i.strip()
            result = result + i +','
        return result[:-1]

    def characterNormalize(character):
        if(character[0] == '['): # 原生格式
            character = character[1:-1]
            if((character == '') | (character == "'.'") | (character == "'__'") | (character=="'*'") | (character=="'-'")):
                return "NULL"
        else:
            if((character == '') | (character == "'.'") | (character == "'__'") | (character=="'*'") | (character=="'-'")):
                return "NULL"
            else:
                character = character.split(", ")
                charlist = set()
                result = ""
                for i in character:
                    charlist.add(i)
                for i in charlist:
                    result = result+i+','
                return result[:-1]
        result = ""
        charlist = set()
        character = character.split(", ")
        for i in character:
            if((i[0]=="'") | (i[0]=='"')):
                i = i[1:]
            if((i[-1]=="'") | (i[-1]=='"')):
                i = i[:-1]
            if(',' in i):# "Josh Breckenridge,Derrick L. Briggs,Bry'Nt", "Josh Breckenridge,\xa0Derrick L. Briggs,\xa0Bry'Nt"
                i = i.split(',')
                for item in i:
                    if(item[:4] == '\\xa0'):
                        item = item[4:]
                    if(item[-4:] == '\\xa0'):
                        item = item[:-4]
                    # print(item)
                    charlist.add(item)
            else:
                if(i[:4] == '\\xa0'):
                    i = i[4:]
                if(i[-4:] == '\\xa0'):
                    i = i[:-4]
                charlist.add(i)
        # print(charlist)
        for i in charlist:
            if(i != ''):
                result = result + i + ','
        return result[:-1]


    def runtimeNormalize(runtime):
        if((runtime == "NULL") | (runtime == "")):
            return "NULL"
        list = re.findall(r"\d+\.?\d*",runtime)
        if(len(list)==1):
            if("hour" in runtime):
                return int(list[0])*60
            else:
                if("minute" in runtime):
                    return int(list[0])
                else:
                    print(runtime)
                    return int(list[0])
        else:
            if(len(list)==2):
                return int(list[0])*60+int(list[1])
            else:
                print(runtime)


    def dateNormalize(date):
        if((date == "") | (date == "NULL")):
            return "NULL"
        else:
            return date
        # if('-' in date): # imdb格式
        #     return date
        # result = ""
        # date = date.split(" ")
        # if(len(date)!=3):
        #     if(len(date)==1):
        #         return date[0]
        #     else:
        #         print("[Error] date")
        #         print(date)
        #         return "NULL"
        # else:
        #     result = date[2] + "-"+str(list(calendar.month_name).index(date[0])).zfill(2)+"-"+date[1][:-1]
        #     return result

    def amazonRatingNormalize(rating):
        if(rating == "NULL"):
            return "NULL"
        list = re.findall(r"\d+\.?\d*",rating)
        if(len(list)==2):
            return list[0]
        else:
            print("[Error] amazon rating")
            print(rating)
            return "NULL"

    def imdbRatingNormalize(rating):
        if(rating == "NULL"):
            return "NULL"
        list = re.findall(r"\d+\.?\d*",rating)
        if(len(list)==2):
            return list[0]
        else:
            print("[Error] imdb rating")
            print(rating)
            return "NULL"


    def ratingNumNormalize(num):
        if(num == "NULL"):
            return "NULL"
        list = re.findall(r"\d+\.?\d*",num)
        result = ""
        if(len(list)>=1):
            for i in list:
                result += i
            return result
        else:
            print("[Error] rating num")
            print(num)
            return "NULL"


    def genresNormalize(genres):
        if((genres == "NULL") | (genres == "\\N")):
            return "NULL"
        else:
            return genres

    def run(self):
        count = 14
        id = 0
        for item in self.dataDict:
            id +=1
            del item["productName"]
            del item["dateFirstAvailable"]
            del item["label"]
            del item["labelMovie"]
            item["MovieId"] = str(id).zfill(5) 
            count -=1
            item["asinRelative"] = self.asinNormalize(item["asinRelative"][1:-1])
            item["director"] = self.characterNormalize(item["director"])
            item["actor"] = self.characterNormalize(item["actor"])
            item["mainActor"] = self.characterNormalize(item["mainActor"])
            item["producers"] = self.characterNormalize(item["producers"])
            item["writers"] = self.characterNormalize(item["writers"])
            item["runTime"] = self.runtimeNormalize(item["runTime"]) # 单位minutes
            item["releaseDate"] = self.dateNormalize(item["releaseDate"]) # year-month-day
            item["rating"] = self.amazonRatingNormalize(item["rating"])
            item["imdbRating"] = self.imdbRatingNormalize(item["imdbRating"]) # Int
            item["ratingNum"] = self.ratingNumNormalize(item["ratingNum"])
            item["genres"] = self.genresNormalize(item["genres"])
        dp = pd.DataFrame(self.dataDict)  
        dp.columns = ['title','asinRelated','directors','runTime','releaseDate','actors','mainActors','studio','productors','writers','amazonRating','imdbRating','ratingNum','genres','tconst','id']
        dp = dp.set_index('id')
        dp.to_csv(self.output_path)



    


