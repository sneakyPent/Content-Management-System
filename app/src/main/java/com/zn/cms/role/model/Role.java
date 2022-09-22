package com.zn.cms.role.model;

import com.zn.cms.user.model.User;
import com.zn.cms.permission.model.Permission;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    @ManyToMany
    @JoinTable(name = "roles_permissions",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id"))
    private List<Permission> permissions;

    private String name;


}
