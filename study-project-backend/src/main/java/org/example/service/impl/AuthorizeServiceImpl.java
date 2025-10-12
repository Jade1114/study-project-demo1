package org.example.service.impl;

import jakarta.annotation.Resource;
import org.example.entity.Account;
import org.example.mapper.UserMapper;
import org.example.service.AuthorizeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class AuthorizeServiceImpl implements AuthorizeService {

    @Value("${spring.mail.username}")
    String from;

    @Resource
    UserMapper userMapper;

    @Resource
    MailSender  mailSender;

    @Resource
    StringRedisTemplate  stringRedisTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null) {
            throw new UsernameNotFoundException("用户名不能为空");
        }

        Account account = userMapper.findAccountByNameOrEmail(username);

        if (account == null) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        return User
                .withUsername(account.getUsername())
                .password(account.getPassword())
                .roles("user")
                .build();
    }

    @Override
    public boolean sendValidateEmail(String email, String sessionId) {
        String key = "email:" + sessionId + ":" + email;
        if(stringRedisTemplate.hasKey(key)) {
            Long expire = stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);
            if (expire > 120) return false;
        }
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(email);
        message.setSubject("验证邮件");
        message.setText("验证码是: " + code);
        try {
            mailSender.send(message);

            stringRedisTemplate.opsForValue().set(key,String.valueOf(code),3, TimeUnit.MINUTES);
            return true;
        } catch (MailException e) {
            e.printStackTrace();
            return false;
        }
    }
}
