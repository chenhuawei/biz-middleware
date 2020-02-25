package biz.middleware.excel.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Loop {
    @XmlAttribute
    private Integer startRow;
    @XmlAttribute
    private Integer endRow;
    @XmlAttribute
    private String items;
    @XmlAttribute
    private String var;
    @XmlAttribute
    private String varType;
    @XmlElement
    private Section section;

    @XmlElement(name = "loopbreakcondition")
    private LoopBreakCondition loopBreakCondition;

}
