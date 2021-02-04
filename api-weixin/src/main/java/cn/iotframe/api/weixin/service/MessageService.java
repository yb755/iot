package cn.iotframe.api.weixin.service;

import cn.iotframe.api.weixin.model.ReceiveXmlModel;
import cn.iotframe.api.weixin.model.SendXmlModel;

public interface MessageService {

	SendXmlModel handler(ReceiveXmlModel receiveXmlModel);
}
