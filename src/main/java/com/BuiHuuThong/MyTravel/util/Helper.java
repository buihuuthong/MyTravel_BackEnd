package com.BuiHuuThong.MyTravel.util;

import com.BuiHuuThong.MyTravel.exception.ApiException;
import com.BuiHuuThong.MyTravel.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Helper {
	@Value("${app.is-production}")
	private boolean IS_PRODUCTION;

	public void rejectApiOnProduction() {
		if (IS_PRODUCTION) {
			throw new ApiException(ErrorCode.NOT_FOUND, "API chỉ hoạt động trên môi trường test");
		}
	}
}
