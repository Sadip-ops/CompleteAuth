--liquibase formatted sql

-- changeset Sadip_Khadka:1_create_refresh_token_table
CREATE TABLE refresh_token (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               token VARCHAR(255) NOT NULL UNIQUE,
                               expiry_date TIMESTAMP NOT NULL,
                               user_id BIGINT NOT NULL,
                               CONSTRAINT fk_refresh_token_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
