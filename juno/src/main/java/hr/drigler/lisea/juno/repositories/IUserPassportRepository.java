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

import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UserDetails;

import hr.drigler.lisea.juno.models.IUserPassport;

public interface IUserPassportRepository {

    IUserPassport fetchByUsername(String username);

    Integer countUserByUsername(String username);

    void insertUser(UserDetails user) throws DuplicateKeyException;

    void updateUser(IUserPassport userPassport);

    void deleteUser(String username);

    void updateUserPassport(String username, String newPassword);

}
