package com.example.backend1.controller.neo4j;


import com.example.backend1.entity.neo4j.CoopDetail;
import com.example.backend1.entity.neo4j.Coopration;
import com.example.backend1.entity.neo4j.Helper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.backend1.mapper.neo4j.Neo4jMapper;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class neo4jController {
    @Autowired(required = false)
    Neo4jMapper neo4jMapper;
    @ApiOperation("无起点搜索演员合作关系前20")
    @RequestMapping(value = "/getTopCoopAA", method = RequestMethod.GET)
    public Helper getTopCoopAA() {
        long startTime = System.nanoTime();
        List<Coopration> TopCoopAAs= neo4jMapper.getTopCoopAA();
        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
        List<CoopDetail> result=new ArrayList<>();
        List<Coopration> TopCoopAAs1=new ArrayList<>();
        for(int i=0;i< TopCoopAAs.size();i++){
            String totalName=TopCoopAAs.get(i).getName();
            String name1=totalName.substring(0,totalName.indexOf("+"));
            String name2=totalName.substring(name1.length()+1,totalName.length());
            CoopDetail detail=new CoopDetail(name1,name2,TopCoopAAs.get(i).getCount());
            result.add(detail);
        }
        Helper helper=new Helper(result,millsecs);
        return helper;
    }

    @ApiOperation("无起点搜索演员和导演合作关系前20")
    @RequestMapping(value = "/getTopCoopAD",method = RequestMethod.GET)
    public Helper getTopCoopAD() {
        long startTime = System.nanoTime();
        List<Coopration> TopCoopADs= neo4jMapper.getTopCoopAD();
        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
        List<CoopDetail> result=new ArrayList<>();
        for(Coopration c:TopCoopADs){
            String totalName=c.getName();
            String name1=totalName.substring(0,totalName.indexOf("+"));
            String name2=totalName.substring(name1.length()+1,totalName.length());
            CoopDetail detail=new CoopDetail(name1,name2,c.getCount());
            result.add(detail);
        }
        Helper helper=new Helper(result,millsecs);
        return helper;
    }

    @ApiOperation("无起点搜索演员和编剧合作关系前20")
    @RequestMapping(value = "/getTopCoopAW",method = RequestMethod.GET)
    public Helper getTopCoopAW() {
        long startTime = System.nanoTime();
        List<Coopration> TopCoopAWs= neo4jMapper.getTopCoopAW();
        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
        List<CoopDetail> result=new ArrayList<>();
        for(Coopration c:TopCoopAWs){
            String totalName=c.getName();
            String name1=totalName.substring(0,totalName.indexOf("+"));
            String name2=totalName.substring(name1.length()+1,totalName.length());
            CoopDetail detail=new CoopDetail(name1,name2,c.getCount());
            result.add(detail);
        }
        Helper helper=new Helper(result,millsecs);
        return helper;
    }

    @ApiOperation("无起点搜索导演和编剧合作关系前20")
    @RequestMapping(value = "/getTopCoopDW",method = RequestMethod.GET)
    public Helper getTopCoopDW() {
        long startTime = System.nanoTime();
        List<Coopration> TopCoopAWs= neo4jMapper.getTopCoopDW();
        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
        List<CoopDetail> result=new ArrayList<>();
        for(Coopration c:TopCoopAWs){
            String totalName=c.getName();
            String name1=totalName.substring(0,totalName.indexOf("+"));
            String name2=totalName.substring(name1.length()+1,totalName.length());
            CoopDetail detail=new CoopDetail(name1,name2,c.getCount());
            result.add(detail);
        }
        Helper helper=new Helper(result,millsecs);
        return helper;
    }

    @ApiOperation("有起点搜索演员合作关系前20")
    @ApiImplicitParams({
            @ApiImplicitParam(name="name",value = "起点演员姓名")
    })

    @RequestMapping(value = "/getTopCoopAAByName",method = RequestMethod.GET)
    public Helper getTopCoopAAByName(String name) {
        name="'"+name+"'";
        long startTime = System.nanoTime();
        List<Coopration> TopCoopAAs= neo4jMapper.getCoopAA(name);
        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
        List<CoopDetail> result=new ArrayList<>();
        for(Coopration c:TopCoopAAs){
            String totalName=c.getName();
            String name1=totalName.substring(0,totalName.indexOf("+"));
            String name2=totalName.substring(name1.length()+1,totalName.length());
            CoopDetail detail=new CoopDetail(name1,name2,c.getCount());
            result.add(detail);
        }
        Helper helper=new Helper(result,millsecs);
        return helper;
    }

    @ApiOperation("有起点搜索演员和导演合作关系前20")
    @ApiImplicitParams({
            @ApiImplicitParam(name="name",value = "起点演员姓名")
    })
    @RequestMapping(value = "/getTopCoopADByName",method = RequestMethod.GET)
    public Helper getTopCoopADByName(String name) {
        name="'"+name+"'";
        long startTime = System.nanoTime();
        List<Coopration> TopCoopADs= neo4jMapper.getCoopAD(name);
        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
        List<CoopDetail> result=new ArrayList<>();
        for(Coopration c:TopCoopADs){
            String totalName=c.getName();
            String name1=totalName.substring(0,totalName.indexOf("+"));
            String name2=totalName.substring(name1.length()+1,totalName.length());
            CoopDetail detail=new CoopDetail(name1,name2,c.getCount());
            result.add(detail);
        }
        Helper helper=new Helper(result,millsecs);
        return helper;
    }

    @ApiOperation("有起点搜索演员和编剧合作关系前20")
    @ApiImplicitParams({
            @ApiImplicitParam(name="name",value = "起点演员姓名")
    })
    @RequestMapping(value = "/getTopCoopAWByName",method = RequestMethod.GET)
    public Helper getTopCoopAWByName(String name) {
        name="'"+name+"'";
        long startTime = System.nanoTime();
        List<Coopration> TopCoopAWs= neo4jMapper.getCoopAW(name);
        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
        List<CoopDetail> result=new ArrayList<>();
        for(Coopration c:TopCoopAWs){
            String totalName=c.getName();
            String name1=totalName.substring(0,totalName.indexOf("+"));
            String name2=totalName.substring(name1.length()+1,totalName.length());
            CoopDetail detail=new CoopDetail(name1,name2,c.getCount());
            result.add(detail);
        }
        Helper helper=new Helper(result,millsecs);
        return helper;
    }

    @ApiOperation("有起点搜索导演和编剧合作关系前20")
    @ApiImplicitParams({
            @ApiImplicitParam(name="name",value = "起点导演姓名")
    })
    @RequestMapping(value = "/getTopCoopDWByName",method = RequestMethod.GET)
    public Helper getTopCoopDWByName(String name) {
        name="'"+name+"'";
        long startTime = System.nanoTime();
        List<Coopration> TopCoopAWs= neo4jMapper.getCoopDW(name);
        double millsecs = (System.nanoTime() - startTime) / 1000000.0;
        List<CoopDetail> result=new ArrayList<>();
        for(Coopration c:TopCoopAWs){
            String totalName=c.getName();
            String name1=totalName.substring(0,totalName.indexOf("+"));
            String name2=totalName.substring(name1.length()+1,totalName.length());
            CoopDetail detail=new CoopDetail(name1,name2,c.getCount());
            result.add(detail);
        }
        Helper helper=new Helper(result,millsecs);
        return helper;
    }
}
