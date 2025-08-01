package com.skax.eatool.mbb.workflow.business.dc.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.math.BigDecimal;

/**
 * MbbUser Entity
 * 
 * @author AI Assistant
 * @version 1.0
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "MBB_USER")
public class MbbUser {

    @Column(name = "USER_ID", nullable = false, length = 255)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userId;

    @Column(name = "USER_NAME", nullable = false, length = 255)
    private String userName;

    @Column(name = "EMAIL", nullable = false, length = 255)
    private String email;

    @Column(name = "CREATED_AT", nullable = false, length = 255)
    private String createdAt;

}
