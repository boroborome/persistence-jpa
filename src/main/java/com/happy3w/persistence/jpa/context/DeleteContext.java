package com.happy3w.persistence.jpa.context;

import com.happy3w.persistence.core.filter.IFilter;
import com.happy3w.persistence.jpa.translator.JpaTranslateAssistant;
import com.happy3w.toolkits.utils.ListUtils;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Predicate;
import java.util.List;

@Getter
@Setter
public class DeleteContext<DT> extends ParameterContext<DT, Query> {
    protected CriteriaDelete<DT> criteriaDelete;

    public DeleteContext(EntityManager entityManager, Class<DT> dataType) {
        super(entityManager);
        criteriaDelete = criteriaBuilder.createCriteriaDelete(dataType);
        root = criteriaDelete.from(dataType);
    }

    @Override
    public Query createQuery(List<IFilter> filterList, JpaTranslateAssistant translateAssistant) {
        List<Predicate> allPredicates = translateAssistant.translate(filterList, this);

        if (!ListUtils.isEmpty(allPredicates)) {
            this.getCriteriaDelete().where(allPredicates.toArray(new Predicate[allPredicates.size()]));
        }
        Query query = entityManager.createQuery(criteriaDelete);
        fillAllParameterValues(query);
        return query;
    }
}
