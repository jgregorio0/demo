package demo.persistence.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "clients")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ClientEntity {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String cif;
}
