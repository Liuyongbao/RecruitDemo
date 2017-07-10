package com.example.recruitdemo.UserBean;

/**
 * Created by 张金瑞 on 2017/4/11.
 */
public class Number {
    private int customer_number;
    private int doing_audit_number;
    private int my_audit_number;
    private int total_audit_number;
    private String status_id;

    public String getStatus_id() {
        return status_id;
    }

    public void setStatus_id(String status_id) {
        this.status_id = status_id;
    }

    public int getCustomer_number() {
        return customer_number;
    }

    public void setCustomer_number(int customer_number) {
        this.customer_number = customer_number;
    }

    public int getDoing_audit_number() {
        return doing_audit_number;
    }

    public void setDoing_audit_number(int doing_audit_number) {
        this.doing_audit_number = doing_audit_number;
    }

    public int getMy_audit_number() {
        return my_audit_number;
    }

    public void setMy_audit_number(int my_audit_number) {
        this.my_audit_number = my_audit_number;
    }

    public int getTotal_audit_number() {
        return total_audit_number;
    }

    public void setTotal_audit_number(int total_audit_number) {
        this.total_audit_number = total_audit_number;
    }
}
