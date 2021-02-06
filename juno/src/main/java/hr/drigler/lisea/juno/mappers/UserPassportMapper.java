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

import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.sql.rowset.serial.SerialException;

import hr.drigler.lisea.juno.models.Authority;
import hr.drigler.lisea.juno.models.IUserPassport;
import hr.drigler.lisea.juno.models.UserPassport;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserPassportMapper implements IUserPassportMapper {

    private final String fieldNameUsername;
    private final String fieldNamePassword;
    private final String fieldNameUniqueId;
    private final String fieldNameEnabled;
    private final String fieldNameAuthorityName;

    @Override
    public IUserPassport mapResultSetToUserPassport(ResultSet rs) throws SQLException {

        List<String> authorities = new LinkedList<>();

        rs.next();
        String username =
            Optional.of(rs.getString(fieldNameUsername)).orElseThrow(SQLDataException::new);
        String password =
            Optional.of(rs.getString(fieldNamePassword)).orElseThrow(SQLDataException::new);
        Boolean enabled =
            Optional.of(rs.getBoolean(fieldNameEnabled)).orElseThrow(SQLDataException::new);
        Long uniqueId =
            Optional.of(rs.getLong(fieldNameUniqueId)).orElseThrow(SQLDataException::new);
        authorities.add(rs.getString(fieldNameAuthorityName));

        while (rs.next()) {
            if (!username.equals(rs.getString(fieldNameUsername))
                || !password.equals(rs.getString(fieldNamePassword))
                || !uniqueId.equals(rs.getLong(fieldNameUniqueId))) {
                throw new SerialException(
                    "Multiple unique results found in result set! This indicates data corruption or query errors. Aborting...");
            }

            authorities.add(rs.getString(fieldNameAuthorityName));
        }

        return new UserPassport(username, password, enabled, uniqueId,
            authorities.stream().map(i -> new Authority(true, i)).collect(Collectors.toSet()));
    }
}
