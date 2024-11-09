plugins {
    id("org.springframework.boot") version "3.3.2"
    id("io.spring.dependency-management") version "1.1.6"
    kotlin("jvm") version "1.9.24"
    kotlin("plugin.spring") version "1.9.24"
    kotlin("plugin.jpa") version "1.9.24"
    kotlin("kapt") version "1.5.31"
    id("com.epages.restdocs-api-spec") version "0.17.1"
}

group = "com.yedongsoon"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("com.mysql:mysql-connector-j")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    testImplementation("io.mockk:mockk:1.13.8")
    testImplementation("org.springframework.restdocs:spring-restdocs-webtestclient:3.0.0")
    testImplementation("com.ninja-squad:springmockk:4.0.2")
    implementation("io.netty:netty-resolver-dns-native-macos:4.1.97.Final:osx-aarch_64")
    implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.1.0")
    implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    implementation("com.querydsl:querydsl-apt:5.0.0:jakarta")
    kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    testImplementation("com.epages:restdocs-api-spec:0.17.1")
    testImplementation("io.mockk:mockk:1.13.8")
    testImplementation("org.springframework.restdocs:spring-restdocs-webtestclient:3.0.0")
    testImplementation("com.ninja-squad:springmockk:4.0.2")
    testImplementation("com.epages:restdocs-api-spec-openapi3-generator:0.17.1")
    implementation("io.micrometer:micrometer-registry-prometheus")


}

kapt {
    arguments {
        arg("querydsl.entityAccessors", "true")
        arg("querydsl.useFields", "false") // 필드 대신 getter/setter를 사용하는 옵션
    }
}

sourceSets {
    main {
        java.srcDir("build/generated/source/kapt/main")
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}


tasks.processResources {
    dependsOn("openapi3")
    from("build/api-spec") {
        include("openapi3.json")
        into("static/docs")
    }
}