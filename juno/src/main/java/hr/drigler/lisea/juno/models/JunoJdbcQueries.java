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

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JunoJdbcQueries implements Serializable {

    private String schema;
    private User user;
    private Authority authority;

    @Getter
    @Setter
    @ToString
    public class User implements Serializable {

        // TODO move to mercury
        private final List<String> fields =
            Arrays.asList("id", "username", "password", "unique_id", "enabled");
        private String selectAllUsers;
        private String selectUserByUsername;
        private String insertUser;
        private String insertUserAndEnable;
        private String enableUser;
        private String disableUser;
        private String updatePassword;
        private String updatePasswordAndEnabled;
        private String countUsersWithUsername;

    }

    @Getter
    @Setter
    @ToString
    public class Authority implements Serializable {

        // TODO move to mercury
        private final List<String> fields = Arrays.asList("id", "authority_name");
        private String selectAllAuthorities;
        private String selectAuthorityByName;
        private String countAuthorityWithName;
        private String updateUserAuthorityByUserAndName;
        private String insertUserAuthorityByUserAndName;
    }
}
