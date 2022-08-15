package com.app.api.user.repository;

import com.app.api.user.entity.UserWithdraw;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserWithdrawRepository extends JpaRepository<UserWithdraw, Long> {
}
