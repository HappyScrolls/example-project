package com.yedongsoon.example_project.presentation.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import java.util.*

@Component
class FcmConfig {
    @Value("\${fcm.json}")
    private lateinit var credentialsJson: String

    @Bean
    suspend fun firebaseMessaging(): FirebaseMessaging {
        val json = String(Base64.getDecoder().decode(credentialsJson))
        println("credentialsJson = ${json}")
        val credentialsStream = json.byteInputStream(Charsets.UTF_8)
        val options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(
                        credentialsStream))
                .build()
        // FirebaseApp 정보 출력
        val app = FirebaseApp.getApps().firstOrNull { it.name == FirebaseApp.DEFAULT_APP_NAME } ?: let {
            FirebaseApp.initializeApp(options)
        }
        if (app != null) {
            println("FirebaseApp initialized successfully!")
            println("Name: ${app.name}")
            println("Options: ${app.options}")
        } else {
            println("Failed to initialize FirebaseApp.")
        }
        return FirebaseMessaging.getInstance(app)
    }
}