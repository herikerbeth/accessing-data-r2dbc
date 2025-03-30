package demo;

import demo.model.Customer;

public class TestData {
    public static Customer newCustomer() {
        return new Customer("John", "Doe");
    }
}
