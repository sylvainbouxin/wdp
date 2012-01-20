package org.whtcorp.wdp;

public class WebSphereSharedLibraryModel {
    
    private String name;
    private String description;
    private String classPath;
    private String nativePath;
    private Boolean isolatedClassLoader;

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

    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    public String getNativePath() {
        return nativePath;
    }

    public void setNativePath(String nativePath) {
        this.nativePath = nativePath;
    }

    public Boolean getIsolatedClassLoader() {
        return isolatedClassLoader;
    }

    public void setIsolatedClassLoader(Boolean isolatedClassLoader) {
        this.isolatedClassLoader = isolatedClassLoader;
    }
}
