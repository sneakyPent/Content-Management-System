package com.zn.cms.role.model;

import com.zn.cms.user.model.User;
import com.zn.cms.permission.model.Permission;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "role")
public class Role {

    @Id
    private String name;

    @ManyToMany(mappedBy = "roles")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<User> users;

    @ManyToMany
    @JoinTable(name = "roles_permissions",
            joinColumns = @JoinColumn(name = "role_name", referencedColumnName = "name"),
            inverseJoinColumns = @JoinColumn(name = "permission_name", referencedColumnName = "name"))
    private List<Permission> permissions;


}
