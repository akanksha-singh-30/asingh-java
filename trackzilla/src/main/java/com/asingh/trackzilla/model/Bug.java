package com.asingh.trackzilla.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor
@Entity 
@DiscriminatorValue(value = "B")
//@Table(name = "bug")
public class Bug extends Ticket {
	
	@Column(name = "severity" )
	protected Integer severity;
	
	@Column(name = "root_cause" )
	protected String rootCause;
		
}
