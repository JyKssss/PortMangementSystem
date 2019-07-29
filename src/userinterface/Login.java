package userinterface;
/**
 * @author TAN
 */

import javax.swing.*;

import mysql.SQL;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Login extends JFrame implements ActionListener {
	JButton jb1, jb2, jb3 = null;
	JRadioButton jrb1, jrb2 = null;
	JPanel jp1, jp2, jp3, jp4 = null;
	JTextField jtf = null;
	JLabel jlb1, jlb2, jlb3 = null;
	JPasswordField jpf = null;
	ButtonGroup bg = null;
	PortInfo portInfo;
	String look;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Login();
	}
	//登录界面构造函数
	public Login() {
		// 定义登录退出按钮
		jb1 = new JButton("登录");
		jb2 = new JButton("退出");
		jb3 = new JButton("重置");
		// 设置监听
		jb1.addActionListener(this);
		jb2.addActionListener(this);
		jb3.addActionListener(this);

		jp1 = new JPanel();
		jp2 = new JPanel();
		jp3 = new JPanel();
		jp4 = new JPanel();

		jlb1 = new JLabel("账   号: ");
		jlb2 = new JLabel("密   码: ");
		// 登录输入账号密码
		jtf = new JTextField(16);
		jpf = new JPasswordField(16);
		// 各组件加入到panel中
		jp1.add(jlb1);
		jp1.add(jtf);

		jp2.add(jlb2);
		jp2.add(jpf);

		jp4.add(jb1);
		jp4.add(jb3);
		jp4.add(jb2);
		// 加入到Jframe中
		this.add(jp1);
		this.add(jp2);
		this.add(jp4);

		setLayout(new GridLayout(3, 1, 2, 5));
		//设置窗口UI风格
		look =  "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
		try {
			UIManager.setLookAndFeel(look);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setTitle("港口管理系统登录");// 设置窗口名称
		setSize(500, 200);// 设置窗口大小
		setDefaultCloseOperation(EXIT_ON_CLOSE);// 设置关闭窗口后动作退出JVM
		setVisible(true);
		setLocation(200, 300);// 设置窗口位置
	}

	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand() == "退出") {
			System.exit(0);
		} else if (e.getActionCommand() == "重置") {
			this.clear();
		} else if (e.getActionCommand() == "登录") {
			// 输入完整的处理
			if (!jtf.getText().isEmpty() && !jpf.getText().isEmpty()) {
				try {
					// 连接数据库
					SQL.connectSql();
					// 执行管理员账号的查找
					SQL.queryAdmin(jtf.getText(), jpf.getText());
					// 如果账号不匹配则清空输入
					if (SQL.pwd == null) {
						this.clear();
					} else {
						this.adminLogin();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			// 输入不完整的处理
			else if (jtf.getText().isEmpty() || jpf.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "请输入账号和密码", "提示信息", JOptionPane.WARNING_MESSAGE);
				this.clear();
			}
		}
	}

	// 清空输入区
	public void clear() {
		jtf.setText("");
		jpf.setText("");
	}

	public void adminLogin() {
		this.clear();
		dispose();
		portInfo = new PortInfo();
	}
}
