package com.example.backend.service;

import com.example.backend.model.Task;
import com.example.backend.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;

    public Task insertOne(Task task) {
        return taskRepository.save(task);
    }

    public void deleteOne(Long id) {
        taskRepository.deleteById(id);
    }

    public Task updateOne(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> findAll(Long listid) {
        return taskRepository.findAllByListid(listid);
    }

    public Optional<Task> findOne(Long id) {
        return taskRepository.findById(id);
    }
}
