package org.example.service;

import jakarta.servlet.http.HttpSession;
import org.example.entity.Account;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthorizeService extends UserDetailsService {
    String sendValidateEmail(String email, String sessionId);
    String validateAndRegister(String username,String password,String email,String code,String sessionId);
}
