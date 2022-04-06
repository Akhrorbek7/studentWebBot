package com.example.demo.dto;

import com.example.demo.model.Group;
import com.example.demo.model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TeacherDetailDto {
    private List<Group> groupList;
    private List<List<User>> userList;
}
