package de.nikoconsulting.demo.multitenancy;

import de.nikoconsulting.demo.multitenancy.core.TenantIdNames;
import org.junit.Test;

import java.io.IOException;

public class SchemaApproachMultitenancyIntegrationTests extends MultitenancyIntegrationTest {

    @Override
    public String getPropertyFile() {
        return "/hibernate-schema-multitenancy.properties";
    }

    @Test
    public void givenSchemaApproach_whenAddingEntries_thenOnlyAddedToConcreteSchema() throws IOException {
        whenCurrentTenantIs(TenantIdNames.MYDB1);
        whenAddEntity("Ferrari");
        thenEntityFound(1l);
        whenCurrentTenantIs(TenantIdNames.MYDB2);
        thenEntityNotFound(1l);
    }

}