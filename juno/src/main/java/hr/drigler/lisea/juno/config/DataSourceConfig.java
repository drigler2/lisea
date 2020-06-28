/***************************************************************************
Copyright (C) 2020 Damir Rigler

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; version 2 of the License.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
***************************************************************************/
package hr.drigler.lisea.juno.config;

import java.util.Arrays;
import java.util.List;

import javax.naming.ConfigurationException;
import javax.sql.DataSource;

import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import hr.drigler.lisea.juno.utils.ZooUtils;

@Configuration
public class DataSourceConfig {

    private static final String JUNO_PREFIX = "/lisea/juno/config/";
    private CuratorFramework client;

    private String url;
    private String username;
    private String password;
    private Integer poolSize;
    private Long timeout;

    @Autowired
    public DataSourceConfig(CuratorFramework client) {

        this.client = client;
    }

    @Bean
    public DataSource getDataSource() throws Exception {

        getValidateConfig();

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setMaximumPoolSize(poolSize);
        config.setConnectionTimeout(timeout);

        HikariDataSource dataSource = new HikariDataSource(config);
        return dataSource;
    }

    private void getValidateConfig() throws ConfigurationException, Exception {

        if (client == null) {
            throw new ConfigurationException("Unable to find Zookeeper client!");
        }

        url = ZooUtils.bToS(client.getData().forPath(JUNO_PREFIX + "db_url"));
        username = ZooUtils.bToS(client.getData().forPath(JUNO_PREFIX + "db_username"));
        password = ZooUtils.bToS(client.getData().forPath(JUNO_PREFIX + "db_password"));
        poolSize =
            Integer.parseInt(ZooUtils.bToS(client.getData().forPath(JUNO_PREFIX + "db_poolSize")));
        timeout = Long.parseLong(
            ZooUtils.bToS(client.getData().forPath(JUNO_PREFIX + "db_connectionTimeout")));

        List<Object> validationList = Arrays.asList(url, username, password, poolSize, timeout);

        ZooUtils.validateList(validationList);
    }

}