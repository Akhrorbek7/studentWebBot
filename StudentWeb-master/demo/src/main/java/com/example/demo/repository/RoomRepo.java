package com.example.demo.repository;

import com.example.demo.model.Class;
import com.example.demo.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoomRepo extends JpaRepository<Room, Long>{
    @Query(value = "Select * from class Where id=:id And deleted_at is null", nativeQuery = true)
    Optional<Room> findByIdAndDeletedAtNull(@Param("id") Long id);

    @Query(value = "Select * from class Where deleted_at is null", nativeQuery = true)
    List<Room> findAllAndDeletedAtNull();
}
