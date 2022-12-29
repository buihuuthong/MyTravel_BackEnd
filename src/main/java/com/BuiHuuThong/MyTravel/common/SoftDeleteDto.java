package com.BuiHuuThong.MyTravel.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SoftDeleteDto {
	private Boolean isDelete;

	public void from(SoftDeleteEntity entity) {
		isDelete = entity.getIsDelete();
	}
}
