package edu.netcracker.hibernate.entity;

import edu.netcracker.hibernate.entity.enumirate.Scope;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author svku0919
 * @version 09.10.2020
 */
@Data
@Entity
@Accessors(chain = true)
@Table(name = "event_formulas")
public class EventFormulaORM {
    @Id
    private Integer secret_id;

    @Column(name = "id")
    private Integer id;

    @Column(name = "formula")
    private String formula;

    @Column(name = "return_type")
    private String returnType;

    @Column(name = "scope")
    @Enumerated(EnumType.STRING)
    private Scope scope;
}
