package com.example.dbbackupscheduler.service;

import com.example.dbbackupscheduler.dto.DatabaseConfigDTO;
import com.example.dbbackupscheduler.dto.DatabaseConfigResponseDTO;
import com.example.dbbackupscheduler.model.DatabaseConfigModel;
import com.example.dbbackupscheduler.repository.DatabaseConfigRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DatabaseConfigService {

    private final DatabaseConfigRepository databaseConfigRepository;

    public DatabaseConfigService(DatabaseConfigRepository databaseConfigRepository) {
        this.databaseConfigRepository = databaseConfigRepository;
    }

    public List<DatabaseConfigResponseDTO> findAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<DatabaseConfigModel> databaseConfigListPage = databaseConfigRepository.findAll(pageable);

        return databaseConfigListPage.getContent().stream()
                .map(databaseConfig -> new DatabaseConfigResponseDTO(
                        databaseConfig.getId(),
                        databaseConfig.getDatabaseType(),
                        databaseConfig.getHost(),
                        databaseConfig.getPort(),
                        databaseConfig.getDatabaseName(),
                        databaseConfig.getCustomUrl()
                )).toList();
    }

    public Optional<DatabaseConfigResponseDTO> findById(Long requestedId) {
        Optional<DatabaseConfigModel> databaseConfig = databaseConfigRepository.findById(requestedId);
        if(databaseConfig.isEmpty()) {
            return Optional.empty();
        }

        DatabaseConfigResponseDTO databaseConfigResponse = new DatabaseConfigResponseDTO(
                databaseConfig.get().getId(),
                databaseConfig.get().getDatabaseType(),
                databaseConfig.get().getHost(),
                databaseConfig.get().getPort(),
                databaseConfig.get().getDatabaseName(),
                databaseConfig.get().getCustomUrl()
        );

        return Optional.of(databaseConfigResponse);
    }

    public DatabaseConfigModel save(DatabaseConfigDTO databaseConfigDTO) {
        DatabaseConfigModel databaseConfigToSave = new DatabaseConfigModel();
        BeanUtils.copyProperties(databaseConfigDTO, databaseConfigToSave);

        return databaseConfigRepository.save(databaseConfigToSave);
    }

    public boolean delete(Long requestedId) {
        Optional<DatabaseConfigModel> databaseConfigToDelete = databaseConfigRepository.findById(requestedId);
        if(databaseConfigToDelete.isEmpty()) {
            return false;
        }

        databaseConfigRepository.delete(databaseConfigToDelete.get());

        return true;
    }

}
