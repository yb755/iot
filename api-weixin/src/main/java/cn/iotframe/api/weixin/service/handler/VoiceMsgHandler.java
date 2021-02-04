package cn.iotframe.api.weixin.service.handler;

import cn.iotframe.api.weixin.annotation.WeiXinMsgHandler;
import cn.iotframe.api.weixin.model.ReceiveXmlModel;
import cn.iotframe.api.weixin.model.SendXmlModel;

@WeiXinMsgHandler(msgType = "voice")
public class VoiceMsgHandler implements MessageHandler {

	@Override
	public SendXmlModel handler(ReceiveXmlModel receiveXmlModel) {
		SendXmlModel sendXmlModel = new SendXmlModel();
		sendXmlModel.setMsgType("text");
		sendXmlModel.setContent("您的语音收到了");
		return sendXmlModel;
	}

}
