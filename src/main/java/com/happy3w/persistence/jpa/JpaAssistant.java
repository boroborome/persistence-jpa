package com.happy3w.persistence.jpa;

import com.happy3w.persistence.core.assistant.IDbAssistant;
import com.happy3w.persistence.core.assistant.QueryOptions;
import com.happy3w.persistence.core.filter.IFilter;
import com.happy3w.persistence.jpa.context.ParameterContext;
import com.happy3w.persistence.jpa.context.RetrievalContext;
import com.happy3w.toolkits.utils.ListUtils;
import com.happy3w.toolkits.utils.Pair;
import lombok.Getter;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class JpaAssistant implements IDbAssistant {
    @Getter
    private final EntityManager entityManager;

    public JpaAssistant(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    @Override
    public <T> T saveData(T data) {
        entityManager.persist(data);
        return data;
    }

    @Transactional
    @Override
    public <T> void saveStream(Stream<T> dataStream) {
        dataStream.forEach(entityManager::persist);
    }

    @Transactional
    @Override
    public <T> Stream<T> queryStream(Class<T> dataType, List<IFilter> filters, QueryOptions options) {
        TypedQuery<T> query = createQuery(entityManager, dataType, filters);

        if (options.getMaxSize() > 0) {
            query.setMaxResults((int) options.getMaxSize());
        }
        return query.getResultStream()
                .collect(Collectors.toList())
                .stream();
    }

    public <T> TypedQuery<T> createQuery(
            EntityManager entityManager,
            Class<T> dataType,
            List<IFilter> filterList) {
        RetrievalContext<T> context = RetrievalContext.retrievalContext(entityManager, dataType);

        return createQueryWithParam(filterList, entityManager, context);
    }

    private <T> TypedQuery createQueryWithParam(List<IFilter> filterList, EntityManager entityManager, ParameterContext<T, ?> context) {
        List<Predicate> allPredicates = createAllPredicatesCriteriaAble(filterList, context);

        if (!ListUtils.isEmpty(allPredicates)) {
            context.getCriteriaQuery().where(allPredicates.toArray(new Predicate[allPredicates.size()]));
        }
        TypedQuery typeQuery = entityManager.createQuery(context.getCriteriaQuery());
        fillAllParameterValues(typeQuery, context);
        return typeQuery;
    }

    private void fillAllParameterValues(TypedQuery typeQuery, ParameterContext<?, ?> context) {
        if (!ListUtils.isEmpty(context.getParameterList())) {
            for (Pair<ParameterExpression, Object> parameterExpressionPair : context.getParameterList()) {
                typeQuery.setParameter(parameterExpressionPair.getKey(), parameterExpressionPair.getValue());
            }
        }
    }

    private <T> List<Predicate> createAllPredicatesCriteriaAble(
            List<IFilter> filterList,
            ParameterContext context) {
        List<Predicate> allPredicates = null;
        if (!ListUtils.isEmpty(filterList)) {
            allPredicates = createAllPredicatesByCriteria(filterList, context);
        }
        return allPredicates;
    }

    private <T> List<Predicate> createAllPredicatesByCriteria(
            List<IFilter> filterList,
            ParameterContext context) {
        List<Predicate> allPredicates = new ArrayList<>();
//        for (IFilter criteria : filterList) {
//            IJpaCriteriaProcessor sqlCriteriaBuilder = findCriteriaBuilder(criteria.getClass());
//            if (sqlCriteriaBuilder == null) {
//                throw new UnsupportedOperationException("Unsupported criteria:" + criteria.getClass());
//            }
//            List<Predicate> newPredicates = sqlCriteriaBuilder.toPredicate(criteria, context);
//            if (!ListUtils.isEmpty(newPredicates)) {
//                allPredicates.addAll(newPredicates);
//            }
//        }
        return allPredicates;
    }
}
