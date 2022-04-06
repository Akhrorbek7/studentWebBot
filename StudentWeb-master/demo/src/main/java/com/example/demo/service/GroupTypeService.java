package com.example.demo.service;

import com.example.demo.dto.GroupTypeDto;
import com.example.demo.exception.CustomGlobalExceptionHandler;
import com.example.demo.model.GroupType;
import com.example.demo.repository.GroupTypeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public record GroupTypeService(GroupTypeRepository groupTypeRepository,
                               ModelMapper modelMapper) {

    //Main functions
    public GroupTypeDto get(Long id){
        return modelMapper.map(getEntity(id), GroupTypeDto.class);
    }

    public List<GroupTypeDto> getAll(){
        List<GroupType> groupTypeList = groupTypeRepository.findAllAndDeletedAtNull();
        List<GroupType> list = new LinkedList<>();
        if(!groupTypeList.isEmpty()){
            for (GroupType groupType : groupTypeList){
                if(groupType.getDeletedAt() == null){
                    list.add(groupType);
                }
            }
            return list.stream().map(groupType -> modelMapper.map(groupType, GroupTypeDto.class)).toList();
        }
        throw new CustomGlobalExceptionHandler("Class Type List not found");
    }

    public void create(GroupTypeDto dto){
        GroupType groupType = modelMapper.map(dto, GroupType.class);
        groupType.setCreatedAt(LocalDateTime.now());
        groupType.setStatus(true);
        groupTypeRepository.save(groupType);
    }

    public void update(Long id, GroupTypeDto dto){
        GroupType groupType = modelMapper.map(dto, GroupType.class);
        updateSet(id, groupType);
        groupTypeRepository.save(groupType);
    }

    public void delete(Long id){
        GroupType groupType = getEntity(id);
        groupType.setDeletedAt(LocalDateTime.now());
        groupTypeRepository.save(groupType);
    }

    //Secondary functions
    public GroupType getEntity(Long id) {
        Optional<GroupType> optional = groupTypeRepository.findByIdAndDeletedAtNull(id);
        if(optional.isEmpty()){
            throw new CustomGlobalExceptionHandler("Class Type not found");
        }
        return optional.get();
    }

    private void updateSet(Long id, GroupType groupType) {
        GroupType old = getEntity(id);
        groupType.setId(id);
        groupType.setUpdatedAt(LocalDateTime.now());
        groupType.setCreatedAt(old.getCreatedAt());
        groupType.setStatus(old.getStatus());
    }
}
