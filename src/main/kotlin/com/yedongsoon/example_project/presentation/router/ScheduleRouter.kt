package com.yedongsoon.example_project.presentation.router

import com.yedongsoon.example_project.presentation.handler.ScheduleHandler
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springdoc.core.annotations.RouterOperation
import org.springdoc.core.annotations.RouterOperations
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class ScheduleRouter(private val scheduleHandler: ScheduleHandler) {
    @Bean
    @RouterOperations(
            RouterOperation(
                    path = "/schedules",
                    beanClass = ScheduleHandler::class,
                    beanMethod = "createSchedule",
                    operation = Operation(
                            summary = "스케줄 생성",
                            description = "새로운 스케줄을 생성합니다.",
                            responses = [
                                ApiResponse(responseCode = "204", description = "스케줄 생성 성공"),
                                ApiResponse(responseCode = "400", description = "잘못된 요청 데이터")
                            ]
                    )
            ),
            // 다른 엔드포인트도 비슷하게 추가합니다.
    )
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
                PUT("/{scheduleNo}/status", scheduleHandler::changeStatus)
                PUT("/{scheduleNo}/common-schedule", scheduleHandler::setToCommonSchedule)
            }
        }
    }
}