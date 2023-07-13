package com.project.drone.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Drone {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long serial_no;
    private String model;
    private Integer weight_limit;
    private Integer battery;
    @Enumerated(EnumType.STRING)
    private DroneState state;
    @JsonIgnore
    @OneToOne(mappedBy = "drone", cascade = CascadeType.MERGE)
    private Medication medication;
}
