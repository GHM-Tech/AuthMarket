package br.com.judev.ghmauthmarket.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService ;
    private final UserDetailsService userDetailsService;


    public SecurityFilter(TokenService tokenService, UserDetailsService userDetailsService) {
        this.tokenService = tokenService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        String path = request.getRequestURI();

        if (path.startsWith("/api/usuarios/register") || path.startsWith("/api/usuarios/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = this.recoverToken(request);
        System.out.println("Token recuperado: " + (token != null ? "SIM" : "NÃO"));

        if (token == null) {
            System.out.println("Token ausente - retornando 401");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

            String login = tokenService.validateToken(token);

            if (login == null || login.isEmpty()) {
                System.out.println("Token inválido - retornando 401");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

        UserDetails usuario = userDetailsService.loadUserByUsername(login);
        System.out.println("Usuário encontrado: " + usuario.getUsername());

        var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println("Autenticação configurada com sucesso");


        System.out.println("Continuando para próximo filtro...");
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}
