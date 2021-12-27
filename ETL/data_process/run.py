from Module.movies_filter import Filter
from Module.data_normalize import Normalizer
from Module.divide_table import Divider
import Module.Spider
from Module.duplicationElimination import dE
from Module.asinExtract import asinExtract


if __name__ == '__main__':
    origin_file_path = "./Data/Movies_and_TV_5.json"
    all_asins_path = " ./Data/asin.json"
    html_dir_path = "./Data/html"
    all_data_path = "./Data/data.csv"
    imdb_movies_path = './Data/imdbmovies.tsv'
    movies_filtered_path = "./Data/filtered_movies.csv"
    movies_normalized_path = "./Data/normalized_movies.csv"
    asin_path="./Data/asin.json"

    #Extract all asins from Movies_and_TV_5.json
    asinExtract(origin_file_path, all_asins_path)

    #Crawl the web
    Spider.run()

    #parse the Web & deduplicate
    dE(all_asins_path, html_dir_path, all_data_path)

    # filter movies 
    filter = Filter(imdb_movies_path,all_data_path,movies_filtered_path,'./Data/filtered_movies_asins.json')
    filter.run()

    # normalize the filtered movies
    normalizer = Normalizer(movies_filtered_path,movies_normalized_path)
    normalizer.run()

    # divide tables based on normalized movies info
    divider = Divider()
    divider.run()