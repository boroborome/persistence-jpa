package com.happy3w.persistence.jpa.translator;

import com.happy3w.persistence.core.filter.impl.AbstractInFilter;
import com.happy3w.persistence.jpa.context.ParameterContext;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;

/**
 * in类条件的公共逻辑
 */
public class InTranslator extends AbstractFilterTranslator<AbstractInFilter> {
    protected InTranslator() {
        super(AbstractInFilter.class);
    }

    @Override
    public Predicate translate(AbstractInFilter filter, ITranslateAssistant translateAssistant, ParameterContext<?, ?> context) {
        CriteriaBuilder cb = context.getCriteriaBuilder();

        CriteriaBuilder.In in = cb.in(context.getRoot().get(filter.getField()));
        for (Object v : filter.getRefs()) {
            in.value(v);
        }

        return filter.isPositive() ? in : cb.not(in);
    }
}
