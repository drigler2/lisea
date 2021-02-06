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

import java.nio.charset.StandardCharsets;

import javax.naming.ConfigurationException;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CuratorFrameworkConfig implements ICuratorFrameworkConfig {

    @Value("${zookeeper.url}")
    private String endpoint;

    @Bean
    public CuratorFramework getClient() {

        int sleepMsBetweenRetries = 100;
        int maxRetries = 3;

        RetryPolicy retryPolicy = new RetryNTimes(maxRetries, sleepMsBetweenRetries);
        // TODO move to config
        CuratorFramework client = CuratorFrameworkFactory.newClient(endpoint, retryPolicy);

        client.start();

        return client;
    }

    @Override
    public String getRequiredZooData(String path) throws ConfigurationException, Exception {

        CuratorFramework client = getClient();
        if (client == null) {
            throw new ConfigurationException("Unable to find Zookeeper client!");
        }
        byte[] b = client.getData().forPath(path);
        if (b == null || b.length < 1) {
            throw new ConfigurationException("Fetched empty field from zookeeper!");
        }

        return new String(b, StandardCharsets.UTF_8);
    }
}
