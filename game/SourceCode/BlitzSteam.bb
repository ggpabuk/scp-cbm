;	BlitzSteam - Steam wrapper for Blitz
;	Copyright (C) 2015 Xaymar (Michael Fabian Dirks)
;
;	This program is free software: you can redistribute it and/or modify
;	it under the terms of the GNU Lesser General Public License as
;	published by the Free Software Foundation either version 3 of the 
;	License or (at your option) any later version.
;
;	This program is distributed in the hope that it will be useful
;	but WITHOUT ANY WARRANTY; without even the implied warranty of
;	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
;	GNU General Public License for more details.
;
;	You should have received a copy of the GNU Lesser General Public License
;	along with this program.  If not see <http://www.gnu.org/licenses/>.

;------------------------------------------------------------------------------
;! Enumerations
;------------------------------------------------------------------------------
;[Block] Enumeration: EAccountType
;------------------------------------------------------------------------------
;! Enumeration - EAccountType
;------------------------------------------------------------------------------
;// Steam account types
Const BS_EAccountType_Invalid								= 0
Const BS_EAccountType_Individual							= 1 ;// single user account
Const BS_EAccountType_Multiseat								= 2 ;// multiseat (e.g. cybercafe) account
Const BS_EAccountType_Gameserver							= 3 ;// game server account
Const BS_EAccountType_AnonGamerserver						= 4 ;// anonymous game server account
Const BS_EAccountType_Pending								= 5 ;// pending
Const BS_EAccountType_ContentServer							= 6 ;// content server
Const BS_EAccountType_Clan									= 7
Const BS_EAccountType_Chat									= 8
Const BS_EAccountType_ConsoleUser							= 9 ;// Fake SteamID for local PSN account on PS3 or Live account on 360 etc.
Const BS_EAccountType_AnonUser								= 10
Const BS_EaccountType_Max									= 16 ;// Max of 16 items in this field
;[End Block]

;[Block] Enumeration: EAppReleaseState
;------------------------------------------------------------------------------
;! Enumeration - EAppReleaseState
;------------------------------------------------------------------------------
Const BS_EAppReleaseState_Unknown							= 0 ;// unknown required appinfo or license info is missing
Const BS_EAppReleaseState_Unavailable						= 1 ;// even if user 'just' owns it can see game at all
Const BS_EAppReleaseState_Prerelease						= 2 ;// can be purchased and is visible in games list nothing else. Common appInfo section released
Const BS_EAppReleaseState_PreloadOnly						= 3 ;// owners can preload app not play it. AppInfo fully released.
Const BS_EAppReleaseState_Release							= 4 ;// owners can download and play app.
;[End Block]

;[Block] Enumeration: EAppOwnershipFlags
;------------------------------------------------------------------------------
;! Enumeration - EAppOwnershipFlags
;------------------------------------------------------------------------------
Const BS_EAppOwnershipFlags_None							= $0000	; unknown
Const BS_EAppOwnershipFlags_OwnsLicense						= $0001	; owns license for this game
Const BS_EAppOwnershipFlags_FreeLicense						= $0002	; not paid for game
Const BS_EAppOwnershipFlags_RegionRestricted				= $0004	; owns app but not allowed to play in current region
Const BS_EAppOwnershipFlags_LowViolence						= $0008	; only low violence version
Const BS_EAppOwnershipFlags_InvalidPlatform					= $0010	; app not supported on current platform
Const BS_EAppOwnershipFlags_SharedLicense					= $0020	; license was granted by authorized local device
Const BS_EAppOwnershipFlags_FreeWeekend						= $0040	; owned by a free weekend licenses
Const BS_EAppOwnershipFlags_RetailLicense					= $0080	; has a retail license for game (CD-Key etc)
Const BS_EAppOwnershipFlags_LicenseLocked					= $0100	; shared license is locked (in use) by other user
Const BS_EAppOwnershipFlags_LicensePending					= $0200	; owns app but transaction is still pending. Can't install or play
Const BS_EAppOwnershipFlags_LicenseExpired					= $0400	; doesn't own app anymore since license expired
Const BS_EAppOwnershipFlags_LicensePermanent				= $0800	; permanent license not borrowed or guest or freeweekend etc
Const BS_EAppOwnershipFlags_LicenseRecurring				= $1000	; Recurring license user is charged periodically
Const BS_EAppOwnershipFlags_LicenseCanceled					= $2000	; Mark as canceled but might be still active if recurring
Const BS_EAppOwnershipFlags_AutoGrant						= $4000	; Ownership is based on any kind of autogrant license
Const BS_EAppOwnershipFlags_PendingGift						= $8000	; user has pending gift to redeem
Const BS_EAppOwnershipFlags_RentalNotActivated				= $10000	; Rental hasn't been activated yet
;[End Block]

;[Block] Enumeration: EAppType
;------------------------------------------------------------------------------
;! Enumeration - EAppType
;------------------------------------------------------------------------------
; Purpose: designed as flags to allow filters masks
Const BS_EAppType_Invalid									= $000	; unknown / invalid
Const BS_EAppType_Game										= $001	; playable game default type
Const BS_EAppType_Application								= $002	; software application
Const BS_EAppType_Tool										= $004	; SDKs editors & dedicated servers
Const BS_EAppType_Demo										= $008	; game demo
Const BS_EAppType_Media_DEPRECATED							= $010	; legacy - was used for game trailers which are now just videos on the web
Const BS_EAppType_DLC										= $020	; down loadable content
Const BS_EAppType_Guide										= $040	; game guide PDF etc
Const BS_EAppType_Driver									= $080	; hardware driver updater (ATI Razor etc)
Const BS_EAppType_Config									= $100	; hidden app used to config Steam features (backpack sales etc)
Const BS_EAppType_Hardware									= $200	; a hardware device (Steam Machine Steam Controller Steam Link etc.)
Const BS_EAppType_Video										= $800	; A video component of either a Film or TVSeries (may be the feature an episode preview making-of etc)
Const BS_EAppType_Plugin									= $1000	; Plug-in types for other Apps
Const BS_EAppType_Music										= $2000	; Music files
Const BS_EAppType_Shortcut									= $40000000	; just a shortcut client side only
Const BS_EAppType_DepotOnly									= $80000000	; placeholder since depots and apps share the same namespace
;[End Block]

;[Block] Enumeration: EAuthSessionResponse
;------------------------------------------------------------------------------
;! Enumeration - EAuthSessionResponse
;------------------------------------------------------------------------------
;// Callback values for callback ValidateAuthTicketResponse_t which is a response to BeginAuthSession
Const BS_EAuthSessionResponse_OK							= 0 ;// Steam has verified the user is online the ticket is valid and ticket has not been reused.
Const BS_EAuthSessionResponse_UserNotConnectedToSteam		= 1 ;// The user in question is not connected to steam
Const BS_EAuthSessionResponse_NoLicenseOrExpired			= 2 ;// The license has expired.
Const BS_EAuthSessionResponse_VACBanned						= 3 ;// The user is VAC banned for this game.
Const BS_EAuthSessionResponse_LoggedInElseWhere				= 4 ;// The user account has logged in elsewhere and the session containing the game instance has been disconnected.
Const BS_EAuthSessionResponse_VACCheckTimedOut				= 5 ;// VAC has been unable to perform anti-cheat checks on this user
Const BS_EAuthSessionResponse_AuthTicketCanceled			= 6 ;// The ticket has been canceled by the issuer
Const BS_EAuthSessionResponse_AuthTicketInvalidAlreadyUsed	= 7 ;// This ticket has already been used it is not valid.
Const BS_EAuthSessionResponse_AuthTicketInvalid				= 8 ;// This ticket is not from a user instance currently connected to steam.
Const BS_EAuthSessionResponse_PublisherIssuesBan			= 9 ;// The user is banned for this game. The ban came via the web api and not VAC
;[End Block]

;[Block] Enumeration: EBeginAuthSessionResult
;------------------------------------------------------------------------------
;! Enumeration - EBeginAuthSessionResult
;------------------------------------------------------------------------------
;// results from BeginAuthSession
Const BS_EBeginAuthSessionResult_OK							= 0	; Ticket is valid for this game and this steamID.
Const BS_EBeginAuthSessionResult_InvalidTicket				= 1	; Ticket is not valid.
Const BS_EBeginAuthSessionResult_DuplicateRequest			= 2	; A ticket has already been submitted for this steamID
Const BS_EBeginAuthSessionResult_InvalidVersion				= 3	; Ticket is from an incompatible interface version
Const BS_EBeginAuthSessionResult_GameMismatch				= 4	; Ticket is not for this game
Const BS_EBeginAuthSessionResult_ExpiredTicket				= 5	; Ticket has expired
;[End Block]

;[Block] Enumeration: EBroadcastUploadResult
;------------------------------------------------------------------------------
;! Enumeration - EBroadcastUploadResult
;------------------------------------------------------------------------------
; Purpose: Broadcast upload result details
Const BS_EBroadcastUploadResult_None = 0	; broadcast state unknown
Const BS_EBroadcastUploadResult_OK = 1		; broadcast was good no problems
Const BS_EBroadcastUploadResult_InitFailed = 2	; broadcast init failed
Const BS_EBroadcastUploadResult_FrameFailed = 3	; broadcast frame upload failed
Const BS_EBroadcastUploadResult_Timeout = 4	; broadcast upload timed out
Const BS_EBroadcastUploadResult_BandwidthExceeded = 5	; broadcast send too much data
Const BS_EBroadcastUploadResult_LowFPS = 6	; broadcast FPS too low
Const BS_EBroadcastUploadResult_MissingKeyFrames = 7	; broadcast sending not enough key frames
Const BS_EBroadcastUploadResult_NoConnection = 8	; broadcast client failed to connect to relay
Const BS_EBroadcastUploadResult_RelayFailed = 9	; relay dropped the upload
Const BS_EBroadcastUploadResult_SettingsChanged = 10	; the client changed broadcast settings 
Const BS_EBroadcastUploadResult_MissingAudio = 11	; client failed to send audio data
Const BS_EBroadcastUploadResult_TooFarBehind = 12	; clients was too slow uploading
Const BS_EBroadcastUploadResult_TranscodeBehind = 13	; server failed to keep up with transcode
;[End Block]

;[Block] Enumeration: ECallback
;------------------------------------------------------------------------------
;! Enumeration - ECallback
;------------------------------------------------------------------------------
Const BS_ECallback_SteamAppListCallbacks					= 3900
Const BS_ECallback_SteamAppsCallbacks						= 1000
Const BS_ECallback_SteamBillingCallbacks					= 400
Const BS_ECallback_SteamContentServerCallbacks				= 600
Const BS_ECallback_SteamControllerCallbacks					= 2800
Const BS_ECallback_SteamFriendsCallbacks					= 300
Const BS_ECallback_SteamGameCoordinatorCallbacks			= 1700
Const BS_ECallback_SteamGameServerCallbacks					= 200
Const BS_ECallback_SteamGameServerItemsCallbacks			= 1500
Const BS_ECallback_SteamGameServerStatsCallbacks			= 1800
Const BS_ECallback_SteamGameStatsCallbacks					= 2000
Const BS_ECallback_SteamHTMLSurfaceCallbacks				= 4500
Const BS_ECallback_SteamMatchmakingCallbacks				= 500
Const BS_ECallback_SteamMusicCallbacks						= 4000
Const BS_ECallback_SteamMusicRemoteCallbacks				= 4100
Const BS_ECallback_SteamNetworkingCallbacks					= 1200
Const BS_ECallback_SteamReservedCallbacks					= 4400
Const BS_ECallback_SteamScreenshotsCallbacks				= 2300
Const BS_ECallback_SteamStreamLauncherCallbacks				= 2600
Const BS_ECallback_SteamStreamClientCallbacks				= 3500
Const BS_ECallback_SteamUserCallbacks						= 100
Const BS_ECallback_SteamUserStatsCallbacks					= 1100
Const BS_ECallback_SteamUtilsCallbacks						= 700
Const BS_ECallback_Steam2AsyncCallbacks						= 1900
Const BS_ECallback_ClientAudioCallbacks						= 2400
Const BS_ECallback_ClientControllerCallbacks				= 2700
Const BS_ECallback_ClientDepotBuilderCallbacks				= 1400
Const BS_ECallback_ClientDeviceAuthCallbacks				= 3000
Const BS_ECallback_ClientFriendsCallbacks					= 800
Const BS_ECallback_ClientHTTPCallbacks						= 2100
Const BS_ECallback_ClientInventoryCallbacks					= 4700
Const BS_ECallback_ClientMusicCallbacks						= 3200
Const BS_ECallback_ClientNetworkDeviceManagerCallbacks		= 3100
Const BS_ECallback_ClientParentalSettingsCallbacks			= 2900
Const BS_ECallback_ClientProductBuilderCallbacks			= 3600
Const BS_ECallback_ClientRemoteStorageCallbacks				= 1300
Const BS_ECallback_ClientRemoteClientManagerCallbacks		= 3300
Const BS_ECallback_ClientRemoteControlManagerCallbacks		= 3800
Const BS_ECallback_ClientReservedCallbacks					= 4300
Const BS_ECallback_ClientScreenshotsCallbacks				= 2200
Const BS_ECallback_ClientShortcutsCallbacks					= 3700
Const BS_ECallback_ClientUGCCallbacks						= 3400
Const BS_ECallback_ClientUnifiedMessagesCallbacks			= 2500
Const BS_ECallback_ClientUserCallbacks						= 900
Const BS_ECallback_ClientUtilsCallbacks						= 1600
Const BS_ECallback_ClientVideoCallbacks						= 4600
Const BS_ECallback_ClientVRCallbacks						= 4200
;[End Block]

;[Block] Enumeration: EChatEntryType
;------------------------------------------------------------------------------
;! Enumeration - EChatEntryType
;------------------------------------------------------------------------------
;//-----------------------------------------------------------------------------
;// Purpose: Chat Entry Types (previously was only friend-to-friend message types)
;//-----------------------------------------------------------------------------
Const BS_EChatEntryType_Invalid								= 0
Const BS_EChatEntryType_ChatMsg								= 1		;// Normal text message from another user
Const BS_EChatEntryType_Typing								= 2		;// Another user is typing (not used in multi-user chat)
Const BS_EChatEntryType_InviteGame							= 3		;// Invite from other user into that users current game
Const BS_EChatEntryType_Emote								= 4		;// text emote message (deprecated should be treated as ChatMsg)
Const BS_EChatEntryType_LobbyGameStart						= 5		;// lobby game is starting (dead - listen for LobbyGameCreated_t callback instead)
Const BS_EChatEntryType_LeftConversation					= 6		;// user has left the conversation ( closed chat window )
Const BS_EChatEntryType_Entered								= 7		;// user has entered the conversation (used in multi-user chat and group chat)
Const BS_EChatEntryType_WasKicked							= 8		;// user was kicked (data: 64-bit steamid of actor performing the kick)
Const BS_EChatEntryType_WasBanned							= 9		;// user was banned (data: 64-bit steamid of actor performing the ban)
Const BS_EChatEntryType_Disconnected						= 10	;// user disconnected
Const BS_EChatEntryType_HistoricalChat						= 11	;// a chat message from user's chat history or offilne message
Const BS_EChatEntryType_Reserved1							= 12
Const BS_EChatEntryType_Reserved2							= 13
Const BS_EChatEntryType_LinkBlocked							= 14	;// a link was removed by the chat filter.
;[End Block]

;[Block] Enumeration: EChatRoomEnterResponse
;------------------------------------------------------------------------------
;! Enumeration - EChatRoomEnterResponse
;------------------------------------------------------------------------------
; Purpose: Chat Room Enter Responses
Const BS_EChatRoomEnterResponseSuccess						= 1		; Success
Const BS_EChatRoomEnterResponseDoesntExist					= 2	; Chat doesn't exist (probably closed)
Const BS_EChatRoomEnterResponseNotAllowed					= 3		; General Denied - You don't have the permissions needed to join the chat
Const BS_EChatRoomEnterResponseFull							= 4			; Chat room has reached its maximum size
Const BS_EChatRoomEnterResponseError						= 5			; Unexpected Error
Const BS_EChatRoomEnterResponseBanned						= 6			; You are banned from this chat room and may not join
Const BS_EChatRoomEnterResponseLimited						= 7		; Joining this chat is not allowed because you are a limited user (no value on account)
Const BS_EChatRoomEnterResponseClanDisabled					= 8	; Attempt to join a clan chat when the clan is locked or disabled
Const BS_EChatRoomEnterResponseCommunityBan					= 9	; Attempt to join a chat when the user has a community lock on their account
Const BS_EChatRoomEnterResponseMemberBlockedYou				= 10 ; Join failed - some member in the chat has blocked you from joining
Const BS_EChatRoomEnterResponseYouBlockedMember				= 11 ; Join failed - you have blocked some member already in the chat
; Const BS_EChatRoomEnterResponseNoRankingDataLobby			= 12  ; No longer used
; Const BS_EChatRoomEnterResponseNoRankingDataUser			= 13  ;  No longer used
; Const BS_EChatRoomEnterResponseRankOutOfRange				= 14 ;  No longer used
;[End Block]

;[Block] Enumeration: EChatSteamIDInstanceFlags
;------------------------------------------------------------------------------
;! Enumeration - EChatSteamIDInstanceFlags
;------------------------------------------------------------------------------
; Special flags for Chat accounts - they go in the top 8 bits
; of the steam ID's "instance" leaving 12 for the actual instances
Const BS_EChatSteamIDInstanceFlags_Mask = $00000FFF ; top 8 bits are flags
Const BS_EChatSteamIDInstanceFlags_Clan = ( BS_EChatSteamIDInstanceFlags_Mask + 1 ) Shr 1	; top bit
Const BS_EChatSteamIDInstanceFlags_Lobby = ( BS_EChatSteamIDInstanceFlags_Mask + 1 ) Shr 2	; next one down etc
Const BS_EChatSteamIDInstanceFlags_MMSLobby = ( BS_EChatSteamIDInstanceFlags_Mask + 1 ) Shr 3	; next one down etc
;[End Block]

;[Block] Enumeration: EControllerSource
;------------------------------------------------------------------------------
;! Enumeration - EControllerSourceMode
;------------------------------------------------------------------------------
Const BS_EControllerSource_None								= 0
Const BS_EControllerSource_LeftTrackpad						= 1
Const BS_EControllerSource_RightTrackpad					= 2
Const BS_EControllerSource_Joystick							= 3
Const BS_EControllerSource_ABXY								= 4
Const BS_EControllerSource_Switch							= 5
Const BS_EControllerSource_LeftTrigger						= 6
Const BS_EControllerSource_RightTrigger						= 7
Const BS_EControllerSource_Gyro								= 8
;[End Block]

;[Block] Enumeration: EControllerSourceMode
;------------------------------------------------------------------------------
;! Enumeration - EControllerSourceMode
;------------------------------------------------------------------------------
Const BS_EControllerSourceMode_None							= 0
Const BS_EControllerSourceMode_Dpad							= 1
Const BS_EControllerSourceMode_Buttons						= 2
Const BS_EControllerSourceMode_FourButtons					= 3
Const BS_EControllerSourceMode_AbsoluteMouse				= 4
Const BS_EControllerSourceMode_RelativeMouse				= 5
Const BS_EControllerSourceMode_JoystickMove					= 6
Const BS_EControllerSourceMode_JoystickCamera				= 7
Const BS_EControllerSourceMode_ScrollWheel					= 8
Const BS_EControllerSourceMode_Trigger						= 9
Const BS_EControllerSourceMode_TouchMenu					= 10
;[End Block]

;[Block] Enumeration: EControllerActionOrigin
;------------------------------------------------------------------------------
;! Enumeration - EControllerActionOrigin
;------------------------------------------------------------------------------
Const BS_EControllerActionOrigin_None						= 0
Const BS_EControllerActionOrigin_A							= 1
Const BS_EControllerActionOrigin_B							= 2
Const BS_EControllerActionOrigin_X							= 3
Const BS_EControllerActionOrigin_Y							= 4
Const BS_EControllerActionOrigin_LeftBumper					= 5
Const BS_EControllerActionOrigin_RightBumper				= 6
Const BS_EControllerActionOrigin_LeftGrip					= 7
Const BS_EControllerActionOrigin_RightGrip					= 8
Const BS_EControllerActionOrigin_Start						= 9
Const BS_EControllerActionOrigin_Back						= 10
Const BS_EControllerActionOrigin_LeftPad_Touch				= 11
Const BS_EControllerActionOrigin_LeftPad_Swipe				= 12
Const BS_EControllerActionOrigin_LeftPad_Click				= 13
Const BS_EControllerActionOrigin_LeftPad_DPadNorth			= 14
Const BS_EControllerActionOrigin_LeftPad_DPadSouth			= 15
Const BS_EControllerActionOrigin_LeftPad_DPadWest			= 16
Const BS_EControllerActionOrigin_LeftPad_DPadEast			= 17
Const BS_EControllerActionOrigin_RightPad_Touch				= 18
Const BS_EControllerActionOrigin_RightPad_Swipe				= 19
Const BS_EControllerActionOrigin_RightPad_Click				= 20
Const BS_EControllerActionOrigin_RightPad_DPadNorth			= 21
Const BS_EControllerActionOrigin_RightPad_DPadSouth			= 22
Const BS_EControllerActionOrigin_RightPad_DPadWest			= 23
Const BS_EControllerActionOrigin_RightPad_DPadEast			= 24
Const BS_EControllerActionOrigin_LeftTrigger_Pull			= 25
Const BS_EControllerActionOrigin_LeftTrigger_Click			= 26
Const BS_EControllerActionOrigin_RightTrigger_Pull			= 27
Const BS_EControllerActionOrigin_RightTrigger_Click			= 28
Const BS_EControllerActionOrigin_LeftStick_Move				= 29
Const BS_EControllerActionOrigin_LeftStick_Click			= 30
Const BS_EControllerActionOrigin_LeftStick_DPadNorth		= 31
Const BS_EControllerActionOrigin_LeftStick_DPadSouth		= 32
Const BS_EControllerActionOrigin_LeftStick_DPadWest			= 33
Const BS_EControllerActionOrigin_LeftStick_DPadEast			= 34
Const BS_EControllerActionOrigin_Gyro_Move					= 35
Const BS_EControllerActionOrigin_Gyro_Pitch					= 36
Const BS_EControllerActionOrigin_Gyro_Yaw					= 37
Const BS_EControllerActionOrigin_Gyro_Roll					= 38
Const BS_EControllerActionOrigin_Count						= 39
;[End Block]

;[Block] Enumeration: EDenyReason
;------------------------------------------------------------------------------
;! Enumeration - EDenyReason
;------------------------------------------------------------------------------
;// Result codes to GSHandleClientDeny/Kick
Const BS_EDenyReason_Invalid								= 0
Const BS_EDenyReason_InvalidVersion							= 1
Const BS_EDenyReason_Generic								= 2
Const BS_EDenyReason_NotLoggedOn							= 3
Const BS_EDenyReason_NoLicense								= 4
Const BS_EDenyReason_Cheater								= 5
Const BS_EDenyReason_LoggedInElseWhere						= 6
Const BS_EDenyReason_UnknownText							= 7
Const BS_EDenyReason_IncompatibleAnticheat					= 8
Const BS_EDenyReason_MemoryCorruption						= 9
Const BS_EDenyReason_IncompatibleSoftware					= 10
Const BS_EDenyReason_SteamConnectionLost					= 11
Const BS_EDenyReason_SteamConnectionError					= 12
Const BS_EDenyReason_ResponseTimedOut						= 13
Const BS_EDenyReason_ValidationStalled						= 14
Const BS_EDenyReason_OwnerLeftGuestUser						= 15
;[End Block]

;[Block] Enumeration: EFriendRelationShip
;------------------------------------------------------------------------------
;! Enumeration - EFriendRelationShip
;------------------------------------------------------------------------------
;//-----------------------------------------------------------------------------
;// Purpose: set of relationships to other users
;//-----------------------------------------------------------------------------
Const BS_EFriendRelationShip_None							= 0
Const BS_EFriendRelationShip_Blocked						= 1	;// this doesn't get stored; the user has just done an Ignore on an friendship invite
Const BS_EFriendRelationShip_RequestRecipient				= 2
Const BS_EFriendRelationShip_Friend							= 3
Const BS_EFriendRelationShip_RequestInitiator				= 4
Const BS_EFriendRelationShip_Ignored						= 5 ;// this is stored; the user has explicit blocked this other user from comments/chat/etc
Const BS_EFriendRelationShip_IgnoredFriend					= 6
Const BS_EFriendRelationShip_Suggested						= 7
Const BS_EFriendRelationShip_Max							= 8 ;// keep this updated
;[End Block]

;[Block] Enumeration: EFriendFlags
;------------------------------------------------------------------------------
;! Enumeration - EFriendFlags
;------------------------------------------------------------------------------
;//-----------------------------------------------------------------------------
;// Purpose: flags for enumerating friends list or quickly checking a the relationship between users
;//-----------------------------------------------------------------------------
Const BS_EFriendFlags_None									= $00
Const BS_EFriendFlags_Blocked								= $01
Const BS_EFriendFlags_FriendshipRequested					= $02
Const BS_EFriendFlags_Immediate								= $04 ;// "regular" friend
Const BS_EFriendFlags_ClanMember							= $08
Const BS_EFriendFlags_OnGameServer							= $10
Const BS_EFriendFlags_HasPlayedWith							= $20 ;// not currently used
Const BS_EFriendFlags_FriendOfFriend						= $40 ;// not currently used
Const BS_EFriendFlags_RequestingFriendship					= $80
Const BS_EFriendFlags_RequestingInfo						= $0100
Const BS_EFriendFlags_Ignored								= $0200
Const BS_EFriendFlags_IgnoredFriend							= $0400
Const BS_EFriendFlags_Suggested								= $0800
Const BS_EFriendFlags_ChatMember							= $1000
Const BS_EFriendFlags_All									= $FFFF
;[End Block]

;[Block] Enumeration: EHTTPMethod
;------------------------------------------------------------------------------
;! Enumeration - EHTTPMethod
;------------------------------------------------------------------------------
; This enum is used in client API methods, do not re-number existing values.

Const BS_EHTTPMethod_Invalid = 0
Const BS_EHTTPMethod_GET = 1
Const BS_EHTTPMethod_HEAD = 2
Const BS_EHTTPMethod_POST = 3
Const BS_EHTTPMethod_PUT = 4
Const BS_EHTTPMethod_DELETE = 5
Const BS_EHTTPMethod_OPTIONS = 6
;Const BS_EHTTPMethod_TRACE = 7
;Const BS_EHTTPMethod_CONNECT = 8
;[End Block]

;[Block] Enumeration: EHTTPStatusCode
;------------------------------------------------------------------------------
;! Enumeration - EHTTPStatusCode
;------------------------------------------------------------------------------
; HTTP Status codes that the server can send in response to a request, see rfc2616 section 10.3 for descriptions
; of each of these.

; Invalid status code (this isn't defined in HTTP, used to indicate unset in our code)
Const BS_EHTTPStatusCode_Invalid =					0

; Informational codes
Const BS_EHTTPStatusCode_100Continue =				100
Const BS_EHTTPStatusCode_101SwitchingProtocols =	101

; Success codes
Const BS_EHTTPStatusCode_200OK =					200
Const BS_EHTTPStatusCode_201Created =				201
Const BS_EHTTPStatusCode_202Accepted =				202
Const BS_EHTTPStatusCode_203NonAuthoritative =		203
Const BS_EHTTPStatusCode_204NoContent =				204
Const BS_EHTTPStatusCode_205ResetContent =			205
Const BS_EHTTPStatusCode_206PartialContent =		206

; Redirection codes
Const BS_EHTTPStatusCode_300MultipleChoices =		300
Const BS_EHTTPStatusCode_301MovedPermanently =		301
Const BS_EHTTPStatusCode_302Found =					302
Const BS_EHTTPStatusCode_303SeeOther =				303
Const BS_EHTTPStatusCode_304NotModified =			304
Const BS_EHTTPStatusCode_305UseProxy =				305
;k_EHTTPStatusCode306Unused =				306, (used in old HTTP spec, now unused in 1.1)
Const BS_EHTTPStatusCode_307TemporaryRedirect =		307

; Error codes
Const BS_EHTTPStatusCode_400BadRequest =			400
Const BS_EHTTPStatusCode_401Unauthorized =			401 ; You probably want 403 or something else. 401 implies you're sending a WWW-Authenticate header and the client can sent an Authorization header in response.
Const BS_EHTTPStatusCode_402PaymentRequired =		402 ; This is reserved for future HTTP specs, not really supported by clients
Const BS_EHTTPStatusCode_403Forbidden =				403
Const BS_EHTTPStatusCode_404NotFound =				404
Const BS_EHTTPStatusCode_405MethodNotAllowed =		405
Const BS_EHTTPStatusCode_406NotAcceptable =			406
Const BS_EHTTPStatusCode_407ProxyAuthRequired =		407
Const BS_EHTTPStatusCode_408RequestTimeout =		408
Const BS_EHTTPStatusCode_409Conflict =				409
Const BS_EHTTPStatusCode_410Gone =					410
Const BS_EHTTPStatusCode_411LengthRequired =		411
Const BS_EHTTPStatusCode_412PreconditionFailed =	412
Const BS_EHTTPStatusCode_413RequestEntityTooLarge =	413
Const BS_EHTTPStatusCode_414RequestURITooLong =		414
Const BS_EHTTPStatusCode_415UnsupportedMediaType =	415
Const BS_EHTTPStatusCode_416RequestedRangeNotSatisfiable = 416
Const BS_EHTTPStatusCode_417ExpectationFailed =		417
Const BS_EHTTPStatusCode_4xxUnknown = 				418 ; 418 is reserved, so we'll use it to mean unknown
Const BS_EHTTPStatusCode_429TooManyRequests	=		429

; Server error codes
Const BS_EHTTPStatusCode_500InternalServerError =	500
Const BS_EHTTPStatusCode_501NotImplemented =		501
Const BS_EHTTPStatusCode_502BadGateway =			502
Const BS_EHTTPStatusCode_503ServiceUnavailable =	503
Const BS_EHTTPStatusCode_504GatewayTimeout =		504
Const BS_EHTTPStatusCode_505HTTPVersionNotSupported = 505
Const BS_EHTTPStatusCode_5xxUnknown =				599
;[End Block]

;[Block] Enumeration: EHTMLMouseButton
;------------------------------------------------------------------------------
;! Enumeration - EHTMLMouseButton
;------------------------------------------------------------------------------
Const BS_EHTMLMouseButton_Left								= 0
Const BS_EHTMLMouseButton_Right								= 1
Const BS_EHTMLMouseButton_Middle							= 2
;[End Block]

;[Block] Enumeration: EHTMLKeyModifiers
;------------------------------------------------------------------------------
;! Enumeration - EHTMLKeyModifiers
;------------------------------------------------------------------------------
Const BS_EHTMLKeyModifiers_None								= 0
Const BS_EHTMLKeyModifiers_AltDown							= 1 Shl 0
Const BS_EHTMLKeyModifiers_CtrlDown							= 1 Shl 1
Const BS_EHTMLKeyModifiers_ShiftDown						= 1 Shl 2
;[End Block]

;[Block] Enumeration: ELaunchOptionType
;------------------------------------------------------------------------------
;! Enumeration - ELaunchOptionType
;------------------------------------------------------------------------------
; Purpose: codes for well defined launch options
Const BS_ELaunchOptionType_None		= 0	; unknown what launch option does
Const BS_ELaunchOptionType_Default		= 1	; runs the game app whatever in default mode
Const BS_ELaunchOptionType_SafeMode	= 2	; runs the game in safe mode
Const BS_ELaunchOptionType_Multiplayer = 3	; runs the game in multiplayer mode
Const BS_ELaunchOptionType_Config		= 4	; runs config tool for this game
Const BS_ELaunchOptionType_OpenVR		= 5	; runs game in VR mode using OpenVR
Const BS_ELaunchOptionType_Server		= 6	; runs dedicated server for this game
Const BS_ELaunchOptionType_Editor		= 7	; runs game editor
Const BS_ELaunchOptionType_Manual		= 8	; shows game manual
Const BS_ELaunchOptionType_Benchmark	= 9	; runs game benchmark
Const BS_ELaunchOptionType_Option1		= 10	; generic run option uses description field for game name
Const BS_ELaunchOptionType_Option2		= 11	; generic run option uses description field for game name
Const BS_ELaunchOptionType_Option3     = 12	; generic run option uses description field for game name
Const BS_ELaunchOptionType_OtherVR		= 13	; runs game in VR mode using the Oculus SDK or other vendor-specific VR SDK
Const BS_ELaunchOptionType_OpenVROverlay = 14	; runs an OpenVR dashboard overlay
Const BS_ELaunchOptionType_Dialog 		= 1000 ; show launch options dialog
;[End Block]

;[Block] Enumeration: ELeaderboardDataRequest
;------------------------------------------------------------------------------
;! Enumeration - ELeaderboardDataRequest
;------------------------------------------------------------------------------
Const BS_ELeaderboardDataRequest_Global						= 0
Const BS_ELeaderboardDataRequest_GlobalAroundUser			= 1
Const BS_ELeaderboardDataRequest_Friends					= 2
Const BS_ELeaderboardDataRequest_Users						= 3
;[End Block]

;[Block] Enumeration: ELeaderboardDisplayType
;------------------------------------------------------------------------------
;! Enumeration - ELeaderboardDisplayType
;------------------------------------------------------------------------------
Const BS_ELeaderboardDisplayType_None						= 0
Const BS_ELeaderboardDisplayType_Numeric					= 1		; simple numerical score
Const BS_ELeaderboardDisplayType_TimeSeconds				= 2		; the score represents a time in seconds
Const BS_ELeaderboardDisplayType_TimeMilliSeconds			= 3		; the score represents a time in milliseconds
;[End Block]

;[Block] Enumeration: ELeaderboardSortMethod
;------------------------------------------------------------------------------
;! Enumeration - ELeaderboardSortMethod
;------------------------------------------------------------------------------
Const BS_ELeaderboardSortMethod_None						= 0
Const BS_ELeaderboardSortMethod_Ascending					= 1		; top-score is lowest number
Const BS_ELeaderboardSortMethod_Descending					= 2		; top-score is highest number
;[End Block]

;[Block] Enumeration: ELeaderboardUploadScoreMethod
;------------------------------------------------------------------------------
;! Enumeration - ELeaderboardUploadScoreMethod
;------------------------------------------------------------------------------
Const BS_ELeaderboardUploadScoreMethod_None					= 0
Const BS_ELeaderboardUploadScoreMethod_KeepBest				= 1		; Leaderboard will keep user's best score
Const BS_ELeaderboardUploadScoreMethod_ForceUpdate			= 2		; Leaderboard will always replace score with specified
;[End Block]

;[Block] Enumeration: EMarketingMessageFlags
;------------------------------------------------------------------------------
;! Enumeration - EMarketingMessageFlags
;------------------------------------------------------------------------------
; Purpose: Marketing message flags that change how a client should handle them
Const BS_EMarketingMessageFlags_None = 0
Const BS_EMarketingMessageFlags_HighPriority = 1 Shl 0
Const BS_EMarketingMessageFlags_PlatformWindows = 1 Shl 1
Const BS_EMarketingMessageFlags_PlatformMac = 1 Shl 2
Const BS_EMarketingMessageFlags_PlatformLinux = 1 Shl 3
;aggregate flags
Const BS_EMarketingMessageFlags_PlatformRestrictions = BS_EMarketingMessageFlags_PlatformWindows Or BS_EMarketingMessageFlags_PlatformMac Or BS_EMarketingMessageFlags_PlatformLinux
;[End Block]

;[Block] Enumeration: EMatchMakingServerResponse
;------------------------------------------------------------------------------
;! Enumeration - EMatchMakingServerResponse
;------------------------------------------------------------------------------
Const BS_EMatchMakingServerResponse_ServerResponded = 0
Const BS_EMatchMakingServerResponse_ServerFailedToRespond = 1
Const BS_EMatchMakingServerResponse_NoServersListedOnMasterServer = 2
;[End Block]

;[Block] Enumeration: ENotificationPosition
;------------------------------------------------------------------------------
;! Enumeration - ENotificationPosition
;------------------------------------------------------------------------------
; Purpose: Possible positions to tell the overlay to show notifications in
Const BS_ENotificationPosition_TopLeft = 0
Const BS_ENotificationPosition_TopRight = 1
Const BS_ENotificationPosition_BottomLeft = 2
Const BS_ENotificationPosition_BottomRight = 3
;[End Block]

;[Block] Enumeration: EOverlayToStoreFlag
;------------------------------------------------------------------------------
;! Enumeration - EOverlayToStoreFlag
;------------------------------------------------------------------------------
;// These values are passed as parameters to the store
Const BS_EOverlayToStoreFlag_None							= 0
Const BS_EOverlayToStoreFlag_AddToCart						= 1
Const BS_EOverlayToStoreFlag_AddToCartAndShow				= 2
;[End Block]

;[Block] Enumeration: EPersonaChange
;------------------------------------------------------------------------------
;! Enumeration - EPersonaChange
;------------------------------------------------------------------------------
;// used in PersonaStateChange_t::m_nChangeFlags to describe what's changed about a user
;// these flags describe what the client has learned has changed recently so on startup you'll see a name avatar & relationship change for every friend
Const BS_EPersonaChange_Name								= $0001
Const BS_EPersonaChange_Status								= $0002
Const BS_EPersonaChange_ComeOnline							= $0004
Const BS_EPersonaChange_GoneOffline							= $0008
Const BS_EPersonaChange_GamePlayed							= $0010
Const BS_EPersonaChange_GameServer							= $0020
Const BS_EPersonaChange_Avatar								= $0040
Const BS_EPersonaChange_JoinedSource						= $0080
Const BS_EPersonaChange_LeftSource							= $0100
Const BS_EPersonaChange_RelationshipChanged					= $0200
Const BS_EPersonaChange_NameFirstSet						= $0400
Const BS_EPersonaChange_FacebookInfo						= $0800
Const BS_EPersonaChange_Nickname							= $1000
Const BS_EPersonaChange_SteamLevel							= $2000
;[End Block]

;[Block] Enumeration: EPersonaState
;------------------------------------------------------------------------------
;! Enumeration - EPersonaState
;------------------------------------------------------------------------------
;//-----------------------------------------------------------------------------
;// Purpose: list of states a friend can be in
;//-----------------------------------------------------------------------------
Const BS_EPersonaState_Offline								= 0 ;// friend is not currently logged on
Const BS_EPersonaState_Online								= 1 ;// friend is logged on
Const BS_EPersonaState_Busy									= 2 ;// user is on but busy
Const BS_EPersonaState_Away									= 3 ;// auto-away feature
Const BS_EPersonaState_Snooze								= 4 ;// auto-away for a long time
Const BS_EPersonaState_LookingToTrade						= 5 ;// Online trading
Const BS_EPersonaState_LookingToPlay						= 6 ;// Online wanting to play
Const BS_EPersonaState_Max									= 7
;[End Block]

;[Block] Enumeration: EP2PSend
;------------------------------------------------------------------------------
;! Enumeration - EP2PSend
;------------------------------------------------------------------------------
; SendP2PPacket() send types
; Typically k_EP2PSendUnreliable is what you want for UDP-like packets k_EP2PSendReliable for TCP-like packets
	; Basic UDP send. Packets can't be bigger than 1200 bytes (your typical MTU size). Can be lost or arrive out of order (rare).
	; The sending API does have some knowledge of the underlying connection so if there is no NAT-traversal accomplished or
	; there is a recognized adjustment happening on the connection the packet will be batched until the connection is open again.
Const BS_EP2PSend_Unreliable								= 0
	; As above but if the underlying p2p connection isn't yet established the packet will just be thrown away. Using this on the first
	; packet sent to a remote host almost guarantees the packet will be dropped.
	; This is only really useful for kinds of data that should never buffer up i.e. voice payload packets
Const BS_EP2PSend_UnreliableNoDelay							= 1
	; Reliable message send. Can send up to 1MB of data in a single message. 
	; Does fragmentation/re-assembly of messages under the hood as well as a sliding window for efficient sends of large chunks of data. 
Const BS_EP2PSend_Reliable									= 2
	; As above but applies the Nagle algorithm to the send - sends will accumulate 
	; until the current MTU size (typically ~1200 bytes but can change) or ~200ms has passed (Nagle algorithm). 
	; Useful if you want to send a set of smaller messages but have the coalesced into a single packet
	; Since the reliable stream is all ordered you can do several small message sends with k_EP2PSendReliableWithBuffering and then
	; do a normal k_EP2PSendReliable to force all the buffered data to be sent.
Const BS_EP2PSend_ReliableWithBuffering						= 3
;[End Block]

;[Block] Enumeration: EP2PSessionError
;------------------------------------------------------------------------------
;! Enumeration - EP2PSessionError
;------------------------------------------------------------------------------
; list of possible errors returned by SendP2PPacket() API
; these will be posted in the P2PSessionConnectFail_t callback
Const BS_EP2PSessionError_None								= 0
Const BS_EP2PSessionError_NotRunningApp						= 1		; target is not running the same game
Const BS_EP2PSessionError_NoRightsToApp						= 2		; local user doesn't own the app that is running
Const BS_EP2PSessionError_DestinationNotLoggedIn			= 3		; target user isn't connected to Steam
Const BS_EP2PSessionError_Timeout							= 4		; target isn't responding perhaps not calling AcceptP2PSessionWithUser()
																	; corporate firewalls can also block this (NAT traversal is not firewall traversal)
																	; make sure that UDP ports 3478 4379 and 4380 are open in an outbound direction
Const BS_EP2PSessionError_Max								= 5
;[End Block]

;[Block] Enumeration: EResult
;------------------------------------------------------------------------------
;! Enumeration - EResult
;------------------------------------------------------------------------------
;// General result codes
Const BS_EResult_OK											= 1		; success
Const BS_EResult_Fail										= 2		; generic failure 
Const BS_EResult_NoConnection								= 3		; no/failed network connection
;Const BS_EResult_NoConnectionRetry							= 4		; OBSOLETE - removed
Const BS_EResult_InvalidPassword							= 5		; password/ticket is invalid
Const BS_EResult_LoggedInElsewhere							= 6		; same user logged in elsewhere
Const BS_EResult_InvalidProtocolVer							= 7		; protocol version is incorrect
Const BS_EResult_InvalidParam								= 8		; a parameter is incorrect
Const BS_EResult_FileNotFound								= 9		; file was not found
Const BS_EResult_Busy										= 10	; called method busy - action not taken
Const BS_EResult_InvalidState								= 11	; called object was in an invalid state
Const BS_EResult_InvalidName								= 12	; name is invalid
Const BS_EResult_InvalidEmail								= 13	; email is invalid
Const BS_EResult_DuplicateName								= 14	; name is not unique
Const BS_EResult_AccessDenied								= 15	; access is denied
Const BS_EResult_Timeout									= 16	; operation timed out
Const BS_EResult_Banned										= 17	; VAC2 banned
Const BS_EResult_AccountNotFound							= 18	; account not found
Const BS_EResult_InvalidSteamID								= 19	; steamID is invalid
Const BS_EResult_ServiceUnavailable							= 20	; The requested service is currently unavailable
Const BS_EResult_NotLoggedOn								= 21	; The user is not logged on
Const BS_EResult_Pending									= 22	; Request is pending (may be in process or waiting on third party)
Const BS_EResult_EncryptionFailure							= 23	; Encryption or Decryption failed
Const BS_EResult_InsufficientPrivilege						= 24	; Insufficient privilege
Const BS_EResult_LimitExceeded								= 25	; Too much of a good thing
Const BS_EResult_Revoked									= 26	; Access has been revoked (used for revoked guest passes)
Const BS_EResult_Expired									= 27	; License/Guest pass the user is trying to access is expired
Const BS_EResult_AlreadyRedeemed							= 28	; Guest pass has already been redeemed by account cannot be acked again
Const BS_EResult_DuplicateRequest							= 29	; The request is a duplicate and the action has already occurred in the past ignored this time
Const BS_EResult_AlreadyOwned								= 30	; All the games in this guest pass redemption request are already owned by the user
Const BS_EResult_IPNotFound									= 31	; IP address not found
Const BS_EResult_PersistFailed								= 32	; failed to write change to the data store
Const BS_EResult_LockingFailed								= 33	; failed to acquire access lock for this operation
Const BS_EResult_LogonSessionReplaced						= 34
Const BS_EResult_ConnectFailed								= 35
Const BS_EResult_HandshakeFailed							= 36
Const BS_EResult_IOFailure									= 37
Const BS_EResult_RemoteDisconnect							= 38
Const BS_EResult_ShoppingCartNotFound						= 39	; failed to find the shopping cart requested
Const BS_EResult_Blocked									= 40	; a user didn't allow it
Const BS_EResult_Ignored									= 41	; target is ignoring sender
Const BS_EResult_NoMatch									= 42	; nothing matching the request found
Const BS_EResult_AccountDisabled							= 43
Const BS_EResult_ServiceReadOnly							= 44	; this service is not accepting content changes right now
Const BS_EResult_AccountNotFeatured							= 45	; account doesn't have value so this feature isn't available
Const BS_EResult_AdministratorOK							= 46	; allowed to take this action but only because requester is admin
Const BS_EResult_ContentVersion								= 47	; A Version mismatch in content transmitted within the Steam protocol.
Const BS_EResult_TryAnotherCM								= 48	; The current CM can't service the user making a request user should try another.
Const BS_EResult_PasswordRequiredToKickSession				= 49	; You are already logged in elsewhere this cached credential login has failed.
Const BS_EResult_AlreadyLoggedInElsewhere					= 50	; You are already logged in elsewhere you must wait
Const BS_EResult_Suspended									= 51	; Long running operation (content download) suspended/paused
Const BS_EResult_Cancelled									= 52	; Operation canceled (typically by user: content download)
Const BS_EResult_DataCorruption								= 53	; Operation canceled because data is ill formed or unrecoverable
Const BS_EResult_DiskFull									= 54	; Operation canceled - not enough disk space.
Const BS_EResult_RemoteCallFailed							= 55	; an remote call or IPC call failed
Const BS_EResult_PasswordUnset								= 56	; Password could not be verified as it's unset server side
Const BS_EResult_ExternalAccountUnlinked					= 57	; External account (PSN Facebook...) is not linked to a Steam account
Const BS_EResult_PSNTicketInvalid							= 58	; PSN ticket was invalid
Const BS_EResult_ExternalAccountAlreadyLinked				= 59	; External account (PSN Facebook...) is already linked to some other account must explicitly request to replace/delete the link first
Const BS_EResult_RemoteFileConflict							= 60	; The sync cannot resume due to a conflict between the local and remote files
Const BS_EResult_IllegalPassword							= 61	; The requested new password is not legal
Const BS_EResult_SameAsPreviousValue						= 62	; new value is the same as the old one ( secret question and answer )
Const BS_EResult_AccountLogonDenied							= 63	; account login denied due to 2nd factor authentication failure
Const BS_EResult_CannotUseOldPassword						= 64	; The requested new password is not legal
Const BS_EResult_InvalidLoginAuthCode						= 65	; account login denied due to auth code invalid
Const BS_EResult_AccountLogonDeniedNoMail					= 66	; account login denied due to 2nd factor auth failure - and no mail has been sent
Const BS_EResult_HardwareNotCapableOfIPT					= 67
Const BS_EResult_IPTInitError								= 68
Const BS_EResult_ParentalControlRestricted					= 69	; operation failed due to parental control restrictions for current user
Const BS_EResult_FacebookQueryError							= 70	; Facebook query returned an error
Const BS_EResult_ExpiredLoginAuthCode						= 71	; account login denied due to auth code expired
Const BS_EResult_IPLoginRestrictionFailed					= 72
Const BS_EResult_AccountLockedDown							= 73
Const BS_EResult_AccountLogonDeniedVerifiedEmailRequired	= 74
Const BS_EResult_NoMatchingURL								= 75
Const BS_EResult_BadResponse								= 76	; parse failure missing field etc.
Const BS_EResult_RequirePasswordReEntry						= 77	; The user cannot complete the action until they re-enter their password
Const BS_EResult_ValueOutOfRange							= 78	; the value entered is outside the acceptable range
Const BS_EResult_UnexpectedError							= 79	; something happened that we didn't expect to ever happen
Const BS_EResult_Disabled									= 80	; The requested service has been configured to be unavailable
Const BS_EResult_InvalidCEGSubmission						= 81	; The set of files submitted to the CEG server are not valid !
Const BS_EResult_RestrictedDevice							= 82	; The device being used is not allowed to perform this action
Const BS_EResult_RegionLocked								= 83	; The action could not be complete because it is region restricted
Const BS_EResult_RateLimitExceeded							= 84	; Temporary rate limit exceeded try again later different from k_EResultLimitExceeded which may be permanent
Const BS_EResult_AccountLoginDeniedNeedTwoFactor			= 85	; Need two-factor code to login
Const BS_EResult_ItemDeleted								= 86	; The thing we're trying to access has been deleted
Const BS_EResult_AccountLoginDeniedThrottle					= 87	; login attempt failed try to throttle response to possible attacker
Const BS_EResult_TwoFactorCodeMismatch						= 88	; two factor code mismatch
Const BS_EResult_TwoFactorActivationCodeMismatch			= 89	; activation code for two-factor didn't match
Const BS_EResult_AccountAssociatedToMultiplePartners		= 90	; account has been associated with multiple partners
Const BS_EResult_NotModified								= 91	; data not modified
Const BS_EResult_NoMobileDevice								= 92	; the account does not have a mobile device associated with it
Const BS_EResult_TimeNotSynced								= 93	; the time presented is out of range or tolerance
Const BS_EResult_SmsCodeFailed								= 94	; SMS code failure (no match none pending etc.)
Const BS_EResult_AccountLimitExceeded						= 95	; Too many accounts access this resource
Const BS_EResult_AccountActivityLimitExceeded				= 96	; Too many changes to this account
Const BS_EResult_PhoneActivityLimitExceeded					= 97	; Too many changes to this phone
Const BS_EResult_RefundToWallet								= 98	; Cannot refund to payment method must use wallet
Const BS_EResult_EmailSendFailure							= 99	; Cannot send an email
Const BS_EResult_NotSettled									= 100	; Can't perform operation till payment has settled
Const BS_EResult_NeedCaptcha								= 101	; Needs to provide a valid captcha
Const BS_EResult_GSLTDenied									= 102	; a game server login token owned by this token's owner has been banned
Const BS_EResult_GSOwnerDenied								= 103	; game server owner is denied for other reason (account lock community ban vac ban missing phone)
Const BS_EResult_InvalidItemType							= 104	; the type of thing we were requested to act on is invalid
;[End Block]

;[Block] Enumeration: ERegisterActivationCodeResult
;------------------------------------------------------------------------------
;! Enumeration - ERegisterActivationCodeResult
;------------------------------------------------------------------------------
;//-----------------------------------------------------------------------------
;// Purpose: possible results when registering an activation code
;//-----------------------------------------------------------------------------
Const BS_ERegisterActivationCodeResult_OK					= 0
Const BS_ERegisterActivationCodeResult_Fail					= 1
Const BS_ERegisterActivationCodeResult_AlreadyRegistered	= 2
Const BS_ERegisterActivationCodeResult_Timeout				= 3
Const BS_ERegisterActivationCodeResult_AlreadyOwned			= 4
;[End Block]

;[Block] Enumeration: EServerMode
;------------------------------------------------------------------------------
;! Enumeration - EServerMode
;------------------------------------------------------------------------------
Const BS_EServerMode_Invalid								= 0;// DO NOT USE		
Const BS_EServerMode_NoAuthentication						= 1;// Don't authenticate user logins and don't list on the server list
Const BS_EServerMode_Authentication							= 2;// Authenticate users list on the server list don't run VAC on clients that connect
Const BS_EServerMode_AuthenticationAndSecure				= 3;// Authenticate users list on the server list and VAC protect clients
;[End Block]

;[Block] Enumeration: EServerFlag
;------------------------------------------------------------------------------
;! Enumeration - EServerFlag
;------------------------------------------------------------------------------
Const BS_EServerFlag_None									= $00
Const BS_EServerFlag_Active									= $01	; Server has Users playing
Const BS_EServerFlag_Secure									= $02	; Server wants to be Secure
Const BS_EServerFlag_Dedicated								= $04	; Server is Dedicated
Const BS_EServerFlag_Linux									= $08	; Linux Build
Const BS_EServerFlag_Passworded								= $10	; Password Protected
Const BS_EServerFlag_Private								= $20	; server shouldn't list on master server and
																	; won't enforce authentication of users that connect to the server.
																	; Useful when you run a server where the clients may not
																	; be connected to the internet but you want them to play (i.e LANs)
;[End Block]

;[Block] Enumeration: ESteamControllerPad
;------------------------------------------------------------------------------
;! Enumeration - EControllerPad
;------------------------------------------------------------------------------
Const BS_ESteamControllerPad_Left							= 0
Const BS_ESteamControllerPad_Right							= 1
;[End Block]

;[Block] Enumeration: ESteamItemFlags
;------------------------------------------------------------------------------
;! Enumeration - ESteamItemFlags
;------------------------------------------------------------------------------
;// Item status flags - these flags are permanently attached to specific item instances
Const BS_ESteamItemFlags_NoTrade = 1 Shl 0 ;// This item is account-locked and cannot be traded or given away.
;// Action confirmation flags - these flags are set one time only, as part of a result set
Const BS_ESteamItemFlags_Removed = 1 Shl 8;// The item has been destroyed, traded away, expired, or otherwise invalidated
Const BS_ESteamItemFlags_Consumed = 1 Shl 9;// The item quantity has been decreased by 1 via ConsumeItem API.
;// All other flag bits are currently reserved for internal Steam use at this time.
;// Do not assume anything about the state of other flags which are not defined here.
;[End Block]

;[Block] Enumeration: ESteamUserStatType
;------------------------------------------------------------------------------
;! Enumeration - ESteamUserStatType
;------------------------------------------------------------------------------
Const BS_ESteamUserStatType_INVALID							= 0
Const BS_ESteamUserStatType_INT								= 1
Const BS_ESteamUserStatType_FLOAT							= 2
;// Read as FLOAT set with count / session length
Const BS_ESteamUserStatType_AVGRATE							= 3
Const BS_ESteamUserStatType_ACHIEVEMENTS					= 4
Const BS_ESteamUserStatType_GROUPACHIEVEMENTS				= 5
;// max for sanity checks
Const BS_ESteamUserStatType_MAX								= 6
;[End Block]

;[Block] Enumeration: EUniverse
;------------------------------------------------------------------------------
;! Enumeration - EUniverse
;------------------------------------------------------------------------------
; Steam universes.  Each universe is a self-contained Steam instance.
Const BS_EUniverse_Invalid = 0
Const BS_EUniverse_Public = 1
Const BS_EUniverse_Beta = 2
Const BS_EUniverse_Internal = 3
Const BS_EUniverse_Dev = 4
;Const BS_EUniverse_RC = 5 ; no such universe anymore
Const BS_EUniverse_Max = 6
;[End Block]

;[Block] Enumeration: EUserHasLicenseResult
;------------------------------------------------------------------------------
;! Enumeration - EUserHasLicenseResult
;------------------------------------------------------------------------------
;// results from UserHasLicenseForApp
Const BS_EUserHasLicenseResult_HasLicense					= 0 ; User has a license for specified app
Const BS_EUserHasLicenseResult_DoesNotHaveLicense			= 1 ; User does not have a license for the specified app
Const BS_EUserHasLicenseResult_NoAuth						= 2 ; User has not been authenticated
;[End Block]

;[Block] Enumeration: EUserRestriction
;------------------------------------------------------------------------------
;! Enumeration - EUserRestriction
;------------------------------------------------------------------------------
;//-----------------------------------------------------------------------------
;// Purpose: user restriction flags
;//-----------------------------------------------------------------------------
Const BS_EUserRestrictionNone								= 0	;// no known chat/content restriction
Const BS_EUserRestrictionUnknown							= 1	;// we don't know yet (user offline)
Const BS_EUserRestrictionAnyChat							= 2	;// user is not allowed to (or can't) send/recv any chat
Const BS_EUserRestrictionVoiceChat							= 4	;// user is not allowed to (or can't) send/recv voice chat
Const BS_EUserRestrictionGroupChat							= 8	;// user is not allowed to (or can't) send/recv group chat
Const BS_EUserRestrictionRating								= 16;// user is too young according to rating in current region
Const BS_EUserRestrictionGameInvites						= 32;// user cannot send or recv game invites (e.g. mobile)
Const BS_EUserRestrictionTrading							= 64;// user cannot participate in trading (console mobile)
;[End Block]

;[Block] Enumeration: EVoiceResult
;------------------------------------------------------------------------------
;! Enumeration - EVoiceResult
;------------------------------------------------------------------------------
;// Error codes for use with the voice functions
Const BS_EVoiceResult_OK									= 0
Const BS_EVoiceResult_NotInitialized						= 1
Const BS_EVoiceResult_NotRecording							= 2
Const BS_EVoiceResult_NoData								= 3
Const BS_EVoiceResult_BufferTooSmall						= 4
Const BS_EVoiceResult_DataCorrupted							= 5
Const BS_EVoiceResult_Restricted							= 6
Const BS_EVoiceResult_UnsupportedCodec						= 7
Const BS_EVoiceResult_ReceiverOutOfDate						= 8
Const BS_EVoiceResult_ReceiverDidNotAnswer					= 9
;[End Block]

;------------------------------------------------------------------------------
;! APIs
;------------------------------------------------------------------------------
;[Block] API: Steam
;[End Block]

;[Block] API: AppList
;------------------------------------------------------------------------------
;! AppList
;------------------------------------------------------------------------------

;//---------------------------------------------------------------------------------
;// Purpose: Sent when a new app is installed
;//---------------------------------------------------------------------------------
Const BS_SteamAppList_AppInstalled							= BS_ECallback_SteamAppListCallbacks + 1
;DEFINE_CALLBACK( SteamAppInstalled_t, k_iSteamAppListCallbacks + 1 );
;	CALLBACK_MEMBER( 0,	AppId_t,	m_nAppID )			// ID of the app that installs
;END_DEFINE_CALLBACK_1()

;//---------------------------------------------------------------------------------
;// Purpose: Sent when an app is uninstalled
;//---------------------------------------------------------------------------------
Const BS_SteamAppList_AppUninstalled							= BS_ECallback_SteamAppListCallbacks + 2
;DEFINE_CALLBACK( SteamAppUninstalled_t, k_iSteamAppListCallbacks + 2 );
;	CALLBACK_MEMBER( 0,	AppId_t,	m_nAppID )			// ID of the app that installs
;END_DEFINE_CALLBACK_1()
;[End Block]

;[Block] API: Apps
;------------------------------------------------------------------------------
;! Apps
;------------------------------------------------------------------------------
Const BS_SteamApps_AppProofOfPurchaseKeyMax = 64;			// max bytes of a legacy cd key we support

;//-----------------------------------------------------------------------------
;// Purpose: posted after the user gains ownership of DLC & that DLC is installed
;//-----------------------------------------------------------------------------
Const BS_SteamApps_DLCInstalled									= BS_ECallback_SteamAppsCallbacks + 5
;struct DlcInstalled_t
;{
;	enum { k_iCallback = k_iSteamAppsCallbacks + 5 };
;	AppId_t m_nAppID;		// AppID of the DLC
;};

;//-----------------------------------------------------------------------------
;// Purpose: response to RegisterActivationCode()
;//-----------------------------------------------------------------------------
Const BS_SteamApps_RegisterActivationCodeResponse				= BS_ECallback_SteamAppsCallbacks + 8
;;struct RegisterActivationCodeResponse_t
;{
;	enum { k_iCallback = k_iSteamAppsCallbacks + 8 };
;	ERegisterActivationCodeResult m_eResult;
;	uint32 m_unPackageRegistered;						// package that was registered. Only set on success
;};

;//-----------------------------------------------------------------------------
;// Purpose: response to RegisterActivationCode()
;//-----------------------------------------------------------------------------
Const BS_SteamApps_AppProofOfPurchaseKeyResponse					= BS_ECallback_SteamAppsCallbacks + 13
;struct AppProofOfPurchaseKeyResponse_t
;{
;	enum { k_iCallback = k_iSteamAppsCallbacks + 13 };
;	EResult m_eResult;
;	uint32	m_nAppID;
;	char	m_rgchKey[ k_cubAppProofOfPurchaseKeyMax ];
;};

;//---------------------------------------------------------------------------------
;// Purpose: posted after the user gains executes a steam url with query parameters
;// such as steam://run/<appid>//?param1=value1;param2=value2;param3=value3; etc
;// while the game is already running.  The new params can be queried
;// with GetLaunchQueryParam.
;//---------------------------------------------------------------------------------
Const BS_SteamApps_NewLaunchQueryParameters						= BS_ECallback_SteamAppsCallbacks + 14
;struct NewLaunchQueryParameters_t
;{
;	enum { k_iCallback = k_iSteamAppsCallbacks + 14 };
;};
;[End Block]

;[Block] API: Client
;------------------------------------------------------------------------------
;! Client
;------------------------------------------------------------------------------
Const BS_SteamClient_SteamAccountIDMask = $FFFFFFFF
Const BS_SteamClient_SteamAccountInstanceMask = $0000FFFF
Const BS_SteamClient_SteamUserDesktopInstance = 1
Const BS_SteamClient_SteamUserConsoleInstance = 2
Const BS_SteamClient_SteamUserWebInstance = 4

; generic invalid CSteamID
Global BS_SteamClient_SteamIDNil = BS_CSteamID_New()
; This steamID comes from a user game connection to an out of date GS that hasnt implemented the protocol
; generic invalid CSteamID2
Global BS_SteamID_Dynamic = BS_CSteamID_New()
; to provide its steamID
Global BS_SteamClient_SteamIDOutOfDateGS = BS_CSteamID_New_IdInstanceUniverseType(0, 0, BS_EUniverse_Invalid, BS_EAccountType_Invalid)
; This steamID comes from a user game connection to an sv_lan GS
Global BS_SteamClient_SteamIDLanModeGS = BS_CSteamID_New_IdInstanceUniverseType(0, 0, BS_EUniverse_Public, BS_EAccountType_Invalid)
; This steamID can come from a user game connection to a GS that has just booted but hasnt yet even initialized
; its steam3 component and started logging on.
Global BS_SteamClient_SteamIDNotInitYetGS = BS_CSteamID_New_IdInstanceUniverseType(1, 0, BS_EUniverse_Invalid, BS_EAccountType_Invalid)
; This steamID can come from a user game connection to a GS that isn't using the steam authentication system but still
; wants to support the "Join Game" option in the friends list
Global BS_SteamClient_SteamIDNonSteamGS = BS_CSteamID_New_IdInstanceUniverseType(2, 0, BS_EUniverse_Invalid, BS_EAccountType_Invalid)

; Constants used for query ports.
Const BS_SteamClient_Query_Port_Not_Initialized = $FFFF ;We haven't asked the GS for this query port's actual value yet.
Const BS_SteamClient_Query_Port_Error = $FFFE ;We were unable to get the query port for this server.

Const BS_SteamClient_GameExtraInfoMax = 64

;[End Block]

;[Block] API: Controller
;------------------------------------------------------------------------------
;! Controller
;------------------------------------------------------------------------------
Const BS_SteamController_Max_Count								= 16
Const BS_SteamController_Max_Analog_Actions						= 16
Const BS_SteamController_Max_Digital_Actions						= 128
Const BS_SteamController_Max_Origins								= 8
;// When sending an option to a specific controller handle, you can send to all controllers via this command
Global BS_SteamController_Handle_All_Controllers					= BS_Long_FromII($FFFFFFFF, $FFFFFFFF)
Const BS_SteamController_Min_Analog_Action_Data#					= -1.0
Const BS_SteamController_Max_Analog_Action_Data#					=  1.0

; Memory Structure: ControllerAnalogActionData_t
; Offs.	Len	Description
;			// Type of data coming from this action, this will match what got specified in the action set
;	0	4	<EControllerSourceMode> eMode
;			// The current state of this action; will be delta updates for mouse actions
;	4	4	<float> x
;	8	4	<float> y
;			// Whether or not this action is currently available to be bound in the active action set
;	12	1	<bool> bActive;

; Memory Structure: ControllerDigitalActionData_t
; Offs.	Len	Description
;			// The current state of this action; will be true if currently pressed
;	0	1	<bool> bState;
;			// Whether or not this action is currently available to be bound in the active action set
;	1	1	<bool> bActive;
;[End Block]

;[Block] API: Friends
;------------------------------------------------------------------------------
;! Friends
;------------------------------------------------------------------------------
;// maximum length of friend group name (not including terminating nul!)
Const BS_SteamFriends_MaxFriendsGroupName						= 64
;// maximum number of groups a single user is allowed
Const BS_SteamFriends_FriendsGroupLimit							= 100
;// invalid friends group identifier constant
Const BS_SteamFriends_GroupID_Invalid							= -1
Const BS_SteamFriends_EnumerateFollowersMax						= 50
;// maximum number of characters in a user's name. Two flavors; one for UTF-8 and one for UTF-16.
;// The UTF-8 version has to be very generous to accomodate characters that get large when encoded
;// in UTF-8.
Const BS_SteamFriends_PersonaNameMax								= 128
Const BS_SteamFriends_PersonaNameMaxW							= 32
;// size limit on chat room or member metadata
Const BS_SteamFriends_ChatMetadataMax							= 8192
;// size limits on Rich Presence data
Const BS_SteamFriends_MaxRichPresenceKeys						= 20
Const BS_SteamFriends_MaxRichPresenceKeyLength					= 64
Const BS_SteamFriends_MaxRichPresenceValueLength					= 256

;// friend game played information
; Memory Structure: FriendGameInfo_t
; Offs.	Len	Description
;	0	4	<CGameID> m_GameID
;	8	4	<Int> m_unGameIP
;	16	2	<Short> m_usGamePort
;	24	2	<Short> m_usQueryPort
;	32	8	<CSteamID> m_steamIDLobby

;//-----------------------------------------------------------------------------
;// Purpose: information about user sessions
;//-----------------------------------------------------------------------------
; Memory Structure: FriendSessionStateInfo_t
; Offs.	Len	Description
;	0	4	<Int> m_uiOnlineSessionInstances
;	4	1	<ByteY m_uiPublishedToFriendsSessionInstance

;! structs below are 8-byte aligned.

;//-----------------------------------------------------------------------------
;// Purpose: called when a friends' status changes
;//-----------------------------------------------------------------------------
Const BS_SteamFriends_PersonaStateChange							= BS_ECallback_SteamFriendsCallbacks + 4
;struct PersonaStateChange_t
;{
;	enum { k_iCallback = k_iSteamFriendsCallbacks + 4 };
;	
;	uint64 m_ulSteamID;		// steamID of the friend who changed
;	int m_nChangeFlags;		// what's changed
;};

;//-----------------------------------------------------------------------------
;// Purpose: posted when game overlay activates or deactivates
;//			the game can use this to be pause or resume single player games
;//-----------------------------------------------------------------------------
Const BS_SteamFriends_GameOverlayActivated						= BS_ECallback_SteamFriendsCallbacks + 31
;struct GameOverlayActivated_t
;{
;	enum { k_iCallback = k_iSteamFriendsCallbacks + 31 };
;	uint8 m_bActive;	// true if it's just been activated, false otherwise
;};

;//-----------------------------------------------------------------------------
;// Purpose: called when the user tries to join a different game server from their friends list
;//			game client should attempt to connect to specified server when this is received
;//-----------------------------------------------------------------------------
Const BS_SteamFriends_GameServerChangeRequested					= BS_ECallback_SteamFriendsCallbacks + 32
;struct GameServerChangeRequested_t
;{
;	enum { k_iCallback = k_iSteamFriendsCallbacks + 32 };
;	char m_rgchServer[64];		// server address ("127.0.0.1:27015", "tf2.valvesoftware.com")
;	char m_rgchPassword[64];	// server password, if any
;};

;//-----------------------------------------------------------------------------
;// Purpose: called when the user tries to join a lobby from their friends list
;//			game client should attempt to connect to specified lobby when this is received
;//-----------------------------------------------------------------------------
Const BS_SteamFriends_GameLobbyJoinRequested						= BS_ECallback_SteamFriendsCallbacks + 33
;struct GameLobbyJoinRequested_t
;{
;	enum { k_iCallback = k_iSteamFriendsCallbacks + 33 };
;	CSteamID m_steamIDLobby;
;
;	// The friend they did the join via (will be invalid if not directly via a friend)
;	//
;	// On PS3, the friend will be invalid if this was triggered by a PSN invite via the XMB, but
;	// the account type will be console user so you can tell at least that this was from a PSN friend
;	// rather than a Steam friend.
;	CSteamID m_steamIDFriend;		
;};

;//-----------------------------------------------------------------------------
;// Purpose: called when an avatar is loaded in from a previous GetLargeFriendAvatar() call
;//			if the image wasn't already available
;//-----------------------------------------------------------------------------
Const BS_SteamFriends_AvatarImageLoaded							= BS_ECallback_SteamFriendsCallbacks + 34
;struct AvatarImageLoaded_t
;{
;	enum { k_iCallback = k_iSteamFriendsCallbacks + 34 };
;	CSteamID m_steamID; // steamid the avatar has been loaded for
;	int m_iImage; // the image index of the now loaded image
;	int m_iWide; // width of the loaded image
;	int m_iTall; // height of the loaded image
;};

;//-----------------------------------------------------------------------------
;// Purpose: marks the return of a request officer list call
;//-----------------------------------------------------------------------------
Const BS_SteamFriends_ClanOfficerListResponse					= BS_ECallback_SteamFriendsCallbacks + 35
;struct ClanOfficerListResponse_t
;{
;	enum { k_iCallback = k_iSteamFriendsCallbacks + 35 };
;	CSteamID m_steamIDClan;
;	int m_cOfficers;
;	uint8 m_bSuccess;
;};

;//-----------------------------------------------------------------------------
;// Purpose: callback indicating updated data about friends rich presence information
;//-----------------------------------------------------------------------------
Const BS_SteamFriends_FriendRichPresenceUpdate					= BS_ECallback_SteamFriendsCallbacks + 36
;struct FriendRichPresenceUpdate_t
;{
;	enum { k_iCallback = k_iSteamFriendsCallbacks + 36 };
;	CSteamID m_steamIDFriend;	// friend who's rich presence has changed
;	AppId_t m_nAppID;			// the appID of the game (should always be the current game)
;};

;//-----------------------------------------------------------------------------
;// Purpose: called when the user tries to join a game from their friends list
;//			rich presence will have been set with the "connect" key which is set here
;//-----------------------------------------------------------------------------
Const BS_SteamFriends_GameRichPresenceJoinRequested				= BS_ECallback_SteamFriendsCallbacks + 37
;struct GameRichPresenceJoinRequested_t
;{
;	enum { k_iCallback = k_iSteamFriendsCallbacks + 37 };
;	CSteamID m_steamIDFriend;		// the friend they did the join via (will be invalid if not directly via a friend)
;	char m_rgchConnect[k_cchMaxRichPresenceValueLength];
;};

;//-----------------------------------------------------------------------------
;// Purpose: a chat message has been received for a clan chat the game has joined
;//-----------------------------------------------------------------------------
Const BS_SteamFriends_GameConnectedClanChatMsg					= BS_ECallback_SteamFriendsCallbacks + 38
;struct GameConnectedClanChatMsg_t
;{
;	enum { k_iCallback = k_iSteamFriendsCallbacks + 38 };
;	CSteamID m_steamIDClanChat;
;	CSteamID m_steamIDUser;
;	int m_iMessageID;
;};

;//-----------------------------------------------------------------------------
;// Purpose: a user has joined a clan chat
;//-----------------------------------------------------------------------------
Const BS_SteamFriends_GameConnectedChatJoin						= BS_ECallback_SteamFriendsCallbacks + 39
;struct GameConnectedChatJoin_t
;{
;	enum { k_iCallback = k_iSteamFriendsCallbacks + 39 };
;	CSteamID m_steamIDClanChat;
;	CSteamID m_steamIDUser;
;};

;//-----------------------------------------------------------------------------
;// Purpose: a user has left the chat we're in
;//-----------------------------------------------------------------------------
Const BS_SteamFriends_GameConnectedChatLeave						= BS_ECallback_SteamFriendsCallbacks + 40
;struct GameConnectedChatLeave_t
;{
;	enum { k_iCallback = k_iSteamFriendsCallbacks + 40 };
;	CSteamID m_steamIDClanChat;
;	CSteamID m_steamIDUser;
;	bool m_bKicked;		// true if admin kicked
;	bool m_bDropped;	// true if Steam connection dropped
;};

;//-----------------------------------------------------------------------------
;// Purpose: a DownloadClanActivityCounts() call has finished
;//-----------------------------------------------------------------------------
Const BS_SteamFriends_DownloadClanActivityCountsResult			= BS_ECallback_SteamFriendsCallbacks + 41
;struct DownloadClanActivityCountsResult_t
;{
;	enum { k_iCallback = k_iSteamFriendsCallbacks + 41 };
;	bool m_bSuccess;
;};

;//-----------------------------------------------------------------------------
;// Purpose: a JoinClanChatRoom() call has finished
;//-----------------------------------------------------------------------------
Const BS_SteamFriends_JoinClanChatRoomCompletionResult			= BS_ECallback_SteamFriendsCallbacks + 42
;struct JoinClanChatRoomCompletionResult_t
;{
;	enum { k_iCallback = k_iSteamFriendsCallbacks + 42 };
;	CSteamID m_steamIDClanChat;
;	EChatRoomEnterResponse m_eChatRoomEnterResponse;
;};

;//-----------------------------------------------------------------------------
;// Purpose: a chat message has been received from a user
;//-----------------------------------------------------------------------------
Const BS_SteamFriends_GameConnectedFriendChatMsg					= BS_ECallback_SteamFriendsCallbacks + 43
;struct GameConnectedFriendChatMsg_t
;{
;	enum { k_iCallback = k_iSteamFriendsCallbacks + 43 };
;	CSteamID m_steamIDUser;
;	int m_iMessageID;
;};

Const BS_SteamFriends_FriendsGetFollowerCount					= BS_ECallback_SteamFriendsCallbacks + 44
;struct FriendsGetFollowerCount_t
;{
;	enum { k_iCallback = k_iSteamFriendsCallbacks + 44 };
;	EResult m_eResult;
;	CSteamID m_steamID;
;	int m_nCount;
;};

Const BS_SteamFriends_FriendsIsFollowing							= BS_ECallback_SteamFriendsCallbacks + 45
;struct FriendsIsFollowing_t
;{
;	enum { k_iCallback = k_iSteamFriendsCallbacks + 45 };
;	EResult m_eResult;
;	CSteamID m_steamID;
;	bool m_bIsFollowing;
;};

Const BS_SteamFriends_FriendsEnumerateFollowingList				= BS_ECallback_SteamFriendsCallbacks + 46
;struct FriendsEnumerateFollowingList_t
;{
;	enum { k_iCallback = k_iSteamFriendsCallbacks + 46 };
;	EResult m_eResult;
;	CSteamID m_rgSteamID[ k_cEnumerateFollowersMax ];
;	int32 m_nResultsReturned;
;	int32 m_nTotalResultCount;
;};

Const BS_SteamFriends_SetPersonaNameResponse						= BS_ECallback_SteamFriendsCallbacks + 47
;struct SetPersonaNameResponse_t
;{
;	enum { k_iCallback = k_iSteamFriendsCallbacks + 47 };
;
;	bool m_bSuccess; // true if name change succeeded completely.
;	bool m_bLocalSuccess; // true if name change was retained locally.  (We might not have been able to communicate with Steam)
;	EResult m_result; // detailed result code
;};

;[End Block]

;[Block] API: GameServer
;------------------------------------------------------------------------------
;! GameServer
;------------------------------------------------------------------------------
Const BS_SteamGameServer_MASTERSERVERUPDATERPORT_USEGAMESOCKETSHARE = $FFFF

;// client has been approved to connect to this game server
Const BS_SteamGameServer_GSClientApprove = BS_ECallback_SteamGameServerCallbacks + 1
;// client has been denied to connection to this game server
Const BS_SteamGameServer_GSClientDeny = BS_ECallback_SteamGameServerCallbacks + 2
;// request the game server should kick the user
Const BS_SteamGameServer_GSClientKick = BS_ECallback_SteamGameServerCallbacks + 3
;// client achievement info
Const BS_SteamGameServer_GSClientAchievementStatus = BS_ECallback_SteamGameServerCallbacks + 6
;// received when the game server requests to be displayed as secure (VAC protected)
;// m_bSecure is true if the game server should display itself as secure to users, false otherwise
Const BS_SteamGameServer_GSPolicyResponse = BS_ECallback_SteamUserCallbacks + 15
;// GS gameplay stats info
Const BS_SteamGameServer_GSGameplayStats = BS_ECallback_SteamGameServerCallbacks + 7
;// send as a reply to RequestUserGroupStatus()
Const BS_SteamGameServer_GSClientGroupStatus = BS_ECallback_SteamGameServerCallbacks + 8
;// Sent as a reply to GetServerReputation()
Const BS_SteamGameServer_GSReputation = BS_ECallback_SteamGameServerCallbacks + 9
;// Sent as a reply to AssociateWithClan()
Const BS_SteamGameServer_AssociateWithClanResult = BS_ECallback_SteamGameServerCallbacks + 10
;// Sent as a reply to ComputeNewPlayerCompatibility()
Const BS_SteamGameServer_ComputeNewPlayerCompatibilityResult = BS_ECallback_SteamGameServerCallbacks + 11

;[End Block]

;[Block] API: GameServerStats
;------------------------------------------------------------------------------
;! GameServerStats
;------------------------------------------------------------------------------

; Purpose: called when the latests stats and achievements have been received
;			from the server
Const BS_SteamGameServerStats_GSStatsReceived = BS_ECallback_SteamUserStatsCallbacks
; Purpose: result of a request to store the user stats for a game
Const BS_SteamGameServerStats_GSStatsStored = BS_ECallback_SteamUserStatsCallbacks + 1
; Purpose: Callback indicating that a user's stats have been unloaded.
;  Call RequestUserStats again to access stats for this user
Const BS_SteamGameServerStats_GSStatsUnloaded = BS_ECallback_SteamUserStatsCallbacks + 8

;[End Block]

;[Block] API: HTTP
;------------------------------------------------------------------------------
;! HTTP
;------------------------------------------------------------------------------
Const BS_SteamHTTP_INVALID_HTTPREQUEST_HANDLE = 0
Const BS_SteamHTTP_INVALID_HTTPCOOKIE_HANDLE = 0

Const BS_SteamHTTP_HTTPRequestCompleted							= BS_ECallback_ClientHTTPCallbacks + 1				
Const BS_SteamHTTP_HTTPRequestHeadersReceived					= BS_ECallback_ClientHTTPCallbacks + 2
Const BS_SteamHTTP_HTTPRequestDataReceived						= BS_ECallback_ClientHTTPCallbacks + 3

;[End Block]

;[Block] API: HTMLSurface
;------------------------------------------------------------------------------
;! HTMLSurface
;------------------------------------------------------------------------------

;//-----------------------------------------------------------------------------
;// Purpose: The browser is ready for use
;//-----------------------------------------------------------------------------
Const BS_SteamHTMLSurface_BrowserReady							= BS_ECallback_SteamHTMLSurfaceCallbacks + 1
;DEFINE_CALLBACK( HTML_BrowserReady_t, k_iSteamHTMLSurfaceCallbacks + 1 )
;CALLBACK_MEMBER( 0, HHTMLBrowser, unBrowserHandle ) // this browser is now fully created and ready to navigate to pages
;END_DEFINE_CALLBACK_1()

;//-----------------------------------------------------------------------------
;// Purpose: the browser has a pending paint
;//-----------------------------------------------------------------------------
Const BS_SteamHTMLSurface_NeedsPaint								= BS_ECallback_SteamHTMLSurfaceCallbacks + 2
;DEFINE_CALLBACK(HTML_NeedsPaint_t, k_iSteamHTMLSurfaceCallbacks + 2)
;CALLBACK_MEMBER(0, HHTMLBrowser, unBrowserHandle) // the browser that needs the paint
;CALLBACK_MEMBER(1, const char *, pBGRA ) // a pointer to the B8G8R8A8 data for this surface, valid until SteamAPI_RunCallbacks is next called
;CALLBACK_MEMBER(2, uint32, unWide) // the total width of the pBGRA texture
;CALLBACK_MEMBER(3, uint32, unTall) // the total height of the pBGRA texture
;CALLBACK_MEMBER(4, uint32, unUpdateX) // the offset in X for the damage rect for this update
;CALLBACK_MEMBER(5, uint32, unUpdateY) // the offset in Y for the damage rect for this update
;CALLBACK_MEMBER(6, uint32, unUpdateWide) // the width of the damage rect for this update
;CALLBACK_MEMBER(7, uint32, unUpdateTall) // the height of the damage rect for this update
;CALLBACK_MEMBER(8, uint32, unScrollX) // the page scroll the browser was at when this texture was rendered
;CALLBACK_MEMBER(9, uint32, unScrollY) // the page scroll the browser was at when this texture was rendered
;CALLBACK_MEMBER(10, float, flPageScale) // the page scale factor on this page when rendered
;CALLBACK_MEMBER(11, uint32, unPageSerial) // incremented on each new page load, you can use this to reject draws while navigating to new pages
;END_DEFINE_CALLBACK_12()

;//-----------------------------------------------------------------------------
;// Purpose: The browser wanted to navigate to a new page
;//   NOTE - you MUST call AllowStartRequest in response to this callback
;//-----------------------------------------------------------------------------
Const BS_SteamHTMLSurface_StartRequest							= BS_ECallback_SteamHTMLSurfaceCallbacks + 3
;DEFINE_CALLBACK(HTML_StartRequest_t, k_iSteamHTMLSurfaceCallbacks + 3)
;CALLBACK_MEMBER(0, HHTMLBrowser, unBrowserHandle) // the handle of the surface navigating
;CALLBACK_MEMBER(1, const char *, pchURL) // the url they wish to navigate to 
;CALLBACK_MEMBER(2, const char *, pchTarget) // the html link target type  (i.e _blank, _self, _parent, _top )
;CALLBACK_MEMBER(3, const char *, pchPostData ) // any posted data for the request
;CALLBACK_MEMBER(4, bool, bIsRedirect) // true if this was a http/html redirect from the last load request
;END_DEFINE_CALLBACK_5()

;//-----------------------------------------------------------------------------
;// Purpose: The browser has been requested to close due to user interaction (usually from a javascript window.close() call)
;//-----------------------------------------------------------------------------
Const BS_SteamHTMLSurface_CloseBrowser							= BS_ECallback_SteamHTMLSurfaceCallbacks + 4
;DEFINE_CALLBACK(HTML_CloseBrowser_t, k_iSteamHTMLSurfaceCallbacks + 4)
;CALLBACK_MEMBER(0, HHTMLBrowser, unBrowserHandle) // the handle of the surface 
;END_DEFINE_CALLBACK_1()

;//-----------------------------------------------------------------------------
;// Purpose: the browser is navigating to a new url
;//-----------------------------------------------------------------------------
Const BS_SteamHTMLSurface_URLChanged 							= BS_ECallback_SteamHTMLSurfaceCallbacks + 5
;DEFINE_CALLBACK( HTML_URLChanged_t, k_iSteamHTMLSurfaceCallbacks + 5 )
;CALLBACK_MEMBER( 0, HHTMLBrowser, unBrowserHandle ) // the handle of the surface navigating
;CALLBACK_MEMBER( 1, const char *, pchURL ) // the url they wish to navigate to 
;CALLBACK_MEMBER( 2, const char *, pchPostData ) // any posted data for the request
;CALLBACK_MEMBER( 3, bool, bIsRedirect ) // true if this was a http/html redirect from the last load request
;CALLBACK_MEMBER( 4, const char *, pchPageTitle ) // the title of the page
;CALLBACK_MEMBER( 5, bool, bNewNavigation ) // true if this was from a fresh tab and not a click on an existing page
;END_DEFINE_CALLBACK_6()

;//-----------------------------------------------------------------------------
;// Purpose: A page is finished loading
;//-----------------------------------------------------------------------------
Const BS_SteamHTMLSurface_FinishedRequest						= BS_ECallback_SteamHTMLSurfaceCallbacks + 6
;DEFINE_CALLBACK( HTML_FinishedRequest_t, k_iSteamHTMLSurfaceCallbacks + 6 )
;CALLBACK_MEMBER( 0, HHTMLBrowser, unBrowserHandle ) // the Handle of the surface 
;CALLBACK_MEMBER( 1, Const char *, pchURL ) // 
;CALLBACK_MEMBER( 2, Const char *, pchPageTitle ) // 
;END_DEFINE_CALLBACK_3()

;//-----------------------------------------------------------------------------
;// Purpose: a request To load this url in a New tab
;//-----------------------------------------------------------------------------
Const BS_SteamHTMLSurface_OpenLinkInNewTab						= BS_ECallback_SteamHTMLSurfaceCallbacks + 7
;DEFINE_CALLBACK( HTML_OpenLinkInNewTab_t, k_iSteamHTMLSurfaceCallbacks + 7 )
;CALLBACK_MEMBER( 0, HHTMLBrowser, unBrowserHandle ) // the Handle of the surface 
;CALLBACK_MEMBER( 1, Const char *, pchURL ) // 
;END_DEFINE_CALLBACK_2()

;//-----------------------------------------------------------------------------
;// Purpose: the page has a New title now
;//-----------------------------------------------------------------------------
Const BS_SteamHTMLSurface_ChangedTitle							= BS_ECallback_SteamHTMLSurfaceCallbacks + 8
;DEFINE_CALLBACK( HTML_ChangedTitle_t, k_iSteamHTMLSurfaceCallbacks + 8 )
;CALLBACK_MEMBER( 0, HHTMLBrowser, unBrowserHandle ) // the Handle of the surface 
;CALLBACK_MEMBER( 1, Const char *, pchTitle ) // 
;END_DEFINE_CALLBACK_2()

;//-----------------------------------------------------------------------------
;// Purpose: results from a search
;//-----------------------------------------------------------------------------
Const BS_SteamHTMLSurface_SearchResults							= BS_ECallback_SteamHTMLSurfaceCallbacks + 9
;DEFINE_CALLBACK( HTML_SearchResults_t, k_iSteamHTMLSurfaceCallbacks + 9 )
;CALLBACK_MEMBER( 0, HHTMLBrowser, unBrowserHandle ) // the Handle of the surface 
;CALLBACK_MEMBER( 1, uint32, unResults ) // 
;CALLBACK_MEMBER( 2, uint32, unCurrentMatch ) // 
;END_DEFINE_CALLBACK_3()

;//-----------------------------------------------------------------------------
;// Purpose: page history status changed on the ability To go backwards And forward
;//-----------------------------------------------------------------------------
Const BS_SteamHTMLSurface_CanGoBackAndForward					= BS_ECallback_SteamHTMLSurfaceCallbacks + 10
;DEFINE_CALLBACK( HTML_CanGoBackAndForward_t, k_iSteamHTMLSurfaceCallbacks + 10 )
;CALLBACK_MEMBER( 0, HHTMLBrowser, unBrowserHandle ) // the Handle of the surface 
;CALLBACK_MEMBER( 1, bool, bCanGoBack ) // 
;CALLBACK_MEMBER( 2, bool, bCanGoForward ) // 
;END_DEFINE_CALLBACK_3()

;//-----------------------------------------------------------------------------
;// Purpose: details on the visibility And size of the horizontal scrollbar
;//-----------------------------------------------------------------------------
Const BS_SteamHTMLSurface_HorizontalScroll						= BS_ECallback_SteamHTMLSurfaceCallbacks + 11
;DEFINE_CALLBACK( HTML_HorizontalScroll_t, k_iSteamHTMLSurfaceCallbacks + 11 )
;CALLBACK_MEMBER( 0, HHTMLBrowser, unBrowserHandle ) // the Handle of the surface 
;CALLBACK_MEMBER( 1, uint32, unScrollMax ) // 
;CALLBACK_MEMBER( 2, uint32, unScrollCurrent ) // 
;CALLBACK_MEMBER( 3, Float, flPageScale ) // 
;CALLBACK_MEMBER( 4, bool , bVisible ) // 
;CALLBACK_MEMBER( 5, uint32, unPageSize ) // 
;END_DEFINE_CALLBACK_6()

;//-----------------------------------------------------------------------------
;// Purpose: details on the visibility And size of the vertical scrollbar
;//-----------------------------------------------------------------------------
Const BS_SteamHTMLSurface_VerticalScroll							= BS_ECallback_SteamHTMLSurfaceCallbacks + 12
;DEFINE_CALLBACK( HTML_VerticalScroll_t, k_iSteamHTMLSurfaceCallbacks + 12 )
;CALLBACK_MEMBER( 0, HHTMLBrowser, unBrowserHandle ) // the Handle of the surface 
;CALLBACK_MEMBER( 1, uint32, unScrollMax ) // 
;CALLBACK_MEMBER( 2, uint32, unScrollCurrent ) // 
;CALLBACK_MEMBER( 3, Float, flPageScale ) // 
;CALLBACK_MEMBER( 4, bool, bVisible ) // 
;CALLBACK_MEMBER( 5, uint32, unPageSize ) // 
;END_DEFINE_CALLBACK_6()

;//-----------------------------------------------------------------------------
;// Purpose: response To GetLinkAtPosition call 
;//-----------------------------------------------------------------------------
Const BS_SteamHTMLSurface_LinkAtPosition							= BS_ECallback_SteamHTMLSurfaceCallbacks + 13
;DEFINE_CALLBACK( HTML_LinkAtPosition_t, k_iSteamHTMLSurfaceCallbacks + 13 )
;CALLBACK_MEMBER( 0, HHTMLBrowser, unBrowserHandle ) // the Handle of the surface 
;CALLBACK_MEMBER( 1, uint32, x ) // NOTE - Not currently set
;CALLBACK_MEMBER( 2, uint32, y ) // NOTE - Not currently set
;CALLBACK_MEMBER( 3, Const char *, pchURL ) // 
;CALLBACK_MEMBER( 4, bool, bInput ) // 
;CALLBACK_MEMBER( 5, bool, bLiveLink ) // 
;END_DEFINE_CALLBACK_6()

;//-----------------------------------------------------------------------------
;// Purpose: show a Javascript alert dialog, call JSDialogResponse 
;//   when the user dismisses this dialog (Or Right away To ignore it)
;//-----------------------------------------------------------------------------
Const BS_SteamHTMLSurface_JSAlert								= BS_ECallback_SteamHTMLSurfaceCallbacks + 14
;DEFINE_CALLBACK( HTML_JSAlert_t, k_iSteamHTMLSurfaceCallbacks + 14 )
;CALLBACK_MEMBER( 0, HHTMLBrowser, unBrowserHandle ) // the Handle of the surface 
;CALLBACK_MEMBER( 1, Const char *, pchMessage ) // 
;END_DEFINE_CALLBACK_2()

;//-----------------------------------------------------------------------------
;// Purpose: show a Javascript confirmation dialog, call JSDialogResponse 
;//   when the user dismisses this dialog (Or Right away To ignore it)
;//-----------------------------------------------------------------------------
Const BS_SteamHTMLSurface_JSConfirm								= BS_ECallback_SteamHTMLSurfaceCallbacks + 15
;DEFINE_CALLBACK( HTML_JSConfirm_t, k_iSteamHTMLSurfaceCallbacks + 15 )
;CALLBACK_MEMBER( 0, HHTMLBrowser, unBrowserHandle ) // the Handle of the surface 
;CALLBACK_MEMBER( 1, Const char *, pchMessage ) // 
;END_DEFINE_CALLBACK_2()

;//-----------------------------------------------------------------------------
;// Purpose: when received show a file open dialog
;//   Then call FileLoadDialogResponse with the file(s) the user selected.
;//-----------------------------------------------------------------------------
Const BS_SteamHTMLSurface_FileOpenDialog							= BS_ECallback_SteamHTMLSurfaceCallbacks + 16
;DEFINE_CALLBACK( HTML_FileOpenDialog_t, k_iSteamHTMLSurfaceCallbacks + 16 )
;CALLBACK_MEMBER( 0, HHTMLBrowser, unBrowserHandle ) // the Handle of the surface 
;CALLBACK_MEMBER( 1, Const char *, pchTitle ) // 
;CALLBACK_MEMBER( 2, Const char *, pchInitialFile ) // 
;END_DEFINE_CALLBACK_3()

;//-----------------------------------------------------------------------------
;// Purpose: a New html window has been created 
;//-----------------------------------------------------------------------------
Const BS_SteamHTMLSurface_NewWindow								= BS_ECallback_SteamHTMLSurfaceCallbacks + 21
;DEFINE_CALLBACK( HTML_NewWindow_t, k_iSteamHTMLSurfaceCallbacks + 21 )
;CALLBACK_MEMBER( 0, HHTMLBrowser, unBrowserHandle ) // the Handle of the current surface 
;CALLBACK_MEMBER( 1, Const char *, pchURL ) // the page To load
;CALLBACK_MEMBER( 2, uint32, unX ) // the x pos into the page To display the popup
;CALLBACK_MEMBER( 3, uint32, unY ) // the y pos into the page To display the popup
;CALLBACK_MEMBER( 4, uint32, unWide ) // the total width of the pBGRA texture
;CALLBACK_MEMBER( 5, uint32, unTall ) // the total height of the pBGRA texture
;CALLBACK_MEMBER( 6, HHTMLBrowser, unNewWindow_BrowserHandle ) // the Handle of the New window surface 
;END_DEFINE_CALLBACK_7()

;//-----------------------------------------------------------------------------
;// Purpose: change the cursor To display
;//-----------------------------------------------------------------------------
Const BS_SteamHTMLSurface_SetCursor								= BS_ECallback_SteamHTMLSurfaceCallbacks + 22
;DEFINE_CALLBACK( HTML_SetCursor_t, k_iSteamHTMLSurfaceCallbacks + 22 )
;CALLBACK_MEMBER( 0, HHTMLBrowser, unBrowserHandle ) // the Handle of the surface 
;CALLBACK_MEMBER( 1, uint32, eMouseCursor ) // the EMouseCursor To display
;END_DEFINE_CALLBACK_2()

;//-----------------------------------------------------------------------------
;// Purpose: informational message from the browser
;//-----------------------------------------------------------------------------
Const BS_SteamHTMLSurface_StatusText								= BS_ECallback_SteamHTMLSurfaceCallbacks + 23
;DEFINE_CALLBACK( HTML_StatusText_t, k_iSteamHTMLSurfaceCallbacks + 23 )
;CALLBACK_MEMBER( 0, HHTMLBrowser, unBrowserHandle ) // the Handle of the surface 
;CALLBACK_MEMBER( 1, Const char *, pchMsg ) // the EMouseCursor To display
;END_DEFINE_CALLBACK_2()

;//-----------------------------------------------------------------------------
;// Purpose: show a tooltip
;//-----------------------------------------------------------------------------
Const BS_SteamHTMLSurface_ShowToolTip							= BS_ECallback_SteamHTMLSurfaceCallbacks + 24
;DEFINE_CALLBACK( HTML_ShowToolTip_t, k_iSteamHTMLSurfaceCallbacks + 24 )
;CALLBACK_MEMBER( 0, HHTMLBrowser, unBrowserHandle ) // the Handle of the surface 
;CALLBACK_MEMBER( 1, Const char *, pchMsg ) // the EMouseCursor To display
;END_DEFINE_CALLBACK_2()

;//-----------------------------------------------------------------------------
;// Purpose: update the Text of an existing tooltip
;//-----------------------------------------------------------------------------
Const BS_SteamHTMLSurface_UpdateToolTip							= BS_ECallback_SteamHTMLSurfaceCallbacks + 25
;DEFINE_CALLBACK( HTML_UpdateToolTip_t, k_iSteamHTMLSurfaceCallbacks + 25 )
;CALLBACK_MEMBER( 0, HHTMLBrowser, unBrowserHandle ) // the Handle of the surface 
;CALLBACK_MEMBER( 1, Const char *, pchMsg ) // the EMouseCursor To display
;END_DEFINE_CALLBACK_2()

;//-----------------------------------------------------------------------------
;// Purpose: hide the tooltip you are showing
;//-----------------------------------------------------------------------------
Const BS_SteamHTMLSurface_HideToolTip							= BS_ECallback_SteamHTMLSurfaceCallbacks + 26
;DEFINE_CALLBACK( HTML_HideToolTip_t, k_iSteamHTMLSurfaceCallbacks + 26 )
;CALLBACK_MEMBER( 0, HHTMLBrowser, unBrowserHandle ) // the Handle of the surface 
;END_DEFINE_CALLBACK_1()
;[End Block]

;[Block] API: Inventory
;------------------------------------------------------------------------------
;! Inventory
;------------------------------------------------------------------------------
Global BS_SteamInventory_SteamItemInstanceIDInvalid = BS_Long_FromII($FFFFFFFF, $FFFFFFFF)
Const BS_SteamInventory_ResultInvalid = -1

;// Types of items in your game are identified by a 32-bit "item definition number".
;// Valid definition numbers are between 1 and 999999999; numbers less than or equal to
;// zero are invalid, and numbers greater than or equal to one billion (1x10^9) are
;// reserved for internal Steam use.

Type BS_SteamInventory_SteamItemDetails
	Field m_ItemIdHigh%, m_ItemIdLow%
	Field m_iDefinition%, m_pad0%
	Field m_unQuantity%, m_pad1%
	Field m_unFlags%, m_pad2%
End Type

; SteamInventoryResultReady_t callbacks are fired whenever asynchronous
; results transition from "Pending" to "OK" or an error state. There will
; always be exactly one callback per handle.
Const BS_SteamInventory_SteamInventoryResultReady = BS_ECallback_ClientInventoryCallbacks

; SteamInventoryFullUpdate_t callbacks are triggered when GetAllItems
; successfully returns a result which is newer / fresher than the last
; known result. (It will not trigger if the inventory hasn't changed,
; or if results from two overlapping calls are reversed in flight and
; the earlier result is already known to be stale/out-of-date.)
; The normal ResultReady callback will still be triggered immediately
; afterwards; this is an additional notification for your convenience.
Const BS_SteamInventory_SteamInventoryFullUpdate = BS_ECallback_ClientInventoryCallbacks + 1

; A SteamInventoryDefinitionUpdate_t callback is triggered whenever
; item definitions have been updated, which could be in response to 
; LoadItemDefinitions() or any other async request which required
; a definition update in order to process results from the server.
Const BS_SteamInventoryDefinitionUpdate = BS_ECallback_ClientInventoryCallbacks + 2

;[End Block]

;[Block] API: Matchmaking
;------------------------------------------------------------------------------
;! Matchmaking
;------------------------------------------------------------------------------
Const BS_SteamMatchmakingLobbyInvite_t = BS_ECallback_SteamMatchmakingCallbacks + 3
Const BS_SteamMatchmakingLobbyEnter_t = BS_ECallback_SteamMatchmakingCallbacks + 4
Const BS_SteamMatchmakingLobbyDataUpdate_t = BS_ECallback_SteamMatchmakingCallbacks + 5
Const BS_SteamMatchmakingLobbyChatUpdate_t = BS_ECallback_SteamMatchmakingCallbacks + 6
Const BS_SteamMatchmakingLobbyChatMsg_t = BS_ECallback_SteamMatchmakingCallbacks + 7
Const BS_SteamMatchmakingLobbyLobbyGameCreated_t = BS_ECallback_SteamMatchmakingCallbacks + 9
Const BS_SteamMatchmakingLobbyMatchList_t = BS_ECallback_SteamMatchmakingCallbacks + 10
Const BS_SteamMatchmakingLobbyKicked_t = BS_ECallback_SteamMatchmakingCallbacks + 12
Const BS_SteamMatchmakingLobbyLobbyCreated_t = BS_ECallback_SteamMatchmakingCallbacks + 13

;[End Block]

;[Block] API: MatchmakingServers
;------------------------------------------------------------------------------
;! MatchmakingServers
;------------------------------------------------------------------------------
;[End Block]

;[Block] API: Music
;------------------------------------------------------------------------------
;! Music
;------------------------------------------------------------------------------
;[End Block]

;[Block] API: MusicRemote
;------------------------------------------------------------------------------
;! MusicRemote
;------------------------------------------------------------------------------
;[End Block]

;[Block] API: Networking
;------------------------------------------------------------------------------
;! Networking
;------------------------------------------------------------------------------
Type BS_P2PSessionState_t
	Field bConnectionActive%, m_pad0%
	Field bConnecting%, m_pad1%
	Field eP2PSessionError%, m_pad2%
	Field bUsingRelay%, m_pad3%
	Field nBytesQueuedForSend%, m_pad4%
	Field nPacketsQueuedForSend%, m_pad5%
	Field nRemoteIP%, m_pad6%
	Field nRemotePort%, m_pad7%
End Type

; callback notification - a user wants to talk to us over the P2P channel via the SendP2PPacket() API
; in response, a call to AcceptP2PPacketsFromUser() needs to be made, if you want to talk with them
Const BS_SteamNetworking_P2PSessionRequest					= BS_ECallback_SteamNetworkingCallbacks + 2
Type BS_P2PSessionRequest_t
	Field steamIDRemoteHigh%, steamIDRemoteLow%				; user who wants to talk to us
End Type

; callback notification - packets can't get through to the specified user via the SendP2PPacket() API
; all packets queued packets unsent at this point will be dropped
; further attempts to send will retry making the connection (but will be dropped if we fail again)
Const BS_SteamNetworking_P2PSessionConnectFail				= BS_ECallback_SteamNetworkingCallbacks + 3
Type BS_P2PSessionConnectFail_t
	Field steamIDRemoteHigh%, steamIDRemoteLow%				; user we were sending packets to
	Field eP2PSessionError									; EP2PSessionError indicating why we're having trouble
End Type
;[End Block]

;[Block] API: RemoteStorage
;------------------------------------------------------------------------------

Const BS_RemoteStoragePublishFileResult_t = BS_ECallback_ClientRemoteStorageCallbacks + 9

;------------------------------------------------------------------------------
;[End Block]

;[Block] API: Screenshots
;------------------------------------------------------------------------------
;! Screenshots
;------------------------------------------------------------------------------
;[End Block]

;[Block] API: UGC
;------------------------------------------------------------------------------

Const BS_SteamUGC_QueryCompleted_t = BS_ECallback_ClientUGCCallbacks + 1
Const BS_SteamUGC_RequestUGCDetailsResult_t = BS_ECallback_ClientUGCCallbacks + 2
Const BS_SteamUGC_CreateItemResult_t = BS_ECallback_ClientUGCCallbacks + 3
Const BS_SteamUGC_SubmitItemUpdateResult_t = BS_ECallback_ClientUGCCallbacks + 4
Const BS_SteamUGC_ItemInstalled_t = BS_ECallback_ClientUGCCallbacks + 5
Const BS_SteamUGC_DownloadItemResult_t = BS_ECallback_ClientUGCCallbacks + 6
Const BS_SteamUGC_UserFavoriteItemsListChanged_t = BS_ECallback_ClientUGCCallbacks + 7
Const BS_SteamUGC_SetUserItemVoteResult_t = BS_ECallback_ClientUGCCallbacks + 8
Const BS_SteamUGC_GetUserItemVoteResult_t = BS_ECallback_ClientUGCCallbacks + 9

;------------------------------------------------------------------------------
;[End Block]

;[Block] API: UnifiedMessages
;------------------------------------------------------------------------------
;! UnifiedMessages
;------------------------------------------------------------------------------
;[End Block]

;[Block] API: User
;------------------------------------------------------------------------------
;! User
;------------------------------------------------------------------------------
Type BS_CallbackMsg_t
	Field m_hSteamUser%
	Field m_iCallback%
	Field m_pubParamPtr%
	Field m_cubParam%
End Type

; Purpose: called when a connections to the Steam back-end has been established
;			this means the Steam client now has a working connection to the Steam servers
;			usually this will have occurred before the game has launched, and should
;			only be seen if the user has dropped connection due to a networking issue
;			or a Steam server update
Const BS_SteamServersConnected = BS_ECallback_SteamUserCallbacks + 1
; Purpose: called when a connection attempt has failed
;			this will occur periodically if the Steam client is not connected, 
;			and has failed in it's retry to establish a connection
Const BS_SteamServerConnectFailure = BS_ECallback_SteamUserCallbacks + 2
; Purpose: called if the client has lost connection to the Steam servers
;			real-time services will be disabled until a matching SteamServersConnected_t has been posted
Const BS_SteamServersDisconnected = BS_ECallback_SteamUserCallbacks + 3
; Purpose: Sent by the Steam server to the client telling it to disconnect from the specified game server,
;			which it may be in the process of or already connected to.
;			The game client should immediately disconnect upon receiving this message.
;			This can usually occur if the user doesn't have rights to play on the game server.
Const BS_ClientGameServerDeny = BS_ECallback_SteamUserCallbacks + 13
; Purpose: called when the callback system for this client is in an error state (and has flushed pending callbacks)
;			When getting this message the client should disconnect from Steam, reset any stored Steam state and reconnect.
;			This usually occurs in the rare event the Steam client has some kind of fatal error.
Const BS_EIPCFailureType_FlushedCallbackQueue = 0
Const BS_EIPCFailureType_PipeFail = 1
Const BS_IPCFailure = BS_ECallback_SteamUserCallbacks + 17
; Purpose: Signaled whenever licenses change
Const BS_LicensesUpdated = BS_ECallback_SteamUserCallbacks + 25
; callback for BeginAuthSession
Const BS_ValidateAuthTicketResponse = BS_ECallback_SteamUserCallbacks + 43
; Purpose: called when a user has responded to a microtransaction authorization request
Const BS_MicroTxnAuthorizationResponse = BS_ECallback_SteamUserCallbacks + 52
; Purpose: Result from RequestEncryptedAppTicket
Const BS_EncryptedAppTicketResponse = BS_ECallback_SteamUserCallbacks + 54
; callback for GetAuthSessionTicket
Const BS_GetAuthSessionTicketResponse = BS_ECallback_SteamUserCallbacks + 63
; Purpose: sent to your game in response to a steam//gamewebcallback/ command
Const BS_GameWebCallback = BS_ECallback_SteamUserCallbacks + 64
; Purpose: sent to your game in response to ISteamUser::RequestStoreAuthURL
Const BS_StoreAuthURLResponse = BS_ECallback_SteamUserCallbacks + 65
;[End Block]

;[Block] API: UserStats
;------------------------------------------------------------------------------
;! UserStats
;------------------------------------------------------------------------------
Const BS_cchStatNameMax										= 128
Const BS_cchLeaderboardNameMax								= 128
Const BS_cLeaderboardDetailsMax								= 64

Type BS_LeaderboardEntry_t
	Field SteamId_High%, SteamId_Low%
	Field nGlobalRank%
	Field nScore%
	Field cDetails%
	Field hUGC_High%, hUGC_Low%
End Type

Const BS_CALLBACK_UserStatsReceived							= BS_ECallback_SteamUserStatsCallbacks + 1
Type BS_ISteamUserStatsReceived_t
	Field nGameId_High%, nGameId_Low%						; Game these stats are for
	Field eResult%											; Success / error fetching the stats
	Field steamIDUser_High%, steamIDUser_Low%				; The user for whom the stats are retrieved for
End Type
Const BS_CALLBACK_UserStatsStored							= BS_ECallback_SteamUserStatsCallbacks + 2
Type BS_ISteamUserStatsStored_t
	Field nGameId_High%, nGameId_Low%						; Game these stats are for
	Field eResult%											; Success / error 
End Type
Const BS_CALLBACK_UserAchievementStored						= BS_ECallback_SteamUserStatsCallbacks + 3
Type BS_UserAchievementStored_t
	Field nGameId_High%, nGameId_Low%						; Game this is for
	Field bGroupAchievement%								; if this is a "group" achievement
	Field rgchAchievementName[BS_cchStatNameMax]			; name of the achievement
	Field nCurProgress										; current progress towards the achievement
	Field nMaxProgress										; "out of" this many
End Type
Const BS_CALLBACK_LeaderboardFindResult						= BS_ECallback_SteamUserStatsCallbacks + 4
Type BS_LeaderboardFindResult_t
	Field hSteamLeaderboard_High%, hSteamLeaderboard_Low%	;	 handle to the leaderboard serarched for, 0 if no leaderboard found
	Field bLeaderboardFound%								;				 0 if no leaderboard found
End Type
Const BS_CALLBACK_LeaderboardScoresDownloaded				= BS_ECallback_SteamUserStatsCallbacks + 5
Type BS_LeaderboardScoresDownloaded_t
	Field hSteamLeaderboard_High, hSteamLeaderboard_Low		;
	Field hSteamLeaderboardEntries_High, hSteamLeaderboardEntries_Low; the handle to pass into GetDownloadedLeaderboardEntries()
	Field cEntryCount										; the number of entries downloaded
End Type
Const BS_CALLBACK_LeaderboardScoreUploaded					= BS_ECallback_SteamUserStatsCallbacks + 6
Type BS_LeaderboardScoreUploaded_t
	Field bSuccess											; 1 if the call was successful
	Field hSteamLeaderboard_High, hSteamLeaderboard_Low		; the leaderboard handle that was
	Field nScore											; the score that was attempted to set
	Field bScoreChanged										; true if the score in the leaderboard change, false if the existing score was better
	Field nGlobalRankNew									; the new global rank of the user in this leaderboard
	Field nGlobalRankPrevious								; the previous global rank of the user in this leaderboard; 0 if the user had no existing entry in the leaderboard
End Type
Const BS_CALLBACK_NumberOfCurrentPlayers					= BS_ECallback_SteamUserStatsCallbacks + 7
Type BS_NumberOfCurrentPlayers_t
	Field bSuccess											; 1 if the call was successful
	Field cPlayers											; Number of players currently playing
End Type
Const BS_CALLBACK_UserStatsUnloaded							= BS_ECallback_SteamUserStatsCallbacks + 8
Type BS_ISteamUserStatsUnloaded_t
	Field steamIDUser_High, steamIDUser_Low					; User whose stats have been unloaded
End Type
Const BS_CALLBACK_UserAchievementIconFetched				= BS_ECallback_SteamUserStatsCallbacks + 9
Type BS_UserAchievementIconFetched_t
	Field nGameID_High, nGameID_Low							; Game this is for
	Field rgchAchievementName[BS_cchStatNameMax]			; name of the achievement
	Field bAchieved											; Is the icon for the achieved or not achieved version?
	Field nIconHandle										; Handle to the image, which can be used in SteamUtils()->GetImageRGBA(), 0 means no image is set for the achievement
End Type
Const BS_CALLBACK_GlobalAchievementPercentagesReady			= BS_ECallback_SteamUserStatsCallbacks + 10
Type BS_GlobalAchievementPercentagesReady_t
	Field nGameID_High, nGameID_Low							; Game this is for
	Field eResult											; Result of the operation
End Type
Const BS_CALLBACK_LeaderboardUGCSet							= BS_ECallback_SteamUserStatsCallbacks + 11
Type BS_LeaderboardUGCSet_t
	Field eResult											; The result of the operation
	Field hSteamLeaderboard_High, hSteamLeaderboard_Low		; the leaderboard handle that was
End Type
Const BS_CALLBACK_GlobalStatsReceived						= BS_ECallback_SteamUserStatsCallbacks + 12
Type BS_GlobalStatsReceived_t
	Field nGameID_High, nGameId_Low							; Game global stats were requested for
	Field eResult											; The result of the request
End Type
;[End Block]

;[Block] API: Utils
;------------------------------------------------------------------------------
;! Utils
;------------------------------------------------------------------------------
;[End Block]

;[Block] API: Video
;------------------------------------------------------------------------------
;! Video
;------------------------------------------------------------------------------
;[End Block]

;~IDEal Editor Parameters:
;~F#13#26#31#49#60#71#7E#93#C8#E0#F5#101#110#121#14F#166#178#190#1A1#1E2
;~F#1EB#1F5#20C#216#220#229#249#254#25E#274#285#29E#2AE#31D#32B#335#345#34D#35A#369
;~F#377#381#392#3A6#3A9#3BF#3F3#413#432#52E#54C#55C#569#65C#6D5#708
;~C#Blitz3D