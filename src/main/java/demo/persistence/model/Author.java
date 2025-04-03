package demo.persistence.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "authors")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}