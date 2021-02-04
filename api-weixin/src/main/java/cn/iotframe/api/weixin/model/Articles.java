package cn.iotframe.api.weixin.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {})
public class Articles {

	private String Title;

	private String Description;

	private String PicUrl;

	private String Url;
}
