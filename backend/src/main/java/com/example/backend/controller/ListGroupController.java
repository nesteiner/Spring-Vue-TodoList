package com.example.backend.controller;

import com.example.backend.model.ListGroup;
import com.example.backend.service.ListGroupService;
import com.example.backend.util.Response;
import com.example.backend.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/listgroup")
public class ListGroupController {
    @Autowired
    ListGroupService listGroupService;

    @GetMapping
    public Response<List<ListGroup>> findAll() {
        return Response.Ok("find all listgroups", listGroupService.findAll());
    }

    @GetMapping("/{id}")
    public Response<ListGroup> findOne(@PathVariable Long id) {
        Optional<ListGroup> optionalListGroup = listGroupService.findOne(id);
        return optionalListGroup
                .map(listGroup -> Response.Ok("listgroup", listGroup))
                .orElseGet(() -> Response.Err("no such listgroup"));
    }

    @PostMapping
    public Response<ListGroup> insertOne(@RequestBody ListGroup listGroup) {
        return Response.Ok("insert success", listGroupService.insertOne(listGroup));
    }

    @DeleteMapping
    public Response<Status> deleteOne(@RequestParam("id") Long id) {
        listGroupService.deleteOne(id);
        return Response.Ok("delete success", Status.Ok);
    }

    @PutMapping
    public Response<ListGroup> updateOne(@RequestBody ListGroup listGroup) {
        return Response.Ok("update succes", listGroupService.updateOne(listGroup));
    }
}
