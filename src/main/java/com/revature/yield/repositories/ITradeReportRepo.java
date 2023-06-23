package com.revature.yield.repositories;

import com.revature.yield.entities.TradeReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ITradeReportRepo extends JpaRepository<TradeReport, UUID> {
}
