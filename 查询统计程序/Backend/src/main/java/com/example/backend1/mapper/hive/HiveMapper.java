package com.example.backend1.mapper.hive;

import com.example.backend1.entity.hive.*;
import com.example.backend1.entity.mysql.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface HiveMapper {
//    List<HiveCoopAA> getAllActors();

    List<HiveMovieDetail> getHiveMoviesByYear(String year);

    List<HiveMovieDetail> getHiveMoviesByYM(String year, String month);

    List<HiveMovieDetail> getHiveMoviesByYQ(String year, String quarter);

    List<HiveMovieDetail> getHiveMoviesByTitle(String title);

    List<HiveMovieDetail> getHiveMovieByTitle(String title);

    List<HiveMovieDetail> getHiveMoviesByDirector(String directorName);

    List<HiveMovieDetail> getHiveMoviesByWriter(String writerName);

    List<HiveMovieDetail> getHiveMoviesByActor(String actorName);

    List<HiveMovieDetail> getHiveMoviesLeadingByActor(String actorName);

//    List<HiveCoopAA> getHiveCoopAATop();

//    List<HiveCoopAD> getHiveCoopADTop();
//
//    List<HiveCoopAW> getHiveCoopAWTop();
//
//    List<HiveCoopDW> getHiveCoopDWTop();
//
//    List<HiveCoopAD> getHiveCoopADByActor(String actor);
//
//    List<HiveCoopAA> getHiveCoopAAByActor(String actor);
//
//    List<HiveMovieDetail> getHiveMoviesByGenres(String genres);
//
//    List<HiveGenres> getHiveGenresRanking();
//
//    List<HiveMovieScore> getHiveMoviesByScore(Integer score);
//
//    List<HiveMovieReview> getHiveMoviesByReviewNum();
//
//    List<HiveMovieDetail> getHiveMoviesByCombination(
//            Integer year , Integer month , Integer quarter,
//            String title , String actor, String director ,
//            String genres, Integer score);
//
//    List<HiveReview> getHiveReviewsByMovie(String title);
//
//    List<HiveMovieDetail> getHiveMoviesWithoutNegReview();
//
//    List<HiveCoopAW> getHiveCoopAWByActor(String actor);
//
//    List<HiveCoopDW> getHiveCoopDWByDirector(String director);
}
