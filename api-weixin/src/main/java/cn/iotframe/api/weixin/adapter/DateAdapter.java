package cn.iotframe.api.weixin.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateAdapter extends XmlAdapter<String, Date> {

	private Object dateFormat = new Object();

	// 简单版
	@Override
	public String marshal(Date v) throws Exception {
		synchronized (dateFormat) {
			return new SimpleDateFormat("yyyy-MM-dd").format(v);
		}
	}

	@Override
	public Date unmarshal(String v) throws Exception {
		synchronized (dateFormat) {
			return new SimpleDateFormat("yyyy-MM-dd").parse(v);
		}
	}
}
