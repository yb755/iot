package cn.iotframe.api.weixin.service.handler;

import cn.iotframe.api.weixin.annotation.WeiXinMsgHandler;
import cn.iotframe.api.weixin.model.ReceiveXmlModel;
import cn.iotframe.api.weixin.model.SendXmlModel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WeiXinMsgHandler(msgType = "text")
public class TextMsgHandler implements MessageHandler {

	// private ObjectMapper objectMapper = ObjectMapperUtil.getObjectMapper();

	@Override
	public SendXmlModel handler(ReceiveXmlModel receiveXmlModel) {
		SendXmlModel sendXmlModel = new SendXmlModel();
		String content = receiveXmlModel.getContent();
		sendXmlModel.setMsgType("text");
		sendXmlModel.setContent(handler(content));
		return sendXmlModel;
	}

	private String handler(String keyword) {
		String result = null;
		try {

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if (result == null) {
			result = keyword;
		}
		return result;
	}
}
