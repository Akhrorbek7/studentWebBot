package com.example.demo.dto;

import com.example.demo.model.Class;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TableDetailDto {
    private String firstname;
    private String lastname;
    private Long userGroupId;
    private List<Class> classList;
}
