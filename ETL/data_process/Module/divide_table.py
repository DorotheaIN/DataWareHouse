from numpy.core.records import record
import pandas as pd
from pandas.core.frame import DataFrame



class Divider():
    def __init__(self):
        data_path = './Data/normalized_movies.csv'
        self.data = pd.read_csv(data_path,header=0,low_memory=False,index_col=0)
        self.data.fillna('NULL',inplace=True)
        self.review = pd.read_csv("./Data/review.csv",header=0,low_memory=False)
        self.review.fillna('NULL',inplace=True)
        del self.review['style']
        del self.review['Format']
        del self.review['all_asin']
        del self.review['unixReviewTime']
        self.review['vote'].replace('NULL','0',inplace=True)


    def getReviewerTable(self):
        user = self.review[['reviewerID','reviewerName']].drop_duplicates(['reviewerID'])
        user.to_csv("./Data/tables/reviewer_table.csv",index=False)

    def normalizeDate(x):
        numbers = x.split(' ')
        if(len(numbers)!=3):
            print(x)
            return x
        else:
            return numbers[2]+'-'+numbers[0]+'-'+numbers[1][:-1]

    def getReviewTable(self):
        review_table = self.review
        # del review_table["reviewerName"]
        review_table['reviewTime'] = review_table['reviewTime'].map(lambda x:self.normalizeDate(x))
        review_table = review_table.drop_duplicates(['reviewerID','asin'])
        review_table.to_csv("./Data/tables/review_table.csv",index=False)
          

    def getActTable(self):
        act_table_path = "./Data/tables/act_table.csv"
        actor_table_path = "./Data/tables/actor_table.csv"
        act_movie_table_path = "./Data/tables/act_movie_table.csv"
        temp = self.data["actors"].str.split(',',expand=True)
        temp = temp.stack()
        temp = temp.reset_index(level = 1,drop = True)
        temp = pd.DataFrame({'movieId':temp.index,'actorName':temp.values})
        act_table = temp.drop((temp.loc[temp["actorName"]=="NULL"]).index)
        act_table.to_csv(act_table_path,index=False)# movie_id,actor_name
        # 生成actor_id
        actor_table = act_table["actorName"].values.tolist()
        actor_table = set(actor_table)
        actor_dict = {}
        index = 0
        for item in actor_table:
            index = index + 1
            actor_dict[item] = index
        # 输出csv
        actor_table = pd.DataFrame.from_dict(actor_dict,orient='index',columns=['actorId'])
        actor_table = actor_table.reset_index().rename(columns={'index':'actorName'})
        actor_table.to_csv(actor_table_path,index=False)# actor_id & actor_name
        # 添加leading字段
        actor_movie_list = act_table.to_dict(orient = 'records')
        for item in actor_movie_list:
            # 匹配actorId
            item["actorId"] = actor_dict[item["actorName"]]
            # 匹配leading
            info = self.data.loc[[item["movieId"]]]
            mainActor = info.at[item["movieId"],"mainActors"]
            mainActor = mainActor.split(',')
            if(item["actorName"] in mainActor):
                item["leading"] = "True"
            else:
                item["leading"] = "False"
        act_table = DataFrame(actor_movie_list)
        # del act_table["actorName"]
        print(act_table)
        act_table.to_csv(act_movie_table_path,index=False) # actor_id,moive_id



    def getDirectTable(self):
        director_table_path = "./Data/tables/director_table.csv"
        director_moive_table_path = "./Data/tables/director_moive_table.csv"
        temp = self.data["directors"].str.split(',',expand=True)
        temp = temp.stack()
        temp = temp.reset_index(level = 1,drop = True)
        temp = pd.DataFrame({'movieId':temp.index,'directorName':temp.values}) 
        direct_table = temp.drop((temp.loc[temp["directorName"]=="NULL"]).index) # movieId & directorName
        direct_table.to_csv("./Data/tables/direct_table.csv",index=False)
        # 生成导演id
        director_table = direct_table["directorName"].values.tolist()
        director_table = set(director_table)
        director_dict = {}
        index = 0
        for item in director_table:
            index = index + 1
            director_dict[item] = index
        director_table = pd.DataFrame.from_dict(director_dict,orient='index',columns=['directorId'])
        director_table = director_table.reset_index().rename(columns={'index':'directorName'})
        director_table.to_csv(director_table_path,index=False)# director_id & director_name
        # 替换direct_table导演名
        direct_movie_list = direct_table.to_dict(orient = 'records')
        for item in direct_movie_list:
            item["directorId"] = director_dict[item["directorName"]]
        direct_table = DataFrame(direct_movie_list)
        # del direct_table["directorName"]
        direct_table.to_csv(director_moive_table_path,index=False)
        print(direct_table)
        

    def getWriteTable(self):
        writer_table_path = "./Data/tables/writer_table.csv"
        writer_movie_table_path = "./Data/tables/write_movie_table.csv"
        temp = self.data["writers"].str.split(',',expand=True)
        temp = temp.stack()
        temp = temp.reset_index(level = 1,drop = True)
        temp = pd.DataFrame({'movieId':temp.index,'writerName':temp.values})
        write_table = temp.drop((temp.loc[temp["writerName"]=="NULL"]).index)
        write_table.to_csv("./Data/tables/write_table.csv",index=False)
        writer_table = write_table["writerName"].values.tolist()
        writer_table = set(writer_table)
        writer_dict = {}
        index = 0
        for item in writer_table:
            index = index+1
            writer_dict[item] = index
        writer_table = pd.DataFrame.from_dict(writer_dict,orient='index',columns=['writerId'])
        writer_table = writer_table.reset_index().rename(columns={'index':'writerName'})
        writer_table.to_csv(writer_table_path,index=False) # writerId & writerName
        # 替换writerName
        write_movie_list = write_table.to_dict(orient='records')
        for item in write_movie_list:
            item["writerId"] = writer_dict[item["writerName"]]
        write_table = DataFrame(write_movie_list)
        # del write_table["writerName"]
        write_table.to_csv(writer_movie_table_path,index=False)
        print(write_table)
        
    def getMovieTable(self):
        movie_table_path = "./Data/tables/movie_table.csv"
        movie = self.data[['title','runTime','releaseDate','amazonRating','ratingNum']]
        movie["runTime"].replace('NULL','0',inplace=True)
        movie["releaseDate"].replace('NULL','0000/00/00',inplace=True)
        movie.replace('NULL','0',inplace=True)
        movie.to_csv(movie_table_path)
        print(movie)


    def getMoviePage(self):
        movie_from_table_path = "./Data/tables/movie_from_table.csv"
        temp = self.data["asinRelated"].str.split(',',expand=True)
        temp = temp.stack()
        temp = temp.reset_index(level = 1,drop = True)
        movie_from = pd.DataFrame({'movieId':temp.index,'productAsin':temp.values})
        movie_from.to_csv(movie_from_table_path,index=False)

    def getGenresTable(self):
        genres_table_path = "./Data/tables/genres_table.csv"
        movie_genres_table_path = "./Data/tables/genres_movie_table.csv"
        temp = self.data["genres"].str.split(',',expand=True)
        temp = temp.stack()
        temp = temp.reset_index(level = 1,drop = True)
        temp = pd.DataFrame({'movieId':temp.index,'genresName':temp.values})
        genres_movie_table = temp.drop((temp.loc[temp["genresName"]=="NULL"]).index) #  movieId & directorName
        genres_list = genres_movie_table["genresName"].values.tolist()
        genres_list = set(genres_list)
        genres_dict = {}
        index = 0
        for item in genres_list:
            index = index+1
            genres_dict[item] = index
        genres_list = genres_movie_table.to_dict(orient="record")
        genres_table = pd.DataFrame.from_dict(genres_dict,orient='index',columns=['genresId'])
        genres_table = genres_table.reset_index().rename(columns={'index':'genresName'})
        genres_table.to_csv(genres_table_path,index=False) # writerId & writerName
        for item in genres_list:
            item["genresId"] = genres_dict[item["genresName"]]
        genres_movie_table = DataFrame(genres_list)
        genres_movie_table.to_csv(movie_genres_table_path,index=False)
        print(genres_table)

    def run(self):
        self.getActTable()
        self.getDirectTable()
        self.getWriteTable()
        self.getMovieTable()
        self.getMoviePage()
        self.getGenresTable()
        self.getReviewerTable()
        self.getReviewTable()

