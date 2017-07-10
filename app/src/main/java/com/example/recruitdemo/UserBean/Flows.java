package com.example.recruitdemo.UserBean;

/**
 * Created by Administrator on 2017/4/5.
 */
public class Flows {

    //审批流程
    private String flows_id;
    private String flows_start_time;
    private String flows_end_time;
    private String flows_notes;

    public String getFlows_notes() {
        return flows_notes;
    }

    public void setFlows_notes(String flows_notes) {
        this.flows_notes = flows_notes;
    }

    public String getFlows_id() {
        return flows_id;
    }

    public void setFlows_id(String flows_id) {
        this.flows_id = flows_id;
    }

    public String getFlows_start_time() {
        return flows_start_time;
    }

    public void setFlows_start_time(String flows_start_time) {
        this.flows_start_time = flows_start_time;
    }

    public String getFl_employee_id() {
        return fl_employee_id;
    }

    public void setFl_employee_id(String fl_employee_id) {
        this.fl_employee_id = fl_employee_id;
    }

    public String getFlows_end_time() {
        return flows_end_time;
    }

    public void setFlows_end_time(String flows_end_time) {
        this.flows_end_time = flows_end_time;
    }

    public String getFl_employee_name() {
        return fl_employee_name;
    }

    public void setFl_employee_name(String fl_employee_name) {
        this.fl_employee_name = fl_employee_name;
    }

    public String getFl_status_id() {
        return fl_status_id;
    }

    public void setFl_status_id(String fl_status_id) {
        this.fl_status_id = fl_status_id;
    }

    public String getFl_status_name() {
        return fl_status_name;
    }

    public void setFl_status_name(String fl_status_name) {
        this.fl_status_name = fl_status_name;
    }

    public String getFl_size() {
        return fl_size;
    }

    public void setFl_size(String fl_size) {
        this.fl_size = fl_size;
    }

    private String fl_employee_id;
    private String fl_employee_name;
    private String fl_status_id;
    private String fl_status_name;
    private String fl_size;
}
