package com.happy3w.persistence.jpa.translator;

import com.happy3w.persistence.core.filter.IFilter;
import com.happy3w.persistence.jpa.context.ParameterContext;

import javax.persistence.criteria.Predicate;
import java.util.List;

public interface ITranslateAssistant {
    /**
     * 将filter翻译为工具使用的条件
     * @param filterList 过滤条件
     * @param context 上下文
     * @return 翻译后条件.
     */
    List<Predicate> translate(List<IFilter> filterList, ParameterContext<?, ?> context);
}
