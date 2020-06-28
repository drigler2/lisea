package hr.drigler.lisea.juno.repositories;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserPassportUtils extends JdbcDaoSupport {

//    private static final Logger LOG = LoggerFactory.getLogger(UserPassportUtils.class);
//
//    private IAuthorityService authService;
//
//    public UserPassportUtils(IAuthorityService authService) {
//
//        this.authService = authService;
//    }
//
//    public static void checkRemoveUnknownAuthority(UserDetails user) {
//
//        String psq = sql.getAuthority().getCountAuthorityWithName();
//
//        user.getAuthorities().forEach(a -> {
//            if ((authService.countAuthorityByName(a.getAuthority()).compareTo(1) != 0)) {
//
//                LOG.warn(
//                    "Attempted to create a user {} with unknown authority {}, removing said "
//                        + "authority from user and continuing",
//                    user.getUsername(), a.getAuthority());
//
//                user.getAuthorities().remove(a);
//            }
//        });
//    }
//
}
