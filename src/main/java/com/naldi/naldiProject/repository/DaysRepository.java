package com.naldi.naldiProject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.naldi.naldiProject.models.Request;

@Repository
public interface DaysRepository extends JpaRepository<Request, Integer> {
    Optional<Request> findById(Integer id);
    void deleteById(Integer id);
}
