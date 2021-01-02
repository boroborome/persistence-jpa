package com.happy3w.persistence.jpa.translator;

import com.happy3w.persistence.core.filter.IFilter;
import com.happy3w.persistence.jpa.context.ParameterContext;
import com.happy3w.toolkits.manager.ConfigManager;
import com.happy3w.toolkits.utils.ListUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class JpaTranslateAssistant implements ITranslateAssistant {
    public static final JpaTranslateAssistant INSTANCE = new JpaTranslateAssistant();

    static {
        INSTANCE.regist(new EqualTranslator());
        INSTANCE.regist(new InTranslator());
        INSTANCE.regist(new NumRangeTranslator());
        INSTANCE.regist(new DateTimeRangeTranslator());
        INSTANCE.regist(new DateRangeTranslator());
        INSTANCE.regist(new MonthRangeTranslator());
        INSTANCE.regist(new LikeTranslator());
        INSTANCE.regist(new LikeInTranslator());
    }

    private ConfigManager<AbstractFilterTranslator> translateManager = ConfigManager.inherit();

    public JpaTranslateAssistant regist(AbstractFilterTranslator translator) {
        translateManager.regist(translator.getFilterType(), translator);
        return this;
    }

    public List<Predicate> translate(List<IFilter> filterList, ParameterContext<?, ?> context) {
        if (ListUtils.isEmpty(filterList)) {
            return null;
        }

        List<Predicate> allPredicates = new ArrayList<>();
        for (IFilter filter : filterList) {
            AbstractFilterTranslator translator = translateManager.findByType(filter.getClass());
            if (translator == null) {
                throw new UnsupportedOperationException("Unsupported filter:" + filter.getClass());
            }
            Predicate newPredicate = translator.translate(filter, context);
            if (newPredicate != null) {
                allPredicates.add(newPredicate);
            }
        }
        return allPredicates;
    }

    @Override
    public Predicate translatePositive(IFilter filter, ParameterContext<?, ?> context) {
        return null;
    }
}
