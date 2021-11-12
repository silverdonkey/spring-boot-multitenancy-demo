package de.nikoconsulting.demo.multitenancy;

/**
 * Please note that if you would like to share the ThreadLocal with the child thread,
 * in case the service thread may create other threads while proceeding, you should use the InheritableThreadLocal.
 *
 * InheritableThreadLocal will pass the same variable to child threads and they will be aware of the tenant datasource.
 */
public class ThreadLocalStorage {

    private static ThreadLocal<String> tenant = new ThreadLocal<>();

    public static void setTenantName(String tenantName) {
        tenant.set(tenantName);
    }

    public static String getTenantName() {
        return tenant.get();
    }
}
