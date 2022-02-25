package com.senai.healthcare.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CNPJ;

@Entity
@Table(name = "healthcare_institution")
public class HealthcareInstitution {

	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "CNPJ is required")
	@CNPJ(message = "CNPJ is invalid")
	@Size(min = 14, max = 14)
	private String cnpj;
	
	@NotBlank(message = "Name is required")
	@Size(min = 3, max = 100)
	private String name;
	
	private int coins = 20;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCoins() {
		return coins;
	}
	
	public void chargeCoins(int coins) {
		this.coins = this.coins - coins;
	}
	
	public boolean haveCoins(int coins) {
		return (this.coins - coins) >= 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cnpj == null) ? 0 : cnpj.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HealthcareInstitution other = (HealthcareInstitution) obj;
		if (cnpj == null) {
			if (other.cnpj != null)
				return false;
		} else if (!cnpj.equals(other.cnpj))
			return false;
		return true;
	}
	
}
