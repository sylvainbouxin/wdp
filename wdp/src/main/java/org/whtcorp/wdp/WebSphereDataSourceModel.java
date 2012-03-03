package org.whtcorp.wdp;

import java.util.List;

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
    private List<WebSphereJ2EEResourcePropertyModel> propertySet;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getJndiName() {
        return jndiName;
    }

    public void setJndiName(final String jndiName) {
        this.jndiName = jndiName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(final String category) {
        this.category = category;
    }

    public String getProviderType() {
        return providerType;
    }

    public void setProviderType(final String providerType) {
        this.providerType = providerType;
    }

    public String getAuthMechanismPreference() {
        return authMechanismPreference;
    }

    public void setAuthMechanismPreference(final String authMechanismPreference) {
        this.authMechanismPreference = authMechanismPreference;
    }

    public String getAuthDataAlias() {
        return authDataAlias;
    }

    public void setAuthDataAlias(final String authDataAlias) {
        this.authDataAlias = authDataAlias;
    }

    public Boolean getManageCachedHandles() {
        return manageCachedHandles;
    }

    public void setManageCachedHandles(final Boolean manageCachedHandles) {
        this.manageCachedHandles = manageCachedHandles;
    }

    public Boolean getLogMissingTransactionContext() {
        return logMissingTransactionContext;
    }

    public void setLogMissingTransactionContext(final Boolean logMissingTransactionContext) {
        this.logMissingTransactionContext = logMissingTransactionContext;
    }

    public String getXaRecoveryAuthAlias() {
        return xaRecoveryAuthAlias;
    }

    public void setXaRecoveryAuthAlias(final String xaRecoveryAuthAlias) {
        this.xaRecoveryAuthAlias = xaRecoveryAuthAlias;
    }

    public Boolean getDiagnoseConnectionUsage() {
        return diagnoseConnectionUsage;
    }

    public void setDiagnoseConnectionUsage(final Boolean diagnoseConnectionUsage) {
        this.diagnoseConnectionUsage = diagnoseConnectionUsage;
    }

    public Integer getStatementCacheSize() {
        return statementCacheSize;
    }

    public void setStatementCacheSize(final Integer statementCacheSize) {
        this.statementCacheSize = statementCacheSize;
    }

    public String getDatasourceHelperClassname() {
        return datasourceHelperClassname;
    }

    public void setDatasourceHelperClassname(final String datasourceHelperClassname) {
        this.datasourceHelperClassname = datasourceHelperClassname;
    }

    public List<WebSphereJ2EEResourcePropertyModel> getPropertySet() {
        return propertySet;
    }

    public void setPropertySet(final List<WebSphereJ2EEResourcePropertyModel> propertySet) {
        this.propertySet = propertySet;
    }
}
