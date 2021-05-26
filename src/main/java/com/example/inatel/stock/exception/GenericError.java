package com.example.inatel.stock.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)

public class GenericError  extends RuntimeException {
		public GenericError(String message) {
			super(message);
		}
	}

