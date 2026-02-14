# WorldbuildersChat
## A Hytale plugin by AssailentDev
A worldbuilding chat plugin for Hytale. Featuring distanced based chats, luckperms support and more!

## Dependencies
### Hard Dependencies
N/A
### Soft Dependencies
* **Luckperms Hytale** luckperms.net
* **TinyMsg** github.com/Zoltus/TinyMessage

## Features
-- Note All of these features are *configurable*

* **Join Messages**
* **Leave Messages**
* **Global Chat** - Formattable (Set as your chat with /global)
* **Global Mute** - Ignore pesky busy chats with /globalmute
* **Local Chat** - Custom Range and Format (Set as your chat with /local)
* **Shouting** - Larger range than local chat!
* **Whisper** - Shorter range than local chat!
* **Swear Filter**
* * Replace certain swear words with a default tag ("!#*$")
* * Replace other words with a full sentence just like Hypixel
* **Prefixes**
* * A custom menu with the command /prefix -- this also works with luckperm prefixes and suffixes!
* * Settable prefixes that work in chat aswell as with luckperms!
* **_Advanced_ Logging**
* * Ensure that no players misdeed goes unnoticed! 
* * Every message sent within global and local are logged into _seperate_ text files! - There's also a seperate file for swear words to ensure propper moderation!

## Commands
/global - All your next messages will be in global chat! \
/globalmute - You will no longer see messages in global chat! \
/local - All your next messages will be in local chat! \
/shout - Your NEXT message will be shouted! (Increased Range) \
/whisper - Your NEXT message will be whispered! (Decreased range) \
/prefix - Choose your prefix from a list in a custom menu!

## Config.json
```json
{
  "ClientJoinMessageEnabled": true,
  "ClientJoinMessage": "Welcome %player",
  "ServerJoinMessageEnabled": true,
  "ServerJoinMessage": "Welcome %player to the server guys!",
  "ServerDisconnectedMessageEnabled": true,
  "ServerDisconnectedMessage": "Say goodbye to %player guys!",
  "GlobalChatFormat": "[%prefix] %player %suffix | %content",
  "LocalChatFormat": "[%distance] [%prefix] %player %suffix | %content",
  "ShoutFormat": "[%distance] [%prefix] %player %suffix | %content",
  "WhisperFormat": "[%distance] [%prefix] %player %suffix | %content",
  "AlreadyInGlobal": "%player You're already in the global channel!",
  "AlreadyInLocal": "%player You're already in the local channel!",
  "MuteGlobal": "You muted global chat",
  "UnMuteGlobal": "%player You unmuted global chat",
  "JoinGlobal": "%player You joined global!",
  "JoinLocal": "%player You joined local!",
  "StartShouting": "%player You are now shouting!",
  "StartWhispering": "%player You are now whispering!",
  "ChannelsEnabled": true,
  "LocalChannelRange": 30,
  "ShoutChannelRange": 60,
  "WhisperChannelRange": 15,
  "SentenceReplace": [
    "I love fluffy kittens!",
    "Bananas are taking over the world!",
    "Did someone say cookies?!",
    "I accidentally hugged a cactus!",
    "Beep boop, I am a toaster!",
    "I like trains!",
    "The ducks are plotting again...",
    "I stubbed my toe on a cloud!",
    "My spaghetti is watching me...",
    "I speak fluent potato!",
    "The cheese has escaped containment!",
    "I tripped over a pixel!",
    "My pet rock is disappointed in me.",
    "I forgot how doors work!",
    "The llamas demand snacks!",
    "I have been bamboozled!",
    "My brain just did a backflip!",
    "I blame lag.",
    "That was not very cash money of me.",
    "The penguins are judging us.",
    "I require emotional support pizza.",
    "Task failed successfully!",
    "I dropped my sandwich!",
    "My keyboard needs a hug.",
    "I have alerted the jellybeans.",
    "This is a certified bruh moment.",
    "The waffles are revolting!",
    "I accidentally the whole thing.",
    "My WiFi sneezed.",
    "The goblins stole my homework!",
    "I am one with the potatoes.",
    "The void is calling… collect.",
    "I need more duct tape!",
    "My brain has left the server.",
    "The squirrels know too much.",
    "I rolled a natural 1.",
    "My cat walked on my keyboard!",
    "I require more snacks to function.",
    "The hamster union is on strike!",
    "I have been outplayed by bread."
  ],
  "AstrixWords": [
    "crud",
    "CussWord!"
  ],
  "BlacklistWords": [
    "SwearWord!",
    ".com",
    ".org",
    "www.",
    "https://",
    "http://"
  ],
  "PrefixesEnabled": true,
  "Prefixes": [
    "Knight",
    "Runner",
    "More",
    "<color:blue>Miner<reset>"
  ]
}
```

P.S. If you have any feature requests please let me know either here or on my discord (@mini_assailent).