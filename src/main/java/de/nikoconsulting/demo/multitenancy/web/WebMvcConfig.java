package de.nikoconsulting.demo.multitenancy.web;

import de.nikoconsulting.demo.multitenancy.core.TenantAwareRoutingDataSource;
import de.nikoconsulting.demo.multitenancy.core.TenantIdNames;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TenantNameInterceptor());
    }

    private final DataSourceProperties dataSourceProperties;

    public WebMvcConfig(DataSourceProperties dataSourceProperties) {
        this.dataSourceProperties = dataSourceProperties;
    }

    @Bean
    public DataSource getDataSource() {
        TenantAwareRoutingDataSource tenantAwareRoutingDataSource = new TenantAwareRoutingDataSource();
        tenantAwareRoutingDataSource.setTargetDataSources(dataSourceProperties.getDatasources());
        tenantAwareRoutingDataSource.setDefaultTargetDataSource(dataSourceProperties.getDatasources().get(TenantIdNames.MYDB1));
        tenantAwareRoutingDataSource.afterPropertiesSet();
        return tenantAwareRoutingDataSource;
    }
}
