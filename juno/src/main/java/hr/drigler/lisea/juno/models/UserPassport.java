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

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UserPassport extends User implements IUserPassport {

    private static final long serialVersionUID = -106371220918739411L;

    public UserPassport(String username, String password, boolean enabled,
        Collection<? extends GrantedAuthority> authorities) {

        super(username, password, enabled, true, true, true, authorities);
    }

    // ==================== Object ====================
    @Override
    public boolean equals(Object rhs) {

        if (this == rhs) {
            return true;
        }

        if (rhs != null || rhs instanceof UserPassport) {

            if (getUsername().equals(((UserPassport) rhs).getUsername())
                && getPassword().equals(((UserPassport) rhs).getPassword())
                && getAuthorities().equals(((UserPassport) rhs).getAuthorities())
                && isEnabled() == ((UserPassport) rhs).isEnabled()) {

                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {

        // username is unique by contract
        return super.hashCode();
    }

}
