package com.example.backend1.entity.mysql;

public class MovieScore extends Movie {

    private Double avgScore;

    public Double getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(Double avgScore) {
        this.avgScore = avgScore;
    }
}
