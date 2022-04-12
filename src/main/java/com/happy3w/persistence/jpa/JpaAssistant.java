package com.happy3w.persistence.jpa;

import com.happy3w.persistence.core.assistant.IDbAssistant;
import com.happy3w.persistence.core.assistant.QueryOptions;
import com.happy3w.persistence.core.filter.IFilter;
import com.happy3w.persistence.jpa.context.DeleteContext;
import com.happy3w.persistence.jpa.context.RetrievalContext;
import com.happy3w.persistence.jpa.translator.JpaTranslateAssistant;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Stream;


public class JpaAssistant implements IDbAssistant<Object> {
    @Getter
    private final EntityManager entityManager;

    @Getter
    @Setter
    private JpaTranslateAssistant translateAssistant = JpaTranslateAssistant.INSTANCE;

    public JpaAssistant(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    @Override
    public <T> T saveData(T data) {
        entityManager.merge(data);
        return data;
    }

    @Transactional
    @Override
    public <T> void saveStream(Stream<T> dataStream) {
        dataStream.forEach(entityManager::merge);
    }

    @Override
    public <T> T findById(Class<T> dataType, Object id) {
        return entityManager.find(dataType, id);
    }

    @Transactional
    @Override
    public <T> Stream<T> findByFilter(Class<T> dataType, List<? extends IFilter> filters, QueryOptions options) {
        RetrievalContext context = new RetrievalContext(entityManager, dataType);
        TypedQuery<T> query = context.createQuery(filters, translateAssistant);

        if (options != null && options.getMaxSize() > 0) {
            query.setMaxResults((int) options.getMaxSize());
        }
        return query.getResultStream();
    }

    @Transactional
    @Override
    public <T> T deleteById(Class<T> dataType, Object id) {
        T data = entityManager.find(dataType, id);
        if (data == null) {
            return null;
        }
        entityManager.remove(data);
        return data;
    }

    @Transactional
    @Override
    public <T> long deleteByFilter(Class<T> dataType, List<? extends IFilter> filters, QueryOptions options) {
        DeleteContext context = new DeleteContext(entityManager, dataType);
        Query query = context.createQuery(filters, translateAssistant);
        return query.executeUpdate();
    }
}
