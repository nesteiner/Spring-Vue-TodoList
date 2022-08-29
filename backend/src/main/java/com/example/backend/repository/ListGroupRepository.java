package com.example.backend.repository;

import com.example.backend.model.ListGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListGroupRepository extends JpaRepository<ListGroup, Long> {

}
