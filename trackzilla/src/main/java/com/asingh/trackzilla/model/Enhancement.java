package com.asingh.trackzilla.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
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
@DiscriminatorValue(value = "E")
//@Table(name = "enhancement")
@NamedQuery(name = "Enhancement.getAllEnhancements", query = "select e from Enhancement e")
public class Enhancement extends Ticket {

	@Column(name = "duplicate")
	protected Boolean duplicate;

	@Column(name = "priority")
	protected String priority;
}
