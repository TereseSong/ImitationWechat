package cn.wydx.servlet;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.wydx.domain.Address;
import cn.wydx.util.JDBCUtil;
import cn.wydx.util.JSONString;

/**
 * 添加通讯录的Servlet
 */
@WebServlet("/InsertAddresListServlet")
public class InsertAddresListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 定义一个json对象
		JSONObject rjson = new JSONObject();
		String s = JSONString.getJsonString(request, response);
		if (s != null) {

			System.out.println(s);// {"password":"123456","name":"admin"}
			// JSON：这是json解析包，myeclipase是没有，要我们自己导入 ，fastjson,是相对使用比较多的，阿里
			Address ads = JSON.parseObject(s, Address.class);// 是用了反射機制來完成對象的封閉
			// 以utf-8解码操作
			String adname = URLDecoder.decode(ads.getAdname(), "utf-8");
			String phone = ads.getPhone();
			String email = URLDecoder.decode(ads.getEmail(), "utf-8");
			String adnumber = ads.getAdnumber();
			System.out.println(adname);
			System.out.println(phone);
			System.out.println(email);
			System.out.println(adnumber);

			/**
			 * 将得到的数据保存到数据库中
			 */
			String sql = "insert into address_list(ad_name,phone,email,ad_number)values(?,?,?,?);";
			// 2.处理占位符的内容
			String[] info = { adname, phone, email, adnumber};

			// 3.调用增删改的方法
			int rs = JDBCUtil.executeUpdate(sql, info);
			int rsjson = 0;

			// 判断用户是与存在
			if (rs != 0) {// 用rs.next是true说明有这个用，则将这个用户的密码取出来，与客户端输入的密码进行比较
				// 将结果返回给客户端 ，將結果構建成json數據返回給客戶端
				rsjson = 1;
			}
			// 将结果返回给客户端 ，將結果構建成json數據返回給客戶端
			rjson.put("json", rsjson);// 這里是寫死的，后面我們會寫活
			response.getOutputStream().write(rjson.toString().getBytes("UTF-8"));// 向客户端发送一个带有json对象内容的响应

		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
