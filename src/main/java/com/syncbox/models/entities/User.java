package com.syncbox.models.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.syncbox.models.Providers;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.*;
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
    private String address;
    private String gender;
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Date must be in the format yyyy-MM-dd")
    private String dob;
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Date must be in the format yyyy-MM-dd")
    private String joinDate;
    @Column(length = 10000, columnDefinition = "TEXT")
    private String profilePic;
    private String cloudinaryPublicId;
    private boolean enabled = false;
    private boolean emailVerified = false;
    private boolean phoneVerified = false;

    private String providerId;
    @Enumerated(EnumType.STRING)
    private Providers provider = Providers.SELF;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonManagedReference
    @JsonIgnore
    private List<Contact> contacts = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Role role;

    private String emailToken;

//    UserDetails Methods
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List of roles
//        Collection of SimpleGrantedAuthority(roles{ADMIN,USER})
//        Collection<SimpleGrantedAuthority> simpleGrantedAuthorities = roles.stream().map(role-> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
//        return simpleGrantedAuthorities;
        Set<GrantedAuthority> set = new HashSet<>(Set.of(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return role.getRoleName();
            }
        }));
        return set;
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
