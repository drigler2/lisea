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

import javax.naming.ConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hr.drigler.lisea.juno.config.ICuratorFrameworkConfig;

@Configuration
public class UserPassportMapperConfig {

    private final ICuratorFrameworkConfig zoo;
    @Value("${zookeeper.url_prefix.juno.field_names}")
    private String prefix;

    @Autowired
    public UserPassportMapperConfig(ICuratorFrameworkConfig zoo) {

        this.zoo = zoo;
    }

    @Bean
    public IUserPassportMapper getMapper() throws ConfigurationException, Exception {

        String username = zoo.getRequiredZooData(prefix + "username");
        String password = zoo.getRequiredZooData(prefix + "password");
        String unique_id = zoo.getRequiredZooData(prefix + "unique_id");
        String enabled = zoo.getRequiredZooData(prefix + "enabled");
        String authority_name = zoo.getRequiredZooData(prefix + "authority_name");

        return new UserPassportMapper(username, password, unique_id, enabled, authority_name);
    }

}
