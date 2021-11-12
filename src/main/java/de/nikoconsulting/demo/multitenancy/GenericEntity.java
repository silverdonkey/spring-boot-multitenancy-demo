package de.nikoconsulting.demo.multitenancy;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "GenericEntity")
@Table(name = "GENERICENTITY")
public class GenericEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    public GenericEntity() {
    }

    public GenericEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
