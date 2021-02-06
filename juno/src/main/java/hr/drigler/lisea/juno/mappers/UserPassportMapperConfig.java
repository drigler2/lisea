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
package hr.drigler.lisea.juno.mappers;

import java.util.Arrays;
import java.util.List;

import javax.naming.ConfigurationException;

import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hr.drigler.lisea.juno.utils.ZooUtils;

@Configuration
public class UserPassportMapperConfig {

    private final CuratorFramework client;
    @Value("${zookeeper.url_prefix.juno.field_names}")
    private String junoPrefix;

    @Autowired
    public UserPassportMapperConfig(CuratorFramework client) {

        this.client = client;
    }

    @Bean
    public IUserPassportMapper getMapper() throws ConfigurationException, Exception {

        if (client == null) {
            throw new ConfigurationException("Unable to find Zookeeper client!");
        }

        String username = ZooUtils.bToS(client.getData().forPath(junoPrefix + "username"));
        String password = ZooUtils.bToS(client.getData().forPath(junoPrefix + "password"));
        String unique_id = ZooUtils.bToS(client.getData().forPath(junoPrefix + "unique_id"));
        String enabled = ZooUtils.bToS(client.getData().forPath(junoPrefix + "enabled"));
        String authority_name =
            ZooUtils.bToS(client.getData().forPath(junoPrefix + "authority_name"));

        List<Object> validationList =
            Arrays.asList(username, password, unique_id, enabled, authority_name);

        ZooUtils.validateList(validationList);

        client.close();
        return new UserPassportMapper(username, password, unique_id, enabled, authority_name);
    }

}
