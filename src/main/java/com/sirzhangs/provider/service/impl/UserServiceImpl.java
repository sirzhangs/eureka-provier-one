package com.sirzhangs.provider.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sirzhangs.common.entity.RequestResult;
import com.sirzhangs.provider.dto.UserDto;
import com.sirzhangs.provider.entity.User;
import com.sirzhangs.provider.mapper.UserMapper;
import com.sirzhangs.provider.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Value("${server.port}")
	private String port;
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public RequestResult add(User user) {
		Integer count = userMapper.add(user);
		return count != null && count > 0 ? RequestResult.ok():RequestResult.fail();
	}

	@Override
	public RequestResult delete(List<String> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RequestResult update(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RequestResult findById(String id) {
		return RequestResult.ok(port);
	}

	@Override
	public RequestResult findList(UserDto userDto) {
		// TODO Auto-generated method stub
		return null;
	}

}
