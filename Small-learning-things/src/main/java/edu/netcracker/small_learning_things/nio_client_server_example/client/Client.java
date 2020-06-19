package edu.netcracker.small_learning_things.nio_client_server_example.client;

import lombok.SneakyThrows;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Client {
    static ArrayBlockingQueue<String> messages = new ArrayBlockingQueue<>(100);
    static Selector selector;
    static SocketChannel client;

    static {
        try {
            selector = Selector.open();
        } catch (IOException ignored) {
        }
    }

    @SneakyThrows
    public static void main(String[] args) {
        InetSocketAddress addr = new InetSocketAddress("localhost", 9890);
        client = SocketChannel.open(addr);
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);

        messages.add("Solve equation: a: 5, b: 10, c: -25");
        messages.add("Solve equation: a: 1, b: 4, c: 4");
        messages.add("Solve equation: a: 1, b: 6, c: 9");
        messages.add("Solve equation: a: 1, b: 2, c: 3");
        messages.add("Greetings: Svyatoslav");
        messages.add("Greetings: Kabaye");
        messages.add("Greetings: Petr");
        messages.add("Close connection");

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleWithFixedDelay(Client::handleConnections, 0, 500, TimeUnit.MILLISECONDS);
    }

    @SneakyThrows
    private static void handleConnections() {
        selector.select();
        Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

        while (iterator.hasNext()) {
            SelectionKey myKey = iterator.next();
            if (myKey.isReadable()) {
                ByteBuffer buf = ByteBuffer.allocate(250);
                client.read(buf);

                final String value = new String(buf.array()).trim();
                log("Message received: " + value);
                if (value.equals("Close connection")) {
                    client.close();
                }
            } else if (myKey.isWritable()) {
                String str = messages.poll();
                log("Message sended: <" + str + ">");
                if (Objects.nonNull(str)) {
                    ByteBuffer buf = ByteBuffer.wrap(new String(str).getBytes());
                    client.write(buf);
                    buf.clear();
                    TimeUnit.SECONDS.sleep(5);
                }
            }
            iterator.remove();
        }
    }

    private static void log(String str) {
        System.out.println(str);
    }
}
