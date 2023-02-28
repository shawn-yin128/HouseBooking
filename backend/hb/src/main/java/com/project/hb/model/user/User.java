package com.project.hb.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.hb.model.Token;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(allowSetters = true, value = {"password"})
public class User {
    @Id
    private String username;
    private String password;
    @JsonIgnore
    private boolean enabled;
}
