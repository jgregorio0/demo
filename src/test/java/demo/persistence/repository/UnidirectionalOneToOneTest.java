package demo.persistence.repository;


import demo.persistence.model.Department;
import demo.persistence.model.Employee;
import demo.persistence.model.Passport;
import demo.persistence.model.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.internal.exceptions.ExceptionIncludingMockitoWarnings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
//
//    @Test
//    @Sql("/insertEmployee1.sql")
//    @Sql("/insertEmployee2AndDepartment2WithEmployee2.sql")
//    @Sql("/insertEmployee3.sql")
//    @Transactional
//    void givenDepartmentWith1Employee_whenAdding2EmployeesIntoDepartment_thenEmployeesSizeIs3() {
//        // GIVEN
//        Employee employee1 = Employee.builder()
//                .id(1L)
//                .build();
//        Employee employee2 = Employee.builder()
//                .id(2L)
//                .build();
//        Employee employee3 = Employee.builder()
//                .id(3L)
//                .build();
//        Department department2WithEmployee2 = departmentJpaRepository.getReferenceById(2L);
//        // WHEN
//        department2WithEmployee2.getEmployees().clear();
//        department2WithEmployee2.getEmployees().addAll(List.of(employee1, employee2, employee3));
//        Department saved = departmentJpaRepository.saveAndFlush(department2WithEmployee2);
//        // THEN
//        assertThat(saved).isNotNull();
//        assertThat(saved.getEmployees().size()).isEqualTo(3);
//        /*
//        select department0_.id as id1_0_0_, department0_.name as name2_0_0_ from departments department0_ where department0_.id=?
//        binding parameter [1] as [BIGINT] - [2]
//
//        select employees0_.department_id as departme3_1_0_, employees0_.id as id1_1_0_, employees0_.id as id1_1_1_, employees0_.name as name2_1_1_ from employees employees0_ where employees0_.department_id=?
//        binding parameter [1] as [BIGINT] - [2]
//
//        select employee0_.id as id1_1_0_, employee0_.name as name2_1_0_ from employees employee0_ where employee0_.id=?
//        binding parameter [1] as [BIGINT] - [1]
//
//        select employee0_.id as id1_1_0_, employee0_.name as name2_1_0_ from employees employee0_ where employee0_.id=?
//        binding parameter [1] as [BIGINT] - [3]
//
//        update employees set department_id=? where id=?
//        binding parameter [1] as [BIGINT] - [2]
//        binding parameter [2] as [BIGINT] - [1]
//
//        update employees set department_id=? where id=?
//        binding parameter [1] as [BIGINT] - [2]
//        binding parameter [2] as [BIGINT] - [3]
//         */
//    }
//
//    @Test
//    @Sql("/insertEmployee2AndDepartment2WithEmployee2.sql")
//    @Transactional
//    void givenDepartmentWith1Employee_whenRemoving1EmployeesFromDepartment_thenEmployeesSizeIs0() {
//        // GIVEN
//        Department department2WithEmployee2 = departmentJpaRepository.getReferenceById(2L);
//        // WHEN
//        department2WithEmployee2.getEmployees().clear();
//        Department saved = departmentJpaRepository.saveAndFlush(department2WithEmployee2);
//        // THEN
//        assertThat(saved).isNotNull();
//        assertThat(saved.getEmployees().size()).isEqualTo(0);
//        /*
//        select department0_.id as id1_0_0_, department0_.name as name2_0_0_ from departments department0_ where department0_.id=?
//        binding parameter [1] as [BIGINT] - [2]
//
//        select employees0_.department_id as departme3_1_0_, employees0_.id as id1_1_0_, employees0_.id as id1_1_1_, employees0_.name as name2_1_1_ from employees employees0_ where employees0_.department_id=?
//        binding parameter [1] as [BIGINT] - [2]
//
//        update employees set department_id=null where department_id=?
//        binding parameter [1] as [BIGINT] - [2]
//         */
//    }
//
//    @Test
//    @Sql("/insertEmployee1.sql")
//    @Sql("/insertEmployee2AndDepartment2WithEmployee2.sql")
//    @Transactional
//    void givenDepartmentWith1Employee_whenRemoving1AndAdding1EmployeeFromDepartment_thenEmployeesSizeIs1() {
//        // GIVEN
//        Employee employee1 = Employee.builder()
//                .id(1L)
//                .build();
//        Department department2WithEmployee2 = departmentJpaRepository.getReferenceById(2L);
//        // WHEN
//        department2WithEmployee2.getEmployees().clear();
//        department2WithEmployee2.getEmployees().add(employee1);
//        Department saved = departmentJpaRepository.saveAndFlush(department2WithEmployee2);
//        // THEN
//        assertThat(saved).isNotNull();
//        assertThat(saved.getEmployees().size()).isEqualTo(1);
//        Set<Long> savedEmployeeIds = saved.getEmployees().stream()
//                .map(Employee::getId)
//                .collect(Collectors.toSet());
//        assertThat(savedEmployeeIds).contains(employee1.getId());
//        /*
//        select department0_.id as id1_0_0_, department0_.name as name2_0_0_ from departments department0_ where department0_.id=?
//        binding parameter [1] as [BIGINT] - [2]
//
//        select employees0_.department_id as departme3_1_0_, employees0_.id as id1_1_0_, employees0_.id as id1_1_1_, employees0_.name as name2_1_1_ from employees employees0_ where employees0_.department_id=?
//        binding parameter [1] as [BIGINT] - [2]
//
//        select employee0_.id as id1_1_0_, employee0_.name as name2_1_0_ from employees employee0_ where employee0_.id=?
//        binding parameter [1] as [BIGINT] - [1]
//
//        update employees set department_id=null where department_id=? and id=?
//        binding parameter [1] as [BIGINT] - [2]
//        binding parameter [2] as [BIGINT] - [2]
//
//        update employees set department_id=? where id=?
//        binding parameter [1] as [BIGINT] - [2]
//        binding parameter [2] as [BIGINT] - [1]
//         */
//    }
}
