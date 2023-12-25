package ru.alt.cloudstorage;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import ru.alt.cloudstorage.domain.Task;
import ru.alt.cloudstorage.repository.Tasks;
import ru.alt.cloudstorage.service.*;
import ru.alt.cloudstorage.controller.AuthController;
import ru.alt.cloudstorage.domain.Role;
import ru.alt.cloudstorage.exception.ErrorInputData;
import ru.alt.cloudstorage.repository.CloudStorageRepository;
import ru.alt.cloudstorage.repository.Users;
import org.springframework.http.ResponseEntity;
import ru.alt.cloudstorage.domain.User;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@SpringBootTest
class CloudStorageApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void getLoginDataTest() {
        Users user = new Users();
        user.setLogin("user1");
        user.setPassword("user1");
        user.setRole("USER");
        User us = new User("user1", "user1", "user1", "user1", Collections.singleton(Role.USER));
        JwtRequest authRequest = Mockito.mock(JwtRequest.class);
        Mockito.when(authRequest.getLogin()).thenReturn(us.getLogin());
        Mockito.when(authRequest.getPassword()).thenReturn(us.getPassword());
        CloudStorageRepository repository = Mockito.mock(CloudStorageRepository.class);
        Mockito.when(repository.findByLoginUser(user.getLogin())).thenReturn(Arrays.asList(user));
        JwtProvider jwtProvider = Mockito.mock(JwtProvider.class);
        Mockito.when(jwtProvider.generateAccessToken(us)).thenReturn("test");
        AuthService authService = new AuthService(jwtProvider, new JwtFilter(jwtProvider), repository);
        CloudStorageService cloudStorageService = new CloudStorageService(authService, repository);
        AuthController authController = new AuthController(authService, repository, cloudStorageService);
        Assert.assertNotNull(authController.login(authRequest));
        Assert.assertEquals(authController.login(authRequest).getClass(), ResponseEntity.class);

    }

    @Test
    void getLoginErrorInputData() {
        boolean errorInputData = false;
        Users user = new Users();
        user.setLogin("user1");
        user.setPassword("user1");
        user.setRole("USER");
        User us = new User("user1", "test", "user1", "user1", Collections.singleton(Role.USER));
        JwtRequest authRequest = Mockito.mock(JwtRequest.class);
        Mockito.when(authRequest.getLogin()).thenReturn(us.getLogin());
        Mockito.when(authRequest.getPassword()).thenReturn(us.getPassword());
        CloudStorageRepository repository = Mockito.mock(CloudStorageRepository.class);
        Mockito.when(repository.findByLoginUser(user.getLogin())).thenReturn(Arrays.asList(user));
        JwtProvider jwtProvider = Mockito.mock(JwtProvider.class);
        Mockito.when(jwtProvider.generateAccessToken(us)).thenReturn("test");
        AuthService authService = new AuthService(jwtProvider, new JwtFilter(jwtProvider), repository);
        CloudStorageService cloudStorageService = new CloudStorageService(authService, repository);
        AuthController authController = new AuthController(authService, repository, cloudStorageService);

        try {
            ResponseEntity<?> res = authController.login(authRequest);
        } catch (ErrorInputData e) {
            errorInputData = true;
            System.out.println("true");
        }
        Assert.assertTrue(errorInputData);
    }

    @Test
    void getListDataTest() {
        Users user = new Users();
        user.setLogin("user1");
        user.setPassword("user1");
        user.setRole("USER");
        Integer limit = 2;
        User us = new User("user1", "user1", "user1", "user1", Collections.singleton(Role.USER));
        JwtRequest authRequest = Mockito.mock(JwtRequest.class);
        List<Tasks> tasks = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            tasks.add(new Tasks(i, String.valueOf(i), "description","active","medium",us.getLogin(), us.getLogin()));
        }
        CloudStorageRepository repository = Mockito.mock(CloudStorageRepository.class);
        Mockito.when(repository.findAllTasks()).thenReturn(tasks);

        JwtProvider jwtProvider = Mockito.mock(JwtProvider.class);
        Mockito.when(jwtProvider.generateAccessToken(us)).thenReturn("test");
        AuthService authService = new AuthService(jwtProvider, new JwtFilter(jwtProvider), repository);
        CloudStorageService cloudStorageService = new CloudStorageService(authService, repository);
        AuthController authController = new AuthController(authService, repository, cloudStorageService);
        Assert.assertNotNull(authController.getList(limit));
        Assert.assertEquals(authController.getList(limit).getClass(), ArrayList.class);
    }

    @Test
    void getListErrorInputData() {
        boolean errorInputData = false;
        Users user = new Users();
        user.setLogin("user1");
        user.setPassword("user1");
        user.setRole("USER");
        Integer limit = 0;
        User us = new User("user1", "user1", "user1", "user1", Collections.singleton(Role.USER));
        JwtRequest authRequest = Mockito.mock(JwtRequest.class);

        List<Tasks> tasks = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            tasks.add(new Tasks(i, String.valueOf(i), "description","active","medium",us.getLogin(), us.getLogin()));
        }

        CloudStorageRepository repository = Mockito.mock(CloudStorageRepository.class);
        Mockito.when(repository.findAllTasks()).thenReturn(tasks);

        JwtProvider jwtProvider = Mockito.mock(JwtProvider.class);
        Mockito.when(jwtProvider.generateAccessToken(us)).thenReturn("test");
        AuthService authService = new AuthService(jwtProvider, new JwtFilter(jwtProvider), repository);
        CloudStorageService cloudStorageService = new CloudStorageService(authService, repository);
        AuthController authController = new AuthController(authService, repository, cloudStorageService);

        try {
            List<Task> fileInfo = authController.getList(limit);
        } catch (ErrorInputData e) {
            errorInputData = true;
        }
        Assert.assertTrue(errorInputData);
    }

}
