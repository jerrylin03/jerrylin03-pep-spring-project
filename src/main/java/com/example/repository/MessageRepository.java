package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.entity.Message;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>{
    @Query("SELECT m FROM Message m WHERE m.posted_by = :posted_by")
    Optional<List<Message>> findAllByPosted_By(Integer posted_by);
}
