package com.example.backend1.entity.neo4j;

public class Helper<T>{
    private T data;
    private double time;

    public Helper(T data, double time) {
        this.data = data;
        this.time = time;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }
}
