package com.mobin.book.role;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mobin.book.common.BaseAuditingEntity;
import com.mobin.book.user.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
public class Role extends BaseAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(unique = true)
    private String name;
    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private List<User> users;

    public Role(LocalDateTime createdDate, LocalDateTime lastModifiedDate, UUID id, String name, List<User> users) {
        super(createdDate, lastModifiedDate);
        this.id = id;
        this.name = name;
        this.users = users;
    }

    public Role() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
