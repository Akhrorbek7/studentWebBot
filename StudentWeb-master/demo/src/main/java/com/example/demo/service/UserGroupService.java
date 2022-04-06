package com.example.demo.service;

import com.example.demo.dto.TeacherDetailDto;
import com.example.demo.dto.UserGroupDto;
import com.example.demo.exception.CustomGlobalExceptionHandler;
import com.example.demo.model.Group;
import com.example.demo.model.User;
import com.example.demo.model.UserGroup;
import com.example.demo.repository.UserGroupRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public record UserGroupService(UserGroupRepository userGroupRepository,
                               GroupService groupService,
                               UserService userService,
                               ModelMapper modelMapper) {

    //Main functions
    public UserGroupDto get(Long id) {
        return modelMapper.map(getEntity(id), UserGroupDto.class);
    }

    public List<UserGroupDto> getAll() {
        List<UserGroup> userGroupList = userGroupRepository.findAllByDeletedAtNull();
        List<UserGroup> list = new ArrayList<>();
        if (!userGroupList.isEmpty()) {
            for (UserGroup userGroup : userGroupList) {
                if (userGroup.getDeletedAt() == null) {
                    setId(userGroup);
                    list.add(userGroup);
                }
            }
            List<UserGroupDto> userGroupDtoList = list.stream().map(userGroup -> modelMapper.map(userGroup, UserGroupDto.class))
                    .collect(Collectors.toList());
            return userGroupDtoList;
        }
        throw new CustomGlobalExceptionHandler("User Group list not found");
    }

    public void create(UserGroupDto userGroupDtoRequest) {
        UserGroup userGroup = modelMapper.map(userGroupDtoRequest, UserGroup.class);
        checkFields(userGroup.getUserId(), userGroup.getGroupId());
        userGroup.setCreatedAt(LocalDateTime.now());
        userGroup.setStatus(true);
        userGroupRepository.save(userGroup);
    }

    public void update(Long id, UserGroupDto userGroupDtoRequest) {
        UserGroup userGroup = modelMapper.map(userGroupDtoRequest, UserGroup.class);
        updateSet(userGroup, id);
        userGroupRepository.save(userGroup);
    }

    public void delete(Long id) {
        UserGroup userGroup = getEntity(id);
        userGroup.setDeletedAt(LocalDateTime.now());
        userGroupRepository.save(userGroup);
    }

    public List<UserGroup> findAllForTeacher(Long id){
        List<UserGroup> userGroupList = userGroupRepository.findAllByUserIdAndDeletedAtNull(id);
        if(userGroupList.isEmpty()){
            throw new CustomGlobalExceptionHandler("User group not found");
        }
        return userGroupList;
    }

    //Secondary functions
    public UserGroup getEntity(Long id) {
        Optional<UserGroup> optional = userGroupRepository.findByIdAndDeletedAtNull(id);
        if (optional.isEmpty()) {
            throw new CustomGlobalExceptionHandler("User Group not found");
        }
        setId(optional.get());
        return optional.get();
    }

    public void checkFields(Long userId, Long groupId) {
        userService.getEntity(userId);
        groupService.getEntity(groupId);
    }

    public TeacherDetailDto findStudentsByTeacherId(Long id){
        List<UserGroup> userGroupEntityList = userGroupRepository.findAllByUserIdAndDeletedAtNull(id);
        TeacherDetailDto teacherDetailDto = new TeacherDetailDto();
        List<Group> groupList = new LinkedList<>();
        for(UserGroup userGroup : userGroupEntityList){
            groupList.add(userGroup.getGroup());
        }
        teacherDetailDto.setGroupList(groupList);
        List<List<User>> resultList = new LinkedList<>();
        for (Group group : groupList){
            resultList.add(getStudentsForGroup(group.getId()));
        }
        teacherDetailDto.setUserList(resultList);
        return teacherDetailDto;
    }

    public List<User> getStudentsForGroup(Long groupId){
        List<UserGroup> userGroupList = getGroupAndStudents(groupId);
        List<User> resultUserList = new LinkedList<>();
        for(UserGroup userGroup : userGroupList){
            if(userGroup.getUser().getUserTypeId() == 1){
                resultUserList.add(userGroup.getUser());
            }
        }
        return resultUserList;
    }

    private List<UserGroup> getGroupAndStudents(Long groupId) {
        List<UserGroup> userGroupList = userGroupRepository.findAllByGroupIdAndDeletedAtIsNull(groupId);
        if(userGroupList.isEmpty()){
            throw new CustomGlobalExceptionHandler("Group is empty");
        }
        return userGroupList;
    }


    public UserGroup setId(UserGroup request) {
        request.setUser(userService.getEntity(request.getUserId()));
        request.setGroup(groupService.getEntity(request.getGroupId()));
        return request;
    }

    public UserGroup updateSet(UserGroup userGroupRequest, Long id) {
        UserGroup userGroup = getEntity(id);
        userGroupRequest.setId(id);
        userGroupRequest.setUpdatedAt(LocalDateTime.now());
        userGroupRequest.setCreatedAt(userGroup.getCreatedAt());
        userGroupRequest.setStatus(userGroup.getStatus());
        return userGroupRequest;
    }
}
