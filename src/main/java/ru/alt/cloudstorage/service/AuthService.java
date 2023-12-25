package ru.alt.cloudstorage.service;

import lombok.NonNull;
import ru.alt.cloudstorage.exception.ErrorInputData;
import ru.alt.cloudstorage.repository.CloudStorageRepository;
import ru.alt.cloudstorage.repository.Users;
import ru.alt.cloudstorage.domain.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private JwtProvider jwtProvider;
    private JwtFilter jwtFilter;
    private CloudStorageRepository repository;
    private String activeToken;
    private String userLogin;

    public AuthService(JwtProvider jwtProvider, JwtFilter jwtFilter, CloudStorageRepository repository) {
        this.jwtProvider = jwtProvider;
        this.jwtFilter = jwtFilter;
        this.repository = repository;
    }

    public String getLogin(){
        return this.userLogin;
    }

//    public AuthService() {}

    public void logout() {
        repository.deactivateToken(this.activeToken);
        this.userLogin = null;

    }

    public JwtResponse login(@NonNull JwtRequest authRequest) {
        String login = authRequest.getLogin();
        final Users users = (repository.findByLoginUser(login).stream()
                .filter(us -> login.equals(us.getLogin()))
                .findFirst())
                .orElseThrow(() -> new ErrorInputData("Bad credentials"));
        final User user = new User(users);
        if (user.getPassword().equals(authRequest.getPassword())) {
            final String accessToken = jwtProvider.generateAccessToken(user);
            repository.insertToken(accessToken);
            this.activeToken = accessToken;
            this.userLogin = users.getLogin();
            return new JwtResponse(accessToken);
        } else {
            throw new ErrorInputData("Bad credentials");
        }
    }

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }

}
