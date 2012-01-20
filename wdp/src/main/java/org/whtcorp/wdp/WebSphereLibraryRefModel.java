package org.whtcorp.wdp;

public class WebSphereLibraryRefModel {
    
    private String libraryName;
    private Boolean sharedClassloader;

    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    public Boolean getSharedClassloader() {
        return sharedClassloader;
    }

    public void setSharedClassloader(Boolean sharedClassloader) {
        this.sharedClassloader = sharedClassloader;
    }
}
