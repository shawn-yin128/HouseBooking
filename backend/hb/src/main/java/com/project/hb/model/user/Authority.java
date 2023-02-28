package com.project.hb.model.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "authority")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Authority {
    @Id
    private String username;
    private String authority;
}
