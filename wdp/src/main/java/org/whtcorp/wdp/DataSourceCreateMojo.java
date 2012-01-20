package org.whtcorp.wdp;

import com.ibm.websphere.management.Session;
import com.ibm.websphere.management.configservice.ConfigService;
import com.ibm.websphere.management.configservice.ConfigServiceHelper;
import com.ibm.websphere.management.configservice.ConfigServiceProxy;
import org.whtcorp.wdp.utils.WebSphereConfigurationServiceProxy;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.ObjectName;
import java.text.AttributedCharacterIterator;
import java.util.List;

/**
 * @goal datasource.create
 */
public class DataSourceCreateMojo extends WebSphereAbstractMojo {

    /**
     * @parameter
     */
    private WebSphereTopologyModel websphere_topology;

    /**
     * @parameter
     */
    private List<WebSphereJDBCProviderModel> jdbc_provider_list;

    @Override
    public void execute() {
        
        try {
            createDataSource();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createDataSource() throws Exception {

        WebSphereConfigurationServiceProxy configurationServiceProxy = new WebSphereConfigurationServiceProxy(getAdminClient(), websphere_topology);
        getLog().info("------------------------------------------------------------------------");

        for (int i = 0; i < jdbc_provider_list.size(); i++) {

            WebSphereJDBCProviderModel jdbcProvider = (WebSphereJDBCProviderModel)jdbc_provider_list.get(i);

            getLog().info("Creating JDBCProvider: " + jdbcProvider.getName());

            configurationServiceProxy.createJDBCProvider(jdbcProvider);
            
            for (int j = 0; j < jdbcProvider.getData_source_list().size(); j++) {

                WebSphereDataSourceModel dataSource = (WebSphereDataSourceModel)jdbcProvider.getData_source_list().get(j);

                getLog().info("Creating DataSource: " + dataSource.getName());

                configurationServiceProxy.createDataSource(dataSource);
            }
        }

        configurationServiceProxy.cleanUpSession();

        getLog().info("------------------------------------------------------------------------");
    }
}
