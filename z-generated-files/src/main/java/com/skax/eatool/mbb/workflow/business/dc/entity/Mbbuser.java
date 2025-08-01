package com.skax.eatool.mbb.workflow.business.dc.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.math.BigDecimal;

/**
 * Mbbuser Entity
 * 
 * @author AI Assistant
 * @version 1.0
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "MBBUSER")
public class Mbbuser {

    @Column(name = "USERID", nullable = false, length = 64)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userid;

    @Column(name = "USERNAME", nullable = false, length = 50)
    private String username;

    @Column(name = "PASSWORD", nullable = false, length = 100)
    private String password;

    @Column(name = "EMAIL", length = 100)
    private String email;

    @Column(name = "ROLE", nullable = false, length = 20)
    private String role;

    @Column(name = "STATUS", nullable = false, length = 10)
    private String status;

    @Column(name = "CREATEDAT", nullable = false, length = 26)
    private LocalDateTime createdat;

    @Column(name = "UPDATEDAT", length = 26)
    private LocalDateTime updatedat;

}
