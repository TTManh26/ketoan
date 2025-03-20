package org.hello.service;

import com.tvd12.ezyfox.security.BCrypt;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hello.dto.request.AuthenticationRequest;
import org.hello.entity.AccessToken;
import org.hello.entity.User;
import org.hello.repository.AccessTokenRepository;
import org.hello.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;
    AccessTokenRepository accessTokenRepository;
    public boolean authenticate(AuthenticationRequest request){
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String hashedPassword = user.getPassword();
        return BCrypt.checkpw(request.getPassword(), hashedPassword);
    }

    public String generateAccessToken(int userId) {
        String tokenValue = UUID.randomUUID().toString();
        AccessToken accessToken = new AccessToken();
        accessToken.setAccessToken(tokenValue);
        accessToken.setUserId(userId);
        accessToken.setIssuedAt(LocalDateTime.now());
        accessToken.setExpiresAt(LocalDateTime.now().plusHours(24));
        accessTokenRepository.save(accessToken);
        return tokenValue;
    }

    public AccessToken validateAccessToken(String tokenValue) {
        AccessToken accessToken = accessTokenRepository.findByAccessToken(tokenValue);
        if (accessToken == null) {
            throw new RuntimeException("Token not found");
        }
        if (LocalDateTime.now().isAfter(accessToken.getExpiresAt())) {
            throw new RuntimeException("Token has expired");
        }
        return accessToken;
    }

    public Integer verifyAccessToken(String tokenValue) {
        try {
            AccessToken accessToken = validateAccessToken(tokenValue);
            return accessToken.getUserId();
        } catch (RuntimeException e) {
            return null;
        }
    }

    public void removeAccessToken(String tokenValue) {
        accessTokenRepository.delete(tokenValue);
    }

    public int getUserIdFromToken(String tokenValue) {
        AccessToken accessToken = validateAccessToken(tokenValue);
        return accessToken.getUserId();
    }
}