package com.Binusa.BawasluServer.repository;

import com.Binusa.BawasluServer.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    UserModel findByUsername(String username);
}
