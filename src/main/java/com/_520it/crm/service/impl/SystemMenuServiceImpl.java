package com._520it.crm.service.impl;

import java.util.Collections;
import java.util.List;

import com._520it.crm.domain.Employee;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com._520it.crm.domain.SystemMenu;
import com._520it.crm.mapper.SystemMenuMapper;
import com._520it.crm.page.PageResult;
import com._520it.crm.query.QueryObject;
import com._520it.crm.service.ISystemMenuService;
@Service
public class SystemMenuServiceImpl implements ISystemMenuService {
	@Autowired
	private SystemMenuMapper systemMenuMapper;
	
	public int deleteByPrimaryKey(Long id) {
		return systemMenuMapper.deleteByPrimaryKey(id);
	}

	public int insert(SystemMenu record) {
		return systemMenuMapper.insert(record);
	}

	public SystemMenu selectByPrimaryKey(Long id) {
		return systemMenuMapper.selectByPrimaryKey(id);
	}

	public List<SystemMenu> selectAll() {
		return systemMenuMapper.selectAll();
	}

	public int updateByPrimaryKey(SystemMenu record) {
		return systemMenuMapper.updateByPrimaryKey(record);
	}

	@Override
	public PageResult queryPage(QueryObject qo) {
		Long count = systemMenuMapper.queryPageCount(qo);
		if(count<=0){
			return new PageResult(count.intValue(), Collections.EMPTY_LIST);
		}
		List<SystemMenu> result = systemMenuMapper.queryPageDataResult(qo);
		PageResult pageResult = new PageResult(count.intValue(),result);
		return pageResult;
	}

	@Override
	public List<SystemMenu> queryTree() {
		return systemMenuMapper.queryTree();
	}

	@Override
	public List<SystemMenu> queryForRole() {
		return systemMenuMapper.queryTree();
	}

	@Override
	public List<Long> queryMenuIdsListForRole(Long roleId) {
		return systemMenuMapper.systemMenuMapper(roleId);
	}

	@Override
	public List<SystemMenu> indexMenu() {
		Employee current = (Employee) SecurityUtils.getSubject().getPrincipal();
		List<SystemMenu> userMenus = systemMenuMapper.queryTree();
		//如果当前登录的用户是超级管理员,拥有所有菜单权限
		if(current.getAdmin()){
			return userMenus;
		}else{
			//若不是超级管理员,则查询出该用户所拥有的菜单id集合
			List<Long> ids = systemMenuMapper.queryMenuIdsByEmployeeId(current.getId());
			//根据ids筛选出对应的菜单权限
			filterMenus(userMenus,ids);
		}
		return systemMenuMapper.queryTree();

	}

	private void filterMenus(List<SystemMenu> userMenus, List<Long> ids) {
		SystemMenu menu = null;
		//循环迭代出每一个根菜单
		for (int i = userMenus.size()-1; i>=0 ; i--) {
			 menu = userMenus.get(i);
			//判断当前登录用户的id集合是否包含根菜单的id
			if (!ids.contains(menu.getId())){
				//若不包含则删除
				userMenus.remove(menu);
				continue;
			}else{
				//若包含,则判断该根菜单的子菜单是否在该用户的ids集合中
				if (menu.getChildren()!=null&&menu.getChildren().size()>0){
					filterMenus(menu.getChildren(),ids);
				}
			}
		}

	}
}
