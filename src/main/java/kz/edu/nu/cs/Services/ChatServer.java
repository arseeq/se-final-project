package kz.edu.nu.cs.Services;

import com.google.gson.Gson;
import kz.edu.nu.cs.Model.Event;
import kz.edu.nu.cs.Model.Message;
import kz.edu.nu.cs.Model.User;
import kz.edu.nu.cs.Utility.SocketMsg;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.*;

public class ChatServer extends WebSocketServer {

    private final static Logger logger = LoggerFactory.getLogger(ChatServer.class);
    private Map<User, WebSocket> users;

    public ChatServer(int port) {
        super(new InetSocketAddress(port));
        users = new HashMap<>();
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        System.out.println("Connection established from: " + webSocket.getRemoteSocketAddress().getHostString());
        logger.info("Connection established from: " + webSocket.getRemoteSocketAddress().getHostString());

    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {
        users.remove(webSocket);
        logger.info("Connection closed to: " + webSocket.getRemoteSocketAddress().getHostString());

    }

    @Override
    public void onMessage(WebSocket webSocket, String message) {

        JSONObject obj = new JSONObject(message);
        String type = obj.getString("type");
        User user = getUserDbManager().getUserByEmail( AuthService.getTokenUtil().isValidToken(obj.getString("token")));
        String eventId = obj.getString("eventId");
        Event event = null;
        boolean isParticipant = false;
        if( eventId == null )
            System.out.println("event id is null");
        else{
            event = getEventDbManager().getEventById(Integer.parseInt(eventId));
            if(event!=null){
                for ( User u : event.getParticipants() ) {
                    if(u.equals(user)) {
                        isParticipant=true;
                        break;
                    }
                }
            }

        }

        if(type.equals("history")){

            if(isParticipant ){
                List<Message> result = getMessageDbManager().getAllGroupMsgByEventId(event.getId());
                for(Message msg: result){
                    msg.setBelGroup(null);
                }

                SocketMsg socketMsg = new SocketMsg();
                socketMsg.setEventId(Integer.parseInt(eventId));
                socketMsg.setType("history");
                socketMsg.setData(result);
                String textToSend = new Gson().toJson(socketMsg);
                users.put(user, webSocket);
                webSocket.send(textToSend);
            }else{
                webSocket.send("You are not participant of this group");
            }

        }else if(type.equals("message")){
            Message msg = new Message();

            msg.setMsg(obj.getString("msg"));

            if(user != null && isParticipant ) {
                System.out.println("22222222222222222222222222");
                msg.setAuthor(user);

                msg.setBelGroup(event);
                msg.setDate(new Date());
                users.put(user, webSocket);
                broadCast(msg);
                logger.info("Message from user: " + msg.getAuthor().getName() + ", text: " + msg.getMsg());
            }else{
                webSocket.send("your message format doesn't match the protocol or you are not participant of this group");
            }
        }else if(type.equals("connect") && isParticipant){
            users.put(user, webSocket);
        }else{
            webSocket.send("your message format doesn't match the protocol or you are not participant of this group");
        }


    }


    @Override
    public void onError(WebSocket webSocket, Exception e) {
        if(webSocket != null)
            users.remove(webSocket);

        assert webSocket!=null;
        logger.info("ERROR from " + webSocket.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    private void broadCast(Message msg) {
        getMessageDbManager().insertMessage(msg);

        SocketMsg socketMsg = new SocketMsg();

        socketMsg.setType("message");

        socketMsg.setEventId(msg.getBelGroup().getId());

        List res = new ArrayList<Message>();
        //msg.setBelGroup(null);
        res.add(msg);

        socketMsg.setData(res);

        String sending = new Gson().toJson(socketMsg);
        for(User user: msg.getBelGroup().getParticipants()){
            if(users.containsKey(user)) {
                users.get(user).send(sending);
            }
        }

    }

    private UserDbManager getUserDbManager(){

        return new UserDbManager();
    }

    private MessageDbManager getMessageDbManager(){

        return new MessageDbManager();
    }

    private EventDbManager getEventDbManager(){

        return new EventDbManager();
    }

}
