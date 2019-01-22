package com.canbot.u05.sdk.clientdemo.socket;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.canbot.u05.sdk.clientdemo.StringMsgBean;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by xh on 2017/8/5.
 */

public class ServerManager {

        private static final String TAG = "ServerManager";

        public static final int PORT = 20001;

        private Context mContext;

        private static ServerManager instance;

        // 服务端ServerSocket
        private ServerSocket mServerSocket;

        private List<Socket> clients = Collections.synchronizedList(new ArrayList<Socket>());

        private static Object lock = new Object();

        private ThreadPoolProxy poolProxy;

        private ServerManager() {
        }

        public static ServerManager getInstance() {
                if (instance == null) {
                        synchronized (lock) {
                                if (instance == null) {
                                        instance = new ServerManager();
                                }
                        }
                }
                return instance;
        }

        public final void init(Context mContext) {
                this.mContext = mContext;
                initServerSocket();
        }

        private final void initServerSocket() {
                destroy();
                poolProxy = new ThreadPoolProxy(5, 5);
                poolProxy.execute(new Runnable() {
                        @Override
                        public void run() {
                                try {
                                        mServerSocket = new ServerSocket(PORT);
                                        while (true) {
                                                Log.d(TAG, "before mServerSocket.accept()");
                                                final Socket mClientsocket = mServerSocket.accept();
                                                handleSocket(mClientsocket);
                                        }
                                }
                                catch (IOException e) {
                                        Log.d(TAG, "before mServerSocket.accept()" + e.getMessage());
                                        e.printStackTrace();
                                }
                        }
                });
        }

        private void destroy() {
                if (poolProxy != null) {
                        poolProxy.shutDown();
                        poolProxy = null;
                }
                synchronized (clients) {
                        for (Socket client : clients) {
                                try {
                                        client.close();
                                }
                                catch (IOException e) {
                                        e.printStackTrace();
                                }
                        }
                        clients.clear();
                }
                if (mServerSocket != null) {
                        try {
                                mServerSocket.close();
                        }
                        catch (IOException e) {
                                e.printStackTrace();
                        }
                        mServerSocket = null;
                }

        }

        /**
         * 处理单个客户端的连接消息
         *
         * @param socket
         */
        private void handleSocket(final Socket socket) {
                poolProxy.execute(new Runnable() {
                        @Override
                        public void run() {
                                Log.d(TAG, "client connect success,ip=" + socket.getInetAddress());
                                synchronized (clients) {
                                        clients.add(socket);
                                }
                                String aa = socket.getRemoteSocketAddress().toString();
                                System.out.println(aa);
                                DataInputStream in = null;
                                OutputStream out = null;
                                try {
                                        out = socket.getOutputStream();
                                        in = new DataInputStream(socket.getInputStream());
                                        // 下面处理读信息.
                                        while (true) {
                                                Log.d(TAG, "before mDataInputStream.read()");
                                                String content = in.readUTF();
                                                Log.d(TAG, "readUTF is " + content);
                                                handleStringMsg(socket.getInetAddress().toString(), content);
                                        }
                                }
                                catch (IOException e) {
                                        e.printStackTrace();
                                        Log.d(TAG, "IOException=" + e.toString());
                                }
                                finally {
                                        Log.d(TAG, "client  connection  lost");


                                        synchronized (clients) {
                                                clients.remove(socket);
                                        }

                                        if (out != null) {
                                                try {
                                                        out.close();
                                                }
                                                catch (IOException e1) {
                                                        e1.printStackTrace();
                                                }
                                        }
                                        if (in != null) {
                                                try {
                                                        in.close();
                                                }
                                                catch (IOException e1) {
                                                        e1.printStackTrace();
                                                }
                                        }
                                        try {
                                                socket.close();
                                        }
                                        catch (IOException e) {
                                                e.printStackTrace();
                                        }
                                }
                        }
                });
        }

        /**
         * 发送string 类型的消息到所有客户端， 如果其中一个客户端发送失败，就将这个客户端从list中删除.
         *
         * @param str
         */

        public final void sendStringMsgToAllClients(String str) {

                synchronized (clients) {
                        Iterator<Socket> socketIterator = clients.iterator();

                        while (socketIterator.hasNext()) {
                                Socket client = socketIterator.next();
                                if (client == null) {
                                        break;
                                }
                                DataOutputStream out;
                                try {
                                        out = new DataOutputStream(client.getOutputStream());
                                        out.writeUTF(str);
                                        out.flush();
                                        Log.d(TAG, "sendStringMsgToAllClients success " + str);
                                }
                                catch (IOException e) {
                                        Log.e(TAG, "sendStringMsgToAllClients IOException ");
                                        try {
                                                client.close();
                                        }
                                        catch (IOException e1) {
                                                e1.printStackTrace();
                                        }
                                        socketIterator.remove();
                                        e.printStackTrace();
                                }

                        }
                }
        }

        /**
         * string类型的消息
         *
         * @param ip
         * @param data
         */
        private void handleStringMsg(String ip, final String data) {
                try {
                        Log.d(TAG, "handleStringMsg " + data);
                                new Handler(mContext.getMainLooper()).post(new Runnable() {
                                        @Override
                                        public void run() {
                                        Toast.makeText(mContext, "收到消息：" + data, Toast.LENGTH_SHORT).show();
                                        }
                                });
                }
                catch (NumberFormatException e) {
                        e.printStackTrace();
                        Log.d(TAG, "stringMsgBean parse error！");
                }
        }

}
