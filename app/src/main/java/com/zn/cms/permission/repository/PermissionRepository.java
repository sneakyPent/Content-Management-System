package com.zn.cms.permission.repository;

import com.zn.cms.permission.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    public Optional<Permission> findByName(String permissionName);

}
