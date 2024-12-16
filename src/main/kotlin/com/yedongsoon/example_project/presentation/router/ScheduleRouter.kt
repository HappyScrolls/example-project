package com.yedongsoon.example_project.presentation.router

import com.yedongsoon.example_project.presentation.handler.ScheduleHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class ScheduleRouter(private val scheduleHandler: ScheduleHandler) {
    @Bean
    fun scheduleRoute(): RouterFunction<ServerResponse> {
        return coRouter {
            (accept(MediaType.APPLICATION_JSON) and "/schedule").nest {
                // 일정 등록
                POST("", scheduleHandler::createSchedule)

                // 특정 날짜 일정 조회
                GET("", scheduleHandler::readSchedules)

                // 일정 상세 조회
                GET("/detail/{scheduleNo}", scheduleHandler::readScheduleDetail)

                // (커플) 특정 날짜 일정 조회
                GET("/couple", scheduleHandler::readCouplePartnerSchedules)

                GET("/common", scheduleHandler::getCommonSchedules)
                // 일정 삭제
                DELETE("/{scheduleNo}", scheduleHandler::deleteSchedule)
                PUT("/{scheduleNo}", scheduleHandler::modifySchedule)
                PUT("/{scheduleNo}/status", scheduleHandler::changeStatus)
                PUT("/{scheduleNo}/common-schedule", scheduleHandler::setToCommonSchedule)
                (accept(MediaType.APPLICATION_JSON) and "/modify-request").nest {
                    POST("", scheduleHandler::createScheduleModifyRequest)
                    GET("{scheduleNo}", scheduleHandler::getScheduleModifyRequest)
                }
            }

        }
    }
}
