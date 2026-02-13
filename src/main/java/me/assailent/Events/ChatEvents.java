package me.assailent.Events;

import me.assailent.Components.WorldbuildComponent;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.math.vector.Vector3d;
//import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.event.events.player.AddPlayerToWorldEvent;
import com.hypixel.hytale.server.core.event.events.player.PlayerChatEvent;
import com.hypixel.hytale.server.core.event.events.player.PlayerDisconnectEvent;
import com.hypixel.hytale.server.core.event.events.player.PlayerReadyEvent;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import me.assailent.Utilities.Config;
import me.assailent.WorldbuildersChat;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ChatEvents {

    private final WorldbuildersChat plugin;

    public ChatEvents(WorldbuildersChat plugin) { this.plugin = plugin; }

    private void putWordbuildComponent(@Nonnull Ref<EntityStore> ref, @Nonnull Store<EntityStore> store) {
//        Player player = (Player) store.getComponent(ref, Player.getComponentType());

        if (store.getComponent(ref, plugin.getWorldbuildComponent()) == null) {
            WorldbuildComponent worldbuildComp =  new WorldbuildComponent();
            worldbuildComp.channel = "Global";
            store.putComponent(ref, plugin.getWorldbuildComponent(), worldbuildComp);
        } else {
            WorldbuildComponent worldbuildComp =  new WorldbuildComponent();
            worldbuildComp.channel = "Global";
        }
    }

    public void onAddPlayerToWorld(AddPlayerToWorldEvent event) {
        event.setBroadcastJoinMessage(false);
    }

    public void onPlayerReady(PlayerReadyEvent event) {
        // Player Join Event
        putWordbuildComponent(event.getPlayerRef(), event.getPlayerRef().getStore());

        Config config = plugin.getConfig();
        if (!config.getClientJoinMessageEnabled() && !config.getServerJoinMessageEnabled()) return;

        Store<EntityStore> store = event.getPlayerRef().getStore();
        PlayerRef playerRef = store.getComponent(event.getPlayerRef(), PlayerRef.getComponentType());
        if (playerRef == null) return;

        Map<String, String> map = new HashMap<>();
        map.put("%player", playerRef.getUsername());

        if (config.getClientJoinMessageEnabled()) {
            playerRef.sendMessage(Message.raw(
                    format(config.getClientJoinMessage(), map)
            ));
        }

        if (config.getServerJoinMessageEnabled()) {
            for (PlayerRef ref : Universe.get().getPlayers()) {
                if (ref.getUuid() == playerRef.getUuid()) continue;

                ref.sendMessage(Message.raw(
                        format(config.getServerJoinMessage(), map)
                ));
            }
        }
    }

    public void onPlayerDisconnected(PlayerDisconnectEvent event) {
        // Player Disconnect Event
        Config config = plugin.getConfig();
        if (!config.getServerDisconnectedMessageEnabled()) return;

        PlayerRef playerRef = event.getPlayerRef();

        Map<String, String> map = new HashMap<>();
        map.put("%player", playerRef.getUsername());

        for (PlayerRef ref : Universe.get().getPlayers()) {
            if (ref.getUuid() == playerRef.getUuid()) continue;

            ref.sendMessage(Message.raw(
                    format(config.getServerDisconnectedMessage(), map)
            ));
        }
    }

    public void onPlayerChatEvent(PlayerChatEvent event) {
        // Player Chat Event
        Config config = plugin.getConfig();

        Store<EntityStore> store = event.getSender().getReference().getStore();
        PlayerRef playerRef = event.getSender();

        String content = moderateChatMessage(event.getContent(), event.getSender().getUsername());

        event.getTargets().clear();

        Map<String, String> map = new HashMap<>();
        map.put("%player", playerRef.getUsername());
        map.put("%content", content);

        Universe.get().getWorld(playerRef.getWorldUuid()).execute(() -> {
            WorldbuildComponent worldbuildComponent = store.ensureAndGetComponent(playerRef.getReference(), plugin.getWorldbuildComponent());
            map.put("%prefix", worldbuildComponent.getPrefix());
            map.put("%suffix", worldbuildComponent.getSuffix());

            if (worldbuildComponent.getChannel().equals("Global")) {
                plugin.getLogging().log("Global", playerRef.getUsername(),  format(config.getGlobalChatFormat(), map));
                for (PlayerRef playerRef1 : Universe.get().getPlayers()) {
                    Store<EntityStore> store1 = playerRef1.getReference().getStore();
                    WorldbuildComponent worldbuildComponent1 = store1.ensureAndGetComponent(playerRef1.getReference(), plugin.getWorldbuildComponent());
                    if (worldbuildComponent1.getGlobalMute()) continue;

                    playerRef1.sendMessage(Message.raw(
                            format(config.getGlobalChatFormat(), map)
                    ));
                }
            } else if (worldbuildComponent.getChannel().equals("Local")) {
                Integer maxDistance = config.getLocalChannelRange();

                String formatstring = config.getLocalChatFormat();
                if (worldbuildComponent.getShout()) {
                    maxDistance = config.getShoutRange();
                    formatstring = config.getShoutRangeFormat();
                    map.remove("%content");
                    map.put("%content", content.toUpperCase());
                    worldbuildComponent.shout = false;
                }

                if (worldbuildComponent.getWhisper()) {
                    maxDistance = config.getWhisperRange();
                    formatstring = config.getWhisperRangeFormat();
                    map.remove("%content");
                    map.put("%content", content.toLowerCase());
                    worldbuildComponent.whisper = false;
                }

                Integer maxDistanceSq = maxDistance * maxDistance;
                var senderEntity = playerRef.getReference();
                TransformComponent senderTransform = store.getComponent(senderEntity, TransformComponent.getComponentType());
                if (senderTransform == null) return;

                Vector3d senderPos = senderTransform.getPosition();

                plugin.getLogging().log("Local", playerRef.getUsername(),  format(config.getGlobalChatFormat(), map));

                for (PlayerRef playerRef1 : Universe.get().getPlayers()) {
                    if (playerRef1.getUuid() == playerRef.getUuid()) {
                        playerRef.sendMessage(Message.raw(format(config.getGlobalChatFormat(), map)));
                        continue;
                    }

                    if (!playerRef.getWorldUuid().equals(playerRef1.getWorldUuid())) continue;

                    Store<EntityStore> store1 = playerRef1.getReference().getStore();
                    TransformComponent targetTransform = store1.getComponent(playerRef1.getReference(), TransformComponent.getComponentType());
                    if (targetTransform == null) continue;

                    Vector3d targetPos = targetTransform.getPosition();

                    double distance = senderPos.distanceSquaredTo(targetPos);

                    if (distance > maxDistanceSq) continue;

                    map.put("%distance", String.valueOf(Math.round(Math.sqrt(distance))));

                    playerRef1.sendMessage(Message.raw(
                            format(formatstring, map)
                    ));
                }
            }
        });
    }

    public String moderateChatMessage(String message, String username) {
        Config config = plugin.getConfig();
        String[] replaceSentence = config.getSentenceReplace();
        String returnMessage = message;
        for (String word : config.getBlacklistWords()) {
            if (message.contains(word)) {
                Random rand = new Random();
                int index = rand.nextInt(replaceSentence.length);
                returnMessage = replaceSentence[index];
                plugin.getLogging().log("Swear", username, message);
                return returnMessage;
            }
        }
        for (String word : config.getAstrixWords()) {
            if (message.contains(word)) {
                plugin.getLogging().log("Swear", username, message);
                returnMessage = returnMessage.replace(word, "*%!#");
            }
        }
        return returnMessage;
    }

    public String format(String message, Map<String, String> args) {
        String newstring = message;
        for (Map.Entry<String, String> entry : args.entrySet()) {
            if (entry.getValue() == null) {
                newstring = newstring.replace(entry.getKey(), "");
            } else {
                newstring = newstring.replace(entry.getKey(), entry.getValue());
            }
        }
        return newstring;
    }
}
