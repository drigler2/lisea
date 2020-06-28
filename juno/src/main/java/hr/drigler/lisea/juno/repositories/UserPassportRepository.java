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
package hr.drigler.lisea.juno.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import hr.drigler.lisea.juno.factories.UserPassportFactory;
import hr.drigler.lisea.juno.models.IUserPassport;
import hr.drigler.lisea.juno.models.JunoJdbcQueries;

@Repository
public class UserPassportRepository extends JdbcDaoSupport implements IUserPassportRepository {

    private static final Logger LOG = LoggerFactory.getLogger(UserPassportRepository.class);

    JunoJdbcQueries sql;

    @Autowired
    public UserPassportRepository(JunoJdbcQueries sql, DataSource dataSource) {

        this.sql = sql;
        setDataSource(dataSource);
    }

    @Override
    public IUserPassport fetchByUsername(String username) {

        LOG.info("Fetching user by username: " + username);

        String psq = sql.getUser().getSelectUserByUsername();
        List<IUserPassport> resultList = getJdbcTemplate().query(psq, //
            ps -> ps.setString(1, username), //
            this::userPassportExtractor);

        int size = resultList.size();
        if (size > 1) {
            LOG.error("Found more then one user with username: {}", username);
        }
        return size == 1 ? resultList.get(0) : null;

    }

    @Override
    public Integer countUserByUsername(String username) {

        LOG.info("Counting all users with username: " + username);

        String psq = sql.getUser().getCountUsersWithUsername();
        return getJdbcTemplate().query(psq, //
            ps -> ps.setString(1, username), //
            rs -> {
                return rs.getInt(0);
            });
    }

    @Override
    public void insertUser(UserDetails user) throws DuplicateKeyException {

        LOG.info("Inserting user with username: " + user.getUsername());

        String psq = sql.getUser().getInsertUser();

        try {
            getJdbcTemplate().update(psq, //
                ps -> {
                    ps.setString(1, user.getUsername());
                    ps.setString(2, user.getPassword());
                    ps.setBoolean(3, user.isEnabled());
                });
        }
        catch (DuplicateKeyException e) {
            LOG.error("Attempted to create user {} with existing username!", user.getUsername());
            throw e;
        }

    }

    @Override
    public void updateUser(IUserPassport newUserPassport) {

        LOG.info("Updating user with username: " + newUserPassport.getUsername());

        IUserPassport oldUser = fetchByUsername(newUserPassport.getUsername());
        if (oldUser == null) {
            LOG.debug("Failed to find existing user with given username. Aborting....");
            return;
        }
        if (oldUser.equals(newUserPassport)) {
            LOG.debug("Fetched user and one submitted for update are equal! Aborting....");
            return;
        }

        String psq = sql.getUser().getUpdatePasswordAndEnabled();
        getJdbcTemplate().update(psq, //
            ps -> {
                ps.setString(1, newUserPassport.getPassword());
                ps.setBoolean(2, newUserPassport.isEnabled());
                ps.setString(3, oldUser.getUsername());
            });

    }

    @Override
    public void deleteUser(String username) {

        LOG.info("Disabling user with username: " + username);
        // TODO Add to blacklisted
        String psq = sql.getUser().getDisableUser();
        getJdbcTemplate().update(psq, ps -> ps.setString(1, username));

    }

    @Override
    public void updateUserPassport(String username, String newPassword) {

        LOG.info("Updating password for user with username: " + username);
        String psq = sql.getUser().getUpdatePassword();

        getJdbcTemplate().update(psq, //
            ps -> {
                ps.setString(1, newPassword);
                ps.setString(2, username);
            });

    }

    private List<IUserPassport> userPassportExtractor(ResultSet rs) throws SQLException {

        List<IUserPassport> resultList = new LinkedList<>();
        Map<String, HashSet<String>> userPassportMap = new HashMap<>();

        String currentUsername = null;
        List<String> dbFields = sql.getUser().getFields();
        dbFields.addAll(sql.getAuthority().getFields());

        while (rs.next()) {

            if (!rs.getString("username").equals(currentUsername) && !rs.isFirst()) {

                resultList.add(UserPassportFactory.buildFromRS(userPassportMap));
                userPassportMap.clear();
            }

            for (String dbField : dbFields) {

                userPassportMap.putIfAbsent(dbField, new HashSet<String>());
                userPassportMap.get(dbField).add(rs.getString(dbField));
            }

            if (rs.isLast()) {
                resultList.add(UserPassportFactory.buildFromRS(userPassportMap));
            }

            currentUsername = rs.getString("username");
        }
        return resultList;
    }

}
