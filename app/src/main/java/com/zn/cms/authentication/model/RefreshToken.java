package com.zn.cms.authentication.model;

import com.zn.cms.user.model.User;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "refreshToken")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private Instant expiryDate;

}
