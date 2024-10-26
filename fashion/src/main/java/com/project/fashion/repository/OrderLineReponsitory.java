package com.project.fashion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.fashion.model.OrderLine;

public interface OrderLineReponsitory extends JpaRepository<OrderLine, Long> {
    @Query("SELECT MONTH(o.createdAt) AS month, COUNT(o) AS orderCount " +
            "FROM OrderLine o " +
            "WHERE YEAR(o.createdAt) = :year " +
            "GROUP BY MONTH(o.createdAt) " +
            "ORDER BY month")
    List<Object[]> countOrderLineByMonth(@Param("year") int year);

    @Query("SELECT o FROM OrderLine o WHERE YEAR(o.createdAt) = :year AND MONTH(o.createdAt) = :month")
    List<OrderLine> findOrderLineByMonth(@Param("year") int year, @Param("month") int month);
}
