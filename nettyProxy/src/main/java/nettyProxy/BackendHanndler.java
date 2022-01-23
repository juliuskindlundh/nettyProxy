package nettyProxy;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.SocketChannel;

public class BackendHanndler extends ChannelInboundHandlerAdapter {

	 private Channel inboundChannel;
	    public BackendHanndler(Channel inboundChannel) {
	        this.inboundChannel = inboundChannel;
	    }

	    @Override
	    public void channelActive(ChannelHandlerContext ctx) {
	        ctx.read();
	    }

	    @Override
	    public void channelRead(final ChannelHandlerContext ctx, Object msg) {
	        inboundChannel.writeAndFlush(msg).addListener(new ChannelFutureListener() {
	            public void operationComplete(ChannelFuture future) {
	                if (future.isSuccess()) {
	                    ctx.channel().close();
	                } else {
	                    future.channel().close();
	                }
	            }
	        });
	    }

	    @Override
	    public void channelInactive(ChannelHandlerContext ctx) {
	    	FrontendHandler.closeOnFlush(inboundChannel);
	    }

	    @Override
	    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
	        cause.printStackTrace();
	        FrontendHandler.closeOnFlush(ctx.channel());
	    }
}
