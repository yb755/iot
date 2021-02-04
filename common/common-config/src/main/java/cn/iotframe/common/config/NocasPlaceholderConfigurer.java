package cn.iotframe.common.config;

import java.io.File;
import java.io.StringReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.stereotype.Component;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;

@Component
public class NocasPlaceholderConfigurer extends PropertySourcesPlaceholderConfigurer {

	private String NOCOS_SERVER_IP = "nacos.server.host";
	private String NOCOS_SERVER_PORT = "nacos.server.port";
	private String NOCOS_SERVER_LOCAL = "nacos.server.local";
	private String NOCOS_CONFIG_NAMESPACE = "nacos.config.namespace";
	private String NOCOS_CONFIG_DATAID = "nacos.config.dataId";
	private String NOCOS_CONFIG_GROUP = "nacos.config.group";

	private String nocosServerIp;
	private Integer nocosServerPort;
	private boolean ncosServerLocal = true;
	private String nocosConfigNamespace;
	private String nocosConfigDataId;
	private String nocosConfigGroupId;

	private static Map<String, Object> propertyMap = new HashMap<String, Object>();

	private String getRemoteProperties(String serverAddr, String dataId, String group, String namespace)
			throws Exception {
		Properties properties = new Properties();
		properties.put("serverAddr", serverAddr);
		if (namespace != null) {
			properties.put("namespace", namespace);
		}
		ConfigService configService = NacosFactory.createConfigService(properties);
		String content = configService.getConfig(dataId, group, 5000);
		return content;

	}

	@Override
	public void setEnvironment(Environment environment) {
		StandardEnvironment env = (StandardEnvironment) environment;
		nocosServerIp = environment.getProperty(NOCOS_SERVER_IP, "nacos.iotframe.com");
		nocosServerPort = Integer.valueOf(environment.getProperty(NOCOS_SERVER_PORT, "8848"));
		ncosServerLocal = ("true".equals(environment.getProperty(NOCOS_SERVER_LOCAL))) ? true : false;
		nocosConfigNamespace = environment.getProperty(NOCOS_CONFIG_NAMESPACE);
		nocosConfigDataId = environment.getProperty(NOCOS_CONFIG_DATAID);
		nocosConfigGroupId = environment.getProperty(NOCOS_CONFIG_GROUP, "DEFAULT_GROUP");
		if (!ncosServerLocal) {
			Map<String, Object> map = loadRemoteProperty();
			PropertySource<Map<String, Object>> propertySource = new MapPropertySource("remote_properties", map);
			env.getPropertySources().addLast(propertySource);
		} else {
			// TODO YeBing将文件写入本地的conf目录,然后参考disconf使用标识位，是否启用本地版本,
			Map<String, Object> map = loadLocalProperty();
			PropertySource<Map<String, Object>> propertySource = new MapPropertySource("remote_properties", map);
			env.getPropertySources().addLast(propertySource);
		}
		super.setEnvironment(env);
	}

	private Map<String, Object> loadLocalProperty() {
		Map<String, Object> localConfig = new HashMap<String, Object>();
		// 读取本地目录文件，写入env
		File confFolder = new File(System.getProperty("user.dir") + "/conf");
		if (confFolder.exists()) {
			File[] files = confFolder.listFiles();
			for (File file : files) {
				if (!file.getName().startsWith("application") && file.getName().endsWith(".properties")) {
					System.out.println(file);
				}
			}
		}
		return localConfig;
	}

	private Map<String, Object> loadRemoteProperty() {
		try {
			String[] dataIds = nocosConfigDataId.split(",");
			for (String dataId : dataIds) {
				if (StringUtils.isEmpty(dataId)) {
					continue;
				}
				String remoteContent = getRemoteProperties(nocosServerIp + ":" + nocosServerPort, dataId,
						nocosConfigGroupId, nocosConfigNamespace);
				if (remoteContent == null) {
					continue;
				}
				System.out.println(remoteContent);
				Properties proper = new Properties();
				proper.load(new StringReader(remoteContent)); // 把字符串转为reader
				Enumeration<?> enum1 = proper.propertyNames();
				while (enum1.hasMoreElements()) {
					String strKey = (String) enum1.nextElement();
					String strValue = proper.getProperty(strKey);
					propertyMap.put(strKey, strValue);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return propertyMap;
	}

	public static Object getProperty(String name) {
		return propertyMap.get(name);
	}
}
