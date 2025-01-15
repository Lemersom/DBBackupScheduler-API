# Database Backup Scheduler API

This project is a web application developed in Java Spring, designed to manage and automate the backup process for multiple databases. The system features a PostgreSQL database for storing configuration details, a CRUD API for managing database configurations, and a scheduler that periodically triggers backups by calling the external API [DatabaseBackup-API](https://github.com/Lemersom/DatabaseBackup-API).

The application also implements encryption for sensitive information, ensuring that database credentials are securely stored.

# Technologies

* Back-End: Java with Spring Boot
* Database: PostgreSQL
* Scheduler: Spring's @Scheduled with cron expressions
* Data Security: AES encryption for storing sensitive data
* Integration: REST API calls to the [DatabaseBackup-API](https://github.com/Lemersom/DatabaseBackup-API).

# Usage

* Set up the application using the application-dev.properties file as a reference.
* Add the necessary encryption keys to the configuration file for encrypting and decrypting sensitive data.

* The application provides endpoints to manage database configurations. These configurations are stored in the PostgreSQL database with encrypted credentials.

  Example request body for `POST /api/database-config`:
  ```
  {
    "databaseType": "postgres",
    "host": "localhost",
    "port": 5432,
    "databaseName": "testdb",
    "customUrl": "jdbc:postgresql://localhost:5432/testdb",
    "username": "dbuser",
    "password": "dbpassword"
  }
  ```

* The scheduler periodically retrieves all database configurations from the PostgreSQL database and sends requests to the [DatabaseBackup-API](https://github.com/Lemersom/DatabaseBackup-API) to perform backups. Scheduler configuration can be customized using cron expressions in the application properties file.
    ```
      # Every day at 2 AM (America/Sao_Paulo timezone)
      cron.string=0 0 2 * * *
    ```

# Related Repositories

* [DatabaseBackup-API](https://github.com/Lemersom/DatabaseBackup-API)
* [CsvToSqlConverter-API](https://github.com/Lemersom/CsvToSqlConverter-API)
