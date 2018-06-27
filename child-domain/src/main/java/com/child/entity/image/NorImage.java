package com.child.entity.image;


import com.child.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 具体图片表
 */
@Data
@Entity
@Table(name = NorImage.TABLE_NAME)
public class NorImage extends BaseEntity {

	static final String TABLE_NAME = "nor_Image";

	@Column(name = "object_id")
	private Long objectId;

	@Column(name = "file_name")
	private String fileName;

	@Column(name = "original_name")
	private String originalName;

	@Column(name = "nor_image_url")
	private String norImageUrl;

	@Column(name = "add_time")
	private Date addTime;
	// 1.患者教育 2.头像
	@Column(name = "type")
	private Integer type;

}
