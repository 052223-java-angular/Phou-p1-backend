package com.revature.yield.repositories;

import com.revature.yield.entities.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface ITradeRecordRepository extends JpaRepository<Trade, UUID> {

    List<Trade> findAllByUserId(UUID userId);

    List<Trade> findAllByReportId(String reportId);

    @Modifying
    @Query("SELECT t.reportId FROM Trade t WHERE t.user.id = :userId")
    Set<String> findReportIdsByUserId(@Param("userId") UUID userId);

    @Modifying
    @Query("DELETE FROM Trade t WHERE t.id = :id")
    int deleteByIdReturnCount(@Param("id") UUID id);

    List<Trade> findAllByIdAndUserId(UUID recordId, UUID userId);

}
