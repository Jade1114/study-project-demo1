package org.example.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Pattern;
import org.example.entity.RestBean;
import org.example.service.AuthorizeService;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/auth")
public class AuthorizeController {

    private final String EAMIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private final String USER_REGEX = "^[a-zA-Z0-9_]{2,8}$";

    @Resource
    AuthorizeService authorizeService;

    @PostMapping("/valid-email")
    public RestBean<String> validateRegisterEmail(@Pattern(regexp = EAMIL_REGEX) @RequestParam("email") String email, HttpSession session) {
        String s = authorizeService.sendValidateEmail(email, session.getId(), false);
        if (s == null) {
            return RestBean.success("邮件已发送，请注意查收");
        } else {
            return RestBean.failure(400, s);
        }
    }

    @PostMapping("/valid-reset-email")
    public RestBean<String> validateResetEmail(@Pattern(regexp = EAMIL_REGEX) @RequestParam("email") String email, HttpSession session) {
        String s = authorizeService.sendValidateEmail(email, session.getId(), true);
        if (s == null) {
            return RestBean.success("邮件已发送，请注意查收");
        } else {
            return RestBean.failure(400, s);
        }
    }

    @PostMapping("/register")
    public RestBean<String> registerUser(@Pattern(regexp = USER_REGEX) @Length(min = 2, max = 8) @RequestParam("username") String username,
                                         @RequestParam("password") @Length(min = 6, max = 16) String password,
                                         @Pattern(regexp = EAMIL_REGEX) @RequestParam("email") String email,
                                         @Length(min = 6, max = 6) @RequestParam("code") String code,
                                         HttpSession session) {
        String s = authorizeService.validateAndRegister(username, password, email, code, session.getId());
        if (s == null) {
            return RestBean.success("注册成功!");
        } else {
            return RestBean.failure(400, s);
        }
    }

    @PostMapping("/start-reset")
    public RestBean<String> startReset(@Pattern(regexp = EAMIL_REGEX) @RequestParam("email") String email,
                                       @Length(min = 6, max = 6) @RequestParam("code") String code,
                                       HttpSession session) {
        String s = authorizeService.validateOnly(email, code, session.getId());
        if (s == null) {
            session.setAttribute("reset-password", email);
            return RestBean.success();
        } else {
            return RestBean.failure(400, s);
        }
    }

    @PostMapping("/do-reset")
    public RestBean<String> resetPassword(@RequestParam("password") @Length(min = 6, max = 16) String password,
                                          HttpSession session) {
        String email = (String) session.getAttribute("reset-password");
        if (email == null) {
            return RestBean.failure(401, "请先完成邮箱验证");
        } else if (authorizeService.resetPassword(password, email)) {
            session.removeAttribute("reset-password");
            return RestBean.success("密码重置成功");
        } else {
            return RestBean.failure(500, "内部错误，请联系管理员");
        }
    }
}
