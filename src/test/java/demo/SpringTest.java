package demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SpringTest {

    @Autowired
    AddressJpaRespository repo;

    @Test
    void givenPersonEntityWithIdentifier_whenAddingAddress_thenPersistWithSameIdentifier() {
        int personId = 3;
        Person person = new Person();
        person.setId(personId);
        person.setFirstName("Azhrioun");
        person.setLastName("Abderrahim");
//        session.persist(person);
        Address address = new Address();
        address.setStreet("7 avenue berlin");
        address.setCity("Tamassint");
        address.setZipode(13000);
        address.setPerson(person);
//        session.persist(address);
//        Address persistedAddress = session.find(Address.class, personId);
//        assertThat(persistedAddress.getId()).isEqualTo(personId);
        repo.save(address);
    }
}
