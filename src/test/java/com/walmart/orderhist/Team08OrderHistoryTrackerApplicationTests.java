package com.walmart.orderhist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.walmart.orderhist.advice.ApplicationExceptionHandler;
import com.walmart.orderhist.controller.OrderHistController;
import com.walmart.orderhist.dto.OrderHistResponse;
import com.walmart.orderhist.dto.ProductResponse;
import com.walmart.orderhist.exception.CartNotFoundException;
import com.walmart.orderhist.exception.CartServiceException;
import com.walmart.orderhist.exception.InvalidCartException;
import com.walmart.orderhist.exception.OrderNotFoundException;
import com.walmart.orderhist.exception.OrderServiceException;
import com.walmart.orderhist.service.OrderHistServiceImpl;

@SpringBootTest
class Team08OrderHistoryTrackerApplicationTests {

	@Test
	void contextLoads() {
		assertTrue(true, "The context loads successfully");
	}

	@Autowired
	private OrderHistController orderHistController;

	@Autowired
	private ApplicationExceptionHandler exceptionHandler;

	@Mock
	private OrderNotFoundException orderNotFoundException;

	@Mock
	private CartNotFoundException cartNotFoundException;

	@Mock
	private OrderServiceException orderServiceException;

	@Mock
	private CartServiceException cartServiceException;

	@Mock
	private InvalidCartException invalidCartException;

	@Mock
	private Exception genericException;

	@MockBean
	private OrderHistServiceImpl orderHistServiceImpl;

	@Test
	void getOrderHist_SuccessfulCase_ShouldReturnOkResponse() throws Exception {
		// Arrange
		String userId = "testUserId";
		OrderHistResponse mockOrderHistResponse = new OrderHistResponse();
		mockOrderHistResponse.setOrderId(123);
		mockOrderHistResponse.setDateOfOrder(new Date());
		mockOrderHistResponse.setAmount(100.0);
		mockOrderHistResponse.setModeOfPayment("Credit Card");
		mockOrderHistResponse.setPaymentStatus("Paid");
		mockOrderHistResponse.setDateOfDelivery(new Date());
		mockOrderHistResponse.setStatusOfOrder("Delivered");

		// Sample data for products
		List<ProductResponse> products = Arrays.asList(new ProductResponse(1, 10), new ProductResponse(2, 20));
		mockOrderHistResponse.setProducts(products);

		when(orderHistServiceImpl.getOrderHistory(userId)).thenReturn(mockOrderHistResponse);

		// Act
		ResponseEntity<OrderHistResponse> response = orderHistController.getOrderHist(userId);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(mockOrderHistResponse, response.getBody());
	}

	@Test
     void testHandleOrderNotFoundException() {
        when(orderNotFoundException.getMessage()).thenReturn("Order not found");

        ResponseEntity<String> response = exceptionHandler.handleOrderNotFoundException(orderNotFoundException);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Order Exception: Order not found", response.getBody());
    }

	@Test
     void testHandleCartNotFoundException() {
        when(cartNotFoundException.getMessage()).thenReturn("Cart not found");

        ResponseEntity<String> response = exceptionHandler.handleCartNotFoundException(cartNotFoundException);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Cart Exception: Cart not found", response.getBody());
    }

	@Test
     void testHandleOrderServiceException() {
        when(orderServiceException.getMessage()).thenReturn("Order service exception");

        ResponseEntity<String> response = exceptionHandler.handleOrderServiceException(orderServiceException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Order Exception: Order service exception", response.getBody());
    }

	@Test
     void testHandleCartServiceException() {
        when(cartServiceException.getMessage()).thenReturn("Cart service exception");

        ResponseEntity<String> response = exceptionHandler.handleCartServiceException(cartServiceException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Cart Exception: Cart service exception", response.getBody());
    }

	@Test
     void testHandleInvalidCartException() {
        when(invalidCartException.getMessage()).thenReturn("Invalid cart exception");

        ResponseEntity<String> response = exceptionHandler.handleInvalidCartException(invalidCartException);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Cart ID mismatch between cart and order Invalid cart exception", response.getBody());
    }

	@Test
     void testHandleRunTimeException() {
        when(genericException.getMessage()).thenReturn("Some internal error");

        ResponseEntity<String> response = exceptionHandler.handleRunTimeException(genericException);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Internal Server Error ,Please refresh the page ", response.getBody());
    }
}
