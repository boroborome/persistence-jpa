package com.happy3w.persistence.jpa.translator;

import com.happy3w.persistence.core.filter.IFilter;
import com.happy3w.persistence.jpa.context.ParameterContext;

import javax.persistence.criteria.Predicate;

public interface ITranslateAssistant {
    /**
     * 将filter转换为正向的queryBuilder
     * @param filter 过滤条件
     * @return es query builder.
     */
    Predicate translatePositive(IFilter filter, ParameterContext<?, ?> context);
}
