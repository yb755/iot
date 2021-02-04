package cn.iotframe.common.utils.bean;

import org.apache.commons.beanutils.PropertyUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


public class BeanMapUtil {
	
	/**
	 * 获得同时有get和set的field和value。
	 * 
	 * @param bean
	 * @return
	 */
	public static Map<String, Object> describe(Object bean) {
		Map<String, Object> des = new HashMap<String, Object>();
		PropertyDescriptor desor[] = PropertyUtils.getPropertyDescriptors(bean);
		String name = null;
		for (int i = 0; i < desor.length; i++) {
			if (desor[i].getReadMethod() != null && desor[i].getWriteMethod() != null) {
				name = desor[i].getName();
				try {
					des.put(name, PropertyUtils.getProperty(bean, name));
				} catch (Exception e) {
					throw new RuntimeException("属性不存在：" + name);
				}
			}
		}
		return des;
	}

	/**
	 * 将一个 JavaBean 对象转化为一个  map
	 * @param bean 要转化的JavaBean 对象
	 * @return 转化出来的  map 对象
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static Map<String, Object> convertBean(Object bean){
		try {
			Class<?> type = bean.getClass();
			Map<String, Object> returnMap = new HashMap<>();
			BeanInfo beanInfo = Introspector.getBeanInfo(type);
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (int i = 0; i < propertyDescriptors.length; i++) {
				PropertyDescriptor descriptor = propertyDescriptors[i];
				String propertyName = descriptor.getName();
				if (!propertyName.equals("class")) {
					Method readMethod = descriptor.getReadMethod();
					try {
						Object result = readMethod.invoke(bean, new Object[0]);
						if (result != null) {
							returnMap.put(propertyName, result);
						} else {
							returnMap.put(propertyName, "");
						}
					} catch (IllegalAccessException e) {
					}catch (InvocationTargetException e) {
					}
				}
			}
			return returnMap;
		}  catch (IllegalArgumentException e) {
		} catch (IntrospectionException e) {
		}
		return null ;
	}
	
	/**
	 * 将一个 map 对象转化为一个 JavaBean
	 * @param type 要转化的类型  
	 * @param map 包含属性值的 map
	 * @return  转化出来的 JavaBean 对象
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws InvocationTargetException
	 */
	public static <T> T convertMap(Class<T> type, Map<String,Object> map){    
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性    
			T obj = type.newInstance();
			// 给 JavaBean 对象的属性赋值    
			PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();    
			for (int i = 0; i< propertyDescriptors.length; i++) {    
			    PropertyDescriptor descriptor = propertyDescriptors[i];    
			    String propertyName = descriptor.getName();    
			    if (map.containsKey(propertyName)) {    
			        // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。    
			        Object value = map.get(propertyName);    
			        Object[] args = new Object[1];    
			        args[0] = value; 
			        try {
						descriptor.getWriteMethod().invoke(obj, args);
					} catch (IllegalArgumentException e) {
					} catch (InvocationTargetException e) {
					}    
			    }    
			}
			return obj;    
		} catch (IllegalAccessException e) {
		} catch (InstantiationException e) {
		} catch (IntrospectionException e) {
		}    
        return null;    
    }
}
