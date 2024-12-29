
//package com.zosh.config;
//
//import java.awt.RenderingHints.Key;
//import java.io.IOException;
//import java.util.List;
//
//import javax.crypto.SecretKey;
//
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.authority.GrantedAuthoritiesContainer;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//public class JwtValidator extends OncePerRequestFilter {
//
//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//		
//		String jwt = request.getHeader(JwtConstant.JWT_HEADER);
//		System.out.println("jwt ------ "+jwt);
//		if(jwt!=null) {
//			jwt=jwt.substring(7);
//			System.out.println("jwt ------ "+jwt);
//			try {
//				
//				SecretKey key= Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
//				
//				Claims claims=Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
//				
//				String email=String.valueOf(claims.get("email"));
//				
//				String authorities=String.valueOf(claims.get("authorities"));
//				
//				List<GrantedAuthority> auths=AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
//				Authentication athentication=new UsernamePasswordAuthenticationToken(email,null, auths);
//				
//				SecurityContextHolder.getContext().setAuthentication(athentication);
//				
//			} catch (Exception e) {
//				// TODO: handle exception
//				throw new BadCredentialsException("invalid token...");
//			}
//		}
//		filterChain.doFilter(request, response);
//		
//	}
//
//}










package com.zosh.config;

import java.io.IOException;
import java.util.List;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;




public class JwtValidator extends OncePerRequestFilter{

//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//		
//		String jwt=request.getHeader("JwtConstant.JWT_HEADER");
//		
//		if(jwt!=null) {
//			jwt=jwt.substring(7);
//			try {
//				SecretKey key=Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
//				
//				Claims claims=Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJwt(jwt).getBody();
//				
//				String email=String.valueOf(claims.get("email"));
//				
//				String authorities=String.valueOf(claims.get("authorities"));
//				
//				List<GrantedAuthority> auths=AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
//				
//				Authentication authentication=new UsernamePasswordAuthenticationToken(email,null, auths);
//				
//				SecurityContextHolder.getContext().setAuthentication(authentication);
//				
//			}catch(Exception e) {
//				
//				throw new BadCredentialsException("invalid token...from jwt validator");
//				
//			}
//		}
//		
//		filterChain.doFilter(request, response);;
//		
//	}
	
	@Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String jwt = request.getHeader(JwtConstant.JWT_HEADER);

        if (jwt != null) {
            jwt = jwt.substring(7); // Remove "Bearer " prefix

            try {
                SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

                Claims claims = Jwts.parserBuilder()
                                    .setSigningKey(key)
                                    .build()
                                    .parseClaimsJws(jwt)
                                    .getBody();

                String email = String.valueOf(claims.get("email"));
                String authorities = String.valueOf(claims.get("authorities"));

                List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);

                Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, auths);
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e) {
                throw new BadCredentialsException("Invalid token", e);
            }
        }

        filterChain.doFilter(request, response);
    }
	
	

}
