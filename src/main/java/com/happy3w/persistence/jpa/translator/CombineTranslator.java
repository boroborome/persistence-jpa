package com.happy3w.persistence.jpa.translator;

import com.happy3w.persistence.core.filter.impl.CombineFilter;
import com.happy3w.persistence.jpa.context.ParameterContext;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.util.List;

/**
 * 组合条件逻辑
 */
public class CombineTranslator extends AbstractFilterTranslator<CombineFilter> {
    protected CombineTranslator() {
        super(CombineFilter.class);
    }

    @Override
    public Predicate translate(CombineFilter filter, ITranslateAssistant translateAssistant, ParameterContext<?, ?> context) {
        CriteriaBuilder cb = context.getCriteriaBuilder();

        List<Predicate> predicates = translateAssistant.translate(filter.getInnerFilters(), context);
        Predicate[] predicateArray = predicates.toArray(new Predicate[predicates.size()]);
        Predicate combine = CombineFilter.Ops.Or.equals(filter.getOperator())
                ? cb.or(predicateArray)
                : cb.and(predicateArray);

        return filter.isPositive() ? combine : cb.not(combine);
    }
}
