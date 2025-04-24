package com.training;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NIOClient {
    public static void main(String[] args) {
        try (SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("localhost", 5000))) {
            String message = "Hello from client at " + System.currentTimeMillis();
            ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
            socketChannel.write(buffer);

            // Read echo
            buffer.clear();
            socketChannel.read(buffer);
            buffer.flip();
            System.out.println("Server replied: " + new String(buffer.array(), 0, buffer.limit()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

