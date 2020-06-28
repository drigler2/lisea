package hr.drigler.lisea.juno.services;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import hr.drigler.lisea.juno.models.IAuthority;
import hr.drigler.lisea.juno.services.exceptions.AssignUserAuthorityException;
import hr.drigler.lisea.juno.services.exceptions.DuplicateAuthorityException;

public interface IAuthorityService {

    public List<IAuthority> getAllAuthorities();

    public IAuthority getAuthorityByName(String authorityName);

    public Integer countAuthorityByName(String authorityName);

    public void updateAuthorities(UserDetails user);

    public void updateAuthoritiesAllSwitched(List<? extends GrantedAuthority> toUpdateList,
        UserDetails user, Boolean enabled);

    public void disableAuthorities(List<? extends GrantedAuthority> toDeleteList, UserDetails user);

    void insertAuthorities(UserDetails user)
        throws DuplicateAuthorityException, AssignUserAuthorityException;

}
