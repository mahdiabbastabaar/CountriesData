package org.example.configurations.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private final RestTemplate restTemplate;

    @Value("${authentication.url}")
    private String authenticationURL;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
        throws ServletException, IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", request.getHeader("Authorization"));
        //headers.add("X-API-Key", request.getHeader("X-API-Key"));

        String userDetails = this.sendRequest(authenticationURL, headers, String.class).getBody();
        System.out.println(userDetails);
        if (userDetails != null) {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken("anonymous", null, null);

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        filterChain.doFilter(request, response);
    }

    private  <E> ResponseEntity<E> sendRequest(String url, HttpHeaders httpHeaders, Class<E> eClass) {
        HttpEntity<String> entity = new HttpEntity<>("", httpHeaders);
        ResponseEntity<E> response;
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, entity, eClass);
            return response;
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "External API doesn't answer!");
        }
    }
}
