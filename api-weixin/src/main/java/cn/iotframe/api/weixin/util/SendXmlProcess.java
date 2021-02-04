package cn.iotframe.api.weixin.util;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import com.sun.xml.internal.bind.marshaller.CharacterEscapeHandler;

import cn.iotframe.api.weixin.model.SendXmlModel;

@SuppressWarnings("restriction")
public class SendXmlProcess {

	public static String getMsg(SendXmlModel xmlModel) {
		if (xmlModel == null) {
			return null;
		}
		return converToXml(xmlModel, "UTF-8");
	}

	private static String converToXml(Object obj, String encoding) {
		String result = null;
		try {
			StringWriter writer = new StringWriter();
			JAXBContext context = JAXBContext.newInstance(obj.getClass());
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
			marshaller.setProperty("com.sun.xml.internal.bind.marshaller.CharacterEscapeHandler", new CharacterEscapeHandler() {
				@Override
				public void escape(char[] ch, int start, int length, boolean isAttVal, Writer writer) throws IOException {
					writer.write(ch, start, length);
				}
			});
			marshaller.marshal(obj, writer);
			result = writer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
