package biz.middleware.excel.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Worksheet {

    @XmlAttribute(name = "idx")
    private Integer index;

    @XmlElement(name = "section")
    private Section section;

    @XmlElement(name = "loop")
    private Loop loop;
}
