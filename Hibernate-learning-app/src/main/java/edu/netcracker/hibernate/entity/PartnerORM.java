package edu.netcracker.hibernate.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
import javax.persistence.UniqueConstraint;
import java.util.List;
import java.util.StringJoiner;


@Entity
@Data
@Accessors(chain = true)
@Table(name = "partners", uniqueConstraints = {@UniqueConstraint(columnNames = {"customer_ref", "account_num"})})
@NamedQueries({@NamedQuery(name = "PartnerORM.findAll", query = "SELECT p FROM PartnerORM p")})
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "retailer_id")
    private RetailerORM retailer;

    @OneToMany(mappedBy = "partner")
    private List<ProgramProductORM> programProducts;

    @OneToMany(mappedBy = "partner", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<ProductFamilyORM> productFamilies;

    @Override
    public String toString() {
        return new StringJoiner(", ", PartnerORM.class.getSimpleName() + "[", "]")
                .add("partnerId = " + partnerId)
                .add("partnerName = '" + partnerName + "'")
                .add("customerRef = '" + customerRef + "'")
                .add("accountNum = '" + accountNum + "'")
                .add("icon = '" + icon + "'")
//                .add("programProducts = " + programProducts)
                .add("productFamilies = " + productFamilies)
                .toString();
    }
}
