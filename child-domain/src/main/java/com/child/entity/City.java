package com.child.entity;


import com.child.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = City.TABLE_NAME)
public class City extends BaseEntity {
	private static final long serialVersionUID = -3836415261880793502L;

	public static final String TABLE_NAME = "city";

	@Column(name = "name", length = 30)
	private String name;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "province_id")
	private Province province;

	@Column(name = "is_active")
	private Boolean isActive;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

}
