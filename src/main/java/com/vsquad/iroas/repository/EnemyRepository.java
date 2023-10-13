package com.vsquad.iroas.repository;

import com.vsquad.iroas.aggregate.entity.Enemy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnemyRepository extends JpaRepository<Long, Enemy> {
}