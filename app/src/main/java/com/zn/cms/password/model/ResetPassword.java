package com.zn.cms.password.model;

import lombok.*;
import javax.persistence.*;
import java.time.Instant;

@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name= "reset_password")
public class ResetPassword {

    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String uuid;
    @Column(unique = true)
    private Long userId;

    private Instant expirationDate;

}
