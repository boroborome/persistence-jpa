package com.happy3w.persistence.jpa.translator;

import com.happy3w.persistence.core.filter.impl.AbstractRangeFilter;
import com.happy3w.persistence.jpa.context.ParameterContext;
import com.happy3w.toolkits.convert.TypeConverter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

public abstract class AbstractRangeTranslator<FT extends AbstractRangeFilter> extends AbstractFilterTranslator<FT> {
    protected AbstractRangeTranslator(Class<FT> filterType) {
        super(filterType);
    }

    @Override
    public Predicate translate(FT filter, ITranslateAssistant translateAssistant, ParameterContext<?, ?> context) {
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

    private Predicate createEndPredicate(FT filter, Path criteriaField, CriteriaBuilder cb, ParameterContext<?, ?> context) {
        if (filter.getEnd() == null) {
            return null;
        }
        Object endValue = adjustEndValue(filter, context);
        if (endValue == null) {
            return null;
        }
        ParameterExpression endExp = createParameterExpression(endValue, criteriaField.getJavaType(), context);

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

    private Predicate createStartPredicate(FT filter, Path criteriaField, CriteriaBuilder cb, ParameterContext<?, ?> context) {
        if (filter.getStart() == null) {
            return null;
        }
        Object startValue = adjustStartValue(filter, context);
        if (startValue == null) {
            return null;
        }
        ParameterExpression startExp = createParameterExpression(startValue, criteriaField.getJavaType(), context);

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

    protected abstract Object adjustStartValue(FT filter, ParameterContext<?, ?> context);
    protected abstract Object adjustEndValue(FT filter, ParameterContext<?, ?> context);
}
