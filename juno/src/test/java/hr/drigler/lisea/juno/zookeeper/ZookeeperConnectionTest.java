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
package hr.drigler.lisea.juno.zookeeper;

import java.nio.charset.StandardCharsets;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
@TestPropertySource(locations = "classpath:application.yml")
public class ZookeeperConnectionTest {

    private static final Logger LOG = LoggerFactory.getLogger(ZookeeperConnectionTest.class);

    @Value("${zookeeper.url}")
    private String url;

    @Test
    public void testZookeeperInitial() {

        int sleepMsBetweenRetries = 100;
        int maxRetries = 3;

        RetryPolicy retryPolicy = new RetryNTimes(maxRetries, sleepMsBetweenRetries);
        CuratorFramework client = CuratorFrameworkFactory.newClient(url, retryPolicy);

        client.start();

        try {
            LOG.info(bToS(client.getData().forPath("/lisea/juno/config/db_url")));
            LOG.info(bToS(client.getData().forPath("/lisea/juno/config/db_username")));
            LOG.info(bToS(client.getData().forPath("/lisea/juno/config/db_password")));
            LOG.info(bToS(client.getData().forPath("/lisea/juno/config/db_poolSize")));
            LOG.info(bToS(client.getData().forPath("/lisea/juno/config/db_connectionTimeout")));
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Test
    public void testZookeeperAuthorities() {

        int sleepMsBetweenRetries = 100;
        int maxRetries = 3;

        RetryPolicy retryPolicy = new RetryNTimes(maxRetries, sleepMsBetweenRetries);
        CuratorFramework client = CuratorFrameworkFactory.newClient(url, retryPolicy);

        client.start();

        try {
            LOG.info(bToS(client.getData().forPath("/lisea/juno/authority/selectAllAuthorities")));
            LOG.info(bToS(client.getData().forPath("/lisea/juno/authority/selectAuthorityByName")));
            LOG.info(
                bToS(client.getData().forPath("/lisea/juno/authority/countAuthorityWithName")));
            LOG.info(bToS(client.getData()
                .forPath("/lisea/juno/authority/updateUserAuthorityByUserAndName")));
            LOG.info(bToS(client.getData()
                .forPath("/lisea/juno/authority/insertUserAuthorityByUserAndName")));
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Test
    public void testZookeeperUser() {

        int sleepMsBetweenRetries = 100;
        int maxRetries = 3;

        RetryPolicy retryPolicy = new RetryNTimes(maxRetries, sleepMsBetweenRetries);
        CuratorFramework client = CuratorFrameworkFactory.newClient(url, retryPolicy);

        client.start();

        try {
            LOG.info(bToS(client.getData().forPath("/lisea/juno/user/selectAllUsers")));
            LOG.info(bToS(client.getData().forPath("/lisea/juno/user/selectUserByUsername")));
            LOG.info(bToS(client.getData().forPath("/lisea/juno/user/insertUser")));
            LOG.info(bToS(client.getData().forPath("/lisea/juno/user/insertUserAndEnable")));
            LOG.info(bToS(client.getData().forPath("/lisea/juno/user/enableUser")));
            LOG.info(bToS(client.getData().forPath("/lisea/juno/user/disableUser")));
            LOG.info(bToS(client.getData().forPath("/lisea/juno/user/updatePassword")));
            LOG.info(bToS(client.getData().forPath("/lisea/juno/user/updatePasswordAndEnabled")));
            LOG.info(bToS(client.getData().forPath("/lisea/juno/user/countUsersWithUsername")));
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private String bToS(byte[] b) {

        return new String(b, StandardCharsets.UTF_8);
    }

}
