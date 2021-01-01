package com.happy3w.persistence.jpa.translator;

import com.happy3w.persistence.core.filter.impl.AbstractEqualFilter;
import com.happy3w.persistence.jpa.context.ParameterContext;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import java.util.Arrays;
import java.util.List;

/**
 * 等于类条件的公共逻辑
 * @param <DT> 条件需要处理的数据类型
 * @param <FT> 过滤条件的类型
 */
public abstract class AbstractEqualTranslator<DT, FT extends AbstractEqualFilter<DT>>
        extends IFilterTranslator<FT> {
    protected AbstractEqualTranslator(Class<FT> filterType) {
        super(filterType);
    }

    @Override
    public List<Predicate> toPredicate(
            FT filter,
            ParameterContext<?, ?> context) {
        CriteriaBuilder cb = context.getCriteriaBuilder();

        Predicate predicate;

        ParameterExpression<DT> parameter = context.newParameter(filter.getRef());
        predicate = cb.equal(context.getRoot().get(filter.getField()), parameter);
        if (!filter.isPositive()) {
            predicate = cb.not(predicate);
        }
        return Arrays.asList(predicate);
    }
}
