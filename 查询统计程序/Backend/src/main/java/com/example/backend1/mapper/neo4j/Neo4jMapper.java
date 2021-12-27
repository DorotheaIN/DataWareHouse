package com.example.backend1.mapper.neo4j;

import com.example.backend1.entity.neo4j.Actor;
import com.example.backend1.entity.neo4j.Coopration;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface Neo4jMapper {
    //No starting point
    List<Coopration> getTopCoopAA();
    List<Coopration> getTopCoopAD();
    List<Coopration> getTopCoopAW();
    List<Coopration> getTopCoopDW();

    //Starting point
    List<Coopration> getCoopAA(String name);
    List<Coopration> getCoopAD(String name);
    List<Coopration> getCoopAW(String name);
    List<Coopration> getCoopDW(String name);


}
