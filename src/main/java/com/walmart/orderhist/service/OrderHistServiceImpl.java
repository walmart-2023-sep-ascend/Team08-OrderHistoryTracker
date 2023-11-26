package com.walmart.orderhist.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.walmart.orderhist.dao.OrderHistDao;
import com.walmart.orderhist.dto.CartResponse;
import com.walmart.orderhist.dto.OrderHistResponse;
import com.walmart.orderhist.dto.OrderResponse;
import com.walmart.orderhist.exception.CartNotFoundException;
import com.walmart.orderhist.exception.CartServiceException;
import com.walmart.orderhist.exception.InvalidCartException;
import com.walmart.orderhist.exception.OrderNotFoundException;
import com.walmart.orderhist.exception.OrderServiceException;
import com.walmart.orderhist.rest.CartAPI;
import com.walmart.orderhist.rest.OrderAPI;

@Service
public class OrderHistServiceImpl implements OrderHistService {

	@Autowired
	private OrderHistDao orderHistDao;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private CartAPI cartAPI;

	@Autowired
	private OrderAPI orderAPI;

	private static final Logger log = LoggerFactory.getLogger(OrderHistServiceImpl.class);

	public OrderHistResponse getOrderHistory(String userId) throws OrderNotFoundException, CartServiceException,
			OrderServiceException, CartNotFoundException, InvalidCartException {

		CartResponse cartResponse = getCartResponse(userId);
		OrderResponse orderResponse = getOrderResponse(userId);

		if (cartResponse.getCartId().equals(orderResponse.getCartId())) {

			return MaptoOrderHistResponse(cartResponse, orderResponse);

		} else {
			log.info("Cart ID mismatch between cart and order for user with ID: {}" , userId);
			throw new InvalidCartException("Cart ID mismatch between cart and order for user with ID: " + userId);
		}

	}

	public CartResponse getCartResponse(String userId) throws CartServiceException, CartNotFoundException {

		CartResponse cartResponse = cartAPI.getCartDetails(userId);

		if (cartResponse == null) {
			// Assuming CartNotFoundException is a custom exception class
			log.info("Cart not found for user with ID: {}" , userId);
			throw new CartNotFoundException("Cart not found for user with ID: " + userId);
		}

		return cartResponse;
	}

	public OrderResponse getOrderResponse(String userId) throws OrderNotFoundException, OrderServiceException {

		OrderResponse orderResponse = orderAPI.getOrderDetails(userId);

		if (orderResponse == null) {
			log.info("Order not found for user with ID:{} " , userId);
			throw new OrderNotFoundException("Order not found for user with ID: " + userId);
		}

		return orderResponse;
	}

	public OrderHistResponse MaptoOrderHistResponse(CartResponse cartResponse, OrderResponse orderResponse) {

		OrderHistResponse orderHistResponse = new OrderHistResponse();
		// Map OrderHistResponse from both CartResponse and OrderResponse
		orderHistResponse.setOrderId(orderResponse.getOrderId());
		orderHistResponse.setDateOfOrder(orderResponse.getDateOfOrder());
		orderHistResponse.setAmount(orderResponse.getAmount());
		orderHistResponse.setModeOfPayment(orderResponse.getModeOfPayment());
		orderHistResponse.setPaymentStatus(orderResponse.getPaymentStatus());
		orderHistResponse.setDateOfDelivery(orderResponse.getDateOfDelivery());
		orderHistResponse.setStatusOfOrder(orderResponse.getStatusOfOrder());
		orderHistResponse.setProducts(cartResponse.getProducts());
		return orderHistResponse;

	}

}
