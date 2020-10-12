package edu.netcracker.hibernate.entity;

import edu.netcracker.hibernate.entity.enumirate.PartnerType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "program_products")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

}
