package com.zhuravlov.repairagency.model.builder;

import com.zhuravlov.repairagency.model.entity.RepairFormEntity;
import com.zhuravlov.repairagency.model.entity.Status;
import com.zhuravlov.repairagency.model.entity.UserEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RepairFormBuilder {
    private RepairFormEntity repairForm;

    private RepairFormBuilder() {
        this.repairForm = new RepairFormEntity();
    }

    public static RepairFormBuilder getInstance() {
        return new RepairFormBuilder();
    }

    public RepairFormBuilder setCreationDate(LocalDateTime creationDate) {
        this.repairForm.setCreationDate(creationDate);
        return this;
    }

    public RepairFormBuilder setAuthor(UserEntity author) {
        this.repairForm.setAuthor(author);
        return this;
    }

    public RepairFormBuilder setCar(String car) {
        this.repairForm.setCar(car);
        return this;
    }

    public RepairFormBuilder setShortDescription(String shortDescription) {
        this.repairForm.setShortDescription(shortDescription);
        return this;
    }

    public RepairFormBuilder setDescription(String description) {
        this.repairForm.setDescription(description);
        return this;
    }

    public RepairFormBuilder setFeedback(String feedback) {
        this.repairForm.setFeedback(feedback);
        return this;
    }

    public RepairFormBuilder setRepairmanId(UserEntity repairman) {
        this.repairForm.setRepairman(repairman);
        return this;
    }

    public RepairFormBuilder setStatus(Status status) {
        this.repairForm.setStatus(status);
        return this;
    }

    public RepairFormBuilder setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.repairForm.setLastModifiedDate(lastModifiedDate);
        return this;
    }

    public RepairFormBuilder setPrice(BigDecimal price) {
        this.repairForm.setPrice(price);
        return this;
    }

    public RepairFormEntity build() {
        return repairForm;
    }


}
