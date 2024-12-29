CREATE TABLE url_mapping
(
    id         VARCHAR(255) PRIMARY KEY,
    version    BIGINT                DEFAULT 0 NOT NULL, -- The version field for optimistic locking
    long_url   VARCHAR(255) NOT NULL,
    short_url  VARCHAR(255) NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);