package com.example.backend.service;

import com.example.backend.model.TaskList;
import com.example.backend.repository.TaskListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskListService {
    @Autowired
    TaskListRepository taskListRepository;

    public TaskList insertOne(TaskList taskList) {
        return taskListRepository.save(taskList);
    }

    public void deleteOne(Long id) {
        taskListRepository.deleteById(id);
    }

    public TaskList updateOne(TaskList taskList) {
        return taskListRepository.save(taskList);
    }

    public List<TaskList> findAll(Long groupid) {
        return taskListRepository.findAllByGroupid(groupid);
    }

    public Optional<TaskList> findOne(Long id) {
        return taskListRepository.findById(id);
    }
}
