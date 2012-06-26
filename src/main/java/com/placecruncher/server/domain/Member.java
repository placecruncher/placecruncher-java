package com.placecruncher.server.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.core.GrantedAuthority;

import com.placecruncher.server.dao.MemberDao;
import com.placecruncher.server.service.ApplePushNotificationService;

@Entity
@Table(name="MEMBER", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"}) })
@Configurable(dependencyCheck = true)
public class Member extends SuperEntity {
    private static final long serialVersionUID = 1L;

    public static final int USERNAME_MAXLEN = 64;
    public static final int PASSWORD_MAXLEN = 32;

    private Integer id;
    private String username;
    private String password;
    private boolean enabled;
    private boolean locked;
    private String token;
    private String email;
    
    private List<ApprovedEmail> approvedEmails;

    @Autowired
    private MemberDao memberDao;
    
    @Autowired
    private ApplePushNotificationService applePushNotificationService;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable=false)
    public Integer getId() {
        return id;
    }

    protected void setId(Integer id) {
        this.id = id;
    }

    /** {@inheritDoc} */
    @Column(nullable=false, unique=true, length=USERNAME_MAXLEN)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    /** {@inheritDoc} */
    @Column(nullable=false, unique=true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /** {@inheritDoc} */
    @Column(length=PASSWORD_MAXLEN)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(nullable=false)
    public boolean isAccountLocked() {
        return locked;
    }

    public void setAccountLocked(boolean accountLocked) {
        this.locked = accountLocked;
    }

    @Column(nullable=false)
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    /** {@inheritDoc} */
    @Column(nullable=false)
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

    public void saveOrUpdate() {
        this.memberDao.saveOrUpdate(this);
    }

    @JsonIgnore
    @OneToMany(mappedBy="member", fetch=FetchType.LAZY)
    public List<ApprovedEmail> getApprovedEmails() {
        return approvedEmails;
    }

    public void setApprovedEmails(List<ApprovedEmail> approvedEmails) {
        this.approvedEmails = approvedEmails;
    }

    public void processEmail() {
        applePushNotificationService.sendMessage("davids", new Device());        
    }

    @Override
    public String toString() {
        return "Member [id=" + id + ", username=" + username + ", password="
                + password + ", enabled=" + enabled + ", locked=" + locked
                + ", token=" + token + ", email=" + email + "]";
    }
}
