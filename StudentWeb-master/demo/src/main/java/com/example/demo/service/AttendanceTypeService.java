package com.example.demo.service;

import com.example.demo.dto.AttendanceTypeDto;
import com.example.demo.exception.CustomGlobalExceptionHandler;
import com.example.demo.model.AttendanceType;
import com.example.demo.repository.AttendanceTypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public record AttendanceTypeService(AttendanceTypeRepository attendanceTypeRepository,
                                    ModelMapper modelMapper) {
    public AttendanceTypeDto get(Long id){
        return modelMapper.map(getEntity(id), AttendanceTypeDto.class);
    }

    public List<AttendanceTypeDto> getAll(){
        List<AttendanceType> attendanceTypeList = attendanceTypeRepository.findAllByDeletedAtNull();
        List<AttendanceType> list = new LinkedList<>();
        if(!attendanceTypeList.isEmpty()){
            for(AttendanceType attendanceType : attendanceTypeList){
                if(attendanceType.getDeletedAt() == null){
                    list.add(attendanceType);
                }
            }
            return list.stream().map(attendanceType -> modelMapper.map(attendanceType, AttendanceTypeDto.class)).toList();
        }
        throw new CustomGlobalExceptionHandler("Attendance type list not found");
    }

    public void create(AttendanceTypeDto dto){
        AttendanceType attendanceType = modelMapper.map(dto, AttendanceType.class);
        attendanceType.setCreatedAt(LocalDateTime.now());
        attendanceType.setStatus(true);
        attendanceTypeRepository.save(attendanceType);
    }

    public void update(Long id, AttendanceTypeDto dto){
        AttendanceType entity = modelMapper.map(dto, AttendanceType.class);
        updateSet(id, entity);
        attendanceTypeRepository.save(entity);
    }

    public void delete(Long id){
        AttendanceType attendanceType = getEntity(id);
        attendanceType.setDeletedAt(LocalDateTime.now());
        attendanceTypeRepository.save(attendanceType);
    }



    //Secondary function
    public AttendanceType getEntity(Long id) {
        Optional<AttendanceType> optional = attendanceTypeRepository.findByIdAndDeletedAtNull(id);
        if (optional.isEmpty()){
            throw new CustomGlobalExceptionHandler("Attendance type not found");
        }
        return optional.get();
    }

    private void updateSet(Long id, AttendanceType entity) {
        AttendanceType attendanceType = getEntity(id);
        attendanceType.setId(id);
        attendanceType.setUpdatedAt(LocalDateTime.now());
        attendanceType.setDeletedAt(entity.getDeletedAt());
        attendanceType.setStatus(entity.getStatus());
    }
}
