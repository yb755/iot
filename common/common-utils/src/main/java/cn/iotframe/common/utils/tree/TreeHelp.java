package cn.iotframe.common.utils.tree;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * 树形结构帮助类
 */
@Slf4j
public class TreeHelp {

	protected static Comparator<Tree> getComparator(){
		return new Comparator<Tree>(){
			@Override
			public int compare(Tree o1, Tree o2) {
				if(o1.getSort()!=null&&o2.getSort()!=null){
					//降序  大数在前面
					return o2.getSort()-o1.getSort() ;
				}
				return o2.getId()-o1.getId();
			}
		};
	}
	
	/**
	 * 通过id查询所有的父级的tree
	 * @param datas
	 * @param id
	 * @return
	 */
	public static List<Tree> findAllTopParent(List<? extends Tree> datas,Integer id){
		if(CollectionUtils.isEmpty(datas)){
			return null ;
		}
		List<Tree> trees = new ArrayList<>();
		for (Iterator<? extends Tree> iterator = datas.iterator(); iterator.hasNext();) {
			 Tree tree = iterator.next();
			 //找到自己
			 if(id.equals(tree.getId())){
				 //找到parent
				 trees.add(tree);
				 List<Tree> reTree = findAllTopParent(datas,tree.getParentId());
				 if(!CollectionUtils.isEmpty(reTree)){
					 trees.addAll(reTree);
				 }
				 break ;
			 }
		}
		return trees ;
	}
	/**
	 * 通过id查询最顶级的tree
	 * @param datas
	 * @param id 当前分组
	 * @return null表示没有顶级
	 */
	public static Tree findTopParentTree(List<? extends Tree> datas,Integer id){
		if(id==null||id==0){
			return null ;
		}
		Tree parent = null ;
		for (Iterator<? extends Tree> iterator = datas.iterator(); iterator.hasNext();) {
			Tree tree = iterator.next();
			if(id.equals(tree.getId())){
				Tree reTree = findTopParentTree(datas,tree.getParentId());
				if(reTree==null){
					parent = tree ;
				}else{
					parent = reTree ;
				}
				break ;
			}
		}
		return parent ;
	}
	
	/**
	 * 将集合数据组织成树形结构的集合数据(自动构成树型)
	 * @param datas
	 * @return
	 */
	public static List<Tree> listToTreeList(List<? extends Tree> datas){
		if(CollectionUtils.isEmpty(datas)){
			return null ;
		}
		List<Tree> trees = new ArrayList<>();
		for(Tree tree : datas){
			if(tree.getParentId()!=null){
				Tree parent = findByParentId(datas, tree.getParentId());
				if(parent==null){
				    log.warn("listToTreeList --- > parentId:{}",tree.getParentId());
					List<Tree> listTrees = listToTreeList(datas,tree.getId());
					tree.setChilds(listTrees);
					trees.add(tree);
				}
			}
		}
		 log.warn("listToTreeList --- > trees大小:{}",trees.size());
		return trees;
	}
	
	/**
	 * 查询树形结构中的parent
	 * 
	 * @param datas
	 * @param parentId
	 * @return
	 */
	public static Tree findByParentId(List<? extends Tree> datas,Integer parentId){
		if(CollectionUtils.isEmpty(datas)||parentId==null||parentId==0){
			return null ;
		}
		Tree parent = null ;
		for (Iterator<? extends Tree> iterator = datas.iterator(); iterator.hasNext();) {
			Tree tree =  iterator.next();
			if(tree.getId().equals(parentId)){
				parent = tree ;
				break ;
			}
		}
		return parent ;
	}
	
	/**
	 * 将集合数据组织成树形结构的集合数据
	 * @param datas
	 * @param parentId
	 * @return
	 */
	public static List<Tree> listToTreeList(List<? extends Tree> datas,Integer parentId){
		if(CollectionUtils.isEmpty(datas)){
			return null ;
		}
		List<Tree> trees = new ArrayList<>();
		for (Tree tree : datas) {
			if(tree.getParentId()!=null&&tree.getParentId().equals(parentId)){
				trees.add(tree);
				List<Tree> childs =	listToTreeList(datas,tree.getId());
				//子排序
				Collections.sort(childs, getComparator());
				tree.setChilds(childs);
			}
		}
		Collections.sort(trees, getComparator());
		return trees ;
	}
	
	
	
	/**
	 * 将树形结构的集合数据组织成集合数据
	 * @param datas
	 * @return
	 */
	public static List<Tree> treeListToList(List<? extends Tree> datas){
		List<Tree> tree = new ArrayList<>();
		if(CollectionUtils.isEmpty(datas)){
			return tree ;
		}
		for(Tree data:datas){
			tree.add(data);
			List<Tree> childs = treeListToList(data.getChilds());
			tree.addAll(childs);
		}
		return tree ;
	}
	
	public static void output(List<? extends Tree> datas){
		if(CollectionUtils.isEmpty(datas)){
			return ;
		}
		for (Iterator<? extends Tree> iterator = datas.iterator(); iterator.hasNext();) {
			Tree tree =  iterator.next();
			log.info("tree:{},childs size:{}",tree,CollectionUtils.isEmpty(tree.getChilds())?0:tree.getChilds().size());
			output(tree.getChilds());
		}
	}
	
}
