package com.happy3w.persistence.jpa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.Test;

public class JpaAssistantTest {

    @Test
    public void should_write_and_read_success() {

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyData {
        private String id;
        private String name;
        private int age;
    }
}
