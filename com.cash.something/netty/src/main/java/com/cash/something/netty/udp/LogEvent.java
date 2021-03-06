package com.cash.something.netty.udp;

import java.net.InetSocketAddress;

/**
 * author cash
 * create 2019-03-31-15:59
 **/
public final class LogEvent {

    public static final byte SEPARATOR= (byte) ':';
    private final InetSocketAddress source;
    private final String logfile;
    private final String msg;
    private final long received;

    public LogEvent(String msg, String logfile) {
        this(null,msg,logfile,-1);
    }

    public LogEvent(InetSocketAddress source, String logfile, String msg, long received) {
        this.source = source;
        this.logfile = logfile;
        this.msg = msg;
        this.received = received;
    }

    public InetSocketAddress getSource() {
        return source;
    }

    public String getLogfile() {
        return logfile;
    }

    public String getMsg() {
        return msg;
    }

    public long getReceived() {
        return received;
    }
}
