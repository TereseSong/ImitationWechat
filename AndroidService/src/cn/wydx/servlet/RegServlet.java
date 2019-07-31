package cn.wydx.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
 * Servlet implementation class RegServlet
 */
@WebServlet("/RegServlet")
public class RegServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject rjson = new JSONObject();
		String jsonString = JSONString.getJsonString(request, response);

		System.out.println(jsonString);// {"password":"123456","name":"admin"}
		// JSON：这是json解析包，myeclipase是没有，要我们自己导入 ，fastjson,是相对使用比较多的，阿里

		if (jsonString != null) {

			User user = JSON.parseObject(jsonString.toString(), User.class);// 是用了反射機制來完成對象的封閉
			// 以utf-8解码操作
			String username = URLDecoder.decode(user.getUsername(), "utf-8");
			System.out.println("用户名是：" + username);
			String password = user.getPassword();
			System.out.println("密码是：" + password);

			// 得到学号
			String number = user.getNumber();

			// 将学号，到数据库查询
			String sql1 = "select * from user where number=?;";
			String[] info1 = { number };

			// 以前：得到的数据是：true,false,修改过后你们得到的数据是：0(注册失败),1(注册成功),2(重复注册)
			ResultSet rs1 = JDBCUtil.executeQuery(sql1, info1);
			try {
				if (rs1.next()) {
					rjson.put("json", 2);
					System.out.println("重复注册");
					response.getOutputStream().write(rjson.toString().getBytes("UTF-8"));// 向客户端发送一个带有json对象内容的响应
					return;

				}
			} catch (SQLException e) {
				rjson.put("json", 2);
				System.out.println("重复注册");
				e.printStackTrace();
			}
			String classinfo = user.getClassinfo();
			System.out.println("user==" + user);
			/**
			 * 将得到的数据保存到数据库中
			 */
			// 1.创建sql语句:查詢的sql語句
			// 在验证用户信息的时，不能直接将用户名和密码去数据查询
			// 将用户名去数据库查询，查询到则说明有这个用户，再将这个用户的密码查出来，与客户端输入的密码进行比较
			// insert into
			// user(username,password,number,class)values('吴正洪','123','10001','7班');
			String sql = "insert into user(username,password,number,classinfo,createdata)values(?,?,?,?,?);";
			// 2.处理占位符的内容
			String[] info = { username, password, number, classinfo,
					new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) };

			// 3.调用增删改的方法
			int rs = JDBCUtil.executeUpdate(sql, info);

			// 判断用户是与存在
			if (rs != 0) {// 用rs.next是true说明有这个用，则将这个用户的密码取出来，与客户端输入的密码进行比较
				// 将结果返回给客户端 ，將結果構建成json數據返回給客戶端
				rjson.put("json", 1);// 這里是寫死的，后面我們會寫活
				response.getOutputStream().write(rjson.toString().getBytes("UTF-8"));// 向客户端发送一个带有json对象内容的响应

			} else {
				// 将结果返回给客户端 ，將結果構建成json數據返回給客戶端
				rjson.put("json", 0);// 這里是寫死的，后面我們會寫活
				response.getOutputStream().write(rjson.toString().getBytes("UTF-8"));// 向客户端发送一个带有json对象内容的响应
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
