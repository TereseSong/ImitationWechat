package cn.wydx.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.wydx.domain.AddressList;
import cn.wydx.util.JDBCUtil;
import cn.wydx.util.JSONString;

/**管理通讯录查询的
 * 
 */
@WebServlet("/AddressListServlet")
public class AddressListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String s = JSONString.getJsonString(request, response);
		
		if(s!=null) {
			
			System.out.println(s);
			JSONObject jobj = JSON.parseObject(s);// 封装成一个JSON对象
			String number = (String) jobj.get("number");// 获取result对应的value
			System.out.println("学号是：" + number);
			if (!number.equals("")) {
				System.out.println(11111);
				/**
				 * 将得到的数据保存到数据库中
				 */
				// 1.创建sql语句:查詢的sql語句
				// 在验证用户信息的时，不能直接将用户名和密码去数据查询
				// 将用户名去数据库查询，查询到则说明有这个用户，再将这个用户的密码查出来，与客户端输入的密码进行比较
				String sql = "select b.id,a.username,b.ad_name,b.phone,b.email from user a,address_list b  where number=ad_number and b.ad_number=?;";
				// 2.处理占位符的内容
				String[] info = { number };

				// 3.调用增删改的方法
				ResultSet rs = JDBCUtil.executeQuery(sql, info);
				List<AddressList> list = new ArrayList<>();
				// 创建一个list集合
				try {
					while (rs.next()) {
						//查询到的数据封装成AddressList对象
						AddressList ads = new AddressList();
						ads.setId(rs.getString("id"));
						ads.setAdname(rs.getString("ad_name"));
						ads.setPhone(rs.getString("phone"));
						ads.setEmail(rs.getString("email"));
						//将对象添加到集合中
						list.add(ads);
					}
					// 将list集合封装成json数据
					String listjson = JSON.toJSONString(list);

					//将json数据返回给客户端
					response.getOutputStream().write(listjson.getBytes("UTF-8"));// 向客户端发送一个带有json对象内容的响应
					System.out.println(listjson);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
