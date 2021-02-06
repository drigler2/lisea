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

import javax.naming.ConfigurationException;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DataSourceConfig {

    private final ICuratorFrameworkConfig zoo;
    private String url;
    private String username;
    private String password;
    private Integer poolSize;
    private Long timeout;
    @Value("${zookeeper.url_prefix.juno.config}")
    private String prefix;

    @Autowired
    public DataSourceConfig(ICuratorFrameworkConfig zoo) {

        this.zoo = zoo;
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

        url = zoo.getRequiredZooData(prefix + "db_url");
        username = zoo.getRequiredZooData(prefix + "db_username");
        password = zoo.getRequiredZooData(prefix + "db_password");
        poolSize = Integer.parseInt(zoo.getRequiredZooData(prefix + "db_poolSize"));
        timeout = Long.parseLong(zoo.getRequiredZooData(prefix + "db_connectionTimeout"));
    }

}