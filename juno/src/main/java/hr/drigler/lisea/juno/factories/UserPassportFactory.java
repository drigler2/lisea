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
package hr.drigler.lisea.juno.factories;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import hr.drigler.lisea.juno.models.Authority;
import hr.drigler.lisea.juno.models.IUserPassport;
import hr.drigler.lisea.juno.models.UserPassport;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserPassportFactory {

    public static IUserPassport buildFromUserDetails(UserDetails user, Long uniqueId) {

        return new UserPassport(user.getUsername(), user.getPassword(), user.isEnabled(), uniqueId,
            user.getAuthorities());
    }

    public static IUserPassport buildEnabledUser(String username, String encodedPass,
        Long uniqueId) {

        List<Authority> auth = Arrays.asList(new Authority(true, "user"));

        return new UserPassport(username, encodedPass, true, uniqueId, auth);
    }
}
