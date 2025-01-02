package com.example.dbbackupscheduler.dto;

public record DatabaseConfigResponseDTO(
        Long id,
        String databaseType,
        String host,
        int port,
        String databaseName,
        String customUrl
) {
}
