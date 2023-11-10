package com.Binusa.BawasluServer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    private static final String[] AUTH_WHITELIST = {
            // -- Swagger UI v2
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-ui/**",
            // API controller
            "/login", "/register",
            "/bawaslu/api/berita",
            "/bawaslu/api/pengumuman",
            "/bawaslu/api/permohonan-informasi/add",
            "/bawaslu/api/permohonan-keberatan/add",
            "/bawaslu/api/berita-terbaru",
            "/bawaslu/api/berita/search",
            "/bawaslu/api/berita/arsip",
            "/bawaslu/api/jenis-regulasi/all",
            "/bawaslu/api/jenis-regulasi/get-by-id",
            "/bawaslu/api/menu-regulasi/all",
            "/bawaslu/api/menu-regulasi/get-by-jenis-regulasi",
            "/bawaslu/api/menu-regulasi/get",
            "/bawaslu/api/regulasi/all",
            "/bawaslu/api/regulasi/get-by-menu-regulasi",
            "/bawaslu/api/regulasi/get",
            "/bawaslu/api/berita/get",
            "/bawaslu/api/category-berita/all",
            "/bawaslu/api/category-berita/get",
            "/bawaslu/api/berita/by-category",
            "/bawaslu/api/berita/by-tags"
    };

    private static final String[] AUTH_AUTHORIZATION = {
            "/bawaslu/api/berita/**",
            "/bawaslu/api/pengumuman/**",
            "/bawaslu/api/isiinformasiketerangan/**",
            "/bawaslu/api/jenisinformasi/**",
            "/bawaslu/api/jenisketerangan/**",
            "/bawaslu/api/permohonan-informasi/**",
            "/bawaslu/api/permohonan-keberatan/**",
            "/bawaslu/api/tags/**",
            "/bawaslu/api/jenis-regulasi/**",
            "/bawaslu/api/menu-regulasi/**",
            "/bawaslu/api/regulasi/**",
            "/bawaslu/api/category-berita/**",
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .antMatchers(AUTH_AUTHORIZATION).hasRole("ADMIN")
                .antMatchers(AUTH_AUTHORIZATION).hasAnyRole( "ADMIN")
                .anyRequest()
                .authenticated().and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);


        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}