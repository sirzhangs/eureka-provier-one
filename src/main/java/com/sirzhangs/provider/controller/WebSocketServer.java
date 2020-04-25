package com.sirzhangs.provider.controller;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/webSocket/{sid}")
public class WebSocketServer {

	private static AtomicInteger onlineCount = new AtomicInteger(0);

	private static Map<String, Session> sessionPool = new ConcurrentHashMap<>();

	/**
	 * 给指定的用户发送消息
	 * @param username
	 * @param message
	 */
	public void sendMessageToOne(String username, String message) {
		Session session = sessionPool.get(username);
		if (session != null) {
			try {
				session.getBasicRemote().sendText(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 发送数据给指定的session
	 * 
	 * @param session
	 * @param messsage
	 */
	public void sendMessage(Session session, String messsage) {
		if (session != null) {
			synchronized (session) {
				try {
					session.getBasicRemote().sendText(messsage);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 监听用户建立连接
	 * @param session
	 * @param username
	 */
	@OnOpen
	public void onOpen(Session session, @PathParam(value = "sid") String username) {
		sessionPool.put(username, session);
		addOnlineCount();
		System.out.println(username + "加入webSocket！当前人数为" + onlineCount);
		sendMessage(session, "欢迎" + username + "加入连接！");
	}

	/**
	 * 监听用户关闭连接
	 * @param session
	 * @param username
	 */
	@OnClose
	public void onClose(Session session, @PathParam(value = "sid") String username) {
		sessionPool.remove(username);
		subOnlineCount();
		System.out.println(username + "断开webSocket连接！当前人数为" + onlineCount);
	}

	/**
	 * 监听用户收到消息
	 * @param message
	 * @throws IOException
	 */
	@OnMessage
	public void onMessage(String message) throws IOException {
		message = "客户端：" + message + ",已收到";
		System.out.println(message);
		for (Session session : sessionPool.values()) {
			try {
				sendMessage(session, message);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
	}

	/**
	 * 监听传输报错
	 * @param session
	 * @param throwable
	 */
	@OnError
	public void onError(Session session, Throwable throwable) {
		System.out.println("发生错误");
		throwable.printStackTrace();
	}

	public static void addOnlineCount() {
		onlineCount.incrementAndGet();
	}

	public static void subOnlineCount() {
		onlineCount.decrementAndGet();
	}
}
