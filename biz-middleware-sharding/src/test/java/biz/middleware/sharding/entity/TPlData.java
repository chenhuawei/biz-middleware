package biz.middleware.sharding.entity;

import lombok.Data;

import java.util.Date;

@Data
public class TPlData {
    private Long id;
    private Integer lineId;
    private Integer equipmentId;
    private Date createTime;

    private Integer dataId;
    private Date checkTime;
    private String material;
    private Long waferId;
    private String rank;
    private String ng;
    private String comment;
    private String imagePath;
}
