package com.squadAlertSystem.squadalertsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.squadAlertSystem.squadalertsystem.entity.Calendar;

import java.util.List;

public interface CalendarRepository extends JpaRepository<Calendar, String> {
    List<Calendar> findByPicsLike(String name);
}
