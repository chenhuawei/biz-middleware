package biz.middleware.excel.model;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Section {
    @XmlAttribute
    private Integer startRow;

    @XmlAttribute
    private Integer endRow;

    @XmlElement(name = "mapping")
    private List<Mapping> mappings;
}
