package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = ("classes"))
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = ("room_id"), insertable = false, updatable = false)
    private Room room;

    @Column(name = ("room_id"))
    private Long roomId;

    @ManyToOne
    @JoinColumn(name = ("user_group_id"), insertable = false, updatable = false)
    private UserGroup userGroup;

    @Column(name = ("user_group_id"))
    private Long userGroupId;

    @Column(name = ("group_id"))
    private Long groupId;

    @ManyToOne
    @JoinColumn(name = ("attendance_type_id"), insertable = false, updatable = false)
    private AttendanceType attendanceType;

    @Column(name = ("attendance_type_id"))
    private Long attendanceTypeId;

    @Column(name = ("name"))
    private String name;

    @Column(name = ("date"))
    private LocalDate date;

    @Column(name = ("status"))
    private Boolean status;

    @Column(name = ("created_at"))
    private LocalDateTime createdAt;

    @Column(name = ("updated_at"))
    private LocalDateTime updatedAt;

    @Column(name = ("deleted_at"))
    private LocalDateTime deletedAt;
}
