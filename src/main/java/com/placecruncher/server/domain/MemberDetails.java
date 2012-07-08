package com.placecruncher.server.domain;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Implementation of UserDetails for Member entities.  This class
 * will be serialized into the session, so we can't use the Member
 * entity itself since it references non-serializable services and
 * other fields.
 */
public class MemberDetails implements UserDetails {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String username;
    private String password;
    private String token;
    private boolean enabled;
    private boolean locked;
    private Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

    public MemberDetails(Member member) {
        this.id = member.getId();
        this.username = member.getUsername();
        this.password = member.getPassword();
        this.enabled = member.isEnabled();
        this.locked = member.isAccountLocked();
        this.token = member.getToken();
        this.authorities.add(new SimpleGrantedAuthority(member.getMemberRole().getName()));
    }

    public Integer getMemberId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return !locked;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return enabled;
    }

}
