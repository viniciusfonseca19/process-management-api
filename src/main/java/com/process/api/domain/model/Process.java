package com.process.api.domain.model;

import com.process.api.domain.enums.ProcessStatus;
import com.process.api.domain.enums.ProcessType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "processes")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Process {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProcessType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProcessStatus status;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String payload;

    @Column(columnDefinition = "TEXT")
    private String result;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Version
    private Long version;
}