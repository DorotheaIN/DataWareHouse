package com.example.backend1.entity.hive;

public class HiveMovieDetail extends HiveMovie{

    private Integer runtime;

    private String releasedate;

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public String getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(String releasedate) {
        this.releasedate = releasedate;
    }

}
