package com.vsquad.iroas.repository;

import com.vsquad.iroas.aggregate.entity.Player;
import com.vsquad.iroas.aggregate.entity.Ranking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RankingRepository extends JpaRepository<Ranking, Long> {

    Page<Ranking> findByCreatorMapIdAndClearCountIsNot(Long creatorMapId, Integer count, Pageable pageable);

    Optional<Ranking> findByPlayerAndCreatorMapId(Player player, Long creatorMapId);
}
