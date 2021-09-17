package org.openmrs.eip.dbsync.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.openmrs.eip.dbsync.entity.light.PersonLight;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
@AttributeOverride(name = "id", column = @Column(name = "user_id"))
public class User extends BaseChangeableMetaDataEntity {
	
	@NotNull
	@OneToOne
	@JoinColumn(name = "person_id")
	private PersonLight person;
	
	@NotNull
	@Column(name = "system_id")
	private String systemId;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "salt")
	private String salt;
	
	@Column(name = "secret_question")
	private String secretQuestion;
	
	@Column(name = "secret_answer")
	private String secretAnswer;
	
	@Column(name = "activation_key")
	private String activationKey;
	
	@Column(name = "email")
	private String email;
	
}
