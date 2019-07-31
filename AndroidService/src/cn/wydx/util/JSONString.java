package cn.wydx.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JSONString {
	public static String getJsonString(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException, IOException {

		// 以下代碼
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("UTF-8");
		// 以json數據完成操作
		response.setContentType("application/json;charset=UTF-8");
		// 接收的数据类型
		String contentType = request.getContentType();
		System.out.println(contentType);// 得到客户端发送过来内容的类型，application/json;charset=UTF-8
		// ip地址
		String remoteAddr = request.getRemoteAddr();
		System.out.println(remoteAddr);// 得到客户端的ip地址，

		if (contentType != null) {

			BufferedReader br = new BufferedReader(new InputStreamReader(// 使用字符流读取客户端发过来的数据
					request.getInputStream()));
			String line = null;
			StringBuffer s = new StringBuffer();// StringBuffer
												// String的區別，如果要對數據作頻繁的修改，則用StringBuffer
			// 以一行的形式读取数据
			while ((line = br.readLine()) != null) {
				s.append(line);
			}
			// 关闭io流
			br.close();

			return s.toString();
		}
		return null;
	}
}
