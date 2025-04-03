package demo.persistence.repository;


import demo.persistence.model.Passport;
import demo.persistence.model.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class UnidirectionalOneToOneTest {

    @Autowired
    private PersonJpaRepository personJpaRepository;

    @Autowired
    private PassportJpaRepository passportJpaRepository;

    @Test
    @Transactional
    void givenPersonWithNonExistingPassport_whenCreatePerson_thenThrowsException() {
        Passport notExistingPassport = Passport.builder()
                .id(10L)
                .build();
        Person person = Person.builder()
                .name("Pepe")
                .passport(notExistingPassport)
                .build();
        Executable exe = () -> personJpaRepository.saveAndFlush(person);
        assertThrows(DataAccessException.class, exe);
    }

    @Test
    @Sql("/insertPassport1.sql")
    @Transactional
    void givenPersonWithExistingPassport_whenCreatePerson_thenCreatedIsNotNull() {
        Passport existingPassport = passportJpaRepository.getReferenceById(1L);
        Person person = Person.builder()
                .name("Pepe")
                .passport(existingPassport)
                .build();
        Person created = personJpaRepository.saveAndFlush(person);
        assertThat(created).isNotNull();
        /*
            insert into persons (id, name, passport_id) values (default, ?, ?)
            binding parameter [1] as [VARCHAR] - [Pepe]
            binding parameter [2] as [BIGINT] - [1]
         */
    }

    @Test
    @Sql("/insertPassport1.sql")
    @Sql("/insertPerson1WithoutPassport.sql")
    @Transactional
    void givenPersonWithoutPassport_whenUpdatePersonWithPassport1_thenPassportIs1() {
        // GIVEN
        Passport existingPassport = passportJpaRepository.getReferenceById(1L);
        Person personWithoutPassport = personJpaRepository.getReferenceById(1L);
        // WHEN
        personWithoutPassport.setPassport(existingPassport);
        Person saved = personJpaRepository.saveAndFlush(personWithoutPassport);
        // THEN
        assertThat(saved).isNotNull();
        assertThat(saved.getPassport().getId()).isEqualTo(1L);
        /*
        select person0_.id as id1_3_0_, person0_.name as name2_3_0_, person0_.passport_id as passport3_3_0_, passport1_.id as id1_2_1_, passport1_.number as number2_2_1_ from persons person0_ left outer join passports passport1_ on person0_.passport_id=passport1_.id where person0_.id=?
        binding parameter [1] as [BIGINT] - [1]

        update persons set name=?, passport_id=? where id=?
        binding parameter [1] as [VARCHAR] - [Pepe Indocumentado]
        binding parameter [2] as [BIGINT] - [1]
        binding parameter [3] as [BIGINT] - [1]
         */
    }

    @Test
    @Sql("/insertPassport1.sql")
    @Sql("/insertPerson2WithPassport2.sql")
    @Transactional
    void givenPersonWithPassport_whenUpdatePersonPassport_thenIsUpdated() {
        // GIVEN
        long updatePassportId = 1L;
        Passport existingPassport = passportJpaRepository.getReferenceById(updatePassportId);
        Person personWithPassport = personJpaRepository.findById(2L)
                .orElseThrow(() -> new EntityNotFoundException("Person not found"));
        // WHEN
        personWithPassport.setPassport(existingPassport);
        Person saved = personJpaRepository.saveAndFlush(personWithPassport);
        // THEN
        assertThat(saved).isNotNull();
        assertThat(saved.getPassport().getId()).isEqualTo(updatePassportId);
        /*
        select person0_.id as id1_3_0_, person0_.name as name2_3_0_, person0_.passport_id as passport3_3_0_, passport1_.id as id1_2_1_, passport1_.number as number2_2_1_ from persons person0_ left outer join passports passport1_ on person0_.passport_id=passport1_.id where person0_.id=?
        binding parameter [1] as [BIGINT] - [2]

        update persons set name=?, passport_id=? where id=?
        binding parameter [1] as [VARCHAR] - [Antonio Documentado]
        binding parameter [2] as [BIGINT] - [1]
        binding parameter [3] as [BIGINT] - [2]
         */
    }

    @Test
    @Sql("/insertPerson2WithPassport2.sql")
    @Transactional
    void givenPersonWithEmployee_whenUpdatePassportNull_thenUpdated() {
        // GIVEN
        Person personWithPassport = personJpaRepository.findById(2L)
                .orElseThrow(() -> new EntityNotFoundException("Person not found"));
        // WHEN
        personWithPassport.setPassport(null);
        Person saved = personJpaRepository.saveAndFlush(personWithPassport);
        // THEN
        assertThat(saved).isNotNull();
        assertThat(saved.getPassport()).isNull();
        /*
        select person0_.id as id1_3_0_, person0_.name as name2_3_0_, person0_.passport_id as passport3_3_0_, passport1_.id as id1_2_1_, passport1_.number as number2_2_1_ from persons person0_ left outer join passports passport1_ on person0_.passport_id=passport1_.id where person0_.id=?
        binding parameter [1] as [BIGINT] - [2]

        update persons set name=?, passport_id=? where id=?
        binding parameter [1] as [VARCHAR] - [Antonio Documentado]
        binding parameter [2] as [BIGINT] - [null]
        binding parameter [3] as [BIGINT] - [2]
         */
    }

}
