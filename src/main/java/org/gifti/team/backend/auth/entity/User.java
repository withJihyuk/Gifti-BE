package org.gifti.team.backend.auth.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.gifti.team.backend.auth.type.Provider;
import org.gifti.team.backend.auth.type.Role;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String name;
    @Column
    private String phone;
    @Column(nullable = false)
    private Provider provider;
    @Column(nullable = false)
    private Role role;
    @Column
    private String sub;
    @Column
    private final Date createdAt = new Date();

    @Builder
    public User(String email, String name, Provider provider, String sub, Role role) {
        this.email = email;
        this.name = name;
        this.provider = provider;
        this.sub = sub;
        this.role = Role.USER;
    }
}
