package com.child;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public class BaseEntity implements Serializable {
	private static final long serialVersionUID = 4796912106778982509L;
	@Id
	@GeneratedValue
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
	        return true;
	    if (obj == null)
	        return false;
	    if (!(obj instanceof BaseEntity))
	        return false;
	    final BaseEntity other = (BaseEntity) obj;
	    if (getId() == null) {
	        if (other.getId() != null)
	            return false;
	    } else if (!getId().equals(other.getId()))
	        return false;
		return true;
	}

	@Override
	public String toString() {
		return "id: " + id;
	}
}
