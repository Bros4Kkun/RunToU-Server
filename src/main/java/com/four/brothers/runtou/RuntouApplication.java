package com.four.brothers.runtou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing //JPA Auditing 활성화 (for 자동 생성·수정일 생성)
@SpringBootApplication
public class RuntouApplication {

	public static void main(String[] args) {
		SpringApplication.run(RuntouApplication.class, args);
	}

}
