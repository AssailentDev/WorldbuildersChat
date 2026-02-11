package me.assailent.Command;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import me.assailent.Components.WorldbuildComponent;
import me.assailent.WorldbuildersChat;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class GlobalMute extends AbstractPlayerCommand {

    private final WorldbuildersChat plugin;

    public GlobalMute(WorldbuildersChat plugin) {
        super("globalmute", "Mute the global chat!");
        addAliases("gm", "muteglobal");
        this.plugin = plugin;
    }

    @Override
    protected void execute(@NonNullDecl CommandContext commandContext, @NonNullDecl Store<EntityStore> store, @NonNullDecl Ref<EntityStore> ref, @NonNullDecl PlayerRef playerRef, @NonNullDecl World world) {
        world.execute(() -> {
           WorldbuildComponent worldBuildComponent = store.getComponent(playerRef.getReference(), plugin.getWorldbuildComponent());
           worldBuildComponent.globalMute = !worldBuildComponent.getGlobalMute();
        });
    }
}
