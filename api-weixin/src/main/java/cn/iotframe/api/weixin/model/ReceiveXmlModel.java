package cn.iotframe.api.weixin.model;

import lombok.Data;

@Data
public class ReceiveXmlModel {	
	private String ToUserName = "";
	private String FromUserName = "";
	private String CreateTime = "";
	private String MsgType = "";
	private String MsgId = "";
	private String Event = "";
	private String EventKey = "";
	private String Ticket = "";
	private String Latitude = "";
	private String Longitude = "";
	private String Precision = "";
	private String PicUrl = "";
	private String MediaId = "";
	private String Title = "";
	private String Description = "";
	private String Url = "";
	private String Location_X = "";
	private String Location_Y = ""; 
	private String Scale = "";
	private String Label = "";
	private String Content = "";
	private String Format = "";
	private String Recognition = "";
	private String MsgID="";	//收到模板消息时，微信使用这个ID
	private String Status;
	public String getMsgID() {
		return MsgID;
	}
	public void setMsgID(String msgID) {
		MsgID = msgID;
	}
	public String getMsgId() {
		return MsgId;
	}
	public void setMsgId(String msgId) {
		MsgId = msgId;
	}
}
