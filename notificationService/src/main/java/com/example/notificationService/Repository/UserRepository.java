package com.example.notificationService.Repository;

import com.example.notificationService.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    @Query("SELECT u.email FROM User u WHERE u.isEnabled = true")
    List<String> findAllEmailsAndNotificationEnabled();
}
