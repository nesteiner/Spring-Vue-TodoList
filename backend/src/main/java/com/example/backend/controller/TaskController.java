package com.example.backend.controller;

import com.example.backend.model.Task;
import com.example.backend.service.TaskService;
import com.example.backend.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.nimbus.State;
import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    TaskService taskService;

    @GetMapping
    public Response<List<Task>> findAll(@RequestParam("listid") Long listid) {
        return Response.Ok("all tasks", taskService.findAll(listid));
    }

    @GetMapping("/{id}")
    public Response<Task> findOne(@PathVariable Long id) {
        return taskService.findOne(id)
                .map(task -> Response.Ok("task", task))
                .orElseGet(() -> Response.Err("no such task"));
    }

    @PostMapping
    public Response<Task> insertOne(@RequestBody Task task) {
        return Response.Ok("insert success", taskService.insertOne(task));
    }

    @DeleteMapping
    public Response<State> deleteOne(@RequestParam("id") Long id) {
        taskService.deleteOne(id);
        return Response.Err("delete success");
    }

    @PutMapping
    public Response<Task> updateOne(@RequestBody Task task) {
        return Response.Ok("update success", taskService.updateOne(task));
    }
}
