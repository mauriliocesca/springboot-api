package br.com.api.models;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Table(name = "laboratories")
@Entity
@Data
@Accessors(chain = true)
public class Laboratory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 100, nullable = false)
    private String name;
}
