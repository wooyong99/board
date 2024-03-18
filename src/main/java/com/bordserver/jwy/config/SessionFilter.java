package com.bordserver.jwy.config;

import com.bordserver.jwy.utils.SessionUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class SessionFilter extends BasicAuthenticationFilter {
    private SessionProvider sessionProvider;

    public SessionFilter(AuthenticationManager authenticationManager, SessionProvider sessionProvider) {
        super(authenticationManager);
        this.sessionProvider = sessionProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpSession session = request.getSession();
        String account = SessionUtil.getLoginMemberId(session);
        if (account == null) {
            account = SessionUtil.getLoginAdminId(session);
        }
        if (account != null) {
            Authentication authentication = sessionProvider.getAuthentication(account);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }
}
