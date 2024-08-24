.lib "steam_api.dll"
SteamAPI_RestartAppIfNecessary%(appid%)
SteamAPI_Init%()
SteamUserStats_SetAchievement%(achievement$):"SteamAPI_ISteamGameServerStats_SetUserAchievement"
SteamUserStats_GetAchievement%(achievement$):"SteamAPI_ISteamUserStats_GetAchievement"
SteamAPI_SkipStats%():"SteamAPI_ISteamUserStats_RequestCurrentStats"