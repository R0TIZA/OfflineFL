package ru.rotiza.offlinefl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rotiza.offlinefl.entity.user.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> { }