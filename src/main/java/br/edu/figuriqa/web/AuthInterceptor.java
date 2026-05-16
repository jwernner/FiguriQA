package br.edu.figuriqa.web;

import br.edu.figuriqa.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getRequestURI();
        if (path.equals("/") || path.startsWith("/login") || path.startsWith("/css/")
                || path.startsWith("/js/") || path.startsWith("/icons/") || path.equals("/manifest.json")
                || path.equals("/service-worker.js") || path.equals("/offline.html") || path.startsWith("/h2-console")) {
            return true;
        }
        User currentUser = (User) request.getSession().getAttribute("currentUser");
        if (currentUser == null) {
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }
}
