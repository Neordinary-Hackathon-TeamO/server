package org.example.secret_santa.config;

import com.google.cloud.storage.Storage;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.StorageOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Configuration
public class GCSConfig {
    @Bean
    public Storage storage() throws IOException {
        // Google Cloud Storage 인증 파일 읽기
        ClassPathResource resource = new ClassPathResource("primal-asset-298817-b8b9c396e5f6.json");
        GoogleCredentials credentials = GoogleCredentials.fromStream(resource.getInputStream());

        // Storage 객체 생성 및 반환
        return StorageOptions.newBuilder()
                .setProjectId("primal-asset-298817")
                .setCredentials(credentials)
                .build()
                .getService();
    }
}
