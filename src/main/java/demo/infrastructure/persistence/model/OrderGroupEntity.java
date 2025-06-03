package demo.infrastructure.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "orders_x_groups")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderGroupEntity {
    @Id
    private OrderGroupEntityKey id;

    @OneToOne
    @MapsId("order_id")
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    private OrderEntity order;

    @JsonIgnore
    @ManyToOne
    @MapsId("group_id")
    @JoinColumn(name = "group_id", nullable = false)
    private GroupEntity group;

}
