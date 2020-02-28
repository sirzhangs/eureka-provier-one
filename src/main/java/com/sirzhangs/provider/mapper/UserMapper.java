package com.sirzhangs.provider.mapper;

import java.util.List;

import com.sirzhangs.provider.dto.UserDto;
import com.sirzhangs.provider.entity.User;

public interface UserMapper {

	/**
	 * 添加用户
	 * @param user
	 * @return
	 */
	Integer add(User user);
	
	/**
	 * 删除用户
	 * @param ids
	 * @return
	 */
	Integer delete(List<String> ids);
	
	/**
	 * 修改用户
	 * @param user
	 * @return
	 */
	Integer update(User user);

	/**
	 * 查询用户
	 * @param id
	 * @return
	 */
	User findById(String id);
	
	/**
	 * 列表查询
	 * @param userDto
	 * @return
	 */
	List<User> findList(UserDto userDto);
}
