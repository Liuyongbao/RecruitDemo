package com.example.recruitdemo.UserBean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/3.
 */
public class ShenPi implements Serializable{

    //订单编号
    private String id;
    private String start_time;
    //发起部门编号  名称
    private String org_id;

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    private String org_name;
    private String org_parentId;
    //发起人编号 名称
    private String employee_id;
    private String employee_name;
    //审批状态编号  名称
    private String status_id;
    private String status_name;
    //订单新状态编号   名称
    private String new_status_id;
    private String new_status_name;
    //创建时间
    private String create_time;


    private String indent_id;
    //订单客户编号  名称
    private String in_customer_id;
    private String in_customer_name;
    //订单负责人编号  名称
    private String in_employee_id;
    private String in_employee_name;
    //订单负责部门编号  名称
    private String in_org_id;
    private String in_org_name;
    private String in_org_parentId;
    //订单状态编号  名称
    private String in_status_id;
    private String in_status_name;

    public String getNew_status_id() {
        return new_status_id;
    }

    public void setNew_status_id(String new_status_id) {
        this.new_status_id = new_status_id;
    }

    public String getNew_status_name() {
        return new_status_name;
    }

    public void setNew_status_name(String new_status_name) {
        this.new_status_name = new_status_name;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrg_id() {
        return org_id;
    }

    public void setOrg_id(String org_id) {
        this.org_id = org_id;
    }

    public String getOrg_name() {
        return org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
    }

    public String getOrg_parentId() {
        return org_parentId;
    }

    public void setOrg_parentId(String org_parentId) {
        this.org_parentId = org_parentId;
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public String getStatus_id() {
        return status_id;
    }

    public void setStatus_id(String status_id) {
        this.status_id = status_id;
    }

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }

    public String getIndent_id() {
        return indent_id;
    }

    public void setIndent_id(String indent_id) {
        this.indent_id = indent_id;
    }

    public String getIn_customer_id() {
        return in_customer_id;
    }

    public void setIn_customer_id(String in_customer_id) {
        this.in_customer_id = in_customer_id;
    }

    public String getIn_customer_name() {
        return in_customer_name;
    }

    public void setIn_customer_name(String in_customer_name) {
        this.in_customer_name = in_customer_name;
    }

    public String getIn_employee_id() {
        return in_employee_id;
    }

    public void setIn_employee_id(String in_employee_id) {
        this.in_employee_id = in_employee_id;
    }

    public String getIn_employee_name() {
        return in_employee_name;
    }

    public void setIn_employee_name(String in_employee_name) {
        this.in_employee_name = in_employee_name;
    }

    public String getIn_org_id() {
        return in_org_id;
    }

    public void setIn_org_id(String in_org_id) {
        this.in_org_id = in_org_id;
    }

    public String getIn_org_name() {
        return in_org_name;
    }

    public void setIn_org_name(String in_org_name) {
        this.in_org_name = in_org_name;
    }

    public String getIn_org_parentId() {
        return in_org_parentId;
    }

    public void setIn_org_parentId(String in_org_parentId) {
        this.in_org_parentId = in_org_parentId;
    }

    public String getIn_status_id() {
        return in_status_id;
    }

    public void setIn_status_id(String in_status_id) {
        this.in_status_id = in_status_id;
    }

    public String getIn_status_name() {
        return in_status_name;
    }

    public void setIn_status_name(String in_status_name) {
        this.in_status_name = in_status_name;
    }
}
