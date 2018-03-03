package uk.ac.ebi.pride.mongodb.authentication.model;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.HashSet;

public class UserAuthentication implements Authentication {

    private final User user;

    private boolean authenticated = true;

    public UserAuthentication(User user) {
        this.user = user;
    }

    @Override
    public String getName() {
        return user.getFirstName();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        final GrantedAuthority a = new GrantedAuthority(){
            @Override
            public String getAuthority() {
                return "USER";
            }
        };
        return new HashSet<GrantedAuthority>(){{ add(a); }};
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public User getDetails() {
        return user;
    }

    @Override
    public Object getPrincipal() {
        return user;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }
}
