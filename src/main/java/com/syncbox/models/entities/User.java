package com.syncbox.models.entities;

import com.syncbox.models.Providers;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class User implements  UserDetails{

    @Id
    private String userId;
    @Column(nullable = false)
    private String name;
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    @Column(length = 10000, columnDefinition = "TEXT")
    private String about;
    private String phoneNumber;
    @Column(length = 10000, columnDefinition = "TEXT")
    private String profilePic;
    private boolean enabled = true;
    private boolean emailVerified = false;
    private boolean phoneVerified = false;

    private String providerId;
    @Enumerated(EnumType.STRING)
    private Providers provider = Providers.SELF;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Contact> contacts = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

//    UserDetails Methods
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List of roles
//        Collection of SimpleGrantedAuthority(roles{ADMIN,USER})
        Collection<SimpleGrantedAuthority> simpleGrantedAuthorities = roles.stream().map(role-> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
        return simpleGrantedAuthorities;
    }

    @Override
    public String getUsername() {
        return this.email;
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
    public boolean isEnabled(){
        return this.enabled;
    }
}
