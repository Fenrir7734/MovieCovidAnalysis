package com.fenrir.moviecovidanalysis;

import com.fenrir.moviecovidanalysis.config.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
		FileStorageProperties.class
})
public class MovieCovidAnalysisApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieCovidAnalysisApplication.class, args);
	}
}
