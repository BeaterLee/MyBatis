package com.beater.mybatis.bean;

import java.io.Serializable;
import java.util.List;

public class Department implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String deptName;
	private List<Employee> empList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDeptName() {
		return deptName;
	}

	public List<Employee> getEmpList() {
		return empList;
	}

	public void setEmpList(List<Employee> empList) {
		this.empList = empList;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Department() {
		super();
	}

	public Department(Integer id) {
		super();
		this.id = id;
	}

	@Override
	public String toString() {
		return "Department [id=" + id + ", deptName=" + deptName + ", empList=" + empList + "]";
	}

}
