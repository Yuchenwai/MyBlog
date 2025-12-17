package com.zhoufeng.myblog.utils;

import com.zhoufeng.myblog.entity.User;
import com.zhoufeng.myblog.service.Impl.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class Permission {
    public Boolean allow() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()
                && authentication.getPrincipal() instanceof UserDetailsImpl) {
            UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
            User user = principal.getUser();
            return user.getType() != null && user.getType() == 1;
        }
        return false;
    }
}
