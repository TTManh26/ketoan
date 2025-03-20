package org.hello.controller;

import com.tvd12.ezyhttp.server.core.annotation.*;
import com.tvd12.ezyhttp.server.core.view.Redirect;
import com.tvd12.ezyhttp.server.core.view.View;
import lombok.AllArgsConstructor;
import org.hello.dto.request.AuthenticationRequest;
import org.hello.entity.User;
import org.hello.service.AuthenticationService;
import org.hello.service.UserService;

import javax.servlet.http.Cookie;

@AllArgsConstructor
@Controller
public class LoginController {
    private AuthenticationService authenticationService;
    private UserService userService;



    @DoGet("/login")
    public View loginGet(
            @RequestParam(value = "lang", defaultValue = "en") String language,
            @RequestParam(value = "lang", defaultValue = "") String langParam,
            @RequestParam(value = "error", defaultValue = "") String error) {

        String selectedLang = langParam.isEmpty() ? language : langParam;

        return View.builder()
                .locale(selectedLang)
                .template("user-login")
                .addVariable("lang",selectedLang)
                .addVariable("error", !error.isEmpty())
                .addCookie("language", selectedLang)
                .build();
    }

    @DoPost("/login")
    public Redirect loginPost(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam(value = "lang", defaultValue = "en") String language) {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername(username);
        request.setPassword(password);

        try {
            boolean isAuthenticated = authenticationService.authenticate(request);
            if (isAuthenticated) {
                User user = userService.findByUsername(username)
                        .orElseThrow(() -> new RuntimeException("User not found"));

                String accessToken = authenticationService.generateAccessToken(user.getId());
                Cookie cookie = new Cookie("accessToken", accessToken);
                cookie.setPath("/");
                cookie.setMaxAge(24*60*60);
                return Redirect.builder()
                        .addCookie(cookie)
                        .addCookie("language", language)
                        .uri("/home")
                        .build();
            } else {
                return Redirect.builder()
                        .addCookie("language", language)
                        .uri("/login?lang=" + language + "&error=1")
                        .build();
            }
        } catch (Exception e) {
            return Redirect.builder()
                    .addCookie("language", language)
                    .uri("/login?lang=" + language + "&error=1")
                    .build();
        }
    }

    @DoPost("/logout")
    public Redirect logout(
            @RequestParam(value = "lang", defaultValue = "en") String language,
            @RequestCookie("accessToken") String accessToken) {
        if (accessToken != null) {
            authenticationService.removeAccessToken(accessToken);
        }
        return Redirect.builder()
                .addCookie("accessToken", "")
                .addCookie("language", language)
                .uri("/login?lang=" + (language != null ? language : "en"))
                .build();
    }
}