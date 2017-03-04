package com.example.scalefunshow.bean;

/**
 * Created by cuihuawei on 3/4/2017.
 *
 */

public class PersonBean {


}

/*输入参数
url	/login
	json

type	post
data	userid	工号
	password	密码	MD5
	deviceid	设备编号
返回：
status		0 成功，1 失败
message		"如果status==0,成功；反之为原因，中文描述。"
token		"如果status==0,token有值，否则为空 。"
data
	c_rybh	员工编号
	c_ryxm	员工姓名
	c_zw	职务
	c_bmbm	部门编码
	c_bmmc	部门名称
	c_lxfs	联系方式
	c_yjlsdh	应急联系电话
	c_xb	性别
	c_mz	民族
	n_sg	身高(cm)
	c_zzmm	政治面貌
	c_hyzk	婚姻状况
	c_zgxl	最高学历
	c_zy	专业
	c_sfzh	身份证号
	c_xzz	现住址
	c_qtsm	其它说明
*/