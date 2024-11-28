package com.syncbox.models.entities;

import jakarta.persistence.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    private long roleId;
    private String roleName;
    @OneToMany(mappedBy="role", fetch=FetchType.EAGER)
    private List<User> users;
    public Role(long roleId) {
        this.roleId = roleId;
    }
}
