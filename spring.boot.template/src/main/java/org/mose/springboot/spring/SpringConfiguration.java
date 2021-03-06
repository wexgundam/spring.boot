package org.mose.springboot.spring;

import org.mose.springboot.dao.IPaging;
import org.mose.springboot.dao.MysqlPaging;
import org.mose.springboot.dao.OraclePaging;
import org.mose.springboot.util.spring.SpringContextHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;

/**
 * Description:配置Spring Application Context
 *
 * Spring boot默认创建的bean：
 * 1. JdbcTemplate
 * 2. NamedParameterJdbcTemplate
 *
 * Spring boot默认启动的annotation
 * 1. @EnableConfigurationProperties
 *
 * @Author: 靳磊
 * @Date: 2017/8/2:22
 */
@Configuration
public class SpringConfiguration {
    /**
     * 配置springContextConfiguration
     *
     * @return
     */
    @Bean
    public SpringContextHolder springContextConfiguration() {
        return new SpringContextHolder();
    }

    @Bean
    @Scope("prototype")
    @Profile("mysql")
    public IPaging mysqlPaging() {
        return new MysqlPaging();
    }

    @Bean
    @Scope("prototype")
    @Profile("oracle")
    public IPaging oraclePaging() {
        return new OraclePaging();
    }
}
