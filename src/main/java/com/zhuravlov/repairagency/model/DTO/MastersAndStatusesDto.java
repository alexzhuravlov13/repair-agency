package com.zhuravlov.repairagency.model.DTO;

import com.zhuravlov.repairagency.model.entity.Status;
import com.zhuravlov.repairagency.model.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MastersAndStatusesDto {
    List<UserEntity> masters;
    List<Status> statuses;
}
