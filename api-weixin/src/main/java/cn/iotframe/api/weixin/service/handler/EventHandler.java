package cn.iotframe.api.weixin.service.handler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import cn.iotframe.api.weixin.annotation.WeiXinMsgHandler;
import cn.iotframe.api.weixin.model.ReceiveXmlModel;
import cn.iotframe.api.weixin.model.SendXmlModel;
import cn.iotframe.biz.user.service.UserService;
import cn.iotframe.biz.user.service.WeiXinService;

@WeiXinMsgHandler(msgType = "event")
public class EventHandler implements MessageHandler {

	private Map<String, EventExecutor> executorMap = new HashMap<String, EventExecutor>();

	@Autowired
	private WeiXinService weiXinService;

	@Autowired
	private UserService userService;

	@PostConstruct
	private void init() {
		executorMap.put("unsubscribe", new UnsubscribeEventExecutor());
		executorMap.put("subscribe", new SubscribeEventExecutor());
		executorMap.put("scan", new ScanEventExecutor());
	}

	@Override
	public SendXmlModel handler(ReceiveXmlModel receiveXmlModel) {
		String eventName = receiveXmlModel.getEvent().toLowerCase();
		EventExecutor executor = executorMap.get(eventName);
		if (executor != null) {
			return executor.execute(receiveXmlModel);
		}
		return null;
	}

	private class UnsubscribeEventExecutor implements EventExecutor {

		@Override
		public SendXmlModel execute(ReceiveXmlModel receiveXmlModel) {
			String fromUserName = receiveXmlModel.getFromUserName();
			WeiXinEntity weiXinEntity = weiXinService.findByOpenId(fromUserName);
			if (weiXinEntity != null) {
				weiXinEntity.setOpenId(null);
				weiXinEntity.setUpdateTime(new Date());
				weiXinService.updateByPrimaryKey(weiXinEntity);
			}
			return null;
		}

	}

	private class SubscribeEventExecutor implements EventExecutor {

		@Override
		public SendXmlModel execute(ReceiveXmlModel receiveXmlModel) {
			String eventKey = receiveXmlModel.getEventKey();
			String openId = receiveXmlModel.getFromUserName();
			String toUserName = receiveXmlModel.getToUserName();
			SendXmlModel sendXmlModel = new SendXmlModel();
			sendXmlModel.setFromUserName(toUserName);
			sendXmlModel.setToUserName(openId);
			sendXmlModel.setMsgType("text");
			if (eventKey.startsWith("qrscene_")) {
				String userId = eventKey.substring(8);// 除去qrscene_字符
				if (!userId.matches("[0-9]+")) {
					sendXmlModel.setContent("欢迎您的关注，请从APP中扫码,操作路径：APP->我的->新消息提醒->微信通知开关");
				} else {
					UserEntity userEntity = userService.findByUserId(Integer.valueOf(userId));
					WeiXinEntity weiXinEntity = weiXinService.selectByUserId(Integer.valueOf(userId));
					String mobile = "";
					if (userEntity != null) {
						mobile = userEntity.getMobile();
					}
					if (weiXinEntity != null) {
						weiXinEntity.setOpenId(openId);
						weiXinEntity.setUpdateTime(new Date());
						weiXinService.updateByPrimaryKeySelective(weiXinEntity);
						sendXmlModel.setContent("欢迎您的关注，您已自动与平台的帐号" + mobile + "进行了绑定");
					} else {
						sendXmlModel.setContent("欢迎您的关注，请从APP中扫码,操作路径：APP->我的->新消息提醒->微信通知开关");
					}
				}
			} else {
				sendXmlModel.setContent("欢迎您的关注，请从APP中扫码,操作路径：APP->我的->新消息提醒->微信通知开关");
			}
			return sendXmlModel;
		}
	}

	private class ScanEventExecutor implements EventExecutor {

		@Override
		public SendXmlModel execute(ReceiveXmlModel receiveXmlModel) {
			String eventKey = receiveXmlModel.getEventKey();
			String fromUserName = receiveXmlModel.getFromUserName();
			String toUserName = receiveXmlModel.getToUserName();
			SendXmlModel sendXmlModel = new SendXmlModel();
			sendXmlModel.setFromUserName(toUserName);
			sendXmlModel.setToUserName(fromUserName);
			sendXmlModel.setMsgType("text");
			if (eventKey.matches("[0-9]+")) {
				String userId = eventKey;
				UserEntity userEntity = userService.findByUserId(Integer.valueOf(userId));
				WeiXinEntity weiXinEntity = weiXinService.selectByUserId(Integer.valueOf(userId));
				String mobile = "";
				if (userEntity != null) {
					mobile = userEntity.getMobile();
				}
				if (weiXinEntity != null) {
					weiXinEntity.setOpenId(fromUserName);
					weiXinEntity.setUpdateTime(new Date());
					weiXinService.updateByPrimaryKeySelective(weiXinEntity);
					sendXmlModel.setContent("欢迎您的关注，您已自动与平台的帐号" + mobile + "进行了绑定");
				} else {
					sendXmlModel.setContent("欢迎您的关注，请从APP中扫码,操作路径：APP->我的->新消息提醒->微信通知开关");
				}
			} else {
				sendXmlModel.setContent("欢迎您的关注，请从APP中扫码,操作路径：APP->我的->新消息提醒->微信通知开关");
			}
			return sendXmlModel;
		}
	}

	interface EventExecutor {
		SendXmlModel execute(ReceiveXmlModel receiveXmlModel);
	}
}
