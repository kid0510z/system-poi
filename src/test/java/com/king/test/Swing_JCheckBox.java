package com.king.test;

import javax.swing.*;
import java.awt.*;
public class Swing_JCheckBox extends JFrame {	//继承JFrame顶级容器类
	
	//定义组件
	JPanel jp1,jp2,jp3;			//定义面板组件
	JLabel jlb1,jlb2;			//定义标签组件
	JButton jb1,jb2;			//定义按钮组件
	JCheckBox jcb1,jcb2,jcb3;	//定义多选框组件
	JRadioButton jrb1,jrb2;		//定义单选框组件
	ButtonGroup bg;				//定义按钮组（注意这个不是组件，它是个作用域，我只是把它定义在这里而已）
	public static void main(String[] args) {
		Swing_JCheckBox a=new Swing_JCheckBox();	//显示界面
 
	}
	public Swing_JCheckBox()
	{
		//创建组件
		jp1=new JPanel();	//创建面板
		jp2=new JPanel();
		jp3=new JPanel();
		jb1=new JButton("注册用户");		//创建按钮
		jb2=new JButton("取消注册");
		jlb1=new JLabel("你最喜欢的运动");	//创建标签
		jlb2=new JLabel("你的性别");
		jcb1=new JCheckBox("足球");			//创建复选框
		jcb2=new JCheckBox("蓝球");
		jcb3=new JCheckBox("网球");
		jrb1=new JRadioButton("男");			//创建单选框
		jrb2=new JRadioButton("女");
		bg=new ButtonGroup();				//创建按钮组
		
		//设置布局管理器
		this.setLayout(new GridLayout(3,1));	//三行一列网格布局
		
		//添加组件
		this.add(jp1);			//添加三个面板
		this.add(jp2);
		this.add(jp3);
		
		jp1.add(jlb1);			//添加面板1的组件
		jp1.add(jcb1);
		jp1.add(jcb2);
		jp1.add(jcb3);
		
		jp2.add(jlb2);			//添加面板2的组件	
		bg.add(jrb1);			//必须要把单选框放入按钮组作用域中才能实现单选！！！！
		bg.add(jrb2);
		jp2.add(jrb1);
		jp2.add(jrb2);
		
		jp3.add(jb1);			//添加面板3的组件
		jp3.add(jb2);
		
		//设置窗体属性
		this.setTitle("用户注册界面");		//设置界面标题
		this.setSize(350, 150);				//设置界面像素
		this.setLocation(200, 200);			//设置界面初始位置
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//设置虚拟机和界面一同关闭
		this.setVisible(true);				//设置界面可视化
	}
}