package com.happy3w.persistence.jpa;

import com.alibaba.fastjson.JSON;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.dataset.ReplacementDataSetLoader;
import com.happy3w.persistence.core.filter.impl.DateRangeFilter;
import com.happy3w.persistence.core.filter.impl.MonthRangeFilter;
import com.happy3w.persistence.core.filter.impl.StringEqualFilter;
import com.happy3w.persistence.core.filter.impl.StringLikeFilter;
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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Rollback
@Transactional
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
    public void should_modify_success() {
        assistant.saveData(CompanyEntity.builder()
                .id("hw")
                .name("HW")
                .catalog("CN")
                .build());
        assistant.saveData(CompanyEntity.builder()
                .id("hw")
                .name("HW-2")
                .catalog("CN")
                .build());

        CompanyEntity result = assistant.findById(CompanyEntity.class,"hw");

        Assert.assertEquals("HW-2", result.getName());
    }

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

        List<CompanyEntity> result = assistant.findByFilter(CompanyEntity.class, Arrays.asList(
                new StringEqualFilter("name", "HW")
        ), null)
                .collect(Collectors.toList());

        Assert.assertEquals("[{\"catalog\":\"CN\",\"id\":\"hw\",\"name\":\"HW\"}]", JSON.toJSONString(result));
    }

    @Test
    public void should_delete_by_month_success() {
        givenDataConfigedDate();

        long size = assistant.deleteByFilter(CompanyEntity.class, Arrays.asList(
                new MonthRangeFilter("favoriteDate", "2020-08", null, true, false, true)
        ), null);

        Assert.assertEquals(2, size);
    }

    @Test
    public void should_query_by_month_success() {
        givenDataConfigedDate();

        List<CompanyEntity> monthResult = assistant.findByFilter(CompanyEntity.class, Arrays.asList(
                new MonthRangeFilter("favoriteDate", "2020-08", null, true, false, true)
        ), null)
                .collect(Collectors.toList());

        Assert.assertEquals("[{\"catalog\":\"AM\",\"favoriteDate\":1596254400000,\"id\":\"cs\",\"name\":\"CS\"},{\"catalog\":\"EN\",\"favoriteDate\":1598932800000,\"id\":\"sc\",\"name\":\"SC\"}]",
                JSON.toJSONString(monthResult));
    }

    @Test
    public void should_query_by_date_success() {
        givenDataConfigedDate();

        List<CompanyEntity> dateResult = assistant.findByFilter(CompanyEntity.class, Arrays.asList(
                new DateRangeFilter("favoriteDate", null, "2020-08-03", true, false, true)
        ), null)
                .collect(Collectors.toList());

        Assert.assertEquals("[{\"catalog\":\"CN\",\"favoriteDate\":1593576000000,\"id\":\"hw\",\"name\":\"HW\"},{\"catalog\":\"AM\",\"favoriteDate\":1596254400000,\"id\":\"cs\",\"name\":\"CS\"}]",
                JSON.toJSONString(dateResult));
    }

    @Test
    public void should_query_by_like_success() {
        givenDataConfigedDate();

        List<CompanyEntity> dateResult = assistant.findByFilter(CompanyEntity.class, Arrays.asList(
                new StringLikeFilter("name", "C")
        ), null)
                .collect(Collectors.toList());

        Assert.assertEquals("[{\"catalog\":\"AM\",\"favoriteDate\":1596254400000,\"id\":\"cs\",\"name\":\"CS\"},{\"catalog\":\"EN\",\"favoriteDate\":1598932800000,\"id\":\"sc\",\"name\":\"SC\"}]",
                JSON.toJSONString(dateResult));
    }

    private void givenDataConfigedDate() {
        assistant.saveStream(Stream.of(
                CompanyEntity.builder()
                        .id("hw")
                        .name("HW")
                        .catalog("CN")
                        .favoriteDate(Timestamp.valueOf("2020-07-01 12:00:00"))
                        .build(),
                CompanyEntity.builder()
                        .id("cs")
                        .name("CS")
                        .catalog("AM")
                        .favoriteDate(Timestamp.valueOf("2020-08-01 12:00:00"))
                        .build(),
                CompanyEntity.builder()
                        .id("sc")
                        .name("SC")
                        .catalog("EN")
                        .favoriteDate(Timestamp.valueOf("2020-09-01 12:00:00"))
                        .build()
        ));
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
