package biz.middleware.excel.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class RowCheck {

    @XmlAttribute
    private Integer offset;

    @XmlElement(name = "cellcheck")
    private CellCheck cellCheck;
}
