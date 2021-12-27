package com.example.backend1.mapper.mysql2;

import com.example.backend1.entity.mysql.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MysqlMapper2 {
    List<MovieDetail> getMoviesByYear(String year);

    List<MovieDetail> getMoviesByYM(String year, String month);

    List<MovieDetail> getMoviesByYQ(String year, String quarter);

    List<MovieDetail> getMovieByTitle(String title);

    List<MovieDetail> getMoviesByTitle(String title);

    List<MovieDetail> getMoviesByDirector(String directorName);

    List<MovieDetail> getMoviesByWriter(String writerName);

    List<MovieDetail> getMoviesByActor(String actorName);

    List<MovieDetail> getMoviesLeadingByActor(String actorName);

    List<CoopAA> getCoopAATop();

    List<CoopAD> getCoopADTop();

    List<CoopAW> getCoopAWTop();

    List<CoopDW> getCoopDWTop();

    List<CoopAD> getCoopADByActor(String actor);

    List<CoopAA> getCoopAAByActor(String actor);

    List<MovieDetail> getMoviesByGenres(String genres);

    List<Genres> getGenresRanking();

    List<MovieScore> getMoviesByScore(Integer score);

    List<MovieReview> getMoviesByReviewNum();

    List<MovieDetail> getMoviesByCombination(
            Integer year , Integer month , Integer quarter,
            String title , String actor, String director ,
            String genres, Integer score);

    List<Review> getReviewsByMovie(String title);

    List<MovieDetail> getMoviesWithoutNegReview();

    List<CoopAW> getCoopAWByActor(String actor);

    List<CoopDW> getCoopDWByDirector(String director);

    List<MovieVersion> getVersionByMovie(String title);
}
