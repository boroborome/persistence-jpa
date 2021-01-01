package com.happy3w.persistence.jpa;

import com.alibaba.fastjson.JSON;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.dataset.ReplacementDataSetLoader;
import com.happy3w.persistence.core.filter.impl.StringEqualFilter;
import com.happy3w.persistence.jpa.app.TestApplication;
import com.happy3w.persistence.jpa.app.entity.CompanyEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
@DbUnitConfiguration(dataSetLoader = ReplacementDataSetLoader.class)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
public class JpaAssistantTest {

    @Autowired
    private JpaAssistant assistant;

    @Test
    public void should_write_and_read_success() {
        assistant.saveData(CompanyEntity.builder()
                .id("hw")
                .name("HW")
                .catalog("CN")
                .build());
        assistant.saveStream(Stream.of(
                CompanyEntity.builder()
                        .id("cs")
                        .name("CS")
                        .catalog("AM")
                        .build(),
                CompanyEntity.builder()
                        .id("sc")
                        .name("SC")
                        .catalog("EN")
                        .build()
        ));

        List<CompanyEntity> result = assistant.queryStream(CompanyEntity.class, Arrays.asList(
                new StringEqualFilter("name", "HW")
        ), null)
                .collect(Collectors.toList());

        Assert.assertEquals("[{\"catalog\":\"CN\",\"id\":\"hw\",\"name\":\"HW\"}]", JSON.toJSONString(result));
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
