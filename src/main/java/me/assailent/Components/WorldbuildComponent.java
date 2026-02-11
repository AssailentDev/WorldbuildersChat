package me.assailent.Components;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class WorldbuildComponent implements Component<EntityStore> {

    public String prefix;
    public String suffix;
    public String channel;
    public Boolean globalMute;

    public Boolean shout;
    public Boolean whisper;

    public WorldbuildComponent() {
        this.prefix = "";
        this.suffix = "";
        this.channel = "Global";
        this.globalMute = false;
        this.shout = false;
        this.whisper = false;
    }

    public WorldbuildComponent(WorldbuildComponent clone) {
        this.prefix = clone.prefix;
        this.suffix = clone.suffix;
        this.channel = clone.channel;
        this.globalMute = clone.globalMute;
        this.shout =  clone.shout;
        this.whisper = clone.whisper;
    }

    public static final BuilderCodec<WorldbuildComponent> CODEC = BuilderCodec.builder(WorldbuildComponent.class, WorldbuildComponent::new)
            .append(new KeyedCodec<>("Prefix", Codec.STRING),
                    (data, value) -> data.prefix = value,
                    data -> data.prefix).add()
            .append(new KeyedCodec<>("Suffix", Codec.STRING),
                    (data, value) -> data.suffix = value,
                    data -> data.suffix).add()
            .append(new KeyedCodec<>("Channel", Codec.STRING),
                    (data, value) -> data.channel = value,
                    data -> data.channel).add()
            .append(new KeyedCodec<>("GlobalMute", Codec.BOOLEAN),
                    (data, value) -> data.globalMute = value,
                    data -> data.globalMute).add()
            .append(new KeyedCodec<>("Shout", Codec.BOOLEAN),
                    (data, value) -> data.shout = value,
                    data -> data.globalMute).add()
            .append(new KeyedCodec<>("Whisper", Codec.BOOLEAN),
                    (data, value) -> data.whisper = value,
                    data -> data.globalMute).add()
            .build();

    @NullableDecl
    @Override
    public Component<EntityStore> clone() {
        return new WorldbuildComponent(this);
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public String getChannel() {
        return this.channel;
    }

    public Boolean getGlobalMute() {
        return this.globalMute;
    }

    public Boolean getShout() {
        return this.shout;
    }

    public Boolean getWhisper() {
        return this.whisper;
    }
}
