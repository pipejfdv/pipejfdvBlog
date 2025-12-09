package com.pipejfdv.MCSAuth.MCSAuth.Config;

import com.pipejfdv.MCSAuth.MCSAuth.Components.JwtUtil;
import com.pipejfdv.MCSAuth.MCSAuth.Models.Model.AuthToken;
import com.pipejfdv.MCSAuth.MCSAuth.Models.ModelsDTO.UserPassDTO;
import com.pipejfdv.MCSAuth.MCSAuth.Models.Repositories.AuthTokenRepository;
import com.pipejfdv.MCSAuth.MCSAuth.Models.Services.MCSUsersFMServices;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
// each request is necessary apply this validation
public class JwtAuth extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final AuthTokenRepository authTokenRepository;
    private final UserDetailsService userDetailsService;
    private final MCSUsersFMServices mcsUsersFMServices;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        /*
        * These routes are free, which do not validate the token
        */
        String path = request.getServletPath();
        if(path.startsWith("/auth")|| path.startsWith("/index")){
            filterChain.doFilter(request,response);
            return;
        }
        /*
        * If the Authorization header does not exist or does not start with Bearer,
        * then I do not attempt to validate the token and let the request through.
        */
        final  String authorization = request.getHeader("Authorization");
        if(authorization == null || !authorization.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }

        /*
        * if User don't exist and null continue with validation
        */
        final String token = authorization.substring(7);
        final String username = jwtUtil.extractUsernameForToken(token);
        if(username == null || SecurityContextHolder.getContext().getAuthentication() != null){
            return;
        }
        /*
        * search in AuthDB if token is not Expired, revoked or null
        */
        final AuthToken authToken = authTokenRepository.findByToken(token);
        if(authToken == null || authToken.isExpired() || authToken.isRevoked()){
            filterChain.doFilter(request,response);
            return;
        }
        /*
        * validate if token is validate
        */
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final UserPassDTO user = mcsUsersFMServices.getCredentialsUser(userDetails.getUsername());
        final boolean isTokenValid = jwtUtil.isTokenValid(token, user);
        if(!isTokenValid){
            return;
        }
        /*
        * Permissions are assigned to the user since they passed all the filters,
        * and this information is assigned to them in the Spring context.
        */
        final var userValid = new UsernamePasswordAuthenticationToken(
            userDetails,
                null,
                userDetails.getAuthorities()
        );
        userValid.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(userValid);
        filterChain.doFilter(request,response);
    }
}
