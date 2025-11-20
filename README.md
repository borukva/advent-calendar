Simple server-side mod.
Mod utilizes sgui for gui and polymer for resource pack(for translations)

All time calculations done in Europe/Kyiv timezone

Config for rewards is required. Example can be found in repository
  allowExpired -- whether or not to allow player to claim gifts of the past days they've missed
  year -- year to check. if it differs in local time, no rewards will be available 
  month -- same as year
  rewards:
    "N" -- day number of the month
      "item" -- item identificator to give player
      "count" -- amount of "item"
      "lore" -- optional field to add some info to gift. translatable

Mod stores info about claimed gifts in json file

Both json files are stored under advent-calendar directory in game's folder.
Config must be added there manually; json for claimed gifts will be created automatically

Commands:
  /calendar -- main command. opens calendar menu 
  /calendar_reload -- reloads configs, requires permission "advent-calendar.command.reload_config"

Contains localizations for en_us and uk_ua
