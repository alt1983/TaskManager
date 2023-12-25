package ru.alt.cloudstorage.repository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class CloudStorageRepository {

    private final UsersRepository usersRepository;
    private final TokensRepository tokensRepository;
    private final TasksRepository tasksRepository;
    private Map<String, Boolean> tokens;

    public CloudStorageRepository(UsersRepository usersRepository, TokensRepository tokensRepository, TasksRepository tasksRepository) {
        this.usersRepository = usersRepository;
        this.tokensRepository = tokensRepository;
        this.tasksRepository = tasksRepository;
    }

    public List<Tasks> findAllTasks() {
        return tasksRepository.findAllTasks();
    }

    public boolean insertTask(String header, String description, String status, String priority, String author, String executor) {
        tasksRepository.insertTask(header, description, status, priority, author, executor);
        return true;
    }

    public void deleteTask(String header, String author) {
        tasksRepository.deleteTask(header, author);
    }

    public void updateTaskDescription(String header, String description, String author){
        tasksRepository.updateTaskDescription(header, description, author);
    }

    public void updateTaskPriority(String header, String priority, String author){
        tasksRepository.updateTaskPriority(header, priority, author);
    }

    public void updateTaskExecutor(String header, String executor, String author){
        tasksRepository.updateTaskExecutor(header, executor, author);
    }

    public void updateTaskStatusAuthor(String header, String status, String author){
        tasksRepository.updateTaskStatusAuthor(header, status, author);
    }

    public void updateTaskStatusExecutor(String header, String status, String executor){
        tasksRepository.updateTaskStatusExecutor(header, status, executor);
    }

    public List<Tokens> findAllTokens() {
        return tokensRepository.findAllTokens();
    }

    public List<Tokens> findByToken(String token) {
        return tokensRepository.findByToken(token);
    }

    public void deactivateToken(String token) {
        tokensRepository.deactivateToken(token);
    }

    public void insertToken(String token) {
        tokensRepository.insertToken(token);
    }

    public List<Users> findAllUsers() {
        return usersRepository.findAllUsers();
    }

    public List<Users> findByLoginUser(String login) {
        return usersRepository.findByLogin(login);
    }

    public List<Users> findByLoginPasswordUser(String login, String password) {
        return usersRepository.findByLoginPassword(login, password);
    }

    public void deactivateUser(String login) {
        usersRepository.deactivateUser(login);
    }
    public void setTokens(Map<String, Boolean> tokens) {
        this.tokens = tokens;
    }
}
