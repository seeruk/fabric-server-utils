package dev.seeruk.fabricserverutils;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerUtilsMod implements ModInitializer {
    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("fabricserverutils");

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("memory").executes(context -> {
                var mebibyte = 2 ^ 20;
                var totalMemory = Runtime.getRuntime().totalMemory() / mebibyte;
                var freeMemory = Runtime.getRuntime().freeMemory() / mebibyte;
                var usedMemory = totalMemory - freeMemory;

                context.getSource().sendMessage(Text.literal(String.format(
                        "%d/%d (%d%%)",
                        usedMemory,
                        totalMemory,
                        100/totalMemory * usedMemory
                )));

                return 1;
            }));
        });
    }
}
