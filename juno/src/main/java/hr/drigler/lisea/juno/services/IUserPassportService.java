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
package hr.drigler.lisea.juno.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;

import hr.drigler.lisea.juno.models.IUserPassport;
import hr.drigler.lisea.juno.services.exceptions.AssignUserAuthorityException;
import hr.drigler.lisea.juno.services.exceptions.DuplicateAuthorityException;
import hr.drigler.lisea.juno.services.exceptions.WrongPasswordException;

public interface IUserPassportService extends UserDetailsManager, UserDetailsService {

    /**
     * There is no user context in juno, no way to get current user. Impossible to
     * implement
     * 
     **/
    @Deprecated
    void changePassword(String oldPassword, String newPassword);

    void changeUserPassword(String username, String newPassword, String oldPassword)
        throws WrongPasswordException;

    void createUserHandleDuplicates(IUserPassport user)
        throws DuplicateUserException, AssignUserAuthorityException, DuplicateAuthorityException;

    IUserPassport loadUserPassportByUsername(String username) throws UsernameNotFoundException;

}
