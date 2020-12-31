package com.happy3w.persistence.jpa.context;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EntityManager;

@Getter
@Setter
@NoArgsConstructor
public class RetrievalContext<T> extends ParameterContext<T, T> {

    public RetrievalContext(EntityManager entityManager, Class<T> dataType) {
        criteriaBuilder = entityManager.getCriteriaBuilder();
        criteriaQuery = criteriaBuilder.createQuery(dataType);
        root = criteriaQuery.from(dataType);
        criteriaQuery.select(root);
    }

    public static <T> RetrievalContext<T> retrievalContext(EntityManager entityManager, Class<T> dataType) {
        return new RetrievalContext(entityManager, dataType);
    }
}
