package org.netty.part04;

import io.netty.channel.Channel;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorServiceDemo {

    public static void main(String[] args) {
        //java原生api实现任务延迟调用
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);
        executorService.schedule(() -> System.out.println("我执行啦......"),10, TimeUnit.SECONDS);
        executorService.shutdown();

    }


    {
        //2.netty的EventLoop实现调度任务
        Channel ch = null;
        //延迟60s执行
        ch.eventLoop().schedule(() -> System.out.println("60 seconds later"),60,TimeUnit.SECONDS);
        //在60s后周期性的运行
        ch.eventLoop().scheduleAtFixedRate(() -> System.out.println("60 seconds later"),60,60,TimeUnit.SECONDS);
    }

}
