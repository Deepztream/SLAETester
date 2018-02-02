package com.abysmal.slaetester;

import com.abysmal.slae.SLAE;
import com.abysmal.slae.Version;
import com.abysmal.slae.message.Message;
import com.abysmal.slae.message.MessageBus;

public class SLAETester {

    public static void main(String[] args) {
        System.out.println("SLAE Version " + Version.getVersion());
        MessageBus b = MessageBus.getBus();

        for(int i = 0; i < 10; i++)
            b.postMessage(new Message("Hello!", i));

        SLAE.initialise();

        b.addSystem((m) -> System.out.println((int)m.getData()));

         for(int i = 0; i < 6; i++)
            b.pushMessage();
    }
}