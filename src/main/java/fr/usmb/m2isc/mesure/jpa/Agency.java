package fr.usmb.m2isc.mesure.jpa;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
public class Agency {

    @Id
    @GeneratedValue
    private long id;
    private String name;
    private ArrayList<Item> backlog;

    public Agency() {
    }

    public Agency(String name) {
        this.name = name;
        this.backlog = new ArrayList<Item>();
    }
}
