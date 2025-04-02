package demo.persistence.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "passports")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Passport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;

}