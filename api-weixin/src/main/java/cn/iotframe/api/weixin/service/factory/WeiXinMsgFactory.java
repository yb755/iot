package cn.iotframe.api.weixin.service.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import cn.iotframe.api.weixin.annotation.WeiXinMsgHandler;
import cn.iotframe.api.weixin.service.handler.MessageHandler;

@Component
public class WeiXinMsgFactory implements ApplicationContextAware {

	private Map<String, MessageHandler> MSG_HANDLER = new HashMap<String, MessageHandler>();

	public MessageHandler getHandler(String msgType) {
		return MSG_HANDLER.get(msgType);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		initFactory(applicationContext);
	}

	private void initFactory(ApplicationContext applicationContext) {
		// 扫描ProtocalType注解,获取Bean的解析类
		// 获取所有的处理实现类
		Map<String, MessageHandler> beansMap = applicationContext.getBeansOfType(MessageHandler.class);
		Set<String> keys = beansMap.keySet();
		for (String key : keys) {
			MessageHandler msgHandler = beansMap.get(key);
			WeiXinMsgHandler annotation = msgHandler.getClass().getAnnotation(WeiXinMsgHandler.class);
			if (annotation != null) {
				MSG_HANDLER.put(annotation.msgType(), msgHandler);
			}
		}
	}
}
