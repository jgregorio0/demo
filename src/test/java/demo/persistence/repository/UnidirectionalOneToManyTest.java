package demo.persistence.repository;


import demo.persistence.model.Department;
import demo.persistence.model.Employee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.StatusResultMatchersExtensionsKt.isEqualTo;

@SpringBootTest
class UnidirectionalOneToManyTest {

    @Autowired
    private DepartmentJpaRepository departmentJpaRepository;

    @Autowired
    private EmployeeJpaRepository employeeJpaRepository;

    @Test
    @Transactional
    void givenDepartmentWithNonExistingEmployee_whenCreateDepartment_thenThrowsException() {
        Employee notExistEmployee = Employee.builder()
                .id(10L)
                .build();
        Department department = Department.builder()
                .name("Department 1")
                .employees(List.of(notExistEmployee))
                .build();
        Executable exe = () -> departmentJpaRepository.saveAndFlush(department);
        assertThrows(DataAccessException.class, exe);
        /*
        insert into departments (id, name) values (default, ?)
        binding parameter [1] as [VARCHAR] - [Department 1]

        update employees set department_id=? where id=?
        binding parameter [1] as [BIGINT] - [1]
        binding parameter [2] as [BIGINT] - [10]

        ERROR when updating employee
         */
    }

    @Test
    @Sql("/insertEmployee1.sql")
    @Transactional
    void givenDepartmentWithExistingEmployee_whenCreateDepartment_thenCreatedIsNotNull() {
        Employee existingEmployee = employeeJpaRepository.getReferenceById(1L);
        Department department = Department.builder()
                .name("Department 1")
                .employees(List.of(existingEmployee))
                .build();
        Department created = departmentJpaRepository.saveAndFlush(department);
        assertThat(created).isNotNull();
        /*
        insert into departments (id, name) values (default, ?)
        binding parameter [1] as [VARCHAR] - [Department 1]
        
        update employees set department_id=? where id=?
        binding parameter [1] as [BIGINT] - [1]
        binding parameter [2] as [BIGINT] - [1]
         */
    }

    @Test
    @Sql("/insertEmployee1.sql")
    @Sql("/insertDepartment1WithoutEmployees.sql")
    @Transactional
    void givenDepartmentWithoutEmployees_whenAdding1Employee_thenEmployeesSizeIs1() {
        // GIVEN
        Employee existingEmployee = employeeJpaRepository.getReferenceById(1L);
        Department departmentWithoutEmployees = departmentJpaRepository.getReferenceById(1L);
        // WHEN
        departmentWithoutEmployees.getEmployees().clear();
        departmentWithoutEmployees.getEmployees().add(existingEmployee);
        Department saved = departmentJpaRepository.saveAndFlush(departmentWithoutEmployees);
        // THEN
        assertThat(saved).isNotNull();
        assertThat(saved.getEmployees().size()).isEqualTo(1);
        /*
        select department0_.id as id1_0_0_, department0_.name as name2_0_0_ from departments department0_ where department0_.id=?
        binding parameter [1] as [BIGINT] - [1]

        select employees0_.department_id as departme4_1_0_, employees0_.id as id1_1_0_, employees0_.id as id1_1_1_, employees0_.name as name2_1_1_, employees0_.surname as surname3_1_1_ from employees employees0_ where employees0_.department_id=?
        binding parameter [1] as [BIGINT] - [1]

        select employee0_.id as id1_1_0_, employee0_.name as name2_1_0_, employee0_.surname as surname3_1_0_ from employees employee0_ where employee0_.id=?
        binding parameter [1] as [BIGINT] - [2]

        update departments set name=? where id=?
        binding parameter [1] as [VARCHAR] - [null]
        binding parameter [2] as [BIGINT] - [1]

        update employees set department_id=? where id=?
        binding parameter [1] as [BIGINT] - [1]
        binding parameter [2] as [BIGINT] - [2]
         */
    }

    @Test
    @Sql("/insertEmployee1.sql")
    @Sql("/insertEmployee2AndDepartment2WithEmployee2.sql")
    @Transactional
    void givenDepartmentWith1Employee_whenAdding1EmployeeIntoDepartment_thenEmployeesSizeIs2() {
        // GIVEN
        Employee employee1 = Employee.builder()
                .id(1L)
                .build();
        Employee employee2 = Employee.builder()
                .id(2L)
                .build();
        Department department2WithEmployee2 = departmentJpaRepository.getReferenceById(2L);
        // WHEN
        department2WithEmployee2.getEmployees().clear();
        department2WithEmployee2.getEmployees().addAll(List.of(employee1, employee2));
        Department saved = departmentJpaRepository.saveAndFlush(department2WithEmployee2);
        // THEN
        assertThat(saved).isNotNull();
        assertThat(saved.getEmployees().size()).isEqualTo(2);
        /*
        select department0_.id as id1_0_0_, department0_.name as name2_0_0_ from departments department0_ where department0_.id=?
        binding parameter [1] as [BIGINT] - [2]

        select employees0_.department_id as departme3_1_0_, employees0_.id as id1_1_0_, employees0_.id as id1_1_1_, employees0_.name as name2_1_1_ from employees employees0_ where employees0_.department_id=?
        binding parameter [1] as [BIGINT] - [2]

        select employee0_.id as id1_1_0_, employee0_.name as name2_1_0_ from employees employee0_ where employee0_.id=?
        binding parameter [1] as [BIGINT] - [1]

        update employees set department_id=? where id=?
        binding parameter [1] as [BIGINT] - [2]
        binding parameter [2] as [BIGINT] - [1]
         */
    }

    @Test
    @Sql("/insertEmployee1.sql")
    @Sql("/insertEmployee2AndDepartment2WithEmployee2.sql")
    @Sql("/insertEmployee3.sql")
    @Transactional
    void givenDepartmentWith1Employee_whenAdding2EmployeesIntoDepartment_thenEmployeesSizeIs3() {
        // GIVEN
        Employee employee1 = Employee.builder()
                .id(1L)
                .build();
        Employee employee2 = Employee.builder()
                .id(2L)
                .build();
        Employee employee3 = Employee.builder()
                .id(3L)
                .build();
        Department department2WithEmployee2 = departmentJpaRepository.getReferenceById(2L);
        // WHEN
        department2WithEmployee2.getEmployees().clear();
        department2WithEmployee2.getEmployees().addAll(List.of(employee1, employee2, employee3));
        Department saved = departmentJpaRepository.saveAndFlush(department2WithEmployee2);
        // THEN
        assertThat(saved).isNotNull();
        assertThat(saved.getEmployees().size()).isEqualTo(3);
        /*
        select department0_.id as id1_0_0_, department0_.name as name2_0_0_ from departments department0_ where department0_.id=?
        binding parameter [1] as [BIGINT] - [2]

        select employees0_.department_id as departme3_1_0_, employees0_.id as id1_1_0_, employees0_.id as id1_1_1_, employees0_.name as name2_1_1_ from employees employees0_ where employees0_.department_id=?
        binding parameter [1] as [BIGINT] - [2]

        select employee0_.id as id1_1_0_, employee0_.name as name2_1_0_ from employees employee0_ where employee0_.id=?
        binding parameter [1] as [BIGINT] - [1]

        select employee0_.id as id1_1_0_, employee0_.name as name2_1_0_ from employees employee0_ where employee0_.id=?
        binding parameter [1] as [BIGINT] - [3]

        update employees set department_id=? where id=?
        binding parameter [1] as [BIGINT] - [2]
        binding parameter [2] as [BIGINT] - [1]

        update employees set department_id=? where id=?
        binding parameter [1] as [BIGINT] - [2]
        binding parameter [2] as [BIGINT] - [3]
         */
    }

    @Test
    @Sql("/insertEmployee2AndDepartment2WithEmployee2.sql")
    @Transactional
    void givenDepartmentWith1Employee_whenRemoving1EmployeesFromDepartment_thenEmployeesSizeIs0() {
        // GIVEN
        Department department2WithEmployee2 = departmentJpaRepository.getReferenceById(2L);
        // WHEN
        department2WithEmployee2.getEmployees().clear();
        Department saved = departmentJpaRepository.saveAndFlush(department2WithEmployee2);
        // THEN
        assertThat(saved).isNotNull();
        assertThat(saved.getEmployees().size()).isEqualTo(0);
        /*
        select department0_.id as id1_0_0_, department0_.name as name2_0_0_ from departments department0_ where department0_.id=?
        binding parameter [1] as [BIGINT] - [2]

        select employees0_.department_id as departme3_1_0_, employees0_.id as id1_1_0_, employees0_.id as id1_1_1_, employees0_.name as name2_1_1_ from employees employees0_ where employees0_.department_id=?
        binding parameter [1] as [BIGINT] - [2]

        update employees set department_id=null where department_id=?
        binding parameter [1] as [BIGINT] - [2]
         */
    }

    @Test
    @Sql("/insertEmployee1.sql")
    @Sql("/insertEmployee2AndDepartment2WithEmployee2.sql")
    @Transactional
    void givenDepartmentWith1Employee_whenRemoving1AndAdding1EmployeeFromDepartment_thenEmployeesSizeIs1() {
        // GIVEN
        Employee employee1 = Employee.builder()
                .id(1L)
                .build();
        Department department2WithEmployee2 = departmentJpaRepository.getReferenceById(2L);
        // WHEN
        department2WithEmployee2.getEmployees().clear();
        department2WithEmployee2.getEmployees().add(employee1);
        Department saved = departmentJpaRepository.saveAndFlush(department2WithEmployee2);
        // THEN
        assertThat(saved).isNotNull();
        assertThat(saved.getEmployees().size()).isEqualTo(1);
        Set<Long> savedEmployeeIds = saved.getEmployees().stream()
                .map(Employee::getId)
                .collect(Collectors.toSet());
        assertThat(savedEmployeeIds).contains(employee1.getId());
        /*
        select department0_.id as id1_0_0_, department0_.name as name2_0_0_ from departments department0_ where department0_.id=?
        binding parameter [1] as [BIGINT] - [2]

        select employees0_.department_id as departme3_1_0_, employees0_.id as id1_1_0_, employees0_.id as id1_1_1_, employees0_.name as name2_1_1_ from employees employees0_ where employees0_.department_id=?
        binding parameter [1] as [BIGINT] - [2]

        select employee0_.id as id1_1_0_, employee0_.name as name2_1_0_ from employees employee0_ where employee0_.id=?
        binding parameter [1] as [BIGINT] - [1]

        update employees set department_id=null where department_id=? and id=?
        binding parameter [1] as [BIGINT] - [2]
        binding parameter [2] as [BIGINT] - [2]

        update employees set department_id=? where id=?
        binding parameter [1] as [BIGINT] - [2]
        binding parameter [2] as [BIGINT] - [1]
         */
    }
}
