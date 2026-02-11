package me.assailent;

import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.event.events.player.PlayerChatEvent;
import com.hypixel.hytale.server.core.event.events.player.PlayerDisconnectEvent;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.io.ServerManager;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.plugin.PluginType;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import me.assailent.Command.*;
import me.assailent.Components.WorldbuildComponent;
import me.assailent.Events.ChatEvents;
import me.assailent.Events.LuckPermEvents;
import me.assailent.Utilities.Config;
import me.assailent.Utilities.Logging;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class WorldbuildersChat extends JavaPlugin {
    public WorldbuildersChat(@NonNullDecl JavaPluginInit init) {
        super(init);
    }

    public static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

    private LuckPerms luckPerms;

    private final com.hypixel.hytale.server.core.util.Config<Config> config = this.withConfig("Config", Config.CODEC);

    private ComponentType<EntityStore, WorldbuildComponent> worldbuildComponent;

    private Logging logging;
    private ChatEvents chatEvents;

    @Override
    protected void setup() {
        super.setup();

        // Setup Logging
        logging = new Logging(this);
        chatEvents = new ChatEvents(this);

        // Config Setup
        config.save();

        // Commands Register
        Config config1 = this.getConfig();
        if (config1.getChannelsEnabled()) {
            this.getCommandRegistry().registerCommand(new Global(this));
            this.getCommandRegistry().registerCommand(new GlobalMute(this));
            this.getCommandRegistry().registerCommand(new Local(this));
            this.getCommandRegistry().registerCommand(new Shout(this));
            this.getCommandRegistry().registerCommand(new Whisper(this));
        }

        if (config1.getPrefixesEnabled()) {
            this.getCommandRegistry().registerCommand(new Prefix(this));
        }

        // Events Register
        this.getEventRegistry().registerGlobal(PlayerReadyEvent.class, chatEvents::onPlayerReady);
        this.getEventRegistry().registerGlobal(PlayerDisconnectEvent.class, chatEvents::onPlayerDisconnected);
        this.getEventRegistry().registerGlobal(PlayerChatEvent.class, chatEvents::onPlayerChatEvent);

        // Component Register
        this.worldbuildComponent = this.getEntityStoreRegistry().registerComponent(
                WorldbuildComponent.class,
                "WorldbuildComponent",
                WorldbuildComponent.CODEC
        );
    }

    @Override
    protected void start() {
        super.start();

        luckPerms = LuckPermsProvider.get();
        LuckPermEvents luckPermEvents = new LuckPermEvents(this, luckPerms);
    }

    public ComponentType<EntityStore, WorldbuildComponent> getWorldbuildComponent() { return this.worldbuildComponent; }

    public Logging getLogging() { return this.logging; }

    public Config getConfig() { return config.get(); }
}
