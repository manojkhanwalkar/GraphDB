package trial;

import io.netty.buffer.ByteBuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import query.Response;

/**
 * Handles a server-side channel.
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelActive(final ChannelHandlerContext ctx) {



    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
      ByteBuf in = (ByteBuf) msg;


            while (in.isReadable()) { // (1)
                System.out.print((char) in.readByte());
            }

        System.out.println();
        System.out.flush();

        in.release();

       // ByteBuf m1 = ctx.alloc().directBuffer(128).writeBytes(new Response("Hello World from server ").toString().getBytes());
        //ctx.writeAndFlush(m1);




    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}