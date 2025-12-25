package com.user.identity.repository.entity;

import java.util.Set;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "roles")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role {

    @Id
    @Column(name = "role_name", nullable = false, unique = true)
    String name;

    @Column(name = "description")
    String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "role_permissions", // Join table between roles and permissions
            joinColumns = @JoinColumn(name = "role_id"), // Foreign key from role_permissions to roles
            inverseJoinColumns =
                    @JoinColumn(name = "permission_id") // Foreign key from role_permissions to permissions
            )
    Set<Permission> permissions;
}
