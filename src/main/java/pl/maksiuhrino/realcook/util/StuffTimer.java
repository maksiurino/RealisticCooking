package pl.maksiuhrino.realcook.util;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;

public class StuffTimer implements ServerTickEvents.EndTick {
    public static final StuffTimer INSTANCE = new StuffTimer();
    private long ticksUntilSomething;
    private Runnable runnable;
    public void setTimer(long ticksUntilSomething, Runnable runnable) {
        this.ticksUntilSomething = ticksUntilSomething;
        this.runnable = runnable;
    }
    @Override
    public void onEndTick(MinecraftServer server) {
        if (--this.ticksUntilSomething == 0L) {
            this.runnable.run();
        }
    }

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(INSTANCE);
    }
}
