package com.example.task.service.data.model;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Date;

import static java.util.Objects.isNull;

/**
 * Task entity.
 */
@Entity
@Data
public class Task {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @NotEmpty(message = "name can't be empty")
    private String name = "";
    private String description = "";
    private Long user;
    private boolean complete;
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Temporal(TemporalType.TIMESTAMP)
    private Date assigned;
    @Temporal(TemporalType.TIMESTAMP)
    private Date completed;

    public Task(String name, String description, Long userId) {
        this.name = name;
        this.description = description;
        this.user = userId;
    }

    // JPA
    public Task() {}

    @PrePersist
    void initCreationDate() {
        if(isNull(created))
            created = new Date();
    }


}
