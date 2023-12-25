package ru.alt.cloudstorage.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TasksRepository extends JpaRepository<Tasks, Integer>{

    @Query("select p from Tasks p")
    List<Tasks> findAllTasks();

    @Modifying
    @Query(value = "insert into manager.TASKS (header, description, status,priority, author, executor) VALUES (:header,:description,:status,:priority,:author,:executor)", nativeQuery = true)
    @Transactional
    void insertTask(@Param("header") String header, @Param("description") String description,
                    @Param("status") String status, @Param("priority") String priority,
                    @Param("author") String author, @Param("executor") String executor);

    @Modifying
    @Transactional
    @Query("delete from Tasks p where p.header =:header and p.author = :author")
    void deleteTask(@Param("header") String header, @Param("author") String author);

    @Modifying
    @Transactional
    @Query("update Tasks p set p.description = :description where p.header=:header and p.author = :author")
    void updateTaskDescription(@Param("header") String header, @Param("description") String description, @Param("author") String author);

    @Modifying
    @Transactional
    @Query("update Tasks p set p.priority = :priority where p.header=:header and p.author = :author")
    void updateTaskPriority(@Param("header") String header, @Param("priority") String priority, @Param("author") String author);

    @Modifying
    @Transactional
    @Query("update Tasks p set p.executor = :executor where p.header=:header and p.author = :author")
    void updateTaskExecutor(@Param("header") String header, @Param("executor") String executor, @Param("author") String author);

    @Modifying
    @Transactional
    @Query("update Tasks p set p.status = :status where p.header=:header and p.author = :author")
    void updateTaskStatusAuthor(@Param("header") String header, @Param("status") String status, @Param("author") String author);

    @Modifying
    @Transactional
    @Query("update Tasks p set p.status = :status where p.header=:header and p.executor = :executor")
    void updateTaskStatusExecutor(@Param("header") String header, @Param("status") String status, @Param("executor") String executor);

}
