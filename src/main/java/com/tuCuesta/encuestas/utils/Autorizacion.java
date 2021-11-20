package com.tuCuesta.encuestas.utils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class Autorizacion implements Filter{
    
    public static final String KEY="abcabcabc";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // TODO Auto-generated method stub

        HttpServletRequest req = (HttpServletRequest) request;

        // Trae la cadena http://localhost:8080
        String url = req.getRequestURI();
        

        // Solo estas rutas son publicas 
        if(url.contains("/api/usuarios") || url.contains("/api/usuarios/login")){
            chain.doFilter(request, response);

        } else{
            String hash = req.getHeader("Authorization");
            if(hash == null || hash.trim().equals("")){
                response.setContentType("application/json");

                String body="{\"autorizacion\":\"NO ESTA AUTORIZADO PARA ACCEDER A ESTA PAGINA, DEBE HACER LOGIN PRIMERO\"}";
                response.getWriter().write(body);
            } else {

                Jws<Claims> claims = Jwts.parser().setSigningKey(KEY).parseClaimsJws(hash);
                if((url.contains("/api/encuestas") || url.contains("/api/candidatos")) && (!claims.getBody().get("username").equals(""))){
                    chain.doFilter(request, response);
                }

            }
        }
    }

}
