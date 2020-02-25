package biz.middleware.excel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "mapping")
@XmlAccessorType(XmlAccessType.FIELD)
public class Mapping {

    @XmlAttribute
    private Integer row;

    @XmlAttribute
    private Integer col;

    @XmlValue
    private String field;
}
