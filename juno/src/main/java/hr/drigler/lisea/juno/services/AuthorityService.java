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

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import hr.drigler.lisea.juno.models.IAuthority;
import hr.drigler.lisea.juno.repositories.DuplicateDatabaseEntryException;
import hr.drigler.lisea.juno.repositories.IAuthorityRepository;
import hr.drigler.lisea.juno.repositories.MissingJoinedDatabaseResourceException;
import hr.drigler.lisea.juno.services.exceptions.AssignUserAuthorityException;
import hr.drigler.lisea.juno.services.exceptions.DuplicateAuthorityException;

@Service
public class AuthorityService implements IAuthorityService {

    private static final Logger LOG = LoggerFactory.getLogger(AuthorityService.class);

    IAuthorityRepository authRepo;

    @Autowired
    public AuthorityService(IAuthorityRepository authRepo) {

        this.authRepo = authRepo;
    }

    @Override
    public List<IAuthority> getAllAuthorities() {

        return authRepo.queryAll();
    }

    @Override
    public IAuthority getAuthorityByName(String authorityName) {

        return authRepo.queryByAuthorityName(authorityName);
    }

    @Override
    public Integer countAuthorityByName(String authorityName) {

        return authRepo.countAuthorityByName(authorityName);
    }

    @Override
    /**
     * will enable authority by default if undefined
     **/
    public void updateAuthorities(UserDetails user) {

        user.getAuthorities().forEach(a -> {
            try {
                if (a instanceof IAuthority) {
                    authRepo.insertSingleUserAuthority(user.getUsername(), a.getAuthority(),
                        ((IAuthority) a).isEnabled());
                }
                else {
                    LOG.info("user_authority {} for user {} created and enabled by default. "
                        + "You probably passed in an instance that doesn't support authority enabling");
                    authRepo.insertSingleUserAuthority(user.getUsername(), a.getAuthority(), true);
                }
            }
            catch (DuplicateDatabaseEntryException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (MissingJoinedDatabaseResourceException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
    }

    @Override
    public void insertAuthorities(UserDetails user)
        throws DuplicateAuthorityException, AssignUserAuthorityException {

        for (GrantedAuthority a : user.getAuthorities()) {
            try {
                if (a instanceof IAuthority) {
                    authRepo.insertSingleUserAuthority(user.getUsername(), a.getAuthority(),
                        ((IAuthority) a).isEnabled());
                }
                else {
                    LOG.info("user_authority {} for user {} created and enabled by default. "
                        + "You probably passed in an instance that doesn't support authority enabling");
                    authRepo.insertSingleUserAuthority(user.getUsername(), a.getAuthority(), true);
                }
            }
            catch (DuplicateDatabaseEntryException e) {
                String t =
                    "Attempted to insert authority {} for user {}, but said combination already "
                        + "exists! skipping...";
                LOG.error(t);
                throw new DuplicateAuthorityException(t);
            }
            catch (MissingJoinedDatabaseResourceException e) {
                String t =
                    "Unable to assign authority {} to user {}. Either user or authority not found.";
                throw new AssignUserAuthorityException(t);
            }
        }

    }

    @Override
    public void updateAuthoritiesAllSwitched(List<? extends GrantedAuthority> toUpdateList,
        UserDetails user, Boolean enabled) {

        user.getAuthorities().stream() //
            .forEach(a -> authRepo.updateSingleUserAuthority(user.getUsername(), a.getAuthority(),
                enabled));
    }

    @Override
    public void disableAuthorities(List<? extends GrantedAuthority> toDisableList,
        UserDetails user) {

        user.getAuthorities().stream() //
            .forEach(a -> authRepo.updateSingleUserAuthority(user.getUsername(), a.getAuthority(),
                false));
    }

}
