package com.child;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = UserSession.TABLE_NAME)
public class UserSession extends BaseEntity {

	final static String TABLE_NAME="user_session";
	private static final long serialVersionUID = 2670298499138209690L;

	@Column(name = "auth_token", length = 50)
	private String authToken;

	@Column(name = "start_time", nullable = false)
	private Date startTime;

	@Column(name = "user_id", nullable = false)
	private Long userId;

	@Column(name = "is_expired", nullable = false)
	private boolean isExpired;


}
