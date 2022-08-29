package com.example.backend.repository;

import com.example.backend.model.FileItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileItemRepository extends JpaRepository<FileItem, Long> {
    List<FileItem> findAllByTaskid(Long taskid);
}
