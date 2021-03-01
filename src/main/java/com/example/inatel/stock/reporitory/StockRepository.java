package com.example.inatel.stock.reporitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.inatel.stock.model.Stock;

public interface StockRepository extends JpaRepository<Stock, String> {

	List<Stock> findByName(String name);

	void deleteByName(String name);
}
