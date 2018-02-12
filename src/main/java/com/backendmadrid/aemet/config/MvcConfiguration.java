package com.backendmadrid.aemet.config;

import com.backendmadrid.aemet.dao.EstacionDAO;
import com.backendmadrid.aemet.dao.EstacionDAOImpl;
import com.backendmadrid.aemet.dao.ObservacionDAO;
import com.backendmadrid.aemet.dao.ObservacionDAOImpl;
import com.backendmadrid.aemet.dao.ProvinciaDAO;
import com.backendmadrid.aemet.dao.ProvinciaDAOImpl;
import com.backendmadrid.aemet.dao.ResultadoDAO;
import com.backendmadrid.aemet.dao.ResultadoDAOImpl;
import com.backendmadrid.aemet.opendata.LectorAemet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@ComponentScan(basePackages="com.backendmadrid.aemet")
@EnableWebMvc
public class MvcConfiguration extends WebMvcConfigurerAdapter{

	@Bean
	public ViewResolver getViewResolver(){
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

	@Bean
        public DriverManagerDataSource dataSource(){
            DriverManagerDataSource ds=new DriverManagerDataSource();
            ds.setDriverClassName("com.mysql.jdbc.Driver");
            ds.setUrl("jdbc:mysql://cloud.josebelda.com:3306/aemet");
            ds.setUsername("aemet");
            ds.setPassword("FTHEf345Fffw");
            return ds;
        }
        
        @Bean
        public EstacionDAO estacionDAO(){
            return new EstacionDAOImpl();
        }
        
        @Bean
        public ObservacionDAO observacionDAO(){
            return new ObservacionDAOImpl();
        }
        
        @Bean
        public LectorAemet lectorAemet(){
            return new LectorAemet();
        }
        
        @Bean
        ProvinciaDAO provinciaDAO(){
            return new ProvinciaDAOImpl();
        }
        
        @Bean
        ResultadoDAO resultadoDAO(){
            return new ResultadoDAOImpl();
        }
        
}
