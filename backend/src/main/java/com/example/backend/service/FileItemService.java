package com.example.backend.service;

import com.example.backend.model.FileItem;
import com.example.backend.repository.FileItemRepository;
import com.example.backend.util.Response;
import com.example.backend.util.Status;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class FileItemService {
    @Autowired
    FileItemRepository fileItemRepository;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    @Value("${file.upload.url}")
    String filepath;

    // upload
    public Tuple2<Status, FileItem> insertOne(Long taskid, MultipartFile file) {
        String name = file.getOriginalFilename();
        String format = sdf.format(new Date());
        String newname = UUID.randomUUID().toString() + format
                + name.substring(name.lastIndexOf("."), name.length());
        File newfile = new File(filepath + newname);
        FileItem fileItem = new FileItem(null, name, newname, taskid);

        try {
            file.transferTo(newfile);
            FileItem result = fileItemRepository.save(fileItem);
            return Tuple.of(Status.Ok, result);
        } catch (IOException exception) {
            log.error(exception.toString());
            return Tuple.of(Status.Err, null);
        }
    }

    public void deleteOne(Long id) {
        Optional<FileItem> optionalFileItem = fileItemRepository.findById(id);
        optionalFileItem.ifPresent(fileItem -> {
            String path = fileItem.getPath();
            File file = new File(path);
            file.delete();
        });

        fileItemRepository.deleteById(id);
    }

    public List<FileItem> findAll(Long taskid) {
        return fileItemRepository.findAllByTaskid(taskid);
    }

    public Optional<FileItem> findOne(Long id) {
        return fileItemRepository.findById(id);
    }

    // download
    public Status download(Long id, HttpServletResponse response) {
        String path = fileItemRepository.findById(id).map(fileItem -> filepath + fileItem.getPath()).orElseGet(() -> "none");
        File file = new File(path);
        if (!file.exists()) {
            log.error("file: {}", file);
            return Status.Err;
        }

        response.reset();
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int)file.length());
        response.setHeader("Content-Disposition", "attachment;filename=" + path);

        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            byte [] buffer = new byte[2048];
            OutputStream outputStream = response.getOutputStream();
            int length = bufferedInputStream.read(buffer);
            while (length != -1) {
                outputStream.write(buffer, 0, length);
                outputStream.flush();
                length = bufferedInputStream.read(buffer);
            }

            return Status.Ok;
        } catch (IOException exception) {
            log.error("{}", exception);
            return Status.Err;
        }

    }
}
