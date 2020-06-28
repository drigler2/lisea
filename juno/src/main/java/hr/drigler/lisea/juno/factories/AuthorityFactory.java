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

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import hr.drigler.lisea.juno.models.Authority;
import hr.drigler.lisea.juno.models.IAuthority;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthorityFactory {

    public static Set<IAuthority> buildFromRS(Collection<String> authorityNameSet) {

        Set<IAuthority> authSet = new HashSet<>();

        authorityNameSet.forEach(rs -> authSet.add(new Authority(true, rs)));

        return authSet;
    }

}
