package com.example.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "FileItem")
public class FileItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name", length = 255, nullable = false)
    String name;

    @Column(name = "path", length = 255, nullable = false)
    String path;

    @Column(name = "taskid", nullable = false)
    Long taskid;
}
