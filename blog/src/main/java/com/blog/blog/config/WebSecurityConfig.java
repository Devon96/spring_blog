package com.blog.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableGlobalMethodSecurity(securedEnabled = true)
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.
                authorizeRequests()
                .antMatchers("/post/add").hasAuthority("ADMIN")
                .antMatchers("/post/create").hasAuthority("ADMIN")
                .antMatchers("/post/edit/**").hasAuthority("ADMIN")
                .antMatchers("/comment/add/**").hasAuthority("USER")
                .antMatchers("/post/delete/**").hasAuthority("ADMIN")
                .antMatchers("/comment/delete/**/**").hasAuthority("USER")
                .antMatchers("/rating/post/like/**").hasAuthority("USER")
                .antMatchers("/rating/post/dislike/**").hasAuthority("USER")
                .antMatchers("/rating/comment/like/**/**").hasAuthority("USER")
                .antMatchers("/rating/comment/dislike/**/**").hasAuthority("USER")
                .antMatchers("/register").permitAll()
                .antMatchers("/post/**").permitAll()
                .antMatchers("/**").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/css/**", "/script/**", "/images/**", "/bootstrap/**", "/jquery/**").permitAll()
                .anyRequest().authenticated()
                    .and()
                .formLogin()
                .loginPage("/login").permitAll().successForwardUrl("/")
                    .and()
                .logout().permitAll()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/");
    }

}



    /*
    DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{



        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder());
    }


    */



