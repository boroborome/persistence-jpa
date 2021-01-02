package com.happy3w.persistence.jpa.context;

import com.happy3w.persistence.core.filter.IFilter;
import com.happy3w.persistence.jpa.translator.JpaTranslateAssistant;
import com.happy3w.toolkits.utils.ListUtils;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import java.util.List;

@Getter
@Setter
public class RetrievalContext<DT> extends ParameterContext<DT, TypedQuery<DT>> {
    protected CriteriaQuery<DT> criteriaQuery;

    public RetrievalContext(EntityManager entityManager, Class<DT> dataType) {
        super(entityManager);
        criteriaQuery = criteriaBuilder.createQuery(dataType);
        root = criteriaQuery.from(dataType);
        criteriaQuery.select(root);
    }

    @Override
    public TypedQuery<DT> createQuery(List<IFilter> filterList, JpaTranslateAssistant translateAssistant) {
        List<Predicate> allPredicates = translateAssistant.translate(filterList, this);

        if (!ListUtils.isEmpty(allPredicates)) {
            this.getCriteriaQuery().where(allPredicates.toArray(new Predicate[allPredicates.size()]));
        }
        TypedQuery typeQuery = entityManager.createQuery(this.getCriteriaQuery());
        fillAllParameterValues(typeQuery);
        return typeQuery;
    }
}
