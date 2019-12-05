package com.crud.tasks.service;
import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DbServiceTestSuite {
    @Autowired
    private DbService dbService;
    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void testSave() {
        //Given
        Task task = new Task(1L, "test", "testing...");
        //When
        int sizeOfRepository = taskRepository.findAll().size();
        dbService.saveTask(task);
        long taskId = taskRepository.findAll().get(sizeOfRepository).getId();
        //Then
        Assert.assertEquals(sizeOfRepository + 1, taskRepository.findAll().size());
        //Clean up
        taskRepository.deleteById(taskId);
    }
    @Test
    public void testGetTask() {
        //Given
        Task task = new Task(1L, "test", "testing...");
        //When
        int sizeOfRepository = taskRepository.findAll().size();
        dbService.saveTask(task);
        long taskId = taskRepository.findAll().get(sizeOfRepository).getId();
        Optional<Task> optionalTask = dbService.getTask(taskId);
        //Then
        Assert.assertTrue(optionalTask.isPresent());
        //Clean up
        taskRepository.deleteById(taskId);
    }
    @Test
    public void testGetAllTasks() {
        //Given
        Task task = new Task(1L, "test", "testing...");
        //When
        int sizeOfRepository = taskRepository.findAll().size();
        dbService.saveTask(task);
        long taskId = taskRepository.findAll().get(sizeOfRepository).getId();
        int resultSizeOfRepository = dbService.getAllTasks().size();
        //Then
        Assert.assertEquals(sizeOfRepository + 1, resultSizeOfRepository);
        //Clean up
        taskRepository.deleteById(taskId);
    }
    @Test
    public void testDeleteTask() {
        //Given
        Task task = new Task(1L, "test", "testing...");
        //When
        int sizeOfRepository = dbService.getAllTasks().size();
        dbService.saveTask(task);
        long taskId = dbService.getAllTasks().get(sizeOfRepository).getId();
        dbService.deleteTask(taskId);
        Optional<Task> optionalTask = dbService.getTask(taskId);
        //Then
        Assert.assertFalse(optionalTask.isPresent());
    }
}
