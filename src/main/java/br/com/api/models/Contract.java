package br.com.api.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

@Table(name = "contracts")
@Entity
@Data
@Accessors(chain = true)
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 20, unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    private Date finalDate;

    @Column(nullable = false)
    private Date initialDate;

    @ManyToOne(targetEntity = User.class, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
    @JsonBackReference(value = "user-contracts")
    private User user;

    @OneToOne(targetEntity = Property.class, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "property_id", nullable = false, referencedColumnName = "id")
    private Property property;

    @OneToOne(targetEntity = Laboratory.class, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "laboratory_id", nullable = false, referencedColumnName = "id")
    private Laboratory laboratory;

    @Lob
    @Column
    private String note;
}
