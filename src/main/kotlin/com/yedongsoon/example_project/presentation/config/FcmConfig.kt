package com.yedongsoon.example_project.presentation.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import org.springframework.context.annotation.Bean
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component

@Component
class FcmConfig {
    private val firebaseResource = ClassPathResource(
            "togethery-52df6-firebase-adminsdk-6ydyl-28996956a9.json")

    @Bean
    fun firebaseApp(): FirebaseApp {
        val options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(
                        firebaseResource.inputStream))
                .build()
        // FirebaseApp 정보 출력
        val app = FirebaseApp.initializeApp(options)

        if (app != null) {
            println("FirebaseApp initialized successfully!")
            println("Name: ${app.name}")
            println("Options: ${app.options}")
        } else {
            println("Failed to initialize FirebaseApp.")
        }
        return app
    }

    @Bean
    fun firebaseMessaging(firebaseApp: FirebaseApp): FirebaseMessaging {
        return FirebaseMessaging.getInstance(firebaseApp)
    }
}