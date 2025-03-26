package demo.persistence.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "employees")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

}