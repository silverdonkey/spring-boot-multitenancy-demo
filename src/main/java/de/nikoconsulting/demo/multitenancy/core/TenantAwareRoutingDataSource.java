package de.nikoconsulting.demo.multitenancy.core;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class TenantAwareRoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return ThreadLocalStorage.getTenantName() != null ? ThreadLocalStorage.getTenantName() : TenantIdNames.MYDB1;
    }
}
