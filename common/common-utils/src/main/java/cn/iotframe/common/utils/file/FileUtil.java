package cn.iotframe.common.utils.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;

public class FileUtil {

	/**
	 * 获得在classpath目录下指定路径的文件
	 * @param path
	 * @param fileName
	 * @return
	 */
	public static InputStream getFileFromClassPath(String path,String fileName){
		InputStream in = null;
		try {
			in = FileUtil.class.getResourceAsStream(
					"/" + path + File.separator + fileName);
			return in ;
		} catch (Exception e) {
			
		}
		return null;
	}
	public static String getClasspathRoot(){
		URL path =  Thread.currentThread().getContextClassLoader().getResource("");
		String urlPath =path==null?"":path.toString() ;
		if(StringUtils.isNotBlank(urlPath)&&urlPath.contains("file:")){
			return urlPath.substring("file:".length()); 
		}
		return null ;
	}
	
	public static String getClasspathRoot(String file){
		URL path =  Thread.currentThread().getContextClassLoader().getResource("");
		String urlPath =path==null?"":path.toString() ;
		if(StringUtils.isNotBlank(urlPath)&&urlPath.contains("file:")){
			urlPath = urlPath.substring("file:".length()) ;
			if(!urlPath.endsWith(file)){
				urlPath = urlPath+file+File.separator;
			}
			return urlPath; 
		}
		return null ;
	}
	/**
	 * 获得在classpath目录下指定路径的文件
	 * @param path
	 * @param fileName
	 * @return
	 */
	public static InputStream getFileFromClassPath(String path){
		InputStream in = null;
		try {
			in = FileUtil.class.getClassLoader().getResourceAsStream(path);
			return in ;
		} catch (Exception e) {
			
		}
		return null;
	}
	
	/**
	 * 获取指定路径的文件
	 * @param path
	 * @param fileName
	 * @return
	 */
	public static InputStream getFileFromPath(String path,String fileName){
		try {
			return new FileInputStream(new File(path,fileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null ;
	}
	
	/**
	 * 获取指定路径的文件
	 * @param path
	 * @param fileName
	 * @return
	 */
	public static InputStream getFileFromPath(String fileName){
		try {
			return new FileInputStream(new File(fileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null ;
	}
	
	/**
	 * 创建文件夹
	 * @param path
	 * @return
	 */
	public static String createDir(String path){
		File file = new File(path);
		if(!file.exists()){     
			file.mkdirs(); 
	    }
		return path ;
	}
	
	/**
	 * 创建文件,可以同时创建父目录
	 * @param path
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static File createFile(String path,String fileName){
		try {
			File file = new File(path,fileName);
			if(!file.getParentFile().exists()){
				file.getParentFile().mkdirs();
			}
			if(!file.exists()){
				file.createNewFile();
			}
			return file ;
		} catch (IOException e) {
			
		}
		return null;
	}
	
	/**
	 * 创建文件,可以同时创建父目录
	 * @param path
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static File createFile(String fileName){
		try {
			File file = new File(fileName);
			if(!file.getParentFile().exists()){
				file.getParentFile().mkdirs();
			}
			if(!file.exists()){
				file.createNewFile();
			}
			return file ;
		} catch (IOException e) {
			
		}
		return null;
	}
	
	/**
	 * 删除该文件或者改文件夹(包含其下所有的文件)
	 * @param file
	 */
	public static void delAllFile(String filePath){
		delAllFile(new File(filePath));
	}
	
	/**
	 * 删除该文件或者改文件夹(包含其下所有的文件)
	 * @param file
	 */
	public static void delAllFile(File file){
		if(file.isDirectory()){
			File[] files = file.listFiles() ;
			if(files==null||files.length==0){
				file.delete();
			}else{
				for(File delFile:files){
					delAllFile(delFile);
				}
				//删除本身文件夹
				file.delete();
			}
		}else{
			file.delete();
		}
	}
	
}
