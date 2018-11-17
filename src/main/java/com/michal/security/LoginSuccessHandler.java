package com.michal.security;

import com.michal.entities.User;
import com.michal.util.Cart;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        request.getSession().setAttribute("user", user);
        if(request.getSession().getAttribute("cart") == null){
            request.getSession().setAttribute("cart", new Cart());
        }
        response.sendRedirect(request.getContextPath());
    }
}
