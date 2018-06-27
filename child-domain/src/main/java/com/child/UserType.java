package com.child;


import com.child.entity.admin.Admin;
import com.child.entity.customer.Customer;

public enum UserType {
	CUSTOMER(UserSession.class, Customer.class, "-c"), ADMIN(
			UserSession.class, Admin.class, "-a");

	private Class<? extends UserSession> sessionClass;
	private Class<?> accountClass;
	private String suffix;

	private UserType(Class<? extends UserSession> sessionClass,
                     Class<?> accountClass, String suffix) {
		this.sessionClass = sessionClass;
		this.accountClass = accountClass;
		this.suffix = suffix;

	}

	public Class<? extends UserSession> getSessionClass() {
		return sessionClass;
	}

	public String getSuffix() {
		return suffix;
	}

	public Class<?> getAccountClass() {
		return accountClass;
	}
}
