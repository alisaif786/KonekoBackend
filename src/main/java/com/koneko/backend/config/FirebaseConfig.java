package com.koneko.backend.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Configuration
public class FirebaseConfig {

    @Value("${firebase.credentials}")
    private String firebaseCredentials;


    @PostConstruct
    public void initialize() {

        try {

            if (FirebaseApp.getApps().isEmpty()) {

                InputStream serviceAccount =
                        new ByteArrayInputStream(
                                firebaseCredentials.getBytes(StandardCharsets.UTF_8)
                        );


                FirebaseOptions options =
                        FirebaseOptions.builder()
                                .setCredentials(
                                        GoogleCredentials.fromStream(serviceAccount)
                                )
                                .build();


                FirebaseApp.initializeApp(options);

                System.out.println("Firebase initialized successfully");

            }

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to initialize Firebase.",
                    e
            );
        }
    }
}