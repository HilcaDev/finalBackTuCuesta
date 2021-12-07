package com.tuCuesta.encuestas.utils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;

//prueba
@Component 
public class Autorizacion implements Filter{
    
    public static final String KEY="abcabcabc";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setHeader("Access-Control-Allow-Origin","*");
        resp.setHeader("Access-Control-Allow-Headers","Authorization,Content-Type");
        resp.setHeader("Access-Control-Allow-Methods","GET,POST");


        // Trae la cadena http://localhost:8080
        String url = req.getRequestURI();

        if(url.contains("/api/usuarios") || url.contains("/api/usuarios/login")){
            chain.doFilter(request, response); // Solo estas rutas son publicas 
        
        } else{
            
            String hash=req.getHeader("Authorization");
            if(hash==null || hash.trim().equals("")){
                response.setContentType("application/json");
                String body="{\"autorizacion\":\"Denegada. Realizar login para poder acceder a esta pagina\"}";
                response.getWriter().write(body);

            } try {
                Jws<Claims> claims = Jwts.parser().setSigningKey(KEY).parseClaimsJws(hash);
                if((url.contains("/api/verificar")) || (url.contains("/api/encuestas") || url.contains("/api/candidatos")) && (!claims.getBody().get("username").equals(""))){
                chain.doFilter(request, response);
                }

            } catch (MalformedJwtException e) {
                response.setContentType("application/json");
                String body="{\"autorizacion\":\"Token inválido\"}";
                response.getWriter().write(body);

            } catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

}
