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
        //链接聊天服务器
        startTalk(serverIp,serverPort);
        //1.窗口
        this.setTitle(title);
        this.setLayout(new BorderLayout());
        this.setSize(600, 800);
        this.setLocation(300, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        //创建文本域
        sArea = new JTextArea(25, 20);
        //2.带滚动条的面板容器
        JScrollPane scrollPane = new JScrollPane(sArea);
        sArea.setEditable(false);//文本域不可编辑
        //3创建中间容器，放3各组件

        //--------------------------------------设置好友姓名---------------------------------------
        JPanel jpFriend = new JPanel();
        JButton jbFriend = new JButton("确认好友姓名");
        JTextField jtFriend = new JTextField(20);
        jbFriend.addActionListener(e -> {
            String content = jtFriend.getText();//获取输入的内容
            if (content != null && !content.trim().equals("")) {
                this.friendName = content;
            } else {
                sArea.append("好友信息不能为空！！！" + "\n");
                jtFriend.setText(this.friendName);
            }
        });
        //--------------------------------------输入发送内容---------------------------------------
        JPanel jp = new JPanel();
        JLabel jl = new JLabel("聊天信息");
        JTextField jt = new JTextField(20);
        jt.addActionListener(e->send(jt));
        JButton jb = new JButton("发送");
        jb.addActionListener(e -> send(jt));
        //--------------------------------------构建窗体---------------------------------------
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
            String content = jt.getText();//获取输入的内容
            if (content != null && !content.trim().equals("")) {
                if(friendName == null){
                    sArea.append("好友信息不能为空！！！" + "\n");
                } else if (friendName.equals(myName)) {
                    sArea.append("好友信息不能为本人！！！" + "\n");
                } else {
                    //发送信息!
                    SendMessage.send(content, channel, myName, friendName);
                    sArea.append(content + "\n");
                }
            } else {
                sArea.append("聊天信息不能为空！！！" + "\n");
            }
            jt.setText("");//清空文本框中内容
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
        ChatWindow chatWindow = new ChatWindow("聊天","卜祥吉");
        ClientChannelHandler.setChannelViews(chatWindow.channel,chatWindow);
        chatWindow.setVisible(true);
    }
}