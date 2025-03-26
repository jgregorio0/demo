package demo.persistence.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "groups_x_orders")
public class GroupOrderEntity {
    @EmbeddedId
    private GroupOrderEntityId id;

}
