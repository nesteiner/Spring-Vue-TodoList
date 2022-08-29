package com.example.backend.service;

import com.example.backend.model.ListGroup;
import com.example.backend.repository.ListGroupRepository;
import com.example.backend.repository.TaskListRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ListGroupService {
    @Autowired
    ListGroupRepository listGroupRepository;
    @Autowired
    TaskListRepository taskListRepository;

    public ListGroup insertOne(ListGroup listGroup) {
        return listGroupRepository.save(listGroup);
    }

    public void deleteOne(Long id) {
        listGroupRepository.deleteById(id);
        taskListRepository.findAllByGroupid(id).forEach(taskList -> {
            taskList.setGroupid(ListGroup.defaultId);
            taskListRepository.save(taskList);
        });
    }

    public ListGroup updateOne(ListGroup listGroup) {
        return listGroupRepository.save(listGroup);
    }

    public List<ListGroup> findAll() {
        return listGroupRepository.findAll();
    }

    public Optional<ListGroup> findOne(Long id) {
        return listGroupRepository.findById(id);
    }
}
