package com.example.vne.repository;

import com.example.vne.model.Vne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VneRepository extends JpaRepository<Vne,Long> {
    List<Vne> findByHost(String Host);

}
