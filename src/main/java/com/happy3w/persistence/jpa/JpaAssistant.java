package com.happy3w.persistence.jpa;

import com.happy3w.persistence.core.assistant.IDbAssistant;
import com.happy3w.persistence.core.assistant.QueryOptions;
import com.happy3w.persistence.core.filter.IFilter;
import lombok.Getter;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Stream;


public class JpaAssistant implements IDbAssistant {
    @Getter
    private final EntityManager entityManager;

    public JpaAssistant(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public <T> T saveData(T t) {
        return null;
    }

    @Override
    public <T> void saveStream(Stream<T> stream) {

    }

    @Override
    public <T> Stream<T> queryStream(Class<T> aClass, List<IFilter> list, QueryOptions queryOptions) {
        return null;
    }
}
