package cn.iotframe.common.pagination;

import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: yanzelin
 * @Date: 2020/6/8/008
 * @Description:
 */
@Data
public class PageRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private int page;
    private int pageSize;
    private String  orderBy;

}


