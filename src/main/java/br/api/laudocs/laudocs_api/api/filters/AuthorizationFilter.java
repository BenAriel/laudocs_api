package br.api.laudocs.laudocs_api.api.filters;

import br.api.laudocs.laudocs_api.service.AuthenticationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

public class AuthorizationFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        System.out.println("doFilter do AuthorizationFilter");

        Authentication authentication =
                AuthenticationService.getAuthentication((HttpServletRequest) request);

        if (authentication == null) {
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"message\":\"Usuário não autenticado\"}");
            response.getWriter().flush();
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
