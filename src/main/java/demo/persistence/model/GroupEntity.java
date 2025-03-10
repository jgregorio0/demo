package demo.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "groups")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String number;

    @Builder.Default
    @OneToMany(mappedBy = "group")
    private List<OrderGroupEntity> orderGroups = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "client_id")
    private ClientEntity client;

}
