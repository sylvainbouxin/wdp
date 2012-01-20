package org.whtcorp.wdp;

import java.util.List;
import java.util.Set;

public class WebSphereDataSourceModel {

    private String name;
    private String jndiName;
    private String description;
    private String category;
    private String providerType;
    private String authMechanismPreference;
    private String authDataAlias;
    private Boolean manageCachedHandles;
    private Boolean logMissingTransactionContext;
    private String xaRecoveryAuthAlias;
    private Boolean diagnoseConnectionUsage;
    private Integer statementCacheSize;
    private String datasourceHelperClassname;
    // a <provider> of type: <J2EEResourceProvider>  which is not required
    private List<WebSphereJ2EEResourcePropertyModel> propertySet;
    // a <connectionPool> of type: <ConnectionPool>  which is not required
    // a <preTestConfig> of type: <ConnectionTest>  which is not required
    // a <mapping> of type: <MappingModule>  which is not required
    // a <properties> of type: <Property[]>  which is not required
    // a <relationalResourceAdapter> of type: <J2CResourceAdapter>  which is not required


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJndiName() {
        return jndiName;
    }

    public void setJndiName(String jndiName) {
        this.jndiName = jndiName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProviderType() {
        return providerType;
    }

    public void setProviderType(String providerType) {
        this.providerType = providerType;
    }

    public String getAuthMechanismPreference() {
        return authMechanismPreference;
    }

    public void setAuthMechanismPreference(String authMechanismPreference) {
        this.authMechanismPreference = authMechanismPreference;
    }

    public String getAuthDataAlias() {
        return authDataAlias;
    }

    public void setAuthDataAlias(String authDataAlias) {
        this.authDataAlias = authDataAlias;
    }

    public Boolean getManageCachedHandles() {
        return manageCachedHandles;
    }

    public void setManageCachedHandles(Boolean manageCachedHandles) {
        this.manageCachedHandles = manageCachedHandles;
    }

    public Boolean getLogMissingTransactionContext() {
        return logMissingTransactionContext;
    }

    public void setLogMissingTransactionContext(Boolean logMissingTransactionContext) {
        this.logMissingTransactionContext = logMissingTransactionContext;
    }

    public String getXaRecoveryAuthAlias() {
        return xaRecoveryAuthAlias;
    }

    public void setXaRecoveryAuthAlias(String xaRecoveryAuthAlias) {
        this.xaRecoveryAuthAlias = xaRecoveryAuthAlias;
    }

    public Boolean getDiagnoseConnectionUsage() {
        return diagnoseConnectionUsage;
    }

    public void setDiagnoseConnectionUsage(Boolean diagnoseConnectionUsage) {
        this.diagnoseConnectionUsage = diagnoseConnectionUsage;
    }

    public Integer getStatementCacheSize() {
        return statementCacheSize;
    }

    public void setStatementCacheSize(Integer statementCacheSize) {
        this.statementCacheSize = statementCacheSize;
    }

    public String getDatasourceHelperClassname() {
        return datasourceHelperClassname;
    }

    public void setDatasourceHelperClassname(String datasourceHelperClassname) {
        this.datasourceHelperClassname = datasourceHelperClassname;
    }

    public List<WebSphereJ2EEResourcePropertyModel> getPropertySet() {
        return propertySet;
    }

    public void setPropertySet(List<WebSphereJ2EEResourcePropertyModel> propertySet) {
        this.propertySet = propertySet;
    }
}
