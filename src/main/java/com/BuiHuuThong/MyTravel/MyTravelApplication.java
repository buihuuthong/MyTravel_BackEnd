package com.BuiHuuThong.MyTravel;

import com.BuiHuuThong.MyTravel.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class MyTravelApplication {

	public static void main(String[] args) {

		// Cho phép giao thức TLSv1 cho kết nối Microsoft SQL Server
		// Thay vì upgrade SQL Server lên phiên bản mới để hỗ trợ TLSv1.2
		// Tránh ảnh hưởng đến các ứng dụng khác dùng chung database server
		Util.allowTLSv1();
		SpringApplication.run(MyTravelApplication.class, args);
	}
}
