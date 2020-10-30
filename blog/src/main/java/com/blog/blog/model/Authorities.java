package com.blog.blog.model;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table( name = "roles" )
public class Authorities {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String name;

    @ManyToMany( mappedBy = "roles")
    private List<User> users = new ArrayList<>();

    public Authorities(){}
    public Authorities(String name){
        this.name = name;
    }

}
