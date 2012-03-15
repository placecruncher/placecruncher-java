package com.placecruncher.server.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


/**
 * An identifiable person, program, or process that is associated with the authentication and authorization systems.
 */
@Entity
@Table(name=Principal.DB_TABLE, uniqueConstraints = {@UniqueConstraint(columnNames = {"USERNAME"}) })
@SequenceGenerator(name = Principal.DB_SEQ, sequenceName = Principal.DB_SEQ)
public class Principal extends AbstractEntity implements UserDetails {
    private static final long serialVersionUID = 1L;

    public static final String DB_TABLE = "PRINCIPAL";
    public static final String DB_SEQ = DB_TABLE + "_SEQ";

    public static final int USERNAME_MAXLEN = 64;
    public static final int PASSWORD_MAXLEN = 32;

    private Integer id;
    private String username;
    private String password;
    private boolean enabled;
    private boolean locked;
    private Member member;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = DB_SEQ)
    @Column(name="ID", nullable=false)
    public Integer getId() {
        return id;
    }

    protected void setId(Integer id) {
        this.id = id;
    }

    /** {@inheritDoc} */
    @Column(name="USERNAME", nullable=false, unique=true, length=USERNAME_MAXLEN)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /** {@inheritDoc} */
    @Column(name="PASSWRD", length=PASSWORD_MAXLEN)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @OneToOne
    @JoinColumn(name = "MEMBER_ID", nullable = true)
    public Member getMember() {
        return member;
    }
    public void setMember(Member member) {
        this.member = member;
    }

    @Column(name="LOCKED", nullable=false)
    public boolean isAccountLocked() {
        return locked;
    }

    public void setAccountLocked(boolean accountLocked) {
        this.locked = accountLocked;
    }


    /** {@inheritDoc} */
    @Column(name="enabled", nullable=false)
    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /** {@inheritDoc} */
    @Transient
    public Collection<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new PrincipalAuthority("ROLE_USER"));
        if(username.equals("root"))
        {
            authorities.add(new PrincipalAuthority("ROOT"));
        }
        return authorities;
    }

    /** {@inheritDoc} */
    @Transient
    public boolean isAccountNonExpired() {
        return true;
    }

    /** {@inheritDoc} */
    @Transient
    public boolean isAccountNonLocked() {
        return !locked;
    }

    /** {@inheritDoc} */
    @Transient
    public boolean isCredentialsNonExpired() {
        return true;
    }

    private final class PrincipalAuthority implements GrantedAuthority {
        private static final long serialVersionUID = 1L;
        private String authority;

        private PrincipalAuthority(String authority) {
            this.authority = authority;
        }

        /** {@inheritDoc} */
        public String getAuthority() {
            return authority;
        }
    }


}
