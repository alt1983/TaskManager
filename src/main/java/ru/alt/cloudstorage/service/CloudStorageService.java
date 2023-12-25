package ru.alt.cloudstorage.service;

import org.springframework.stereotype.Service;
import ru.alt.cloudstorage.domain.Task;
import ru.alt.cloudstorage.exception.ErrorInputData;
import ru.alt.cloudstorage.repository.CloudStorageRepository;
import ru.alt.cloudstorage.exception.ErrorInternal;
import ru.alt.cloudstorage.repository.Tasks;

import java.util.ArrayList;
import java.util.List;

@Service
public class CloudStorageService {
    private AuthService authService;
    private CloudStorageRepository repository;

    public CloudStorageService(AuthService authService, CloudStorageRepository repository) {
        this.authService = authService;
        this.repository = repository;
    }

    public List<Task> getTasksList(Integer limit) {

        if(limit <= 0) throw new ErrorInputData("Error amount of tasks");
        List<Task> tasksList = new ArrayList<>();
        List<Tasks> tasks = repository.findAllTasks();
        int lim = limit;
        for (Tasks task : tasks) {
            if(lim > 0) {
                tasksList.add(new Task(task.getHeader(), task.getDescription(), task.getStatus(),task.getPriority(),task.getAuthor(),task.getExecutor()));
                lim--;
            }
        }
        if (tasksList.isEmpty()) throw new ErrorInternal("Error getting task list");
        if (tasksList.isEmpty()) throw new ErrorInternal("Error getting task list");
        return tasksList;

    }

    public boolean insertTask(String header, String description, String status, String priority, String executor) {
        if ((header == null)||(description == null)||(header == status)||(header == priority)||(executor == null)) throw new ErrorInputData("Error input data");
        return repository.insertTask(header,description,status,priority,executor,authService.getLogin());
    }

    public void deleteTask(String header) {
        if (header == null) throw new ErrorInputData("Error input data");
        repository.deleteTask(header, authService.getLogin());
    }

    public void updateTaskDescription(String header, String description) {
        if ((header == null)||(description == null)) throw new ErrorInputData("Error input data");
        repository.updateTaskDescription(header, description, authService.getLogin());
    }

    public void updateTaskPriority(String header, String priority) {
        if ((header == null)||(priority == null)) throw new ErrorInputData("Error input data");
        repository.updateTaskPriority(header, priority, authService.getLogin());
    }

    public void updateTaskExecutor(String header, String executor) {
        if ((header == null)||(executor == null)) throw new ErrorInputData("Error input data");
        repository.updateTaskExecutor(header, executor, authService.getLogin());
    }

    public void updateTaskStatusAuthor(String header, String status) {
        if ((header == null)||(status == null)) throw new ErrorInputData("Error input data");
        repository.updateTaskStatusAuthor(header, status, authService.getLogin());
    }

    public void updateTaskStatusExecutor(String header, String status) {
        if ((header == null)||(status == null)) throw new ErrorInputData("Error input data");
        repository.updateTaskStatusExecutor(header, status, authService.getLogin());
    }
}
