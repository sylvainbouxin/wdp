package org.whtcorp.wdp;

/**
 * The <code>WebSphereSharedLibraryBindingModel</code> class.
 * @author i23704
 */
public class WebSphereSharedLibraryBindingModel {
    private String name;
    private boolean sharedClassLoader = true;
    private String module;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public boolean isSharedClassLoader() {
        return sharedClassLoader;
    }

    public void setSharedClassLoader(final boolean sharedClassLoader) {
        this.sharedClassLoader = sharedClassLoader;
    }

    public String getModule() {
        return module;
    }

    public void setModule(final String module) {
        this.module = module;
    }

}
