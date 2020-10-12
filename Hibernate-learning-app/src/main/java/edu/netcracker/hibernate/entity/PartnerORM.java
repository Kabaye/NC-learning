package edu.netcracker.hibernate.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Table(
        name = "partners",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"customer_ref", "account_num"})
        }
)
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class PartnerORM {

    @Id
    @Column(name = "partner_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer partnerId;

    @Column(name = "partner_name")
    private String partnerName;

    @Column(name = "customer_ref")
    private String customerRef;

    @Column(name = "account_num")
    private String accountNum;

    @Column(name = "icon")
    private String icon;


    @Column(name = "retailer_id")
    private Integer retailerId;

}
