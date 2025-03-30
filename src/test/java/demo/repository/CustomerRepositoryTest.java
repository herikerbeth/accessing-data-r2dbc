package demo.repository;

import demo.TestData;
import demo.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import reactor.core.publisher.Flux;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CustomerRepositoryTest {

    @MockitoBean
    private CustomerRepository repository;

    @Test
    void shouldFindByLastName() {
        Customer customer = TestData.newCustomer();
        when(repository.findByLastName("Doe")).thenReturn(Flux.just(customer));

        Flux<Customer> customerResult = repository.findByLastName("Doe");

        assertThat(customerResult.collectList().block())
                .isNotNull()
                .hasSize(1)
                .contains(customer);
    }
}
