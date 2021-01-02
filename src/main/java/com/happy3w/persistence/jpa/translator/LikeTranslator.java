package com.happy3w.persistence.jpa.translator;

import com.happy3w.persistence.core.filter.impl.StringLikeFilter;
import com.happy3w.persistence.jpa.context.ParameterContext;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;

/**
 * 相似条件翻译
 */
public class LikeTranslator
        extends AbstractFilterTranslator<StringLikeFilter> {

    protected LikeTranslator() {
        super(StringLikeFilter.class);
    }

    @Override
    public Predicate translate(StringLikeFilter filter, ITranslateAssistant translateAssistant, ParameterContext<?, ?> context) {
        CriteriaBuilder cb = context.getCriteriaBuilder();

        ParameterExpression<String> parameter = context.newParameter("%" + filter.getRef() + "%");
        if (filter.isPositive()) {
            return cb.like(context.getRoot().get(filter.getField()), parameter);
        } else {
            return cb.notLike(context.getRoot().get(filter.getField()), parameter);
        }
    }
}
