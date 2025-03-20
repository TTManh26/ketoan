package org.hello.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "access_token", schema = "accounting_system")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "accessToken")
public class AccessToken implements Serializable {

    @Id
    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "issued_at")
    private LocalDateTime issuedAt;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;
}