package com.training.customerservice.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import java.util.Arrays;

@Service
public class InMemoryPipeService {
   private Pipe pipe;
//   @PostConstruct
//   public void setupPipe() throws IOException {
//       pipe = Pipe.open();
//   }

   public void startCommunication(String message) throws IOException{
       pipe = Pipe.open();
        Thread writerThread = new Thread(()->writeToPipe(message));
        Thread readerThread = new Thread(this::readFromPipe);
        writerThread.start();
        readerThread.start();
   }

    private void readFromPipe() {
        try(Pipe.SourceChannel sourceChannel = pipe.source()){
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int byteRead = sourceChannel.read(buffer);
            buffer.flip();
            byte[] data = new byte[byteRead];
            buffer.get(data);
            System.out.println("Reader: Message Received-"+ new String(data));

        } catch (Exception e) {
          e.printStackTrace();
        }
    }

    private void writeToPipe(String message){
       try(Pipe.SinkChannel sinkChannel = pipe.sink()){
           ByteBuffer buffer = ByteBuffer.allocate(1024);
           buffer.put(message.getBytes());
           buffer.flip();
           while(buffer.hasRemaining()){
               sinkChannel.write(buffer);
           }
       } catch (Exception e) {
          e.printStackTrace();
       }
    }

}
