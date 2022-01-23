package nettyProxy;

import java.util.ArrayList;
import java.util.List;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class Server {
    static final int LOCAL_PORT = 8080;
    static List<MyNode> nodes;
    
	public static void main(String[] args) {
		nodes = new ArrayList<MyNode>();
		//Nodes that the server is a proxy for:
		nodes.add(new MyNode("localhost", 8000));
		nodes.add(new MyNode("localhost", 8001));
		new Server().run();
	}
	
	private void run() {
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class)
             .childHandler(new ChannelInit(nodes))
             .childOption(ChannelOption.AUTO_READ, false)
             .bind(LOCAL_PORT);
            b.option(ChannelOption.AUTO_READ, false);
            
		}
		catch (Exception e) {
			workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
		}
	}
}
