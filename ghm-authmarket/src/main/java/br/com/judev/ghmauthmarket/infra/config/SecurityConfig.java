package br.com.judev.ghmauthmarket.infra.config;

import br.com.judev.ghmauthmarket.infra.security.SecurityFilter;
import br.com.judev.ghmauthmarket.infra.security.TokenService;
import br.com.judev.ghmauthmarket.repository.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    private final UsuarioRepository usuarioRepository;
    private final TokenService tokenService;

    public SecurityConfig(UsuarioRepository usuarioRepository, TokenService tokenService) {
        this.usuarioRepository = usuarioRepository;
        this.tokenService = tokenService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/usuarios/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/usuarios/login").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/usuarios/atualiza-senha").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/usuarios/**").authenticated()

                        .requestMatchers(HttpMethod.POST,"/api/produtos/register").authenticated()
                        .requestMatchers(HttpMethod.PUT,"/api/produtos/{id}").authenticated()
                        .requestMatchers(HttpMethod.GET,"/api/produtos/").authenticated()

                        .requestMatchers(HttpMethod.POST,"/api/pedidos/register").authenticated()
                        .requestMatchers(HttpMethod.DELETE,"/api/pedidos/{id}").authenticated()
                        .requestMatchers(HttpMethod.GET,"/api/pedidos/{idPedido}/produtos/{idProduto}").authenticated()
                        .requestMatchers(HttpMethod.GET,"/api/pedidos/{id}").authenticated()

                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public SecurityFilter securityFilter() {
        return new SecurityFilter(tokenService, userDetailsService());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception{
        return authConfig.getAuthenticationManager();
    }
    @Bean
    public UserDetailsService userDetailsService(){
        return username -> (UserDetails) usuarioRepository.findByEmail(username)
                .orElseThrow(()-> new UsernameNotFoundException("Usuario Não encontrado"));
    }
}
