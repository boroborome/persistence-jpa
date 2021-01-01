package com.happy3w.persistence.jpa.translator;

import com.happy3w.persistence.core.filter.impl.AbstractInFilter;
import com.happy3w.persistence.jpa.context.ParameterContext;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.util.Arrays;
import java.util.List;

/**
 * in类条件的公共逻辑
 * @param <DT> 条件需要处理的数据类型
 * @param <FT> 过滤条件的类型
 */
public abstract class AbstractInTranslator<DT, FT extends AbstractInFilter<DT>>
        extends IFilterTranslator<FT> {
    protected AbstractInTranslator(Class<FT> filterType) {
        super(filterType);
    }

    @Override
    public List<Predicate> toPredicate(
            FT filter,
            ParameterContext<?, ?> context) {
        CriteriaBuilder cb = context.getCriteriaBuilder();

        CriteriaBuilder.In in = cb.in(context.getRoot().get(filter.getField()));
        for (DT v : filter.getRefs()) {
            in.value(v);
        }
        Predicate predicate = in;

        if (!filter.isPositive()) {
            predicate = cb.not(predicate);
        }
        return Arrays.asList(predicate);
    }
}
