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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hr.drigler.lisea.juno.models.JunoJdbcQueries;

@Configuration
public class JdbcQueryInitialiser {

    private static final Logger LOG = LoggerFactory.getLogger(JdbcQueryInitialiser.class);

    private JunoJdbcQueries q;
    private final ICuratorFrameworkConfig zoo;
    @Value("${zookeeper.url_prefix.juno.queries.user}")
    private String userPrefix;
    @Value("${zookeeper.url_prefix.juno.queries.authority}")
    private String authorityPrefix;

    @Autowired
    public JdbcQueryInitialiser(ICuratorFrameworkConfig zoo) {

        this.zoo = zoo;
    }

    @Bean
    public JunoJdbcQueries junoJdbcQueriesInitialiser() throws ConfigurationException, Exception {

        q = new JunoJdbcQueries();

        getValidateUser();
        getValidateAuthority();

        LOG.info("Juno queries: {}", q);

        return q;
    }

    private void getValidateUser() throws ConfigurationException, Exception {

        JunoJdbcQueries.User user = q.new User();

        String selectAllUsers = zoo.getRequiredZooData(userPrefix + "selectAllUsers");
        String selectUserByUsername = zoo.getRequiredZooData(userPrefix + "selectUserByUsername");
        String insertUser = zoo.getRequiredZooData(userPrefix + "insertUser");
        String insertUserAndEnable = zoo.getRequiredZooData(userPrefix + "insertUserAndEnable");
        String enableUser = zoo.getRequiredZooData(userPrefix + "enableUser");
        String disableUser = zoo.getRequiredZooData(userPrefix + "disableUser");
        String updatePassword = zoo.getRequiredZooData(userPrefix + "updatePassword");
        String updatePasswordAndEnabled =
            zoo.getRequiredZooData(userPrefix + "updatePasswordAndEnabled");
        String countUsersWithUsername =
            zoo.getRequiredZooData(userPrefix + "countUsersWithUsername");

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
            zoo.getRequiredZooData(authorityPrefix + "selectAllAuthorities");
        String selectAuthorityByName =
            zoo.getRequiredZooData(authorityPrefix + "selectAuthorityByName");
        String countAuthorityWithName =
            zoo.getRequiredZooData(authorityPrefix + "countAuthorityWithName");
        String updateUserAuthorityByUserAndName =
            zoo.getRequiredZooData(authorityPrefix + "updateUserAuthorityByUserAndName");
        String insertUserAuthorityByUserAndName =
            zoo.getRequiredZooData(authorityPrefix + "insertUserAuthorityByUserAndName");

        authority.setSelectAllAuthorities(selectAllAuthorities);
        authority.setSelectAuthorityByName(selectAuthorityByName);
        authority.setCountAuthorityWithName(countAuthorityWithName);
        authority.setUpdateUserAuthorityByUserAndName(updateUserAuthorityByUserAndName);
        authority.setInsertUserAuthorityByUserAndName(insertUserAuthorityByUserAndName);

        q.setAuthority(authority);
    }
}
