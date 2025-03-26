package demo.persistence.repository;

import demo.persistence.model.OrderEntity;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;

public class OrderEntitySpecifications {
    public static Specification<OrderEntity> filterByGrouping(Boolean grouped,
                                                              Long groupId,
                                                              GroupOperation operation) {
        return (root, query, cb) -> {
            Predicate groupedPredicate = null;
            Predicate groupIdPredicate = null;

            if (grouped != null) {
                root.join("")
                groupedPredicate = cb.equal(root.get("grouped"), grouped);
            }

            if (groupId != null) {
                groupIdPredicate = cb.equal(root.get("groupId"), groupId);
            }

            if (groupedPredicate == null && groupIdPredicate == null) {
                return cb.conjunction();
            }

            if (operation == GroupOperation.AND) {
                return groupedPredicate != null && groupIdPredicate != null ? 
                       cb.and(groupedPredicate, groupIdPredicate) : 
                       groupedPredicate != null ? groupedPredicate : groupIdPredicate;
            } else {
                return groupedPredicate != null || groupIdPredicate != null ?
                       cb.or(groupedPredicate, groupIdPredicate) :
                       groupedPredicate != null ? groupedPredicate : groupIdPredicate;
            }
        };
    }
}