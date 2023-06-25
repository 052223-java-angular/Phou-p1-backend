package com.revature.yield.repositories;

import com.revature.yield.entities.TradeReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface ITradeReportRepo extends JpaRepository<TradeReport, UUID> {

    @Modifying
    @Query("DELETE FROM TradeReport t WHERE t.id = :uid")
    int deleteByIdReturnCount(@Param("uid") UUID uid);

}
