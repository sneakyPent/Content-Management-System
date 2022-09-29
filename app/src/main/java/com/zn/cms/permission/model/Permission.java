package com.zn.cms.permission.model;

import com.zn.cms.role.model.Role;
import lombok.*;

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
    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "permissions_depends_on",
            joinColumns = { @JoinColumn(name = "permission_name", referencedColumnName = "name") },
            inverseJoinColumns = { @JoinColumn(name = "depends_on_permission_name", referencedColumnName = "name") }
    )
    private List<Permission> dependsOnPermissions;

    @ManyToMany(mappedBy = "permissions")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Role> roles;

}
