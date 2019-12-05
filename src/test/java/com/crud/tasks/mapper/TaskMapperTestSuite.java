package com.crud.tasks.mapper;
import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskMapperTestSuite {
    @Autowired
    private TaskMapper taskMapper;

    @Test
    public void mappingToTaskTest() {
        //Given
        TaskDto taskDto = new TaskDto(3L, "test task", "descriptions");
        //When
        Task resultTask = taskMapper.mapToTask(taskDto);
        //Then
        Assert.assertEquals(taskDto.getId(), resultTask.getId());
        Assert.assertEquals(taskDto.getTitle(), resultTask.getTitle());
        Assert.assertEquals(taskDto.getContent(), resultTask.getContent());
    }
    @Test
    public void mappingToTaskDtoTest() {
        //Given
        Task task = new Task(5L, "testing...", "desc");
        //When
        TaskDto resultTaskDto = taskMapper.mapToTaskDto(task);
        //Then
        Assert.assertEquals(task.getId(), resultTaskDto.getId());
        Assert.assertEquals(task.getTitle(), resultTaskDto.getTitle());
        Assert.assertEquals(task.getContent(), resultTaskDto.getContent());

    }
    @Test
    public void mappingToTaskDtoListTest() {
        //Given
        List<Task> theList = new ArrayList<>();
        theList.add(new Task(6L, "tests", "some tests"));
        theList.add(new Task(22L, "random test", "..."));
        theList.add(new Task(3L, "test", ""));
        //When
        List<TaskDto> resultTaskDtoList = taskMapper.mapToTaskDtoList(theList);
        //Then
        Assert.assertEquals(3, resultTaskDtoList.size());
        Assert.assertEquals(theList.size(), resultTaskDtoList.size());
        Assert.assertEquals(theList.get(0).getId(), resultTaskDtoList.get(0).getId());
        Assert.assertEquals(theList.get(1).getTitle(), resultTaskDtoList.get(1).getTitle());
        Assert.assertEquals(theList.get(2).getContent(), resultTaskDtoList.get(2).getContent());

    }
}
