package cn.wydx.test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.wydx.domain.User;
import cn.wydx.util.JDBCUtil;

public class JdbcTest {
	public static void main(String[] args) {
		String sql = "select * from user;";

		// 操作数据库
		ResultSet rs = JDBCUtil.executeQuery(sql, null);
		
		List<User> list = new ArrayList<>();
		// 遍历rs
		try {
			while (rs.next()) {
				String id = rs.getString("id");
				String username = rs.getString("username");
				String password = rs.getString("password");

				// 将数据封装到对象中
				User user = new User();
				user.setId(id);
				user.setPassword(password);
				user.setUsername(username);
				
				//将对象封装到集合中
				list.add(user);
			}
			//从控制台输出集合
			list.forEach(li->System.out.println(li));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 关闭资源
			JDBCUtil.close(JDBCUtil.getCt(), JDBCUtil.getPs(), rs);
		}

	}

	private static void update() {
		String sql = "update user set password=? where id=?;";
		// 处理占位符
		String info[] = { "aaaaaaa", "30" };
		// 操作数据库
		int i = JDBCUtil.executeUpdate(sql, info);
	}

	// 删
	private static void delete() {
		String sql = "delete from user where id=?;";
		// 处理占位符
		String info[] = { "28" };
		// 操作数据库
		JDBCUtil.executeUpdate(sql, info);
	}

	// 添加数据
	private static void add() {
		// 1.jdbc增
		String sql = "insert into user(username,password)\r\n" + "values(?,?)";
		// 处理占位符
		String info[] = { "乔峰", "admin" };
		// 操作数据库
		JDBCUtil.executeUpdate(sql, info);
	}

}
