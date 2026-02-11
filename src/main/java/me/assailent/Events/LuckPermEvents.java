package me.assailent.Events;

import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import me.assailent.Components.WorldbuildComponent;
import me.assailent.WorldbuildersChat;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedPermissionData;
import net.luckperms.api.event.EventBus;
import net.luckperms.api.event.node.NodeAddEvent;
import net.luckperms.api.event.user.UserDataRecalculateEvent;
import net.luckperms.api.event.user.UserLoadEvent;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.PrefixNode;
import net.luckperms.api.platform.PlayerAdapter;
import net.luckperms.api.util.Tristate;

public class LuckPermEvents {
    private final WorldbuildersChat plugin;

    public LuckPermEvents(WorldbuildersChat plugin, LuckPerms luckPerms) {
        this.plugin = plugin;

        EventBus eventBus = luckPerms.getEventBus();

        eventBus.subscribe(this.plugin, NodeAddEvent.class, this::onNodeAdd);

        eventBus.subscribe(this.plugin, UserLoadEvent.class, this::onUserLoad);

        eventBus.subscribe(this.plugin, UserDataRecalculateEvent.class, this::onUserDataRecalculate);
    }

    private void onNodeAdd(NodeAddEvent event) {
        if (event.isGroup() && event.getNode().getType() == NodeType.PREFIX) {
            Group group = ((Group) event.getTarget());
            PrefixNode node = (PrefixNode) event.getNode();

            PlayerAdapter playerAdapter = LuckPermsProvider.get().getPlayerAdapter(PlayerRef.class);
            for (PlayerRef player : Universe.get().getPlayers()) {
                Universe.get().getWorld(player.getWorldUuid()).execute(() -> {
                    User user = playerAdapter.getUser(player);
                    if (user.getCachedData().getPermissionData().checkPermission("group." + group).equals(Tristate.TRUE)) {
                        Store<EntityStore> store = player.getReference().getStore();
                        WorldbuildComponent worldbuildComponent = store.ensureAndGetComponent(player.getReference(), plugin.getWorldbuildComponent());
                        worldbuildComponent.prefix = node.getKey().split(".")[-1];
                    }
                });
            }
        }
    }

    private void onUserLoad(UserLoadEvent event) {
        User user = event.getUser();
        for (Node node : user.getNodes()) {
            if (node.getType() == NodeType.PREFIX) {
                PrefixNode prefixNode = (PrefixNode) node;
                prefixNode.getKey();
                PlayerRef playerRef = Universe.get().getPlayer(user.getUniqueId());
                Universe.get().getWorld(playerRef.getWorldUuid()).execute(() -> {
                    Store<EntityStore> store = playerRef.getReference().getStore();
                    WorldbuildComponent worldbuildComponent = store.ensureAndGetComponent(playerRef.getReference(), plugin.getWorldbuildComponent());
                    worldbuildComponent.prefix = node.getKey().split(".")[-1];
                });
            }
        }
    }

    private void onUserDataRecalculate(UserDataRecalculateEvent event) {
        User user = event.getUser();
        PlayerRef playerRef = Universe.get().getPlayer(user.getUniqueId());
        Store<EntityStore> store = playerRef.getReference().getStore();
        Universe.get().getWorld(playerRef.getWorldUuid()).execute(() -> {
            WorldbuildComponent worldbuildComponent = store.ensureAndGetComponent(playerRef.getReference(), plugin.getWorldbuildComponent());
            worldbuildComponent.prefix = event.getData().getMetaData().getPrefix();
            worldbuildComponent.suffix = event.getData().getMetaData().getSuffix();
        });
    }
}
