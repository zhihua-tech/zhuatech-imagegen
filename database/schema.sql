-- Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/
CREATE DATABASE IF NOT EXISTS zhuatech_imagegen DEFAULT CHARACTER SET utf8mb4;
USE zhuatech_imagegen;
CREATE TABLE creative_project (id BIGINT PRIMARY KEY AUTO_INCREMENT, project_name VARCHAR(120) NOT NULL, prompt_text TEXT NOT NULL, negative_prompt TEXT, style_code VARCHAR(60), aspect_ratio VARCHAR(20), brand_color VARCHAR(10), authorization_status VARCHAR(30), created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP);
CREATE TABLE generation_job (id BIGINT PRIMARY KEY AUTO_INCREMENT, project_id BIGINT NOT NULL, provider_code VARCHAR(60), provider_job_id VARCHAR(120), job_status VARCHAR(30), image_count INT NOT NULL, disclosure_enabled BOOLEAN NOT NULL DEFAULT TRUE, created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, INDEX idx_generation_project_status(project_id,job_status));
