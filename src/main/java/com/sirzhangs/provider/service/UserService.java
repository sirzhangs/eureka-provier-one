package com.sirzhangs.provider.service;

import java.util.List;

import com.sirzhangs.common.entity.RequestResult;
import com.sirzhangs.provider.dto.UserDto;
import com.sirzhangs.provider.entity.User;

public interface UserService {
	/**
	 * 添加用户
	 * @param user
	 * @return
	 */
	RequestResult add(User user);
	
	/**
	 * 删除用户
	 * @param ids
	 * @return
	 */
	RequestResult delete(List<String> ids);
	
	/**
	 * 修改用户
	 * @param user
	 * @return
	 */
	RequestResult update(User user);
	
	/**
	 * 主键查询
	 * @param id
	 * @return
	 */
	RequestResult findById(String id);
	
	/**
	 * 列表查询
	 * @param userDto
	 * @return
	 */
	RequestResult findList(UserDto userDto);

}
