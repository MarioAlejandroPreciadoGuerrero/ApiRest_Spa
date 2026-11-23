package com.example.ApiRest_Spa.Security;
import com.example.ApiRest_Spa.dto.ErrorDTO;
import com.example.ApiRest_Spa.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tools.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Boolean isPublicRoute (HttpServletRequest request) {
        String method = request.getMethod();
        String path = request.getServletPath();

        if (method.equalsIgnoreCase("OPTIONS")) return true;

        if(path.equals("/api/auth/login") && method.equals("POST")) return true;

        if(path.equals("/api/usuario") && method.equals("POST")) return true;

        if(path.equals("/api/servicios") && method.equals("GET")) return true;

        if(path.equals("/api/sucursales") && method.equals("GET")) return true;

        if(path.equals("/api/terapeutas") && method.equals("GET")) return true;

        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {
        if (isPublicRoute(request)) {
            chain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            sendUnauthorized(response, "Se requiere token de autenticación");
            return;
        }

        String token = authHeader.substring(7);

        if(!jwtService.validateToken(token)) {
            sendUnauthorized(response, "Token inválido o expirado");
            return;
        }

        String username = jwtService.extractUsername(token);
        request.setAttribute("username", username);

        //crea un objeto de autenticación spring security
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());

        //despues de crear el objeto de autenticación en el contexto le pone esa autenticacion creada
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //ahora se pone identidad pero no permisos
        chain.doFilter(request, response);
    }

    private void sendUnauthorized(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ErrorDTO errorDTO = new ErrorDTO(
                "No autorizado",
                List.of(message),
                LocalDateTime.now()
        );

        response.getWriter().write(objectMapper.writeValueAsString(errorDTO));
    }

}
