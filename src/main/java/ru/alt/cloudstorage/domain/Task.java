package ru.alt.cloudstorage.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Task {

    public String header;
    public String description;
    public String status;
    public String priority;
    public String author;
    public String executor;

    public Task(String header, String description, String status, String priority, String authorLogin, String executorLogin) {
        this.header = header;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.author = authorLogin;
        this.executor = executorLogin;
    }

    public Task() {
    }

    @Override
    public String toString() {
        return "Task{" +
                "header='" + header + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", priority='" + priority + '\'' +
                ", author=" + author +
                ", executor=" + executor +
                '}';
    }
}
