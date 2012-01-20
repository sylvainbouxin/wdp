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
 * @goal datasource.delete
 */
public class DataSourceDeleteMojo extends WebSphereAbstractMojo {

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
            deleteJDBCProvider();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteJDBCProvider() throws Exception {

        WebSphereConfigurationServiceProxy configurationServiceProxy = new WebSphereConfigurationServiceProxy(getAdminClient(), websphere_topology);
        getLog().info("------------------------------------------------------------------------");

        for (int i = 0; i < jdbc_provider_list.size(); i++) {

            WebSphereJDBCProviderModel jdbcProvider = (WebSphereJDBCProviderModel)jdbc_provider_list.get(i);

            getLog().info("Deleting JDBCProvider: " + jdbcProvider.getName());

            configurationServiceProxy.deleteJDBCProvider(jdbcProvider);
        }

        configurationServiceProxy.cleanUpSession();

        getLog().info("------------------------------------------------------------------------");
    }
}
