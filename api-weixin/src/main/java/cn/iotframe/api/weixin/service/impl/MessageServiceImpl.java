package cn.iotframe.api.weixin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.iotframe.api.weixin.model.ReceiveXmlModel;
import cn.iotframe.api.weixin.model.SendXmlModel;
import cn.iotframe.api.weixin.service.MessageService;
import cn.iotframe.api.weixin.service.factory.WeiXinMsgFactory;
import cn.iotframe.api.weixin.service.handler.MessageHandler;

@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	private WeiXinMsgFactory msgFactory;

	@Override
	public SendXmlModel handler(ReceiveXmlModel receiveXmlModel) {
		MessageHandler msgHandler = msgFactory.getHandler(receiveXmlModel.getMsgType());
		SendXmlModel sendXmlModel = msgHandler.handler(receiveXmlModel);
		return sendXmlModel;
	}

}
