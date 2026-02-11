package me.assailent.Utilities;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;

import java.util.Map;

public class Config {
    public static final BuilderCodec<Config> CODEC = BuilderCodec.builder(Config.class, Config::new)
            .append(new KeyedCodec<>("ClientJoinMessageEnabled", Codec.BOOLEAN),
                    (config, value) -> config.clientJoinMessageEnabled = value,
                    (config) -> config.clientJoinMessageEnabled).add()
            .append(new KeyedCodec<>("ClientJoinMessage", Codec.STRING),
                    (config, value) -> config.clientJoinMessage = value,
                    (config) -> config.clientJoinMessage).add()
            .append(new KeyedCodec<>("ServerJoinMessageEnabled", Codec.BOOLEAN),
                    (config, value) -> config.serverJoinMessageEnabled = value,
                    (config) -> config.serverJoinMessageEnabled).add()
            .append(new KeyedCodec<>("ServerJoinMessage", Codec.STRING),
                    (config, value) -> config.serverJoinMessage = value,
                    (config) -> config.serverJoinMessage).add()
            .append(new KeyedCodec<>("ServerDisconnectedMessageEnabled", Codec.BOOLEAN),
                    (config, value) -> config.serverDisconnectedMessageEnabled = value,
                    (config) -> config.serverDisconnectedMessageEnabled).add()
            .append(new KeyedCodec<>("ServerDisconnectedMessage", Codec.STRING),
                    (config, value) -> config.serverDisconnectedMessage = value,
                    (config) -> config.serverDisconnectedMessage).add()

            .append(new KeyedCodec<>("GlobalChatFormat", Codec.STRING),
                    (config, value) -> config.globalChatFormat = value,
                    (config) -> config.globalChatFormat).add()
            .append(new KeyedCodec<>("LocalChatFormat", Codec.STRING),
                    (config, value) -> config.localChatFormat = value,
                    (config) -> config.localChatFormat).add()
            .append(new KeyedCodec<>("ShoutFormat", Codec.STRING),
                    (config, value) -> config.shoutRangeFormat = value,
                    (config) -> config.shoutRangeFormat).add()
            .append(new KeyedCodec<>("WhisperFormat", Codec.STRING),
                    (config, value) -> config.whisperRangeFormat = value,
                    (config) -> config.whisperRangeFormat).add()

            .append(new KeyedCodec<>("JoinGlobal", Codec.STRING),
                    (config, value) -> config.joinGlobal = value,
                    (config) -> config.joinGlobal).add()
            .append(new KeyedCodec<>("JoinLocal", Codec.STRING),
                    (config, value) -> config.joinLocal = value,
                    (config) -> config.joinLocal).add()
            .append(new KeyedCodec<>("StartShouting", Codec.STRING),
                    (config, value) -> config.startShouting = value,
                    (config) -> config.startShouting).add()
            .append(new KeyedCodec<>("StartWhispering", Codec.STRING),
                    (config, value) -> config.startWhispering = value,
                    (config) -> config.startWhispering).add()

            .append(new KeyedCodec<>("ChannelsEnabled", Codec.BOOLEAN),
                    (config, value) -> config.channelsEnabled = value,
                    (config) -> config.channelsEnabled).add()
            .append(new KeyedCodec<>("LocalChannelRange", Codec.INTEGER),
                    (config, value) -> config.localChannelRange = value,
                    (config) -> config.localChannelRange).add()
            .append(new KeyedCodec<>("ShoutChannelRange", Codec.INTEGER),
                    (config, value) -> config.shoutRange = value,
                    (config) -> config.shoutRange).add()
            .append(new KeyedCodec<>("WhisperChannelRange", Codec.INTEGER),
                    (config, value) -> config.whisperRange = value,
                    (config) -> config.whisperRange).add()

            .append(new KeyedCodec<>("SentenceReplace", Codec.STRING_ARRAY),
                    (config, value) -> config.sentenceReplace  = value,
                    (config) -> config.sentenceReplace).add()
            .append(new KeyedCodec<>("AstrixWords", Codec.STRING_ARRAY),
                    (config, value) -> config.astrixWords = value,
                    (config) -> config.astrixWords).add()
            .append(new KeyedCodec<>("BlacklistWords", Codec.STRING_ARRAY),
                    (config,  value) -> config.blacklistWords = value,
                    (config) -> config.blacklistWords).add()

            .append(new KeyedCodec<>("PrefixesEnabled", Codec.BOOLEAN),
                    (config, value) -> config.prefixesEnabled = value,
                    (config) -> config.prefixesEnabled).add()
            .append(new KeyedCodec<>("Prefixes", Codec.STRING_ARRAY),
                    (config, value) -> config.prefixes = value,
                    (config) -> config.prefixes).add()
            .build();

    private boolean clientJoinMessageEnabled = true;
    private String clientJoinMessage = "Welcome %player"; // Default Setting - %player for player
    private boolean serverJoinMessageEnabled = true;
    private String serverJoinMessage = "Welcome %player to the server guys!";

    private boolean serverDisconnectedMessageEnabled = true;
    private String serverDisconnectedMessage = "Say goodbye to %player guys!";

    private String globalChatFormat = "[%prefix] %player %suffix | %content"; // %prefix for prefix %player for player %suffix for suffix %content for content
    private String localChatFormat = "[%distance] [%prefix] %player %suffix | %content";
    private String shoutRangeFormat = "[%distance] [%prefix] %player %suffix | %content";
    private String whisperRangeFormat = "[%distance] [%prefix] %player %suffix | %content";

    private String joinGlobal = "%player You joined global!"; // %player for player
    private String joinLocal = "%player You joined local!";
    private String startShouting = "%player You are now shouting!"; // %player for player
    private String startWhispering = "%player You are now whispering!"; // %player for player

    private boolean channelsEnabled = true;
    private int localChannelRange = 30;
    private int shoutRange = 60;
    private int whisperRange = 15;

    private String[] sentenceReplace = {"I love fluffy kittens!", "Bananas are taking over the world!", "Did someone say cookies?!", "I accidentally hugged a cactus!", "Beep boop, I am a toaster!", "I like trains!", "The ducks are plotting again...", "I stubbed my toe on a cloud!", "My spaghetti is watching me...", "I speak fluent potato!", "The cheese has escaped containment!", "I tripped over a pixel!", "My pet rock is disappointed in me.", "I forgot how doors work!", "The llamas demand snacks!", "I have been bamboozled!", "My brain just did a backflip!", "I blame lag.", "That was not very cash money of me.", "The penguins are judging us.", "I require emotional support pizza.", "Task failed successfully!", "I dropped my sandwich!", "My keyboard needs a hug.", "I have alerted the jellybeans.", "This is a certified bruh moment.", "The waffles are revolting!", "I accidentally the whole thing.", "My WiFi sneezed.", "The goblins stole my homework!", "I am one with the potatoes.", "The void is calling… collect.", "I need more duct tape!", "My brain has left the server.", "The squirrels know too much.", "I rolled a natural 1.", "My cat walked on my keyboard!", "I require more snacks to function.", "The hamster union is on strike!", "I have been outplayed by bread."};
    private String[] astrixWords = {"crud", "CussWord!"}; // Words that just get *** like that
    private String[] blacklistWords = {"SwearWord!", ".com", ".org", "www.", "https://", "http://"}; // words that block a whole message

    private Boolean prefixesEnabled = true;
    private String[] prefixes = {"Knight", "Runner", "More", "Miner"};


    public Config() {} // Constructor

    public Boolean getClientJoinMessageEnabled() {
        return clientJoinMessageEnabled;
    }

    public String getClientJoinMessage() {
        return clientJoinMessage;
    }

    public Boolean getServerJoinMessageEnabled() {
        return serverJoinMessageEnabled;
    }

    public String getServerJoinMessage() {
        return serverJoinMessage;
    }

    public Boolean getServerDisconnectedMessageEnabled() {
        return serverDisconnectedMessageEnabled;
    }

    public String getServerDisconnectedMessage() {
        return serverDisconnectedMessage;
    }


    public String getGlobalChatFormat() {
        return globalChatFormat;
    }

    public String getLocalChatFormat() {
        return localChatFormat;
    }

    public String getShoutRangeFormat() {
        return shoutRangeFormat;
    }

    public String getWhisperRangeFormat() {
        return whisperRangeFormat;
    }


    public String getJoinGlobal() { return joinGlobal; }

    public String getJoinLocal() { return joinLocal; }

    public String getStartShouting() { return startShouting; }

    public String getStartWhispering() { return startWhispering; }


    public Boolean getChannelsEnabled() {
        return channelsEnabled;
    }

    public int getLocalChannelRange() {
        return localChannelRange;
    }

    public int getShoutRange() {
        return shoutRange;
    }

    public int getWhisperRange() {
        return whisperRange;
    }


    public String[] getSentenceReplace() { return sentenceReplace; }

    public String[] getAstrixWords() { return astrixWords; }

    public String[] getBlacklistWords() { return  blacklistWords; }


    public Boolean getPrefixesEnabled() { return prefixesEnabled; }

    public String[] getPrefixes() { return prefixes; }


    public String format(String message, Map<String, String> args) {
        String newstring = message;
        for (Map.Entry<String, String> entry : args.entrySet()) {
            newstring = newstring.replace(entry.getKey(), entry.getValue());
        }
        return newstring;
    }
}
