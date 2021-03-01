package com.example.inatel.stock.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.inatel.stock.model.Stock;
import com.example.inatel.stock.reporitory.StockRepository;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class StockController {

	@Autowired
	StockRepository stockRepository;

	@GetMapping("/stock")
	public List<Stock> getAllStocks() {
		return this.stockRepository.findAll();
	}

	@GetMapping("/stockByName")
	public ResponseEntity<List<Stock>> getAllStocks(@RequestParam("name") String name) {
		try {
			List<Stock> stocksList = new ArrayList<Stock>();
			stockRepository.findByName(name).forEach(stocksList::add);
			if (stocksList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(stocksList, HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/stock")
	public ResponseEntity<Stock> createStock(@RequestBody Stock stock) {
		try {
			if(stock.getName().isEmpty()||stock.getName().isBlank()) {
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}else {
				Stock stocknew = stockRepository.save(new Stock(stock.getName(), stock.getQuotes()));
				return new ResponseEntity<>(stocknew, HttpStatus.CREATED);
			}
		
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/stockUpdate")
	public ResponseEntity<Stock> updateTutorial(@RequestParam("name") String name, @RequestBody Stock stockUpdate) {
		List<Stock> stock = stockRepository.findByName(name);

		if (stock.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

		} else {
			Stock s = new Stock();
			s.setName(name);
			s.setQuotes(stockUpdate.getQuotes());
			return new ResponseEntity<>(stockRepository.save(s), HttpStatus.OK);
		}
	}

	@Transactional
	@DeleteMapping("/stock")
	public ResponseEntity<HttpStatus> deleteStock(@RequestParam("name") String name) {
		try {
			stockRepository.deleteByName(name);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
