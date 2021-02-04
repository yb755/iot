package cn.iotframe.api.weixin.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.iotframe.api.weixin.model.ReceiveXmlModel;
import cn.iotframe.api.weixin.model.SendXmlModel;
import cn.iotframe.api.weixin.service.MessageService;
import cn.iotframe.api.weixin.util.ReceiveXmlProcess;
import cn.iotframe.api.weixin.util.SendXmlProcess;
import cn.iotframe.api.weixin.util.StreamUtil;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class WeixinTokenController {

	@Autowired
	private MessageService messageService;

	@RequestMapping("/interface")
	public void api(String echostr, HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (echostr != null) {
			response.getWriter().write(echostr);
			response.flushBuffer();
			return;
		}
		InputStream is = request.getInputStream();
		String revXml = StreamUtil.parse(is);
		log.info("\r\n收到消息msg=\r\n{}", revXml);
		ReceiveXmlModel receiveXmlModel = ReceiveXmlProcess.getMsgEntity(revXml);
		SendXmlModel sendXmlModel = messageService.handler(receiveXmlModel);
		if (sendXmlModel != null) {
			sendXmlModel.setCreateTime(Long.valueOf(System.currentTimeMillis() / 1000).intValue());
			if (sendXmlModel.getFromUserName() == null) {
				sendXmlModel.setFromUserName(receiveXmlModel.getToUserName());
			}
			if (sendXmlModel.getToUserName() == null) {
				sendXmlModel.setToUserName(receiveXmlModel.getFromUserName());
			}
			String sendXml = SendXmlProcess.getMsg(sendXmlModel);
			log.info("\r\n回复消息msg=\r\n{}", sendXml);
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(sendXml);
			response.flushBuffer();
		} else {
			log.info("回复消息msg=null");
		}
	}
}
