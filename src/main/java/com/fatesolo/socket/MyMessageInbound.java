package com.fatesolo.socket;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.WsOutbound;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

public class MyMessageInbound extends MessageInbound {

    //断开连接
    @Override
    protected void onClose(int status) {
        WebSocketManager.remove(this);
        super.onClose(status);
    }

    //建立连接
    @Override
    protected void onOpen(WsOutbound outbound) {
        super.onOpen(outbound);
        WebSocketManager.add(this);
    }

    @Override
    protected void onBinaryMessage(ByteBuffer message) throws IOException {
    }

    @Override
    protected void onTextMessage(CharBuffer message) throws IOException {
    }

}
