package com.asingh.trackzilla.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue(value = "B")
@NamedQueries({
	@NamedQuery(name = "Bug.getAllBugs", query = "select b from Bug b"),
	@NamedQuery(name = "Bug.getSevereBugs", query = "select b from Bug b where b.severity = 1")
})
//@Table(name = "bug")
public class Bug extends Ticket {

	@Column(name = "severity")
	protected Integer severity;

	@Column(name = "root_cause")
	protected String rootCause;

}
