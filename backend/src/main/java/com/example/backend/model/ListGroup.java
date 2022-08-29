package com.example.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "ListGroup")
public class ListGroup {
    public static final Long defaultId = 0L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name", length = 64, nullable = false)
    @NotBlank(message = "name cannot be empty")
    @Length(min = 5, max = 64, message = "name length must between [5, 64]")
    String name;
}
