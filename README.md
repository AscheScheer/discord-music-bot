# try make Discord Music Bot with Blackbox.ai

This is a Java Discord bot that can play music from YouTube or Spotify URLs using JDA and LavaPlayer. The bot is built as a Maven project and packaged as a fat .jar file for easy deployment on a VPS.

## Features

- Play music from YouTube and Spotify links
- Queue management for tracks and playlists
- Simple command interface (`!play <url>`)
- Connects to voice channels and streams audio

## Requirements

- Java 17 or higher
- Maven 3.6 or higher
- A Discord bot token (create a bot at https://discord.com/developers/applications)

## Setup and Build

1. Clone or download the project files.

2. Build the project and create a fat jar:

```bash
mvn clean package
```

The output jar will be located at `target/discord-music-bot-1.0-SNAPSHOT.jar`.

## Running the Bot

Run the bot with your Discord bot token as the first argument:

```bash
java -jar target/discord-music-bot-1.0-SNAPSHOT.jar YOUR_BOT_TOKEN
```

Replace `YOUR_BOT_TOKEN` with your actual Discord bot token.

## Usage

- Join a voice channel in your Discord server.
- In a text channel, type:

```
!play <YouTube or Spotify URL>
```

The bot will join your voice channel and start playing the requested music.

## Notes

- Spotify playback requires the URL to be a track or playlist URL. LavaPlayer handles Spotify links by searching YouTube for the corresponding audio.
- Make sure your bot has permissions to connect and speak in voice channels.
- This bot uses LavaPlayer and JDA libraries.

## License

This project is provided as-is without warranty.
