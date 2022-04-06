package com.example.demo.dto;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ClassDto {
    private Long id;
    private Long roomId;
    private RoomDto room;
    private UserGroupDto userGroup;
    private Long userGroupId;
    private Long groupId;
    private AttendanceTypeDto attendanceType;
    private Long attendanceTypeId;
    private String name;
    private LocalDate date;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
