package com.happy3w.persistence.jpa.translator;

import com.happy3w.persistence.core.filter.impl.StringLikeInFilter;
import com.happy3w.persistence.jpa.context.ParameterContext;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * in类条件的公共逻辑
 */
public class LikeInTranslator extends AbstractFilterTranslator<StringLikeInFilter> {
    protected LikeInTranslator() {
        super(StringLikeInFilter.class);
    }

    @Override
    public Predicate translate(StringLikeInFilter filter, ITranslateAssistant translateAssistant, ParameterContext<?, ?> context) {
        CriteriaBuilder cb = context.getCriteriaBuilder();

        List<Predicate> likes = new ArrayList<>();
        for (String v : filter.getRefs()) {
            ParameterExpression<String> parameter = context.newParameter("%" + v + "%");
            Predicate likeItem = cb.like(context.getRoot().get(filter.getField()), parameter);
            likes.add(likeItem);
        }

        Predicate combine = cb.and(likes.toArray(new Predicate[likes.size()]));

        return filter.isPositive() ? combine : cb.not(combine);
    }
}
