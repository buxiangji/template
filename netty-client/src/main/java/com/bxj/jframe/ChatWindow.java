package com.bxj.jframe;

/**
 * @author buxiangji
 * @makedate 2023/7/10 10:29
 */
import com.bxj.ClientChannelHandler;
import com.bxj.SendMessage;
import com.bxj.core.ChatMessageDecoder;
import com.bxj.core.ChatMessageEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import javax.swing.*;
import java.awt.*;

public class ChatWindow extends JFrame{
    private final String myName;
    private String friendName;
    private String serverIp = "10.253.81.24";
    private int serverPort = 8081;
    private Channel channel;
    private NioEventLoopGroup work = new NioEventLoopGroup();
    private Bootstrap bootstrap = new Bootstrap();
    private JTextArea sArea;

    public ChatWindow(String title,String myName) throws Exception {
        this.myName = myName;
        //�������������
        startTalk(serverIp,serverPort);
        //1.����
        this.setTitle(title);
        this.setLayout(new BorderLayout());
        this.setSize(600, 800);
        this.setLocation(300, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        //�����ı���
        sArea = new JTextArea(25, 20);
        //2.�����������������
        JScrollPane scrollPane = new JScrollPane(sArea);
        sArea.setEditable(false);//�ı��򲻿ɱ༭
        //3�����м���������3�����

        //--------------------------------------���ú�������---------------------------------------
        JPanel jpFriend = new JPanel();
        JButton jbFriend = new JButton("ȷ�Ϻ�������");
        JTextField jtFriend = new JTextField(20);
        jbFriend.addActionListener(e -> {
            String content = jtFriend.getText();//��ȡ���������
            if (content != null && !content.trim().equals("")) {
                this.friendName = content;
            } else {
                sArea.append("������Ϣ����Ϊ�գ�����" + "\n");
                jtFriend.setText(this.friendName);
            }
        });
        //--------------------------------------���뷢������---------------------------------------
        JPanel jp = new JPanel();
        JLabel jl = new JLabel("������Ϣ");
        JTextField jt = new JTextField(20);
        jt.addActionListener(e->send(jt));
        JButton jb = new JButton("����");
        jb.addActionListener(e -> send(jt));
        //--------------------------------------��������---------------------------------------
        jp.add(jl);
        jp.add(jt);
        jp.add(jb);
        jpFriend.add(jtFriend);
        jpFriend.add(jbFriend);
        this.add(jpFriend);
        this.add(jp, BorderLayout.PAGE_END);
        this.add(scrollPane, BorderLayout.PAGE_START);
    }

    public void setReceiveMessage(String s){
        sArea.append(s+"\n");
    }
    private void send(JTextField jt){
        {
            String content = jt.getText();//��ȡ���������
            if (content != null && !content.trim().equals("")) {
                if(friendName == null){
                    sArea.append("������Ϣ����Ϊ�գ�����" + "\n");
                } else if (friendName.equals(myName)) {
                    sArea.append("������Ϣ����Ϊ���ˣ�����" + "\n");
                } else {
                    //������Ϣ!
                    SendMessage.send(content, channel, myName, friendName);
                    sArea.append(content + "\n");
                }
            } else {
                sArea.append("������Ϣ����Ϊ�գ�����" + "\n");
            }
            jt.setText("");//����ı���������
        }
    }

    public void startTalk(String serverIp, int serverPort) throws Exception {
        bootstrap.group(work)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        ChannelPipeline pipeline = nioSocketChannel.pipeline();
                        pipeline.addLast(new ChatMessageDecoder());
                        pipeline.addLast(new ChatMessageEncoder());
                        pipeline.addLast(new ClientChannelHandler());
                    }
                });
        ChannelFuture channelFuture = bootstrap.connect(serverIp, serverPort).sync();
        channel = channelFuture.channel();
        SendMessage.upLine(channel,myName);
    }

    public static void main(String[] args) throws Exception {
        ChatWindow chatWindow = new ChatWindow("����","���鼪");
        ClientChannelHandler.setChannelViews(chatWindow.channel,chatWindow);
        chatWindow.setVisible(true);
    }
}