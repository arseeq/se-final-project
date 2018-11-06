package kz.edu.nu.cs.Services;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.edu.nu.cs.Model.EventGroup;
import kz.edu.nu.cs.Model.Message;
import kz.edu.nu.cs.Model.User;
import kz.edu.nu.cs.Utility.TokenUtil;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ChatServer extends WebSocketServer {

    private final static Logger logger = LoggerFactory.getLogger(ChatServer.class);
    private HashMap<User, WebSocket> users;
    private Set<WebSocket> conns;
    private UserDbManager userManager;
    private EventGroupDbManager eventGroupDbManager;
    private MessageDbManager messageDbManager;

    public ChatServer(int port) {
        super(new InetSocketAddress(port));
        users = new HashMap<>();
        conns = new HashSet<>();
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        System.out.println("Connection established from: " + webSocket.getRemoteSocketAddress().getHostString());
        conns.add(webSocket);
        logger.info("Connection established from: " + webSocket.getRemoteSocketAddress().getHostString());

    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {
        conns.remove(webSocket);
        users.remove(webSocket);
        logger.info("Connection closed to: " + webSocket.getRemoteSocketAddress().getHostString());

    }

    @Override
    public void onMessage(WebSocket webSocket, String message) {
        ObjectMapper mapper = new ObjectMapper();
        Message msg = new Message();
        JSONObject obj = new JSONObject(message);
        msg.setMsg(obj.getString("msg"));
        User user = getUserDbManager().getUserByEmail( AuthService.getTokenUtil().isValidToken(obj.getString("token")));
        msg.setAuthor(user);
        msg.setGroup(getEventGroupDbManager().getGroupById(obj.getInt("eventId")));
        msg.setDate(new Date());
        if(!users.containsKey(user))
            users.put(user, webSocket);
        broadCast(msg);
        logger.info("Message from user: " + msg.getAuthor().getName() + ", text: " + msg.getMsg());
    }


    @Override
    public void onError(WebSocket webSocket, Exception e) {
        if(webSocket != null)
            conns.remove(webSocket);

        assert webSocket!=null;
        logger.info("ERROR from " + webSocket.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    private void broadCast(Message msg) {
        for(User user: msg.getGroup().getParticipants()){
            users.get(user).send(msg.getMsg());
        }

        getMessageDbManager().insertMessage(msg);
    }

    private UserDbManager getUserDbManager(){
        if( userManager == null) {
            userManager = new UserDbManager();
        }
        return userManager;
    }

    private MessageDbManager getMessageDbManager(){
        if( messageDbManager == null) {
            messageDbManager = new MessageDbManager();
        }
        return messageDbManager;
    }

    private EventGroupDbManager getEventGroupDbManager(){
        if( eventGroupDbManager == null) {
            eventGroupDbManager = new EventGroupDbManager();
        }
        return eventGroupDbManager;
    }

}
