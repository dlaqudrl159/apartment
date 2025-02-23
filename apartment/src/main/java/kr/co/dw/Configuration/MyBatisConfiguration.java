package kr.co.dw.Configuration;

import javax.sql.DataSource;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
public class MyBatisConfiguration {

	 	@Bean(name = "dataSource")
	    @ConfigurationProperties(prefix = "spring.datasource") //application.properties파일을 읽어 "spring.datasource"이 붙은 설정을 자동 맵핑
	    public DataSource dataSource() {
	        return DataSourceBuilder.create().build();
	    }
	   
	 	@Bean(name = "sqlSessionFactory")
	    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
	        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
	        factoryBean.setDataSource(dataSource);
	        factoryBean.setMapperLocations(
	                new PathMatchingResourcePatternResolver().getResources("classpath:mapper/**/*.xml")
	            );
	            factoryBean.setTypeAliasesPackage("kr.co.dw.Domain,kr.co.dw.Dto");
	        return factoryBean.getObject();
	    }
	 	
	    @Bean(name = "sqlSessionTemplate")
	    @Primary
	    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception {
	        return new SqlSessionTemplate(sqlSessionFactory);
	    }

	    @Bean(name = "batchSqlSessionTemplate")
	    public SqlSessionTemplate batchSqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception {
	        return new SqlSessionTemplate(sqlSessionFactory, ExecutorType.BATCH);
	    }
	
}
