package ru.rotiza.offlinefl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rotiza.offlinefl.entity.timetable.TimeTable;

import java.util.UUID;

@Repository
public interface TimeTableRepo extends JpaRepository<TimeTable, UUID> { }