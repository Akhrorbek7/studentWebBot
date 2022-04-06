package com.example.demo.controller;

import com.example.demo.dto.GroupTypeDto;
import com.example.demo.service.GroupTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/class-type")
public record GroupTypeController(GroupTypeService groupTypeService) {

    @PostMapping
    public ResponseEntity<?> create(@RequestBody GroupTypeDto dto){
        groupTypeService.create(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id){
        GroupTypeDto groupTypeDto = groupTypeService.get(id);
        return ResponseEntity.ok(groupTypeDto);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll(){
        List<GroupTypeDto> groupTypeDto = groupTypeService.getAll();
        return ResponseEntity.ok(groupTypeDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody GroupTypeDto dto){
        groupTypeService.update(id, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        groupTypeService.delete(id);
        return ResponseEntity.ok().build();
    }
}