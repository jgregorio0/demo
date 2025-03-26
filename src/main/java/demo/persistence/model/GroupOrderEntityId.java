package demo.persistence.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class GroupOrderEntityId implements Serializable {

    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "order_id")
    private Long orderId;
}
