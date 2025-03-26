drop database if exists cubednloader;
create database cubednloader;
use cubednloader;

-- 오브젝트 저장소 서버 정보
CREATE TABLE minio_servers (
	id    INT PRIMARY KEY AUTO_INCREMENT,
    ip    VARCHAR(45) NOT NULL,
    port  INT NOT NULL
);

-- 오브젝트 저장소 버킷 정보
CREATE TABLE minio_buckets (
	id           INT PRIMARY KEY AUTO_INCREMENT,
    server_id    INT NOT NULL,
    bucket_name  VARCHAR(255) NOT NULL,
    FOREIGN KEY (server_id) REFERENCES minio_servers(id) ON DELETE CASCADE,
    UNIQUE (server_id, bucket_name)  -- 서버별로 버킷명이 중복되지 않도록 UNIQUE 추가
);

-- 오브젝트 저장 정보 테이블
CREATE TABLE minio_objects (
	id            INT PRIMARY KEY AUTO_INCREMENT,
    bucket_id     INT NOT NULL,
    object_name   VARCHAR(255) UNIQUE NOT NULL,
    file_name     VARCHAR(255) NOT NULL,
    metadata      LONGTEXT DEFAULT NULL COMMENT '"uploader": "user123", "file_size": 204800, "tags": ["image", "profile", "png"] 등, 확장 가능한 JSON 데이터 (메타데이터 저장용)',
    FOREIGN KEY (bucket_id) REFERENCES minio_buckets(id) ON DELETE CASCADE
);

-- 다운로드 처리 정보 테이블
CREATE TABLE download_processing (
    id            INT PRIMARY KEY AUTO_INCREMENT,
    download_url  TEXT NOT NULL,
    object_id     INT,
    FOREIGN KEY (object_id) REFERENCES minio_objects(id) ON DELETE SET NULL
);

-- 다운로드 요청 로그 테이블
CREATE TABLE download_url_requests (
    id            INT PRIMARY KEY AUTO_INCREMENT,
    download_url  TEXT NOT NULL,
    client_ip     VARCHAR(45)
);
