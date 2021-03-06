package com.qunjie.crm.beans.results;

import com.google.common.base.MoreObjects;

import java.util.Map;

/**
 * CRM 
 * Created by huanghp on 2017/04/10.
 */
public class CrmDescResult extends BaseResult {

    private static final long serialVersionUID = -6938203198421600660L;

    private Map<String, Map<String, Object>> objectDesc;

    public Map<String, Map<String, Object>> getObjectDesc() {
        return objectDesc;
    }

    public void setObjectDesc(Map<String, Map<String, Object>> objectDesc) {
        this.objectDesc = objectDesc;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("objectDesc", objectDesc)
                .toString();
    }
}
