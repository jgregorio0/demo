package demo.persistence.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class OrderGroupEntityKey implements Serializable {

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "group_id")
    private Long groupId;
}
