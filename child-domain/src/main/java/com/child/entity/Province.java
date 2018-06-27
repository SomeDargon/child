package com.child.entity;

import com.child.BaseEntity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = Province.TABLE_NAME)
public class Province extends BaseEntity {

	private static final long serialVersionUID = -8090014867976482825L;

	public static final String TABLE_NAME = "province";

	@Column(name = "name", length = 30)
	private String name;

	@Column(name = "is_active")
	private Boolean isActive;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "province", fetch = FetchType.LAZY)
	private List<City> cities;

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<City> getCities() {
		return cities;
	}

	public void setCities(List<City> cities) {
		this.cities = cities;
	}
}
