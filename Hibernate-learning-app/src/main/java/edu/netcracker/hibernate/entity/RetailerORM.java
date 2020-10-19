package edu.netcracker.hibernate.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Table(name = "retailers")
@Entity
@Data
@Accessors(chain = true)
public class RetailerORM {

    @Id
    @Column(name = "retailer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer retailerId;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "icon")
    private String icon;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "retailer", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<ProgramORM> programs;

    @OneToMany(mappedBy = "retailer", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<PartnerORM> partners;
}
