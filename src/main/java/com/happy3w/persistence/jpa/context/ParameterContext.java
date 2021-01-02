package com.happy3w.persistence.jpa.context;

import com.happy3w.toolkits.utils.Pair;
import lombok.Getter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class ParameterContext<T, R> {

    protected Root<T> root;
    protected CriteriaBuilder criteriaBuilder;
    protected List<Pair<ParameterExpression, Object>> parameterList = new ArrayList<>();

    protected CriteriaQuery<R> criteriaQuery;
    protected ZoneId zoneId;

    public ParameterExpression newParameter(Object value) {
        ParameterExpression parameter = criteriaBuilder.parameter(value.getClass());
        parameterList.add(new Pair<>(parameter, value));
        return parameter;
    }
}
