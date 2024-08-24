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

;------------------------------------------------------------------------------
;! Constants
;------------------------------------------------------------------------------
Const BSU_NAME_LENGTH					= 4096
Const BSU_INSTALLDIR_LENGTH				= 4096
Const BSU_APPID_COUNT					= 4096
Const BSU_INSTALLEDDEPOTS_COUNT			= 4096

;------------------------------------------------------------------------------
;! Globals
;------------------------------------------------------------------------------
Global BSU_Initialized = False
Global BSU_IsSteamRunning% = False
; -- Interfaces
Global BSU_AppList%
Global BSU_Apps%
Global BSU_Client%
Global BSU_Controller%
Global BSU_Friends%
Global BSU_GameServer%
Global BSU_GameServerStats%
Global BSU_HTMLSurface%
Global BSU_HTTP%
Global BSU_Inventory%
Global BSU_Matchmaking%
Global BSU_MatchmakingServers%
Global BSU_Music%
Global BSU_MusicRemote%
Global BSU_Networking%
Global BSU_RemoteStorage%
Global BSU_Screenshots%
Global BSU_UGC%
Global BSU_UnifiedMessages%
Global BSU_User%
Global BSU_UserStats%
Global BSU_Utils%
Global BSU_Video%

;[Block] API: Steam
;------------------------------------------------------------------------------
;! API: Steam
;------------------------------------------------------------------------------
Function BSU_Init()
	BSU_IsSteamRunning = BS_Steam_IsSteamRunning()
	If BSU_IsSteamRunning And BS_Steam_Init() Then
		
		BSU_AppList				= BS_AppList()
		BSU_Apps				= BS_Apps()
		BSU_Client				= BS_Client()
		BSU_Controller			= BS_Controller()
		BSU_Friends				= BS_Friends()
		BSU_GameServer			= BS_GameServer()
		BSU_GameServerStats		= BS_GameServerStats()
		BSU_HTMLSurface			= BS_HTMLSurface()
		BSU_HTTP				= BS_HTTP()
		BSU_Inventory			= BS_Inventory()
		BSU_Matchmaking			= BS_Matchmaking()
		BSU_MatchmakingServers	= BS_MatchmakingServers()
		BSU_Music				= BS_Music()
		BSU_MusicRemote			= BS_MusicRemote()
		BSU_Networking			= BS_Networking()
		BSU_RemoteStorage		= BS_RemoteStorage()
		BSU_Screenshots			= BS_Screenshots()
		BSU_UGC					= BS_UGC()
		BSU_UnifiedMessages		= BS_UnifiedMessages()
		BSU_User				= BS_User()
		BSU_UserStats			= BS_ISteamUserStats()
		BSU_Utils				= BS_Utils()
		BSU_Video				= BS_Video()
		
		BSU_Initialized = True
	EndIf
End Function

Function BSU_Shutdown()
	If BSU_IsSteamRunning
		BS_GameServer_Shutdown()
		BS_Steam_Shutdown()
		
		BSU_AppList=0
		BSU_Apps=0
		BSU_Client=0
		BSU_Controller=0
		BSU_Friends=0
		BSU_GameServer=0
		BSU_GameServerStats=0
		BSU_HTTP=0
		BSU_HTMLSurface=0
		BSU_Matchmaking=0
		BSU_MatchmakingServers=0
		BSU_Music=0
		BSU_MusicRemote=0
		BSU_Networking=0
		BSU_RemoteStorage=0
		BSU_Screenshots=0
		BSU_UGC=0
		BSU_UnifiedMessages=0
		BSU_User=0
		BSU_UserStats=0
		BSU_Utils=0
		BSU_Video=0
		
		BSU_Initialized = False
	EndIf
End Function
;[End Block]

;[Block] API: AppList
;------------------------------------------------------------------------------
;! API: AppList
;------------------------------------------------------------------------------
Type BSU_App
	Field AppId%
	Field Name$
	Field InstallDir$
End Type
Global BSU_AppCount = 0

Function BSU_AppList_GetInstalledApps(BankAppIdsStorage=0, BankAppNameStorage=0, BankAppInstallDirStorage=0)
	Local BankAppIds, BankAppIdsSz = BSU_APPID_COUNT
	Local BankAppName, BankAppNameSz = BSU_NAME_LENGTH
	Local BankAppInstallDir, BankAppInstallDirSz = BSU_INSTALLDIR_LENGTH
	Local AppCount%, InstalledApp.BSU_App
	
	If BSU_Initialized Then
		; Clear Installed App List
		Delete Each BSU_App
		
		; Early-Exit to not waste time.
		If BS_AppList_GetNumInstalledApps(BSU_Apps) = 0 Then Return
		
		If BankAppIdsStorage = 0
			; Create Temporary storage for AppIds.
			BankAppIds = CreateBank(BankAppIdsSz * 4)
		Else
			; Reuse existing Bank.
			BankAppIds = BankAppIdsStorage
			BankAppIdsSz = Floor(BankSize(BankAppIds) / 4)
		EndIf
		
		; Request installed apps from Steam.
		BSU_AppCount = BS_AppList_GetInstalledApps(BSU_AppList, BankAppIds, BankAppIdsSz)
		
		; We don't need to do this if we don't actually have any apps returned.
		If BSU_AppCount > 0 Then
			If BankAppNameStorage = 0 Then
				; Create temporary storage for name.
				BankAppName = CreateBank(BankAppNameSz)
			Else
				BankAppName = BankAppNameStorage
				BankAppNameSz = BankSize(BankAppName)
			EndIf
			If BankAppInstallDirStorage = 0 Then
				; Create temporary storage for installdir.
				BankAppInstallDir = CreateBank(BankAppInstallDirSz)
			Else
				BankAppInstallDir = BankAppInstallDirStorage
				BankAppInstallDirSz = BankSize(BankAppInstallDir)
			EndIf
			
			; Index all apps.
			Local AppIndex
			For AppIndex = 0 To BSU_AppCount - 1
				InstalledApp.BSU_App = New BSU_App
				InstalledApp\AppId = PeekInt(BankAppIds, AppIndex * 4)
				InstalledApp\Name = BSU_AppList_GetAppName(InstalledApp\AppId, BankAppName)
				InstalledApp\InstallDir = BSU_AppList_GetInstallDir(InstalledApp\AppId, BankAppInstallDir)
			Next
			
			; Free temporary storage for name and installdir.
			If BankAppInstallDirStorage = 0 Then FreeBank BankAppInstallDir
			If BankAppNameStorage = 0 Then FreeBank BankAppName
		EndIf
			
		; Free temporary storage for AppIds.
		If BankAppIdsStorage = 0 Then FreeBank BankAppIds
	EndIf
End Function

Function BSU_AppList_GetAppName$(AppID%, BankStorage=0)
	Local Bank, BankSz = BSU_NAME_LENGTH
	Local AppName$
	
	If BSU_Initialized Then
		If BankStorage = 0 Then
			; Create temporary storage.
			Bank = CreateBank(BankSz)
		Else
			; Reuse existing Bank.
			Bank = BankStorage
			BankSz = BankSize(Bank)
		EndIf
		
		; Request App name from Steam.
		BS_AppList_GetAppName(BSU_AppList, AppID, Bank, BankSz)
		
		; Read returned C-String from Bank.
		AppName$ = BSU_PeekCString(Bank, 0)
		
		; Free temporary storage.
		If BankStorage = 0 Then FreeBank Bank
	EndIf
	
	; Return name read.
	Return AppName
End Function

Function BSU_AppList_GetInstallDir$(AppID%, BankStorage=0)
	Local Bank, BankSz = BSU_INSTALLDIR_LENGTH
	Local AppInstallDir$
	
	If BSU_Initialized Then
		If BankStorage = 0 Then
			; Create temporary storage.
			Bank = CreateBank(BankSz)
		Else
			; Reuse existing Bank.
			Bank = BankStorage
			BankSz = BankSize(Bank)
		EndIf
		
		; Request App name from Steam.
		BS_AppList_GetAppInstallDir(BSU_AppList, AppID, Bank, BankSz)
		
		; Read returned C-String from Bank.
		AppInstallDir$ = BSU_PeekCString(Bank, 0)
		
		; Free temporary storage.
		If BankStorage = 0 Then FreeBank Bank
	EndIf
	
	; Return name read.
	Return AppInstallDir
End Function
;[End Block]

;[Block] API: Apps
;------------------------------------------------------------------------------
;! API: Apps
;------------------------------------------------------------------------------
Type BSU_DLC
	Field AppId%
	Field Available%
	Field Name$
End Type
Global BSU_DLCCount

Type BSU_Depot
	Field DepotId%
End Type
Global BSU_DepotCount

Function BSU_Apps_GetDLCData(BankAppIdStorage=0, BankAvailableStorage=0, BankNameStorage=0)
	Local BankAppId%, BankAvailable%
	Local BankName%, BankNameSz% = BSU_NAME_LENGTH
	Local DLCCount% = 0
	
	If BSU_Initialized Then
		Delete Each BSU_DLC
		
		BSU_DLCCount = BS_Apps_GetDLCCount(BSU_Apps)
		If BSU_DLCCount > 0
			If BankAppIdStorage = 0 Then
				; Create temporary storage for AppId.
				BankAppId = CreateBank(4)
			Else
				; Reuse existing storage.
				BankAppId = BankAppIdStorage
			EndIf
			
			If BankAvailableStorage = 0 Then
				; Create temporary storage for Availability.
				BankAvailable = CreateBank(4)
			Else
				; Reuse existing storage.
				BankAvailable = BankAvailableStorage
			EndIf
			
			If BankNameStorage = 0 Then
				; Create temporary storage for Availability.
				BankName = CreateBank(BankNameSz)
			Else
				; Reuse existing storage.
				BankName = BankNameStorage
				BankNameSz = BankSize(BankName)
			EndIf
			
			Local DLCIndex%
			For DLCIndex = 0 To BSU_DLCCount - 1
				BSU_Apps_GetDLCDataByIndex(DLCIndex, BankAppId, BankAvailable, BankName)
			Next
			
			; Free temporary storages.
			If BankNameStorage = 0 Then FreeBank BankName
			If BankAvailableStorage = 0 Then FreeBank BankAvailable
			If BankAppIdStorage = 0 Then FreeBank BankAppId
		EndIf
	EndIf
End Function

Function BSU_Apps_GetDLCDataByIndex.BSU_DLC(iDLC%, BankAppIdStorage=0, BankAvailableStorage=0, BankNameStorage=0)
	Local BankAppId%, BankAvailable%
	Local BankName%, BankNameSz% = BSU_NAME_LENGTH
	Local DLC.BSU_DLC
	
	If BSU_Initialized Then
		If BankAppIdStorage = 0 Then
			; Create temporary storage for AppId.
			BankAppId = CreateBank(4)
		Else
			; Reuse existing storage.
			BankAppId = BankAppIdStorage
		EndIf
		
		If BankAvailableStorage = 0 Then
			; Create temporary storage for Availability.
			BankAvailable = CreateBank(4)
		Else
			; Reuse existing storage.
			BankAvailable = BankAvailableStorage
		EndIf
		
		If BankNameStorage = 0 Then
			; Create temporary storage for Availability.
			BankName = CreateBank(BankNameSz)
		Else
			; Reuse existing storage.
			BankName = BankNameStorage
			BankNameSz = BankSize(BankName)
		EndIf
		
		; Request DLC Data from Steam.
		If BS_Apps_GetDLCDataByIndex(BSU_Apps, iDLC, BankAppId, BankAvailable, BankName, BankNameSz)
			; Create a result DLC object.
			DLC.BSU_DLC = New BSU_DLC
			DLC\AppId = PeekInt(BankAppId, 0)
			DLC\Available = PeekInt(BankAvailable, 0)
			DLC\Name = BSU_PeekCString(BankName, 0)
		EndIf
		
		; Free temporary storages.
		If BankNameStorage = 0 Then FreeBank BankName
		If BankAvailableStorage = 0 Then FreeBank BankAvailable
		If BankAppIdStorage = 0 Then FreeBank BankAppId
	EndIf
	
	; Return the result.
	Return DLC
End Function

Function BSU_Apps_GetCurrentBetaName$(BankNameStorage=0)
	Local BankName, BankNameSz = BSU_NAME_LENGTH
	Local BetaName$ = ""
	
	If BSU_Initialized Then
		If BankNameStorage = 0 Then
			; Create temporary storage for name.
			BankName = CreateBank(BankNameSz)
		Else
			; Reuse existing storage.
			BankName = BankNameStorage
			BankNameSz = BankSize(BankName)
		EndIf
		
		; Request beta name from Steam.
		BS_Apps_GetCurrentBetaName(BSU_Apps, BankName, BankNameSz)
		
		; Read returned name.
		BetaName = BSU_PeekCString(BankName, 0)
		
		; Free temporary storage.
		If BankNameStorage = 0 Then FreeBank BankName
	EndIf
	
	; Return the result.
	Return BetaName
End Function

Function BSU_Apps_GetInstalledDepots(nAppID%, BankDepotStorage=0)
	Local BankDepot, BankDepotSz = BSU_INSTALLEDDEPOTS_COUNT
	Local DepotCount
	
	If BSU_Initialized Then
		Delete Each BSU_Depot
		
		If BankDepotStorage = 0 Then
			; Create temporary storage.
			BankDepot = CreateBank(BankDepotSz * 4)
		Else
			; Reuse existing storage.
			BankDepot = BankDepotStorage
			BankDepotSz = BankSize(BankDepot) / 4
		EndIf
		
		; Request depots from Steam.
		BSU_DepotCount = BS_Apps_GetInstalledDepots(BSU_Apps, nAppID, BankDepot, BankDepotSz)
		
		; Read returned depots into objects.
		Local DepotIndex
		For DepotIndex = 0 To BSU_DepotCount - 1
			Local Depot.BSU_Depot = New BSU_Depot
			Depot\DepotId = PeekInt(BankDepot, DepotIndex * 4)
		Next
		
		; Free temporary storage.
		If BankDepotStorage = 0 Then FreeBank BankDepot
	EndIf
End Function

Function BSU_Apps_GetAppInstallDir$(nAppID%, BankInstallDirStorage=0)
	Local BankInstallDir, BankInstallDirSz = BSU_INSTALLDIR_LENGTH
	Local InstallDir$
	
	If BSU_Initialized Then
		If BankInstallDirStorage = 0 Then
			; Create temporary storage.
			BankInstallDir = CreateBank(BankInstallDirSz)
		Else
			; Reuse existing storage.
			BankInstallDir = BankInstallDirStorage
			BankInstallDirSz = BankSize(BankInstallDir)
		EndIf
		
		; Request install dir from Steam.
		Local InstallDirLen% = BS_Apps_GetAppInstallDir(BSU_Apps, nAppID, BankInstallDir, BankInstallDirSz)
		
		; Read returned value.
		InstallDir = BSU_PeekCString(BankInstallDir, 0)
		
		; Free temporary storage.
		If BankInstallDirStorage = 0 Then FreeBank BankInstallDir
	EndIf
	
	Return InstallDir$
End Function

Function BSU_Apps_GetDLCDownloadProgress#(nAppID%)
	Local Progress# = 1.0
	If BSU_Initialized
		; Create temporary storage.
		Local i64_Downloaded, i64_Total
		i64_Downloaded	= BU_LongLong_Create()
		i64_Total		= BU_LongLong_Create()
		
		; Request download progress from Steam.
		If BS_Apps_GetDlcDownloadProgressEx(BSU_Apps, nAppID, i64_Downloaded, i64_Total) Then
			Local dDownloaded, dTotal
			dDownloaded = BU_LongLong_ToDouble(i64_Downloaded)
			dTotal = BU_LongLong_ToDouble(i64_Total)
			
			BU_Double_Div(dDownloaded, dTotal)
			Progress = BU_Double_ToFloat(dDownloaded)
			
			BU_Double_Destroy dDownloaded
			BU_Double_Destroy dTotal
		EndIf
		
		; Free temporary storage.
		BU_LongLong_Destroy i64_Downloaded
		BU_LongLong_Destroy i64_Total
	EndIf
	Return Progress
End Function
;[End Block]

;[Block] API: Apps
;------------------------------------------------------------------------------
;! API: Friends
;------------------------------------------------------------------------------
Type BSU_Friend
	Field SteamID_L, SteamID_R
	
	Field Name$
	Field NickName$
	
	Field Index%
	Field Relationship%
	Field State%
	Field SteamLevel%
End Type
Global BSU_FriendCount

Function BSU_Friends_GetFriends(iFriendFlags = BS_EFriendFlags_All)
	If BSU_Initialized Then
		Delete Each BSU_Friend
		
		BSU_FriendCount = BS_Friends_GetFriendCount(BSU_Friends, iFriendFlags)
		If BSU_FriendCount > 0 Then
			Local FriendIndex
			For FriendIndex = 0 To BSU_FriendCount - 1
				Local CSteamID = BS_Friends_GetFriendByIndex(BSU_Friends, FriendIndex, iFriendFlags)
				Local Friend.BSU_Friend = New BSU_Friend
				
				; Store a 'native' version of the SteamID
				Local SteamID64 = BS_CSteamID_ConvertToUInt64(CSteamID)
				Friend\SteamID_L = BU_LongLong_ToLongHigh(SteamID64)
				Friend\SteamID_R = BU_LongLong_ToLongLow(SteamID64)
				BU_LongLong_Destroy(SteamID64)
				
				; Names
				Friend\Name = BS_Friends_GetFriendPersonaName(BSU_Friends, CSteamID)
				Friend\NickName = BS_Friends_GetPlayerNickname(BSU_Friends, CSteamID)
				
				; Other Stuff
				Friend\Index = FriendIndex
				Friend\Relationship = BS_Friends_GetFriendRelationship(BSU_Friends, CSteamID)
				Friend\State = BS_Friends_GetFriendPersonaState(BSU_Friends, CSteamID)
				Friend\SteamLevel = BS_Friends_GetFriendSteamLevel(BSU_Friends, CSteamID)
				
				BS_CSteamID_Destroy(CSteamID)
			Next
		EndIf
	EndIf
End Function

;[Block] API: GameServer
;------------------------------------------------------------------------------
;! API: GameServer
;------------------------------------------------------------------------------
Function BSU_GameServer_Init(IPv4%=0, Port%=27015, SteamPort%=27016, QueryPort=27017, ServerMode=BS_EServerMode_AuthenticationAndSecure, Version$="1.0.0.0")
	If BS_SteamGameServer_Init(IPv4, SteamPort, Port, QueryPort, ServerMode, Version) Then
		BSU_GameServer = BS_GameServer()
		BSU_GameServerStats = BS_GameServerStats()
		BSU_HTTP = BS_GameServerHTTP()
		BSU_Inventory = BS_GameServerInventory()
		BSU_Networking = BS_GameServerNetworking()
		BSU_UGC = BS_GameServerUGC()
		BSU_Utils = BS_GameServerUtils()
		
		BSU_Initialized = True
	EndIf
End Function

Function BSU_GameServer_Shutdown()
	If BSU_Initialized = True
		BS_SteamGameServer_Shutdown()
		
		BSU_GameServer=0
		BSU_GameServerStats=0
		BSU_HTTP=0
		BSU_Inventory=0
		BSU_Networking=0
		BSU_UGC=0
		BSU_Utils=0
		
		BSU_Initialized = False
	EndIf
End Function
;[End Block]

;------------------------------------------------------------------------------
;! API: GameServerStats
;------------------------------------------------------------------------------



;[Block] Blitz Stuff
;----------------------------------------------------------------
;! Blitz Stuff
;----------------------------------------------------------------
; -- Utility
; Writes a C-String value to a Bank.
; Returns amount of bytes written.
Function BSU_PokeCString%(Bank%, Pos%, Value$)
	If Bank Then
		Local BankSz = BankSize(Bank)
		
		If Pos < 0 Then Pos = 0
		If Pos >= BankSz Then Pos = BankSz - 1
		
		Local ValuePos, ValueLen = Len(Value)
		For ValuePos = 1 To ValueLen
			; Don't write over the edge, we still need space for the 0-byte
			If (Pos + ValuePos) >= (BankSz - 1) Then Exit
			
			PokeByte Bank, Pos + ValuePos, Asc(Mid(Value, ValuePos, 1))
		Next
		PokeByte Bank, Pos + ValuePos, 0
		Return ValuePos
	EndIf
End Function

; Reads a C-String value from a Bank.
; Returns read C-String
Function BSU_PeekCString$(Bank%, Pos%, Len%=-1)
	If Bank Then
		Local BankSz = BankSize(Bank)
		
		If Pos < 0 Then Pos = 0
		If Pos >= BankSz Then Pos = BankSz - 1
		
		Local OutStr$, OutLen = (BankSz - Pos)
		Local BankPos
		For BankPos = 0 To OutLen
			If (Pos + BankPos) >= BankSz Then Exit
			
			Local Value = PeekByte(Bank, Pos + BankPos)
			
			If (Value = 0 And Len = -1) Or (Pos > Len) Then
				Exit
			Else
				OutStr=OutStr+Chr(Value)
			EndIf
		Next
		Return OutStr
	EndIf
End Function
;[End Block]

;~IDEal Editor Parameters:
;~F#3A#5A#7F#86#C3#DF#100#107#10C#13C#16E#18A#1A9#1C4
;~C#Blitz3D