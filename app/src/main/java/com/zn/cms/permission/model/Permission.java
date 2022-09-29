package com.zn.cms.permission.model;

import com.zn.cms.role.model.Role;
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
@Table(name = "permission")
public class Permission {

    @Id
    private String name;

    @ManyToMany
    @JoinTable(
            name = "permissions_depends_on",
            joinColumns = { @JoinColumn(name = "permission_name", referencedColumnName = "name") },
            inverseJoinColumns = { @JoinColumn(name = "depends_on_permission_name", referencedColumnName = "name") }
    )
    private List<Permission> dependsOnPermissions;

    @ManyToMany(mappedBy = "permissions")
    private List<Role> roles;

}
