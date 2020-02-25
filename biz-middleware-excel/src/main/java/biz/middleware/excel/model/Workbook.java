package biz.middleware.excel.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@XmlRootElement(name = "workbook")
@XmlAccessorType(XmlAccessType.FIELD)
public class Workbook {



    @XmlElement(name = "worksheet")
    private List<Worksheet> worksheets;
}
