package nettyProxy;

import java.util.List;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.cors.CorsConfigBuilder;
import io.netty.handler.codec.http.cors.CorsHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class ChannelInit extends ChannelInitializer<SocketChannel> {

    private List<MyNode> nodes;
    int nodeIndex = 0;
    public ChannelInit(List<MyNode> nodes) {
    	this.nodes = nodes;
    }

    @Override
    public void initChannel(SocketChannel socketChannel) {
    	socketChannel.pipeline().addLast(
                new FrontendHandler(nodes.get(nodeIndex))
                );
    	nodeIndex++;
    	if(nodeIndex >= nodes.size()) {
    		nodeIndex =0;
    	}
    }
}
