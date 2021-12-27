package com.example.backend1.Config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.annotation.Resource;
import javax.sql.DataSource;


@Configuration
@MapperScan(basePackages = {"com.example.backend1.mapper.mysql2"}, sqlSessionFactoryRef = "mysqlSqlSessionFactory2")
public class MysqlSecondDataSourceConfig {
    @Primary
    @Bean(name = "MysqlDataSource2")
    @ConfigurationProperties(prefix="spring.datasource.mysql2")
    public DataSource MysqlDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Resource
    @Bean(name = "mysqlSqlSessionFactory2")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("MysqlDataSource2") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath*:mapper/mysql2/*.xml"));
        return sessionFactoryBean.getObject();
    }
}
