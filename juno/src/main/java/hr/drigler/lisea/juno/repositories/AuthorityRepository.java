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
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import hr.drigler.lisea.juno.models.Authority;
import hr.drigler.lisea.juno.models.IAuthority;
import hr.drigler.lisea.juno.models.JunoJdbcQueries;

@Repository
public class AuthorityRepository extends JdbcDaoSupport implements IAuthorityRepository {

    private static final Logger LOG = LoggerFactory.getLogger(AuthorityRepository.class);
    // ==================== IAuthority ====================

    JunoJdbcQueries sql;

    @Autowired
    public AuthorityRepository(JunoJdbcQueries sql, DataSource dataSource) {

        this.sql = sql;
        setDataSource(dataSource);
    }

    @Override
    public List<IAuthority> queryAll() {

        String psq = sql.getAuthority().getSelectAllAuthorities();

        List<IAuthority> resultList = getJdbcTemplate().query(psq, this::authorityExtractor);

        int size = resultList.size();
        LOG.debug("Found authorities {}", size);
        if (size > 1) {
            LOG.debug("Found auhtorities: {}", resultList.toString());
        }
        return size > 0 ? resultList : null;
    }

    @Override
    public IAuthority queryByAuthorityName(String authorityName) {

        String psq = sql.getAuthority().getSelectAuthorityByName();

        List<IAuthority> resultList = getJdbcTemplate().query(psq, //
            ps -> {
                ps.setString(0, authorityName);
            }, //
            this::authorityExtractor);

        int size = resultList.size();
        if (size > 1) {
            LOG.error("Found more then one auhtority with name: {}", authorityName);
        }
        return size == 1 ? resultList.get(0) : null;
    }

    @Override
    public Integer countAuthorityByName(String authorityName) {

        String psq = sql.getAuthority().getCountAuthorityWithName();
        return getJdbcTemplate().queryForObject(psq, new Object[] { authorityName }, Integer.class);

    }

    @Override
    public void updateSingleUserAuthority(String username, IAuthority authority) {

        updateSingleUserAuthority(username, authority.getAuthority(), authority.isEnabled());

    };

    @Override
    public void updateSingleUserAuthority(String username, String authorityName, boolean enabled) {

        String psq = sql.getAuthority().getUpdateUserAuthorityByUserAndName();

        try {
            getJdbcTemplate().update(psq, ps -> { //
                ps.setBoolean(1, enabled);
                ps.setString(2, username);
                ps.setString(3, authorityName);
            });
        }
        catch (DuplicateKeyException e) {
            LOG.error(
                "Failed attempted to create authority {} for user {}. Either user or authority "
                    + "is not found. Skipping...",
                authorityName, username);
        }

    }

    @Override
    public void insertSingleUserAuthority(String username, String authorityName, boolean enabled)
        throws DuplicateDatabaseEntryException, MissingJoinedDatabaseResourceException {

        LOG.info("Inserting authority {} for user {} which enabled is {}", username, authorityName,
            enabled);
        String psq = sql.getAuthority().getInsertUserAuthorityByUserAndName();

        try {
            getJdbcTemplate().update(psq, ps -> { //
                ps.setString(1, username);
                ps.setString(2, authorityName);
                ps.setBoolean(3, enabled);
            });
        }
        catch (DuplicateKeyException e) {
            LOG.error(
                "Failed attempted to create authority {} for user {}. "
                    + "Selected authority already exists for selected user",
                authorityName, username);
            throw new DuplicateDatabaseEntryException();
        }
        catch (DataIntegrityViolationException e) {
            LOG.error(
                "Failed attempted to create authority {} for user {}. Either user or authority "
                    + "is not found. Skipping...",
                authorityName, username);
        }

    }

    private List<IAuthority> authorityExtractor(ResultSet rs) throws SQLException {

        List<IAuthority> resultList = new LinkedList<>();

        while (rs.next()) {
            IAuthority auth =
                new Authority(rs.getBoolean("enabled"), rs.getString("authority_name"));
            resultList.add(auth);
        }

        return resultList;
    }

}
