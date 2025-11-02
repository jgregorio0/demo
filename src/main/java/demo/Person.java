package demo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
public class Person {
    @Id
    private int id;
    private String firstName;
    private String lastName;
    @OneToOne(mappedBy = "person")
    private Address address;
}
