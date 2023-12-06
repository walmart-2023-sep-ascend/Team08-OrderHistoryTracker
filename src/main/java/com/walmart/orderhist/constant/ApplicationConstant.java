package com.walmart.orderhist.constant;

public class ApplicationConstant {

	private ApplicationConstant() {
		throw new IllegalStateException("Utility class");
	}

	public static final String ID = "id";

	public static final String USER_ID = "userId";

	public static final String CART_EXCEPTION = "Cart Exception: ";

	public static final String ORDER_EXCEPTION = "Order Exception: ";
	
	public static final String INVALID_CART_EXCEPTION  = "Cart ID mismatch between cart and order ";

}
