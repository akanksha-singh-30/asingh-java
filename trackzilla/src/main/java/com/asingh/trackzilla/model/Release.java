package com.asingh.trackzilla.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "release", uniqueConstraints = @UniqueConstraint(columnNames = {"description", "release_date"}))
@Entity
public class Release {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected long id;

	@Column(name = "release_date")
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd")
	protected LocalDate releaseDate;

	@Column(name = "description", nullable = false)
	protected String description;

	@OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = false)
	@JoinColumn(name = "release_id")
	protected List<Application> applications = new ArrayList<>();

	public Release(String description, LocalDate releaseDate) {
		this.description = description;
		this.releaseDate = releaseDate;
	}

	public void addApplication(Application application) {
        this.applications.add(application);
    }
}
