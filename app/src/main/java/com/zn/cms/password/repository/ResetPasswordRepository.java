package com.zn.cms.password.repository;

import com.zn.cms.password.model.ResetPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResetPasswordRepository extends JpaRepository<ResetPassword, Long> {

    Optional<ResetPassword> findByUuid(String uuid);

    Optional<ResetPassword> findByUserId(Long userId);
}
