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

    private final Long uniqueId;

    public UserPassport(String username, String password, boolean enabled, Long uniqueId,
        Collection<? extends GrantedAuthority> authorities) {

        super(username, password, enabled, true, true, true, authorities);
        this.uniqueId = uniqueId;
    }

    // ==================== IUserPassport ====================
    @Override
    public Long getUniqueId() {

        return uniqueId;
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
                && getUniqueId().equals(((UserPassport) rhs).getUniqueId())
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

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append(": ");
        sb.append("Username: ").append(getUsername()).append("; ");
        sb.append("Password: [PROTECTED]; ");
        sb.append("Enabled: ").append(isEnabled()).append("; ");
        sb.append("AccountNonExpired: ").append(isAccountNonExpired()).append("; ");
        sb.append("credentialsNonExpired: ").append(isCredentialsNonExpired()).append("; ");
        sb.append("AccountNonLocked: ").append(isAccountNonLocked()).append("; ");
        sb.append("UniqueId: ").append(getUniqueId()).append("; ");

        if (!getAuthorities().isEmpty()) {
            sb.append("Granted Authorities: ");

            boolean first = true;
            for (GrantedAuthority auth : getAuthorities()) {
                if (!first) {
                    sb.append(",");
                }
                first = false;

                sb.append(auth);
            }
        }
        else {
            sb.append("Not granted any authorities");
        }

        return sb.toString();
    }
}
