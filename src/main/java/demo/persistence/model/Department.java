package demo.persistence.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "departments")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Department {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
 
    @OneToMany
    @JoinColumn(name = "department_id")
    @Builder.Default
    private List<Employee> employees = new ArrayList<>();
}