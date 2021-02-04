package cn.iotframe.api.weixin.service.handler;

import cn.iotframe.api.weixin.model.ReceiveXmlModel;
import cn.iotframe.api.weixin.model.SendXmlModel;

public interface MessageHandler {

	SendXmlModel handler(ReceiveXmlModel receiveXmlModel);
}
