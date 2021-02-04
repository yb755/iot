package cn.iotframe.api.weixin.service.handler;

import cn.iotframe.api.weixin.annotation.WeiXinMsgHandler;
import cn.iotframe.api.weixin.model.ReceiveXmlModel;
import cn.iotframe.api.weixin.model.SendXmlModel;

@WeiXinMsgHandler(msgType = "image")
public class ImageMsgHandler implements MessageHandler {

	@Override
	public SendXmlModel handler(ReceiveXmlModel receiveXmlModel) {
		SendXmlModel sendXmlModel = new SendXmlModel();
		sendXmlModel.setMsgType("text");
		sendXmlModel.setContent("收到图片了");
		return sendXmlModel;
	}

}
