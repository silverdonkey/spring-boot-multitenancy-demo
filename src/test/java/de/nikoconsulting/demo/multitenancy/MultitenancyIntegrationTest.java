package de.nikoconsulting.demo.multitenancy;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.service.ServiceRegistry;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public abstract class MultitenancyIntegrationTest {

    public abstract String getPropertyFile();

    @Mock
    private CurrentTenantIdentifierResolver currentTenantIdentifierResolver;

    private SessionFactory sessionFactory;

    @Before
    public void setup() throws IOException {
        MockitoAnnotations.initMocks(this);

        Mockito.when(currentTenantIdentifierResolver.validateExistingCurrentSessions())
                .thenReturn(false);

        Properties properties = getHibernateProperties();
        properties.put(AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER, currentTenantIdentifierResolver);

        sessionFactory = buildSessionFactory(properties);

        initTenant(TenantIdNames.MYDB1);
        initTenant(TenantIdNames.MYDB2);
    }

    protected void initTenant(String tenantId) {
        whenCurrentTenantIs(tenantId);
        //createEntityTable();
    }

    protected void whenCurrentTenantIs(String tenantId) {
        Mockito.when(currentTenantIdentifierResolver.resolveCurrentTenantIdentifier())
                .thenReturn(tenantId);
    }

    protected void whenAddEntity(String name) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        GenericEntity entity = new GenericEntity();
        entity.setName(name);
        session.save(entity);
        tx.commit();
    }

    protected void thenEntityFound(Long id) {
        Session session = sessionFactory.openSession();
        assertNotNull(session.get(GenericEntity.class, id));
    }

    protected void thenEntityNotFound(Long id) {
        Session session = sessionFactory.openSession();
        assertNull(session.get(GenericEntity.class, id));
    }

    private Properties getHibernateProperties() throws IOException {
        Properties properties = new Properties();
        properties.load(getClass().getResourceAsStream(getPropertyFile()));
        return properties;
    }

    private static SessionFactory buildSessionFactory(Properties properties) {
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(properties)
                .build();
        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        metadataSources.addAnnotatedClass(GenericEntity.class);
        return metadataSources.buildMetadata()
                .buildSessionFactory();
    }
}