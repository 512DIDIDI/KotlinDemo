package com.dididi.kotlindemo.function;

import java.util.HashSet;
import java.util.Set;

/**
 * @author dididi(叶超)
 * @email yc512yc@163.com
 * @describe
 * @since 10/06/2020
 */
public class Event {
    interface EventListener{
        public void invoke();
    }

    private static Set<EventListener> sets = new HashSet<>();

    public static void addEventListener(EventListener listener){
        sets.add(listener);
    }

    public static void removeEventListener(EventListener listener){
        sets.remove(listener);
    }

    public static void addSamInterface(SamInterface listener){
        listener.invoke();
    }
}
