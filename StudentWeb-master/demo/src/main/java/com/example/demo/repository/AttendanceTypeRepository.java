package com.example.demo.repository;

import com.example.demo.model.AttendanceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AttendanceTypeRepository extends JpaRepository<AttendanceType, Long> {

    Optional<AttendanceType> findByIdAndDeletedAtNull(Long id);

    List<AttendanceType> findAllByDeletedAtNull();
}
