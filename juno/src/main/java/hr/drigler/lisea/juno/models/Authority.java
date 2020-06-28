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

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Authority implements IAuthority {

    private static final long serialVersionUID = 822773518468876847L;

    private Boolean enabled;
    private String authorityName;

    // ==================== GrantedAuthority ====================
    @Override
    public String getAuthority() {

        return authorityName;
    }

    // ==================== iAuthority ====================

    @Override
    public Boolean isEnabled() {

        return enabled;
    }

}
