package edu.netcracker.hibernate.entity;

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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.util.StringJoiner;

@Table(name = "partners_product_families")
@Entity
@Data
@Accessors(chain = true)
@NamedQueries({@NamedQuery(name = "ProductFamilyORM.findAll", query = "SELECT pf FROM ProductFamilyORM pf")})
public class ProductFamilyORM {

    @Id
    @Column(name = "ppf_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ppfId;

    @Column(name = "product_family_id")
    private Integer productFamilyId;

    @Column(name = "product_family_name")
    private String productFamilyName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "partner_id")
    private PartnerORM partner;

    @Override
    public String toString() {
        return new StringJoiner(", ", ProductFamilyORM.class.getSimpleName() + "[", "]")
                .add("ppfId = " + ppfId)
                .add("productFamilyId = " + productFamilyId)
                .add("productFamilyName = '" + productFamilyName + "'")
                .toString();
    }
}
