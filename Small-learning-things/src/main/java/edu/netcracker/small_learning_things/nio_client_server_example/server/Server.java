package edu.netcracker.small_learning_things.nio_client_server_example.server;

import lombok.SneakyThrows;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

//@SpringBootApplication
public class Server {

    static Selector selector;
    static ServerSocketChannel socket;
    static ArrayBlockingQueue<String> messages = new ArrayBlockingQueue<>(100);

    static {
        try {
            selector = Selector.open();
            socket = ServerSocketChannel.open();
        } catch (IOException ignored) {
        }
    }

    // Server
    @SneakyThrows
    public static void main(String[] args) {
        socket.configureBlocking(false);
        socket.bind(new InetSocketAddress(9890));
        socket.register(selector, socket.validOps(), null);

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            try {
                log("Listening to incoming connections...");
                final SocketChannel accept = socket.accept();
                if (Objects.nonNull(accept)) {
                    handleConnection(accept);
                    executor.shutdown();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }, 0, 3000, TimeUnit.MILLISECONDS);
    }

    @SneakyThrows
    private static void handleConnection(SocketChannel client) {
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleWithFixedDelay(Server::handleConnections, 0, 200, TimeUnit.MILLISECONDS);
    }

    @SneakyThrows
    private static void handleConnections() {
        selector.select();
        Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

        while (iterator.hasNext()) {
            SelectionKey myKey = iterator.next();

            if (myKey.isAcceptable()) {
                SocketChannel newClient = socket.accept();
                newClient.configureBlocking(false);
                newClient.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                log("Connection Accepted: " + newClient.getLocalAddress());

            } else if (myKey.isReadable()) {
                SocketChannel client = (SocketChannel) myKey.channel();
                ByteBuffer buf = ByteBuffer.allocate(250);
                client.read(buf);

                final String value = new String(buf.array()).trim();
                log("Message received: " + value);

                messages.offer(value);

            } else if (myKey.isWritable()) {
                final String value = messages.poll();
                String output = "";

                if (Objects.nonNull(value)) {
                    if (value.equals("Close connection")) {
                        log("\nIt's time to close connection as we got close command 'Close connection'");
                        log("\nServer will keep running. Try running client again to establish new connection");
                        output = value;
                    } else if (value.contains("Solve equation:")) {
                        String aSubStr = value.substring(value.indexOf("a: ") + 3);
                        String bSubStr = aSubStr.substring(aSubStr.indexOf("b: ") + 3);
                        String cSubStr = bSubStr.substring(bSubStr.indexOf("c: ") + 3);

                        int a = Integer.parseInt(aSubStr.substring(0, aSubStr.indexOf(",")));
                        int b = Integer.parseInt(bSubStr.substring(0, bSubStr.indexOf(",")));
                        int c = Integer.parseInt(cSubStr);

                        int disc = b * b - 4 * a * c;

                        if (disc > 0) {
                            double r1 = (-b + Math.pow(disc, 0.5)) / (2.0 * a);
                            double r2 = (-b - Math.pow(disc, 0.5)) / (2.0 * a);
                            output = "Equation solved: x1 = " + r1 + ", x2 = " + r2 + ";";
                        } else if (disc == 0) {
                            double r1 = -b / (2.0 * a);
                            output = "Equation solved: x1 = " + r1 + ";";
                        } else {
                            output = "No real roots";
                        }
                    } else if (value.contains("Greetings:")) {
                        output = "Hello " + value.substring("Greetings: ".length()) + "! How r u?";
                    }

                    if (!output.isEmpty()) {
                        SocketChannel client = (SocketChannel) myKey.channel();
                        ByteBuffer buf = ByteBuffer.wrap(output.getBytes());
                        client.write(buf);
                        log("Sended: <" + output + ">");
                        buf.clear();
                        if (output.equals("Close connection")) {
                            client.close();
                        }
                    }
                }
            }
            iterator.remove();
        }
    }

    private static void log(String str) {
        System.out.println(str);
    }
}
