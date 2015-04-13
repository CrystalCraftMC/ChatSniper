ChatSniper
==========

## What is it?
ChatSniper is how you extract any group of messages from your server's entire chat history by customizing a search in a
chest GUI. You may also upload your existing log files into the automatically generated Sqlite database (MySQL
optional). Rest assured, the plugin wont cause any lag as all of it's dealings utilize multi-threading.

## How do searches work?
- **Time Frame**: From any year To any second. Very intuitive to configure in the GUI. The start time and end time
values will always default to the current year and the current hour of the server's time zone
- **Messages By Certain Players**: Specify messages from specific players to search for
- **UUID Support**: When searching for messages of certain players, have all messages shown by their UUID, regardless of
name change (UUID not recorded with messages synced with .gz log files)
- **Organized Results**: Have search results ordered by time, going either forwards or backwards
- **Limit Amount Of Results**: Cap the search results at a certain amount
- **Search For messages, commands, or both**
- **Have search results displayed either in books or as chat messages**
- **Look for keywords in your search**


**Note**: *The database updates after every 15 seconds.*

## Commands
- `/querydownload`
    - Cycles through all of your server's .gz log files and uploads the names / messages and times into
the database ( *Warning*: May take upwards of a few hours if you have a lot of files - no lag; handled in separate
thread )
- `/query`
    - Opens the Chest Graphical User Interface
- `/query <player name>`
    - Opens the Chest GUI, searching for a specific player
- `/query <player name | all> <keyword>`
    - Opens the Chest GUI, searching for a certain keyword (and specific player if
specified)
- `/queryperms <add | remove> <player name>`
    - Gives permission to use `/query` and `/queryperms` commands to non-OPs
- `/querybow`
    - Spawns in a bow that shows the last 5 messages sent of the person hit
- `/queryhelp`
    - Provides a link to the resource page
- `/queryListen <true/false>`
    - Toggle whether messages are currently being recorded or not

## Permissions
OPs have full permissions. To give perms to individuals, it's done with the `/queryperms <add | remove> <player name>`
command and the data is autonomously saved to a file.

## MySQL Configuration
Everything is automatic in creating the SQLite database in your local folder, then connecting to it. For those who wish
to use MySQL, a file is generated upon first loading the plugin, called *DatabaseLogin.txt* inside the folder
**ChatSniperFiles**.

In that file, turn SQLite to `false`, then fill out the rest of the fields. Next time your server reloads, it will be
linked to you MySQL database.

## [YouTube Video Review](https://youtu.be/mdGoPdzmQmY)