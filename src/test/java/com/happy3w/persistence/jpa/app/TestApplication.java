package com.happy3w.persistence.jpa.app;

import com.happy3w.persistence.jpa.JpaAssistant;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.EntityManager;

@EnableJpaRepositories(basePackages = "com.happy3w.persistence.jpa.app.repository")
@SpringBootApplication
public class TestApplication {

    @Bean
    public JpaAssistant jpaAssistant(EntityManager entityManager) {
        return new JpaAssistant(entityManager);
    }
}
