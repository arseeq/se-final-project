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
    private Set<WebSocket> conns;
    private UserDbManager userManager;
    private EventDbManager eventDbManager;
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

        JSONObject obj = new JSONObject(message);

        String type = obj.getString("type");

        User user = getUserDbManager().getUserByEmail( AuthService.getTokenUtil().isValidToken(obj.getString("token")));
        System.out.println(type  + "   1111111111111111111111111111111111111111111111111111111111");
        String eventId = obj.getString("eventId");
        Event event=null;
        boolean isParticipant = false;
        if(eventId==null)
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
                //webSocket.send("You are not participant of this group");
            }
            //return Response.ok(new Gson().toJson(result)).build();

        }else if(type.equals("message")){
            Message msg = new Message();

            msg.setMsg(obj.getString("msg"));

            if(user != null && isParticipant ) {
                System.out.println("22222222222222222222222222");
                msg.setAuthor(user);

                msg.setBelGroup(event);
                msg.setDate(new Date());
                users.put(user, webSocket);
                broadCast(msg, user);
                logger.info("Message from user: " + msg.getAuthor().getName() + ", text: " + msg.getMsg());
            }else{
                System.out.println("::::::::::::::::::::something went wrong on message JSON content :::::::::::::::::::");
            }
        }


    }


    @Override
    public void onError(WebSocket webSocket, Exception e) {
        if(webSocket != null)
            conns.remove(webSocket);

        assert webSocket!=null;
        logger.info("ERROR from " + webSocket.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    private void broadCast(Message msg, User me) {
        getMessageDbManager().insertMessage(msg);
        System.out.println(msg.getBelGroup().getParticipants() + "44444444444444444444444\n\n\n\n\n\n\n");

        SocketMsg socketMsg = new SocketMsg();

        socketMsg.setType("message");

        socketMsg.setEventId(msg.getBelGroup().getId());

        List res = new ArrayList<Message>();
        //msg.setBelGroup(null);
        res.add(msg);

        socketMsg.setData(res);

        for(Map.Entry<User, WebSocket> entry: users.entrySet()){
            System.out.println("Key: " + entry.getKey() + ", value: " + entry.getValue() + " 1212121212\n\n\n\n\n\n");
        }

        String sending = new Gson().toJson(socketMsg);
        System.out.println("7777777\n\n\n\n\n\n\n\n\n\n\n");
        for(User user: msg.getBelGroup().getParticipants()){
            System.out.println(user + "\n\n\n\n\n\n\n\n\n");
            if(users.containsKey(user)) {
                System.out.println(user + "55555555555555555555555555555555555555555 " + users.get(user) + "\n\n\n\n\n\n\n");

                users.get(user).send(sending);
            }
        }

    }

    private UserDbManager getUserDbManager(){
        //if( userManager == null) {
            userManager = new UserDbManager();
        //}
        return userManager;
    }

    private MessageDbManager getMessageDbManager(){
        //if( messageDbManager == null) {
            messageDbManager = new MessageDbManager();
        //}
        return messageDbManager;
    }

    private EventDbManager getEventDbManager(){
        //if( eventDbManager == null) {
            eventDbManager = new EventDbManager();
        //}
        return eventDbManager;
    }

}
