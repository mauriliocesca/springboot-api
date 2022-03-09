package br.com.api.models;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Table(name = "properties")
@Entity
@Data
@Accessors(chain = true)
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 14, unique = true, nullable = false)
    private Integer pin;
}
