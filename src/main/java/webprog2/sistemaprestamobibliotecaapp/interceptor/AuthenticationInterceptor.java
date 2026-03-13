package webprog2.sistemaprestamobibliotecaapp.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getRequestURI();

        // Allow public paths (home, static resources, login/logout, error pages)
        if (isPublicPath(path)) {
            return true;
        }

        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            // User is present in session, allow the request
            return true;
        }

        log.info("Unauthenticated access to {} - redirecting to /?error=user_not_authenticated", path);
        // Redirect to home with error query
        response.sendRedirect(request.getContextPath() + "/?error=user_not_authenticated");
        return false;
    }

    private boolean isPublicPath(String path) {
        if (path == null) return true;
        return path.equals("/")
                || path.startsWith("/login")
                || path.startsWith("/css/")
                || path.startsWith("/images/");

    }
}
