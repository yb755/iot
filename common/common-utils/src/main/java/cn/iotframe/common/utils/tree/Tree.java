package cn.iotframe.common.utils.tree;

import java.io.Serializable;
import java.util.List;

/**
 * tree标识
 * ClassName: Tree <br/>
 */
public class Tree implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected Integer id ;

	protected Integer parentId ;
	
	protected Integer sort ;
	
	protected Integer level ;
	
	protected List<Tree> childs;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public List<Tree> getChilds() {
		return childs;
	}

	public void setChilds(List<Tree> childs) {
		this.childs = childs;
	}

}
