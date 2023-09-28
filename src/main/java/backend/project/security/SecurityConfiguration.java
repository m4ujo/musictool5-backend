package backend.project.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfiguration {
/*
http://localhost:8080/swagger-ui/index.html
*/
    private static final String[] AUTH_WHITELIST = {
            // -- swagger ui
            "/v2/api-docs/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/swagger-ui/**",
            // -- login
            "/api/login/**"
    };

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration
                                                        authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /*
    Autorizacion

    1. Cuales van a ser los pedidos=request que seran evaluados para saber si el usuario tiene permiso sobre ellos
        a. AnyRequest -> Todos
        b. RequestMatchers -> Los que coincidan con la lista (rutas)
        b. RequestMatchers with HttpMethod -> Los que coincidan con la lista (rutas + metodo HTTP)


    2. Cual es la regla de autorizacion que va a aplicar sobre esos pedidos=request
        a. permitAll()
        b. denyAll()
        c. requestMatchers()
        d. hasRole()
        e. hayAnyRole()
        f. hasAuthority()
        g. SpEL Spring Expression Language
        h. authenticated()
    */
    @Autowired
    private JwtRequestFilter jwtRequestFilter;


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
/*
        //Opcion 1 - Basic Auth
        http.httpBasic();

        //http.authorizeHttpRequests().antMatchers("/api/employees/**").hasRole("ADMIN");
        http.authorizeHttpRequests().antMatchers("/api/employees/projects").hasRole("ADMIN");
        //http.authorizeHttpRequests().antMatchers("/api/employees").hasRole("TEACHER");
        http.authorizeHttpRequests().antMatchers("/api/employees").hasAnyRole("ADMIN","TEACHER");
        //http.authorizeHttpRequests().antMatchers("/api/employees").hasAuthority("ROLE_ADMIN");
        //http.authorizeHttpRequests().antMatchers("/api/employees/projects").access(new WebExpressionAuthorizationManager("hasRole('ADMIN') or hasRole('TEACHER')"));
        //http.authorizeHttpRequests().anyRequest().authenticated();
        http.csrf().disable();
        return http.build();


        Opcion 2 - Basic Auth
        return http
                .httpBasic()
                .and()
                .authorizeHttpRequests().antMatchers("/api/employees/projects").hasRole("ADMIN")
                .and()
                .authorizeHttpRequests().antMatchers("/api/employees").hasAnyRole("ADMIN","TEACHER")
                .and()
                .authorizeHttpRequests().anyRequest().authenticated()
                .and()
                .csrf().disable()
                .build();


        Opcion 3 - Basic Auth

        http.httpBasic();
        http.authorizeHttpRequests( (auth) ->auth
                .antMatchers("/api/login/**").permitAll()
                .antMatchers("/api/employees/projects").hasRole("ADMIN")
                .antMatchers("/api/employees").hasAnyRole("ADMIN","TEACHER")
                .anyRequest().authenticated()
        );
        http.csrf().disable();
        return http.build();
*/

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        http.csrf().disable();
        http.cors(withDefaults());


        http.authorizeHttpRequests( (auth) ->auth
                .antMatchers(AUTH_WHITELIST).permitAll()
                .antMatchers("/api/employees/projects").hasRole("ADMIN")
                .antMatchers("/api/employees").hasAnyRole("ADMIN","TEACHER")
                .anyRequest().authenticated()

        );



        http.sessionManagement( (session)-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));



        return http.build();

    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



}


