package com.ecommerce.ms.exception;

public class DuplicateResourceException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6076433089497699197L;

	public DuplicateResourceException(String message) {
        super(message);
    }
}
