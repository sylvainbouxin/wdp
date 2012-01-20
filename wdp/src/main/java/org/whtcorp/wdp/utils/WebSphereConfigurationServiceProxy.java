package org.whtcorp.wdp.utils;

import com.ibm.websphere.management.AdminClient;
import com.ibm.websphere.management.Session;
import com.ibm.websphere.management.configservice.ConfigServiceProxy;
import org.whtcorp.wdp.*;

import javax.management.*;

public class WebSphereConfigurationServiceProxy {

    private AdminClient adminClient;
    private ConfigServiceProxy configurationServiceProxy;
    private Session session;
    private ObjectName applicationServerScope;
    private ObjectName jdbcConfigurationData;

    public WebSphereConfigurationServiceProxy(AdminClient adminClient) throws Exception {

        this.adminClient = adminClient;
        this.configurationServiceProxy = new ConfigServiceProxy(adminClient);
    }

    public WebSphereConfigurationServiceProxy(AdminClient adminClient, WebSphereTopologyModel websphere_topology) throws Exception {

        this.adminClient = adminClient;
        this.configurationServiceProxy = new ConfigServiceProxy(adminClient);

        resolveApplicationServerScope(websphere_topology);
    }
    
    public void createJDBCProvider(WebSphereJDBCProviderModel jdbcProvider) throws Exception {

        AttributeList jdbcProviderAttributeList = new AttributeList();

        jdbcProviderAttributeList.add(new Attribute("name", jdbcProvider.getName()));
        jdbcProviderAttributeList.add(new Attribute("implementationClassName", jdbcProvider.getImplementationClassName()));
        jdbcProviderAttributeList.add((new Attribute("description", jdbcProvider.getName())));

        this.jdbcConfigurationData = configurationServiceProxy.createConfigData(session, applicationServerScope, "JDBCProvider", "JDBCProvider", jdbcProviderAttributeList);

        configurationServiceProxy.addElement(session, jdbcConfigurationData, "classpath", jdbcProvider.getClasspath(), -1);
        configurationServiceProxy.save(session, false);
    }

    public void deleteJDBCProvider(WebSphereJDBCProviderModel jdbcProvider) throws Exception {

        ObjectName[] jdbcProviderObjects = configurationServiceProxy.resolve(session, "JDBCProvider=" + jdbcProvider.getName());
        
        for (int i = 0; i < jdbcProviderObjects.length; i++) {

            configurationServiceProxy.deleteConfigData(session, jdbcProviderObjects[i]);
        }

        configurationServiceProxy.save(session, false);
    }

    public void createDataSource(WebSphereDataSourceModel dataSource) throws Exception {

        AttributeList dataSourceAttributeList = new AttributeList();

        dataSourceAttributeList.add(new Attribute("name", dataSource.getName()));
        dataSourceAttributeList.add(new Attribute("jndiName", dataSource.getJndiName()));
        dataSourceAttributeList.add(new Attribute("datasourceHelperClassname", dataSource.getDatasourceHelperClassname()));
        dataSourceAttributeList.add(new Attribute("statementCacheSize", dataSource.getStatementCacheSize()));
        dataSourceAttributeList.add(new Attribute("description", dataSource.getDescription()));
        ObjectName newDataSource = configurationServiceProxy.createConfigData(session, jdbcConfigurationData, "DataSource", "DataSource", dataSourceAttributeList);

        AttributeList j2eeResourcePropertyAttributeList = new AttributeList();
        ObjectName propertySet = configurationServiceProxy.createConfigData(session, newDataSource, "propertySet", "", j2eeResourcePropertyAttributeList);

        AttributeList j2eeResourceProperty = new AttributeList();
        
        for (int i = 0; i < dataSource.getPropertySet().size(); i++) {

            WebSphereJ2EEResourcePropertyModel property = dataSource.getPropertySet().get(i);

            j2eeResourceProperty.clear();

            j2eeResourceProperty.add(new Attribute("name", property.getName()));
            if (property.getType() != null)
                j2eeResourceProperty.add(new Attribute("type", property.getType()));
            else
                j2eeResourceProperty.add(new Attribute("type", "java.lang.String"));
            j2eeResourceProperty.add(new Attribute("value", property.getValue()));
            
            configurationServiceProxy.addElement(session, propertySet, "resourceProperties",j2eeResourceProperty, -1);
        }

        configurationServiceProxy.save(session, false);
    }

    public void createSharedLibrary(WebSphereSharedLibraryModel sharedLibrary) throws Exception {

        AttributeList sharedLibraryAttributeList = new AttributeList();

        sharedLibraryAttributeList.add(new Attribute("name", sharedLibrary.getName()));
        sharedLibraryAttributeList.add(new Attribute("description", sharedLibrary.getDescription()));
        sharedLibraryAttributeList.add((new Attribute("isolatedClassLoader", sharedLibrary.getIsolatedClassLoader())));

        ObjectName sharedLib = configurationServiceProxy.createConfigData(session, applicationServerScope, "Library", "Library", sharedLibraryAttributeList);

        configurationServiceProxy.addElement(session, sharedLib, "classPath", sharedLibrary.getClassPath(), -1);

        configurationServiceProxy.save(session, false);
    }

    public void createLibraryReferenceForApplication(String applicationName, String sharedLibraryName) throws Exception {

        ObjectName[] deployedApplicationObjects = configurationServiceProxy.resolve(session, "Deployment=" + applicationName);
        
        for (int i = 0; i < deployedApplicationObjects.length; i++) {

        }

        AttributeList sharedLibraryAttributeList = new AttributeList();

        configurationServiceProxy.save(session, false);
    }

    public void deleteSharedLibrary(WebSphereSharedLibraryModel sharedLibrary) throws Exception {

        ObjectName[] sharedLibraryObjects = configurationServiceProxy.resolve(session, "Library=" + sharedLibrary.getName());

        for (int i = 0; i < sharedLibraryObjects.length; i++) {

            configurationServiceProxy.deleteConfigData(session, sharedLibraryObjects[i]);
        }

        configurationServiceProxy.save(session, false);
    }

    public void createStringNameSpaceBinding(WebSphereStringNameSpaceBindingModel namespaceBinding) throws Exception {

        AttributeList namespaceBindingAttributeList = new AttributeList();

        namespaceBindingAttributeList.add(new Attribute("name", namespaceBinding.getName()));
        namespaceBindingAttributeList.add(new Attribute("nameInNameSpace", namespaceBinding.getNameInNameSpace()));
        namespaceBindingAttributeList.add((new Attribute("stringToBind", namespaceBinding.getStringToBind())));

        ObjectName sharedLib = configurationServiceProxy.createConfigData(session, applicationServerScope, "StringNameSpaceBinding", "StringNameSpaceBinding", namespaceBindingAttributeList);

        configurationServiceProxy.save(session, false);
    }

    public void deleteStringNameSpaceBinding(WebSphereStringNameSpaceBindingModel nameSpaceBinding) throws Exception{

        ObjectName[] namespaceBindingObjects = configurationServiceProxy.resolve(session, "StringNameSpaceBinding=" + nameSpaceBinding.getName());

        for (int i = 0; i < namespaceBindingObjects.length; i++) {

            configurationServiceProxy.deleteConfigData(session, namespaceBindingObjects[i]);
        }

        configurationServiceProxy.save(session, false);
    }

    private void resolveApplicationServerScope(WebSphereTopologyModel websphere_topology) throws Exception {

        this.session = new Session();

        if(websphere_topology.getWebsphere_cell_name() != null) {
            this.applicationServerScope = configurationServiceProxy.resolve(session, "Cell=" + websphere_topology.getWebsphere_cell_name() + ":Node=" + websphere_topology.getWebsphere_node_name() + ":Server=" + websphere_topology.getWebsphere_server_name())[0];
        } else {
            this.applicationServerScope = configurationServiceProxy.resolve(session, "Node=" + websphere_topology.getWebsphere_node_name() + ":Server=" + websphere_topology.getWebsphere_server_name())[0];
        }
    }

    public void queryObjectType(String objectType) {
        
        AttributeList attributeList = new AttributeList();
        
        try {
            attributeList = configurationServiceProxy.getAttributesMetaInfo(objectType);
            System.out.println("A WebSphereObjectType of type <" + objectType + "> takes the following attributes:");

            for (int i = 0; i < attributeList.size(); i++) {

                AttributeList detailedAttributeList = (AttributeList)((Attribute)attributeList.get(i)).getValue();

                String attributeName = null;
                String attributeIsCollection = null;
                String attributeType = null;
                String attributeIsRequired = null;

                for (int j = 0; j < detailedAttributeList.size(); j++) {

                    if (((Attribute)detailedAttributeList.get(j)).getName().contains("_Attribute_MetaInfo_Name"))
                        attributeName = (String) (((Attribute)detailedAttributeList.get(j)).getValue());

                    if (((Attribute)detailedAttributeList.get(j)).getName().contains("_Attribute_MetaInfo_Is_Collection")) {
                        Boolean isCollection = (Boolean) ((Attribute)detailedAttributeList.get(j)).getValue();
                        if (isCollection)
                            attributeIsCollection = "[]";
                        else
                            attributeIsCollection = "";
                    }

                    if (((Attribute)detailedAttributeList.get(j)).getName().contains("_Attribute_MetaInfo_Type"))
                        attributeType = (String) (((Attribute)detailedAttributeList.get(j)).getValue());

                    if (((Attribute)detailedAttributeList.get(j)).getName().contains("_Attribute_MetaInfo_Is_Required")) {
                        Boolean isRequired = (Boolean) ((Attribute)detailedAttributeList.get(j)).getValue();
                        if (isRequired) {
                            attributeIsRequired = " which is required";
                        } else {
                            attributeIsRequired = " which is not required";
                    }

                        System.out.println("\ta <" + attributeName + ">" + " of type: <" + attributeType + attributeIsCollection + "> " + attributeIsRequired);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void obtainConfigurationService() {

    }

    public void createSession() {

    }

    public void identifyConfigurationObject() {

    }

    public void obtainConfigObjectIDs() {

    }

    public void queryAttributeValues() {

    }

    public void modifyAttributeValues() {

    }

    public void queryPortInformation() {

    }

    public void saveChanges() {

    }

    public void cleanUpSession() throws Exception {

        configurationServiceProxy.discard(session);
    }
}