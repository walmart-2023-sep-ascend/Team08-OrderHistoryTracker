# Team08-OrderHistoryTracker

# Order History Project

The Order History Project is a Spring Boot application that retrieves information from Order API(from Team6) and Cart API(from Team4) to display order history information.

## Prerequisites

Before you begin, ensure you have the following installed:

- Java Development Kit (JDK) 17
- Apache Maven


Follow these steps to set up and run the project:

## Commands

   ```bash
   git clone https://github.com/walmart-2023-sep-ascend/Team08-OrderHistoryTracker.git
   cd Team08-OrderHistoryTracker

Build the project using Maven:

mvn clean install -DCART_URI=<CART_URI> -DORDER_URI=<ORDER_URI>

Replace <CART_URI> and <ORDER_URI> with the actual URLs for your Cart API and Order API.

3.Run the application:

```bash
java -jar target/order-history-tracker.jar![image](https://github.com/walmart-2023-sep-ascend/Team08-OrderHistoryTracker/assets/140786982/2455d10a-12b1-4d5b-ac03-c673e8a464e4)




