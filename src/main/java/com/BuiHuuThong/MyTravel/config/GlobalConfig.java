package com.BuiHuuThong.MyTravel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.relational.core.mapping.NamingStrategy;
import org.springframework.data.relational.core.mapping.RelationalPersistentProperty;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.time.ZoneOffset;
import java.util.TimeZone;
import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class GlobalConfig {
	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone(ZoneOffset.UTC));
	}

	@Bean
	public Executor taskExecutor() {
		var executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(5);
		executor.setMaxPoolSize(20);
		executor.setQueueCapacity(50);
		executor.setThreadNamePrefix("Worker-");
		executor.initialize();
		return executor;
	}

	@Bean
	public NamingStrategy namingStrategy() {
		return new NamingStrategy() {
			@Override
			public String getTableName(Class<?> type) {
				// Tên bảng giống tên class
				Assert.notNull(type, "Type must not be null");
				return type.getSimpleName();
			}

			@Override
			public String getColumnName(RelationalPersistentProperty property) {
				// Chuyển tên field (camelCase) thành tên cột (PascalCase)
				Assert.notNull(property, "Property must not be null");
				return StringUtils.capitalize(property.getName());
			}
		};
	}
}
