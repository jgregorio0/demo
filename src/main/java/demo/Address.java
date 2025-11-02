package demo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Address {

    @Id
    private int id;
    private String street;
    private String city;
    private int zipode;
    @OneToOne
    @JoinColumn(name = "id")
    @MapsId
    private Person person;
}
