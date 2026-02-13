package me.assailent.Command;

import com.hypixel.hytale.server.core.Message;
import me.assailent.Components.WorldbuildComponent;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import me.assailent.Utilities.Config;
import me.assailent.WorldbuildersChat;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import java.util.HashMap;
import java.util.Map;

public class GlobalMute extends AbstractPlayerCommand {

    private final WorldbuildersChat plugin;

    public GlobalMute(WorldbuildersChat plugin) {
        super("globalmute", "Mute the global chat!");
        addAliases("gm", "muteglobal");
        this.plugin = plugin;
    }

    @Override
    protected void execute(@NonNullDecl CommandContext commandContext, @NonNullDecl Store<EntityStore> store, @NonNullDecl Ref<EntityStore> ref, @NonNullDecl PlayerRef playerRef, @NonNullDecl World world) {
        Config config = plugin.getConfig();
        Map<String, String> map = new HashMap<>();
        map.put("%player", playerRef.getUsername());

        world.execute(() -> {
           WorldbuildComponent worldbuildComponent = store.getComponent(playerRef.getReference(), plugin.getWorldbuildComponent());
           worldbuildComponent.globalMute = !worldbuildComponent.getGlobalMute();
           if (worldbuildComponent.globalMute) {
               playerRef.sendMessage(Message.raw(config.format(config.getMuteGlobal(), map)));
               if (worldbuildComponent.getChannel().equals("Local")) { return; }
               worldbuildComponent.channel = "Local";
           } else {
               playerRef.sendMessage(Message.raw(config.format(config.getUnMuteGlobal(), map)));
           }
        });
    }
}
