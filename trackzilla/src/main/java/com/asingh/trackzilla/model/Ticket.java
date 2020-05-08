package com.asingh.trackzilla.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.asingh.trackzilla.enums.TicketStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "ticket")
@DiscriminatorColumn(name="ticket_type", discriminatorType = DiscriminatorType.CHAR)
@DiscriminatorValue(value = "T")
//@MappedSuperclass
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected long id;

	@Column(name = "title", nullable = false, unique = false)
	protected String title;

	@Column(name = "description", length = 200)
	protected String description;

	@ManyToOne
	@JoinColumn(name = "application_id")
	protected Application application;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", columnDefinition = "varchar(255) default 'DRAFT'")
	protected TicketStatus status;

	@ManyToOne
	@JoinTable(name = "ticket_release", joinColumns = @JoinColumn(name = "ticket_fk", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "release_fk", referencedColumnName = "id"))
	protected Release release;

	public Ticket(String title, String description) {
		this.title = title;
		this.description = description;
	}
}
