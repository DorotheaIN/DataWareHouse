package com.example.backend1.entity.mysql;

public class MovieDetail extends Movie{

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
