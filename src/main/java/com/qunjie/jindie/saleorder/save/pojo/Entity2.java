package com.qunjie.jindie.saleorder.save.pojo;
/**
 * Created by whs on 2020/12/8.
 */

import lombok.Data;

/**
 * Copyright (C),2020-2020,群杰印章物联网
 * FileName: com.qunjie.jindie.saleorder.save.pojo.Entity2
 *
 * @author whs
 *         Date:   2020/12/8  15:25
 *         Description:
 *         History:
 *         &lt;author&gt;    &lt;time&gt;  &lt;version&gt;  &lt;desc&gt;
 *         修改人姓名           修改时间           版本号          描述
 */
@Data
public class Entity2 {

    private String FNumber;

    public Entity2() {
    }

    public Entity2(String FNumber) {
        this.FNumber = FNumber;
    }
}
