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

import com.example.inatel.stock.exception.GenericError;
import com.example.inatel.stock.exception.NotFoundException;
import com.example.inatel.stock.exception.SucceededWarning;
import com.example.inatel.stock.model.Stock;
import com.example.inatel.stock.reporitory.StockRepository;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class StockController {

	@Autowired
	StockRepository stockRepository;
	String message;
	List<Stock> stocksList;

	// Read all stocks
	@GetMapping("/stock")
	public List<Stock> getAllStocks() {
		return this.stockRepository.findAll();
	}

	// Read the stocks by name
	@GetMapping("/stockByName")
	public ResponseEntity<List<Stock>> getAllStocks(@RequestParam("name") String name) {
		try {
			stocksList = new ArrayList<Stock>();
			stockRepository.findByName(name).forEach(stocksList::add);
			if (stocksList.isEmpty()) {
				message = "The Stock cound not be found with name: "+name;
			}
			return new ResponseEntity<>(stocksList, HttpStatus.OK);
		} catch (Exception e) {			
			throw new GenericError("Something went wrong and was not possible to find the Stock! "+ message);
		}
	}

	// Create new Stock
	@PostMapping("/stock")
	public ResponseEntity<Stock> createStock(@RequestBody Stock stock) {
		stocksList = new ArrayList<Stock>();
		stockRepository.findByName(stock.getName()).forEach(stocksList::add);
		try {
			if (stock.getName().isEmpty() || stock.getName().isBlank()) {
				throw new NotFoundException("The Stock name can not be blank or empty!");			
			} else if (stocksList.size()>=1) {
				throw new GenericError("Already exist a Stock created with the name: "+stock.getName());				
			} else {
				Stock stocknew = stockRepository.save(new Stock(stock.getName(), stock.getQuotes()));
				throw new SucceededWarning("The following Stock was successfully created!" +stock.getName());
			}
		} catch (Exception e) {
			throw new GenericError("Something went wrong and was not possible to insert the Stock! "+stock.getName());
		}
	}

	// Update quotes
	@PutMapping("/stockUpdate")
	public ResponseEntity<Stock> updateTutorial(@RequestParam("name") String name, @RequestBody Stock stockUpdate) {
		List<Stock> stock = stockRepository.findByName(name);

		if (stock.isEmpty()) {
			throw new NotFoundException("The Stock name can not be blank or empty!");

		} else {
			Stock s = new Stock();
			s.setName(name);
			s.setQuotes(stockUpdate.getQuotes());
			throw new SucceededWarning("The following Stock was successfully updated!" + name);
		}
	}

	// Delete by Name
	@Transactional
	@DeleteMapping("/stock")
	public ResponseEntity<HttpStatus> deleteStock(@RequestParam("name") String name) {
		try {
			stockRepository.deleteByName(name);	
			throw new SucceededWarning("The following Stock was successfully deleted!" + name);
		} catch (Exception e) {
			throw new GenericError("Something went wrong and was not possible to delete the following Stock! "+name);
		}
	}

}
