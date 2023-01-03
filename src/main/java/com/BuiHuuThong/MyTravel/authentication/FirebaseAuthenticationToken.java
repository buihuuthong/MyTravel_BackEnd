package com.BuiHuuThong.MyTravel.authentication;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.Collection;

@Getter
@Setter
public class FirebaseAuthenticationToken extends PreAuthenticatedAuthenticationToken {
    private String role;

    public FirebaseAuthenticationToken(Object aPrincipal, Object aCredentials) {
        super(aPrincipal, aCredentials);
    }

    public FirebaseAuthenticationToken(
            Object aPrincipal,
            Object aCredentials,
            Collection<? extends GrantedAuthority> anAuthorities
    ) {
        super(aPrincipal, aCredentials, anAuthorities);
    }
}
