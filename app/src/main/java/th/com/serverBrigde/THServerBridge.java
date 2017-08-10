package th.com.serverBrigde;

/**
 * Created by The on 5/8/2016.
 */


import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class THServerBridge extends AsyncTask<String, String, String> {
    private static Selector selector;
    private static SocketChannel channel;
    private static boolean connected;

    public THServerBridge() {
    }

    public String sendToServer(String dataSend) throws IOException {
        Log.d("thedv", "send to Server");
        if (!channel.finishConnect()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        Log.d("thedv", "<Connected> connected: " + channel.isConnected());
        if (channel.isConnected()) {
            Log.d("thedv", "ready to send data");
            ByteBuffer buff = ByteBuffer.allocate(4096);
            buff.put(dataSend.getBytes());
            buff.flip();

            try {
                channel.write(buff);
                channel.register(selector, SelectionKey.OP_READ);
                Log.d("thedv", "Write data 1 OK!!!");
            } catch (NullPointerException e) {
                Log.e("thedv", e.toString());
            }
            String result = "";
            try {
                int channelReady = selector.select();
                System.out.println("sel: " + channelReady);
                for (Iterator<SelectionKey> it = selector.selectedKeys()
                        .iterator(); it.hasNext(); ) {
                    SelectionKey key = it.next();
                    it.remove();
                    if (key.isValid()) {
                        if (key.isReadable()) {
                            SocketChannel myChannelR = (SocketChannel) key
                                    .channel();
                            System.out.println("key is readable");
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            if (buffer.hasRemaining()) {
                                System.out.println("Readable: "
                                        + myChannelR.read(buffer));
                            }
                            buffer.flip();
                            result = new String(buffer.array()).trim();
                            // myChannelR
                            // .register(selector, SelectionKey.OP_WRITE);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        } else {
            return null;
        }
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            channel = SocketChannel.open();
            selector = Selector.open();
            String hostName = params[0];
            int port = Integer.valueOf(params[1]);
            String dataSend = params[2];
            Log.d("thedv", "before connected");
            Log.d("thedv", "ready :  hostName -> " + hostName );
            Log.d("thedv", "ready :  port -> " + port );
            Log.d("thedv", "ready :  dataSend -> " + dataSend );

            channel.configureBlocking(false);
            channel.connect(new InetSocketAddress(hostName, port));
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Log.d("thedv", e.toString()  );
            }
            connected = channel.isConnected();
            Log.d("thedv", "after  connected");
            Log.d("thedv", "connected: " + connected);
            result = sendToServer(dataSend);
            Log.d("thedv", "result: " + result);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private String result = "";

    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
    }

    // public static void main(String[] args) throws IOException,
    // InterruptedException {
    //
    // listeningFromServer();

    // ByteBuffer buff2 = ByteBuffer.allocate(1024);
    // buff2.put("hello le roi2 , em o dau day".getBytes());
    // buff2.flip();
    // channel.write(buff2);
    // System.out.println("Write data 2 OK!!!");
    // channel.register(selector, SelectionKey.OP_READ);
    //
    // listeningFromServer();
    // channel.close();
    // }

    // private static void listeningFromServer() {
    // // TODO Auto-generated method stub
    // int sel = 0;
    // System.out.println("Thread started");
    // try {
    // while (true) {
    // int channelReady = selector.select();
    // if (channelReady <= 0) {
    // break;
    // }
    //
    // System.out.println("sel: " + channelReady);
    // for (Iterator<SelectionKey> it = selector.selectedKeys()
    // .iterator(); it.hasNext();) {
    //
    // SelectionKey key = it.next();
    // it.remove();
    // if (key.isValid()) {
    // if (key.isReadable()) {
    // SocketChannel myChannelR = (SocketChannel) key
    // .channel();
    // System.out.println("key is readable");
    // ByteBuffer buffer = ByteBuffer.allocate(1024);
    // if (buffer.hasRemaining()) {
    // System.out.println("Readable: "
    // + myChannelR.read(buffer));
    // }
    // buffer.flip();
    // System.out.println(new String(buffer.array())
    // .trim());
    //
    // myChannelR
    // .register(selector, SelectionKey.OP_WRITE);
    // }
    // }
    // }
    // sel = selector.select();
    // }
    // } catch (IOException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // }

}
