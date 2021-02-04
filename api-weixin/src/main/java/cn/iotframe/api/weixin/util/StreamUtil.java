package cn.iotframe.api.weixin.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class StreamUtil {

	public static String parse(InputStream inputStream) throws IOException {
		return parse(inputStream, StandardCharsets.UTF_8.name());
	}

	public static String parse(InputStream inputStream, String charset) throws IOException {
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length;
		while ((length = inputStream.read(buffer)) != -1) {
			result.write(buffer, 0, length);
		}
		String str = result.toString(charset);
		return str;
	}
}
