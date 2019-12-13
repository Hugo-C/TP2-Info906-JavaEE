package fr.usmb.m2isc.mesure.jpa;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
public class Agency {

    @Id
    @GeneratedValue
    private long id;
    private String name;

    public Agency() {
    }

    public Agency(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }
}
