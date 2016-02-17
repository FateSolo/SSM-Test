package com.fatesolo.socket;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.WsOutbound;

import java.nio.CharBuffer;
import java.util.concurrent.CopyOnWriteArrayList;

public class WebSocketManager {

    //线程安全的ArrayList
    private static CopyOnWriteArrayList<MessageInbound> clients = new CopyOnWriteArrayList<MessageInbound>();

    public static void add(MessageInbound client) {
        clients.add(client);
    }

    public static void remove(MessageInbound client) {
        clients.remove(client);
    }

    public static int getSize() {
        return clients.size();
    }

    public static void sendMessage(String message) {
        for (MessageInbound client : clients) {
            try {
                CharBuffer buffer = CharBuffer.wrap(message);
                WsOutbound outbound = client.getWsOutbound();

                outbound.writeTextMessage(buffer);
                outbound.flush();
            } catch (Exception e) {
                remove(client);
            }
        }
    }

}
