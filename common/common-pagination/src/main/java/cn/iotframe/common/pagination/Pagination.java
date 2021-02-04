package cn.iotframe.common.pagination;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Pagination<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<T> list;

	private int totalCount;

	private int page;

	private int pageSize;
}
