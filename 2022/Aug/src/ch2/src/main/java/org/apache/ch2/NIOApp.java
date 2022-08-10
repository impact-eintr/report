package org.apache.ch2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOApp {
  public static void selector() throws IOException {
    ByteBuffer buffer = ByteBuffer.allocate(1024);
    Selector selector = Selector.open();
    ServerSocketChannel ssc = ServerSocketChannel.open();
    ssc.configureBlocking(false); // 设置为非阻塞
    ssc.socket().bind(new InetSocketAddress(8080));
    while (true) {
      Set<SelectionKey> selectedKeys = selector.selectedKeys(); // 取得所有key集合
      Iterator<SelectionKey> it = selectedKeys.iterator();
      while (it.hasNext()) {
        SelectionKey key = (SelectionKey) it.next();
        if ((key.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT) {
          ServerSocketChannel ssChannel = (ServerSocketChannel) key.channel();
          SocketChannel sc = ssChannel.accept(); // 接受到服务端的请求
          System.out.println("TEST");
          sc.configureBlocking(false);
          sc.register(selector, SelectionKey.OP_READ);
          it.remove();
        } else if ((key.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ) {
          SocketChannel sc = (SocketChannel) key.channel();
          while (true) {
            buffer.clear();
            int n = sc.read(buffer); // 读取数据
            if (n <= 0) {
              break;
            }
            System.out.println(buffer.toString());
            buffer.flip();
          }
          it.remove();
        }
      }
    }
  }

  public static void main(String[] args) {
    try {
      selector();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
