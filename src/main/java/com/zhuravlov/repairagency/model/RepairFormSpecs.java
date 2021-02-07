package com.zhuravlov.repairagency.model;

import com.zhuravlov.repairagency.model.entity.RepairFormEntity;
import com.zhuravlov.repairagency.model.entity.Status;
import com.zhuravlov.repairagency.model.entity.UserEntity;
import org.springframework.data.jpa.domain.Specification;

public class RepairFormSpecs {

    /** if master == null then specification is ignored */
    public static Specification<RepairFormEntity> masterEquals(Integer masterId) {
        return (root, query, builder) ->
                masterId == null ?
                        builder.conjunction() :
                        builder.equal(root.get("repairman").get("userId"), masterId);
    }

    /** if status == null then specification is ignored */
    public static Specification<RepairFormEntity> statusEquals(Status status) {
        return (root, query, builder) ->
                status == null ?
                        builder.conjunction() :
                        builder.equal(root.get("status"), status);
    }

}
