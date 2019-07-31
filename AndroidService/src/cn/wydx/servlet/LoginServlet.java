package cn.wydx.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.wydx.domain.User;
import cn.wydx.util.JDBCUtil;
import cn.wydx.util.JSONString;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String jsonString = JSONString.getJsonString(request, response);

		if (jsonString != null) {

			System.out.println(jsonString);// {"password":"123456","name":"admin"}
			// JSON：这是json解析包，myeclipase是没有，要我们自己导入 ，fastjson,是相对使用比较多的，阿里
			User user = JSON.parseObject(jsonString, User.class);// 是用了反射機制來完成對象的封閉
			// 以utf-8解码操作
			String number = URLDecoder.decode(user.getNumber(), "utf-8");
			System.out.println("学号是：" + number);

			String password = user.getPassword();
			System.out.println("密码是：" + password);
			/**
			 * 将得到的数据保存到数据库中
			 */
			// 1.创建sql语句:查詢的sql語句
			// 在验证用户信息的时，不能直接将用户名和密码去数据查询
			// 将用户名去数据库查询，查询到则说明有这个用户，再将这个用户的密码查出来，与客户端输入的密码进行比较
			String sql = "select * from user where number=?";
			// 2.处理占位符的内容
			String[] info = { number };

			// 3.调用增删改的方法
			ResultSet rs = JDBCUtil.executeQuery(sql, info);
			JSONObject rjson = new JSONObject();

			// 判断用户是与存在
			try {
				if (rs.next()) {// 用rs.next是true说明有这个用，则将这个用户的密码取出来，与客户端输入的密码进行比较
					// 从数据库取出这个用户的密码
					String password_db = rs.getString("password");
					if (password.equals(password_db)) {// 如果两个密码相等说明用户登录成功
						// 将结果返回给客户端 ，將結果構建成json數據返回給客戶端
						rjson.put("json", 1);// 這里是寫死的，后面我們會寫活
						System.out.println("登录成功");
						response.getOutputStream().write(rjson.toString().getBytes("UTF-8"));// 向客户端发送一个带有json对象内容的响应
					} else {
						// 将结果返回给客户端 ，將結果構建成json數據返回給客戶端
						rjson.put("json", 2);// 這里是寫死的，后面我們會寫活
						System.out.println("密码错误");
						response.getOutputStream().write(rjson.toString().getBytes("UTF-8"));// 向客户端发送一个带有json对象内容的响应

					}

				} else {
					// 将结果返回给客户端 ，將結果構建成json數據返回給客戶端
					rjson.put("json", 0);// 這里是寫死的，后面我們會寫活
					System.out.println("登录失败");
					response.getOutputStream().write(rjson.toString().getBytes("UTF-8"));// 向客户端发送一个带有json对象内容的响应
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
