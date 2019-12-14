package com.crud.tasks.controller;
import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TaskMapper taskMapperMock;
    @MockBean
    private DbService dbServiceMock;

    @Test
    public void getTasksTest() throws Exception {
        //Given
        List<TaskDto> tasksList = new ArrayList<>();
        tasksList.add(new TaskDto(2L, "title", "content test"));
        when(taskMapperMock.mapToTaskDtoList(dbServiceMock.getAllTasks())).thenReturn(tasksList);
        //When & Then
        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(2)))
                .andExpect(jsonPath("$[0].title", is("title")))
                .andExpect(jsonPath("$[0].content", is("content test")));
    }
    @Test
    public void getTaskTest() throws Exception {
        //Given
        TaskDto exampleTaskDto = new TaskDto(4L, "testing...", "random stuff");
        Task exampleTask = new Task(4L, "testing...", "random stuff");

        when(dbServiceMock.getTask(4L)).thenReturn(Optional.of(exampleTask));
        when(taskMapperMock.mapToTaskDto(exampleTask)).thenReturn(exampleTaskDto);

        //When & Then
        mockMvc.perform(get("/v1/task/getTask?taskId=4")
                .contentType(MediaType.APPLICATION_JSON) )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(4)))
                .andExpect(jsonPath("$.title", is("testing...")))
                .andExpect(jsonPath("$.content", is("random stuff")));
    }
    @Test
    public void deleteTaskTest() throws Exception {
        mockMvc.perform(delete("/v1/task/deleteTask?taskId=4")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(dbServiceMock).deleteTask(4L);
    }
    @Test
    public void updateTaskTest() throws Exception {
        //Given
        TaskDto exampleTaskDto = new TaskDto(4L, "testing...", "random stuff");

        when(taskMapperMock.mapToTaskDto(dbServiceMock.saveTask(ArgumentMatchers.any(Task.class)))).thenReturn(exampleTaskDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(exampleTaskDto);

        //When & Then
        mockMvc.perform(put("/v1/task/updateTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(jsonPath("$.id", is(4)))
                .andExpect(jsonPath("$.title", is("testing...")))
                .andExpect(jsonPath("$.content", is("random stuff")));
    }
    @Test
    public void createTaskTest() throws Exception {
        TaskDto exampleTaskDto = new TaskDto(4L, "testing...", "random stuff");

        Gson gson = new Gson();
        String jsonContent = gson.toJson(exampleTaskDto);

        when(taskMapperMock.mapToTaskDto(dbServiceMock.saveTask(ArgumentMatchers.any(Task.class)))).thenReturn(exampleTaskDto);

        mockMvc.perform(post("/v1/task/createTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }
}