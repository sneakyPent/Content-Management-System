package com.zn.cms.role.repository;

import com.zn.cms.role.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String roleName);

    List<Role> findAllByNameIn(List<String> roleNames);
}
