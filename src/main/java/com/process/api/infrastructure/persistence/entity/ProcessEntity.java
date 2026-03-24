package com.process.api.infrastructure.persistence.entity;

import com.process.api.domain.enums.ProcessStatus;
import com.process.api.domain.enums.ProcessType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "processes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProcessEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ProcessType type;

    @Enumerated(EnumType.STRING)
    private ProcessStatus status;

    @Column(columnDefinition = "TEXT")
    private String payload;

    @Column(columnDefinition = "TEXT")
    private String result;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Version
    private Long version;
}