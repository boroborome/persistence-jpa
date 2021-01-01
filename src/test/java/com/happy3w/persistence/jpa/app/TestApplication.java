package com.happy3w.persistence.jpa.app;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.happy3w.persistence.jpa.app.repository")
@SpringBootApplication
public class TestApplication {
}
