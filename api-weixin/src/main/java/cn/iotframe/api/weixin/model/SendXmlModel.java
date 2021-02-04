package cn.iotframe.api.weixin.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import cn.iotframe.api.weixin.adapter.CDATAAdapter;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "xml")
@XmlType(propOrder = {})
public class SendXmlModel {

	@XmlElement(required = true)
	private String ToUserName;

	@XmlElement(required = true)
	private String FromUserName;

	@XmlElement(required = true)
	private Integer CreateTime;

	@XmlElement
	private String MsgType;

	@XmlElement
	@XmlJavaTypeAdapter(value = CDATAAdapter.class)
	private String Content;

	@XmlElement
	private Voice Voice;

	@XmlElement
	private Image Image;

	@XmlElement
	private Music Music;
	
	@XmlElement
	private Integer ArticleCount;
	
	@XmlElement
	private List<Articles> Articles;
}
