package com.example.recruitdemo.Utils;

import android.util.Log;

import com.example.recruitdemo.UserBean.Area;
import com.example.recruitdemo.UserBean.Branch;
import com.example.recruitdemo.UserBean.City;
import com.example.recruitdemo.UserBean.College;
import com.example.recruitdemo.UserBean.Customer;
import com.example.recruitdemo.UserBean.Customer1;
import com.example.recruitdemo.UserBean.Department;
import com.example.recruitdemo.UserBean.Division;
import com.example.recruitdemo.UserBean.Edition;
import com.example.recruitdemo.UserBean.Employee;
import com.example.recruitdemo.UserBean.Flows;
import com.example.recruitdemo.UserBean.Leadership;
import com.example.recruitdemo.UserBean.Login;
import com.example.recruitdemo.UserBean.Number;
import com.example.recruitdemo.UserBean.Province;
import com.example.recruitdemo.UserBean.ShenPi;
import com.example.recruitdemo.UserBean.ShenPi1;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张金瑞 on 2017/3/22.
 */
public class JsonUtils {

    /**
     * 肖辉   登录验证解析
     *
     * @param jsonStr
     * @return
     */
    public static Login JsonLogin(String jsonStr) {
        Login list = new Login();
        JSONObject jo;
        try {
            jo = new JSONObject(jsonStr);
            String status = jo.optString("status");
            if (status.equals("200")) {
                JSONObject data = jo.optJSONObject("data");
                String data_id = data.optString("id");
                String name = data.optString("name");
                String mobile = data.optString("mobile");

                String token = data.optString("token");

                JSONObject org = data.optJSONObject("org");
                String org_id = org.optString("id");
                String org_name = org.optString("name");
                JSONObject statu = data.optJSONObject("status");
                int id = statu.optInt("id");
                if (data.has("leader")) {
                    JSONObject leader = data.optJSONObject("leader");
                    String leader_id = leader.optString("id");
                    String leader_name = leader.optString("name");
                    list.setLeader_id(leader_id);
                    list.setLeader_Name(leader_name);
                }
                list.setName(name);
                list.setDepartment_id(org_id);
                list.setDepartment_name(org_name);
                list.setMobile(mobile);
                list.setToken(token);
                list.setStatus_id(id);

                list.setId(data_id);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }


    /**
     * 思浓  解析部门
     *
     * @param path
     * @return
     */
    public static List<College> JsonOrg(String path) {
        JSONObject jo;
        try {
            jo = new JSONObject(path);
            String status = jo.optString("status");
            if (status.equals("200")) {
                JSONArray data = jo.optJSONArray("data");
                List<College> list = new ArrayList<>();
                for (int i = 0; i < data.length(); i++) {
                    JSONObject Jo = data.optJSONObject(i);
                    String id = Jo.optString("id");
                    String name = Jo.optString("name");
                    JSONArray subs = Jo.optJSONArray("subs");

                    List<Department> list2 = new ArrayList<>();
                    for (int j = 0; j < subs.length(); j++) {
                        JSONObject Jo2 = subs.optJSONObject(j);
                        String name2 = Jo2.optString("name");
                        String id2 = Jo2.optString("id");
                        String parent_id = Jo.optString("parentId");

                        List<Division> list3 = new ArrayList<>();
                        if (Jo2.has("subs")) {
                            JSONArray subs2 = Jo2.optJSONArray("subs");
                            for (int k = 0; k < subs2.length(); k++) {
                                JSONObject Jo3 = subs2.optJSONObject(k);
                                String name3 = Jo3.optString("name");
                                String id3 = Jo3.optString("id");
                                String parentId = Jo3.optString("parentId");

                                List<Branch> list4 = new ArrayList<>();
                                if (Jo3.has("subs")) {
                                    JSONArray subs3 = Jo3.optJSONArray("subs");
                                    for (int p = 0; p < subs3.length(); p++) {
                                        JSONObject Jo4 = subs3.optJSONObject(p);
                                        String name4 = Jo4.optString("name");
                                        String id4 = Jo4.optString("id");
                                        String parentId2 = Jo4.optString("parentId");

                                        Branch dep4 = new Branch();
                                        dep4.setId(id4);
                                        dep4.setName(name4);
                                        dep4.setParentId(parentId2);
                                        list4.add(dep4);
                                    }
                                }

                                Division dep3 = new Division();
                                dep3.setId(id3);
                                dep3.setName(name3);
                                dep3.setParentId(parentId);
                                dep3.setDivisionList(list4);
                                list3.add(dep3);
                            }
                        }
                        Department dep2 = new Department();
                        dep2.setId(id2);
                        dep2.setName(name2);
                        dep2.setParentId(parent_id);
                        dep2.setDepartmentList(list3);
                        list2.add(dep2);

                    }
                    College dep = new College();
                    dep.setId(id);
                    dep.setName(name);
                    dep.setCollegeList(list2);
                    list.add(dep);
                }
                return list;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    //解析地区  省市区三级联动
    public static List<Province> JsonRegion(String s) {

        JSONObject jo;
        try {
            jo = new JSONObject(s);
            JSONArray province = jo.optJSONArray("province");
            List<Province> provinceList = new ArrayList<Province>();
            for (int i = 0; i < province.length(); i++) {
                JSONObject jo1 = province.optJSONObject(i);
                String pName = jo1.optString("name");
                JSONArray city = jo1.optJSONArray("children");
                List<City> cityList = new ArrayList<City>();
                if (city != null) {
                    for (int j = 0; j < city.length(); j++) {
                        JSONObject jo2 = city.optJSONObject(j);
                        String cName = jo2.optString("name");

                        JSONArray area = jo2.optJSONArray("children");
                        List<Area> areaList = new ArrayList<Area>();
                        if (area != null) {
                            for (int k = 0; k < area.length(); k++) {
                                JSONObject jo3 = area.optJSONObject(k);
                                String aNmae = jo3.optString("name");
                                int id = jo3.optInt("id");

                                Area a = new Area();
                                a.setaName(aNmae);
                                a.setId(id);
                                areaList.add(a);
                            }
                        }

                        City c = new City();
                        c.setAreaList(areaList);
                        c.setcName(cName);
                        cityList.add(c);
                    }

                }
                Province p = new Province();
                p.setCityList(cityList);
                p.setpName(pName);
                provinceList.add(p);
            }

            return provinceList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 客户订单
     */
    public static List<Customer> jsonKe(String path) {

        try {
            JSONObject jo = new JSONObject(path);
            String status = jo.getString("status");
            if (status.equals("200")) {
                JSONObject data = jo.getJSONObject("data");

                List<Customer> cusList = new ArrayList<>();
                JSONArray list = data.getJSONArray("list");
                for (int i = 0; i < list.length(); i++) {
                    JSONObject j = list.getJSONObject(i);
                    Customer customer = new Customer();

                    if (j.has("id")){
                        String idd = j.getString("id");//订单编号
                        customer.setIdd(idd);
                    }
                    if (data.has("create_time")){
                        String create_time = j.getString("create_time");//创建时间
                        customer.setCreate_time(create_time);
                    }

                    //customer   身份证号   姓名
                    JSONObject j_customer = j.getJSONObject("customer");
                    if (j_customer.has("id")){
                        String customer_id = j_customer.getString("id");
                        customer.setId(customer_id);
                    }
                    if (j_customer.has("name")){
                        String customer_name = j_customer.getString("name");
                        customer.setName(customer_name);
                        customer.setAvatar(customer_name);
                    }

                    //employee 责任人编号  姓名
                    JSONObject j_employee = j.getJSONObject("employee");
                    if (j_employee.has("id")){
                        String employee_id = j_employee.getString("id");
                        //employee_id 负责人电话
                        customer.setEmployee_id(employee_id);
                    }
                    if (j_employee.has("name")){
                        String employee_name = j_employee.getString("name");
                        customer.setEmployee_name(employee_name);
                    }

                    //org   责任部门编号   名称
                    JSONObject j_org = j.getJSONObject("org");
                    if (j_org.has("id")){
                        String org_id = j_org.getString("id");
                        customer.setOrg_id(org_id);
                    }
                    if (j_org.has("name")){
                        String org_name = j_org.getString("name");
                        //org   责任部门编号   名称
                        customer.setOrg_name(org_name);
                    }

                    //status  状态 编号 名称
                    JSONObject j_status = j.getJSONObject("status");
                    if (j_status.has("id")){
                        String status_id = j_status.getString("id");
                        customer.setStatus_id(status_id);
                    }
                    if (j_status.has("name")){
                        String status_name = j_status.getString("name");
                        customer.setStatus_name(status_name);
                    }


                    //customer   身份证号   姓名


                    //employee 责任人编号  姓名




//                    customer.setOrg_parentId(org_parentId);
                    //status  状态 编号 名称



                    //创建时间


//                    customer.setZone_id(zone_id);
//                    customer.setZone_name(zone_name);
                    cusList.add(customer);
                }
                return cusList;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

    }


    /**
     * 学生详情解析
     *
     * @param stu
     * @return
     */
    public static Customer1 JsonStudent(String stu) {
        Customer1 customer1 = new Customer1();
        try {
            JSONObject jo = new JSONObject(stu);
            JSONObject joData = jo.optJSONObject("data");
            String id = joData.optString("id");
            String create_time = joData.optString("create_time");
            Log.e("create-time--------", create_time);
            JSONObject joCustomer = joData.optJSONObject("customer");
            Log.e("joCustomer-------------", joCustomer + "");
            String idd = joCustomer.optString("id");
            String name = joCustomer.optString("name");
            String sex = joCustomer.optString("sex");
            String address = joCustomer.optString("address");
            String contact = joCustomer.optString("contact");
            String relative = joCustomer.optString("relative");
            String relation = joCustomer.optString("relation");
            String relative_contact = joCustomer.optString("relative_contact");
            String notes = joCustomer.optString("notes");
            JSONObject joEmployee = joData.optJSONObject("employee");
            String employeeId = joEmployee.optString("id");
            String employeeName = joEmployee.optString("name");
            JSONObject joOrg = joData.optJSONObject("org");
            String orgName = joOrg.optString("name");
            String orgId = joOrg.optString("id");
            String orgParentId = joOrg.optString("parentId");
            JSONObject joStatus = joData.optJSONObject("status");
            String statusId = joStatus.optString("id");
            String statusName = joStatus.optString("name");
            if (joCustomer.has("zone")){
                JSONObject joZone = joCustomer.optJSONObject("zone");
                String zoneId = joZone.optString("id");
                customer1.setZone_id(zoneId);
                String zoneName = joZone.optString("name");
                customer1.setZone(zoneName);
            }
            customer1.setName(name);
            customer1.setSex(sex);
            customer1.setId(idd);
            customer1.setPhone(contact);
            customer1.setAddress(address);
            customer1.setTime(create_time);
            customer1.setStatus(statusName);
            customer1.setParents(relative);
            customer1.setParents_phone(relative_contact);
            customer1.setGuanxi(relation);
            customer1.setBumen(orgName);
            customer1.setFuzeren(employeeName);
            customer1.setF_phone(employeeId);

            customer1.setNotes(notes);
            customer1.setIdd(id);

            return customer1;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 学生领导查询
     * @param employee
     * @return
     */
    public static List<Employee> JsonEmp(String employee) {

        try {
            JSONObject jo = new JSONObject(employee);
            String status = jo.optString("status");
            List<Employee> employeeList = new ArrayList<>();
            if (status.equals("200")) {
                JSONObject data = jo.optJSONObject("data");
                int size=data.getInt("size");
                if (data.has("list")) {
                    JSONArray list = data.optJSONArray("list");
                    for (int i = 0; i < list.length(); i++) {
                        Employee employee1 = new Employee();
                        JSONObject Jo = list.optJSONObject(i);
                        String name = Jo.optString("name");
                        String id = Jo.optString("id");
                        employee1.setId(id);
                        employee1.setName(name);
                        employee1.setSize(size);
                        employeeList.add(employee1);
                    }
                }else{
                    Employee employee1 = new Employee();
                    String id = data.optString("id");
                    String name = data.optString("name");
                    employee1.setId(id);
                    employee1.setName(name);
                    employee1.setSize(size);
                    employeeList.add(employee1);
                }

            }
            return employeeList;


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Leadership> Jsonemp(String employee) {
        List<Leadership> empList = new ArrayList<>();
        try {
            JSONObject jo = new JSONObject(employee);
            String status = jo.optString("status");
            if (status.equals("200")) {
                JSONObject data = jo.optJSONObject("data");
                JSONArray list = data.optJSONArray("list");
                for (int i = 0; i < list.length(); i++) {
                    JSONObject Jo = list.optJSONObject(i);
                    String name = Jo.optString("name");
                    String id = Jo.optString("id");

                    Leadership leadership = new Leadership();
                    leadership.setId(id);
                    leadership.setName(name);
                    empList.add(leadership);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return empList;
    }

    /**
     * 审批列表
     */
    public static List<ShenPi> jsonShenPi(String s) {

        try {
            JSONObject jo = new JSONObject(s);
            String status1 = jo.getString("status");
            if (status1.equals("200")) {
                JSONObject data = jo.getJSONObject("data");
                String size = data.getString("size");

                List<ShenPi> list = new ArrayList<>();

                if (data.has("list")) {
                    JSONArray ja = data.getJSONArray("list");
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject j = ja.getJSONObject(i);
                        String id = j.getString("id");
                        String start_time = j.optString("start_time");

                        JSONObject org = j.getJSONObject("org");
                        String org_id = org.getString("id");
                        String org_name = org.getString("name");
                        String org_parentId = org.getString("parentId");

                        JSONObject employee = j.getJSONObject("employee");
                        String employee_id = employee.getString("id");
                        String employee_name = employee.getString("name");

                        JSONObject status = j.getJSONObject("status");
                        String status_id = status.getString("id");
                        String status_name = status.getString("name");

                        JSONObject new_status = j.getJSONObject("new_status");
                        String new_status_id = new_status.getString("id");
                        String new_status_name = new_status.getString("name");


                        JSONObject indent = j.getJSONObject("indent");
                        String indent_id = indent.getString("id");
                        String indent_create_time = indent.getString("create_time");

                        JSONObject in_customer = indent.getJSONObject("customer");
                        String in_customer_id = in_customer.getString("id");
                        String in_customer_name = in_customer.getString("name");

                        JSONObject in_employee = indent.getJSONObject("employee");
                        String in_employee_id = in_employee.getString("id");
                        String in_employee_name = in_employee.getString("name");

                        JSONObject in_org = indent.getJSONObject("org");
                        String in_org_id = in_org.getString("id");
                        String in_org_name = in_org.getString("name");
                        String in_org_parentId = in_org.getString("parentId");

                        JSONObject in_status = indent.getJSONObject("status");
                        String in_status_id = in_status.getString("id");
                        String in_status_name = in_status.getString("name");

                        ShenPi shenpi = new ShenPi();
                        shenpi.setId(id);
                        shenpi.setOrg_id(org_id);
                        shenpi.setOrg_name(org_name);
                        shenpi.setOrg_parentId(org_parentId);
                        shenpi.setEmployee_id(employee_id);
                        shenpi.setEmployee_name(employee_name);
                        shenpi.setStatus_id(status_id);
                        shenpi.setStatus_name(status_name);
                        shenpi.setNew_status_id(new_status_id);
                        shenpi.setNew_status_name(new_status_name);
                        shenpi.setCreate_time(indent_create_time);
                        shenpi.setIndent_id(indent_id);
                        shenpi.setIn_customer_id(in_customer_id);
                        shenpi.setIn_customer_name(in_customer_name);
                        shenpi.setIn_employee_id(in_employee_id);
                        shenpi.setIn_employee_name(in_employee_name);
                        shenpi.setIn_org_id(in_org_id);
                        shenpi.setIn_org_name(in_org_name);
                        shenpi.setIn_org_parentId(in_org_parentId);
                        shenpi.setIn_status_id(in_status_id);
                        shenpi.setIn_status_name(in_status_name);
                        shenpi.setStart_time(start_time);
                        list.add(shenpi);

                    }
                    return list;
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 审批详细列表
     */
    public static ShenPi1 jsonShenPi1(String s) {

        try {
            JSONObject jo = new JSONObject(s);
            String status1 = jo.getString("status");
            if (status1.equals("200")) {
                ShenPi1 shenPi1 = new ShenPi1();

                JSONObject data = jo.getJSONObject("data");
                String id = data.getString("id");

                JSONObject org = data.getJSONObject("org");
                String org_id = org.getString("id");
                String org_name = org.getString("name");
//                String org_parentId = org.getString("parentId");

                JSONObject employee = data.getJSONObject("employee");
                String employee_id = employee.getString("id");
                String employee_name = employee.getString("name");

                JSONObject status = data.getJSONObject("status");
                String status_id = status.getString("id");
                String status_name = status.getString("name");

                JSONObject new_status = data.getJSONObject("new_status");
                String new_status_id = new_status.getString("id");
                String new_status_name = new_status.getString("name");

                List<Flows> flowsList = new ArrayList<>();
                if (data.has("flows")) {
                    JSONArray flows = data.getJSONArray("flows");
                    for (int i = 0; i < flows.length(); i++) {
                        Flows fl = new Flows();
                        JSONObject j = flows.getJSONObject(i);
                        String flows_id = j.getString("id");

                        if(j.has("start_time")){
                            String fl_start_time = j.getString("start_time");
                            fl.setFlows_start_time(fl_start_time);
                        }

                        if (j.has("end_time")){
                            String fl_end_time = j.getString("end_time");
                            fl.setFlows_end_time(fl_end_time);
                        }

                        if (j.has("notes")){
                            String notes=j.optString("notes");
                            if(notes!=null){
                                fl.setFlows_notes(notes);
                            }
                        }

                        JSONObject fl_employee = j.getJSONObject("employee");
                        String fl_employee_id = fl_employee.getString("id");
                        String fl_employee_name = fl_employee.getString("name");

                        JSONObject fl_status = j.getJSONObject("status");
                        String fl_status_id = fl_status.getString("id");
                        String fl_status_name = fl_status.getString("name");


                        fl.setFl_size(flows.length() + "");
                        fl.setFlows_id(flows_id);
                        fl.setFl_employee_id(fl_employee_id);
                        fl.setFl_employee_name(fl_employee_name);
                        fl.setFl_status_id(fl_status_id);
                        fl.setFl_status_name(fl_status_name);


                        flowsList.add(fl);
                    }
                    shenPi1.setFlowsList(flowsList);
                }


                JSONObject indent = data.getJSONObject("indent");
                String indent_id = indent.getString("id");
                String indent_create_time = indent.getString("create_time");

                JSONObject in_customer = indent.getJSONObject("customer");
                String in_customer_id = in_customer.getString("id");
                String in_customer_name = in_customer.getString("name");

                JSONObject in_employee = indent.getJSONObject("employee");
                String in_employee_id = in_employee.getString("id");
                String in_employee_name = in_employee.getString("name");

                JSONObject in_org = indent.getJSONObject("org");
                String in_org_id = in_org.getString("id");
                String in_org_name = in_org.getString("name");
//                String in_org_parentId = in_org.getString("parentId");

                JSONObject in_status = indent.getJSONObject("status");
                String in_status_id = in_status.getString("id");
                String in_status_name = in_status.getString("name");

                shenPi1.setId(id);
                shenPi1.setOrg_id(org_id);
                shenPi1.setOrg_name(org_name);
//                shenPi1.setOrg_parentId(org_parentId);
                shenPi1.setEmployee_id(employee_id);
                shenPi1.setEmployee_name(employee_name);
                shenPi1.setStatus_id(status_id);
                shenPi1.setStatus_name(status_name);
                shenPi1.setNew_status_id(new_status_id);
                shenPi1.setNew_status_name(new_status_name);
                shenPi1.setIndent_id(indent_id);
                shenPi1.setCreate_time(indent_create_time);
                shenPi1.setIn_customer_id(in_customer_id);
                shenPi1.setIn_customer_name(in_customer_name);
                shenPi1.setIn_employee_id(in_employee_id);
                shenPi1.setIn_employee_name(in_employee_name);
                shenPi1.setIn_org_id(in_org_id);
                shenPi1.setIn_org_name(in_org_name);
//                shenPi1.setIn_org_parentId(in_org_parentId);
                shenPi1.setIn_status_id(in_status_id);
                shenPi1.setIn_status_name(in_status_name);


                return shenPi1;
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<ShenPi> getDaiShenPi(String s) {

        try {
            JSONObject jo = new JSONObject(s);
            JSONObject data = jo.optJSONObject("data");
            List<ShenPi> shenPiList = new ArrayList<>();
            if (data.has("list")) {
                JSONArray list = data.optJSONArray("list");
                for (int i = 0; i < list.length(); i++) {
                    JSONObject jo1 = list.optJSONObject(i);

                    String id = jo1.optString("id");
                    String start_time = jo1.optString("start_time");

                    JSONObject org = jo1.optJSONObject("org");
                    String org_id = org.optString("id");
                    String org_name = org.optString("name");
                    String org_parentId = org.optString("parentId");

                    JSONObject employee = jo1.optJSONObject("employee");
                    String employee_id = employee.optString("id");
                    String employee_name = employee.optString("name");

                    JSONObject status = jo1.optJSONObject("status");
                    String status_id = status.optString("id");
                    String status_name = status.optString("name");

                    JSONObject new_status = jo1.optJSONObject("new_status");
                    String new_status_id = new_status.optString("id");
                    String new_status_name = new_status.optString("name");

                    JSONObject indent = jo1.optJSONObject("indent");
                    String indent_id = indent.optString("id");
                    String indent_create_time = indent.optString("create_time");

                    JSONObject customer = indent.optJSONObject("customer");
                    String customer_id = customer.optString("id");
                    String customer_name = customer.optString("name");

                    JSONObject in_employee = indent.optJSONObject("employee");
                    String in_employee_id = in_employee.optString("id");
                    String in_employee_name = in_employee.optString("name");

                    JSONObject in_org = indent.optJSONObject("org");
                    String in_org_id = in_org.optString("id");
                    String in_org_name = in_org.optString("name");
                    String in_org_parentId = in_org.optString("parentId");

                    JSONObject in_status = indent.optJSONObject("status");
                    String in_status_id = in_status.optString("id");
                    String in_status_name = in_status.optString("name");

                    ShenPi shenPi = new ShenPi();
                    shenPi.setId(id);
                    shenPi.setStart_time(start_time);
                    shenPi.setOrg_id(org_id);
                    shenPi.setOrg_name(org_name);
                    shenPi.setOrg_parentId(org_parentId);
                    shenPi.setEmployee_id(employee_id);
                    shenPi.setEmployee_name(employee_name);
                    shenPi.setStatus_id(status_id);
                    shenPi.setStatus_name(status_name);
                    shenPi.setNew_status_id(new_status_id);
                    shenPi.setNew_status_name(new_status_name);
                    shenPi.setIndent_id(indent_id);
                    shenPi.setCreate_time(indent_create_time);
                    shenPi.setIn_customer_id(customer_id);
                    shenPi.setIn_customer_name(customer_name);
                    shenPi.setIn_employee_id(in_employee_id);
                    shenPi.setIn_employee_name(in_employee_name);
                    shenPi.setIn_org_id(in_org_id);
                    shenPi.setIn_org_name(in_org_name);
                    shenPi.setIn_org_parentId(in_org_parentId);
                    shenPi.setIn_status_id(in_status_id);
                    shenPi.setIn_status_name(in_status_name);

                    shenPiList.add(shenPi);
                }
                return shenPiList;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Number JsonNumber(String s) {
        Number number = new Number();
        try {
            JSONObject jo = new JSONObject(s);
            String status = jo.optString("status");
            number.setStatus_id(status);
            if (status.equals("200")) {
                JSONObject data = jo.optJSONObject("data");
                int customer_number = data.optInt("customer_number");
                int doing_audit_number = data.optInt("doing_audit_number");
                int my_audit_number = data.optInt("my_audit_number");
                int total_audit_number = data.optInt("done_audit_number");

                number.setCustomer_number(customer_number);
                number.setDoing_audit_number(doing_audit_number);
                number.setMy_audit_number(my_audit_number);
                number.setTotal_audit_number(total_audit_number);
            }else{

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return number;
    }


    /**
     * 版本更新
     */
    public static Edition jsonEdition(String s) {
        Edition edition = new Edition();
        try {
            JSONObject jo = new JSONObject(s);
            String status = jo.getString("status");
            if (status.equals("200")) {


                JSONObject data = jo.getJSONObject("data");
                String id = data.getString("id");
                String name = data.getString("name");
                String version = data.getString("version");
                String create_time = data.getString("create_time");
                String notes = data.getString("notes");

                edition.setId(id);
                edition.setName(name);
                edition.setVersion(version);
                edition.setCreate_time(create_time);
                edition.setNotes(notes);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return edition;
    }

    /**
     * 修改密码
     * @param s
     * @return
     */
    public static String JsonPasswd(String s){
        String data = null;
        try {
            JSONObject jo=new JSONObject(s);
            data=jo.optString("status");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }
}
