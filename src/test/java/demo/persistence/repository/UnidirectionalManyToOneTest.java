package demo.persistence.repository;


import demo.persistence.model.School;
import demo.persistence.model.Student;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class UnidirectionalManyToOneTest {

    @Autowired
    private SchoolJpaRepository schoolJpaRepository;

    @Autowired
    private StudentJpaRepository studentJpaRepository;

    @Test
    @Transactional
    void givenNonExistingSchool_whenCreateStudentWithSchool_thenThrowsException() {
        School nonExistingSchool = School.builder()
                .id(10L)
                .build();
        Student student = Student.builder()
                .school(nonExistingSchool)
                .build();
        Executable exe = () -> studentJpaRepository.saveAndFlush(student);
        assertThrows(DataAccessException.class, exe);
        /*
        insert into students (id, name, school_id) values (default, ?, ?)
        inding parameter [1] as [VARCHAR] - [null]
        inding parameter [2] as [BIGINT] - [10]

        SQL Error: 23506, SQLState: 23506
        Referential integrity constraint violation: "FKDOJMG8V3RW2OW4DEV2B8Q5OQQ: PUBLIC.STUDENTS FOREIGN KEY(SCHOOL_ID) REFERENCES PUBLIC.SCHOOLS(ID) (CAST(10 AS BIGINT))"; SQL statement:
        insert into students (id, name, school_id) values (default, ?, ?) [23506-214]
         */
    }

    @Test
    @Sql("/insertSchool1.sql")
    @Transactional
    void givenExistingSchool_whenCreateStudentWithSchool_thenCreatedIsNotNull() {
        School existingSchool = schoolJpaRepository.getReferenceById(1L);
        Student student = Student.builder()
                .name("Student 1")
                .school(existingSchool)
                .build();
        Student created = studentJpaRepository.saveAndFlush(student);
        assertThat(created).isNotNull();
        /*
        insert into students (id, name, school_id) values (default, ?, ?)
        binding parameter [1] as [VARCHAR] - [Student 1]
        binding parameter [2] as [BIGINT] - [1]
         */
    }

    @Test
    @Transactional
    void givenNonExistingStudent_whenRead_thenThrowsException() {
        // GIVEN
        // WHEN
        Executable exe = () -> studentJpaRepository.findById(10L)
                .orElseThrow(() -> new PersistenceException("Not Found"));
        // THEN
        assertThrows(PersistenceException.class, exe);
        /*
        select student0_.id as id1_3_0_, student0_.name as name2_3_0_, student0_.school_id as school_i3_3_0_, school1_.id as id1_2_1_, school1_.name as name2_2_1_ from students student0_ left outer join schools school1_ on student0_.school_id=school1_.id where student0_.id=?
        binding parameter [1] as [BIGINT] - [10]
         */
    }

    @Test
    @Sql("/insertStudent1WithoutSchool.sql")
    @Transactional
    void givenExistingStudent_whenRead_thenFound() {
        // GIVEN
        // WHEN
        Student found = studentJpaRepository.findById(1L)
                .orElseThrow(() -> new PersistenceException("Not Found"));
        // THEN
        assertThat(found).isNotNull();
        /*
        select student0_.id as id1_3_0_, student0_.name as name2_3_0_, student0_.school_id as school_i3_3_0_, school1_.id as id1_2_1_, school1_.name as name2_2_1_ from students student0_ left outer join schools school1_ on student0_.school_id=school1_.id where student0_.id=?
        binding parameter [1] as [BIGINT] - [1]
         */
    }

    @Test
    @Sql("/insertSchool1.sql")
    @Sql("/insertStudent1WithoutSchool.sql")
    @Transactional
    void givenStudentWithoutSchool_whenUpdateStudentSchool_thenStudentSchoolIsNotNull() {
        School existingSchool = schoolJpaRepository.getReferenceById(1L);
        Student student = studentJpaRepository.getReferenceById(1L);
        student.setSchool(existingSchool);
        Student updated = studentJpaRepository.saveAndFlush(student);
        assertThat(updated).isNotNull();
        assertThat(updated.getSchool()).isNotNull();

        /*
        select student0_.id as id1_3_0_, student0_.name as name2_3_0_, student0_.school_id as school_i3_3_0_, school1_.id as id1_2_1_, school1_.name as name2_2_1_ from students student0_ left outer join schools school1_ on student0_.school_id=school1_.id where student0_.id=?
        binding parameter [1] as [BIGINT] - [1]

        update students set name=?, school_id=? where id=?
        binding parameter [1] as [VARCHAR] - [Student 1]
        binding parameter [2] as [BIGINT] - [1]
        binding parameter [3] as [BIGINT] - [1]
         */
    }

    @Test
    @Sql("/insertSchool1.sql")
    @Sql("/insertSchool2AndStudent2WithSchool2.sql")
    @Transactional
    void givenStudentWithSchool_whenUpdateStudentSchool_thenStudentSchoolUpdated() {
        // GIVEN
        School school1 = schoolJpaRepository.getReferenceById(1L);
        Student studentWithSchool = studentJpaRepository.getReferenceById(2L);
        // WHEN
        studentWithSchool.setSchool(school1);
        Student updated = studentJpaRepository.saveAndFlush(studentWithSchool);
        // THEN
        assertThat(updated).isNotNull();
        assertThat(updated.getSchool()).isNotNull();
        assertThat(updated.getSchool().getId()).isEqualTo(school1.getId());

        /*
        select student0_.id as id1_3_0_, student0_.name as name2_3_0_, student0_.school_id as school_i3_3_0_, school1_.id as id1_2_1_, school1_.name as name2_2_1_ from students student0_ left outer join schools school1_ on student0_.school_id=school1_.id where student0_.id=?
        binding parameter [1] as [BIGINT] - [2]

        update students set name=?, school_id=? where id=?
        binding parameter [1] as [VARCHAR] - [Student 2]
        binding parameter [2] as [BIGINT] - [1]
        binding parameter [3] as [BIGINT] - [2]
         */
    }

    @Test
    @Sql("/insertSchool2AndStudent2WithSchool2.sql")
    @Transactional
    void givenStudentWithSchool_whenCreateStudentInSameSchool_thenStudentIsCreated() {
        // GIVEN
        School school2 = schoolJpaRepository.getReferenceById(2L);
        Student student = Student.builder()
                .name("Student 3")
                .school(school2)
                .build();
        // WHEN
        Student created = studentJpaRepository.saveAndFlush(student);
        // THEN
        assertThat(created).isNotNull();
        assertThat(created.getSchool()).isNotNull();
        /*
        insert into students (id, name, school_id) values (default, ?, ?)
        binding parameter [1] as [VARCHAR] - [Student 3]
        binding parameter [2] as [BIGINT] - [2]
         */
    }

    @Test
    @Sql("/insertSchool2AndStudent2WithSchool2.sql")
    @Transactional
    void givenStudentWithSchool_whenUpdateWithoutSchool_thenStudentSchoolIsNull() {
        // GIVEN
        Student student = studentJpaRepository.getReferenceById(2L);
        // WHEN
        student.setSchool(null);
        Student updated = studentJpaRepository.saveAndFlush(student);
        // THEN
        assertThat(updated).isNotNull();
        assertThat(updated.getSchool()).isNull();
        /*
        select student0_.id as id1_3_0_, student0_.name as name2_3_0_, student0_.school_id as school_i3_3_0_, school1_.id as id1_2_1_, school1_.name as name2_2_1_ from students student0_ left outer join schools school1_ on student0_.school_id=school1_.id where student0_.id=?
        binding parameter [1] as [BIGINT] - [2]

        update students set name=?, school_id=? where id=?
        binding parameter [1] as [VARCHAR] - [Student 2]
        binding parameter [2] as [BIGINT] - [null]
        binding parameter [3] as [BIGINT] - [2]
         */
    }

    @Test
    @Sql("/insertSchool2AndStudent2WithSchool2.sql")
    @Transactional
    void givenStudentWithSchool_whenDelete_thenDeleted() {
        // GIVEN
        // WHEN
        studentJpaRepository.deleteById(2L);
        studentJpaRepository.flush();
        // THEN
        Student found = studentJpaRepository.findById(2L).orElse(null);
        assertThat(found).isNull();
        /*
        select student0_.id as id1_3_0_, student0_.name as name2_3_0_, student0_.school_id as school_i3_3_0_, school1_.id as id1_2_1_, school1_.name as name2_2_1_ from students student0_ left outer join schools school1_ on student0_.school_id=school1_.id where student0_.id=?
        binding parameter [1] as [BIGINT] - [2]

        delete from students where id=?
        binding parameter [1] as [BIGINT] - [2]

        select student0_.id as id1_3_0_, student0_.name as name2_3_0_, student0_.school_id as school_i3_3_0_, school1_.id as id1_2_1_, school1_.name as name2_2_1_ from students student0_ left outer join schools school1_ on student0_.school_id=school1_.id where student0_.id=?
        binding parameter [1] as [BIGINT] - [2]
         */
    }
}
