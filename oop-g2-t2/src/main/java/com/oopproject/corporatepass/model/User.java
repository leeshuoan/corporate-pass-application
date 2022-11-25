package com.oopproject.corporatepass.model;

import java.util.*;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonProperty;

// import com.oopproject.corporatepass.repository.UserRoleRepository;

@Entity
public class User implements UserDetails {
    @Id
    private String email;
    private String username;
    // the purpose of this is to ensure that the password is not sent to the client
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String name;
    private int contact;
    private double dues;
    // the purpose of this is to ensure that the salt is not sent to the client
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String salt;
    private int isBanned;
    private boolean enabled;
    private int verificationCode;
    @Transient
    private ArrayList<Integer> type;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "email", referencedColumnName = "email")
    private List<UserRole> userRole;

    // @ManyToMany
    // @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "email"), inverseJoinColumns = @JoinColumn(name = "roleId"))
    @Transient
    private Set<Role> roles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public User() {
        super();
        this.enabled = false;
    }

    public User(User user) {
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.name = user.getName();
        this.contact = user.getContact();
        this.dues = user.getDues();
        this.salt = user.getSalt();
        this.roles = user.getRoles();
        this.isBanned = user.getBanned();
        this.enabled = false;
    }

    public int getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(int verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getContact() {
        return contact;
    }

    public void setContact(int contact) {
        this.contact = contact;
    }

    public double getDues() {
        return dues;
    }

    public void setDues(double dues) {
        this.dues = dues;
    }

    public String getSalt() {
        return salt;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public int getBanned() {
        return isBanned;
    }

    public void setBanned(int isBanned) {
        this.isBanned = isBanned;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public ArrayList<Integer> getType() {
        return type;
    }

    public void setType(ArrayList<Integer> type) {
        this.type = type;
    }

}
