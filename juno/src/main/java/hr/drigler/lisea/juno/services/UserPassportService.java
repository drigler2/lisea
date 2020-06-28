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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import hr.drigler.lisea.juno.factories.UserPassportFactory;
import hr.drigler.lisea.juno.models.IUserPassport;
import hr.drigler.lisea.juno.repositories.IUserPassportRepository;
import hr.drigler.lisea.juno.services.exceptions.AssignUserAuthorityException;
import hr.drigler.lisea.juno.services.exceptions.DuplicateAuthorityException;
import hr.drigler.lisea.juno.services.exceptions.WrongPasswordException;

@Service
public class UserPassportService implements IUserPassportService {

    private static final Logger LOG = LoggerFactory.getLogger(UserPassportService.class);

    IUserPassportRepository userRepo;
    IAuthorityService authService;
    PasswordEncoder pCoder;

    @Autowired
    public UserPassportService(IUserPassportRepository userRepo, IAuthorityService authService,
        PasswordEncoder encoder) {

        this.userRepo = userRepo;
        this.authService = authService;
        this.pCoder = encoder;
    }

    // ==================== IUserPassport ====================
    @Override
    public IUserPassport loadUserPassportByUsername(String username)
        throws UsernameNotFoundException {

        LOG.debug("Loading user {} by username", username);

        IUserPassport user = userRepo.fetchByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Failed to find username: " + username);
        }
        return user;
    }

    @Override
    public void changeUserPassword(String username, String newPassword, String oldPassword)
        throws WrongPasswordException {

        LOG.debug("Changing the password for user {}", username);

        UserDetails user = loadUserByUsername(username);

        if (pCoder.matches(oldPassword, user.getPassword())) {
            userRepo.updateUserPassport(username, pCoder.encode(newPassword));
        }
        else {
            LOG.error("Provided password for user {} incorrect!", username);
            throw new WrongPasswordException();
        }
    }

    @Override
    public void createUserHandleDuplicates(UserDetails user)
        throws AssignUserAuthorityException, DuplicateUserException, DuplicateAuthorityException {

        LOG.debug("Creating user {} with authorities {}", user.getUsername(),
            user.getAuthorities().toString());

        try {
            userRepo.insertUser(user);
        }
        catch (DuplicateKeyException e) {
            LOG.error(
                "Attempted to create user, but said user already exists! Username must be unique");
            throw new DuplicateUserException();
        }

        authService.insertAuthorities(user);

    }

    // ==================== UserDetailsService ====================
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return loadUserPassportByUsername(username);
    }

    // ==================== UserDetailsManager ====================
    @Override
    public void updateUser(UserDetails user) {

//        UserPassportUtils.checkRemoveUnknownAuthority(user);w
        authService.updateAuthorities(user);

        userRepo.updateUser(UserPassportFactory.buildFromUserDetails(user));

    }

    @Override
    public void deleteUser(String username) {

        LOG.debug("Deleting user {}", username);

        userRepo.deleteUser(username);

    }

    @Override
    @Deprecated
    public void changePassword(String oldPassword, String newPassword) {

        LOG.error("Attempted to call a unimplemented method, please check your implementation");
    }

    @Override
    public boolean userExists(String username) {

        LOG.debug("Checking if user {} exists...", username);

        return userRepo.countUserByUsername(username).equals(1) ? true : false;
    }

    @Override
    public void createUser(UserDetails user) {

    }

}
