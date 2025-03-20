package org.hello.controller;

import com.tvd12.ezyhttp.core.exception.HttpBadRequestException;
import com.tvd12.ezyhttp.server.core.annotation.Controller;
import com.tvd12.ezyhttp.server.core.annotation.DoPost;
import com.tvd12.ezyhttp.server.core.annotation.RequestBody;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hello.dto.request.AuthenticationRequest;
import org.hello.dto.response.AuthenticationResponse;
import org.hello.entity.User;
import org.hello.service.AuthenticationService;
import org.hello.service.UserService;

@Controller("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;
    UserService userService;

    @DoPost("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest request) {
        boolean isAuthenticated = authenticationService.authenticate(request);

        AuthenticationResponse response = new AuthenticationResponse();
        response.setAuthenticated(isAuthenticated);

        if (!isAuthenticated) {
            throw new HttpBadRequestException("Invalid username or password");
        }

        User user = userService.findByUsername(request.getUsername())
                .orElseThrow(() -> new HttpBadRequestException("User not found"));

        String accessToken = authenticationService.generateAccessToken(user.getId());
        response.setAccessToken(accessToken);
        response.setMessage("Login successful");

        return response;
    }
}