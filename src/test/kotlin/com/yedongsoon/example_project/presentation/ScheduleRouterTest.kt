package com.yedongsoon.example_project.presentation

import com.epages.restdocs.apispec.ResourceDocumentation
import com.epages.restdocs.apispec.ResourceSnippetParameters
import com.ninjasquad.springmockk.MockkBean
import com.yedongsoon.example_project.application.couple.CoupleQueryService
import com.yedongsoon.example_project.application.schedule.ScheduleCommandService
import com.yedongsoon.example_project.application.schedule.ScheduleQueryService
import com.yedongsoon.example_project.domain.schedule.Schedule
import com.yedongsoon.example_project.presentation.handler.ScheduleHandler
import com.yedongsoon.example_project.presentation.handler.model.ScheduleCreateRequest
import com.yedongsoon.example_project.presentation.handler.model.ScheduleDetailResponse
import com.yedongsoon.example_project.presentation.handler.model.ScheduleModifyRequestCreateDto
import com.yedongsoon.example_project.presentation.router.ScheduleRouter
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebFlux
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.ApplicationContext
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import java.time.LocalDate
import java.time.LocalDateTime

@ExtendWith(RestDocumentationExtension::class, SpringExtension::class, MockKExtension::class)
@ContextConfiguration(classes = [ScheduleRouter::class, ScheduleHandler::class])
@AutoConfigureWebFlux
@WebFluxTest
class ScheduleRouterTest(private val context: ApplicationContext) {

    private lateinit var webTestClient: WebTestClient

    @MockkBean
    private lateinit var scheduleQueryService: ScheduleQueryService

    @MockkBean
    private lateinit var scheduleCommandService: ScheduleCommandService

    @MockkBean
    private lateinit var coupleQueryService: CoupleQueryService

    @BeforeEach
    fun setUp(restDocumentation: RestDocumentationContextProvider) {
        webTestClient = WebTestClient.bindToApplicationContext(context)
                .configureClient()
                .baseUrl("http://localhost:8080")
                .filter(WebTestClientRestDocumentation.documentationConfiguration(restDocumentation))
                .build()
    }

    @Test
    fun `일정 등록 API 테스트`() {
        val request = ScheduleCreateRequest(
                busyLevel = "MEDIUM",
                scheduleName = "Meeting",
                scheduleLocation = "Office",
                scheduleWith = "Colleagues",
                genderType = "MIXED",
                scheduleStartAt = LocalDateTime.now(),
                scheduleEndAt = LocalDateTime.now().plusHours(2),
                isCommon = false,
        )

        coJustRun { scheduleCommandService.createSchedule(any()) }
        webTestClient.post()
                .uri("/schedule")
                .header("Member-Code", "eyJubyI6MiwibmFtZSI6Im5hbWUiLCJhY2NvdW50IjoiYWNjb3VudCJ9")
                .bodyValue(request)
                .exchange()
                .expectStatus().isNoContent
                .expectBody().consumeWith(
                        document(
                                "create-schedule",
                                ResourceDocumentation.resource(
                                        ResourceSnippetParameters.builder()
                                                .tag("schedule")
                                                .description("일정 등록")
                                                .requestFields(
                                                        fieldWithPath("scheduleName").type(JsonFieldType.STRING).description("일정 이름"),
                                                        fieldWithPath("scheduleLocation").type(JsonFieldType.STRING).description("일정 위치"),
                                                        fieldWithPath("scheduleWith").type(JsonFieldType.STRING).description("일정을 함께 하는 사람"),
                                                        fieldWithPath("busyLevel").type(JsonFieldType.STRING).description("바쁨 정도"),
                                                        fieldWithPath("groupGenderType").type(JsonFieldType.STRING).description("성별 그룹 타입"),
                                                        fieldWithPath("scheduleStartAt").type(JsonFieldType.ARRAY).description("일정 시작 시간"),
                                                        fieldWithPath("scheduleEndAt").type(JsonFieldType.ARRAY).description("일정 종료 시간"),
                                                        fieldWithPath("isCommon").type(JsonFieldType.BOOLEAN).description("공유 여부"),
                                                ).build()
                                )
                        )
                )
    }

    @Test
    fun `특정 날짜 일정 조회 API 테스트`() {
        val date = LocalDate.now()
        val scheduleDetail = ScheduleDetailResponse(
                scheduleNo = 1,
                accountNo = 100,
                busyLevel = "LOW",
                scheduleName = "Lunch",
                scheduleLocation = "Cafe",
                scheduleWith = "Friend",
                status = "ONGOING",
                scheduleStartAt = LocalDateTime.now(),
                scheduleEndAt = LocalDateTime.now().plusHours(1),
                genderType = "남자",
                isCommon = false
        )

        coEvery { scheduleQueryService.getScheduleByDateExceptCommon(any(), date) } returns listOf(scheduleDetail)
        webTestClient.get()
                .uri("/schedule?searchDate={searchDate}", date.toString())
                .header("Member-Code", "eyJubyI6MiwibmFtZSI6Im5hbWUiLCJhY2NvdW50IjoiYWNjb3VudCJ9")
                .exchange()
                .expectStatus().isOk
                .expectBody().consumeWith(
                        document(
                                "read-schedules",
                                ResourceDocumentation.resource(
                                        ResourceSnippetParameters.builder()
                                                .tag("schedule")
                                                .description("특정 날짜 일정 조회")
                                                .queryParameters(
                                                        parameterWithName("searchDate").description("조회할 날짜"),
                                                )
                                                .responseFields(
                                                        fieldWithPath("[].scheduleNo").type(JsonFieldType.NUMBER).description("일정 이름"),
                                                        fieldWithPath("[].accountNo").type(JsonFieldType.NUMBER).description("일정 위치"),
                                                        fieldWithPath("[].scheduleStartAt").type(JsonFieldType.STRING).description("함께하는 사람"),
                                                        fieldWithPath("[].scheduleEndAt").type(JsonFieldType.STRING).description("바쁨 정도"),
                                                        fieldWithPath("[].isCommon").type(JsonFieldType.BOOLEAN).description("일정 상태"),
                                                        fieldWithPath("[].busyLevel").type(JsonFieldType.STRING).description("일정 이름"),
                                                        fieldWithPath("[].scheduleName").type(JsonFieldType.STRING).description("일정 위치"),
                                                        fieldWithPath("[].scheduleLocation").type(JsonFieldType.STRING).description("함께하는 사람"),
                                                        fieldWithPath("[].scheduleWith").type(JsonFieldType.STRING).description("바쁨 정도"),
                                                        fieldWithPath("[].status").type(JsonFieldType.STRING).description("일정 상태")
                                                ).build()
                                )
                        )

                )
    }

    @Test
    fun `일정 상세 조회 API 테스트`() {
        val scheduleNo = 1
        val schedule = Schedule(
                scheduleNo = scheduleNo,
                accountNo = 100,
                busyLevel = "HIGH",
                scheduleName = "Workshop",
                scheduleLocation = "Conference Room",
                scheduleWith = "Team",
                groupGenderType = "Male",
                scheduleStartAt = LocalDateTime.now(),
                scheduleEndAt = LocalDateTime.now().plusHours(3),
                isCommon = false,
                scheduleAt = LocalDate.now()
        )


        coEvery { scheduleQueryService.getScheduleByScheduleNo(any(), scheduleNo) } returns schedule
        webTestClient.get()
                .uri("/schedule/detail/{scheduleNo}", scheduleNo)
                .header("Member-Code", "eyJubyI6MiwibmFtZSI6Im5hbWUiLCJhY2NvdW50IjoiYWNjb3VudCJ9")
                .exchange()
                .expectStatus().isOk
                .expectBody().consumeWith(
                        document(
                                "read-schedule-detail",
                                ResourceDocumentation.resource(
                                        ResourceSnippetParameters.builder()
                                                .tag("schedule")
                                                .description("일정 상세 조회")
                                                .pathParameters(
                                                        parameterWithName("scheduleNo").description("일정 번호"),
                                                )
                                                .responseFields(
                                                        fieldWithPath("scheduleNo").type(JsonFieldType.NUMBER).description("일정 이름"),
                                                        fieldWithPath("accountNo").type(JsonFieldType.NUMBER).description("일정 위치"),
                                                        fieldWithPath("busyLevel").type(JsonFieldType.STRING).description("일정 위치"),
                                                        fieldWithPath("scheduleName").type(JsonFieldType.STRING).description("일정 위치"),
                                                        fieldWithPath("scheduleLocation").type(JsonFieldType.STRING).description("일정 위치"),
                                                        fieldWithPath("scheduleWith").type(JsonFieldType.STRING).description("일정 위치"),
                                                        fieldWithPath("status").type(JsonFieldType.STRING).description("일정 위치"),
                                                        fieldWithPath("scheduleStartAt").type(JsonFieldType.STRING).description("함께하는 사람"),
                                                        fieldWithPath("scheduleEndAt").type(JsonFieldType.STRING).description("바쁨 정도"),
                                                        fieldWithPath("isCommon").type(JsonFieldType.BOOLEAN).description("일정 상태")
                                                ).build()
                                )
                        )
                )
    }

    @Test
    fun `일정을 공용 일정으로 설정 API 테스트`() {
        val scheduleNo = 1

        // `scheduleCommandService.setToCommonSchedule` 메서드 호출이 정상적으로 실행되도록 Mock 설정
        coJustRun { scheduleCommandService.setToCommonSchedule(scheduleNo) }

        // 테스트 요청: `/schedule/{scheduleNo}/common-schedule`에 대한 PUT 요청
        webTestClient.put()
                .uri("/schedule/{scheduleNo}/common-schedule", scheduleNo)
                .header("Member-Code", "eyJubyI6MiwibmFtZSI6Im5hbWUiLCJhY2NvdW50IjoiYWNjb3VudCJ9")
                .exchange()
                .expectStatus().isOk  // 상태 코드가 200 OK인지 확인
                .expectBody().consumeWith(
                        document(
                                "set-to-common-schedule",  // REST Docs 문서 스니펫 이름 설정
                                ResourceDocumentation.resource(
                                        ResourceSnippetParameters.builder()
                                                .tag("schedule")
                                                .description("일정을 공용 일정으로 설정")
                                                .pathParameters(
                                                        parameterWithName("scheduleNo").description("공용 일정으로 설정할 일정 번호")
                                                ).build()
                                )
                        )
                )
    }

    @Test
    fun `수정 요청 생성 테스트`() {
        val scheduleNo = 1

        val request = ScheduleModifyRequestCreateDto(
                scheduleNo = scheduleNo,
                busyLevel = "HIGH",
                scheduleName = "Workshop",
                scheduleLocation = "Conference Room",
                scheduleWith = "Team",
                genderType = "Male",
                scheduleStartAt = LocalDateTime.now(),
                scheduleEndAt = LocalDateTime.now().plusHours(3),
                isCommon = false,
        )

        coJustRun { scheduleCommandService.createScheduleModifyRequest(any()) }

        webTestClient.post()
                .uri("/schedule/modify-reqeust", scheduleNo)
                .header("Member-Code", "eyJubyI6MiwibmFtZSI6Im5hbWUiLCJhY2NvdW50IjoiYWNjb3VudCJ9")
                .bodyValue(request)
                .exchange()
                .expectStatus().isNoContent
                .expectBody().consumeWith(
                        document(
                                "modify-request-schedule",  // REST Docs 문서 스니펫 이름 설정
                                ResourceDocumentation.resource(
                                        ResourceSnippetParameters.builder()
                                                .tag("modify-request")
                                                .description("수정 요청 생성")
                                                .requestFields(
                                                        fieldWithPath("scheduleNo").type(JsonFieldType.NUMBER).description("일정 이름"),
                                                        fieldWithPath("busyLevel").type(JsonFieldType.STRING).description("일정 위치"),
                                                        fieldWithPath("scheduleName").type(JsonFieldType.STRING).description("일정 위치"),
                                                        fieldWithPath("scheduleLocation").type(JsonFieldType.STRING).description("일정 위치"),
                                                        fieldWithPath("scheduleWith").type(JsonFieldType.STRING).description("일정 위치"),
                                                        fieldWithPath("genderType").type(JsonFieldType.STRING).description("일정 위치"),
                                                        fieldWithPath("scheduleStartAt").type(JsonFieldType.ARRAY).description("함께하는 사람"),
                                                        fieldWithPath("scheduleEndAt").type(JsonFieldType.ARRAY).description("바쁨 정도"),
                                                        fieldWithPath("isCommon").type(JsonFieldType.BOOLEAN).description("일정 상태")).build()
                                )
                        )
                )
    }


    @Test
    fun `일정 수정 API 테스트`() {
        val request = ScheduleCreateRequest(
                busyLevel = "MEDIUM",
                scheduleName = "Meeting",
                scheduleLocation = "Office",
                scheduleWith = "Colleagues",
                genderType = "MIXED",
                scheduleStartAt = LocalDateTime.now(),
                scheduleEndAt = LocalDateTime.now().plusHours(2),
                isCommon = false,
        )
        val scheduleNo = 1
        coJustRun { scheduleCommandService.modifySchedule(any()) }
        webTestClient.put()
                .uri("/schedule/{scheduleNo}", scheduleNo)
                .header("Member-Code", "eyJubyI6MiwibmFtZSI6Im5hbWUiLCJhY2NvdW50IjoiYWNjb3VudCJ9")
                .bodyValue(request)
                .exchange()
                .expectStatus().isNoContent
                .expectBody().consumeWith(
                        document(
                                "modify-schedule",
                                ResourceDocumentation.resource(
                                        ResourceSnippetParameters.builder()
                                                .tag("schedule")
                                                .description("스케쥴 수정")
                                                .pathParameters(
                                                        parameterWithName("scheduleNo").description("일정 번호"),
                                                )
                                                .requestFields(
                                                        fieldWithPath("scheduleName").type(JsonFieldType.STRING).description("일정 이름"),
                                                        fieldWithPath("scheduleLocation").type(JsonFieldType.STRING).description("일정 위치"),
                                                        fieldWithPath("scheduleWith").type(JsonFieldType.STRING).description("일정을 함께 하는 사람"),
                                                        fieldWithPath("busyLevel").type(JsonFieldType.STRING).description("바쁨 정도"),
                                                        fieldWithPath("genderType").type(JsonFieldType.STRING).description("성별 그룹 타입"),
                                                        fieldWithPath("scheduleStartAt").type(JsonFieldType.ARRAY).description("일정 시작 시간"),
                                                        fieldWithPath("scheduleEndAt").type(JsonFieldType.ARRAY).description("일정 종료 시간"),
                                                        fieldWithPath("isCommon").type(JsonFieldType.BOOLEAN).description("공유 여부"),
                                                ).build()
                                )
                        )
                )
    }

}
