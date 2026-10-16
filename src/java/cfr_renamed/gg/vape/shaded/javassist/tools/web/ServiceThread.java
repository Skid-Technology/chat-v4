/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.javassist.tools.web;

import gg.vape.shaded.javassist.tools.web.Webserver;
import java.io.IOException;
import java.net.Socket;

class ServiceThread
extends Thread {
    Webserver web;
    Socket sock;

    public ServiceThread(Webserver w, Socket s) {
        this.web = w;
        this.sock = s;
    }

    @Override
    public void run() {
        try {
            this.web.process(this.sock);
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }
}

