package edu.netcracker.hibernate.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.Instant;
import java.util.List;
import java.util.StringJoiner;

@Data
@Entity
@Table(name = "programs")
@Accessors(chain = true)
@NamedQueries({@NamedQuery(name = "ProgramORM.findAll", query = "select p from ProgramORM p")})
public class ProgramORM {

    @Id
    @Column(name = "program_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer programId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "retailer_id")
    private RetailerORM retailer;

    //Retailer's product from RBM
    @Column(name = "product_id")
    private Integer retailerProductId;

    @Column(name = "name")
    private String name;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProgramProductORM> programProducts;

    @Override
    public String toString() {
        return new StringJoiner(", ", ProgramORM.class.getSimpleName() + "[", "]")
                .add("programId = " + programId)
                .add("retailerProductId = " + retailerProductId)
                .add("name = '" + name + "'")
                .add("startDate = " + startDate)
                .add("endDate = " + endDate)
                .add("programProducts = " + programProducts)
                .toString();
    }
}
