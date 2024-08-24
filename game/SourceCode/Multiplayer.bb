DebugLog "Multiplayer mod for SCP - Containment Breach"
; ================================================ MULTIPLAYER MOD FOR SCP CONTAINMENT BREACH 1.3.11
Const STEAM_RELEASE = True
Const ALLOW_STEAM_API_TXT = True
Const PATRON_COMPILE = False
; ================================================ GLOBAL CONSTANTS
	Local InitErrorStr$ = ""
	If FileSize("fmod.dll")=0 Then InitErrorStr=InitErrorStr+ "fmod.dll"+Chr(13)+Chr(10)
	If FileSize("zlibwapi.dll")=0 Then InitErrorStr=InitErrorStr+ "zlibwapi.dll"+Chr(13)+Chr(10)
	If STEAM_RELEASE And (FileSize(Chr(14720.0/128)+Chr(14848.0/128)+Chr(12928.0/128)+Chr(12416.0/128)+Chr(13952.0/128)+Chr(12160.0/128)+Chr(12416.0/128)+Chr(14336.0/128)+Chr(13440.0/128)+Chr(5888.0/128)+Chr(12800.0/128)+Chr(13824.0/128)+Chr(13824.0/128))<216000 Or FileSize(Chr(14720.0/128)+Chr(14848.0/128)+Chr(12928.0/128)+Chr(12416.0/128)+Chr(13952.0/128)+Chr(12160.0/128)+Chr(12416.0/128)+Chr(14336.0/128)+Chr(13440.0/128)+Chr(5888.0/128)+Chr(12800.0/128)+Chr(13824.0/128)+Chr(13824.0/128)) > 218000) Then InitErrorStr=InitErrorStr+ Chr(14720.0/128)+Chr(14848.0/128)+Chr(12928.0/128)+Chr(12416.0/128)+Chr(13952.0/128)+Chr(12160.0/128)+Chr(12416.0/128)+Chr(14336.0/128)+Chr(13440.0/128)+Chr(5888.0/128)+Chr(12800.0/128)+Chr(13824.0/128)+Chr(13824.0/128)+Chr(13)+Chr(10)
	If STEAM_RELEASE And FileSize("BlitzSteam.dll")=0 Then InitErrorStr=InitErrorStr+ "BlitzSteam.dll"+Chr(13)+Chr(10)
	If STEAM_RELEASE And FileSize("BlitzPointer.dll")=0 Then InitErrorStr=InitErrorStr+ "BlitzPointer.dll"+Chr(13)+Chr(10)
	
	If Len(InitErrorStr)>0 Then RuntimeError "The following DLLs were not found in the game directory or corrupted:"+Chr(13)+Chr(10)+Chr(13)+Chr(10)+InitErrorStr+Chr(13)+Chr(10)+"Please reinstall the game."
	
	Global Discord_Inited
	
	; =============== STEAM
	
	Include "SourceCode\BlitzSteam.bb"
	Include "SourceCode\BlitzSteamHTML.bb"
	
	Global CurrentSteamID%
	Global CurrentSessionTicketData%
	Global CurrentSessionTicket%
	Global SessionTicketSize%
	
	Global API_SteamHTML
	Global MenuBrowser.SteamBrowser
	
	; ===============

	Steam_API_Init()
	Discord_API_Init()
	
	; ================================================ MULTIPLAYER
		Const SERVER_OFFICIAL = 5
		Const SERVER_LOCAL = 4
		Const SERVER_HISTORY = 3
		Const SERVER_FAVOURITES = 6
		Const SERVER_INTERNET = 1

		Const SERVER_SORT_SERVERS = 1
		Const SERVER_SORT_PLAYERS = 2
		Const SERVER_SORT_MAP = 3
		Const SERVER_SORT_PING = 4
		;
		Const MULTIPLAYER_MOD_HOST_RESERVE$ = "http://lanylow.xyz/"
		Const MULTIPLAYER_MOD_HOST$ = "http://cbmlist.scp-fusion.com/"
		Const PATCH$ = ".5"
		Global MULTIPLAYER_VERSION$
		; 1156.5 = 0.9.9 // +0.5 - new version // rgb hex
		MULTIPLAYER_VERSION = Version(66057)+PATCH
		Const CLIENT_VERSION = 2 ; for mods
		
		Const MAX_BYTES = 10
		Global NETWORK_BYTE[MAX_BYTES+1]
		
		
		
		; ============================================ STATISTIC
			Global STATISTIC_STREAM = 0;CreateUDPStream()
			Global ONLINE_UPDATE
			Const PLAYER_OPEN_GAME = 5
			Const PLAYER_LAUNCH_GAME = 6
			Const PLAYER_EXIT_LAUNCHER = 7
			Const PLAYER_EXIT_GAME = 8
			Const PLAYER_UPDATE_GAME = 9
			Const PLAYER_GO_SITE = 10
			Const PLAYER_IGNORE_UPDATE = 11
			Const PLAYER_GO_PATREON = 12
			Const PLAYER_GO_DISCORD = 13
			Const PLAYER_GO_YOUTUBE = 14
			Const PLAYER_GO_REDDIT = 15
			Const PLAYER_GO_ONLINE = 16
			
			Const STATISTIC_IP = 3000287540
			Const STATISTIC_PORT = 61244
			
			SendStatisticRequest(PLAYER_OPEN_GAME)
		
	; ================================================ LIMITATIONS
		Const MAX_ITEMS = 1000
		Const MAX_DOORS = 500
		Const MAX_NPCs = 255
		Const MAX_PLAYERS = 65
		Const MAX_ROOMS = 200
		Const MAX_CHAT_LINES = 20
		Const MAX_RAGDOLLS = 20
		Const MAX_EVENTS = 110
		
		Const MAX_OBJECTS = 255
		Const MAX_DRAWS = 32
		Const MAX_TEXTS = 32
		Const MAX_CORPSES = 256
		
		Const MAX_SERVERS_IN_PAGE = 12
		Const MAX_SERVER_LIST_PAGES = 14
		
		Const MAX_PLAYERS_IN_PAGE = 8
		Const MAX_PLAYER_LIST_PAGES = 8
		
		Const MAX_DESC_LINES = 20
	; ================================================ MULTIPLAYER POINTERS
		Const DRAW_RECT = 1, DRAW_OVAL = 2
		Const DEVELOPER_MODE = False
; ================================================ GAME POINTERS
	; BREACH ROLES
		Const MAX_BREACH_ROLES = 255
		Const MAX_ROLES_ANIMATIONS = 30
		Type breachtypes
			Field name$
			Field ConstID
			Field model, scale#, texture$
			Field r,g,b
			Field consthp
			Field IsASCP%
			Field hitboxx#, hitboxy#, hitboxz#
			Field Instructions$
			
			Field DeadAnimationData
			Field AmbientSoundAnims$[MAX_ROLES_ANIMATIONS]
			
			Field AnimationFrameData[MAX_ROLES_ANIMATIONS]
			Field AnimationSpeed#[MAX_ROLES_ANIMATIONS]
			
			Field AnimationArmedFrameData[MAX_ROLES_ANIMATIONS]
			Field AnimationArmedSpeed#[MAX_ROLES_ANIMATIONS]
			
			Field Viewmodel%
			Field AnimationViewmodelFrameData[MAX_ROLES_ANIMATIONS]
			Field AnimationViewmodelSpeed#[MAX_ROLES_ANIMATIONS]
			
			Field IsAFriend%[MAX_BREACH_ROLES]
			Field BoneName$[6]
			Field HoldingGrenade, HoldingGrenadeData$[2]
			Field HoldingItem, HoldingItemData#[1]
			Field Handcuffs, HandCuffData$[7]
			Field AllowJump, UsualSprint, DisableCrouch, DisableGodmode, DisableInjuries, DisableSprint, CameraOffset#
			Field AttackType%, Sound$, SoundInterval, HitInterval
			Field CopiedSpawn%
			Field rolloffset#
			Field RoleYawOffset#
			Field HeadPitchFlip%
			Field AllowUpdate%, AllowAttack, AttackRadius#, AttackSound%
			Field AllowWeaponsAttaches%, AllowItemsAttaches%
			Field FixPivot#, FixRotate#, FixPitch#
			Field IsA049, IsA035
			Field RandomSounds
			Field CopiedGroup%
		End Type

		Global BreachTypesArray.breachtypes[MAX_BREACH_ROLES]
		
		Const BONE_HEAD = 1
		Const BONE_HAND = 2
		Const BONE_SPINE = 3
		Const BONE_SPINE2 = 4
		Const BONE_FOREARM = 5

		Const FIRST_BREACH_TYPE = 1
		Global LAST_BREACH_TYPE = 0

		Global NTF_MODEL = 1
		Global GUARD_MODEL = 2
		Global CLASSD_MODEL = 3
		Global SCIENTIST_MODEL = 4
		Global MODEL_173 = 5
		Global MODEL_049 = 6
		Global HAOS_MODEL = 7
		Global JANITOR_MODEL = 8
		Global WORKER_MODEL = 9
		Global MODEL_939 = 10
		Global MODEL_106 = 11
		Global MODEL_966 = 12
		Global MODEL_ZOMBIE = 13
		Global MODEL_096 = 14
		Global MODEL_860 = 15
		Global MODEL_035 = 16
		Global MODEL_CLERK = 17
		Global MODEL_WAIT = 18
	; ==========
	Const PLAYER_SITTING_IDLING = 5
	Const PLAYER_SITTING_WALKING_LEFT = 7
	Const PLAYER_SITTING_WALKING_RIGHT = 8
	Const PLAYER_SITTING_WALKING_BACK = 9
	Const PLAYER_SITTING_WALKING_FORWARD = 10
	Const PLAYER_IDLING = 11
	Const PLAYER_WALKING = 12
	Const PLAYER_RUNNING = 13
	Const PLAYER_CRYING = 14
	Const PLAYER_BITE = 15
	Const PLAYER_BITE_STOP = 16
	Const PLAYER_TO_CRYING = 17
	Const PLAYER_FROM_CRYING = 18
	Const PLAYER_TO_ANGRY = 19
	Const PLAYER_TO_WALK = 20
	Const PLAYER_FROM_WALK = 21
	
	Const ROCKET_SPEED# = 15
	Const BULLET_SPEED# = 1
	
	Const ITEM_MAX_DIVIDE = 4
	Const MULTIPLAYER_EXIT = 3131
	
	Const PREDICTION_FRAMELIMIT = 300
	Function ExecuteFramelimit(LimitFrames, PrevSecs, FrameDelay)
		Delay((1000.0 / LimitFrames) - (PrevSecs - FrameDelay))
	End Function
; ================================================ NETWORK POINTERS
	; backups
		Const M_ITSH = 24
		Const M_ITHD = 21
	;
	Const STEAM_CONNECT = 125
	Const STEAM_TRYCONNECT = 126
	
	Const M_UPDATE = 1
	Const M_ONSERV = 2
	Const M_SOUND = 3
	Const M_SOUNDSTR = 4
	Const M_DECAL = 5
	Const M_START = 6
	Const M_EXPL = 7
	Const M_EVENTS = 8
	Const M_ANNOUNC = 9
	Const M_CRNPC = 10
	Const M_CHAT = 11
	Const M_ISLOAD = 12
	Const M_DISCONNECT = 13
	Const M_ROTATEENTITY = 14
	Const M_KILL = 15
	Const M_RESPAWN = 16
	Const M_UNLOCKEXITS = 18
	Const M_TAKEITEM = 21
	Const M_FIXITEMS = 22
	Const M_DOOR = 23
	Const M_DROPITEM = 24
	Const M_ELEVATOR = 25
	Const M_CONNECT = 26
	Const M_NEWCLIENT = 27
	Const M_ADDBOT = 28
	Const M_VOICE = 29
	Const M_REMOVENPC = 30
	Const M_SERVERUPD = 31
	Const M_SERVERONSERV = 32
	Const M_NEWROOM = 33
	Const M_SERVERLIST = 35
	Const M_SERVERPING = 36
	Const M_FEMUR = 37
	Const M_EVENT173 = 38
	Const M_NPCS = 39
	Const M_SERVERCLOSE = 40
	Const M_STOPSOUND = 42
	Const M_REMOVEITEM = 44
	Const M_SHOOT = 46
	Const M_BREACH = 47
	Const M_SHOOT2 = 48
	Const M_RESETBREACH = 49
	Const M_SYNCDOOR = 50
	Const M_REMOTEDOOR = 51
	Const M_GETROLE = 52
	Const M_KILL2 = 53
	Const M_ANNOUNC2 = 55
	Const M_GIVE = 57
	Const M_REMOVEDOOR = 58
	Const M_INFOITEM = 59
	Const M_SERVERSYNC = 60
	Const M_NPCSYNC = 61
	Const M_SYNCTOSERVER = 62
	Const M_CHATSYNC = 63
	Const M_CREATENPC = 64
	Const M_ITEMSYNC = 65
	Const M_FIXEDITEMS = 66
	Const M_PLAYERSYNC = 67
	Const M_GETRAND = 68
	Const M_GETRND = 69
	Const M_CONNECTBOT = 70
	Const M_CONNECTISFULL = 71
	Const M_CONNECTISSTARTED = 72
	Const M_CONNECTVERSION = 73
	Const M_WANTROLE = 74
	Const M_RESTARTSERVERBREACH = 75
	Const M_BREACHEXPLOSION = 76
	Const M_CONNECTNAME = 77
	Const M_CONNECTBANIP = 78
	Const M_RCONCOMMANDS = 79
	Const M_ROCKETS = 80
	Const M_CREATEROCKET = 81
	Const M_096TRIGGERED = 82

	Const M_EVENTSWRITE = 83
	Const M_ROOMOBJECTS = 84
	Const M_GETLOAD = 85
	Const M_CONNECTPASSWORD = 86

	Const M_WANTITEMS = 87
	Const M_WANTDOORS = 88
	Const M_WANTNPCS = 89
	Const M_WANTEVENTS = 90
	Const M_WANTOBJECTS = 91
	Const M_WANTSERVERINFO = 92
	Const M_OBJECTS = 93
	Const M_DRAWS = 94
	Const M_TEXTS = 95
	Const M_SETPOS = 96
	Const M_CONSOLE = 97
	Const M_MSG = 98
	Const M_LIGHT = 99
	Const M_PING = 100
	Const M_BANKDOWNLOAD = 101
	Const M_SOUND3D = 102
	Const M_EVENTQUERY = 103
	Const m_miscellaneous = 104
	Const M_FOGRANGE = 105
	Const M_MOUSE = 106
	Const M_CREATEBULLET = 107
	Const M_GETPOS = 108
	Const M_REQUESTPLAYERNAME = 109
	Const M_NOTARGET = 110
	Const M_REQUESTLOAD = 111
	Const M_LOADDATA = 112
	Const M_CONNECTERROR = 113
	Const M_RAWPACKET = 114
	Const M_LIFTSTATE = 115
	Const M_CREATEGRENADE = 116
	Const M_WARHEADS = 117
	Const M_GLOBALBLINK = 118
	Const M_GLOBALCONSOLE = 119
	Const M_SAVEPOS = 120
	Const M_PUTHANDCUFFS = 121
	Const M_SENDMISC = 122
	Const M_VOICESTOP = 123
	Const M_HEALTHREDUCE = 124
	Const M_WANTFILES = 125
	Const M_DONECONNECTION = 126
	Const M_WRITEELEVATORS = 127
	Const M_KNIFEDAMAGE = 128
	Const M_OBJECTSOUND = 129
	Const M_WANTINTERCOM = 130
	Const M_CORPSESSYNC = 131
	
	NETWORK_BYTE[1] = M_NPCSYNC
	NETWORK_BYTE[2] = M_SYNCDOOR
	NETWORK_BYTE[3] = M_ROOMOBJECTS
	NETWORK_BYTE[4] = M_FIXEDITEMS
	NETWORK_BYTE[5] = M_EVENTSWRITE
	NETWORK_BYTE[6] = M_WRITEELEVATORS
	NETWORK_BYTE[7] = M_CORPSESSYNC
	
	Const JumpFalling# = -1.0
	Global CurrentFOV#
	
	Global MENU_OPEN_TYPE = 0
	Global TAB_MENU_STATE = 0
	Global PreviousClickedButton%
	Global TAB_MENU_ROLE_INPUT$ = ""
	Global TAB_MENU_ITEM_INPUT$
	Global TAB_MENU_LOBBY_INPUT$
	Global InFocus
	Global DownloadSpeedBytes, DownloadSpeedUpdate, PreviousDownloadPos[2]
	Global ShouldRestartServer%
	Global MyPlayer.players
	Global KeyBufferFactor, KeyBufferCant%
	Global MP_InstructionsDone = GetINIInt(OptionFile, "audio", "mp instructions", 0)
	Dim KeyBuffer%(211)
	
	
	Global WorkshopExecute%
	Global BlockGuns%
	Dim Inventory.Items(MaxItemAmount + 1)
	
	Const MAX_PLAYER_DIRS = 60
; ================================================ MULTIPLAYER INCLUDES
	Include "SourceCode\Guns.bb"
	Include "SourceCode\scripts\gsplugin.bb"
	Include "SourceCode\scripts\gspublics.bb"
	Include "SourceCode\scripts\Skynet++.bb"
	Include "SourceCode\scripts\timers.bb"
	Include "SourceCode\3D_Text_Library\GG_3D_Text_Library.bb"
	Include "SourceCode\ragdolls.bb"
	;Include "SourceCode\FastTCPClient.bb"
; ================================================ MULTIPLAYER TYPES

	Type players
		Field ID
		Field x#, y#, z#,yaw#,BonePitch#
		Field prevX#, prevY#, prevZ#, prevyaw#, prevpitch#, prevpitch2#
		Field obj,Pivot,Camera,PlayerHead,PlayerHand, PlayerSpine%, PlayerForearm, Hitbox%, PlayerHeadOffset#, PlayerForearmOffset#, PlayerForearmOffsetTimed#, PlayerSpine2
		Field name$, nameent.tGG_TextLine, afkent.tGG_TextLine, voiceent.tGG_TextLine, patreonent.tGG_TextLine, IP, Port
		Field BlinkTimer#, AFK, IsDead, Ready$, CrouchState#,PlayerSoundVolume#, UsedWeapon
		Field WearingGasMask,WearingNightVision,WearingHazmat,WearingVest
		Field AttachObjects.Attachs
		Field IsLoad,Timeout
		Field Talking
		Field PlayerRoomName$, PlayerRoomID
		Field ping, lastping
		Field TextureID
		Field BreachType
		Field Announcement, soundchn, Soundtimeout
		Field PLAYER_MOVE,PLAYER_MOVE_TIMED,PLAYER_MOVE_TIMED_PREV,PREV_PLAYER_MOVE,PREV_PLAYER_MOVE2
		Field voicebank, voiceMS#, radiofactor#, intercomfactor#
		Field voiceavail, volume#, voicefactor#
		Field CurrentRadio, SelectedItem
		Field currpage
		Field Health#, PrevHealth#
		Field p_byte, prevbreachtype, prevbreachtypevm
		Field monitor_width, monitor_height
		Field patron
		Field showObjects
		Field wearingdir
		Field PlayerBones[8]
		Field Synced%
		Field MicroHIDSHOOT%, Handcuffed%
		Field prevvoicedelta, voicedelta
		Field voicerelease, OpusDecoder%
		Field tag$, tagr, tagg, tagb
		Field size#,prevsize#
		Field SpeedMult#
		Field steamid
		Field RequestSteamData%
		Field GunRoll#, CurrentGunRoll#
		Field IsShooted%, IsReloaded%, IsClearShoot%, Sighting%
		Field ShouldSendShoot.bs
		Field setplaywith%
		Field insomniaupdate%
	End Type

	Type multiplayer_objects
		Field filename$
		Field obj
		Field Readed
		Field x#, y#, z#, pitch#, yaw#, roll#, lerp, haveAnim
	End Type

	Type p_obj
		Field ID, Received
		Field CorpseEntity
		Field Timeout
		Field FallFactor#
		Field BreachType%
	End Type

	Type errors
		Field txt$
		Field Interval
		Field r,g,b
	End Type

	Type SCPs
		Field Sound, HitInterval, SCP173Factor#, Raged%, ragedupdate
		Field look%[MAX_PLAYERS]
		Field ragestarted%, rageStart#
		Field HitIntervalMax
	End Type

	Type ScriptsThread
		Field scriptThread.SE_Script
		Field path$
	End Type

	Type ServerPage
		Field SERVER_LIST_SORT[4]
		Field SERVER_SORT_TYPE
	End Type

	Type querys
		Field filename$, fullsize, updater, file, queryindex
		Field timeupdater%, currbytes, downloadspeed#
		Field isascript%, compressedfullsize%
		Field tempbank%
	End Type

	Type ServerUDP
		Field Stream%
		Field IP,Port,ServerTimeout,Timeout,EventProb#, ConnectTimeout%, ConnectTries%
		Field IPs$,Ports$
		Field TCPStream, TCPStreamTimeout
		Field JumpHeight#, JumpMode
		Field Maxplayers
		Field Breach, BreachTime, BreachLight
		Field MainPlayer
		Field Tickrate
		Field password$
		Field Servername$
		Field EntitiesLoaded%
		Field keepitems
		Field haveVoice
		Field seeNames, checkVoice, chatOpen
		Field MyID, PlayersCount
		Field voicerate
		Field LoadedObjects
		Field drawsTicks, objectsTicks, textsTicks, chatTicks
		Field pspawnx#, pspawny#, pspawnz#, pspawnname$
		Field hpbar, downloadspeed#
		Field Hosted
		Field CurrentUpdate
		Field ServerCustomMap$, ServerMapLoaded
		Field NetworkTicks%
		Field LongCulling%, MapSize%
		Field SteamStream, CurrentLobby, CurrentLobbyType
		Field FullSync%
		Field MyPosX#, MyPosY#, MyPosZ#, PositionTick#, DetectIncorrect%, IncorrectTicks%
		Field Jumped%
		Field Prediction%, Interpolation%
		Field warheadschannel%, sirenchannel%
		Field notshouldplaysiren%
		Field NoclipAnticheat%
		;Field PacketLossPercent%
		;Field PlayerDirs%, PlayerDirsHeap%
		;Field PlayerDir[MAX_PLAYER_DIRS], PlayerDirAngle#[MAX_PLAYER_DIRS], PlayerDirPitch#[MAX_PLAYER_DIRS], PlayerDirFactor#[MAX_PLAYER_DIRS]
		Field HaveFiles
		Field MenuHTML$
		Field RestartMenuHTML$
		;Field MemX#[MAX_PLAYER_DIRS], MemY#[MAX_PLAYER_DIRS], MemZ#[MAX_PLAYER_DIRS]
	End Type

	Type Attachs
		Field HazmatSuit%,BalVest%,HeavyBalVest%,BulkyBalVest%,GasMaskObj%,NVGobj%,BallisticVest,Gun[GUN_GRENADESMOKE]
		Field NewYearHat, NYHatBone.rd_bone[2], NYHatForce#[2]
	End Type

	Type udp_net
		Field stream, messip, messport, myport%
		Field ReceivedAvail#, WriteAvail#
		Field recv, msgip, msgport
		Field nettime[4], nettimeout[4]
		Field netbyte
		Field myip%
		Field availread%
		Field buffer%, bufferreceive%, bufferwrited%, bufferreaded%
		Field CSteamID_Buff%
		Field CurTime%, ElapsedTime#, PrevTime%, Factor#, PrevFactor#
	End Type

	Type multiplayer_texts
		Field fntname$, fntsize#, font, txtColor
		Field txt$
		Field x, y, Readed
	End Type

	Type rcn
		Field Authorized%
	End Type

	Type WorkshopThread
		Field scriptfile$, scriptpath$
		Field scriptThread.SE_Script
		Field IsLanguage$, LanguageImg%
	End Type

	Type grenades
		Field ID
		Field Pivot, obj
		Field speed#, rollcurr#, angle#
		Field channel
		Field synced
		Field constpitch#
		Field constyaw#
		Field rocketparticle.emitters
		Field timer#
		Field prevfloor, prevy#
		Field xspeed#
		Field state
		Field CollisionTimer#
		Field ticks%
		Field shooter%
		Field grenadetype
		Field fallingspeed#
		Field goteffect%
	End Type
	
	Type Servers
		Field sType
		Field IP$
		Field Port$
		Field Password$
		Field ping
		Field map$
		Field players$
		Field nocheat
		Field Started
		Field state$
		Field Servername$
		Field pms,ims,ums
		Field server
		Field timed
		Field Breach
		Field spage
		Field shostip
		Field voice
		Field description$
		Field version$
		Field weburl$
		Field playerscount%
		Field desclines$[20]
		Field secured%
	End Type

	Type BlacklistedServers
		Field IP$
	End Type

	Type TempServers
		Field sType
		Field IP$
		Field Port$
		Field Password$
		Field ping
		Field map$
		Field players$
		Field nocheat
		Field Started
		Field state$
		Field Servername$
		Field pms,ims,ums
		Field server
		Field timed
		Field Breach
		Field spage
		Field shostip
		Field voice
		Field forcename$
		Field description$
		Field version$
		Field weburl$
		Field desclines$[20]
		Field playerscount%
		Field secured%
	End Type

	Type ChatMessage
		Field Txt$
		Field Timer%, Factor#
		Field messageID%, Formatted%
	End Type

	Type draws
		Field filename$, drawtype, pointer
		Field x, y, width, height, Readed, drawColor
	End Type

	Type snd3d
		Field fmod%
		Field sound%
		Field soundchn%
		Field range#
		Field volume#
		Field entity%
		Field tempentity%
	End Type

	Type spec
		Field SpectateType
		Field SpectatePlayer
		Field cam_pitch#
		Field cam_yaw#
		Field targetpitch#
		Field targetyaw#
	End Type

	Type MultiplayerImages
		Field AFKIMG
		Field VoicePL
		Field VoiceME
		Field VoiceNO
		Field SoundIMG
		Field list_nocheat
		Field list_voice
		Field menu_image[2], arrowimg[2]
		Field load_gif
		Field lock_img, lockr_img
		Field Smile%,Smile2%
		Field Friend%
	End Type

	Type rockets
		Field ID
		Field Pivot, obj
		Field rocketspeed#, rocketspeedSmooth#
		Field channel
		Field synced
		Field constpitch#
		Field constyaw#
		Field rocketparticle.Emitters
		Field timer#
		Field shooter%
	End Type

	Type IntercomSystem
		Field TimeIntercom%
		Field CheckIntercom%
		Field IntercomTimeout%
		Field Picked%
	End Type

	Type announcements
		Field file$
	End Type

	Type breach
		Field WonTimer#, WonCategory$, wonr, wong, wonb
		Field LobbyTimer
		Field CurrentBreachTime
		Field BreachTimer
		Field BreachUpdate
		Field YouAreTimer, YouAreMsg$
	End Type
	
	Type WorkshopItemTemplates
	
	End Type

; ================================================ VARIABLES

	; ================================================ DIM
		
	; ================================================ TYPE GLOBAL
		Global SERVER_LIST_STREAM = CreateUDPStream()
		Global ServerPages.ServerPage[10]
		Global Player.players[MAX_PLAYERS]
		Global multiplayer_Object.multiplayer_objects[MAX_OBJECTS]
		Global multiplayer_Text.multiplayer_texts[MAX_TEXTS]
		Global multiplayer_Draw.draws[MAX_DRAWS]
		
		Global NetworkServer.ServerUDP = New ServerUDP
		Global PlayerIntercom.IntercomSystem = New IntercomSystem
		Global SCP.SCPs = New SCPs
		Global b_br.breach = New breach
		Global rcon.rcn = New rcn
		Global Spectate.spec = New spec
		Global mpimg.MultiplayerImages = New MultiplayerImages
		Global m.MEMORYSTATUS = New MEMORYSTATUS

		Global udp_network.udp_net
		Global M_Corpse.p_obj[MAX_CORPSES]

	; ================================================ GLOBAL - too more globals, please remove unnecessary
		Global MILLISECS_SYS[10]
		Global error_rate
		Global HOST_SERVER_BUTTON_TEXT$ = "CREATE LOBBY"
		Global OutSCP% = True, SavedAngle#
		Global RadioUse
		Global NPCcount
		Global IPnet$
		Global Portnet$
		Global GameLoad
		Global Nickname$
		Global Ready$
		Global ConnectMenu
		Global NoCheat
		Global GlobalTimerMP
		Global SavePosX#
		Global SavePosY#
		Global SavePosZ#
		Global SavePosA#
		Global SaveRoom$
		Global ServerScript
		Global SelectedWord
		Global KeysDelete
		Global KeysDelete2
		Global TypedChatMsg$
		Global JumpState#
		Global LastXSpeed#
		Global LastZSpeed#
		Global GlobalTimerMP2
		Global GUARD_OBJECT
		Global HAOS_OBJECT
		Global VEST_OBJ
		Global GASMASK_OBJ
		Global NVG_OBJ
		Global mainplayersvolume#
		Global GateBSirenSFX%
		Global ServerMenuOpen
		Global SCRIPT_COUNT
		Global WORKSHOP_SCRIPT_COUNT
		Global PatternRecoil#
		Global loading_frame#
		; === objects
		Global NY_HAT, EASTER_EGG_MODEL
		Global NTF_OBJECT
		Global HAZMAT_OBJECT
		Global PISTOL_OBJECT
		Global OBJECT_173
		Global OBJECT_860
		Global OBJECT_096
		Global P90_OBJECT
		Global MP5_OBJECT
		Global RPG_OBJECT
		Global GRENADE_OBJECT
		Global GRENADESMOKE_OBJECT
		Global GRENADEFLASHBANG_OBJECT
		Global KNIFE_OBJECT
		Global HANDCUFFS_OBJECT
		Global ROCKET_OBJECT
		Global MINIGUN_OBJECT
		Global MICROHID_OBJECT
		Global DEAGLE_OBJECT
		Global SHOTGUN_OBJECT
		Global M4A4_OBJECT
		Global HKG36_OBJECT
		Global SURGEON_ZOMBIE
		Global OBJECT_035
		Global NEWYEARINDEX% = IsANewYear()
		Global HALLOWEENINDEX% = IsAHalloween()
		;
		Global CurrentCompressedBank
		Global CurrentUnCompressedBank
		Global SELECTED_SERVERS = SERVER_OFFICIAL
		Global bellchannel
		Global SELECTED_PAGE, SELECTED_P_PAGE
		Global GlobalServerUpdate
		Global PLAYER_MOVE,PREV_PLAYER_MOVE,PLAYER_MOVE_TIMED
		Global AddServerMenu
		Global PasswordMenu
		Global pingcheck
		Global Password$
		Global ItemAmount%
		Global NearObject
		Global NearObjectQueue
		Global SelectServer
		Global SelectedSettings
		
		Global otherindex, otherindex2, otherindex3, otherindex4, otherindex5, otherindex6, otherindexstr$
		Global STEAM_RICH_PRESENCE_UPDATE
		Global MULTIPLAYER_GET_LIMIT
		Global UPDATE_DOORS_DISTANCE
		Global HalloweenScene[4]
		Global PredictionDelay%, YInterval%
		Global CurrentPositionID%
		Global Multiplayer_Models$[65535]
		Function PrepareModelIdentifier(ID, filename$)
			Multiplayer_Models[ID]=filename
		End Function
		
		Global MicroByte.bs = CreateByteStream(8192)
	; ================================================ mp voice
		Global vSrc
		Global vDest
		; -
		Global ServerPing
		Global ServerMil
		Global LastConn
		
		Include "SourceCode\voice.bb"
; ================================================ CHANGED VARIABLES
	Spectate\SpectatePlayer = -1
	SelectServer = -1
	SelectedWord = -1
	Ready = ""
	JumpState = JumpFalling
	Spectate\cam_pitch=-25
	Spectate\cam_yaw=180
	Spectate\targetpitch=-25
	Spectate\targetyaw=180
	PlayerIntercom\IntercomTimeout = 20000
	NetworkServer\CurrentLobbyType = True
	NetworkServer\MapSize = 3
	udp_network = UDP_Init()
	For i = 0 To 10
		ServerPages[i] = New ServerPage
		
		For x = 0 To 4
			ServerPages[i]\SERVER_LIST_SORT[x] = -1
		Next
	Next
; ================================================ FUNCTIONS
Function IsValidPlayer(playerid)
	Return (playerid <= NetworkServer\Maxplayers)
End Function
Function DebugLog(z$)
End Function
Function multiplayer_Receive(byteID = 0)
	If Not udp_GetStream() Then Return
	;Local ID,prevgameload = GameLoad,playerid.players,savepath$,timedsave$
	;Local apply, M_ID
	;Local otherindex, otherindex2, otherindex3, otherindex4, otherindex5, otherindex6, otherindexstr$
	;Local floatx#, floaty#, floatz#, floatyaw#, floatpitch#, floatroll#, floatscale#, animTime
	;Local n.NPCs, e.Events, it.Items, d.Doors, r.Rooms, i, a, f

;	For i = 0 To 5
;		NetworkPacket[otherindex] = false
;	Next

	If NetworkServer\MainPlayer And (Not NetworkServer\Hosted) Then multiplayer_SetServerTime((MilliSecs()+NetworkServer\ServerTimeout)+2000)
	
	While udp_RecvUDPMsg()
		udp_CountAvail(udp_ReadAvail()) ; Adding to receive size
		
		packetid = udp_ReadByte() ; Packet id
		playerid = udp_ReadByte() ; player send ID

		; ========= Receiving
		If byteID <> 0 Then
			If Not (packetid <> byteID) Then 
				multiplayer_GetPacket(packetid, playerid)
			EndIf
		Else
			multiplayer_GetPacket(packetid, playerid)
		EndIf
	Wend
	If NetworkServer\SteamStream Then
		; For self packets
		While udp_RecvUDPMsg(True)
			packetid = udp_ReadByte() ; Packet id
			playerid = udp_ReadByte() ; player send ID

			; ========= Receiving
			If byteID <> 0 Then
				If Not (packetid <> byteID) Then 
					multiplayer_GetPacket(packetid, playerid)
				EndIf
			Else
				multiplayer_GetPacket(packetid, playerid)
			EndIf
		Wend
	EndIf
	
	;if NetworkServer\TCPStream <> 0 Then
	;	While TCPRecvMsg(NetworkServer\TCPStream)
	;		packetid = tcp_ReadByte() ; Packet id
	;		playerid = tcp_ReadByte() ; player send ID
	;	
	;		multiplayer_GetTCPPacket(packetid, playerid)
	;	Wend
	;EndIf
End Function
;Function multiplayer_GetTCPPacket(packetid, playerid)
;	Select packetid
;		Case M_LOADDATA
;			Local bnkname$ = tcp_ReadLine()
;			Local offset = -1
;			Local fullsize = 0
;			For q.querys = Each querys
;				if q\filename = bnkname Then
;					offset = tcp_ReadInt()
;					tcp_ReadInt()
;					if offset = FilePos(q\file) Then 
;						bnk = CreateBank(tcp_ReadAvail()-1)
;						tcp_ReadBytes bnk, 0, BankSize(bnk)
;						WriteBytes bnk, q\file, 0, BankSize(bnk)
;						q\currbytes = q\currbytes + BankSize(bnk)
;						FreeBank bnk
;					EndIf
;					
;					Return
;				EndIf
;			Next
;			
;			if offset = -1 Then
;				tcp_ReadInt()
;				q = New querys
;				q\fullsize = tcp_ReadInt()
;				q\filename = bnkname
;				bnk = CreateBank(tcp_ReadAvail()-1)
;				tcp_ReadBytes bnk, 0, BankSize(bnk)
;				FreeBank bnk
;				q\isascript = tcp_ReadByte()
;				if FileSize("multiplayer\serversdata\"+q\filename) = q\fullsize Then
;					udp_WriteByte M_REQUESTLOAD
;					udp_WriteByte NetworkServer\MyID
;					udp_WriteLine q\filename
;					udp_WriteInt q\fullsize
;					udp_WriteShort NetworkServer\downloadspeed
;					udp_SendMessage()
;					Delete q
;				Else
;					q\file = WriteFileDir("multiplayer\serversdata\"+q\filename)
;				EndIf
;			EndIf
;			
;	End Select
;End Function

; Function to get the system's MAC address


Function multiplayer_GetMicroBytePacket(packetid%, ID%, playerid.players)
	Local floatx#, floaty#, floatz#, floatyaw#, floatpitch#, floatroll#, floatscale#
	
	Select packetid%
		Case M_MOUSE
			udp_ReadShort()
			udp_ReadShort()
		Case M_HEALTHREDUCE
			udp_ReadLine()
			udp_ReadShort()
		Case M_REMOVEITEM
			otherindex = udp_ReadShort()
			If ID <> NetworkServer\MyID Then
				For it.Items = Each Items
					If it\ID = otherindex Then
						RemoveItem(it, False)
						Exit
					EndIf
				Next
			EndIf
		Case M_TAKEITEM
			otherindex = udp_ReadShort()
			For it.Items = Each Items
				If it\ID = otherindex And it\picker = 0 Then
					it\picker = ID
					HideEntity it\collider
					it\Picked = True
					Exit
				EndIf
			Next
		Case M_DROPITEM
			otherindex = udp_ReadShort()
			For it.Items = Each Items
				If it\ID = otherindex And it\picker = ID Then
					PlayerDropItem(it)
					Exit
				EndIf
			Next
		Case M_GLOBALCONSOLE
			OnPlayerConsole(ID, udp_ReadLine())
		Case M_DOOR

			otherindex = udp_ReadShort()
			otherindex2 = udp_ReadByte()
			otherindex3 = udp_ReadByte()
			udp_ReadShort()
			udp_ReadLine()
			
			If ID <> NetworkServer\MyID Then
				For d.Doors = Each Doors
					If d\ID = otherindex Then
						d\locked = otherindex3
						If d\open <> otherindex2 Then UseDoor(d, False, True, False, "", True)
						Exit
					EndIf
				Next
			EndIf

			For p.players = Each players
				If p\ID <> ID And p\ID <> 1 Then
					udp_WriteByte M_DOOR
					udp_WriteByte 1
					udp_WriteShort otherindex
					udp_WriteByte otherindex2
					udp_WriteByte otherindex3
					udp_SendMessage(p\ID)
				EndIf
			Next
		Case M_SOUND
			otherindexstr$ = udp_ReadLine()
			floatx# = udp_ReadFloat()
			floaty# = udp_ReadFloat()
			If playerid <> Null Then
				If playerid\Pivot <> 0 Then
					If ID <> NetworkServer\MyID Then
						PlayPlayerSound(playerid, otherindexstr,floatx,floaty)
					EndIf
				EndIf
			EndIf

			For p.players = Each players
				If p\ID <> ID And p\ID <> 1 Then
					udp_WriteByte M_SOUND
					udp_WriteByte ID
					udp_WriteLine otherindexstr
					udp_WriteFloat floatx
					udp_WriteFloat floaty
					udp_SendMessage(p\ID)
				EndIf
			Next
		Case M_DECAL
			pers% = udp_ReadByte()
			pers2# = udp_ReadFloat()
			pers3# = udp_ReadFloat()
			pers4# = udp_ReadFloat()
			pers5# = udp_ReadFloat()
			pers6# = udp_ReadFloat()
			pers7# = udp_ReadFloat()
			pers8# = udp_ReadFloat()
			pers9# = udp_ReadFloat()
			pers10# = udp_ReadFloat()
			pers11# = udp_ReadFloat()
			pers12# = udp_ReadFloat()
			pers13# = udp_ReadFloat()
			des.decals = CreateDecal(pers%, pers2#, pers3#, pers4#, pers7#, pers5#, pers6#)
			If pers = 5 Then EntityColor(des\obj, 0.0, Rnd(200, 255), 0.0)
			des\SizeChange = pers8#
			des\Size = pers9#
			des\MaxSize = pers10#
			des\AlphaChange = pers11#
			des\Alpha = pers12#
			des\timer = pers13#
			EntityAlpha(des\obj, des\Alpha)
			ScaleSprite(des\obj,des\Size,des\Size)

			For p.players = Each players
				If p\ID <> ID And p\ID <> 1 Then
					udp_WriteByte M_DECAL
					udp_WriteByte 1
					udp_WriteByte pers
					udp_WriteFloat pers2
					udp_WriteFloat pers3
					udp_WriteFloat pers4
					udp_WriteFloat pers5
					udp_WriteFloat pers6
					udp_WriteFloat pers7
					udp_WriteFloat pers8
					udp_WriteFloat pers9
					udp_WriteFloat pers10
					udp_WriteFloat pers11
					udp_WriteFloat pers12
					udp_WriteFloat pers13
					udp_SendMessage(p\ID)
				EndIf
			Next
		Case M_KILL2
			SendID = udp_ReadByte()
			If SendID = NetworkServer\MyID Then
				If playerid\BreachType = MODEL_106 Then
					MoveToPocketDimension()
					MyPlayer\Health = MyPlayer\Health - 55
					If MyPlayer\Health < 1 Then Kill("was killed by "+playerid\name)
				Else
					Kill("was killed by "+playerid\name)
				EndIf
			Else
				udp_WriteByte(M_KILL2)
				udp_WriteByte(1)
				udp_SendMessage(SendID)
			EndIf
		Case M_SHOOT
			SendID = udp_ReadByte()
			If SendID = NetworkServer\MyID Then
				If Not player_isdead() Then
					If (Not multiplayer_IsASCP(MyPlayer\BreachType)) Then
						pa.Particles = CreateParticle(EntityX(Collider),EntityY(Collider)-0.1,EntityZ(Collider), 5, 0.06, 0.2, 80)
						pa\speed = 0.001
						pa\SizeChange = 0.003
						pa\A = 0.8
						pa\Achange = -0.02
						
						pvt = CreatePivot()
						PositionEntity pvt, EntityX(Collider)+Rnd(-0.05,0.05),EntityY(Collider)-0.05,EntityZ(Collider)+Rnd(-0.05,0.05)
						TurnEntity pvt, 90, 0, 0
						EntityPick(pvt,0.3)
						de.decals = CreateDecal(Rand(15,16), PickedX(), PickedY()+0.005, PickedZ(), 90, Rand(360), 0)
						de\size = Rnd(0.03,0.08)*Min(Injuries,3.0) : EntityAlpha(de\obj, 1.0) : ScaleSprite de\obj, de\size, de\size
						
						FreeEntity pvt
						multiplayer_WriteDecal(de,1)
						If Not NetworkServer\Breach Then
							Injuries = Injuries + Rnd(2.0, 3.0)
							If Injuries >= 10 Then Kill("was killed by "+playerid\name)
						Else
							If Injuries <= 1.0 Then 
								Injuries = 1.01
							Else
								Injuries = Injuries+0.01
							EndIf
							MyPlayer\Health = MyPlayer\Health - ( Rnd(GetGunDamage(playerid\UsedWeapon)-3, GetGunDamage(playerid\UsedWeapon)+3)-(WearingVest*8) )
							If multiplayer_IsASCP(playerid\BreachType) Then MyPlayer\Health = MyPlayer\Health-Rand(30,40)
							If MyPlayer\Health < 1 Then Kill("was killed by "+playerid\name)
						EndIf
						;FirstInjuries = Injuries
						;if Injuries = 0 Then FirstInjuries
						;MyHP = 100/FirstInjuries
					Else
						MyPlayer\Health = MyPlayer\Health - GetGunDamage(playerid\UsedWeapon) - Rand(1, 2)
						Injuries = 0
						If MyPlayer\Health < 0 Then
							GodMode = False
							Kill("was killed by "+playerid\name)
						EndIf
					EndIf
				EndIf
			
			Else
				udp_WriteByte(M_SHOOT)
				udp_WriteByte(1)
				udp_SendMessage(SendID)
			EndIf
		Case M_CHAT
			Local MessageC$ = udp_ReadLine()
			If udp_ReadByte() Then
				If playerid <> Null Then
					cm.ChatMessage = multiplayer_CreateMessage(playerid\name+MessageC)
				Else
					cm.ChatMessage = multiplayer_CreateMessage(MessageC)
				EndIf
				For p.players = Each players
					If p\ID <> NetworkServer\MyID Then
						udp_WriteByte M_CHAT
						udp_WriteByte 0
						udp_WriteLine cm\Txt
						udp_WriteByte 1
						udp_SendMessage(p\ID)
					EndIf
				Next
			EndIf
		Case M_REQUESTPLAYERNAME
			Local plid = udp_ReadByte()
			If plid > 0 And plid < MAX_PLAYERS Then
				If Player[plid] <> Null Then
					udp_WriteByte(M_REQUESTPLAYERNAME)
					udp_WriteByte(plid)
					udp_WriteLine Player[plid]\name
					udp_WriteLine Player[plid]\tag
					udp_WriteByte Player[plid]\tagr
					udp_WriteByte Player[plid]\tagg
					udp_WriteByte Player[plid]\tagb
					udp_WriteShort 100
					udp_WriteInt Player[plid]\steamid
					udp_WriteByte Player[plid]\TextureID
					udp_SendMessage(ID)
				EndIf
			EndIf
		Case M_ROTATEENTITY
			otherindex = udp_ReadByte()
			floatpitch# = udp_ReadFloat()
			floatyaw# = udp_ReadFloat()
			floatroll# = udp_ReadFloat()
			For r.Rooms = Each Rooms
				If r\ID = playerid\PlayerRoomID Then
					RotateEntity(r\Objects[otherindex], floatpitch, floatyaw, floatroll, True)
					r\ObjectStatic[otherindex] = False
					Exit
				EndIf
			Next
		Case M_WANTROLE
			playerid\BreachType = Max(Min(udp_ReadByte(), LAST_BREACH_TYPE-1), 0)
		Case M_PUTHANDCUFFS
			If ID = NetworkServer\MyID Then
				MyPlayer\Handcuffed = True
			Else
				udp_WriteByte M_PUTHANDCUFFS
				udp_WriteByte 1
				udp_SendMessage(ID)
			EndIf
			For it.items = Each Items
				If it\picker = ID Then PlayerDropItem(it)
			Next
		Case M_KNIFEDAMAGE
			udp_ReadShort()
			
			udp_WriteByte M_KNIFEDAMAGE
			udp_WriteByte 1
			udp_SendMessage(udp_ReadByte())
		Case M_CREATEROCKET
			udp_ReadShort()
			floatx# = udp_ReadFloat()
			floaty# = udp_ReadFloat()
			floatz# = udp_ReadFloat()
			floatpitch# = udp_ReadFloat()
			floatyaw# = udp_ReadFloat()
			If ID <> NetworkServer\MyID Then 
				CreateRocket(ROCKET_SPEED, floatx, floaty, floatz, floatpitch,floatyaw, ID)
				Player[ID]\IsShooted = True
			EndIf
			For p.players = Each players
				If p\ID <> ID And p\ID <> 1 Then
					udp_WriteByte M_CREATEROCKET
					udp_WriteByte 1
					udp_WriteShort 0
					udp_WriteFloat floatx
					udp_WriteFloat floaty
					udp_WriteFloat floatz
					udp_WriteFloat floatpitch
					udp_WriteFloat floatyaw
					udp_SendMessage(p\ID)
				EndIf
			Next
		Case M_CREATEGRENADE
			udp_ReadShort()
			floatx# = udp_ReadFloat()
			floaty# = udp_ReadFloat()
			floatz# = udp_ReadFloat()
			floatpitch# = udp_ReadFloat()
			floatyaw# = udp_ReadFloat()
			grenadetype% = udp_ReadByte()
			grenadeslow% = udp_ReadByte()
			If ID <> NetworkServer\MyID Then CreateGrenade(grenadetype, floatx, floaty, floatz, floatpitch,floatyaw, ID, grenadeslow)
			
			For p.players = Each players
				If p\ID <> ID And p\ID <> 1 Then
					udp_WriteByte M_CREATEGRENADE
					udp_WriteByte ID
					udp_WriteShort 0
					udp_WriteFloat floatx
					udp_WriteFloat floaty
					udp_WriteFloat floatz
					udp_WriteFloat floatpitch
					udp_WriteFloat floatyaw
					udp_WriteByte grenadetype
					udp_WriteByte grenadeslow
					udp_SendMessage(p\ID)
				EndIf
			Next
		Case M_FIXITEMS
			itnameid = udp_ReadInt()
			If ID <> NetworkServer\MyID Then
				For its.ItemTemplates = Each ItemTemplates
					If its\templateID = itnameID Then
						it.Items = CreateItem(its\name, its\tempname, EntityX(playerid\Pivot),EntityY(GetPlayerCamera(ID))+0.1,EntityZ(playerid\Pivot))
						EntityType (it\collider, HIT_ITEM)
						it\picker = 0
						Exit
					EndIf
				Next
			EndIf
	End Select
End Function

;Function GetMacAddress()
;    ; Use the "wmic" command to get the MAC address
;    Local cmd:String = "wmic nic get macaddress"
;    Local proc:TStream = ShellExecute(cmd)
;    Local result:String = ""
;    While Not Eof(proc)
;        result = ReadString(proc)
;    Wend
;    CloseStream(proc)
;	
;    ; Parse the result to extract the MAC address
;    Local macStartt = Instr(result, ":")
;    If macStart > 0
;        macStart = macStart + 1
;        Local mac:String = Trim(Mid(result, macStart))
;        Return mac
;    EndIf
;	
;    ; Return an empty string if MAC address not found
;    Return ""
;End Function


Function multiplayer_GetPacket(packetid, ID)
	If Not IsValidPlayer(ID) Then Return
	Local floatx#, floaty#, floatz#, floatyaw#, floatpitch#, floatroll#, floatscale#
	
	If packetid <> M_CONNECTERROR Then playerid.players = Player[ID]
	
	multiplayer_SetServerTime((MilliSecs()+NetworkServer\ServerTimeout)+2000)
	
	Select packetid
		Case M_KNIFEDAMAGE
			MyPlayer\Health = MyPlayer\Health - Rand(30,40)
		Case M_GLOBALBLINK
			BlinkTimer = udp_ReadFloat()
		Case M_WANTROLE
			If NetworkServer\Breach Then playerid\BreachType = Max(Min(udp_ReadByte(), LAST_BREACH_TYPE-1), 0)
		Case M_PUTHANDCUFFS
			MyPlayer\Handcuffed = Not MyPlayer\Handcuffed
		Case M_UPDATE
			If ID <> NetworkServer\MyID Then
				Select udp_ReadByte()
					Case 0
						playerid\Timeout = MilliSecs()+NetworkServer\ServerTimeout
						playerid\Ready = Str(udp_ReadByte())
						playerid\IsLoad = udp_ReadByte()
						If playerid\Ready = "0" Then : playerid\Ready = "Not Ready"
						Else : playerid\Ready = "Ready" : EndIf
						udp_ReadByte()
						udp_ReadByte()
						udp_ReadByte()
						udp_ReadByte()
					Case 1
						playerid\Timeout = MilliSecs()+NetworkServer\ServerTimeout
						playerid\yaw = convertshorttovalue(udp_ReadShort())
						playerid\BonePitch = convertshorttovalue(udp_ReadShort())
						playerid\BlinkTimer = udp_ReadFloat()
						playerid\wearingdir = udp_ReadByte()
						whazmat = ReadBool(playerid\wearingdir, 0)
						playerid\WearingNightVision = ReadBool(playerid\wearingdir, 1)
						playerid\WearingGasMask = ReadBool(playerid\wearingdir, 2)
						playerid\WearingVest = ReadBool(playerid\wearingdir, 3)
						playerid\HandCuffed = ReadBool(playerid\wearingdir, 5)
						playerid\MicrohidSHOOT = ReadBool(playerid\wearingdir, 6)
						
						playerid\PlayerSoundVolume = udp_ReadByte()
						playerid\p_byte = udp_ReadByte()
						playerid\UsedWeapon = udp_ReadByte()
						playerid\IsLoad = ReadBool(playerid\p_byte, 0)
						playerid\Sighting = ReadBool(playerid\p_byte, 1)
						playerid\SelectedItem = udp_ReadShort()
						
						udp_ReadByte()
						udp_ReadByte()
						udp_ReadByte()
						udp_ReadByte()
						udp_ReadFloat()
						
						playerid\PLAYER_MOVE = udp_ReadByte()
						
						udp_ReadShort()
						
						playerid\x = udp_ReadFloat()
						playerid\y = udp_ReadFloat()
						playerid\z = udp_ReadFloat()
						playerid\PlayerRoomID = udp_ReadByte()
						playerid\CurrentRadio = udp_ReadByte()
						multiplayer_SetPlayerRoom(playerid)
						host_UpdatePlayer(ID, playerid\textureid, whazmat, playerid\p_byte)

				End Select
				
				If udp_ReadAvail() <= 640 Then
					While udp_ReadAvail() > 0
						multiplayer_GetMicroBytePacket(udp_ReadByte(), ID, playerid)
					Wend
				EndIf
			EndIf
		Case M_WANTDOORS
			udp_WriteByte M_SYNCDOOR
			udp_WriteByte 1
			For d.Doors = Each Doors
				udp_WriteShort(d\ID)
				udp_WriteByte((d\open) + (2 * d\locked))
			Next
			udp_WriteShort(0)
			udp_SendMessage(ID)
		Case M_GETPOS
			udp_WriteByte(M_GETPOS)
			udp_WriteByte(1)
			udp_WriteLine ""
			udp_WriteFloat 0
			udp_WriteFloat 0
			udp_WriteFloat 0
			udp_SendMessage(ID)
		Case M_DISCONNECT
			multiplayer_CreateMessage(playerid\name+" has left the server")

			For p.players = Each players
				If p\ID <> 1 Then
					udp_WriteByte M_CHAT
					udp_WriteByte 0
					udp_WriteLine playerid\name+" has left the server"
					udp_WriteByte 1
					udp_SendMessage(p\ID)
				EndIf
			Next
			
			multiplayer_DisconnectPlayer(Player[ID])
			NetworkServer\PlayersCount = NetworkServer\PlayersCount-1
		Case STEAM_TRYCONNECT
			Local fp 
			For p.players = Each players
				If p\ID <> NetworkServer\MyID Then
					If BS_CSteamID_GetAccountID(udp_UDPMsgIP()) = BS_CSteamID_GetAccountID(p\IP) Then
						fp = True
						Exit
					EndIf
				EndIf
			Next
			If Not fp Then
				udp_WriteByte STEAM_CONNECT
				udp_WriteByte 2
				udp_SendMessageInternal(0, udp_network\msgip, 0)
				udp_network\msgip = 0
			EndIf
		Case M_CONNECT
			Local PlayerIP$ = DottedIP(IP)
			name$ = Left(udp_ReadLine(), 24)
			Local Version$ = udp_ReadLine()
			Local plpassword$ = udp_ReadLine()
			Local clversion = udp_ReadByte()
			Local errorMessage$ = ""
			If plpassword <> NetworkServer\password Then
				errorMessage = "Wrong password"
			EndIf
			
			If name = "" Then errorMessage = "Set nickname."
			
			If Instr(name, "%") Then
				errorMessage = "Invalid syntax, please change your nickname!"
			End If
			
			For p.players = Each players
				If Lower(p\name) = Lower(name) Then
					errorMessage = "Change your nickname"+Chr(13)+Chr(10)+"A player with this name"+Chr(13)+Chr(10)+"is already on the server"
					Exit
				EndIf
			Next
			If MULTIPLAYER_VERSION <> Version Or clversion <> CLIENT_VERSION Then
				errorMessage = "Version doesn't match"+Chr(13)+Chr(10)+"Server version: "+MULTIPLAYER_VERSION+Chr(13)+Chr(10)+"Game version: "+Version
			EndIf
			If NetworkServer\PlayersCount = NetworkServer\Maxplayers Then
				errorMessage = "Server lobby is full!"
			EndIf
			If errorMessage <> "" Then
				udp_WriteByte M_CONNECTERROR
				udp_WriteLine(errorMessage)
				If (Not NetworkServer\Hosted) Then
					udp_WriteInt udp_UDPMsgIP()
					udp_WriteInt udp_UDPMsgPort()
					udp_SendMessageInternal(udp_network\stream,udp_network\messip,udp_network\messport)
				Else
					udp_SendMessageInternal(udp_network\stream, udp_UDPMsgIP(), udp_UDPMsgPort())
				EndIf
				If NetworkServer\SteamStream Then

					BS_ISteamNetworking_CloseP2PSessionWithUser(BS_SteamNetworking(), udp_FillSteam(BS_CSteamID_GetAccountID(udp_UDPMsgIP())))
					BS_CSteamID_Destroy(udp_UDPMsgIP())
					udp_network\msgip = 0
				EndIf
				Return
			EndIf
			NetworkServer\PlayersCount = NetworkServer\PlayersCount + 1
			ID = FindFreePlayerID()
			multiplayer_CreatePlayer(ID)
			playerid = Player[ID]
			playerid\monitor_width = udp_ReadShort()
			playerid\monitor_height = udp_ReadShort()
			playerid\patron = udp_ReadByte()
			playerid\steamid = udp_ReadInt()
			playerid\BreachType = CLASSD_MODEL
			playerid\Timeout = MilliSecs()+NetworkServer\ServerTimeout
			playerid\IP = udp_UDPMsgIP()
			playerid\Port = udp_UDPMsgPort()
			playerid\ping = 5
			playerid\TextureID = Rand(1,3)
			
			If playerid\patron Then
				playerid\tag = "PATRON"
				playerid\tagr = 212
				playerid\tagg = 160
				playerid\tagb = 49
			EndIf
			
			Local difffound = 0
			For i = 0 To 2
				If SelectedDifficulty = difficulties(i) Then
					difffound = i
					Exit
				EndIf
			Next
			udp_WriteByte(M_CONNECT)
			udp_WriteByte(ID)
			udp_WriteLine(RandomSeed)
			udp_WriteByte(IntroEnabled)
			udp_WriteByte(NoCheat)
			udp_WriteByte(NetworkServer\haveVoice)
			udp_WriteFloat(0)
			udp_WriteInt NetworkServer\ServerTimeout
			udp_WriteFloat(NetworkServer\JumpHeight)
			udp_WriteByte(NetworkServer\JumpMode)
			udp_WriteByte(NetworkServer\Maxplayers)
			udp_WriteByte(NetworkServer\Tickrate)
			udp_WriteByte(0)
			udp_WriteByte(NetworkServer\keepitems)
			udp_WriteByte playerid\BreachType
			udp_WriteInt -1
			udp_WriteInt 0
			udp_WriteByte playerid\TextureID
			udp_WriteInt -1
			udp_WriteByte difffound
			udp_WriteByte Not MainMenuOpen
			udp_WriteInt NetworkServer\voicerate
			udp_WriteLine ""
			udp_WriteByte 0
			udp_WriteByte NetworkServer\LongCulling
			udp_WriteByte NetworkServer\MapSize
			udp_WriteByte 0
			udp_WriteByte 0
			udp_WriteByte 0
			udp_WriteByte 0
			udp_WriteLine ""
			udp_WriteLine ""
			udp_WriteByte HALLOWEENINDEX
			udp_WriteByte NEWYEARINDEX
			udp_SendMessage(ID)
			multiplayer_InitPlayer(ID)
			multiplayer_ChangePlayerName(ID, name, playerid\tag)
			
			multiplayer_CreateMessage(playerid\name+" has joined to server")
			For p.players = Each players
				If p\ID <> 1 Then
					udp_WriteByte M_CHAT
					udp_WriteByte 0
					udp_WriteLine playerid\name+" has joined to server"
					udp_WriteByte 1
					udp_SendMessage(p\ID)
				EndIf
			Next
			
			If Not IntroEnabled Then
				For r.rooms = Each Rooms
					If r\roomtemplate\name = "start" Then
						Player[ID]\x = EntityX(r\obj)+3584*RoomScale
						Player[ID]\y = (704*RoomScale)-0.9
						Player[ID]\z = EntityZ(r\obj)+1024*RoomScale
						Player[ID]\yaw = 130.3
						Player[ID]\PlayerRoomID = r\ID
						Player[ID]\PLAYER_MOVE = PLAYER_IDLING
						Exit
					EndIf
				Next
			Else
				For r.rooms = Each Rooms
					If r\roomtemplate\name = "173" Then
						Player[ID]\x = EntityX(r\obj)
						Player[ID]\y = 1.0
						Player[ID]\z = EntityZ(r\obj)

						Player[ID]\yaw = 130.3
						Player[ID]\PlayerRoomID = r\ID
						Player[ID]\PLAYER_MOVE = PLAYER_IDLING
						Exit
					EndIf
				Next
			EndIf
		Case M_SERVERLIST
			If NetworkServer\SteamStream Then Return
			udp_WriteLine(NetworkServer\Servername)
			udp_WriteLine(NetworkServer\PlayersCount+" / "+NetworkServer\Maxplayers)
			udp_WriteByte(NoCheat)
			udp_WriteByte(Not MainMenuOpen)
			If NetworkServer\password <> "" Then udp_WriteLine("PS") Else udp_WriteLine("")
			udp_WriteByte(0)
			udp_WriteByte(NetworkServer\haveVoice)
			udp_WriteLine(RandomSeed)
			udp_WriteLine("")
			udp_WriteLine("")
			udp_WriteLine(MULTIPLAYER_VERSION)
			If NetworkServer\MainPlayer And (Not NetworkServer\Hosted) Then
				udp_WriteByte 254
				udp_WriteInt udp_network\msgip
				udp_WriteInt udp_network\msgport
				udp_SendMessageInternal(udp_network\stream,udp_network\messip,udp_network\messport)
			Else
				udp_SendMessageInternal(udp_network\stream,udp_network\msgip,udp_network\msgport)
			EndIf
		Case M_CHAT
			Local MessageC$ = udp_ReadLine()
			If udp_ReadByte() Then
				If playerid <> Null Then
					cm.ChatMessage = multiplayer_CreateMessage(playerid\name+MessageC)
				Else
					cm.ChatMessage = multiplayer_CreateMessage(MessageC)
				EndIf
			EndIf
		Case M_RAWPACKET
			If GetScripts() Then
				Local bnk = CreateBank(udp_ReadAvail())
				udp_ReadBytes bnk, 0, BankSize(bnk)
				public_inqueue(public_OnReceiveRawPacket)
				public_addparam(bnk)
				callback
				FreeBank bnk
			EndIf
		Case M_LOADDATA
			Local bnkname$ = udp_ReadLine()
			Local fileindex = udp_ReadInt()
			Local offset = -1
			Local fullsize = 0

			If Not IsFolderSecured(bnkname) Then
				udp_ReadInt()
				udp_WriteByte M_REQUESTLOAD
				udp_WriteByte NetworkServer\MyID
				udp_WriteInt fileindex
				udp_WriteInt udp_ReadInt()
				udp_WriteShort NetworkServer\downloadspeed
				udp_SendMessage()
				Return
			EndIf
							
			For q.querys = Each querys
				If q\queryindex = fileindex Then
					offset = udp_ReadInt()
					udp_ReadInt()
					
					If offset = FilePos(q\file) Then 
						ResizeBank q\tempbank, udp_ReadAvail()-5
						udp_ReadBytes q\tempbank, 0, BankSize(q\tempbank)
						WriteBytes q\tempbank, q\file, 0, BankSize(q\tempbank)
						q\currbytes = q\currbytes + BankSize(q\tempbank)
					ElseIf offset > FilePos(q\file)
						udp_WriteByte M_REQUESTLOAD
						udp_WriteByte NetworkServer\MyID
						udp_WriteInt q\queryindex
						udp_WriteInt FilePos(q\file)
						udp_WriteShort NetworkServer\downloadspeed
						udp_SendMessage()
					EndIf
					Exit
				EndIf
			Next
			If offset = -1 Then
				udp_ReadInt()
				q = New querys
				q\queryindex = fileindex
				q\fullsize = udp_ReadInt()
				q\filename = bnkname
				q\tempbank = CreateBank(udp_ReadAvail()-5)
				udp_ReadBytes q\tempbank, 0, BankSize(q\tempbank)
				q\isascript = udp_ReadByte()
				q\compressedfullsize = udp_ReadInt()
				If q\compressedfullsize <> 0 Then
					If FileSize("multiplayer\serversdata\"+q\filename+".packed") = q\fullsize Then
						udp_WriteByte M_REQUESTLOAD
						udp_WriteByte NetworkServer\MyID
						udp_WriteInt q\queryindex
						udp_WriteInt q\fullsize
						udp_WriteShort NetworkServer\downloadspeed
						udp_SendMessage()

						f = ReadFile("multiplayer\serversdata\"+q\filename+".packed")
						If f <> 0 Then
							bnk = CreateBank(q\fullsize)
							ReadBytes(bnk, f, 0, q\fullsize)
							CloseFile(f)
						EndIf
						
						If bnk <> 0 Then
							newbank = ZipApi_UnCompress(bnk, q\compressedfullsize)
							DebugLog "UNCOMPRESSED: "+newbank
							If newbank <> 0 Then
								f = WriteFileDir("multiplayer\serversdata\"+q\filename)
								WriteBytes(newbank, f, 0, q\compressedfullsize)
								CloseFile(f)
								
								DeleteFile("multiplayer\serversdata\"+q\filename+".packed")
							EndIf
						EndIf
						If q\tempbank <> 0 Then FreeBank q\tempbank
						Delete q
					ElseIf FileSize("multiplayer\serversdata\"+q\filename) <> q\compressedfullsize Then
						q\file = WriteFileDir("multiplayer\serversdata\"+q\filename+".packed")
						udp_WriteByte M_REQUESTLOAD
						udp_WriteByte NetworkServer\MyID
						udp_WriteInt q\queryindex
						udp_WriteInt FilePos(q\file)
						udp_WriteShort NetworkServer\downloadspeed
						udp_SendMessage()
					Else
						udp_WriteByte M_REQUESTLOAD
						udp_WriteByte NetworkServer\MyID
						udp_WriteInt q\queryindex
						udp_WriteInt q\fullsize
						udp_WriteShort NetworkServer\downloadspeed
						udp_SendMessage()
						
						If q\tempbank <> 0 Then FreeBank q\tempbank
						Delete q
					EndIf
				Else
					If FileSize("multiplayer\serversdata\"+q\filename) = q\fullsize And (Not q\isascript) Then
						
						udp_WriteByte M_REQUESTLOAD
						udp_WriteByte NetworkServer\MyID
						udp_WriteInt q\queryindex
						udp_WriteInt q\fullsize
						udp_WriteShort NetworkServer\downloadspeed
						udp_SendMessage()
						
						If q\isascript Then
							For sc.ScriptsThread = Each ScriptsThread
								If sc\path = "multiplayer\serversdata\"+q\filename Then Return
							Next

							Local sscript.ScriptsThread = New ScriptsThread
							
							sscript\scriptThread = SE_LoadScriptExec("multiplayer\serversdata\"+q\filename)
							sscript\path = "multiplayer\serversdata\"+q\filename
							skynet_onload
							
							init_publics_for_script(sscript\scriptThread)
							
							public_inqueue(public__main)
							public_update_current(sscript\scriptThread)
							public_clear()
							
							DeleteFile "multiplayer\serversdata\"+q\filename
						EndIf
						
						If q\tempbank <> 0 Then FreeBank q\tempbank
						Delete q
					Else
						q\file = WriteFileDir("multiplayer\serversdata\"+q\filename)
						udp_WriteByte M_REQUESTLOAD
						udp_WriteByte NetworkServer\MyID
						udp_WriteInt q\queryindex
						udp_WriteInt FilePos(q\file)
						udp_WriteShort NetworkServer\downloadspeed
						udp_SendMessage()
					EndIf
				EndIf
				
				If q <> Null Then
					If q\file <> 0 Then
						WriteBytes q\tempbank, q\file, 0, BankSize(q\tempbank)
						q\currbytes = q\currbytes + BankSize(q\tempbank)
					EndIf
				EndIf
			EndIf
		Case M_REQUESTPLAYERNAME
			If Player[ID] <> Null Then
				Local Tag$
				Name$ = udp_ReadLine()
				Tag$ = udp_ReadLine()
				Player[ID]\tagr = udp_ReadByte()
				Player[ID]\tagg = udp_ReadByte()
				Player[ID]\tagb = udp_ReadByte()
				multiplayer_ChangePlayerName(ID,Name,Tag)
				
				Player[ID]\size = Float(udp_ReadShort())/100.0
				If Player[ID]\size = 0 Then Player[ID]\size = 1.0
				
				Local prevsteam = Player[ID]\steamid
				Player[ID]\steamid = udp_ReadInt()
				
				If prevsteam <> Player[ID]\steamid Then
					If Player[ID]\RequestSteamData <> 0 Then
						FreeImage(Player[ID]\RequestSteamData)
						Player[ID]\RequestSteamData = 0
					EndIf
				EndIf

				multiplayer_InitSettingsForPlayer(Player[ID], Player[ID]\BreachType, udp_ReadByte(), Player[ID]\WearingHazmat, Player[ID]\p_byte)
			EndIf
		Case M_FOGRANGE
			CameraFogNear = 0.5
			CameraFogFar = udp_ReadFloat()
		Case m_miscellaneous
			otherindex = udp_ReadByte()
			Select NETWORK_BYTE[otherindex]
				Case M_NPCSYNC
					If QuickLoadPercent = -1 Or QuickLoadPercent = 100 Then
						NoTarget = udp_ReadByte()
						While True
							otherindex = udp_ReadByte()
							If otherindex = 0 Then Exit
							otherindex2 = udp_ReadByte()
							n.NPCs = M_NPC[otherindex]
							If n = Null Then
								n = CreateNPC(otherindex2, 0,0,0)
								SetNPCID(n, otherindex)
							EndIf
							ResetNPC(n, otherindex2)
							n\waitFactor = 70*NPC_WAIT_FACTOR
							n\Idle = udp_ReadByte()
							n\State = udp_ReadFloat()
							n\State2 = udp_ReadFloat()
							n\State3 = udp_ReadFloat()
							n\x = udp_ReadFloat()
							n\y = udp_ReadFloat()
							n\z = udp_ReadFloat()
							n\yaw = udp_ReadFloat()
							n\mpframe = udp_ReadFloat()
							
							ChangeNPCTextureID(n, udp_ReadByte()-1)
							n\EventID = udp_ReadByte()
							n\NPCEventID = udp_ReadByte()
							n\Target = M_NPC[udp_ReadByte()]
							If M_Event[n\EventID] <> Null Then M_Event[n\EventID]\room\NPC[n\NPCEventID] = n
							Select n\NPCtype
								Case NPCtype173: Curr173 = n
								Case NPCtype096: Curr096 = n
								Case NPCtypeOldMan: Curr106 = n
								Case NPCtype5131: Curr5131 = n
							End Select
						Wend
						For n.NPCs = Each NPCs
							If n\waitFactor < 1 Then
								If M_Event[n\EventID] <> Null Then RemoveEvent(M_Event[n\EventID])
								RemoveNPC(n)
							Else
								If n\waitFactor <> 70*NPC_WAIT_FACTOR Then n\x = 999 : n\y = 999 : n\z = 999 ; deactivate npc if he cant received
								n\waitFactor = n\waitFactor - FPSfactor
							EndIf
						Next
					EndIf
				Case M_FIXEDITEMS
					;debuglog "get item: "+udp_ReadAvail()
					While True
						otherindex = udp_ReadShort()
						If otherindex = 0 Then Exit
						it.Items = M_Item[otherindex]
						otherindex2 = udp_ReadInt()
						floatx# = udp_ReadFloat()
						floaty# = udp_ReadFloat()
						floatz# = udp_ReadFloat()
						otherindex3 = udp_ReadByte()
						If it = Null Then
							For its.ItemTemplates = Each ItemTemplates
								If its\templateID = otherindex2 Then
									it = CreateItemByTemplate(its,1,1,1)
									EntityType it\collider, HIT_ITEM
									Exit
								EndIf
							Next
						ElseIf it\itemtemplate\templateID <> otherindex2 Then
							RemoveItem(it, False)
							For its.ItemTemplates = Each ItemTemplates
								If its\templateID = otherindex2 Then
									it = CreateItemByTemplate(its,1,1,1)
									EntityType it\collider, HIT_ITEM
									Exit
								EndIf
							Next
						EndIf
						If it <> Null Then
							SetItemID(it, otherindex)
							it\waitFactor = True
							it\x = floatx#
							it\y = floaty#
							it\z = floatz#
							it\picker = otherindex3
							it\picked = (it\picker<>0)
						EndIf
					Wend
					For it.Items = Each Items
						If Not it\waitFactor Then
							RemoveItem(it, False)
						Else
							it\waitFactor = False
						EndIf
					Next
				Case M_SYNCDOOR
					While True
						otherindex = udp_ReadShort()
						If otherindex = 0 Then Exit
						d = multiplayer_Door[otherindex]
						otherindex2 = udp_ReadByte()
						If d <> Null Then
							d\open = ReadBool(otherindex2, 0)
							d\locked = ReadBool(otherindex2, 1)
							If ReadBool(otherindex2, 2) Then
								prevkeycard% = d\keycard
								d\KeyCard = udp_ReadByte()
								If prevkeycard = 0 And d\Keycard > 0 Then
									For i% = 0 To 1
										If d\buttons[i] <> 0 Then
											ButtonsX# = EntityX(d\buttons[i])
											ButtonsY# = EntityY(d\buttons[i])
											ButtonsZ# = EntityZ(d\buttons[i])
											ButtonsYaw# = EntityYaw(d\buttons[i])
											FreeEntity d\buttons[i]
											d\buttons[i]= CopyEntity(ButtonKeyOBJ)
											EntityFX(d\buttons[i], 1)
											ScaleEntity(d\buttons[i], 0.03, 0.03, 0.03)
											PositionEntity(d\Buttons[i], ButtonsX, ButtonsY, ButtonsZ)
											RotateEntity(d\buttons[i], 0, buttonsyaw,0)
										EndIf
									Next
								EndIf
								
								If prevkeycard > 0 And d\Keycard = 0 Then
									For i% = 0 To 1
										If d\buttons[i] <> 0 Then
											ButtonsX# = EntityX(d\buttons[i])
											ButtonsY# = EntityY(d\buttons[i])
											ButtonsZ# = EntityZ(d\buttons[i])
											ButtonsYaw# = EntityYaw(d\buttons[i])
											FreeEntity d\buttons[i]
											
											d\buttons[i]= CopyEntity(ButtonOBJ)
											EntityFX(d\buttons[i], 1)
											ScaleEntity(d\buttons[i], 0.03, 0.03, 0.03)
											PositionEntity(d\Buttons[i], ButtonsX, ButtonsY, ButtonsZ)
											RotateEntity(d\buttons[i], 0, buttonsyaw,0)
										EndIf
									Next
								EndIf
							EndIf
							If ReadBool(otherindex2, 3) Then
								prevcode$ = d\code
								d\ChangedCode = udp_ReadByte()
								If prevcode = "" And d\ChangedCode = True Then
									d\Code = "ABCD"
									For i% = 0 To 1
										If d\buttons[i] <> 0 Then
											ButtonsX# = EntityX(d\buttons[i])
											ButtonsY# = EntityY(d\buttons[i])
											ButtonsZ# = EntityZ(d\buttons[i])
											ButtonsYaw# = EntityYaw(d\buttons[i])
											FreeEntity d\buttons[i]
											
											d\buttons[i]= CopyEntity(ButtonCodeOBJ)
											EntityFX(d\buttons[i], 1)
											ScaleEntity(d\buttons[i], 0.03, 0.03, 0.03)
											PositionEntity(d\Buttons[i], ButtonsX, ButtonsY, ButtonsZ)
											RotateEntity(d\buttons[i], 0, buttonsyaw,0)
										EndIf
									Next
								EndIf
								
								If prevcode <> "" And d\ChangedCode = False Then
									d\Code = ""
									For i% = 0 To 1
										If d\buttons[i] <> 0 Then
											ButtonsX# = EntityX(d\buttons[i])
											ButtonsY# = EntityY(d\buttons[i])
											ButtonsZ# = EntityZ(d\buttons[i])
											ButtonsYaw# = EntityYaw(d\buttons[i])
											FreeEntity d\buttons[i]
											
											d\buttons[i]= CopyEntity(ButtonOBJ)
											EntityFX(d\buttons[i], 1)
											ScaleEntity(d\buttons[i], 0.03, 0.03, 0.03)
											PositionEntity(d\Buttons[i], ButtonsX, ButtonsY, ButtonsZ)
											RotateEntity(d\buttons[i], 0, buttonsyaw,0)
										EndIf
									Next
								EndIf
							EndIf
						Else
							If ReadBool(otherindex2, 2) Then udp_ReadByte()
							If ReadBool(otherindex2, 3) Then udp_ReadByte()
						EndIf
					Wend
				Case M_WRITEELEVATORS
					While True
						otherindex = udp_ReadShort()
						If otherindex = 0 Then Exit
						d = multiplayer_Door[otherindex]
						otherindex2 = udp_ReadByte()
						floatx# = udp_ReadFloat()
						If d <> Null Then
							d\open = ReadBool(otherindex2, 0)
							d\locked = ReadBool(otherindex2, 1)
							
							d\liftstate = floatx

							If ReadBool(otherindex2, 2) Then
								prevkeycard% = d\keycard
								d\KeyCard = udp_ReadByte()
								If prevkeycard = 0 And d\Keycard > 0 Then
									For i% = 0 To 1
										If d\buttons[i] <> 0 Then
											ButtonsX# = EntityX(d\buttons[i])
											ButtonsY# = EntityY(d\buttons[i])
											ButtonsZ# = EntityZ(d\buttons[i])
											ButtonsYaw# = EntityYaw(d\buttons[i])
											FreeEntity d\buttons[i]
											d\buttons[i]= CopyEntity(ButtonKeyOBJ)
											EntityFX(d\buttons[i], 1)
											ScaleEntity(d\buttons[i], 0.03, 0.03, 0.03)
											PositionEntity(d\Buttons[i], ButtonsX, ButtonsY, ButtonsZ)
											RotateEntity(d\buttons[i], 0, buttonsyaw,0)
										EndIf
									Next
								EndIf
								
								If prevkeycard > 0 And d\Keycard = 0 Then
									For i% = 0 To 1
										If d\buttons[i] <> 0 Then
											ButtonsX# = EntityX(d\buttons[i])
											ButtonsY# = EntityY(d\buttons[i])
											ButtonsZ# = EntityZ(d\buttons[i])
											ButtonsYaw# = EntityYaw(d\buttons[i])
											FreeEntity d\buttons[i]
											
											d\buttons[i]= CopyEntity(ButtonOBJ)
											EntityFX(d\buttons[i], 1)
											ScaleEntity(d\buttons[i], 0.03, 0.03, 0.03)
											PositionEntity(d\Buttons[i], ButtonsX, ButtonsY, ButtonsZ)
											RotateEntity(d\buttons[i], 0, buttonsyaw,0)
										EndIf
									Next
								EndIf
							EndIf
							If ReadBool(otherindex2, 3) Then
								prevcode$ = d\code
								d\ChangedCode = udp_ReadByte()
								If prevcode = "" And d\ChangedCode = True Then
									d\Code = "ABCD"
									For i% = 0 To 1
										If d\buttons[i] <> 0 Then
											ButtonsX# = EntityX(d\buttons[i])
											ButtonsY# = EntityY(d\buttons[i])
											ButtonsZ# = EntityZ(d\buttons[i])
											ButtonsYaw# = EntityYaw(d\buttons[i])
											FreeEntity d\buttons[i]
											
											d\buttons[i]= CopyEntity(ButtonCodeOBJ)
											EntityFX(d\buttons[i], 1)
											ScaleEntity(d\buttons[i], 0.03, 0.03, 0.03)
											PositionEntity(d\Buttons[i], ButtonsX, ButtonsY, ButtonsZ)
											RotateEntity(d\buttons[i], 0, buttonsyaw,0)
										EndIf
									Next
								EndIf
								
								If prevcode <> "" And d\ChangedCode = False Then
									d\Code = ""
									For i% = 0 To 1
										If d\buttons[i] <> 0 Then
											ButtonsX# = EntityX(d\buttons[i])
											ButtonsY# = EntityY(d\buttons[i])
											ButtonsZ# = EntityZ(d\buttons[i])
											ButtonsYaw# = EntityYaw(d\buttons[i])
											FreeEntity d\buttons[i]
											
											d\buttons[i]= CopyEntity(ButtonOBJ)
											EntityFX(d\buttons[i], 1)
											ScaleEntity(d\buttons[i], 0.03, 0.03, 0.03)
											PositionEntity(d\Buttons[i], ButtonsX, ButtonsY, ButtonsZ)
											RotateEntity(d\buttons[i], 0, buttonsyaw,0)
										EndIf
									Next
								EndIf
							EndIf
						Else
							If ReadBool(otherindex2, 2) Then udp_ReadByte()
							If ReadBool(otherindex2, 3) Then udp_ReadByte()
						EndIf
					Wend
				Case M_EVENTSWRITE
					If QuickLoadPercent = -1 Or QuickLoadPercent = 100 Then
						While True
							otherindex = udp_ReadByte()
							If otherindex = 0 Then Exit
							e.Events = M_Event[otherindex]
							otherindex2 = udp_ReadByte()
							otherindex3 = udp_ReadByte()
							floatx# = udp_ReadFloat()
							floaty# = udp_ReadFloat()
							floatz# = udp_ReadFloat()
							If e = Null Then
								e.Events = New Events
								e\EventConst = otherindex2
								e\EventName = FindEventNameConst(otherindex2)
								e\room = Room[otherindex3]
								e\EventState = floatx#
								e\EventState2 = floaty
								e\EventState3 = floatz
								e\Received = 1
								e\StaticEvent = False
								SetEventID(e, otherindex)
								SetEventVarEx(e, True)
							Else
								e\Received = True
								e\StaticEvent = False
								If floatx > e\EventState Then e\EventState = floatx
								If floaty > e\EventState2 Then e\EventState2 = floaty
								If floatz > e\EventState3 Then e\EventState3 = floatz
							EndIf
						Wend
						For e.Events = Each Events
							If e\Received = False Then
								If (Not IsANotRemovedEvent(e)) Then
									If (Not IsABlockedEvent(e)) Then
										e\StaticEvent = True
										RemoveEvent(e)
									EndIf
								EndIf
							Else
								e\Received = False
							EndIf
						Next
					EndIf
				Case M_ROOMOBJECTS
					While True
						otherindex = udp_ReadByte()
						If otherindex = 0 Then Exit
						r.Rooms = Room[otherindex]
						otherindex2 = udp_ReadByte()
						
						floatx# = udp_ReadFloat()
						floaty# = udp_ReadFloat()
						floatz# = udp_ReadFloat()
						
						floatyaw# = udp_ReadFloat()
						floatpitch# = udp_ReadFloat()
						floatroll# = udp_ReadFloat()
						If r <> Null Then
							If GrabbedEntity <> r\Objects[otherindex2] Then
								If r\Objects[otherindex2] <> 0 Then
									PositionEntity(r\Objects[otherindex2], floatx, floaty, floatz, True)
									RotateEntity(r\Objects[otherindex2], floatyaw, floatpitch, floatroll, True)
								EndIf
							EndIf
							r\ObjectStatic[otherindex2] = False
						EndIf
					Wend
				Case M_CORPSESSYNC
					While True
						otherindex = udp_ReadByte()
						If otherindex = 0 Then Exit
						c.p_obj = M_Corpse[otherindex]
						otherindex2 = udp_ReadByte()
						floatx# = udp_ReadFloat()
						floaty# = udp_ReadFloat()
						floatz# = udp_ReadFloat()
						floatyaw# = convertshorttovalue(udp_ReadShort())
						floatscale# = Float(udp_ReadShort())/100.0
						otherindex3 = udp_ReadByte()
						BT.BreachTypes = GetBreachType(otherindex2)
						
						If GetSecondPackedValue(BT\DeadAnimationData) <> 0 Then
							If c = Null Then
								CreateRoleCorpse(otherindex2, floatx, floaty, floatz, floatyaw, floatscale, False, ReadBool(otherindex3,1), otherindex, True)
							Else
								If otherindex2 <> c\breachtype Then
									FreeEntity c\CorpseEntity
									c\CorpseEntity = CreateRoleCorpse(otherindex2, floatx, floaty, floatz, floatyaw, floatscale, True, ReadBool(otherindex3,1))
									c\Timeout = (MilliSecs()+120000)*(Not ReadBool(otherindex3,0))
									c\FallFactor = 70*5
									c\breachtype = otherindex2
									
									
								EndIf

								PositionEntity c\CorpseEntity, floatx, floaty-0.32-BT\FixPivot, floatz
								RotateEntity c\CorpseEntity, BT\FixPitch, floatyaw+BT\FixRotate, 0
								
								c\Received = True
								c\Timeout = (MilliSecs()+120000)*(Not ReadBool(otherindex3,0))
							EndIf
						EndIf
						
					Wend
					
					For c.p_obj = Each p_obj
						If Not c\received Then
							FreeEntity c\CorpseEntity
							Delete c
						Else
							c\received = False
						EndIf
					Next
			End Select
		Case M_OBJECTSOUND
			otherindex = udp_ReadByte()
			
			If multiplayer_Object[otherindex] <> Null Then
				If multiplayer_Object[otherindex]\obj <> 0 Then
					otherindexstr$ = udp_ReadLine()
					floatpitch# = udp_ReadFloat()
					floatyaw# = udp_ReadFloat()
					
					If otherindexstr = "SFX\SCP\513\Bell1.ogg" Then 
						If Curr5131 = Null
							Curr5131 = CreateNPC(NPCtype5131, 0,0,0)
							Curr5131\IsSpawned = True
						EndIf
					EndIf
					
					If otherindexstr <> "" Then
						For snd.sound = Each Sound
							If snd\name = otherindexstr Then
								Play3DSound(Handle(snd), Camera, multiplayer_Object[otherindex]\obj, floatpitch, floatyaw)
								otherindexstr = ""
								Exit
							EndIf
						Next
						If otherindexstr <> "" Then Play3DSound(0, Camera, multiplayer_Object[otherindex]\obj, floatpitch, floatyaw, otherindexstr)
					EndIf
				EndIf
			EndIf
		Case M_SOUND3D

			otherindextosend$ = udp_ReadLine()
			otherindexstr$ = otherindextosend
			floatx# = udp_ReadFloat()
			floaty# = udp_ReadFloat()
			floatz# = udp_ReadFloat()
			floatpitch# = udp_ReadFloat()
			floatyaw# = udp_ReadFloat()
			tempent = CreatePivot()
			PositionEntity tempent, floatx, floaty, floatz
			If otherindexstr = "SFX\SCP\513\Bell1.ogg" Then 
				If Curr5131 = Null
					Curr5131 = CreateNPC(NPCtype5131, 0,0,0)
					Curr5131\IsSpawned = True
				EndIf
			EndIf
			If otherindexstr <> "" Then
				For snd.sound = Each Sound
					If snd\name = otherindexstr Then
						Play3DSoundEntity(Handle(snd), Camera, tempent, floatpitch, floatyaw)
						otherindexstr = ""
						Exit
					EndIf
				Next
				If otherindexstr <> "" Then Play3DSoundEntity(0, Camera, tempent, floatpitch, floatyaw, otherindexstr)
			
			EndIf
			If NetworkServer\MainPlayer Then
				For p.players = Each players
					If p\ID <> ID And p\ID <> 1 Then
						udp_WriteByte M_SOUND3D
						udp_WriteByte 1
						udp_WriteLine otherindextosend
						udp_WriteFloat floatx
						udp_WriteFloat floaty
						udp_WriteFloat floatz
						udp_WriteFloat floatpitch
						udp_WriteFloat floatyaw
						udp_SendMessage(p\ID)
					EndIf
				Next
			EndIf
		Case M_PING
			If NetworkServer\MainPlayer Then 
				playerid\ping = Max(5, MilliSecs()-playerid\lastping)
			Else
				udp_WriteByte(M_PING)
				udp_WriteByte(NetworkServer\MyID)
				udp_SendMessage()
			EndIf
		Case M_LIGHT
			SecondaryLightOn = udp_ReadFloat()
		Case M_MSG
			Msg = udp_ReadLine()
			MsgTimer = udp_ReadInt()
		Case M_CONSOLE
			ConsoleCommand$ = udp_ReadLine()
			MessageTick% = udp_ReadByte()
			ExecuteConsoleCommand(ConsoleCommand, True, MessageTick)
		Case M_SETPOS
			roomname$ = udp_ReadLine()
			floatx = udp_ReadFloat()
			floaty = udp_ReadFloat()
			floatz = udp_ReadFloat()
			PositionEntity Collider, floatx, floaty, floatz
			ResetEntity Collider
			For r.Rooms = Each Rooms
				If r\RoomTemplate\Name = roomname Then
					PlayerRoom = r
					UpdateRooms()
					UpdateEvents()
					Exit
				EndIf
			Next
		Case M_TEXTS
			NetworkServer\textsTicks = udp_ReadByte()
			otherindex2 = 0 
			While True
				otherindex = udp_ReadByte()
				If otherindex = 0 Then Exit
				If multiplayer_Text[otherindex] = Null Then multiplayer_Text[otherindex] = New multiplayer_texts
				multiplayer_Text[otherindex]\fntname = udp_ReadLine()
				multiplayer_Text[otherindex]\txt = udp_ReadLine()
				multiplayer_Text[otherindex]\x = udp_ReadInt()
				multiplayer_Text[otherindex]\y = udp_ReadInt()
				multiplayer_Text[otherindex]\txtColor = udp_ReadInt()
				multiplayer_Text[otherindex]\fntsize = udp_ReadByte()
				multiplayer_Text[otherindex]\Readed = True
			Wend
			For t.multiplayer_texts = Each multiplayer_texts
				If (Not t\readed) Then
					If t\font Then
						For t2.multiplayer_texts = Each multiplayer_texts
							If t2\font = t\font And t2 <> t Then otherindex2 = 1 : Exit
						Next
						If (Not otherindex2) Then FreeFont t\font
					EndIf
					Delete t
				Else
					t\readed = False
				EndIf
			Next
		Case M_DRAWS
			NetworkServer\drawsTicks = udp_ReadByte()
			otherindex2 = 0 
			While True
				otherindex = udp_ReadByte()
				If otherindex = 0 Then Exit
				If multiplayer_Draw[otherindex] = Null Then multiplayer_Draw[otherindex] = New draws
				multiplayer_Draw[otherindex]\filename = udp_ReadLine()
				multiplayer_Draw[otherindex]\x = udp_ReadInt()
				multiplayer_Draw[otherindex]\y = udp_ReadInt()
				multiplayer_Draw[otherindex]\width = udp_ReadInt()
				multiplayer_Draw[otherindex]\height = udp_ReadInt()
				multiplayer_Draw[otherindex]\drawColor = udp_ReadInt()
				multiplayer_Draw[otherindex]\drawtype = udp_ReadByte()
				multiplayer_Draw[otherindex]\Readed = True
			Wend
			For dr.draws = Each draws
				If (Not dr\readed) Then
					If dr\pointer Then
						For dr2.draws = Each draws
							If dr2\pointer = dr\pointer And dr2 <> dr Then otherindex2 = 1 : Exit
						Next
						If (Not otherindex2) Then FreeImage dr\pointer
					EndIf
					Delete dr
				Else
					dr\readed = False
				EndIf
			Next
		Case M_OBJECTS
			NetworkServer\objectsTicks = udp_ReadByte()
			While True
				otherindex = udp_ReadByte()
				If otherindex = 0 Then Exit
				otherindexstr = Multiplayer_Models[udp_ReadShort()]
				floatx = udp_ReadFloat()
				floaty = udp_ReadFloat()
				floatz = udp_ReadFloat()
				floatpitch = convertshorttovalue(udp_ReadShort())
				floatyaw = convertshorttovalue(udp_ReadShort())
				floatroll = convertshorttovalue(udp_ReadShort())
				floatscale = udp_ReadFloat()
				AnimTime = udp_ReadShort()
				lerp% = Max(udp_ReadByte(), 1.0)
				
				If otherindexstr <> "" Then
					If multiplayer_Object[otherindex] = Null Then
						otherindex2 = 0
						For mpobj2.multiplayer_objects = Each multiplayer_objects
							If mpobj2\filename = otherindexstr And mpobj2\obj <> 0 Then
								otherindex2 = CopyEntity(mpobj2\obj)
								Exit
							EndIf
						Next
						If otherindex2 = 0 Then otherindex2 = LoadAnimMesh(otherindexstr)
						If otherindex2 Then
							multiplayer_Object[otherindex] = New multiplayer_objects
							multiplayer_Object[otherindex]\obj = otherindex2
							multiplayer_Object[otherindex]\filename = otherindexstr
							EntityType multiplayer_Object[otherindex]\obj, HIT_MAP
							CheckObjectAnimation(otherindex)
						EndIf
					EndIf
					If multiplayer_Object[otherindex] <> Null Then
						If multiplayer_Object[otherindex]\filename <> otherindexstr Then
							For mpobj2.multiplayer_objects = Each multiplayer_objects
								If mpobj2\filename = otherindexstr And mpobj2\obj <> 0 Then 
									multiplayer_Object[otherindex]\obj = CopyEntity(mpobj2\obj)
									Exit
								EndIf
							Next
							If multiplayer_Object[otherindex]\obj = 0 Then multiplayer_Object[otherindex]\obj = LoadAnimMesh(otherindexstr)
							multiplayer_Object[otherindex]\filename = otherindexstr
							If multiplayer_Object[otherindex]\obj Then 
								EntityType multiplayer_Object[otherindex]\obj, HIT_MAP
								CheckObjectAnimation(otherindex)
							EndIf
						EndIf
						
						If multiplayer_Object[otherindex]\obj <> 0 Then
							ScaleEntity multiplayer_Object[otherindex]\obj, floatscale, floatscale, floatscale
							If multiplayer_Object[otherindex]\haveAnim Then SetAnimTime multiplayer_Object[otherindex]\obj, AnimTime
							
							multiplayer_Object[otherindex]\x = floatx
							multiplayer_Object[otherindex]\y = floaty
							multiplayer_Object[otherindex]\z = floatz
							multiplayer_Object[otherindex]\pitch = floatpitch
							multiplayer_Object[otherindex]\yaw = floatyaw
							multiplayer_Object[otherindex]\roll = floatroll
							multiplayer_Object[otherindex]\lerp = lerp
						EndIf
						
						multiplayer_Object[otherindex]\Readed = True
					EndIf
				EndIf
			Wend
			
			For mpobj.multiplayer_objects = Each multiplayer_objects
				If (Not mpobj\readed) Then
					For snd2.snd3d = Each snd3d
						If snd2\entity = mpobj\obj Then
							If Not snd2\fmod Then
								StopChannel(snd2\soundchn)
								If snd2\tempentity Then FreeEntity snd2\entity
								Delete snd2
							Else
								FSOUND_StopSound(snd2\soundchn)
								FSOUND_Stream_Stop(snd2\sound)
								FSOUND_Stream_Close(snd2\sound)
								Delete snd2
							EndIf
						EndIf
					Next
					FreeEntity mpobj\obj
					Delete mpobj
				Else
					mpobj\readed = False
				EndIf
			Next
		Case M_GETLOAD
			multiplayer_Send(M_START)
			SavePath$ = udp_ReadLine()
			timedsave$ = udp_ReadLine()
			StartLoadGame(SavePath, timedsave)
		Case M_CREATEBULLET
			While udp_ReadAvail() > 0
				udp_ReadShort()
				floatx# = udp_ReadFloat()
				floaty# = udp_ReadFloat()
				floatz# = udp_ReadFloat()
				floatpitch# = udp_ReadFloat()
				floatyaw# = udp_ReadFloat()
				spreadmax# = Float(udp_ReadByte())/10.0
				If ID <> NetworkServer\MyID Then
					If Player[ID] <> Null Then
						ShootTicks = GetGunShootTicks(Player[ID]\UsedWeapon)
						For i = 1 To ShootTicks
							CreateBullet(ID, BULLET_SPEED, floatx, floaty, floatz, floatpitch+Rnd(-spreadmax*GetGunSpreadRate(Player[ID]\UsedWeapon),spreadmax*GetGunSpreadRate(Player[ID]\UsedWeapon)),floatyaw+Rnd(-spreadmax*GetGunSpreadRate(Player[ID]\UsedWeapon), spreadmax*GetGunSpreadRate(Player[ID]\UsedWeapon)))
						Next
						Player[ID]\IsShooted = True
					Else
						CreateBullet(0, BULLET_SPEED, floatx, floaty, floatz, floatpitch,floatyaw)
					EndIf
					
					PlayPlayerSound(Player[ID], GetGunSound(Player[ID]\UsedWeapon),15.0,1)
				EndIf
			Wend
			If NetworkServer\MainPlayer
				For p.players = Each players
					If p\ID <> ID And p\ID <> 1 Then
						udp_WriteByte M_CREATEBULLET
						udp_WriteByte ID
						udp_WriteShort 0
						udp_WriteFloat floatx
						udp_WriteFloat floaty
						udp_WriteFloat floatz
						udp_WriteFloat floatpitch
						udp_WriteFloat floatyaw
						udp_WriteByte spreadmax*10
						udp_SendMessage(p\ID)
					EndIf
				Next
			EndIf
		Case M_CREATEROCKET
			udp_ReadShort()
			floatx# = udp_ReadFloat()
			floaty# = udp_ReadFloat()
			floatz# = udp_ReadFloat()
			floatpitch# = udp_ReadFloat()
			floatyaw# = udp_ReadFloat()
			If ID <> NetworkServer\MyID Then 
				CreateRocket(ROCKET_SPEED, floatx, floaty, floatz, floatpitch,floatyaw, ID)
				Player[ID]\IsShooted = True
			EndIf
		Case M_CREATEGRENADE
			udp_ReadShort()
			floatx# = udp_ReadFloat()
			floaty# = udp_ReadFloat()
			floatz# = udp_ReadFloat()
			floatpitch# = udp_ReadFloat()
			floatyaw# = udp_ReadFloat()
			grenadetype% = udp_ReadByte()
			grenadeslow% = udp_ReadByte()
			If ID <> NetworkServer\MyID Then CreateGrenade(grenadetype, floatx, floaty, floatz, floatpitch,floatyaw, ID, grenadeslow)
		Case M_KILL2
			If playerid\BreachType = MODEL_106 Then
				MoveToPocketDimension()
				MyPlayer\Health = MyPlayer\Health - 55
				If MyPlayer\Health < 1 Then Kill("was killed by "+playerid\name)
			Else 
				Kill("was killed by "+playerid\name)
			EndIf
		Case M_REMOVENPC
			RemoveNPC(M_NPC[udp_ReadByte()], False)
		Case M_CREATENPC
			n.NPCs = CreateNPC(udp_ReadByte(), playerid\x,playerid\y+0.3,playerid\z)
			n\State = udp_ReadFloat()
		Case M_CHATSYNC
			;if Timers[m_id] < MilliSecs() Then DebugLog "Get CHAT "+udp_ReadAvail() : Timers[m_id] = MilliSecs()+1500
			;For cm.Chatmessage = Each chatmessage
			;	Delete cm
			;Next
			NetworkServer\chatTicks = udp_ReadByte()
			
			Local chatID% = udp_ReadShort()
			
			While chatID > 0
				multiplayer_CreateMessage(udp_ReadLine(), chatID)
				chatID = udp_ReadShort()
			Wend
		Case M_SERVERSYNC
			;if Timers[m_id] < MilliSecs() Then DebugLog "Get SERVER "+udp_ReadAvail() : Timers[m_id] = MilliSecs()+1500
			udp_UpdateDelta(True)
			NetworkServer\MainPlayer = False
			NetworkServer\CurrentUpdate = udp_ReadByte()
			multiplayer_breach_SetPlayerType(udp_ReadByte())
			NetworkServer\JumpHeight = udp_ReadFloat()
			b_br\LobbyTimer = MilliSecs()+udp_ReadInt()
			b_br\BreachTimer = MilliSecs()+udp_ReadInt()
			ServerPing = udp_ReadShort()
			
			NetworkServer\PlayersCount = 1
			
			Local PlayerPointer
			
			While True
				PlayerPointer = udp_ReadByte()
				If Not PlayerPointer Then 
					Exit
				Else
					If PlayerPointer = NetworkServer\MyID Then
						SkipDataPlayer()
						PlayerPointer = udp_ReadByte()
						If Not PlayerPointer Then Exit
					EndIf
					
					NetworkServer\PlayersCount = NetworkServer\PlayersCount + 1
					
					If Player[PlayerPointer] = Null Then
						multiplayer_CreatePlayer(PlayerPointer)
						multiplayer_InitPlayer(PlayerPointer)
					EndIf
					
					playerid = Player[PlayerPointer]
					
					If NetworkServer\CurrentUpdate = 1 Then
						otherindex2 = udp_ReadByte()

						If otherindex2 <> 0 Then
							playerid\x = udp_ReadFloat()
							playerid\y = udp_ReadFloat()
							playerid\z = udp_ReadFloat()
							playerid\yaw = convertshorttovalue(udp_ReadShort())
							playerid\BonePitch = convertshorttovalue(udp_ReadShort())
							playerid\PLAYER_MOVE = udp_ReadByte()
							
							playerid\BlinkTimer = udp_ReadFloat()
							playerid\wearingdir = udp_ReadByte()
							otherindex4 = ReadBool(playerid\wearingdir, 0)
							playerid\WearingNightVision = ReadBool(playerid\wearingdir, 1)
							playerid\WearingGasMask = ReadBool(playerid\wearingdir, 2)
							playerid\WearingVest = ReadBool(playerid\wearingdir, 3)
							playerid\Handcuffed = ReadBool(playerid\wearingdir, 5)
							playerid\microhidshoot = ReadBool(playerid\wearingdir, 6)
							
							playerid\PlayerSoundVolume = udp_ReadByte()
							playerid\ping = udp_ReadShort()
							otherindex5 = udp_ReadByte()
							playerid\UsedWeapon = udp_ReadByte()
							playerid\SelectedItem = udp_ReadShort()
							playerid\PlayerRoomID = udp_ReadByte()
							
							multiplayer_SetPlayerRoom(playerid)
							multiplayer_InitSettingsForPlayer(playerid, otherindex2, playerid\TextureID, otherindex4, otherindex5)
						Else
							playerid\x = 0
							playerid\y = 0
							playerid\z = 0
							
							playerid\ping = udp_ReadShort()
							otherindex5 = udp_ReadByte()
							multiplayer_InitSettingsForPlayer(playerid, otherindex2, playerid\TextureID, 0, otherindex5)
						EndIf
					Else
						If udp_ReadByte() = 0 Then playerid\Ready = "Not Ready" Else playerid\Ready = "Ready"
						playerid\ping = udp_ReadShort()
						playerid\IsLoad = udp_ReadByte()
					EndIf
					playerid\Synced = True
				EndIf
			Wend
			
			MyPlayer\Synced = True
			
			For p.players = Each players
				If Not p\Synced Then
					multiplayer_DisconnectPlayer(p)
				Else
					p\Synced = False
				EndIf
			Next

			SecondaryLightOn = convertshorttovalue(udp_ReadShort())
			PlayerPointer = udp_ReadByte()
			Contained106 = ReadBool(PlayerPointer, 0)
			RemoteDoorOn = ReadBool(PlayerPointer, 1)
			MTFtimer = udp_ReadShort()
			ItemsRotateRand = convertshorttovalue(udp_ReadShort())
			exploded = udp_ReadByte()
			
			If exploded = 1 And (Not NetworkServer\notshouldplaysiren) Then
				If NetworkServer\warheadschannel = 0 Then NetworkServer\warheadschannel = PlaySound_Strict(LoadTempSound("SFX\Ending\GateB\DetonatingAlphaWarheads.ogg"))
				If Not ChannelPlaying(NetworkServer\sirenchannel) Then
					NetworkServer\sirenchannel = PlaySound_Strict(GateBSirenSFX)
				EndIf
			Else
				If ChannelPlaying(NetworkServer\warheadschannel) Then StopChannel(NetworkServer\warheadschannel)
				If ChannelPlaying(NetworkServer\sirenchannel) Then StopChannel(NetworkServer\sirenchannel)
				NetworkServer\warheadschannel = 0
			EndIf
			
			If multiplayer_IsFullSync() And NetworkServer\CurrentUpdate = 1 Then

				InfoDir = udp_ReadByte()
				
				If ReadBool(infodir, 0) Then
					If Not NetworkServer\Breach Then
						If KillTimer >= 0 Then Min(-1, KillTimer)
					EndIf
				Else
					If (Not (KillTimer >= 0))  And NetworkServer\Breach Then multiplayer_RequestRole(0)
				EndIf
				
				NoClip = ReadBool(infodir, 1)
				MyPlayer\Handcuffed = ReadBool(infodir, 2)
				NetworkServer\NoclipAnticheat = ReadBool(infodir, 4)
				rcon\Authorized = ReadBool(infodir, 6)
				Injuries = udp_ReadFloat()
				
				MyPlayer\Health = udp_ReadShort()
				If MyPlayer\PrevHealth > MyPlayer\Health And MyPlayer\BreachType > 0 And (Not ReadBool(infodir, 3)) Then CameraShake = (MyPlayer\PrevHealth-MyPlayer\Health)/15.0
				MyPlayer\PrevHealth = MyPlayer\Health
				
				MyPlayer\size = Float(udp_ReadShort())/100
				MyPlayer\SpeedMult = udp_ReadFloat()
				
				playerannounc = ReadBool(infodir, 5)
				If playerannounc = True And MyPlayer\Announcement = False Then PlaySound_Strict(LoadTempSound("GFX\multiplayer\game\sounds\Announcement.ogg"))
				If playerannounc = False And MyPlayer\Announcement = True Then PlaySound_Strict(LoadTempSound("GFX\multiplayer\game\sounds\Announcement2.ogg"))
				MyPlayer\Announcement = playerannounc
				
				PlayerIntercom\CheckIntercom = MilliSecs()+Max(udp_ReadInt(), 0)-1
				PlayerIntercom\TimeIntercom = MilliSecs()+Max(udp_ReadInt(), 0)-1
				
				statepd = udp_ReadByte()
				If pocketdimension106 <> Null Then pocketdimension106\EventState2 = statepd
				
				serverpositionid% = udp_ReadShort()
				
				If serverpositionid <> 0 Then
					If serverpositionid <> CurrentPositionID Then
						IsInteger% = False
						CurrentPositionID% = serverpositionid
						
						floatx# = udp_ReadFloat()
						floaty# = udp_ReadFloat()
						floatz# = udp_ReadFloat()
						IsInteger = udp_ReadByte()
						If Not IsInteger Then 
							roomname$ = udp_ReadLine()
						Else
							roomname$ = Str(udp_ReadByte())
						EndIf
						
						PositionEntity Collider, floatx, floaty, floatz
						ResetEntity Collider
						
						If Not IsInteger Then
							For r.Rooms = Each Rooms
								If r\RoomTemplate\Name = roomname Then
									PlayerRoom = r
									UpdateRooms()
									UpdateEvents()
									Exit
								EndIf
							Next
						Else
							For r.Rooms = Each Rooms
								If r\ID = Int(roomname) Then
									PlayerRoom = r
									UpdateRooms()
									UpdateEvents()
									Exit
								EndIf
							Next
						EndIf
					EndIf
					
				EndIf
				
				
				If Injuries < 0.1 Then Bloodloss = 0
				
			EndIf
			
			If (NetworkServer\CurrentUpdate = 1) And MainMenuOpen = True Then
				StartNewGame()
				Return
			EndIf
		Case M_REMOTEDOOR
			RemoteDoorOn = True
			For e.Events = Each Events
				If e\EventName = "gateaentrance" Then
					e\EventState3 = 1
					e\room\RoomDoors[1]\open = True
				ElseIf e\EventName = "exit1" Then
					e\EventState3 = 1
					e\room\RoomDoors[4]\open = True
				EndIf
			Next
			
			PlaySound_Strict(LoadTempSound("SFX\Character\MTF\Announc.ogg"))
			If NetworkServer\MainPlayer
				For p.players = Each players
					If p\ID <> 1 Then
						udp_WriteByte M_REMOTEDOOR
						udp_WriteByte 1
						udp_SendMessage(p\ID)
					EndIf
				Next
			EndIf
		Case M_SHOOT
			If Not player_isdead() Then
				If (Not multiplayer_IsASCP(MyPlayer\BreachType)) Then
					pa.Particles = CreateParticle(EntityX(Collider),EntityY(Collider)-0.1,EntityZ(Collider), 5, 0.06, 0.2, 80)
					pa\speed = 0.001
					pa\SizeChange = 0.003
					pa\A = 0.8
					pa\Achange = -0.02
					
					pvt = CreatePivot()
					PositionEntity pvt, EntityX(Collider)+Rnd(-0.05,0.05),EntityY(Collider)-0.05,EntityZ(Collider)+Rnd(-0.05,0.05)
					TurnEntity pvt, 90, 0, 0
					EntityPick(pvt,0.3)
					de.decals = CreateDecal(Rand(15,16), PickedX(), PickedY()+0.005, PickedZ(), 90, Rand(360), 0)
					de\size = Rnd(0.03,0.08)*Min(Injuries,3.0) : EntityAlpha(de\obj, 1.0) : ScaleSprite de\obj, de\size, de\size
					
					FreeEntity pvt
					multiplayer_WriteDecal(de,1)
					If Not NetworkServer\Breach Then
						Injuries = Injuries + Rnd(2.0, 3.0)
						If Injuries >= 10 Then Kill("was killed by "+playerid\name)
					Else
						If Injuries <= 1.0 Then 
							Injuries = 1.01
						Else
							Injuries = Injuries+0.01
						EndIf
						MyPlayer\Health = MyPlayer\Health - ( Rnd(GetGunDamage(playerid\UsedWeapon)-3, GetGunDamage(playerid\UsedWeapon)+3)-(WearingVest*8) )
						If multiplayer_IsASCP(playerid\BreachType) Then MyPlayer\Health = MyPlayer\Health-Rand(30,40)
						If MyPlayer\Health < 1 Then Kill("was killed by "+playerid\name)
					EndIf
					;FirstInjuries = Injuries
					;if Injuries = 0 Then FirstInjuries
					;MyHP = 100/FirstInjuries
				Else
					MyPlayer\Health = MyPlayer\Health - GetGunDamage(playerid\UsedWeapon) - Rand(1, 2)
					Injuries = 0
					If MyPlayer\Health < 0 Then
						GodMode = False
						Kill("was killed by "+playerid\name)
					EndIf
				EndIf
			EndIf
		Case M_EXPL
			ExplosionTimer = udp_ReadInt()
			If NetworkServer\MainPlayer
				For p.players = Each players
					If p\ID <> 1 Then
						udp_WriteByte M_EXPL
						udp_WriteByte 1
						udp_WriteInt ExplosionTimer
						udp_SendMessage(p\ID)
					EndIf
				Next
			EndIf
		Case M_WARHEADS
			b_br\WonCategory = udp_ReadLine()
			If b_br\WonCategory <> "Warheads" Then
				b_br\wonr = udp_ReadByte()
				b_br\wong = udp_ReadByte()
				b_br\wonb = udp_ReadByte()
				b_br\WonTimer = 70*20
				If udp_ReadByte() = 0 Then
					NetworkServer\notshouldplaysiren% = False
				Else
					NetworkServer\notshouldplaysiren% = True
				EndIf
			Else
				udp_ReadByte()
				udp_ReadByte()
				udp_ReadByte()
				
				If udp_ReadByte() = 0 Then
					networkserver\notshouldplaysiren% = False
				Else
					networkserver\notshouldplaysiren% = True
				EndIf
				
				b_br\WonCategory = ""
			EndIf
		Case M_ANNOUNC
			PlayAnnouncement(udp_ReadLine(), False)
		Case M_EVENT173
			For e.events = Each events
				If e\EventName = "173" Then
					e\EventState3 = udp_ReadFloat()
					Exit
				EndIf
			Next
			If NetworkServer\MainPlayer
				For p.players = Each players
					If p\ID <> 1 Then
						udp_WriteByte M_EVENT173
						udp_WriteByte 1
						If e <> Null Then udp_WriteFloat e\EventState3
						udp_SendMessage(p\ID)
					EndIf
				Next
			EndIf
		Case M_STOPSOUND
			For e.Events = Each Events
				If e\EventName = "alarm" Then 
					If e\room\NPC[0] <> Null Then RemoveNPC(e\room\NPC[0])
					If e\room\NPC[1] <> Null Then RemoveNPC(e\room\NPC[1])
					If e\room\NPC[2] <> Null Then RemoveNPC(e\room\NPC[2])

					PositionEntity Curr173\Collider, 0,0,0
					ResetEntity Curr173\Collider
					ShowEntity Curr173\obj
					RemoveEvent(e)
					Exit
				EndIf
			Next
			CreateConsoleMsg("Stopped all sounds.")
			If NetworkServer\MainPlayer
				For p.players = Each players
					If p\ID <> 1 Then
						udp_WriteByte M_STOPSOUND
						udp_WriteByte 1
						udp_SendMessage(p\ID)
					EndIf
				Next
			EndIf
		Case M_FEMUR
			For e.events = Each events
				If e\EventName = "room106" Then
					If e\EventState = 0 Then
						e\EventState = 1
						If SoundTransmission = True Then ;only play sounds if transmission is on
							If e\SoundCHN2 <> 0 Then
								If ChannelPlaying(e\SoundCHN2) Then StopChannel e\SoundCHN2
							EndIf 
							FemurBreakerSFX = LoadSound_Strict("SFX\Room\106Chamber\FemurBreaker.ogg")
							e\SoundCHN2 = PlaySound_Strict (FemurBreakerSFX)
						EndIf
					EndIf
					Exit
				EndIf
			Next
			If NetworkServer\MainPlayer
				For p.players = Each players
					If p\ID <> 1 Then
						udp_WriteByte M_FEMUR
						udp_WriteByte 1
						udp_SendMessage(p\ID)
					EndIf
				Next
			EndIf
		Case M_UNLOCKEXITS
			For e.Events = Each Events
				If e\EventName = "gateaentrance" Then
					e\EventState3 = 1
					e\room\RoomDoors[1]\open = True
				ElseIf e\EventName = "exit1" Then
					e\EventState3 = 1
					e\room\RoomDoors[4]\open = True
				EndIf
			Next
			CreateConsoleMsg("Gate A and B are now unlocked.")	
			
			RemoteDoorOn = True
			If NetworkServer\MainPlayer
				For p.players = Each players
					If p\ID <> ID And p\ID <> 1 Then
						udp_WriteByte M_UNLOCKEXITS
						udp_WriteByte 1
						udp_SendMessage(p\ID)
					EndIf
				Next
			EndIf
		Case M_KILL

		Case M_DECAL
			pers% = udp_ReadByte()
			pers2# = udp_ReadFloat()
			pers3# = udp_ReadFloat()
			pers4# = udp_ReadFloat()
			pers5# = udp_ReadFloat()
			pers6# = udp_ReadFloat()
			pers7# = udp_ReadFloat()
			pers8# = udp_ReadFloat()
			pers9# = udp_ReadFloat()
			pers10# = udp_ReadFloat()
			pers11# = udp_ReadFloat()
			pers12# = udp_ReadFloat()
			pers13# = udp_ReadFloat()
			des.decals = CreateDecal(pers%, pers2#, pers3#, pers4#, pers7#, pers5#, pers6#)
			If pers = 5 Then EntityColor(des\obj, 0.0, Rnd(200, 255), 0.0)
			des\SizeChange = pers8#
			des\Size = pers9#
			des\MaxSize = pers10#
			des\AlphaChange = pers11#
			des\Alpha = pers12#
			des\timer = pers13#
			EntityAlpha(des\obj, des\Alpha)
			ScaleSprite(des\obj,des\Size,des\Size)
		Case M_FIXITEMS
			If ID <> NetworkServer\MyID Or (Not NetworkServer\MainPlayer) Then
				itnameid = udp_ReadInt()
				For its.ItemTemplates = Each ItemTemplates
					If its\templateID = itnameID Then
						it.Items = CreateItem(its\name, its\tempname, EntityX(playerid\Pivot),EntityY(GetPlayerCamera(ID))+0.1,EntityZ(playerid\Pivot))
						EntityType (it\collider, HIT_ITEM)
						it\picker = 0
						Exit
					EndIf
				Next
			EndIf
		Case M_DOOR
			;if Timers[m_id] < MilliSecs() Then DebugLog "Get M_DOOR "+udp_ReadAvail() : Timers[m_id] = MilliSecs()+1500
			otherindex = udp_ReadShort()
			otherindex2 = udp_ReadByte()
			otherindex3 = udp_ReadByte()

			If ID <> NetworkServer\MyID Then
				For d.Doors = Each Doors
					If d\ID = otherindex Then
						d\locked = otherindex3
						If d\open <> otherindex2 Then UseDoor(d, False, True, False, "", True)
						Exit
					EndIf
				Next
			EndIf
		Case M_SERVERCLOSE
			ShouldRestartServer = True
			Return 0
		Case M_RESTARTSERVERBREACH
			ShouldRestartServer = True
			Return 0
		Case M_VOICESTOP
		
			If playerid <> Null And ID <> NetworkServer\MyID Then
				playerid\voicerelease = True
			EndIf
			
			If NetworkServer\MainPlayer
				For p.players = Each players
					If p\ID <> ID And p\ID <> 1 Then
						udp_WriteByte M_VOICESTOP
						udp_WriteByte ID
						udp_SendMessage(p\ID)
					EndIf
				Next
			EndIf
		Case M_VOICE
			;debuglog "Get voice: "+udp_ReadAvail()
			vSrc = CreateBank(udp_ReadAvail()-1)
			udp_ReadBytes(vSrc,0,BankSize(vSrc))
			Local IsRadio% = udp_ReadByte()
			If playerid <> Null And ID <> NetworkServer\MyID Then
				Local currtime% = MilliSecs()
				playerid\voicedelta = Min(Max(currtime-playerid\prevvoicedelta, 5), 1000)
				playerid\prevvoicedelta = currtime
				
				If playerid\volume <> 0 Then
					pcm = opus_pcm_decode(playerid\OpusDecoder, vSrc, 0)
					If pcm <> 0 Then
						voice_player_receive(ID, pcm, playerid\voicedelta, IsRadio)
						FreeBank pcm
					EndIf
				EndIf
			EndIf
			If NetworkServer\MainPlayer
				For p.players = Each players
					If p\ID <> ID And p\ID <> 1 Then
						udp_WriteByte M_VOICE
						udp_WriteByte ID
						udp_WriteBytes vSrc, 0, BankSize(vSrc)
						udp_WriteByte p\CurrentRadio
						udp_SendMessage(p\ID)
					EndIf
				Next
			EndIf
			FreeBank vSrc
		Case M_SOUND
			;if GetMilliSecs(5) then
			otherindexstr$ = udp_ReadLine()
			floatx# = udp_ReadFloat()
			floaty# = udp_ReadFloat()
			If playerid <> Null Then
				If playerid\Pivot <> 0 Then
					If ID <> NetworkServer\MyID Then
						PlayPlayerSound(playerid, otherindexstr,floatx,floaty)
					Else
						If udp_ReadByte() = 0 Then
							If (Not NetworkServer\MainPlayer) Then
								If SelectedDoor <> Null Then
									Select Lower(otherindexstr)
										Case "sfx\interact\scanneruse1.ogg"
													
											If SelectedDoor\Code = Str(AccessCode) Then
												GiveAchievement(AchvMaynard)
											ElseIf SelectedDoor\Code = "7816"
												GiveAchievement(AchvHarp)
											EndIf									
											
											SelectedDoor\locked = 0
											UseDoor(SelectedDoor,True,True,False)
											SelectedDoor = Null
											ResetMouse()
										Case "sfx\interact\scanneruse2.ogg"
											KeypadMSG = "ACCESS DENIED"
											KeypadTimer = 210
											KeypadInput = ""	
									End Select
								EndIf
								PlaySound_Strict(LoadTempSound(otherindexstr))
							EndIf
						EndIf
					EndIf
				EndIf
			EndIf
				;StartMillISecs(5, 1000)
			;EndIf
	End Select
End Function
Function GetArmour#()
	Select MyPlayer\BreachType
		Case CLASSD_MODEL,SCIENTIST_MODEL,MODEL_CLERK,JANITOR_MODEL,WORKER_MODEL
			Return -1.5+(WearingVest*0.5)
		Case HAOS_MODEL
			Return 0.5
		Case NTF_MODEL
			Return 0.5
		Case GUARD_MODEL
			Return 0.2
	End Select
End Function
Function SynchronizeItem(it.Items)
	If Not udp_GetStream() Then Return
	udp_ByteStreamWriteChar M_FIXITEMS 
	udp_ByteStreamWriteInt it\itemtemplate\templateID
End Function
Function FindNearestID(n.NPCs)
	Local nearid, dist2#
	For p.players = Each players
		If (Not p\IsDead) And (Not multiplayer_IsASCP(p\BreachType)) Then
			nearid% = p\ID
			dist2 = EntityDistance(n\Collider,p\Pivot)
			For ps.players = Each players
				If p <> ps And (Not ps\IsDead) And (Not multiplayer_IsASCP(ps\BreachType)) Then
					If dist2>EntityDistance(n\Collider,ps\Pivot)
						nearid = 0
						Exit
					EndIf
				EndIf
			Next
		EndIf
		If nearid > 0 Then Return nearid
	Next
	Return NetworkServer\MyID
End Function
Function GetPlayerHead(i)
	If i = NetworkServer\MyID Or Player[i] = Null Then Return Camera
	Return Player[i]\PlayerHead
End Function

Function GetPlayerSpine(i)
	Return Player[i]\PlayerSpine
End Function

Function GetPlayerHeadOffset#(i)
	Return Player[i]\PlayerHeadOffset
End Function

Function GetPlayerHand(i)
	Return Player[i]\PlayerHand
End Function

Function GetPlayerForearm(i)
	Return Player[i]\PlayerForearm
End Function

Function AttachObject(objectid%, xbone#, ybone#, zbone#, pitchbone#, yaw#, roll#, startint, endint)
	
	If startint = -1 Then
		If endint > 0 Then
			PositionEntity(objectid, xbone,ybone,zbone, True)
			RotateEntity objectid,pitchbone,yaw,roll, True
			ShowEntity objectid
			Return True
		Else
			HideEntity objectid
		EndIf
	Else
		If startint = endint Then
			PositionEntity(objectid, xbone,ybone,zbone, True)
			RotateEntity objectid,pitchbone,yaw,roll, True
			ShowEntity objectid
			Return True
		Else
			HideEntity objectid
		EndIf
	EndIf
	Return False
End Function
Function InitPlayerObjects(i)
	Local tex%, ex2
	If Player[i]\AttachObjects = Null Then Player[i]\AttachObjects = New Attachs
	If Player[i]\AttachObjects\BallisticVest = 0 Then
		tex = LoadTexture_Strict("GFX\items\Vest.png")
		tex2 = LoadTexture_Strict("GFX\items\mp5sd_main.png")
		
		If NEWYEARINDEX Then
			Player[i]\AttachObjects\NewYearHat = CopyEntity(NY_HAT)
			ScaleEntity(Player[i]\AttachObjects\NewYearHat, 0.026,0.023,0.026)
		EndIf
		
		Player[i]\AttachObjects\BallisticVest = CopyEntity(VEST_OBJ)
		EntityTexture Player[i]\AttachObjects\BallisticVest, tex
		ScaleEntity(Player[i]\AttachObjects\BallisticVest, 0.022,0.045,0.022)
		Player[i]\AttachObjects\GasMaskObj = CopyEntity(GASMASK_OBJ)
		ScaleEntity(Player[i]\AttachObjects\GasMaskObj, 0.02,0.02,0.02)
		Player[i]\AttachObjects\NVGobj = CopyEntity(NVG_OBJ)
		ScaleEntity(Player[i]\AttachObjects\NVGobj, 0.02,0.02,0.02)
		
		Player[i]\AttachObjects\Gun[GUN_USP] = CopyEntity(PISTOL_OBJECT)
		Player[i]\AttachObjects\Gun[GUN_P90] = CopyEntity(P90_OBJECT)
		Player[i]\AttachObjects\Gun[GUN_MP5SD] = CopyEntity(MP5_OBJECT)
		Player[i]\AttachObjects\Gun[GUN_BAZOOKA] = CopyEntity(RPG_OBJECT)
		Player[i]\AttachObjects\Gun[GUN_MINIGUN] = CopyEntity(MINIGUN_OBJECT)
		Player[i]\AttachObjects\Gun[GUN_MICROHID] = CopyEntity(MICROHID_OBJECT)
		Player[i]\AttachObjects\Gun[GUN_DEAGLE] = CopyEntity(DEAGLE_OBJECT)
		Player[i]\AttachObjects\Gun[GUN_SHOTGUN] = CopyEntity(SHOTGUN_OBJECT)
		Player[i]\AttachObjects\Gun[GUN_M4A4] = CopyEntity(M4A4_OBJECT)
		Player[i]\AttachObjects\Gun[GUN_HANDCUFFS] = CopyEntity(HANDCUFFS_OBJECT)
		Player[i]\AttachObjects\Gun[GUN_GRENADE] = CopyEntity(GRENADE_OBJECT)
		Player[i]\AttachObjects\Gun[GUN_GRENADEFLASHBANG] = CopyEntity(GRENADEFLASHBANG_OBJECT)
		Player[i]\AttachObjects\Gun[GUN_GRENADESMOKE] = CopyEntity(GRENADESMOKE_OBJECT)
		Player[i]\AttachObjects\Gun[GUN_KNIFE] = CopyEntity(KNIFE_OBJECT)
		Player[i]\AttachObjects\Gun[GUN_HKG36] = CopyEntity(HKG36_OBJECT)
		EntityTexture Player[i]\AttachObjects\Gun[GUN_MP5SD], tex2
		ScaleEntity(Player[i]\AttachObjects\Gun[GUN_HKG36], 0.027,0.027,0.027)
		ScaleEntity(Player[i]\AttachObjects\Gun[GUN_P90], 0.02,0.02,0.02)
		ScaleEntity(Player[i]\AttachObjects\Gun[GUN_USP], 0.02,0.02,0.02)
		ScaleEntity(Player[i]\AttachObjects\Gun[GUN_MP5SD], 0.015,0.015,0.015)
		ScaleEntity(Player[i]\AttachObjects\Gun[GUN_BAZOOKA], 0.012,0.012,0.012)
		ScaleEntity(Player[i]\AttachObjects\Gun[GUN_MINIGUN], 0.014,0.014,0.014)
		ScaleEntity(Player[i]\AttachObjects\Gun[GUN_MICROHID], 0.015,0.015,0.015)
		ScaleEntity(Player[i]\AttachObjects\Gun[GUN_DEAGLE], 0.02,0.02,0.02)
		ScaleEntity(Player[i]\AttachObjects\Gun[GUN_SHOTGUN], 0.016,0.016,0.016)
		ScaleEntity(Player[i]\AttachObjects\Gun[GUN_M4A4], 0.02,0.02,0.02)
		ScaleEntity(Player[i]\AttachObjects\Gun[GUN_GRENADE], 0.012,0.012,0.012)
		ScaleEntity(Player[i]\AttachObjects\Gun[GUN_GRENADEFLASHBANG], 0.012,0.012,0.012)
		ScaleEntity(Player[i]\AttachObjects\Gun[GUN_GRENADESMOKE], 0.012,0.012,0.012)
		ScaleEntity(Player[i]\AttachObjects\Gun[GUN_KNIFE], 0.012,0.012,0.012)
		ScaleEntity(Player[i]\AttachObjects\Gun[GUN_HANDCUFFS], 0.012,0.012,0.012)
		For a = GUN_USP To GUN_GRENADESMOKE
			HideEntity Player[i]\AttachObjects\Gun[a]
		Next
		

		If Player[i]\AttachObjects\NewYearHat <> 0 Then
			Local BT.breachtypes = GetBreachType(Player[i]\BreachType)
			If BT\hitboxx = 0.15 And BT\hitboxy = 0.52 And BT\hitboxz = 0.15 Then
				If FindChild(Player[i]\AttachObjects\NewYearHat, "Bone.003") <> 0 Then
					MoveEntity FindChild(Player[i]\AttachObjects\NewYearHat, "Bone.003"), -0.3,0,0
					Player[i]\AttachObjects\NYHatBone[0] = rd_AddEntity(FindChild(Player[i]\AttachObjects\NewYearHat, "Bone.004"), FindChild(Player[i]\AttachObjects\NewYearHat, "Bone.003"))
					Player[i]\AttachObjects\NYHatBone[1] = rd_AddEntity(FindChild(Player[i]\AttachObjects\NewYearHat, "Bone.003"), FindChild(Player[i]\AttachObjects\NewYearHat, "Bone.003"))
					EntityParent Player[i]\AttachObjects\NewYearHat, GetPlayerHead(i)
					PositionEntity Player[i]\AttachObjects\NewYearHat, EntityX(GetPlayerHead(i))-2.15, EntityY(GetPlayerHead(i)), EntityZ(GetPlayerHead(i))+0.2
					RotateEntity Player[i]\AttachObjects\NewYearHat, EntityPitch(Player[i]\AttachObjects\NewYearHat, True), EntityYaw(Player[i]\AttachObjects\NewYearHat, True)+90, EntityRoll(Player[i]\AttachObjects\NewYearHat, True), True
				Else
					FreeEntity Player[i]\AttachObjects\NewYearHat
					Player[i]\AttachObjects\NewYearHat = 0
				EndIf
			Else
				FreeEntity Player[i]\AttachObjects\NewYearHat
				Player[i]\AttachObjects\NewYearHat = 0
			EndIf
		EndIf
		
		If Player[i]\AttachObjects\NewYearHat <> 0 Then HideEntity Player[i]\AttachObjects\NewYearHat
		HideEntity Player[i]\AttachObjects\GasMaskObj
		HideEntity Player[i]\AttachObjects\NVGobj
		HideEntity Player[i]\AttachObjects\BallisticVest
		FreeTexture tex
		FreeTexture tex2
	EndIf
	
End Function
Function FreePlayerObjects(p.players)
	If p\AttachObjects = Null Or NetworkServer\MyID = p\ID Then Return
	If p\AttachObjects\BallisticVest <> 0 Then
		FreeEntity p\AttachObjects\BallisticVest
		FreeEntity p\AttachObjects\GasMaskObj
		FreeEntity p\AttachObjects\NVGobj
		If p\AttachObjects\NewYearHat <> 0 Then
			rd_Remove(p\AttachObjects\NYHatBone[0])
			rd_Remove(p\AttachObjects\NYHatBone[1])
		EndIf
		;if p\AttachObjects\NewYearHat <> 0 Then FreeEntity p\AttachObjects\NewYearHat
		For a = GUN_USP To GUN_GRENADESMOKE
			FreeEntity p\AttachObjects\Gun[a]
		Next
		p\AttachObjects\BallisticVest = 0
	EndIf
	Delete p\AttachObjects
End Function
Function InitMultiplayerImages()
	mpimg\AFKIMG% = LoadImage_Strict("GFX\multiplayer\game\images\afk.png")
	MaskImage (mpimg\AFKIMG, 0, 0, 0)
	mpimg\SoundIMG% = LoadImage_Strict("GFX\multiplayer\game\images\sound.png")
	MaskImage (mpimg\SoundIMG, 0, 0, 0)
	mpimg\VoicePL% = LoadImage_Strict("GFX\multiplayer\game\images\sound.png")
	mpimg\VoiceME% = LoadImage_Strict("GFX\multiplayer\game\images\va.png")
	mpimg\VoiceNO% = LoadImage_Strict("GFX\multiplayer\game\images\vt.png")
	
	mpimg\list_voice% = LoadImage_Strict("GFX\multiplayer\menu\voice.png")
	mpimg\list_nocheat% = LoadImage_Strict("GFX\multiplayer\menu\nocheat.png")
	mpimg\lock_img% = LoadImage_Strict("GFX\multiplayer\menu\lock.png")
	mpimg\lockr_img% = LoadImage_Strict("GFX\multiplayer\menu\lockr.png")
	
	mpimg\menu_image[0] = LoadImage_Strict("GFX\multiplayer\menu\p1.png")
	mpimg\menu_image[1] = LoadImage_Strict("GFX\multiplayer\menu\p0.png")
	
	mpimg\arrowimg[0] = LoadImage_Strict("GFX\menu\arrow.png")
	mpimg\arrowimg[1] = LoadImage_Strict("GFX\menu\arrow.png")
	
	RotateImage(mpimg\arrowimg[0], 180)
	HandleImage(mpimg\arrowimg[0], 0, 0)
	
	MaskImage (mpimg\AFKIMG, 0, 0, 0)
	MaskImage (mpimg\list_nocheat, 0, 0, 0)
	MaskImage (mpimg\list_voice, 0, 0, 0)
	
	ResizeImage(mpimg\VoicePL,10*MenuScale,10*MenuScale)
	ResizeImage(mpimg\VoiceME,50*MenuScale,50*MenuScale)
	ResizeImage(mpimg\VoiceNO,50*MenuScale,50*MenuScale)
	
	MaskImage (mpimg\VoiceNO, 0,0,0)
	MaskImage (mpimg\VoiceME, 0,0,0)
	
	ResizeImage(mpimg\SoundIMG,ImageWidth(mpimg\SoundIMG)*MenuScale,ImageHeight(mpimg\SoundIMG)*MenuScale)
	ResizeImage(mpimg\AFKIMG,ImageWidth(mpimg\AFKIMG)*MenuScale,ImageHeight(mpimg\AFKIMG)*MenuScale)
	ResizeImage(mpimg\menu_image[0],ImageWidth(mpimg\menu_image[0])*MenuScale,ImageHeight(mpimg\menu_image[0])*MenuScale)
	ResizeImage(mpimg\menu_image[1],ImageWidth(mpimg\menu_image[1])*MenuScale,ImageHeight(mpimg\menu_image[1])*MenuScale)
	
	ResizeImage(mpimg\arrowimg[0],(ImageWidth(mpimg\arrowimg[0])*0.6)*MenuScale,(ImageHeight(mpimg\arrowimg[0])*0.6)*MenuScale)
	ResizeImage(mpimg\arrowimg[1],(ImageWidth(mpimg\arrowimg[1])*0.6)*MenuScale,(ImageHeight(mpimg\arrowimg[1])*0.6)*MenuScale)
	
	ResizeImage(mpimg\list_nocheat,10/1.16*MenuScale,11/1.16*MenuScale)
	ResizeImage(mpimg\list_voice,10/1.16*MenuScale,13/1.16*MenuScale)
	
	ResizeImage(mpimg\lock_img,15*MenuScale,15*MenuScale)
	ResizeImage(mpimg\lockr_img,15*MenuScale,15*MenuScale)
	
	mpimg\load_gif = LoadAnimImage("GFX\multiplayer\menu\load.png", 630, 637, 0, 11)
	ResizeImage(mpimg\load_gif,30*MenuScale, 30*MenuScale)
	
	mpimg\Smile% = LoadImage_Strict("GFX\multiplayer\game\images\smile2.jpg")
	ResizeImage(mpimg\Smile,20*MenuScale, 20*MenuScale)
	MaskImage(mpimg\Smile, 0,0,0)
	
	mpimg\Smile2% = LoadImage_Strict("GFX\multiplayer\game\images\smile.jpg")
	ResizeImage(mpimg\Smile2,20*MenuScale, 20*MenuScale)
	MaskImage(mpimg\Smile2, 0,0,0)
	
	mpimg\Friend = LoadImage_Strict("GFX\multiplayer\game\images\friend10x10.png")
	ResizeImage(mpimg\Friend,10*MenuScale, 10*MenuScale)
	MaskImage(mpimg\Friend, 0,0,0)
End Function
Global img_CurTime, img_ElapsedTime#, img_FPSfactor#, img_PrevTime
Function PlayAnimImage#(img, x,y, speed#, currentFrame#, maxframe#)
	DrawImage img, x,y, Min(currentFrame, maxframe-1)
	currentFrame = currentFrame + speed
	If currentFrame >= maxframe-speed Then currentFrame = 0
	Return currentFrame
End Function
Global WAITING_TIME
Function InitMultiplayer(selected = -1, paint% = True)
	Local currselect
	If selected <> -1 Then currselect = selected 
	While True
		Select currselect
			Case 0:
				InitMultiplayerImages()
				currselect = currselect+1
			Case 1:
				LoadMultiplayerOptions()
				currselect = currselect+1
			Case 2:
				LoadNecessaryEntities()
				currselect = currselect+1
			Case 3:
				currselect = currselect+1
			Case 4:
				resultconnect = LoadInternetServers()
				currselect = currselect+1
			Case 5:
				currselect = currselect+1
				Exit
		End Select
	Wend
End Function

Function ReloadHolidaysEntities()
	If HALLOWEENINDEX Then
		FreeEntity(GRENADE_OBJECT)
		GRENADE_OBJECT = LoadMesh_Strict("GFX\items\grenadeworldmodelHw.b3d")
		
		HalloweenScene[0] = LoadAnimMesh_Strict("GFX\multiplayer\game\models\Pumpkin1.b3d")
		HideEntity(HalloweenScene[0])
		
		HalloweenScene[1] = LoadAnimMesh_Strict("GFX\multiplayer\game\models\Pumpkin2.b3d")
		HideEntity(HalloweenScene[1])
		
		HalloweenScene[2] = LoadAnimMesh_Strict("GFX\multiplayer\game\models\Pumpkin1.b3d")
		HideEntity(HalloweenScene[2])
		
		HalloweenScene[3] = LoadAnimMesh_Strict("GFX\multiplayer\game\models\Pumpkin2.b3d")
		HideEntity(HalloweenScene[3])
		
		;On Halloween set jack-o-latern texture.
		Local texFestive = LoadTexture_Strict("GFX\npcs\173h.pt", 1)
		EntityTexture OBJECT_173, texFestive, 0, 0
		FreeTexture texFestive
		
		EntityParent WitchHat, FindChild(ClassDObj, "Bip01_Head")
		MoveEntity WitchHat, 0, 69, -2
	EndIf
	
	If NEWYEARINDEX Then
		Local SEED% = RndSeed()
		SeedRnd(1234)
		For i = 1 To 3
			HalloweenScene[i-1] = LoadMesh_Strict("GFX\multiplayer\game\models\snow"+i+".b3d")
			
			tx = LoadTexture_Strict("GFX\multiplayer\game\models\snow.jpg")
			EntityTexture HalloweenScene[i-1], tx
			FreeTexture tx
			
			Present = CopyEntity(EASTER_EGG_MODEL)
			ScaleEntity Present, 150,150,150
			EntityParent Present, HalloweenScene[i-1]
			RotateEntity Present, 0, -45, 0, True
			HideEntity(HalloweenScene[i-1])
		Next
		SeedRnd SEED
	EndIf
End Function

Function ReloadNecessaryEntities()
	Delete Each breachtypes
	Delete Each tGG_Font
	Delete Each tGG_TextLine
	Delete Each tGG_Character
	LoadNecessaryEntities()
End Function
Function LoadNecessaryEntities()
	Delete Each breachtypes
	
	
	Collider = CreatePivot()
	EntityRadius Collider, 0.15, 0.30
	EntityPickMode(Collider, 1)
	EntityType Collider, HIT_PLAYER
	
	MyHitbox = CreateCube()
	EntityParent MyHitbox, Collider
	EntityPickMode MyHitbox, 2, False
	EntityAlpha MyHitbox, 0.0
	
	ViewmodelPivot = CreatePivot()
	
	Head = CreatePivot()
	EntityRadius Head, 0.15
	EntityType Head, HIT_PLAYER
	WitchHat = LoadMesh_Strict("GFX\multiplayer\game\models\witch_hat.b3d")
	ScaleEntity WitchHat, 0.05, 0.05, 0.05
	EntityColor WitchHat, 10, 10, 10
	OBJECT_173 = LoadAnimMesh_Strict("GFX\npcs\173_2.b3d")
	ROCKET_OBJECT = LoadMesh_Strict("GFX\items\rpg_rocket.b3d")
	LiquidObj = LoadMesh_Strict("GFX\items\cupliquid.x") ;optimized the cups dispensed by 294
	MTFObj = LoadAnimMesh_Strict("GFX\npcs\MTF2.b3d") ;optimized MTFs
	GuardObj = LoadAnimMesh_Strict("GFX\npcs\guard.b3d") ;optimized Guards
	ApacheObj = LoadAnimMesh_Strict("GFX\apache.b3d") ;optimized Apaches (helicopters)
	ApacheRotorObj = LoadAnimMesh_Strict("GFX\apacherotor.b3d") ;optimized the Apaches even more
	ClassDObj = LoadAnimMesh_Strict("GFX\npcs\classd.b3d") ;optimized Class-D's and scientists/researchers
	
	P90_OBJECT = LoadMesh_Strict("GFX\items\P90_Worldmodel.b3d")
	PISTOL_OBJECT = LoadMesh_Strict("GFX\items\USP_Tactical_Worldmodel.b3d")
	MP5_OBJECT = LoadMesh_Strict("GFX\items\mp5sd_worldmodel.b3d")
	NTF_OBJECT = LoadAnimMesh_Strict("GFX\npcs\MTFMP.b3d")
	HAOS_OBJECT = LoadanimMesh_Strict("GFX\multiplayer\game\models\CI.b3d")
	GUARD_OBJECT = LoadAnimMesh_Strict("GFX\npcs\guardMP.b3d")
	
	VEST_OBJ = LoadMesh_Strict("GFX\items\vest.x")
	GASMASK_OBJ = LoadMesh_Strict("GFX\items\gasmask.b3d")
	NVG_OBJ = LoadMesh_Strict("GFX\items\NVG.b3d")
	NPC049OBJ = LoadAnimMesh_Strict("GFX\npcs\scp-049.b3d")
	NPC0492OBJ = LoadAnimMesh_Strict("GFX\npcs\zombie1.b3d")
	ClerkOBJ = LoadAnimMesh_Strict("GFX\npcs\clerk.b3d")
	ClerkMP = LoadAnimMesh_Strict("GFX\npcs\clerkMP.b3d")
	Obj939 = LoadAnimMesh_Strict("GFX\NPCs\scp-939.b3d")
	RPG_OBJECT = LoadMesh_Strict("GFX\items\rpg_worldmodel.b3d")
	GRENADE_OBJECT = LoadMesh_Strict("GFX\items\grenadeworldmodel.b3d")
	HANDCUFFS_OBJECT = LoadMesh_Strict("GFX\items\Handcuts_Worldmodel.b3d")
	MICROHID_OBJECT = LoadMesh_Strict("GFX\items\MicroHid_Worldmodel.b3d")
	DEAGLE_OBJECT = LoadMesh_Strict("GFX\items\deagle_worldmodel.b3d")
	SHOTGUN_OBJECT = LoadMesh_Strict("GFX\items\SPAS_Worldmodel.b3d")
	MINIGUN_OBJECT = LoadMesh_Strict("GFX\items\M134_Worldmodel.b3d")
	M4A4_OBJECT = LoadMesh_Strict("GFX\items\M4_Worldmodel.b3d")
	HAZMAT_OBJECT = LoadAnimMesh_Strict("GFX\multiplayer\game\models\hazmat.b3d")
	NPC106Obj = LoadAnimMesh_Strict("GFX\npcs\106_2.b3d")
	NPC966Obj = LoadAnimMesh_Strict("GFX\npcs\scp-966.b3d")
	SURGEON_ZOMBIE = LoadAnimMesh_Strict("GFX\npcs\zombiesurgeon.b3d")
	OBJECT_096 = LoadAnimMesh_Strict("GFX\npcs\scp096.b3d")
	OBJECT_860 = LoadAnimMesh_Strict("GFX\npcs\forestmonster.b3d")
	OBJECT_035 = LoadAnimMesh_Strict("GFX\npcs\classd.b3d")
	HKG36_OBJECT = LoadMesh_Strict("GFX\items\HKG36_Worldmodel.b3d")
	GRENADEFLASHBANG_OBJECT = LoadMesh_Strict("GFX\items\flashgrenadeworldmodel.b3d")
	GRENADESMOKE_OBJECT = LoadMesh_Strict("GFX\items\smokegrenadeworldmodel.b3d")
	KNIFE_OBJECT = LoadMesh_Strict("GFX\items\knife_worldmodel.b3d")
	
	tex = LoadTexture_Strict("GFX\npcs\035victim.jpg")
	EntityTexture OBJECT_035, tex
	FreeTexture tex
	
	Mask% = LoadMesh_Strict("GFX\items\035.b3d")
	EntityParent Mask, FindChild(OBJECT_035, "Bip01_Head")
	PositionEntity Mask, EntityX(FindChild(OBJECT_035, "Bip01_Head")), EntityY(FindChild(OBJECT_035, "Bip01_Head")), EntityZ(FindChild(OBJECT_035, "Bip01_Head"))
	RotateEntity Mask, EntityPitch(FindChild(OBJECT_035, "Bip01_Head"))+90, EntityYaw(FindChild(OBJECT_035, "Bip01_Head"))+90, 90, True
	TranslateEntity Mask, 0, 0, 4.31

	NY_HAT = LoadAnimMesh_Strict("GFX\multiplayer\game\models\Hat.b3d")
	
	TextureFilter("GFX\multiplayer\game\models\branch.png", 1+2+(256*(EnableVRam=True)))
	EASTER_EGG_MODEL = LoadAnimMesh("GFX\multiplayer\game\models\tree.b3d")
	snowman% = LoadMesh("GFX\multiplayer\game\models\snowman.b3d")
	ScaleEntity snowman, 0.4, 0.4, 0.4
	HideEntity snowman
	EntityParent snowman, EASTER_EGG_MODEL
	MoveEntity Snowman, -1, 0, 0
	;tex = LoadTexture_Strict("GFX\multiplayer\game\models\branch.png", 2)
	;EntityTexture(EASTER_EGG_MODEL, tex)
	;FreeTexture(tex)
	
	HideEntity NY_HAT
	HideEntity EASTER_EGG_MODEL
	FlipMesh ROCKET_OBJECT
	HideEntity WitchHat
	HideEntity SURGEON_ZOMBIE
	HideEntity ClerkMP
	HideEntity Obj939
	HideEntity RPG_OBJECT
	HideEntity ROCKET_OBJECT
	HideEntity NPC049OBJ
	HideEntity NPC0492OBJ
	HideEntity ClerkOBJ	
	HideEntity LiquidObj
	HideEntity VEST_OBJ
	HideEntity GASMASK_OBJ
	HideEntity NVG_OBJ
	HideEntity OBJECT_173
	HideEntity OBJECT_035
	HideEntity ClassDObj
	HideEntity MP5_OBJECT
	HideEntity MTFObj
	HideEntity GuardObj
	HideEntity ApacheObj
	HideEntity ApacheRotorObj
	HideEntity NTF_OBJECT
	HideEntity P90_OBJECT
	HideEntity PISTOL_OBJECT
	HideEntity HAOS_OBJECT
	HideEntity GUARD_OBJECT
	HideEntity MINIGUN_OBJECT
	HideEntity MICROHID_OBJECT
	HideEntity DEAGLE_OBJECT
	HideEntity SHOTGUN_OBJECT
	HideEntity M4A4_OBJECT
	HideEntity HKG36_OBJECT
	HideEntity GRENADE_OBJECT
	HideEntity HANDCUFFS_OBJECT
	HideEntity HAZMAT_OBJECT
	HideEntity NPC106Obj
	HideEntity NPC966Obj
	HideEntity OBJECT_096
	HideEntity OBJECT_860
	
	HideEntity GRENADEFLASHBANG_OBJECT
	HideEntity GRENADESMOKE_OBJECT
	HideEntity KNIFE_OBJECT
	;RotateMesh MICROHID_OBJECT, 45, 0, 0
	
	LAST_BREACH_TYPE = 0
		
	multiplayer_breach_CreatePlayerRole("Spectator", ClassDObj, 0.5 / MeshWidth(ClassDObj), "",192,192,192)
	NTF_MODEL = multiplayer_breach_CreatePlayerRole("MTF", NTF_OBJECT, 0.3 / 2.7, "", 0,0,255, 150)
	GUARD_MODEL = multiplayer_breach_CreatePlayerRole("Guard", GUARD_OBJECT, 0.27 / 2.5, "" ,0,0,255, 120)
	CLASSD_MODEL = multiplayer_breach_CreatePlayerRole("Class D", ClassDObj, 0.5 / MeshWidth(ClassDObj), "" ,212,113,0, 100)
	SCIENTIST_MODEL = multiplayer_breach_CreatePlayerRole("Scientist", ClassDObj, 0.5 / MeshWidth(ClassDObj), "GFX\npcs\scientist.jpg",255,255,255, 100)
	MODEL_173 = multiplayer_breach_CreatePlayerRole("SCP-173", OBJECT_173, 0.35 / MeshDepth(OBJECT_173), "" ,255,0,0, 5000)
	MODEL_049 = multiplayer_breach_CreatePlayerRole("SCP-049", NPC049OBJ, 1.2, "",255,0,0, 5000)
	HAOS_MODEL = multiplayer_breach_CreatePlayerRole("Chaos Soldier", HAOS_OBJECT, 0.515 / MeshWidth(HAOS_OBJECT), "",26,64,1, 150)
	JANITOR_MODEL = multiplayer_breach_CreatePlayerRole("Janitor", ClassDObj, 0.5 / MeshWidth(ClassDObj), "GFX\npcs\janitor.jpg",199,219,252, 100)
	WORKER_MODEL = multiplayer_breach_CreatePlayerRole("Janitor", ClassDObj, 0.5 / MeshWidth(ClassDObj), "GFX\npcs\janitor2.png",199,219,252, 100)
	MODEL_939 = multiplayer_breach_CreatePlayerRole("SCP-939", Obj939, 0.5/2.5, "",255,0,0, 2000)
	MODEL_106 = multiplayer_breach_CreatePlayerRole("SCP-106", NPC106Obj, 0.25/2.2, "",255,0,0, 5000)
	MODEL_966 = multiplayer_breach_CreatePlayerRole("SCP-966", NPC966Obj, 0.011, "",255,0,0, 2000)
	MODEL_ZOMBIE = multiplayer_breach_CreatePlayerRole("SCP-049-2", SURGEON_ZOMBIE, 0.5 / MeshWidth(SURGEON_ZOMBIE), "",255,0,0, 300)
	MODEL_096 = multiplayer_breach_CreatePlayerRole("SCP-096", OBJECT_096, 0.6 / 3.0, "",255,0,0, 2000)
	MODEL_860 = multiplayer_breach_CreatePlayerRole("SCP-860-2", OBJECT_860, 0.02, "",255,0,0, 2000)
	MODEL_035 = multiplayer_breach_CreatePlayerRole("SCP-035", OBJECT_035, 0.5 / MeshWidth(OBJECT_035),"",255,0,0, 3000)
	MODEL_CLERK = multiplayer_breach_CreatePlayerRole("Clerk", ClerkMP, 0.5 / MeshWidth(ClerkMP), "",255,255,255, 100)
	MODEL_WAIT = multiplayer_breach_CreatePlayerRole(" ", ClassDObj, 0.5 / MeshWidth(ClassDObj), "",255,255,255, 100)
	;local temprole = multiplayer_breach_CreatePlayerRole("SCP-049-2", ClassDObj, 0.5 / MeshWidth(ClassDObj),"GFX\npcs\corpse.jpg",255,0,0, 500)
	
	multiplayer_breach_MarkRoleAsSCP(MODEL_173)
	multiplayer_breach_MarkRoleAsSCP(MODEL_049)
	multiplayer_breach_MarkRoleAsSCP(MODEL_939)
	multiplayer_breach_MarkRoleAsSCP(MODEL_966)
	multiplayer_breach_MarkRoleAsSCP(MODEL_106)
	multiplayer_breach_MarkRoleAsSCP(MODEL_ZOMBIE)
	multiplayer_breach_MarkRoleAsSCP(MODEL_096)
	multiplayer_breach_MarkRoleAsSCP(MODEL_860)
	multiplayer_breach_MarkRoleAsSCP(MODEL_035)
	
	;multiplayer_breach_MarkRoleAsSCP(temprole)
	
	multiplayer_breach_SetRoleSettings(MODEL_173, True, 2, 100, 1.0, True, "", 0) ; just marking for allowing scp update
	multiplayer_breach_SetRoleSettings(MODEL_096, True, 2, 500, 1.0, True, "", 0) ; just marking for allowing scp update
	
	multiplayer_breach_SetRoleSettings(MODEL_049, True, 2, 8000, 1.0, False, "SFX\SCP\049\Searching.ogg", 10000, 6)
	multiplayer_breach_SetRoleSettings(MODEL_106, True, 2, 3000, 1.0, False, "SFX\SCP\106\Laugh.ogg", 10000)
	multiplayer_breach_SetRoleSettings(MODEL_035, True, 1, 1000, 1.0, True, "", 0)
	multiplayer_breach_SetRoleSettings(MODEL_966, True, 1, 1000, 1.0, True, "SFX\SCP\966\Echo.ogg", 10000, 3)
	multiplayer_breach_SetRoleSettings(MODEL_860, True, 1, 2000, 1.5, True, "SFX\SCP\860\Cancer.ogg", 10000, 5)
	multiplayer_breach_SetRoleSettings(MODEL_939, True, 1, 2000, 1.5, True, "SFX\SCP\939\0Lure.ogg", 10000, 10)
	multiplayer_breach_SetRoleSettings(MODEL_ZOMBIE, True, 1, 1500, 1.0, True, "SFX\SCP\049\0492Breath.ogg", 3000)
	;multiplayer_breach_SetRoleSettings(temprole, True, 1, 1500, 1.0, True, "SFX\SCP\049\0492Breath.ogg", 3000)
	
	multiplayer_breach_SetRoleEffects(MODEL_966, 0.0, 0, 0, False, True, False, True, False, True, True)
	multiplayer_breach_SetRoleEffects(MODEL_173, 0.08, 0, 0, False, False, False, True, False, False, True)
	multiplayer_breach_SetRoleEffects(MODEL_939, -0.1, 0, 0, False, True, True, True, False, False, False)
	multiplayer_breach_SetRoleEffects(MODEL_860, -0.1, 0, 0, False, True, True, True, False, True, False)
	multiplayer_breach_SetRoleEffects(MODEL_049, 0, 0, -180, True, True, False, True, False, True, True)
	multiplayer_breach_SetRoleEffects(MODEL_106, 0, 0, 180, True, True, False, True, False, True, True)
	multiplayer_breach_SetRoleEffects(MODEL_096, 0, 0, -180, True, True, False, True, False, False, False)
	multiplayer_breach_SetRoleEffects(MODEL_ZOMBIE, 0, 0, 0, False, True, True, True, False, False, False)
	multiplayer_breach_SetRoleEffects(GUARD_MODEL, 0.0, 0, -180, True, True, False, False, False, False, False)
	multiplayer_breach_SetRoleEffects(MODEL_CLERK, 0.0, 0, 0, True, True, False, False, False, False, False)
	;multiplayer_breach_SetRoleEffects(temprole, 0, 0, -180, False, True, True, False, False, False, True)
	
	multiplayer_breach_SetRoleAmbientSound(MODEL_096, PLAYER_RUNNING, "SFX\SCP\096\Scream.ogg")
	multiplayer_breach_SetRoleAmbientSound(MODEL_049, 0, "SFX\Horror\Horror12.ogg")
	multiplayer_breach_SetRoleAmbientSound(MODEL_049, PLAYER_WALKING, "SFX\Music\049Chase.ogg")
	
	multiplayer_breach_SetRoleHitboxScales(0, 0.15, 0.52, 0.15)
	multiplayer_breach_SetRoleHitboxScales(CLASSD_MODEL, 0.15, 0.52, 0.15)
	multiplayer_breach_SetRoleHitboxScales(GUARD_MODEL, 0.15, 0.6, 0.15)
	multiplayer_breach_SetRoleHitboxScales(SCIENTIST_MODEL, 0.15, 0.52, 0.15)
	multiplayer_breach_SetRoleHitboxScales(MODEL_CLERK, 0.15, 0.4, 0.15)
	multiplayer_breach_SetRoleHitboxScales(NTF_MODEL, 0.15, 0.55, 0.15)
	multiplayer_breach_SetRoleHitboxScales(HAOS_MODEL, 0.15, 0.55, 0.15)
	multiplayer_breach_SetRoleHitboxScales(MODEL_173, 0.15, 0.67, 0.15)
	multiplayer_breach_SetRoleHitboxScales(MODEL_049, 0.15, 0.58, 0.15)
	multiplayer_breach_SetRoleHitboxScales(MODEL_939, 0.5, 0.3, 0.5)
	multiplayer_breach_SetRoleHitboxScales(MODEL_966, 0.15, 0.6, 0.15)
	multiplayer_breach_SetRoleHitboxScales(MODEL_106, 0.15, 0.55, 0.15)
	multiplayer_breach_SetRoleHitboxScales(MODEL_ZOMBIE, 0.15, 0.52, 0.15)
	multiplayer_breach_SetRoleHitboxScales(MODEL_096, 0.15, 0.7, 0.15)
	multiplayer_breach_SetRoleHitboxScales(JANITOR_MODEL, 0.15, 0.52, 0.15)
	multiplayer_breach_SetRoleHitboxScales(WORKER_MODEL, 0.15, 0.52, 0.15)
	multiplayer_breach_SetRoleHitboxScales(MODEL_860, 0.15, 0.52, 0.15)
	multiplayer_breach_SetRoleHitboxScales(MODEL_035, 0.15, 0.35, 0.3)
	multiplayer_breach_SetRoleHitboxScales(MODEL_WAIT, 0.15, 0.52, 0.15)
	;multiplayer_breach_SetRoleHitboxScales(temprole, 0.15, 0.52, 0.15)
	
	multiplayer_breach_SetRoleInstruction(NTF_MODEL, "Your task is to evacuate scientists.")
	multiplayer_breach_SetRoleInstruction(GUARD_MODEL, "Your task is to evacuate scientists.")
	multiplayer_breach_SetRoleInstruction(HAOS_MODEL, "Your task is to evacuate the Class D")
	multiplayer_breach_SetRoleInstruction(CLASSD_MODEL, "Escape from the facility") 
	multiplayer_breach_SetRoleInstruction(SCIENTIST_MODEL, "Escape from the facility") 
	multiplayer_breach_SetRoleInstruction(MODEL_CLERK, "Escape from the facility") 
	multiplayer_breach_SetRoleInstruction(JANITOR_MODEL, "Escape from the facility") 
	multiplayer_breach_SetRoleInstruction(WORKER_MODEL, "Escape from the facility") 
	multiplayer_breach_SetRoleInstruction(MODEL_173, "Kill everyone in facility.") 
	multiplayer_breach_SetRoleInstruction(MODEL_939, "Kill everyone in facility.") 
	multiplayer_breach_SetRoleInstruction(MODEL_106, "Kill everyone in facility.") 
	multiplayer_breach_SetRoleInstruction(MODEL_966, "Kill everyone in facility.") 
	multiplayer_breach_SetRoleInstruction(MODEL_096, "Kill everyone in facility.") 
	multiplayer_breach_SetRoleInstruction(MODEL_860, "Kill everyone in facility.") 
	multiplayer_breach_SetRoleInstruction(MODEL_035, "You can kill anyone or teaming with somebody. You also can take items") 
	multiplayer_breach_SetRoleInstruction(MODEL_049, "Cure everyone in facility.") 
	multiplayer_breach_SetRoleInstruction(MODEL_ZOMBIE, "Follow instructions from SCP-049 and kill everyone.")
	;multiplayer_breach_SetRoleInstruction(temprole, "Follow instructions from SCP-049 and kill everyone. You're mega version.")
	
	For i = 0 To 18
		Select i
			Case CLASSD_MODEL,SCIENTIST_MODEL,MODEL_CLERK,WORKER_MODEL,JANITOR_MODEL,MODEL_WAIT,MODEL_035;,temprole
				multiplayer_breach_SetRoleAnimation(i, PLAYER_SITTING_IDLING, 357, 381, 0.1)
				multiplayer_breach_SetRoleAnimation(i, PLAYER_IDLING, 210, 235, 0.1)
				multiplayer_breach_SetRoleAnimation(i, PLAYER_RUNNING, 301, 319, 0.3)
				multiplayer_breach_SetRoleAnimation(i, PLAYER_WALKING, 236, 260, 0.3)
				multiplayer_breach_SetRoleAnimation(i, PLAYER_SITTING_WALKING_FORWARD, 382, 406, 0.3)
				multiplayer_breach_SetRoleAnimation(i, PLAYER_SITTING_WALKING_BACK, 382, 406, -0.3)
				multiplayer_breach_SetRoleAnimation(i, PLAYER_JUMPING, 834, 894, 1)
				multiplayer_breach_SetRoleAnimation(i, PLAYER_SITTING_WALKING_LEFT, 261, 280, 0.3)
				multiplayer_breach_SetRoleAnimation(i, PLAYER_SITTING_WALKING_RIGHT, 281, 300, 0.3)
				
				multiplayer_breach_SetRoleArmedAnimation(i, PLAYER_SITTING_IDLING, 733, 757, 0.1)
				multiplayer_breach_SetRoleArmedAnimation(i, PLAYER_IDLING, 573, 595, 0.1)
				multiplayer_breach_SetRoleArmedAnimation(i, PLAYER_RUNNING, 664, 682, 0.3)
				multiplayer_breach_SetRoleArmedAnimation(i, PLAYER_WALKING, 599, 623, 0.3)
				multiplayer_breach_SetRoleArmedAnimation(i, PLAYER_SITTING_WALKING_FORWARD, 758, 782, 0.3)
				multiplayer_breach_SetRoleArmedAnimation(i, PLAYER_SITTING_WALKING_BACK, 758, 782, 0.3)
				multiplayer_breach_SetRoleArmedAnimation(i, PLAYER_JUMPING, 834, 894, 1)
				multiplayer_breach_SetRoleArmedAnimation(i, PLAYER_SITTING_WALKING_LEFT, 758, 782, 0.3)
				multiplayer_breach_SetRoleArmedAnimation(i, PLAYER_SITTING_WALKING_RIGHT, 758, 782, 0.3)
		End Select
	Next
	
	multiplayer_breach_SetRoleAnimation(MODEL_096, PLAYER_CRYING, 737, 822, 0.3)
	multiplayer_breach_SetRoleAnimation(MODEL_096, PLAYER_SITTING_IDLING, 423, 471, 0.2)
	multiplayer_breach_SetRoleAnimation(MODEL_096, PLAYER_IDLING, 423, 471, 0.2)
	multiplayer_breach_SetRoleAnimation(MODEL_096, PLAYER_WALKING, 1383, 1456, 0.9)
	multiplayer_breach_SetRoleAnimation(MODEL_096, PLAYER_RUNNING, 907, 935, 0.9)
	multiplayer_breach_SetRoleAnimation(MODEL_096, PLAYER_JUMPING, 2, 26, 0.7)
		
	multiplayer_breach_SetRoleAnimation(MODEL_ZOMBIE, PLAYER_SITTING_IDLING, 323, 344, 0.3)
	multiplayer_breach_SetRoleAnimation(MODEL_ZOMBIE, PLAYER_IDLING, 323, 344, 0.3)
	multiplayer_breach_SetRoleAnimation(MODEL_ZOMBIE, PLAYER_WALKING, 96, 125, 0.6)

	multiplayer_breach_SetRoleAnimation(NTF_MODEL, PLAYER_JUMPING, 1484, 1518, 0.3)
	multiplayer_breach_SetRoleAnimation(NTF_MODEL, 7, 1558,1592, 0.3)
	multiplayer_breach_SetRoleAnimation(NTF_MODEL, 8, 1558,1592, 0.3)
	multiplayer_breach_SetRoleAnimation(NTF_MODEL, 9, 1558,1592, 0.3)
	multiplayer_breach_SetRoleAnimation(NTF_MODEL, 10, 1558,1592, 0.3)
	multiplayer_breach_SetRoleAnimation(NTF_MODEL, PLAYER_SITTING_IDLING, 1519,1520, 0.01)
	multiplayer_breach_SetRoleAnimation(NTF_MODEL, PLAYER_WALKING, 1484,1518, 0.3)
	multiplayer_breach_SetRoleAnimation(NTF_MODEL, PLAYER_RUNNING, 1484,1518, 0.6)
	multiplayer_breach_SetRoleAnimation(NTF_MODEL, PLAYER_IDLING, 1480,1480, 0.01)
	
	multiplayer_breach_SetRoleArmedAnimation(NTF_MODEL, PLAYER_JUMPING, 488, 522, 0.3)
	multiplayer_breach_SetRoleArmedAnimation(NTF_MODEL, 7, 1523,1557, 0.3)
	multiplayer_breach_SetRoleArmedAnimation(NTF_MODEL, 8, 1523,1557, 0.3)
	multiplayer_breach_SetRoleArmedAnimation(NTF_MODEL, 9, 1523,1557, 0.3)
	multiplayer_breach_SetRoleArmedAnimation(NTF_MODEL, 10, 1523,1557, 0.3)
	multiplayer_breach_SetRoleArmedAnimation(NTF_MODEL, PLAYER_SITTING_IDLING, 1521,1522, 0.3)
	multiplayer_breach_SetRoleArmedAnimation(NTF_MODEL, PLAYER_IDLING, 488, 488, 0.01)
	multiplayer_breach_SetRoleArmedAnimation(NTF_MODEL, PLAYER_WALKING, 488, 522, 0.3)
	multiplayer_breach_SetRoleArmedAnimation(NTF_MODEL, PLAYER_RUNNING, 488, 522, 0.6)

	multiplayer_breach_SetRoleAnimation(GUARD_MODEL, PLAYER_JUMPING, 750,786, 0.4)
	multiplayer_breach_SetRoleAnimation(GUARD_MODEL, 7, 789,824, 0.3)
	multiplayer_breach_SetRoleAnimation(GUARD_MODEL, 8, 789,824, 0.3)
	multiplayer_breach_SetRoleAnimation(GUARD_MODEL, 9, 789,824, 0.3)
	multiplayer_breach_SetRoleAnimation(GUARD_MODEL, 10, 789,824, 0.3)
	multiplayer_breach_SetRoleAnimation(GUARD_MODEL, PLAYER_SITTING_IDLING, 864,864, 0.01)
	multiplayer_breach_SetRoleAnimation(GUARD_MODEL, PLAYER_WALKING, 750,786, 0.3)
	multiplayer_breach_SetRoleAnimation(GUARD_MODEL, PLAYER_RUNNING, 750,786, 0.6)
	multiplayer_breach_SetRoleAnimation(GUARD_MODEL, PLAYER_IDLING, 700,748, 0.01)
	
	multiplayer_breach_SetRoleArmedAnimation(GUARD_MODEL, PLAYER_JUMPING, 39,76, 0.6)
	multiplayer_breach_SetRoleArmedAnimation(GUARD_MODEL, 7, 827,861, 0.3)
	multiplayer_breach_SetRoleArmedAnimation(GUARD_MODEL, 8, 827,861, 0.3)
	multiplayer_breach_SetRoleArmedAnimation(GUARD_MODEL, 9, 827,861, 0.3)
	multiplayer_breach_SetRoleArmedAnimation(GUARD_MODEL, 10, 827,861, 0.3)
	multiplayer_breach_SetRoleArmedAnimation(GUARD_MODEL, PLAYER_SITTING_IDLING, 866,866, 0.01)
	multiplayer_breach_SetRoleArmedAnimation(GUARD_MODEL, PLAYER_IDLING, 245,245, 0.01)
	multiplayer_breach_SetRoleArmedAnimation(GUARD_MODEL, PLAYER_WALKING, 39,76, 0.6)
	multiplayer_breach_SetRoleArmedAnimation(GUARD_MODEL, PLAYER_RUNNING, 39,76, 0.6)
	
	multiplayer_breach_SetRoleAnimation(MODEL_049, PLAYER_SITTING_IDLING, 270,345, 0.1)
	multiplayer_breach_SetRoleAnimation(MODEL_049, PLAYER_IDLING, 270,345, 0.1)
	multiplayer_breach_SetRoleAnimation(MODEL_049, PLAYER_WALKING, 428,463, 0.35)
	multiplayer_breach_SetRoleAnimation(MODEL_049, PLAYER_BITE, 538, 660, 1.5)
	multiplayer_breach_SetRoleAnimation(MODEL_049, PLAYER_BITE_STOP, 538, 660, -1)

	multiplayer_breach_SetRoleAnimation(HAOS_MODEL, PLAYER_JUMPING, 380,408, 0.3)
	multiplayer_breach_SetRoleAnimation(HAOS_MODEL, 7, 531,548, 0.3)
	multiplayer_breach_SetRoleAnimation(HAOS_MODEL, 8, 531,548, 0.3)
	multiplayer_breach_SetRoleAnimation(HAOS_MODEL, 9, 531,548, 0.3)
	multiplayer_breach_SetRoleAnimation(HAOS_MODEL, 10, 531,548, 0.3)
	multiplayer_breach_SetRoleAnimation(HAOS_MODEL, PLAYER_SITTING_IDLING, 509,509, 0.01)
	multiplayer_breach_SetRoleAnimation(HAOS_MODEL, PLAYER_WALKING, 380,408, 0.3)
	multiplayer_breach_SetRoleAnimation(HAOS_MODEL, PLAYER_RUNNING, 380,408, 0.6)
	multiplayer_breach_SetRoleAnimation(HAOS_MODEL, PLAYER_IDLING, 410,500, 0.01)
	
	multiplayer_breach_SetRoleArmedAnimation(HAOS_MODEL, PLAYER_IDLING, 183,218, 0.3)
	multiplayer_breach_SetRoleArmedAnimation(HAOS_MODEL, 7, 557,588, 0.3)
	multiplayer_breach_SetRoleArmedAnimation(HAOS_MODEL, 8, 557,588, 0.3)
	multiplayer_breach_SetRoleArmedAnimation(HAOS_MODEL, 9, 557,588, 0.3)
	multiplayer_breach_SetRoleArmedAnimation(HAOS_MODEL, 10, 557,588, 0.3)
	multiplayer_breach_SetRoleArmedAnimation(HAOS_MODEL, PLAYER_SITTING_IDLING, 551,551, 0.3)
	multiplayer_breach_SetRoleArmedAnimation(HAOS_MODEL, PLAYER_IDLING, 4,170, 0.01)
	multiplayer_breach_SetRoleArmedAnimation(HAOS_MODEL, PLAYER_WALKING, 183,218, 0.3)
	multiplayer_breach_SetRoleArmedAnimation(HAOS_MODEL, PLAYER_RUNNING, 183,218, 0.6)

	multiplayer_breach_SetRoleAnimation(MODEL_106, PLAYER_SITTING_IDLING, 280, 280, 0.3)
	multiplayer_breach_SetRoleAnimation(MODEL_106, PLAYER_IDLING, 280, 280, 0.3)
	multiplayer_breach_SetRoleAnimation(MODEL_106, PLAYER_WALKING, 284, 333, 0.3)

	multiplayer_breach_SetRoleAnimation(MODEL_939, PLAYER_BITE, 18,68, 0.7)
	multiplayer_breach_SetRoleAnimation(MODEL_939, PLAYER_SITTING_IDLING, 445, 445, 0.3)
	multiplayer_breach_SetRoleAnimation(MODEL_939, PLAYER_IDLING, 445, 445, 0.3)
	multiplayer_breach_SetRoleAnimation(MODEL_939, PLAYER_WALKING, 449,464, 0.1)

	multiplayer_breach_SetRoleAnimation(MODEL_860, PLAYER_BITE, 451,493, 0.7)
	multiplayer_breach_SetRoleAnimation(MODEL_860, PLAYER_SITTING_IDLING, 200, 297, 0.1)
	multiplayer_breach_SetRoleAnimation(MODEL_860, PLAYER_IDLING, 200, 297, 0.1)
	multiplayer_breach_SetRoleAnimation(MODEL_860, 7, 494, 569, 0.7)
	multiplayer_breach_SetRoleAnimation(MODEL_860, 8, 494, 569, 0.7)
	multiplayer_breach_SetRoleAnimation(MODEL_860, 9, 494, 569, 0.7)
	multiplayer_breach_SetRoleAnimation(MODEL_860, 10, 494, 569, 0.7)
	multiplayer_breach_SetRoleAnimation(MODEL_860, PLAYER_WALKING, 494, 569, 0.7)
	multiplayer_breach_SetRoleAnimation(MODEL_860, PLAYER_RUNNING, 298,316, 0.7)

	multiplayer_breach_SetRoleAnimation(MODEL_966, PLAYER_BITE, 488, 517, 0.5)
	multiplayer_breach_SetRoleAnimation(MODEL_966, PLAYER_SITTING_IDLING, 2, 72, 0.3)
	multiplayer_breach_SetRoleAnimation(MODEL_966, PLAYER_IDLING, 2, 72, 0.3)
	multiplayer_breach_SetRoleAnimation(MODEL_966, PLAYER_WALKING, 580,628, 0.3)
	
	multiplayer_breach_SetRoleDeadAnimation(SCIENTIST_MODEL, 0, 19)
	multiplayer_breach_SetRoleDeadAnimation(MODEL_CLERK, 0, 19)
	multiplayer_breach_SetRoleDeadAnimation(MODEL_939, 70,164)
	multiplayer_breach_SetRoleDeadAnimation(CLASSD_MODEL, 0, 19)
	multiplayer_breach_SetRoleDeadAnimation(JANITOR_MODEL, 0, 19)
	multiplayer_breach_SetRoleDeadAnimation(MODEL_035, 0, 19)
	multiplayer_breach_SetRoleDeadAnimation(WORKER_MODEL, 0, 19)
	multiplayer_breach_SetRoleDeadAnimation(GUARD_MODEL, 249,285)
	multiplayer_breach_SetRoleDeadAnimation(NTF_MODEL, 2,26)
	multiplayer_breach_SetRoleDeadAnimation(HAOS_MODEL, 593,AnimLength(HAOS_OBJECT))
	multiplayer_breach_SetRoleDeadAnimation(MODEL_ZOMBIE, 344,363)
	multiplayer_breach_SetRoleDeadAnimation(MODEL_173, 1,40)
	multiplayer_breach_SetRoleDeadAnimation(MODEL_106, 496,526)
	multiplayer_breach_SetRoleDeadAnimation(MODEL_860, 570,585)
	multiplayer_breach_SetRoleDeadAnimation(MODEL_049, 1123,1163)
	multiplayer_breach_SetRoleDeadAnimation(MODEL_096, 1472,1502)
	multiplayer_breach_SetRoleDeadAnimation(MODEL_966, 753,791)
	multiplayer_breach_SetRoleDeadAnimation(MODEL_WAIT, 0, 19)
	;multiplayer_breach_SetRoleDeadAnimation(temprole, 0, 19)
	
	multiplayer_breach_SetRolePositionsOffset(MODEL_966, 0.2, 0, -90)
	multiplayer_breach_SetRolePositionsOffset(MODEL_106, 0.3, 0, 0)
	multiplayer_breach_SetRolePositionsOffset(NTF_MODEL, 0.3, 0, -90)
	multiplayer_breach_SetRolePositionsOffset(GUARD_MODEL, 0.3, -180, 0)
	multiplayer_breach_SetRolePositionsOffset(MODEL_049, 0.3, 0, 0)
	multiplayer_breach_SetRolePositionsOffset(HAOS_MODEL, 0.3, 0, 0)
	multiplayer_breach_SetRolePositionsOffset(MODEL_939, 0.3, 0, -90)
	multiplayer_breach_SetRolePositionsOffset(MODEL_096, 0.03, 0, 0)
	multiplayer_breach_SetRolePositionsOffset(MODEL_860, 0.3, 0, -90)

	multiplayer_breach_SetRoleBone(NTF_MODEL, BONE_HEAD,"head")
	multiplayer_breach_SetRoleBone(GUARD_MODEL, BONE_HEAD, "Head")
	multiplayer_breach_SetRoleBone(HAOS_MODEL, BONE_HEAD, "NeckHead")
	multiplayer_breach_SetRoleBone(MODEL_049, BONE_HEAD, "Bone_019")
	multiplayer_breach_SetRoleBone(MODEL_106, BONE_HEAD, "Bone_022")
	multiplayer_breach_SetRoleBone(MODEL_939, BONE_HEAD, "Bone_004")
	multiplayer_breach_SetRoleBone(MODEL_966, BONE_HEAD, "face")
	multiplayer_breach_SetRoleBone(MODEL_096, BONE_HEAD, "head")
	multiplayer_breach_SetRoleBone(MODEL_860, BONE_HEAD, "Bone_006")

	multiplayer_breach_SetRoleBone(NTF_MODEL, BONE_HAND, "hand_R")
	multiplayer_breach_SetRoleBone(GUARD_MODEL, BONE_HAND, "Thumb01.R.001")
	multiplayer_breach_SetRoleBone(HAOS_MODEL, BONE_HAND, "M4MB")
	multiplayer_breach_SetRoleBone(MODEL_096, BONE_HAND, "Bone_013")

	multiplayer_breach_SetRoleBone(NTF_MODEL, BONE_SPINE, "spine")
	multiplayer_breach_SetRoleBone(GUARD_MODEL, BONE_SPINE, "Chest")
	multiplayer_breach_SetRoleBone(HAOS_MODEL, BONE_SPINE, "Spine2")
	
	multiplayer_breach_SetRoleBone(NTF_MODEL, BONE_FOREARM, "forearm_R")
	multiplayer_breach_SetRoleBone(GUARD_MODEL, BONE_FOREARM, "Lower Arm.R")
	multiplayer_breach_SetRoleBone(HAOS_MODEL, BONE_FOREARM, "RArmForearm1")

	For i = 0 To 17
		Select i
			Case CLASSD_MODEL,SCIENTIST_MODEL,MODEL_CLERK,WORKER_MODEL,JANITOR_MODEL,MODEL_WAIT,MODEL_035
				multiplayer_breach_SetRoleHoldingGrenade(i, "Bip01_R_UpperArm", 65.5855, -4.02472)
				multiplayer_breach_SetRoleHoldingGrenade(i, "Bip01_R_UpperArm", 65.5855, -4.02472)
				multiplayer_breach_SetRoleHoldingGrenade(i, "Bip01_R_UpperArm", 65.5855, -4.02472)
				multiplayer_breach_SetRoleHoldingGrenade(i, "Bip01_R_UpperArm", 65.5855, -4.02472)
				multiplayer_breach_SetRoleHoldingGrenade(i, "Bip01_R_UpperArm", 65.5855, -4.02472)
				
				multiplayer_breach_SetRoleHoldingItem(i, 100.5855, 20.02472)
				multiplayer_breach_SetRoleHoldingItem(i, 100.5855, 20.02472)
				multiplayer_breach_SetRoleHoldingItem(i, 100.5855, 20.02472)
				multiplayer_breach_SetRoleHoldingItem(i, 100.5855, 20.02472)
				multiplayer_breach_SetRoleHoldingItem(i, 100.5855, 20.02472)
				
				multiplayer_breach_SetRoleHandcuff(i, "Bip01_L_UpperArm", -13.3971, 58.1681, 135.2683, "Bip01_R_UpperArm", -13.3971, -50.1686, 48.732)
				multiplayer_breach_SetRoleHandcuff(i, "Bip01_L_UpperArm", -13.3971, 58.1681, 135.2683, "Bip01_R_UpperArm", -13.3971, -50.1686, 48.732)
				multiplayer_breach_SetRoleHandcuff(i, "Bip01_L_UpperArm", -13.3971, 58.1681, 135.2683, "Bip01_R_UpperArm", -13.3971, -50.1686, 48.732)
				multiplayer_breach_SetRoleHandcuff(i, "Bip01_L_UpperArm", -13.3971, 58.1681, 135.2683, "Bip01_R_UpperArm", -13.3971, -50.1686, 48.732)
				multiplayer_breach_SetRoleHandcuff(i, "Bip01_L_UpperArm", -13.3971, 58.1681, 135.2683, "Bip01_R_UpperArm", -13.3971, -50.1686, 48.732)
				multiplayer_breach_SetRoleHandcuff(i, "Bip01_L_UpperArm", -13.3971, 58.1681, 135.2683, "Bip01_R_UpperArm", -13.3971, -50.1686, 48.732)
				
				multiplayer_breach_AllowItemsAttaches(i, True)
				multiplayer_breach_AllowItemsAttaches(i, True)
				multiplayer_breach_AllowItemsAttaches(i, True)
				multiplayer_breach_AllowItemsAttaches(i, True)
				multiplayer_breach_AllowItemsAttaches(i, True)
		End Select
	Next
	
	multiplayer_breach_AllowRoleWeaponAttaches(NTF_MODEL, True)
	multiplayer_breach_AllowRoleWeaponAttaches(GUARD_MODEL, True)
	multiplayer_breach_AllowRoleWeaponAttaches(HAOS_MODEL, True)
	multiplayer_breach_AllowRoleWeaponAttaches(MODEL_035, True)
	multiplayer_breach_AllowRoleWeaponAttaches(CLASSD_MODEL, True)
	multiplayer_breach_AllowRoleWeaponAttaches(JANITOR_MODEL, True)
	multiplayer_breach_AllowRoleWeaponAttaches(WORKER_MODEL, True)
	multiplayer_breach_AllowRoleWeaponAttaches(SCIENTIST_MODEL, True)
	multiplayer_breach_AllowRoleWeaponAttaches(MODEL_CLERK, True)
	multiplayer_breach_AllowRoleWeaponAttaches(MODEL_WAIT, True)

	multiplayer_breach_CreateViewmodelForRole(MODEL_049, "GFX\multiplayer\game\models\viewmodels\049_Viewmodel.b3d", -0.08, 0.15, 0.017, 0.028)
	multiplayer_breach_CreateViewmodelForRole(MODEL_106, "GFX\multiplayer\game\models\viewmodels\106_Viewmodel.b3d", -0.03, 0.18, 0.03, 0.04)
	multiplayer_breach_CreateViewmodelForRole(MODEL_173, "GFX\multiplayer\game\models\viewmodels\173_Viewmodel.b3d", -0.15, 0.1, 0.03)
	multiplayer_breach_CreateViewmodelForRole(MODEL_860, "GFX\multiplayer\game\models\viewmodels\860_Viewmodel.b3d", -0.15, 0.15, 0.025, 0.04)
	multiplayer_breach_CreateViewmodelForRole(MODEL_966, "GFX\multiplayer\game\models\viewmodels\966_Viewmodel.b3d", -0.05, -0.14, 0.012, 0.02)
	multiplayer_breach_CreateViewmodelForRole(MODEL_096, "GFX\multiplayer\game\models\viewmodels\096_Viewmodel.b3d", -0.2, 0.1, 0.017, 0.025)
	multiplayer_breach_CreateViewmodelForRole(MODEL_ZOMBIE, "GFX\multiplayer\game\models\viewmodels\Zombie_Viewmodel.b3d", -0.03, 0.2, 0.03)
	
	multiplayer_breach_SetRoleViewmodelAnimation(MODEL_049, PLAYER_SITTING_IDLING, 1,41, 0.1)
	multiplayer_breach_SetRoleViewmodelAnimation(MODEL_049, PLAYER_IDLING, 1,41, 0.1)
	multiplayer_breach_SetRoleViewmodelAnimation(MODEL_049, PLAYER_WALKING, 42,82, 0.4)
	multiplayer_breach_SetRoleViewmodelAnimation(MODEL_049, PLAYER_BITE, 83, 113, 0.3)
	multiplayer_breach_SetRoleViewmodelAnimation(MODEL_049, PLAYER_TO_WALK, 114, 118, 0.2)
	multiplayer_breach_SetRoleViewmodelAnimation(MODEL_049, PLAYER_FROM_WALK, 114, 118-1, -0.2)
	
	multiplayer_breach_SetRoleViewmodelAnimation(MODEL_106, PLAYER_SITTING_IDLING, 1, 41, 0.1)
	multiplayer_breach_SetRoleViewmodelAnimation(MODEL_106, PLAYER_IDLING, 1, 41, 0.1)
	multiplayer_breach_SetRoleViewmodelAnimation(MODEL_106, PLAYER_WALKING, 42, 82, 0.3)
	multiplayer_breach_SetRoleViewmodelAnimation(MODEL_106, PLAYER_BITE, 83, 106, 0.3)
	multiplayer_breach_SetRoleViewmodelAnimation(MODEL_106, PLAYER_TO_WALK, 107, 111, 0.2)
	multiplayer_breach_SetRoleViewmodelAnimation(MODEL_106, PLAYER_FROM_WALK, 107, 111-1, -0.2)
	
	multiplayer_breach_SetRoleViewmodelAnimation(MODEL_173, PLAYER_SITTING_IDLING, 1, 24, 0.1)
	multiplayer_breach_SetRoleViewmodelAnimation(MODEL_173, PLAYER_IDLING, 1, 24, 0.1)
	multiplayer_breach_SetRoleViewmodelAnimation(MODEL_173, PLAYER_WALKING, 1, 24, 0.1)

	multiplayer_breach_SetRoleViewmodelAnimation(MODEL_860, PLAYER_BITE, 77,91, 0.3)
	multiplayer_breach_SetRoleViewmodelAnimation(MODEL_860, PLAYER_SITTING_IDLING, 1, 20, 0.1)
	multiplayer_breach_SetRoleViewmodelAnimation(MODEL_860, PLAYER_IDLING, 1, 20, 0.1)
	multiplayer_breach_SetRoleViewmodelAnimation(MODEL_860, 21, 60, 569, 0.2)
	multiplayer_breach_SetRoleViewmodelAnimation(MODEL_860, 21, 60, 569, 0.2)
	multiplayer_breach_SetRoleViewmodelAnimation(MODEL_860, 21, 60, 569, 0.2)
	multiplayer_breach_SetRoleViewmodelAnimation(MODEL_860, 21, 60, 569, 0.2)
	multiplayer_breach_SetRoleViewmodelAnimation(MODEL_860, PLAYER_WALKING, 21, 60, 0.2)
	multiplayer_breach_SetRoleViewmodelAnimation(MODEL_860, PLAYER_RUNNING, 61,76, 0.3)
	multiplayer_breach_SetRoleViewmodelAnimation(MODEL_860, PLAYER_TO_WALK, 107, 113, 0.2)
	multiplayer_breach_SetRoleViewmodelAnimation(MODEL_860, PLAYER_FROM_WALK, 107, 113-1, -0.2)
	
	multiplayer_breach_SetRoleViewmodelAnimation(MODEL_966, PLAYER_SITTING_IDLING, 1,63, 0.1)
	multiplayer_breach_SetRoleViewmodelAnimation(MODEL_966, PLAYER_IDLING, 1,63, 0.1)
	multiplayer_breach_SetRoleViewmodelAnimation(MODEL_966, PLAYER_WALKING,64,123, 0.2)
	multiplayer_breach_SetRoleViewmodelAnimation(MODEL_966, PLAYER_BITE, 124, 148, 0.3)
	multiplayer_breach_SetRoleViewmodelAnimation(MODEL_966, PLAYER_TO_WALK, 174, 178, 0.2)
	multiplayer_breach_SetRoleViewmodelAnimation(MODEL_966, PLAYER_FROM_WALK, 174, 178-1, -0.2)
	
	multiplayer_breach_SetRoleViewmodelAnimation(MODEL_096, PLAYER_CRYING, 170, 209, 0.2)
	multiplayer_breach_SetRoleViewmodelAnimation(MODEL_096, PLAYER_SITTING_IDLING, 1, 62, 0.2)
	multiplayer_breach_SetRoleViewmodelAnimation(MODEL_096, PLAYER_IDLING, 1, 62, 0.2)
	multiplayer_breach_SetRoleViewmodelAnimation(MODEL_096, PLAYER_WALKING, 63, 135, 0.3)
	multiplayer_breach_SetRoleViewmodelAnimation(MODEL_096, PLAYER_RUNNING, 293, 305, 0.3)
	multiplayer_breach_SetRoleViewmodelAnimation(MODEL_096, PLAYER_JUMPING, 306, 325, 0.3)

	multiplayer_breach_SetRoleViewmodelAnimation(MODEL_096, PLAYER_TO_CRYING, 136, 169, 0.3)
	multiplayer_breach_SetRoleViewmodelAnimation(MODEL_096, PLAYER_FROM_CRYING, 136, 169, -0.3)
	multiplayer_breach_SetRoleViewmodelAnimation(MODEL_096, PLAYER_TO_ANGRY, 210, 223, 0.3)
	
	multiplayer_breach_SetRoleViewmodelAnimation(MODEL_096, PLAYER_TO_WALK, 346, 350, 0.2)
	multiplayer_breach_SetRoleViewmodelAnimation(MODEL_096, PLAYER_FROM_WALK, 346, 350, -0.2)
	
	multiplayer_breach_SetRoleViewmodelAnimation(MODEL_ZOMBIE, PLAYER_SITTING_IDLING, 1, 33, 0.1)
	multiplayer_breach_SetRoleViewmodelAnimation(MODEL_ZOMBIE, PLAYER_IDLING, 1, 33, 0.1)
	multiplayer_breach_SetRoleViewmodelAnimation(MODEL_ZOMBIE, PLAYER_WALKING, 34, 63, 0.2)
	multiplayer_breach_SetRoleViewmodelAnimation(MODEL_ZOMBIE, PLAYER_BITE, 64, 83, 0.3)
	multiplayer_breach_SetRoleViewmodelAnimation(MODEL_ZOMBIE, PLAYER_RUNNING, 34, 63, 0.3)
	
	multiplayer_breach_SetRoleViewmodelAnimation(MODEL_ZOMBIE, PLAYER_TO_WALK, 84, 88, 0.2)
	multiplayer_breach_SetRoleViewmodelAnimation(MODEL_ZOMBIE, PLAYER_FROM_WALK, 84, 88-1, -0.2)
	For i = 0 To 18
		Select i
			; ========= SCPS
			Case MODEL_173,MODEL_106,MODEL_939,MODEL_966,MODEL_049,MODEL_ZOMBIE,MODEL_096,MODEL_860,MODEL_035,0
				For a = 0 To 17
					Select a
						Case MODEL_173,MODEL_106,MODEL_939,MODEL_966,MODEL_049,MODEL_ZOMBIE,MODEL_096,MODEL_860,MODEL_035,0
							multiplayer_breach_MarkAsFriend(i, a, True)
					End Select
				Next
			; ========= CLASS D
			Case CLASSD_MODEL,HAOS_MODEL
				For a = 0 To 17
					Select a
						Case CLASSD_MODEL,HAOS_MODEL
							multiplayer_breach_MarkAsFriend(i, a, True)
					End Select
				Next
			; ========= SECURITY
			Case NTF_MODEL,GUARD_MODEL,JANITOR_MODEL,WORKER_MODEL,SCIENTIST_MODEL,MODEL_CLERK
				For a = 0 To 17
					Select a
						Case NTF_MODEL,GUARD_MODEL,JANITOR_MODEL,WORKER_MODEL,SCIENTIST_MODEL,MODEL_CLERK
							multiplayer_breach_MarkAsFriend(i, a, True)
					End Select
				Next
			; ========= STAFF
			Case JANITOR_MODEL,WORKER_MODEL,SCIENTIST_MODEL,MODEL_CLERK
				For a = 0 To 17
					Select a
						Case JANITOR_MODEL,WORKER_MODEL,SCIENTIST_MODEL,MODEL_CLERK
							multiplayer_breach_MarkAsFriend(i, a, True)
					End Select
				Next
		End Select
	Next
	
	Text3DFont = GG_Load_Bitmap_Font("Courier New Rus","GFX\multiplayer\game\images\Courier_New_Rus.ttf_64B.ggfnt")
	AFK3DFont = GG_Load_Bitmap_Font("AFK","GFX\multiplayer\game\images\afk.ggfnt")
	Sound3DFont = GG_Load_Bitmap_Font("Sound","GFX\multiplayer\game\images\sound.ggfnt")
	
	
End Function
Function multiplayer_InitPlayer(i)
	If Player[i]\obj <> 0 Then 
		FreeEntity Player[i]\obj
		Player[i]\obj = 0
	EndIf
	If Player[i]\voiceent = Null Then
		GG_Set_Current_Font(Sound3DFont)
		Player[i]\voiceent = GG_Create_Text_Block("a", 64, 0)
		ScaleEntity Player[i]\voiceent\cPv, 0.02, 0.02, 0.02
	EndIf
	If Player[i]\afkent = Null Then
		GG_Set_Current_Font(AFK3DFont)
		Player[i]\afkent = GG_Create_Text_Block("a", 64, 0)
		ScaleEntity Player[i]\afkent\cPv, 0.02, 0.02, 0.02
	EndIf
	multiplayer_CreatePlayerObject(i)
End Function
Function multiplayer_ChangePlayerName(i, name$, tag$ = "")
	If Player[i]\nameent <> Null Then GG_Delete_Text_Block(Player[i]\nameent\cPv)
	If Player[i]\patreonent <> Null Then GG_Delete_Text_Block(Player[i]\patreonent\cPv)
	GG_Set_Current_Font(Text3DFont)
	Player[i]\nameent = GG_Create_Text_Block(name, 32, 0)
	ScaleEntity Player[i]\nameent\cPv, 0.02, 0.02, 0.02
	Player[i]\name = name
	Player[i]\tag = tag$
	
	If tag <> "" Then
		GG_Set_Current_Font(Text3DFont)
		Player[i]\patreonent = GG_Create_Text_Block(tag, 32, 0)
		ScaleEntity Player[i]\patreonent\cPv, 0.02, 0.02, 0.02
	EndIf
End Function

Function multiplayer_ResetPlayerSize(i)
	Player[i]\prevsize = -999
End Function
Function multiplayer_UpdatePlayerSize(i)

	If Player[i]\prevsize <> Player[i]\size Then
		br.breachtypes = GetBreachType(Player[i]\BreachType)
		
		If Player[i]\WearingHazmat <> 0 Then br.BreachTypes = GetBreachType(CLASSD_MODEL)
		If Player[i]\obj <> 0 Then ScaleEntity Player[i]\obj, br\scale*Player[i]\size, br\scale*Player[i]\size, br\scale*Player[i]\size
		ScaleEntity Player[i]\Hitbox, br\hitboxx*Player[i]\size, br\hitboxy*Player[i]\size, br\hitboxz*Player[i]\size
		
		PositionEntity Player[i]\Hitbox,0,0,0
		MoveEntity Player[i]\Hitbox, 0, -0.2/Player[i]\size, 0
		
		If Player[i]\nameent <> Null Then ScaleEntity Player[i]\nameent\cPv, 0.02*Player[i]\size, 0.02*Player[i]\size, 0.02*Player[i]\size
		If Player[i]\patreonent <> Null Then ScaleEntity Player[i]\patreonent\cPv, 0.02*Player[i]\size, 0.02*Player[i]\size, 0.02*Player[i]\size
		If Player[i]\voiceent <> Null Then ScaleEntity Player[i]\voiceent\cPv, 0.02*Player[i]\size, 0.02*Player[i]\size, 0.02*Player[i]\size
		If Player[i]\afkent <> Null Then ScaleEntity Player[i]\afkent\cPv, 0.02*Player[i]\size, 0.02*Player[i]\size, 0.02*Player[i]\size
		
		Player[i]\prevsize = Player[i]\size
	EndIf
End Function
Function ResetPlayer()
	Stamina# = 60
	StaminaEffect#=1
	StaminaEffectTimer# = 0
	For i = 0 To 5
		SCP1025state[i] = 0
	Next
	Bloodloss = 0
	
	SuperMan = False
	SuperManTimer = 0
	
	DropSpeed = -0.1
	HeadDropSpeed = 0.0
	Shake = 0
	CurrSpeed = 0
	
	HeartBeatVolume = 0
	HeartBeatRate = 0
	HeartBeatTimer = 0
	Sanity = 0
	RestoreSanity = True
	
	Shake = 0
	LightFlash = 0
	BlurTimer = 0
	
	FallTimer = 0
	;MenuOpen = False
	
	GodMode = 0
	NoClip = 0
	BlurTimer = 0
	
	KillTimer = 0
	KillAnim = 0
	Spectate\SpectatePlayer = -1
	Injuries = 0
	HideEntity Head
	ShowEntity Collider
	MyPlayer\IsDead = 0
	EyeIrritation = 0
	RotateEntity Collider, 0, EntityYaw(Collider), 0
	
	WearingVest = 0
	WearingGasMask = 0
	WearingHazmat = 0
	Wearing1499 = 0
	WearingNightVision = 0
	
	I_427\Using = 0
	I_427\Timer = 0.0

	MyPlayer\PLAYER_MOVE_TIMED = 0
	
End Function
Function multiplayer_breach_SetPlayerType(pType)
	
	If Not(pType = 0 And Spectate\SpectatePlayer = -1) Then
		If MyPlayer\BreachType = pType Or MainMenuOpen Then Return
	EndIf

	br.BreachTypes = GetBreachType(pType)
	
	CanSpawnBody% = True

	If pType = MODEL_ZOMBIE And MyPlayer\BreachType <> MODEL_ZOMBIE Then CanSpawnBody = False
	If pType = MODEL_035 And MyPlayer\BreachType <> MODEL_035 Then CanSpawnBody = False
	
	If CanSpawnBody Then CreateRoleCorpse(MyPlayer\BreachType, EntityX(MyPlayer\Pivot), EntityY(MyPlayer\Pivot)+0.32, EntityZ(MyPlayer\Pivot), EntityYaw(MyPlayer\Pivot), MyPlayer\size)
	
	Local PrevType = MyPlayer\BreachType, teleported = False
	ResetPlayer()
	
	MyPlayer\BreachType = Max(Min(pType, LAST_BREACH_TYPE-1), 0)
	MyPlayer\PrevHealth = -9999
	MyPlayer\Health = 100
	MyPlayer\Handcuffed = 0
	WearingHazmat = 0
	WearingGasMask = 0
	WearingNightVision = 0
	Wearing714 = 0
	Wearing1499 = 0
	Using294 = 0
	EntityType Collider, HIT_PLAYER
	
	For i = 0 To MaxItemAmount-1
		Inventory(i) = Null
	Next
	If Not ChannelPlaying(bellchannel) Then bellchannel = PlaySound_Strict(LoadTempSound("SFX\Ending\GateA\Bell1.ogg"))
	
	MyPlayer.players = MyPlayer

	b_br\YouAreMsg = "You are "+br\Name
	MyPlayer\Health = br\consthp
	Select br\CopiedSpawn
		Case MODEL_096
			For r.Rooms = Each Rooms
				If r\RoomTemplate\Name = "room2servers" Then
					PositionEntity (Collider, EntityX(r\obj), EntityY(r\obj)+0.7, EntityZ(r\obj))
					
					UpdateDoors()
					UpdateRooms()
					For it.Items = Each Items
						it\disttimer = 0
					Next
					PlayerRoom = r
					teleported = True
					Exit
				EndIf
			Next
		Case MODEL_860
			For e.Events = Each Events
				If e\EventConst = e_room860 Then
					Local fr.Forest = e\room\fr
					If fr <> Null Then
						Local rnddoor% = Rand(0, 1)
						PositionEntity Collider,EntityX(fr\Door[rnddoor],True),EntityY(fr\Door[rnddoor],True)+1.5,EntityZ(fr\Door[rnddoor],True),True
						RotateEntity Collider, 0.0, EntityYaw(fr\Door[rnddoor],True)-180, 0.0, True
						MoveEntity Collider, -0.5,0.0,0.5
						PlayerRoom = e\room
						teleported = True
						e\EventState2 = Not rnddoor
					EndIf
					Exit
				EndIf
			Next
		Case MODEL_939
			
			For r.Rooms = Each Rooms
				If r\RoomTemplate\Name = "room3storage" Then
					PositionEntity (Collider, EntityX(r\Objects[4],True),EntityY(r\Objects[4],True)+0.4,EntityZ(r\Objects[4],True))
					
					UpdateDoors()
					UpdateRooms()
					For it.Items = Each Items
						it\disttimer = 0
					Next
					PlayerRoom = r
					teleported = True
					Exit
				EndIf
			Next
		Case NTF_MODEL
			For r.Rooms = Each Rooms
				If r\RoomTemplate\Name = "exit1" Then
					PositionEntity (Collider, EntityX(r\Objects[26],True), EntityY(r\Objects[26],True)+1, EntityZ(r\Objects[26],True))
					
					UpdateDoors()
					UpdateRooms()
					For it.Items = Each Items
						it\disttimer = 0
					Next
					PlayerRoom = r
					teleported = True
					Exit
				EndIf
			Next
			If PrevType = CLASSD_MODEL Or PrevType = SCIENTIST_MODEL Or PrevType = MODEL_CLERK Or PrevType = JANITOR_MODEL Or PrevType = WORKER_MODEL Then Steam_API_SetAchievement("AchvEscape")
			Steam_API_SetAchievement("AchvMTF")
		Case MODEL_049
			For r.Rooms = Each Rooms
				If r\RoomTemplate\Name = "room049" Then
					PositionEntity (Collider, EntityX(r\obj), EntityY(r\obj)+0.7, EntityZ(r\obj))
					
					UpdateDoors()
					UpdateRooms()
					For it.Items = Each Items
						it\disttimer = 0
					Next
					PlayerRoom = r
					teleported = True
					Exit
				EndIf
			Next
		Case MODEL_173
			For r.Rooms = Each Rooms
				If r\RoomTemplate\Name = "room2closets" Then
					PositionEntity (Collider, EntityX(r\obj), EntityY(r\obj)+0.8, EntityZ(r\obj))

					UpdateDoors()
					UpdateRooms()
					For it.Items = Each Items
						it\disttimer = 0
					Next
					PlayerRoom = r
					teleported = True
					Exit
				EndIf
			Next
		Case GUARD_MODEL
			For r.Rooms = Each Rooms
				If r\RoomTemplate\Name = "gateaentrance" Then
					PositionEntity (Collider, EntityX(r\obj), EntityY(r\obj)+0.7, EntityZ(r\obj))
					
					UpdateDoors()
					UpdateRooms()
					For it.Items = Each Items
						it\disttimer = 0
					Next
					PlayerRoom = r
					teleported = True
					Exit
				EndIf
			Next
		Case HAOS_MODEL
			For r.Rooms = Each Rooms
				If r\RoomTemplate\Name = "gatea" Then
					PositionEntity (Collider, EntityX(r\Objects[27],True),EntityY(r\Objects[27],True)+1,EntityZ(r\Objects[27],True))
					
					UpdateDoors()
					UpdateRooms()
					For it.Items = Each Items
						it\disttimer = 0
					Next
					PlayerRoom = r
					teleported = True
					Exit
				EndIf
			Next
			If PrevType = CLASSD_MODEL Or PrevType = SCIENTIST_MODEL Or PrevType = MODEL_CLERK Or PrevType = JANITOR_MODEL Or PrevType = WORKER_MODEL Then Steam_API_SetAchievement("AchvEscape")
		Case CLASSD_MODEL
			For r.rooms = Each rooms
				If r\Roomtemplate\name = "start" Then
					PositionEntity (Collider, EntityX(r\obj)+3584*RoomScale, 704*RoomScale, EntityZ(r\obj)+1024*RoomScale)
					ResetEntity Collider
					PlayerRoom = r
					teleported = True
					Exit
				EndIf
			Next
		Case WORKER_MODEL,JANITOR_MODEL,SCIENTIST_MODEL,MODEL_CLERK
			For r.Rooms = Each Rooms
				If r\RoomTemplate\Name = "room3storage" Then
					PositionEntity (Collider, EntityX(r\obj), EntityY(r\obj)+0.7, EntityZ(r\obj))
					
					UpdateDoors()
					UpdateRooms()
					For it.Items = Each Items
						it\disttimer = 0
					Next
					PlayerRoom = r
					teleported = True
					Exit
				EndIf
			Next
			If MyPlayer\BreachType = SCIENTIST_MODEL Then Steam_API_SetAchievement("AchvScientist")
		Case MODEL_966
			For r.rooms = Each rooms
				If r\roomtemplate\name = "room966" Then
					PositionEntity Collider, EntityX(r\Objects[2],True), EntityY(r\Objects[2],True), EntityZ(r\Objects[2],True)
					
					UpdateDoors()
					UpdateRooms()
					For it.Items = Each Items
						it\disttimer = 0
					Next
					PlayerRoom = r
					teleported = True
					Exit
				EndIf
			Next
		Case MODEL_106
			teleported = False
			For r.rooms = Each rooms
				If r\roomtemplate\name = "room106" Then
					PositionEntity Collider, EntityX(r\Objects[10],True),0.4,EntityZ(r\Objects[10],True)
					
					PlayerRoom = r
					teleported = True
					UpdateDoors()
					UpdateRooms()
					For it.Items = Each Items
						it\disttimer = 0
					Next
					Exit
				EndIf
			Next
			
			EntityType Collider, HIT_OLDMAN
		Case 0
			;runtimeerror("Dead here (1)")
			Spectate\SpectatePlayer = 0
			Spectate\SpectatePlayer = FindSpectatePlayer(True)
			
			Kill("")
			
			MouseHit2 = True
			multiplayer_UpdateSpectate()
			
		Default
			teleported = 2
	End Select
	
	If Not teleported Then
		If br\CopiedSpawn = SCIENTIST_MODEL Or br\CopiedSpawn = MODEL_CLERK Or br\CopiedSpawn = WORKER_MODEL Or br\CopiedSpawn = JANITOR_MODEL Then
			For r.rooms = Each rooms
				If r\roomtemplate\name = "start" Then
					PositionEntity (Collider, EntityX(r\obj)+3584*RoomScale, 704*RoomScale, EntityZ(r\obj)+1024*RoomScale)
					ResetEntity Collider
					PlayerRoom = r
					teleported = True
					Exit
				EndIf
			Next
		Else
			If (multiplayer_IsASCP(br\CopiedSpawn) Or br\CopiedSpawn = HAOS_MODEL Or br\CopiedSpawn = NTF_MODEL Or br\CopiedSpawn = GUARD_MODEL) Then
				For r.rooms = Each rooms
					If r\roomtemplate\name = "room2nuke" Then
						PositionEntity Collider, EntityX(r\obj), EntityY(r\obj)+0.7, EntityZ(r\obj)
						
						PlayerRoom = r
						UpdateDoors()
						UpdateRooms()
						For it.Items = Each Items
							it\disttimer = 0
						Next
						teleported = True
						Exit
					EndIf
				Next
			EndIf
		EndIf
	EndIf

	b_br\YouAreTimer = 70*15
	EqquipedGun = Null
	
	For g.Guns = Each Guns
		g\Magazines = g\MaxMagazines
		g\CurrAmmo = g\MaxCurrAmmo
	Next
	
	If br\CopiedSpawn = MODEL_WAIT Then
		If NetworkServer\Breach And ((b_br\BreachTimer-MilliSecs())-b_br\CurrentBreachTime) > 0 Then
			For r.rooms = Each rooms
				If r\RoomTemplate\Name = "173" Then
					b_br\YouAreMsg = ""
					PositionEntity Collider, EntityX(r\Objects[5], True), 0.5, EntityZ(r\Objects[5], True)
					PlayerRoom = r
					teleported = True
					Exit
				EndIf
			Next
		EndIf
	EndIf
	
	ScaleEntity MyHitbox, br\hitboxx, br\hitboxy, br\hitboxz
	PositionEntity MyHitBox, 0, (-br\FixPivot)+0.05, 0
	If teleported <> 2 Then ResetEntity(Collider)
	DropSpeed = 0
End Function
Function multiplayer_UpdatePlayerModel(i)
	
	Local br.BreachTypes = GetBreachType(Player[i]\BreachType)
	
	If Player[i]\obj <> 0 Then
		FreeEntity(Player[i]\obj)
		Player[i]\obj = 0
	EndIf

	If (Not Player[i]\WearingHazmat) Then
		Player[i]\obj = CopyEntity(br\model)
		If br\texture <> "" Then
			tx = loadtexture_strict(br\texture)
			EntityTexture Player[i]\obj, tx
			FreeTexture(tx)
		EndIf
		ScaleEntity player[i]\obj, br\scale, br\scale, br\scale
		MeshCullBox (Player[i]\obj, -MeshWidth(Player[i]\obj)*2, -MeshHeight(Player[i]\obj)*2, -MeshDepth(Player[i]\obj)*2, MeshWidth(Player[i]\obj)*2, MeshHeight(Player[i]\obj)*4, MeshDepth(Player[i]\obj)*4)
		
		Player[i]\PlayerHead = FindChild(Player[i]\obj, br\BoneName[BONE_HEAD])
		Player[i]\PlayerHand = FindChild(Player[i]\obj, br\BoneName[BONE_HAND])
		Player[i]\PlayerSpine = FindChild(Player[i]\obj, br\BoneName[BONE_SPINE])
		Player[i]\PlayerSpine2 = FindChild(Player[i]\obj, br\BoneName[BONE_SPINE2])
		Player[i]\PlayerForearm = FindChild(Player[i]\obj, br\BoneName[BONE_FOREARM])
	Else
	
		Player[i]\obj = CopyEntity(HAZMAT_OBJECT)
		tempS# = 0.013
		ScaleEntity Player[i]\obj, tempS, tempS, tempS
		MeshCullBox (Player[i]\obj, -MeshWidth(ClassDObj), -MeshHeight(ClassDObj), -MeshDepth(ClassDObj), MeshWidth(ClassDObj)*2, MeshHeight(ClassDObj)*2, MeshDepth(ClassDObj)*2)
		
		tex = LoadTexture_Strict("GFX\items\hazmat.jpg")
		EntityTexture(Player[i]\obj, tex)
		FreeTexture tex

		Local tmpBr.BreachTypes = GetBreachType(CLASSD_MODEL)
		Player[i]\PlayerHead = FindChild(Player[i]\obj, tmpBr\BoneName[BONE_HEAD])
		Player[i]\PlayerHand = FindChild(Player[i]\obj, tmpBr\BoneName[BONE_HAND])
		Player[i]\PlayerSpine = FindChild(Player[i]\obj, tmpBr\BoneName[BONE_SPINE])
		Player[i]\PlayerSpine2 = FindChild(Player[i]\obj, tmpBr\BoneName[BONE_SPINE2])
		Player[i]\PlayerForearm = FindChild(Player[i]\obj, tmpBr\BoneName[BONE_FOREARM])
	EndIf
End Function
Function multiplayer_CreatePlayerObject(i)

	Local br.BreachTypes = GetBreachType(Player[i]\BreachType)
	
	For a = 0 To 7
		Player[i]\playerbones[a] = 0
	Next
	If Player[i]\obj <> 0 Then
		FreeEntity(Player[i]\obj)
		Player[i]\obj = 0
	EndIf
	FreePlayerObjects(Player[i])
	If (Not Player[i]\WearingHazmat) Then
		Player[i]\obj = CopyEntity(br\model)
		If br\texture <> "" Then
			tx = loadtexture_strict(br\texture)
			EntityTexture Player[i]\obj, tx
			FreeTexture(tx)
		EndIf
		ScaleEntity player[i]\obj, br\scale, br\scale, br\scale
		MeshCullBox (Player[i]\obj, -MeshWidth(Player[i]\obj)*2, -MeshHeight(Player[i]\obj)*2, -MeshDepth(Player[i]\obj)*2, MeshWidth(Player[i]\obj)*2, MeshHeight(Player[i]\obj)*4, MeshDepth(Player[i]\obj)*4)
		
		Player[i]\PlayerHead = FindChild(Player[i]\obj, br\BoneName[BONE_HEAD])
		Player[i]\PlayerHand = FindChild(Player[i]\obj, br\BoneName[BONE_HAND])
		Player[i]\PlayerSpine = FindChild(Player[i]\obj, br\BoneName[BONE_SPINE])
		Player[i]\PlayerSpine2 = FindChild(Player[i]\obj, br\BoneName[BONE_SPINE2])
		Player[i]\PlayerForearm = FindChild(Player[i]\obj, br\BoneName[BONE_FOREARM])
	Else
	
		Player[i]\obj = CopyEntity(HAZMAT_OBJECT)
		tempS# = 0.013
		ScaleEntity Player[i]\obj, tempS, tempS, tempS
		MeshCullBox (Player[i]\obj, -MeshWidth(ClassDObj), -MeshHeight(ClassDObj), -MeshDepth(ClassDObj), MeshWidth(ClassDObj)*2, MeshHeight(ClassDObj)*2, MeshDepth(ClassDObj)*2)
		
		tex = LoadTexture_Strict("GFX\items\hazmat.jpg")
		EntityTexture(Player[i]\obj, tex)
		FreeTexture tex

		Local tmpBr.BreachTypes = GetBreachType(CLASSD_MODEL)
		Player[i]\PlayerHead = FindChild(Player[i]\obj, tmpBr\BoneName[BONE_HEAD])
		Player[i]\PlayerHand = FindChild(Player[i]\obj, tmpBr\BoneName[BONE_HAND])
		Player[i]\PlayerSpine = FindChild(Player[i]\obj, tmpBr\BoneName[BONE_SPINE])
		Player[i]\PlayerSpine2 = FindChild(Player[i]\obj, tmpBr\BoneName[BONE_SPINE2])
		Player[i]\PlayerForearm = FindChild(Player[i]\obj, tmpBr\BoneName[BONE_FOREARM])
	EndIf
	
	If GetPlayerHead(i) <> 0 Then Player[i]\PlayerHeadOffset = EntityRoll(GetPlayerHead(i), True)
		
	If Player[i]\BreachType = CLASSD_MODEL And (Not Player[i]\WearingHazmat) Then
		If Player[i]\TextureID > 0 Then
			tex = LoadTexture_Strict("GFX\npcs\classd"+Player[i]\TextureID+".jpg")
			EntityTexture(Player[i]\obj, tex)
			FreeTexture tex
		EndIf
	EndIf
	;EntityPickMode Player[i]\obj, 2, False
	
	Player[i]\Camera = CreateCamera()
	
	If Player[i]\monitor_width <> 0 Then
		CameraViewport Player[i]\Camera,0,0,Player[i]\monitor_width,Player[i]\monitor_height
	Else
		CameraViewport Player[i]\Camera,0,0,GraphicWidth,GraphicHeight
	EndIf
	
	CameraRange(Player[i]\Camera, 0.05, 35)
	CameraProjMode(Player[i]\Camera, 0)
	
	If Player[i]\Pivot = 0 Then
		Player[i]\Pivot = CreatePivot()
		EntityType Player[i]\Pivot, HIT_PLAYER
		EntityRadius Player[i]\Pivot, 0.15, 1
		;EntityPickMode Player[i]\Pivot, 1
		
		Player[i]\Hitbox = CreateCube()
		EntityParent Player[i]\Hitbox, Player[i]\Pivot
		EntityPickMode Player[i]\Hitbox, 2, False
		MoveEntity Player[i]\Hitbox, 0, -0.2, 0
		EntityAlpha Player[i]\Hitbox, 0.0
	EndIf
	
	ScaleEntity Player[i]\Hitbox, br\hitboxx, br\hitboxy, br\hitboxz
	
	InitPlayerObjects(i)
	multiplayer_ResetPlayerSize(i)
End Function

Function IsACommand(mess$)
	;if instr(mess, "noclip") then
	;	Noclip = Not Noclip
	;	return 1
	;EndIf
	
	;if instr(mess, "sanic") then
	;	SuperMan = Not SuperMan
	;	return 1
	;EndIf
	;if Instr(mess, "/createbot") Then
		;Local nick$ = getrandomname("botnames.txt")
		;if NetworkServer\Breach = True Then
		;	if rcon\authorized Then
		;		multiplayer_AddChatMsg("Adding...", False)
		;		udp_WriteByte M_CONNECTBOT
		;		udp_WriteByte NetworkServer\MyID
		;		udp_WriteLine nick
		;		udp_SendMessage()
		;	Else
		;		multiplayer_AddChatMsg("You are not a admin.", False)
		;	EndIf
		;	Return
		;EndIf
		;Local p.players = multiplayer_CreatePlayer(FindFreePlayerID())
		;p\name = nick
		;multiplayer_InitPlayer(p\ID)
		;multiplayer_ChangePlayerName(p\ID, nick)
		;n.NPCs = CreateNPC(NPCtypeClassDBot, EntityX(Collider), EntityY(Collider)+0.32, EntityZ(Collider))
		;p\BreachType = CLASSD_MODEL
		;if n <> Null Then
		;
		;	multiplayer_AddChatMsg(p\name+" has joined to server")
		;	n\obj = p\obj
		;	n\collider = p\pivot
		;	PositionEntity(n\collider, EntityX(Collider), EntityY(Collider)+0.32, EntityZ(Collider), true)
		;	PositionEntity(n\obj, EntityX(Collider), EntityY(Collider)+0.32, EntityZ(Collider), true)
		;	ResetEntity n\collider
		;	p\IsABot = n\ID
		;	;p\PlayerRoomID = MyPlayer\PlayerRoomID
		;	InitPlayerObjects(p\ID)
		;	if Instr(mess, "1") Then n\StupidMode = True
		;Else
		;	multiplayer_DisconnectPlayer(p\ID)
		;EndIf
		;Return 1
	;EndIf
	Return 0
End Function
Function ClearServer()
	For p.players = Each players
		multiplayer_DisconnectPlayer(p)
	Next
	NetworkServer\Breach = False
	If MainMenuOpen = False Then
		PlaySound_Strict(ButtonSFX)
		NullGame(False)
		MenuOpen = False
	EndIf
	For sc.Servers = Each Servers
		If sc\timed Then multiplayer_list_DeleteServer(sc)
	Next
	FlushKeys()
	
	UDP_SetStream(udp_network, False)
	CancelSteamTicket()
	
	;if NetworkServer\TCPStream <> 0 Then
	;	CloseTCPStream(NetworkServer\TCPStream)
	;	NetworkServer\TCPStream = 0
	;EndIf
	NetworkServer\HaveFiles = False
	NetworkServer\DetectIncorrect = False
	NetworkServer\MainPlayer = False
	NetworkServer\Hosted = False
	NetworkServer\objectsTicks = False
	NetworkServer\drawsTicks = False
	NetworkServer\textsTicks = False
	NetworkServer\chatTicks = False
	NetworkServer\LoadedObjects = False
	NetworkServer\EntitiesLoaded = False
	NetworkServer\pspawnx = 0
	NetworkServer\pspawny = 0
	NetworkServer\pspawnz = 0
	NetworkServer\MyID = 0
	NetworkServer\ServerCustomMap = ""
	NetworkServer\ServerMapLoaded = False
	NetworkServer\Servername = ""
	NetworkServer\LongCulling = 0
	NetworkServer\SteamStream = False
	NetworkServer\CurrentLobby = 0
	NetworkServer\JumpMode = False
	NetworkServer\Breach = False
	NetworkServer\MapSize = 3
	NetworkServer\FullSync = 0
	NetworkServer\notshouldplaysiren% = False
	NetworkServer\NoclipAnticheat% = False
	;NetworkServer\PlayerDirsHeap = 0
	Discord_API_SetState("", 2)
	
	SecondaryLightOn = True
	rcon\Authorized = False
	CurrentPositionID = 0
	LockMouse = False
	MENU_OPEN_TYPE = 0
	MainMenuOpen = True
	MainMenuTab = True
	GameLoad = False
	PasswordMenu = False
	ConnectMenu = False
	Ready = "Not Ready"
	SELECTED_P_PAGE = 0
	SCRIPT_COUNT = 0
	NoCheat = False
	PlayerIntercom\IntercomTimeout = 20000
	OutSCP = True
	chatedittype = False
	b_br\YouAreTimer=0.0
	MsgTimer=0.0
	b_br\WonTimer=0.0
	NEWYEARINDEX% = IsANewYear()
	HALLOWEENINDEX% = IsAHalloween()
	b_br\LobbyTimer = 0
	b_br\BreachTimer = 0
			
	For snd.snd3d = Each snd3d
		If Not snd\fmod Then
			StopChannel(snd\soundchn)
			Delete snd
		Else
			FSOUND_StopSound(snd\soundchn)
			FSOUND_Stream_Close(snd\sound)
			Delete snd
		EndIf
	Next
	
	Delete Each chatmessage
	Delete Each announcements
	Delete Each multiplayer_objects
	;Delete Each MoveDirs
	deletequerys()
	
	For s.ScriptsThread = Each ScriptsThread
		SE_DeleteScript(s\scriptThread)
		Delete s
	Next
	
	For WT.WorkshopThread = Each WorkshopThread
		If WT\scriptthread = Null Then
			WT\scriptthread = SE_LoadScriptExec(WT\scriptfile)
			skynet_onload(1)
			
			init_publics_for_script(WT\scriptThread)
		EndIf
	Next
	
	For dr.draws = Each draws
		If dr\pointer <> False Then FreeImage dr\pointer
		For dr2.draws = Each draws
			If dr2\pointer = dr\pointer And dr2 <> dr Then dr2\pointer = False
		Next
		Delete dr
	Next
	For txts.multiplayer_texts = Each multiplayer_texts
		If txts\font <> False Then FreeFont txts\font
		For txts2.multiplayer_texts = Each multiplayer_texts
			If txts\font = txts2\font And txts <> txts2 Then txts2\font = False
		Next
		Delete txts
	Next
	
	ws_CheckSubscribedItems(True)
	ByteStreamReset(MicroByte)
	
	For sc.Servers = Each servers
		If sc\sType = SERVER_INTERNET Or sc\sType = SERVER_OFFICIAL Then multiplayer_list_UpdateServer(sc)
	Next
End Function
Function FindEventObject(e.Events)
	If (Not udp_GetStream()) Or e\room = Null Then
		e\NearObjectQueue = NetworkServer\MyID
		Return Collider
	EndIf
	If e\Tick >= NetworkServer\PlayersCount Or e\NearObjectQueue+1 = NetworkServer\Maxplayers+1 Then 
		e\NearObjectQueue = 0
		e\Tick = 0
	EndIf
	For i = e\NearObjectQueue+1 To NetworkServer\Maxplayers
		If Player[i] <> Null Then
			e\Tick = e\Tick+1
			If Player[i]\PlayerRoomID = e\room\ID And (Not Player[i]\IsDead) Then
				e\NearObjectQueue = i
				Return Player[e\NearObjectQueue]\Pivot
			EndIf
		EndIf
	Next
	If Player[e\NearObjectQueue] = Null Then e\NearObjectQueue = NetworkServer\MyID
	Return Player[e\NearObjectQueue]\Pivot
End Function
Function DisconnectServer(msgs$ = "", ClearAServer% = True)
	If GetScripts() Then public_inqueue(public_OnDisconnect, True)
	If udp_GetStream() Then
		udp_WriteByte(M_DISCONNECT)
		udp_WriteByte(NetworkServer\MyID)
		If NetworkServer\MainPlayer And (Not NetworkServer\Hosted) Then
			udp_WriteByte 253
			udp_WriteInt 0
			udp_WriteInt 0
			udp_SendMessageInternal(udp_network\stream,udp_network\messip,udp_network\messport)
		Else
			udp_SendMessage()
			If NetworkServer\SteamStream Then
				Local waitms = MilliSecs2()+1000
				While True
					Steam_Update()
					If waitms < MilliSecs2() Then Exit
				Wend
			EndIf
		EndIf
		
		If msgs = "exit" Then Return
	EndIf
	If ClearAServer Then ClearServer()
	AddErrorLog(msgs, 255, 255, 255, 15000)
	DebugLog "DISCONNECT SUCCESSFUL"
End Function
Function PlayerInRoom(e.Events)
	Return (Player[e\NearObjectQueue]\PlayerRoomID = e\room\ID)
End Function
Function CreateFile(filename$)
	CloseFile(WriteFile(filename))
End Function
Function GetObject(e.Events)
	If Player[e\NearObjectQueue] = Null Then Return Collider
	Return Player[e\NearObjectQueue]\Pivot
End Function

Function DeleteCharFromString$(stri$, char$, offset = 1)
	Local returnstring$
	For i = 1 To Len(stri)
		If Mid(stri, i, 1) <> char Or i < offset Then returnstring = returnstring + Mid(stri, i, 1)
	Next
	Return returnstring
End Function
Function LoadMultiplayerOptions(crypt = False)
	If FileType("Temp") <> 2 Then
		CreateDir("Temp")
	EndIf
	Local d = ReadDir("Temp"), filename$
	While True
		filename = NextFile(d)
		If filename = "" Then Exit
		If FileType("Temp\"+filename) = 1 Then DeleteFile("Temp\"+filename)
	Wend
	If FileSize("Data\multiplayer.ini") = 0 Then 
		CreateFile("Data\multiplayer.ini")
		NetworkServer\seeNames = 1
		voice\volume = 0.7
		mainplayersvolume = 1.0
		NetworkServer\downloadspeed = 1024
		SaveMultiplayerOptions()
		KEY_CHAT = 64
		KEY_VOICE = 47
		Return
	EndIf
	Local f = ReadFile("Data\multiplayer.ini")
	If ReadFloat(f) < 0.67 Then
		CloseFile f
		DeleteFile("Data\multiplayer.ini")
		CreateFile("Data\multiplayer.ini")
		NetworkServer\seeNames = 1
		voice\volume = 0.7
		NetworkServer\downloadspeed = 1024
		mainplayersvolume = 1.0
		SaveMultiplayerOptions()
		Return
	EndIf
	Nickname$ = ReadLine(f)
	ReadLine(f)
	NetworkServer\seeNames = ReadInt(f)
	NetworkServer\haveVoice = ReadInt(f)
	While True
		If ReadInt(f) = 0 Then Exit
		ReadInt(f)
		ReadInt(f)
		ReadInt(f)
	Wend
	voice\volume = Float(ReadByte(f))/100
	mainplayersvolume = Float(ReadByte(f))/100
	NetworkServer\hpbar = ReadByte(f)
	NetworkServer\downloadspeed = ReadShort(f)
	voice\PushToTalk = ReadByte(f)

	While True
		If ReadInt(f) = 0 Then Exit
		Local ip$ = DottedIP(ReadInt(f))
		Local port = ReadInt(f)
		Local stype = ReadInt(f)
		If ip = "0.0.0.0" Then ip = "localhost"
		
		multiplayer_list_AddServer(ip, port, stype, False, False)
	Wend
	
	For sc.servers = Each Servers
		If sc\stype = SERVER_LOCAL Or sc\stype = SERVER_INTERNET Or sc\stype = SERVER_OFFICIAL Then Delete sc
	Next
	
	If Eof(f) Then
		chatoffsety# = GraphicHeight-650*MenuScale
		chatoffsetx# = 20*MenuScale
		
		chatwidth# = GraphicWidth/3.2
		chatheight# = 450*MenuScale
	Else
		Local SavedGraphicWidth = ReadFloat(f)
		Local SavedGraphicHeight = ReadFloat(f)
		
		chatoffsetx = ReadFloat(f)
		chatoffsety = ReadFloat(f)
		chatwidth = ReadFloat(f)
		chatheight = ReadFloat(f)
		chattypes = ReadByte(f)
		
		If SavedGraphicWidth <> GraphicWidth Or SavedGraphicHeight <> GraphicHeight Then
			chatoffsety# = GraphicHeight-500*MenuScale
			chatoffsetx# = 20*MenuScale
			
			chatwidth# = GraphicWidth/3.2
			chatheight# = 300*MenuScale
		EndIf
	EndIf
	CloseFile f
End Function
Function SaveMultiplayerOptions()
	If FileSize("Data\multiplayer.ini") = 0 Then CreateFile("Data\multiplayer.ini")
	Local f = WriteFile("Data\multiplayer.ini")
	WriteFloat f,Float(DeleteCharFromString(MULTIPLAYER_VERSION, ".", 3))
	WriteLine f,Nickname
	WriteLine f,"NULL"
	WriteInt f,NetworkServer\seeNames
	WriteInt f,NetworkServer\haveVoice
	WriteInt f,0
	WriteByte f, Int(voice\volume*100)
	WriteByte f, Int(mainplayersvolume*100)
	WriteByte f, NetworkServer\hpbar
	WriteShort f, NetworkServer\downloadspeed
	WriteByte f, voice\PushToTalk
	
	For sc.Servers = Each Servers
		If (Not sc\timed) And sc\stype <> 0 Then
			WriteInt f,1
			WriteInt f,IPToDecimal(sc\IP)
			WriteInt f,sc\Port
			WriteInt f,sc\stype
		EndIf
	Next
	WriteInt f,0
	
	WriteFloat f, GraphicWidth
	WriteFloat f, GraphicHeight
	WriteFloat f, chatoffsetx
	WriteFloat f, chatoffsety
	WriteFloat f, chatwidth
	WriteFloat f, chatheight
	WriteByte f, chattypes
	
	CloseFile f
End Function
Function GetClipboardText$()
	If Not api_OpenClipboard(0) Then Return
	If Not api_IsClipboardFormatAvailable(1) Then Return
	txt$ = api_GetClipboardDataText$(1)
	api_CloseClipboard()
	Return txt$
End Function
Function SetClipboardText(tex$)
	If Not api_OpenClipboard(0) Then Return
	If Not api_IsClipboardFormatAvailable(1) Then Return
	
	clipbuffer = api_GlobalAlloc($0002,Len(tex)+1)
	If clipbuffer <> 0 Then
		buffer2 = api_GlobalLock( clipbuffer )
		If buffer2 <> 0 Then
			Local bnk = CreateBank(Len(tex)+1)
			For i = 1 To Len(tex)
				PokeByte(bnk, i-1, Asc(Mid(tex, i, 1)))
			Next
			api_lstrcpy(buffer2,bnk) 
			api_GlobalUnlock( clipbuffer )
			api_EmptyClipboard()
			api_SetClipboardData(1,clipbuffer)
			api_CloseClipboard()
			FreeBank(bnk)
		EndIf
		api_GlobalFree(clipbuffer)
	EndIf
End Function
;;;;;Function UpdateBot(i)
;;;;;;;;	Select i
;;;;;;;;;;;;;;		Case 1
;;;;;;;;;;;;;			if CurrBot = Last botspath1 Then Return
;;;;;;;;;;;;;;;;;;;;;			if CurrBot = Null Then CurrBot = First botspath1
;;;;;;;;;;;;;;;;			PositionEntity Player[i]\obj, CurrBot\X,CurrBot\Y-0.32,CurrBot\Z
;;;;;;;;;			RotateEntity Player[i]\obj,0,CurrBot\Yaw,0
;;;;;;;;;;;;;;;;			CurrBot = After CurrBot
;;;;;;;;;			Animate2(Player[i]\obj, AnimTime(Player[i]\obj), 301, 319, 0.3) 
;;;;;;;;		Case 2
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;			if CurrBot2 = Last botspath2 Then Return
;;;;;;;;			if CurrBot2 = Null Then CurrBot2 = First botspath2
;;;;;;;;;			PositionEntity Player[i]\obj, CurrBot2\X,CurrBot2\Y-0.32,CurrBot2\Z
;;;;;;;;;;;;;;;;;;;;			RotateEntity Player[i]\obj,0,CurrBot2\Yaw,0
;;;;;;;;;;;;;;;;;;;;;;;			CurrBot2 = After CurrBot2
;;;;;;;;;;			Animate2(Player[i]\obj, AnimTime(Player[i]\obj), 301, 319, 0.3) 
;;;;;;;		Case 3
;;;;;;;;			if CurrBot3 = Last botspath3 Then Return
;;;;;;;;;			if CurrBot3 = Null Then CurrBot3 = First botspath3
;;;;;;;;			PositionEntity Player[i]\obj, CurrBot3\X,CurrBot3\Y-0.32,CurrBot3\Z
;;;;;;;;			RotateEntity Player[i]\obj,0,CurrBot3\Yaw,0
;;;;;;;;			CurrBot3 = After CurrBot3
;;;;;;;;;;			Animate2(Player[i]\obj, AnimTime(Player[i]\obj), 301, 319, 0.3) 
;;;;;;;;		Case 4
;;;;;;;;;;			if CurrBot4 = Last botspath4 Then Return
;;;;;;;;;;;;;;;;;;;;			if CurrBot4 = Null Then CurrBot4 = First botspath4
;;;;;;;;;			PositionEntity Player[i]\obj, CurrBot4\X,CurrBot4\Y-0.32,CurrBot4\Z
;;;;;;			RotateEntity Player[i]\obj,0,CurrBot4\Yaw-180,0
;;;;;;			CurrBot4 = After CurrBot4
;			Animate2(Player[i]\obj, AnimTime(Player[i]\obj), 284, 333, 1.2*43)
;	End Select
;End Function
;Function LoadBotPath(i)
;	Local f = ReadFile("multiplayer\botpath"+i)
;	Select i
;		Case 1
;			While Not Eof(f)
;				Local bb.botspath1 = New botspath1
;				bb\X = Float(ReadLine(f))
;				bb\Y = Float(ReadLine(f))
;				bb\Z = Float(ReadLine(f))
;				bb\Yaw = Float(ReadLine(f))
;			Wend
;		Case 2
;			While Not Eof(f)
;				Local bbs.botspath2 = New botspath2
;				bbs\X = Float(ReadLine(f))
;				bbs\Y = Float(ReadLine(f))
;				bbs\Z = Float(ReadLine(f))
;				bbs\Yaw = Float(ReadLine(f))
;			Wend
;		Case 3
;			While Not Eof(f)
;				Local bbd.botspath3 = New botspath3
;				bbd\X = Float(ReadLine(f))
;				bbd\Y = Float(ReadLine(f))
;				bbd\Z = Float(ReadLine(f))
;				bbd\Yaw = Float(ReadLine(f))
;			Wend
;		Case 4
;			While Not Eof(f)
;;				Local bbf.botspath4 = New botspath4
;				bbf\X = Float(ReadLine(f))
;				bbf\Y = Float(ReadLine(f))
;				bbf\Z = Float(ReadLine(f))
;				bbf\Yaw = Float(ReadLine(f))
;			Wend
;	End Select
;;	CloseFile f
;End Function
Function GetPlayerCamera(id)
	If id = NetworkServer\MyID Then Return Camera
	If Player[id] = Null Then Return Camera
	Return Player[id]\Camera
End Function

Function UpdateDeadBodies()
	For c.p_obj = Each p_obj
	
		If EntityDistance(Collider, c\CorpseEntity) >= HideDistance*1.5 Then 
			HideEntity c\CorpseEntity
		Else
			ShowEntity c\CorpseEntity
		EndIf
		
		If c\Timeout < MilliSecs() Then
			If c\FallFactor < 0 Then
				FreeEntity c\CorpseEntity
				Delete c
			Else
				TranslateEntity c\CorpseEntity, 0, -0.001*FPSfactor, 0
				c\FallFactor = c\FallFactor-FPSfactor
			EndIf
		EndIf
	Next
End Function
Function CreateRoleCorpse(role%, X#,Y#,Z#,A#, scale#=1.0, ReturnOnlyModel%=False, AlreadyDied%=False, ForceID%=0, IsReceived% = False)
	BT.BreachTypes = GetBreachType(role)
	If GetSecondPackedValue(BT\DeadAnimationData) = 0 Then Return 0

	CorpseEntity = CopyEntity(BT\model)
	PositionEntity CorpseEntity, X, Y-0.32-BT\FixPivot, Z
	ScaleEntity CorpseEntity, BT\scale*scale, BT\scale*scale, BT\scale*scale
	RotateEntity CorpseEntity, BT\FixPitch, A+BT\FixRotate, 0
	ResetEntity(CorpseEntity)
	MeshCullBox (CorpseEntity, -MeshWidth(BT\model), -MeshHeight(BT\model), -MeshDepth(BT\model), MeshWidth(BT\model)*2, MeshHeight(BT\model)*2, MeshDepth(BT\model)*2)

	ExtractAnimSeq(CorpseEntity, GetFirstPackedValue(BT\DeadAnimationData),GetSecondPackedValue(BT\DeadAnimationData))
	Animate(CorpseEntity, 3,0.5,1)
	;Else
	;	SetAnimTime(CorpseEntity, GetSecondPackedValue(BT\DeadAnimationData))
	;EndIf
	
	If BT\Texture <> "" Then
		Tex = LoadTexture_Strict(BT\Texture)
		EntityTexture(CorpseEntity, Tex)
		FreeTexture Tex
	EndIf
	
	If ReturnOnlyModel Then Return CorpseEntity
	
	c.p_obj = New p_obj
	c\breachtype = role
	c\Timeout = MilliSecs()+120000
	c\FallFactor = 70*5
	c\ID = FindFreeCorpseID()
	c\CorpseEntity = CorpseEntity
	c\Received = IsReceived
	
	If ForceID <> 0 Then c\ID = ForceID

	M_Corpse[c\ID] = c
	
	Return CorpseEntity
End Function

Function FindFreeCorpseID()
	For i = 1 To MAX_CORPSES-1
		If M_Corpse[i] = Null Then Return i
	Next
End Function

Function IsAEnding(roomname$)
	If roomname = "exit1" And EntityY(Collider)>1040.0*RoomScale Then Return 1
	If roomname = "gatea" Then Return 1
	Return 0
End Function
Function ClampValue(Original#, low#, high#)
	If Original<low  Then Return low
	If Original>high Then Return high
	Return Original
End Function
Function UseIntercom()
	If player_isdead() Or PlayerIntercom\CheckIntercom > MilliSecs() Then Return
	
	If GetMilliSecs(10) Then
		udp_ByteStreamWriteChar(M_WANTINTERCOM)
		StartMilliSecs(10, 50)
	EndIf
End Function
; find player for spectate ;
Function FindSpectatePlayer(leftright)
	If leftright = False Then
		If Spectate\SpectatePlayer-1 >= 0 Then
			PrevSpectate = Spectate\SpectatePlayer
			For i = Spectate\SpectatePlayer-1 To 1 Step -1
				If Player[i] <> Null Then
					If Player[i]\BreachType <> 0 Then 
						Spectate\SpectatePlayer = i
						Exit
					EndIf
				EndIf
			Next
			If PrevSpectate = Spectate\SpectatePlayer Then
				For i = Spectate\SpectatePlayer+1 To NetworkServer\Maxplayers
					If Player[i] <> Null Then
						If Player[i]\BreachType <> 0 Then 
							Spectate\SpectatePlayer = i
						EndIf
					EndIf
				Next
			EndIf
		EndIf
	Else
		PrevSpectate = Spectate\SpectatePlayer
		For i = Spectate\SpectatePlayer+1 To NetworkServer\Maxplayers
			If Player[i] <> Null Then
				If Player[i]\BreachType <> 0 Then 
					Spectate\SpectatePlayer = i
					Exit
				EndIf
			EndIf
		Next
		If PrevSpectate = Spectate\SpectatePlayer Then
			For i = Spectate\SpectatePlayer-1 To 1 Step -1
				If Player[i] <> Null Then
					If Player[i]\BreachType <> 0 Then 
						Spectate\SpectatePlayer = i
					EndIf
				EndIf
			Next
		EndIf
	EndIf
	Return Spectate\SpectatePlayer
End Function
Function AddErrorLog(txt$, r=255, g=255, b=255, interval = 5000)
	If txt = "" Then Return
	Local er.errors = New errors
	er\txt = txt
	er\Interval = MilliSecs()+interval
	er\r = r
	er\g = g
	er\b = b
End Function
Function intround(i#, round# = 10)
	Return Int((i/round)*round)
End Function
Function FindFreePlayerID()
	For i = 1 To NetworkServer\Maxplayers
		If Player[i] = Null Then Return i
	Next
End Function
Function FindFreeDoorID()
	For i = 1 To MAX_DOORS-1
		If multiplayer_Door[i] = Null Then Return i
	Next
End Function
Function FindFreeItemID%()
	For i = 1 To MAX_ITEMS-1
		If M_Item[i] = Null Then Return i
	Next
End Function
Function FindFreeNPCID%()
	For i = 1 To MAX_NPCs-1
		If M_NPC[i] = Null Then Return i
	Next
End Function
Function FindFreeEventID()
	For i = 1 To MAX_EVENTS-1
		If M_Event[i] = Null Then Return i
	Next
End Function
Function GetItemID(it.Items)
	If it <> Null Then Return it\ID
	Return 0
End Function
Function GetRoomID(r.Rooms)
	If r <> Null Then Return r\ID
	Return 0
End Function

Type GlobalLoadedSound
	Field loadername$
	Field SoundID%
End Type

Function LoadSound_Global(filename$)
	
	For GLS.GLobalLoadedSound = Each GlobalLoadedSound
		If GLS\LoaderName = filename Then
			Return GLS\SoundID
		EndIf
	Next
	
	Local lowered$ = Lower(filename)
	For RS.RedirectedSound = Each RedirectedSound
		If lowered = rs\lowerednewfile Then
			filename = rs\newfile
			Exit
		EndIf
	Next
	
	Local Sound = LoadSound(filename)
	If Sound = 0 Then Return 0
	
	GLS.GLobalLoadedSound = New GlobalLoadedSound
	GLS\Loadername = filename
	GLS\SoundID = Sound
	Return GLS\SoundID
End Function

Function Play3DSoundEntity(soundhandle, cam%, entity%, range# = 10, volume# = 1.0, loader$ = "")
	snd.snd3d = New snd3d
	snd\entity = entity
	snd\range = range
	snd\volume = volume
	snd\tempentity = True
	If loader = "" Then
		snd\soundchn = PlaySound2(soundhandle, cam, entity, range, volume)
	Else
		snd\sound = LoadSound_Global(loader)
		
		If snd\sound = 0 Then
			Delete snd
			Return 0
		EndIf
		snd\soundchn = PlaySound(snd\sound)
		UpdateSoundOrigin(snd\soundchn, Camera, snd\entity, snd\range, snd\volume)
	EndIf
	Return snd\soundchn
End Function
Function Play3DSound(soundhandle, cam%, entity%, range# = 10, volume# = 1.0, loader$ = "")
	snd.snd3d = New snd3d
	snd\entity = entity
	snd\range = range
	snd\volume = volume
	If loader = "" Then
		snd\soundchn = PlaySound2(soundhandle, cam, entity, range, volume)
	Else
		snd\sound = LoadSound_Global(loader)
		If snd\sound = 0 Then
			Delete snd
			Return 0
		EndIf
		snd\soundchn = PlaySound(snd\sound)
		UpdateSoundOrigin(snd\soundchn, Camera, snd\entity, snd\range, snd\volume)
	EndIf
	Return snd\soundchn
End Function
			
Function Update3DSounds()
	For snd.snd3d = Each snd3d
		If Not snd\fmod Then
			If snd\entity <> 0 Then UpdateSoundOrigin(snd\soundchn,Camera, snd\entity, snd\range, snd\volume)
			If Not ChannelPlaying(snd\soundchn) Then 
				StopChannel(snd\soundchn)
				If snd\tempentity <> 0 Then FreeEntity snd\entity
				Delete snd
			EndIf
		Else
			If snd\entity <> 0 Then UpdateSoundFMOD(snd\soundchn,Camera, snd\entity, snd\range, snd\volume)
			If Not FSOUND_IsPlaying(snd\soundchn) Then 
				FSOUND_StopSound(snd\soundchn)
				FSOUND_Stream_Close(snd\sound)
				Delete snd
			EndIf
		EndIf
	Next
End Function
Function CalculateDist#(obj, r.Rooms)
	Return Max(Abs(r\x-EntityX(obj,True)),Abs(r\z-EntityZ(obj,True)))
End Function
Function FindTextInChat(msg$)
	For cm.ChatMessage = Each ChatMessage
		If cm\txt = msg Then Return True
	Next
	Return False
End Function

Function multiplayer_GetTickrateDelay()
	Return 40-Float(NetworkServer\Tickrate)/6.4
End Function
Function ClearStreamBuffer(stream)
	Local port = UDPStreamPort(stream)
	CloseUDPStream stream
	Return CreateUDPStream(port)
End Function
Function GiveDamage(id, force#)
	;if (NetworkServer\MainPlayer or rcon\authorized) And Player[id] <> Null Then
	;	if player[id]\isabot <> 0 And player[id]\isdead = false Then
	;		Play3DSound(0, Camera, Player[id]\pivot, 10, 1, "SFX\multiplayer\scream.mp3")
	;		multiplayer_WriteTempSound("SFX\multiplayer\scream.mp3", 0,0,0,10,1,id)
	;		if force = -1 Or Player[id]\hp-force < 0 Then
	;			M_NPC[Player[id]\IsABot]\IsDead = True
	;			CreateCorpse(EntityX(Player[id]\obj),EntityY(Player[id]\obj),EntityZ(Player[id]\obj),EntityYaw(Player[id]\obj), ClassDObj, "GFX\npcs\classd1.jpg", 0,19)
	;			player[id]\x = 8788
	;			player[id]\y = 8788
	;			player[id]\z = 8788
	;			multiplayer_UpdatePlayer(Player[id])
	;			Player[id]\IsDead = True
	;			multiplayer_UpdatePlayer(Player[id])
	;			if NetworkServer\Breach = True
	;				multiplayer_RequestRoleForPlayer(id, 0)
	;				Player[id]\breachtype = 0
	;			Else
	;
	;			EndIf
	;		Else
	;			Player[id]\hp = Player[id]\hp-force
	;			M_NPC[Player[id]\IsABot]\State = 2
	;			M_NPC[Player[id]\IsABot]\PathTimer = 0.0
	;			M_NPC[Player[id]\IsABot]\PathStatus = 0
	;			M_NPC[Player[id]\IsABot]\CurrSpeed = 0.0
	;		EndIf
	;	EndIf
	;EndIf
End Function
;Function GetBotByID(npcid)
;	For p.players = each players
;		if p\IsABot = npcid Then Return p\ID
;	Next
;End Function
Function getrandomname$(file$)
	Local f = ReadFile(file)
	If f = 0 Then Return "Player"
	Local nick$ = "Player"
	Local nicks$[65535], count
	While Not Eof(f)
		nicks[count] = ReadLine(f)
		count = count + 1
	Wend
	CloseFile f
	Return nicks[Rand(0, count-1)]
End Function
Function ServerInList(IP$, Port, stype)
	For sc.Servers = Each Servers
		If sc\IP = IP And sc\port = Port And stype = sc\stype Then Return True
	Next
	Return False
End Function
Function GetServerInList.Servers(IP$, Port, stype)
	For sc.Servers = Each Servers
		If sc\IP = IP And sc\port = Port And stype = sc\stype Then Return sc
	Next
End Function
Function RandStr(size%)
	Local returnstring$
	For i = 1 To size
		If Rand(3)=1 Then
			returnstring = returnstring + Rand(0,9)
		Else
			returnstring = returnstring + Chr(Rand(97,122))
		EndIf
	Next
	Return returnstring
End Function
Function LoadInternetServers(Host$ = MULTIPLAYER_MOD_HOST)
	Local TrimmedHOST$ = Right(Host, Len(Host)-7)
	;debuglog Left(TrimmedHOST, Len(TrimmedHOST)-1)
	
	f = DownloadFile(Host+"BlacklistedServers.txt")
	If f <> 0 Then
		If ReadLine(f) = "SCP-CB Multiplayer Mod Blacklisted Servers" Then
			While Not Eof(f)
				ip$ = ReadLine(f)
				bs.BlacklistedServers = New BlacklistedServers
				bs\IP = ip
			Wend
		EndIf
	EndIf

	If CountHostIPs(Left(TrimmedHOST, Len(TrimmedHOST)-1)) <> 0 Then
		f = DownloadFile(Host+"Servers.txt") ;TestServers-Disabled
		If f <> 0 Then
			Local msg$
			added% = False
			If ReadLine(f) = "SCP-CB Multiplayer Mod Servers" Then
				While Not Eof(f)
					msgf$ = ReadLine(f)
					IPAndPort$ = Piece(msgf, 1, ",")
					ip$ = Left(IPAndPort, Instr(IPAndPort, ":")-1)
					port = Right(IPAndPort, Len(IPAndPort)-Instr(IPAndPort, ":"))
					multiplayer_list_AddServer(ip, port, Int(Piece(msgf, 2, ",")), False, False)
					added = True
				Wend
			EndIf
			
			CloseTCPStream(f)
		EndIf
		
		f = DownloadFile(Host+"Button.txt")
		If f <> 0 Then
			HOST_SERVER_BUTTON_TEXT = ReadLine(f)
			MULTIPLAYER_GET_LIMIT = Int(ReadLine(f))
			UPDATE_DOORS_DISTANCE = Int(ReadLine(f))
			If HOST_SERVER_BUTTON_TEXT = "" Then HOST_SERVER_BUTTON_TEXT = "CREATE LOBBY"
			CloseTCPStream(f)
		EndIf
	EndIf
	
	If added = False Then
		f = ReadFile("Data\servers")
		If f <> 0 Then
			added% = False
			If ReadLine(f) <> "SCP-CB Multiplayer Mod Servers" Then SeekFile(f,FileSize(randomstr)) 
			While Not Eof(f)
				msgf$ = ReadLine(f)
				IPAndPort$ = Piece(msgf, 1, ",")
				ip$ = Left(IPAndPort, Instr(IPAndPort, ":")-1)
				port = Right(IPAndPort, Len(IPAndPort)-Instr(IPAndPort, ":"))
				multiplayer_list_AddServer(ip, port, Int(Piece(msgf, 2, ",")), False, False)
				added = True
			Wend
			CloseFile f
		EndIf
	EndIf
		
	Return f
End Function
Function IPToDecimal(IP$)
	Return (Int(Piece(IP, 1, ".")) Shl 24)+(Int(Piece(IP, 2, ".")) Shl 16)+(Int(Piece(IP, 3, ".")) Shl 8)+Int(Piece(IP, 4, "."))
End Function

Function sqrvalue#(val#)
	Return Sqr(val*val)
End Function
Function onplayerdropgrenade(gunid)
	For a = 0 To MaxItemAmount-1
		If Inventory(a) <> Null Then
			Select gunid
				Case GUN_GRENADE
					If Inventory(a)\itemtemplate\tempname = "grenade" Then
						RemoveItem(Inventory(a))
						Exit
					EndIf
				Case GUN_GRENADEFLASHBANG
					If Inventory(a)\itemtemplate\tempname = "grenadeflashbang" Then
						RemoveItem(Inventory(a))
						Exit
					EndIf
				Case GUN_GRENADESMOKE
					If Inventory(a)\itemtemplate\tempname = "grenadesmoke" Then
						RemoveItem(Inventory(a))
						Exit
					EndIf
			End Select
		EndIf
	Next
End Function
Function CreateGrenade(grenadetype%, x#, y#, z#, pitch#, yaw#, shooter, throwType%)
	Local g.grenades = New grenades
	
	Select grenadetype
		Case GUN_GRENADE: g\obj = CopyEntity(GRENADE_OBJECT)
		Case GUN_GRENADEFLASHBANG: g\obj = CopyEntity(GRENADEFLASHBANG_OBJECT)
		Case GUN_GRENADESMOKE: g\obj = CopyEntity(GRENADESMOKE_OBJECT)
	End Select
	ScaleEntity g\obj, 0.012,0.012, 0.012
	PositionEntity g\obj, x,y,z
	RotateEntity g\obj, pitch, yaw, -20
	EntityType g\obj, HIT_GRENADE
	EntityRadius g\obj, 0.03
	;EntityColor g\obj, 3000, 3000, 3000
	g\speed = 0.12-(throwType*0.09)
	g\xspeed = 0.008
	g\angle = yaw
	g\shooter = shooter
	g\grenadetype% = grenadetype
	g\fallingspeed = 0.00
	g\rollcurr = -20
	
	If throwType = 1 Then RotateEntity g\obj, -30, yaw, -20
End Function
Function UpdateGrenades()
	;Local PrevX#, PrevY#, PrevZ#
	Local currsound$ = ""
	Local minusdist# = 0
	For g.grenades = Each grenades
		g\timer = g\timer + FPSfactor
		If g\timer > 259 Then
			If g\goteffect = 0 Then
				minusdist = 0
				Select g\grenadetype
					Case GUN_GRENADE:
						currsound$ = "SFX\Guns\Bazooka\Explosion.ogg"
						snd_real = LinePick(EntityX(g\obj),EntityY(g\obj),EntityZ(g\obj), 0, 10, 0)
						snd_real_player = LinePick(EntityX(Collider),EntityY(Collider),EntityZ(Collider), 0, 10, 0)
						If (Not snd_real) And snd_real_player Then currsound = "SFX\Guns\Bazooka\ExplosionOutside.ogg"
						If (Not snd_real) And (Not snd_real_player) Then currsound = "SFX\Guns\Bazooka\ExplosionOutside.ogg"
						For p.players = Each players
							If p\ID <> NetworkServer\MyID Then
								If EntityDistance(p\Pivot, g\obj) < 3 Then GiveDamage(p\ID, ((7*10)-EntityDistance(p\Pivot, g\obj)*5))
							EndIf
						Next
						If EntityVisible(Collider, g\obj) Then
							If EntityDistance(Collider, g\obj) < 3 Then
								If Not player_isdead() Then
									If multiplayer_IsASCP(MyPlayer\BreachType) Or NetworkServer\Breach Then
										If Not multiplayer_IsFullSync() Then MyPlayer\Health = MyPlayer\Health - Max(0, ((7*17)-(EntityDistance(Collider, g\obj)*20)))
										If Not multiplayer_IsASCP(MyPlayer\BreachType) Then Injuries = 1.01
										If MyPlayer\Health < 0 Then
											GodMode = False
											Kill("was killed by explosion by "+Player[g\shooter]\name)
										EndIf
									Else
										Injuries = Injuries + (5-EntityDistance(Collider, g\obj))
										If Injuries > 5 Then Kill("was killed by explosion by "+Player[g\shooter]\name)
									EndIf
								EndIf
							EndIf
						EndIf
						If NetworkServer\MainPlayer Then
							For n.NPCs = Each NPCs
								If n\IsDead = False Then
									If n\NPCtype = NPCtypeZombie Or n\NPCtype = NPCtype008 Then
										If EntityDistance(n\Collider, g\obj) < 5 Then
											n\hp = n\hp - (7*5)-(EntityDistance(n\Collider, g\obj)*5)
											If n\hp < 1 Then n\IsDead = True
										EndIf
									EndIf
									If n\NPCtype = NPCtypeOldMan Then
										If EntityDistance(n\Collider, g\obj) < 3 Then
											n\State = Rand(22000, 27000)
											PositionEntity n\Collider,0,500,0
										EndIf
									EndIf
								EndIf
							Next
						EndIf
						pvt = CreatePivot() 
						PositionEntity pvt, EntityX(g\obj),EntityY(g\obj)-0.05,EntityZ(g\obj)
						TurnEntity pvt, 90, 0, 0
						If EntityPick(pvt,10) <> 0 Then
							de.decals = CreateDecal(1, PickedX(), PickedY()+0.005, PickedZ(), 90, Rand(360), 0)
							de\size = Rnd(0.5,1) : EntityAlpha(de\obj, 1.0) : ScaleSprite de\obj, de\size, de\size
						EndIf
						FreeEntity pvt
						
						SetEmitter(g\obj,ParticleEffect[3], True, 1)
						SetEmitter(g\obj,ParticleEffect[4], True, 1)
						
						CameraShake = Max(0, 10-EntityDistance(g\obj, Collider))
					Case GUN_GRENADEFLASHBANG
						currsound$ = "SFX\Guns\Bazooka\Explosion.ogg"
						snd_real = LinePick(EntityX(g\obj),EntityY(g\obj),EntityZ(g\obj), 0, 10, 0)
						snd_real_player = LinePick(EntityX(Collider),EntityY(Collider),EntityZ(Collider), 0, 10, 0)
						If (Not snd_real) And snd_real_player Then currsound = "SFX\Guns\Bazooka\ExplosionOutside.ogg"
						If (Not snd_real) And (Not snd_real_player) Then currsound = "SFX\Guns\Bazooka\ExplosionOutside.ogg"
						
						If EntityVisible(g\obj, Camera) And EntityDistance(g\obj, Collider) < 10 Then
							LightFlash = 180.0/70.0+(EntityInView(g\obj,Camera)*180.0/70.0)
							LightFlash = LightFlash/Max(EntityDistance(g\obj, Collider)/5.0, 1.0)
							DeafTimer# = (LightFlash*70)-20
							DeafPlayer = True
							
							g\goteffect = g\grenadetype
						EndIf
						
						pvt = CreatePivot() 
						PositionEntity pvt, EntityX(g\obj),EntityY(g\obj)-0.05,EntityZ(g\obj)
						TurnEntity pvt, 90, 0, 0
						If EntityPick(pvt,10) <> 0 Then
							de.decals = CreateDecal(1, PickedX(), PickedY()+0.005, PickedZ(), 90, Rand(360), 0)
							de\size = Rnd(0.5,1) : EntityAlpha(de\obj, 1.0) : ScaleSprite de\obj, de\size, de\size
						EndIf
						FreeEntity pvt
						
						SetEmitter(g\obj,ParticleEffect[6], True, 1)
					Case GUN_GRENADESMOKE
						minusdist = 30
						currsound = "SFX\Guns\Grenade\SmokeExplosion.ogg"
						SetEmitter(g\obj,ParticleEffect[5], True, 1)
				End Select
				
				If g\goteffect = 0 Then
					If currsound <> "" Then
						pivo = CreatePivot()
						PositionEntity pivo, EntityX(g\obj),EntityY(g\obj),EntityZ(g\obj)
						ChannelPitch(Play3DSound(0, Camera, pivo, 60-minusdist, 0.5, currsound), Max(40000, 44100-(EntityDistance(Camera, pivo)*500)))
					EndIf
					FreeEntity g\obj
					Delete g
				EndIf
			Else
				Select g\goteffect
					Case GUN_GRENADEFLASHBANG
						If g\obj <> 0 Then
							FreeEntity g\obj
							g\obj = 0
						EndIf
						
						If LightFlash < 1 Then 
							Delete g
						Else
							ShouldPlay = 26
						EndIf
				End Select
			EndIf
		ElseIf g\obj <> 0 Then
			If g\speed > 0.01 Then
				If CountCollisions(g\obj) <> 0 Then
					pivo = CreatePivot()
					PositionEntity pivo, EntityX(g\obj),EntityY(g\obj),EntityZ(g\obj)
					Play3DSound(0, Camera, pivo, 10, 0.5, "SFX\Guns\Grenade\fall.ogg")
					If Abs(CollisionNY(g\obj, 1)) = 1 Then ; Jump
						RotateEntity g\obj, FlipAngle(EntityPitch(g\obj)), EntityYaw(g\obj), EntityRoll(g\obj)
						g\fallingspeed = 0
						;debuglog "Flip? "+
					Else
						RotateEntity g\obj, EntityPitch(g\obj), BounceWall(VectorYaw(CollisionNX(g\obj, 1), 0, -CollisionNZ(g\obj, 1)), EntityYaw(g\obj)), EntityRoll(g\obj)
					EndIf
					g\speed = g\speed*0.65 ; Reducing speed if collided
				Else
					MoveEntity g\obj, 0, 0, g\speed*FPSfactor
					;TranslateEntity g\obj, 0, -0.01*FPSfactor, 0
					
					TranslateEntity g\obj, 0, g\fallingspeed*FPSfactor, 0
					g\fallingspeed = Max(g\fallingspeed - 0.001*FPSfactor, -0.2)
					
					If EntityPitch(g\obj) < 90 Then 
						RotateEntity g\obj, WrapAngle(EntityPitch(g\obj)+0.8*FPSfactor), EntityYaw(g\obj), WrapAngle(EntityRoll(g\obj)+(g\speed*24)*FPSfactor)
					Else
						RotateEntity g\obj, EntityPitch(g\obj), EntityYaw(g\obj), WrapAngle(EntityRoll(g\obj)+(g\speed*24)*FPSfactor)
					EndIf
				EndIf
			Else
				;if CountCollisions(g\obj) <> 0 Then
				
					g\speed = Max(g\speed-0.0001*FPSfactor, 0)
					Local prevpitch# = EntityPitch(g\obj)
					RotateEntity(g\obj, 0, EntityYaw(g\obj), CurveValue(90, EntityRoll(g\obj), 15))
					MoveEntity g\obj, 0, 0, g\speed*FPSfactor
					
					;AlignToVector(g\obj, CollisionNX(g\obj, 1), CollisionNY(g\obj, 1), CollisionNZ(g\obj, 1), 2)
					RotateEntity(g\obj, WrapAngle(prevpitch+(g\speed*24)*FPSfactor), EntityYaw(g\obj), CurveValue(90, EntityRoll(g\obj), 15))

					TranslateEntity g\obj, 0, g\fallingspeed*FPSfactor, 0
					g\fallingspeed = Max(g\fallingspeed - 0.001*FPSfactor, -0.2)
				;EndIf
			EndIf
		EndIf
	Next
End Function
Function multiplayer_UpdatePlayerMicrohid(p.players)
	If p\MicroHIDShoot <> 0 Then
		If p\PlayerHead <> 0 Then
			EntityPickMode p\Hitbox, 0, False
			If p = myplayer Then EntityPickMode p\Pivot, 0
			;if p\obj <> 0 then EntityPickMode p\obj, 0, False
			Local pvt = CreatePivot()
			PositionEntity pvt, EntityX(p\PlayerHead, True), EntityY(p\PlayerHead, True), EntityZ(p\PlayerHead, True)
			RotateEntity pvt, p\bonepitch, p\yaw, 0
			If EntityPick(pvt, 30) <> 0 Then
				cp.Particles = CreateParticle(PickedX(),PickedY(),PickedZ(), 4, 0.5, 0, 10)
				If (Not multiplayer_IsAFriend(p\BreachType, MyPlayer\BreachType)) And MyPlayer\BreachType <> 0 Then
					If EntityDistance(cp\pvt, Collider) < 0.2 Then
						If Not multiplayer_IsFullSync() Then
							If multiplayer_IsASCP(MyPlayer\BreachType) Or NetworkServer\Breach Then
								MyPlayer\Health = MyPlayer\Health - 70*FPSfactor
								If MyPlayer\Health < 0 Then Kill("was killed by Micro-HID", True)
							Else
								Kill("was killed by Micro-HID", True)
							EndIf
						EndIf
					EndIf
				EndIf
				If EntityVisible(Camera, pvt) Then
					LightFlash = 0.05+(EntityInView(pvt,Camera)/50)
				EndIf
			EndIf
			EntityPickMode p\Hitbox, 2, False
			If p = myplayer Then EntityPickMode p\Pivot, 1
			;if p\obj <> 0 then EntityPickMode p\obj, 2, False
			FreeEntity pvt
		EndIf
	EndIf
End Function
Function UpdateRockets()
	Local shooter%
	For rc.rockets = Each rockets
		rc\RocketSpeed = CurveValue(ROCKET_SPEED, rc\rocketspeed, 3000)
		MoveEntity rc\Pivot, 0, 0, rc\rocketspeed*FPSfactor
		PositionEntity rc\rocketparticle\obj,EntityX(rc\obj), EntityY(rc\obj), EntityZ(rc\obj), True
		PositionEntity rc\obj, EntityX(rc\Pivot), EntityY(rc\pivot), EntityZ(rc\Pivot)
		RotateEntity rc\Pivot, WrapAngle(EntityPitch(rc\Pivot)), EntityYaw(rc\Pivot), EntityRoll(rc\Pivot)
		RotateEntity rc\obj, EntityPitch(rc\Pivot), EntityYaw(rc\obj), EntityRoll(rc\pivot)
		rc\timer = rc\timer + FPSfactor
		shooter = rc\shooter
		If shooter <> MyPlayer\ID Then
			If shooter > 0 Then EntityPickMode Player[shooter]\Hitbox, 0, False
		;	EntityPickmode Player[shooter]\obj, 0, False
		EndIf
		
		;if Player[shooter] <> Null Then EntityPickmode Player[shooter]\Pivot, 0
		
		If EntityPick(rc\pivot, 1) Or rc\timer > 400 Then
			Local currsound$ = "SFX\Guns\Bazooka\Explosion.ogg"
			Local snd_real = LinePick(EntityX(rc\pivot),EntityY(rc\pivot),EntityZ(rc\pivot), 0, 10, 0)
			Local snd_real_player = LinePick(EntityX(Collider),EntityY(Collider),EntityZ(Collider), 0, 10, 0)
			If (Not snd_real) And snd_real_player Then currsound = "SFX\Guns\Bazooka\ExplosionOutside.ogg"
			If (Not snd_real) And (Not snd_real_player) Then currsound = "SFX\Guns\Bazooka\ExplosionOutside.ogg"
			For p.players = Each players
				If p\ID <> NetworkServer\MyID Then
					If EntityDistance(p\Pivot, rc\pivot) < 3 Then GiveDamage(p\ID, ((7*10)-EntityDistance(p\Pivot, rc\pivot)*5))
				EndIf
			Next
			If EntityVisible(Collider, rc\obj) Then
				If EntityDistance(Collider, rc\pivot) < 3 Then
					If Not player_isdead() Then
						If multiplayer_IsASCP(MyPlayer\BreachType) Or NetworkServer\Breach Then
							If Not multiplayer_IsFullSync() Then MyPlayer\Health = MyPlayer\Health - Max(0, ((7*17)-(EntityDistance(Collider, rc\pivot)*20)))
							If Not multiplayer_IsASCP(MyPlayer\BreachType) Then Injuries = 1.01
							If MyPlayer\Health < 0 Then
								GodMode = False
								If shooter > 0 Then 
									Kill("was killed by explosion by "+Player[shooter]\name)
								Else
									Kill("was killed by explosion")
								EndIf
							EndIf
						Else
							Injuries = Injuries + (5-EntityDistance(Collider, rc\pivot))
							
							If Injuries > 5 Then
								If shooter > 0 Then 
									Kill("was killed by explosion by "+Player[shooter]\name)
								Else
									Kill("was killed by explosion")
								EndIf
							EndIf
						EndIf
					EndIf
				EndIf
			EndIf
			If NetworkServer\MainPlayer Then
				For n.NPCs = Each NPCs
					If n\IsDead = False Then
						If n\NPCtype = NPCtypeZombie Or n\NPCtype = NPCtype008 Then
							If EntityDistance(n\Collider, rc\pivot) < 5 Then
								n\hp = n\hp - (7*5)-(EntityDistance(n\Collider, rc\pivot)*5)
								If n\hp < 1 Then n\IsDead = True
							EndIf
						EndIf
						If n\NPCtype = NPCtypeOldMan Then
							If EntityDistance(n\Collider, rc\pivot) < 3 Then
								n\State = Rand(22000, 27000)
								PositionEntity n\Collider,0,500,0
							EndIf
						EndIf
					EndIf
				Next
			EndIf
			pvt = CreatePivot() 
			PositionEntity pvt, EntityX(rc\pivot),EntityY(rc\pivot)-0.05,EntityZ(rc\pivot)
			TurnEntity pvt, 90, 0, 0
			If EntityPick(pvt,10) <> 0 Then
				de.decals = CreateDecal(1, PickedX(), PickedY()+0.005, PickedZ(), 90, Rand(360), 0)
				de\size = Rnd(0.5,1) : EntityAlpha(de\obj, 1.0) : ScaleSprite de\obj, de\size, de\size
			EndIf
			FreeEntity pvt
			
			SetEmitter(rc\pivot,ParticleEffect[3], True)
			SetEmitter(rc\pivot,ParticleEffect[4], True)
			
			CameraShake = Max(0, 10-EntityDistance(rc\pivot, Collider))
			For snd.snd3d = Each snd3d
				If snd\soundchn = rc\channel Then
					StopChannel(snd\soundchn)
					Delete snd
					Exit
				EndIf
			Next
			rc\channel = 0
			
			Local pivo = CreatePivot()
			PositionEntity pivo, EntityX(rc\pivot),EntityY(rc\pivot),EntityZ(rc\pivot)
			ChannelPitch(Play3DSound(0, Camera, pivo, 60, 0.5, currsound), Max(40000, 44100-(EntityDistance(Camera, pivo)*500)))
			RemoveEmitter(rc\rocketparticle)
			FreeEntity rc\obj
			FreeEntity rc\pivot
			
			Delete rc
		EndIf
		If shooter <> MyPlayer\ID Then
			If shooter > 0 Then EntityPickMode Player[shooter]\Hitbox, 2, False
		;	EntityPickmode Player[shooter]\obj, 2, False
		EndIf
		;if Player[shooter] <> Null then EntityPickmode Player[shooter]\Pivot, 1
	Next
	UpdateBullets()
	UpdateGrenades()
End Function
Function CreateRocket.rockets(rocketspeed#, x#, y#, z#, constpitch#, constyaw#, shooter%)
	rc.rockets = New rockets
	rc\obj = CopyEntity(ROCKET_OBJECT)
	ScaleEntity rc\obj, 0.01, 0.01, 0.01
	rc\constpitch = constpitch
	rc\constyaw = constyaw
	rc\pivot = CreatePivot()
	;rc\rocketspeed = rocketspeed
	PositionEntity rc\pivot, x, y+0.2, z
	RotateEntity rc\pivot, constpitch, constyaw, 0
	RotateEntity rc\obj, constpitch, constyaw, 0
	ResetEntity rc\pivot
	MoveEntity rc\pivot, 0.1, -0.17, 0
	rc\shooter = shooter
	rc\channel = Play3DSound(0, Camera, rc\Pivot, 20, 1, "SFX\Guns\Bazooka\shoot.ogg")
	
	rc\rocketparticle = CreateEmitter(EntityX(rc\obj, True), EntityY(rc\obj, True), EntityZ(rc\obj, True), 0)
	TurnEntity(rc\rocketparticle\Obj, 90, 0, 0, True)
	rc\rocketparticle\RandAngle = 5
	rc\rocketparticle\Speed = 0
	rc\rocketparticle\maxsize = 0.1
	rc\rocketparticle\SizeChange = 0.01
	rc\rocketparticle\gravity = 0
	rc\rocketparticle\minimage = 6
	rc\rocketparticle\maximage = 6
	InitRoomForEmitter(rc\rocketparticle)
	
	Return rc
End Function
Type bullets
	Field obj
	Field bulletspeed#, timer#, shooter
	Field pivot
End Type
Function UpdateBullets()
	For b.bullets = Each bullets
		b\bulletspeed = CurveValue(BULLET_SPEED, b\bulletspeed, 5)
		MoveEntity b\obj, 0, -0.01*FPSfactor, b\bulletspeed*FPSfactor
		ScaleEntity b\obj, 0.0003, 0.0003, (b\bulletspeed/2)*FPSfactor
		EntityColor b\obj, 255*(b\bulletspeed*10), 255*(b\bulletspeed*10), 0
		b\timer = b\timer+FPSfactor
		
		If b\timer > 400 Or EntityDistance(Collider, b\obj) > HideDistance*2 Then
			FreeEntity b\obj
			Delete b
		EndIf
	Next
End Function
Function CreateBullet.bullets(shooter%, bulletspeed#, x#, y#, z#, constpitch#, constyaw#)
	b.bullets = New bullets
	b\obj = CreateCube()
	ScaleEntity b\obj, 0.0003, 0.0003, 0
	EntityColor b\obj, 9999, 9999, 0
	EntityShininess b\obj, 100000
	PositionEntity b\obj, x, y, z
	RotateEntity b\obj, 0, constyaw, 0
	RotateEntity b\obj, constpitch, constyaw, 0
	MoveEntity b\obj, 0, 0, -0.5
	
	If shooter > 0 Then
		If shooter = MyPlayer\ID Then EntityPickMode Player[shooter]\Pivot, 0
		EntityPickMode Player[shooter]\Hitbox, 0, False
	EndIf
	
	b\bulletspeed = bulletspeed
	b\shooter = shooter
	
	Local pv = CreatePivot()
	PositionEntity pv, x, y, z
	RotateEntity pv, constpitch, constyaw, 0
	MoveEntity pv, 0, 0, -0.5
	
	If EntityPick(pv, 1000) <> 0 Then
		Local temp = 0, pickent = PickedEntity()
		For pl.players = Each players
			If pickent = pl\Obj Or pickent = pl\hitbox Or pickent = pl\Pivot Then
				;if pl\IsABot <> 0 Then
				;	GiveDamage(pl\ID, 15+Rnd(1,3))
				;EndIf
				temp = 1
				If udp_GetStream() Then
					If NetworkServer\MainPlayer Then
						udp_WriteByte(M_SHOOT)
						udp_WriteByte(b\shooter)
						udp_SendMessage(pl\ID)
						CreateSound("SFX\General\BulletHit.ogg", EntityX(pl\obj), EntityY(pl\obj), EntityZ(pl\obj), 10, 1.0)
					EndIf
				EndIf
				Exit
			EndIf
		Next
		If temp <> 1 Then
			For n.NPCs = Each NPCs
				If pickent = n\Collider Or pickent = n\obj Then
					If udp_GetStream() Then CreateSound("SFX\General\BulletHit.ogg", EntityX(pickent), EntityY(pickent), EntityZ(pickent), 10, 1.0)
					
					If n\NPCtype = NPCtype008 Or n\NPCtype = NPCtypeZombie Then
						n\hp = n\hp - 20+Rnd(1,3)
						If n\hp < 1 Then n\IsDead = True
					EndIf
					;plIDs = GetBotByID(n\ID)
					;if plIDs <> 0 Then
					;	GiveDamage(plIDs, 20+Rnd(1,3))
					;EndIf
					temp = 1
					Exit
				EndIf
			Next
		EndIf
		If temp = 1 Then
			p.Particles = CreateParticle(PickedX(),PickedY(),PickedZ(), 5, 0.06, 0.2, 80)
			p\speed = 0.001
			p\SizeChange = 0.003
			p\A = 0.8
			p\Achange = -0.02
		Else
			p.Particles = CreateParticle(PickedX(),PickedY(),PickedZ(), 0, 0.03, 0, 80)
			p\speed = 0.001
			p\SizeChange = 0.003
			p\A = 0.8
			p\Achange = -0.01
			RotateEntity p\pvt, EntityPitch(b\obj)-180, EntityYaw(b\obj),0
			If give = True
				bullet = Rand(170,190)
				bullet2 = Rand(0, 10)
			EndIf
			For i = 0 To Rand(2,3)
				p.Particles = CreateParticle(PickedX(),PickedY(),PickedZ(), 0, 0.006, 0.003, 80)
				p\speed = 0.02
				p\A = 0.8
				p\Achange = -0.01
				RotateEntity p\pvt, EntityPitch(b\obj), EntityYaw(b\obj),0	
			Next
			
			;bullet hole decal
			de.Decals = CreateDecal(Rand(13,14), PickedX(),PickedY(),PickedZ(), 0,0,0)
			AlignToVector de\obj,-PickedNX(),-PickedNY(),-PickedNZ(),3
			MoveEntity de\obj, 0,0,-0.001
			EntityFX de\obj, 1
			de\lifetime = 70*20
			EntityBlend de\obj, 2
			de\Size = Rnd(0.028,0.034)
			ScaleSprite de\obj, de\Size, de\Size
			PlaySound2(Gunshot3SFX, Camera, pickent, 5, Rnd(0.8,1.0))
			EntityParent de\obj, pickent
		EndIf
	EndIf
	FreeEntity pv
	
	If shooter > 0 Then
		If shooter = MyPlayer\ID Then EntityPickMode Player[shooter]\Pivot, 1
		EntityPickMode Player[shooter]\Hitbox, 2, False
	EndIf
	
	EntityPickMode b\obj, 0
	
	If Not EnableBullets Then
		FreeEntity b\obj
		Delete b
	EndIf
	
	Return b
End Function


Function IsANotRemovedEvent%(e.Events)
	Select e\eventconst
		Case e_room1162,e_room1123,e_room012
			Return True
		Case e_exit1
			Return True
		Case e_Gatea
			Return True
		Case e_room3storage
			Return True
		Case e_breach
			Return True
		Case e_pocketdimension
			Return True
		Case e_room860
			Return True
		Case e_dimension1499
			Return True
		Case e_914
			Return True
		Case e_room2tesla
			Return True
		Case e_room2tunnel
			Return True
	End Select
	Return False
End Function
Function IsABlockedEvent%(e.Events)
	If e\StaticEvent = True Then Return True
	Select e\eventconst
		Case e_checkpoint
			Return True
		Case e_room1123
			Return True
		Case e_1048a
			Return True
		Case e_room2tesla
			Return True
		Case e_breach
			Return True
		Case e_room2tesla
			Return True
		Case e_pocketdimension
			Return True
		Case e_dimension1499
			Return True
		Case e_room860
			Return True
		Case e_914
			Return True
	End Select
	Return False
End Function
Function IsABlockedRoom%(r.Rooms)
	Select Lower(r\RoomTemplate\Name)
		Case "dimension1499"
			Return True
	End Select
	Return False
End Function
Function multiplayer_SendDoor(d.Doors, itemtosend.Items, code$ = "")
	udp_ByteStreamWriteChar(M_DOOR)
	udp_ByteStreamWriteShort(d\ID)
	udp_ByteStreamWriteChar(d\open)
	udp_ByteStreamWriteChar(d\locked)
	If itemtosend <> Null Then 
		udp_ByteStreamWriteShort(itemtosend\ID)
	Else
		udp_ByteStreamWriteShort(0)
	EndIf
	udp_ByteStreamWriteLine(code)
	udp_setmicrobyte(M_DOOR)
End Function
Function multiplayer_WriteSound(soundhandle%, X# = 0, Y# = 0, Z# = 0, Distance# = 10.0, volume# = 1.0)
	If Not udp_GetStream() Then Return
	snd.Sound = Object.Sound(soundhandle)
	If snd <> Null Then multiplayer_WriteTempSound(snd\name, X, Y, Z, Distance, volume)
End Function
Function multiplayer_WriteTempSound(soundhandle$, X# = 0, Y# = 0, Z# = 0, Distance# = 10.0, volume# = 1.0)
	If Not udp_GetStream() Then Return
	udp_ByteStreamWriteChar M_SOUND
	udp_ByteStreamWriteLine soundhandle
	udp_ByteStreamWriteFloat Distance
	udp_ByteStreamWriteFloat volume
	udp_setmicrobyte(M_SOUND)
End Function
Function multiplayer_WriteDecal(de.Decals, editdecal = 1, send = True)
	If Not udp_GetStream() Then Return
	udp_ByteStreamWriteChar M_DECAL 
	udp_ByteStreamWriteChar de\ID
	udp_ByteStreamWriteFloat de\x 
	udp_ByteStreamWriteFloat de\y 
	udp_ByteStreamWriteFloat de\z 
	udp_ByteStreamWriteFloat de\yaw 
	udp_ByteStreamWriteFloat de\roll 
	udp_ByteStreamWriteFloat de\pitch 
	udp_ByteStreamWriteFloat de\SizeChange 
	udp_ByteStreamWriteFloat de\Size
	udp_ByteStreamWriteFloat de\MaxSize 
	udp_ByteStreamWriteFloat de\AlphaChange 
	udp_ByteStreamWriteFloat de\Alpha
	udp_ByteStreamWriteFloat de\Timer 
	udp_SetMicroByte(M_DECAL)
End Function
Function multiplayer_UpdateGUI(Ex% = True)
	If udp_GetStream() Then
		updatequery()
		If MainMenuOpen = False Then
			If HudENABLED Then
				If Ex Then
					If NetworkServer\Breach Then
						seconds = ((b_br\BreachTimer+999-MilliSecs()) / 1000) Mod 60
						minutes = (b_br\BreachTimer+999-MilliSecs()) / (1000*60) Mod 60
						If ((b_br\BreachTimer-MilliSecs())-b_br\CurrentBreachTime) < 0 Then
							secstr$ = seconds
							If seconds < 10 Then secstr = "0"+seconds
							AASetFont Font3
							Color 255,255,255
							If MilliSecs() < b_br\BreachTimer Then 
								AAText GraphicWidth-60, 20, minutes+":"+secstr
							Else
								AAText GraphicWidth-60, 20, "0:00"
							EndIf
						EndIf
						If Not ConsoleOpen Then
							x% = 310
							y% = GraphicHeight - 95
							
							width = (504/2)*MenuScale
							height = 20*MenuScale
							If MyPlayer\BreachType <> 0 Then
								Color 255,255,255
								Rect (x, y, width, height, False)
								Color 180,0,0
								;Rect (x+2*MenuScale, y+2*MenuScale, (((MyPlayer\Health*0.1)/2)-2)*MenuScale, height-2*Menuscale)
								RenderProgressBar (x+2*MenuScale, y+2*MenuScale, width-4*MenuScale, height-4*MenuScale, multiplayer_breach_GetMaxHP(MyPlayer\BreachType), Int(MyPlayer\Health))
								Color 255,255,255
								
								AASetFont FontSL
								AAText(x+1*MenuScale, y+1*MenuScale, Int(MyPlayer\Health))
								If multiplayer_IsASCP(MyPlayer\BreachType) Or multiplayer_breach_IsA035(MyPlayer\BreachType) Then
									y = y + 40
									
									height = 10*MenuScale
									Color 255,255,255
									Rect (x, y, width, height, False)
									Color 0,0,180
									RenderProgressBar (x+2*MenuScale, y+2*MenuScale, width-4*MenuScale, height-4*MenuScale, SCP\HitIntervalMax, SCP\HitInterval-MilliSecs())
									Color 255,255,255
								EndIf
								
								SetTypeColor(MyPlayer\BreachType)
								AASetFont Font1
								AAText(x+width+20*MenuScale, y, GetTypeName(MyPlayer\BreachType))
							EndIf
						EndIf
					EndIf
				EndIf
			EndIf
		EndIf
		If NetworkServer\haveVoice = True Then
			Local offset
			For i = 1 To NetworkServer\Maxplayers
				If Player[i] <> Null Then
					If Player[i]\Talking = 1 Then
						If (MainMenuOpen And (Not NetworkServer\Breach)) Or (Player[i]\BreachType = 0 And MyPlayer\BreachType = 0) Or Player[i]\Announcement Then
							Color 255,255,255
							AASetFont FontSL
							Color Player[i]\tagr, Player[i]\tagg, Player[i]\tagb
							DisableRedirectAccess = True
							AAText 40*MenuScale, (200+offset)*MenuScale, Player[i]\name+"["+i+"]"
							DisableRedirectAccess = False
							Color 255,255,255
							AASetFont Font1
							DrawImage mpimg\VoicePL, 25*MenuScale,(200+offset)*MenuScale
							offset = offset+30
						EndIf
					EndIf
				EndIf
			Next
		EndIf
		If Ex Then
			For p.Players = Each players
				If p\ID <> NetworkServer\MyID Then multiplayer_RenderPlayer2D(p)
			Next
		EndIf
		If KeyHit(KEY_CHAT) And (Not ConsoleOpen) And (Not NetworkServer\chatOpen) And TAB_MENU_STATE < 2 Then
			FlushKeys()
			NetworkServer\chatOpen = (Not NetworkServer\chatOpen)
		EndIf
		draws_Render()
		texts_Render()
		multiplayer_RenderVoice()
		multiplayer_RenderChat()
		
		If HUDenabled Then
			If Ex Then
				If b_br\YouAreTimer > 0 And b_br\WonTimer < 1 And ((b_br\BreachTimer-MilliSecs())-b_br\CurrentBreachTime) < 1 Then
					AASetFont Font1
					Color 255,255,255
					AAText((GraphicWidth / 2)+1, (GraphicHeight * 0.1) + 1, "You are", True, False, Min(b_br\YouAreTimer / 2, 255)/255.0)
					AAText((GraphicWidth / 2), (GraphicHeight * 0.1) , "You are", True, False, Min(b_br\YouAreTimer / 2, 255)/255.0)
					AASetFont Font2
					SetTypeColor(MyPlayer\BreachType)
					AAText((GraphicWidth / 2)+1, (GraphicHeight * 0.13) + 1, GetTypeName(MyPlayer\BreachType), True, False, Min(b_br\YouAreTimer / 2, 255)/255.0)
					AAText((GraphicWidth / 2), (GraphicHeight * 0.13) , GetTypeName(MyPlayer\BreachType), True, False, Min(b_br\YouAreTimer / 2, 255)/255.0)
					Color 255,255,255
					SetTypeText((GraphicWidth / 2)+10, (GraphicHeight * 0.2))
					;if multiplayer_IsASCP(MyPlayer\BreachType) Then AAText((GraphicWidth / 2)+10, (GraphicHeight * 0.24), "Waiting for the game to start ("+minutes+":"+secstr+")" , True, False, Min(b_br\YouAreTimer / 2, 255)/255.0)
				End If
				If b_br\WonTimer > 0 And b_br\WonCategory <> "NULL" Then
					Color 255,255,255
					AASetFont Font2
					AAText((GraphicWidth / 2), (GraphicHeight * 0.08), "THE ROUND HAS FINISHED", True, False, Min(b_br\WonTimer / 2, 255)/255.0)
					AAText((GraphicWidth / 2)+1, (GraphicHeight * 0.08)+1, "THE ROUND HAS FINISHED", True, False, Min(b_br\WonTimer / 2, 255)/255.0)
					AASetFont Font2
					Color b_br\wonr, b_br\wong, b_br\wonb
					If b_br\WonCategory <> "" Then
						AAText((GraphicWidth / 2)+1, (GraphicHeight * 0.17) + 1, b_br\WonCategory+" WON", True, False, Min(b_br\WonTimer / 2, 255)/255.0)
						AAText((GraphicWidth / 2), (GraphicHeight * 0.17) , b_br\WonCategory+" WON", True, False, Min(b_br\WonTimer / 2, 255)/255.0)
					EndIf
				End If
				If NetworkServer\Breach And ((b_br\BreachTimer-MilliSecs())-b_br\CurrentBreachTime) > 0 And MyPlayer\BreachType = MODEL_WAIT Then
				
					Local time = (b_br\BreachTimer-MilliSecs())-b_br\CurrentBreachTime
					seconds = ((time+999) / 1000) Mod 60
					minutes = ((time+999) / (1000*60)) Mod 60
					secstr$ = seconds
					If seconds < 10 Then secstr = "0"+seconds
					
					Color 200,200,200
					AASetFont ConsoleFont
					FormatText((GraphicWidth / 2), (GraphicHeight * 0.08), "%w%CONNECTED %g%"+NetworkServer\PlayersCount+" %w%PLAYERS", True, False)
					;AASetFont Font1
					AAText((GraphicWidth / 2), (GraphicHeight * 0.12), "Remaining before the start of the game - "+minutes+":"+secstr, True, False)
					leftstr$ = ""
					If NetworkServer\PlayersCount < 4 Then leftStr$ = "%w%Requires %r%"+Str(4-NetworkServer\PlayersCount)+" %w%more players to start the game"
					If leftstr <> "" Then FormatText((GraphicWidth / 2), (GraphicHeight * 0.15), leftstr, True, False)
					
				EndIf
			EndIf
		EndIf
		
		If Not udp_respond() Then
			AASetFont Font1
			Color 255, 0, 0
			AAText(20*MenuScale, GraphicHeight-20*MenuScale, "Server not responding...")
			Color 255,255,255
		EndIf
		
		If TAB_MENU_STATE <> 0 Then
			Local plcount = 0
			MenuOpen = False
			InvOpen = False
								
			AASetFont Font1			
			Select Int(TAB_MENU_STATE)
				Case 3
					xtab = GraphicWidth/2-175*MenuScale
					ytab = GraphicHeight/2-200*MenuScale
					DrawFrame(xtab, ytab, 350*MenuScale, 360*MenuScale)
					DrawFrame(xtab, ytab-30*MenuScale, 350*MenuScale, 30*MenuScale)
					
					If DrawButton(xtab + 300 * MenuScale, ytab - 23 * MenuScale, 30*MenuScale, 20*MenuScale, "<<", False) Then 
						currplayer = Null
						BlockGuns = True
						MouseHit1 = False
					EndIf
					If currplayer = Null Then
						TAB_MENU_STATE = 1
					Else
						BS_CSteamID_Set(BS_SteamID_Dynamic, currplayer\steamid, BS_EUniverse_Public, BS_EAccountType_Individual)
						
						DisableRedirectAccess = True
						Color currplayer\tagr, currplayer\tagg, currplayer\tagb
						AAText(xtab + 10 * MenuScale, ytab - 23 * MenuScale, currplayer\name)
						Color 255,255,255
						DisableRedirectAccess = False
						currplayer\volume = (SlideBar(xtab + 10 * MenuScale, ytab + 30 * MenuScale, 135*MenuScale, currplayer\volume*100.0, False)/100.0)
						AAText xtab + 10 * MenuScale, ytab + 5 * MenuScale, "Player volume: "+Int(currplayer\volume*100)
						
						If BS_ISteamFriends_GetFriendRelationship(BS_SteamFriends(), BS_SteamID_Dynamic) = 3 Then
							DrawImage(mpimg\Friend, (xtab + AAStringWidth(currplayer\name)) + 15 * MenuScale, ytab - 19 * MenuScale)
						EndIf
						
						If currplayer\steamid <> 0 Then
							Color 255,255,255
							Rect(xtab + 170 * MenuScale, ytab + 30 * MenuScale, 170*MenuScale, 170*MenuScale, False)
							
							If DrawButton(xtab + 180 * MenuScale, ytab + 40 * MenuScale, 150*MenuScale, 25*MenuScale, "Open profile", False) Then BS_ISteamFriends_ActivateGameOverlayToUser(BS_SteamFriends(), "steamid", BS_SteamID_Dynamic)
							
							If BS_ISteamFriends_GetFriendRelationship(BS_SteamFriends(), BS_SteamID_Dynamic) > 0 Then
								DrawButton(xtab + 180 * MenuScale, ytab + 70 * MenuScale, 150*MenuScale, 25*MenuScale, "Add to friends", False,False,True,-1,SELECTED_SERVERS)
							Else
								If DrawButton(xtab + 180 * MenuScale, ytab + 70 * MenuScale, 150*MenuScale, 25*MenuScale, "Add to friends", False) Then BS_ISteamFriends_ActivateGameOverlayToUser(BS_SteamFriends(), "friendadd", BS_SteamID_Dynamic)
							EndIf
							
							If BS_ISteamFriends_RequestUserInformation(BS_SteamFriends(), BS_SteamID_Dynamic, False) = 0 Then
								If currplayer\RequestSteamData = 0 Then
									image = BS_ISteamFriends_GetLargeFriendAvatar(BS_SteamFriends(), BS_SteamID_Dynamic)

									If Not(image = -1 Or image = 0) Then
										Local WidthBank = CreateBank(4)
										Local HeightBank = CreateBank(4)
										
										BS_SteamUtils_GetImageSize(BS_SteamUtils(), image, WidthBank, HeightBank)
										
										Local IWidth = PeekInt(WidthBank, 0)
										Local IHeight = PeekInt(HeightBank, 0)
										
										FreeBank WidthBank
										FreeBank HeightBank
										
										If IWidth > 0 And IHeight > 0 Then 
											bank = CreateBank(IWidth*IHeight*4)
											FormattedBank = CreateBank(IWidth*IHeight*3)
											CurrBytes = 0
											
											BS_SteamUtils_GetImageRGBA(BS_SteamUtils(), image, bank, BankSize(bank))

											For i = 0 To BankSize(bank)-1 Step 4
												PokeByte(FormattedBank, CurrBytes, PeekByte(bank, i+2))
												CurrBytes=CurrBytes+1
												PokeByte(FormattedBank, CurrBytes, PeekByte(bank, i+1))
												CurrBytes=CurrBytes+1
												PokeByte(FormattedBank, CurrBytes, PeekByte(bank, i))
												CurrBytes=CurrBytes+1
											Next

											fibuffer% = FI_ConvertFromRawBits%(FormattedBank,IWidth,IHeight,IWidth*3,24,$FF0000,$00FF00,$0000FF,True)
											FI_Save(13,fibuffer,"Temp\avatar"+currplayer\steamid+".png",0)
											FI_Unload(fibuffer%)
											
											FreeBank bank
											FreeBank FormattedBank
											
											currplayer\RequestSteamData = LoadImage("Temp\avatar"+currplayer\steamid+".png")
											ResizeImage(currplayer\RequestSteamData, ImageWidth(currplayer\RequestSteamData)*0.3*MenuScale, ImageHeight(currplayer\RequestSteamData)*0.3*MenuScale)
										EndIf
									EndIf
								Else
									Color 255,255,255
									Rect(xtab + 178 * MenuScale, ytab + 108 * MenuScale, ImageWidth(currplayer\RequestSteamData)+4*MenuScale, ImageHeight(currplayer\RequestSteamData)+4*MenuScale)
									DrawBlock(currplayer\RequestSteamData, xtab + 180 * MenuScale, ytab + 110 * MenuScale)
									
									AASetFont Font1

									;AAText (xtab + 170 * MenuScale), ytab + 170 * MenuScale, "[STEAM_1:"+((currplayer\steamid Mod 2) <> 0)+":"+int((currplayer\steamid-((currplayer\steamid Mod 2) <> 0))/2)+"]"
									AAText (xtab + 178 * MenuScale), (ytab + 118 * MenuScale)+ImageHeight(currplayer\RequestSteamData), "[U:1:"+currplayer\steamid+"]"
								EndIf
							EndIf
						EndIf
						;if BS_ISteamFriends_RequestUserInformation(BS_SteamFriends(), BS_SteamID_Dynamic, false) = 0 then
							
						;EndIf
						
						;Admin
						If multiplayer_IsFullSync() And IsPlayerAdmin() Then
							AASetFont Font1
							Color 255,255,255
							If DrawButton(xtab + 10 * MenuScale, ytab + 60 * MenuScale, 140/1.2*MenuScale, 25/1.14*MenuScale, "Ban IP", FontServers) Then
								If PreviousClickedButton = 1 Then 
									ExecuteConsoleCommand("ban "+currplayer\ID, False)
									PreviousClickedButton = 0
								Else
									PreviousClickedButton = 1
								EndIf
							EndIf
							If DrawButton(xtab + 10 * MenuScale, ytab + 90/1.14 * MenuScale, 140/1.2*MenuScale, 25/1.14*MenuScale, "Ban steam", FontServers) Then
								If PreviousClickedButton = 2 Then 
									ExecuteConsoleCommand("bansteam "+currplayer\ID, False)
									PreviousClickedButton = 0
								Else
									PreviousClickedButton = 2
								EndIf
							EndIf
							If DrawButton(xtab + 10 * MenuScale, ytab + 120/1.14 * MenuScale, 140/1.2*MenuScale, 25/1.14*MenuScale, "Kick", FontServers) Then
								If PreviousClickedButton = 3 Then 
									ExecuteConsoleCommand("kick "+currplayer\ID, False)
									PreviousClickedButton = 0
								Else
									PreviousClickedButton = 3
								EndIf
							EndIf
							If DrawButton(xtab + 10 * MenuScale, ytab + 150/1.14 * MenuScale, 140/1.2*MenuScale, 25/1.14*MenuScale, "Mute\Unmute", FontServers) Then
								If PreviousClickedButton = 4 Then 
									ExecuteConsoleCommand("mute "+currplayer\ID, False)
									PreviousClickedButton = 0
								Else
									PreviousClickedButton = 4
								EndIf
							EndIf
							If DrawButton(xtab + 10 * MenuScale, ytab + 180/1.14 * MenuScale, 140/1.2*MenuScale, 25/1.14*MenuScale, "Teleport to", FontServers) Then
								If PreviousClickedButton = 5 Then 
									ExecuteConsoleCommand("tpto "+currplayer\ID, False)
									PreviousClickedButton = 0
								Else
									PreviousClickedButton = 5
								EndIf
							EndIf
							If DrawButton(xtab + 10 * MenuScale, ytab + 210/1.14 * MenuScale, 140/1.2*MenuScale, 25/1.14*MenuScale, "Teleport to me", FontServers) Then
								If PreviousClickedButton = 6 Then 
									ExecuteConsoleCommand("tpme "+currplayer\ID, False)
									PreviousClickedButton = 0
								Else
									PreviousClickedButton = 6
								EndIf
							EndIf
							
							AASetFont Font1
							
							If PreviousClickedButton <> 0 Then AAText(xtab + 135 * MenuScale, (ytab + (60+(30/1.14*(PreviousClickedButton-1))) * MenuScale)-5*MenuScale, "Sure?")
							
							AAText(xtab + 10 * MenuScale, ytab + 260 * MenuScale, "Give role:")
							TAB_MENU_ROLE_INPUT = InputBox(xtab + 110 * MenuScale, ytab + 260 * MenuScale, 180*MenuScale, 20*MenuScale, TAB_MENU_ROLE_INPUT, 8)
							If DrawButton(xtab + 290 * MenuScale, ytab + 260 * MenuScale, 50*MenuScale, 20*MenuScale, "Give", False) Then ExecuteConsoleCommand("giverole "+currplayer\ID+" "+TAB_MENU_ROLE_INPUT, False)
							AAText(xtab + 10 * MenuScale, ytab + 300 * MenuScale, "Give item:")
							TAB_MENU_ITEM_INPUT = InputBox(xtab + 110 * MenuScale, ytab + 300 * MenuScale, 180*MenuScale, 20*MenuScale, TAB_MENU_ITEM_INPUT, 9)
							If DrawButton(xtab + 290 * MenuScale, ytab + 300 * MenuScale, 50*MenuScale, 20*MenuScale, "Give", False) Then ExecuteConsoleCommand("giveitem "+currplayer\ID+" "+TAB_MENU_ITEM_INPUT, False)
							
							If DrawButton(xtab + 85 * MenuScale, ytab + 340 * MenuScale, 200*MenuScale, 20*MenuScale, "ADMIN PANEL", False) Then TAB_MENU_STATE = 4
						EndIf
					EndIf
				Case 4
					xtab = GraphicWidth/2-175*MenuScale
					ytab = GraphicHeight/2-200*MenuScale
					DrawFrame(xtab, ytab, 350*MenuScale, 360*MenuScale)
					DrawFrame(xtab, ytab-30*MenuScale, 350*MenuScale, 30*MenuScale)
					
					If DrawButton(xtab + 250 * MenuScale, ytab + 4 * MenuScale, 30*MenuScale, 20*MenuScale, "<<", False) Then 
						TAB_MENU_STATE = 3
					EndIf
					
					AASetFont FontServers
					
					If DrawButton(xtab + 10 * MenuScale, ytab + (20/1.14) * MenuScale, (140/1.2)*MenuScale, (25/1.14)*MenuScale, "Start match", FontServers) Then ExecuteConsoleCommand("startmatch", False)
					
					If DrawButton(xtab + 10 * MenuScale, ytab + (50/1.14) * MenuScale, (140/1.2)*MenuScale, (25/1.14)*MenuScale, "Restart server", FontServers) Then ExecuteConsoleCommand("restart", False)
					
					If DrawButton(xtab + 10 * MenuScale, ytab + (80/1.14) * MenuScale, (140/1.2)*MenuScale, (25/1.14)*MenuScale, "Spawn Chaos", FontServers) Then ExecuteConsoleCommand("spawnchaos", False)
					
					If DrawButton(xtab + 10 * MenuScale, ytab + (110/1.14) * MenuScale, (140/1.2)*MenuScale, (25/1.14)*MenuScale, "Spawn MTF", FontServers) Then ExecuteConsoleCommand("spawnmtf", False)
					
					If DrawButton(xtab + 10 * MenuScale, ytab + (140/1.14) * MenuScale, (140/1.2)*MenuScale, (25/1.14)*MenuScale, "Use warheads", FontServers) Then ExecuteConsoleCommand("activatewarheads", False)
					
					If DrawButton(xtab + 10 * MenuScale, ytab + (170/1.14) * MenuScale, (140/1.2)*MenuScale, (25/1.14)*MenuScale, "Explode warheads", FontServers) Then ExecuteConsoleCommand("forcewarheads", False)
					
					If DrawButton(xtab + 10 * MenuScale, ytab + (200/1.14) * MenuScale, (140/1.2)*MenuScale, (25/1.14)*MenuScale, "Cancel warheads", FontServers) Then ExecuteConsoleCommand("cancelwarheads", False)
					
					If DrawButton(xtab + 10 * MenuScale, ytab + (230/1.14) * MenuScale, (140/1.2)*MenuScale, (25/1.14)*MenuScale, "Intercom", FontServers) Then ExecuteConsoleCommand("shouldannounc", False)
					
					AASetFont Font1
					
					;
					AAText(xtab + 10 * MenuScale, ytab + 230 * MenuScale, "Lobby time (min):") 
					TAB_MENU_LOBBY_INPUT = InputBox(xtab + 160 * MenuScale, ytab + 230 * MenuScale, 100*MenuScale, 20*MenuScale, TAB_MENU_LOBBY_INPUT, 10)
					If DrawButton(xtab + 290 * MenuScale, ytab + 230 * MenuScale, 50*MenuScale, 20*MenuScale, "Set", False) Then ExecuteConsoleCommand("lob "+TAB_MENU_LOBBY_INPUT, False)
					;
					AAText(xtab + 10 * MenuScale, ytab + 260 * MenuScale, "MTF Tickets:")
					TAB_MENU_ROLE_INPUT = InputBox(xtab + 160 * MenuScale, ytab + 260 * MenuScale, 100*MenuScale, 20*MenuScale, TAB_MENU_ROLE_INPUT, 8)
					If DrawButton(xtab + 290 * MenuScale, ytab + 260 * MenuScale, 50*MenuScale, 20*MenuScale, "Set", False) Then ExecuteConsoleCommand("setmtftickets "+TAB_MENU_ROLE_INPUT, False)
					;
					AAText(xtab + 10 * MenuScale, ytab + 290 * MenuScale, "Chaos Tickets:")
					TAB_MENU_ITEM_INPUT = InputBox(xtab + 160 * MenuScale, ytab + 290 * MenuScale, 100*MenuScale, 20*MenuScale, TAB_MENU_ITEM_INPUT, 9)
					If DrawButton(xtab + 290 * MenuScale, ytab + 290 * MenuScale, 50*MenuScale, 20*MenuScale, "Set", False) Then ExecuteConsoleCommand("setchaostickets "+TAB_MENU_ITEM_INPUT, False)
				Default
					PreviousClickedButton = 0
					xtab = GraphicWidth/2-175*MenuScale
					ytab = GraphicHeight/2-200*MenuScale
					DrawFrame(xtab, ytab, 350*MenuScale, 330*MenuScale)
					DrawFrame(xtab, ytab-30*MenuScale, 350*MenuScale, 30*MenuScale)
					
					DrawFrame(xtab, ytab + 30 * MenuScale, 330*MenuScale, 2 * MenuScale)
													
					DrawFrame(xtab + 260 * MenuScale, ytab,2*MenuScale,30*MenuScale)
					FormatText(xtab + 11 * MenuScale, ytab - 23 * MenuScale, NetworkServer\Servername)
					AAText(xtab + 10 * MenuScale, ytab + 5 * MenuScale, "Nickname")
					AAText(xtab + 270 * MenuScale, ytab + 5 * MenuScale, "Ping")
					
					Local ppages[MAX_PLAYER_LIST_PAGES], ppcount = 0
					For p.players = Each players
						If p\name <> "" Then
							For a = 0 To MAX_PLAYER_LIST_PAGES-1
								If plcount/MAX_PLAYERS_IN_PAGE = a Then 
									p\currpage = a
									ppages[a] = True
									Exit
								EndIf
							Next
							plcount = plcount + 1
						EndIf
					Next
			
					Local cc
					For p.players = Each players
						If p\name <> "" Then
							If SELECTED_P_PAGE = p\currpage Then

								DrawFrame(xtab, ytab + (30+cc) * MenuScale, 350*MenuScale, 30 * MenuScale)
								
								If p\ID = NetworkServer\MyID Then 
									p\ping = ServerPing
									p\name = Nickname
								Else
									If BS_ISteamFriends_GetFriendRelationship(BS_SteamFriends(), udp_FillSteam(p\steamid)) = 3 Then
										DrawImage(mpimg\Friend, xtab + 250 * MenuScale, (ytab+7) + (30+cc) * MenuScale)
									EndIf
								EndIf
								Color 255,255,255
								DisableRedirectAccess = True
								Color p\tagr, p\tagg, p\tagb
								AAText(xtab + 10 * MenuScale, (ytab+5) + (30+cc) * MenuScale, "["+p\ID+"] "+p\name)
								DisableRedirectAccess = False
								Color 255,255,255
								AAText(xtab + 270 * MenuScale, (ytab+5) + (30+cc) * MenuScale, p\Ping)
								If DrawButton(xtab + 305 * MenuScale, (ytab+4) + (30+cc) * MenuScale, 20*MenuScale, 20*MenuScale, "+", False) Then 
									TAB_MENU_STATE = 3
									currplayer = p
									
									;BS_CSteamID_Set(BS_SteamID_Dynamic, p\steamid, BS_EUniverse_Public, BS_EAccountType_Individual)
									;BS_ISteamFriends_RequestUserInformation(BS_SteamFriends(), BS_SteamID_Dynamic, false)
								EndIf
								cc = cc + 30
							EndIf
						EndIf
					Next
					
					For i = 0 To MAX_PLAYER_LIST_PAGES-1
						If ppages[i] = True Then
							If SELECTED_P_PAGE = i Then
								DrawButton(xtab + (10+(30*i)) * MenuScale,  ytab + 280 * MenuScale, 20*MenuScale, 20*MenuScale, i+1,False, False, True, -1, -1, SELECTED_P_PAGE)
							Else
								If DrawButton(xtab + (10+(30*i)) * MenuScale,  ytab + 280 * MenuScale, 20*MenuScale, 20*MenuScale,i+1,False, False, True, -1, -1, -1) Then SELECTED_P_PAGE = i
							EndIf
						EndIf
					Next
					
					Color 255,255,255
					AAText(xtab + 10 * MenuScale, ytab + 310*MenuScale, NetworkServer\PlayersCount+" / "+NetworkServer\Maxplayers)
			End Select
			If Fullscreen And TAB_MENU_STATE > 1 Then DrawImage CursorIMG, ScaledMouseX(),ScaledMouseY()
		EndIf
	EndIf
	If (CurrentWorkshopDownloadItems <> 0 Or CurrentWorkshopUploadingItems <> 0) And (Not have_querys()) Then
		txt$ = "Downloading workshop items... ("+Int(CurrentWorkshopDownloadItems+CurrentWorkshopUploadingItems)+" item left)"
		Color 255,255,255
		AASetFont Font1
		AAText GraphicWidth-AAStringWidth(txt)-30*MenuScale, GraphicHeight-45, txt
		loading_frame = PlayAnimImage(mpimg\load_gif, GraphicWidth-AAStringWidth(txt)-70*MenuScale, GraphicHeight-50, 0.05*FPSfactor, loading_frame, 11)
	EndIf
End Function
Function multiplayer_RenderVoice()
	If voice\VoiceInstall <> 0 And NetworkServer\haveVoice = True And (Not have_querys()) And (Not (CurrentWorkshopDownloadItems <> 0 Or CurrentWorkshopUploadingItems <> 0)) Then
		;Local a# = voice\UpdateFactor/60.0
		;if voice\updatefactor = 0 And voice\talking Then a = 1.0
		;If voice\Talking Then
		
		Local sx# = GraphicWidth-60.0*MenuScale
		Local sy# = GraphicHeight-(70.0+(20.0*MainMenuOpen))*MenuScale
		
		Local x# = GraphicWidth-60.0*MenuScale
		Local y# = GraphicHeight-(70.0+(20.0*MainMenuOpen))*MenuScale
		
		DrawFrameBlack(x, y, 50*MenuScale, 50*MenuScale)
		
		x = x + 2*MenuScale
		y = y + 50*MenuScale
		
		Color 241,24,79
		RenderProgressBarY(x, y, (50*MenuScale)-(2*MenuScale), (50*MenuScale)-(2*MenuScale), 0.2, Max(voice\SmoothOffset, 0))
		
		If voice\Talking Then 
			voice\SmoothOffset = CurveValue(voice_get_offset()-0.74, voice\SmoothOffset, 7)
			DrawImage(mpimg\VoiceME,sx, sy)
		Else
			voice\SmoothOffset = CurveValue(0, voice\SmoothOffset, 7)
			DrawImage(mpimg\VoiceNO,sx, sy)
		EndIf
			;DrawImage(mpimg\VoiceME,GraphicWidth-65*MenuScale, GraphicHeight-70*MenuScale) 
			;Color 90*a,90*a,90*a
			;Rect(GraphicWidth-55*MenuScale, GraphicHeight-65*MenuScale, 30*MenuScale, 40*MenuScale)
		;Else
		;	DrawImage(mpimg\VoiceNO,GraphicWidth-65*MenuScale, GraphicHeight-90*MenuScale)
		;	;Color 90*a,90*a,90*a
		;	;Rect(GraphicWidth-55*MenuScale, GraphicHeight-65*MenuScale, 30*MenuScale, 40*MenuScale)
		;EndIf
	EndIf
End Function
Function multiplayer_UpdateSpectate()
	If Spectate\SpectatePlayer <> -1 Then
		If Not MenuOpen Then
			Local PrevSpectate% = Spectate\SpectatePlayer
			
			If (((Not MenuOpen) And (Not InvOpen) And (OtherOpen=Null) And (SelectedDoor = Null) And (ConsoleOpen = False) And (Not LockMouse) And (Using294 = False) And (SelectedScreen = Null) And 	EndingTimer=>0 And (Not NetworkServer\chatOpen) And (Not (TAB_MENU_STATE > 1))) Or MainMenuOpen) Then
				If MouseHit1 Then Spectate\SpectatePlayer = FindSpectatePlayer(False)
				If MouseHit2 Then Spectate\SpectatePlayer = FindSpectatePlayer(True)
			EndIf
			
			If PrevSpectate <> Spectate\SpectatePlayer Then
				If Player[Spectate\SpectatePlayer] <> Null Then 
					PositionEntity Collider, EntityX(Player[Spectate\SpectatePlayer]\obj),EntityY(Player[Spectate\SpectatePlayer]\obj)+1,EntityZ(Player[Spectate\SpectatePlayer]\obj)
					For r.Rooms = Each Rooms
						If r\ID = Player[Spectate\SpectatePlayer]\PlayerRoomID Then
							PlayerRoom = r
							Exit
						EndIf
					Next
					ResetEntity(Collider)
				EndIf
			EndIf
		EndIf
		
		If WearingNightVision Then
			CameraFogFar = StoredCameraFogFar
			WearingNightVision = 0
		EndIf
	
		MyPlayer\IsDead = 1
		For i = 0 To 5
			SCP1025state[i] = 0
		Next
		
		SuperMan = False
		SuperManTimer = 0
		
		DropSpeed = -0.1
		HeadDropSpeed = 0.0
		Shake = 0
		CurrSpeed = 0
		
		HeartBeatVolume = 0

		Shake = 0
		LightFlash = 0
		BlurTimer = 0
		
		FallTimer = 0
		
		GodMode = True
		NoClip = 0
		
		HideEntity Collider
		
		KillTimer = 0
		KillAnim = 0
		Injuries = 0
		Bloodloss = 0
		InvOpen = False
		
		WearingHazmat = 0
		WearingGasMask = 0
		WearingNightVision = 0
		Wearing714 = 0
		Wearing1499 = 0
		Using294 = 0
		HideEntity(GasMaskOverlay)
		HideEntity(NVOverlay)
		HideEntity(InfectOverlay)
		HideEntity(Dark)
		MyPlayer\BreachType = 0
		HideEntity Head

		If KeyHit(19) Then 
				Spectate\SpectateType = (Spectate\SpectateType+1) Mod 3
				
			If Player[Spectate\SpectatePlayer] <> Null Then
				If Player[Spectate\SpectatePlayer]\obj <> 0 Then
					If Spectate\SpectateType = 0 Then
						ShowEntity(Player[Spectate\SpectatePlayer]\obj)
					ElseIf Spectate\SpectateType = 1 Then
						HideEntity(Player[Spectate\SpectatePlayer]\obj)
					Else
						ShowEntity(Player[Spectate\SpectatePlayer]\obj)
					EndIf
				EndIf
			EndIf
			
			MP_InstructionsDone = True
			PutINIValue(OptionFile, "options", "mp instructions", True)
		EndIf
		
		If Spectate\SpectateType < 2 Then
			If Player[Spectate\SpectatePlayer] <> Null Then
				For r.Rooms = Each Rooms
					If r\ID = Player[Spectate\SpectatePlayer]\PlayerRoomID Then
						PlayerRoom = r
						Exit
					EndIf
				Next
				
				If Player[Spectate\SpectatePlayer]\BreachType = 0 Then
					Spectate\SpectatePlayer = FindSpectatePlayer(False)
					Spectate\SpectatePlayer = FindSpectatePlayer(True)
				EndIf
			Else
				Spectate\SpectatePlayer = FindSpectatePlayer(False)
				Spectate\SpectatePlayer = FindSpectatePlayer(True)
				Return
			EndIf
		EndIf

		CameraZoom(Camera, 1.0  / (Tan((2.0 * ATan(Tan((CurrentFOV) / 2.0) * (Float(RealGraphicWidth) / Float(RealGraphicHeight)))) / 2.0)) )

		Local MovedMouse%
		Local X#, Y#, Z#
		Select Spectate\SpectateType
			Case 0
				If Player[Spectate\SpectatePlayer] <> Null Then
					If Player[Spectate\SpectatePlayer]\obj <> 0 Then
						X = EntityX(Player[Spectate\SpectatePlayer]\obj)
						Y = EntityY(Player[Spectate\SpectatePlayer]\obj)
						Z = EntityZ(Player[Spectate\SpectatePlayer]\obj)
				
						If (((Not MenuOpen) And (Not InvOpen) And (OtherOpen=Null) And (SelectedDoor = Null) And (ConsoleOpen = False) And (Not LockMouse) And (Using294 = False) And (SelectedScreen = Null) And 	EndingTimer=>0 And (Not NetworkServer\chatOpen) And (Not (TAB_MENU_STATE > 1))) Or MainMenuOpen) Then
							mxspd# = MouseXSpeed()*0.2*(MouseSens+0.6)
							myspd# = MouseYSpeed()*0.2*(MouseSens+0.6)

							MoveMouse GraphicWidth/2,GraphicHeight/2
							MovedMouse = True
						EndIf
						Spectate\targetpitch = Spectate\targetpitch + myspd
						Spectate\targetpitch = ClampValue(Spectate\targetpitch, -40,40)
						Spectate\targetyaw = Spectate\targetyaw - mxspd	

						Spectate\cam_pitch = Spectate\cam_pitch + (Spectate\targetpitch - Spectate\cam_pitch)
						Spectate\cam_yaw = Spectate\cam_yaw + (Spectate\targetyaw - Spectate\cam_yaw)
						
						RotateEntity Camera,Spectate\cam_pitch,Spectate\cam_yaw,0
						RotateEntity Collider,0,Spectate\cam_yaw,0
						X# =  X + (-1 * Sin(-EntityYaw(Camera)))
						Z# =  Z + (-1 * Cos(-EntityYaw(Camera)))
						Y# =  Y + (-1 * Tan(-EntityPitch(Camera)))
						PositionEntity(Camera, X, Y+1, Z)
						PositionEntity Collider, X,Y+1,Z
						ResetEntity Collider
						
					EndIf
				EndIf
			Case 1
				If Player[Spectate\SpectatePlayer] <> Null Then
					If Player[Spectate\SpectatePlayer]\obj <> 0 Then
						X = EntityX(Player[Spectate\SpectatePlayer]\obj)
						Y = EntityY(Player[Spectate\SpectatePlayer]\obj)
						Z = EntityZ(Player[Spectate\SpectatePlayer]\obj)
						
						Local bt.breachtypes = GetBreachType(Player[Spectate\SpectatePlayer]\BreachType)
						
						If Player[Spectate\SpectatePlayer]\BreachType <> MODEL_173 Then
							PositionEntity(Camera, EntityX(GetPlayerHead(Spectate\SpectatePlayer), True), EntityY(GetPlayerHead(Spectate\SpectatePlayer), True), EntityZ(GetPlayerHead(Spectate\SpectatePlayer), True))
							If bt\FixRotate <> 0 Then
								RotateEntity(Camera, Player[Spectate\SpectatePlayer]\prevpitch, EntityYaw(Player[Spectate\SpectatePlayer]\Pivot), Player[Spectate\SpectatePlayer]\CurrentGunRoll)
							Else
								RotateEntity(Camera, Player[Spectate\SpectatePlayer]\prevpitch, EntityYaw(Player[Spectate\SpectatePlayer]\Pivot), FlipValue(Player[Spectate\SpectatePlayer]\CurrentGunRoll))
							EndIf
						Else
							PositionEntity(Camera, EntityX(Player[Spectate\SpectatePlayer]\obj), EntityY(Player[Spectate\SpectatePlayer]\obj)+1*Player[Spectate\SpectatePlayer]\size, EntityZ(Player[Spectate\SpectatePlayer]\obj))
							RotateEntity(Camera, Player[Spectate\SpectatePlayer]\BonePitch, EntityYaw(Player[Spectate\SpectatePlayer]\Pivot), 0)
						EndIf
						PositionEntity Collider, X,Y+1,Z
						ResetEntity Collider
						HideEntity(Player[Spectate\SpectatePlayer]\obj)
						
						If Player[Spectate\SpectatePlayer]\AttachObjects\NewYearHat <> 0 Then HideEntity Player[Spectate\SpectatePlayer]\AttachObjects\NewYearHat
						HideEntity Player[Spectate\SpectatePlayer]\AttachObjects\BallisticVest
						HideEntity Player[Spectate\SpectatePlayer]\AttachObjects\NVGobj
						HideEntity Player[Spectate\SpectatePlayer]\AttachObjects\GasMaskObj
						
						For i = GUN_USP To GUN_GRENADESMOKE
							HideEntity Player[Spectate\SpectatePlayer]\AttachObjects\Gun[i]
						Next
					EndIf
				EndIf
			Case 2
				If (((Not MenuOpen) And (Not InvOpen) And (OtherOpen=Null) And (SelectedDoor = Null) And (ConsoleOpen = False) And (Not LockMouse) And (Using294 = False) And (SelectedScreen = Null) And 	EndingTimer=>0 And (Not NetworkServer\chatOpen) And (Not (TAB_MENU_STATE > 1))) Or MainMenuOpen) Then
					mxspd# = MouseXSpeed()*0.2*(MouseSens+0.6)
					myspd# = MouseYSpeed()*0.2*(MouseSens+0.6)

					MoveMouse GraphicWidth/2,GraphicHeight/2
					
					If KeyBuffer(KEY_DOWN) Then MoveEntity Collider, 0, 0, -(2.0*0.018*(Max(KeyBuffer(KEY_SPRINT)*2.5, 1)))*FPSfactor; : MyPlayer\MoveDir = MyPlayer\MoveDir + 2
					If KeyBuffer(KEY_UP) Then MoveEntity Collider, 0, 0, (2.0*0.018*(Max(KeyBuffer(KEY_SPRINT)*2.5, 1)))*FPSfactor; : MyPlayer\MoveDir = MyPlayer\MoveDir + 1
					
					If KeyBuffer(KEY_LEFT) Then MoveEntity Collider, -(2.0*0.018*(Max(KeyBuffer(KEY_SPRINT)*2.5, 1)))*FPSfactor, 0, 0; : MyPlayer\MoveDir = MyPlayer\MoveDir + 4
					If KeyBuffer(KEY_RIGHT) Then MoveEntity Collider, (2.0*0.018*(Max(KeyBuffer(KEY_SPRINT)*2.5, 1)))*FPSfactor, 0, 0; : MyPlayer\MoveDir = MyPlayer\MoveDir + 8
					MovedMouse = True
				EndIf
				
				spectate\targetpitch = spectate\targetpitch + myspd
				spectate\targetpitch = ClampValue(spectate\targetpitch, -80,80)
				spectate\targetyaw = spectate\targetyaw - mxspd	

				spectate\cam_pitch = spectate\cam_pitch + (spectate\targetpitch - spectate\cam_pitch)
				spectate\cam_yaw = spectate\cam_yaw + (spectate\targetyaw - spectate\cam_yaw)
				
				RotateEntity Camera,spectate\cam_pitch,spectate\cam_yaw,0
				RotateEntity collider,spectate\cam_pitch,spectate\cam_yaw,0
				
				PositionEntity(Camera, EntityX(Collider), EntityY(Collider), EntityZ(Collider))
				ResetEntity Collider
		End Select
		If Not MovedMouse Then MouseXSpeed() : MouseYSpeed() : MouseZSpeed()
	EndIf
End Function

Function multiplayer_UpdateSelfSCPs()
    For p.players = Each Players
        Select p\BreachType
            Case MODEL_966
                If (Not multiplayer_IsAFriend(MyPlayer\BreachType,p\BreachType)) Then
                    If EntityDistance(Collider, p\Pivot) < 2.5 Then
							StaminaEffect = 3.0 ;insomnia
							StaminaEffectTimer = 30
                        	MyPlayer\insomniaupdate = MilliSecs()+1000
                    End If
                End If
        End Select
    Next
    
    If MyPlayer\insomniaupdate < MilliSecs() And MyPlayer\insomniaupdate <> 0 Then
		Stamina = 1
        StaminaEffect = 1.0
        MyPlayer\insomniaupdate = 0
		StaminaEffectTimer = 0
    End If
End Function

Function multiplayer_UpdateSCPs()
	
	multiplayer_UpdateSelfSCPs()
	
	Local BT.BreachTypes = GetBreachType(MyPlayer\BreachType)
	If Not BT\AllowUpdate Then Return 1
			
	Select MyPlayer\BreachType
		Case MODEL_096
			Crouch = False
			Injuries = 0
			GodMode = True
			Shake = 0
			Injuries = 0
			BlinkTimer = 0
			move = True
			
			;TempCamera% = CreateCamera()
			;CameraRange(TempCamera, 0.05, 35)
			;CameraProjMode(TempCamera, 0)
			;CameraViewport TempCamera,0,0,GraphicWidth,GraphicHeight
	
			For p.players = Each players
				If p\ID <> NetworkServer\MyID And (Not multiplayer_IsAFriend(MyPlayer\BreachType,p\BreachType)) Then
					If (p\BlinkTimer < - 16 Or p\BlinkTimer > - 6) Then
						If p\showobjects Then
							;if not SCP\look[p\ID] Then
								;PositionEntity TempCamera, EntityX(p\PlayerHead, True),EntityY(p\PlayerHead, True),EntityZ(p\PlayerHead, True)
								;RotateEntity TempCamera, EntityPitch(p\PlayerHead, True),EntityYaw(p\PlayerHead, True),0
								
								If EntityInView(Camera, GetPlayerCamera(p\ID)) And EntityInView(p\PlayerHead, Camera) Then
									If EntityVisible(MyHitbox, p\hitbox) Then
										SCP\look[p\ID] = True
										SCP\ragedupdate = MilliSecs()+15000
										
										If (Not ChannelPlaying(SCP\ragestarted)) And SCP\rageStart = 0.0 And SCP\Raged = False Then
											SCP\ragestarted = PlaySound_Strict(LoadTempSound("SFX\Music\096Angered.ogg"))
											multiplayer_WriteTempSound("SFX\Music\096Angered.ogg")
											SCP\rageStart = 70*27
										EndIf
									EndIf
								EndIf
							;endif
						EndIf
					EndIf
				EndIf
			Next
			
			;FreeEntity TempCamera
			
			SCP\rageStart = Max(0, SCP\rageStart-FPSfactor)
			If SCP\rageStart = 0.0 Then
				SCP\Raged = True
				If SCP\ragedupdate < MilliSecs() Then 
					SCP\Raged = False
					For i = 1 To NetworkServer\Maxplayers
						SCP\look[i] = False
					Next
				EndIf
			Else
				SCP\Raged = False
				SCP\ragedupdate = MilliSecs()+15000
			EndIf
			
			If MouseHit1 And SCP\Raged And SCP\HitInterval < MilliSecs() Then
				If CanInteract() Then
					EntityPick(Camera, BT\AttackRadius)
					For p.players = Each players
						If SCP\look[p\id] = True Then
							If (p\Pivot = PickedEntity() Or p\obj = PickedEntity() Or p\Hitbox = PickedEntity()) And (Not p\IsDead) And (Not multiplayer_IsAFriend(MyPlayer\BreachType,p\BreachType)) Then
								udp_ByteStreamWriteChar(M_KILL2)
								udp_ByteStreamWriteChar(p\ID)
								udp_setmicrobyte(M_KILL2)
								
								PlaySound2(LoadTempSound("SFX\Character\D9341\Damage5.ogg"), Camera, Collider)
								multiplayer_WriteTempSound("SFX\Character\D9341\Damage5.ogg")
								
								SCP\HitIntervalMax = BT\HitInterval
								SCP\HitInterval = MilliSecs()+SCP\HitIntervalMax
								
								JumpState = 0.05
								If KeyDown(KEY_UP) Then LastZSpeed = CurrSpeed
								If KeyDown(KEY_DOWN) Then LastZSpeed = -CurrSpeed
								If KeyDown(KEY_LEFT) Then LastXSpeed = -CurrSpeed
								If KeyDown(KEY_RIGHT) Then LastXSpeed = CurrSpeed
								SCP\look[i] = False
								Exit
							EndIf
						EndIf
					Next
				EndIf
			EndIf
			Return 1
		Case MODEL_173
			Crouch = False
			Injuries = 0
			GodMode = True
			Shake = 0
			SuperMan = True
			Injuries = 0
			BlinkTimer = 0
			move = True

			For p.players = Each players
				If p\ID <> NetworkServer\MyID And (Not multiplayer_IsAFriend(MyPlayer\BreachType,p\BreachType)) Then
					If (p\BlinkTimer < - 16 Or p\BlinkTimer > - 6) Then
						If p\showobjects Then
							If EntityInView(MyHitbox, GetPlayerCamera(p\ID)) Then
								If EntityVisible(MyHitbox, p\Hitbox) Then move = False
							EndIf
						EndIf
					EndIf
					If move = False Then Exit
				EndIf
			Next
			If MouseHit1 And move = True And SCP\HitInterval < MilliSecs() Then
				If CanInteract() Then
					EntityPick(Camera, BT\AttackRadius)
					For p.players = Each players
						If (p\Pivot = PickedEntity() Or p\obj = PickedEntity() Or p\Hitbox = PickedEntity()) And (Not p\IsDead) And (Not multiplayer_IsAFriend(MyPlayer\BreachType,p\BreachType)) Then
							udp_ByteStreamWriteChar(M_KILL2)
							udp_ByteStreamWriteChar(p\ID)
							udp_setmicrobyte(M_KILL2)
							PlaySound2(LoadTempSound("SFX\SCP\173\NeckSnap"+Rand(1,2)+".ogg"), Camera, Collider)
							multiplayer_WriteTempSound("SFX\SCP\173\NeckSnap"+Rand(1,2)+".ogg", EntityX(Collider),EntityY(Collider),EntityZ(Collider))
							
							SCP\HitIntervalMax = BT\HitInterval
							SCP\HitInterval = MilliSecs()+SCP\HitIntervalMax
							Exit
						EndIf
					Next
				EndIf
			EndIf
			
			If move = False Then
				If savedangle = -361 Then SavedAngle# = EntityYaw(Collider)
			Else
				savedangle = -361
			EndIf
			
			Return move
		Default
			GodMode = Not BT\DisableGodmode
			If BT\DisableCrouch Then Crouch = False
			If BT\DisableInjuries Then Injuries = False
			
			If CanInteract() Then
				If BT\Sound <> "" Then
					If MouseHit2 And MilliSecs() > SCP\Sound Then
						SCP\Sound = MilliSecs()+BT\SoundInterval
						If BT\RandomSounds > 0 Then
							RenderSound$ = Replace(BT\Sound, ".", "0.")
							If FileSize(RenderSound) = 0 Then 
								RenderSound$ = Replace(BT\Sound, ".", Rand(1, BT\RandomSounds)+".")
							Else
								RenderSound$ = Replace(BT\Sound, ".", Rand(0, BT\RandomSounds)+".")
							EndIf
							
							PlaySound_Strict(LoadTempSound(RenderSound))
							multiplayer_WriteTempSound(RenderSound)
						Else
							PlaySound_Strict(LoadTempSound(BT\Sound))
							multiplayer_WriteTempSound(BT\Sound)
						EndIf
					EndIf
				EndIf
				If BT\AllowAttack > 0 Then
					If MouseHit1 And SCP\HitInterval < MilliSecs() Then
						EntityPick(Camera, BT\AttackRadius)
						For p.players = Each players
							If (p\Pivot = PickedEntity() Or p\obj = PickedEntity() Or p\Hitbox = PickedEntity()) And (Not p\IsDead) And (Not multiplayer_IsAFriend(MyPlayer\BreachType,p\BreachType)) Then
								If bt\attacksound Then
									PlaySound_Strict(LoadTempSound("SFX\General\Slash"+Rand(1,2)+".ogg"))
									multiplayer_WriteTempSound("SFX\General\Slash"+Rand(1,2)+".ogg")
								EndIf
								If BT\AllowAttack = 1 Then
									udp_ByteStreamWriteChar(M_SHOOT)
								Else
									udp_ByteStreamWriteChar(M_KILL2)
								EndIf
								udp_ByteStreamWriteChar(p\ID)
								
								udp_SetMicroByte(M_SHOOT)
								udp_SetMicroByte(M_KILL2)
								
								SCP\HitIntervalMax = BT\HitInterval
								SCP\HitInterval = MilliSecs()+SCP\HitIntervalMax
								
								PLAYER_MOVE_TIMED = PLAYER_BITE
								
								Exit
							EndIf
						Next
					EndIf
				EndIf
			EndIf
			Return 1
	End Select
End Function
Function multiplayer_ManipulatePlayerBones(p.players)
	If p\obj = 0 Or (Not p\ShowObjects) Then Return
	p\prevpitch = curveangle(p\bonepitch, p\prevpitch, 7)
	
	Local yaw# = EntityYaw(p\Pivot)
	Local pitch# = p\prevpitch
	Local headpitch# = p\prevpitch
	Local pitchoffset# = 0
	
	p\gunroll = 0
	If ReadBool(p\wearingdir, 4) Then p\gunroll = -25
	If ReadBool(p\wearingdir, 7) Then p\gunroll = 25
	
	p\CurrentGunRoll = CurveValue(p\GunRoll, p\CurrentGunRoll, 7)
	
	If p\WearingHazmat <> 0 Then 
		If p\UsedWeapon <> 0 And p\PlayerSpine <> 0 Then RotateEntity p\PlayerSpine,EntityPitch(p\PlayerSpine)+pitch,EntityYaw(p\PlayerSpine, True),EntityRoll(p\PlayerSpine, True)+p\CurrentGunRoll,True
		RotateEntity p\PlayerHead,headpitch,yaw,-90,True
		Return
	EndIf

	Select p\BreachType
		Case MODEL_966
			p\PlayerHeadOffset = -90
		Case MODEL_939
			;headpitch = -headpitch
			p\PlayerHeadOffset = EntityRoll(p\PlayerHead, True)
		Case MODEL_860
			headpitch = headpitch-90
			p\PlayerHeadOffset = 90
		Case NTF_MODEL
			;headpitch = -headpitch
			If p\UsedWeapon <> 0 Then RotateEntity p\PlayerSpine, p\CurrentGunRoll, EntityYaw(p\PlayerSpine, True), pitch,True
			RotateEntity p\PlayerHead,0,wrapangle(yaw-90),headpitch,True
			Return
		Default
			Local BT.BreachTypes = GetBreachType(p\BreachType)
			If BT\RollOffset <> 0 Then p\PlayerHeadOffset = BT\RollOffset
			yaw = yaw - BT\RoleYawOffset
			If BT\HeadPitchFlip Then headpitch = -headpitch
	End Select
	
	If p\UsedWeapon <> 0 And p\PlayerSpine <> 0 Then RotateEntity p\PlayerSpine,EntityPitch(p\PlayerSpine)+pitch,EntityYaw(p\PlayerSpine, True),EntityRoll(p\PlayerSpine, True)+p\CurrentGunRoll,True
	RotateEntity p\PlayerHead,headpitch,yaw,GetPlayerHeadOffset(p\ID),True
End Function
Function multiplayer_DisconnectPlayer(p.players)
	If p = Null Then Return
	DebugLog "Disconnect "+p\ID
	For snd.snd3d = Each snd3d
		If snd\entity = p\Pivot Then
			If Not snd\fmod Then
				StopChannel(snd\soundchn)
				If snd\tempentity Then FreeEntity snd\entity
				Delete snd
			Else
				FSOUND_StopSound(snd\soundchn)
				FSOUND_Stream_Stop(snd\sound)
				FSOUND_Stream_Close(snd\sound)
				Delete snd
			EndIf
		EndIf
	Next
	For r.records = Each records
		If r\sender = p\ID Then
			If r\effect = True Then alSourceRemoveEffect(r\sound, 0)
			alFreeSource(r\sound)
			If FileType(r\filename) <> 0 Then DeleteFile(r\filename)
			Delete r
		EndIf
	Next
	
	For g.grenades = Each grenades
		If g\shooter = p\ID Then
			FreeEntity g\obj
			Delete g
		EndIf
	Next
	
	For rc.rockets = Each rockets
		If rc\shooter = p\ID Then
			FreeEntity rc\obj
			FreeEntity rc\pivot
			Delete rc
		EndIf
	Next
			
	If p\ID <> NetworkServer\MyID Then
		If p\soundchn <> 0 Then StopChannel(p\soundchn)
		
		If NetworkServer\MainPlayer Then
			For it.Items = Each Items
				If it\picker = p\ID And it\picker <> 0 Then 
					PlayerDropItem(it)
				EndIf
			Next
		EndIf
		If p\nameent <> Null Then GG_Delete_Text_Block(p\nameent\cPv)
		If p\patreonent <> Null Then GG_Delete_Text_Block(p\patreonent\cPv)
		GG_Delete_Text_Block(p\voiceent\cPv)
		GG_Delete_Text_Block(p\afkent\cPv)
		
		FreePlayerObjects(p)

		If p\obj <> 0 Then
			FreeEntity(p\obj)
			p\obj = 0
		EndIf
		If p\Pivot <> 0 Then 
			FreeEntity(p\Pivot)
			p\Pivot = 0
		EndIf
		If NetworkServer\SteamStream And NetworkServer\Hosted Then
			BS_ISteamNetworking_CloseP2PSessionWithUser(BS_SteamNetworking(), udp_FillSteam(BS_CSteamID_GetAccountID(p\IP)))
			BS_CSteamID_Destroy(p\IP)
		EndIf
	EndIf
	FreeBank p\voicebank
	opus_remove_decoder(p\OpusDecoder)
	
	If p\RequestSteamData <> 0 Then FreeImage(p\RequestSteamData)
	If p\ShouldSendShoot <> Null Then RemoveByteStream(p\ShouldSendShoot)
	Delete p
End Function
Function multiplayer_CreatePlayer.players(i)
	If i < 1 Or i > MAX_PLAYERS Then Return
	If Player[i] <> Null Then Return Player[i]
	Player[i] = New players
	Player[i]\Timeout = MilliSecs()+15000
	Player[i]\ID = i
	Player[i]\voicebank = CreateBank(0)
	Player[i]\volume = mainplayersvolume
	Player[i]\BreachType = (MODEL_WAIT*NetworkServer\Breach)
	Player[i]\monitor_width = GraphicWidth
	Player[i]\monitor_height = GraphicHeight
	Player[i]\OpusDecoder = opus_get_new_decoder()
	Player[i]\tagr = 255
	Player[i]\tagg = 255
	Player[i]\tagb = 255
	Player[i]\size = 1
	If i = NetworkServer\MyID Then
		Player[i]\monitor_width = GraphicWidth
		Player[i]\monitor_height = GraphicHeight
		Player[i]\steamid = CurrentSteamID
		If PATRON_COMPILE Then Player[i]\patron = True
		
		MyPlayer = Player[i]
	EndIf
	Return Player[i]
End Function
Function multiplayer_MasterServerSet(IP$, Port$)
	IPnet = IP
	Portnet = Port
End Function
Function multiplayer_MasterServerGetIP$()
	Return IPnet
End Function
Function multiplayer_MasterServerGetPort$()
	Return Portnet
End Function
Function multiplayer_ConnectTo(IP$, port, password$ = "", SteamConnect% = False, interval = 5000)
	For bs.BlacklistedServers = Each BlacklistedServers
		If bs\IP = IP Then
			AddErrorLog("This server is blacklisted", 255, 0, 0)
			Return 0
		EndIf
	Next

	If Not SteamConnect Then
		For sc.Servers = Each Servers
			If sc\Timed Then multiplayer_list_DeleteServer(sc)
		Next
		sc.servers = multiplayer_list_AddServer(IP, port, 0, True, False)
		sc\timed = True
	EndIf
	
	MouseHit1 = False
	AddErrorLog("Joining...")
	If Not SteamConnect Then
		If CountHostIPs(IP) = 0 Then
			AddErrorLog("Failed to establish connection", 255, 0, 0)
			Return 0
		EndIf
		port = Max(Min(65535, port), 80)
		UDP_SetStream(udp_network, 0, 0, 0, False)
		UDP_SetStream(udp_network, CreateUDPStream(), HostIP(1), port)
	Else
		UDP_SetStream(udp_network, 0, 0, 0, False)
		UDP_SetStream(udp_network, CreateUDPStream(), Int(IP))
		DebugLog "Set stream: "+Int(IP)
		NetworkServer\SteamStream = True
	EndIf
	If (Not udp_GetConnection()) Then
		AddErrorLog("Couldn't connect", 255, 0, 0)
		MainMenuTab = 1
		Return 0
	Else
		Nickname = Left(Nickname, 24)
		multiplayer_SetServerTime(MilliSecs()+interval)
		
		If Not SteamConnect Then
			RequestSteamTicket()
			NetworkServer\ConnectTimeout = MilliSecs()+1500
			NetworkServer\ConnectTries = 0
		Else
			udp_WriteByte M_CONNECT
			udp_WriteByte 0
			udp_WriteLine Nickname
			udp_WriteLine MULTIPLAYER_VERSION
			udp_WriteLine password
			udp_WriteByte CLIENT_VERSION
			udp_WriteShort GraphicWidth
			udp_WriteShort GraphicHeight
			udp_WriteByte PATRON_COMPILE
			udp_WriteInt CurrentSteamID
			;udp_WriteBytes CurrentSessionTicketData, 0, BankSize(CurrentSessionTicketData)
			udp_SendMessage()
			
			NetworkServer\ConnectTimeout = 0
			NetworkServer\ConnectTries = 0
		EndIf
	EndIf
	Return 0
End Function

Global chatScrollDragging, chatScroll#, chatMouseMem#
Global chatoffsety#
Global chatoffsetx#
Global chatwidth#, chatheight#
Global chatedittype%
Global chattypes%
Function multiplayer_UpdateChatMessages()
	AASetFont Font1
	
	Local LockButton% = False
	Local CurrentText$
	
	If NetworkServer\chatOpen Then
		Select chatedittype
			Case 1
				chatoffsetx = (ScaledMouseX()-chatwidth)-10*MenuScale
				chatoffsety = ScaledMouseY()-35*MenuScale
				;if MouseHit1 Then ChatEditType = 0
			Case 2
				chatwidth = Min(Max(80*MenuScale, ScaledMouseX()-chatoffsetx-10*MenuScale), (GraphicWidth-chatoffsetx)-30*MenuScale)
				chatheight = Max(80*MenuScale, ScaledMouseY()-chatoffsety)
				If MouseHit1 Then 
					chatedittype = 0
					LockButton = True
					SaveMultiplayerOptions()
				EndIf
		End Select
	Else
		chatedittype = 0
	EndIf
	
	Local x# = chatoffsetx, y# = chatoffsety, width# = chatwidth, height# = chatheight
	
	;if NetworkServer\ChatOpen Then DrawFrame x,y,width,height+30*MenuScale
	
	Local chatHeightc% = 30*MenuScale
	Local scrollbarHeight% = 0
	For cm.ChatMessage = Each ChatMessage
		chatHeightc = chatHeightc + 30*MenuScale
	Next
	scrollbarHeight = (Float(height)/Float(chatHeightc))*height
	If scrollbarHeight>height Then scrollbarHeight = height
	If chatHeightc<height Then chatHeightc = height
	
	
	If NetworkServer\chatOpen Then
		Select chattypes
			Case 1
				DrawFrame x,y,width,1
				DrawFrame x,y,1,height
			Case 0
				DrawFrame x,y,width,1
				DrawFrame x,y,1,height
				Color 1,1,1
				Rect x+1,y+1,width-1,height-1
		End Select
		Color 50,50,50
		inBar% = MouseOn(x+width-26*MenuScale,y,26*MenuScale,height)
		If inBar Then Color 70,70,70
		Rect x+width-26*MenuScale,y,26*MenuScale,height,True
		
		
		Color 120,120,120
		inBox% = MouseOn(x+width-23*MenuScale,y+height-scrollbarHeight+(chatScroll*scrollbarHeight/height),20*MenuScale,scrollbarHeight)
		If inBox Then Color 200,200,200
		If chatScrollDragging Then Color 255,255,255
		Rect x+width-23*MenuScale,y+height-scrollbarHeight+(chatScroll*scrollbarHeight/height),20*MenuScale,scrollbarHeight,True
		
		If Not MouseDown(1) Then
			chatScrollDragging=False
		ElseIf chatScrollDragging Then
			chatScroll = chatScroll+((ScaledMouseY()-chatMouseMem)*height/scrollbarHeight)
			chatMouseMem = ScaledMouseY()
		EndIf
		
		If (Not chatScrollDragging) Then
			If MouseHit1 Then
				If inBox Then
					chatScrollDragging=True
					chatMouseMem = ScaledMouseY()
				ElseIf inBar Then
					chatScroll = chatScroll+((ScaledMouseY()-(y+height))*chatHeightc/height+(height/2))
					chatScroll = chatScroll/2
				EndIf
			EndIf
		EndIf
		
		
		Local mousescroll# = MouseZSpeed()
		
		If mousescroll > 0 Then 
			chatScroll = chatScroll - 15*MenuScale
		ElseIf mousescroll < 0 Then
			chatScroll = chatScroll + 15*MenuScale
		EndIf
		
		If chatScroll<-chatHeightc+height Then chatScroll = -chatHeightc+height
		If chatScroll>0 Then chatScroll = 0
	
		Color 255, 255, 255
		
		SelectedInputBox = 1337
		TypedChatMsg = InputBox(x, y + height, width, 35*MenuScale, TypedChatMsg, 1337, True, chatwidth-55*MenuScale)
		TypedChatMsg = Left(TypedChatMsg, 100) ;Max(0, (chatwidth-30*MenuScale)/AAStringWidth("W")))
		
		
		If MouseOn(x + width-30*MenuScale, y + height + 8 *MenuScale, 20*MenuScale, 20*MenuScale) Then
			DrawImage(mpimg\Smile2, x + width - 30*MenuScale, y + height + 8*MenuScale)
			DrawTextRect(ScaledMouseX()-15*MenuScale, ScaledMouseY()-20*MenuScale,0,0, "Soon...")
		Else
			DrawImage(mpimg\Smile, x + width - 30*MenuScale, y + height + 8*MenuScale)
		EndIf
		
		If TypedChatMsg = "" Then
			CurrentText = "Type message"
			
			If AAStringWidth(CurrentText) > chatwidth-55*MenuScale Then
				CurrentText = Left(CurrentText, Max(0, (chatwidth-55*MenuScale)/AAStringWidth("W")-3))
				CurrentText = CurrentText+"..."
			EndIf
						
			AAText(x+10*MenuScale, y + height + 10*MenuScale, CurrentText)
		EndIf
		
		If KeyHit(28) Then
			If GetScripts() Then 
				public_inqueue(public_OnSendMessage)
				public_addparam(TypedChatMsg, SE_STRING)
				callback
			EndIf
			If (Not IsACommand(TypedChatMsg)) And TypedChatMsg <> "" Then 
				multiplayer_AddChatMsg(": "+TypedChatMsg)
				chatScroll = 0
			EndIf
			
			NetworkServer\chatOpen = False
			TypedChatMsg = ""
			SelectedInputBox = 0
			
			FlushKeys()
			FlushMouse()
			
		End If
		
		
		; =============================================================== Buttons

		If DrawButton(x+width, y, 25*MenuScale,25*MenuScale, "X", False, False, True, -1, -1, -1, 255, 0, 0) Then
			NetworkServer\chatOpen = False
			TypedChatMsg = ""
			SelectedInputBox = 0
			
			FlushKeys()
			FlushMouse()
		EndIf
		
		Local buttontype$
		
		Select chattypes
			Case 1: buttontype$ = "+"
			Case 0: buttontype$ = "-"
		End Select
		
		If DrawButton(x+width, y+25*MenuScale, 25*MenuScale,25*MenuScale, "P", False) Then
			If Not LockButton Then
				If chatedittype <> 0 Then 
					chatedittype = 0
					SaveMultiplayerOptions()
				Else
					chatedittype = 1
				EndIf
			EndIf
		EndIf
		If DrawButton(x+width, y+50*MenuScale, 25*MenuScale,25*MenuScale, "S", False) Then
			If Not LockButton Then
				If chatedittype <> 0 Then 
					chatedittype = 0
					SaveMultiplayerOptions()
				Else
					chatedittype = 2
				EndIf
			EndIf
		EndIf
		
		If DrawButton(x+width, y+75*MenuScale, 25*MenuScale,25*MenuScale, buttontype, False) Then 
			chattypes = Not chattypes
			SaveMultiplayerOptions()
		EndIf
	EndIf
	
	Local TempY% = y + height - 25*MenuScale - chatScroll
	Local count% = 0
	
	Color 255,255,255
	
	DisableRedirectAccess = True
	
	Local MaxMin#
	
	For cm.Chatmessage = Each ChatMessage
		count=count+1
		If count>300 Then
			count = 0
			For cm.Chatmessage = Each ChatMessage
				Delete cm
				count = count + 1
				If count > 150 Then Exit
			Next
			Exit
		Else
		
			cm\Factor = Max(cm\Factor-FPSfactor, 0)
			
			If TempY >= y Then
				If TempY < y + height - 5*MenuScale Then
					
					MaxMin = 1.0

					If Not NetworkServer\chatOpen Then MaxMin = Min(cm\Factor / 2, 255)/255.0
					
					If MaxMin > 0 Then
						Color 255*MaxMin,255*MaxMin,255*MaxMin
						If cm\Formatted Then 
							LimitFormatText(x+5*MenuScale, TempY, cm\txt, chatwidth-30*MenuScale, False, False, MaxMin, (chattypes = 1 Or (Not NetworkServer\chatOpen)))
						Else
							LimitText(cm\txt, x+5*MenuScale, TempY, chatwidth-30*MenuScale, True, (chattypes = 1 Or (Not NetworkServer\chatOpen)))
						EndIf
					EndIf
				EndIf
			EndIf
			
			TempY = TempY - 30*MenuScale
		EndIf
	Next
	
	DisableRedirectAccess = False
	
	Color 255,255,255
	AASetFont Font1
	
	If NetworkServer\chatOpen Then 
		If Fullscreen Then DrawImage CursorIMG, ScaledMouseX(),ScaledMouseY()
	EndIf
End Function
Function multiplayer_AddChatMsg(txt$, add% = True)
	If (Not udp_GetStream()) Then Return
	
	If NetworkServer\MainPlayer
		cm.ChatMessage = multiplayer_CreateMessage(Nickname+txt)
		For p.players = Each players
			If p\ID <> NetworkServer\MyID Then
				udp_WriteByte M_CHAT
				udp_WriteByte 0
				udp_WriteLine cm\Txt
				udp_WriteByte 1
				udp_SendMessage(p\ID)
			EndIf
		Next
	Else
		udp_ByteStreamWriteChar(M_CHAT)
		udp_ByteStreamWriteLine(txt)
		udp_ByteStreamWriteChar(add)
		udp_setmicrobyte(M_CHAT)
	EndIf
End Function
Function multiplayer_RenderChat()
	multiplayer_UpdateChatMessages()
End Function
Function multiplayer_UpdateAttachPlayers(p.players)
	If p\obj = 0 Or p\AttachObjects = Null Then Return
	
	Local BT.breachtypes = GetBreachType(p\BreachType)
	
	If p\showObjects = True Then
		If BT\AllowWeaponsAttaches Then
			For i = GUN_USP To GUN_M4A4
				AttachObject(p\AttachObjects\Gun[i], EntityX(GetPlayerHand(p\ID),True),EntityY(GetPlayerHand(p\ID), True),EntityZ(GetPlayerHand(p\ID),True), p\BonePitch,p\yaw,90, p\UsedWeapon, i)
			Next
		EndIf
		
		If BT\AllowItemsAttaches Then
			q# = EntityX(p\PlayerHead, True)
			w# = EntityZ(p\PlayerHead, True)
			q# =  q + (0.025 * Sin(-p\yaw))
			w# =  w + (0.025 * Cos(-p\yaw))

			AttachObject(p\AttachObjects\GasMaskObj, q,EntityY(p\PlayerHead, True)+0.01,w, EntityPitch(p\PlayerHead, True)+90,EntityYaw(p\PlayerHead, True),0, -1, p\WearingGasMask)
			
			AttachObject(p\AttachObjects\NVGobj, q,EntityY(p\PlayerHead, True)+0.05,w, EntityPitch(p\PlayerHead, True),EntityYaw(p\PlayerHead, True),EntityRoll(p\PlayerHead), -1, p\WearingNightVision)
			
			q = EntityX(p\PlayerSpine2, True)
			w = EntityZ(p\PlayerSpine2, True)
			q# =  q + (0.1 * Sin(-p\yaw))
			w# =  w + (0.1 * Cos(-p\yaw))
			
			AttachObject(p\AttachObjects\BallisticVest, q,EntityY(p\PlayerSpine2, True)-0.08,w, EntityPitch(p\PlayerSpine2, True)+90,EntityYaw(p\PlayerSpine2, True)-180,EntityRoll(p\PlayerSpine2, True)+90, -1, p\WearingVest)
		EndIf
		
		HideEntity p\AttachObjects\Gun[GUN_GRENADE]
		HideEntity p\AttachObjects\Gun[GUN_GRENADEFLASHBANG]
		HideEntity p\AttachObjects\Gun[GUN_GRENADESMOKE]
		HideEntity p\AttachObjects\Gun[GUN_HANDCUFFS]
		HideEntity p\AttachObjects\Gun[GUN_KNIFE]
		
		If Not p\Handcuffed Then
			If p\PlayerHand <> 0 Then
				Select p\UsedWeapon
					Case GUN_GRENADE, GUN_GRENADEFLASHBANG, GUN_GRENADESMOKE
						If BT\HoldingGrenade Then
							If p\PlayerBones[2] = 0 Then p\PlayerBones[2] = FindChild(p\obj, BT\HoldingGrenadeData[0])
							RotateEntity p\PlayerBones[2], EntityPitch(p\PlayerBones[2]), Float(BT\HoldingGrenadeData[1]), Float(BT\HoldingGrenadeData[2])
						EndIf
						PositionEntity p\AttachObjects\Gun[p\UsedWeapon], EntityX(p\PlayerHand, True), EntityY(p\PlayerHand, True), EntityZ(p\PlayerHand, True), True
						RotateEntity p\AttachObjects\Gun[p\UsedWeapon], EntityPitch(p\PlayerHand, True)-45, EntityYaw(p\PlayerHand, True)-90, EntityRoll(p\PlayerHand, True), True
						ShowEntity p\AttachObjects\Gun[p\UsedWeapon]
					Case GUN_HANDCUFFS
						PositionEntity p\AttachObjects\Gun[p\UsedWeapon], EntityX(p\PlayerHand, True), EntityY(p\PlayerHand, True), EntityZ(p\PlayerHand, True), True
						RotateEntity p\AttachObjects\Gun[p\UsedWeapon], EntityPitch(p\PlayerHand, True)-45, EntityYaw(p\PlayerHand, True)-90, EntityRoll(p\PlayerHand, True), True
						ShowEntity p\AttachObjects\Gun[p\UsedWeapon]
					Case GUN_KNIFE
						
						If p\PLAYER_MOVE_TIMED = PLAYER_BITE Then
							If p\PlayerForearmOffset = 0 Then
								p\PlayerForearmOffset = EntityYaw(p\PlayerForearm)
								p\PlayerForearmOffsetTimed = 0
								p\PLAYER_MOVE_TIMED_PREV = False
								
								If p\PlayerForearmOffset = 0 Then p\PlayerForearmOffset = 0.1
							EndIf
							
							
							RotateEntity p\PlayerForearm, EntityPitch(p\PlayerForearm), p\PlayerForearmOffsetTimed, EntityRoll(p\PlayerForearm)
							
							If p\PlayerForearmOffsetTimed <= 74 And (Not p\PLAYER_MOVE_TIMED_PREV) Then
								p\PlayerForearmOffsetTimed = CurveValue(75, p\PlayerForearmOffsetTimed, 3)
							Else
							
								p\PLAYER_MOVE_TIMED_PREV = True
								p\PlayerForearmOffsetTimed = CurveValue(0, p\PlayerForearmOffsetTimed, 3)
								If p\PlayerForearmOffsetTimed < 1 Then
									p\PlayerForearmOffset = 0
									p\PlayerForearmOffsetTimed = 0
									p\PLAYER_MOVE_TIMED = 0
									p\PLAYER_MOVE_TIMED_PREV = False
								EndIf
							EndIf
						EndIf
						
						PositionEntity p\AttachObjects\Gun[p\UsedWeapon], EntityX(p\PlayerHand, True), EntityY(p\PlayerHand, True), EntityZ(p\PlayerHand, True), True
						RotateEntity p\AttachObjects\Gun[p\UsedWeapon], EntityPitch(p\PlayerHand, True)-45, EntityYaw(p\PlayerHand, True)-90, EntityRoll(p\PlayerHand, True), True
						ShowEntity p\AttachObjects\Gun[p\UsedWeapon]
				End Select
				
			
				If p\SelectedItem <> 0 Then
					If BT\HoldingItem Then RotateEntity p\PlayerForearm, EntityPitch(p\PlayerForearm), BT\HoldingItemData[0], BT\HoldingItemData[1]
				EndIf
			EndIf
		Else
			
			If BT\Handcuffs Then
				If p\PlayerBones[4] = 0 Then p\PlayerBones[4] = FindChild(p\obj, BT\HandCuffData[0])
				If p\PlayerBones[5] = 0 Then p\PlayerBones[5] = FindChild(p\obj, BT\HandCuffData[4])
				RotateEntity(p\PlayerBones[4], Float(BT\HandCuffData[1]), EntityYaw(p\obj)+Float(BT\HandCuffData[2]), Float(BT\HandCuffData[3]), True)
				RotateEntity(p\PlayerBones[5], Float(BT\HandCuffData[5]), EntityYaw(p\obj)+Float(BT\HandCuffData[6]), Float(BT\HandCuffData[7]), True)
			EndIf
		EndIf
	EndIf
	
	If p\IsDead Or p\showObjects = False Then
		If p\AttachObjects\NewYearHat <> 0 Then HideEntity p\AttachObjects\NewYearHat
		HideEntity p\AttachObjects\BallisticVest
		HideEntity p\AttachObjects\NVGobj
		HideEntity p\AttachObjects\GasMaskObj
		
		For i = GUN_USP To GUN_GRENADESMOKE
			HideEntity p\AttachObjects\Gun[i]
		Next
	Else
		If Not BT\AllowItemsAttaches Then
			If p\AttachObjects\NewYearHat <> 0 Then HideEntity p\AttachObjects\NewYearHat
			HideEntity p\AttachObjects\BallisticVest
			HideEntity p\AttachObjects\NVGobj
			HideEntity p\AttachObjects\GasMaskObj
		EndIf
		
		If Not BT\AllowWeaponsAttaches Then
			For i = GUN_USP To GUN_GRENADESMOKE
				HideEntity p\AttachObjects\Gun[i]
			Next
		EndIf
	EndIf
End Function
Function multiplayer_RenderPlayer2D(p.players)
	If Spectate\SpectatePlayer = p\ID Then
		AASetFont Font1_2
		
		Local y# = GraphicHeight-50*MenuScale
		
		DisableRedirectAccess = True
		
		Color 255,255,255
		
		AAText 20*MenuScale, y, p\name
		DisableRedirectAccess = False
		
		If GetTypeName(p\BreachType) <> " " And GetTypeName(p\BreachType) <> "" Then
			y = y - 35*MenuScale
			SetTypeColor(p\BreachType)
			AAText(20*MenuScale, y, GetTypeName(p\BreachType))
		EndIf
		
		If p\tag <> "" Then
			y = y - 35*MenuScale
			Color p\tagr,p\tagg,p\tagb
			AAText 20*MenuScale, y, p\tag
		EndIf
		
		AASetFont Font1
		Color 255,255,255

		If Not MP_InstructionsDone Then
		
			If voice\VoiceInstall <> 0 And NetworkServer\haveVoice = True Then
				AAText(GraphicWidth-(AAStringWidth("Use LMB or RMB to switch between players")+10*MenuScale), GraphicHeight-120*MenuScale, "Press R to switch camera mode")
				AAText(GraphicWidth-(AAStringWidth("Use LMB or RMB to switch between players")+10*MenuScale), GraphicHeight-100*MenuScale, "Use LMB or RMB to switch between players")
			Else
				AAText(GraphicWidth-(AAStringWidth("Use LMB or RMB to switch between players")+10*MenuScale), GraphicHeight-70*MenuScale, "Press R to switch camera mode")
				AAText(GraphicWidth-(AAStringWidth("Use LMB or RMB to switch between players")+10*MenuScale), GraphicHeight-50*MenuScale, "Use LMB or RMB to switch between players")
			EndIf
		EndIf
	EndIf
	If Not MainMenuOpen Then
		If MyPlayer\BreachType = MODEL_096 Then
			If SCP\look[p\ID] = True Then
				DrawProjectedImage(SprintIcon, Camera, p\Pivot)
			EndIf
		EndIf
	EndIf
End Function

Function DrawProjectedText(txt$, cam, entity)
	Local VIEW_ENTITY_PIVOT,VIEW_ENTITY_PIVOT2
	VIEW_ENTITY_PIVOT = CreatePivot()
	VIEW_ENTITY_PIVOT2 = CreatePivot()
	PositionEntity VIEW_ENTITY_PIVOT, EntityX(cam), EntityY(cam), EntityZ(cam)
	PositionEntity VIEW_ENTITY_PIVOT2,EntityX(entity), EntityY(entity), EntityZ(entity)
	PointEntity VIEW_ENTITY_PIVOT, VIEW_ENTITY_PIVOT2
	yawvalue# = WrapAngle(EntityYaw(cam) - EntityYaw(VIEW_ENTITY_PIVOT))
	xvalue# = 0.0
	If yawvalue > 90 And yawvalue <= 180 Then
		xvalue# = Sin(90)/90*yawvalue
	Else If yawvalue > 180 And yawvalue < 270 Then
		xvalue# = Sin(270)/yawvalue*270
	Else
		xvalue = Sin(yawvalue)
	EndIf
	pitchvalue# = WrapAngle(EntityPitch(cam) - EntityPitch(VIEW_ENTITY_PIVOT))
	yvalue# = 0.0
	If pitchvalue > 90 And pitchvalue <= 180 Then
		yvalue# = Sin(90)/90*pitchvalue
	Else If pitchvalue > 180 And pitchvalue < 270 Then
		yvalue# = Sin(270)/pitchvalue*270
	Else
		yvalue# = Sin(pitchvalue)
	EndIf
	FreeEntity VIEW_ENTITY_PIVOT
	FreeEntity VIEW_ENTITY_PIVOT2
	AAText(GraphicWidth / 2 + xvalue * (GraphicWidth / 2), GraphicHeight / 2 - yvalue * (GraphicHeight / 2), txt)
End Function

Function DrawProjectedOval(width, height, cam, entity, solid = True)
	Local VIEW_ENTITY_PIVOT,VIEW_ENTITY_PIVOT2
	VIEW_ENTITY_PIVOT = CreatePivot()
	VIEW_ENTITY_PIVOT2 = CreatePivot()
	PositionEntity VIEW_ENTITY_PIVOT, EntityX(cam), EntityY(cam), EntityZ(cam)
	PositionEntity VIEW_ENTITY_PIVOT2,EntityX(entity), EntityY(entity), EntityZ(entity)
	PointEntity VIEW_ENTITY_PIVOT, VIEW_ENTITY_PIVOT2
	yawvalue# = WrapAngle(EntityYaw(cam) - EntityYaw(VIEW_ENTITY_PIVOT))
	xvalue# = 0.0
	If yawvalue > 90 And yawvalue <= 180 Then
		xvalue# = Sin(90)/90*yawvalue
	Else If yawvalue > 180 And yawvalue < 270 Then
		xvalue# = Sin(270)/yawvalue*270
	Else
		xvalue = Sin(yawvalue)
	EndIf
	pitchvalue# = WrapAngle(EntityPitch(cam) - EntityPitch(VIEW_ENTITY_PIVOT))
	yvalue# = 0.0
	If pitchvalue > 90 And pitchvalue <= 180 Then
		yvalue# = Sin(90)/90*pitchvalue
	Else If pitchvalue > 180 And pitchvalue < 270 Then
		yvalue# = Sin(270)/pitchvalue*270
	Else
		yvalue# = Sin(pitchvalue)
	EndIf
	FreeEntity VIEW_ENTITY_PIVOT
	FreeEntity VIEW_ENTITY_PIVOT2
	Oval(GraphicWidth / 2 + xvalue * (GraphicWidth / 2), GraphicHeight / 2 - yvalue * (GraphicHeight / 2), width, height, solid)
End Function

Function DrawProjectedImage(image, cam, entity)
	Local VIEW_ENTITY_PIVOT,VIEW_ENTITY_PIVOT2
	VIEW_ENTITY_PIVOT = CreatePivot()
	VIEW_ENTITY_PIVOT2 = CreatePivot()
	PositionEntity VIEW_ENTITY_PIVOT, EntityX(cam), EntityY(cam), EntityZ(cam)
	PositionEntity VIEW_ENTITY_PIVOT2,EntityX(entity), EntityY(entity), EntityZ(entity)
	PointEntity VIEW_ENTITY_PIVOT, VIEW_ENTITY_PIVOT2
	yawvalue# = WrapAngle(EntityYaw(cam) - EntityYaw(VIEW_ENTITY_PIVOT))
	xvalue# = 0.0
	If yawvalue > 90 And yawvalue <= 180 Then
		xvalue# = Sin(90)/90*yawvalue
	Else If yawvalue > 180 And yawvalue < 270 Then
		xvalue# = Sin(270)/yawvalue*270
	Else
		xvalue = Sin(yawvalue)
	EndIf
	pitchvalue# = WrapAngle(EntityPitch(cam) - EntityPitch(VIEW_ENTITY_PIVOT))
	yvalue# = 0.0
	If pitchvalue > 90 And pitchvalue <= 180 Then
		yvalue# = Sin(90)/90*pitchvalue
	Else If pitchvalue > 180 And pitchvalue < 270 Then
		yvalue# = Sin(270)/pitchvalue*270
	Else
		yvalue# = Sin(pitchvalue)
	EndIf
	FreeEntity VIEW_ENTITY_PIVOT
	FreeEntity VIEW_ENTITY_PIVOT2
	DrawImage(image, GraphicWidth / 2 + xvalue * (GraphicWidth / 2), GraphicHeight / 2 - yvalue * (GraphicHeight / 2))
End Function

Function multiplayer_UpdatePlayer(p.players)
	If (Not p\obj) Or (Not p\Pivot) Then Return
	If p\IsDead = 1 Or p\BreachType = 0 Then
		SCP\look[p\ID] = False
		HideEntity p\obj
		HideEntity p\Pivot
		HideEntity p\Hitbox
		p\showObjects = False
		Return
	Else
		ShowEntity p\Pivot
		ShowEntity p\obj
		ShowEntity p\Hitbox
	EndIf
	Local BT.breachtypes = GetBreachType(p\BreachType)
	
	If p\WearingHazmat <> 0 Then BT.breachtypes = GetBreachType(CLASSD_MODEL)
	
	Local FixPivot# = BT\FixPivot, FixRotate# = BT\FixRotate, FixPitch = BT\FixPitch
	
	If p\WearingHazmat < 1 Then
		Select p\BreachType
			Case MODEL_966
				If WearingNightVision = 0 And (Not multiplayer_IsAFriend(MyPlayer\BreachType, p\BreachType)) Then
					FixPivot = 9999
				EndIf
		End Select
	EndIf
	
	; --------- new year hat upd
		;if EntityDistance(Collider, p\Pivot) < 0 Then
		;	ShowEntity p\AttachObjects\NewYearHat
			
		;Else
		;	HideEntity p\AttachObjects\NewYearHat
		;EndIf
	; ---------
	
	;PrevX# = EntityX(p\Pivot)
	;PrevZ# = EntityZ(p\Pivot)
	
	;speedx# = tovalue(p\x, EntityX(p\Pivot), max(0.02, p\velocity))
	;speedy# = tovalue(p\y+0.32, EntityY(p\Pivot), max(0.02, p\velocity))
	;speedz# = tovalue(p\z, EntityZ(p\Pivot), max(0.02, p\velocity))
	;speedyaw# = curveangle(p\yaw, p\prevyaw, 7)

	;PositionEntity p\Pivot, speedx, speedy, speedz
	;ResetEntity p\pivot
	
	;if Distance(PrevX, PrevZ, EntityX(p\Pivot), EntityZ(p\Pivot)) >= 0.02 Then ; Set the correct animation if we have a high ping
	;	Select p\PLAYER_MOVE
	;		Case PLAYER_IDLING
	;			p\PLAYER_MOVE = PLAYER_WALKING
	;		Case PLAYER_SITTING_IDLING
	;			p\PLAYER_MOVE = PLAYER_SITTING_WALKING_FORWARD
	;	End Select
	;Else
	;	Select p\PLAYER_MOVE
	;		Case PLAYER_WALKING
	;			p\PLAYER_MOVE = PLAYER_IDLING
	;		Case PLAYER_SITTING_WALKING_BACK,PLAYER_SITTING_WALKING_FORWARD,PLAYER_SITTING_WALKING_LEFT,PLAYER_SITTING_WALKING_RIGHT
	;			p\PLAYER_MOVE = PLAYER_SITTING_IDLING
	;	End Select
	;EndIf
	
	PositionEntity(p\Pivot, CurveValue(p\x#, EntityX(p\Pivot), 4), CurveValue(p\y#+0.32, EntityY(p\Pivot), 4), CurveValue(p\z#, EntityZ(p\Pivot), 4))
	RotateEntity(p\Pivot,0,CurveAngle(p\yaw, EntityYaw(p\Pivot), 6),0)
	
	PositionEntity(p\obj, EntityX(p\Pivot), EntityY(p\Pivot) - FixPivot - 0.32, EntityZ(p\Pivot))
	RotateEntity(p\obj,FixPitch,EntityYaw(p\Pivot)+FixRotate, 0, True)
	
	If p\PlayerHead <> 0 Then 
		PositionEntity(p\Camera, EntityX(p\PlayerHead,True),EntityY(p\PlayerHead,True),EntityZ(p\PlayerHead,True))
	Else
		PositionEntity(p\Camera, EntityX(p\Pivot,True),EntityY(p\Pivot,True)+(0.7*p\size),EntityZ(p\Pivot,True))
	EndIf
	
	RotateEntity(p\Camera, p\prevpitch, EntityYaw(p\Pivot), 0, True)
	ResetEntity p\Pivot
	
	If p\AttachObjects\NewYearHat <> 0 Then
		If p\showObjects Then
			If BT\hitboxx = 0.15 And BT\hitboxy = 0.52 And BT\hitboxz = 0.15 Then
				rd_SetForce(p\AttachObjects\NYHatBone[0], (p\prevX-EntityX(p\Pivot))*1.2, (p\prevY-EntityY(p\Pivot))*1.2, (p\prevZ-EntityZ(p\Pivot))*1.2)
				rd_SetForce(p\AttachObjects\NYHatBone[1], (p\prevX-EntityX(p\Pivot))*1.2, (p\prevY-EntityY(p\Pivot))*1.2, (p\prevZ-EntityZ(p\Pivot))*1.2)

				rd_SetForce(p\AttachObjects\NYHatBone[0], 0, 0, (p\prevyaw-EntityYaw(p\obj))*0.01)
				rd_SetForce(p\AttachObjects\NYHatBone[1], 0, 0, (p\prevyaw-EntityYaw(p\obj))*0.01)
				
				rd_SetForce(p\AttachObjects\NYHatBone[0], 0, (p\prevpitch2-p\prevpitch)*0.01, 0)
				rd_SetForce(p\AttachObjects\NYHatBone[1], 0, (p\prevpitch2-p\prevpitch)*0.01, 0)

				ShowEntity(p\AttachObjects\NewYearHat)
				
				p\prevX = EntityX(p\Pivot)
				p\prevY = EntityY(p\Pivot)
				p\prevZ = EntityZ(p\Pivot)
				p\prevyaw = EntityYaw(p\obj)
				p\prevpitch2 = p\prevpitch
			Else
				HideEntity(p\AttachObjects\NewYearHat)
			EndIf
		Else
			HideEntity(p\AttachObjects\NewYearHat)
		EndIf
	EndIf
	
	;if NetworkServer\MainPlayer Then
	;	PositionEntity(p\Pivot, EntityX(p\Pivot), EntityY(p\Pivot), EntityZ(p\Pivot), 5)
	;EndIf
	
	;if Distance(p\x, p\z, EntityX(p\pivot), EntityZ(p\pivot)) > 1 Then
	;	PositionEntity(p\Pivot, p\x#, p\y#+0.32, p\z#)
	;	ResetEntity p\Pivot
	;EndIf
	;angle# = 0.0
	
	;PositionEntity p\Pivot, EntityX(p\Pivot), p\y, EntityZ(p\Pivot)
	
	;If ReadBool(p\movedir, 1) Then
	;	angle = 180
	;	If ReadBool(p\movedir, 2) Then 
	;		angle = 135
	;	EndIf
	;	If ReadBool(p\movedir, 3) Then
	;		angle = -135
	;	EndIf
	;
	;ElseIf ReadBool(p\movedir, 0) Then
	;	angle = 0
	;	If ReadBool(p\movedir, 2) Then 
	;		angle = 45
	;	EndIf
	;	If ReadBool(p\movedir, 3) Then
	;		angle = -45
	;	EndIf
	;Else
	;	If ReadBool(p\movedir, 2) Then 
	;		angle = 90
	;	EndIf
	;	If ReadBool(p\movedir, 3) Then
	;		angle = -90
	;	EndIf
	;EndIf
	
	;RotateEntity(p\Pivot,0,p\yaw,0)
	;angle = WrapAngle(p\yaw+angle+90.0)
	
	;TranslateEntity p\Pivot, Cos(angle) * p\velocity * FPSfactor, 0, Sin(angle) * p\velocity * FPSfactor, True
	
	;PositionEntity(p\obj, EntityX(p\pivot), EntityY(p\pivot) - FixPivot, EntityZ(p\pivot))
	;RotateEntity(p\obj,FixPitch,p\yaw-FixRotate, 0, True)
	
	;p\prevyaw = EntityYaw(p\Pivot)

	p\showObjects = True
	
	If Not multiplayer_IsAFriend(MyPlayer\BreachType, p\BreachType) Then
		If MyPlayer\BreachType = MODEL_939 Then
			p\showObjects = False
			HideEntity p\obj
			If p\PlayerSoundVolume > 0.01 And (Not (p\PLAYER_MOVE >= 5 And p\PLAYER_MOVE <= 10)) Then
				ShowEntity p\obj
				p\showObjects = True
			EndIf
		EndIf
	EndIf
	
	If EntityDistance(p\Pivot, Collider) > HideDistance*2 Then
		HideEntity p\Hitbox
		HideEntity p\obj
		p\showObjects = False
	EndIf
End Function
Function multiplayer_UpdatePlayerObjects(p.players)
	If p\nameent <> Null And Camera <> 0 Then
		If (p\BreachType <> MODEL_966 Or multiplayer_IsAFriend(MyPlayer\BreachType, p\BreachType)) And p\BreachType <> 0 And p\showObjects = True And NetworkServer\SeeNames Then
			PointEntity p\nameent\cPv, Camera
			PointEntity p\afkent\cPv, Camera
			PointEntity p\voiceent\cPv, Camera
			If p\patreonent <> Null Then PointEntity p\patreonent\cPv, Camera
			
			Local Offset% = (p\PLAYER_MOVE = PLAYER_SITTING_IDLING Or p\PLAYER_MOVE = PLAYER_SITTING_WALKING_BACK Or p\PLAYER_MOVE = PLAYER_SITTING_WALKING_FORWARD Or p\PLAYER_MOVE = PLAYER_SITTING_WALKING_LEFT Or p\PLAYER_MOVE = PLAYER_SITTING_WALKING_RIGHT)
			
			RotateEntity p\nameent\cPv, -EntityPitch(p\nameent\cPv), EntityYaw(p\nameent\cPv)-180, 0, True
			PositionEntity(p\nameent\cPv, EntityX(p\Pivot), EntityY(p\Pivot)+(0.76*(p\Size*1)) - 0.32 - (0.32*Offset), EntityZ(p\Pivot))
			RotateEntity p\voiceent\cPv, EntityPitch(p\nameent\cPv), EntityYaw(p\nameent\cPv), 0, True
			PositionEntity(p\voiceent\cPv, EntityX(p\Pivot), EntityY(p\Pivot)+(0.8*(p\Size*1)) - 0.32 - (0.32*Offset), EntityZ(p\Pivot))
			RotateEntity p\afkent\cPv, EntityPitch(p\nameent\cPv), EntityYaw(p\nameent\cPv), 0, True
			PositionEntity(p\afkent\cPv, EntityX(p\Pivot), EntityY(p\Pivot)+(0.86*(p\Size*1)) - 0.32 - (0.32*Offset), EntityZ(p\Pivot))
			
			If p\patreonent <> Null Then
				RotateEntity p\patreonent\cPv, EntityPitch(p\nameent\cPv), EntityYaw(p\nameent\cPv), 0, True
				PositionEntity(p\patreonent\cPv, EntityX(p\Pivot), EntityY(p\Pivot)+(0.92*(p\Size*1)) - 0.32 - (0.32*Offset), EntityZ(p\Pivot))
			EndIf

			If p\BreachType = MyPlayer\BreachType Or multiplayer_IsAFriend(MyPlayer\BreachType, p\BreachType) Then SetTypeColor(p\BreachType) Else Color 255,255,255
			GG_Set_Color p\nameent, ColorRed(), ColorGreen(), ColorBlue()
			ShowEntity p\nameent\cPv

			If NetworkServer\haveVoice = True Then
				If p\Talking = 1 Then
					ShowEntity p\voiceent\cPv
				Else
					HideEntity p\voiceent\cPv
				EndIf
			Else
				HideEntity p\voiceent\cPv
			EndIf
			;if p\IsABot <> 0 Then p\AFK = 0
			If p\AFK Then
				ShowEntity p\afkent\cPv
			Else 
				HideEntity p\afkent\cPv
			EndIf
			If p\patreonent <> Null Then
				Color p\tagr,p\tagg,p\tagb
				GG_Set_Color p\patreonent, ColorRed(), ColorGreen(), ColorBlue()
				ShowEntity p\patreonent\cPv
			EndIf
		Else
			HideEntity p\voiceent\cPv
			HideEntity p\afkent\cPv
			HideEntity p\nameent\cPv
			If p\patreonent <> Null Then HideEntity p\patreonent\cPv
		EndIf
	EndIf
	
	multiplayer_UpdatePlayerSize(p\ID)
End Function
Function multiplayer_UpdateplayerRoom(p.players)
	Local foundnewroom% = False, x#, z#
	Local r.Rooms = Room[p\PlayerRoomID]
	If r <> Null Then
		If Abs(EntityY(p\Pivot) - EntityY(r\obj)) < 1.5 Then
			x = Abs(r\x-EntityX(p\Pivot))
			z = Abs(r\z-EntityZ(p\Pivot))	
			If x < 4.0 Then
				If z < 4.0 Then foundnewroom = True
			EndIf
			If foundnewroom = False Then
				For i=0 To 3
					If r\Adjacent[i]<>Null Then
						x = Abs(r\Adjacent[i]\x-EntityX(p\Pivot,True))
						If x < 4.0 Then
							z = Abs(r\Adjacent[i]\z-EntityZ(p\Pivot,True))
							If z < 4.0 Then
								p\PlayerRoomID = r\ID
								multiplayer_SetPlayerRoom(p)
								Return
							EndIf
						EndIf
					EndIf
				Next
			EndIf
		Else
			foundnewroom = True
		EndIf
	EndIf
	If Not foundnewroom Then
		r = Null
		For r = Each rooms
			x = Abs(r\x-EntityX(p\Pivot))
			z = Abs(r\z-EntityZ(p\Pivot))	
			If x < 4.0 Then
				If z < 4.0 Then
					If Abs(EntityY(p\Pivot) - EntityY(r\obj)) < 1.5 Then 
						p\PlayerRoomID = r\ID
						multiplayer_SetPlayerRoom(p)
						Return
					EndIf
				EndIf
			EndIf
		Next
	EndIf
End Function
Global Cube
Function multiplayer_UpdateplayerSounds(p.players)
	Select p\BreachType
		Case MODEL_173
			If p\ShowObjects Or p\ID = MyPlayer\ID Then
				If Distance(p\prevx, p\prevz, EntityX(p\Pivot), EntityZ(p\Pivot)) >= 0.03 Then
					If p\ID = MyPlayer\ID Then
						p\SoundChn = LoopSound2(StoneDragSFX, p\SoundChn, Camera, p\Pivot, 13.0, 0.8)
						ChannelPan p\SoundChn, 0.5
						ChannelVolume p\SoundChn, 0.8*SFXVolume
					Else
						p\SoundChn = LoopSound2(StoneDragSFX, p\SoundChn, Camera, p\Pivot, 13.0, 0.8)
					EndIf
				Else
					StopChannel(p\SoundChn)
					p\SoundChn = 0
				EndIf
				p\prevx = EntityX(p\Pivot)
				p\prevz = EntityZ(p\Pivot)
			EndIf
		Case MODEL_096
			If p\SoundTimeout < MilliSecs() Then
				If p\PLAYER_MOVE = PLAYER_CRYING And p\PREV_PLAYER_MOVE <> PLAYER_CRYING Then
					If (Not multiplayer_IsAFriend(MyPlayer\BreachType,p\BreachType)) Then
						If (BlinkTimer < - 16 Or BlinkTimer > - 6) Then
							If p\showobjects Then
								If EntityInView(Camera, GetPlayerCamera(p\ID)) And EntityInView(GetPlayerCamera(p\ID), Camera) Then
									If EntityVisible(MyHitbox, p\hitbox) Then
										PlaySound_Strict(Triggered096SFX)
										p\SoundTimeout = MilliSecs()+30000
										p\PREV_PLAYER_MOVE = PLAYER_CRYING
									EndIf
									
								EndIf
							EndIf
						EndIf
					EndIf
				Else
					If p\PLAYER_MOVE <> PLAYER_CRYING And p\PREV_PLAYER_MOVE = PLAYER_CRYING Then p\PREV_PLAYER_MOVE = 0
				EndIf
			EndIf
	End Select
	
	If p\PLAYER_MOVE < MAX_ROLES_ANIMATIONS Then
		Local BT.BreachTypes = GetBreachType(p\BreachType)
		
		If BT\AmbientSoundAnims[0] <> "" Then
			If p\showobjects Or p\ID = MyPlayer\ID Then
				If Not ChannelPlaying(p\SoundChn) Then
				
					p\SoundCHN = 0
					For snd.sound = Each sound
						If snd\name = BT\AmbientSoundAnims[0] Then
							If p\ID = MyPlayer\ID Then
								p\SoundCHN = PlaySound_Strict(Handle(snd))
								ChannelVolume p\SoundChn, 0.5*SFXVolume
							Else
								p\SoundChn = Play3DSound(Handle(snd), Camera, p\Pivot, 13, 0.8)
							EndIf
							Exit
						EndIf
					Next
					
					If p\SoundCHN = 0 Then 
						If p\ID = MyPlayer\ID Then
							p\SoundCHN = PlaySound_Strict(LoadTempSound(BT\AmbientSoundAnims[p\PLAYER_MOVE]))
							ChannelVolume p\SoundChn, 0.5*SFXVolume
						Else
							p\SoundChn = Play3DSound(0, Camera, p\Pivot, 13, 0.8, BT\AmbientSoundAnims[p\PLAYER_MOVE])
						EndIf
					EndIf
					
				EndIf
			EndIf
		EndIf

		If BT\AmbientSoundAnims[p\PLAYER_MOVE] <> "" Then
			If p\showobjects Or p\ID = MyPlayer\ID Then
				If Not ChannelPlaying(p\SoundChn) Then
					p\SoundCHN = 0
					For snd.sound = Each sound
						If snd\name = BT\AmbientSoundAnims[p\PLAYER_MOVE] Then
							If p\ID = MyPlayer\ID Then
								p\SoundCHN = PlaySound_Strict(Handle(snd))
								ChannelVolume p\SoundChn, 0.5*SFXVolume
							Else
								p\SoundChn = Play3DSound(Handle(snd), Camera, p\Pivot, 13, 0.8)
							EndIf
							Exit
						EndIf
					Next
					If p\SoundCHN = 0 Then 
						If p\ID = MyPlayer\ID Then
							p\SoundCHN = PlaySound_Strict(LoadTempSound(BT\AmbientSoundAnims[p\PLAYER_MOVE]))
							ChannelVolume p\soundchn, 0.5*SFXVolume
						Else
							p\soundchn = Play3DSound(0, Camera, p\Pivot, 13, 0.8, BT\AmbientSoundAnims[p\PLAYER_MOVE])
						EndIf
					EndIf
				EndIf
			EndIf
		EndIf
	EndIf
End Function

Function multiplayer_IsFullSync()
	Return NetworkServer\FullSync
End Function

Const BUFFER_TICKRATE = 0

Function multiplayer_UpdatePlayerSync()
	;if cube = 0 then
	;	cube = createcube()
	;	scaleentity cube, 0.009, 0.05, 0.009
	;EndIf
	;
	;EntityColor cube, Rand(255), Rand(255), Rand(255)
	
	;positionentity Cube, networkserver\MyPosX, networkserver\MyPosY, networkserver\MyPosZ
	;ResetEntity cube
End Function
Function multiplayer_UpdatePlayers()
	For p.players = Each players
		If p\ID <> NetworkServer\MyID Then
			multiplayer_UpdatePlayer(p)
			multiplayer_UpdatePlayerObjects(p)
			If p\PlayerHead <> 0 Then
				multiplayer_UpdatePlayerRagdolls(p)
				multiplayer_UpdatePlayerAnimations(p)
				multiplayer_ManipulatePlayerBones(p)
				multiplayer_UpdateAttachPlayers(p)
			EndIf
		EndIf
		multiplayer_UpdatePlayerMicrohid(p)
		multiplayer_UpdateplayerSounds(p)
	Next
End Function
Function multiplayer_UpdatePlayerRagdolls(p.players)
	If p\AttachObjects\NewYearHat <> 0 Then
		rd_Update(p\AttachObjects\NYHatBone[0])
		rd_Update(p\AttachObjects\NYHatBone[1])	
	EndIf
End Function
Function multiplayer_UpdatePlayerAnimations(p.players)
	If (Not p\obj) Or (Not p\showObjects) Then Return
	BT.BreachTypes = GetBreachType(p\BreachType)
	
	If p\WearingHazmat <> 0 Then BT.BreachTypes = GetBreachType(CLASSD_MODEL)

	
	Select p\PLAYER_MOVE_TIMED
		Case PLAYER_BITE
			If BT\AnimationFrameData[PLAYER_BITE] <> 0 Then
				If p\PLAYER_MOVE_TIMED_PREV = 0 And AnimLength(p\obj) > 1 Then 
					SetAnimTime(p\obj, 1)
					p\PLAYER_MOVE_TIMED_PREV = p\PLAYER_MOVE_TIMED
				EndIf
				Animate2(p\obj, AnimTime(p\obj), GetFirstPackedValue(BT\AnimationFrameData[PLAYER_BITE]),GetSecondPackedValue(BT\AnimationFrameData[PLAYER_BITE]), BT\AnimationSpeed[PLAYER_BITE], False)
				If AnimTime(p\obj) >= GetSecondPackedValue(BT\AnimationFrameData[PLAYER_BITE])-1 Then
					If BT\AnimationFrameData[PLAYER_BITE_STOP] <> 0 Then
						p\PLAYER_MOVE_TIMED = PLAYER_BITE_STOP
					Else
						p\PLAYER_MOVE_TIMED = 0
						p\PLAYER_MOVE_TIMED_PREV = 0
					EndIf
				EndIf
				Return
			EndIf
		Case PLAYER_BITE_STOP
			If BT\AnimationFrameData[PLAYER_BITE] <> 0 Then
				Animate2(p\obj, AnimTime(p\obj), GetFirstPackedValue(BT\AnimationFrameData[PLAYER_BITE_STOP]),GetSecondPackedValue(BT\AnimationFrameData[PLAYER_BITE_STOP]), BT\AnimationSpeed[PLAYER_BITE_STOP], False)
				If AnimTime(p\obj) <= GetFirstPackedValue(BT\AnimationFrameData[PLAYER_BITE_STOP])+1 Then 
					p\PLAYER_MOVE_TIMED = 0
					p\PLAYER_MOVE_TIMED_PREV = 0
				EndIf
				Return
			EndIf
	End Select
	
	If p\PLAYER_MOVE < MAX_ROLES_ANIMATIONS Then
		If p\UsedWeapon = 0 Or p\UsedWeapon > GUN_M4A4 Then
			If BT\AnimationFrameData[p\PLAYER_MOVE] = 0 Then
				Animate2(p\obj, AnimTime(p\obj), GetFirstPackedValue(BT\AnimationFrameData[PLAYER_WALKING]), GetSecondPackedValue(BT\AnimationFrameData[PLAYER_WALKING]), BT\AnimationSpeed[PLAYER_WALKING])
			Else
				Animate2(p\obj, AnimTime(p\obj), GetFirstPackedValue(BT\AnimationFrameData[p\PLAYER_MOVE]), GetSecondPackedValue(BT\AnimationFrameData[p\PLAYER_MOVE]), BT\AnimationSpeed[p\PLAYER_MOVE])
			EndIf
		Else
			If BT\AnimationArmedFrameData[p\PLAYER_MOVE] = 0 Then
				Animate2(p\obj, AnimTime(p\obj), GetFirstPackedValue(BT\AnimationArmedFrameData[PLAYER_WALKING]), GetSecondPackedValue(BT\AnimationArmedFrameData[PLAYER_WALKING]), BT\AnimationArmedSpeed[PLAYER_WALKING])
			Else
				Animate2(p\obj, AnimTime(p\obj), GetFirstPackedValue(BT\AnimationArmedFrameData[p\PLAYER_MOVE]), GetSecondPackedValue(BT\AnimationArmedFrameData[p\PLAYER_MOVE]), BT\AnimationArmedSpeed[p\PLAYER_MOVE])
			EndIf
		EndIf
	EndIf
End Function
Function multiplayer_main()
	multiplayer_Receive()
	If Not udp_GetStream() Then Return
	
	If MouseHit1 Then 
		udp_ByteStreamWriteChar(M_MOUSE)
		udp_ByteStreamWriteShort MouseX()
		udp_ByteStreamWriteShort MouseY()
	EndIf

	If udp_netout(0) Then
		If MainMenuOpen = False Then
		
			If CanInteract() Then
				multiplayer_UpdateRoomObjects()
			EndIf
			
			multiplayer_Send(M_UPDATE, -1, 1)
		Else
			multiplayer_Send(M_UPDATE, -1, 0)
		EndIf
	EndIf
	
	If udp_netout(2) Then
		If Not NetworkServer\MainPlayer Then
			For p.players = Each players
				If p\name = "" Then
					udp_ByteStreamWriteChar(M_REQUESTPLAYERNAME)
					udp_ByteStreamWriteChar(p\ID)
					udp_setmicrobyte(M_REQUESTPLAYERNAME)
				EndIf
			Next
		Else
			If Not NetworkServer\Hosted Then
				udp_WriteByte -1
				udp_SendMessageInternal(udp_network\stream,udp_network\messip,udp_network\messport)
			EndIf
		EndIf
	EndIf

	multiplayer_WriteNetwork()
	MyPlayer\CurrentRadio = 0
	
	If NetworkServer\Timeout < MilliSecs() Then 
		ShouldRestartServer = True
		Return MULTIPLAYER_EXIT
	EndIf
End Function
Function multiplayer_UpdateConnection()
	If udp_GetConnection() And (Not udp_GetStream()) Then
		If MilliSecs() > NetworkServer\Timeout Then
			For sc.servers = Each Servers
				If sc\timed Then multiplayer_list_DeleteServer(sc)
			Next
			AddErrorLog("Server not responding", 255, 0, 0)
			UDP_SetStream(udp_network, 0)
			Return 0
		Else
		
			If MilliSecs() > NetworkServer\ConnectTimeout And NetworkServer\ConnectTimeout <> 0 Then
				udp_WriteByte M_CONNECT
				udp_WriteByte 0
				udp_WriteLine Nickname
				udp_WriteLine MULTIPLAYER_VERSION
				udp_WriteLine Password
				udp_WriteByte CLIENT_VERSION
				udp_WriteShort GraphicWidth
				udp_WriteShort GraphicHeight
				udp_WriteByte PATRON_COMPILE
				udp_WriteInt CurrentSteamID
				udp_WriteBytes CurrentSessionTicketData, 0, BankSize(CurrentSessionTicketData)
				udp_SendMessage()
				NetworkServer\ConnectTimeout = MilliSecs()+1500
				
				If BS_ISteamUser_IsLoggedOn(BS_SteamUser()) = 0 And NetworkServer\ConnectTries Then
					AddErrorLog("No connection to the Steam was detected.", 255, 0, 0)
					UDP_SetStream(udp_network, 0)
					NetworkServer\ConnectTries = False
					Return 0
				EndIf
				
				NetworkServer\ConnectTries = True
			EndIf
			
			While udp_RecvUDPMsg()
				udp_CountAvail(udp_ReadAvail())
				Select udp_ReadByte()
					Case M_CONNECT:
						NetworkServer\IP = udp_UDPMsgIP()
						NetworkServer\Port = udp_UDPMsgPort()
						NetworkServer\IPs = ip
						NetworkServer\Ports = port
						NetworkServer\MyID = udp_ReadByte()
						If NetworkServer\MyID < 0 Or NetworkServer\MyID > MAX_PLAYERS-1 Then 
							AddErrorLog("Unknown error", 255, 0, 0)
							ClearServer()
							Return 0
						EndIf
						If NetworkServer\MyID > 0 Then multiplayer_CreatePlayer(NetworkServer\MyID)
						NetworkServer\Servername = ""
						For sc.servers = Each Servers
							If Int(sc\shostip) = NetworkServer\IP And Int(sc\Port) = NetworkServer\Port And sc\ping <> 0 Then
								NetworkServer\Servername = sc\servername
								Exit
							EndIf
						Next
						If NetworkServer\Servername = "" Then NetworkServer\Servername = "Classic server"
						If NetworkServer\MyID > 0 Then MyPlayer\name = Nickname
						
						RandomSeed = udp_ReadLine()

						IntroEnabled = udp_ReadByte()
						NoCheat = udp_ReadByte()
						NetworkServer\haveVoice = udp_ReadByte()
						NetworkServer\EventProb = udp_ReadFloat()
						NetworkServer\ServerTimeout = udp_ReadInt()
						NetworkServer\JumpHeight = udp_ReadFloat()
						NetworkServer\JumpMode = udp_ReadByte()
						NetworkServer\Maxplayers = udp_ReadByte()
						NetworkServer\Tickrate = udp_ReadByte()
						NetworkServer\Breach = udp_ReadByte()
						NetworkServer\keepitems = udp_ReadByte()
						If (Not NetworkServer\Breach) And NetworkServer\MyID > 0 Then
							MyPlayer\BreachType = udp_ReadByte()
						Else
							udp_ReadByte()
							If NetworkServer\MyID > 0 Then MyPlayer\BreachType = 0
						EndIf
						b_br\BreachTimer = MilliSecs()+udp_ReadInt()
						b_br\CurrentBreachTime = udp_ReadInt()
						If NetworkServer\MyID > 0 Then 
							MyPlayer\TextureID = udp_ReadByte()
						Else
							udp_ReadByte()
						EndIf
						b_br\LobbyTimer = MilliSecs()+udp_ReadInt()
						SelectedDifficulty = difficulties(udp_ReadByte())
						Local startgame = udp_ReadByte()

						NetworkServer\voicerate = 48000
						udp_ReadInt()

						If (Not ServerInList(DottedIP(NetworkServer\IP), NetworkServer\Port, SERVER_HISTORY)) And (Not NetworkServer\SteamStream) Then
							sc2.servers = New servers
							sc2\sType = SERVER_HISTORY
							sc2\IP = DottedIP(NetworkServer\IP)
							sc2\Port = NetworkServer\Port
						EndIf
						NetworkServer\Timeout = (MilliSecs()+NetworkServer\ServerTimeout)+2000
						udp_WriteTimeout(0,multiplayer_GetTickrateDelay()) ; client
						udp_WriteTimeout(1,multiplayer_GetTickrateDelay()) ; host
						udp_WriteTimeout(2, 500)
						NetworkServer\ServerCustomMap = udp_ReadLine()
						NetworkServer\MainPlayer = False
						
						If udp_ReadByte() = 128 Then 
							NetworkServer\MainPlayer = True
							NetworkServer\Hosted = False
							udp_WriteTimeout(0,multiplayer_GetTickrateDelay()) ; client
							udp_WriteTimeout(1,multiplayer_GetTickrateDelay()) ; host
							udp_WriteTimeout(2, 1000)
							udp_WriteTimeout(3, 1500)
						EndIf
						
						NetworkServer\LongCulling = udp_ReadByte()
						NetworkServer\MapSize = udp_ReadByte()
						NetworkServer\FullSync = udp_ReadByte()
						NetworkServer\Prediction = udp_ReadByte()
						NetworkServer\Interpolation = udp_ReadByte()
						NetworkServer\HaveFiles = udp_ReadByte()
						NetworkServer\MenuHTML = udp_ReadLine()
						NetworkServer\RestartMenuHTML = udp_ReadLine()
						HALLOWEENINDEX = udp_ReadByte()
						NEWYEARINDEX = udp_ReadByte()

						MainMenuTab = 14
						
						If NetworkServer\MyID = 0 Then
							udp_WriteByte M_DONECONNECTION
							udp_WriteByte 0
							udp_SendMessage()
							
							DoneDelay = MilliSecs()+3000
							SendDelay = MilliSecs()+300
							While DoneDelay > MilliSecs()
							
								Local CurrentByte%
								If udp_RecvUDPMsg() Then
									CurrentByte = udp_ReadByte()
									Select CurrentByte
										Case M_DONECONNECTION
											NetworkServer\MyID = udp_ReadByte()
											
											multiplayer_CreatePlayer(NetworkServer\MyID)
											
											If Not NetworkServer\Breach Then
												MyPlayer\BreachType = udp_ReadByte()
											Else
												udp_ReadByte()
												MyPlayer\BreachType = 0
											EndIf
											MyPlayer\TextureID = udp_ReadByte()
											SelectedDifficulty = difficulties(udp_ReadByte())
											Exit
										Case M_CONNECTERROR
											currerr$ = udp_ReadLine()
											If Instr(currerr, "password") Then
												PasswordMenu = True
												AddServerMenu = False
												ConnectMenu = False
											EndIf
											While currerr <> ""
												AddErrorLog(currerr, 255, 0, 0)
												currerr$ = udp_ReadLine()
												
												If Instr(currerr, "password") Then
													PasswordMenu = True
													AddServerMenu = False
													ConnectMenu = False
												EndIf
											Wend
											DoneDelay = -1
											Exit
									End Select
								EndIf
								
								If SendDelay < MilliSecs() Then
									udp_WriteByte M_DONECONNECTION
									udp_WriteByte 0
									udp_SendMessage()
									SendDelay = MilliSecs()+300
								EndIf
								
								Delay 10
							Wend
							
							If DoneDelay <= MilliSecs() And DoneDelay <> -1 Then
								AddErrorLog("Unknown error", 255, 0, 0)
								ClearServer()
								Return 0
							EndIf
							
							If DoneDelay = -1 Then
								ClearServer()
								Return 0
							EndIf
						EndIf
						
						For br.breachtypes = Each breachtypes
							If br\constid = MyPlayer\BreachType Then
								ScaleEntity MyHitbox, br\hitboxx, br\hitboxy, br\hitboxz
								PositionEntity MyHitbox, 0, (-br\FixPivot)+0.05, 0
								Exit
							EndIf
						Next
						
						PrepareModels()
						CancelSteamTicket()
						DebugLog NEWYEARINDEX + " WTF?"
						DebugLog "Joined!"
						
						If udp_network\stream <> 0 Then SetUDPStreamBufferSize(udp_network\stream, 8192*8)
						
						If startgame = 1 And NetworkServer\ServerCustomMap = "" Then
							StartNewGame()
						Else
							Steam_API_SetAchievement("AchvMultiplayer")
						EndIf
						Return 1
					Case M_CONNECTVERSION
						AddErrorLog("Version doesn't match", 255, 0, 0)
						AddErrorLog("Your version: v"+MULTIPLAYER_VERSION, 255, 0, 0)
						AddErrorLog("Server version: v"+udp_ReadLine(), 255, 0, 0)
						UDP_SetStream(udp_network, 0)
						Return 0
					Case M_CONNECTBANIP
						AddErrorLog("You are banned", 255, 0, 0)
						UDP_SetStream(udp_network, 0)
						Return 0
					Case M_CONNECTNAME
						AddErrorLog("Change your name", 255, 0, 0)
						UDP_SetStream(udp_network, 0)
						Return 0
					Case M_CONNECTPASSWORD
						AddErrorLog("Wrong password", 255, 0, 0)
						UDP_SetStream(udp_network, 0)
						Return 0
					Case M_CONNECTERROR
						currerr$ = udp_ReadLine()
						If Instr(currerr, "password") Then
							PasswordMenu = True
							AddServerMenu = False
							ConnectMenu = False
						EndIf
						While currerr <> ""
							AddErrorLog(currerr, 255, 0, 0)
							currerr$ = udp_ReadLine()
							
							If Instr(currerr, "password") Then
								PasswordMenu = True
								AddServerMenu = False
								ConnectMenu = False
							EndIf
						Wend
						
						;AddErrorLog("is already on the serveis already on the server fucking serveis already on the server fucking serveis already on the server fucking serveis already on the server fucking serveis already on the server fucking serveis already on the server fucking serveis already on the server fucking server fucking server", 12, 255, 100, 100000)
						UDP_SetStream(udp_network, 0)
						Return 0
				End Select
			Wend
		EndIf
	Else
		If STEAM_RELEASE Then
			If (Not udp_GetStream())  Then
				Local RecvBank = CreateBank(4)
				While BS_ISteamNetworking_IsP2PPacketAvailable(BS_SteamNetworking(), RecvBank, 0)
					udp_ClearReadAvail()
					
					udp_network\recv = UDP_GetSteamReceive()
					udp_network\availread = PeekInt(RecvBank, 0)
					
					udp_network\msgip = udp_network\recv
					udp_network\msgport = 0
					
					BS_ISteamNetworking_ReadP2PPacket(BS_SteamNetworking(), udp_network\bufferreceive, udp_network\availread, RecvBank, udp_network\recv, 0)

					If udp_ReadByte() = STEAM_CONNECT Then 
						multiplayer_ConnectTo(udp_UDPMsgIP(), 0, "", True)
						Exit
					EndIf
				Wend
				FreeBank RecvBank
			EndIf
		EndIf
	EndIf
End Function
Function multiplayer_Update()
	If ONLINE_UPDATE < MilliSecs() Then
		SendStatisticRequest(PLAYER_GO_ONLINE)
		ONLINE_UPDATE = MilliSecs()+60000
	EndIf

	steam_Update()
	Discord_API_Update()
	multiplayer_list_ServerListUDPMsgs()
	multiplayer_UpdateConnection()
	If udp_GetStream() Then Return multiplayer_main()
End Function
Function multiplayer_GetPlayersCount()
	Local count
	For i = 1 To NetworkServer\maxplayers
		If Player[i] <> Null Then 
			If multiplayer_CheckTimeout(i) = False Then count = count + 1
		EndIf
	Next
	Return count
End Function
Function multiplayer_UpdateRoomObjects()
	If PlayerRoom = Null Then Return
	If GrabbedEntity <> 0 Then
		For i = 0 To MaxRoomObjects-1
			If GrabbedEntity = PlayerRoom\Objects[i] Then
				If NetworkServer\MainPlayer Then 
					PlayerRoom\ObjectStatic[i] = False
				Else
					multiplayer_WritePlayerRoomObject(GrabbedEntity, i)
				EndIf
			EndIf
		Next
	EndIf
End Function
Function multiplayer_WriteNetwork()
	If NetworkServer\MainPlayer Then
		If NetworkServer\Hosted Then multiplayer_SetServerTime(MilliSecs()+100000)
		If udp_netout(1) Then
			NetworkServer\PlayersCount = multiplayer_GetPlayersCount()
			multiplayer_SendServerInformation()
			
			NetworkServer\NetworkTicks = ReverseTo(NetworkServer\NetworkTicks+1, 1, 5)
			multiplayer_WriteMiscellaneous(NetworkServer\NetworkTicks)
		EndIf
		If udp_netout(3) Then
			For i = 2 To NetworkServer\Maxplayers
				If Player[i] <> Null Then
					udp_WriteByte(M_PING)
					udp_WriteByte(1)
					udp_SendMessage(i)
					Player[i]\lastping = MilliSecs()
				EndIf
			Next
		EndIf
		
		ByteStreamReset(MicroByte)
	EndIf
	;EndIf
End Function
Global LastSendTick, TicksCount
Function multiplayer_Send(packetid, wait = -1, param = -1)
	If (Not udp_GetStream()) Return False
	If wait <> -1 Then
		If Not udp_netout(wait) Then Return False
	EndIf
	udp_WriteByte(packetid)
	udp_WriteByte(NetworkServer\MyID)
	Select packetid
		Case M_UPDATE
		
			udp_WriteByte(param)
			Select param
				Case 0
					udp_WriteByte((Ready="Ready"))
					udp_WriteByte(MyPlayer\IsLoad)
					udp_WriteByte NetworkServer\textsTicks
					udp_WriteByte NetworkServer\drawsTicks
					udp_WriteByte NetworkServer\objectsTicks
					udp_WriteByte NetworkServer\chatTicks
				Case 1
					udp_WriteShort converttoshort(MyPlayer\yaw)
					udp_WriteShort converttoshort(MyPlayer\BonePitch)
					udp_WriteFloat MyPlayer\BlinkTimer
					
					udp_WriteByte MyPlayer\wearingdir
					udp_WriteByte MyPlayer\PlayerSoundVolume
					udp_WriteByte MyPlayer\p_byte + (128 * (MyPlayer\ShouldSendShoot<>Null))
					udp_WriteByte MyPlayer\UsedWeapon
					udp_WriteShort MyPlayer\SelectedItem
					
					udp_WriteByte NetworkServer\textsTicks
					udp_WriteByte NetworkServer\drawsTicks
					udp_WriteByte NetworkServer\objectsTicks
					udp_WriteByte NetworkServer\chatTicks

					udp_WriteFloat Stamina
				
					udp_WriteByte MyPlayer\PLAYER_MOVE
					udp_WriteShort CurrentPositionID

					udp_WriteFloat EntityX(Collider)
					udp_WriteFloat EntityY(Collider)
					udp_WriteFloat EntityZ(Collider)
					udp_WriteByte MyPlayer\PlayerRoomID
					
					If MyPlayer\ShouldSendShoot <> Null Then
						udp_WriteBytes(GetByteStreamData(MyPlayer\ShouldSendShoot), 0, 23)
						RemoveByteStream(MyPlayer\ShouldSendShoot)
					EndIf
					
					udp_WriteByte MyPlayer\CurrentRadio
				
				Case 3
					udp_WriteByte MyPlayer\p_byte
			End Select
			
			udp_WriteBytes(GetByteStreamData(MicroByte), 0, GetByteStreamDataSize(MicroByte))
			ByteStreamReset(MicroByte)
	End Select
	udp_SendMessage()
	; ------------------------------ 0.5 version
	;if packetid = M_UPDATE Then
	;	if NetworkServer\MainPlayer Or rcon\authorized Then
	;		for p.players = Each players
	;			if p\IsABot <> 0 And (Not p\IsDead) Then
	;				udp_WriteByte(packetid)
	;				udp_WriteByte(p\ID)
	;				udp_WriteFloat EntityX(p\pivot)
	;				udp_WriteFloat EntityY(p\pivot)
	;				udp_WriteFloat EntityZ(p\pivot)
	;				udp_WriteFloat EntityYaw(p\pivot)
	;				udp_WriteFloat 0
	;
	;				udp_WriteByte m_NPC[p\IsABot]\PLAYER_MOVE
	;				udp_WriteShort -15 ; blink timer
	;				
	;				udp_WriteByte 0
	;				
	;				udp_WriteByte 1.0 ; playersoundvolume
	;				udp_WriteByte 0
	;				udp_WriteByte((1) + (2 * 0) + (4 * 0) + (8 * 0) + (16 * M_NPC[p\IsABot]\isDead))
	;				udp_WriteByte(0)
	;				udp_WriteByte 0
	;				udp_WriteByte 0
	;				udp_WriteByte 0
	;				udp_WriteByte 0
	;				udp_WriteByte 0
	;				udp_WriteFloat 0
	;				
	;				udp_WriteByte NetworkServer\textsTicks
	;				udp_WriteByte NetworkServer\drawsTicks
	;				udp_WriteByte NetworkServer\objectsTicks
	;				udp_WriteByte NetworkServer\chatTicks
	;				
	;				udp_SendMessage()
	;			EndIf
	;		Next
	;	EndIf
	;EndIf
	Return True
End Function

Function ReadBool(byte, index)
	Return ((byte Shr index) Mod 2)
End Function

Function multiplayer_WriteMiscellaneous(byte)
	If MainMenuOpen Then Return
	
	For i = 2 To NetworkServer\MaxPlayers
		If Player[i] <> Null Then
			If Player[i]\IsLoad Then
				udp_WriteByte(m_miscellaneous)
				udp_WriteByte(1)
				udp_WriteByte(byte)
				Select NETWORK_BYTE[byte]
					Case M_NPCSYNC
						multiplayer_WriteNPCs(i)
					Case M_SYNCDOOR
						multiplayer_WriteDoors(i)
					Case M_FIXEDITEMS
						multiplayer_WriteItems(i)
					Case M_EVENTSWRITE
						multiplayer_WriteEvents()
					Case M_ROOMOBJECTS
						multiplayer_WriteRoomObjects()
				End Select
				udp_SendMessage(i)
			EndIf
		EndIf
	Next
End Function
Function multiplayer_WritePlayerRoomObject(obj, id)
	udp_ByteStreamWriteChar(M_ROTATEENTITY)
	udp_ByteStreamWriteChar(id)
	udp_ByteStreamWriteFloat(EntityPitch(obj, True))
	udp_ByteStreamWriteFloat(EntityYaw(obj, True))
	udp_ByteStreamWriteFloat(EntityRoll(obj, True))
	udp_SetMicroByte(M_ROTATEENTITY)
End Function
Function multiplayer_WriteRoomObject(r.Rooms, id)
	If r\ObjectStatic[id] = True Then Return
	udp_WriteByte r\ID
	udp_WriteByte(id)
	udp_WriteFloat(EntityX(r\Objects[id],True))
	udp_WriteFloat(EntityY(r\Objects[id],True))
	udp_WriteFloat(EntityZ(r\Objects[id],True))
	udp_WriteFloat(EntityPitch(r\Objects[id], True))
	udp_WriteFloat(EntityYaw(r\Objects[id], True))
	udp_WriteFloat(EntityRoll(r\Objects[id], True))
End Function
Function multiplayer_WriteRoomObjects()
	For r.Rooms = Each Rooms
		If Not IsABlockedRoom(r) Then
			For i = 0 To MaxRoomObjects-1
				If r\Objects[i] <> 0 Then multiplayer_WriteRoomObject(r, i)
			Next
		EndIf
	Next
	udp_WriteByte(0)
End Function
Function multiplayer_WriteMisc(i)
	multiplayer_WriteRoomObjects()
	multiplayer_WriteDoors(i)
	multiplayer_WriteEvents()
End Function
Function multiplayer_WriteEvents()
	For e.Events = Each Events
		If Not IsABlockedEvent(e) Then
			udp_WriteByte e\ID
			udp_WriteByte e\EventConst
			udp_WriteByte e\room\ID
			udp_WriteFloat e\EventState
			udp_WriteFloat e\EventState2
			udp_WriteFloat e\EventState3
		EndIf
	Next
	udp_WriteByte 0
End Function
Function multiplayer_WriteNPCs(playerid)
	udp_WriteByte NoTarget
	For n.NPCs = Each NPCs
		If n\EventID <> 0 Or EntityDistance(n\Collider, Player[playerid]\Pivot) < HideDistance Or Curr106 = n Or Curr5131 = n Or Curr096 = n Or Curr173 = n Then
			udp_WriteByte(n\ID)
			udp_WriteByte(n\NPCtype)
			udp_WriteByte(n\Idle)
			
			udp_WriteFloat(n\State)
			udp_WriteFloat(n\State2)
			udp_WriteFloat(n\State3)
			
			udp_WriteFloat(EntityX(n\Collider,True))
			udp_WriteFloat(EntityY(n\Collider,True))
			udp_WriteFloat(EntityZ(n\Collider,True))
			udp_WriteFloat(EntityYaw(n\Collider, True))
			udp_WriteFloat(AnimTime(n\obj))
			
			udp_WriteByte(n\TextureID)
			udp_WriteByte(n\EventID)
			udp_WriteByte(n\NPCEventID)
			If n\Target <> Null Then udp_WriteByte(n\Target\ID) Else udp_WriteByte 0
		EndIf
	Next
	udp_WriteByte 0
End Function
Function multiplayer_WriteDoors(playerid)
	For d.Doors = Each Doors
		If EntityDistance(d\obj, Player[playerid]\Pivot) < HideDistance And d\IsElevatorDoor = 0 Then
			udp_WriteShort(d\ID)
			udp_WriteByte((d\open) + (2 * d\locked))
		EndIf
	Next
	udp_WriteShort(0)
End Function
Function multiplayer_WriteItems(playerid)
	Local toSend = False
	For it.Items = Each Items
		If it\picker <> 0 Then
			If Player[it\picker]\SelectedItem = it\ID Then toSend = True
		EndIf
		If EntityDistance(it\collider, Player[playerid]\Pivot) < HideDistance*0.5 Or it\picker = playerid Or toSend Then
			udp_WriteShort(it\ID)
			udp_WriteInt(it\itemtemplate\templateID)
			udp_WriteFloat(EntityX(it\collider, True))
			udp_WriteFloat(EntityY(it\collider, True))
			udp_WriteFloat(EntityZ(it\collider, True))
			udp_WriteByte(it\picker)
		EndIf
		toSend = False
	Next
	udp_WriteShort(0)
End Function
Function SetPlayerVariables()

	Local BT.breachtypes = GetBreachType(MyPlayer\BreachType)

	MyPlayer\name = NickName
	MyPlayer\IsDead = player_isdead()
	MyPlayer\x = EntityX(Collider)
	MyPlayer\y = EntityY(Collider)
	MyPlayer\z = EntityZ(Collider)
	MyPlayer\yaw = EntityYaw(Camera)
	MyPlayer\BonePitch = EntityPitch(Camera)
	MyPlayer\PLAYER_MOVE = PLAYER_MOVE
	MyPlayer\BlinkTimer = BlinkTimer
	
	If OutSCP = False Then MyPlayer\yaw = SavedAngle

	MyPlayer\WearingHazmat = (WearingHazmat<>0)
	MyPlayer\WearingNightVision = (WearingNightVision<>0)
	MyPlayer\WearingGasMask = (WearingGasMask<>0)
	MyPlayer\WearingVest = (WearingVest<>0)
	
	If BT\FixRotate = -180 Or BT\constid = HAOS_MODEL Then
		MyPlayer\WearingDir = (MyPlayer\WearingHazmat) + (2 * MyPlayer\WearingNightVision) + (4 * MyPlayer\WearingGasMask) + (8 * MyPlayer\WearingVest) + (16 * (GunRoll<0)) + (32*MyPlayer\HandCuffed) + (64*MyPlayer\MicroHIDSHOOT) + (128*(GunRoll>0))
	Else
		MyPlayer\WearingDir = (MyPlayer\WearingHazmat) + (2 * MyPlayer\WearingNightVision) + (4 * MyPlayer\WearingGasMask) + (8 * MyPlayer\WearingVest) + (16 * (GunRoll>0)) + (32*MyPlayer\HandCuffed) + (64*MyPlayer\MicroHIDSHOOT) + (128*(GunRoll<0))
	EndIf
	;MyPlayer\velocity = currSpeed+DropSpeed
	
	MyPlayer\PlayerSoundVolume = PlayerSoundVolume
	MyPlayer\CrouchState = CrouchState
	MyPlayer\UsedWeapon = HoldingGun
	MyPlayer\PlayerRoomName = PlayerRoom\RoomTemplate\Name
	MyPlayer\PlayerRoomID = PlayerRoom\ID
	MyPlayer\p_byte = (MyPlayer\IsLoad) + (2 * IsGunSighting()) + (4 * voice\Talking) + (8 * MenuOpen) + (16 * MyPlayer\IsDead) + (32 * GameLoad) + (64*MyPlayer\Announcement)
	MyPlayer\SelectedItem = GetItemID(SelectedItem)
	;MyPlayer\MoveDir = KeyBuffer(KEY_UP) + (KeyBuffer(KEY_DOWN) * 2) + (KeyBuffer(KEY_LEFT) * 4) + (KeyBuffer(KEY_RIGHT) * 8) + (KeyBuffer(KEY_SPRINT) * 16) + (Crouch * 32) + (Networkserver\jumped * 64) + (CollidedFloor * 128)
	If NetworkServer\Prediction Then Networkserver\jumped = False
	
	If Not NetworkServer\Breach Then 
		MyPlayer\Health = 100-(Injuries*10)
	EndIf
End Function

Function multiplayer_SendServerInformation()
	MyPlayer\ping = 5
	MyPlayer\Ready = Ready
	ServerPing = 5
	For i = 2 To NetworkServer\Maxplayers
		If Player[i] <> Null Then
			udp_WriteByte(M_SERVERSYNC)
			udp_WriteByte(1)
			udp_WriteByte(Not MainMenuOpen)
			udp_WriteByte(Player[i]\BreachType)
			udp_WriteFloat(NetworkServer\JumpHeight)
			udp_WriteInt(Max(-1, b_br\LobbyTimer-MilliSecs()))
			udp_WriteInt(Max(-1, b_br\BreachTimer-MilliSecs()))
			udp_WriteShort(Player[i]\ping)
			For i2 = 1 To NetworkServer\Maxplayers
				If Player[i2] <> Null And i2 <> i Then
					udp_WriteByte(i2)
					If Not MainMenuOpen = True Then
						udp_WriteByte Player[i2]\BreachType
						
						If Player[i2]\BreachType <> 0 Then
						
							udp_WriteFloat EntityX(Player[i2]\Pivot)
							udp_WriteFloat EntityY(Player[i2]\Pivot)-(0.32*(i2<>MyPlayer\ID))
							udp_WriteFloat EntityZ(Player[i2]\Pivot)
							udp_WriteShort converttoshort(Player[i2]\yaw)
							udp_WriteShort converttoshort(Player[i2]\BonePitch)
							udp_WriteByte Player[i2]\PLAYER_MOVE
							udp_WriteFloat Player[i2]\BlinkTimer
							udp_WriteByte Player[i2]\WearingDir
							
							udp_WriteByte Player[i2]\PlayerSoundVolume
							udp_WriteShort Player[i2]\ping
							udp_WriteByte Player[i2]\p_byte
							udp_WriteByte Player[i2]\UsedWeapon
							udp_WriteShort Player[i2]\SelectedItem
							udp_WriteByte Player[i2]\PlayerRoomID
						Else
							udp_WriteShort Player[i2]\ping
							udp_WriteByte Player[i2]\p_byte
						EndIf
					Else
						udp_WriteByte (Player[i2]\Ready="Ready")
						udp_WriteShort Player[i2]\ping
						udp_WriteByte Player[i2]\IsLoad
					EndIf
				EndIf
			Next
			udp_WriteByte(0)

			udp_WriteShort(ConvertToShort(SecondaryLightOn))
			udp_WriteByte(Contained106+(2*RemoteDoorOn))
			udp_WriteShort(MTFTimer)
			udp_WriteShort(converttoshort(ItemsRotateRand))
			udp_SendMessage(i)
		EndIf
	Next
End Function
Function multiplayer_CheckTimeout(i)
	If Player[i]\timeout < MilliSecs() And i <> NetworkServer\MyID Then
		multiplayer_CreateMessage(Player[i]\name+" timed out")
		
		For p.players = Each players
			If p\ID <> 1 Then
				udp_WriteByte M_CHAT
				udp_WriteByte 0
				udp_WriteLine Player[i]\name+" timed out"
				udp_WriteByte 1
				udp_SendMessage(p\ID)
			EndIf
		Next
		
		multiplayer_DisconnectPlayer(Player[i])
		NetworkServer\PlayersCount = NetworkServer\PlayersCount-1
		Return True
	EndIf
	If Player[i]\timeout < MilliSecs()+(NetworkServer\ServerTimeout-2000) And i <> NetworkServer\MyID Then Player[i]\p_byte = (ReadBool(Player[i]\p_byte, 0) + (2 * ReadBool(Player[i]\p_byte, 1)) + (4 * ReadBool(Player[i]\p_byte, 2)) + (8 * 1) + (16 * ReadBool(Player[i]\p_byte, 4)) + (32 * ReadBool(Player[i]\p_byte, 5))) + (64 * ReadBool(Player[i]\p_byte, 6))
	Return False
End Function
Function multiplayer_FindLocalServers(port)
	For i = 1 To CountHostIPs("")
		Local ip$ = DottedIP(HostIP(i))
		Local cancel = False
		For sc.servers = Each servers
			If sc\IP = ip And sc\port = port And sc\sType = SERVER_LOCAL Then cancel = True : Exit
		Next
		If Not cancel Then
			sc.servers = New servers
			sc\IP = ip
			sc\Port = port
			sc\Servername = ip+":"+port
			sc\ping = 0
			sc\map = "-"
			sc\players = "0 / 0"
			sc\state = "Offline"
			sc\stype = SERVER_LOCAL
			multiplayer_list_UpdateServer(sc)
		EndIf
	Next
End Function
Function multiplayer_RequestRole(role)
	

	If NetworkServer\MainPlayer Then 
		multiplayer_breach_SetPlayerType(role)
	Else
		udp_ByteStreamWriteChar M_WANTROLE
		udp_ByteStreamWriteChar role
		udp_SetMicroByte(M_WANTROLE)
		
		udp_WriteByte M_WANTROLE
		udp_WriteByte NetworkServer\MyID
		udp_WriteByte role
		udp_SendMessage()
	EndIf
End Function
Function multiplayer_RequestHealthReduce(health, reason$)
	udp_ByteStreamWriteChar M_HEALTHREDUCE
	udp_ByteStreamWriteLine reason$
	udp_ByteStreamWriteShort health
	udp_SetMicroByte(M_HEALTHREDUCE)
End Function

Function multiplayer_InitSettingsForPlayer(p.players, playertype, playertexture, playerhazmat, playerbyte)

	
	p\IsLoad = ReadBool(playerbyte, 0)
	p\Sighting = readbool(playerbyte, 1)
	p\CrouchState = (p\PLAYER_MOVE>=5 And p\PLAYER_MOVE <= 10)
	
	If MainMenuOpen Then Return
	
	Local toInit% = False
	
	If playertype <> p\BreachType Or (ReadBool(playerbyte, 4) = True And (Not p\isdead)) Then
		If NetworkServer\Breach Or (Not NetworkServer\keepitems) Then
			For it.Items = Each Items
				If it\picker = p\ID And it\picker <> 0 Then
					PlayerDropItem(it)
				EndIf
			Next
		EndIf
		
		Local CanSpawnBody% = (p\BreachType>0)
		
		If playertype = MODEL_ZOMBIE And p\BreachType <> MODEL_ZOMBIE Then CanSpawnBody = False
		If playertype = MODEL_035 And p\BreachType <> MODEL_035 Then CanSpawnBody = False

		If CanSpawnBody Then
			CreateRoleCorpse(p\BreachType, EntityX(p\Pivot), EntityY(p\Pivot), EntityZ(p\Pivot), EntityYaw(p\Pivot), p\size)
		Else
			If p\BreachType <> MODEL_035 Then
				For p2.players = Each players
					If p2\BreachType = MODEL_049 Then
						If p2\obj <> 0 Then
							If EntityDistance(p2\Pivot, p\Pivot) < 4 Then
								p2\PLAYER_MOVE_TIMED = PLAYER_BITE
								SetAnimTime(p2\obj, 0)
							EndIf
						EndIf
					EndIf
				Next
			EndIf
		EndIf
		p\BreachType = playertype
		
		toInit = True
		If p\BreachType = MODEL_939 Then 
			EntityRadius p\Pivot, 0.3
		Else
			EntityRadius p\Pivot, 0.15, 0.3
		EndIf
		If NetworkServer\MainPlayer = True Then
			Select playertype
				Case HAOS_MODEL,NTF_MODEL
					If Rand(3) = 1 Then 
						GiveItem("FN P90", "p90",p\ID) 
					Else
						GiveItem("MP5-SD", "mp5sd",p\ID)
					EndIf
					GiveItem("USP Tactical", "usp",p\ID)
					GiveItem("Key Card Omni", "key6",p\ID)
					GiveItem("Radio Transceiver", "radio", p\ID)
					GiveItem("Grenade", "grenade", p\ID)
					GiveItem("Grenade", "grenade", p\ID)
					GiveItem("Strange Bottle", "veryfinefirstaid",p\ID)
					GiveItem("Night Vision Goggles", "supernv", p\ID)
					If Rand(234) = 1 Then GiveItem("Rocket Launcher", "rpg",p\ID)
				Case GUARD_MODEL
					If Rand(3) = 1 Then 
						GiveItem("FN P90", "p90",p\ID) 
					Else 
						GiveItem("MP5-SD", "mp5sd",p\ID)
					EndIf
					GiveItem("USP Tactical", "usp",p\ID)
					GiveItem("Level 5 Key Card", "key5",p\ID)
					GiveItem("Radio Transceiver", "radio", p\ID)
					GiveItem("Grenade", "grenade", p\ID)
					GiveItem("Strange Bottle", "veryfinefirstaid",p\ID)
					If Rand(234) = 1 Then GiveItem("Rocket Launcher", "rpg",p\ID)
				Case SCIENTIST_MODEL,MODEL_CLERK
					GiveItem("Level 2 Key Card", "key2",p\ID)
				Case JANITOR_MODEL,WORKER_MODEL
					GiveItem("Level 1 Key Card", "key1",p\ID)
				Default
					If multiplayer_IsASCP(playertype) Then GiveItem("Level 5 Key Card", "key5",p\ID)
			End Select
		EndIf
		
		
	EndIf
	If playertexture <> p\TextureID And playertype = CLASSD_MODEL And (Not p\WearingHazmat) Then
		p\TextureID = playertexture
		toInit = True
	EndIf
	If playerhazmat <> p\WearingHazmat Then 
		p\WearingHazmat = playerhazmat
		toInit = True
	EndIf
	If toInit Then multiplayer_InitPlayer(p\ID)
	playerannounc = ReadBool(playerbyte, 6)
	If playerannounc = True And p\Announcement = False Then PlaySound_Strict(LoadTempSound("GFX\multiplayer\game\sounds\Announcement.ogg"))
	If playerannounc = False And p\Announcement = True Then PlaySound_Strict(LoadTempSound("GFX\multiplayer\game\sounds\Announcement2.ogg"))
	p\Announcement = playerannounc
	p\AFK = ReadBool(playerbyte, 3)
	p\IsDead = ReadBool(playerbyte, 4)
	
	If p\steamid <> 0 And (Not p\setplaywith) Then
		BS_ISteamFriends_SetPlayedWith(BS_SteamFriends(), udp_FillSteam(p\steamid))
		p\setplaywith = True
	EndIf
	
	If Distance3(p\x, p\y, p\z, EntityX(p\Pivot), EntityY(p\Pivot), EntityZ(p\Pivot)) >= 5 Then
		PositionEntity(p\Pivot, p\x, p\y+0.32, p\z)
		ResetEntity p\Pivot
	EndIf
	
End Function

Function host_UpdatePlayer(i, playertexture, playerhazmat, playerbyte)
	Player[i]\IsLoad = ReadBool(playerbyte, 0)
	Player[i]\CrouchState = (Player[i]\PLAYER_MOVE>=5 And Player[i]\PLAYER_MOVE <= 10)
	If MainMenuOpen Then Return
	Local toInit% = False
	If Player[i]\PrevBreachType <> Player[i]\BreachType Or (ReadBool(playerbyte, 4) = True And (Not Player[i]\isdead)) Then
		If NetworkServer\Breach Or (Not NetworkServer\keepitems) Then
			For it.Items = Each Items
				If it\picker = i And it\picker <> 0 Then
					PlayerDropItem(it)
				EndIf
			Next
		EndIf
		
		CreateRoleCorpse(Player[i]\PrevBreachType, EntityX(Player[i]\Pivot), EntityY(Player[i]\Pivot), EntityZ(Player[i]\Pivot), EntityYaw(Player[i]\Pivot), Player[i]\size)

		Player[i]\PrevBreachType = Player[i]\BreachType
		toInit = True
		If NetworkServer\MainPlayer = True Then
			Select Player[i]\BreachType
				Case HAOS_MODEL,NTF_MODEL
					If Rand(3) = 1 Then 
						GiveItem("FN P90", "p90",i) 
					Else
						GiveItem("MP5-SD", "mp5sd",i)
					EndIf
					GiveItem("USP Tactical", "usp",i)
					GiveItem("Key Card Omni", "key6",i)
					GiveItem("Radio Transceiver", "radio", i)
					GiveItem("Grenade", "grenade", i)
					GiveItem("Grenade", "grenade", i)
					If Rand(3) = 1 Then GiveItem("First Aid Kit", "firstaid",i)
					If Rand(4) = 1 Then GiveItem("Rocket Launcher", "rpg",i)
				Case GUARD_MODEL
					If Rand(3) = 1 Then 
						GiveItem("FN P90", "p90",i) 
					Else 
						GiveItem("MP5-SD", "mp5sd",i)
					EndIf
					GiveItem("USP Tactical", "usp",i)
					GiveItem("Level 5 Key Card", "key5",i)
					GiveItem("Radio Transceiver", "radio", i)
					GiveItem("Grenade", "grenade", i)
					If Rand(3) = 1 Then GiveItem("First Aid Kit", "firstaid",i)
					If Rand(8) = 1 Then GiveItem("Rocket Launcher", "rpg",i)
				Case SCIENTIST_MODEL,MODEL_CLERK
					GiveItem("Level 2 Key Card", "key2",i)
				Case JANITOR_MODEL,WORKER_MODEL
					GiveItem("Level 1 Key Card", "key1",i)
				Default
					If multiplayer_IsASCP(Player[i]\BreachType) Then GiveItem("Level 5 Key Card", "key5",i)
			End Select
		EndIf
	EndIf
	If playertexture <> Player[i]\TextureID And Player[i]\BreachType = CLASSD_MODEL And (Not Player[i]\WearingHazmat) Then
		Player[i]\TextureID = playertexture
		toInit = True
	EndIf
	If playerhazmat <> Player[i]\WearingHazmat Then 
		Player[i]\WearingHazmat = playerhazmat
		toInit = True
	EndIf
	If toInit Then multiplayer_InitPlayer(i)
	Player[i]\AFK = ReadBool(playerbyte, 3)
	Player[i]\IsDead = ReadBool(playerbyte, 4)
End Function

Function multiplayer_breach_IsASCP(bType)
	
End Function

; Breach Roles Custom System
Function multiplayer_breach_CreatePlayerRole%(name$, model, scale#, texture$, r,g,b, hp=0)
	Local br.breachtypes = New breachtypes
	br\name = name
	br\constid = LAST_BREACH_TYPE
	br\model = model
	br\scale = scale
	br\r = r
	br\g = g
	br\b = b
	br\texture = texture
	br\consthp = hp
	
	; defaults
	
	br\BoneName[BONE_HEAD] = "Bip01_Head"
	br\BoneName[BONE_HAND] = "FIRESPOT"
	br\BoneName[BONE_SPINE] = "Bip01_Spine1"
	br\BoneName[BONE_SPINE2] = "Bip01_Spine2"
	br\BoneName[BONE_FOREARM] = "Bip01_R_Forearm"
	br\AllowJump = True
	br\FixPivot = 0.3
	br\FixRotate = -180
	br\FixPitch = 0
	br\CopiedGroup = br\constid
	br\CopiedSpawn = br\constid
	;
	
	BreachTypesArray[br\constid] = br
	
	LAST_BREACH_TYPE = LAST_BREACH_TYPE+1
	Return br\constid
End Function

Function GetBreachType.BreachTypes(id)
	If BreachTypesArray[id] = Null Then Return BreachTypesArray[CLASSD_MODEL]
	Return BreachTypesArray[id]
End Function
Function SetTypeColor(bType)
	Local BT.BreachTypes = GetBreachType(bType)
	Color bt\r, bt\g, bt\b
	Return True
End Function
Function GetTypeName$(bType)
	Local BT.BreachTypes = GetBreachType(bType)
	Return BT\Name
End Function
Function SetTypeText(x, y)
	Local BT.BreachTypes = GetBreachType(MyPlayer\BreachType)
	AASetFont Font1
	AAText(x+1, y + 1, BT\Instructions, True, False, Min(b_br\YouAreTimer / 2, 255)/255.0)
	AAText(x, y , BT\Instructions, True, False, Min(b_br\YouAreTimer / 2, 255)/255.0)
End Function

Function multiplayer_breach_SetRoleSettings(role%, allowupdate%, attacktype%, attackinterval, attackradius#, haveattacksound, sound$, soundinterval, randomsounds%=0)
	Local BT.BreachTypes = GetBreachType(role)
	bt\AllowUpdate = allowupdate
	bt\AllowAttack = attacktype%
	bt\hitinterval = attackinterval
	bt\attackradius = attackradius
	bt\soundinterval = soundinterval
	bt\sound = sound
	bt\randomsounds = randomsounds
	bt\attacksound = haveattacksound
End Function

Function multiplayer_breach_SetRoleEffects(role%, cameraoffset#, boneheadroleoffset#, boneheadyawoffset#, flipboneheadpitch%, allowjump, usualsprint, disablecrouch, disablegodmode, disableinjuries, disablesprint)
	Local BT.BreachTypes = GetBreachType(role)
	bt\allowjump = allowjump
	bt\usualsprint = usualsprint
	bt\disablecrouch = disablecrouch
	bt\disablegodmode = disablegodmode
	bt\disableinjuries = disableinjuries
	bt\disablesprint = disablesprint
	bt\cameraoffset = cameraoffset
	bt\rolloffset = boneheadroleoffset
	bt\RoleYawOffset = boneheadyawoffset
	bt\HeadPitchFlip = flipboneheadpitch
End Function

Function multiplayer_breach_SetRoleAmbientSound(role%, animationid%, sound$)
	Local BT.BreachTypes = GetBreachType(role)
	bt\AmbientSoundAnims[animationid] = sound
End Function

Function multiplayer_breach_SetRoleInstruction(roleid, txt$)
	Local BT.BreachTypes = GetBreachType(roleid)
	BT\Instructions = txt
End Function

Function multiplayer_breach_SetRoleDeadAnimation(role, FirstFrame, EndFrame)
	Local BT.BreachTypes = GetBreachType(role)
	BT\DeadAnimationData = PackTwoValues(FirstFrame, EndFrame)
End Function

Function multiplayer_breach_SetRoleBone(role, boneid, name$)
	Local BT.BreachTypes = GetBreachType(role)
	BT\BoneName[boneid] = name
End Function

Function multiplayer_breach_SetRoleAnimation(role, animationid, FirstFrame, EndFrame, Speed#)
	Local BT.BreachTypes = GetBreachType(role)
	BT\AnimationFrameData[animationid] = PackTwoValues(FirstFrame, EndFrame)
	BT\AnimationSpeed[animationid] = Speed
End Function

Function multiplayer_breach_SetRoleArmedAnimation(role, animationid, FirstFrame, EndFrame, Speed#)
	Local BT.BreachTypes = GetBreachType(role)
	BT\AnimationArmedFrameData[animationid] = PackTwoValues(FirstFrame, EndFrame)
	BT\AnimationArmedSpeed[animationid] = Speed
End Function
Function multiplayer_breach_MarkAsFriend(role, friendrole, bool=True)
	Local BT.BreachTypes = GetBreachType(role)
	BT\IsAFriend[friendrole] = bool
End Function
Function multiplayer_breach_RoleTakeRoleSpawn(myrole, role)
	Local BT.BreachTypes = GetBreachType(myrole)
	BT\CopiedSpawn = role
End Function
Function multiplayer_breach_SetRolePositionsOffset(role, FixPivot#, FixRotate#, FixPitch#)
	Local BT.BreachTypes = GetBreachType(role)
	BT\FixPivot = FixPivot
	BT\FixRotate = FixRotate
	BT\FixPitch = FixPitch
End Function
Function multiplayer_breach_SetRoleHoldingGrenade(role, bonename$, bonex#, boney#)
	Local BT.BreachTypes = GetBreachType(role)
	BT\HoldingGrenade = (Float(bonex+boney)<>0.0)
	BT\HoldingGrenadeData[0] = bonename
	BT\HoldingGrenadeData[1] = Str(bonex)
	BT\HoldingGrenadeData[2] = Str(boney)
End Function
Function multiplayer_breach_SetRoleHoldingItem(role, bonex#, boney#)
	Local BT.BreachTypes = GetBreachType(role)
	BT\HoldingItem = (Float(bonex+boney)<>0.0)
	BT\HoldingItemData[0] = bonex
	BT\HoldingItemData[1] = boney
End Function
Function multiplayer_breach_SetRoleHandcuff(role, bone1$, bonex1#, boney1#, bonez1#, bone2$, bonex2#, boney2#, bonez2#)
	Local BT.BreachTypes = GetBreachType(role)
	BT\Handcuffs = (bone1<>"" Or bone2<>"")
	BT\HandCuffData[0] = bone1
	BT\HandCuffData[1] = Str(bonex1)
	BT\HandCuffData[2] = Str(boney1)
	BT\HandCuffData[3] = Str(bonez1)
	
	BT\HandCuffData[4] = bone2
	BT\HandCuffData[5] = Str(bonex2)
	BT\HandCuffData[6] = Str(boney2)
	BT\HandCuffData[7] = Str(bonez2)
End Function
Function multiplayer_breach_AllowRoleWeaponAttaches(role, bool)
	Local BT.breachtypes = GetBreachType(role)
	BT\AllowWeaponsAttaches = bool
End Function
Function multiplayer_breach_AllowItemsAttaches(role, bool)
	Local BT.breachtypes = GetBreachType(role)
	BT\AllowItemsAttaches = bool
End Function
Function multiplayer_breach_MarkRoleAsSCP(role, bool=True)
	Local BT.breachtypes = GetBreachType(role)
	BT\IsASCP = bool
	If bool = True And BT\IsA035 Then BT\IsASCP = False
End Function
Function multiplayer_breach_MarkAs035(role, bool=True)
	Local BT.breachtypes = GetBreachType(role)
	BT\IsA035 = bool
	If bool = True Then BT\IsASCP = False
End Function
Function multiplayer_breach_MarkAs049(role, bool=True)
	Local BT.breachtypes = GetBreachType(role)
	BT\IsA049 = bool
End Function
Function multiplayer_breach_IsA049(role)
	Local BT.breachtypes = GetBreachType(role)
	Return BT\IsA049
End Function
Function multiplayer_breach_IsA035(role)
	Local BT.breachtypes = GetBreachType(role)
	Return BT\IsA035
End Function
Function multiplayer_breach_GetRoleScale#(role)
	Local BT.breachtypes = GetBreachType(role)
	Return BT\Scale
End Function

Function multiplayer_IsAFriend(myrole, role)
	Local BT.BreachTypes = GetBreachType(myrole)
	Return BT\IsAFriend[role]
End Function

Function multiplayer_breach_SetRoleHitboxScales(constid, x#, y#, z#)
	Local BT.BreachTypes = GetBreachType(constid)
	BT\hitboxx = x
	BT\hitboxy = y
	BT\hitboxz = z
End Function

Function multiplayer_breach_CreateViewmodelForRole(role, viewmodel$, y#, z#, scale#, zscale#=0.0)

	If zscale = 0.0 Then zscale = scale
	Local BT.BreachTypes = GetBreachType(role)
	If BT\Viewmodel <> 0 Then FreeEntity(BT\Viewmodel)
	BT\Viewmodel = LoadAnimMesh_Strict(viewmodel)
	ScaleEntity BT\ViewModel, scale, scale, zscale
	EntityParent BT\Viewmodel, ViewmodelPivot
	
	MoveEntity BT\Viewmodel, 0, y, z
	
	HideEntity BT\Viewmodel
End Function

Function multiplayer_breach_SetRoleViewmodelAnimation(role, animationid, FirstFrame, EndFrame, Speed#)
	Local BT.BreachTypes = GetBreachType(role)
	BT\AnimationViewmodelFrameData[animationid] = PackTwoValues(FirstFrame, EndFrame)
	BT\AnimationViewmodelSpeed[animationid] = Speed
End Function

;Function multiplayer_ShowSCPVIewmodel()
;	CurrentType% = MyPlayer\BreachType
;	BT = GetBreachType(CurrentType)
;	MyPlayer\prevbreachtypevm = CurrentType
;
;	If ShowSCPViewmodel Then
;		If multiplayer_IsASCP(CurrentType) Then
;				ShowEntity BT\Viewmodel
;			Else
;				HideEntity BT\Viewmodel
;		End If
;	End If
;	
;End Function

Function multiplayer_AnimateViewmodel()
	;multiplayer_ShowSCPVIewmodel()
	CurrentType% = MyPlayer\BreachType
	
	If Spectate\SpectatePlayer <> -1 Then 
		If Spectate\SpectateType = 1 Then
			CurrentType = Player[Spectate\SpectatePlayer]\BreachType
		EndIf
	EndIf

	If Not ShowSCPViewmodel Then CurrentType = 0
	Local BT.BreachTypes
	
	If MyPlayer\prevbreachtypevm <> CurrentType Then
		BT = GetBreachType(MyPlayer\prevbreachtypevm)
		If BT\Viewmodel <> 0 Then HideEntity(BT\Viewmodel)
	EndIf
	
	MyPlayer\prevbreachtypevm = CurrentType
	
	BT = GetBreachType(CurrentType)
	
	
	If BT\Viewmodel <> 0 Then
		If Spectate\SpectatePlayer <> -1 Then
			If Spectate\SpectateType = 1 Then
				If Player[Spectate\SpectatePlayer] <> Null Then
					If Player[Spectate\SpectatePlayer]\PLAYER_MOVE < MAX_ROLES_ANIMATIONS Then
						PREV_PLAYER_MOVE = PLAYER_MOVE
						PLAYER_MOVE = Player[Spectate\SpectatePlayer]\PLAYER_MOVE
						If PLAYER_MOVE_TIMED = 0 Then PLAYER_MOVE_TIMED = Player[Spectate\SpectatePlayer]\PLAYER_MOVE_TIMED
					EndIf
				EndIf
			EndIf
		EndIf
	
		PositionEntity ViewmodelPivot, EntityX(Camera), EntityY(Camera), EntityZ(Camera)
		RotateEntity(ViewmodelPivot, EntityPitch(Camera), EntityYaw(Camera), EntityRoll(Camera))
		
		ShowEntity(BT\Viewmodel)
		
		Select CurrentType
			Case MODEL_096
				
				If PLAYER_MOVE_TIMED = 0 Then
					If PREV_PLAYER_MOVE <> PLAYER_MOVE Then
						Select PREV_PLAYER_MOVE
							Case PLAYER_IDLING, PLAYER_WALKING,PLAYER_RUNNING,PLAYER_JUMPING
								Select PLAYER_MOVE
									Case PLAYER_CRYING
										PLAYER_MOVE_TIMED = PLAYER_TO_CRYING
										SetAnimTime(BT\Viewmodel, GetFirstPackedValue(BT\AnimationViewmodelFrameData[PLAYER_MOVE_TIMED]))
								End Select
							Case PLAYER_CRYING
								Select PLAYER_MOVE
									Case PLAYER_RUNNING
										PLAYER_MOVE_TIMED = PLAYER_TO_ANGRY
										SetAnimTime(BT\Viewmodel, GetFirstPackedValue(BT\AnimationViewmodelFrameData[PLAYER_MOVE_TIMED]))
									Case PLAYER_IDLING,PLAYER_WALKING
										PLAYER_MOVE_TIMED = PLAYER_FROM_CRYING
										SetAnimTime(BT\Viewmodel, GetSecondPackedValue(BT\AnimationViewmodelFrameData[PLAYER_MOVE_TIMED]))
								End Select
						End Select
						
						Select PREV_PLAYER_MOVE
							Case PLAYER_IDLING,PLAYER_SITTING_IDLING
								Select PLAYER_MOVE
									Case PLAYER_WALKING,PLAYER_SITTING_WALKING_BACK,PLAYER_SITTING_WALKING_FORWARD,PLAYER_SITTING_WALKING_LEFT,PLAYER_SITTING_WALKING_RIGHT
										PLAYER_MOVE_TIMED = PLAYER_TO_WALK
										SetAnimTime(BT\Viewmodel, GetFirstPackedValue(BT\AnimationViewmodelFrameData[PLAYER_MOVE_TIMED]))
								End Select
							Case PLAYER_WALKING,PLAYER_SITTING_WALKING_BACK,PLAYER_SITTING_WALKING_FORWARD,PLAYER_SITTING_WALKING_LEFT,PLAYER_SITTING_WALKING_RIGHT
								Select PLAYER_MOVE
									Case PLAYER_IDLING,PLAYER_SITTING_IDLING
										PLAYER_MOVE_TIMED = PLAYER_FROM_WALK
										SetAnimTime(BT\Viewmodel, GetSecondPackedValue(BT\AnimationViewmodelFrameData[PLAYER_MOVE_TIMED]))
								End Select
						End Select
						Return
					EndIf
				EndIf
				
				If PLAYER_MOVE_TIMED <> 0 Then
					If GetSecondPackedValue(BT\AnimationViewmodelFrameData[PLAYER_MOVE_TIMED]) <> 0 Then
						Animate2(BT\Viewmodel, AnimTime(BT\Viewmodel), GetFirstPackedValue(BT\AnimationViewmodelFrameData[PLAYER_MOVE_TIMED]), GetSecondPackedValue(BT\AnimationViewmodelFrameData[PLAYER_MOVE_TIMED]), BT\AnimationViewmodelSpeed[PLAYER_MOVE_TIMED], False)
						
						If BT\AnimationViewmodelSpeed[PLAYER_MOVE_TIMED] >= 0.0 Then
							If AnimTime(BT\Viewmodel) >= GetSecondPackedValue(BT\AnimationViewmodelFrameData[PLAYER_MOVE_TIMED])-2.0 Then
								Select PLAYER_MOVE_TIMED
									Case PLAYER_TO_CRYING
										Select PLAYER_MOVE
											Case PLAYER_RUNNING
												PLAYER_MOVE_TIMED = PLAYER_FROM_CRYING
												Return
										End Select
								End Select
								PLAYER_MOVE_TIMED = 0
							EndIf
							Return
						Else
							If GetFirstPackedValue(BT\AnimationViewmodelFrameData[PLAYER_MOVE_TIMED])+2.0 >= AnimTime(BT\Viewmodel) Then 
								PLAYER_MOVE_TIMED = 0
							EndIf
							Return
						EndIf
					Else
						PLAYER_MOVE_TIMED = 0
					EndIf
				EndIf
			Default
				If PLAYER_MOVE_TIMED = 0 Then
					If PREV_PLAYER_MOVE <> PLAYER_MOVE Then
						;debuglog PREV_PLAYER_MOVE + " | "+PLAYER_MOVE
						Select PREV_PLAYER_MOVE
						
							Case PLAYER_IDLING,PLAYER_SITTING_IDLING
								Select PLAYER_MOVE
									Case PLAYER_RUNNING,PLAYER_WALKING,PLAYER_SITTING_WALKING_BACK,PLAYER_SITTING_WALKING_FORWARD,PLAYER_SITTING_WALKING_LEFT,PLAYER_SITTING_WALKING_RIGHT,PLAYER_JUMPING
										PLAYER_MOVE_TIMED = PLAYER_TO_WALK
										SetAnimTime(BT\Viewmodel, GetFirstPackedValue(BT\AnimationViewmodelFrameData[PLAYER_MOVE_TIMED]))
								End Select
								
							Case PLAYER_RUNNING,PLAYER_WALKING,PLAYER_SITTING_WALKING_BACK,PLAYER_SITTING_WALKING_FORWARD,PLAYER_SITTING_WALKING_LEFT,PLAYER_SITTING_WALKING_RIGHT,PLAYER_JUMPING
								Select PLAYER_MOVE
									Case PLAYER_IDLING,PLAYER_SITTING_IDLING
										PLAYER_MOVE_TIMED = PLAYER_FROM_WALK
										SetAnimTime(BT\Viewmodel, GetSecondPackedValue(BT\AnimationViewmodelFrameData[PLAYER_MOVE_TIMED]))
								End Select
								
						End Select
						;debuglog "Current move: "+PLAYER_MOVE_TIMED+ " | "+AnimTime(BT\Viewmodel)+ " | "+GetSecondPackedValue(BT\AnimationViewmodelFrameData[PLAYER_MOVE_TIMED])
						Return
					EndIf
				EndIf
				
				If PLAYER_MOVE_TIMED <> 0 Then
					If GetSecondPackedValue(BT\AnimationViewmodelFrameData[PLAYER_MOVE_TIMED]) <> 0 Then
						Animate2(BT\Viewmodel, AnimTime(BT\Viewmodel), GetFirstPackedValue(BT\AnimationViewmodelFrameData[PLAYER_MOVE_TIMED]), GetSecondPackedValue(BT\AnimationViewmodelFrameData[PLAYER_MOVE_TIMED]), BT\AnimationViewmodelSpeed[PLAYER_MOVE_TIMED], False)

						If BT\AnimationViewmodelSpeed[PLAYER_MOVE_TIMED] >= 0.0 Then
							If AnimTime(BT\Viewmodel) >= GetSecondPackedValue(BT\AnimationViewmodelFrameData[PLAYER_MOVE_TIMED])-2.0 Then
								Select PLAYER_MOVE_TIMED
									Case PLAYER_TO_WALK
										Select PLAYER_MOVE
											Case PLAYER_IDLING,PLAYER_SITTING_IDLING
												PLAYER_MOVE_TIMED = PLAYER_FROM_WALK
												SetAnimTime(BT\Viewmodel, GetSecondPackedValue(BT\AnimationViewmodelFrameData[PLAYER_MOVE_TIMED]))
												Return
										End Select
									Case PLAYER_BITE
										Select PLAYER_MOVE
											Case PLAYER_RUNNING,PLAYER_WALKING,PLAYER_SITTING_WALKING_BACK,PLAYER_SITTING_WALKING_FORWARD,PLAYER_SITTING_WALKING_LEFT,PLAYER_SITTING_WALKING_RIGHT,PLAYER_JUMPING
												PLAYER_MOVE_TIMED = PLAYER_TO_WALK
												SetAnimTime(BT\Viewmodel, GetSecondPackedValue(BT\AnimationViewmodelFrameData[PLAYER_MOVE_TIMED]))
												Return
											Case PLAYER_IDLING,PLAYER_SITTING_IDLING
												PLAYER_MOVE_TIMED = PLAYER_FROM_WALK
												SetAnimTime(BT\Viewmodel, GetSecondPackedValue(BT\AnimationViewmodelFrameData[PLAYER_MOVE_TIMED]))
												Return
										End Select
								End Select
								PLAYER_MOVE_TIMED = 0
								;debuglog "Doned."
							EndIf
						Else
							;debuglog AnimTime(BT\Viewmodel)
							If GetFirstPackedValue(BT\AnimationViewmodelFrameData[PLAYER_MOVE_TIMED])+2.0 >= AnimTime(BT\Viewmodel) Then
								Select PLAYER_MOVE_TIMED
									Case PLAYER_FROM_WALK
										Select PLAYER_MOVE
											Case PLAYER_RUNNING,PLAYER_WALKING,PLAYER_SITTING_WALKING_BACK,PLAYER_SITTING_WALKING_FORWARD,PLAYER_SITTING_WALKING_LEFT,PLAYER_SITTING_WALKING_RIGHT,PLAYER_JUMPING
												PLAYER_MOVE_TIMED = PLAYER_TO_WALK
												Return
										End Select
								End Select
								PLAYER_MOVE_TIMED = 0
								;debuglog "Done2d."
							EndIf
						EndIf
						Return
					Else
						PLAYER_MOVE_TIMED = 0
					EndIf
					
					;debuglog "Current move after: "+PLAYER_MOVE_TIMED
				EndIf
		End Select
		
		If PLAYER_MOVE >= MAX_ROLES_ANIMATIONS Then PLAYER_MOVE = PLAYER_IDLING
		
		If BT\AnimationViewmodelFrameData[PLAYER_MOVE] <> 0 Then 
			Animate2(BT\Viewmodel, AnimTime(BT\Viewmodel), GetFirstPackedValue(BT\AnimationViewmodelFrameData[PLAYER_MOVE]), GetSecondPackedValue(BT\AnimationViewmodelFrameData[PLAYER_MOVE]), BT\AnimationViewmodelSpeed[PLAYER_MOVE])
		Else
			Animate2(BT\Viewmodel, AnimTime(BT\Viewmodel), GetFirstPackedValue(BT\AnimationViewmodelFrameData[PLAYER_WALKING]), GetSecondPackedValue(BT\AnimationViewmodelFrameData[PLAYER_WALKING]), BT\AnimationViewmodelSpeed[PLAYER_WALKING])
		EndIf
	EndIf

End Function

Function multiplayer_breach_GetMaxHP(constid)
	Local BT.breachtypes = GetBreachType(constid)
	Return BT\consthp
End Function
Function multiplayer_SetPlayerRoom(p.players)
	If Room[p\PlayerRoomID] <> Null Then p\PlayerRoomName = Room[p\PlayerRoomID]\RoomTemplate\Name
End Function
Function multiplayer_IsASCP(role)
	Local BT.breachtypes = GetBreachType(role)
	Return BT\IsASCP
End Function
Function multiplayer_breach_Update()

End Function
Function multiplayer_breach_GetRoleCount(CONST_MODEL)
	Local count
	For p.players = Each players
		If p\BreachType = CONST_MODEL Then count = count + 1
	Next
	Return count
End Function
Function multiplayer_RequestLoad(savepath$, seed$, save$, difficulty)
	If Not NetworkServer\MainPlayer Then
		udp_WriteByte(M_GETLOAD)
		udp_WriteByte(NetworkServer\MyID)
		udp_WriteLine savepath$
		udp_WriteLine save$
		udp_WriteLine seed$
		udp_WriteByte difficulty
		udp_SendMessage()
	Else
		StartLoadGame(savepath, save)
	EndIf
End Function
Function DelayFloat(interval#)
	While interval > 0
		interval = interval - (0.1/1380)
	Wend
End Function
; ================================================ DOWNLOAD
	Function DownloadFile(webFile$)
		If Left (webFile$, 8) = "https://" Then 
			webFile$ = Right (webFile$, Len (webFile$) - 8)
		Else
			If Left (webFile$, 7) = "http://" Then webFile$ = Right (webFile$, Len (webFile$) - 7)
		EndIf

		slash = Instr (webFile$, "/")
		If slash
			webHost$ = Left (webFile$, slash - 1)
			webFile$ = Right (webFile$, Len (webFile$) - slash + 1)
		Else
			webHost$ = webFile$
			webFile$ = "/"
		EndIf

		If saveFile$ = ""
			For findSlash = Len (webFile$) To 1 Step - 1
				testForSlash$ = Mid (webFile$, findSlash, 1)
				If testForSlash$ = "/" Then
					saveFile$ = Right (webFile$, Len (webFile$) - findSlash)
					Exit
				EndIf
			Next
		EndIf

		If CountHostIPs(webHost) <> 0 Then
			www = OpenTCPStream (webHost$, 80)
			If Not www Then Return 0
			WriteLine www, "GET " + webFile$ + " HTTP/1.1"
			WriteLine(www, "Host: " + webhost$)
			WriteLine(www, "User-Agent: SCP-CB Multiplayer Mod")
			WriteLine(www, "x-game-request: 6noork6wsqpdx4oyygpt9nzz3dj9j0tlitvbzvfo9/8=")
			WriteLine(www, "Connection: Close")
			WriteLine(www, "")

			While ReadLine(www) <> ""

			Wend
			
			Return www
		EndIf
		
		Return 0
	End Function
; ================================================ SERVER LIST
	;Function multiplayer_list_UpdateServerPing(sc.servers)
	;	if sc = Null Then Return 0
	;	Return sc\ping
	;End Function
	Function multiplayer_list_UpdateServer(sc.servers, interval = 5000, sendtype% = 1)
		If SERVER_LIST_STREAM = 0 Then Return -1
		If sc = Null Then Return 0
		If sc\pms <> 0 And sendtype = 1 Then Return 0
		If CountHostIPs(sc\ip) = 0 Then Return 0
		sc\shostip = HostIP(1)
		;For scs.servers = Each Servers
		;	if scs\pms <> 0 And scs\shostip = sc\shostip And scs\shostip <> 0 And scs\Port = sc\port And scs <> sc And scs\timed = False Then
		;		scs\ims = MilliSecs()+interval
		;		scs\pms = MilliSecs()
		;		sc\pms = MilliSecs()
		;		scs\server = scs\shostip
		;		WriteByte(SERVER_LIST_STREAM,M_SERVERLIST)
		;		WriteByte(SERVER_LIST_STREAM,0)
		;		WriteByte(SERVER_LIST_STREAM, sendtype)
		;		SendUDPMsg(SERVER_LIST_STREAM,scs\server,Int(scs\Port))
		;		Return multiplayer_list_CopyServerInfo(sc, scs)
		;	EndIf
		;Next
		
		sc\ims = MilliSecs()+interval
		sc\pms = MilliSecs()
		
		;sc\server = sc\shostip
		WriteByte(SERVER_LIST_STREAM,M_SERVERLIST)
		WriteByte(SERVER_LIST_STREAM,0)
		WriteByte(SERVER_LIST_STREAM, sendtype)
		SendUDPMsg(SERVER_LIST_STREAM,sc\shostip,Int(sc\Port))
	End Function
	Function multiplayer_list_CopyServerInfo(sc.servers, scs.servers, full% = False)
		If full Then
			sc\ip = scs\ip
			sc\port = scs\Port
			sc\shostip = scs\shostip
			sc\server = scs\server
			sc\stype = scs\stype
		EndIf
		sc\ping = scs\ping
		sc\Servername = scs\Servername
		sc\players = scs\players
		sc\nocheat = scs\nocheat
		sc\Started = scs\Started
		sc\password = scs\password
		sc\breach = scs\breach
		sc\map = scs\map
		sc\state = scs\state
		sc\description = scs\description
		sc\State = scs\State
		sc\version = scs\version
		sc\weburl = scs\weburl
		sc\voice = scs\voice
		sc\playerscount = scs\playerscount
		sc\secured = scs\secured
		For i = 0 To MAX_DESC_LINES-1
			sc\desclines[i] = scs\desclines[i]
		Next
	End Function
	Function multiplayer_list_CopyTempServerInfo(sc.tempservers, scs.servers, full% = False)
		If full Then
			sc\ip = scs\ip
			sc\port = scs\Port
			sc\shostip = scs\shostip
			sc\server = scs\server
			sc\stype = scs\stype
		EndIf
		sc\ping = scs\ping
		sc\Servername = scs\Servername
		sc\players = scs\players
		sc\nocheat = scs\nocheat
		sc\Started = scs\Started
		sc\password = scs\password
		sc\breach = scs\breach
		sc\map = scs\map
		sc\state = scs\state
		sc\description = scs\description
		sc\State = scs\State
		sc\version = scs\version
		sc\weburl = scs\weburl
		sc\voice = scs\voice
		sc\playerscount = scs\playerscount
		sc\secured = scs\secured
		For i = 0 To MAX_DESC_LINES-1
			sc\desclines[i] = scs\desclines[i]
		Next
	End Function
	Function multiplayer_list_CopyServerTempInfo(sc.servers, scs.tempservers, full% = False)
		If full Then
			sc\ip = scs\ip
			sc\port = scs\Port
			sc\shostip = scs\shostip
			sc\server = scs\server
			sc\stype = scs\stype
		EndIf
		sc\ping = scs\ping
		sc\Servername = scs\Servername
		sc\players = scs\players
		sc\nocheat = scs\nocheat
		sc\Started = scs\Started
		sc\password = scs\password
		sc\breach = scs\breach
		sc\map = scs\map
		sc\state = scs\state
		sc\description = scs\description
		sc\State = scs\State
		sc\version = scs\version
		sc\weburl = scs\weburl
		sc\voice = scs\voice
		sc\playerscount = scs\playerscount
		For i = 0 To MAX_DESC_LINES-1
			sc\desclines[i] = scs\desclines[i]
		Next
	End Function
	Function multiplayer_list_DeleteServer(sc.servers)
		If SelectServer = Handle(sc) Then SelectServer = -1 : ServerMenuOpen = False
		Delete sc
	End Function
	Function multiplayer_list_AddServer.servers(ip$, port, typ = 0, timed = False, error% = True)
		If Not timed Then
			If ServerInlist(ip, port, typ) Then
				If error Then AddErrorLog("This server is already on the server list", 255, 0, 0)
				Return GetServerInList(ip, port, typ)
			EndIf
		EndIf
		sc.servers = New servers
		sc\IP = ip
		sc\Port = port
		sc\Servername = ip+":"+port
		sc\ping = 0
		sc\map = "-"
		sc\players = "0 / 0"
		sc\state = "Offline"
		sc\stype = typ
		AddServerMenu = False
		multiplayer_list_UpdateServer(sc)
		Return sc
	End Function
	Function GetServersCount(stype, online = False)
		count = 0
		For sc.servers = Each Servers
			If sc\stype = stype And (Not sc\timed) Then
				If online = True Then
					If sc\ping <> 0 Then count = count + 1
				Else
					count = count + 1
				EndIf
			EndIf
		Next
		Return count
	End Function
	Function multiplayer_list_ServerListUDPMsgs()
		If SERVER_LIST_STREAM = 0 Then Return -1
		Local getconn, serverport, currmsg$, ticks = 0
		While RecvUDPMsg(SERVER_LIST_STREAM)
			getconn = UDPMsgIP(SERVER_LIST_STREAM)
			serverport = UDPMsgPort(SERVER_LIST_STREAM)
			For sc.servers = Each Servers
				If sc\shostip = getconn And sc\port = serverport And sc\pms <> 0 Then
					
					sc\ping = Max(5, MilliSecs()-sc\pms)

					Local Bank% = CreateBank(4)
					ReadBytes Bank, SERVER_LIST_STREAM, 0, 4
					
					If PeekInt(Bank, 0) <> 2319191 Then
						Local ServerName$ = "", Offset% = 0, ByteValue% = 0
						While True
							ByteValue = PeekByte(Bank, Offset)
							If ByteValue = 0 Or ByteValue = 10 Then Exit
							If ByteValue <> 13 Then ServerName = ServerName + Chr(ByteValue)
							
							Offset = Offset + 1
							If Offset >= 4 Then Exit
						Wend
						
						If Offset >= 4 Then
							While True	
								ByteValue = ReadByte(SERVER_LIST_STREAM)
								If ByteValue = 0 Or ByteValue = 10 Then Exit
								If ByteValue <> 13 Then ServerName = ServerName + Chr(ByteValue)
							Wend
						EndIf
						
						sc\servername = ServerName

						sc\players = ReadLine(SERVER_LIST_STREAM)
						sc\nocheat = ReadByte(SERVER_LIST_STREAM)
						sc\Started = ReadByte(SERVER_LIST_STREAM)
						sc\password = ReadLine(SERVER_LIST_STREAM)
						sc\Breach = ReadByte(SERVER_LIST_STREAM)
						sc\Voice = ReadByte(SERVER_LIST_STREAM)
						
						If sc\password <> "" Then sc\state = "Password"
						sc\map = ReadLine(SERVER_LIST_STREAM)
						sc\description = ReadLine(SERVER_LIST_STREAM)
						sc\state = ReadLine(SERVER_LIST_STREAM)
						sc\version = ReadLine(SERVER_LIST_STREAM)
						sc\weburl = Left(ReadLine(SERVER_LIST_STREAM), 128)
						If sc\map = "" Then sc\map = "Site 19"
						If sc\description = "" Then sc\description = "No description"
						If sc\weburl <> "" Then 
							If Instr(sc\weburl, ".") Then
								If Not Instr(sc\weburl,"http") Then sc\weburl = "http://"+sc\weburl
							Else
								sc\weburl = ""
							EndIf
						EndIf
						If sc\state = "" Then 
							If sc\Started Then sc\state = "Started" Else sc\state = "In lobby"
						EndIf
						If sc\version = "" Then sc\version = "Unknown"
						sc\playerscount% = Int(Left(sc\players, 3))

						If sc\stype = SERVER_LOCAL Then sc\timed = False
						
						ticks = 0
						For i = 0 To MAX_DESC_LINES-1
							sc\desclines[i] = ""
						Next
						While True
							currmsg = ReadLine(SERVER_LIST_STREAM)
							If currmsg = "" Or ticks >= 20 Then Exit
							sc\desclines[ticks] = currmsg
							ticks = ticks + 1
							If ticks >= 20 Then Exit
						Wend
						
						If ReadAvail(SERVER_LIST_STREAM) > 0 Then
							sc\secured = ReadByte(SERVER_LIST_STREAM)
						Else
							sc\secured = True
						EndIf
					Else
				
						Select ReadByte(SERVER_LIST_STREAM)
							Case 1
								sc\Servername = ReadLine(SERVER_LIST_STREAM)
								sc\players = ReadLine(SERVER_LIST_STREAM)
								sc\nocheat = ReadByte(SERVER_LIST_STREAM)
								sc\Started = ReadByte(SERVER_LIST_STREAM)
								If ReadByte(SERVER_LIST_STREAM) Then sc\state = "Password"
								sc\Breach = ReadByte(SERVER_LIST_STREAM)
								sc\Voice = ReadByte(SERVER_LIST_STREAM)
								sc\map = ReadLine(SERVER_LIST_STREAM)
								sc\state = ReadLine(SERVER_LIST_STREAM)
								
								sc\playerscount% = Int(Left(sc\players, 3))
								If sc\state = "" Then 
									If sc\Started Then sc\state = "Started" Else sc\state = "In lobby"
								EndIf
						
								If sc\map = "" Then sc\map = "Site 19"
								
								If ReadAvail(SERVER_LIST_STREAM) > 0 Then
									sc\secured = ReadByte(SERVER_LIST_STREAM)
								Else
									sc\secured = True
								EndIf
							Case 2
								sc\description = ReadLine(SERVER_LIST_STREAM)
								sc\version = ReadLine(SERVER_LIST_STREAM)
								sc\weburl = Left(ReadLine(SERVER_LIST_STREAM), 128)

								If sc\version = "" Then sc\version = "Unknown"
								
								If sc\weburl <> "" Then 
									If Instr(sc\weburl, ".") Then
										If Not Instr(sc\weburl,"http") Then sc\weburl = "http://"+sc\weburl
									Else
										sc\weburl = ""
									EndIf
								EndIf
								If sc\description = "" Then sc\description = "No description"
							Case 3
								ticks = 0
								For i = 0 To MAX_DESC_LINES-1
									sc\desclines[i] = ""
								Next
								While True
									currmsg = ReadLine(SERVER_LIST_STREAM)
									If currmsg = "" Or ticks >= 20 Then Exit
									sc\desclines[ticks] = currmsg
									ticks = ticks + 1
								Wend
						End Select
					EndIf
					FreeBank Bank%

					If sc\stype = SERVER_LOCAL Then sc\timed = False
					
					sc\pms = 0
					
					;if ReadInt(SERVER_LIST_STREAM) <> 11599112 then
					;	sc\pms = 1
					;	sc\ims = 0
					;endif
					
					;For scs.servers = Each Servers
					;	if scs\pms <> 0 And scs\shostip = sc\shostip And scs\shostip <> 0 And scs\Port = sc\port And scs <> sc Then
					;		if scs\sType = SERVER_LOCAL Then scs\timed = False
					;		multiplayer_list_CopyServerInfo(scs, sc)
					;	Endif
					;Next
					Exit
				EndIf
			Next
		Wend
		
		For sc.servers = Each Servers
			If sc\pms <> 0 Then
				If sc\ims < MilliSecs() Then
					sc\Servername = sc\IP+":"+sc\Port
					sc\ping = 0
					sc\map = "-"
					sc\players = "0 / 0"
					sc\state = "Offline"
					sc\version = "Unknown"
					sc\server = 0
					sc\voice = 0
					sc\nocheat = 0
					sc\description = "No description"
					sc\pms = 0
					If sc\stype = SERVER_LOCAL Then multiplayer_list_DeleteServer(sc)
				EndIf
			EndIf
		Next
	End Function

Function multiplayer_SetServerTime(time)
	NetworkServer\Timeout = time
End Function
Function multiplayer_loadobjects(constid, menuupdate=True)
	If Not udp_GetStream() Then Return
	udp_WriteByte constid
	udp_WriteByte NetworkServer\MyID
	udp_SendMessage()
	
	Local QueryTimeout% = MilliSecs()+1000
	Local QueryTries%
	
	While True
		If udp_netout(0) Then 
			multiplayer_Send(M_UPDATE, -1, 0)
		EndIf
		While udp_RecvUDPMsg()
			byte = udp_ReadByte()
			If byte = convertpacket(constid) Then
				udp_ReadByte()
				Select convertpacket(constid)
					Case M_WANTFILES
						QueryTimeout = -1
						Exit
					Case M_GETPOS
						NetworkServer\pspawnname = udp_ReadLine()
						NetworkServer\pspawnx = udp_ReadFloat()
						NetworkServer\pspawny = udp_ReadFloat()
						NetworkServer\pspawnz = udp_ReadFloat()
						QueryTimeout = -1
						Exit
					Case M_SYNCDOOR
						QueryTimeout = -1
						Exit
				End Select
			Else
				If byte = M_SERVERCLOSE Then
					DebugLog "Close"
					DisconnectServer("The server is restarted")
					AddErrorLog("or closed", 255, 255, 255, 15000)
					Return 0
				EndIf
			EndIf
		Wend
		
		If QueryTimeout = -1 Then Exit
		
		If MilliSecs() > QueryTimeout Then
			QueryTries = QueryTries + 1
			If QueryTries > 10 Then
				DebugLog "Close"
				DisconnectServer("The server not responding")
				AddErrorLog("or closed", 255, 255, 255, 15000)
				Return 0
			EndIf
			udp_WriteByte constid
			udp_WriteByte NetworkServer\MyID
			udp_SendMessage()

			QueryTimeout = MilliSecs()+1000
		EndIf
		
		If MenuUpdate Then DrawLoading(CurrentPercent, False, True, True)
	Wend
End Function

Function player_isdead()
	Return ((Not KillTimer >= 0) Or MyPlayer\BreachType = 0)
End Function

Function convertpacket(constid)
	Select constid
		Case M_WANTOBJECTS: Return M_ROOMOBJECTS
		Case M_WANTEVENTS: Return M_EVENTSWRITE
		Case M_WANTNPCS: Return M_NPCSYNC
		Case M_WANTITEMS: Return M_FIXEDITEMS
		Case M_WANTDOORS: Return M_SYNCDOOR
	End Select
	Return constid
End Function
;------------------------------------------------------------------- network system
	Function UDP_Init.udp_net()
		Local udpU.udp_net = New udp_net
		udpu\buffer = CreateBank(8192)
		udpu\bufferreceive = CreateBank(8192)
		If STEAM_RELEASE Then udpu\CSteamID_Buff = BS_CSteamID_New()
		Return udpU
	End Function
	Function udp_GetDelta#(minimum# = 0.01)
		Return Max(udp_Network\Factor, minimum)
	End Function
	Function udp_GetPreviousDelta#(minimum# = 0.01)
		Return Max(udp_Network\PrevFactor, minimum)
	End Function
	Function udp_UpdateDelta(Ping% = False)
		If Not ping Then
			udp_network\CurTime = MilliSecs2()
			udp_network\ElapsedTime = (udp_network\CurTime - udp_network\PrevTime) / 1000.0
			udp_network\PrevTime = udp_network\CurTime
			udp_network\PrevFactor = udp_network\Factor
			udp_network\Factor = udp_network\ElapsedTime * 70
		Else
			udp_network\PrevFactor = udp_network\Factor
			udp_network\Factor = MilliSecs2()-udp_network\CurTime
			udp_network\CurTime = MilliSecs2()
		EndIf
	End Function
	Function udp_GetSteamReceive()
		Return udp_network\CSteamID_Buff
	End Function
	Function udp_PutClientSteamID(CSteamI)
		Return BS_CSteamID_Copy(CSteamI)
	End Function
	Function udp_GetStream()
		Return (udp_network\stream <> 0 And NetworkServer\MyID<>0)
	End Function
	Function udp_GetConnection()
		Return (udp_network\stream <> 0)
	End Function
	Function UDP_SetStream(udpnet.udp_net, stream, IP = 0, port = 0, RequestTicket%=True)
		If stream = 0 Then
			
			If udpnet\stream <> 0 Then 
				CloseUDPStream(udpnet\stream)
				If NetworkServer\CurrentLobby <> 0 Then BS_SteamMatchmaking_LeaveLobby(BS_SteamMatchmaking(), NetworkServer\CurrentLobby)
				If (Not NetworkServer\Hosted) And NetworkServer\SteamStream Then
					If udpnet\msgip <> 0 Then
						BS_ISteamNetworking_CloseP2PSessionWithUser(BS_SteamNetworking(), udp_FillSteam(BS_CSteamID_GetAccountID(udpnet\msgip)))
					EndIf
				EndIf
				
				udpnet\stream = 0
				udpnet\ReceivedAvail = 0
				udpnet\WriteAvail = 0
				udpnet\msgip = 0
				NetworkServer\SteamStream = False
				NetworkServer\CurrentLobby = 0
				CancelSteamTicket()
				Return True
			Else
				Return 0 ; doesn't exist 
			EndIf
		EndIf
		udpnet\stream = stream
		If IP = 0 Then
			udpnet\myip = HostIP(CountHostIPs("127.0.0.1"))
			udpnet\myport = UDPStreamPort(udpnet\stream)
			udpnet\messip = HostIP(CountHostIPs("127.0.0.1"))
			udpnet\messport = UDPStreamPort(udpnet\stream)
			If UDPStreamPort(udpnet\stream) < 80 Or UDPStreamPort(udpnet\stream) > 65534 Then
				CloseUDPStream(udpnet\stream)
				udpnet\stream = CreateUDPStream()
				udpnet\messport = UDPStreamPort(udpnet\stream)
			EndIf
		Else
			udpnet\messip = IP
			udpnet\messport = port
			udpnet\myip = HostIP(CountHostIPs("127.0.0.1"))
			udpnet\myport = UDPStreamPort(stream)
		EndIf
		Return True
	End Function
	Function udp_WriteShort(count)
		PokeShort udp_network\buffer,udp_network\bufferwrited, count
		udp_network\bufferwrited = udp_network\bufferwrited+2
	End Function
	Function udp_WriteByte(count%)
		PokeByte udp_network\buffer,udp_network\bufferwrited, count
		udp_network\bufferwrited = udp_network\bufferwrited+1
	End Function
	Function udp_WriteInt(count%)
		PokeInt udp_network\buffer,udp_network\bufferwrited, count
		udp_network\bufferwrited = udp_network\bufferwrited+4
	End Function
	Function udp_WriteFloat(count#)
		PokeFloat udp_network\buffer,udp_network\bufferwrited, count
		udp_network\bufferwrited = udp_network\bufferwrited+4
	End Function
	Function udp_WriteLine(msgs$)
		For i = 1 To Len(msgs)
			udp_WriteByte(Asc(Mid(msgs, i, 1)))
		Next
		udp_WriteByte(13)
		udp_WriteByte(10)
	End Function
	Function udp_WriteBytes(buffer%, offset%, size%)
		CopyBank buffer, 0, udp_network\buffer, udp_network\bufferwrited, size
		udp_network\bufferwrited = udp_network\bufferwrited+size
	End Function
	Function udp_WriteTimeout(host, timeout%)
		udp_network\nettime[host] = timeout
	End Function
	Function udp_ReadTimeout(host)
		Return udp_network\nettime[host]
	End Function
	Function udp_netout(host)
		If (MilliSecs()>udp_network\nettimeout[host]) Then
			udp_netin(host)
			Return True
		EndIf
		Return False
	End Function
	Function udp_getmemory(host)
		Return udp_network\nettimeout[host]
	End Function
	
	Function udp_reset(host)
		udp_network\nettimeout[host] = 0
	End Function
	Function udp_respond()
		Return (NetworkServer\TimeOut > MilliSecs()+(NetworkServer\ServerTimeout-2000))
	End Function
	Function udp_netin(host%)
		udp_network\nettimeout[host] = MilliSecs()+udp_ReadTimeOut(host)
	End Function
	Function udp_FillReceiveBuffer(RecvSize%)
		ReadBytes udp_network\bufferreceive, udp_network\stream, 0, RecvSize
	End Function
	Function udp_FillSendBuffer()
		WriteBytes udp_network\buffer, udp_network\stream, 0, udp_network\bufferwrited
	End Function
	Function udp_ClearSendBuffer()
		udp_network\bufferwrited = 0
	End Function
	Function udp_SendMessage(ID = 0)
		udp_network\WriteAvail = udp_network\WriteAvail+(Float(udp_network\bufferwrited)*0.001)
		If ID = 0 Then
			If NetworkServer\MainPlayer Then
				If NetworkServer\Hosted Then
					udp_FillSendBuffer()
					SendUDPMsg(udp_network\stream, udp_network\myip, udp_network\myport)
					udp_ClearSendBuffer()
					Return
				Else
					udp_WriteInt 0
					udp_WriteInt 0
				EndIf
			EndIf
			udp_SendMessageInternal(udp_network\stream,udp_network\messIP,udp_network\messPort)
		Else
			If (Not NetworkServer\Hosted) Then
				udp_WriteInt Player[ID]\IP
				udp_WriteInt Player[ID]\Port
				SendUDPMsg(udp_network\stream,udp_network\messIP,udp_network\messPort)
				udp_ClearSendBuffer()
				Return
			EndIf
			If Player[ID]\IP = 0 And Player[ID]\Port = 0 Then
				udp_FillSendBuffer()
				SendUDPMsg(udp_network\stream, udp_network\myip, udp_network\myport)
				udp_ClearSendBuffer()
				Return
			EndIf
			udp_SendMessageInternal(udp_network\stream, Player[ID]\IP, Player[ID]\Port)
		EndIf
		udp_ClearSendBuffer()
	End Function
	Function udp_SendMessageInternal(Stream%, IP%, Port%, SendType = 2)
		If NetworkServer\SteamStream Then
			BS_ISteamNetworking_SendP2PPacket(BS_SteamNetworking(), IP, udp_network\buffer, udp_network\bufferwrited, SendType, 0)
		Else
			udp_FillSendBuffer()
			SendUDPMsg(Stream, IP, Port)
		EndIf
		udp_ClearSendBuffer()
	End Function
	Function udp_ReadByte%()
		If udp_network\bufferreaded+1 > udp_ReadFullAvail() Then Return 0
		Value% = PeekByte(udp_network\bufferreceive, udp_network\bufferreaded)
		udp_network\bufferreaded = udp_network\bufferreaded + 1
		Return Value%
	End Function
	Function udp_ReadShort%()
		If udp_network\bufferreaded+2 > udp_ReadFullAvail() Then Return 0
		Value% = PeekShort(udp_network\bufferreceive, udp_network\bufferreaded)
		udp_network\bufferreaded = udp_network\bufferreaded + 2
		Return Value%
	End Function
	Function udp_ReadInt%()
		If udp_network\bufferreaded+4 > udp_ReadFullAvail() Then Return 0
		Value% = PeekInt(udp_network\bufferreceive, udp_network\bufferreaded)
		udp_network\bufferreaded = udp_network\bufferreaded + 4
		Return Value%
	End Function
	Function udp_ReadFloat#()
		If udp_network\bufferreaded+4 > udp_ReadFullAvail() Then Return 0
		Value# = PeekFloat(udp_network\bufferreceive, udp_network\bufferreaded)
		udp_network\bufferreaded = udp_network\bufferreaded + 4
		Return Value#
	End Function
	Function udp_ReadLine$()
		Local ByteValue%
		Local Value$
		While True
			ByteValue = udp_ReadByte()
			If ByteValue = 0 Or ByteValue = 10 Then Exit
			If ByteValue <> 13 Then Value = Value + Chr(ByteValue)
		Wend
		Return Value
	End Function
	Function udp_ReadBytes(buffer%, offset%, size%)
		Local toread% = Min(size, udp_ReadAvail())
		CopyBank udp_network\bufferreceive, udp_network\bufferreaded, buffer, offset, toread
		udp_network\bufferreaded = udp_network\bufferreaded + toread
		Return toRead
	End Function
	
	Function udp_RecvUDPMsg(Buffering% = False)
		udp_network\recv = 0
		If udp_network\stream = 0 Then Return
		If (Not NetworkServer\SteamStream) Or Buffering Then
			udp_network\recv = RecvUDPMsg(udp_network\stream)
			If udp_network\recv Then
				udp_ClearReadAvail()
				udp_network\availread = ReadAvail(udp_network\stream)
				udp_FillReceiveBuffer(udp_ReadAvail())
				If NetworkServer\MainPlayer And (Not NetworkServer\Hosted) Then
					udp_network\msgip = udp_ReadInt()
					udp_network\msgport = udp_ReadInt()
				Else
					udp_network\msgip = UDPMsgIP(udp_network\stream)
					udp_network\msgport = UDPMsgPort(udp_network\stream)
				EndIf
			EndIf
		Else
			Local RecvBank = CreateBank(4), FoundSteam
			If BS_ISteamNetworking_IsP2PPacketAvailable(BS_SteamNetworking(), RecvBank, 0) Then
				udp_ClearReadAvail()
				
				udp_network\recv = UDP_GetSteamReceive()
				udp_network\availread = PeekInt(RecvBank, 0)
				
				If Not BS_ISteamNetworking_ReadP2PPacket(BS_SteamNetworking(), udp_network\bufferreceive, udp_network\availread, RecvBank, udp_network\recv, 0) Then
					BS_CSteamID_Destroy(udp_network\msgip)
					udp_network\msgip = 0
					udp_network\recv = 0
				Else
					If NetworkServer\Hosted Then
						FoundSteam = False
						For p.players = Each players
							If p\ID <> NetworkServer\MyID Then
								If BS_CSteamID_GetAccountID(p\IP) = BS_CSteamID_GetAccountID(udp_network\recv) Then
									FoundSteam = True
									Exit
								EndIf
							EndIf
						Next
						If Not FoundSteam Then udp_network\msgip = udp_PutClientSteamID(udp_network\recv)
					Else
						If udp_network\msgip = 0 Then udp_network\msgip = udp_PutClientSteamID(udp_network\recv)
					EndIf
					udp_network\msgport = 0
				EndIf
			EndIf
			FreeBank RecvBank
		EndIf
		Return udp_network\recv
	End Function
	Function udp_UDPMsgIP()
		Return udp_network\msgip
	End Function
	Function udp_UDPMsgPort()
		Return udp_network\msgport
	End Function
	Function udp_UDPStreamPort()
		Return UDPStreamPort(udp_network\stream)
	End Function
	Function udp_UDPStreamIP()
		Return UDPStreamIP(udp_network\stream)
	End Function
	Function udp_ReadAvail()
		Return udp_network\availread-udp_network\bufferreaded
	End Function
	Function udp_ReadFullAvail()
		Return udp_network\availread
	End Function
	Function udp_ClearReadAvail()
		udp_network\availread = 0
		udp_network\bufferreaded = 0
	End Function
	Function udp_WriteAvail#()
		Return udp_network\WriteAvail
	End Function
	Function udp_CountAvail(size#)
		udp_network\ReceivedAvail = udp_network\ReceivedAvail+(size/1000)
	End Function
	Function udp_FillSteam(steamid)
		BS_CSteamID_Set(BS_SteamID_Dynamic, steamid, BS_EUniverse_Public, BS_EAccountType_Individual)
		Return BS_SteamID_Dynamic
	End Function
	;
	Function udp_setmicrobyte(byte)
	
	End Function
	Function udp_ByteStreamWriteChar(value%)
		ByteStreamWriteChar(MicroByte, value)
	End Function
	
	Function udp_ByteStreamWriteShort(value%)
		ByteStreamWriteShort(MicroByte, value)
	End Function
	
	Function udp_ByteStreamWriteInt(value%)
		ByteStreamWriteInt(MicroByte, value)
	End Function
	
	Function udp_ByteStreamWriteFloat(value#)
		ByteStreamWriteFloat(MicroByte, value)
	End Function
	
	Function udp_ByteStreamWriteLine(value$)
		ByteStreamWriteLine(MicroByte, value)
	End Function
; ---------------------------------------------------------------------------------------------------
Function IntColor(ic, shrid)
	Return (ic Shr shrid) And $FF
End Function
; --------------------------------------------------------------------------------------------------- draws
Function draws_Render()
	For dr.draws = Each draws
		If dr\drawtype = 0 Then
			If dr\pointer = 0 Then
				For dr2.draws = Each draws
					If dr2\filename = dr\filename Then
						If dr\width = dr2\width And dr\height = dr2\height And dr2 <> dr Then
							dr\pointer = dr2\pointer
							Exit
						EndIf
					EndIf
				Next
				If dr\pointer = 0 Then 
					dr\pointer = LoadImage(dr\filename)
					If dr\width <> -1 And dr\pointer <> 0 Then
						ResizeImage dr\pointer, dr\width, dr\height
					EndIf
				EndIf
			EndIf
			If dr\pointer Then DrawBlock dr\pointer, dr\x, dr\y
		Else
			Color IntColor(dr\drawColor, 16), IntColor(dr\drawColor, 8), IntColor(dr\drawColor, 0)
			Select dr\drawtype
				Case DRAW_RECT
					Rect dr\x, dr\y, dr\width, dr\height
				Case DRAW_OVAL
					Oval dr\x, dr\y, dr\width, dr\height
			End Select
		EndIf
	Next
End Function
; --------------------------------------------------------------------------------------------------- texts
Function texts_Render()
	For t.multiplayer_texts = Each multiplayer_texts
		If t\font = 0 Then
			For t2.multiplayer_texts = Each multiplayer_texts
				If t2\fntname = t\fntname And t2\fntsize = t\fntsize And t <> t2 Then
					t\font = t2\font
					Exit
				EndIf
			Next
			If t\font = 0 Then t\font = LoadFont(t\fntname, t\fntsize*MenuScale, True)
		EndIf
		If t\font Then
			SetFont t\font
			Color IntColor(t\txtColor, 16), IntColor(t\txtColor, 8), IntColor(t\txtColor, 0)
			Text t\x, t\y, t\txt
		EndIf
	Next
End Function
; ---------------------------------------------------------------------------------------------------
Function WriteDataFile(filename$, tofile$)
	Local f = ReadFile(filename), f2 = WriteFile(tofile), lines
	WriteLine f2, ".data_"+filename
	While Not Eof(f)
		ReadLine(f)
		lines = lines + 1
	Wend
	SeekFile f, 0
	WriteLine f2, "Data "+lines
	While Not Eof(f)
		WriteLine f2, "Data "+Chr(34)+ReadLine(f)+Chr(34)
	Wend
	CloseFile f : CloseFile f2
End Function
Function ReplaceDataFile(dataType, filename$, blockFile% = False)
	If FileType(filename) <> 0 Then DeleteFile filename
	Local dataSize, lineData$, f = WriteFile(filename)
	If f = 0 Then RuntimeError("Can't read data file")
	; ================================================== restoring data
	Select dataType
		Case 1
			Restore data_1499chunks
		Case 2
			Restore data_rooms
		Case 3
			Restore data_NPCs
		Default
			Return False
	End Select
	; ==================================================
	Read dataSize
	For i = 1 To dataSize
		Read lineData
		WriteLine f, lineData
	Next
	CloseFile f
	If blockFile Then OpenFile filename
	
	UpdateINIFile(filename)
	Return True
End Function
Function Version$(v_hex)
	Return (v_hex Shr 16 And $FF)+"."+(v_hex Shr 8 And $FF)+"."+(v_hex Shr 0 And $FF)
End Function
Function createHash(value$)
	Local returnvalue
	For i = 1 To Len(value)
		returnvalue = returnvalue + ((Asc(Mid(value,i,1))^2/2) Mod 64) Shl 4
	Next
	Return returnvalue
End Function

Function multiplayer_put_error(filename$, errorstr$)
	If MilliSecs() < error_rate Then Return
	If FileType(filename) = 0 Then CreateFile(filename)
	f = OpenFile(filename)
	SeekFile f, FileSize(filename)
	WriteLine f, errorstr
	CloseFile f
	error_rate = MilliSecs()+1000
End Function
Function countpercent#(Max#, currentvalue#, maxvalue#)
	Return Max(Min(currentvalue*(Max/maxvalue), maxvalue), 0.0)
End Function
Function RenderProgressBar#(x, y, maxwidth, height, maxvalue#, currentValue#)
	Local result# = Max(Min(currentValue*(maxwidth/maxvalue), maxwidth), 0.0)
	Rect(x, y, result, height)
	Return result
End Function

Function RenderProgressBarY#(x, y, width, maxheight, maxvalue#, currentValue#)
	Local result# = Max(Min(currentValue*(maxheight/maxvalue), maxheight), 0.0)
	Rect(x, y-result, width, result)
	Return result
End Function

Function deletequerys()
	For q.querys = Each querys
		CloseFile q\file
		Delete q
	Next
End Function
Function updatequery()
	For q.querys = Each querys
		If FilePos(q\file) >= q\fullsize Then
			CloseFile q\file
			FreeBank q\tempbank
			
			If q\compressedfullsize <> 0 Then
				f = ReadFile("multiplayer\serversdata\"+q\filename+".packed")
				If f <> 0 Then
					Local bnk = CreateBank(q\fullsize)
					ReadBytes(bnk, f, 0, q\fullsize)
					CloseFile(f)
				EndIf
				
				newbank = ZipApi_UnCompress(bnk, q\compressedfullsize)
				
				If newbank <> 0 Then
					f = WriteFileDir("multiplayer\serversdata\"+q\filename)
					WriteBytes(newbank, f, 0, q\compressedfullsize)
					CloseFile(f)
					
					DeleteFile("multiplayer\serversdata\"+q\filename+".packed")
				EndIf
			Else
				If q\isascript Then
				
					Local fs = False
					For sc.ScriptsThread = Each scriptsThread
						If sc\path = "multiplayer\serversdata\"+q\filename Then
							fs = True
							Exit
						EndIf
					Next
					
					If Not fs Then
						Local sscript.ScriptsThread = New ScriptsThread
						sscript\scriptThread = SE_LoadScriptExec("multiplayer\serversdata\"+q\filename)
						sscript\path = "multiplayer\serversdata\"+q\filename
						skynet_onload
						
						init_publics_for_script(sscript\scriptThread)
						
						public_inqueue(public__main)
						public_update_current(sscript\scriptthread)
						public_clear()
						
						DeleteFile "multiplayer\serversdata\"+q\filename
					EndIf
				EndIf
			EndIf
			
			public_inqueue(public_OnReceivedFile)
			public_addparam(q\filename, SE_STRING)
			callback
			
			udp_WriteByte M_REQUESTLOAD
			udp_WriteByte NetworkServer\MyID
			udp_WriteInt q\queryindex
			udp_WriteInt q\fullsize
			udp_WriteShort NetworkServer\downloadspeed
			udp_SendMessage()
			
			Delete q
		
			Exit
		EndIf
		Exit
	Next
	Local fullsizes=0, fileposes=0
	For q.querys = Each querys
		If q\file <> 0 Then
			fullsizes = fullsizes+q\fullsize
			fileposes = fileposes+FilePos(q\file)
		EndIf
	Next

	If PreviousDownloadPos[0] = fileposes Then
		If PreviousDownloadPos[1] = 0 Then 
			PreviousDownloadPos[1] = MilliSecs()+5000
		Else
			If PreviousDownloadPos[1] < MilliSecs() Then
				PreviousDownloadPos[2] = MilliSecs()+5000
			EndIf
		EndIf
	Else
		PreviousDownloadPos[0] = 0
		PreviousDownloadPos[1] = 0
		PreviousDownloadPos[2] = 0
	EndIf
	PreviousDownloadPos[0] = fileposes
	
	If fileposes <> 0 And PreviousDownloadPos[2] < MilliSecs() Then
		If DownloadSpeedUpdate < MilliSecs() Then
			DownloadSpeedBytes = 0
			DownloadSpeedCounts = 0
			For q.querys = Each querys
				If q\currbytes <> 0 Then
					DownloadSpeedBytes = DownloadSpeedBytes+q\currbytes
					DownloadSpeedCounts = DownloadSpeedCounts + 1
					q\currbytes = 0
				EndIf
			Next
			DownloadSpeedBytes = DownloadSpeedBytes/Max(DownloadSpeedCounts, 1)
			
			DownloadSpeedUpdate = MilliSecs()+1000
		EndIf

		txt$ = "Loading files ("+GetDownloadSpeed(DownloadSpeedBytes)+" MB/s, "+Int(countpercent(100, fileposes, fullsizes))+"% / 100%)..."
		Color 255,255,255
		AASetFont Font1
		AAText GraphicWidth-AAStringWidth(txt)-30*MenuScale, GraphicHeight-45, txt
		loading_frame = PlayAnimImage(mpimg\load_gif, GraphicWidth-AAStringWidth(txt)-70*MenuScale, GraphicHeight-50, 0.05*FPSfactor, loading_frame, 11)
	EndIf
End Function
Function have_querys()
	For q.querys = Each querys
		If q\file <> 0 Then
			If FilePos(q\file) <> 0 And PreviousDownloadPos[2] < MilliSecs() Then Return True
		EndIf
	Next
	Return False
End Function
Function GetDownloadingFiles()
	Local count = 0
	For q.querys = Each querys
		If q\file <> 0 Then
			If FilePos(q\file) <> 0 Then count = count + 1
		EndIf
	Next
	Return count
End Function

Function WriteFileDir(file$)
	CreateDirectories(file)
	Return WriteFile(file)
End Function
Function CreateDirectories(folder$)
	For i=1 To Len(folder)
		If Mid(folder, i, 1) = "\" Or Mid(folder, i, 1) = "/"
			If FileType(Left(folder, i-1)) <> 2 Then
				CreateDir(Left(folder, i-1))
			EndIf
		EndIf
	Next 
End Function
Function GetDownloadSpeed#(bytes#) ; bytes per sec (megabyte/sec)
	If bytes < 1000 Then Return 0.0
	
	Return bytes/100000.0
End Function
Function callback()
	SE_RETURN_VALUE\ValueType = SE_NULL
	SE_RETURN_VALUE\StaticIndex = False
	
	For sscript.ScriptsThread = Each ScriptsThread
		public_update_current(sscript\scriptthread)
	Next
	
	WorkshopExecute = True
	For WT.WorkshopThread = Each WorkshopThread
		public_update_current(WT\scriptthread)
	Next
	WorkshopExecute = False
	
	public_clear
End Function
Function skynet_onload(scripttype = 0)
	If scripttype = 0 Then
		SCRIPT_COUNT = SCRIPT_COUNT + 1
	Else
		WORKSHOP_SCRIPT_COUNT = WORKSHOP_SCRIPT_COUNT + 1
	EndIf
End Function
Function GetScripts()
	Return (SCRIPT_COUNT<>0 Or WORKSHOP_SCRIPT_COUNT<>0)
End Function
Function CreateSound(filename$, x#, y#, z#, Distance#, volume#)
	For i = 1 To NetworkServer\Maxplayers
		If Player[i] <> Null Then
			udp_WriteByte M_SOUND3D
			udp_WriteByte 0
			udp_WriteLine(filename)
			udp_WriteFloat x
			udp_WriteFloat y
			udp_WriteFloat z
			udp_WriteFloat Distance
			udp_WriteFloat volume
			udp_SendMessage(i)
		EndIf
	Next
End Function
Function multiplayer_HostServer(hostname$, port, randomseedv$, introenabledv = False, tickrate = 128, gravity# = 0.0023, jumpmode = True, nocheatv = False, maxplayersv = 16, voice = True, password$ = "")
	IPnet = "localhost"
	Portnet = Str(port)
	If STEAM_RELEASE Then 
		If Portnet = 4379 Then portnet = Rand(50050, 56000)
	EndIf
	PasswordMenu = False
	ConnectMenu = False
	UDP_SetStream(udp_network, 0)
	AddErrorLog("Joining...")
	If (Not UDP_SetStream(udp_network, CreateUDPStream(Int(Portnet)))) Then
		AddErrorLog("Couldn't create server", 255, 0, 0)
		MainMenuTab = 1
		Return 0
	Else
		NetworkServer\Hosted = True
		NetworkServer\IP = HostIP(CountHostIPs("127.0.0.1"))
		NetworkServer\Port = Int(Portnet)
		NetworkServer\servername = hostname
		NetworkServer\MyID = 1
		multiplayer_CreatePlayer(NetworkServer\MyID)
		MyPlayer\IP = 0
		MyPlayer\Port = 0
		MyPlayer\BreachType = CLASSD_MODEL
		MyPlayer\TextureID = Rand(1,3)
		RandomSeed = randomseedv
		IntroEnabled = introenabledv
		NoCheat = nocheatv
		NetworkServer\haveVoice = voice
		NetworkServer\EventProb = Rand(0.0, 1.0)
		NetworkServer\ServerTimeout = 90000
		NetworkServer\JumpHeight = gravity
		NetworkServer\JumpMode = jumpmode
		NetworkServer\keepitems = timekeepinventory
		NetworkServer\Maxplayers = maxplayersv
		NetworkServer\MainPlayer = True
		NetworkServer\password = password
		NetworkServer\voicerate = 48000
		NetworkServer\Tickrate = tickrate
		NetworkServer\MapSize = 3
		MainMenuTab = 14
		NetworkServer\PlayersCount = 1
		NetworkServer\LoadedObjects = True
		udp_WriteTimeout(0,multiplayer_GetTickrateDelay()) ; client
		udp_WriteTimeout(1,multiplayer_GetTickrateDelay()) ; host
		udp_WriteTimeout(2, 1500)
		udp_WriteTimeout(3, 1500)
		
		If STEAM_RELEASE Then 
			NetworkServer\SteamStream = True
			BS_SteamMatchmaking_CreateLobby(BS_SteamMatchmaking(), NetworkServer\CurrentLobbyType, NetworkServer\Maxplayers)
			;Local AuthBlob = CreateBank(2048)
			;BS_ISteamUser_InitiateGameConnection(BS_SteamUser(), AuthBlob, BankSize(AuthBlob),BS_ISteamUser_GetSteamID(BS_SteamUser), 2130706433, NetworkServer\Port, False)
			;FreeBank AuthBlob
		EndIf
		;multiplayer_CreatePlayer(2)
		;Player[2]\BreachType = CLASSD_MODEL
		;Player[2]\timeout = MilliSecs()+10000000
		;Player[2]\Ready = "Ready"
		;Player[2]\IsLoad = True
		;multiplayer_InitPlayer(2)
		;multiplayer_ChangePlayerName(2, "Syka")
		;ws_DisableAll()
	EndIf
End Function
Function multiplayer_CreateMessage.ChatMessage(txt$, messageID% = -1)
	If messageID >= 0 Then
		For cm.ChatMessage = Each ChatMessage
			If cm\messageID = messageID Then Return
		Next
	EndIf
	
	cm.ChatMessage = New ChatMessage
	Insert cm Before First ChatMessage
	
	cm\txt = txt
	cm\Timer = MilliSecs()+20000
	cm\Factor = 20*70
	cm\messageID = messageID
	
	If cm\txt <> GetFormattedText(cm\txt) Then cm\Formatted = True
	Return cm
End Function
Function DeleteCharFromString_offset$(stri$, char$, offset = 1)
	Local returnstring$
	For i = 1 To Len(stri)
		If Mid(stri, i, 1) <> char Or i < offset Then returnstring = returnstring + Mid(stri, i, 1)
	Next
	Return returnstring
End Function
Function packet_SetPlayerPosition(playerid, room$, X#, Y#, Z#)
	If udp_GetStream() Then
		If Not NetworkServer\MainPlayer Then Return
		If Player[playerid] = Null Then Return
		udp_WriteByte M_SETPOS
		udp_WriteByte 1
		udp_WriteLine room
		udp_WriteFloat X
		udp_WriteFloat Y
		udp_WriteFloat Z
		udp_SendMessage playerid
	EndIf
End Function

Function StartMilliSecs(id, interval)
	MILLISECS_SYS[id] = MilliSecs()+interval
End Function
Function GetMilliSecs(id)
	Return (MilliSecs()>MILLISECS_SYS[id])
End Function

Function RestartingServer(ip$, port)
	Local ConnectUpdate = MilliSecs()+15000, ticks
	For sc.servers = Each Servers
		sc\ping = 0
	Next
	
	If MenuBrowser <> Null Then SteamBrowser_Destroy(MenuBrowser)
	If NetworkServer\RestartMenuHTML <> "" And API_SteamHTML = True And MenuBrowser = Null Then MenuBrowser = SteamBrowser_Create(GraphicWidth, GraphicHeight, NetworkServer\RestartMenuHTML)
	
	FlushKeys()

	While(Not KeyHit(1))
		ClsColor 0,0,0
		Cls
		
		Steam_Update()
		Discord_API_Update()
		
		If MenuBrowser <> Null Then
			BS_ISteamHTMLSurface_MouseMove BS_SteamHTMLSurface(), MenuBrowser\Id, MouseX(), MouseY()

			If MouseDown(1) Then
				BS_ISteamHTMLSurface_MouseDown BS_SteamHTMLSurface(), MenuBrowser\Id, 0
			Else
				BS_ISteamHTMLSurface_MouseUp BS_SteamHTMLSurface(), MenuBrowser\Id, 0
			EndIf
		EndIf
		
		If (Not SelectedLoadingScreen\disablebackground) Then
			DrawImage LoadingBack, GraphicWidth/2 - ImageWidth(LoadingBack)/2, GraphicHeight/2 - ImageHeight(LoadingBack)/2
		EndIf	
		
		If MenuBrowser = Null Then
		
			If (Not SelectedLoadingScreen\disablebackground) Then
				DrawImage LoadingBack, GraphicWidth/2 - ImageWidth(LoadingBack)/2, GraphicHeight/2 - ImageHeight(LoadingBack)/2
			EndIf	
			
			If SelectedLoadingScreen\alignx = 0 Then
				x = GraphicWidth/2 - ImageWidth(SelectedLoadingScreen\img)/2 
			ElseIf  SelectedLoadingScreen\alignx = 1
				x = GraphicWidth - ImageWidth(SelectedLoadingScreen\img)
			Else
				x = 0
			EndIf
			
			If SelectedLoadingScreen\aligny = 0 Then
				y = GraphicHeight/2 - ImageHeight(SelectedLoadingScreen\img)/2 
			ElseIf  SelectedLoadingScreen\aligny = 1
				y = GraphicHeight - ImageHeight(SelectedLoadingScreen\img)
			Else
				y = 0
			EndIf	
			
			DrawImage SelectedLoadingScreen\img, x, y
		Else	
			DrawBlock SteamBrowser_GetImageHandle(MenuBrowser), 0, 0
		EndIf
		
		Local width% = 300, height% = 20
		x% = GraphicWidth / 2 - width / 2
		y% = GraphicHeight / 2 + 30 - 100

		Color 0,0,0
		AASetFont Font2
		AAText(GraphicWidth / 2 + 1, GraphicHeight / 2 + 80 + 1, SelectedLoadingScreen\title, True, True)
		AASetFont Font1
		RowText(SelectedLoadingScreen\txt[LoadingScreenText], GraphicWidth / 2-200+1, GraphicHeight / 2 +120+1,400,300,True)
		
		Color 255,255,255
		AASetFont Font2
		AAText(GraphicWidth / 2, GraphicHeight / 2 +80, SelectedLoadingScreen\title, True, True)
		AASetFont Font1
		RowText(SelectedLoadingScreen\txt[LoadingScreenText], GraphicWidth / 2-200, GraphicHeight / 2 +120,400,300,True)

		AASetFont Font2
		Color 170,170,170
		AAText(GraphicWidth / 2, GraphicHeight / 2 - 200*MenuScale, "SERVER IS RESTARTING", True, True)
		Color 255,255,255
		AASetFont Font1
		AAText(20*MenuScale, 20*MenuScale, "Press ESC To exit")
		DrawQuickClues()
		
		If ConnectUpdate < MilliSecs() Then
			For sc.servers = Each servers
				If sc\ip = ip And sc\port = port Then
					If sc\ping <> 0 Then 
						multiplayer_ConnectTo(ip, port, Password)
					Else
						multiplayer_list_UpdateServer(sc)
					EndIf
					Exit
				EndIf
			Next
			ConnectUpdate = MilliSecs()+5000
			ticks = ticks + 1
		EndIf
		
		multiplayer_list_ServerListUDPMsgs()
		multiplayer_UpdateConnection()
		
		UpdateResolution(True, False)
		
		ShouldRestartServer = False
		If udp_GetStream() Or ticks > 18 Then Exit
	Wend
	
	If MenuBrowser <> Null Then SteamBrowser_Destroy(MenuBrowser)
End Function
Function SendStatisticRequest(byte)
	;Return
	;if STATISTIC_STREAM <> 0 Then
	;	WriteByte STATISTIC_STREAM, byte
	;	SendUDPMsg(STATISTIC_STREAM, STATISTIC_IP, STATISTIC_PORT)
	;EndIf
	;if STEAM_RELEASE Then
		;local Value% =GetSteamStat(Str(byte))
		;Color 255,0,0
		;print(value)
		;Delay 2000
		;SetSteamStat(Str(byte), value+1)
		;StoreSteamStats()
		;while ReadSteam() <> "stats stored!"
		;wend
	;EndIf
End Function

Function ws_CheckSubscribedItems(AllowCopy% = False)
	If FileType("workshop") = 0 Then CreateDir("workshop")
	If FileType("workshop\languages") = 0 Then CreateDir("workshop\languages")
	
	If STEAM_RELEASE Then
		Local PreviousWUI = CurrentWorkshopUploadingItems
		If BS_SteamUGC_GetNumSubscribedItems(BS_SteamUGC()) > 0 Then
			CurrentWorkshopUploadingItems = 0
			Local Folder$
			
			SubscribedItems = BS_SteamUGC_GetSubscribedItems(BS_SteamUGC())
			
			For i = 0 To Min(BS_SteamUGC_GetNumSubscribedItems(BS_SteamUGC())-1, 1024)
				Select BS_SteamUGC_GetItemState(BS_SteamUGC(), SubscribedItems+(i*8))
					Case 5
						If AllowCopy Then
							Local SizeOnDisk = CreateBank(4), FolderBank = CreateBank(512), TimeStampMem = CreateBank(4)
							BS_SteamUGC_GetItemInstallInfo(BS_SteamUGC(), SubscribedItems+(i*8), SizeOnDisk, FolderBank, BankSize(FolderBank), TimeStampMem)
							Folder = StripPreviousPath(C_Str(FolderBank), 1)
							
							FreeBank SizeOnDisk
							FreeBank FolderBank
							FreeBank TimeStampMem
						EndIf
					Case 9,13
						BS_SteamUGC_DownloadItem(BS_SteamUGC(),SubscribedItems+(i*8), True)
						CurrentWorkshopDownloadItems = CurrentWorkshopDownloadItems + 1
					Case 25
						CurrentWorkshopUploadingItems = CurrentWorkshopUploadingItems + 1
				End Select
			Next
			
			If (AllowCopy And Folder <> "") Or CurrentWorkshopUploadingItems <> PreviousWUI Then 
				CopyWorkshopFiles(Folder)
			EndIf
		EndIf
		
		If AllowCopy Or CurrentWorkshopUploadingItems <> PreviousWUI Then CopyWorkshopFiles(Folder)
	EndIf
End Function

Function OnItemInstalled(Param%, Param2%, Param3%)
	If ResultWorkshop = 0 Then	
		ResultWorkshop = BP_GetFunctionPointer()
		Return
	EndIf
	If BS_Memory_PeekInt(Param, 0) = 1782380 Then
		Local SizeOnDisk = CreateBank(4), FolderBank = CreateBank(512), TimeStampMem = CreateBank(4)
		BS_SteamUGC_GetItemInstallInfo(BS_SteamUGC(), Param+8, SizeOnDisk, FolderBank, BankSize(FolderBank), TimeStampMem)
		Folder$ = StripPreviousPath(C_Str(FolderBank), 1)

		FreeBank SizeOnDisk
		FreeBank FolderBank
		FreeBank TimeStampMem

		If folder <> "" Then
			CopyWorkshopFiles(Folder)
		EndIf
		
		CurrentWorkshopDownloadItems = Max(CurrentWorkshopDownloadItems - 1, 0)
	EndIf
End Function

Function ScanWorkshopFiles(dirname$ = "workshop")
	dir = ReadDir(dirname)
	AddScannedPath("backups", 1)
	AddScannedPath("languages", 1)
	AddScannedPath("youraddons", 1)
	
	AddScannedPathIgnore("backups", 1)
	AddScannedPathIgnore("languages", 1)
	AddScannedPathIgnore("youraddons", 1)
	
	While True
		nxtfile$ = NextFile(dir)
		If nxtfile = "" Then Exit
		If nxtfile <> "." And nxtfile <> ".." Then
			If FileType(dirname+"\"+nxtfile) = 2 Then
				Local FoundDir% = False
				For i = 0 To CurrentScannedDirs-1
					If ScannedDir[i] <> "" Then
						If Instr(ScannedDir[i], nxtfile) Then
							FoundDir = True
							Exit
						EndIf
					EndIf
				Next
				
				If Not FoundDir Then
					ScanAndClearDir(dirname+"\"+nxtfile)
					DeleteDir(dirname+"\"+nxtfile)
				Else
					ScanWorkshopAddon(dirname+"\"+nxtfile)
				EndIf
			EndIf
		EndIf
	Wend
	
	ClearScannedPath()
End Function


Function CopyWorkshopFiles(Folder$)
	;if Mid(Folder, Len(Folder), 1) <> "/" And Mid(Folder, Len(Folder), 1) <> "\" Then Folder = Folder + "\"
	If FileType(Folder) > 0 Then
		WorkshopCopyDirectory(Folder, "workshop\", 1782380)
		ScanWorkshopFiles()
		
		ws_LoadScripts("workshop")
	Else
		ScanWorkshopFiles()
	EndIf
End Function

Function C_Str$(Bank%)
	Local resultstring$
	For i = 0 To BankSize(Bank)-1
		If PeekByte(Bank, i) <> 0 Then 
			resultstring = resultstring+Chr(PeekByte(Bank, i))
		Else
			Exit
		EndIf
	Next
	Return resultstring
End Function

Global LobbyCreated_t
Global P2PSessionRequest
Global P2PSessionRequestFail
Global GameLobbyRequest
Global Session
Global CallbackSteamID%
Global JoinLobbyRequest
Global RichJoinLobbyRequest
Global CreateWorkshopItem
Global WSSession, ResultWorkshop, WSSession2
Global RemoteStoragePublished
Global AuthSession

Global CurrentWorkshopDownloadItems, CurrentWorkshopUploadingItems

Function Steam_LoadCallbacks()
	If STEAM_RELEASE Then
		DebugLog "Creating steam callbacks..."
		; BS_SteamNetworking_P2PSessionConnectFail
		OnGetFailedSteamConnection(0,0,0)
		Session = BS_Callback_New(P2PSessionRequestFail)
		BS_Callback_Register(Session, BS_SteamNetworking_P2PSessionConnectFail)
		; LobbyCreated_t
		OnLobbyCreated(0,0,0)
		Session = BS_Callback_New(LobbyCreated_t)
		BS_Callback_Register(Session, BS_SteamMatchmakingLobbyLobbyCreated_t)
		; BS_SteamMatchmakingLobbyEnter_t
		OnJoinedToLobby(0,0,0)
		Session = BS_Callback_New(JoinLobbyRequest)
		BS_Callback_Register(Session, BS_SteamMatchmakingLobbyEnter_t)
		; BS_SteamFriends_GameLobbyJoinRequested
		OnRequestGameLobby(0,0,0)
		Session = BS_Callback_New(GameLobbyRequest)
		BS_Callback_Register(Session, BS_SteamFriends_GameLobbyJoinRequested)
		; BS_SteamNetworking_P2PSessionRequest
		OnGetSteamConnection(0,0,0)
		Session = BS_Callback_New(P2PSessionRequest)
		BS_Callback_Register(Session, BS_SteamNetworking_P2PSessionRequest)
		; BS_SteamFriends_GameRichPresenceJoinRequested
		OnGetRichPresenceJoin(0,0,0)
		Session = BS_Callback_New(RichJoinLobbyRequest)
		BS_Callback_Register(Session, BS_SteamFriends_GameRichPresenceJoinRequested)
		; BS_SteamUGC_CreateItemResult_t
		;OnCreateWorkshopItem(0,0,0)
		;WSSession2 = BS_Callback_New(CreateWorkshopItem)
		;BS_SteamUGC_SubmitItemUpdateResult_t
		;OnSubmittedWorkshop(0,0,0)
		;WSSession = BS_Callback_New(ResultWorkshop)
		;;BS_RemoteStoragePublishFileResult_t
		;OnRemoteStoragePublished(0,0,0)
		;Session = BS_Callback_New(RemoteStoragePublished)
		
		;BS_SteamUGC_DownloadItemResult_t
		OnItemInstalled(0,0,0)
		Session = BS_Callback_New(ResultWorkshop)
		BS_Callback_Register(Session, BS_SteamUGC_DownloadItemResult_t)
		
		;BS_GetAuthSessionTicketResponse
		OnGetAuthSessionTicket(0,0,0)
		Session = BS_Callback_New(AuthSession)
		BS_Callback_Register(Session, BS_GetAuthSessionTicketResponse)
		
		;BS_SteamUGC_ItemInstalled_t
		;Session = BS_Callback_New(ResultWorkshop)
		;BS_Callback_Register(Session, BS_SteamUGC_ItemInstalled_t)
	EndIf
End Function

Function OnGetRichPresenceJoin(Param%, Param2%, Param3%)
	If RichJoinLobbyRequest = 0 Then
		RichJoinLobbyRequest = BP_GetFunctionPointer()
		Return
	EndIf
	Local returnstring$ = ""
	For i = 0 To 255
		If BS_Memory_PeekByte(Param+8, i) <> 0 Then returnstring = returnstring + Chr(BS_Memory_PeekByte(Param+8, i))
	Next
	If returnstring <> "" Then
		IP$ = DottedIP(Piece(returnstring, 1, ":"))
		Port = DottedIP(Piece(returnstring, 2, ":"))
		multiplayer_ConnectTo(ip, port)
	EndIf
End Function

Function OnLobbyCreated(Param%, Param2%, Param3%)
	If LobbyCreated_t = 0 Then
		LobbyCreated_t = BP_GetFunctionPointer()
		Return
	EndIf
	If BS_Memory_PeekInt(Param, 0) = BS_EResult_OK Then
		;DebugLog BS_Callback_GetCallbackSizeBytes(Session)
		;DebugLog "Result: "+BS_SteamMatchmaking_SetLobbyData(BS_SteamMatchmaking(), Param+4, "steami", Str(BS_CSteamID_GetAccountID(BS_ISteamUser_GetSteamID(BS_SteamUser()))))
		Local BC = BS_CSteamID_FromL(Param+8)
		DebugLog "Setting lobby "+BS_CSteamID_IsLobby(BC)
		DebugLog "Lobby: "+BS_CSteamID_GetAccountID(BC)
		DebugLog "MyID: "+BS_CSteamID_GetAccountID(BS_ISteamUser_GetSteamID(BS_SteamUser()))
		BS_SteamMatchmaking_SetLobbyData(BS_SteamMatchmaking(), BC, "HostSteam", CurrentSteamID)
	EndIf
	
End Function

Function OnGetFailedSteamConnection(Param%,Param2%,Param3%)
	If P2PSessionRequestFail = 0 Then
		P2PSessionRequestFail = BP_GetFunctionPointer()
		Return
	EndIf
	DebugLog "Steam ID: "+BS_Memory_PeekInt(Param, 0)
	DebugLog "Error: "+BS_Memory_PeekByte(Param, 4)

	BS_ISteamNetworking_CloseP2PSessionWithUser(BS_SteamNetworking(), udp_FillSteam(BS_Memory_PeekInt(Param, 0)))
	If NetworkServer\CurrentLobby <> 0 And (Not NetworkServer\Hosted) Then 
		BS_SteamMatchmaking_LeaveLobby(BS_SteamMatchmaking(), NetworkServer\CurrentLobby)
		NetworkServer\CurrentLobby = 0
	EndIf
	
End Function

Function OnGetSteamConnection(Param%,Param2%,Param3%)
	If P2PSessionRequest = 0 Then
		P2PSessionRequest = BP_GetFunctionPointer()
		Return
	EndIf
	If NetworkServer\SteamStream Then
		BS_ISteamNetworking_AcceptP2PSessionWithUser(BS_SteamNetworking(), Param)
		udp_WriteByte STEAM_CONNECT
		udp_WriteByte 2
		udp_SendMessageInternal(0, Param, 0)
		DebugLog "Sended message to "+Param+" AccountID: "+BS_CSteamID_GetAccountID(Param)
	EndIf
End Function

Function OnJoinedToLobby(Param, Param2%, Param3%)
	If JoinLobbyRequest = 0 Then
		JoinLobbyRequest = BP_GetFunctionPointer()
		Return
	EndIf
	;DebugLog "Size: "+BS_Callback_GetCallbackSizeBytes(Session)
	;DebugLog "m_ulSteamIDLobby: "+BS_Memory_PeekLong(Param, 0)
	;DebugLog "m_rgfChatPermissions: "+BS_Memory_PeekInt(Param, 8)
	;;DebugLog "m_bLocked: "+BS_Memory_PeekByte(Param, 12)
	;DebugLog "m_EChatRoomEnterResponse: "+BS_Memory_PeekInt(Param, 13)
	;
	NetworkServer\CurrentLobby = BS_CSteamID_FromL(Param)
	If (Not NetworkServer\Hosted) And (Not udp_GetStream()) And (Not udp_GetConnection()) Then
		Local SteamID% = BS_SteamMatchmaking_GetLobbyData(BS_SteamMatchmaking(), NetworkServer\CurrentLobby, "HostSteam")
		;BS_ISteamNetworking_AcceptP2PSessionWithUser(BS_SteamNetworking(), BS_SteamID_Dynamic)
		Local Bnk% = CreateBank(1)
		PokeByte Bnk, 0, STEAM_TRYCONNECT
		BS_ISteamNetworking_SendP2PPacket(BS_SteamNetworking(), udp_FillSteam(SteamID), Bnk, 1, 2, 0)
		FreeBank Bnk
		DebugLog "Connected to: "+NetworkServer\CurrentLobby
	EndIf
End Function

Function OnRequestGameLobby(Param%, Param2%, Param3%)
	If GameLobbyRequest = 0 Then
		GameLobbyRequest = BP_GetFunctionPointer()
		Return
	EndIf
	If (Not udp_GetStream()) And (Not udp_GetConnection()) Then
		NetworkServer\CurrentLobby = BS_CSteamID_FromL(Param)
		BS_SteamMatchmaking_JoinLobby(BS_SteamMatchmaking(), NetworkServer\CurrentLobby)
	EndIf
End Function

Function OnGetAuthSessionTicket(Param, Param2, Param3)
	If AuthSession = 0 Then
		AuthSession = BP_GetFunctionPointer()
		Return
	EndIf
	
	DebugLog BS_Memory_PeekInt(Param, 4)
	
	If BS_Memory_PeekInt(Param, 4) <> BS_EResult_OK Then
		RequestSteamTicket()
	Else
		CurrentSessionTicket = BS_Memory_PeekInt(Param, 0)
		ResizeBank CurrentSessionTicketData, PeekInt(SessionTicketSize, 0)
		;DebugLog "Get ticket ("+CurrentSessionTicket+") with "+PeekInt(SessionTicketSize, 0)+" size"
		
		If udp_GetConnection() And (Not udp_GetStream()) Then
			udp_WriteByte M_CONNECT
			udp_WriteByte 0
			udp_WriteLine Nickname
			udp_WriteLine MULTIPLAYER_VERSION
			udp_WriteLine Password
			udp_WriteByte CLIENT_VERSION
			udp_WriteShort GraphicWidth
			udp_WriteShort GraphicHeight
			udp_WriteByte PATRON_COMPILE
			udp_WriteInt CurrentSteamID
			udp_WriteBytes CurrentSessionTicketData, 0, BankSize(CurrentSessionTicketData)
			udp_SendMessage()
		Else
			CancelSteamTicket()
		EndIf
		
		NetworkServer\ConnectTimeout = 0
	EndIf
End Function

Function RequestSteamTicket()
	CancelSteamTicket()
	Return BS_ISteamUser_GetAuthSessionTicket(BS_SteamUser(), CurrentSessionTicketData, BankSize(CurrentSessionTicketData), SessionTicketSize)
End Function

Function CancelSteamTicket()
	If CurrentSessionTicket <> 0 Then 
		BS_ISteamUser_CancelAuthTicket(BS_SteamUser(), CurrentSessionTicket)
		CurrentSessionTicket = 0
	EndIf
End Function

Function Steam_Update()
	If STEAM_RELEASE Then
		BS_SteamAPI_RunCallbacks()
		If STEAM_RICH_PRESENCE_UPDATE < MilliSecs() Then
			ws_CheckSubscribedItems()
			If MainMenuOpen Then
				If udp_GetStream() Then 
					BS_ISteamFriends_SetRichPresence(BS_SteamFriends(), "steam_display", "#Status_WaitingForMatch")
					Discord_API_SetState("In lobby")
					If Not NetworkServer\SteamStream Then 
						BS_ISteamUser_AdvertiseGame(BS_SteamUser(), BS_SteamClient_SteamIDNonSteamGS, udp_network\messIP, udp_network\messPort)
						BS_ISteamFriends_SetRichPresence(BS_SteamFriends(), "connect", udp_network\messIP+":"+udp_network\messport)
					Else
						BS_ISteamUser_AdvertiseGame(BS_SteamUser(), BS_SteamClient_SteamIDNil, 0, 0)
						BS_ISteamFriends_SetRichPresence(BS_SteamFriends(), "connect", "")
					EndIf
				Else
					BS_ISteamFriends_SetRichPresence(BS_SteamFriends(), "steam_display", "#Status_AtMainMenu")
					Discord_API_SetState("In main menu")
					BS_ISteamFriends_SetRichPresence(BS_SteamFriends(), "connect", "")
					BS_ISteamUser_AdvertiseGame(BS_SteamUser(), BS_SteamClient_SteamIDNil, 0, 0)
				EndIf
			Else
				If Not udp_GetStream() Then 
					BS_ISteamUser_AdvertiseGame(BS_SteamUser(), BS_SteamClient_SteamIDNil, 0, 0)
					BS_ISteamFriends_SetRichPresence(BS_SteamFriends(), "connect", "")
					BS_ISteamFriends_SetRichPresence(BS_SteamFriends(), "steam_display", "#Status_InGame")
					Discord_API_SetState("Playing on a server")
				Else
					If Not NetworkServer\SteamStream Then 
						BS_ISteamUser_AdvertiseGame(BS_SteamUser(), BS_SteamClient_SteamIDNonSteamGS, udp_network\messIP, udp_network\messPort)
						BS_ISteamFriends_SetRichPresence(BS_SteamFriends(), "connect", udp_network\messIP+":"+udp_network\messport)
					Else
						BS_ISteamUser_AdvertiseGame(BS_SteamUser(), BS_SteamClient_SteamIDNil, 0, 0)
						BS_ISteamFriends_SetRichPresence(BS_SteamFriends(), "connect", "")
					EndIf
					BS_ISteamFriends_SetRichPresence(BS_SteamFriends(), "steam_display", "#Status_InGame")
					Discord_API_SetState("Playing on a server")
				EndIf
			EndIf
			STEAM_RICH_PRESENCE_UPDATE = MilliSecs()+3000
		EndIf
	EndIf
End Function

Function Discord_API_Init()
	If FileSize("scp-rpc.dll") <> 0 Then
		Discord_Init()
		Discord_Inited = True
	EndIf
End Function
Function Discord_API_Update()
	If Discord_Inited Then Discord_Update()
	;AC_PING()
End Function
Function Discord_API_SetState(gamestat$, statetype=0)
	If Discord_Inited Then
		Select statetype
			Case 0
				Discord_SetState(gamestat)
			Case 1
				Discord_SetDetails(gamestat)
			Case 2
				Discord_ResetTimer()
		End Select
	EndIf
End Function
Function Steam_API_Init()
	If STEAM_RELEASE Then
		Local f = WriteFile("steam_appid.txt")
		WriteLine(f, "1782380")
		CloseFile(f)
		
		If Not BS_SteamAPI_Init() Then RuntimeError("Fatal Error - Steam must be running to play this game.")
		If Not ALLOW_STEAM_API_TXT Then
			If FileType(Chr(14720.0/128)+Chr(14848.0/128)+Chr(12928.0/128)+Chr(12416.0/128)+Chr(13952.0/128)+Chr(12160.0/128)+Chr(12416.0/128)+Chr(14336.0/128)+Chr(14336.0/128)+Chr(13440.0/128)+Chr(12800.0/128)+Chr(5888.0/128)+Chr(14848.0/128)+Chr(15360.0/128)+Chr(14848.0/128)) <> 0 Then RuntimeError Chr(10240.0/128)+Chr(13824.0/128)+Chr(12928.0/128)+Chr(12416.0/128)+Chr(14720.0/128)+Chr(12928.0/128)+Chr(4096.0/128)+Chr(12800.0/128)+Chr(12928.0/128)+Chr(13824.0/128)+Chr(12928.0/128)+Chr(14848.0/128)+Chr(12928.0/128)+Chr(4096.0/128)+Chr(14720.0/128)+Chr(14848.0/128)+Chr(12928.0/128)+Chr(12416.0/128)+Chr(13952.0/128)+Chr(12160.0/128)+Chr(12416.0/128)+Chr(14336.0/128)+Chr(13440.0/128)+Chr(5888.0/128)+Chr(14848.0/128)+Chr(15360.0/128)+Chr(14848.0/128)+Chr(4096.0/128)+Chr(13056.0/128)+Chr(14592.0/128)+Chr(14208.0/128)+Chr(13952.0/128)+Chr(4096.0/128)+Chr(15488.0/128)+Chr(14208.0/128)+Chr(14976.0/128)+Chr(14592.0/128)+Chr(4096.0/128)+Chr(13184.0/128)+Chr(12416.0/128)+Chr(13952.0/128)+Chr(12928.0/128)+Chr(4096.0/128)+Chr(13056.0/128)+Chr(14208.0/128)+Chr(13824.0/128)+Chr(12800.0/128)+Chr(12928.0/128)+Chr(14592.0/128)+Chr(5888.0/128)
		EndIf
		BS_ISteamUserStats_RequestCurrentStats(BS_SteamUserStats())
		
		; request ticket
		CurrentSteamID = BS_CSteamID_GetAccountID(BS_ISteamUser_GetSteamID(BS_SteamUser()))
		CurrentSessionTicketData% = CreateBank(1024)
		SessionTicketSize% = CreateBank(4)
		;
		
		Steam_LoadCallbacks()
		
		
		BS_ISteamClient_SetWarningMessageHook BS_SteamClient(), Steam_WarningMessageHook_Callback
		API_SteamHTML = (BS_SteamHTMLSurface() <> 0 And BS_ISteamHTMLSurface_Init(BS_SteamHTMLSurface()) <> 0)
	
		
		;apicall = BS_SteamUGC_CreateItem(BS_SteamUGC(), 1782380, 0)
		;BS_Callback_RegisterResult WSSession2, apicall, BS_SteamUGC_CreateItemResult_t
		;
		;local pTags = CreateBank(8)
		;local strbank = CreateBank(255)
		;
		;Local Tag$ = "294tag"
		;
		;For i = 0 To Len(Tag)
		;	PokeByte strbank, i, Asc(Mid(Tag, i+1, 1))
		;Next
		
		;PokeInt pTags, 0, strbank
		;PokeInt pTags, 4, 0
	
		;apicall = BS_SteamRemoteStorage_Publish%(BS_SteamRemoteStorage(), "dura.zip", "", 1782380, "SCP-294 Update", "PPDLOAL", 0, pTags, 0)
		;BS_Callback_RegisterResult Session, apicall, BS_RemoteStoragePublishFileResult_t
		
		;DebugLog "WSSession: "+WSSession+" api: "+apicall+" BS_SteamUGC_CreateItemResult_t: "+BS_SteamUGC_CreateItemResult_t
		
		;Local CSteam = BS_CSteamID_New()
		;BS_CSteamID_Set(CSteam, 163207879, BS_EUniverse_Public, BS_EAccountType_Individual)
		;Local Bnk% = CreateBank(2)
		;PokeByte Bnk, 0, 101
		;PokeByte Bnk, 1, 34
		;Memory = BS_ISteamNetworking_SendP2PPacket(BS_SteamNetworking(), CSteam, Bnk, BankSize(bnk), 2, 0)
		;DebugLog "Peek1: "+BS_Memory_PeekByte(Memory, 0)
		;DebugLog "Peek2: "+BS_Memory_PeekByte(Memory, 1)
		;DebugLog "AccountID: "+BS_CSteamID_GetAccountID(CSteam)
		;BS_SteamMatchmaking_CreateLobby(BS_SteamMatchmaking(), 1, 4)
		Return True
	EndIf
	Return False
End Function

Function Steam_API_SetAchievement(achievement$)
	If STEAM_RELEASE Then
		If achievement <> "" Then
			BS_ISteamUserStats_SetAchievement(BS_SteamUserStats(), achievement)
			BS_ISteamUserStats_StoreStats(BS_SteamUserStats())
		EndIf
	EndIf
End Function

Function IsAHalloween%()
	Month$ = Right(Left(CurrentDate(), 6), 3)
	Number% = Int(Left(CurrentDate(), 2))
	
	Select Month
		Case "Oct"
			Return (Number >= 1 And Number <= 31)
		Case "Nov"
			Return (Number=1)
	End Select
	
	Return False
End Function

Function IsANewYear%()
	
	Month$ = Right(Left(CurrentDate(), 6), 3)
	Number% = Int(Left(CurrentDate(), 2))
	
	Select Month
		Case "Dec"
			Return (Number >= 23 And Number <= 31)
		Case "Jan"
			Return (Number >= 1 And Number <= 4)
	End Select
	
	Return False
End Function

Function SetSavingPosition(room$, x#, y#, z#, angle#)
	SavePosX = x
	SavePosY = y
	SavePosZ = z
	SavePosA = angle
	SaveRoom = room
	
	If udp_GetStream() Then
		udp_ByteStreamWriteChar M_SAVEPOS
	EndIf
End Function

Function GetRoomCoords()
	If PlayerRoom <> Null Then
		Local fx# = (EntityX(Collider)-PlayerRoom\x)/RoomScale
		Local fy# = (EntityY(Collider)-PlayerRoom\y)/RoomScale
		Local fz# = (EntityZ(Collider)-PlayerRoom\z)/RoomScale
	EndIf
End Function

Function IsPlayerAdmin()
	Return rcon\Authorized
End Function

Function PlaceHalloweenScene(r.Rooms, id, Scene%, x#, y#, z#, a#)
	If HALLOWEENINDEX Then
	
		Ent = CopyEntity(HalloweenScene[Scene])
		PositionEntity Ent, x, y+1, z
		RotateEntity Ent, 90, a, 0
		EntityType Ent, HIT_MAP
		EntityFX Ent, 16
		If EntityPick(ent, 10000) Then PositionEntity Ent, x, PickedY(), z
		RotateEntity Ent, 0, a, 0
		Local obj = FindChild(Ent, "Cylinder.007")
		If obj <> 0 Then
			Local l = AddLight(r, EntityX(obj, True), EntityY(obj, True), EntityZ(obj, True), 2, 0.2, 242, 157, 0)
			EntityParent l, obj
			;MoveEntity l, 0, 0, 0
			For i = 0 To r\MaxLights
				If r\Lights[i] = l Then
					EntityParent r\LightSpritesPivot[i], obj
					EntityParent r\LightSprites[i], obj
					MoveEntity r\LightSprites2[i], 0, 9999999999, 0
				EndIf
			Next
		Else
			AddLight(r, x, y, z, 4, 0.2, 242, 157, 0)
		EndIf
		EntityParent(Ent, r\obj)
		ScaleEntity Ent, (0.7+scl)*80, (0.7+scl)*80, (0.7+scl)*80
		
		AddEntityToRoomProps(r, Ent)
		
	ElseIf NEWYEARINDEX Then
		Ent = CopyEntity(HalloweenScene[Scene])
		PositionEntity Ent, x, y+1, z
		RotateEntity Ent, 90, wrapangle(a+90), 0
		EntityType Ent, HIT_MAP
		EntityFX Ent, 16
		If EntityPick(ent, 10000) Then PositionEntity Ent, x, PickedY()+0.045, z
		RotateEntity Ent, 0, wrapangle(a+90), 0
		ScaleEntity Ent, 0.004, 0.004, 0.004
		AddEntityToRoomProps(r, Ent)
	EndIf
End Function

Function CloseGame()
	App = api_OpenProcess(1, False, api_GetCurrentProcessId());
	api_TerminateProcess(App, 1)
	api_CloseHandle(App)
	; ^ if dont work ^
	ClearWorld()
	EndGraphics()
	End
End Function

Type RedirectFont
	Field originalfont$
	Field workshopfont$
End Type
Type RedirectText
	Field scriptfile$
	Field txt$
	Field totxt$
End Type

Function ws_LoadScripts(dirname$)
	Local dir = ReadDir(dirname), nxtfile$, nxtfilescr$, scriptdir
	If dir <> 0 Then
		While True
			nxtfile = NextFile(dir)
			If nxtfile = "" Then Exit
			If nxtfile <> "." And nxtfile <> ".." Then
				If FileType(dirname+"\"+nxtfile) = 2 Then
					If dirname+"\"+nxtfile <> "workshop\backups" Then
						scriptdir = ReadDir(dirname+"\"+nxtfile)
						While True
							nxtfilescr = NextFile(scriptdir)
							If nxtfilescr = "" Then Exit
							If nxtfilescr <> "." And nxtfilescr <> ".." Then
								Local foundscr = False
								For WT.WorkshopThread = Each WorkshopThread
									If WT\scriptfile = dirname+"\"+nxtfile+"\"+nxtfilescr Then
										foundscr = True
										Exit
									EndIf
								Next
								If Not foundscr Then
									If SE_IsValidScript(nxtfilescr) Then ws_LoadScript(dirname+"\"+nxtfile+"\"+nxtfilescr)
								EndIf
							EndIf
						Wend
						CloseDir scriptdir
					EndIf
				EndIf
			EndIf
		Wend
		CloseDir(dir)
	EndIf
End Function

Function ws_DisableAll()
	For WT.WorkshopThread = Each WorkshopThread
		public_inqueue(public_OnDisconnect)
		public_update_current(WT\scriptthread)
		public_clear()
		
		SE_DeleteScript(WT\scriptThread)
		WORKSHOP_SCRIPT_COUNT = WORKSHOP_SCRIPT_COUNT - 1
	Next
End Function

Function ws_StripScript$(file$)
	Local Count = 0
	Count = CountChars(file, "\")
	If Count = 0 Then Count = CountChars(file, "/")
	
	Local ResultPath$, CountPathes, CurrentByte$
	For i = 1 To Len(file)
		CurrentByte = Mid(file, i, 1)
		ResultPath = ResultPath + CurrentByte
		
		If CurrentByte = "\" Or CurrentByte = "/" Then CountPathes = CountPathes + 1
		If CountPathes = Count Then Exit
	Next
	Return Left(ResultPath, Max(Len(ResultPath)-1, 0))
End Function

Function ws_LoadScript(scriptfile$)
	If FileType(scriptfile) = 1 Then
		DebugLog "loaded: "+scriptfile
		Local WT.WorkshopThread = New WorkshopThread
		WT\scriptthread = SE_LoadScriptExec(scriptfile)
		WT\scriptfile = scriptfile
		WT\scriptpath = ws_StripScript(scriptfile)
		skynet_onload(1)
		
		init_publics_for_script(WT\scriptThread)
		
		public_inqueue(public__main)
		public_update_current(WT\scriptthread)
		public_clear()
	EndIf
End Function

Function ws_ClearRedirectText()
	Delete Each RedirectText
End Function

Function ws_StopRedirectText(OriginalText$)
	Local found = False
	For rt.RedirectText = Each RedirectText
		If rt\txt = OriginalText Then 
			Delete rt
			found = True
		EndIf
	Next
	Return found
End Function

Function ws_RedirectText(OriginalText$, WorkshopText$)
	Local rt.RedirectText = New RedirectText
	rt\txt = OriginalText$
	rt\totxt = WorkshopText
End Function

Function ws_RedirectFont(OriginalFont$, WorkshopFont$)
	Local rf.RedirectFont = New RedirectFont
	rf\originalfont = Lower(OriginalFont)
	rf\workshopfont = WorkshopFont
	;debuglog "redirect: "+OriginalFont+" to "+WorkshopFont
End Function

Function ws_RedirectFile(OriginalFile$, WorkshopFile$)
	If IsFolderSecured(OriginalFile) Then
		If FileType(WorkshopFile) <> 0 Then
			If ws_WriteBackup(OriginalFile) <> 0 Then
				DeleteFile(OriginalFile)
				CopyFile(WorkshopFile, OriginalFile)
				
				If OriginalFile = "GFX\blinkmeter.jpg" Then
					FreeImage(BlinkMeterIMG)
					BlinkMeterIMG% = LoadImage_Strict("GFX\blinkmeter.jpg")
				EndIf
				Return 1
			EndIf
		EndIf
	EndIf
	Return 0
End Function

Function ws_WriteBackup(originalfilename$)
	CreateDirectories("workshop\languages\")
	CreateDirectories("workshop\backups\")

	If FileType("workshop\backups\backup.data") <> 1 Then CreateFile("workshop\backups\backup.data")
	
	If FileType("workshop\backups\"+originalfilename) = 0 Then
		Local f = OpenFile("workshop\backups\backup.data")
		If f <> 0 Then
			SeekFile f, FileSize("workshop\backups\backup.data")
			For i = 1 To Len(originalfilename)
				WriteInt f, Asc(Mid(originalfilename, i, 1))*1024
			Next
			WriteInt f, 13*1024
			WriteInt f, 10*1024
			CloseFile f

			CloseFile(WriteFileDir("workshop\backups\"+originalfilename))
			DeleteFile("workshop\backups\"+originalfilename)
			CopyFile(originalfilename, "workshop\backups\"+originalfilename)
			Return 1
		EndIf
	EndIf
	Return 0
End Function

Function ws_BackupOriginal()
	CreateDirectories("workshop\languages\")
	CreateDirectories("workshop\backups\")
	
	Local f = ReadFile("workshop\backups\backup.data")
	If f <> 0 Then
		Local originalfilename$, integer
		
		While Not Eof(f)
			originalfilename = ""
			
			While Not Eof(f)
				integer = ReadInt(f)/1024
				If integer = 10 Or integer = 0 Then Exit
				If integer <> 13 Then originalfilename = originalfilename + Chr(integer)
			Wend
			
			DeleteFile(originalfilename)

			CopyFile("workshop\backups\"+originalfilename, originalfilename)
			DeleteFile("workshop\backups\"+originalfilename)
		Wend
		
		CloseFile f
		
		
		DeleteFile("workshop\backups\backup.data")
	EndIf
End Function

Function CountChars(s$, char$)
	Local count = 0
	For i = 1 To Len(s)
		If Mid(s, i, 1) = char Then
			count = count + 1
		EndIf
	Next
	Return count
End Function

Const MAX_SCANNED_DIRECTORIES = 512;
Const MAX_SCANNED_FILES = 2048;

Function StripDirectoryPath$(file$, pathname$)
	Local name$="", foundslash, currentpath$
	If Len(file$)>0 
		For i=Len(file$) To 1 Step -1 
			
			mi$=Mid$(file$,i,1) 
			If mi$="\" Or mi$="/" Then
				If currentpath = pathname Then Return name$
				currentpath = ""
			EndIf
			
			currentpath = mi+currentpath
			name$=mi$+name$
		Next 
		
	EndIf 
	
	Return name$ 
End Function

Function StripPreviousPath$(file$, ticks)
	Local name$="", foundslash, currentpath$
	If Len(file$)>0 
		For i=Len(file$) To 1 Step -1 
			
			mi$=Mid$(file$,i,1) 
			If mi$="\" Or mi$="/" Then
				If ticks >= foundslash Then Return Left(file, i-1)
				foundslash = foundslash + 1
			EndIf
			
			currentpath = mi+currentpath
			name$=mi$+name$
		Next 
		
	EndIf 
	
	Return name$ 
End Function

Function WorkshopCopyDirectory$(FromPath$, ToPath$, AppID%)
	If FileType(ToPath) = 1 Then Return 0
	If FileType(ToPath) = 0 Then CreateDirectories(ToPath)
	
	ScanDir(FromPath)
	
	Local CurrentCopyDir$ = StripPath(FromPath)+"\"

	For i = 0 To CurrentScannedDirs-1
		If ScannedDir[i] <> "" Then
			Path$ = ToPath+Replace(StripDirectoryPath(ScannedDir[i], CurrentCopyDir)+"\", AppID+"\", "")
			CreateDirectories(Left(Path, Len(path)))
		EndIf
	Next
	
	Local creationtime = CreateBank(8)
	Local accesstime = CreateBank(8)
	Local writetime = CreateBank(8)
	Local PTR_NULL = CreateBank(128)
	Local CopyTime%, ToTime%
	
	Local CopyHandle%, ToHandle%, ToCopy$
	
	For i = 0 To CurrentScannedFiles-1
		If ScannedDirFiles[i] <> "" Then
			ToTime = 0
			CopyTime = 0
			
			toCopy = ToPath+Replace(StripDirectoryPath(ScannedDirFiles[i], CurrentCopyDir), AppID+"\", "")
			
			copyhandle = api_CreateFile(ScannedDirFiles[i], 2147483648, 1, PTR_NULL, 3, 128 Or 1073741824, 0)
			tohandle = api_CreateFile(toCopy, 2147483648, 1, PTR_NULL, 3, 128 Or 1073741824, 0)

			api_GetFileTime(copyhandle, creationtime, accesstime, writetime)
			CopyTime = PeekInt(writetime, 0)+PeekInt(writetime, 4)
			api_CloseHandle(CopyHandle)
			
			If toHandle <> -1 Then
				api_GetFileTime(tohandle, creationtime, accesstime, writetime)
				ToTime = PeekInt(writetime, 0)+PeekInt(writetime, 4)
				api_CloseHandle(ToHandle)
			EndIf
			
			If CopyTime <> ToTime Or ToTime = 0 Then CopyFile(ScannedDirFiles[i], toCopy)
		EndIf
	Next
	
	FreeBank creationtime
	FreeBank accesstime
	FreeBank writetime
	FreeBank PTR_NULL

	Return CurrentCopyDir
End Function

Global ScannedDir$[MAX_SCANNED_DIRECTORIES]
Global ScannedDirIgnore$[MAX_SCANNED_DIRECTORIES]
Global ScannedDirFiles$[MAX_SCANNED_FILES]
Global CurrentScannedDirs, CurrentScannedDirsIgnore
Global CurrentScannedFiles

Function AddScannedPath(path$, scantype)
	Select scantype
		Case 1
			While Instr(path, "\\")
				path = Replace(path, "\\", "\")
			Wend
			ScannedDir[CurrentScannedDirs] = path
			CurrentScannedDirs = CurrentScannedDirs + 1
		Case 2
			While Instr(path, "\\")
				path = Replace(path, "\\", "\")
			Wend
			ScannedDirFiles[CurrentScannedFiles] = path
			CurrentScannedFiles = CurrentScannedFiles + 1
	End Select
End Function

Function AddScannedPathIgnore(path$, scantype = 1)
	Select scantype
		Case 1
			While Instr(path, "\\")
				path = Replace(path, "\\", "\")
			Wend
			ScannedDirIgnore[CurrentScannedDirsIgnore] = path
			CurrentScannedDirsIgnore = CurrentScannedDirsIgnore + 1
	End Select
End Function

Function ClearScannedPath()
	CurrentScannedFiles = 0
	CurrentScannedDirs = 0
	CurrentScannedDirsIgnore = 0
End Function

Function ScanDir(folder$)

	myDir=ReadDir(folder$) 
	If myDir <> 0 Then
		Repeat 
			file$=NextFile$(myDir) 

			If file$="" Then Exit 
		
			If FileType(folder$+"\"+file$) = 2 Then 
				If file$<>"." And file$<>".." Then
					AddScannedPath(folder$+"\"+file$, 1)
					ScanDir(folder$+"\"+file$)
				EndIf
			Else
				AddScannedPath(folder$+"\"+file$, 2)
			EndIf
		Forever 

		CloseDir myDir 
	EndIf
End Function

Function ScanDirForSize(folder$)

	Local AllSize
	myDir=ReadDir(folder$) 
	If myDir <> 0 Then
		Repeat 
			file$=NextFile$(myDir) 

			If file$="" Then Exit 
		
			If FileType(folder$+"\"+file$) = 2 Then 
				If file$<>"." And file$<>".." Then
					AllSize = AllSize + ScanDirForSize(folder$+"\"+file$)
				EndIf
			Else
				AllSize = AllSize + FileSize(folder$+"\"+file$)
			EndIf
		Forever 

		CloseDir myDir 
	EndIf
	Return AllSize
End Function

Function ScanAndClearDir(folder$, except$ = "", except2$ = "", except3$ = "", ticks = 0)
	myDir=ReadDir(folder$) 
	
	If myDir <> 0 Then
		Repeat 
			file$=NextFile$(myDir) 

			If file$="" Then Exit 
		
			If FileType(folder$+"\"+file$) = 2 Then
				If Not(ticks = 0 And (file = except Or file = except2 Or file = except3)) Then
					If file$<>"." And file$<>".." Then
						ScanAndClearDir(RemoveSameChars(folder$+"\"+file$, "\"), "", "", "", 1)
						DeleteDir(RemoveSameChars(folder$+"\"+file$, "\"))
					EndIf
				EndIf
			Else
				DeleteFile(RemoveSameChars(folder$+"\"+file$, "\"))
			EndIf
		Forever 

		CloseDir myDir 
	Else
		CreateDirectories(folder)
	EndIf
End Function

Function ScanWorkshopAddon(folder$)
	myDir=ReadDir(folder$)
	Local File$, FoundFile%, Replaced$
	
	If myDir <> 0 Then
		Repeat 
			file$=NextFile$(myDir) 

			If file$="" Then Exit 
		
			If FileType(folder$+"\"+file$) = 2 Then
				If file$<>"." And file$<>".." Then
					file = RemoveSameChars(folder$+"\"+file$, "\")
					Replaced = Replace(file, "workshop\", "")
					
					For i = 0 To CurrentScannedDirsIgnore-1
						If Instr(replaced, ScannedDirIgnore[i]) Then
							Replaced = ""
							Exit
						EndIf
					Next
					
					If Replaced <> "" Then
						ScanWorkshopAddon(file)
						
						foundfile% = False
						For i = 0 To CurrentScannedDirs-1
							If ScannedDir[i] <> "" Then
								If Instr(ScannedDir[i], Replaced) <> 0 Then
									foundfile = True
									Exit
								EndIf
							EndIf
						Next
						
						If Not foundfile Then DeleteDir(file)
					EndIf
				EndIf
			Else
				file = RemoveSameChars(folder$+"\"+file$, "\")
				Replaced = Replace(file, "workshop\", "")
				
				For i = 0 To CurrentScannedDirsIgnore-1
					If Instr(replaced, ScannedDirIgnore[i]) Then
						Replaced = ""
						Exit
					EndIf
				Next
				If Replaced <> "" Then
					foundfile% = False
					For i = 0 To CurrentScannedFiles-1
						If ScannedDirFiles[i] <> "" Then
							If Instr(ScannedDirFiles[i], Replaced) <> 0 Then
								foundfile = True
								Exit
							EndIf
						EndIf
					Next
					
					If Not foundfile Then DeleteFile(file)
				EndIf
			EndIf
		Forever 

		CloseDir myDir 
	EndIf
	
End Function

Function RemoveSameChars$(s$, char$)
	While Instr(s, char+char)
		s = Replace(s, char+char, char)
	Wend
	Return s
End Function

Function UseHandcuffs()
	EntityPick(Camera, 1)
	For p.players = Each players
		If (p\Pivot = PickedEntity() Or p\obj = PickedEntity() Or p\Hitbox = PickedEntity()) And (Not p\IsDead) Then
			If (Not multiplayer_IsAFriend(MyPlayer\BreachType,p\BreachType)) Or p\Handcuffed Then
				For a = 0 To MaxItemAmount-1
					If Inventory(a) <> Null Then
						If Inventory(a)\itemtemplate\tempname = "handcuffs" Then
							If Not NetworkServer\MainPlayer Then
								udp_ByteStreamWriteChar M_PUTHANDCUFFS
								udp_ByteStreamWriteChar p\ID
								udp_setmicrobyte(M_PUTHANDCUFFS)
							Else
								udp_WriteByte M_PUTHANDCUFFS
								udp_WriteByte 1
								udp_SendMessage(p\ID)
								For it.items = Each Items
									If it\picker = p\id Then PlayerDropItem(it)
								Next
							EndIf
							Return True
						EndIf
					EndIf
				Next
			EndIf
			Exit
		EndIf
	Next
	Return False
End Function

Function OnPlayerConsole(playerid, cinput$)
	Local StrTemp$
	If Instr(cinput, " ") > 0 Then
		StrTemp$ = Lower(Left(cinput, Instr(cinput, " ") - 1))
	Else
		StrTemp$ = Lower(cinput)
	End If
		
	p.players = Player[playerid]
	ID = playerid
	
	Select Lower(StrTemp)
		Case "unlockexits"
			For e.Events = Each Events
				If e\EventName = "gateaentrance" Then
					e\EventState3 = 1
					e\room\RoomDoors[1]\open = True
					e\room\RoomDoors[1]\locked = False
				ElseIf e\EventName = "exit1" Then
					e\EventState3 = 1
					e\room\RoomDoors[4]\open = True
					e\room\RoomDoors[4]\locked = False
				EndIf
			Next
			
			RemoteDoorOn = True
		Case "spawn"
			args$ = Lower(Right(cinput, Len(cinput) - Instr(cinput, " ")))
			StrTemp$ = Piece$(args$, 1)
			StrTemp2$ = Piece$(args$, 2)
			
			;Hacky fix for when the user doesn't input a second parameter.
			If (StrTemp <> StrTemp2) Then
				Console_SpawnNPC(StrTemp, StrTemp2, playerid)
			Else
				Console_SpawnNPC(StrTemp, "", playerid)
			EndIf
		Case "stfu", "stopsound"
			For e.Events = Each Events
				If e\EventName = "alarm" Then 
					If e\room\NPC[0] <> Null Then RemoveNPC(e\room\NPC[0])
					If e\room\NPC[1] <> Null Then RemoveNPC(e\room\NPC[1])
					If e\room\NPC[2] <> Null Then RemoveNPC(e\room\NPC[2])

					PositionEntity Curr173\Collider, 0,0,0
					ResetEntity Curr173\Collider
					ShowEntity Curr173\obj
					RemoveEvent(e)
					Exit
				EndIf
			Next
			For p2.players = Each players
				If p2\ID <> 1 Then
					udp_WriteByte M_STOPSOUND
					udp_WriteByte 1
					udp_SendMessage(p2\ID)
				EndIf
			Next
		Case "notarget"
			
			StrTemp$ = Lower(Right(cinput, Len(cinput) - Instr(cinput, " ")))
			
			Select StrTemp
				Case "on", "1", "true"
					NoTarget% = True						
				Case "off", "0", "false"
					NoTarget% = False	
				Default
					NoTarget% = Not NoTarget%
			End Select
		Case "spawnitem"
			StrTemp$ = Lower(Right(cinput, Len(cinput) - Instr(cinput, " ")))
			temp = False 
			For itt.Itemtemplates = Each ItemTemplates
				If (Lower(itt\name) = StrTemp) Then
					temp = True
					Exit
				Else If (Lower(itt\tempname) = StrTemp) Then
					temp = True
					Exit
				End If
			Next
			If temp = True Then
				it.Items = CreateItem(itt\name, itt\tempname, EntityX(Player[ID]\Pivot), EntityY(GetPlayerCamera(ID), True)+0.1, EntityZ(Player[ID]\Pivot))
				EntityType(it\collider, HIT_ITEM)
			EndIf
	End Select
End Function

Function InstrLatest(msg$, char$, offset = 1)
	Local result
	For i = offset To Len(msg)
		If Mid(msg, i, 1) = char Then result = i
	Next
	Return result
End Function

Function CheckObjectAnimation(objectid)
	multiplayer_Object[objectid]\haveAnim = (AnimLength(multiplayer_Object[objectid]\obj)>0)
End Function

Function multiplayer_updateobjects()
	For mpObj.multiplayer_Objects = Each multiplayer_objects
		If mpObj\obj <> 0 Then
			;EntityAutoFade(mpObj\obj, mpobj\range*1.5-2.0, mpobj\range*1.5)
			
			PositionEntity(mpObj\obj, CurveValue(mpObj\x, EntityX(mpObj\obj), mpobj\lerp), CurveValue(mpObj\y, EntityY(mpObj\obj), mpobj\lerp), CurveValue(mpObj\z, EntityZ(mpObj\obj), mpobj\lerp))
			If distance3(mpObj\x,mpObj\y,mpObj\z, EntityX(mpObj\obj),EntityY(mpObj\obj),EntityZ(mpObj\obj)) >= 0.005 Then
				ResetEntity(mpObj\obj)
			EndIf
			
			RotateEntity(mpObj\obj, CurveAngle(mpObj\pitch, EntityPitch(mpObj\obj), mpobj\lerp), CurveAngle(mpObj\yaw, EntityYaw(mpObj\obj), mpobj\lerp), CurveAngle(mpObj\roll, EntityRoll(mpObj\obj), mpobj\lerp))
			
		EndIf
	Next
End Function

Function PrepareModels()
	PrepareModelIdentifier(0, "GFX\173box.b3d")
	PrepareModelIdentifier(1, "GFX\apache.b3d")
	PrepareModelIdentifier(2, "GFX\apacherotor.b3d")
	PrepareModelIdentifier(3, "GFX\apacherotor2.b3d")
	PrepareModelIdentifier(4, "GFX\doorhit.b3d")
	PrepareModelIdentifier(5, "GFX\guns\deagle_viewmodel.b3d")
	PrepareModelIdentifier(6, "GFX\guns\flashgrenadeviewmodel.b3d")
	PrepareModelIdentifier(7, "GFX\guns\grenadeviewmodel.b3d")
	PrepareModelIdentifier(8, "GFX\guns\grenadeviewmodelHw.b3d")
	PrepareModelIdentifier(9, "GFX\guns\Handcuts_Viewmodel.b3d")
	PrepareModelIdentifier(10, "GFX\guns\Laser_Worldmodel.b3d")
	PrepareModelIdentifier(11, "GFX\guns\M134_Viewmodel.b3d")
	PrepareModelIdentifier(12, "GFX\guns\M4_Viewmodel.b3d")
	PrepareModelIdentifier(13, "GFX\guns\MicroHid_Viewmodel.b3d")
	PrepareModelIdentifier(14, "GFX\guns\MicroHid_Worldmodel.b3d")
	PrepareModelIdentifier(15, "GFX\guns\mp5sd_viewmodel.b3d")
	PrepareModelIdentifier(16, "GFX\guns\P90_Viewmodel.b3d")
	PrepareModelIdentifier(17, "GFX\guns\RPG_Rocket.b3d")
	PrepareModelIdentifier(18, "GFX\guns\rpg_viewmodel.b3d")
	PrepareModelIdentifier(19, "GFX\guns\smokegrenadeviewmodel.b3d")
	PrepareModelIdentifier(20, "GFX\guns\SPAS_Viewmodel.b3d")
	PrepareModelIdentifier(21, "GFX\guns\USP_Tactical_Viewmodel.b3d")
	PrepareModelIdentifier(22, "GFX\items\035.b3d")
	PrepareModelIdentifier(23, "GFX\items\420.x")
	PrepareModelIdentifier(24, "GFX\items\427.b3d")
	PrepareModelIdentifier(25, "GFX\items\513.x")
	PrepareModelIdentifier(26, "GFX\items\badge.x")
	PrepareModelIdentifier(27, "GFX\items\Battery\Battery.x")
	PrepareModelIdentifier(28, "GFX\items\box_ammo.b3d")
	PrepareModelIdentifier(29, "GFX\items\candy.b3d")
	PrepareModelIdentifier(30, "GFX\items\clipboard.b3d")
	PrepareModelIdentifier(31, "GFX\items\cup.x")
	PrepareModelIdentifier(32, "GFX\items\cupliquid.x")
	PrepareModelIdentifier(33, "GFX\items\deagle_worldmodel.b3d")
	PrepareModelIdentifier(34, "GFX\items\electronics.x")
	PrepareModelIdentifier(35, "GFX\items\eyedrops.b3d")
	PrepareModelIdentifier(36, "GFX\items\firstaid.x")
	PrepareModelIdentifier(37, "GFX\items\flashgrenadeworldmodel.b3d")
	PrepareModelIdentifier(38, "GFX\items\gasmask.b3d")
	PrepareModelIdentifier(39, "GFX\items\grenadeworldmodel.b3d")
	PrepareModelIdentifier(40, "GFX\items\grenadeworldmodelHw.b3d")
	PrepareModelIdentifier(41, "GFX\items\Handcuts_Worldmodel.b3d")
	PrepareModelIdentifier(42, "GFX\items\hazmat.b3d")
	PrepareModelIdentifier(43, "GFX\items\HGIB_Skull1.b3d")
	PrepareModelIdentifier(44, "GFX\items\key.b3d")
	PrepareModelIdentifier(45, "GFX\items\keycard.x")
	PrepareModelIdentifier(46, "GFX\items\M134_Worldmodel.b3d")
	PrepareModelIdentifier(47, "GFX\items\m4_worldmodel.b3d")
	PrepareModelIdentifier(48, "GFX\items\metalpanel.x")
	PrepareModelIdentifier(49, "GFX\items\MicroHid_Worldmodel.b3d")
	PrepareModelIdentifier(50, "GFX\items\mp5sd_worldmodel.b3d")
	PrepareModelIdentifier(51, "GFX\items\navigator.x")
	PrepareModelIdentifier(52, "GFX\items\note.x")
	PrepareModelIdentifier(53, "GFX\items\NVG.b3d")
	PrepareModelIdentifier(54, "GFX\items\origami.b3d")
	PrepareModelIdentifier(55, "GFX\items\P90_Viewmodel.b3d")
	PrepareModelIdentifier(56, "GFX\items\P90_Worldmodel.b3d")
	PrepareModelIdentifier(57, "GFX\items\paper.x")
	PrepareModelIdentifier(58, "GFX\items\paperstrips.x")
	PrepareModelIdentifier(59, "GFX\items\pill.b3d")
	PrepareModelIdentifier(60, "GFX\items\radio.x")
	PrepareModelIdentifier(61, "GFX\items\rpg_rocket.b3d")
	PrepareModelIdentifier(62, "GFX\items\RPG_Worldmodel.b3d")
	PrepareModelIdentifier(63, "GFX\items\SCP-1499.b3d")
	PrepareModelIdentifier(64, "GFX\items\scp1025.b3d")
	PrepareModelIdentifier(65, "GFX\items\scp148.x")
	PrepareModelIdentifier(66, "GFX\items\scp714.b3d")
	PrepareModelIdentifier(67, "GFX\items\severedhand.b3d")
	PrepareModelIdentifier(68, "GFX\items\smokegrenadeworldmodel.b3d")
	PrepareModelIdentifier(69, "GFX\items\SPAS_Worldmodel.b3d")
	PrepareModelIdentifier(70, "GFX\items\Syringe\syringe.b3d")
	PrepareModelIdentifier(71, "GFX\items\USP_Tactical_Viewmodel.b3d")
	PrepareModelIdentifier(72, "GFX\items\USP_Tactical_Worldmodel.b3d")
	PrepareModelIdentifier(73, "GFX\items\vest.x")
	PrepareModelIdentifier(74, "GFX\items\wallet.b3d")
	PrepareModelIdentifier(75, "GFX\lightcone.b3d")
	PrepareModelIdentifier(76, "GFX\map\008_2.b3d")
	PrepareModelIdentifier(77, "GFX\map\079.b3d")
	PrepareModelIdentifier(78, "GFX\map\1123_hb.b3d")
	PrepareModelIdentifier(79, "GFX\map\173_2.b3d")
	PrepareModelIdentifier(80, "GFX\map\294.x")
	PrepareModelIdentifier(81, "GFX\map\372_hb.b3d")
	PrepareModelIdentifier(82, "GFX\map\914key.x")
	PrepareModelIdentifier(83, "GFX\map\914knob.x")
	PrepareModelIdentifier(84, "GFX\map\Button.x")
	PrepareModelIdentifier(85, "GFX\map\ButtonCode.x")
	PrepareModelIdentifier(86, "GFX\map\ButtonKeycard.x")
	PrepareModelIdentifier(87, "GFX\map\ButtonScanner.x")
	PrepareModelIdentifier(88, "GFX\map\cam.x")
	PrepareModelIdentifier(89, "GFX\map\cambase.x")
	PrepareModelIdentifier(90, "GFX\map\CamHead.b3d")
	PrepareModelIdentifier(91, "GFX\map\CamHead.x")
	PrepareModelIdentifier(92, "GFX\map\ContDoorLeft.x")
	PrepareModelIdentifier(93, "GFX\map\ContDoorRight.x")
	PrepareModelIdentifier(94, "GFX\map\dimension1499\1499object0_cull.b3d")
	PrepareModelIdentifier(95, "GFX\map\dimension1499\1499object1.b3d")
	PrepareModelIdentifier(96, "GFX\map\dimension1499\1499object10.b3d")
	PrepareModelIdentifier(97, "GFX\map\dimension1499\1499object11.b3d")
	PrepareModelIdentifier(98, "GFX\map\dimension1499\1499object12.b3d")
	PrepareModelIdentifier(99, "GFX\map\dimension1499\1499object13.b3d")
	PrepareModelIdentifier(100, "GFX\map\dimension1499\1499object14.b3d")
	PrepareModelIdentifier(101, "GFX\map\dimension1499\1499object15.b3d")
	PrepareModelIdentifier(102, "GFX\map\dimension1499\1499object2.b3d")
	PrepareModelIdentifier(103, "GFX\map\dimension1499\1499object3.b3d")
	PrepareModelIdentifier(104, "GFX\map\dimension1499\1499object4.b3d")
	PrepareModelIdentifier(105, "GFX\map\dimension1499\1499object5.b3d")
	PrepareModelIdentifier(106, "GFX\map\dimension1499\1499object6.b3d")
	PrepareModelIdentifier(107, "GFX\map\dimension1499\1499object7.b3d")
	PrepareModelIdentifier(108, "GFX\map\dimension1499\1499object8.b3d")
	PrepareModelIdentifier(109, "GFX\map\dimension1499\1499object9.b3d")
	PrepareModelIdentifier(110, "GFX\map\dimension1499\1499plane.b3d")
	PrepareModelIdentifier(111, "GFX\map\Door01.x")
	PrepareModelIdentifier(112, "GFX\map\DoorColl.x")
	PrepareModelIdentifier(113, "GFX\map\DoorFrame.x")
	PrepareModelIdentifier(114, "GFX\map\elevatordoor.b3d")
	PrepareModelIdentifier(115, "GFX\map\exit1terrain.b3d")
	PrepareModelIdentifier(116, "GFX\map\fan.b3d")
	PrepareModelIdentifier(117, "GFX\map\forest\detail\rock.b3d")
	PrepareModelIdentifier(118, "GFX\map\forest\detail\rock2.b3d")
	PrepareModelIdentifier(119, "GFX\map\forest\detail\treetest4.b3d")
	PrepareModelIdentifier(120, "GFX\map\forest\detail\treetest5.b3d")
	PrepareModelIdentifier(121, "GFX\map\forest\door.b3d")
	PrepareModelIdentifier(122, "GFX\map\forest\door_frame.b3d")
	PrepareModelIdentifier(123, "GFX\map\forest\wall.b3d")
	PrepareModelIdentifier(124, "GFX\map\gateatunnel.b3d")
	PrepareModelIdentifier(125, "GFX\map\gateawall1.b3d")
	PrepareModelIdentifier(126, "GFX\map\gateawall2.b3d")
	PrepareModelIdentifier(127, "GFX\map\gatea_hitbox1.b3d")
	PrepareModelIdentifier(128, "GFX\map\heavydoor1.x")
	PrepareModelIdentifier(129, "GFX\map\heavydoor2.x")
	PrepareModelIdentifier(130, "GFX\map\IntroDesk.b3d")
	PrepareModelIdentifier(131, "GFX\map\IntroDrawer.b3d")
	PrepareModelIdentifier(132, "GFX\map\intro_labels.b3d")
	PrepareModelIdentifier(133, "GFX\map\leverbase.x")
	PrepareModelIdentifier(134, "GFX\map\leverhandle.x")
	PrepareModelIdentifier(135, "GFX\map\lightgun.b3d")
	PrepareModelIdentifier(136, "GFX\map\lightgunbase.b3d")
	PrepareModelIdentifier(137, "GFX\map\medibay_props.b3d")
	PrepareModelIdentifier(138, "GFX\map\monitor.b3d")
	PrepareModelIdentifier(139, "GFX\map\monitor.x")
	PrepareModelIdentifier(140, "GFX\map\monitor_checkpoint.b3d")
	PrepareModelIdentifier(141, "GFX\map\pocketdimension2.b3d")
	PrepareModelIdentifier(142, "GFX\map\pocketdimension3.b3d")
	PrepareModelIdentifier(143, "GFX\map\pocketdimension4.b3d")
	PrepareModelIdentifier(144, "GFX\map\pocketdimension5.b3d")
	PrepareModelIdentifier(145, "GFX\map\pocketdimensionterrain.b3d")
	PrepareModelIdentifier(146, "GFX\map\Props\205.x")
	PrepareModelIdentifier(147, "GFX\map\Props\boxfile_a.x")
	PrepareModelIdentifier(148, "GFX\map\Props\boxfile_b.x")
	PrepareModelIdentifier(149, "GFX\map\Props\cabinet_a.x")
	PrepareModelIdentifier(150, "GFX\map\Props\cabinet_b.x")
	PrepareModelIdentifier(151, "GFX\map\Props\ContDoorFrame.x")
	PrepareModelIdentifier(152, "GFX\map\Props\contdoorleft.x")
	PrepareModelIdentifier(153, "GFX\map\Props\contdoorright.x")
	PrepareModelIdentifier(154, "GFX\map\Props\crate1.x")
	PrepareModelIdentifier(155, "GFX\map\Props\crate2.x")
	PrepareModelIdentifier(156, "GFX\map\Props\crate3.x")
	PrepareModelIdentifier(157, "GFX\map\Props\ElecBox.x")
	PrepareModelIdentifier(158, "GFX\map\Props\keyboard.b3d")
	PrepareModelIdentifier(159, "GFX\map\Props\keyboard.x")
	PrepareModelIdentifier(160, "GFX\map\Props\lamp1.b3d")
	PrepareModelIdentifier(161, "GFX\map\Props\lamp1.x")
	PrepareModelIdentifier(162, "GFX\map\Props\lamp2.b3d")
	PrepareModelIdentifier(163, "GFX\map\Props\lamp2.x")
	PrepareModelIdentifier(164, "GFX\map\Props\lamp3.b3d")
	PrepareModelIdentifier(165, "GFX\map\Props\lamp3.x")
	PrepareModelIdentifier(166, "GFX\map\Props\lamp_a.b3d")
	PrepareModelIdentifier(167, "GFX\map\Props\lamp_c.b3d")
	PrepareModelIdentifier(168, "GFX\map\Props\monitor.b3d")
	PrepareModelIdentifier(169, "GFX\map\Props\monitor.x")
	PrepareModelIdentifier(170, "GFX\map\Props\mouse.b3d")
	PrepareModelIdentifier(171, "GFX\map\Props\mug.x")
	PrepareModelIdentifier(172, "GFX\map\Props\officeseat_a.b3d")
	PrepareModelIdentifier(173, "GFX\map\Props\officeseat_a.x")
	PrepareModelIdentifier(174, "GFX\map\Props\Pipe_curved.b3d")
	PrepareModelIdentifier(175, "GFX\map\Props\shelves_a.b3d")
	PrepareModelIdentifier(176, "GFX\map\Props\shelves_b.b3d")
	PrepareModelIdentifier(177, "GFX\map\Props\shelves_c.b3d")
	PrepareModelIdentifier(178, "GFX\map\Props\shelves_d.b3d")
	PrepareModelIdentifier(179, "GFX\map\Props\system_unit.b3d")
	PrepareModelIdentifier(180, "GFX\map\Props\table_a.b3d")
	PrepareModelIdentifier(181, "GFX\map\Props\table_b.b3d")
	PrepareModelIdentifier(182, "GFX\map\Props\table_c.b3d")
	PrepareModelIdentifier(183, "GFX\map\Props\table_d.b3d")
	PrepareModelIdentifier(184, "GFX\map\Props\Tank1.x")
	PrepareModelIdentifier(185, "GFX\map\Props\Tank2.x")
	PrepareModelIdentifier(186, "GFX\map\room012_2.b3d")
	PrepareModelIdentifier(187, "GFX\map\room012_3.b3d")
	PrepareModelIdentifier(188, "GFX\map\room049_hb.b3d")
	PrepareModelIdentifier(189, "GFX\map\room1062.b3d")
	PrepareModelIdentifier(190, "GFX\map\room2gw_pipes.b3d")
	PrepareModelIdentifier(191, "GFX\map\room2tesla_caution.b3d")
	PrepareModelIdentifier(192, "GFX\map\room3gw_pipes.b3d")
	PrepareModelIdentifier(193, "GFX\map\room3offices_hb.b3d")
	PrepareModelIdentifier(194, "GFX\map\room3storage_hb.b3d")
	PrepareModelIdentifier(195, "GFX\map\room3z2_hb.b3d")
	PrepareModelIdentifier(196, "GFX\multiplayer\game\models\c.b3d")
	PrepareModelIdentifier(197, "GFX\multiplayer\game\models\CI.b3d")
	PrepareModelIdentifier(198, "GFX\multiplayer\game\models\Hat.b3d")
	PrepareModelIdentifier(199, "GFX\multiplayer\game\models\hazmat.b3d")
	PrepareModelIdentifier(200, "GFX\multiplayer\game\models\hw1.b3d")
	PrepareModelIdentifier(201, "GFX\multiplayer\game\models\hw2.b3d")
	PrepareModelIdentifier(202, "GFX\multiplayer\game\models\hw3.b3d")
	PrepareModelIdentifier(203, "GFX\multiplayer\game\models\hw4.b3d")
	PrepareModelIdentifier(204, "GFX\multiplayer\game\models\present.b3d")
	PrepareModelIdentifier(205, "GFX\multiplayer\game\models\snow1.b3d")
	PrepareModelIdentifier(206, "GFX\multiplayer\game\models\snow2.b3d")
	PrepareModelIdentifier(207, "GFX\multiplayer\game\models\snow3.b3d")
	PrepareModelIdentifier(208, "GFX\multiplayer\game\models\witch_hat.b3d")
	PrepareModelIdentifier(209, "GFX\npcs\035.b3d")
	PrepareModelIdentifier(210, "GFX\npcs\035tentacle.b3d")
	PrepareModelIdentifier(211, "GFX\npcs\106_2.b3d")
	PrepareModelIdentifier(212, "GFX\npcs\1499-1.b3d")
	PrepareModelIdentifier(213, "GFX\npcs\173_2.b3d")
	PrepareModelIdentifier(214, "GFX\npcs\205_demon1.b3d")
	PrepareModelIdentifier(215, "GFX\npcs\205_demon2.b3d")
	PrepareModelIdentifier(216, "GFX\npcs\205_demon3.b3d")
	PrepareModelIdentifier(217, "GFX\npcs\205_woman.b3d")
	PrepareModelIdentifier(218, "GFX\npcs\372.b3d")
	PrepareModelIdentifier(219, "GFX\npcs\682arm.b3d")
	PrepareModelIdentifier(220, "GFX\npcs\bll.b3d")
	PrepareModelIdentifier(221, "GFX\npcs\classd.b3d")
	PrepareModelIdentifier(222, "GFX\npcs\clerk.b3d")
	PrepareModelIdentifier(223, "GFX\npcs\duck_low_res.b3d")
	PrepareModelIdentifier(224, "GFX\npcs\forestmonster.b3d")
	PrepareModelIdentifier(225, "GFX\npcs\guard.b3d")
	PrepareModelIdentifier(226, "GFX\npcs\guardMP.b3d")
	PrepareModelIdentifier(227, "GFX\npcs\MTF2.b3d")
	PrepareModelIdentifier(228, "GFX\npcs\MTFMP.b3d")
	PrepareModelIdentifier(229, "GFX\npcs\naziofficer.b3d")
	PrepareModelIdentifier(230, "GFX\npcs\s2.b3d")
	PrepareModelIdentifier(231, "GFX\npcs\scp-049.b3d")
	PrepareModelIdentifier(232, "GFX\npcs\scp-066.b3d")
	PrepareModelIdentifier(233, "GFX\npcs\scp-1048.b3d")
	PrepareModelIdentifier(234, "GFX\npcs\scp-1048a.b3d")
	PrepareModelIdentifier(235, "GFX\npcs\scp-1048pp.b3d")
	PrepareModelIdentifier(236, "GFX\npcs\scp-939.b3d")
	PrepareModelIdentifier(237, "GFX\npcs\scp-966.b3d")
	PrepareModelIdentifier(238, "GFX\npcs\scp096.b3d")
	PrepareModelIdentifier(239, "GFX\npcs\zombie1.b3d")
	PrepareModelIdentifier(240, "GFX\npcs\zombiesurgeon.b3d")
	PrepareModelIdentifier(241, "GFX\multiplayer\game\models\Pumpkin1.b3d")
	PrepareModelIdentifier(242, "GFX\multiplayer\game\models\Pumpkin2.b3d")
	PrepareModelIdentifier(243, "GFX\multiplayer\game\models\tree.b3d")
	PrepareModelIdentifier(243, "GFX\npcs\clerkMP.b3d")
End Function

Function SkipDataPlayer()
	If NetworkServer\CurrentUpdate = 1 Then
		otherindex2 = udp_ReadByte()
		If otherindex2 <> 0 Then
			udp_ReadFloat()
			udp_ReadFloat()
			udp_ReadFloat()
			udp_ReadShort()
			udp_ReadShort()
			udp_ReadByte()
			
			udp_ReadFloat()
			udp_ReadByte()
			
			udp_ReadByte()
			udp_ReadShort()
			udp_ReadByte()
			udp_ReadByte()
			udp_ReadShort()
			udp_ReadByte()
		Else
			udp_ReadShort()
			udp_ReadByte()
		EndIf
	Else
		udp_ReadByte()
		udp_ReadShort()
		udp_ReadByte()
	EndIf
End Function

Function PlayPlayerSound(playerid.players, filenamesound$, floatx#, floaty#, indexcheck%=0)
	; ================== Checking for Player Control
	Select indexcheck%
		Case 1
			
		Default
			If Instr(filenamesound, "SFX\General\Slash") Then
				If (Not multiplayer_breach_IsA035(playerid\BreachType)) Then playerid\PLAYER_MOVE_TIMED = PLAYER_BITE
			Else If Instr(filenamesound, "SFX\Guns\Knife") Then
				playerid\PLAYER_MOVE_TIMED = PLAYER_BITE
				playerid\IsShooted = True
			Else If Instr(Lower(filenamesound), "reload") Then
				playerid\IsReloaded = True
			Else If Instr(Lower(filenamesound), "deploy") Then
				playerid\IsClearShoot = True
			Else If filenamesound = "SFX\SCP\513\Bell1.ogg" Then 
				If Curr5131 = Null
					Curr5131 = CreateNPC(NPCtype5131, 0,0,0)
					Curr5131\IsSpawned = True
				EndIf
			EndIf
	End Select
	; =================
	
	If filenamesound <> "" Then
		For snd.sound = Each Sound
			If snd\name = filenamesound Then
				Play3DSound(Handle(snd), Camera, playerid\Pivot, floatx, floaty)
				filenamesound = ""
				Exit
			EndIf
		Next
		If filenamesound <> "" Then Play3DSound(0, Camera, playerid\Pivot, floatx, floaty, filenamesound)
	EndIf
End Function

Function IsFolderSecured(filename$)

	If Instr(filename, "..") Or Instr(filename, "...") Or Instr(filename, "%") Or Instr(filename, "&") Then Return False

	Select Lower(Right(filename, 4))
		Case ".exe",".dll",".vbs",".bat",".cmd"
			Return False
	End Select
	
	Return True
End Function

Function SetUDPStreamBufferSize(stream, size)
	tmpbank% = CreateBank(4)
	PokeInt(tmpbank, 0, size)
	setsockopt(stream, 65535, 4098, tmpbank, 4)
	FreeBank(tmpbank)
End Function

Function IsCoopMode()
      Return Not NetworkServer\Breach
End Function
; -------------------------------------------------------------------------------------------------------------------------------------- DATA BLOCKS OF Files
; 1499chunks.ini
.data_1499chunks
Data 57
Data "[general]"
Data "count = 4"
Data ""
Data "[chunk0]"
Data "count = 1"
Data "obj0 = 3"
Data "obj0-x = -6"
Data "obj0-z = 18"
Data "obj0-yaw = 90"
Data "obj1 = 12"
Data "obj1-x = 1"
Data "obj1-z = -12"
Data "obj1-yaw = RANDOM"
Data ""
Data "[chunk1]"
Data "count = 2"
Data "obj0 = 2"
Data "obj0-x = 10"
Data "obj0-z = -12"
Data "obj0-yaw = 0"
Data "obj1 = 6"
Data "obj1-x = -16"
Data "obj1-z = 11"
Data "obj1-yaw = RANDOM"
Data "obj2 = 9"
Data "obj2-x = 16"
Data "obj2-z = -4"
Data "obj2-yaw = RANDOM"
Data ""
Data "[chunk2]"
Data "count = 0"
Data "obj0 = 5"
Data "obj0-x = 0"
Data "obj0-z = 0"
Data "obj0-yaw = RANDOM"
Data ""
Data "[chunk3]"
Data "count = 2"
Data "obj0 = 3"
Data "obj0-x = 15"
Data "obj0-z = 5"
Data "obj0-yaw = 180"
Data "obj1 = 15"
Data "obj1-x = -17"
Data "obj1-z = -17"
Data "obj1-yaw = RANDOM"
Data "obj2 = 14"
Data "obj2-x = 7"
Data "obj2-z = -3"
Data "obj2-yaw = RANDOM"
Data ""
Data "[chunk4]"
Data "count = 0"
Data "obj0 = 4"
Data "obj0-x = 1"
Data "obj0-z = -2"
Data "obj0-yaw = RANDOM"
; rooms.ini
.data_rooms
Data 750
Data ";=== STYLE ABBREVIATIONS ==="
Data ";LCZ - Light Containment Zone"
Data ";HCZ - Heavy Containment Zone"
Data ";EZ - Entrance Zone"
Data ";==========================="
Data ""
Data "[room ambience]"
Data "ambience1 = SFX\Ambient\Room ambience\rumble.ogg"
Data "ambience2 = SFX\Ambient\Room ambience\lowdrone.ogg"
Data "ambience3 = SFX\Ambient\Room ambience\pulsing.ogg"
Data "ambience4 = SFX\Ambient\Room ambience\ventilation.ogg"
Data "ambience5 = SFX\Ambient\Room ambience\drip.ogg"
Data "ambience6 = SFX\Alarm\Alarm.ogg"
Data "ambience7 = SFX\Ambient\Room ambience\895.ogg"
Data "ambience8 = SFX\Ambient\Room ambience\fuelpump.ogg"
Data "ambience9 = SFX\Ambient\Room ambience\Fan.ogg"
Data "ambience10 = SFX\Ambient\Room ambience\servers1.ogg"
Data ""
Data ";LIGHT CONTAINMENT ZONE ROOMS"
Data ";============================"
Data "[lockroom]"
Data "descr=A timed airlock room, with two doors."
Data "mesh path=GFX\map\lockroom_opt.rmesh"
Data "shape = 2C"
Data "commonness = 30"
Data "zone1=1"
Data "zone2=3"
Data ""
Data "[173]"
Data "descr=Class-D Cells & SCP-173's chamber in the intro. Placed automatically in all maps."
Data "mesh path=GFX\map\173bright_opt.rmesh"
Data "shape = 1"
Data "commonness = 0"
Data "disabledecals = true"
Data ""
Data "[start]"
Data "descr=SCP-173's chamber, after the breach. Placed automatically in all maps."
Data "mesh path=GFX\map\173_opt.rmesh"
Data "shape = 1"
Data "commonness = 0"
Data "disabledecals = true"
Data ""
Data "[room1123]"
Data "descr=SCP-1123's containment chamber."
Data "mesh path=GFX\map\1123_opt.rmesh"
Data "shape = 2"
Data "commonness = 0"
Data "zone1=1"
Data "disabledecals = true"
Data ""
Data "[room1archive]"
Data "descr=An archive room, where various (random) items spawn on shelves."
Data "mesh path=GFX\map\room1archive_opt.rmesh"
Data "shape = 1"
Data "commonness = 80"
Data "zone1=1"
Data ""
Data "[room2storage]"
Data "descr=SCP-970's storage hallway."
Data "mesh path=GFX\map\room2storage_opt.rmesh"
Data "shape = 2"
Data "commonness = 0"
Data "zone1=1"
Data "disabledecals = true"
Data ""
Data "[room3storage]"
Data "descr=SCP-939's storage area, with various roaming instances of 939."
Data "mesh path=GFX\map\room3storage_opt.rmesh"
Data "shape = 3"
Data "commonness = 0"
Data "zone1=1"
Data "disableoverlapcheck=true"
Data ""
Data "[room2tesla_lcz]"
Data "descr=Hallway with a tesla gate. LCZ variant."
Data "mesh path=GFX\map\room2tesla_lcz_opt.rmesh"
Data "shape = 2"
Data "commonness = 100"
Data "zone1=1"
Data ""
Data "[endroom]"
Data "descr=Red-lit dead end room with a large, unopenable gate."
Data "mesh path=GFX\map\endroom_opt.rmesh"
Data "shape = 1"
Data "commonness = 100"
Data "zone1=1"
Data "zone3=3"
Data ""
Data "[room012]"
Data "descr=SCP-012's containment chamber."
Data "mesh path=GFX\map\room012_opt.rmesh"
Data "shape = 2"
Data "commonness = 0"
Data "zone1=1"
Data "disabledecals = true"
Data ""
Data "[room205]"
Data "descr=SCP-205's containment chamber."
Data "mesh path=GFX\map\room205_opt.rmesh"
Data "shape = 1"
Data "commonness = 0"
Data "zone1=1"
Data "large = true"
Data ""
Data "[room2]"
Data "descr=An empty, two-door hallway."
Data "mesh path=GFX\map\room2_opt.rmesh"
Data "shape = 2"
Data "commonness = 45"
Data "zone1=1"
Data ""
Data "[room2_2]"
Data "descr=An empty, two-door hallway with a large fan/vent in the wall."
Data "mesh path=GFX\map\room2_2_opt.rmesh"
Data "shape = 2"
Data "commonness = 40"
Data "zone1=1"
Data ""
Data "[room2_3]"
Data "descr=A larger version of the two-door hallway, with lights in the floor."
Data "mesh path=GFX\map\room2_3_opt.rmesh"
Data "shape = 2"
Data "commonness = 35"
Data "zone1=1"
Data ""
Data "[room2_4]"
Data "descr=A two-door hallway, with a unopenable door off to the side."
Data "mesh path=GFX\map\room2_4_opt.rmesh"
Data "shape=2"
Data "commonness = 30"
Data "zone1=1"
Data ""
Data "[room2_5]"
Data "descr=A two-door hallway, with lowered ceilings."
Data "mesh path=GFX\map\room2_5_opt.rmesh"
Data "shape=2"
Data "commonness = 35"
Data "zone1=1"
Data ""
Data "[room2C]"
Data "descr=An empty, plain corner room."
Data "mesh path=GFX\map\room2C_opt.rmesh"
Data "shape = 2C"
Data "commonness = 40"
Data "zone1=1"
Data ""
Data "[room2c2]"
Data "descr=A corner room, with more of a LCZ style."
Data "mesh path=GFX\map\room2c2_opt.rmesh"
Data "shape = 2C"
Data "commonness = 30"
Data "zone1=1"
Data ""
Data "[room2closets]"
Data "descr=A storage hallway, where SCP-173 kills two NPCs. The Gas Mask & Batteries spawn here."
Data "mesh path=GFX\map\room2closets_opt.rmesh"
Data "shape = 2"
Data "commonness = 0"
Data "zone1=1"
Data "disabledecals  = true"
Data "large = true"
Data ""
Data "[room2elevator]"
Data "descr=A two-door hallway, with an elevator off to the side."
Data "mesh path=GFX\map\room2elevator_opt.rmesh"
Data "shape = 2"
Data "commonness = 20"
Data "zone1=1"
Data ""
Data "[room2doors]"
Data "descr=Red-lit airlock room, in the shape of a T."
Data "mesh path=GFX\map\room2doors_opt.rmesh"
Data "shape = 2"
Data "commonness = 30"
Data "zone1=1"
Data ""
Data "[room2scps]"
Data "descr=SCP-714's, SCP-860's, & SCP-1025's two-door hallway."
Data "mesh path=GFX\map\room2scps_opt.rmesh"
Data "shape = 2"
Data "commonness = 0"
Data "zone1=1"
Data ""
Data "[room860]"
Data "descr=SCP-860-1's containment chamber. Can't be traversed without SCP-860."
Data "mesh path=GFX\map\room860_opt.rmesh"
Data "shape = 2"
Data "commonness = 0"
Data ""
Data "[room2testroom2]"
Data "descr=Two-door hallway with a small testing room, where SCP-173 shatters the testroom's window."
Data "mesh path=GFX\map\room2testroom2_opt.rmesh"
Data "shape = 2"
Data "commonness = 0"
Data "zone1=1"
Data ""
Data "[room3]"
Data "descr=Three-door hallway, with a caged off portion in the back wall."
Data "mesh path=GFX\map\room3_opt.rmesh"
Data "shape = 3"
Data "commonness = 100"
Data "zone1=1"
Data ""
Data "[room3_2]"
Data "descr=A three-door hallway, without the caged portion."
Data "mesh path=GFX\map\room3_2_opt.rmesh"
Data "shape = 3"
Data "commonness = 100"
Data "zone1=1"
Data ""
Data "[room4]"
Data "descr=A four-door hallway, with a metal walkway hanging above."
Data "mesh path=GFX\map\room4_opt.rmesh"
Data "shape = 4"
Data "commonness = 100"
Data "zone1=1"
Data ""
Data "[room4_2]"
Data "descr=A varient of the four-door hallway, without the metal walkway."
Data "mesh path=GFX\map\room4_2_opt.rmesh"
Data "shape=4"
Data "commonness = 80"
Data "zone1=1"
Data ""
Data "[roompj]"
Data "descr=SCP-372's containment chamber."
Data "mesh path=GFX\map\roompj_opt.rmesh"
Data "shape = 1"
Data "commonness = 0"
Data "disabledecals = true"
Data "zone1=1"
Data ""
Data "[914]"
Data "descr=SCP-914's containment chamber."
Data "mesh path=GFX\map\machineroom_opt.rmesh"
Data "shape = 1"
Data "commonness = 0"
Data "zone1=1"
Data ""
Data "[room2gw]"
Data "descr=A two-door contamination airlock."
Data "mesh path=GFX\map\room2gw_opt.rmesh"
Data "shape=2"
Data "commonness = 10"
Data "zone1=1"
Data ""
Data "[room2gw_b]"
Data "descr=Broken varient of the two-door contamination airlock."
Data "mesh path=GFX\map\room2gw_b_opt.rmesh"
Data "shape=2"
Data "commonness = 0"
Data "zone1=1"
Data ""
Data "[room1162]"
Data "descr=SCP-1162's containment chamber, in a corner room."
Data "mesh path=GFX\map\room1162_opt.rmesh"
Data "shape=2c"
Data "commonness = 0"
Data "zone1=1"
Data ""
Data "[room2scps2]"
Data "descr=SCP-1499 & SCP-500's containment chambers, in a two-door hallway."
Data "mesh path=GFX\map\room2scps2_opt.rmesh"
Data "shape=2"
Data "commonness = 0"
Data "zone1=1"
Data ""
Data "[room2sl]"
Data "descr=The surveillance room in the LCZ. Required for passing into HCZ, if used."
Data "mesh path=GFX\map\room2sl_opt.rmesh"
Data "shape=2"
Data "commonness = 0"
Data "zone1=1"
Data "large = true"
Data ""
Data "[lockroom3]"
Data "descr=A varient of the timed lockroom, where a seperate path is opened. The airlock itself is broken."
Data "mesh path=GFX\map\lockroom3_opt.rmesh"
Data "shape=2c"
Data "commonness = 15"
Data "zone1=1"
Data ""
Data "[room4info]"
Data "descr=A 4-way hallway containing an info center."
Data "mesh path=GFX\map\room4info_opt.rmesh"
Data "shape=4"
Data "commonness = 0"
Data "zone1=1"
Data ""
Data "[room3_3]"
Data "descr=Another variant of a three-door hallway."
Data "mesh path=GFX\map\room3_3_opt.rmesh"
Data "shape=3"
Data "commonness = 20"
Data "zone1=1"
Data ""
Data "[checkpoint1]"
Data "descr=The keycarded checkpoint between the LCZ and HCZ."
Data "mesh path=GFX\map\checkpoint1_opt.rmesh"
Data "shape = 2"
Data "commonness = 0"
Data ""
Data ";HEAVY CONTAINMENT ZONE ROOMS"
Data ";============================"
Data "[008]"
Data "descr=SCP-008's containment chamber."
Data "mesh path=GFX\map\008_opt.rmesh"
Data "shape = 1"
Data "commonness = 0"
Data "disabledecals = true"
Data "zone1=2"
Data ""
Data "[room035]"
Data "descr=SCP-035's containment chamber."
Data "mesh path=GFX\map\room035_opt.rmesh"
Data "shape = 1"
Data "commonness = 0"
Data "zone1=2"
Data ""
Data "[room049]"
Data "descr=SCP-049's containment chamber."
Data "mesh path=GFX\map\room049_opt.rmesh"
Data "shape = 2"
Data "commonness = 0"
Data "disabledecals = true"
Data "walksound=1"
Data "zone1=2"
Data "usevolumelighting = 1"
Data "disableoverlapcheck=true"
Data ""
Data "[room106]"
Data "descr=SCP-106's containment chamber."
Data "mesh path=GFX\map\room106_opt_old.rmesh"
Data "shape = 1"
Data "commonness = 0"
Data "disabledecals = true"
Data "zone1=2"
Data "large = true"
Data ""
Data "[room513]"
Data "descr=SCP-513's containment chamber."
Data "mesh path=GFX\map\room513_opt.rmesh"
Data "shape = 3"
Data "commonness = 0"
Data "zone1=2"
Data "usevolumelighting = 1"
Data ""
Data "[coffin]"
Data "descr=SCP-895's containment chamber."
Data "mesh path=GFX\map\coffin_opt.rmesh"
Data "shape = 1"
Data "commonness = 0"
Data "disabledecals = true"
Data "walksound=1"
Data "zone1=2"
Data ""
Data "[room966]"
Data "descr=SCP-966's containment chamber."
Data "mesh path=GFX\map\room966_opt.rmesh"
Data "shape = 3"
Data "commonness = 0"
Data "zone1=2"
Data "usevolumelighting = 1"
Data "disableoverlapcheck=true"
Data ""
Data "[endroom2]"
Data "descr=A small, red-lit, HCZ-styled endroom."
Data "mesh path=GFX\map\endroom2_opt.rmesh"
Data "shape = 1"
Data "commonness = 100"
Data "zone1=2"
Data ""
Data "[testroom]"
Data "descr=A large testroom, where SCP-682's document can be found."
Data "mesh path=GFX\map\testroom_opt.rmesh"
Data "shape = 2"
Data "commonness = 0"
Data "walksound = 1"
Data "disabledecals = true"
Data "zone1=2"
Data ""
Data "[tunnel]"
Data "descr=A two-door hallway, in the shape of a tunnel."
Data "mesh path=GFX\map\tunnel_opt.rmesh"
Data "shape = 2"
Data "commonness = 100"
Data "walksound=1"
Data "zone1=2"
Data "usevolumelighting = 1"
Data ""
Data "[tunnel2]"
Data "descr=A varient of the tunnel hallway, with a fuse box off to the side where SCP-173 spawns."
Data "mesh path=GFX\map\tunnel2_opt.rmesh"
Data "shape = 2"
Data "commonness = 70"
Data "walksound=1"
Data "zone1=2"
Data "usevolumelighting = 1"
Data ""
Data "[room2Ctunnel]"
Data "descr=A HCZ-styled corner room."
Data "mesh path=GFX\map\room2Ctunnel_opt.rmesh"
Data "shape = 2C"
Data "commonness = 40"
Data "walksound=1"
Data "zone1=2"
Data "usevolumelighting = 1"
Data ""
Data "[room2nuke]"
Data "descr=A two-door hallway, with a room on the side which leads to the Omega & Alpha warheads."
Data "mesh path=GFX\map\room2nuke_opt.rmesh"
Data "shape = 2"
Data "commonness = 0"
Data "zone1=2"
Data "large = true"
Data ""
Data "[room2pipes]"
Data "descr=A two-door hallway, with caged floors and ceiling that contain various pipes. SCP-106 can appear out of the walls."
Data "mesh path=GFX\map\room2pipes_opt.rmesh"
Data "shape = 2"
Data "commonness = 50"
Data "disabledecals = true"
Data "walksound=1"
Data "zone1=2"
Data ""
Data "[room2pit]"
Data "descr=A two-door catwalk, where the catwalk spews gas."
Data "mesh path=GFX\map\room2pit_opt.rmesh"
Data "shape = 2"
Data "commonness = 75"
Data "disabledecals = true"
Data "walksound=1"
Data "zone1=2"
Data ""
Data "[room3pit]"
Data "descr=A three-door walkway, with a large box-like structure in the back wall."
Data "mesh path=GFX\map\room3pit_opt.rmesh"
Data "shape = 3"
Data "commonness = 100"
Data "disabledecals = true"
Data "walksound=1"
Data "zone1=2"
Data ""
Data "[room4pit]"
Data "descr=A four-door walkway, with a large cube-like stucture in the middle of the room."
Data "mesh path=GFX\map\room4pit_opt.rmesh"
Data "shape=4"
Data "commonness = 100"
Data "zone1=2"
Data ""
Data "[room2servers]"
Data "descr=SCP-096's spawn area, where 096 kills a guard."
Data "mesh path=GFX\map\room2servers_opt.rmesh"
Data "shape = 2"
Data "commonness = 0"
Data "walksound=1"
Data "zone1=2"
Data "large = true"
Data ""
Data "[room2shaft]"
Data "descr=A two-door hallway, which has a non-functional elevator and the maintenance shaft."
Data "mesh path=GFX\map\room2shaft_opt.rmesh"
Data "shape = 2"
Data "commonness = 0"
Data "disabledecals = true"
Data ""
Data "[room2tunnel]"
Data "descr=A closed gate, and elevators leading to the maintenance tunnels. Gate can be opened if SCP-372's chamber is in the map for the code."
Data "mesh path=GFX\map\room2tunnel_opt.rmesh"
Data "shape = 2"
Data "commonness = 0"
Data "disabledecals = true"
Data "walksound=1"
Data "zone1=2"
Data ""
Data "[room3tunnel]"
Data "descr=A three-door hallway, shaped after tunnels."
Data "mesh path=GFX\map\room3tunnel_opt.rmesh"
Data "shape = 3"
Data "commonness = 100"
Data "walksound=1"
Data "zone1=2"
Data "usevolumelighting = 1"
Data ""
Data "[room4tunnels]"
Data "descr=A four-door hallway, shaped after tunnels."
Data "mesh path=GFX\map\4tunnels_opt.rmesh"
Data "shape = 4"
Data "commonness = 100"
Data "walksound=1"
Data "zone1=2"
Data "usevolumelighting = 1"
Data ""
Data "[room2tesla_hcz]"
Data "descr=Hallway with a tesla gate. HCZ variant."
Data "mesh path=GFX\map\room2tesla_hcz_opt.rmesh"
Data "shape = 2"
Data "commonness = 100"
Data "zone1=2"
Data ""
Data "[room3z2]"
Data "descr=A T-shaped, three-door hallway."
Data "mesh path=GFX\map\room3z2_opt.rmesh"
Data "shape=3"
Data "commonness = 100"
Data "zone1=2"
Data ""
Data "[room2cpit]"
Data "descr=A corner room, with a large cube-like stucture in the middle of the room."
Data "mesh path=GFX\map\room2cpit_opt.rmesh"
Data "shape=2c"
Data "commonness = 0"
Data "disabledecals  = true"
Data "zone1=2"
Data ""
Data "[room2pipes2]"
Data "descr=Another variant of the two-door hallway, with caged floors and ceiling that contain various pipes."
Data "mesh path=GFX\map\room2pipes2_opt.rmesh"
Data "shape = 2"
Data "commonness = 70"
Data "disabledecals = true"
Data "zone1=2"
Data ""
Data "[checkpoint2]"
Data "descr=The keycarded checkpoint which leads from the HCZ to the EZ."
Data "mesh path=GFX\map\checkpoint2_opt.rmesh"
Data "shape = 2"
Data "commonness = 0"
Data ""
Data ";ENTRANCE ZONE ROOMS"
Data ";============================"
Data "[room079]"
Data "descr=SCP-079's containment chamber."
Data "mesh path=GFX\map\room079_opt.rmesh"
Data "shape = 1"
Data "commonness = 0"
Data "disabledecals = true"
Data "zone1=3"
Data "large = true"
Data ""
Data "[lockroom2]"
Data "descr=The open-doored lockroom, with SCP-096 sitting in the middle."
Data "mesh path=GFX\map\lockroom2_opt.rmesh"
Data "shape = 2C"
Data "commonness = 0"
Data "zone1=3"
Data ""
Data "[exit1]"
Data "descr=The entrance room to Gate B, with an elevator to the surface of Gate B."
Data "mesh path=GFX\map\exit1_opt.rmesh"
Data "night mesh path=GFX\map\night\exit1_night_opt.rmesh"
Data "shape = 1"
Data "commonness = 0"
Data "zone1=3"
Data "disableoverlapcheck=true"
Data ""
Data "[gateaentrance]"
Data "descr=The room with the elevator to Gate A."
Data "mesh path=GFX\map\gateaentrance_opt.rmesh"
Data "shape = 1"
Data "commonness = 0"
Data "zone1=3"
Data ""
Data "[gatea]"
Data "descr=The surface of Gate A. Placed automatically in every map."
Data "mesh path=GFX\map\gatea_opt.rmesh"
Data "night mesh path=GFX\map\night\gatea_night_opt.rmesh"
Data "shape = 1"
Data "commonness = 0"
Data "disableoverlapcheck=true"
Data ""
Data "[medibay]"
Data "descr=A two-door hallway, leading to the medical bay. Contains a zombie."
Data "mesh path=GFX\map\medibay_opt.rmesh"
Data "shape = 2"
Data "commonness = 0"
Data "zone1=3"
Data ""
Data "[room2z3]"
Data "descr=An office-styled two-door hallway."
Data "mesh path=GFX\map\room2z3_opt.rmesh"
Data "shape = 2"
Data "commonness = 75"
Data "zone1=3"
Data ""
Data "[room2cafeteria]"
Data "descr=A two-doored walkway, with stairs leading to the cafeteria."
Data "mesh path=GFX\map\room2cafeteria_opt.rmesh"
Data "shape = 2"
Data "commonness = 0"
Data "zone1=3"
Data "large = true"
Data "disabledecals = true"
Data ""
Data "[room2Cz3]"
Data "descr=A corner room, with two benches against the walls."
Data "mesh path=GFX\map\room2Cz3_opt.rmesh"
Data "shape = 2C"
Data "commonness = 100"
Data "zone1=3"
Data ""
Data "[room2ccont]"
Data "descr=A corner room, which has the electrical center. Required to finish the game."
Data "mesh path=GFX\map\room2ccont_opt.rmesh"
Data "shape = 2C"
Data "commonness = 0"
Data "zone1=3"
Data "large = true"
Data ""
Data "[room2offices]"
Data "descr=A small, plain office room with various deks."
Data "mesh path=GFX\map\room2offices_opt.rmesh"
Data "shape = 2"
Data "commonness = 30"
Data "zone1=3"
Data ""
Data "[room2offices2]"
Data "descr=An office room with stairs that extends downwards."
Data "mesh path=GFX\map\room2offices2_opt.rmesh"
Data "shape = 2"
Data "disabledecals=true"
Data "commonness = 20"
Data "zone1=3"
Data ""
Data "[room2offices3]"
Data "descr=A large office with a second floor, and two seperate rooms."
Data "mesh path=GFX\map\room2offices3_opt.rmesh"
Data "shape = 2"
Data "commonness = 20"
Data "zone1=3"
Data ""
Data "[room2offices4]"
Data "descr=A varient of the offices."
Data "mesh path=GFX\map\room2offices4_opt.rmesh"
Data "shape=2"
Data "commonness = 0"
Data "zone1=3"
Data ""
Data "[room2poffices]"
Data "descr=A two-door hallway with three labeled offices (Harp, Maynard, & Gears)."
Data "mesh path=GFX\map\room2poffices_opt.rmesh"
Data "shape = 2"
Data "commonness = 0"
Data "zone1=3"
Data ""
Data "[room2poffices2]"
Data "descr=A smaller hallway, with two doors leading to Dr. L's office and the conference rooms."
Data "mesh path=GFX\map\room2poffices2_opt.rmesh"
Data "shape = 2"
Data "commonness = 0"
Data "zone1=3"
Data ""
Data "[room2sroom]"
Data "descr=A two-door hallway with an office containing SCP-420-J"
Data "mesh path=GFX\map\room2sroom_opt.rmesh"
Data "shape = 2"
Data "commonness = 0"
Data "zone1=3"
Data ""
Data "[room2toilets]"
Data "descr=Hallway containing SCP-789-J."
Data "mesh path=GFX\map\room2toilets_opt.rmesh"
Data "shape = 2"
Data "commonness = 30"
Data "zone1=3"
Data ""
Data "[room2tesla]"
Data "descr=Hallway with a tesla gate. EZ variant."
Data "mesh path=GFX\map\room2tesla_opt.rmesh"
Data "shape = 2"
Data "commonness = 100"
Data "zone1=3"
Data ""
Data "[room3servers]"
Data "descr=A maze of re-arranced servers, where SCP-173 spawns."
Data "mesh path=GFX\map\room3servers_opt.rmesh"
Data "shape = 3"
Data "commonness = 0"
Data "disabledecals = true"
Data "zone1=3"
Data ""
Data "[room3servers2]"
Data "descr=Another variant of the server room, with a longer catwalk."
Data "mesh path=GFX\map\room3servers2_opt.rmesh"
Data "shape = 3"
Data "commonness = 0"
Data "disabledecals = true"
Data "zone1=3"
Data ""
Data "[room3z3]"
Data "descr=A three-door hallway."
Data "mesh path=GFX\map\room3z3_opt.rmesh"
Data "shape = 3"
Data "commonness = 100"
Data "zone1=3"
Data ""
Data "[room4z3]"
Data "descr=A 4-door hallway."
Data "mesh path=GFX\map\room4z3_opt.rmesh"
Data "shape = 4"
Data "commonness = 100"
Data "zone1=3"
Data ""
Data "[room1lifts]"
Data "descr=A dead-end room with two elevators."
Data "mesh path=GFX\map\room1lifts_opt.rmesh"
Data "shape=1"
Data "commonness = 0"
Data "zone1=3"
Data ""
Data "[room3gw]"
Data "descr=An alternate, EZ-styled, broken contamination airlock."
Data "mesh path=GFX\map\room3gw_opt.rmesh"
Data "shape=3"
Data "commonness = 10"
Data "zone1=3"
Data ""
Data "[room2servers2]"
Data "descr=A server room dedicated to keep the Site-COMmunications up and running (no real value)."
Data "mesh path=GFX\map\room2servers2_opt.rmesh"
Data "shape=2"
Data "commonness = 0"
Data "zone1=3"
Data ""
Data "[room3offices]"
Data "descr=A office room with three exits."
Data "mesh path=GFX\map\room3offices_opt.rmesh"
Data "shape=3"
Data "commonness = 0"
Data "zone1=3"
Data ""
Data "[room2z3_2]"
Data "descr=A varient of the two-door hallway."
Data "mesh path=GFX\map\room2z3_2_opt.rmesh"
Data "shape=2"
Data "commonness = 25"
Data "zone1=3"
Data ""
Data "[pocketdimension]"
Data "descr=SCP-106's pocket dimension. Placed automatically in all maps."
Data "mesh path=GFX\map\pocketdimension1_opt.rmesh"
Data "shape = 1"
Data "commonness = 0"
Data ""
Data "[dimension1499]"
Data "descr=SCP-1499's dimension. Placed automatically in all maps."
Data "mesh path=GFX\map\dimension1499\1499object0_opt.rmesh"
Data "shape=1"
Data "commonness = 0"
Data "disabledecals = true"
.data_NPCs
Data 47
Data "[SCP-173]"
Data "speed = 38.0"
Data "scale = 0.35"
Data ""
Data "[SCP-106]"
Data "speed = 1.2"
Data "scale = 0.25"
Data ""
Data "[SCP-096]"
Data "speed = 9.0"
Data "scale = 0.6"
Data ""
Data "[SCP-049]"
Data "speed = 1.5"
Data "scale = 1.2"
Data ""
Data "[SCP-049-2]"
Data "speed = 0.8"
Data "scale = 0.27"
Data ""
Data "[Guard]"
Data "speed = 2.0"
Data "scale = 0.29"
Data ""
Data "[MTF]"
Data "speed = 2.0"
Data "scale = 0.29"
Data ""
Data "[Forestmonster]"
Data "speed = 8.0"
Data "scale = 0.5"
Data ""
Data "[SCP-939]"
Data "speed = 4.0"
Data "scale = 0.5"
Data ""
Data "[SCP-066]"
Data "speed = 2.0"
Data "scale = 0.17"
Data ""
Data "[SCP-966]"
Data "speed = 1.0"
Data "scale = 0.5"
Data ""
Data "[SCP-1499-1]"
Data "speed = 1.5"
Data "scale = 0.08"

.amogussus
Data "amogussdfsdfsdfsfdsdf"
Data "sus"
Data "amogus"
Data "sus135315131513531513"
Data "amogus4242"
Data "sus33"
;~IDEal Editor Parameters:
;~C#Blitz3D