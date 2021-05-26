package com.example.inatel.stock.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "stocks",uniqueConstraints={@UniqueConstraint(columnNames={"name"})})
public class Stock {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO,generator="native")
	@GenericGenerator(name = "native",strategy = "native")
	private long id;

	@Column(name = "name")
	private String name;

	@Column(name = "quotes")
	private float quotes;

	public Stock(String name, float quotes) {
		this.name = name;
		this.quotes = quotes;
	}

	public Stock() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getQuotes() {
		return quotes;
	}

	public void setQuotes(float quotes) {
		this.quotes = quotes;
	}	
}
