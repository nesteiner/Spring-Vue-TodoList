package com.example.backend.controller;

import com.example.backend.model.FileItem;
import com.example.backend.service.FileItemService;
import com.example.backend.util.Response;
import com.example.backend.util.Status;
import io.vavr.Tuple2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/fileitem")
public class FileItemController {
    @Autowired
    FileItemService fileItemService;

    @GetMapping
    public Response<List<FileItem>> findAll(@RequestParam("taskid") Long taskid) {
        return Response.Ok("all fileitem", fileItemService.findAll(taskid));
    }

    @GetMapping("/{id}")
    public Response<Status> findOne(@PathVariable Long id, HttpServletResponse response) {
//        return Response.Ok("download success", fileItemService.download(id, response));
        Status result = fileItemService.download(id, response);
        if(result == Status.Ok) {
            return Response.Ok("download success", Status.Ok);
        } else {
            return Response.Err("download failed");
        }
    }

    @PostMapping
    public Response<FileItem> insertOne(@RequestParam("taskid") Long taskid, @RequestParam("file") MultipartFile file) {
        Tuple2<Status, FileItem> result = fileItemService.insertOne(taskid, file);
        if(result._1 == Status.Ok) {
            return Response.Ok("insert success", result._2);
        } else {
            return Response.Err("insert failed");
        }
    }

    @DeleteMapping
    public Response<Status> deleteOne(@RequestParam("id") Long id) {
        fileItemService.deleteOne(id);
        return Response.Ok("delete success", Status.Ok);
    }

}
