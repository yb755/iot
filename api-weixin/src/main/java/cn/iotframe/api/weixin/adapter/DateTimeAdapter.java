package cn.iotframe.api.weixin.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateTimeAdapter extends XmlAdapter<String, Date> {

	private Object dateFormat = new Object();

	// 简单版
	@Override
	public String marshal(Date v) throws Exception {
		synchronized (dateFormat) {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(v);
		}
	}

	@Override
	public Date unmarshal(String v) throws Exception {
		synchronized (dateFormat) {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(v);
		}
	}
}
