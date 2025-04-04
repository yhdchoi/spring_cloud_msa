package com.yhdc.video_catalog_server.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Table(name = "video_info")
@Entity
public class VideoInfo extends EntityDateAudit {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.AUTO)
    @Column(name = "id", columnDefinition = "BINARY(16)", updatable = false, nullable = false, unique = true)
    private UUID videoInfoId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "file_path", nullable = false)
    private String filePath;

}
