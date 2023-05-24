package com.wjh.nettytest;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Bio阻塞模式当有新的连接进来才会还是获取channel中的数据
 */
@Slf4j
public class BioTest {
    public static void main(String[] args) throws IOException {
        // 申请缓存
        ByteBuffer buffer = ByteBuffer.allocate(16);
        // 申请连接通道
        try(ServerSocketChannel ssc = ServerSocketChannel.open()) {
            ssc.bind(new InetSocketAddress(8080));
            // 监听端口
            // 用于存放建立的连接
            ArrayList<SocketChannel> list = new ArrayList<>();
            while (true) {
                // 阻塞方法，当有新的连接进来取消阻塞
                log.debug("connecting...");
                SocketChannel sc = ssc.accept();
                log.debug("connected:{}",sc);
                list.add(sc);
                // 遍历保存的连接
                for (SocketChannel socketChannel : list) {
                    socketChannel.read(buffer);
                    buffer.flip();
                    System.out.println(StandardCharsets.UTF_8.decode(buffer));
                    buffer.clear();
                }
            }
        }catch (IOException e) {
            System.out.println();
        }

    }
}
