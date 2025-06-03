package demo.infrastructure.persistence.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "students")
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String nif;

//    @JsonIgnore
//    @OneToMany(mappedBy = "student")
//    private List<OrderStudentEntity> orderStudents;
}
