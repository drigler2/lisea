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

import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hr.drigler.lisea.juno.models.JunoJdbcQueries;
import hr.drigler.lisea.juno.utils.ZooUtils;

@Configuration
public class JdbcQueryInitialiser {

    private static final Logger LOG = LoggerFactory.getLogger(JdbcQueryInitialiser.class);

    private static final String USER_PREFIX = "/lisea/juno/user/";
    private static final String AUTHORITY_PREFIX = "/lisea/juno/authority/";

    private JunoJdbcQueries q;
    private CuratorFramework client;

    @Autowired
    public JdbcQueryInitialiser(CuratorFramework client) {

        this.client = client;
    }

    @Bean
    public JunoJdbcQueries junoJdbcQueriesInitialiser() throws ConfigurationException, Exception {

        q = new JunoJdbcQueries();

        if (client == null) {
            throw new ConfigurationException("Unable to find Zookeeper client!");
        }

        getValidateUser();
        getValidateAuthority();

        LOG.debug("Juno queries: {}", q);

        return q;
    }

    private void getValidateUser() throws ConfigurationException, Exception {

        JunoJdbcQueries.User user = q.new User();

        String selectAllUsers =
            ZooUtils.bToS(client.getData().forPath(USER_PREFIX + "selectAllUsers"));
        String selectUserByUsername =
            ZooUtils.bToS(client.getData().forPath(USER_PREFIX + "selectUserByUsername"));
        String insertUser = ZooUtils.bToS(client.getData().forPath(USER_PREFIX + "insertUser"));
        String insertUserAndEnable =
            ZooUtils.bToS(client.getData().forPath(USER_PREFIX + "insertUserAndEnable"));
        String enableUser = ZooUtils.bToS(client.getData().forPath(USER_PREFIX + "enableUser"));
        String disableUser = ZooUtils.bToS(client.getData().forPath(USER_PREFIX + "disableUser"));
        String updatePassword =
            ZooUtils.bToS(client.getData().forPath(USER_PREFIX + "updatePassword"));
        String updatePasswordAndEnabled =
            ZooUtils.bToS(client.getData().forPath(USER_PREFIX + "updatePasswordAndEnabled"));
        String countUsersWithUsername =
            ZooUtils.bToS(client.getData().forPath(USER_PREFIX + "countUsersWithUsername"));

        List<Object> validationList = Arrays.asList(selectAllUsers, selectUserByUsername,
            insertUser, insertUserAndEnable, enableUser, disableUser, updatePassword,
            updatePasswordAndEnabled, countUsersWithUsername);

        ZooUtils.validateList(validationList);

        user.setSelectAllUsers(selectAllUsers);
        user.setSelectUserByUsername(selectUserByUsername);
        user.setInsertUser(insertUser);
        user.setInsertUserAndEnable(insertUserAndEnable);
        user.setEnableUser(enableUser);
        user.setDisableUser(disableUser);
        user.setUpdatePassword(updatePassword);
        user.setUpdatePasswordAndEnabled(updatePasswordAndEnabled);
        user.setCountUsersWithUsername(countUsersWithUsername);

        q.setUser(user);
    }

    private void getValidateAuthority() throws ConfigurationException, Exception {

        JunoJdbcQueries.Authority authority = q.new Authority();

        String selectAllAuthorities =
            ZooUtils.bToS(client.getData().forPath(AUTHORITY_PREFIX + "selectAllAuthorities"));
        String selectAuthorityByName =
            ZooUtils.bToS(client.getData().forPath(AUTHORITY_PREFIX + "selectAuthorityByName"));
        String countAuthorityWithName =
            ZooUtils.bToS(client.getData().forPath(AUTHORITY_PREFIX + "countAuthorityWithName"));
        String updateUserAuthorityByUserAndName = ZooUtils
            .bToS(client.getData().forPath(AUTHORITY_PREFIX + "updateUserAuthorityByUserAndName"));
        String insertUserAuthorityByUserAndName = ZooUtils
            .bToS(client.getData().forPath(AUTHORITY_PREFIX + "insertUserAuthorityByUserAndName"));

        List<Object> validationList =
            Arrays.asList(selectAllAuthorities, selectAuthorityByName, countAuthorityWithName,
                updateUserAuthorityByUserAndName, insertUserAuthorityByUserAndName);

        ZooUtils.validateList(validationList);

        authority.setSelectAllAuthorities(selectAllAuthorities);
        authority.setSelectAuthorityByName(selectAuthorityByName);
        authority.setCountAuthorityWithName(countAuthorityWithName);
        authority.setUpdateUserAuthorityByUserAndName(updateUserAuthorityByUserAndName);
        authority.setInsertUserAuthorityByUserAndName(insertUserAuthorityByUserAndName);

        q.setAuthority(authority);
    }
}
