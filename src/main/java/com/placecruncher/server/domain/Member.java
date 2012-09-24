package com.placecruncher.server.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.core.context.SecurityContextHolder;

import com.placecruncher.server.application.Constants;
import com.placecruncher.server.controller.MemberController;
import com.placecruncher.server.dao.MemberDao;

@Entity
@Table(name="MEMBER", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"}) })
@Configurable(dependencyCheck = true)
public class Member extends SuperEntity {
    private static final Logger LOGGER = Logger.getLogger(MemberController.class);

    public static final int USERNAME_MAXLEN = 64;
    public static final int PASSWORD_MAXLEN = 32;

    private Integer id;
    private String username;
    private String password;
    private boolean enabled = true;
    private boolean locked;
    private String token;
    private String email;
    private String placecruncherEmail;

    private MemberRole memberRole = MemberRole.ROLE_USER;

    private List<ApprovedEmail> approvedEmails = new ArrayList<ApprovedEmail>();
    private List<Device> devices = new ArrayList<Device>();

    @Autowired
    private MemberDao memberDao;

    public void saveOrUpdate() {
        this.memberDao.saveOrUpdate(this);
    }

    public Integer persist() {
        return memberDao.persist(this);
    }

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

    @Enumerated(value = EnumType.STRING)
    @Column(length=Constants.ENUM_MAXLEN)
    public MemberRole getMemberRole() {
        return memberRole;
    }
    public void setMemberRole(MemberRole memberRole) {
        this.memberRole = memberRole;
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

    @JsonIgnore
    @OneToMany(mappedBy="member", fetch=FetchType.LAZY, cascade = { CascadeType.ALL})
    public List<ApprovedEmail> getApprovedEmails() {
        return approvedEmails;
    }

    public void setApprovedEmails(List<ApprovedEmail> approvedEmails) {
        this.approvedEmails = approvedEmails;
    }

    public void notifyDevices(String msg) {
        LOGGER.debug("Sending message '" + msg + "' to user " + getUsername());
        for (Device device : devices) {
            LOGGER.debug("Sending message '" + msg + "' to device " + device);
            device.sendMessage(msg);
        }
    }

    @JsonIgnore
    @OneToMany(mappedBy="member", fetch=FetchType.EAGER, cascade = { CascadeType.ALL} )
    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }


    public String getPlacecruncherEmail() {
        return placecruncherEmail;
    }

    public void setPlacecruncherEmail(String placecruncherEmail) {
        this.placecruncherEmail = placecruncherEmail;
    }

    @Override
    public String toString() {
        return "Member [id=" + id + ", username=" + username + ", password="
                + password + ", enabled=" + enabled + ", locked=" + locked
                + ", token=" + token + ", email=" + email + "]";
    }

    public void registerDevice(Device device) {
       if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Registering : " + device + " for member: " + this);
       }

       List<Device> savedDevices = getDevices();

       if (!savedDevices.isEmpty()) {
           Device sd = savedDevices.get(0);
           if (StringUtils.equals(sd.getToken(), device.getToken())) {
               return;
           }

           savedDevices.remove(0);
           sd.delete();
       }

       device.setMember(this);
       savedDevices.add(device);

       device.saveOrUpdate();
       this.saveOrUpdate();
    }

    public static Member currentMember() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof MemberDetails) {
            return ((MemberDetails)principal).getMember();
        }
        return null;
    }

    public static boolean isAuthenticated() {
      return SecurityContextHolder.getContext().getAuthentication() != null;
    }

}
