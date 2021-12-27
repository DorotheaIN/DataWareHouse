package com.example.backend1.Config;


import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.neo4j.driver.internal.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
//@EnableNeo4jRepositories
@EnableTransactionManagement
@MapperScan(basePackages = {"com.example.backend1.mapper.hive"}, sqlSessionFactoryRef = "hiveSqlSessionFactory")
public class HiveDataSourceConfig {
    @Bean(name = "hiveDataSource")
    @ConfigurationProperties(prefix="spring.datasource.hive")
    public DataSource hiveDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "hiveSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("hiveDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath*:mapper/hive/*.xml"));
        return sessionFactoryBean.getObject();
    }

}

