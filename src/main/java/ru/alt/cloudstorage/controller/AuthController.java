package ru.alt.cloudstorage.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alt.cloudstorage.domain.Task;
import ru.alt.cloudstorage.repository.CloudStorageRepository;
import ru.alt.cloudstorage.service.CloudStorageService;
import ru.alt.cloudstorage.service.JwtRequest;
import ru.alt.cloudstorage.service.JwtResponse;
import ru.alt.cloudstorage.service.AuthService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/")
public class AuthController {

    private final AuthService authService;
    private CloudStorageRepository repository;
    private CloudStorageService cloudStorageService;

    public AuthController(AuthService authService, CloudStorageRepository repository, CloudStorageService cloudStorageService) {
        this.authService = authService;
        this.repository = repository;
        this.cloudStorageService = cloudStorageService;
    }

    @GetMapping("list")
    public List<Task> getList(@RequestParam("limit") Integer limit) {

        return cloudStorageService.getTasksList(limit);
    }

    @PostMapping("task")
    public ResponseEntity<?> insertTask(@RequestParam("header") String header, @RequestParam("description") String description, @RequestParam("status") String status, @RequestParam("priority") String priority, @RequestParam("executor") String executor) throws IOException {
        if(cloudStorageService.insertTask(header, description, status,priority,executor)){ return ResponseEntity.ok("Success upload");
        } else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error input data");
    }

    @DeleteMapping("task")
    public ResponseEntity<?> deleteTask(@RequestParam("header") String header) {
        cloudStorageService.deleteTask(header);
        return ResponseEntity.ok("Success deleted");
    }

    @PostMapping("description")
    public ResponseEntity<?> updateDescription(@RequestParam("header") String header, @RequestParam("description") String description) {
        cloudStorageService.updateTaskDescription(header,description);
        return ResponseEntity.ok("Success description update");
    }

    @PostMapping("priority")
    public ResponseEntity<?> updatePriority(@RequestParam("header") String header, @RequestParam("priority") String priority) {
        cloudStorageService.updateTaskPriority(header,priority);
        return ResponseEntity.ok("Success priority update");
    }

    @PostMapping("executor")
    public ResponseEntity<?> updateExecutor(@RequestParam("header") String header, @RequestParam("executor") String executor) {
        cloudStorageService.updateTaskExecutor(header,executor);
        return ResponseEntity.ok("Success executor update");
    }

    @PostMapping("statusauthor")
    public ResponseEntity<?> updateStatusAuthor(@RequestParam("header") String header, @RequestParam("status") String status) {
        cloudStorageService.updateTaskStatusAuthor(header,status);
        return ResponseEntity.ok("Success status update by author");
    }

    @PostMapping("statusexecutor")
    public ResponseEntity<?> updateStatusExecutor(@RequestParam("header") String header, @RequestParam("status") String status) {
        cloudStorageService.updateTaskStatusExecutor(header,status);
        return ResponseEntity.ok("Success status update by executor");
    }

    @PostMapping("login")
    public ResponseEntity<Map<String, String>> login(@RequestBody JwtRequest authRequest) {
        final JwtResponse token = authService.login(authRequest);
        HashMap<String, String> map = new HashMap<>();
        map.put("auth-token", token.getAccessToken());
        return ResponseEntity.ok().body(map);
    }

    @PostMapping("logout")
    public ResponseEntity<?> logout() {
        authService.logout();
        return ResponseEntity.ok("Success logout");
    }
}
