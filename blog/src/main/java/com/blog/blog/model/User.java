package com.blog.blog.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User implements UserDetails {

    @Id
    private String username;

    @Email
    private String email;

    @NotNull
    private String password;

    private String img;

    private Date birthday;

    @NotNull
    private boolean enabled;


    @ManyToMany( cascade = CascadeType.ALL, fetch = FetchType.EAGER )
    @JoinTable(
            name = "users_roles",
            joinColumns = {@JoinColumn(name="users_username")},
            inverseJoinColumns = {@JoinColumn(name="roles_id")}
    )
    private Collection<Authorities> roles;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<Authorities> roles = this.getRoles();

        Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        for(Authorities authoriti : roles){
            authorities.add(new SimpleGrantedAuthority(authoriti.getName()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return username;
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
        return enabled;
    }
}
