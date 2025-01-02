package com.example.dbbackupscheduler.repository;

import com.example.dbbackupscheduler.model.DatabaseConfigModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DatabaseConfigRepository extends JpaRepository<DatabaseConfigModel, Long> {
}
