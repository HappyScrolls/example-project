package com.yedongsoon.example_project.domain.notification

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface NotificationHistoryRepository : JpaRepository<NotificationHistory, Int> {
    fun findByNotificationNoAndIsDeletedFalse(no: Int): NotificationHistory?
    fun findByAccountNoAndIsDeletedFalseOrderByMessagedAtDesc(no: Int, pageRequest: Pageable): Page<NotificationHistory>
}
