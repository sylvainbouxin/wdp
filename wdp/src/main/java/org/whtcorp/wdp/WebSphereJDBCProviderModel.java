package org.whtcorp.wdp;

import java.util.List;
import java.util.Set;

public class WebSphereJDBCProviderModel {

    private String name;
    private String description;
    private String classpath;
    private String nativepath;
    private String providerType;
    private Boolean isolatedClassLoader;
    private String implementationClassName;
    private String xa;
    private List<WebSphereJ2EEResourcePropertyModel> propertySet;
    private List<WebSphereDataSourceModel> data_source_list;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClasspath() {
        return classpath;
    }

    public void setClasspath(String classpath) {
        this.classpath = classpath;
    }

    public String getNativepath() {
        return nativepath;
    }

    public void setNativepath(String nativepath) {
        this.nativepath = nativepath;
    }

    public String getProviderType() {
        return providerType;
    }

    public void setProviderType(String providerType) {
        this.providerType = providerType;
    }

    public Boolean getIsolatedClassLoader() {
        return isolatedClassLoader;
    }

    public void setIsolatedClassLoader(Boolean isolatedClassLoader) {
        this.isolatedClassLoader = isolatedClassLoader;
    }

    public String getImplementationClassName() {
        return implementationClassName;
    }

    public void setImplementationClassName(String implementationClassName) {
        this.implementationClassName = implementationClassName;
    }

    public String getXa() {
        return xa;
    }

    public void setXa(String xa) {
        this.xa = xa;
    }

    public List<WebSphereJ2EEResourcePropertyModel> getPropertySet() {
        return propertySet;
    }

    public void setPropertySet(List<WebSphereJ2EEResourcePropertyModel> propertySet) {
        this.propertySet = propertySet;
    }

    public List<WebSphereDataSourceModel> getData_source_list() {
        return data_source_list;
    }

    public void setData_source_list(List<WebSphereDataSourceModel> data_source_list) {
        this.data_source_list = data_source_list;
    }
}
