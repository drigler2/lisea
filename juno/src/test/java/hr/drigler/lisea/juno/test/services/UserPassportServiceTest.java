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
package hr.drigler.lisea.juno.test.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import hr.drigler.lisea.juno.models.Authority;
import hr.drigler.lisea.juno.models.IAuthority;
import hr.drigler.lisea.juno.models.IUserPassport;
import hr.drigler.lisea.juno.models.UserPassport;
import hr.drigler.lisea.juno.services.IUserPassportService;
import hr.drigler.lisea.juno.services.IVulcanService;
import hr.drigler.lisea.juno.services.exceptions.AssignUserAuthorityException;
import hr.drigler.lisea.juno.services.exceptions.DuplicateAuthorityException;
import hr.drigler.lisea.juno.services.exceptions.DuplicateUserException;
import hr.drigler.lisea.juno.services.exceptions.WrongPasswordException;

@SpringBootTest
//@ExtendWith(SpringExtension.class)
//@EnableAutoConfiguration
public class UserPassportServiceTest {

    private static final Logger LOG = LoggerFactory.getLogger(UserPassportServiceTest.class);

    private final IUserPassportService service;
    private final PasswordEncoder pCoder;
    private final IVulcanService vulcan;

    @Autowired
    public UserPassportServiceTest(IUserPassportService service, PasswordEncoder pCoder,
        IVulcanService vulcan) {

        this.service = service;
        this.pCoder = pCoder;
        this.vulcan = vulcan;
    }

    @Test
    public void loadUserDetailsByUsernameTest() {

        LOG.info("Running loadUserDetailsByUsernameTest");

        UserDetails user = service.loadUserByUsername("admin");

        assertThat(user).isNotNull();
        assertThat(user).isInstanceOf(UserPassport.class);
        assertThat(user.getUsername()).isEqualTo("admin");
        assertThat(user.getPassword().equals("admin"));
    }

    @Test
    public void createUser() {

        LOG.info("Running createUser");

        String password = pCoder.encode("testPassword");
        List<IAuthority> authList = Arrays.asList(new Authority(true, "TEST"),
            new Authority(true, "DAMIR"), new Authority(true, "ADMIN"));

        IUserPassport user =
            new UserPassport("testUser", password, true, vulcan.getVulcanId(), authList);

        try {
            service.createUserHandleDuplicates(user);
        }
        catch (DuplicateUserException //
            | AssignUserAuthorityException //
            | DuplicateAuthorityException e) {

        }
    }

    @Test
    public void changeUserPassword() {

        LOG.info("Running changeUserPassword");

        try {
            service.changeUserPassword("testUser", "newTestPassword", "wrongPass");
        }
        catch (WrongPasswordException e) {

        }

        try {
            service.changeUserPassword("testUser", "newTestPassword", "testPassword");
        }
        catch (WrongPasswordException e) {

        }
    }

    @Test
    public void testPassword() {

        String oldPass = "$2a$10$Ldqir/LK3XvuVbtM5cGaiO6wlf69aLeUksOgv4HmbSPXRBXRUjCum";
        String newPass = pCoder.encode("testPassword");
        LOG.debug(newPass);

        assertThat(pCoder.matches(oldPass, newPass));
    }

}
