package de.nikoconsulting.demo.multitenancy;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class TenantAwareRoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return ThreadLocalStorage.getTenantName() != null ? ThreadLocalStorage.getTenantName() : "mydb1";
    }
}
