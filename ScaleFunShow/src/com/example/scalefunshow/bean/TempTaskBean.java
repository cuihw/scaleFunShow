package com.example.scalefunshow.bean;

/**
 * Created by cuihuawei on 3/4/2017.
 */

/*工号
设备编号
产品编号
产品名称
生产数量（箱）
锅的类型型号，80KG的锅表示配方中主料的值。必须填写
出成值，每一锅料能产出多少正品。
标准锅数，=生产数量/出成值
实际锅数，因为界面可以修改标准值。
生产线
开始时间
结束时间
*/

public class TempTaskBean {
    String token;
    String userid; //工号
    String deviceid; //
    String c_productId; //产品编号
    String c_productName; //产品名称
    String n_num; //生产数量（箱）
    String c_glx;  //锅的类型型号，80KG的锅表示配方中主料的值。必须填写
    String n_chuchengzhi; //出成值，每一锅料能产出多少正品。
    String n_bzgs; //标准锅数，=生产数量/出成值
    String n_sjgs; //实际锅数，因为界面可以修改标准值.
    String c_scx; //生产线
    String d_kssj; //开始时间
    String d_jssj; //结束时间

    public String getC_glx() {
        return c_glx;
    }

    public void setC_glx(String c_glx) {
        this.c_glx = c_glx;
    }

    public String getC_productId() {
        return c_productId;
    }

    public void setC_productId(String c_productId) {
        this.c_productId = c_productId;
    }

    public String getC_productName() {
        return c_productName;
    }

    public void setC_productName(String c_productName) {
        this.c_productName = c_productName;
    }

    public String getC_scx() {
        return c_scx;
    }

    public void setC_scx(String c_scx) {
        this.c_scx = c_scx;
    }

    public String getD_jssj() {
        return d_jssj;
    }

    public void setD_jssj(String d_jssj) {
        this.d_jssj = d_jssj;
    }

    public String getD_kssj() {
        return d_kssj;
    }

    public void setD_kssj(String d_kssj) {
        this.d_kssj = d_kssj;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getN_bzgs() {
        return n_bzgs;
    }

    public void setN_bzgs(String n_bzgs) {
        this.n_bzgs = n_bzgs;
    }

    public String getN_chuchengzhi() {
        return n_chuchengzhi;
    }

    public void setN_chuchengzhi(String n_chuchengzhi) {
        this.n_chuchengzhi = n_chuchengzhi;
    }

    public String getN_num() {
        return n_num;
    }

    public void setN_num(String n_num) {
        this.n_num = n_num;
    }

    public String getN_sjgs() {
        return n_sjgs;
    }

    public void setN_sjgs(String n_sjgs) {
        this.n_sjgs = n_sjgs;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
