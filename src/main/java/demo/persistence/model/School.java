package demo.persistence.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "schools")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}