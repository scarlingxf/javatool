package org.ysh.tool.aunit.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.io.InputStream;
import java.util.Arrays;

public class MyClassLoader extends ClassLoader{
	private static String searchRoot = System.getProperty("myClassPath") != null ? System.getProperty("myClassPath") : ".";

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		File f = new File(searchRoot + File.separatorChar +  name + ".class");
		if(! f.exists()){
			throw new ClassNotFoundException("Can not find class " + name + "  in " + searchRoot);
		}
		
		Class<?> clz = null;
		try{
			byte[] content = new byte[1024 * 10];
			byte[] buffer = new byte[1024];
			int len = 0;
			int readLen = 0;
			
			InputStream input = new FileInputStream(f);
			
			while((readLen = input.read(buffer)) != -1){
				if(content.length < (readLen + len)){
					content = Arrays.copyOf(content, (readLen + len + 1) * 2);
				}
				
				System.arraycopy(buffer, 0, content, len, readLen);
				len += readLen;
			}
			
			clz = super.defineClass(null, content, 0, len);
		}catch(IOException e){
			throw new ClassNotFoundException("Can not load class " + f.getAbsolutePath(), e);
		}
		
		return clz;
	}

}
