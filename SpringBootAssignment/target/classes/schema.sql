CREATE TABLE job (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    status VARCHAR(255)
);

CREATE TABLE s3_object (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    bucket_name VARCHAR(255),
    object_key VARCHAR(255)
);

CREATE TABLE file_metadata (
    id INT AUTO_INCREMENT PRIMARY KEY,
    bucket_name VARCHAR(255) NOT NULL,
    file_name VARCHAR(255) NOT NULL
);
