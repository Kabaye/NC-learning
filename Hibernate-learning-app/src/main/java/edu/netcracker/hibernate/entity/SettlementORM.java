package edu.netcracker.hibernate.entity;

import edu.netcracker.hibernate.entity.enumirate.ChargeType;
import edu.netcracker.hibernate.entity.enumirate.SettlementEvent;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "settlements")
@Entity
@Data
@Accessors(chain = true)
public class SettlementORM {
    @Id
    @Column(name = "settlement_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer settlementId;

    @Column(name = "settlement_event")
    private SettlementEvent settlementEvent;

    @Column(name = "charge_type")
    private ChargeType chargeType;

    @Column(name = "fixed_amount")
    private Double fixedAmount;

    @Column(name = "revenue_share_amount")
    private Integer revenueShareAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pp_id")
    private ProgramProductORM product;
}
