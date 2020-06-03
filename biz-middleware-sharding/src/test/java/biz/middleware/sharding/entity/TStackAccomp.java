package biz.middleware.sharding.entity;

import lombok.Data;

import java.util.Date;

@Data
public class TStackAccomp {
    private Long uId;
    private Integer lineId;
    private Integer equipmentId;
    private Integer stackId;
    private Date outTime;
    private Date createTime;
}
