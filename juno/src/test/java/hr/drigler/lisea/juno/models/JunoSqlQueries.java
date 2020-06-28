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
package hr.drigler.lisea.juno.models;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JunoSqlQueries implements Serializable {

    private static final long serialVersionUID = -3761098866943976812L;

    private String schema;
    private User user;
    private Authority authority;

    public JunoSqlQueries(@Nullable String schema) {

        if (schema != null && schema.length() > 0 && isNameValid(schema)) {
            this.schema = schema + ".";
        }
        else {
            this.schema = "";
        }

        this.user = new User();
        this.authority = new Authority();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public class User implements Serializable {

        private static final long serialVersionUID = 2498970123555012674L;

        private final List<String> fields = Arrays.asList("id", "username", "password", "enabled");

        private final String selectAllUsers = "select * from " + schema + "user uu " //
            + "inner join " + schema + "user_authority ua on ua.id_user = uu.id " //
            + "inner join " + schema + "authority aa on aa.id = ua.id_authority " //
            + "where uu.enabled = true and ua.enabled = true";

        private final String selectUserByUsername = "select * from " + schema + "user uu " //
            + "inner join " + schema + "user_authority ua on ua.id_user = uu.id " //
            + "inner join " + schema + "authority aa on aa.id = ua.id_authority " //
            + "where uu.enabled = true and ua.enabled = true " //
            + "and uu.username = ?";

        private final String insertUser =
            "insert into " + schema + "user (username, password, enabled) " + "values(?, ?, ?)";

        private final String insertUserAndEnable =
            "insert into " + schema + "user (username, password, enabled) " + "values(?, ?, true)";

        private final String enableUser =
            "update " + schema + "user uu set enabled = true where uu.username = ?";

        private final String disableUser =
            "update " + schema + "user uu set enabled = false where uu.username = ?";

        private final String updatePassword =
            "update " + schema + "user uu set password = ? where uu.username = ?";

        private final String updatePasswordAndEnabled =
            "update " + schema + "user uu set password = ?, enabled = ? where uu.username = ?";

        private final String countUsersWithUsername =
            "select count(*) from " + schema + "user uu where uu.username = ?";

    }

    @Getter
    @Setter
    @AllArgsConstructor
    public class Authority implements Serializable {

        private static final long serialVersionUID = 2937227203184530458L;

        private final List<String> fields = Arrays.asList("id", "authority_name");

        private final String selectAllAuthorities = "select * from " + schema + "authority";

        private final String selectAuthorityByName =
            "select * from " + schema + "authority aa where aa.authority_name = ?";

        private final String countAuthorityWithName =
            "select count(*) from " + schema + "authority aa where aa.authority_name = ?";

        private final String updateUserAuthorityByUserAndName = //
            "update " + schema + "user_authority set enabled=? " //
                + "where id_user=(select id from " + schema + "user where username=?) " //
                + "and id_authority=(select id from " + schema
                + "authority where authority_name=?)";

        private final String insertUserAuthorityByUserAndName = //
            "insert into " + schema + "user_authority (id_user, id_authority, enabled) " //
                + "values(" //
                + "(select id from " + schema + "user where username=?)," //
                + "(select id from " + schema + "authority where authority_name=?)," //
                + "?" //
                + ")";

    }

    private static boolean isNameValid(String schema) {

        List<String> bad = Arrays.asList(".", ",", ":", "\'", "?", "!");

        return bad.stream().anyMatch(b -> b.equals(schema)) == true ? false : true;
    }

}
