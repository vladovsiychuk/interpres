package letscode.interpres.config;

import org.springframework.beans.factory.annotation.Autowired;
import letscode.interpres.services.CustomOidcUserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomOidcUserService customOidcUserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/", "/login**", "/js/**", "/error**").permitAll()
                .anyRequest().authenticated()
                .and().logout().logoutSuccessUrl("/").permitAll()
                .and().oauth2Login().userInfoEndpoint()
                .oidcUserService(customOidcUserService);
    }
}
