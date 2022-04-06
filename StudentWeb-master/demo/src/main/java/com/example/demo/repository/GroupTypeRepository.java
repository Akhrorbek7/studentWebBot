package com.example.demo.repository;

import com.example.demo.model.GroupType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GroupTypeRepository extends JpaRepository<GroupType, Long> {
    @Query(value = "Select * from class_type Where id=:id And deleted_at is null", nativeQuery = true)
    Optional<GroupType> findByIdAndDeletedAtNull(@Param("id") Long id);

    @Query(value = "Select * from class_type Where deleted_at is null", nativeQuery = true)
    List<GroupType> findAllAndDeletedAtNull();
}
