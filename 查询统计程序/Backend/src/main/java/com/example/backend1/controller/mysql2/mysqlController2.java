package com.example.backend1.controller.mysql2;

import com.example.backend1.entity.mysql.*;
import com.example.backend1.mapper.mysql2.MysqlMapper2;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"Denormalization MovieDB"})
@RestController
@CrossOrigin
public class mysqlController2 {
    @Autowired
    MysqlMapper2 mysqlMapper;


    //Search for movies by the year released
    @RequestMapping(value = "/getMoviesByYearFromD2", method = RequestMethod.GET)
    @ApiOperation("Search for movies by the year released")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "year",value = "default:2014")
    })
    public Helper getMoviesByYear(@RequestParam(value = "year") String year){
//        year="2014";
        long startTime = System.nanoTime();
        List<MovieDetail> movieDetails =mysqlMapper.getMoviesByYear(year);
        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
        Helper helper = new Helper(movieDetails,millsecs);
        return helper;
    }

    //Search for the num of movies released by a certain year
    @RequestMapping(value = "/getMoviesNumByYearFromD2", method = RequestMethod.GET)
    @ApiOperation("Search for the num of movies released by a certain year")
    public Helper getMoviesNumByYear(String year){
//        year="2014";
        long startTime = System.nanoTime();
        List<MovieDetail> movieDetails =mysqlMapper.getMoviesByYear(year);
        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
        return new Helper(movieDetails.size(),millsecs);
    }

    //Search for movies by a certain month of a certain year
    @RequestMapping(value = "/getMoviesByYMFromD2", method = RequestMethod.GET)
    @ApiOperation("Search for movies by a certain month of a certain year")
    public Helper getMoviesByYM(String year, String month){
//        year="2014";
//        month="3";
        long startTime = System.nanoTime();
        List<MovieDetail> movieDetails =mysqlMapper.getMoviesByYM(year,month);
        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
        return new Helper(movieDetails,millsecs);
    }

    //Search for the num of movies released in a certain month of a certain year
    @RequestMapping(value = "/getMoviesNumByYMFromD2",method = RequestMethod.GET)
    @ApiOperation("Search for the num of movies released in a certain month of a certain year")
    public Helper getMoviesNumByYM(String year,String month){
//        year="2014";
//        month="3";
        long startTime = System.nanoTime();
        List<MovieDetail> movieDetails =mysqlMapper.getMoviesByYM(year,month);
        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
        return new Helper(movieDetails.size(),millsecs);
    }

    //Search for movies by a certain quarter in a certain year
    @RequestMapping(value = "/getMoviesByYQFromD2",method = RequestMethod.GET)
    @ApiOperation("Search for movies by a certain quarter in a certain year")
    public Helper getMoviesByYQ(String year, String quarter){
//        year="2014";
//        quarter="1";
        long startTime = System.nanoTime();
        List<MovieDetail> movieDetails = mysqlMapper.getMoviesByYQ(year, quarter);
        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
        return new Helper(movieDetails,millsecs);
    }

    //Search movies by the first few words of title
    @RequestMapping(value = "/getMoviesByTitleFromD2",method = RequestMethod.GET)
    @ApiOperation("Search movies by the first few words of title")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "title",value = "default:Harry Potter")
    })
    public Helper getMoviesByTitle(@RequestParam(value = "title") String title){
//        title="Harry Potter";
        long startTime = System.nanoTime();
        List<MovieDetail> movieDetailList =mysqlMapper.getMoviesByTitle(title);
        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
        return new Helper(movieDetailList,millsecs);
    }

    //Search for movie by full title
    @RequestMapping(value = "/getMovieByTitleFromD2",method = RequestMethod.GET)
    @ApiOperation("Search for movie by full title")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "title",value = "To Be or Not to Be")
    })
    public Helper getMovieByTitle(@RequestParam(value = "title") String title){
//        title="Harry Potter";
        long startTime = System.nanoTime();
        List<MovieDetail> movieDetailList =mysqlMapper.getMovieByTitle(title);
        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
        return new Helper(movieDetailList,millsecs);
    }

    // Search for movies by director
    @RequestMapping(value = "/getMoviesByDirectorFromD2",method = RequestMethod.GET)
    @ApiOperation("Search for movies by director")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "name",value = "directorname : John huston")
    })
    public Helper getMoviesByDirector(@RequestParam(value = "name") String name){
//        name = "John huston";
        long startTime = System.nanoTime();
        List<MovieDetail> movieDetails = mysqlMapper.getMoviesByDirector(name);
        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
        return new Helper(movieDetails,millsecs);
    }

    // Search for movies by writer
    @RequestMapping(value = "/getMoviesByWriterFromD2",method = RequestMethod.GET)
    @ApiOperation("Search for movies by writer")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "name",value = "writername : Ethan Coen")
    })
    public Helper getMoviesByWriter(@RequestParam String name){
//        name = "Ethan Coen";
        long startTime = System.nanoTime();
        List<MovieDetail> movieDetails = mysqlMapper.getMoviesByWriter(name);
        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
        return new Helper(movieDetails,millsecs);
    }

    // Search for movies by actor
    @RequestMapping(value = "/getMoviesByActorFromD2",method = RequestMethod.GET)
    @ApiOperation("Search for movies by actor")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "name",value = "actorname : Oliver Hardy")
    })
    public Helper getMoviesByActor(@RequestParam(value = "name") String name){
//        name = "Oliver Hardy";
        long startTime = System.nanoTime();
        List<MovieDetail> movieDetails = mysqlMapper.getMoviesByActor(name);
        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
        return new Helper(movieDetails,millsecs);
    }

    // Search for movies by leading actor
    @RequestMapping(value = "/getMoviesLeadingByActorFromD2",method = RequestMethod.GET)
    @ApiOperation("Search for movies by leading actor")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "name",value = "actorname : Morgan Freeman")
    })
    public Helper getMoviesLeadingByActor(@RequestParam(value = "name") String name){
//        name = "Morgan Freeman";
        long startTime = System.nanoTime();
        List<MovieDetail> movieDetails = mysqlMapper.getMoviesLeadingByActor(name);
        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
        return new Helper(movieDetails,millsecs);
    }

    //Search for the top 20 groups of actors and actresses that worked together the most
    @RequestMapping(value = "/getCoopAATopFromD2",method = RequestMethod.GET)
    @ApiOperation("Search for the top 20 groups of actors and actresses that worked together the most")
    public Helper getCoopAATop(){
        long startTime = System.nanoTime();
        List<CoopAA> coopAAList = mysqlMapper.getCoopAATop();
        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
        return new Helper(coopAAList,millsecs);
    }

    // Search for the top 20 groups of actors and directors that worked together the most
    @RequestMapping(value = "/getCoopADTopFromD2",method = RequestMethod.GET)
    @ApiOperation("Search for the top 20 groups of actors and directors that worked together the most")
    public Helper getCoopADTop(){
        long startTime = System.nanoTime();
        List<CoopAD> coopADList = mysqlMapper.getCoopADTop();
        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
        return new Helper(coopADList,millsecs);
    }

    // Search for the top 20 groups of actors and writers that worked together the most
    @RequestMapping(value = "/getCoopAWTopFromD2",method = RequestMethod.GET)
    @ApiOperation("Search for the top 20 groups of actors and writers that worked together the most")
    public Helper getCoopAWTop(){
        long startTime = System.nanoTime();
        List<CoopAW> coopAWList = mysqlMapper.getCoopAWTop();
        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
        return new Helper(coopAWList,millsecs);
    }

    // Search for the top 20 groups of directors and writers that worked together the most
    @RequestMapping(value = "/getCoopDWTopFromD2",method = RequestMethod.GET)
    @ApiOperation("Search for the top 20 groups of directors and writers that worked together the most")
    public Helper getCoopDWTop(){
        long startTime = System.nanoTime();
        List<CoopDW> coopDWList = mysqlMapper.getCoopDWTop();
        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
        return new Helper(coopDWList,millsecs);
    }

    // Search for the directors that worked together with a certain actor
    @RequestMapping(value = "/getCoopADByActorFromD2",method = RequestMethod.GET)
    @ApiOperation("Search for the directors that worked together with a certain actor")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "name",value = "actorname : Todd Haberkorn")
    })
    public Helper getCoopADByActor(@RequestParam(value = "name") String actor){
//        actor = "Todd Haberkorn";
        long startTime = System.nanoTime();
        List<CoopAD> coopADList = mysqlMapper.getCoopADByActor(actor);
        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
        return new Helper(coopADList,millsecs);
    }

    // Search for the actors that worked together with a certain actor
    @RequestMapping(value = "/getCoopAAByActorFromD2",method = RequestMethod.GET)
    @ApiOperation("Search for the actors that worked together with a certain actor")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "name",value = "actorname : Todd Haberkorn")
    })
    public Helper getCoopAAByActor(@RequestParam(value = "name") String actor){
//        actor = "Todd Haberkorn";
        long startTime = System.nanoTime();
        List<CoopAA> coopAAList = mysqlMapper.getCoopAAByActor(actor);
        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
        return new Helper(coopAAList,millsecs);
    }

    @RequestMapping(value = "/getCoopAWByActorFromD2",method = RequestMethod.GET)
    @ApiOperation("Search for the writers that worked together with a certain actor")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "name",value = "actorname : Todd Haberkorn")
    })
    public Helper getCoopAWByActor(@RequestParam(value = "name") String actor){
        long startTime = System.nanoTime();
        List<CoopAW> coopAWList = mysqlMapper.getCoopAWByActor(actor);
        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
        return new Helper(coopAWList,millsecs);
    }

    @RequestMapping(value = "/getCoopDWByDirectorFromD2",method = RequestMethod.GET)
    @ApiOperation("Search for the writers that worked together with a certain director")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "name",value = "director : ")
    })
    public Helper getCoopDWByDirector(@RequestParam(value = "name") String director){
        long startTime = System.nanoTime();
        List<CoopDW> coopDWList = mysqlMapper.getCoopDWByDirector(director);
        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
        return new Helper(coopDWList,millsecs);
    }

    // Search for movies by a certain genres
    @RequestMapping(value = "/getMoviesByGenresFromD2",method = RequestMethod.GET)
    @ApiOperation("Search for movies by a certain genres")
    public Helper getMoviesByGenres(String genres){
//        genres = "Action";
        long startTime = System.nanoTime();
        List<MovieDetail> movieDetails = mysqlMapper.getMoviesByGenres(genres);
        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
        return new Helper(movieDetails,millsecs);
    }

    // Search for genres ranking based on the num of movies
    @RequestMapping(value = "/getGenresRankingFromD2",method = RequestMethod.GET)
    @ApiOperation("Search for genres ranking based on the num of movies")
    public Helper getGenresRanking(){
        long startTime = System.nanoTime();
        List<Genres> genresList = mysqlMapper.getGenresRanking();
        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
        return new Helper(genresList,millsecs);
    }

    // Search for movies by score
    @RequestMapping(value = "/getMoviesByScoreFromD2",method = RequestMethod.GET)
    @ApiOperation("Search for movies by score")
    public Helper getMoviesByScore(Integer score){
//        score = 4;
        long startTime = System.nanoTime();
        List<MovieScore> movies = mysqlMapper.getMoviesByScore(score);
        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
        return new Helper(movies,millsecs);
    }

    // Search for the top 20 movies receiving most reviews
    @RequestMapping(value = "/getMoviesByReviewNumFromD2",method = RequestMethod.GET)
    @ApiOperation("Search for the top 20 movies receiving most reviews")
    public Helper getMoviesByReviewNum(){
        long startTime = System.nanoTime();
        List<MovieReview> moives = mysqlMapper.getMoviesByReviewNum();
        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
        return new Helper(moives,millsecs);
    }

    @RequestMapping(value = "/getMoviesByCombinationFromD2",method = RequestMethod.GET)
    @ApiOperation("Search for movies by combination conditions")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "actor", value = "Todd Haberkorn"),
            @ApiImplicitParam(name = "director", value = "John huston"),
            @ApiImplicitParam(name = "genres", value = "Romance")
    })
    public Helper getMoviesByCombination(
            Integer year , Integer month , Integer quarter,
            String title , @RequestParam(value = "actor",required = false) String actor, @RequestParam(value = "director",required = false) String director ,
            @RequestParam(value = "genres",required = false) String genres , Integer score
    ){
        long startTime = System.nanoTime();
        List<MovieDetail> movieDetails = mysqlMapper.getMoviesByCombination(year, month, quarter, title, actor, director, genres,score);
        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
        return new Helper(movieDetails,millsecs);
    }

    @RequestMapping(value = "/getReviewsByMovieFromD2",method = RequestMethod.GET)
    @ApiOperation("Search for reviews of a certain movie")
    public Helper getReviewsByMovie(String title){
        long startTime = System.nanoTime();
        List<Review> reviewList = mysqlMapper.getReviewsByMovie(title);
        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
        return new Helper(reviewList,millsecs);
    }

    @RequestMapping(value = "/getMoviesWithoutNegReviewFromD2",method = RequestMethod.GET)
    @ApiOperation("Search for movies without negative review")
    public Helper getMoviesWithoutNegReview(){
        long startTime = System.nanoTime();
        List<MovieDetail> movieDetails = mysqlMapper.getMoviesWithoutNegReview();
        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
        return new Helper(movieDetails,millsecs);
    }

    @RequestMapping(value = "/getVersionByMovieFromD2",method = RequestMethod.GET)
    @ApiOperation("Search for version of a movie")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "title",value = "The Left Handed Gun")
    })
    public Helper getVersionByMovie(@RequestParam(value = "title") String title){
        long startTime = System.nanoTime();
        List<MovieVersion> movieVersions = mysqlMapper.getVersionByMovie(title);
        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
        return new Helper(movieVersions,millsecs);
    }
}
