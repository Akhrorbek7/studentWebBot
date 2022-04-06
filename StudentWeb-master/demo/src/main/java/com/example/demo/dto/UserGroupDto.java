package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserGroupDto {
    private Long id;
    private UserDto user;
    @NotNull(message = "User id invalid")
    private Long userId;
    private GroupDto group;
    @NotNull(message = "Group id invalid")
    private Long groupId;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
