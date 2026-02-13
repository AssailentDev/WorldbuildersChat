package me.assailent.Command;

import me.assailent.Pages.PrefixesPage;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import me.assailent.WorldbuildersChat;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class Prefix extends AbstractPlayerCommand {

    private final WorldbuildersChat plugin;

    public Prefix(WorldbuildersChat plugin) {
        super("prefix", "Command to set user prefixes/titles", false);
        this.plugin = plugin;
        addAliases("title");
    }

    @Override
    protected void execute(@NonNullDecl CommandContext commandContext, @NonNullDecl Store<EntityStore> store, @NonNullDecl Ref<EntityStore> ref, @NonNullDecl PlayerRef playerRef, @NonNullDecl World world) {
        Player player = store.getComponent(ref, Player.getComponentType());

        PrefixesPage page = new PrefixesPage(playerRef, plugin);

        player.getPageManager().openCustomPage(ref, store, page);
    }
}
