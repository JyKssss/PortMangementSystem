package mysql;

import java.nio.charset.UnmappableCharacterException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PseudoColumnUsage;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.text.html.HTMLDocument.HTMLReader.SpecialAction;

import com.mysql.jdbc.PreparedStatement;

import algorithm.DateCalculate;
import userinterface.StringToDatetime;

public class SQL {
	static Connection con = null;
	// 驱动程序名
	static final String driver = "com.mysql.jdbc.Driver";
	// 要访问的数据库地址
	static final String url = "jdbc:mysql://cdb-lgvkfdom.bj.tencentcdb.com:10094/portManagement?useSSL=false&useOldAliasMetadataBehavior=true";
	// 数据库用户名
	static final String username = "root";
	// 数据库密码
	static final String password = "tjy1998217";
	// sql语句
	static java.sql.PreparedStatement ps = null;
	// 数据库返回结果
	public static ResultSet rs, rs1, rs2, rs3, rs4,rs5, rs6,rs7= null;
	// 管理员账号密码
	public static String pwd = null;
	static String unm = null;

	// 数据库连接方法
	public static void connectSql() throws SQLException {
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, username, password);
			// System.out.println(con.isClosed());
			if (!con.isClosed()) {
				System.out.println("connection success");
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 管理员账号登录
	public static void queryAdmin(String user, String pass) {
		try {
			ps = con.prepareStatement("select * from adminInfo where 管理员账号 =? and 密码 =?");
			ps.setString(1, user);
			ps.setString(2, pass);
			// 查询结果集
			rs = ps.executeQuery();

			// 如果查询有相关记录则密码正确
			if (rs.next()) {
				// 获得管理员账号和密码
				unm = rs.getString(1);
				pwd = rs.getString(2);
				System.out.println("登陆成功");
				// System.out.println(unm+pwd);
			} else {
				JOptionPane.showMessageDialog(null, "密码不正确或无此用户", "提示信息", JOptionPane.WARNING_MESSAGE);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 泊位信息查询
	public static void queryPort() {
		try {
			ps = con.prepareStatement("select * from portInfo");
			rs1 = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	//根据泊位号查询泊位信息
	public static void queryPort(String portNumber) {
		try {
			ps = con.prepareStatement("select * from portInfo where 泊位号 =?");
			ps.setString(1, portNumber);
			rs5 = ps.executeQuery();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// 船只信息查询
	public static void queryFreight(String freightcode) {
		try {
			ps = con.prepareStatement("select * from freightInfo where 船只编码 =? ");
			ps.setString(1, freightcode);
			rs2 = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//查询所有船只信息
	public static void queryFreight() {
		// TODO Auto-generated method stub
		try {
			ps = con.prepareStatement("select * from freightInfo");
			rs3 = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 插入新船只
	public static void insertFreight(String[] insertinfo) {
		try {
			ps = con.prepareStatement(
					"INSERT INTO `freightInfo`(`船只编码`, `船名`, `抵达港口时间`, `靠泊时长`, `偏好泊位`) VALUES (?,?,?,?,?)");
			java.sql.Date[] date = new java.sql.Date[2];
			date[0] = StringToDatetime.transStringToDatetime(insertinfo[2]);
			for (int i = 0; i < insertinfo.length; i++) {
				if (i == 2 ) {
					ps.setDate(i+1,  (java.sql.Date) date[0]);
					System.out.println(date[0]);
					continue;
				}				
				ps.setString(i + 1, insertinfo[i]);
			}
			System.out.println(ps);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "船只编号发生冲突或数据非法", "提示信息", JOptionPane.WARNING_MESSAGE);

		}
	}

	// 修改船只信息
	public static void updateFreight(String val, String sid, String col) {
		// System.out.println(val+sid+col);
		java.sql.Date val1;
		int i = 0;

		try {
			// 如果修改的是时间 对string进行转化
			if (col == "抵达港口时间") {
				val1 = StringToDatetime.transStringToDatetime(val);
				ps = con.prepareStatement("UPDATE freightInfo SET " + col + "=?  WHERE 船只编码=" + sid);
				ps.setDate(1, val1);
				System.out.println(ps);
				i = 1;
			} else if (i == 0) {
				ps = con.prepareStatement("UPDATE freightInfo SET " + col + "=" + val + " WHERE 船只编码=" + sid);
			}
			// System.out.println(ps);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 删除船只信息
	public static void deleteFreight(String sid) {
		try {
			ps = con.prepareStatement("DELETE FROM freightInfo WHERE 船只编码=? ");
			ps.setString(1, sid);
			// System.out.println(ps);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 查找泊位表中的记录数（多少个泊位）
	public static void queryNumberofPort() {
		try {
			ps = con.prepareStatement("SELECT COUNT(*) AS NUM FROM portInfo");
			rs4 = ps.executeQuery();
			// System.out.println(rs4.getString("NUM") + "123");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// 向泊位表中插入记录（自动增加泊位数）
	public static void increasPortNumber(int i) {
		try {
			ps = con.prepareStatement(
					"INSERT INTO `portInfo`(`泊位号`, `是否有空`, `船只编码`, `入泊位时间`, `离港时间`) VALUES (?,?,?,?,?) ");
			ps.setInt(1, i);
			ps.setString(2, "是");
			ps.setDate(3, null);
			ps.setDate(4, null);
			ps.setString(5, null);
			// System.out.println(ps);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 减少泊位数
	public static void decreasePortNumber(int i) {
		try {
			ps = con.prepareStatement("DELETE FROM portInfo WHERE 泊位号=?");
			ps.setInt(1, i);
			// System.out.println(ps);
			ps.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	//手动分配泊位
	public static void updatePort(String portNumber, String freightNumber, String arriveTime ,String timeLong) {
		try {
			ps = con.prepareStatement("UPDATE `portInfo` SET `是否有空`='否',`船只编码`=?,`入泊位时间`=?,`离港时间`=? WHERE `泊位号`=? ");
			ps.setString(1, freightNumber);
			ps.setString(2, arriveTime);
			ps.setString(3, DateCalculate.dateAdd(arriveTime, Integer.valueOf(timeLong)));
			ps.setString(4, portNumber);
			System.out.println(ps);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO: handle exception
		}
	}
	//自动分配码头
	public static void autoDistribute() {
		try {
			//分别查询空泊位和未分配的船只
			
			ps = con.prepareStatement("select * from freightInfo left join portInfo on freightInfo.船只编码= portInfo.船只编码 where portInfo.船只编码 is null  ");
			rs6= ps.executeQuery();
			ps = con.prepareStatement("select 泊位号 from portInfo where 船只编码 is null  ");
			rs7 = ps.executeQuery();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
