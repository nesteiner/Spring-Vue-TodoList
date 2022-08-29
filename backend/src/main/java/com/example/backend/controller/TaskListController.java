package com.example.backend.controller;

import com.example.backend.model.TaskList;
import com.example.backend.service.TaskListService;
import com.example.backend.util.Response;
import com.example.backend.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasklist")
public class TaskListController {
    @Autowired
    TaskListService taskListService;

    @GetMapping
    public Response<List<TaskList>> findAll(@RequestParam("groupid") Long groupid) {
        return Response.Ok("all tasklist", taskListService.findAll(groupid));
    }

    @GetMapping("/{id}")
    public Response<TaskList> findOne(@PathVariable Long id) {
        return taskListService.findOne(id)
                .map(taskList -> Response.Ok("tasklist", taskList))
                .orElseGet(() -> Response.Err("no such tasklist"));
    }

    @PostMapping
    public Response<TaskList> insertOne(@RequestBody TaskList taskList) {
        TaskList result = taskListService.insertOne(taskList);
        return Response.Ok("insert success", result);
    }

    @DeleteMapping
    public Response<Status> deleteOne(@RequestParam("id") Long id) {
        taskListService.deleteOne(id);
        return Response.Ok("delete success", Status.Ok);
    }

    @PutMapping
    public Response<TaskList> updateOne(@RequestBody TaskList taskList) {
        TaskList result = taskListService.updateOne(taskList);
        return Response.Ok("update success", result);
    }
}
