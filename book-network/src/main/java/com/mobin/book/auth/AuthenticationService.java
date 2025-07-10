package com.mobin.book.auth;

import com.mobin.book.email.EmailService;
import com.mobin.book.email.EmailTemplateName;
import com.mobin.book.role.RoleRepository;
import com.mobin.book.security.JwtService;
import com.mobin.book.user.Token;
import com.mobin.book.user.TokenRepository;
import com.mobin.book.user.User;
import com.mobin.book.user.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class AuthenticationService {
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    @Value("${application.mailing.frontend.activation-url}")
    private String activationUrl;

    public AuthenticationService(RoleRepository roleRepository, PasswordEncoder passwordEncoder, UserRepository userRepository, TokenRepository tokenRepository, EmailService emailService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public void register(RegistrationRequest request) throws MessagingException {
        var userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("User role not initialized"));
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setAccountLocked(false);
        user.setRoles(List.of(userRole));
        user.setEnabled(false);
        userRepository.save(user);
        sendValidationEmail(user);
    }

    private void sendValidationEmail(User user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);
        emailService.sendEmail(
                user.getEmail(),
                user.getFullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Activate activation"
        );

    }

    private String generateAndSaveActivationToken(User user) {
        // generate a token
        String generatedToken = generateActivationToken(6);
        var token = new Token();
        token.setUser(user);
        token.setToken(generatedToken);
        token.setCreatedAt(LocalDateTime.now());
        token.setExpiresAt(LocalDateTime.now().plusMinutes(15));
        tokenRepository.save(token);
        return generatedToken;
    }

    private String generateActivationToken(int length) {
        String characters = "123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        var auth= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        var claims = new HashMap<String, Object>();
        var user = ((User) auth.getPrincipal());
        claims.put("fullName", user.getFullName());
        var jwtToken = jwtService.generateToken(claims, user);
        return new AuthenticationResponse(jwtToken);
    }

    @Transactional
    public void activateAccount(String token) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));
        if (savedToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            sendValidationEmail(savedToken.getUser());
            throw new RuntimeException("Token expired, a new token has been sent to your email");
        }
        User user = userRepository.findById(savedToken.getUser().getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setEnabled(true);
        userRepository.save(user);
        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }
}
