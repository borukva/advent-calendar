Simple server-side mod.
Mod utilizes sgui for gui and polymer for resource pack(for translations)

All time calculations done in Europe/Kyiv timezone

Config for rewards is required. Example can be found in repository  
&emsp;allowExpired -- whether or not to allow player to claim gifts of the past days they've missed  
&emsp;year -- year to check. if it differs in local time, no rewards will be available  
&emsp;month -- same as year  
&emsp;rewards:  
&emsp;&emsp;"N" -- day number of the month  
&emsp;&emsp;&emsp;"item" -- item identificator to give player  
&emsp;&emsp;&emsp;"count" -- amount of "item"  
&emsp;&emsp;&emsp;"lore" -- optional field to add some info to gift. translatable  

Mod stores info about claimed gifts in json file

Both json files are stored under advent-calendar directory in game's folder.  
Config must be added there manually; json for claimed gifts will be created automatically

Commands:  
&emsp;/calendar -- main command. opens calendar menu  
&emsp;/calendar_reload -- reloads configs, requires permission "advent-calendar.command.reload_config"

Contains localizations for en_us and uk_ua
