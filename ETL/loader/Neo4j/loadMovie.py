from py2neo import Graph, Node, Relationship
import csv
import pandas as pd
from py2neo.matching import NodeMatcher
#连接数据库
graph=Graph("http://localhost:7474",auth=("neo4j","SSG010824"))
#清除原有所有节点等信息
graph.delete_all()

#电影标签：id[0] title[1] asin[2] runTime[4] releaseDate[5] mainActor[7] studio[8] amazonRating[11] imdbRating[12] ratingNum[13] genres[14] tconst[15]
#演员标签：name[6]
#导演标签：name[3]
#编剧标签：name[10]
   #制片人标签：name[9]
   #asin标签：id[2]

#读取电影数据
with open('E:\\大三上\\DW\\neo4j\\movies_normalized.csv','r',encoding="utf-8") as f:
    reader=csv.reader(f)
    data=list(reader)
#为了去重采用匹配
matcher=NodeMatcher(graph)
for i in range(1,len(data)):
    #电影是唯一的，可直接创建
    if(data[i][5]=='NULL'):
        data[i][5]='0000/00/0'
    MovieNode=Node('Movie',title=data[i][1],asin=data[i][2],runTime=data[i][4],releaseDate=data[i][5],mainActor=data[i][7],studio=data[i][8],amazonRating=data[i][11],imdbRating=data[i][12],ratingNum=data[i][13],genres=data[i][14],tconst=data[i][15])
    graph.create(MovieNode)
    #演员
    if(data[i][6]!='NULL'):
        #形成list形式
        ActorNameList=data[i][6].split(',')
        for j in range(0,len(ActorNameList)):
            #匹配此刻数据库中是否有该节点，如果有直接创建关系，如果没有先创建节点
            m=matcher.match('Actor',name=ActorNameList[j]).first()
            if m is None:
                ActorNode=Node('Actor',name=ActorNameList[j])
                graph.create(ActorNode)
                act=Relationship(ActorNode,'act',MovieNode)
                graph.create(act)
            else:
                act=Relationship(m,'act',MovieNode)
                graph.create(act)
    #导演
    if(data[i][3]!='NULL'):
        DirectorNameList=data[i][3].split(',')
        for k in range(0,len(DirectorNameList)):
            m=matcher.match('Director',name=DirectorNameList[k]).first()
            if m is None:
                DirectorNode=Node('Director',name=DirectorNameList[k])
                graph.create(DirectorNode)
                direct=Relationship(DirectorNode,'direct',MovieNode)
                graph.create(direct)
            else:
                direct=Relationship(m,'direct',MovieNode)
                graph.create(direct)
    #编剧
    if(data[i][10]!='NULL'):
        ScreenwriterNameList=data[i][10].split(',')
        for a in range(0,len(ScreenwriterNameList)):
            m=matcher.match('Screenwriter',name=ScreenwriterNameList[a]).first()
            if m is None:
                ScreenwriterNode=Node('Screenwriter',name=ScreenwriterNameList[a])
                graph.create(ScreenwriterNode)
                write=Relationship(ScreenwriterNode,'write',MovieNode)
                graph.create(write)
            else:
                write=Relationship(m,'write',MovieNode)
                graph.create(write)
    # #制片人
    # if(data[i][9]!='NULL'):
    #     ProducerNameList=data[i][9].split(',')
    #     for n in range(0,len(ProducerNameList)):
    #         m=matcher.match('Producer',name=ProducerNameList[n]).first()
    #         if m is None:
    #             ProducerNode=Node('Producer',name=ProducerNameList[n])
    #             graph.create(ProducerNode)
    #             produce=Relationship(ProducerNode,'produce',MovieNode)
    #             graph.create(produce)
    #         else:
    #             produce=Relationship(m,'produce',MovieNode)
    #             graph.create(produce)
    # #asin
    # if(data[i][2]!='NULL'):
    #     AsinList=data[i][2].split(',')
    #     for p in range(0,len(AsinList)):
    #         m=matcher.match('Asin',name=AsinList[p]).first()
    #         if m is None:
    #             AsinNode=Node('Asin',name=AsinList[p])
    #             graph.create(AsinNode)
    #             relative=Relationship(AsinNode,'relative',MovieNode)
    #             graph.create(relative)
                




