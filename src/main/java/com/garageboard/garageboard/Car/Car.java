package com.garageboard.garageboard.Car;

import java.util.List;

import com.garageboard.garageboard.BuildLog.BuildLog;
import com.garageboard.garageboard.User.User;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Table(name = "car")
@Getter
@Setter
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    private String make;

    @Column(nullable = false)
    private String model;

    @Column
    private String trim;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "owner", nullable = false)
    private User user;

    @OneToMany(mappedBy = "car", fetch = FetchType.LAZY)
    private List<BuildLog> buildLogs;
}
