package com.project.drone.entity;

import com.project.drone.entity.Drone;
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

public class Medication {
    @Id
    private String code;
    private String name;
    private Integer weight;
    private String image;
    @OneToOne(fetch = FetchType.LAZY, targetEntity = Drone.class)
    @JoinColumn(name = "fk_serial_no", referencedColumnName = "serial_no")
    private Drone drone;
}
