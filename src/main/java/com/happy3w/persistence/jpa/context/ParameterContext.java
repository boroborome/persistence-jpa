package com.happy3w.persistence.jpa.context;

import com.happy3w.persistence.core.filter.IFilter;
import com.happy3w.persistence.jpa.translator.JpaTranslateAssistant;
import com.happy3w.toolkits.utils.ListUtils;
import com.happy3w.toolkits.utils.Pair;
import lombok.Getter;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class ParameterContext<DT, QT> {
    protected final EntityManager entityManager;
    protected final CriteriaBuilder criteriaBuilder;

    protected Root<DT> root;
    protected List<Pair<ParameterExpression, Object>> parameterList = new ArrayList<>();


    protected ZoneId zoneId;

    protected ParameterContext(EntityManager entityManager) {
        this.entityManager = entityManager;
        criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public ParameterExpression newParameter(Object value) {
        ParameterExpression parameter = criteriaBuilder.parameter(value.getClass(), "p" + parameterList.size());
        parameterList.add(new Pair<>(parameter, value));
        return parameter;
    }

    protected void fillAllParameterValues(Query query) {
        if (!ListUtils.isEmpty(parameterList)) {
            for (Pair<ParameterExpression, Object> parameterExpressionPair : parameterList) {
                query.setParameter(parameterExpressionPair.getKey(), parameterExpressionPair.getValue());
            }
        }
    }

    public abstract QT createQuery(List<IFilter> filterList, JpaTranslateAssistant translateAssistant);
}
