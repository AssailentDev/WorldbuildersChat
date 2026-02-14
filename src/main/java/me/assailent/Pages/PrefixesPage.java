package me.assailent.Pages;

import me.assailent.Components.WorldbuildComponent;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.packets.interface_.CustomPageLifetime;
import com.hypixel.hytale.protocol.packets.interface_.CustomUIEventBindingType;
import com.hypixel.hytale.server.core.entity.entities.player.pages.InteractiveCustomUIPage;
import com.hypixel.hytale.server.core.permissions.PermissionsModule;
import com.hypixel.hytale.server.core.ui.builder.EventData;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.ui.builder.UIEventBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import me.assailent.Utilities.Config;
import me.assailent.Utilities.Formatting;
import me.assailent.WorldbuildersChat;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.PrefixNode;
import net.luckperms.api.platform.PlayerAdapter;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import java.util.ArrayList;

public class PrefixesPage extends InteractiveCustomUIPage<PrefixesPage.PrefixNameData> {

    public static class PrefixNameData {
        public String prefix;

        public static final BuilderCodec<PrefixNameData> CODEC = BuilderCodec.builder(PrefixNameData.class, PrefixNameData::new)
                .append(new KeyedCodec<>("Prefix", BuilderCodec.STRING),
                        (obj, val) -> obj.prefix = val,
                        (obj) -> obj.prefix).add()
                .build();
    }

    private final WorldbuildersChat plugin;

    public PrefixesPage(@NonNullDecl PlayerRef playerRef, WorldbuildersChat plugin) {
        super(playerRef, CustomPageLifetime.CanDismissOrCloseThroughInteraction, PrefixNameData.CODEC);
        this.plugin = plugin;
    }

    @Override
    public void build(@NonNullDecl Ref<EntityStore> ref, @NonNullDecl UICommandBuilder uiCommandBuilder, @NonNullDecl UIEventBuilder uiEventBuilder, @NonNullDecl Store<EntityStore> store) {
        Config config = plugin.getConfig();
        uiCommandBuilder.append("Pages/Prefixes.ui");

        ArrayList<String> prefixes = new ArrayList<>();
        for (String prefix : config.getPrefixes()) {
            boolean canUse = PermissionsModule.get().hasPermission(playerRef.getUuid(), "WorldbuildersChat.worldbuilderschat.prefix." + prefix);
            if (!canUse) continue;

            prefixes.add(prefix);
        }

        int i = 0;
        int x = 0;
        for (String prefix : prefixes) {
            String group = "";
            if ((i+1) % 3 == 1) {
                group = "#Prefix1";
            } else if ((i+1) % 3 == 2) {
                group = "#Prefix2";
            } else {
                group = "#Prefix3";
            }

            String selector = group + "[" + x + "]";

            Formatting formatting = new Formatting();
            String cleanPrefix = formatting.removeTinyMsg(prefix);

            uiCommandBuilder.append(group, "Pages/PrefixEntry.ui");
            uiCommandBuilder.set(selector + " #UseButton" + ".Text", cleanPrefix);


            uiEventBuilder.addEventBinding(CustomUIEventBindingType.Activating,
                    selector + " #UseButton",
                    new EventData().append("Prefix", prefix),
                    false);

            i++;
            if ((i) % 3 == 0) {
                x++;
            }
        }
    }

    @Override
    public void handleDataEvent(Ref<EntityStore> ref, Store<EntityStore> store, PrefixNameData data) {
        if (PermissionsModule.get().hasPermission(playerRef.getUuid(), "worldbuilderschat.prefix." + data.prefix)) {
            WorldbuildComponent worldbuildComponent = store.getComponent(ref, plugin.getWorldbuildComponent());
            worldbuildComponent.prefix = data.prefix;
            LuckPerms lp = LuckPermsProvider.get();
            PlayerAdapter playerAdapter = lp.getPlayerAdapter(PlayerRef.class);
            User user = playerAdapter.getUser(playerRef);
            for (Node node : user.getNodes(NodeType.PREFIX)) {
                user.data().remove(node);
            }
            PrefixNode node = PrefixNode.builder(data.prefix, 100).build();
            user.data().add(node);
            close();
        }
    }
}