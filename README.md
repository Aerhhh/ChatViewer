# ChatViewer
Allows players to view messages from other servers running this plugin.

**A Redis server is required!**

# Installation
- Download this plugin and drop it into your server's `plugins` folder.
- Start the server and a config file will be created at `plugins/ChatViewer/config.yml`
- Insert your Redis server information into the `auth` section of the config.
  - If applicable, uncomment the `username` and `password` options and set them to your Redis server's username and password.
- Edit the `serverId` to whatever you want the server to be named. For example, `minigame1` or `lobby`.
- Restart the server.

# Usage
The only command in this plugin is `/viewchat <serverId>`. You must specify a valid server ID that is also running this plugin, however there is no validation whether the other chat actually exists.

For example, `/viewchat lobby` will display player chat messages from your `lobby` server that is connected to the same Redis server.