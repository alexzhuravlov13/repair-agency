package com.zhuravlov.repairagency.model;

import com.zhuravlov.repairagency.model.entity.RepairFormEntity;
import com.zhuravlov.repairagency.model.entity.Status;
import org.springframework.data.jpa.domain.Specification;

public class RepairFormSpecs {

    /**
     * if master == null then specification is ignored
     */
    public static Specification<RepairFormEntity> masterEquals(Integer userId) {
        return (root, query, builder) ->
                userId == null ?
                        builder.conjunction() :
                        builder.equal(root.get("repairman").get("userId"), userId);
    }

    /**
     * if status == null then specification is ignored
     */
    public static Specification<RepairFormEntity> statusEquals(Status status) {
        return (root, query, builder) ->
                status == null ?
                        builder.conjunction() :
                        builder.equal(root.get("status"), status);
    }

}
