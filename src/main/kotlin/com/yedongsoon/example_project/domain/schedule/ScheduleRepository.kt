package com.yedongsoon.example_project.domain.schedule

import org.springframework.data.jpa.repository.JpaRepository

interface ScheduleRepository : JpaRepository<Schedule, Int>{
}