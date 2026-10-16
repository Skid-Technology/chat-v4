/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.protocol;

import gg.vape.manager.client.OnlineConnectionManager;
import gg.vape.manager.client.OnlineConnectionState;
import gg.vape.protocol.ZeusClient;
import gg.vape.protocol.ZeusClientChannelInitializer;
import gg.vape.protocol.packet.HandshakeResponsePacket;
import gg.vape.protocol.packet.HandshakeStatus;
import gg.vape.ui.click.component.GuiComponent;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

public class ZeusConnectionManager {
    private final static ZeusConnectionManager var_gg_vape_protocol_ZeusConnectionManager_i;
    private boolean var_boolean_c = false;
    private final ZeusClient var_gg_vape_protocol_ZeusClient_t = new ZeusClient();
    private final static String var_java_lang_String_b;
    private static GuiComponent[] var_gg_vape_ui_click_component_GuiComponent_arr_v;

    public ZeusClient u() {
        return this.var_gg_vape_protocol_ZeusClient_t;
    }

    private void lambda$start$2(Thread thread, Runnable runnable, EventLoopGroup eventLoopGroup, Future future) throws Exception {
        thread.interrupt();
        this.var_boolean_c = false;
        ZeusConnectionManager.shutdownEventLoopGroup(eventLoopGroup);
        runnable.run();
    }

    public static ZeusConnectionManager T() {
        return var_gg_vape_protocol_ZeusConnectionManager_i;
    }

    public void V(Runnable runnable, Runnable runnable2) throws InterruptedException, IOException {
        this.connect(var_java_lang_String_b, runnable, runnable2);
    }

    void connect(String address, Runnable runnable, Runnable runnable2) throws InterruptedException, IOException {
        if (this.var_boolean_c) {
            return;
        }
        this.var_boolean_c = true;
        NioEventLoopGroup eventLoopGroup = null;
        try {
            String[] stringArray = address.split(":");
            String string = stringArray[0];
            int n = Integer.parseInt(stringArray[1]);
            InetSocketAddress inetSocketAddress = new InetSocketAddress(string, n);
            eventLoopGroup = new NioEventLoopGroup(1);
            Bootstrap bootstrap = (Bootstrap)((Bootstrap)((Bootstrap)new Bootstrap().group(eventLoopGroup)).channel(NioSocketChannel.class)).remoteAddress(inetSocketAddress).handler(new ZeusClientChannelInitializer(this));
            Channel channel = bootstrap.connect().sync().channel();
            Thread thread = new Thread(this::lambda$start$0);
            if (channel.isActive()) {
                this.var_gg_vape_protocol_ZeusClient_t.N(arg_0 -> ZeusConnectionManager.lambda$start$1(channel, runnable, thread, arg_0));
            }
            NioEventLoopGroup connectionEventLoopGroup = eventLoopGroup;
            channel.closeFuture().addListener((GenericFutureListener<? extends Future<? super Void>>)((GenericFutureListener<Future>)arg_0 -> this.lambda$start$2(thread, runnable2, connectionEventLoopGroup, arg_0)));
            eventLoopGroup = null;
        }
        catch (Throwable throwable) {
            this.var_boolean_c = false;
            ZeusConnectionManager.shutdownEventLoopGroup(eventLoopGroup);
            runnable2.run();
        }
    }

    private static void shutdownEventLoopGroup(EventLoopGroup eventLoopGroup) {
        if (eventLoopGroup != null) {
            eventLoopGroup.shutdownGracefully(0L, 5L, TimeUnit.SECONDS);
        }
    }

    private void lambda$start$0() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(1000L);
            }
            catch (InterruptedException interruptedException) {
                break;
            }
            this.var_gg_vape_protocol_ZeusClient_t.p();
        }
    }

    public static void d(GuiComponent[] guiComponentArray) {
        var_gg_vape_ui_click_component_GuiComponent_arr_v = guiComponentArray;
    }

    private static Throwable a(Throwable throwable) {
        return throwable;
    }

    public static GuiComponent[] r() {
        return var_gg_vape_ui_click_component_GuiComponent_arr_v;
    }

    private static void lambda$start$1(Channel channel, Runnable runnable, Thread thread, HandshakeResponsePacket handshakeResponsePacket) {
        if (handshakeResponsePacket.Q() != HandshakeStatus.MATCHING) {
            if (handshakeResponsePacket.Q() == HandshakeStatus.OUTDATED_CLIENT) {
                OnlineConnectionManager.INSTANCE.setConnectionState(OnlineConnectionState.OUTDATED_CLIENT);
            } else if (handshakeResponsePacket.Q() == HandshakeStatus.OUTDATED_SERVER) {
                OnlineConnectionManager.INSTANCE.setConnectionState(OnlineConnectionState.OUTDATED_SERVER);
            }
            channel.close().syncUninterruptibly();
            return;
        }
        runnable.run();
        thread.start();
    }

    public static ZeusClient m(ZeusConnectionManager zeusConnectionManager) {
        return zeusConnectionManager.var_gg_vape_protocol_ZeusClient_t;
    }

    static {
        ZeusConnectionManager.d(new GuiComponent[3]);
        String configuredAddress = System.getenv("VAPE_ZEUS_ADDRESS");
        var_java_lang_String_b = configuredAddress == null || configuredAddress.trim().isEmpty() ? "zeus-prod.vape.chat:8091" : configuredAddress.trim();
        var_gg_vape_protocol_ZeusConnectionManager_i = new ZeusConnectionManager();
    }
}

