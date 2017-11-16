package com._520it.crm.mapper;

import com._520it.crm.domain.SystemMenu;
import com._520it.crm.query.QueryObject;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SystemMenuMapper {
    int deleteByPrimaryKey(Long id);
    int insert(SystemMenu record);
    SystemMenu selectByPrimaryKey(Long id);
    List<SystemMenu> selectAll();
    int updateByPrimaryKey(SystemMenu record);
	Long queryPageCount(QueryObject qo);
	List<SystemMenu> queryPageDataResult(QueryObject qo);
	List<SystemMenu> queryTree();
	List<Long> systemMenuMapper(Long roleId);
	List<Long> queryMenuIdListByEmployeeId(Long id);

    /**
     * 根据当前登录用户的id,查询出该用户拥有的菜单id集合
     * @param employeeId
     * @return
     */
    List<Long> queryMenuIdsByEmployeeId(Long employeeId);
}