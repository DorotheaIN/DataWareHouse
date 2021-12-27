package com.example.backend1.mapper.mysql;

import com.example.backend1.entity.mysql.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MysqlMapper {
    List<MovieDetail> getMoviesByYear(String year);

    List<MovieDetail> getMoviesByYM(String year, String month);

    List<MovieDetail> getMoviesByYQ(String year, String quarter);

    List<MovieDetail> getMovieByTitle(String title);

    List<MovieDetail> getMoviesByTitle(String title);

    List<MovieDetail> getMoviesByDirector(String directorName);

    List<MovieDetail> getMoviesByWriter(String writerName);

    List<MovieDetail> getMoviesByActor(String actorName);

    List<MovieDetail> getMoviesLeadingByActor(String actorName);

    List<Coop> getCoopAATop();

    List<Coop> getCoopADTop();

    List<Coop> getCoopAWTop();

    List<Coop> getCoopDWTop();

    List<Coop> getCoopADByActor(String actor);

    List<Coop> getCoopAAByActor(String actor);

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

    List<Coop> getCoopAWByActor(String actor);

    List<Coop> getCoopDWByDirector(String director);

    List<MovieVersion> getVersionByMovie(String title);

}
