package com.project.drone.repository;

import com.project.drone.entity.Drone;
import com.project.drone.entity.DroneState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DroneRepository extends JpaRepository<Drone,Long> {
    List<Drone> findAllByState (DroneState state);
}
