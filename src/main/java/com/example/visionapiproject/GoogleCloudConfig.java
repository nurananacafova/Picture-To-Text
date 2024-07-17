package com.example.visionapiproject;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.ImageAnnotatorSettings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
public class GoogleCloudConfig {
    @Value("${google.cloud.vision.credentials.path}")
    private String googleCredentialsPath;

    @Bean
    public ImageAnnotatorClient imageAnnotatorClient() throws IOException {
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(googleCredentialsPath));
        ImageAnnotatorSettings settings = ImageAnnotatorSettings.newBuilder().setCredentialsProvider(() -> credentials).build();
        return ImageAnnotatorClient.create(settings);
    }
}
