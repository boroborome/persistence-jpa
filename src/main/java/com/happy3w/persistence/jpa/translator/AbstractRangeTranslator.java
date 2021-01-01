package com.happy3w.persistence.jpa.translator;

import com.happy3w.persistence.core.filter.impl.AbstractRangeFilter;
import com.happy3w.persistence.jpa.context.ParameterContext;
import com.happy3w.toolkits.convert.TypeConverter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractRangeTranslator<T extends AbstractRangeFilter> extends IFilterTranslator<T> {
    protected AbstractRangeTranslator(Class<T> criteriaType) {
        super(criteriaType);
    }

    @Override
    public List<Predicate> toPredicate(T criteria, ParameterContext<?, ?> context) {
        CriteriaBuilder cb = context.getCriteriaBuilder();

        Path criteriaField = context.getRoot()
                .get(criteria.getField());
        ParameterExpression startValue = createParameterExpression(criteria.getStart(), criteriaField.getJavaType(), context);
        ParameterExpression endValue = createParameterExpression(criteria.getEnd(), criteriaField.getJavaType(), context);

        Predicate predicate;
        if (startValue != null && endValue != null) {
            predicate = cb.between(criteriaField, startValue, endValue);
        } else if (startValue == null) {
            predicate = cb.lessThan(criteriaField, endValue);
        } else {
            predicate = cb.greaterThan(criteriaField, startValue);
        }
        return Arrays.asList(predicate);
    }

    private ParameterExpression createParameterExpression(Object value, Class expectValueType, ParameterContext<?, ?> context) {
        if (value == null) {
            return null;
        }

        if (value.getClass() == expectValueType) {
            return context.newParameter(value);
        }

        return context.newParameter(
                TypeConverter.INSTANCE.convert(value, expectValueType));
    }
}
