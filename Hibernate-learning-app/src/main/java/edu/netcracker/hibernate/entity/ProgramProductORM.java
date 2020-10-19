package edu.netcracker.hibernate.entity;

import edu.netcracker.hibernate.entity.enumirate.PartnerType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.StringJoiner;

@Table(name = "program_products")
@Entity
@Data
@Accessors(chain = true)
public class ProgramProductORM {

    @Id
    @Column(name = "pp_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ppId;

    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "label")
    private String label;

    @Column(name = "partner_type")
    private PartnerType partnerType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id")
    private ProgramORM program;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_id")
    private PartnerORM partner;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SettlementORM> settlements;

    @Override
    public String toString() {
        return new StringJoiner(", ", ProgramProductORM.class.getSimpleName() + "[", "]")
                .add("ppId = " + ppId)
                .add("productId = " + productId)
                .add("label = '" + label + "'")
                .add("partnerType = " + partnerType)
                .add("settlements = " + settlements)
                .toString();
    }
}
