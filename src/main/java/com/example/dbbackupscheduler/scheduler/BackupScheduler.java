package com.example.dbbackupscheduler.scheduler;

import com.example.dbbackupscheduler.model.DatabaseConfigModel;
import com.example.dbbackupscheduler.repository.DatabaseConfigRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Component
@EnableScheduling
public class BackupScheduler {

    private static final String TIME_ZONE = "America/Sao_Paulo";

    @Value("${backup.api.url}")
    private String backupAPIUrl;

    private final DatabaseConfigRepository databaseConfigRepository;

    public BackupScheduler(DatabaseConfigRepository databaseConfigRepository) {
        this.databaseConfigRepository = databaseConfigRepository;
    }

    @Scheduled(cron = "${cron.string}", zone = TIME_ZONE)
    public void backupDatabases() {
        List<DatabaseConfigModel> databaseConfigs = getAllDatabaseConfig();
        if(databaseConfigs.isEmpty()) {
            System.out.println("No database found for backup.");
            return;
        }

        for(DatabaseConfigModel dbConfig : databaseConfigs) {
            sendBackupRequest(dbConfig);
        }
    }

    private void sendBackupRequest(DatabaseConfigModel dbConfig) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            String requestBody = new ObjectMapper().writeValueAsString(dbConfig);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(backupAPIUrl))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenAccept(response -> {
                        if(response.statusCode() == 200) {
                            System.out.println("API request success: " + response.body());
                        }else {
                            System.out.println("API request error: " + response.body());
                        }
                    });
        } catch (Exception e) {
            System.out.println("Error sending backup request for database: " +dbConfig.getDatabaseName() + " Error: " + e.getMessage());
        }
    }

    private List<DatabaseConfigModel> getAllDatabaseConfig() {
        return databaseConfigRepository.findAll();
    }

}
