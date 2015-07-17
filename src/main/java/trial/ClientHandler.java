package trial;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import query.Request;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    volatile ChannelHandlerContext ctx = null;

    @Override
    public void channelActive(final ChannelHandlerContext ctx) { // (1)
        this.ctx = ctx;
    }

    public void channelWrite(Request request)
    {
        byte[] bA = request.toString().getBytes();
        ByteBuf m1 = ctx.alloc().directBuffer(bA.length).writeBytes(bA);
        ctx.writeAndFlush(m1);

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf m = (ByteBuf) msg; // (1)

        while (m.isReadable()) { // (1)
            System.out.print((char) m.readByte());
        }
        System.out.println();
        System.out.flush();
        m.release();

   /*     try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        ByteBuf m1 = ctx.alloc().directBuffer(128).writeBytes(new Request("Hello World from client").toString().getBytes());
            ctx.writeAndFlush(m1); */
 }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}