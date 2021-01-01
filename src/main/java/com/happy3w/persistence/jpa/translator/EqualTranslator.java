package com.happy3w.persistence.jpa.translator;

import com.happy3w.persistence.core.filter.impl.AbstractEqualFilter;
import com.happy3w.persistence.jpa.context.ParameterContext;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;

/**
 * 等于类条件的公共逻辑
 */
public class EqualTranslator
        extends AbstractFilterTranslator<AbstractEqualFilter> {

    protected EqualTranslator() {
        super(AbstractEqualFilter.class);
    }

    @Override
    public Predicate translate(AbstractEqualFilter filter, ParameterContext<?, ?> context) {
        CriteriaBuilder cb = context.getCriteriaBuilder();

        ParameterExpression<?> parameter = context.newParameter(filter.getRef());
        if (filter.isPositive()) {
            return cb.equal(context.getRoot().get(filter.getField()), parameter);
        } else {
            return cb.notEqual(context.getRoot().get(filter.getField()), parameter);
        }
    }
}
