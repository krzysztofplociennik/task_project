package com.crud.tasks.controller;
import com.crud.tasks.domain.TaskDto;
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
    private TaskController taskController;

    @Test
    public void getTasksTest() throws Exception {
        //Given
        List<TaskDto> tasksList = new ArrayList<>();
        tasksList.add(new TaskDto(2L, "title", "content test"));
        when(taskController.getTasks()).thenReturn(tasksList);
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

        when(taskController.getTask(4L)).thenReturn(exampleTaskDto);
        //When & Then
        mockMvc.perform(get("/v1/task/getTask?taskId=4")
                .contentType(MediaType.APPLICATION_JSON) )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(4)))
                .andExpect(jsonPath("$.title", is("testing...")))
                .andExpect(jsonPath("$.content", is("random stuff")))
                .andDo(print());
    }
    @Test
    public void deleteTaskTest() throws Exception {
        mockMvc.perform(delete("/v1/task/deleteTask?taskId=4")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        verify(taskController).deleteTask(4L);
    }
    @Test
    public void updateTaskTest() throws Exception {
        //Given
        TaskDto exampleTaskDto = new TaskDto(4L, "testing...", "random stuff");

        when(taskController.updateTask(ArgumentMatchers.any(TaskDto.class))).thenReturn(exampleTaskDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(exampleTaskDto);

        //When & Then
        mockMvc.perform(put("/v1/task/updateTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(jsonPath("$.id", is(4)))
                .andExpect(jsonPath("$.title", is("testing...")))
                .andExpect(jsonPath("$.content", is("random stuff")))
                .andDo(print());
    }
    @Test
    public void createTaskTest() throws Exception {
        TaskDto exampleTaskDto = new TaskDto(4L, "testing...", "random stuff");

        Gson gson = new Gson();
        String jsonContent = gson.toJson(exampleTaskDto);

        mockMvc.perform(post("/v1/task/createTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andDo(print());

        verify(taskController).createTask(ArgumentMatchers.any(TaskDto.class));
    }
}