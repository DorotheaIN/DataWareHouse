package com.example.backend1.entity.neo4j;

public class CoopDetail {
    String name1;
    String name2;
    int count;

    public CoopDetail(String name1, String name2, int count) {
        this.name1 = name1;
        this.name2 = name2;
        this.count = count;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
