package com.example.adapter.out.persistence;

import java.time.OffsetDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("url_mapping")
record URLMappingEntity(@Id String id,
                        @Version
                        Long version,
                        @Column("long_url")
                        String longUrl,
                        @Column("short_url")
                        String shortUrl,
                        @Column("created_at")
                        OffsetDateTime createdAt) {
}
