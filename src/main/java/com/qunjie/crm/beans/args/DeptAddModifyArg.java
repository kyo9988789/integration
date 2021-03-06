package com.qunjie.crm.beans.args;

import com.google.common.base.MoreObjects;
import com.qunjie.crm.beans.results.Department;

/**
 * 增加部门的参数 Created by zhongcy on 2016/4/8.
 */
public class DeptAddModifyArg extends BaseArg {

    private static final long serialVersionUID = 4492378393524138518L;

    /**
     * 部门信息@see Department
     */
    private Department department;

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("corpAccessToken", corpAccessToken)
                .add("corpId", corpId)
                .add("department", department)
                .toString();
    }
}
