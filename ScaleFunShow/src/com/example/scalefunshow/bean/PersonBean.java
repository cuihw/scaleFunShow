package com.example.scalefunshow.bean;

/**
 * Created by cuihuawei on 3/4/2017.
 *  c_rybh  员工编号
    c_ryxm  员工姓名
    c_zw    职务
    c_bmbm  部门编码
    c_bmmc  部门名称
    c_lxfs  联系方式
    c_yjlsdh    应急联系电话
    c_xb    性别
    c_mz    民族
    n_sg    身高(cm)
    c_zzmm  政治面貌
    c_hyzk  婚姻状况
    c_zgxl  最高学历
    c_zy    专业
    c_sfzh  身份证号
    c_xzz   现住址
    c_qtsm  其它说明
 */

public class PersonBean {
    String c_rybh  ;
    String c_ryxm  ;
    String c_zw    ;
    String c_bmbm  ;
    String c_bmmc  ;
    String c_lxfs  ; 
    String c_yjlsdh;
    String c_xb    ;
    String c_mz    ;
    String n_sg    ;
    String c_zzmm  ;
    String c_hyzk  ;
    String c_zgxl  ;
    String c_zy    ;
    String c_sfzh  ;
    String c_xzz   ;
    String c_qtsm  ;
    public String getC_rybh() {
        return c_rybh;
    }
    public void setC_rybh(String c_rybh) {
        this.c_rybh = c_rybh;
    }
    public String getC_ryxm() {
        return c_ryxm;
    }
    public void setC_ryxm(String c_ryxm) {
        this.c_ryxm = c_ryxm;
    }
    public String getC_zw() {
        return c_zw;
    }
    public void setC_zw(String c_zw) {
        this.c_zw = c_zw;
    }
    public String getC_bmbm() {
        return c_bmbm;
    }
    public void setC_bmbm(String c_bmbm) {
        this.c_bmbm = c_bmbm;
    }
    public String getC_bmmc() {
        return c_bmmc;
    }
    public void setC_bmmc(String c_bmmc) {
        this.c_bmmc = c_bmmc;
    }
    public String getC_lxfs() {
        return c_lxfs;
    }
    public void setC_lxfs(String c_lxfs) {
        this.c_lxfs = c_lxfs;
    }
    public String getC_yjlsdh() {
        return c_yjlsdh;
    }
    public void setC_yjlsdh(String c_yjlsdh) {
        this.c_yjlsdh = c_yjlsdh;
    }
    public String getC_xb() {
        return c_xb;
    }
    public void setC_xb(String c_xb) {
        this.c_xb = c_xb;
    }
    public String getC_mz() {
        return c_mz;
    }
    public void setC_mz(String c_mz) {
        this.c_mz = c_mz;
    }
    public String getN_sg() {
        return n_sg;
    }
    public void setN_sg(String n_sg) {
        this.n_sg = n_sg;
    }
    public String getC_zzmm() {
        return c_zzmm;
    }
    public void setC_zzmm(String c_zzmm) {
        this.c_zzmm = c_zzmm;
    }
    public String getC_hyzk() {
        return c_hyzk;
    }
    public void setC_hyzk(String c_hyzk) {
        this.c_hyzk = c_hyzk;
    }
    public String getC_zgxl() {
        return c_zgxl;
    }
    public void setC_zgxl(String c_zgxl) {
        this.c_zgxl = c_zgxl;
    }
    public String getC_zy() {
        return c_zy;
    }
    public void setC_zy(String c_zy) {
        this.c_zy = c_zy;
    }
    public String getC_sfzh() {
        return c_sfzh;
    }
    public void setC_sfzh(String c_sfzh) {
        this.c_sfzh = c_sfzh;
    }
    public String getC_xzz() {
        return c_xzz;
    }
    public void setC_xzz(String c_xzz) {
        this.c_xzz = c_xzz;
    }
    public String getC_qtsm() {
        return c_qtsm;
    }
    public void setC_qtsm(String c_qtsm) {
        this.c_qtsm = c_qtsm;
    }

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