package demo.repository;

import demo.TestData;
import demo.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CustomerRepositoryIT {

    @Autowired
    private CustomerRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll().block();
    }

    @Test
    void shouldSaveCustomer() {
        Customer customer = TestData.newCustomer();

        Customer savedCustomer = repository.save(customer).block();

        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getId()).isNotNull();
        assertThat(savedCustomer.getFirstName()).isEqualTo(customer.getFirstName());
        assertThat(savedCustomer.getLastName()).isEqualTo(customer.getLastName());
    }

    @Test
    void shouldFindByLastName() {
        Customer customer = TestData.newCustomer();
        Customer customer1 = TestData.newCustomer();

        repository.saveAll(Arrays.asList(customer, customer1)).blockLast();

        List<Customer> customers = repository.findByLastName("Doe")
                .collectList()
                .block();

        assertThat(customers).isNotNull();
        assertThat(customers).hasSize(2);
        assertThat(customers).extracting(Customer::getLastName).containsOnly("Doe");
    }

    @Test
    void shouldFIndById() {
        Customer customer = TestData.newCustomer();

        Customer savedCustomer = repository.save(customer).block();

        Customer foundCustomer = repository.findById(savedCustomer.getId()).block();

        assertThat(foundCustomer).isNotNull();
        assertThat(foundCustomer.getId()).isEqualTo(savedCustomer.getId());
        assertThat(foundCustomer.getFirstName()).isEqualTo(customer.getFirstName());
        assertThat(foundCustomer.getLastName()).isEqualTo(customer.getLastName());
    }
}
