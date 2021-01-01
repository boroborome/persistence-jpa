package com.happy3w.persistence.jpa.translator;

import com.happy3w.persistence.core.filter.impl.AbstractRangeFilter;
import com.happy3w.persistence.jpa.context.ParameterContext;
import com.happy3w.toolkits.convert.TypeConverter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

public class RangeTranslator extends AbstractFilterTranslator<AbstractRangeFilter> {
    protected RangeTranslator() {
        super(AbstractRangeFilter.class);
    }

    @Override
    public Predicate translate(AbstractRangeFilter filter, ParameterContext<?, ?> context) {
        CriteriaBuilder cb = context.getCriteriaBuilder();

        Path criteriaField = context.getRoot().get(filter.getField());
        Predicate startPredicate = createStartPredicate(filter, criteriaField, cb, context);
        Predicate endPredicate = createEndPredicate(filter, criteriaField, cb, context);

        Predicate combinePredicate = combineStartAndEnd(endPredicate, startPredicate, cb);

        Predicate finalPredicate = filter.isPositive() ? combinePredicate : cb.not(combinePredicate);
        return finalPredicate;
    }

    private Predicate combineStartAndEnd(Predicate endPredicate, Predicate startPredicate, CriteriaBuilder cb) {
        Predicate combinePredicate;
        if (startPredicate != null && endPredicate != null) {
            combinePredicate = cb.and(startPredicate, endPredicate);
        } else if (startPredicate != null) {
            combinePredicate = startPredicate;
        } else if (endPredicate != null) {
            combinePredicate = endPredicate;
        } else {
            combinePredicate = null;
        }
        return combinePredicate;
    }

    private Predicate createEndPredicate(AbstractRangeFilter filter, Path criteriaField, CriteriaBuilder cb, ParameterContext<?, ?> context) {
        ParameterExpression endExp = createParameterExpression(filter.getEnd(), criteriaField.getJavaType(), context);

        Predicate endPredicate = null;
        if (endExp != null) {
            if (filter.isIncludeEnd()) {
                endPredicate = cb.lessThanOrEqualTo(criteriaField, endExp);
            } else {
                endPredicate = cb.lessThan(criteriaField, endExp);
            }
        }
        return endPredicate;
    }

    private Predicate createStartPredicate(AbstractRangeFilter filter, Path criteriaField, CriteriaBuilder cb, ParameterContext<?, ?> context) {
        ParameterExpression startExp = createParameterExpression(filter.getStart(), criteriaField.getJavaType(), context);

        Predicate startPredicate = null;
        if (startExp != null) {
            if (filter.isIncludeStart()) {
                startPredicate = cb.greaterThanOrEqualTo(criteriaField, startExp);
            } else {
                startPredicate = cb.greaterThan(criteriaField, startExp);
            }
        }
        return startPredicate;
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
