package com.asingh.trackzilla.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Entity
@Table(name = "application", uniqueConstraints = { @UniqueConstraint(columnNames = { "a_name", "a_owner" }) })
public class Application {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "a_id")
	private long id;

	@Column(name = "a_name", nullable = false)
	private String name;

	@Column(name = "a_owner", nullable = false)
	private String owner;

	@Column(name = "a_desc", length = 2000)
	private String description;

	public Application(String name, String owner) {
		this.name = name;
		this.owner = owner;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
