package com.example.parkingsystem.parkingsystem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AverageParkTimeRepository extends JpaRepository<AverageParkTime, Integer> {

	
}
