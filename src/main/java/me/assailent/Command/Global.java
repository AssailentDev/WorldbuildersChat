package me.assailent.Command;

import me.assailent.Components.WorldbuildComponent;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import me.assailent.Utilities.Config;
import me.assailent.Utilities.Formatting;
import me.assailent.WorldbuildersChat;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import java.util.HashMap;
import java.util.Map;

public class Global extends AbstractPlayerCommand {

    private final WorldbuildersChat plugin;

    public Global(WorldbuildersChat plugin) {
        super("global", "Change your channel to be in the global channel!!");
        this.plugin = plugin;
    }

    @Override
    protected void execute(@NonNullDecl CommandContext commandContext, @NonNullDecl Store<EntityStore> store, @NonNullDecl Ref<EntityStore> ref, @NonNullDecl PlayerRef playerRef, @NonNullDecl World world) {
        Config config = plugin.getConfig();
        Map<String, String> map = new HashMap<>();
        map.put("%player", playerRef.getUsername());

        Formatting formatting = new Formatting();

        world.execute(() -> {
            WorldbuildComponent worldbuildComponent = store.getComponent(playerRef.getReference(), plugin.getWorldbuildComponent());
            if (worldbuildComponent.getChannel().equals("Global")) {
                playerRef.sendMessage(formatting.message(formatting.format(config.getAlreadyInGlobal(), map)));
                return;
            }
            if (worldbuildComponent.getGlobalMute()) {
                worldbuildComponent.globalMute = false;
            }

            worldbuildComponent.channel = "Global";
            playerRef.sendMessage(formatting.message((formatting.format(config.getJoinGlobal(), map))));
        });
    }
}

