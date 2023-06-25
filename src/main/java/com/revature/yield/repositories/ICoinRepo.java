package com.revature.yield.repositories;

import com.revature.yield.entities.Coin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ICoinRepo extends JpaRepository<Coin, UUID> {
}
