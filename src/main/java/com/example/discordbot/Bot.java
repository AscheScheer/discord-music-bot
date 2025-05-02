package com.example.discordbot;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;

public class Bot extends ListenerAdapter {

    private final MusicManager musicManager;

    public Bot() {
        this.musicManager = new MusicManager();
    }

    public static void main(String[] args) throws LoginException {
        if (args.length < 1) {
            System.out.println("Please provide the Discord bot token as the first argument.");
            System.exit(1);
        }
        String token = args[0];

        JDABuilder builder = JDABuilder.createDefault(token,
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.GUILD_VOICE_STATES,
                GatewayIntent.MESSAGE_CONTENT);

        builder.disableCache(CacheFlag.ACTIVITY, CacheFlag.EMOTE, CacheFlag.CLIENT_STATUS);

        Bot bot = new Bot();
        builder.addEventListeners(bot);

        builder.build();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        String raw = event.getMessage().getContentRaw();
        if (!raw.startsWith("!play ")) return;

        String url = raw.substring(6).trim();
        if (url.isEmpty()) {
            event.getChannel().sendMessage("Please provide a YouTube or Spotify URL to play.").queue();
            return;
        }

        Member member = event.getMember();
        if (member == null) {
            event.getChannel().sendMessage("Could not get your member info.").queue();
            return;
        }

        GuildVoiceState voiceState = member.getVoiceState();
        if (voiceState == null || !voiceState.inAudioChannel()) {
            event.getChannel().sendMessage("You need to be in a voice channel to play music.").queue();
            return;
        }

        VoiceChannel voiceChannel = (VoiceChannel) voiceState.getChannel();
        if (voiceChannel == null) {
            event.getChannel().sendMessage("Could not get your voice channel.").queue();
            return;
        }

        musicManager.loadAndPlay(event.getGuild(), voiceChannel, url, (TextChannel) event.getChannel());
    }
}
