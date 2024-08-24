;	BlitzSteam - Steam wrapper for Blitz
;	Copyright (C) 2015 Xaymar (Michael Fabian Dirks)
;
;	This program is free software: you can redistribute it and/or modify
;	it under the terms of the GNU Lesser General Public License as
;	published by the Free Software Foundation, either version 3 of the 
;	License, or (at your option) any later version.
;
;	This program is distributed in the hope that it will be useful,
;	but WITHOUT ANY WARRANTY; without even the implied warranty of
;	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
;	GNU General Public License for more details.
;
;	You should have received a copy of the GNU Lesser General Public License
;	along with this program.  If not, see <http:;www.gnu.org/licenses/>.

.lib "BlitzSteam.dll"

; UserStats -------------------------------------------------------------------
BS_SteamUserStats%()																			:"_BS_SteamUserStats@0"
BS_ISteamUserStats_RequestCurrentStats%(pThis%)													:"_BS_ISteamUserStats_RequestCurrentStats@4"
BS_ISteamUserStats_GetStat%(pThis%, cName$, pData*)												:"_BS_ISteamUserStats_GetStat@12"
BS_ISteamUserStats_GetStatEx%(pThis%, cName$, pData%)											:"_BS_ISteamUserStats_GetStat@12"
BS_ISteamUserStats_GetStatF%(pThis%, cName$, pData*)											:"_BS_ISteamUserStats_GetStatF@12"
BS_ISteamUserStats_GetStatFEx%(pThis%, cName$, pData%)											:"_BS_ISteamUserStats_GetStatF@12"
BS_ISteamUserStats_SetStat%(pThis%, cName$, pData%)												:"_BS_ISteamUserStats_SetStat@12"
BS_ISteamUserStats_SetStatF%(pThis%, cName$, pData#)											:"_BS_ISteamUserStats_SetStatF@12"
BS_ISteamUserStats_UpdateAvgRateStat%(pThis%, cName$, fCountThisSession#, dSessionLength%)		:"_BS_ISteamUserStats_UpdateAvgRateStat@16"
BS_ISteamUserStats_GetAchievement%(pThis%, cName$, pbAchieved*)									:"_BS_ISteamUserStats_GetAchievement@12"
BS_ISteamUserStats_GetAchievementEx%(pThis%, cName$, pbAchieved%)								:"_BS_ISteamUserStats_GetAchievement@12"
BS_ISteamUserStats_SetAchievement%(pThis%, cName$)												:"_BS_ISteamUserStats_SetAchievement@8"
BS_ISteamUserStats_ClearAchievement%(pThis%, cName$)											:"_BS_ISteamUserStats_ClearAchievement@8"
BS_ISteamUserStats_GetAchievementAndUnlockTime%(pThis%, cName$, pbAchieved*, piUnlockTime*)		:"_BS_ISteamUserStats_GetAchievementAndUnlockTime@16"
BS_ISteamUserStats_GetAchievementAndUnlockTimeEx%(pThis%, cName$, pbAchieved%, piUnlockTime%)	:"_BS_ISteamUserStats_GetAchievementAndUnlockTime@16"
BS_ISteamUserStats_StoreStats%(pThis%)															:"_BS_ISteamUserStats_StoreStats@4"
BS_ISteamUserStats_GetAchievementIcon%(pThis%, cName$)											:"_BS_ISteamUserStats_GetAchievementIcon@8"
BS_ISteamUserStats_GetAchievementDisplayAttribute$(pThis%, cName$, cKey$)						:"_BS_ISteamUserStats_GetAchievementDisplayAttribute@12"
BS_ISteamUserStats_IndicateAchievementProgress%(pThis%, cName$, iProgress%, iMaxProgress%)		:"_BS_ISteamUserStats_IndicateAchievementProgress@16"
BS_ISteamUserStats_GetNumAchievements%(pThis%)													:"_BS_ISteamUserStats_GetNumAchievements@4"
BS_ISteamUserStats_GetAchievementName$(pThis%, iAchievement%)									:"_BS_ISteamUserStats_GetAchievementName@8"
BS_ISteamUserStats_RequestUserStats%(pThis%, lSteamId%)											:"_BS_ISteamUserStats_RequestUserStats@8"
BS_ISteamUserStats_GetUserStat%(pThis%, lSteamId%, cName$, pData*)								:"_BS_ISteamUserStats_GetUserStat@16"
BS_ISteamUserStats_GetUserStatEx%(pThis%, lSteamId%, cName$, pData%)							:"_BS_ISteamUserStats_GetUserStat@16"
BS_ISteamUserStats_GetUserStatF%(pThis%, lSteamId%, cName$, pData*)								:"_BS_ISteamUserStats_GetUserStatF@16"
BS_ISteamUserStats_GetUserStatFEx%(pThis%, lSteamId%, cName$, pData%)							:"_BS_ISteamUserStats_GetUserStatF@16"
BS_ISteamUserStats_GetUserAchievement%(pThis%, lSteamId%, cName$, pbAchieved*)					:"_BS_ISteamUserStats_GetUserAchievement@16"
BS_ISteamUserStats_GetUserAchievementEx%(pThis%, lSteamId%, cName$, pbAchieved%)				:"_BS_ISteamUserStats_GetUserAchievement@16"
BS_ISteamUserStats_GetUserAchievementAndUnlockTime%(pThis%, lSteamId%, cName$, pbAchieved*, piUnlockTime*)												:"_BS_ISteamUserStats_GetUserAchievementAndUnlockTime@20"
BS_ISteamUserStats_GetUserAchievementAndUnlockTimeEx%(pThis%, lSteamId%, cName$, pbAchieved%, piUnlockTime%)											:"_BS_ISteamUserStats_GetUserAchievementAndUnlockTime@20"
BS_ISteamUserStats_ResetAllStats%(pThis%, bAchievementsToo%)									:"_BS_ISteamUserStats_ResetAllStats@8"
BS_ISteamUserStats_FindOrCreateLeaderboard%(pThis%, cLeaderboardName$, eLeaderboardSortMethod%, eLeaderboardDisplayType%)								:"_BS_ISteamUserStats_FindOrCreateLeaderboard@16"
BS_ISteamUserStats_FindLeaderboard%(pThis%, cLeaderboardName$)									:"_BS_ISteamUserStats_FindLeaderboard@8"
BS_ISteamUserStats_GetLeaderboardName$(pThis%, hSteamLeaderboard%)								:"_BS_ISteamUserStats_GetLeaderboardName@8"
BS_ISteamUserStats_GetLeaderboardEntryCount%(pThis%, hSteamLeaderboard%)						:"_BS_ISteamUserStats_GetLeaderboardEntryCount@8"
BS_ISteamUserStats_GetLeaderboardSortMethod%(pThis%, hSteamLeaderboard%)						:"_BS_ISteamUserStats_GetLeaderboardSortMethod@8"
BS_ISteamUserStats_GetLeaderboardDisplayType%(pThis%, hSteamLeaderboard%)						:"_BS_ISteamUserStats_GetLeaderboardDisplayType@8"
BS_ISteamUserStats_DownloadLeaderboardEntries%(pThis%, hSteamLeaderboard%, eLeaderboardDataRequest%, iRangeStart%, iRangeEnd%)							:"_BS_ISteamUserStats_DownloadLeaderboardEntries@20"
BS_ISteamUserStats_DownloadLeaderboardEntriesForUsers%(pThis%, hSteamLeaderboard%, plUsers*, iUserCount%)												:"_BS_ISteamUserStats_DownloadLeaderboardEntriesForUsers@16"
BS_ISteamUserStats_DownloadLeaderboardEntriesForUsersEx%(pThis%, hSteamLeaderboard%, plUsers%, iUserCount%)												:"_BS_ISteamUserStats_DownloadLeaderboardEntriesForUsers@16"
BS_ISteamUserStats_GetDownloadedLeaderboardEntry%(lpSteamUsers%, hSteamLeaderboardEntries%, iIndex%, pLeaderboardEntry*, pDetails*, iDetailsMax%)		:"_BS_ISteamUserStats_GetDownloadedLeaderboardEntry@24"
BS_ISteamUserStats_GetDownloadedLeaderboardEntryEx%(lpSteamUsers%, hSteamLeaderboardEntries%, iIndex%, pLeaderboardEntry%, pDetails%, iDetailsMax%)		:"_BS_ISteamUserStats_GetDownloadedLeaderboardEntry@24"
BS_ISteamUserStats_UploadLeaderboardScore%(pThis%, hSteamLeaderboard%, eLeaderboardUploadScoreMethod%, iScore%, pScoreDetails*, iScoreDetailsCount%)	:"_BS_ISteamUserStats_UploadLeaderboardScore@24"
BS_ISteamUserStats_UploadLeaderboardScoreEx%(pThis%, hSteamLeaderboard%, eLeaderboardUploadScoreMethod%, iScore%, pScoreDetails%, iScoreDetailsCount%)	:"_BS_ISteamUserStats_UploadLeaderboardScore@24"
BS_ISteamUserStats_AttachLeaderboardUGC%(pThis%, hSteamLeaderboard%, hUGC%)						:"_BS_ISteamUserStats_AttachLeaderboardUGC@12"
BS_ISteamUserStats_GetNumberOfCurrentPlayers%(pThis%)											:"_BS_ISteamUserStats_GetNumberOfCurrentPlayers@4"
BS_ISteamUserStats_RequestGlobalAchievementPercentages%(pThis%)									:"_BS_ISteamUserStats_RequestGlobalAchievementPercentages@4"
BS_ISteamUserStats_GetMostAchievedAchievementInfo%(pThis%, cName$, iNameLength%, pfPercent*, pbAchieved*)												:"_BS_ISteamUserStats_GetMostAchievedAchievementInfo@20"
BS_ISteamUserStats_GetMostAchievedAchievementInfoEx%(pThis%, cName$, iNameLength%, pfPercent%, pbAchieved%)												:"_BS_ISteamUserStats_GetMostAchievedAchievementInfo@20"
BS_ISteamUserStats_GetNextMostAchievedAchievementInfo%(pThis%, iIteratorPrevious%, cName$, iNameLength%, pfPercent*, pbAchieved*)						:"_BS_ISteamUserStats_GetNextMostAchievedAchievementInfo@24"
BS_ISteamUserStats_GetNextMostAchievedAchievementInfoEx%(pThis%, iIteratorPrevious%, cName$, iNameLength%, pfPercent%, pbAchieved%)						:"_BS_ISteamUserStats_GetNextMostAchievedAchievementInfo@24"
BS_ISteamUserStats_GetAchievementAchievedPercent%(pThis%, cName$, pfPercent*)					:"_BS_ISteamUserStats_GetAchievementAchievedPercent@12"
BS_ISteamUserStats_GetAchievementAchievedPercentEx%(pThis%, cName$, pfPercent%)					:"_BS_ISteamUserStats_GetAchievementAchievedPercent@12"
BS_ISteamUserStats_RequestGlobalStats%(pThis%, iHistoryDays%)									:"_BS_ISteamUserStats_RequestGlobalStats@8"
BS_ISteamUserStats_GetGlobalStatL%(pThis, cStatName$, plData%)									:"_BS_ISteamUserStats_GetGlobalStatL@12"
BS_ISteamUserStats_GetGlobalStatD%(pThis, cStatName$, pdData%)									:"_BS_ISteamUserStats_GetGlobalStatD@12"
BS_ISteamUserStats_GetGlobalStatHistoryL%(pThis, cStatName$, plDataArray%, iDataSize%)			:"_BS_ISteamUserStats_GetGlobalStatHistoryL@16"
BS_ISteamUserStats_GetGlobalStatHistoryD%(pThis, cStatName$, pdDataArray%, iDataSize%)			:"_BS_ISteamUserStats_GetGlobalStatHistoryD@16"