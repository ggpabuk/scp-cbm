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

; UGC -------------------------------------------------------------------------
BS_SteamUGC%()																												:"_BS_SteamUGC@0"
BS_SteamGameServerUGC%()																									:"_BS_SteamGameServerUGC@0"
BS_SteamUGC_CreateItem%(pThis%, nConsumerAppId%, eFileType%)																:"_BS_UGC_CreateItem@12"
BS_SteamUGC_StartItemUpdate%(pThis%, nConsumerAppId%, nPublishedFileID%)													:"_BS_UGC_StartItemUpdate@12"
BS_SteamUGC_SetItemTitle%(pThis%, handle%, pchTitle$)																		:"_BS_UGC_SetItemTitle@12"
BS_SteamUGC_SetItemDescription%(pThis%, handle%, pchDescription$)															:"_BS_UGC_SetItemDescription@12"
BS_SteamUGC_SetItemUpdateLanguage%(pThis%, handle%, pchLanguage$)															:"_BS_UGC_SetItemMetadata@12"
BS_SteamUGC_SetItemMetadata%(pThis%, handle%, pchMetaData$)																	:"_BS_UGC_SetItemMetadata@12"
BS_SteamUGC_SetItemVisibility%(pThis%, handle%, eVisibility%)																:"_BS_UGC_SetItemVisibility@12"
BS_SteamUGC_SetItemTags%(pThis%, handle%, SteamParamStringArray_t*)															:"_BS_UGC_SetItemTags@12"
BS_SteamUGC_SetItemContent%(pThis%, handle%, pszContentFolder$)																:"_BS_UGC_SetItemContent@12"
BS_SteamUGC_SetItemPreview%(pThis%, handle%, pszPreviewFile$)																:"_BS_UGC_SetItemPreview@12"
BS_SteamUGC_RemoveItemKeyValueTags%(pThis%, handle%, pchKey$)																:"_BS_UGC_RemoveItemKeyValueTags@12"
BS_SteamUGC_AddItemKeyValueTag%(pThis%, handle%, pchKey$, pchValue$)														:"_BS_UGC_AddItemKeyValueTag@16"
BS_SteamUGC_SubmitItemUpdate%(pThis%, handle%, pchChangeNote$)																:"_BS_UGC_SubmitItemUpdate@12"
BS_SteamUGC_GetItemUpdateProgress%(pThis%, handle%, punBytesProcessed%, punBytesTotal%)										:"_BS_UGC_GetItemUpdateProgress@16"
BS_SteamUGC_GetItemInstallInfo%(pThis%, PublishedFileID%, SizeOnDisk*, pchFolder*, pchFolderSize%, TimeStampMem*)			:"_BS_UGC_GetItemInstallInfo@24"
BS_SteamUGC_GetSubscribedItems%(pThis%)																						:"_BS_UGC_GetSubscribedItems@4"
BS_SteamUGC_GetItemState%(pThis%, PublishedFileIDMem%)																		:"_BS_UGC_GetItemState@8"
BS_SteamUGC_GetNumSubscribedItems%(pThis%)																					:"_BS_UGC_GetNumSubscribedItems@4"
BS_SteamUGC_DownloadItem%(pThis%, PublishedFileID%, HighPriority%)															:"_BS_UGC_DownloadItem@12"
BS_SteamUGC_GetItemStateByFolder(pThis%, folder$)																			:"_BS_UGC_GetItemStateByFolder@8"