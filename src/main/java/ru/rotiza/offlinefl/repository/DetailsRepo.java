package ru.rotiza.offlinefl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rotiza.offlinefl.entity.user.UserDetails;

import java.util.UUID;

@Repository
public interface DetailsRepo extends JpaRepository<UserDetails, UUID> {
}
