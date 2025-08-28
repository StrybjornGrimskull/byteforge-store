package com.byteforge.byteforge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ByteForgeOnlineStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(ByteForgeOnlineStoreApplication.class, args);
	}

}
