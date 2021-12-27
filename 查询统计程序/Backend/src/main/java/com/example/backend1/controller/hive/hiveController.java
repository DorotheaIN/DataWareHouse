package com.example.backend1.controller.hive;

import com.example.backend1.entity.hive.*;
import com.example.backend1.entity.mysql.*;
import com.example.backend1.mapper.hive.HiveMapper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class hiveController {
    @Autowired
    HiveMapper hiveMapper;

//    @ApiOperation("")
//    @RequestMapping(value = "/getHiveActors",method = RequestMethod.GET)
//    public List<HiveCoopAA> getAllActors()
//    {
//        return hiveMapper.getAllActors();
//    }

    @RequestMapping(value = "/getMoviesByYearFromHive", method = RequestMethod.GET)
    @ApiOperation("Hive search for movies by the year released")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "year",value = "default:2014")
    })
    public HiveHelper getHiveMoviesByYear(@RequestParam(value = "year",required = false) String year){
//        year="2014";
        long startTime = System.nanoTime();
        List<HiveMovieDetail> movieDetails =hiveMapper.getHiveMoviesByYear(year);
        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
        return new HiveHelper(movieDetails,millsecs);
    }

    //Search for movies by a certain month of a certain year
    @RequestMapping(value = "/getMoviesByYMFromHive", method = RequestMethod.GET)
    @ApiOperation("Search for movies by a certain month of a certain year")
    public HiveHelper getHiveMoviesByYM(String year, String month){
//        year="2014";
//        month="3";
        long startTime = System.nanoTime();
        List<HiveMovieDetail> movieDetails =hiveMapper.getHiveMoviesByYM(year,month);
        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
        return new HiveHelper(movieDetails,millsecs);
    }

    //Search for movies by a certain quarter in a certain year
    @RequestMapping(value = "/getMoviesByYQFromHive",method = RequestMethod.GET)
    @ApiOperation("Search for movies by a certain quarter in a certain year")
    public HiveHelper getMoviesByYQ(String year, String quarter){
//        year="2014";
//        quarter="1";
        long startTime = System.nanoTime();
        List<HiveMovieDetail> movieDetails = hiveMapper.getHiveMoviesByYQ(year, quarter);
        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
        return new HiveHelper(movieDetails,millsecs);
    }

    //Search movies by the first few words of title
    @RequestMapping(value = "/getMoviesByTitleFromHive",method = RequestMethod.GET)
    @ApiOperation("Search movies by the first few words of title")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "title",value = "default:Harry Potter")
    })
    public HiveHelper getHiveMoviesByTitle(@RequestParam(value = "title") String title){
//        title="";
        long startTime = System.nanoTime();
        List<HiveMovieDetail> movieDetailList =hiveMapper.getHiveMoviesByTitle(title);
        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
        return new HiveHelper(movieDetailList,millsecs);
    }

    // Search for movie by full title
    @RequestMapping(value = "/getMovieByTitleFromHive",method = RequestMethod.GET)
    @ApiOperation("Search for movie by full title")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "title",value = "default: To Be or Not to Be")
    })
    public HiveHelper getHiveMovieByTitle(@RequestParam(value = "title") String title){
//        title="Harry Potter";
        long startTime = System.nanoTime();
        List<HiveMovieDetail> movieDetailList =hiveMapper.getHiveMovieByTitle(title);
        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
        return new HiveHelper(movieDetailList,millsecs);
    }

    // Search for movies by director
    @RequestMapping(value = "/getMoviesByDirectorFromHive",method = RequestMethod.GET)
    @ApiOperation("Search for movies by director")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "name",value = "directorname : John huston")
    })
    public HiveHelper getHiveMoviesByDirector(@RequestParam(value = "name") String name){
//        name = "John huston";
        long startTime = System.nanoTime();
        List<HiveMovieDetail> movieDetails = hiveMapper.getHiveMoviesByDirector(name);
        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
        return new HiveHelper(movieDetails,millsecs);
    }

    // Search for movies by writer
    @RequestMapping(value = "/getMoviesByWriterFromHive",method = RequestMethod.GET)
    @ApiOperation("Search for movies by writer")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "name",value = "writername : Ethan Coen")
    })
    public HiveHelper getHiveMoviesByWriter(@RequestParam String name){
//        name = "Ethan Coen";
        long startTime = System.nanoTime();
        List<HiveMovieDetail> movieDetails = hiveMapper.getHiveMoviesByWriter(name);
        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
        return new HiveHelper(movieDetails,millsecs);
    }

    // Search for movies by actor
    @RequestMapping(value = "/getMoviesByActorFromHive",method = RequestMethod.GET)
    @ApiOperation("Search for movies by actor")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "name",value = "actorname : Oliver Hardy")
    })
    public HiveHelper getHiveMoviesByActor(@RequestParam(value = "name") String name){
//        name = "Oliver Hardy";
        long startTime = System.nanoTime();
        List<HiveMovieDetail> movieDetails = hiveMapper.getHiveMoviesByActor(name);
        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
        return new HiveHelper(movieDetails,millsecs);
    }

    // Search for movies by leading actor
    @RequestMapping(value = "/getMoviesLeadingByActorFromHive",method = RequestMethod.GET)
    @ApiOperation("Search for movies by leading actor")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "name",value = "actorname : Morgan Freeman")
    })
    public HiveHelper getHiveMoviesLeadingByActor(@RequestParam(value = "name") String name){
//        name = "Morgan Freeman";
        long startTime = System.nanoTime();
        List<HiveMovieDetail> movieDetails = hiveMapper.getHiveMoviesLeadingByActor(name);
        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
        return new HiveHelper(movieDetails,millsecs);
    }

//    // Search for the top 20 groups of actors and actresses that worked together the most
//    @RequestMapping(value = "/getCoopAATopFromHive",method = RequestMethod.GET)
//    @ApiOperation("Search for the top 20 groups of actors and actresses that worked together the most")
//    public HiveHelper getHiveCoopAATop(){
//        long startTime = System.nanoTime();
//        List<HiveCoopAA> coopAAList = hiveMapper.getHiveCoopAATop();
//        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
//        return new HiveHelper(coopAAList,millsecs);
//    }
//
//    // Search for the top 20 groups of actors and directors that worked together the most
//    @RequestMapping(value = "/getCoopADTopFromHive",method = RequestMethod.GET)
//    @ApiOperation("Search for the top 20 groups of actors and directors that worked together the most")
//    public HiveHelper getHiveCoopADTop(){
//        long startTime = System.nanoTime();
//        List<HiveCoopAD> coopADList = hiveMapper.getHiveCoopADTop();
//        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
//        return new HiveHelper(coopADList,millsecs);
//    }
//
//    // Search for the top 20 groups of actors and writers that worked together the most
//    @RequestMapping(value = "/getCoopAWTopFromHive",method = RequestMethod.GET)
//    @ApiOperation("Search for the top 20 groups of actors and writers that worked together the most")
//    public HiveHelper getHiveCoopAWTop(){
//        long startTime = System.nanoTime();
//        List<HiveCoopAW> coopAWList = hiveMapper.getHiveCoopAWTop();
//        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
//        return new HiveHelper(coopAWList,millsecs);
//    }
//
//    // Search for the top 20 groups of directors and writers that worked together the most
//    @RequestMapping(value = "/getCoopDWTopFromHive",method = RequestMethod.GET)
//    @ApiOperation("Search for the top 20 groups of directors and writers that worked together the most")
//    public HiveHelper getHiveCoopDWTop(){
//        long startTime = System.nanoTime();
//        List<HiveCoopDW> coopDWList = hiveMapper.getHiveCoopDWTop();
//        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
//        return new HiveHelper(coopDWList,millsecs);
//    }
//
//    // Search for the directors that worked together with a certain actor
//    @RequestMapping(value = "/getCoopADByActorFromHive",method = RequestMethod.GET)
//    @ApiOperation("Search for the directors that worked together with a certain actor")
//    @ApiImplicitParams(value = {
//            @ApiImplicitParam(name = "name",value = "actorname : Todd Haberkorn")
//    })
//    public HiveHelper getHiveCoopADByActor(@RequestParam(value = "name") String actor){
////        actor = "Todd Haberkorn";
//        long startTime = System.nanoTime();
//        List<HiveCoopAD> coopADList = hiveMapper.getHiveCoopADByActor(actor);
//        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
//        return new HiveHelper(coopADList,millsecs);
//    }
//
//
//    // Search for the actors that worked together with a certain actor
//    @RequestMapping(value = "/getCoopAAByActorFromHive",method = RequestMethod.GET)
//    @ApiOperation("Search for the actors that worked together with a certain actor")
//    @ApiImplicitParams(value = {
//            @ApiImplicitParam(name = "name",value = "actorname : Todd Haberkorn")
//    })
//    public HiveHelper getHiveCoopAAByActor(@RequestParam(value = "name") String actor){
////        actor = "Todd Haberkorn";
//        long startTime = System.nanoTime();
//        List<HiveCoopAA> coopAAList = hiveMapper.getHiveCoopAAByActor(actor);
//        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
//        return new HiveHelper(coopAAList,millsecs);
//    }
//
//    // Search for movies by a certain genres
//    @RequestMapping(value = "/getMoviesByGenresFromHive",method = RequestMethod.GET)
//    @ApiOperation("Search for movies by a certain genres")
//    public HiveHelper getHiveMoviesByGenres(String genres){
////        genres = "Action";
//        long startTime = System.nanoTime();
//        List<HiveMovieDetail> movieDetails = hiveMapper.getHiveMoviesByGenres(genres);
//        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
//        return new HiveHelper(movieDetails,millsecs);
//    }
//
//    // Search for genres ranking based on the num of movies
//    @RequestMapping(value = "/getGenresRankingFromHive",method = RequestMethod.GET)
//    @ApiOperation("Search for genres ranking based on the num of movies")
//    public HiveHelper getHiveGenresRanking(){
//        long startTime = System.nanoTime();
//        List<HiveGenres> genresList = hiveMapper.getHiveGenresRanking();
//        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
//        return new HiveHelper(genresList,millsecs);
//    }
//
//
//    // Search for movies by score
//    @RequestMapping(value = "/getMoviesByScoreFromHive",method = RequestMethod.GET)
//    @ApiOperation("Search for movies by score")
//    public HiveHelper getHiveMoviesByScore(Integer score){
////        score = 4;
//        long startTime = System.nanoTime();
//        List<HiveMovieScore> movies = hiveMapper.getHiveMoviesByScore(score);
//        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
//        return new HiveHelper(movies,millsecs);
//    }
//
//    // Search for the top 20 movies receiving most reviews
//    @RequestMapping(value = "/getMoviesByReviewNumFromHive",method = RequestMethod.GET)
//    @ApiOperation("Search for the top 20 movies receiving most reviews")
//    public HiveHelper getHiveMoviesByReviewNum(){
//        long startTime = System.nanoTime();
//        List<HiveMovieReview> moives = hiveMapper.getHiveMoviesByReviewNum();
//        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
//        return new HiveHelper(moives,millsecs);
//    }
//
//    @RequestMapping(value = "/getMoviesByCombinationFromHive",method = RequestMethod.GET)
//    @ApiOperation("Search for movies by combination conditions")
//    @ApiImplicitParams(value = {
//            @ApiImplicitParam(name = "actor", value = "Todd Haberkorn"),
//            @ApiImplicitParam(name = "director", value = "John huston"),
//            @ApiImplicitParam(name = "genres", value = "Romance")
//    })
//    public HiveHelper getHiveMoviesByCombination(
//            Integer year , Integer month , Integer quarter,
//            String title , @RequestParam(value = "actor") String actor, @RequestParam(value = "director") String director ,
//            @RequestParam(value = "genres") String genres , Integer score
//    ){
//        long startTime = System.nanoTime();
//        List<HiveMovieDetail> movieDetails = hiveMapper.getHiveMoviesByCombination(year, month, quarter, title, actor, director, genres,score);
//        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
//        return new HiveHelper(movieDetails,millsecs);
//    }
//
//    @RequestMapping(value = "/getReviewsByMovieFromHive",method = RequestMethod.GET)
//    @ApiOperation("Search for reviews of a certain movie")
//    public HiveHelper getHiveReviewsByMovie(String title){
//        long startTime = System.nanoTime();
//        List<HiveReview> reviewList = hiveMapper.getHiveReviewsByMovie(title);
//        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
//        return new HiveHelper(reviewList,millsecs);
//    }
//
//    @RequestMapping(value = "/getMoviesWithoutNegReviewFromHive",method = RequestMethod.GET)
//    @ApiOperation("Search for movies without negative review")
//    public HiveHelper getHiveMoviesWithoutNegReview(){
//        long startTime = System.nanoTime();
//        List<HiveMovieDetail> movieDetails =hiveMapper.getHiveMoviesWithoutNegReview();
//        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
//        return new HiveHelper(movieDetails,millsecs);
//    }
//
//    @RequestMapping(value = "/getCoopAWByActorFromHive",method = RequestMethod.GET)
//    @ApiOperation("Search for the writers that worked together with a certain actor")
//    @ApiImplicitParams(value = {
//            @ApiImplicitParam(name = "name",value = "actorname : Todd Haberkorn")
//    })
//    public HiveHelper getHiveCoopAWByActor(@RequestParam(value = "name") String actor){
//        long startTime = System.nanoTime();
//        List<HiveCoopAW> coopAWList = hiveMapper.getHiveCoopAWByActor(actor);
//        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
//        return new HiveHelper(coopAWList,millsecs);
//    }
//
//    @RequestMapping(value = "/getCoopDWByDirectorFromHive",method = RequestMethod.GET)
//    @ApiOperation("Search for the writers that worked together with a certain director")
//    @ApiImplicitParams(value = {
//            @ApiImplicitParam(name = "name",value = "director : ")
//    })
//    public HiveHelper getHiveCoopDWByDirector(@RequestParam(value = "name") String director){
//        long startTime = System.nanoTime();
//        List<HiveCoopDW> coopDWList = hiveMapper.getHiveCoopDWByDirector(director);
//        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
//        return new HiveHelper(coopDWList,millsecs);
//    }
}
