; SERVER;
Include "Source Code\bytestream.bb"
Include "Source Code\compiler\compiler.bb"
Include "Source Code\easybank.bb"
; ----------------------------------------- Map

Const COMPILER_SYSTEM = False
Const STANDART_MAX_PLAYERS = 16
Const STANDART_JUMP_HEIGHT = 23
Const STANDART_TIME_OUT = 15000
Const CPU_COMPILE% = False
Global STEAM_RELEASE% = False
Const PACKET_LOSS_PERCENT = 0

; NETWORK ;
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
Const M_RESETOBJ = 17
Const M_UNLOCKEXITS = 18
Const M_NEWEVENT = 19
Const M_NEWEVENT2 = 20
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
Const M_NEWEVENTS = 34
Const M_SERVERLIST = 35
Const M_SERVERPING = 36
Const M_FEMUR = 37
Const M_EVENT173 = 38
Const M_NPCS = 39
Const M_SERVERCLOSE = 40
Const M_SETTEXTURE = 41
Const M_STOPSOUND = 42
Const M_EVENTALARM = 43
Const M_REMOVEITEM = 44
Const M_ITEMSSYNC = 45
Const M_SHOOT = 46
Const M_BREACH = 47
Const M_SHOOT2 = 48
Const M_RESETBREACH = 49
Const M_SYNCDOOR = 50
Const M_REMOTEDOOR = 51
Const M_GETROLE = 52
Const M_KILL2 = 53
Const M_FIXBREACH = 54
Const M_ANNOUNC2 = 55
Const M_FIXELEVATORS = 56
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
Const M_VOICESTOP = 123
Const M_HEALTHREDUCE = 124
Const M_WANTFILES = 125
Const M_DONECONNECTION = 126
Const M_WRITEELEVATORS = 127
Const M_KNIFEDAMAGE = 128
Const M_OBJECTSOUND = 129
Const M_WANTINTERCOM = 130
Const M_CORPSESSYNC = 131
; ==============================================================================
Type versions
	Field vID$
End Type
Const MULTIPLAYER_MOD_HOST_RESERVE$ = "http://lanylow.xyz/"
Const MULTIPLAYER_MOD_HOST$ = "http://scpmphost.ucoz.net/"
Const M_DEFAULT = 0
Const MP_PATCH$ = ".5"
Global MP_VERSION$ = Version(66057)+MP_PATCH ; hackers cant find this :)
Const CLIENT_VERSION = 2

AddVersion(MP_VERSION)
; ==============================================================================
Const MAX_NPCs = 255
Const MAX_ITEMS = 1000
Const MAX_DOORS = 500
Const MAX_ROOMS = 200
Const MAX_EVENTS = 110
Const MAX_ROCKETS = 200
Const MAX_OBJECTS = 255
Const MAX_DRAWS = 32
Const MAX_TEXTS = 32
Const MAX_CORPSES = 256

Const MAX_PLAYERS = 65

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
Global MODEL_CLERK = 17 ;newcode
Global MODEL_WAIT = 18

Const FIRST_BREACH_TYPE = 1
Global LAST_BREACH_TYPE = 0

Const MAX_SCPS = 5

Const CATEGORY_STALEMATE = 8
Const CATEGORY_CHAOS = 6
Const CATEGORY_NTF = 7
Const CATEGORY_ANOMALY = 5
Const CATEGORY_STAFF = 4
Const CATEGORY_SECURITY = 3
Const CATEGORY_WORKERS = 2
Const CATEGORY_TRASH = 1
Const CATEGORY_NULL = 0

Const MAX_VARIABLES = 512
Const MAX_PLAYER_VARIABLES = 256

Const ITEM_MAX_DIVIDE = 4

Const MAX_BYTES = 7

Const MAX_PLAYER_DIRS = 1



Global ShouldStartRound%
Global NETWORK_BYTE[MAX_BYTES]

NETWORK_BYTE[1] = M_NPCSYNC
NETWORK_BYTE[2] = M_SYNCDOOR
NETWORK_BYTE[3] = M_ROOMOBJECTS
NETWORK_BYTE[4] = M_FIXEDITEMS
NETWORK_BYTE[5] = M_EVENTSWRITE
NETWORK_BYTE[6] = M_WRITEELEVATORS
NETWORK_BYTE[7] = M_CORPSESSYNC

Type errs
	Field txt$
End Type
Type timers
	Field scriptthread.SE_Script
	Field paramsdata.bs, identifiers$
	Field interval, updater, loop
	Field timerfunc.SE_FuncPtr
	Field LuaState, LuaFunc$
End Type

Type ChatMessage
	Field MessageID
	Field Txt$
	Field Timer%
	Field PlayerAccept[MAX_PLAYERS]
End Type
Const MAX_CHAT_MESSAGES = 30
; - - ;
; - SERVER DATA - ;
Global MainPlayer
; - - ;
Type mp_objects
	Field modelid%, objectid%
	Field X#, Y#, Z#
	Field rX#, rY#, rZ#
	Field scale#, animtime#
	Field bytesSpend
	Field ent
	Field isdynamic#
	Field visibled[MAX_PLAYERS]
	Field dynamic[MAX_PLAYERS]
	Field lerp
	Field prevdata$
End Type
Const DRAW_RECT = 1, DRAW_OVAL = 2
Type texts
	Field playerid
	Field fntname$, fntsize#
	Field txt$
	Field x, y
	Field txtColor
	Field bytesSpend
End Type
Type draws
	Field playerid
	Field filename$, drawtype
	Field x, y, width, height
	Field drawColor
	Field bytesSpend
End Type
Type ScriptsThread
	Field scriptThread.SE_Script
	Field scriptname$
	Field status$
	Field luathread%
End Type
Type MultiplayerServer
	Field ConnectServer
	Field Port$
	Field NoCheat
	Field tickrate%,tickratedelay%
	Field Servername$
	Field UpdateTimer
	Field RandomSeed$,IntroEnabled
	Field IsStarted
	Field ServerVoice
	Field PlayersCount
	Field FileLog
	Field timeout
	Field Password$
	Field EventProb#
	Field TCPServer%, TCPServerPort%
	Field Maxplayers
	Field JumpMode
	Field JumpHeight#
	Field Breach
	Field BreachTime, BreachLight, i_Delay[3]
	Field CurrSync
	Field Difficulty$, PrevDifficulty$
	Field RconPassword$
	Field onlydm
	Field deathmatchround%
	Field keepitems
	Field voicekhz
	Field netbyte
	Field sae
	Field pspawnX#, pspawnY#, pspawnZ#, pspawnname$
	Field LobbyDisabled
	Field TimerToKick%, AutoKick%
	Field Description$, gamestate$, weburl$
	Field PrevStart%
	Field CustomMap$
	Field breachchat
	Field GlobalBlink
	Field NetworkTicks%
	Field logsoff
	Field LongCulling%
	Field MapSize%
	Field minplayerstostart, resettimerafterconnect
	Field ReloadApplicationRounds, ReloadApplicationRoundsInternal
	Field FullSync%
	Field FallDamage%, Prediction%, Interpolation%
	Field DescLines$[20]
	Field BulletAnticheat%
	Field Pickitems%
	Field FilesSendActivate%
	Field GetAuthkey%
	Field OldConnectionResponse
	Field MaxSpawnPlayers
	Field DisableTimestamp
	Field CentralServer%
	Field CentralServerTCPRequest%
	Field FriendlyFire%
	Field CameraShakeOnDamage%
	Field NoclipAnticheat%, AC_TeleportRate#
	Field menuhtml$,restartmenuhtml$
	Field IntercomTimeout, IntercomUsingTime, DisableIntercom
	Field BindedIP$
	Field ItemsMoveOnCollision%
	Field max_receive_bytes
	Field CurrentGlobalBlink,CurrentGlobalBlinkInterval
	Field FriendlyFireExplosion
	Field MaxIConnectionsFromIP
	Field recvbuffermult
End Type
Type breach
	Field PrevBreachTimer, BreachTimer, LobbyTimer, PrevNTFTimeoutTimer
	Field TimeoutBreach
	Field NTFTimeout
	Field Exploded, ExplodeTimeout
	Field Teams
	Field MTFTickets#, ChaosTickets#
	Field CurrentWarheadText$
	Field CuffedScientistsEscaped,CuffedClassDEscaped,ClassDEscaped,ScientistsEscaped
End Type
Type g_I
	Field SecondaryLightOn#
	Field MTFTimer#
	Field RemoteDoorOn
	Field Contained106
	Field gameStarted%
	Field b.breach
	Field breachrolecount[MAX_BREACH_ROLES]
	Field br_time
	;Field SendPackets
End Type
Type types_ticks
	Field chatTicks
End Type
; Breach Roles
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
	Field categoryimportance%
	
	Field Instructions$
	Field DeadAnimationData
	Field AmbientSoundAnims$[MAX_ROLES_ANIMATIONS]
	Field AnimationFrameData[MAX_ROLES_ANIMATIONS]
	Field AnimationSpeed#[MAX_ROLES_ANIMATIONS]
	Field AnimationArmedFrameData[MAX_ROLES_ANIMATIONS]
	Field AnimationArmedSpeed#[MAX_ROLES_ANIMATIONS]
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
	
	Field SpawnItem$[10]
	Field speedmult#
	Field MaxRoleCount%
	Field AllowToGenerate%
	Field CopiedGroup%
End Type
Global BreachTypesArray.breachtypes[MAX_BREACH_ROLES]

Type servervariables
	Field name$
	Field value$
End Type
Type playervariables
	Field name$
	Field value$
End Type
Const MAX_MIDDLE_TICK = 4
Const MAX_SHOOT_TICKS = 1
Global DYNAMIC_SHOOT_TICKS = 1
Type players
	Field x#, y#, z#,yaw#, prevyaw#, prevpitch#
	Field prevx#, prevy#, prevz#, PacketLoss%, PacketLossX#, PacketLossZ#, PreviousPacketLoss
	Field IP%, Port%
	Field name$
	Field BonePitch#, BlinkTimer#
	Field WearingGasMask%,WearingNightVision%,WearingHazmat%,WearingVest%
	Field IsLoad%,Timeout%,IPstr$
	Field Ready%, CrouchState#,PlayerSoundVolume#
	Field Size#,PrevSize#
	Field ID%
	Field Talking%
	Field PlayerRoomID%
	Field ping%
	Field checkping%
	Field UsedWeapon%
	Field BreachType%
	Field PLAYER_MOVE%
	Field p_byte%
	Field TextureID%
	Field PlayerIP$
	Field Authorized%
	Field lastping%
	Field drawpointers.draws[MAX_DRAWS], textpointers.texts[MAX_TEXTS]
	Field variables.playervariables[MAX_PLAYER_VARIABLES]
	Field pingupdate
	Field textsTicks
	Field drawsTicks
	Field objectsTicks
	
	Field textsTicksContain
	Field drawsTicksContain
	Field objectsTicksContain
	
	Field chatTicks, version$
	Field Fake%
	Field SelectedItem, CurrentRadio
	Field ingame%, isdead%
	Field Health#, Injuries#
	Field Pivot, AnticheatPivot%, Obj, PlayerHead,PlayerHand, PlayerRoomName$, Camera, Hitbox
	Field KickTimer
	Field monitor_width, monitor_height
	Field prevbreachtype
	Field mapload%
	Field patron
	Field wearingdir, velocity#
	
	Field MoveLeft, MoveRight, MoveForward, MoveBack, MoveJump%, MoveSprint%, CurrSpeed#, Stamina#, DropSpeed#, MoveJumpUpdate%, MoveUpdate%
	Field ShowPlayer%, JumpState#, LastXSpeed#, LastZSpeed#
	Field MouseUpdate
	Field Noclip%, NoclipSpeed#
	Field MaxHealth
	Field SavePosX#, SavePosY#, SavePosZ#, SaveRoom
	Field Godmode%
	Field CollisionTicks%
	Field LatestPlayerSound$
	Field Factor#, FactorFloat#, LastTick%, PrevFactorFloat#, FactorFloatStatic#, ResetTicks#
	Field Shake#
	Field Raged%, ragedupdate%, ragestart#
	Field insomniaupdate%
	Field StaminaEffect%, StaminaEffectTimer%


	Field FPSfactor#
	Field SpeedMult#
	;Field PacketID%
	;Field LastPacketID%
	;Field LastClientMsg%
	Field CanShoot
	Field Announcement%, CheckIntercom%, TimeIntercom%
	Field SoundUpdate%,DecalUpdate%,ChatUpdate%
	Field MicroHIDSHOOT, MicroHIDItem, MicroHIDAnticheatTimer%
	Field PlayerSteamID%
	Field ForestYaw#, PocketDimensionState
	Field ForceMove#
	Field DimensionPrevX#, DimensionPrevY#, DimensionPrevZ#, DimensionPrevRoom.Rooms
	Field PreviousRemovedItemTempName$
	Field HandCuffed%, Muted%
	Field HandCufftimeout
	Field SinkholeState#
	Field DirYaw#, DirPitch#
	Field PlayerDirs, DynamicDirs
	Field PlayerDir[MAX_PLAYER_DIRS], PlayerDirAngle#[MAX_PLAYER_DIRS], PlayerDirPitch#[MAX_PLAYER_DIRS], PlayerDirFactor#[MAX_PLAYER_DIRS]
	Field NeedFixX#, NeedFixY#, NeedFixZ#
	Field Packets, WrongPredictions, MaxPacketID%
	Field DeadTicks%
	Field Tag$, TagR, TagG, TagB
	Field PlayerCSteamID%
	Field ShouldAnnouncement
	Field ShouldPlayerTeleport%, CurrentPositionID%
	Field ShouldPlayerTeleportX#,ShouldPlayerTeleportY#,ShouldPlayerTeleportZ#,ShouldPlayerTeleportRoom$, ShouldPlayerTeleportIsID
	Field PrevPlayerRoomID%
	Field TCPStream%
	Field ShouldDebugPosition
	Field AC_ShouldSpeed#,AC_PositionTick#
	Field ShouldSendShoot.bs[MAX_SHOOT_TICKS], ShouldSendShootTicks
End Type
Type eventq
	Field eventname$
	Field state#
	Field remove%
End Type
Type banned
	Field name$, IP$, ID
End Type
Type rcon
	Field command$
End Type
Type querys
	Field file$
	Field offset%
	Field fullsize%
	Field playerid%
	Field downloadspeed%
	Field filehandle%
	Field isascript
	Field updatetime
	Field compressedfullsize%
End Type
Type SpawnPoint
	Field playerid,role
	Field room$
	Field x#,y#,z#
End Type
Type AntiDDOS
	Field IP%, Count%
End Type
SeedRnd MilliSecs()
; =====================================
Include "Source Code\Players.bb"
; =====================================
Global Player.players[MAX_PLAYERS]
Global PlayerOptimize.players[MAX_PLAYERS]
Global gameInfo.g_I = New g_I
Global Server.MultiplayerServer = New MultiplayerServer
Global multiplayer_Object.mp_objects[MAX_OBJECTS]
Global s_variables.servervariables[MAX_VARIABLES]
Global ticks.types_ticks = New types_ticks
Global scriptbstream.bs
Global CompactBank.bs[1]
Global SCRIPT_COUNT
Global ShouldRestartServer%
Global FixedTimeSteps
Global incomingversion$, incomingpatron

Global Multiplayer_Models$[65535]
Global NEWYEARINDEX% = IsANewYear()
Global HALLOWEENINDEX% = IsAHalloween()

; ================================================== IgnoreData
Global IgnoreBank% = CreateBank(128)
Function ReadIgnoreBytes(s)
	ReadBytes(IgnoreBank, Server\ConnectServer, 0, s)
End Function
Function ReadIgnoreLine()
	ReadLine(Server\ConnectServer)
End Function
; ==================================================

; ------------------------------ difs
Type Difficulty
	Field name$
	Field description$
	Field permaDeath%
	Field aggressiveNPCs
	Field saveType%
	Field otherFactors%
	
	Field r%
	Field g%
	Field b%
	
	Field customizable%
End Type

Dim difficulties.Difficulty(4)
Global SelectedDifficulty.Difficulty

Global FrameLimit% = 300
; =============================
Global FAKE_STREAM_IP = HostIP(CountHostIPs("localhost"))
Global QUERY_GLOBAL_DATA = CreateBank(1)
; STEAM ==============================================================

Include "..\game\SourceCode\BlitzSteamOnlyConstants.bb"
Include "Source Code\ServerSteamAuth.bb"
Include "Source Code\SteamInstances.bb"
; =================================================================================

If COMPILER_SYSTEM Then
	SE_Init()
	compiler_init()
Else
	server_init()
EndIf

Global fpse, dsk
Global errorupd


Include "Source Code\Ticks.bb"
Include "Source Code\Map\main.bb"

server_start()

; + 100-200fps
Function debuglog(z$)

End Function


Function server_start()
	;------------------------------
	
	If GetScripts() Then
		;public_inqueue(public__main, True)
		public_inqueue(public_user_main, True)
		
		public_inqueue(public_OnGenerateWorld, True)
		If SE_GetReturnValue() <> -1 Then Server\RandomSeed = SE_GetReturnValue()
		NullMap()
		InitNewGame()

		public_inqueue(public_OnServerStart)
		public_addparam(0, Server\Port)
		callback
	Else
		NullMap()
		InitNewGame()
	EndIf
	AddLog("Server successfully started.")
	
	RequestDataFromGlobal()
	;AddCentralServer("127.0.0.1", 6696)
	;ConnectToCentralServer()
	Server\ConnectServer = ReloadUDPStream(Server\ConnectServer)
	SetUDPStreamBufferSize(Server\ConnectServer, 8192*Server\recvbuffermult)
	
	Repeat
		;if millisecs() > dsk Then
		;	debuglog "Server FPS: "+fpse+" FPS Factor: "+FPSfactor
		;	fpse = 0
		;	dsk = MilliSecs()+1000
		;	bulletsc = 0
		;EndIf
		UDPReceive()
		UpdateMap()
		UpdateServer()
		fpse = fpse + 1
		
		If ShouldRestartServer Then 
			RestartServer()
			ShouldRestartServer = False
		EndIf
	Forever
End Function
Function server_init()
	Graphics3D 1,1, 32,2
	api_ShowWindow(SystemProperty("AppHWnd"), 0)
	; =============================
	RCON_AddCommand("login")
	RCON_AddCommand("kick")
	RCON_AddCommand("banip")
	RCON_AddCommand("reloadbanlist")
	RCON_AddCommand("hostname")
	RCON_AddCommand("status")
	RCON_AddCommand("commands")
	RCON_AddCommand("gravity")
	RCON_AddCommand("password")
	RCON_AddCommand("restart")
	RCON_AddCommand("seed")
	RCON_AddCommand("difficulty")
	RCON_AddCommand("startmatch")
	RCON_AddCommand("getip")
	RCON_AddCommand("getipid")
	RCON_AddCommand("getid")
	RCON_AddCommand("banid")
	RCON_AddCommand("size")
	; ==============================
	
	InitServer()
	;if int(getserversettings("checkforupdates")) = 1 And (Not STEAM_RELEASE) Then CheckForUpdates()
	
	gameInfo\b.breach = New breach

	scriptbstream.bs = CreateByteStream(8192)
	CompactBank[0] = CreateByteStream(8192)
	CompactBank[1] = CreateByteStream(8192)
	AddLog("Creating scene...", 0, False)
	
	PrepareModels()
End Function
Function callback(fromScript = False)
	SE_RETURN_VALUE\ValueType = SE_NULL
	SE_RETURN_VALUE\StaticIndex = False
	For script.ScriptsThread = Each ScriptsThread
		public_update_current(script, fromScript)
	Next
	public_clear
End Function
Function callbacksingle(script.ScriptsThread, publicid)
	public_inqueue(publicid)
	SE_RETURN_VALUE\ValueType = SE_NULL
	SE_RETURN_VALUE\StaticIndex = False
	public_update_current(script, False)
	public_clear
End Function
Function CheckPlayerTimeout(p.players)
	If p\Fake Then Return False
	If p\Timeout < MilliSecs() Then Return (Not Kick(p\ID, p\name+" has timed out!"))
	If p\Timeout < MilliSecs()+(Server\timeout-2000) Then p\p_byte = ReadBool(p\p_byte, 0) + (2 * ReadBool(p\p_byte, 1)) + (4 * ReadBool(p\p_byte, 2)) + (8 * 1) + (16 * ReadBool(p\p_byte, 4)) + (32 * ReadBool(p\p_byte, 5)) + (64 * p\Announcement)
End Function

Function UpdateServer()
	;udp_clearbuff(Server\ConnectServer
	If GetScripts() Then public_inqueue(public_OnServerUpdate, True)
	
	UpdateSteamAuthConnections()
	mp_UpdatePlayers(1)
	UpdateTimers()
	UpdatePlayersCount()
	UpdateChat()
	breach_Update()
	kick_updater()
	updatefakeplayers()
	updatequerys()
	
	If IsTimedout(3, Server\tickratedelay) Then
		server_network_update()
		chat_network_update()
		objects_network_update()
		draws_network_update()
		texts_network_update()
		
		Server\NetworkTicks = ReverseTo(Server\NetworkTicks+1, 1, MAX_BYTES)
		misc_network_update(Server\NetworkTicks)
	EndIf
	
	If IsTimedout(4, 1500) Then
		For i = 1 To Server\PlayersCount
			udp_WriteByte(M_PING)
			udp_WriteByte(1)
			udp_SendMessage(PlayerOptimize[i]\ID)
			PlayerOptimize[i]\lastping = MilliSecs()
		Next
	EndIf
	
	DYNAMIC_SHOOT_TICKS = Max(Min(FPSfactor*3, MAX_SHOOT_TICKS), 2)
	
	errorstr$ = errorlogex()
	If errorstr <> "" And errorupd < MilliSecs() Then
		AddLog("Detected error: "+errorstr, 0, False)
		errorupd = MilliSecs()+1000
	EndIf
End Function
Function UDPReceive()
	Local IP, Port
	;Local MaxPacketsPerTick% = ((Server\PlayersCount*2)+Server\MaxPlayers)*2
	;Local CurrentRecvPackets% = 0
	
	While RecvUDPMsg(Server\ConnectServer)
		If ReadAvail(Server\ConnectServer) <= 512 Then
			IP = UDPMsgIP(Server\ConnectServer)
			Port = UDPMsgPort(Server\ConnectServer)
			AcceptPacket(ReadByte(Server\ConnectServer), IP, Port)
		EndIf
	Wend
End Function
Function InitServer()
	If CPU_COMPILE = True Then 
		ConsoleLog "CPU VERSION (if your server is frozen, then use the usual version)",7
		Flip
		Delay 100
	EndIf
	
	Server\GetAuthkey = True
	Server\CentralServer = True
	Server\CameraShakeOnDamage = True
	Server\AC_TeleportRate = 0.6
	Server\IntercomTimeout = 60000
	Server\IntercomUsingTime = 20000
	Server\max_receive_bytes = 256
	Server\CurrentGlobalBlink = 600
	Server\CurrentGlobalBlinkInterval = 8000
	Server\MaxIConnectionsFromIP = 8
	Server\recvbuffermult = 64
	
	OnServerStart()
End Function
Function RandomBetween(CONST1, CONST2)
	If Rand(1,2) = 1 Then Return CONST1 Else Return CONST2
End Function
Function AcceptMicroBytePacket(byte, IP, Port, ID, ReceivePlayer.players)
	ReceivePlayer\IsLoad = True
	Select byte%
		Case M_REMOVEITEM
			If (Not ReceivePlayer\isdead) And (Not ReceivePlayer\BreachType = 0) Then
				itID = ReadShort(Server\ConnectServer)
			
				If itID < MAX_ITEMS Then
					it.Items = M_Item[itID]
					If it <> Null Then
						If it\picker = ID Then
							If GetScripts() Then
								pbid = public_inqueue(public_OnPlayerUseItem)
								public_addparam(pbid, ID)
								public_addparam(pbid, itID)
								callback
							EndIf
							If Not SE_RETURN_VALUE\StaticIndex Then 
								ReceivePlayer\PreviousRemovedItemTempName = it\itemtemplate\tempname
								OnPlayerUseItem(ID, it\itemtemplate\tempname)

								RemoveItem(it, False)
							EndIf
							
						EndIf
					EndIf
				EndIf
				Return
			EndIf
			
			ReadIgnoreBytes(2)
		Case M_TAKEITEM
			If ((Not mp_IsASCP(ReceivePlayer\BreachType)) And ReceivePlayer\BreachType <> 0) Or (Not Server\Breach) Then
				itid = ReadShort(Server\ConnectServer)
				For it.ITems = Each Items
					If it\ID = itid Then
						If it\picker = 0 Then
							If EntityDistance(ReceivePlayer\Pivot, it\collider) < 1.5 Then
								If GetScripts() Then
									pbid = public_inqueue(public_OnPlayerTakeItem)
									public_addparam(pbid, ID)
									public_addparam(pbid, itid)
									public_addparam(pbid, it\itemtemplate\templateID)
									callback
								EndIf
								
								If Not SE_RETURN_VALUE\StaticIndex Then
									If ReceivePlayer\HandCuffed Then
										SendPlayerMsg(ID, "You cannot pick up any items because you handcuffed", 70*5)
									Else
										If Server\Breach Then
											For it2.Items = Each items
												If it2\picker = ID Then
													If it2\itemtemplate\templateid = it\itemtemplate\templateid Then Return
												EndIf
											Next
										EndIf
										
										it\picked = True
										it\picker = ID
									EndIf
								EndIf
							EndIf
						EndIf
						Exit
					EndIf
				Next
				Return
			EndIf
			
			ReadIgnoreBytes(2)
		Case M_DROPITEM
			If (Not mp_IsASCP(ReceivePlayer\BreachType)) Or (Not Server\Breach) Then
				itID = ReadShort(Server\ConnectServer)
				For it.ITems = Each Items
					If it\ID = itid Then
						If it\picker = ID Then
							If GetScripts() Then
								pbid = public_inqueue(public_OnPlayerDropItem)
								public_addparam(pbid, ID)
								public_addparam(pbid, itid)
								public_addparam(pbid, it\itemtemplate\templateID)
								callback
							EndIf
							
							If Not SE_RETURN_VALUE\StaticIndex Then PlayerDropItem(it)
						EndIf
						Exit
					EndIf
				Next
				Return
			EndIf
			
			ReadIgnoreBytes(2)
		Case M_GLOBALCONSOLE
			txt$ = ReadLine(Server\ConnectServer)
			If GetScripts() Then
				public_inqueue(public_OnPlayerConsole)
				public_addparam(0, ID)
				public_addparam(0, txt, SE_STRING)
				callback
			EndIf
			
			If Not SE_RETURN_VALUE\StaticIndex Then OnPlayerConsole(ID, txt)
		Case M_RESPAWN
			If Not Server\Breach Then
				If ReceivePlayer\IsDead Then
					PositionEntity ReceivePlayer\Pivot, ReceivePlayer\SavePosX, ReceivePlayer\SavePosY, ReceivePlayer\SavePosZ
					ResetEntity ReceivePlayer\Pivot
					
					For r.Rooms = Each Rooms
						If r\ID = ReceivePlayer\SaveRoom Then
							mp_SetPlayerRoomID(ReceivePlayer, r)
							Exit
						EndIf
					Next
					mp_UpdatePlayerPosition(ReceivePlayer, True)
					
					SetPlayerType(ReceivePlayer\ID, CLASSD_MODEL)
					ReceivePlayer\Health = 100
					ReceivePlayer\Injuries = 0
					
					;addlog("Respawn!")
				EndIf
			EndIf
		Case M_WANTINTERCOM
			If (Not Server\DisableIntercom) And ReceivePlayer\BreachType > 0 Then
				If ReceivePlayer\PlayerRoomName = "room2ccont" And ((Not mp_IsASCP(ReceivePlayer\BreachType)) Or multiplayer_breach_IsA049(ReceivePlayer\BreachType)) Then
					r.Rooms = Room[ReceivePlayer\PlayerRoomID]
					If Distance3(r\x-265.0*RoomScale, r\y+1280.0*RoomScale, r\z+105.0*RoomScale, EntityX(ReceivePlayer\Pivot), EntityY(ReceivePlayer\Pivot), EntityZ(ReceivePlayer\Pivot)) < 3 Then
						If ReceivePlayer\CheckIntercom < MilliSecs() Or ReceivePlayer\TimeIntercom > MilliSecs() Then
							ReceivePlayer\Announcement = Not ReceivePlayer\Announcement
							If ReceivePlayer\Announcement = False Then 
								ReceivePlayer\CheckIntercom = MilliSecs()+Server\IntercomTimeout
								ReceivePlayer\TimeIntercom = 0
							Else
								ReceivePlayer\TimeIntercom = MilliSecs()+Server\IntercomUsingTime
								ReceivePlayer\CheckIntercom = MilliSecs()+Server\IntercomTimeout+Server\IntercomUsingTime
							EndIf
						Else
							ReceivePlayer\TimeIntercom = 0
							ReceivePlayer\CheckIntercom = MilliSecs()+Server\IntercomTimeout
							ReceivePlayer\Announcement = False
						EndIf
					Else
						ReceivePlayer\TimeIntercom = 0
						ReceivePlayer\CheckIntercom = MilliSecs()+Server\IntercomTimeout
						ReceivePlayer\Announcement = False
					EndIf
				Else
					ReceivePlayer\TimeIntercom = 0
					ReceivePlayer\CheckIntercom = MilliSecs()+Server\IntercomTimeout
					ReceivePlayer\Announcement = False
				EndIf
			EndIf
		Case M_DOOR
			If (Not ReceivePlayer\isdead) And (Not ReceivePlayer\BreachType = 0) Then
				intf3 = ReadShort(Server\ConnectServer)
				opens = ReadByte(Server\ConnectServer)
				locke = ReadByte(Server\ConnectServer)
				selitem = ReadShort(Server\ConnectServer)
				code$ = ReadLine(Server\ConnectServer)
				For d.Doors = Each Doors
					If d\ID = intf3 Then
						Local CanOpenDoor% = False
						For i = 0 To 1
							If d\buttons[i] <> 0 Then
								If EntityDistance(d\buttons[i], ReceivePlayer\Pivot) < 4 Then
									CanOpenDoor = True
									Exit
								EndIf
							EndIf
						Next
						
						If Not CanOpenDoor Then
							If d\liftparent <> Null Then
								For i = 0 To 1
									If d\liftparent\buttons[i] <> 0 Then
										If EntityDistance(d\liftparent\buttons[i], ReceivePlayer\Pivot) < 4 Then
											CanOpenDoor = True
											Exit
										EndIf
									EndIf
								Next
							EndIf
						EndIf
						
						If CanOpenDoor Then
							If GetScripts() Then
								pbid = public_inqueue(public_OnPlayerClickButton)
								public_addparam(pbid, ID)
								public_addparam(pbid, intf3)
								public_addparam(pbid, opens)
								public_addparam(pbid, locke)
								public_addparam(pbid, selitem)
								public_addparam(pbid, code, SE_STRING)
								callback
							EndIf
							If Not SE_RETURN_VALUE\StaticIndex Then
								If d\Code = code Or d\Code = "" Then
									
									; ======================Elevator Fix
									If d\IsElevatorDoor <> 0 Then
										If d\open Then
											If d\openstate = 180 Then
												If Abs(EntityX(ReceivePlayer\Pivot)-EntityX(d\liftobj,True))<280.0*RoomScale+(0.015*0.05) Then
													If Abs(EntityZ(ReceivePlayer\Pivot)-EntityZ(d\liftobj,True))<280.0*RoomScale+(0.015*0.05) Then	
														If Abs(EntityY(ReceivePlayer\Pivot)-0.32-EntityY(d\liftobj,True))<280.0*RoomScale+(0.015*0.05) Then	
															d\open = False
															d\liftparent\open = False
															If d\liftid = 1 Then d\liftmaindoor\liftstate = -1
															If d\liftid = 2 Then d\liftmaindoor\liftstate = 1
														EndIf
													EndIf
												EndIf
											EndIf
										Else
											If d\openstate = 0 Then
												If d\liftmaindoor\liftstate = 0 Then
													If d\liftparent\openstate = 180 Then
														d\liftparent\open = False
														If d\liftparent\liftid = 1 Then d\liftmaindoor\liftstate = -1
														If d\liftparent\liftid = 2 Then d\liftmaindoor\liftstate = 1
													EndIf
												EndIf
											EndIf
										EndIf
									Else
									;=====================
										UseDoor(d, True, True, ID, selitem)
									EndIf
									For i = 1 To Server\PlayersCount
										If EntityDistance(d\obj, PlayerOptimize[i]\Pivot) < 20 Or d\IsElevatorDoor <> 0 Then
											udp_WriteByte M_DOOR
											udp_WriteByte ID
											udp_WriteShort d\ID
											udp_WriteByte(d\open)
											udp_WriteByte(d\locked)
											udp_SendMessage(PlayerOptimize[i]\ID)
										EndIf
									Next
								EndIf
								
								If d\code <> "" And d\changedcode Then
									If code = d\code Then
										PlaySoundForPlayer(ReceivePlayer\ID, "sfx\interact\scanneruse1.ogg")
									Else
										PlaySoundForPlayer(ReceivePlayer\ID, "sfx\interact\scanneruse2.ogg")
									EndIf
								EndIf
							EndIf
						Else
							OnCheatDetected(ID, CHEAT_INCORRECT_POS)
						EndIf
						Exit
					EndIf
				Next
				Return
			EndIf
			
			ReadIgnoreBytes(6)
			ReadIgnoreLine()
		Case M_SOUND
			If ReceivePlayer\BreachType <> 0 And (Not ReceivePlayer\isdead) And ReceivePlayer\SoundUpdate < MilliSecs() Then
				strfile$ = ReadLine(Server\ConnectServer)
				rX# = ReadFloat(Server\ConnectServer)
				rY# = ReadFloat(Server\ConnectServer)

				If GetScripts() Then
					pbid = public_inqueue(public_OnPlayerReleaseSound)
					public_addparam(pbid, ID)
					public_addparam(pbid, strfile, SE_STRING)
					public_addparam(pbid, rY, SE_FLOAT)
					public_addparam(pbid, rX, SE_FLOAT)
					callback
				EndIf

				If Not SE_RETURN_VALUE\StaticIndex Then
					ReceivePlayer\SoundUpdate = MilliSecs()+Server\tickratedelay
					
					If strfile = "SFX\SCP\513\Bell1.ogg" Then 
						If Curr5131 = Null
							Curr5131 = CreateNPC(NPCtype5131, 0,0,0)
							Curr5131\IsSpawned = True
						EndIf
					EndIf
					
					ReceivePlayer\LatestPlayerSound = strfile
					
					For i = 1 To Server\PlayersCount
						If EntityDistance(PlayerOptimize[i]\Pivot, ReceivePlayer\Pivot) < 20 Then
							udp_WriteByte byte
							udp_WriteByte ID
							udp_WriteLine(strfile)
							udp_WriteFloat rX
							udp_WriteFloat rY
							udp_WriteByte(1)
							udp_SendMessage(PlayerOptimize[i]\ID)
						EndIf
					Next
				EndIf
				Return
			EndIf
			
			ReadIgnoreLine()
			ReadIgnoreBytes(8)
		Case M_DECAL
			If ReceivePlayer\BreachType <> 0 And (Not ReceivePlayer\IsDead) And ReceivePlayer\DecalUpdate < MilliSecs() Then
				If GetScripts() Then
					pbid = public_inqueue(public_OnPlayerCreateDecal)
					public_addparam(pbid, ID)
					callback
				EndIf
				If Not SE_RETURN_VALUE\StaticIndex Then
					Local floats#[12]
					intf = ReadByte(Server\ConnectServer)
					For a = 0 To 11
						floats[a] = ReadFloat(Server\ConnectServer)
					Next
					For i = 1 To Server\Maxplayers
						If Player[i] <> Null And i <> ID Then
							udp_WriteByte byte
							udp_WriteByte ID
							udp_WriteByte intf
							For a = 0 To 11
								udp_WriteFloat floats[a]
							Next
							udp_SendMessage(i)
						EndIf
					Next
				EndIf
				
				ReceivePlayer\DecalUpdate = MilliSecs()+500
				Return
			EndIf
			
			ReadIgnoreBytes(49)
		Case M_KILL2
			If ReceivePlayer\CanShoot < MilliSecs() Then
				SendID = ReadByte(Server\ConnectServer)
				If IsValidPlayer(SendID) Then
					If EntityDistance(ReceivePlayer\Pivot, Player[SendID]\Pivot) < 2 And (Not Player[SendID]\isdead) Then
						BT.BreachTypes = GetBreachType(ReceivePlayer\BreachType)
						If BT\AllowAttack = 2 Then
							If GetScripts() Then
								pbid = public_inqueue(public_OnPlayerKillPlayer)
								public_addparam(pbid, ID)
								public_addparam(pbid, SendID)
								public_addparam(pbid, Player[ID]\UsedWeapon)
								callback
							EndIf
									
							If Not SE_RETURN_VALUE\StaticIndex Then 
								If ReceivePlayer\BreachType = MODEL_049 Then
									SetPlayerType(SendID, MODEL_ZOMBIE)
									For it.Items = Each Items
										If it\picker = SendID Then 
											PlayerDropItem(it)
										EndIf
									Next
								Else
									If Not Server\FullSync Then
										udp_WriteByte byte
										udp_WriteByte ID
										udp_SendMessage(SendID)
									Else
										If Not (ReceivePlayer\BreachType = MODEL_106) Then
											GivePlayerHealth(SendID, -1000, "was killed by "+ReceivePlayer\name)
										Else
											GivePlayerHealth(SendID, -55, "was killed by "+ReceivePlayer\name)
											MoveToPocketDimension(SendID)
										EndIf
									EndIf
								EndIf
								
								ReceivePlayer\CanShoot = MilliSecs()+BT\HitInterval
							EndIf
						EndIf
					EndIf
				EndIf
				Return
			EndIf
			
			ReadIgnoreBytes(1)
		Case M_SHOOT
			If ReceivePlayer\CanShoot < MilliSecs() Then
				SendID = ReadByte(Server\ConnectServer)
				If IsValidPlayer(SendID) Then
					If EntityDistance(ReceivePlayer\Pivot, Player[SendID]\Pivot) < 2 And (Not Player[SendID]\isdead) Then
						BT.BreachTypes = GetBreachType(ReceivePlayer\BreachType)
						If BT\AllowAttack = 1 Then
							GiveHealth% = Rand(30, 40)
							
							If GetScripts() Then
								pbid = public_inqueue(public_OnPlayerHitPlayer)
								public_addparam(pbid, ID)
								public_addparam(pbid, SendID)
								public_addparam(pbid, GiveHealth, SE_FLOAT)
								public_addparam(pbid, Player[ID]\UsedWeapon)
								callback
							EndIf
							
							If Not SE_RETURN_VALUE\StaticIndex Then 
								GivePlayerHealth(SendID, -GiveHealth, "was killed by "+ReceivePlayer\name)
								
								If Player[SendID]\BreachType = 0 Then
									If GetScripts() Then
										pbid = public_inqueue(public_OnPlayerKillPlayer)
										public_addparam(pbid, ID)
										public_addparam(pbid, SendID)
										public_addparam(pbid, ReceivePlayer\UsedWeapon)
										callback
									EndIf
								EndIf
								
								If ReceivePlayer\BreachType = MODEL_035 Then GivePlayerHealth(ID, GiveHealth, " ")
							EndIf
							
							ReceivePlayer\CanShoot = MilliSecs()+BT\HitInterval
						Else
							OnCheatDetected(ID, CHEAT_INCORRECT_ROLE)
						EndIf
					EndIf
				EndIf
				Return
			EndIf
			
			ReadIgnoreBytes(1)
		Case M_CHAT
			If ReceivePlayer\ChatUpdate < MilliSecs() Then
				Local msg$ = Left(ReadLine(Server\ConnectServer), 80)
				Local add = ReadByte(Server\ConnectServer)
				If Instr(msg, "/rcon") Then
					msg = Right(msg, Len(msg)-2)
					Local cmd$ = RCON_findcmd(msg)
					If cmd = "Not found" Then Return AddTextToChat("[RCON] Command not found", ID)
					Local attribute$ = RCON_GetAttribute(msg)
					If (Not ReceivePlayer\Authorized) Then
						If cmd = "login" Then
							If Server\RconPassword = "" Then Return AddTextToChat("[RCON] RCON switched off", ID)
							If Server\RconPassword <> attribute Then
								AddTextToChat("[RCON] Wrong password", ID)
								If GetScripts() Then 
									public_addparam(public_inqueue(public_OnPlayerRconIncorrect), ID)
									callback
								EndIf
							Else
								If GetScripts() Then 
									public_addparam(public_inqueue(public_OnPlayerRconAuthorized), ID)
									callback
								EndIf
								
								If Not SE_RETURN_VALUE\StaticIndex Then 
									AddTextToChat("[RCON] You got the admin role.", ID)
									ReceivePlayer\Authorized = True
								EndIf
							EndIf
						Else
							AddTextToChat("[RCON] You are not an admin", ID)
						EndIf
						Return
					Else
						Select RCON_ExecuteCMD(cmd, attribute)
							Case "login"
								AddTextToChat("[RCON] You already have the admin role", ID)
							Case "status"
								For p.players = Each players
									AddTextToChat(p\name+" (Ping "+p\ping+")", ID)
								Next
							Case "commands"
								For rc.rcon = Each rcon
									AddTextToChat("[RCON] "+rc\command, ID)
								Next
							Case "gravity"
								AddLog("Gravity changed to "+attribute)
							Case "hostname"
								AddLog("Hostname changed to "+attribute)
							Case "hostname"
								AddLog("Hostname changed to "+attribute)
							Case "size"
								ChangePlayerSize(ID, Int(attribute))
								AddTextToChat("[RCON] Your size changed to "+Player[ID]\Size, ID)
							Case "getip"
								For p.players = Each players
									If Instr(Lower(p\name),Lower(attribute)) Then
										AddTextToChat("Player IP: "+p\PlayerIP, ID)
										Exit
									EndIf
								Next
							Case "getipid"
								For p.players = Each players
									If p\ID = Int(attribute) Then
										AddTextToChat("Player IP: "+p\PlayerIP, ID)
										Exit
									EndIf
								Next
							Case "getid"
								For p.players = Each players
									If Instr(Lower(p\name),Lower(attribute)) Then
										AddTextToChat("Player ID: "+p\ID, ID)
										Exit
									EndIf
								Next
						End Select
					EndIf
				Else
					If add = True Then
						If GetScripts() Then
						
							msg = GetFormattedText(msg)
							
							Local chatthread = public_inqueue(public_OnPlayerChat)
							public_addparam(chatthread, ID)
							public_addparam(chatthread, msg, SE_STRING)
							callback
							If Not SE_RETURN_VALUE\StaticIndex Then
								If Server\Breach And (Not Server\breachchat) Then Return
								If Server\FullSync And Instr(msg, "killed") And (Not Instr(msg, ":")) Then Return
								If Not ReceivePlayer\Muted Then AddLog(ReceivePlayer\name+msg)
							EndIf
						Else
							If Server\FullSync And Instr(msg, "killed") And (Not Instr(msg, ":")) Then Return
							If Server\Breach And (Not Server\breachchat) Then Return
							If Not ReceivePlayer\Muted Then
								msg = GetFormattedText(msg)
								AddLog(ReceivePlayer\name+msg)
							EndIf
						EndIf
					Else
						msg = GetFormattedText(msg)
						AddTextToChat(msg, ID)
					EndIf
				EndIf
				ReceivePlayer\ChatUpdate = MilliSecs()+250
				Return
			EndIf
			ReadIgnoreLine()
			ReadIgnoreBytes(1)
		Case M_REQUESTPLAYERNAME
			Local plid = ReadByte(Server\ConnectServer)
			If plid > 0 And plid < MAX_PLAYERS Then
				If Player[plid] <> Null Then
					udp_WriteByte(M_REQUESTPLAYERNAME)
					udp_WriteByte(plid)
					udp_WriteLine Player[plid]\name
					udp_WriteLine Player[plid]\Tag
					udp_WriteByte Player[plid]\TagR
					udp_WriteByte Player[plid]\TagG
					udp_WriteByte Player[plid]\TagB
					udp_WriteShort Player[plid]\Size*100
					udp_WriteInt Player[plid]\PlayerSteamID
					udp_WriteByte Player[plid]\TextureID
					udp_SendMessage(ID)
				EndIf
			EndIf
		Case M_ROTATEENTITY
			If ReceivePlayer\BreachType <> 0 And (Not ReceivePlayer\IsDead) Then
				objectid = ReadByte(Server\ConnectServer)
				floatpitch# = ReadFloat(Server\ConnectServer)
				floatyaw# = ReadFloat(Server\ConnectServer)
				floatroll# = ReadFloat(Server\ConnectServer)
				
				If ReceivePlayer\PlayerRoomID < MAX_ROOMS Then
					r.Rooms = Room[ReceivePlayer\PlayerRoomID]
					If r <> Null Then
						If objectid <= MaxRoomObjects Then
							If r\Objects[objectid] <> 0 Then
								If EntityDistance(ReceivePlayer\Pivot, r\Objects[objectid]) < 2 Then
									If GetScripts() Then
										pbid = public_inqueue(public_OnPlayerRotateLever)
										public_addparam(pbid, ID)
										public_addparam(pbid, objectid)
										public_addparam(pbid, floatpitch, SE_FLOAT)
										public_addparam(pbid, floatyaw, SE_FLOAT)
										public_addparam(pbid, floatroll, SE_FLOAT)
										callback
									EndIf
									
									If Not SE_RETURN_VALUE\StaticIndex Then
										RotateEntity(r\Objects[objectid], floatpitch, floatyaw, floatroll, True)
										r\ObjectStatic[objectid] = False
									EndIf
								Else
									OnCheatDetected(ID, CHEAT_INCORRECT_POS)
								EndIf
							EndIf
						EndIf
					EndIf
				EndIf
				Return
			EndIf
			
			ReadIgnoreBytes(13)
		Case M_WANTROLE
			Local brtype = ReadByte(Server\ConnectServer)
		
			If Not Server\FullSync Then
				If GetScripts() Then
					pbid = public_inqueue(public_OnPlayerRequestNewRole)
					public_addparam(pbid, ID)
					public_addparam(pbid, Max(Min(brtype, LAST_BREACH_TYPE-1), 0))
					callback
				EndIf
				
				If Not SE_RETURN_VALUE\StaticIndex Then SetPlayerType(ReceivePlayer\ID, Max(Min(brtype, LAST_BREACH_TYPE-1), 0))
				;if ReceivePlayer\BreachType = 0 And (Not Server\Breach) Then ReceivePlayer\BreachType = CLASSD_MODEL
			Else
				If brtype = 0 Then
				
					If GetScripts() Then
						pbid = public_inqueue(public_OnPlayerRequestNewRole)
						public_addparam(pbid, ID)
						public_addparam(pbid, Max(Min(brtype, LAST_BREACH_TYPE-1), 0))
						callback
					EndIf
					
					If Not SE_RETURN_VALUE\StaticIndex Then 
						If (Not Server\Breach) Then 
							GivePlayerHealth(ID, -1000, "")
						Else
							SetPlayerType(ReceivePlayer\ID, 0)
						EndIf
					EndIf
				EndIf
			EndIf
		Case M_HEALTHREDUCE
			If (Not ReceivePlayer\isdead) And ReceivePlayer\BreachType <> 0 Then
				Reason$ = ReadLine(Server\ConnectServer)
				HealthReduce = ReadShort(Server\ConnectServer)
				GivePlayerHealth(ID, -HealthReduce, Reason)
				Return
			EndIf
			
			ReadIgnoreLine()
			ReadIgnoreBytes(2)
		Case M_PUTHANDCUFFS
			SendID = ReadByte(Server\ConnectServer)
			If IsValidPlayer(SendID) Then
				If Player[SendID] <> Null Then
					If (Not ReceivePlayer\HandCuffed) And Player[SendID]\BreachType <> 0 And ReceivePlayer\BreachType <> 0 And EntityDistance(ReceivePlayer\Pivot, Player[SendID]\Pivot) < 1.5 And ReceivePlayer\UsedWeapon = GUN_HANDCUFFS Then
						If ReceivePlayer\HandCufftimeout < MilliSecs() Then
							If (Not mp_IsAFriend(ReceivePlayer\BreachType, Player[SendID]\BreachType)) Or Player[SendID]\HandCuffed <> 0 Then
								If GetScripts() Then
									public_inqueue(public_OnPlayerCuffPlayer)
									public_addparam(0, ID)
									public_addparam(0, SendID)
									callback
								EndIf
								
								If Not SE_RETURN_VALUE\StaticIndex Then
									BT.BreachTypes = GetBreachType(Player[SendID]\BreachType)
									If BT\Handcuffs Then
										For it.Items = Each Items
											If it\picker = ID Then
												If it\itemtemplate\tempname = "handcuffs" Then
													Player[SendID]\HandCuffed = Not Player[SendID]\HandCuffed
													If Player[SendID]\HandCuffed Then
														For it.items = Each items
															If it\picker = sendid Then
																PlayerDropItem(it)
															EndIf
														Next
													EndIf
													
													If Not Server\FullSync Then
														udp_WriteByte(M_PUTHANDCUFFS)
														udp_WriteByte(ID)
														udp_SendMessage(SendID)
													EndIf
													If Player[SendID]\HandCuffed Then
														SendPlayerMsg(SendID, "You are handcuffed.", 70*5)
														SendPlayerMsg(ID, "You handcuffed the player.", 70*5)
													Else
														SendPlayerMsg(SendID, "You are uncuffed.", 70*5)
														SendPlayerMsg(ID, "You uncuffed the player.", 70*5)
													EndIf
													Exit
												EndIf
											EndIf
										Next
										ReceivePlayer\HandCufftimeout = MilliSecs()+60000
									Else
										SendPlayerMsg(ID, "You can't cuff this player.", 70*5)
									EndIf
								EndIf
							EndIf
						EndIf
					Else
						SendPlayerMsg(ID, "Wait a bit for the next use of the handcuffs.", 70*5)
					EndIf
				EndIf
			EndIf
		Case M_SAVEPOS
			If Not Server\Breach Then
				If (Not ReceivePlayer\isdead) Then
					ReceivePlayer\SavePosX = EntityX(ReceivePlayer\Pivot)
					ReceivePlayer\SavePosY = EntityY(ReceivePlayer\Pivot)
					ReceivePlayer\SavePosZ = EntityZ(ReceivePlayer\Pivot)
					ReceivePlayer\SaveRoom = ReceivePlayer\PlayerRoomID
				EndIf
			EndIf
		Case M_WANTWARHEADS
			If (Not ReceivePlayer\isdead) And (Not ReceivePlayer\BreachType = 0) Then 
				If Room[ReceivePlayer\PlayerRoomID] <> Null Then
					If Room[ReceivePlayer\PlayerRoomID]\RoomTemplate\Name = "exit1" Then
						If EntityDistance(Room[ReceivePlayer\PlayerRoomID]\Objects[22], ReceivePlayer\Pivot) < 1 Then
							If breach_IsStarted() Then
								If (Not mp_IsASCP(ReceivePlayer\BreachType)) Or multiplayer_breach_IsA049(ReceivePlayer\BreachType) Then
									If gameInfo\b\ExplodeTimeout < MilliSecs() Then
										If gameInfo\b\Exploded = 0 Then
											temp = True
											For e2.Events = Each Events
												If e2\EventConst = e_room2nuke Then
													temp = e2\EventState
													Exit
												EndIf
											Next
											
											If temp = 1 Then ;remote detonation on -> explode
												If GetScripts() Then
													public_inqueue(public_OnPlayerActivateWarheads)
													public_addparam(0, ID)
													callback
												EndIf
											
												If Not SE_RETURN_VALUE\StaticIndex Then
													ActivateWarheads("Warheads", 0, ID)
													gameInfo\b\ExplodeTimeout = MilliSecs()+20000
													SendPlayerMsg(ID, "The warheads is activated!", 70*6)
												EndIf
											Else
												SendPlayerMsg(ID, "Remote warhead control is disabled.", 70*6)
											EndIf
										ElseIf gameInfo\b\Exploded <> 2 Then
											If GetScripts() Then
												public_inqueue(public_OnPlayerDeactivateWarheads)
												public_addparam(0, ID)
												callback
											EndIf
										
											If Not SE_RETURN_VALUE\StaticIndex Then
											
												gameInfo\b\ExplodeTimeout = MilliSecs()+120000
												DeactivateWarheads(ID)
												SendPlayerMsg(ID, "You turned off the warheads.", 70*6)
											EndIf
										EndIf
									Else
										SendPlayerMsg(ID, "You pushed the button but nothing happened.", 70*6)
									EndIf
								EndIf
							Else
								SendPlayerMsg(ID, "You pushed the button but nothing happened.", 70*6)
							EndIf
						EndIf
					Else
						OnCheatDetected(ID, CHEAT_INCORRECT_POS)
					EndIf
				EndIf
			EndIf
		Case M_MOUSE
			If GetScripts() Then 
				public_inqueue(public_OnPlayerMouseHit)
				public_addparam(0, ID)
				public_addparam(0, ReadShort(Server\ConnectServer))
				public_addparam(0, ReadShort(Server\ConnectServer))
				callback
			Else
				ReadIgnoreBytes(4)
			EndIf
			
			ReceivePlayer\MouseUpdate = MilliSecs()+500
		Case M_KNIFEDAMAGE
			If ReceivePlayer\CanShoot < MilliSecs() Then
				If (Not ReceivePlayer\isdead) And (Not ReceivePlayer\BreachType = 0) Then
					If Not ReceivePlayer\HandCuffed Then
						itemid = ReadShort(Server\ConnectServer)
						SendID = ReadByte(Server\ConnectServer)
						
						If itemid < MAX_ITEMS Then
							it.Items = M_Item[itemid]
							If it <> Null Then
								If it\picker = ID Then
									If IsAGun(it\itemtemplate\tempname) = Player[ID]\UsedWeapon Then
										If (Not mp_IsASCP(ReceivePlayer\BreachType)) Then
											If IsValidPlayer(SendID) Then
												If Player[SendID] <> Null Then
													
													If Player[SendID]\BreachType > 0 Then
														If EntityDistance(ReceivePlayer\Pivot, Player[SendID]\Pivot) < 1.5 Then
															If SendID <> ID And ((Not mp_IsAFriend(ReceivePlayer\BreachType, Player[SendID]\BreachType) Or (Not Server\Breach)) Or Server\FriendlyFire) Then
																GiveHealth% = GetGunDamage(GUN_KNIFE)+Rand(10)
						
																If GetScripts() Then
																	pbid = public_inqueue(public_OnPlayerShoot)
																	public_addparam(pbid, 0, SE_FLOAT)
																	public_addparam(pbid, 0, SE_FLOAT)
																	public_addparam(pbid, 0, SE_FLOAT)
																	public_addparam(pbid, 0, SE_FLOAT)
																	public_addparam(pbid, 0, SE_FLOAT)
																	callback
																EndIf
															
																If Not SE_RETURN_VALUE\StaticIndex Then
																	;Player[SendID]\Health
																	GivePlayerHealth(SendID, -GiveHealth, "was killed by "+ReceivePlayer\name)
																	ReceivePlayer\CanShoot = MilliSecs()+500
																	
																	If Player[SendID]\isdead Then
																		If GetScripts() Then
																			pbid = public_inqueue(public_OnPlayerKillPlayer)
																			public_addparam(pbid, ID)
																			public_addparam(pbid, SendID)
																			public_addparam(pbid, ReceivePlayer\UsedWeapon)
																			callback
																		EndIf
																		
																		category = breach_GetCategoryByType(ReceivePlayer\BreachType, True)
																			
																		If Player[SendID]\prevbreachtype = SCIENTIST_MODEL And category = CATEGORY_CHAOS Then breach_givetickets(1, 1)
																		If Player[SendID]\prevbreachtype = CLASSD_MODEL And category = CATEGORY_NTF Then breach_givetickets(0, 1)
																	Else
																		If GetScripts() Then
																			pbid = public_inqueue(public_OnPlayerHitPlayer)
																			public_addparam(pbid, ID)
																			public_addparam(pbid, Player[SendID]\ID)
																			public_addparam(pbid, GiveHealth, SE_FLOAT)
																			public_addparam(pbid, ReceivePlayer\UsedWeapon)
																			callback
																		EndIf
																		
																		If mp_IsASCP(Player[SendID]\BreachType) Then
																			category = breach_GetCategoryByType(ReceivePlayer\BreachType, True)
																			If category = CATEGORY_NTF Then breach_givetickets(0, 0.01)
																			If category = CATEGORY_CHAOS Then breach_givetickets(1, 0.01)
																		EndIf
																	EndIf
																EndIf
															EndIf
														EndIf
													EndIf
													
												EndIf
											EndIf
										EndIf
									Else
										OnCheatDetected(ID, CHEAT_NOITEM)
									EndIf
								EndIf
							Else
								OnCheatDetected(ID, CHEAT_NOITEM)
							EndIf
						EndIf
						Return
					Else
						OnCheatDetected(ID, CHEAT_NOITEM)
					EndIf
				EndIf
			EndIf
			
			ReadIgnoreBytes(3)
		Case M_CREATEROCKET
			If (Not ReceivePlayer\IsDead) And (Not ReceivePlayer\BreachType = 0) Then
				If Not ReceivePlayer\HandCuffed Then
					itemid = ReadShort(Server\ConnectServer)
					xRocket# = ReadFloat(Server\ConnectServer)
					yRocket# = ReadFloat(Server\ConnectServer)
					zRocket# = ReadFloat(Server\ConnectServer)
					pitchRocket# = ReadFloat(Server\ConnectServer)
					yawRocket# = ReadFloat(Server\ConnectServer)
					If itemid < MAX_ITEMS Then
						it.Items = M_Item[itemid]
						If it <> Null Then
							If it\picker = ID Then
								If Instr(it\itemtemplate\tempname, "rpg") Then
									If Distance3(xRocket, yRocket, zRocket, EntityX(ReceivePlayer\Pivot), EntityY(ReceivePlayer\Pivot), EntityZ(ReceivePlayer\Pivot)) < 4 Then
										If (Not mp_IsASCP(ReceivePlayer\BreachType)) Then
										
											If GetScripts() Then
												pbid = public_inqueue(public_OnPlayerShootRocket)
												public_addparam(pbid, ID)
												public_addparam(pbid, xRocket, SE_FLOAT)
												public_addparam(pbid, yRocket, SE_FLOAT)
												public_addparam(pbid, zRocket, SE_FLOAT)
												public_addparam(pbid, yawRocket, SE_FLOAT)
												public_addparam(pbid, pitchRocket, SE_FLOAT)
												callback
											EndIf
											
											If Not SE_RETURN_VALUE\StaticIndex Then
												CreateRocket(ROCKET_SPEED, xRocket, yRocket, zRocket, pitchRocket,yawRocket, ID)
												For i = 1 To Server\PlayersCount
													If Distance3(xRocket, yRocket, zRocket, EntityX(PlayerOptimize[i]\Pivot), EntityY(PlayerOptimize[i]\Pivot), EntityZ(PlayerOptimize[i]\Pivot)) < 50 Then
														udp_WriteByte M_CREATEROCKET
														udp_WriteByte ID
														udp_WriteShort 0
														udp_WriteFloat xrocket
														udp_WriteFloat yrocket
														udp_WriteFloat zrocket
														udp_WriteFloat pitchRocket
														udp_WriteFloat yawRocket
														udp_SendMessage(PlayerOptimize[i]\ID)
													EndIf
												Next
											EndIf
										EndIf
									EndIf
								Else
									OnCheatDetected(ID, CHEAT_NOITEM)
								EndIf
							Else
								OnCheatDetected(ID, CHEAT_NOITEM)
							EndIf
						EndIf
					EndIf
					Return
				Else
					OnCheatDetected(ID, CHEAT_NOITEM)
				EndIf
			EndIf
			
			ReadIgnoreBytes(22)
		Case M_CREATEGRENADE
			If ReceivePlayer\CanShoot < MilliSecs() Then
				If (Not ReceivePlayer\IsDead) And (Not ReceivePlayer\BreachType = 0) Then
					If Not ReceivePlayer\HandCuffed Then
						itemid = ReadShort(Server\ConnectServer)
						xRocket# = ReadFloat(Server\ConnectServer)
						yRocket# = ReadFloat(Server\ConnectServer)
						zRocket# = ReadFloat(Server\ConnectServer)
						pitchRocket# = ReadFloat(Server\ConnectServer)
						yawRocket# = ReadFloat(Server\ConnectServer)
						grenadetype% = ReadByte(Server\ConnectServer)
						grenadeslow% = Min(ReadByte(Server\ConnectServer), 1)
						If itemid < MAX_ITEMS Then
							it.Items = M_Item[itemid]
							If it <> Null Then
								If it\picker = ID Then
									
									Select grenadetype
										Case GUN_GRENADE:
											If it\itemtemplate\tempname <> "grenade" Then
												OnCheatDetected(ID, CHEAT_NOITEM)
												Return
											EndIf
										Case GUN_GRENADEFLASHBANG:
											If it\itemtemplate\tempname <> "grenadeflashbang" Then
												OnCheatDetected(ID, CHEAT_NOITEM)
												Return
											EndIf
										Case GUN_GRENADESMOKE:
											If it\itemtemplate\tempname <> "grenadesmoke" Then
												OnCheatDetected(ID, CHEAT_NOITEM)
												Return
											EndIf
										Default:
											OnCheatDetected(ID, CHEAT_NOITEM)
											Return
									End Select
									
									If Distance3(xRocket, yRocket, zRocket, EntityX(ReceivePlayer\Pivot), EntityY(ReceivePlayer\Pivot), EntityZ(ReceivePlayer\Pivot)) < 4 Then
										If (Not mp_IsASCP(ReceivePlayer\BreachType)) And ReceivePlayer\BreachType <> 0 Then
											If GetScripts() Then
												pbid = public_inqueue(public_OnPlayerDropGrenade)
												public_addparam(pbid, ID)
												public_addparam(pbid, xRocket, SE_FLOAT)
												public_addparam(pbid, yRocket, SE_FLOAT)
												public_addparam(pbid, zRocket, SE_FLOAT)
												public_addparam(pbid, yawRocket, SE_FLOAT)
												public_addparam(pbid, pitchRocket, SE_FLOAT)
												public_addparam(pbid, grenadetype)
												public_addparam(pbid, grenadeslow)
												callback
											EndIf
											
											If Not SE_RETURN_VALUE\StaticIndex Then
												If grenadetype = GUN_GRENADE Then CreateGrenade(xRocket, yRocket, zRocket, pitchRocket,yawRocket, ID, grenadeslow)
												For i = 1 To Server\PlayersCount
													udp_WriteByte M_CREATEGRENADE
													udp_WriteByte ID
													udp_WriteShort 0
													udp_WriteFloat xrocket
													udp_WriteFloat yrocket
													udp_WriteFloat zrocket
													udp_WriteFloat pitchRocket
													udp_WriteFloat yawRocket
													udp_WriteByte grenadetype
													udp_WriteByte grenadeslow
													udp_SendMessage(PlayerOptimize[i]\ID)
												Next
											EndIf
										EndIf
										RemoveItem(it, False)
									EndIf
								Else
									OnCheatDetected(ID, CHEAT_NOITEM)
								EndIf
							EndIf
						EndIf
						ReceivePlayer\CanShoot = MilliSecs()+500
						Return
					Else
						OnCheatDetected(ID, CHEAT_NOITEM)
					EndIf
				EndIf
			EndIf
			
			ReadIgnoreBytes(24)
		Case M_FIXITEMS
			itnameid = ReadInt(Server\ConnectServer)
			If Server\FullSync Then
				If ReceivePlayer\PlayerRoomName = "room1162" Then
					r.Rooms = Room[ReceivePlayer\PlayerRoomID]
					If EntityDistance(r\Objects[0], ReceivePlayer\Pivot) < 2 Then
						If (Not ReceivePlayer\isdead) Then
							Local shouldcreateitem% = False
							For itt.ItemTemplates = Each ItemTemplates
								If IsItemGoodFor1162(itt) Then
									shouldcreateitem% = False
									If ReceivePlayer\PreviousRemovedItemTempName <> "" Then
										Select ReceivePlayer\PreviousRemovedItemTempName
											Case "key"
												If itt\tempname = "key1" Or itt\tempname = "key2" And Rand(2)=1
													shouldcreateitem = True
												EndIf
											Case "paper","oldpaper"
												If itt\tempname = "paper" And Rand(12)=1 Then
													shouldcreateitem = True
												EndIf
											Case "gasmask","gasmask3","supergasmask","hazmatsuit","hazmatsuit2","hazmatsuit3"
												If itt\tempname = "gasmask" Or itt\tempname = "gasmask3" Or itt\tempname = "supergasmask" Or itt\tempname = "hazmatsuit" Or itt\tempname = "hazmatsuit2" Or itt\tempname = "hazmatsuit3" And Rand(2)=1
													shouldcreateitem = True
												EndIf
											Case "key1","key2","key3"
												If itt\tempname = "key1" Or itt\tempname = "key2" Or itt\tempname = "key3" Or itt\tempname = "misc" And Rand(6)=1
													shouldcreateitem = True
												EndIf
											Case "vest","finevest"
												If itt\tempname = "vest" Or itt\tempname = "finevest" And Rand(1)=1
													shouldcreateitem = True
												EndIf
											Default
												If itt\tempname = "misc" And Rand(6)=1
													shouldcreateitem = True
												EndIf
										End Select
										If shouldcreateitem Then
											it.Items = CreateItem(itt\name, itt\tempname, ReceivePlayer\x, ReceivePlayer\y+0.1, ReceivePlayer\z)
											If GetScripts() Then
												pbid = public_inqueue(public_OnPlayerUse1162)
												public_addparam(pbid, ID)
												public_addparam(pbid, it\ID)
												public_addparam(pbid, itnameid)
												callback
											EndIf
											ReceivePlayer\PreviousRemovedItemTempName = ""
											Exit
										EndIf
									Else
										If Not Player[ID]\isdead Then
											GivePlayerHealth(ID, -Rand(51, 55), "was killed by SCP-1162")
										EndIf
										Exit
									EndIf
								EndIf
							Next
						EndIf
					EndIf
				Else
					OnCheatDetected(ReceivePlayer\ID, CHEAT_SPAWN_ITEM)
				EndIf
			EndIf
	End Select
End Function

Function AcceptPacket(byte, IP, Port)
	ID = ReadByte(Server\ConnectServer)
	If (Not IsValidPlayer(ID)) Then Return
	
	If Player[ID] <> Null And ID <> 0 Then
		If Player[ID]\IP <> IP Or Player[ID]\Port <> Port Then Return
	Else
		If byte <> M_CONNECT And byte <> M_SERVERLIST And byte <> M_DONECONNECTION Then Return
	EndIf
	
	ReceivePlayer.Players = Player[ID]
	ReceivePlayer\Timeout = MilliSecs()+Server\timeout
	
	Select byte
		Case M_WANTROLE
			Local brtype = ReadByte(Server\ConnectServer)
		
			If Not Server\FullSync Then
				If GetScripts() Then
					pbid = public_inqueue(public_OnPlayerRequestNewRole)
					public_addparam(pbid, ID)
					public_addparam(pbid, Max(Min(brtype, LAST_BREACH_TYPE-1), 0))
					callback
				EndIf
				
				If Not SE_RETURN_VALUE\StaticIndex Then SetPlayerType(ReceivePlayer\ID, Max(Min(brtype, LAST_BREACH_TYPE-1), 0))
				;if ReceivePlayer\BreachType = 0 And (Not Server\Breach) Then ReceivePlayer\BreachType = CLASSD_MODEL
			Else
				If brtype = 0 Then
				
					If GetScripts() Then
						pbid = public_inqueue(public_OnPlayerRequestNewRole)
						public_addparam(pbid, ID)
						public_addparam(pbid, Max(Min(brtype, LAST_BREACH_TYPE-1), 0))
						callback
					EndIf
					
					If Not SE_RETURN_VALUE\StaticIndex Then 
						If (Not Server\Breach) Then 
							GivePlayerHealth(ID, -1000, "")
						Else
							SetPlayerType(ReceivePlayer\ID, 0)
						EndIf
					EndIf
				EndIf
			EndIf
	;Case M_LIFTSTATE
	;	did = udp_ReadByte()
	;	For d.Doors = Each doors
	;		if d\ID = did Then
	;			d\liftstate = udp_ReadFloat()
	;			Exit
	;		EndIf
	;	Next
	Case M_WANTFILES
		udp_WriteByte M_WANTFILES
		udp_WriteByte 1
		udp_SendMessage(ID)

		ReceivePlayer\IsLoad = True
		
		If GetScripts() Then
			public_inqueue(public_OnPlayerRequestFiles)
			public_addparam(0, ID)
			callback
		EndIf
	Case M_RAWPACKET
		If GetScripts() Then
			bnk = CreateBank(ReadAvail(Server\ConnectServer))
			ReadBytes bnk, Server\ConnectServer, 0, BankSize(bnk)
			public_inqueue(public_OnReceiveRawPacket)
			public_addparam(0, ID)
			public_addparam(0, bnk)
			callback
			FreeBank bnk
		EndIf
	Case M_REQUESTLOAD
		Local fni = ReadInt(Server\ConnectServer)
		Local offs = ReadInt(Server\ConnectServer)
		Local size = ReadShort(Server\ConnectServer)
		size = Min(Max(1, size), 4000)
		For q.querys = Each querys
			If q\playerid = ID Then
				If Handle(q) = fni Then
					q\downloadspeed = size
					q\offset = Min(Max(offs, 0), q\fullsize)
					
					If q\offset < q\fullsize Then
						ResizeBank QUERY_GLOBAL_DATA, q\downloadspeed
						SeekFile q\filehandle, q\offset
						ResizeBank QUERY_GLOBAL_DATA, Min(q\downloadspeed, q\fullsize-FilePos(q\filehandle))
						ReadBytes(QUERY_GLOBAL_DATA, q\filehandle, 0, BankSize(QUERY_GLOBAL_DATA))
						
						udp_WriteByte M_LOADDATA
						udp_WriteByte 1
						udp_WriteLine q\file
						udp_WriteInt Handle(q)
						udp_WriteInt q\offset
						udp_WriteInt q\fullsize
						udp_WriteBytes QUERY_GLOBAL_DATA, 0, BankSize(QUERY_GLOBAL_DATA)
						udp_WriteByte q\isascript
						udp_WriteInt q\compressedfullsize
						udp_SendMessage(q\playerid)
						
						q\updatetime = MilliSecs()+20
					Else
						q\offset = q\fullsize+1
					EndIf
					
					Exit
				EndIf
			EndIf
		Next
	Case M_PING
		If ReceivePlayer\lastping <> 0 Then
			ReceivePlayer\ping = Max(5, MilliSecs()-ReceivePlayer\lastping)
			ReceivePlayer\lastping = 0
		EndIf
		
		ReceivePlayer\IsLoad = True
	Case M_EVENT173
		If Not Server\Breach Then
			evFloat# = ReadFloat(Server\ConnectServer)
			For e.events = Each events
				If e\EventName = "173" Then
					e\EventState3 = evFloat
					For i = 1 To Server\Maxplayers
						If Player[i] <> Null And i <> ID Then
							udp_WriteByte byte
							udp_WriteByte ID
							udp_WriteFloat evFloat
							udp_SendMessage(i)
						EndIf
					Next
					Exit
				EndIf
			Next
		EndIf
	Case M_FEMUR
		If Not Server\Breach Then
			If GetScripts() Then
				pbid = public_inqueue(public_OnPlayerActivateFemurBreaker)
				public_addparam(pbid, ID)
				callback
			EndIf
			
			If Not SE_RETURN_VALUE\StaticIndex Then 
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
							
							For i = 1 To Server\Maxplayers
								If Player[i] <> Null Then
									udp_WriteByte byte
									udp_WriteByte ID
									udp_SendMessage(i)
								EndIf
							Next
						EndIf
						Exit
					EndIf
				Next
			EndIf
		EndIf
	Case M_EXPL
		Local expl = ReadInt(Server\ConnectServer)
		If GetScripts() Then
			pbid = public_inqueue(public_OnPlayerRequestExplosion)
			public_addparam(pbid, ID)
			public_addparam(pbid, expl)
			callback
		EndIf
		If SE_GetReturnValue() = 0 Or SE_GetReturnValue() = -1 Then
			For i = 1 To Server\Maxplayers
				If Player[i] <> Null And i <> ID Then
					udp_WriteByte(byte)
					udp_WriteByte(ID)
					udp_WriteInt(expl)
					udp_SendMessage(i)
				EndIf
			Next
		EndIf
	Case M_DISCONNECT
		Kick(ID, ReceivePlayer\name+" has left the server")
	Case M_SERVERLIST
		If IsConnectionSpam(IP, 16, 1000, ReadAvail(Server\ConnectServer)>=4) Then Return
		
		If GetScripts() Then
			pbid = public_inqueue(public_OnConnectionResponse)
			public_addparam(pbid, DottedIP(IP), SE_STRING)
			public_addparam(pbid, Port)
			callback
		EndIf
		
		If Not SE_RETURN_VALUE\StaticIndex Then
			
			If ReadAvail(Server\ConnectServer) < 1 Or Server\OldConnectionResponse Then
				udp_WriteLine(Server\Servername)
				udp_WriteLine(Server\PlayersCount+" / "+Server\Maxplayers)
				udp_WriteByte(Server\NoCheat)
				If Server\Breach Then
					udp_WriteByte(breach_IsStarted())
				Else
					udp_WriteByte(Server\IsStarted)
				EndIf
				If Server\Password <> "" Then udp_WriteLine("PS") Else udp_WriteLine("")
				udp_WriteByte(Server\Breach)
				udp_WriteByte(Server\ServerVoice)
				udp_WriteLine(Server\RandomSeed)
				udp_WriteLine(Server\Description)
				udp_WriteLine(Server\GameState)
				udp_WriteLine(MP_VERSION)
				udp_WriteLine(Server\weburl)
				If Server\OldConnectionResponse Then
					For i = 0 To 19
						If Server\DescLines[i] <> "" Then udp_WriteLine(Server\DescLines[i])
					Next
					udp_WriteLine("")
					udp_WriteByte(Server\CentralServer And Server\GetAuthkey And Server\NoclipAnticheat)
					
				EndIf
			Else
				Select ReadByte(Server\ConnectServer)
					Case 1
						udp_WriteInt(2319191)
						udp_WriteByte(1)
						udp_WriteLine(Server\Servername)
						udp_WriteLine(Server\PlayersCount+" / "+Server\Maxplayers)
						udp_WriteByte(Server\NoCheat)
						If Server\Breach Then
							udp_WriteByte(breach_IsStarted())
						Else
							udp_WriteByte(Server\IsStarted)
						EndIf
						udp_WriteByte((Server\Password <> ""))
						udp_WriteByte(Server\Breach)
						udp_WriteByte(Server\ServerVoice)
						udp_WriteLine(Server\RandomSeed)
						udp_WriteLine(Server\gamestate)
						udp_WriteByte(Server\CentralServer And Server\GetAuthkey And Server\NoclipAnticheat)
					Case 2
						udp_WriteInt(2319191)
						udp_WriteByte(2)
						udp_WriteLine(Server\Description)
						udp_WriteLine(MP_VERSION)
						udp_WriteLine(Server\weburl)
					Case 3
						udp_WriteInt(2319191)
						udp_WriteByte(3)
						For i = 0 To 19
							If Server\DescLines[i] <> "" Then udp_WriteLine(Server\DescLines[i])
						Next
						;runtimeerror("3")
				End Select
			EndIf


			SendUDPMsg(Server\ConnectServer,IP,Port)
		EndIf
	Case M_VOICESTOP
		If Server\ServerVoice Then
			If Not ReceivePlayer\Muted Then
				For i = 1 To Server\PlayersCount
					If PlayerOptimize[i]\IsLoad Or Server\IsStarted = False Then
						If Server\IsStarted = False Or EntityDistance(PlayerOptimize[i]\Pivot, ReceivePlayer\Pivot) <= 15 Or (PlayerOptimize[i]\CurrentRadio = ReceivePlayer\CurrentRadio And ReceivePlayer\CurrentRadio <> 0) Or ReceivePlayer\Announcement = True Or (ReceivePlayer\BreachType <> 0 Or PlayerOptimize[i]\BreachType = ReceivePlayer\BreachType) Then
							udp_WriteByte M_VOICESTOP
							udp_WriteByte ID
							udp_SendMessage(PlayerOptimize[i]\ID)
						EndIf
					EndIf
				Next
			EndIf
		EndIf
	Case M_VOICE
		If Server\ServerVoice Then
			If Not ReceivePlayer\Muted Then
				Local vSrc = CreateBank(ReadAvail(Server\ConnectServer))
				ReadBytes(vSrc, Server\ConnectServer, 0, ReadAvail(Server\ConnectServer))

				If GetScripts() Then
					pbid = public_inqueue(public_OnPlayerSpeaking)
					public_addparam(pbid, ID)
					public_addparam(pbid, vSrc)
					public_addparam(pbid, ReceivePlayer\CurrentRadio)
					public_addparam(pbid, ReceivePlayer\Announcement)
					callback
					If SE_RETURN_VALUE\StaticIndex Then
						FreeBank vSrc
						Return
					EndIf
				EndIf
				
				For i = 1 To Server\PlayersCount
					If PlayerOptimize[i]\IsLoad Or Server\IsStarted = False Then
						If Server\IsStarted = False Or EntityDistance(PlayerOptimize[i]\Pivot, ReceivePlayer\Pivot) <= 15 Or (PlayerOptimize[i]\CurrentRadio = ReceivePlayer\CurrentRadio And ReceivePlayer\CurrentRadio <> 0) Or ReceivePlayer\Announcement = True Or (ReceivePlayer\BreachType <> 0 Or PlayerOptimize[i]\BreachType = ReceivePlayer\BreachType) Then
							udp_WriteByte M_VOICE
							udp_WriteByte ID
							udp_WriteBytes(vSrc, 0, BankSize(vSrc))
							udp_WriteByte(ReceivePlayer\CurrentRadio)
							udp_SendMessage(PlayerOptimize[i]\ID)
						EndIf
					EndIf
				Next
				FreeBank vSrc
			EndIf
		EndIf
	Case M_CONNECT
		;consolelog BS_ISteamGameServer_IsLoggedOn(BS_SteamGameServer()), 7
		;if BS_ISteamGameServer_IsLoggedOn(BS_SteamGameServer()) = 0 Then Return
		
		If IsConnectionSpam(IP, 16, 1000, False) Then Return
		
		name$ = ReadLineSafe(Server\ConnectServer)
		Version$ = ReadLine(Server\ConnectServer)
		plpassword$ = ReadLine(Server\ConnectServer)
		clversion = ReadByte(Server\ConnectServer)
		mwidth = ReadShort(Server\ConnectServer)
		mheight = ReadShort(Server\ConnectServer)
		patron = ReadByte(Server\ConnectServer)
		steamid = ReadInt(Server\ConnectServer)
		;Local key = ReadInt(Server\ConnectServer)
		
		If steamid = 0 Or mheight = 0 Or mwidth = 0 Or clversion = 0 Or Version$ = "" Or Len(name) > 24 Or Len(Version) > 8 Then Return
		;mwidth = 6677557
		;mheight = 56565876
		
		patron = False
		; =================================================================== Check connect key
		;if Server\GetAuthKey Then
		;	Local CryptLevel# = Min(Max(ReadByte(Server\ConnectServer), 1), 255)
		;	Local DecryptedKey# = steamid
		;	
		;	DecryptedKey = DecryptedKey/8
		;	DecryptedKey = DecryptedKey/4
		;	DecryptedKey = (DecryptedKey/2)*4^2
		;	DecryptedKey = DecryptedKey/CryptLevel
		;	DecryptedKey = DecryptedKey*(mwidth/(512-Max(Len(name), 1)))
		;	
		;	if ceil(DecryptedKey*1.2*0.5*(0.2*Max(Len(name), 1))) <> key or key = 0 or steamid = 0 Then
		;	
		;		if GetScripts() Then
		;			public_inqueue(public_OnBadIncomingConnection)
		;			public_addparam(0, name, SE_STRING)
		;			public_addparam(0, PlayerIP, SE_STRING)
		;			public_addparam(0, steamid)
		;			public_addparam(0, version, SE_STRING)
		;			public_addparam(0, patron)
		;			public_addparam(0, false)
		;			callback
		;		EndIf
		;		
		;		if Not SE_RETURN_VALUE\StaticIndex Then AddLog(name+" bad incoming connection: "+DottedIP$(IP), 0, False)
		;		
		;		Return
		;	endif
		;Else
		;	ReadByte(Server\ConnectServer)
		;EndIf
		; ======================================================================
		
		Local AuthConnectionsCount% = 0
		For AC.AuthConnection = Each AuthConnection
			AuthConnectionsCount = AuthConnectionsCount + 1
			If AC\SteamID = steamid Then 
				Return
			ElseIf AC\IP = IP Then 
				Return
			EndIf
		Next
		
		If AuthConnectionsCount >= 200 Then
			For AC.AuthConnection = Each AuthConnection
				RemoveAuthConnection(AC)
			Next
		EndIf
		
		For i = 1 To Server\PlayersCount
			If PlayerOptimize[i]\IP = IP Then 
				If PlayerOptimize[i]\Port = Port Then Return
			EndIf
		Next
		
		AddLog(name+" incoming connection: "+DottedIP$(IP)+" [U:1:"+steamid+"]", 0, False)
		
		incomingversion = Version
		incomingpatron = patron
		
		For SI.SteamInstances = Each SteamInstances
			If SI\steamid = steamid Then 
				incomingpatron = (SI\tag="PATRON")
				Exit
			EndIf
		Next
		
		Local PlayerIP$ = DottedIP(IP)
		Local errorMessage$ = ""
		If GetScripts() Then
			public_inqueue(public_OnIncomingConnection)
			public_addparam(0, name, SE_STRING)
			public_addparam(0, PlayerIP, SE_STRING)
			public_addparam(0, steamid)
			public_addparam(0, Version, SE_STRING)
			public_addparam(0, patron)
			callback
		EndIf
		
		If Instr(name, "%") Then
			errorMessage = "Invalid syntax , please change your nickname!"
		End If
		
		If plpassword <> Server\Password Then errorMessage = "Wrong password"
		If name = "" Then 
			errorMessage = "Invalid input, please set your nickname!"
		Else
			For p.players = Each players
				If Lower(p\name) = Lower(name) Then
					errorMessage = "Change your nickname"+Chr(13)+Chr(10)+"A player with this name"+Chr(13)+Chr(10)+"is already on the server"
					Exit
				EndIf
			Next
		EndIf

		For ban.Banned = Each banned
			If ban\IP = PlayerIP Or ban\ID = steamid Then 
				errorMessage = "You've got banned from the server."
				Exit
			EndIf
		Next
		
		If (Not IsAccessVersion(Version)) Or clversion <> CLIENT_VERSION Then errorMessage = "Version doesn't match"+Chr(13)+Chr(10)+"Server version: "+MP_VERSION
		If Server\PlayersCount = Server\Maxplayers Then errorMessage = "Server is full"
		
		If GetScripts() Then 
			If SE_GetReturnValue() <> -1 Then errorMessage = SE_GetReturnValue()
		EndIf
		
		If errorMessage <> "" Then
			udp_WriteByte M_CONNECTERROR
			udp_WriteLine(errorMessage)
			SendUDPMsg(Server\ConnectServer, IP, Port)
			AddLog(name+" could not connect due to: "+errorMessage, 0, False)
			Return False
		EndIf
		
		If Server\Breach = True Then Server\IsStarted = True
		
		AC.AuthConnection = New AuthConnection
		AC\Timeout = MilliSecs()+5000
		AC\IP = IP
		AC\Port = Port
		AC\Name = name
		AC\mwidth = mwidth
		AC\mheight = mheight
		AC\patron = patron
		AC\steamid = steamid
		AC\version = Version
		
		If Server\CentralServer Then;CentralServer\Stream <> 0 Then
			If Server\GetAuthkey Then
				AC\AuthTicket = CreateBank(ReadAvail(Server\ConnectServer))
				ReadBytes AC\AuthTicket, Server\ConnectServer, 0, BankSize(AC\AuthTicket)

				Local RandomKey% = 0
				For CentralServers.CentralServerSegments = Each CentralServerSegments
					RandomKey = 1
					Exit
				Next
				
				If RandomKey = 0 Then
					AddLog("No central servers for player authorization were found. Receiving data...", 0, False)
					RequestDataFromGlobal()
				EndIf
				
				For CentralServers.CentralServerSegments = Each CentralServerSegments
					RandomKey% = Rand(1,2147383000)
					WriteByte CentralServer\UDPStream, CENTRALSERVER_REQUESTPLAYERAUTH+50
					WriteInt CentralServer\UDPStream,RandomKey
					WriteInt CentralServer\UDPStream,Int(Int(RandomKey/2)*4*2-20)
					WriteInt CentralServer\UDPStream, Handle(AC)
					WriteInt CentralServer\UDPStream, AC\SteamID
					WriteBytes AC\AuthTicket, CentralServer\UDPStream, 0, BankSize(AC\AuthTicket)
					SendUDPMsg(CentralServer\UDPStream, CentralServers\DecimalIP, CentralServers\Port)
				Next

				FreeBank AC\AuthTicket
				AC\AuthTicket = 0
			Else
				AC\GetRequest = True
				SendServerDataToPlayer(IP, Port)
			EndIf
		Else
			If Not STEAM_RELEASE Then Server\GetAuthkey = False
			
			If Server\GetAuthkey Then
				AC\AuthTicket = CreateBank(ReadAvail(Server\ConnectServer))
				ReadBytes AC\AuthTicket, Server\ConnectServer, 0, BankSize(AC\AuthTicket)
			EndIf
			
			AC\GetRequest = True
			
			SendServerDataToPlayer(IP, Port)
		EndIf
		
	Case M_DONECONNECTION
		For AC.AuthConnection = Each AuthConnection
			If AC\IP = IP Then
				If AC\Port = Port Then
					If AC\GetRequest = 1 Then
						Local TicketResult = AC\TicketResult
						
						If TicketResult = 0 And (Not (Server\Maxplayers = Server\PlayersCount)) Then
							For AD.AntiDDoS = Each AntiDDOS
								If AD\IP = IP Then Delete AD
							Next

							ID = FindFreePlayerID()
							CreatePlayer(ID)
							ReceivePlayer = Player[ID]
							ReceivePlayer\monitor_width = AC\mwidth
							ReceivePlayer\monitor_height = AC\mheight
							ReceivePlayer\patron = AC\patron
							ReceivePlayer\version = AC\version
							
							;if ReceivePlayer\patron Then
							;	ReceivePlayer\Tag = "PATRON"
							;	ReceivePlayer\TagR = 212
							;	ReceivePlayer\TagG = 160
							;	ReceivePlayer\TagB = 49
							;EndIf
							
							SetPlayerType(ReceivePlayer\ID, MODEL_WAIT)
							ReceivePlayer\Timeout = MilliSecs()+Server\timeout
							ReceivePlayer\IP = IP
							ReceivePlayer\ping = 5
							ReceivePlayer\PlayerIP = DottedIP(IP)
							ReceivePlayer\PlayerSteamID = AC\steamid
							ReceivePlayer\name = AC\name
							ReceivePlayer\Port = Port
							ReceivePlayer\TextureID = Rand(1,3)
							ReceivePlayer\KickTimer = MilliSecs()+60000
							
							For SI.SteamInstances = Each SteamInstances
								If SI\SteamID = ReceivePlayer\PlayerSteamID Then
									ReceivePlayer\Tag = SI\Tag
									ReceivePlayer\TagR = SI\r
									ReceivePlayer\TagG = SI\g
									ReceivePlayer\TagB = SI\b
									Exit
								EndIf
							Next
							
							Local difffound = 0
							For i = 0 To 2
								If Lower(Server\Difficulty) = GetNameDifficulty(i) Then
									difffound = i
									Exit
								EndIf
							Next
							If Server\Breach = True Then
								Server\IsStarted = True
								If gameInfo\b\BreachTimer = 0 Then
									If Server\onlydm Then Server\deathmatchround = True
									gameInfo\b\BreachTimer = MilliSecs()+Server\BreachTime+90000
									gameInfo\b\TimeoutBreach = MilliSecs()+90000
									gameInfo\b\LobbyTimer = MilliSecs()+90000
								EndIf
								If gameInfo\b\TimeoutBreach < MilliSecs() Then
									SetPlayerType(ReceivePlayer\ID, 0)
								Else
									If Server\resettimerafterconnect Then
										gameInfo\b\BreachTimer = MilliSecs()+Server\BreachTime+90000
										gameInfo\b\TimeoutBreach = MilliSecs()+90000
										gameInfo\b\LobbyTimer = MilliSecs()+90000
									EndIf
								EndIf
							Else
								SetPlayerType(ReceivePlayer\ID, CLASSD_MODEL)
								ReceivePlayer\PrevBreachType = CLASSD_MODEL
							EndIf
							GivePlayerHealth(ReceivePlayer\ID, 100)
							
							If MainPlayer = 0 Then 
								MainPlayer = ID
							Else
								If Player[MainPlayer]\Fake Then MainPlayer = ID
							EndIf
							AddLog(ReceivePlayer\name+" has joined the server")
							ClearChatForPlayer(ID)
							mp_CreatePlayerObject(ID)
				
							
							If Server\IsStarted Then
								If (Not Server\IntroEnabled) And (Not Server\Breach) Then
									For r.rooms = Each rooms
										If r\roomtemplate\name = "start" Then
											ReceivePlayer\x = EntityX(r\obj)+3584*RoomScale
											ReceivePlayer\y = (704*RoomScale)
											ReceivePlayer\z = EntityZ(r\obj)+1024*RoomScale
											ReceivePlayer\yaw = 130.3
											ReceivePlayer\playerroomid = r\ID
											ReceivePlayer\player_move = 11
											
											mp_UpdatePlayerPosition(ReceivePlayer)
											mp_SetRoomNameToPlayer(ReceivePlayer)
											
											ReceivePlayer\SavePosX = EntityX(ReceivePlayer\Pivot)
											ReceivePlayer\SavePosY = EntityY(ReceivePlayer\Pivot)
											ReceivePlayer\SavePosZ = EntityZ(ReceivePlayer\Pivot)
											ReceivePlayer\SaveRoom = ReceivePlayer\PlayerRoomID
											Exit
										EndIf
									Next
								Else
									tp = False
									For e.events = Each events
										If e\eventconst = e_173 Then
											tp = True
											Exit
										EndIf
									Next
									If tp Or Server\Breach Then
										For r.rooms = Each rooms
											If r\roomtemplate\name = "173" Then
												If Server\Breach Then
													ReceivePlayer\x = EntityX(r\Objects[5], True)
													ReceivePlayer\y = 2.0
													ReceivePlayer\z = EntityZ(r\Objects[5], True)
												Else
													ReceivePlayer\x = EntityX(r\obj)
													ReceivePlayer\y = 1.0
													ReceivePlayer\z = EntityZ(r\obj)
												EndIf
												ReceivePlayer\yaw = 130.3
												ReceivePlayer\playerroomid = r\ID
												ReceivePlayer\player_move = 11
												
												mp_UpdatePlayerPosition(ReceivePlayer)
												mp_SetRoomNameToPlayer(ReceivePlayer)
												
												ReceivePlayer\SavePosX = EntityX(ReceivePlayer\Pivot)
												ReceivePlayer\SavePosY = EntityY(ReceivePlayer\Pivot)
												ReceivePlayer\SavePosZ = EntityZ(ReceivePlayer\Pivot)
												ReceivePlayer\SaveRoom = ReceivePlayer\PlayerRoomID
												Exit
											EndIf
										Next
									Else
										For r.rooms = Each rooms
											If r\roomtemplate\name = "start" Then
												ReceivePlayer\x = EntityX(r\obj)+3584*RoomScale
												ReceivePlayer\y = (704*RoomScale)
												ReceivePlayer\z = EntityZ(r\obj)+1024*RoomScale
												ReceivePlayer\yaw = 130.3
												ReceivePlayer\playerroomid = r\ID
												ReceivePlayer\player_move = 11
												
												mp_UpdatePlayerPosition(ReceivePlayer)
												mp_SetRoomNameToPlayer(ReceivePlayer)
												
												ReceivePlayer\SavePosX = EntityX(ReceivePlayer\Pivot)
												ReceivePlayer\SavePosY = EntityY(ReceivePlayer\Pivot)
												ReceivePlayer\SavePosZ = EntityZ(ReceivePlayer\Pivot)
												ReceivePlayer\SaveRoom = ReceivePlayer\PlayerRoomID
												Exit
											EndIf
										Next
									EndIf
								EndIf
							Else
								ReceivePlayer\y = -100000
								mp_UpdatePlayerPosition(ReceivePlayer)
							EndIf

							udp_WriteByte(M_DONECONNECTION)
							udp_WriteByte(ID)
							udp_WriteByte(ReceivePlayer\BreachType)
							udp_WriteByte(ReceivePlayer\TextureID)
							udp_WriteByte(difffound)
							udp_SendMessage(ID)
							
							If GetScripts() Then public_addparam(public_inqueue(public_OnPlayerConnect), ID) : callback
							
							If Server\CustomMap <> "" Then
								SendFile(ID, Server\CustomMap, "servermaps\"+StripPath(Server\CustomMap))
							EndIf
						Else
							;consolelog "TicketResult: "+TicketResult, 7
							If GetScripts() Then
								public_inqueue(public_OnBadIncomingConnection)
								public_addparam(0, AC\name, SE_STRING)
								public_addparam(0, DottedIP(IP), SE_STRING)
								public_addparam(0, AC\steamid)
								public_addparam(0, AC\version, SE_STRING)
								public_addparam(0, AC\patron)
								public_addparam(0, TicketResult)
								callback
							EndIf
							
							
							If Not SE_RETURN_VALUE\StaticIndex Then AddLog(AC\name+" bad authorization ticket ["+TicketResult+"]: "+DottedIP$(IP), 0, False)

							k_EAuthSessionResponse$ = "Unknown"
							
							Select TicketResult
								Case 1: k_EAuthSessionResponse = "UserNotConnectedToSteam"
								Case 2: k_EAuthSessionResponse = "NoLicenseOrExpired"
								Case 3: k_EAuthSessionResponse = "VACBanned"
								Case 4: k_EAuthSessionResponse = "LoggedInElseWhere"
								Case 5: k_EAuthSessionResponse = "VACCheckTimedOut"
								Case 6: k_EAuthSessionResponse = "AuthTicketCanceled"
								Case 7: k_EAuthSessionResponse = "AuthTicketInvalidAlreadyUsed"
								Case 8: k_EAuthSessionResponse = "AuthTicketInvalid"
								Case 9: k_EAuthSessionResponse = "PublisherIssuedBan"
							End Select
							
							udp_WriteByte M_CONNECTERROR
							udp_WriteLine("SteamError: "+k_EAuthSessionResponse)
							SendUDPMsg(Server\ConnectServer, IP, Port)
									
						EndIf

						AC\GetRequest = 2
						Return
					EndIf
				EndIf
			EndIf
		Next
	Case M_UPDATE
	
		;if ReceivePlayer\lastping < MilliSecs() and receiveplayer\factor > 0 Then
		;	ReceivePlayer\Ping = Max((ReceivePlayer\PrevFactorFloat/ReceivePlayer\Factor)-GetTickrateDelay(), 5)
		;	ReceivePlayer\Factor = 0
		;	ReceivePlayer\PrevFactorFloat = 0
		;	ReceivePlayer\lastping = MilliSecs()+1500
		;EndIf
		;
		;ReceivePlayer\PrevFactorFloat = ReceivePlayer\PrevFactorFloat+(MilliSecs()-ReceivePlayer\LastTick)
		;ReceivePlayer\LastTick = MilliSecs()
		;
		
		;ReceivePlayer\Factor = ReceivePlayer\Factor+1
		;if ReceivePlayer\lastping < MilliSecs() Then
		;	ReceivePlayer\Ping = 5*((GetTickrateDelay()/ReceivePlayer\Factor)*5)
		;	ReceivePlayer\Factor = 0
		;	ReceivePlayer\lastping = MilliSecs()+1000
		;EndIf
				
		Select ReadByte(Server\ConnectServer)
			Case 0
				ReceivePlayer\Ready = ReadByte(Server\ConnectServer)
				If ReadByte(Server\ConnectServer) <> 0 Then ReceivePlayer\IsLoad = True
				
				ReceivePlayer\textsTicks = ReadByte(Server\ConnectServer)
				ReceivePlayer\drawsTicks = ReadByte(Server\ConnectServer)
				ReceivePlayer\objectsTicks = ReadByte(Server\ConnectServer)
				ReceivePlayer\chatTicks = ReadByte(Server\ConnectServer)
			Case 1
				;if rand(0+(1*(PACKET_LOSS_PERCENT=0)), 100-PACKET_LOSS_PERCENT) <> 0 Then
				
				;if ReceivePlayer\MiddleTicks = 0 Then
				;	ReceivePlayer\Factor = 0
				;	For i = 0 To MAX_MIDDLE_TICK-1
				;		ReceivePlayer\Factor = ReceivePlayer\Factor+ReceivePlayer\FactorBetween[i]
				;	Next
				;	ReceivePlayer\Factor = ReceivePlayer\Factor/MAX_MIDDLE_TICK
				;	
				;	ReceivePlayer\Ping = ReceivePlayer\Factor
				;EndIf
			
				ReceivePlayer\yaw = convertshorttovalue(ReadShort(Server\ConnectServer))
				ReceivePlayer\BonePitch = convertshorttovalue(ReadShort(Server\ConnectServer))
				
				ReceivePlayer\DirYaw = ReceivePlayer\yaw
				ReceivePlayer\DirPitch = ReceivePlayer\BonePitch
				
				If Server\GlobalBlink < MilliSecs() Then
					ReadFloat(Server\ConnectServer)
				Else
					ReceivePlayer\BlinkTimer = ReadFloat(Server\ConnectServer)
				EndIf
				
				ReceivePlayer\WearingDir = ReadByte(Server\ConnectServer)
				ReceivePlayer\WearingHazmat = ReadBool(ReceivePlayer\WearingDir, 0)
				ReceivePlayer\WearingNightVision = ReadBool(ReceivePlayer\WearingDir, 1)
				ReceivePlayer\WearingGasMask = ReadBool(ReceivePlayer\WearingDir, 2)
				ReceivePlayer\WearingVest = ReadBool(ReceivePlayer\WearingDir, 3)
				ReceivePlayer\MicroHIDSHOOT = ReadBool(ReceivePlayer\WearingDir, 6)
				
				ReceivePlayer\PlayerSoundVolume = ReadByte(Server\ConnectServer)
				ReceivePlayer\p_byte = ReadByte(Server\ConnectServer)
				ReceivePlayer\UsedWeapon = ReadByte(Server\ConnectServer)*(Not mp_IsASCP(ReceivePlayer\BreachType))
				If ReadBool(ReceivePlayer\p_byte, 0) <> 0 Then ReceivePlayer\IsLoad = True
				ReceivePlayer\InGame = Not ReadBool(ReceivePlayer\p_byte, 5)
				
				ReceivePlayer\SelectedItem = ReadShort(Server\ConnectServer)
				If ReceivePlayer\SelectedItem < MAX_ITEMS Then
					If M_Item[ReceivePlayer\SelectedItem] <> Null Then
						If M_Item[ReceivePlayer\SelectedItem]\picker <> ReceivePlayer\ID Then ReceivePlayer\SelectedItem = 0
					EndIf
				EndIf

				ReceivePlayer\textsTicks = ReadByte(Server\ConnectServer)
				ReceivePlayer\drawsTicks = ReadByte(Server\ConnectServer)
				ReceivePlayer\objectsTicks = ReadByte(Server\ConnectServer)
				ReceivePlayer\chatTicks = ReadByte(Server\ConnectServer)
				
				ReceivePlayer\Stamina = ReadFloat(Server\ConnectServer)

				ReceivePlayer\PLAYER_MOVE = ReadByte(Server\ConnectServer)
				ReceivePlayer\CurrentPositionID% = ReadShort(Server\ConnectServer)
				
				ReceivePlayer\CrouchState = (ReceivePlayer\PLAYER_MOVE >= 5 And ReceivePlayer\PLAYER_MOVE <= 10)

				If ReceivePlayer\ShouldPlayerTeleport = ReceivePlayer\CurrentPositionID Then
					ReceivePlayer\x = ReadFloat(Server\ConnectServer)
					ReceivePlayer\y = ReadFloat(Server\ConnectServer)
					ReceivePlayer\z = ReadFloat(Server\ConnectServer)
					RoomID% = ReadByte(Server\ConnectServer)
				
					If RoomID < MAX_ROOMS Then
						If Room[RoomID] <> Null Then 
							ReceivePlayer\PlayerRoomID = RoomID
							mp_SetRoomNameToPlayer(ReceivePlayer)
						EndIf
					EndIf
				Else
					ReadIgnoreBytes(13)
				EndIf
				
				If ReadBool(ReceivePlayer\p_byte, 7) Then
					ReceivePlayer\ShouldSendShootTicks = (ReceivePlayer\ShouldSendShootTicks Mod MAX_SHOOT_TICKS)+1
					ReceivePlayer\ShouldSendShoot[ReceivePlayer\ShouldSendShootTicks] = CreateByteStream(23)
					ReadBytes(GetByteStreamData(ReceivePlayer\ShouldSendShoot[ReceivePlayer\ShouldSendShootTicks]), Server\ConnectServer, 0, 23)
				EndIf

				ReceivePlayer\CurrentRadio = ReadByte(Server\ConnectServer)
		End Select
		
		If ReadAvail(Server\ConnectServer) <= Server\max_receive_bytes Then
			While ReadAvail(Server\ConnectServer) > 0
				AcceptMicroBytePacket(ReadByte(Server\ConnectServer), IP, Port, ID, ReceivePlayer)
			Wend
		EndIf
				
		If GetScripts() Then
			public_addparam(public_inqueue(public_OnPlayerUpdate), ID)
			callback
		EndIf
	End Select
End Function
Function Limit#(a#, max#)
	If a > max Then Return max Else Return a
End Function
Function Limit2#(a#, min# = 0.0)
	If min > a Then Return min Else Return a
End Function
Function InitVariables()
	If Server\Servername = "" Then Server\Servername = "SCP Server v"+MP_VERSION
	If Server\RandomSeed = "" Or Server\RandomSeed = " " Then Server\RandomSeed = SetRandomSeed()
	Server\IntroEnabled = Int(Limit2(Limit(Server\IntroEnabled, 1)))
	Server\NoCheat = Int(Limit2(Limit(Server\NoCheat, 1)))
	Server\ServerVoice = Int(Limit2(Limit(Server\ServerVoice, 1)))
	Server\JumpMode = Int(Limit2(Limit(Server\JumpMode, 1)))
	Server\Breach = Int(Limit2(Limit(Server\Breach, 1)))
	Server\BreachLight = Int(Limit2(Limit(Server\BreachLight, 1)))
	Server\EventProb = Limit2(Limit(Server\EventProb, 1.0))
	Server\timeout = Limit2(Limit(Server\timeout, 300000))
	Server\BreachTime = Limit2(Limit(Server\BreachTime, 3599999), 400000)
	Server\Port = Limit2(Limit(Server\Port, 65535), 80)
	Server\voicekhz = 48000;Limit2(Limit(Server\voicekhz, 96000), 5000)
	If Server\MapSize = 0 Then Server\MapSize = 4
	Server\MapSize = Limit2(Limit(Server\MapSize, 4), 1)-1
	If Server\ReloadApplicationRounds = 0 Then Server\ReloadApplicationRounds = 4
	Local founddif = False
	For i = 0 To 2
		If Lower(Server\Difficulty) = Lower(GetNameDifficulty(i)) Then 
			founddif = True
			Exit
		EndIf
	Next
	If founddif = False Then RCON_Difficulty(0)
	If Server\EventProb = 0.0 Then Server\EventProb = Rnd(0.0, 1.0)
	If Server\Maxplayers < 1 Or Server\Maxplayers > MAX_PLAYERS-1 Then Server\Maxplayers = STANDART_MAX_PLAYERS
	If Server\timeout < 5000 Then Server\timeout = STANDART_TIME_OUT
	
	;if Server\MinPlayersToStart < 4 Then Server\MinPlayersToStart = Server\MaxPlayers/1.5
	Server\minplayerstostart = Server\Maxplayers
	Server\FullSync = True
	
	SeedRnd MilliSecs()

	
	FSOUND_Init(8000, 64, 0)
End Function
Function OnServerStart()
	Cls
	file = ReadFile("server.cfg")
	While Not Eof(file)
		cfg_findcmd(Trim$(ReadLine(file)))
	Wend
	CloseFile file
	
	Local offset
	file = ReadFile("ServerConfig\advanceddescription.txt")
	While Not Eof(file)
		If offset >= 20 Then Exit
		Server\DescLines[offset] = ReadLine(file)
		offset = offset+1
	Wend
	CloseFile file
	
	InitVariables()
	CreateConsole("SCP CB Dedicated Server | "+GetFormattedText(Server\Servername))
	
	Server\ConnectServer = CreateUDPStream(Int(Server\Port))
	If (Not Server\ConnectServer) Then
		ConsoleLog("Server failed to start. Trying to create a server ...", 7)
		While True
			Server\ConnectServer = CreateUDPStream(Int(Server\Port))
			If (Not Server\ConnectServer) Then 
				ConsoleLog("Trying to create a server... Please check if the server is already running or if the port is busy, or binded ip does not exist", 7)
				Delay 3000
			Else
				Exit
			EndIf
		Wend
	EndIf
	
	SetUDPStreamBufferSize(Server\ConnectServer, 8192*Server\recvbuffermult)
	;ConsoleLog "Version: v"+MP_VERSION,7
	;ConsoleLog "CPU Mode: "+Bool(CPU_COMPILE),7

	;lower("")

	AddLog("Server started on port "+UDPStreamPort(Server\ConnectServer))+" with tickrate "+Server\tickrate
	AddLog("Server version: v"+MP_VERSION)
	If Server\BindedIP <> "" Then AddLog("Server successfully binded on "+Server\BindedIP)
	AddLog("-----------------")
	AddLog("Max players: "+Server\Maxplayers)
	AddLog("Map seed: "+Server\RandomSeed)
	AddLog("Map size: "+(Server\MapSize+1)+" chunks")
	AddLog("Hostname: "+GetFormattedText(Server\Servername))
	If Server\Password <> "" Then AddLog("Password: "+Server\Password)
	If Server\Breach = False Then AddLog("Difficulty: "+Server\Difficulty)
	AddLog("Time out time (ms): "+Server\timeout)
	AddLog("Intro enabled: "+Bool(Server\IntroEnabled))
	AddLog("Voice chat: "+Bool(Server\ServerVoice))
	If Server\ServerVoice Then AddLog("Voice quality: "+Server\voicekhz+" hz")
	;if not(server\voicekhz = 8000 or server\voicekhz = 12000 or server\voicekhz = 16000 or server\voicekhz = 24000 or server\voicekhz = 48000) then
	;	addlog("Voice chat only supports 8000, 12000, 16000, 24000, 48000 formats. Server start canceled.",0, true, 1)
	;	while true
	;		delay 1000
	;	wend
	;endif
	AddLog("No cheat mode: "+Bool(Server\NoCheat))
	AddLog("Jump mode: "+Bool(Server\JumpMode))
	AddLog("Keep inventory: "+Bool(Server\keepitems))
	If Server\JumpMode = 1 Then AddLog("Gravity: "+Server\JumpHeight)
	AddLog("Rounds limit per start: "+Server\ReloadApplicationRounds)
	AddLog("RCON Password: "+Server\RconPassword)
	AddLog("Breach mode: "+Bool(Server\Breach))
	If Server\Breach = True Then
		AddLog("Breach time: "+ConvertTime(Server\BreachTime)+" min")
		AddLog("Breach chat: "+Bool(Server\breachchat))
		AddLog("Only deathmatch: "+Bool(Server\onlydm))
		;	AddLog("    Breach light: "+Bool(Server\BreachLight))
	EndIf
	AddLog("Using central server: "+Bool(Server\CentralServer))
	AddLog("Central server using TCP passthrough: "+Bool(Server\CentralServerTCPRequest))
	AddLog("Noclip anticheat: "+Bool(Server\NoclipAnticheat))
	If Server\NoclipAnticheat Then AddLog("Speedhack rate: "+Server\AC_TeleportRate)
	AddLog("Camera shake on damage: "+Bool(Server\CameraShakeOnDamage))
	If Server\CustomMap <> "" Then AddLog("Custom map: "+Server\CustomMap)
	For script.ScriptsThread = Each ScriptsThread
		If script\ScriptThread = Null And script\luathread = 0 Then
			If script\status = "" Then
				AddLog("Script "+script\scriptname+" load failed")
			Else
				If Instr(script\status, Chr(13)) Then script\status = Left(script\status, Instr(script\status, Chr(13))-1)
				AddLog("Script "+script\scriptname+" load failed ["+script\status+"]")
			EndIf
			Delete script
		Else
			AddLog("Script "+script\scriptname+" loaded successfully")
			
			If script\luathread = 0 Then
				SE_SCRIPT_ACCESS_INVOKE = (script\scriptthread\version = "NULL")
				callbacksingle(script, public__main)
				SE_SCRIPT_ACCESS_INVOKE = True
			EndIf
			
			
			callbacksingle(script, public_OnScriptLoaded)
		EndIf
	Next
	AddLog("-----------------")
	RCON_LoadBanList("banlist")
	RCON_LoadSteamBanlist("banliststeam")
	Delete Each ChatMessage
End Function
Function getdiff(name$)
	Select name
		Case "safe"
			Return 0
		Case "euclid"
			Return 1
		Case "keter"
			Return 2
	End Select
	Return 0
End Function
Function ConvertTime$(time)
	minutes$ = time / (1000*60) Mod 60
	Return minutes
End Function
Function cfg_findbool(stats$)
	If Lower(stats) = "true" Then 
		Return 1
	ElseIf Lower(stats) = "false" Then
		Return 0
	EndIf
	Return stats
End Function
Function cfg_findcmd(msg$)
	Local stats$
	stats = Trim$(Right(msg, Len(msg) - Instr(msg, " ")))
	msg = Trim$(Left(msg, Instr(msg, " ")))
	Select msg
	Case "maxiconnfromip"
		Server\MaxIConnectionsFromIP = Int(stats)
	Case "globalblinktimer"
		Server\CurrentGlobalBlink = Int(stats)
	Case "globalblinktimerinterval"
		Server\CurrentGlobalBlinkInterval = Int(stats)
	Case "max_receive_bytes"
		;server\max_receive_bytes = Int(stats)
	Case "recvbuffermult"
		Server\recvbuffermult = Max(Int(stats), 1)
	Case "bind"
		Server\BindedIP = stats
	Case "intercom_timeout"
		Server\IntercomTimeout = Int(stats)
	Case "intercom_timeout"
		Server\IntercomUsingTime = Int(stats)
	Case "intercom_enable"
		stats = cfg_findbool(stats)
		Server\DisableIntercom = 1-Int(stats)
	Case "speedhack_rate"
		Server\AC_TeleportRate = Float(stats)
	Case "noclipanticheat"
		stats = cfg_findbool(stats)
		Server\NoclipAnticheat = Int(stats)
	Case "camerashakeondamage"
		stats = cfg_findbool(stats)
		Server\CameraShakeOnDamage = Int(stats)
	Case "centralservertcprequest"
		stats = cfg_findbool(stats)
		Server\CentralServerTCPRequest = Int(stats)
	Case "centralserver"
		stats = cfg_findbool(stats)
		Server\CentralServer = Int(stats)
	Case "disabletimestamp"
		stats = cfg_findbool(stats)
		Server\DisableTimestamp = Int(stats)
	;case "fullsynchronization"
		;stats = cfg_findbool(stats)
		;Server\FullSync = Int(stats)
	Case "roundslimitperstart"
		Server\ReloadApplicationRounds = Int(stats)
	Case "resettimerafterconnect"
		stats = cfg_findbool(stats)
		Server\resettimerafterconnect = Int(stats)
	Case "minplayerstostart"
		Server\minplayerstostart = Int(stats)
	Case "voice_quality"
		Server\voicekhz = Int(stats)
	Case "keepinventory"
		stats = cfg_findbool(stats)
		Server\keepitems = Int(stats)
	Case "rconpassword"
		Server\RconPassword = stats
	Case "difficulty"
		Server\Difficulty = Lower(stats)
		Server\PrevDifficulty = Server\Difficulty
		SelectedDifficulty = difficulties(getdiff(Server\Difficulty))
	Case "breach_onlydeathmatch"
		stats = cfg_findbool(stats)
		Server\onlydm = Int(stats)
	Case "disableauthkey"
		stats = cfg_findbool(stats)
		Server\GetAuthkey = Not Int(stats)
	Case "breach"
		stats = cfg_findbool(stats)
		Server\Breach = Int(stats)
	Case "breachtime"
		Server\BreachTime = Int(stats)
	Case "gravity"
		Server\JumpHeight = Float(stats)
	Case "jumpmode"
		stats = cfg_findbool(stats)
		Server\JumpMode = Int(stats)
	Case "maxplayers"
		Server\Maxplayers = Int(stats)
	Case "eventprob"
		Server\EventProb = Float(stats)
	Case "password"
		Server\Password = stats
	Case "timeout"
		Server\timeout = Int(stats)
	Case "nocheat","nocheat mode"
		stats = cfg_findbool(stats)
		Server\NoCheat = Int(stats)
	Case "hostname"
		Server\Servername = stats
	Case "tickrate"
		Server\tickrate = Int(stats)
		Select Server\tickrate
			Case 128,64:
			Default
				Server\tickrate = 64
		End Select
		Server\tickratedelay = GetTickrateDelay()
	Case "port"
		Server\Port = Int(stats)
	Case "mapsize"
		Server\MapSize = Int(stats)
	Case "mapseed"
		Server\RandomSeed = stats
	Case "description"
		Server\Description = stats
	Case "weburl"
		Server\weburl = stats
	Case "menuhtml"
		Server\menuhtml = stats
	Case "restartmenuhtml"
		Server\restartmenuhtml = stats
	Case "gamestate"
		Server\gamestate = stats
	Case "savegameafterexit"
		Server\sae = stats
	Case "introenabled"
		stats = cfg_findbool(stats)
		Server\IntroEnabled = Int(stats)
	Case "voice"
		stats = cfg_findbool(stats)
		Server\ServerVoice = Int(stats)
	Case "argumentsstacksize"
		SE_ARGUMENTS_STACK_SIZE = Int(stats)
	Case "transientstacksize"
		SE_TRANSIENT_STACK_SIZE = Int(stats)
	Case "stacksize"
		SE_ARGUMENTS_STACK_SIZE = 32
		SE_TRANSIENT_STACK_SIZE = Int(stats)
	Case "luascript"
		SE_ExecuteFunction(1, 0)

		For i = 1 To countchar(stats, " ")+1
			script.ScriptsThread = New ScriptsThread
			script\scriptname = Piece(stats, i)
			If SE_IsValidScript(script\scriptname, ".lua") Then
				script\luaThread = SLUA_LOAD_FILE(script\scriptname)
				If script\luaThread <> 0 Then skynet_onload()
			EndIf
		Next
	Case "script"
		SE_Init()

		For i = 1 To countchar(stats, " ")+1
			script.ScriptsThread = New ScriptsThread
			script\scriptname = Piece(stats, i)
			If SE_IsValidScript(script\scriptname) Then
				script\scriptThread = SE_LoadScriptExec(script\scriptname)
				If script\scriptthread <> Null Then
					init_publics_for_script(script\scriptthread)
					skynet_onload
				EndIf
			EndIf
		Next
	Case "scripttext"
		SE_Init()

		For i = 1 To countchar(stats, " ")+1
			script.ScriptsThread = New ScriptsThread
			script\scriptname = Piece(stats, i)
			If SE_IsValidScript(script\scriptname, ".gs") Then

				DeclareDefine("public def", "def")
			
				DeleteFile("SKYNET_TEMP_SCRIPT")
				
				SE_CompileScript(script\scriptname, "SKYNET_TEMP_SCRIPT", "ignoreversion")
				
				script\scriptThread = SE_LoadScriptExec("SKYNET_TEMP_SCRIPT")
				script\status = "Compiling"
				
				If script\scriptthread <> Null Then
					script\status = "Successfully compiled"
					init_publics_for_script(script\scriptthread)
					skynet_onload
				Else
					If Not(SE_ERROR_MESSAGE = "" And (Not SE_Error)) Then 
						script\status = "Error: "+SE_ERROR_MESSAGE
					Else
						script\status = "Unknown error"
					EndIf
				EndIf
				
				SE_ClearCompiler()
				SE_Error = False
				SE_ERROR_MESSAGE = ""
				Delete Each defines
				
				DeleteFile("SKYNET_TEMP_SCRIPT")
			EndIf
		Next
	Case "custommap"
		Server\CustomMap = stats
	Case "breachchat"
		stats = cfg_findbool(stats)
		Server\breachchat = Int(stats)
	Case "logsoff"
		stats = cfg_findbool(stats)
		Server\logsoff = Int(stats)
	Case "longculling"
		stats = cfg_findbool(stats)
		Server\LongCulling = Int(stats)
	End Select
End Function
Function countchar(dest$, char$)
	Local count
	For i = 1 To Len(dest)
		If Mid(dest, i, 1) = char Then count = count + 1
	Next
	Return count
End Function
Function UpdateChat()
	ticks\chatTicks = 0
	Local foundbr, foundlimit
	For cm.Chatmessage = Each ChatMessage
		If cm\Timer < MilliSecs() Then
			Delete cm
		EndIf
	Next
	;if Server\Breach And Server\deathmatchround Then
	;	For cm.ChatMessage = Each ChatMessage
	;		if cm\txt = "Deathmatch round" Then foundbr = True : Exit
	;	Next
	;	if (Not foundbr) Then
	;		cm = New chatmessage
	;		cm\txt = "Deathmatch round"
	;		For a = 1 To Server\Maxplayers
	;			cm\PlayerAccept[a] = 1
	;		Next
	;	EndIf
	;EndIf
	
	For cm.Chatmessage = Each ChatMessage
		ticks\chatTicks = ticks\chatTicks + 1
		If ticks\chatTicks > 255 Then ticks\chatTicks = 0
	Next
End Function
Function chat_network_update()
	For i = 1 To Server\PlayersCount
		If PlayerOptimize[i]\chatTicks <> ticks\chatTicks Then
			udp_WriteByte(M_CHATSYNC)
			udp_WriteByte(1)
			udp_WriteByte ticks\chatTicks
			For cm.ChatMessage = Each ChatMessage
				If cm\PlayerAccept[PlayerOptimize[i]\ID] Then
					udp_WriteShort(cm\MessageID)
					udp_WriteLine(cm\Txt)
				EndIf
			Next
			udp_WriteByte 0
			udp_SendMessage(PlayerOptimize[i]\ID)
		EndIf
	Next
End Function

Global GlobalMessages

Function AddTextToChat(msg$, ID = 0)

	MessagesCount = 0
	
	For cm.ChatMessage = Each ChatMessage
		MessagesCount = MessagesCount+1
	Next
	
	While MessagesCount >= MAX_CHAT_MESSAGES
		Delete (First ChatMessage)
		MessagesCount = MessagesCount-1
	Wend
	
	GlobalMessages = (GlobalMessages Mod 65536) + 1
	
	cm.ChatMessage = New ChatMessage
	cm\Txt = msg
	cm\Timer = MilliSecs()+5000
	cm\MessageID = GlobalMessages
	
	For a = 1 To Server\Maxplayers
		If ID = 0 Then 
			cm\PlayerAccept[a] = 1
		Else
			If ID = a Then cm\PlayerAccept[a] = 1 Else cm\PlayerAccept[a] = 0
		EndIf
	Next
End Function


Function AddLog(msg$, ID = 0, give = True, withouttime = 0)	If give Then AddTextToChat(msg, ID)
	Local serverlogname = CurrentDate()
	If withouttime = 0 And (Not Server\DisableTimestamp) Then msg = "["+CurrentTime()+"] "+msg
	ConsoleLog msg,7
	If Not Server\logsoff Then
		If FileType("serverlogs\") <> 1 Then CreateDir("serverlogs\")
		If FileType("serverlogs\" + CurrentDate() + ".txt") <> 1 Then CreateFile("serverlogs\" + CurrentDate() + ".txt")
		Local f = OpenFile("serverlogs\" + CurrentDate() + ".txt")
		If f <> 0 Then
			SeekFile f, FileSize("serverlogs\" + CurrentDate() + ".txt")
			WriteLine f, msg
			CloseFile f
		EndIf
	EndIf	
End Function
Function CreateFile(filename$)
	CloseFile(WriteFile(filename))
End Function
Function udp_WriteShort(i)
	WriteShort Server\ConnectServer,i
End Function
Function udp_WriteByte(count%)
	WriteByte Server\ConnectServer,count
End Function
Function udp_WriteInt(count)
	WriteInt Server\ConnectServer,count
End Function
Function udp_WriteFloat(count#)
	WriteFloat Server\ConnectServer,count
End Function
Function udp_WriteLine(msgs$)
	WriteLine Server\ConnectServer,msgs
End Function
Function udp_WriteBytes(buffer%, offset%, size%)
	WriteBytes buffer, Server\ConnectServer, offset, size
End Function
Function udp_SendMessage(ID = 0)
	If Player[ID]\Fake Or (Player[ID]\IP = 0 And Player[ID]\Port = 0) Then;Or (Player[ID]\IsLoad = False And Server\IsStarted) Then
		SendUDPMsg(Server\ConnectServer, FAKE_STREAM_IP, 0) ; get access by counthostips and send fake message
		Return
	EndIf
	SendUDPMsg(Server\ConnectServer,Player[ID]\IP,Player[ID]\Port)
End Function
Function CreateBindUDPStream(bind$="",port%=0)
	Local stream = 0
	If bind <> "" Then
		Local currip$ = ""
		Local ips%[256], ipsc%
		While True
			ips[ipsc] = CreateUDPStream(port)
			If ips[ipsc] = 0 Then Exit
			
			If DottedIP(UDPStreamIP(ips[ipsc])) = bind Then
				stream = ips[ipsc]
				Exit
			Else
				ipsc = ipsc+1
			EndIf
		Wend
		
		For i = 0 To ipsc-1
			CloseUDPStream(ips[i])
		Next
		
	Else
		stream = CreateUDPStream(port)
	EndIf
	Return stream
End Function

Function SetUDPStreamBufferSize(stream, size)
	tmpbank% = CreateBank(4)
	PokeInt(tmpbank, 0, size)
	setsockopt(stream, 65535, 4098, tmpbank, 4)
	FreeBank(tmpbank)
End Function
; ----------------------------------------------------------------- multiplayer objects
	Function PrepareModelIdentifier(ID, filename$)
		Multiplayer_Models[ID]=filename
	End Function
		
	Function object_create(modelid, X#, Y#, Z#, range#, rawMesh%, rawMeshFrames%)
		id = object_getid()
		If id = 0 Then Return 0
		multiplayer_Object[id] = New mp_objects
		
		If rawMesh Then
			multiplayer_Object[id]\ent = CreateCube()
			AddAnimSeq(multiplayer_Object[id]\ent, rawMeshFrames)
			SetAnimTime(multiplayer_Object[id]\ent, 0) ; Starting anim mesh animations
		Else
			multiplayer_Object[id]\ent = LoadAnimMesh_Strict(Multiplayer_Models[modelid])
		EndIf
		
		multiplayer_Object[id]\modelid = modelid
		multiplayer_Object[id]\X = X
		multiplayer_Object[id]\Y = Y
		multiplayer_Object[id]\Z = Z
		multiplayer_Object[id]\scale = 1.0
		multiplayer_Object[id]\lerp = 1.0
		multiplayer_Object[id]\objectid = id
		multiplayer_Object[id]\isdynamic = range
		
		For p.players = Each players
			p\objectsTicksContain = p\objectsTicksContain + 1
		Next
		
		PositionEntity multiplayer_Object[id]\ent, X, Y, Z
		Return id
	End Function
	Function object_update_visible(pointer, playerid, Bool)
		If multiplayer_Object[pointer]\visibled[playerid] = (Not Bool) Then Return
		
		Player[playerid]\objectsTicksContain = Player[playerid]\objectsTicksContain + 1
		multiplayer_Object[pointer]\visibled[playerid] = Not Bool
	End Function
	Function object_update_lerp(pointer, lerp)
		multiplayer_Object[pointer]\lerp = lerp
	End Function
	Function object_remove(pointer)
		For p.players = Each players
			p\objectsTicksContain = p\objectsTicksContain + 1
		Next
		Delete multiplayer_Object[pointer]
	End Function
	Function objects_stream_update()
		For p.players = Each players
			p\objectsTicksContain = p\objectsTicksContain + 1
		Next
	End Function
	Function object_getid()
		For i = 1 To MAX_OBJECTS-1
			If multiplayer_Object[i] = Null Then Return i
		Next
	End Function
	
	Function object_sound_create(pointer, filename$, distance#, volume#, playerid=0)
		If playerid > 0 Then
			udp_WriteByte M_OBJECTSOUND
			udp_WriteByte playerid
			udp_WriteByte pointer
			udp_WriteLine(filename)
			udp_WriteFloat distance
			udp_WriteFloat volume
			udp_SendMessage(playerid)
		Else
			For i = 1 To Server\PlayersCount
				udp_WriteByte M_OBJECTSOUND
				udp_WriteByte PlayerOptimize[i]\ID
				udp_WriteByte pointer
				udp_WriteLine(filename)
				udp_WriteFloat distance
				udp_WriteFloat volume
				udp_SendMessage(PlayerOptimize[i]\ID)
			Next
		EndIf
	End Function
	Function objects_network_update()
		
		For i = 1 To Server\PlayersCount
			If PlayerOptimize[i]\objectsTicksContain > 255 Then PlayerOptimize[i]\objectsTicksContain = 1
			
			If PlayerOptimize[i]\objectsTicks <> PlayerOptimize[i]\objectsTicksContain Then
				udp_WriteByte(M_OBJECTS)
				udp_WriteByte(1)
				udp_WriteByte PlayerOptimize[i]\objectsTicksContain
				For mpObj.mp_Objects = Each mp_Objects
					If Not mpObj\visibled[PlayerOptimize[i]\ID] Then
						If mpobj\isdynamic = 0 Or (mpObj\isdynamic > 0 And mpObj\dynamic[PlayerOptimize[i]\ID]) Then
							udp_WriteByte(mpObj\objectid)
							udp_WriteShort(mpObj\modelid)
							udp_WriteFloat(EntityX(mpObj\ent,True))
							udp_WriteFloat(EntityY(mpObj\ent,True))
							udp_WriteFloat(EntityZ(mpObj\ent,True))
							udp_WriteShort(ConvertToShort(EntityPitch(mpObj\ent,True)))
							udp_WriteShort(ConvertToShort(EntityYaw(mpObj\ent,True)))
							udp_WriteShort(ConvertToShort(EntityRoll(mpObj\ent,True)))
							udp_WriteFloat(EntityScaleX2(mpObj\ent))
							udp_WriteShort(AnimTime(mpObj\ent))
							udp_WriteByte(mpObj\lerp)
						EndIf
					EndIf
				Next
				udp_SendMessage(PlayerOptimize[i]\ID)
			EndIf
		Next
		
		Local ObjData$
		For mpObj.mp_Objects = Each mp_Objects
			ObjData = EntityX(mpObj\ent,True)+""+EntityY(mpObj\ent,True)+""+EntityZ(mpObj\ent,True)+""+EntityPitch(mpObj\ent,True)+""+EntityYaw(mpObj\ent,True)+""+EntityRoll(mpObj\ent,True)+""+EntityScaleX2(mpObj\Ent)+""+AnimTime(mpObj\Ent)+""+mpObj\lerp
			If mpObj\PrevData <> ObjData Then
				For p.players = Each players
					If Not mpObj\visibled[p\ID] Then
						If mpObj\dynamic[p\ID] Or mpobj\isdynamic = 0 Then p\objectsTicksContain = p\objectsTicksContain + 1
					EndIf
				Next
			EndIf
			mpObj\PrevData = ObjData
		Next
		
		If IsTimedout(1, 1000) Then
			For mpObj.mp_Objects = Each mp_Objects
				If mpObj\isdynamic > 0 Then
					For p.players = Each players
						If EntityDistance(p\Pivot, mpObj\ent) < mpObj\isdynamic Then
							If Not mpObj\dynamic[p\ID] Then
								p\objectsTicksContain = p\objectsTicksContain + 1
								mpObj\dynamic[p\ID] = True
							EndIf
						Else
							If mpObj\dynamic[p\ID] Then
								p\objectsTicksContain = p\objectsTicksContain + 1
								mpObj\dynamic[p\ID] = False
							EndIf
						EndIf
					Next
				Else
					For p.players = Each players
						p\objectsTicksContain = p\objectsTicksContain + 1
					Next
				EndIf
			Next
		EndIf
	End Function
; -----------------------------------------------------------------
; ----------------------------------------------------------------- multiplayer draws
	Function draw_create(playerid, X, Y, width, height, TYPE_DRAW, intcolor, filename$ = "")
		id = draw_getid(playerid)
		If id = 0 Then Return 0
		Player[playerid]\drawpointers[id] = New draws
		Player[playerid]\drawpointers[id]\filename = filename
		Player[playerid]\drawpointers[id]\X = X
		Player[playerid]\drawpointers[id]\Y = Y
		Player[playerid]\drawpointers[id]\width = width
		Player[playerid]\drawpointers[id]\height = height
		Player[playerid]\drawpointers[id]\drawtype = TYPE_DRAW
		Player[playerid]\drawpointers[id]\drawColor = intcolor
		Player[playerid]\drawpointers[id]\bytesSpend = Len(filename)+1+21
		Player[playerid]\drawsTicksContain = Player[playerid]\drawsTicksContain + 1
		Return id
	End Function
	Function draw_setpos(playerid, pointer, X, Y)
		Player[playerid]\drawpointers[pointer]\X = X
		Player[playerid]\drawpointers[pointer]\Y = Y
		Player[playerid]\drawsTicksContain = Player[playerid]\drawsTicksContain + 1
	End Function
	Function draw_setcolor(playerid, pointer, intcolor)
		Player[playerid]\drawpointers[pointer]\drawColor = intcolor
		Player[playerid]\drawsTicksContain = Player[playerid]\drawsTicksContain + 1
	End Function
	Function draw_remove(playerid, pointer)
		Player[playerid]\drawsTicksContain = Player[playerid]\drawsTicksContain + 1
		Delete Player[playerid]\drawpointers[pointer]
	End Function
	Function draw_getid(playerid)
		For i = 1 To MAX_DRAWS-1
			If Player[playerid]\drawpointers[i] = Null Then Return i
		Next
	End Function
	Function draws_network_update()
		For i = 1 To Server\PlayersCount
			If PlayerOptimize[i]\drawsTicksContain > 255 Then PlayerOptimize[i]\drawsTicksContain = 1
			If PlayerOptimize[i]\drawsticks <> PlayerOptimize[i]\drawsTicksContain Then
				udp_WriteByte(M_DRAWS)
				udp_WriteByte(1)
				udp_WriteByte PlayerOptimize[i]\drawsTicksContain
				For a = 1 To MAX_DRAWS-1
					If PlayerOptimize[i]\drawpointers[a] <> Null Then
						udp_WriteByte a
						udp_WriteLine PlayerOptimize[i]\drawpointers[a]\filename
						udp_WriteInt PlayerOptimize[i]\drawpointers[a]\x
						udp_WriteInt PlayerOptimize[i]\drawpointers[a]\y
						udp_WriteInt PlayerOptimize[i]\drawpointers[a]\width
						udp_WriteInt PlayerOptimize[i]\drawpointers[a]\height
						udp_WriteInt PlayerOptimize[i]\drawpointers[a]\drawColor
						udp_WriteByte PlayerOptimize[i]\drawpointers[a]\drawType
					EndIf
				Next
				udp_WriteByte(0)
				udp_SendMessage(PlayerOptimize[i]\ID)
			EndIf
		Next
	End Function
; ----------------------------------------------------------------- multiplayer texts
	Function text_create(playerid, txt$, X, Y, intcolor, fntname$, fntsize#)
		id = text_getid(playerid)
		If id = 0 Then Return 0
		Player[playerid]\textpointers[id] = New texts
		Player[playerid]\textpointers[id]\fntname = fntname
		Player[playerid]\textpointers[id]\fntsize = fntsize
		Player[playerid]\textpointers[id]\X = X
		Player[playerid]\textpointers[id]\Y = Y
		Player[playerid]\textpointers[id]\txtColor = intcolor
		Player[playerid]\textpointers[id]\txt = txt
		Player[playerid]\textsTicksContain = Player[playerid]\textsTicksContain + 1
		Return id
	End Function
	Function text_setpos(playerid, pointer, X, Y)
		Player[playerid]\textpointers[pointer]\X = X
		Player[playerid]\textpointers[pointer]\Y = Y
		Player[playerid]\textsTicksContain = Player[playerid]\textsTicksContain + 1
	End Function
	Function text_setcolor(playerid, pointer, intcolor)
		Player[playerid]\textpointers[pointer]\txtColor = intcolor
		Player[playerid]\textsTicksContain = Player[playerid]\textsTicksContain + 1
	End Function
	Function text_settext(playerid, pointer, txt$)
		Player[playerid]\textpointers[pointer]\txt = txt
		Player[playerid]\textsTicksContain = Player[playerid]\textsTicksContain + 1
	End Function
	Function text_remove(playerid, pointer)
		Player[playerid]\textsTicksContain = Player[playerid]\textsTicksContain + 1
		Delete Player[playerid]\textpointers[pointer]
	End Function
	Function text_getid(playerid)
		For i = 1 To MAX_TEXTS-1
			If Player[playerid]\textpointers[i] = Null Then Return i
		Next
	End Function
	Function texts_network_update()
		For i = 1 To Server\PlayersCount
			If PlayerOptimize[i]\textsTicksContain > 255 Then PlayerOptimize[i]\textsTicksContain = 1
			If PlayerOptimize[i]\textsTicks <> PlayerOptimize[i]\textsTicksContain Then
				udp_WriteByte(M_TEXTS)
				udp_WriteByte(1)
				udp_WriteByte PlayerOptimize[i]\textsTicksContain
				For a = 1 To MAX_TEXTS-1
					If PlayerOptimize[i]\textpointers[a] <> Null Then
						udp_WriteByte a
						udp_WriteLine PlayerOptimize[i]\textpointers[a]\fntname
						udp_WriteLine PlayerOptimize[i]\textpointers[a]\txt
						udp_WriteInt PlayerOptimize[i]\textpointers[a]\x
						udp_WriteInt PlayerOptimize[i]\textpointers[a]\y
						udp_WriteInt PlayerOptimize[i]\textpointers[a]\txtColor
						udp_WriteByte PlayerOptimize[i]\textpointers[a]\fntsize
					EndIf
				Next
				udp_WriteByte(0)
				udp_SendMessage(PlayerOptimize[i]\ID)
			EndIf
		Next
	End Function
; -----------------------------------------------------------------
Function Kick(ID, reason$)
	If GetScripts() Then 
		public_inqueue(public_OnPlayerDisconnect)
		public_addparam(0, ID)
		public_addparam(0, reason, SE_STRING)
		callback
	EndIf
	If reason <> "" Then AddLog(reason)
	ClearChatForPlayer(ID)
		
	For i = 1 To MAX_PLAYER_VARIABLES-1
		If Player[ID]\variables[i] <> Null Then Delete Player[ID]\variables[i]
	Next
	For i = 1 To MAX_TEXTS-1
		If Player[ID]\textpointers[i] <> Null Then Delete Player[ID]\textpointers[i]
	Next
	For i = 1 To MAX_DRAWS-1
		If Player[ID]\drawpointers[i] <> Null Then Delete Player[ID]\drawpointers[i]
	Next

	For rc.Rockets = Each Rockets
		If rc\shooter = ID Then
			FreeEntity rc\pivot
			Delete rc
		EndIf
	Next
	For g.grenades = Each grenades
		If g\shooter = ID Then
			FreeEntity g\obj
			Delete g
		EndIf
	Next
	
	For spawnpointer.SpawnPoint = Each SpawnPoint
		If spawnpointer\playerid = ID Then Delete spawnpointer
	Next
	
	For it.Items = Each Items
		If it\picker = ID And it\picker <> 0 Then
			If Not mp_IsASCP(Player[ID]\BreachType) Then
				PositionEntity it\collider,Player[it\picker]\x,Player[it\picker]\y+0.7,Player[it\picker]\z, True
				ResetEntity it\Collider
				RotateEntity(it\collider, 0, EntityYaw(Player[it\picker]\Pivot)+Rnd(-110,110), 0)
				MoveEntity(it\collider, 0, -0.1, 0.1)
				it\picked = False
				it\picker = 0
			Else
				RemoveItem(it)
			EndIf
		EndIf
	Next
	If Player[ID]\Obj <> 0 Then
		FreeEntity(Player[ID]\Obj)
		Player[ID]\Obj = 0
	EndIf
	If Player[ID]\Pivot <> 0 Then 
		FreeEntity(Player[ID]\Pivot)
		Player[ID]\Pivot = 0
	EndIf
	
	If Player[ID]\AnticheatPivot <> 0 Then 
		FreeEntity(Player[ID]\AnticheatPivot)
		Player[ID]\AnticheatPivot = 0
	EndIf

	For i = 1 To Player[ID]\ShouldSendShootTicks
		RemoveByteStream(Player[ID]\ShouldSendShoot[i])
	Next
	
	Local lastplayerroomname$ = Player[ID]\PlayerRoomName
	Delete Player[ID]
	Server\PlayersCount = Server\PlayersCount-1
	If Server\PlayersCount = 0 Then 
		If Server\IsStarted And lastplayerroomname <> "" Then
			ShouldRestartServer% = breach_IsStarted()
			
			Server\IsStarted = False
			Server\Difficulty = Server\PrevDifficulty
			SelectedDifficulty = difficulties(getdiff(Server\Difficulty))
			
			If Server\LobbyDisabled Then Server\IsStarted = True
		EndIf
		
		gameInfo\b\BreachTimer = 0
		gameInfo\b\TimeoutBreach = 0
		gameInfo\b\LobbyTimer = 0
		gameInfo\b\Exploded = False
		Server\deathmatchround = False
	EndIf
	; Optimizing
	Server\PlayersCount = 0
	For i = 1 To Server\Maxplayers
		If Player[i] <> Null Then
			Server\PlayersCount = Server\PlayersCount+1
			PlayerOptimize[Server\PlayersCount] = Player[i]
		EndIf
	Next
End Function
Function FindFreePlayerID()
	For i = 1 To Server\Maxplayers
		If Player[i] = Null Then Return i
	Next
End Function
Function CreatePlayer.players(ID)
	If Player[ID] <> Null Then Return Player[ID]
	Player[ID] = New players
	Player[ID]\ID = ID
	Player[ID]\mapload = (Server\Custommap="")
	Player[ID]\speedmult = 1.0
	Player[ID]\Tag = ""
	Player[ID]\TagR = 255
	Player[ID]\TagG = 255
	Player[ID]\TagB = 255
	Player[ID]\JumpState = JumpFalling
	Player[ID]\Size = 1
	
	; Optimizing
	If ID > 0 Then
		Server\PlayersCount = Server\PlayersCount+1
		PlayerOptimize[Server\PlayersCount] = Player[ID]
	EndIf
	Return Player[ID]
End Function
Function Piece$(s$,entry,char$=" ")
	While Instr(s,char+char)
		s=Replace(s,char+char,char)
	Wend
	For n=1 To entry-1
		p=Instr(s,char)
		s=Right(s,Len(s)-p)
	Next
	p=Instr(s,char)
	If p<1
		a$=s
	Else
		a=Left(s,p-1)
	EndIf
	Return a
End Function
Function Bool$(i)
	Select i
		Case 0: Return "False"
		Case 1: Return "True"
	End Select
	Return "Undefined"
End Function

Function GetTickrateDelay()
	Return 40-Float(Server\tickrate)/6.4
End Function
Function GetMaxPredictionTicks()
	Select Server\tickrate
		Case 2: Return 12
		Case 4: Return 7
		Case 8: Return 5
		Case 16: Return 4
		Case 32: Return 3
		Case 64: Return 2
		Case 128: Return 2
	End Select
End Function
Function GetMaxPredictionTicksFix()
	Select Server\tickrate
		Case 2: Return 2
		Case 4: Return 2
		Case 8: Return 3
		Case 16: Return 4
		Case 32: Return 5
		Case 64: Return 7
		Case 128: Return 12
	End Select
End Function

Function SetRandomSeed$()
	Local RandomSeed$
	If Rand(15)=1 Then 
		Select Rand(14)
			Case 1 
				RandomSeed = "NIL"
			Case 2
				RandomSeed = "NO"
			Case 3
				RandomSeed = "d9341"
			Case 4
				RandomSeed = "5CP_I73"
			Case 5
				RandomSeed = "DONTBLINK"
			Case 6
				RandomSeed = "CRUNCH"
			Case 7
				RandomSeed = "die"
			Case 8
				RandomSeed = "HTAED"
			Case 9
				RandomSeed = "rustledjim"
			Case 10
				RandomSeed = "larry"
			Case 11
				RandomSeed = "JORGE"
			Case 12
				RandomSeed = "dirtymetal"
			Case 13
				RandomSeed = "whatpumpkin"
			Case 14
				RandomSeed = "14542015"
		End Select
	Else
		n = Rand(1,13)
		For i = 1 To n
			If Rand(3)=1 Then
				Select Rand(12)
					Case 1
						RandomSeed = "deeznuts"
					Case 2
						RandomSeed = "7kyr06"
					Case 3
						RandomSeed = "36139326"
					Case 4
						RandomSeed = "catsarecute"
					Case 5
						RandomSeed = "6660666"
					Case 6
						RandomSeed = "173309319"
					Case 7
						RandomSeed = "Ohmygandhi"
					Case 8
						RandomSeed = "16535651"
					Case 9
						RandomSeed = "Oi61sfbn"
					Case 10
						RandomSeed = "612672311"
					Case 11
						RandomSeed = "cum"
					Case 12
						RandomSeed = "whiskey"
				End Select
			Else
					Select Rand(14)
				Case 1 
					RandomSeed = "NIL"
				Case 2
					RandomSeed = "NO"
				Case 3
					RandomSeed = "d9341"
				Case 4
					RandomSeed = "5CP_I73"
				Case 5
					RandomSeed = "DONTBLINK"
				Case 6
					RandomSeed = "CRUNCH"
				Case 7
					RandomSeed = "die"
				Case 8
					RandomSeed = "HTAED"
				Case 9
					RandomSeed = "rustledjim"
				Case 10
					RandomSeed = "larry"
				Case 11
					RandomSeed = "JORGE"
				Case 12
					RandomSeed = "dirtymetal"
				Case 13
					RandomSeed = "whatpumpkin"
				Case 14
					RandomSeed = "14542015"
				End Select
			EndIf
		Next							
	EndIf
	Return RandomSeed
End Function

Function ClearChatForPlayer(i)
	For cm.ChatMessage = Each ChatMessage
		cm\PlayerAccept[i] = 0
	Next
End Function
Function GetNameDifficulty$(i)
	Select i
		Case 0
			Return "safe"
		Case 1
			Return "euclid"
		Case 2
			Return "keter"
	End Select
End Function
Function misc_network_update(byte)
	If Not Server\IsStarted Then Return
	
	If Server\Breach And NETWORK_BYTE[byte] = M_NPCSYNC Then
		Server\NetworkTicks = reverseto(Server\NetworkTicks+1, 1, MAX_BYTES)
		misc_network_update(Server\NetworkTicks)
		Return
	EndIf

	
	Select NETWORK_BYTE[byte]
		Case M_EVENTSWRITE
			ByteStreamReset(CompactBank[0])
			For e.Events = Each Events
				If Not IsABlockedEvent(e) Then
					ByteStreamWriteChar(CompactBank[0],e\ID)
					ByteStreamWriteChar(CompactBank[0],e\EventConst)
					ByteStreamWriteChar(CompactBank[0],e\room\ID)
					ByteStreamWriteFloat(CompactBank[0],e\EventState)
					ByteStreamWriteFloat(CompactBank[0],e\EventState2)
					ByteStreamWriteFloat(CompactBank[0],e\EventState3)
				EndIf
			Next
			ByteStreamWriteChar CompactBank[0],0
		Case M_ROOMOBJECTS
			ByteStreamReset(CompactBank[0])
			For r.Rooms = Each Rooms
				If Not IsABlockedRoom(r) Then
					For i = 0 To MaxRoomObjects-1
						If r\Objects[i] <> 0 Then
							If Not r\ObjectStatic[i] Then
								ByteStreamWriteChar(CompactBank[0],r\ID)
								ByteStreamWriteChar(CompactBank[0],i)
								ByteStreamWriteFloat(CompactBank[0],EntityX(r\Objects[i],True))
								ByteStreamWriteFloat(CompactBank[0],EntityY(r\Objects[i],True))
								ByteStreamWriteFloat(CompactBank[0],EntityZ(r\Objects[i],True))
								ByteStreamWriteFloat(CompactBank[0],EntityPitch(r\Objects[i], True))
								ByteStreamWriteFloat(CompactBank[0],EntityYaw(r\Objects[i], True))
								ByteStreamWriteFloat(CompactBank[0],EntityRoll(r\Objects[i], True))
							EndIf
						EndIf
					Next
				EndIf
			Next
			ByteStreamWriteChar(CompactBank[0],0)
		Case M_WRITEELEVATORS
			If Not IsTimedout(2, 500) Then
				Server\NetworkTicks = ReverseTo(Server\NetworkTicks+1, 1, MAX_BYTES)
				misc_network_update(Server\NetworkTicks)
				Return
			EndIf
		Case M_CORPSESSYNC
			If (Not IsTimedout(11, 300)) Or (Not Server\Breach) Then
				Server\NetworkTicks = reverseto(Server\NetworkTicks+1, 1, MAX_BYTES)
				misc_network_update(Server\NetworkTicks)
				Return
			EndIf
	End Select
	
	For i = 1 To Server\PlayersCount
		If PlayerOptimize[i]\IsLoad Then
			udp_WriteByte(m_miscellaneous)
			udp_WriteByte(1)
			udp_WriteByte(byte)
			Select NETWORK_BYTE[byte]
				Case M_NPCSYNC
					WriteNPCs(PlayerOptimize[i]\ID)
				Case M_SYNCDOOR
					WriteDoors(PlayerOptimize[i]\ID)
				Case M_FIXEDITEMS
					WriteItems(PlayerOptimize[i]\ID)
				Case M_EVENTSWRITE,M_ROOMOBJECTS
					WriteAsData(CompactBank[0])
				Case M_WRITEELEVATORS
					WriteElevators()
				Case M_CORPSESSYNC
					WriteCorpses(PlayerOptimize[i]\ID)
			End Select
			udp_SendMessage(PlayerOptimize[i]\ID)
		EndIf
	Next
End Function

Function WriteCorpses(playerid)			
	For db.corpses = Each corpses
		If Distance3(db\x, db\y, db\z, EntityX(Player[playerid]\Pivot), EntityY(Player[playerid]\Pivot), EntityZ(Player[playerid]\Pivot)) < 35*2 Then
			udp_WriteByte(db\ID)
			udp_WriteByte(db\breachtype)
			udp_WriteFloat(db\x)
			udp_WriteFloat(db\y)
			udp_WriteFloat(db\z)
			udp_WriteShort(db\yaw)
			udp_WriteShort(db\playerscale)
			udp_WriteByte(db\IsCorpseTimedOut + (db\IsCorpseFalled * 2))
		EndIf
	Next
End Function

Function WriteNPCs(playerid)
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
Function WriteDoors(playerid)
	;if Server\FullSync And (Not ((Not Server\Interpolation) And (Not Server\Prediction))) Then
	;	For d.Doors = Each Doors
	;		if EntityDistance(d\obj, Player[playerid]\Pivot) < 15 Or d\IsElevatorDoor <> 0 Then
	;			udp_WriteShort(d\ID)
	;			udp_WriteByte((d\open) + (2 * d\locked) + (4*(d\IsElevatorDoor<>0)))
	;			if d\IsElevatorDoor <> 0 then udp_WriteFloat(d\liftstate)
	;		EndIf
	;	Next
	;Else
	For d.Doors = Each Doors
		If EntityDistance(d\obj, Player[playerid]\Pivot) < 15 Then
			If d\IsElevatorDoor = 0 Then
				udp_WriteShort(d\ID)
				udp_WriteByte((d\open) + (2 * d\locked) + (4 * (d\ChangedKeycard<>0)) + (8 * (d\ChangedCode<>0)))
				If d\ChangedKeycard Then udp_WriteByte(d\Keycard)
				If d\ChangedCode Then udp_WriteByte(d\Code<>"")
			EndIf
		EndIf
	Next
End Function

Function WriteElevators()
	;Return 0
	For d.Doors = Each Doors
		If d\IsElevatorDoor > 0 Then
			;if (d\liftstate < -1 or d\liftstate > 1) then
			udp_WriteShort(d\ID)
			udp_WriteByte((d\open) + (2 * d\locked) + (4 * (d\ChangedKeycard<>0)) + (8 * (d\ChangedCode<>0)))
			udp_WriteFloat(d\liftstate)
			
			If d\ChangedKeycard Then udp_WriteByte(d\Keycard)
			If d\ChangedCode Then udp_WriteByte(d\Code<>"")
			;EndIf
		EndIf
	Next
End Function
Function WriteItems(playerid)
	For it.Items = Each Items

		If (EntityDistance(it\collider, Player[playerid]\Pivot) < 15*0.5 Or it\picker = playerid) And (it\picker < 1 Or Player[it\picker]\SelectedItem = it\ID Or it\picker = playerid) Then
			udp_WriteShort(it\ID)
			udp_WriteInt(it\itemtemplate\templateID)
			udp_WriteFloat(EntityX(it\collider, True))
			udp_WriteFloat(EntityY(it\collider, True))
			udp_WriteFloat(EntityZ(it\collider, True))
			udp_WriteByte(it\picker)
		EndIf
	Next
	
End Function

Function server_network_update()

	Local ReceivePlayer.players
	Local GetPlayer.players

	ByteStreamReset(CompactBank[0])
	ByteStreamReset(CompactBank[1])
	
	For i = 1 To Server\PlayersCount
		GetPlayer = PlayerOptimize[i]
		ByteStreamWriteChar(CompactBank[0],GetPlayer\ID)
		If Server\IsStarted > 0 Then
			ByteStreamWriteChar CompactBank[0],GetPlayer\BreachType

			If GetPlayer\BreachType <> 0 Then
				ByteStreamWriteFloat CompactBank[0],EntityX(GetPlayer\Pivot)
				ByteStreamWriteFloat CompactBank[0],EntityY(GetPlayer\Pivot)-0.32
				ByteStreamWriteFloat CompactBank[0],EntityZ(GetPlayer\Pivot)
				ByteStreamWriteShort CompactBank[0],converttoshort(GetPlayer\yaw)
				ByteStreamWriteShort CompactBank[0],converttoshort(GetPlayer\BonePitch)
				ByteStreamWriteChar CompactBank[0],GetPlayer\PLAYER_MOVE
				ByteStreamWriteFloat CompactBank[0],GetPlayer\BlinkTimer
				ByteStreamWriteChar CompactBank[0],GetPlayer\wearingdir
				
				ByteStreamWriteChar CompactBank[0],GetPlayer\PlayerSoundVolume
				ByteStreamWriteShort CompactBank[0],GetPlayer\ping
				ByteStreamWriteChar CompactBank[0],GetPlayer\p_byte
				ByteStreamWriteChar CompactBank[0],GetPlayer\UsedWeapon
				ByteStreamWriteShort CompactBank[0],GetPlayer\SelectedItem
				ByteStreamWriteChar CompactBank[0],GetPlayer\PlayerRoomID
			Else
				ByteStreamWriteShort CompactBank[0],GetPlayer\ping
				ByteStreamWriteChar CompactBank[0],GetPlayer\p_byte
			EndIf
		Else
			ByteStreamWriteChar CompactBank[0],GetPlayer\Ready
			ByteStreamWriteShort CompactBank[0],GetPlayer\ping
			ByteStreamWriteChar CompactBank[0],GetPlayer\IsLoad
		EndIf

		ByteStreamWriteChar(CompactBank[1],GetPlayer\ID)
		ByteStreamWriteChar CompactBank[1],GetPlayer\Ready
		ByteStreamWriteShort CompactBank[1],GetPlayer\ping
		ByteStreamWriteChar CompactBank[1],GetPlayer\IsLoad
	Next
	
	For i = 1 To Server\PlayersCount
		ReceivePlayer = PlayerOptimize[i]
		
		udp_WriteByte(M_SERVERSYNC)
		udp_WriteByte(1)
		udp_WriteByte(Server\IsStarted*ReceivePlayer\mapload)
		udp_WriteByte(ReceivePlayer\BreachType)
		udp_WriteFloat(Server\JumpHeight)
		udp_WriteInt(Max(-1, gameInfo\b\LobbyTimer-MilliSecs()))
		udp_WriteInt(Max(-1, gameInfo\b\BreachTimer-MilliSecs()))
		udp_WriteShort(ReceivePlayer\ping)
		If (Server\IsStarted*ReceivePlayer\mapload) > 0 Then
			udp_WriteBytes(GetByteStreamData(CompactBank[0]), 0, GetByteStreamDataSize(CompactBank[0]))
		Else
			udp_WriteBytes(GetByteStreamData(CompactBank[1]), 0, GetByteStreamDataSize(CompactBank[1]))
		EndIf
		udp_WriteByte(0)

		udp_WriteShort(ConvertToShort(SecondaryLightOn))
		udp_WriteByte(Contained106+(2*RemoteDoorOn))
		udp_WriteShort(MTFTimer)
		udp_WriteShort(converttoshort(ItemsRotateRand))
		udp_WriteByte(gameInfo\b\Exploded)
		
		If Server\FullSync Then

			udp_WriteByte(ReceivePlayer\IsDead+(2*ReceivePlayer\Noclip)+(4*ReceivePlayer\HandCuffed)+(8*Server\CameraShakeOnDamage)+(16*Server\NoclipAnticheat)+(32*ReceivePlayer\Announcement) + (64*ReceivePlayer\Authorized))
			udp_WriteFloat(ReceivePlayer\Injuries)
			udp_WriteShort(ReceivePlayer\Health)
			udp_WriteShort(ReceivePlayer\Size*100)
			udp_WriteFloat(ReceivePlayer\SpeedMult)
			udp_WriteInt(Max(ReceivePlayer\CheckIntercom-MilliSecs(), -1))
			udp_WriteInt(Max(ReceivePlayer\TimeIntercom-MilliSecs(), -1))
			udp_WriteByte(ReceivePlayer\pocketdimensionstate)
			If ReceivePlayer\ShouldPlayerTeleport <> 0 And ReceivePlayer\ShouldPlayerTeleport <> ReceivePlayer\CurrentPositionID Then
				udp_WriteShort(ReceivePlayer\ShouldPlayerTeleport)
				udp_WriteFloat(ReceivePlayer\ShouldPlayerTeleportX)
				udp_WriteFloat(ReceivePlayer\ShouldPlayerTeleportY)
				udp_WriteFloat(ReceivePlayer\ShouldPlayerTeleportZ)
				udp_WriteByte(ReceivePlayer\ShouldPlayerTeleportIsID)
				If ReceivePlayer\ShouldPlayerTeleportIsID Then
					udp_WriteByte(Int(ReceivePlayer\ShouldPlayerTeleportRoom))
				Else
					udp_WriteLine(ReceivePlayer\ShouldPlayerTeleportRoom)
				EndIf
			EndIf

			;udp_WriteInt(Player[i]\PacketLoss)
			;udp_WriteInt(Player[i]\PreviousPacketLoss)
		EndIf
		udp_SendMessage(ReceivePlayer\ID)
	Next
	;For i2 = 1 To Server\Maxplayers
	;			if Player[i2] <> Null Then
	;				if i2 <> i Then
	;					GetPlayer = Player[i2]
	;					udp_WriteByte(i2)
	;					if (Server\IsStarted*ReceivePlayer\mapload) > 0 Then
	;						udp_WriteByte GetPlayer\BreachType
;
	;						if GetPlayer\BreachType <> 0 Then
	;							udp_WriteFloat EntityX(GetPlayer\Pivot)
	;							udp_WriteFloat EntityY(GetPlayer\Pivot)-0.32
	;							udp_WriteFloat EntityZ(GetPlayer\Pivot)
	;							udp_WriteShort converttoshort(GetPlayer\yaw)
	;							udp_WriteShort converttoshort(GetPlayer\BonePitch)
	;							udp_WriteByte GetPlayer\PLAYER_MOVE
	;							udp_WriteFloat GetPlayer\BlinkTimer
	;							udp_WriteByte GetPlayer\WearingDir
	;							
	;							udp_WriteByte GetPlayer\PlayerSoundVolume
	;							udp_WriteShort GetPlayer\ping
	;							udp_WriteByte GetPlayer\p_byte
	;							udp_WriteByte GetPlayer\UsedWeapon
	;							udp_WriteShort GetPlayer\SelectedItem
	;							udp_WriteByte GetPlayer\PlayerRoomID
	;						Else
	;							udp_WriteShort GetPlayer\ping
	;							udp_WriteByte GetPlayer\p_byte
	;						EndIf
	;					Else
	;						udp_WriteByte GetPlayer\Ready
	;						udp_WriteShort GetPlayer\ping
	;						udp_WriteByte GetPlayer\IsLoad
	;					EndIf
	;				EndIf
	;				;EndIf
	;			EndIf
	;		Next
	;gameinfo\SendPackets = gameinfo\SendPackets + 1
End Function
Function UpdatePlayersCount()
	For i = 1 To Server\PlayersCount
		CheckPlayerTimeout(PlayerOptimize[i])
	Next
End Function
Function ReadBool(byte, index)
	Return ((byte Shr index) Mod 2)
End Function

Function ReloadUDPStream(stream)
	Local port = UDPStreamPort(stream)
	CloseUDPStream stream
	Return CreateUDPStream(port)
End Function

Function GenerateSeedNumber(seed$)
 	Local temp% = 0
 	Local shift% = 0
 	For i = 1 To Len(seed)
 		temp = temp Xor (Asc(Mid(seed,i,1)) Shl shift)
 		shift=(shift+1) Mod 24
	Next
 	Return temp
End Function
Function RestartServer(seed$ = "")
	
	DisconnectFromCentralServer()
	
	AddLog("Restarting...")
	
	Delete Each AntiDDOS
	Delete gameInfo\b
	Delete gameInfo
	gameInfo = New g_I
	gameInfo\b = New breach

	
	If seed = "" Then seed = SetRandomSeed()
	Server\RandomSeed = seed
	
	Server\IsStarted = False
	Server\deathmatchround = False
	Server\PrevStart = False
	ticks\chatTicks = 0
	
	For p.players = Each players
		If Not p\fake Then
			For i = 0 To 10
				udp_WriteByte(M_RESTARTSERVERBREACH)
				udp_WriteByte(1)
				udp_SendMessage(p\ID)
			Next
			Kick(p\ID, "")
		EndIf
	Next
	
	Server\ReloadApplicationRoundsInternal = Server\ReloadApplicationRoundsInternal + 1
	If Server\ReloadApplicationRoundsInternal >= Server\ReloadApplicationRounds Then
		AddLog("The rounds have exceeded the number of allowed rounds, restart the application.")
		If Not ReloadApplication() Then AddLog("The application couldn't be restarted, because you maybe don't have server.exe in current directory")
	EndIf
	
	public_inqueue(public_OnGenerateWorld, True)
	If SE_GetReturnValue() <> -1 Then Server\RandomSeed = SE_GetReturnValue()
	
	NullMap()
	InitNewGame()
	
	For p.players = Each players
		If p\fake Then
			mp_CreatePlayerObject(p\ID)
			For r.rooms = Each rooms
				If r\roomtemplate\name = "173" Then
					p\playerroomid = r\ID
					mp_UpdatePlayerPosition(p)
					mp_SetRoomNameToPlayer(p)
					Exit
				EndIf
			Next
		EndIf
	Next
	
	If GetScripts() Then 
		Local srvres = public_inqueue(public_OnServerRestart)
		public_addparam(srvres, Server\RandomSeed, SE_STRING)
		callback(seed<>"")
	EndIf
	
	AddLog("Server successfully restarted.")
	
	;Local count = 0
	;for r.rooms = each rooms
	;	count = count + 1
	;next
	;debuglog "room count: "+count
	;count = 0
	;for d.doors = each doors
	;	count = count + 1
	;next
	;debuglog "room count: "+count
	
	For AC.AuthConnection = Each AuthConnection
		RemoveAuthConnection(AC)
	Next
	
	RequestDataFromGlobal()
	;AddCentralServer("127.0.0.1", 6696)
	;ConnectToCentralServer()
	Server\ConnectServer = ReloadUDPStream(Server\ConnectServer)
	SetUDPStreamBufferSize(Server\ConnectServer, 8192*Server\recvbuffermult)
End Function
; ---------------------------------------------------------------------------- rcon
Function RCON_Kick(name$ = "")
	For p.players = Each players
		If p\name = name Then Kick(p\ID, "[RCON] "+p\Name+" has been kicked.")
	Next
End Function

Function RCON_BanIP(banfile$, IP$)
	If FileType(banfile) = 0 Then CreateFile(banfile)
	For p.players = Each players
		If p\PlayerIP = IP Then Kick(p\ID, "[RCON] "+p\Name+" has been banned.")
	Next
	ban.banned = New banned
	ban\IP = IP
	Local f = OpenFile(banfile)
	SeekFile f, FileSize(banfile)
	WriteLine(f, IP)
	CloseFile f
End Function

Function RCON_BanSteamID(banfile$, ID)
	If FileType(banfile) = 0 Then CreateFile(banfile)
	For p.players = Each players
		If p\PlayerSteamID = ID Then Kick(p\ID, "[RCON] "+p\Name+" has been banned.")
	Next
	ban.banned = New banned
	ban\ID = ID
	Local f = OpenFile(banfile)
	SeekFile f, FileSize(banfile)
	WriteLine(f, ID)
	CloseFile f
End Function

Function RCON_LoadBanList(banfile$)
	Local f = ReadFile(banfile)
	If f = 0 Then ConsoleLog("[RCON] IP Ban list not loaded.",7) : Return
	While Not Eof(f)
		ban.banned = New banned
		ban\IP = ReadLine(f)
	Wend
	ConsoleLog("[RCON] Ban IP list loaded successfully.",7)
	CloseFile f
End Function

Function RCON_LoadSteamBanlist(banfile$)
	f = ReadFile(banfile)
	If f = 0 Then ConsoleLog("[RCON] Steam ban list not loaded.",7) : Return
	While Not Eof(f)
		ban.banned = New banned
		ban\ID = Int(ReadLine(f))
	Wend
	ConsoleLog("[RCON] Steam ban list loaded successfully.",7)
	CloseFile f
End Function

Function RCON_ReloadBanList(banfile$)
	For ban.banned = Each banned
		Delete ban
	Next
	RCON_LoadBanList(banfile)
	RCON_LoadSteamBanlist(banfile+"steam")
End Function

Function RCON_Restart()
	RestartServer()
End Function
Function RCON_Hostname(hostname$)
	Server\Servername = hostname
End Function
Function RCON_findcmd$(cmdline$)
	Local cmd$ = Piece(cmdline, 2), found = False
	For rc.rcon = Each rcon
		If rc\command = cmd Then found = True : Exit
	Next
	If Not found Then Return "Not found"
	Return cmd
End Function
Function RCON_GetAttribute$(cmdline$)
	Return Piece(cmdline, 3)
End Function
Function RCON_AddCommand(cmd$)
	Local rc.rcon = New rcon
	rc\command = cmd
End Function
Function RCON_Gravity(gravity#)
	Server\JumpHeight = gravity
End Function
Function RCON_Password(password$)
	Server\Password = password
End Function
Function RCON_MapSeed(seed$)
	Server\RandomSeed = seed
End Function
Function RCON_Difficulty(byte)
	Server\Difficulty = GetNameDifficulty(byte)
	SelectedDifficulty = difficulties(byte)
End Function
; your
Function RCON_ExecuteCMD$(cmd$, attribute$ = "")
	Select Lower(cmd)
		Case "kick"
			RCON_Kick(attribute)
		Case "banip"
			RCON_BanIP("banlist", attribute)
		Case "banid"
			RCON_BanSteamID("banliststeam", attribute)
		Case "reloadbanlist"
			RCON_ReloadBanList("banlist")
		Case "hostname"
			RCON_Hostname(attribute)
		Case "restart"
			RCON_Restart()
		Case "gravity"
			RCON_Gravity(attribute)
		Case "password"
			RCON_Password(attribute)
		Case "seed"
			RCON_MapSeed(attribute)
		Case "difficulty"
			RCON_Difficulty(attribute)
		Case "startmatch"
			ShouldStartRound = True
	End Select
	Return cmd
End Function
;-----------------------------------------------------------------------------
Function IPToDecimal(IP$)
	Return (Int(Piece(IP, 1, ".")) Shl 24) + (Int(Piece(IP, 2, ".")) Shl 16) + (Int(Piece(IP, 3, ".")) Shl 8) + Int(Piece(IP, 4, "."))
End Function
Function WriteRoomsObjects()
	For r.Rooms = Each Rooms
		If Not IsABlockedRoom(r) Then
			For i = 0 To MaxRoomObjects-1
				If r\Objects[i] <> 0 Then mp_WriteRoomObject(r, i)
			Next
		EndIf
	Next
	udp_WriteByte 0
End Function

Function mp_WriteRoomObject(r.Rooms, id)
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

Function WriteAsData(ByteStream.bs)
	udp_WriteBytes(GetByteStreamData(CompactBank[0]), 0, GetByteStreamDataSize(CompactBank[0]))
End Function
Function breach_CountRoles()
	Return 
	For i = 0 To LAST_BREACH_TYPE-1
		gameInfo\breachrolecount[i] = 0
	Next
	For a = 1 To Server\PlayersCount
		gameInfo\breachrolecount[PlayerOptimize[a]\BreachType] = gameInfo\breachrolecount[PlayerOptimize[a]\BreachType]+1
	Next
End Function

Function breach_GetRoleCount(CONST_MODEL)
	Local count
	For i = 1 To Server\PlayersCount
		If PlayerOptimize[i]\BreachType = CONST_MODEL Then count = count + 1
	Next
	Return count
End Function

Function breach_GetCategoryCount(CATEGORY)
	Local count
	For i = 1 To Server\PlayersCount
		If breach_GetCategoryByType(PlayerOptimize[i]\BreachType, True) = CATEGORY Then count = count + 1
	Next
	Return count
End Function
Function breach_GetCategoryByType(typeid, raise = False)
	For brT.breachtypes = Each breachtypes
		If brt\constid = typeid Then
			If (typeid = NTF_MODEL Or typeid = HAOS_MODEL Or typeid = MODEL_ZOMBIE) And (Not raise) Then Return CATEGORY_NULL
			Return brt\categoryimportance
		EndIf
	Next
End Function

Function breach_countlatestcategory()
	Local latestroles = 0
	For i = 1 To 7
		If breach_GetCategoryCount(i) <> 0 Then
			latestroles = i
			For a = 1 To 7
				If a <> i Then
					If breach_GetCategoryCount(a) <> 0 Then
						latestroles = 0
						Exit
					EndIf
				EndIf
			Next
			If latestroles = 0 Then Exit
		EndIf
	Next
	Return latestroles
End Function
Function breach_IsStarted()
	Return (gameInfo\b\LobbyTimer < MilliSecs() And gameInfo\b\LobbyTimer <> 0)
End Function
Function breach_givetickets(category, tickets#)
	Select category
		Case 0
			gameInfo\b\MTFTickets = gameInfo\b\MTFTickets+tickets
		Case 1
			gameInfo\b\ChaosTickets = gameInfo\b\ChaosTickets+tickets
	End Select
End Function
Function breach_CheckMatchOver%() ; Returns who wins
	Local MTFUnits%, Guards%, Scientists%, SCPs%, Chaoses, ClassDs%

	For i = 1 To Server\PlayersCount
		If multiplayer_breach_IsA035(PlayerOptimize[i]\BreachType) Or mp_IsASCP(PlayerOptimize[i]\BreachType) Then 
			SCPs = SCPs + 1
		Else
			BT.BreachTypes = GetBreachType(PlayerOptimize[i]\BreachType)
			Select BT\CopiedGroup
				Case NTF_MODEL: MTFUnits = MTFUnits+1
				Case GUARD_MODEL: Guards = Guards+1
				Case JANITOR_MODEL,SCIENTIST_MODEL,WORKER_MODEL,MODEL_CLERK: Scientists = Scientists + 1 ;newcode
				Case NTF_MODEL: MTFUnits = MTFUnits+1
				Case HAOS_MODEL: Chaoses = Chaoses+1
				Case CLASSD_MODEL: ClassDs = ClassDs + 1
			End Select
		EndIf
	Next

	If MTFUnits > 0 Or Guards > 0 Or Scientists > 0 Then
		If SCPs > 0 Or Chaoses > 0 Or ClassDs > 0 Then
			Return 0
		ElseIf gameInfo\b\CuffedClassDEscaped > 0 Or gameInfo\b\ScientistsEscaped > 0 Then
			Return CATEGORY_SECURITY
		Else
			Return CATEGORY_STALEMATE
		EndIf
	Else
		If SCPs > 0 Then
			If ClassDs > 0 Then 
				Return 0
			Else
				If gameInfo\b\ClassDEscaped > 0 Or gameInfo\b\CuffedScientistsEscaped > 0 Then
					Return CATEGORY_CHAOS
				Else
					Return CATEGORY_ANOMALY
				EndIf
			EndIf
		Else
			If gameInfo\b\ClassDEscaped > 0 Or gameInfo\b\CuffedScientistsEscaped > 0 Then
				Return CATEGORY_CHAOS
			Else
				Return CATEGORY_ANOMALY
			EndIf
		EndIf
	EndIf
End Function


Function breach_Update()
	If Server\Breach = True Then
		
		NoTarget = True
		MTFtimer = 24000
		Local latestroles
		
		If Server\PlayersCount < 4 And gameInfo\b\TimeoutBreach > MilliSecs() Then
			gameInfo\b\BreachTimer = MilliSecs()+Server\BreachTime+90000
			gameInfo\b\TimeoutBreach = MilliSecs()+90000
			gameInfo\b\LobbyTimer = MilliSecs()+90000
		EndIf
		
		If Server\GlobalBlink < MilliSecs() Then
			If IsTimedout(5, 10) Then
				For p.players = Each players
					If p\BreachType = MODEL_173 Then
						For p2.players = Each players
							If (Not mp_IsAFriend(p\BreachType, p2\BreachType)) And EntityDistance(p\Pivot, p2\Pivot) < 15 And p2\BreachType <> 0 Then
								mp_sendblinktimer(p2\ID, -6)
							EndIf
						Next
					EndIf
				Next
			EndIf
			If Not(Server\GlobalBlink > MilliSecs()-Server\CurrentGlobalBlink) Then Server\GlobalBlink = MilliSecs()+Server\CurrentGlobalBlinkInterval
		EndIf
		
		If Server\PrevStart = False Then
			If ShouldStartRound Or (Server\PlayersCount >= Server\minplayerstostart) Then
				If Server\PlayersCount >= 4 Then
					gameInfo\b\BreachTimer = MilliSecs()+Server\BreachTime
					gameInfo\b\TimeoutBreach = MilliSecs()-1
					gameInfo\b\LobbyTimer = MilliSecs()-1
				EndIf
				ShouldStartRound = False
			EndIf
		EndIf
		
		If breach_IsStarted() Then

			If Server\PrevStart = False Then
			
				gameInfo\b\BreachTimer = MilliSecs()+Server\BreachTime
				gameInfo\b\TimeoutBreach = MilliSecs()-1
				gameInfo\b\LobbyTimer = MilliSecs()-1
				
				gameInfo\b\ExplodeTimeout = MilliSecs()+(Server\BreachTime/2)
				;gameinfo\b\ExplodeTimeout = MilliSecs()+60000
				latestroles = True
				
				Local playermassive[MAX_PLAYERS]
				Local playerscount = Server\PlayersCount
				
				For i = 1 To Server\PlayersCount
					playermassive[i-1] = PlayerOptimize[i]\ID
				Next
				
				shuffleplayersarray(playermassive, Server\PlayersCount)
				
				For i = 0 To playerscount-1
					If Server\deathmatchround Then
						SetPlayerType(playermassive[i], RandomBetween(GUARD_MODEL, HAOS_MODEL))
					Else
						breach_CountRoles()
						Local AllowedRoles[MAX_BREACH_ROLES]
						Local AllRoles = 0
						Local ctype% = 0
						
						Local AllowThisRole% = False
						For br.breachtypes = Each breachtypes
							If br\AllowToGenerate Then
								AllowThisRole = (br\maxrolecount > breach_GetRoleCount(br\constid))

								If AllowThisRole Then
									ctype = breach_GetCategoryByType(br\constid)
									
									Select ctype
										Case CATEGORY_ANOMALY
											If breach_GetCategoryCount(ctype) > Server\PlayersCount/4 Or breach_GetCategoryCount(br\constid) > min(Server\PlayersCount/5, 6) Or (br\constid = MODEL_860 And room860event = Null) Then AllowThisRole = False
										Case CATEGORY_NTF
											If breach_GetCategoryCount(ctype) > Server\PlayersCount/2 Then
												AllowThisRole = False
											EndIf
									End Select
									If AllowThisRole Then
										AllowedRoles[AllRoles] = br\constid
										AllRoles = AllRoles+1
									EndIf
								EndIf
							EndIf
						Next
						
						If AllRoles > 0 Then 
							SetPlayerType(playermassive[i], AllowedRoles[Rand(0,AllRoles-1)])
						Else
							SetPlayerType(playermassive[i], CLASSD_MODEL)
						EndIf
					EndIf
				Next

				latestroles = 0
				
				Server\PrevStart = True
				
				For e.Events = Each Events
					If e\EventName = "gateaentrance" Then
						e\EventState3 = 1
						e\room\RoomDoors[1]\open = False
						e\room\RoomDoors[1]\locked = False
					ElseIf e\EventName = "exit1" Then
						e\EventState3 = 1
						e\room\RoomDoors[4]\open = False
						e\room\RoomDoors[4]\locked = False
					EndIf
				Next
				
				For e.Events = Each Events
					If e\EventName = "checkpoint" Then
						e\room\RoomDoors[1]\open = False
						e\room\RoomDoors[0]\open = False
					EndIf
				Next
				
				RemoteDoorOn = True
				
				For r.Rooms = Each Rooms
					If r\RoomTemplate\Name = "exit1" Then
						r\RoomDoors[3]\open = True
						Exit
					EndIf
				Next
				
				For it.Items = Each Items
					If it\picker <> 0 Then RemoveItem(it)
				Next
				
				gameInfo\b\NTFTimeout = MilliSecs()+Rand(300000, 400000)
				
				CreateEvent("alarm", "start", 0)
				
				If GetScripts() Then
					public_inqueue(public_OnRoundStarted)
					callback
				EndIf
				
				breach_givetickets(0, 24)
				breach_givetickets(1, 18)
				
				For r.rooms = Each rooms
					If r\roomtemplate\name = "start" Then
						r\RoomDoors[2]\open=True
						
						While r\RoomDoors[1]\openstate < 180
							r\RoomDoors[1]\openstate = Min(180, r\RoomDoors[1]\openstate + 0.8)
							MoveEntity(r\RoomDoors[1]\obj, Sin(r\RoomDoors[1]\openstate) / 180.0, 0, 0)
							MoveEntity(r\RoomDoors[1]\obj2, -Sin(r\RoomDoors[1]\openstate) / 180.0, 0, 0)
						Wend
						Exit
					EndIf
				Next
			EndIf
			; ========================================= ROLES REFRESH
			Local count, survivecount
			For i = 1 To Server\PlayersCount
				If PlayerOptimize[i]\BreachType = 0 Then count = count + 1
				If PlayerOptimize[i]\BreachType <> 0 Then survivecount = survivecount + 1
			Next
		
			
			If Server\deathmatchround And (Not gameInfo\b\Exploded) Then
				For p.players = Each players
					If ReadBool(p\p_byte, 4) Or p\BreachType = 0 Then SetPlayerType(p\ID, RandomBetween(HAOS_MODEL, GUARD_MODEL))
				Next
			EndIf
			
			For e.events = Each events
				Select e\eventconst
					Case e_gatea
						For p.players = Each players
							If p\BreachType <> 0 Then
								If Abs(p\y+0.32-EntityY(e\room\Objects[11],True))<1.0 Then
									If Distance(p\x,p\z,EntityX(e\room\Objects[11],True),EntityZ(e\room\Objects[11],True)) < 10.0 And p\BreachType <> NTF_MODEL And p\BreachType <> GUARD_MODEL And p\BreachType <> HAOS_MODEL And (Not MP_IsASCP(p\BreachType)) And (Not multiplayer_breach_IsA035(p\BreachType)) Then
										shouldgiverole% = False
										If p\BreachType = CLASSD_MODEL Then 
											breach_givetickets(1, 1)
											shouldgiverole = True
											gameInfo\b\ClassDEscaped = gameInfo\b\ClassDEscaped + 1
										EndIf
										If (p\BreachType = SCIENTIST_MODEL Or p\BreachType = MODEL_CLERK Or p\BreachType = JANITOR_MODEL Or p\BreachType = WORKER_MODEL) And p\HandCuffed Then
											breach_givetickets(1, 2)
											shouldgiverole = True
											gameInfo\b\CuffedScientistsEscaped = gameInfo\b\CuffedScientistsEscaped+1
										EndIf
										If shouldgiverole Then 
											SetPlayerType(p\ID, HAOS_MODEL)
											If GetScripts() Then
												public_inqueue(public_OnPlayerEscape)
												public_addparam(0, p\ID)
												public_addparam(0, p\breachtype)
												public_addparam(0, p\prevbreachtype)
												callback
											EndIf
										Else
											SetPlayerType(p\ID, 0)
											If GetScripts() Then
												public_inqueue(public_OnPlayerEscapeButDead)
												public_addparam(0, p\ID)
												public_addparam(0, p\breachtype)
												public_addparam(0, p\prevbreachtype)
												callback
											EndIf
										EndIf
									EndIf
								EndIf
							EndIf
						Next
					Case e_exit1
						For p.players = Each players
							If p\BreachType <> 0 Then
								If EntityDistance(e\room\Objects[27], p\Pivot) < 4 Then
									If p\BreachType <> NTF_MODEL And p\BreachType <> GUARD_MODEL And p\BreachType <> HAOS_MODEL And (Not MP_IsASCP(p\BreachType)) And (Not multiplayer_breach_IsA035(p\BreachType)) Then
										shouldgiverole% = False
										If p\BreachType = SCIENTIST_MODEL Or p\BreachType = MODEL_CLERK Or p\BreachType = JANITOR_MODEL Or p\BreachType = WORKER_MODEL Then 
											breach_givetickets(0, 1)
											shouldgiverole% = True
											gameInfo\b\ScientistsEscaped = gameInfo\b\ScientistsEscaped+1
										EndIf
										If p\BreachType = CLASSD_MODEL And p\HandCuffed Then 
											breach_givetickets(0, 1)
											shouldgiverole% = True
											gameInfo\b\CuffedClassDEscaped = gameInfo\b\CuffedClassDEscaped+1
										EndIf
										If shouldgiverole Then
											SetPlayerType(p\ID, NTF_MODEL)
											If GetScripts() Then
												public_inqueue(public_OnPlayerEscape)
												public_addparam(0, p\ID)
												public_addparam(0, p\breachtype)
												public_addparam(0, p\prevbreachtype)
												callback
											EndIf
										Else
											SetPlayerType(p\ID, 0)
											If GetScripts() Then
												public_inqueue(public_OnPlayerEscapeButDead)
												public_addparam(0, p\ID)
												public_addparam(0, p\breachtype)
												public_addparam(0, p\prevbreachtype)
												callback
											EndIf
										EndIf
									EndIf
								EndIf
							EndIf
						Next
				End Select
			Next
			
			breach_CountRoles()
			; ====================================
			
			latestroles = breach_CheckMatchOver()
			
			If MilliSecs() > gameInfo\b\BreachTimer Then gameInfo\b\BreachTimer = 0
			If gameInfo\b\BreachTimer <> 0 Then
				If gameInfo\b\NTFTimeout < MilliSecs() Then
					If count > 0 Then
						SpawnMTF()
					Else
						gameInfo\b\NTFTimeout = MilliSecs()+5000
					EndIf
				EndIf
			EndIf
			
			If gameInfo\b\CurrentWarheadText = "Warheads" Then
				If gameInfo\b\Exploded > 0 Then
					For e2.Events = Each Events
						If e2\EventConst = e_room2nuke Then
							If Not e2\eventstate Then 
								DeactivateWarheads()
								gameInfo\b\ExplodeTimeout = MilliSecs()+120000
							EndIf
							Exit
						EndIf
					Next
				EndIf
			EndIf
			If Server\PlayersCount <> 0 Then
				If (Not gameInfo\b\Exploded) Then
					If gameInfo\b\TimeoutBreach < MilliSecs() Then
						If MilliSecs()+20000 > gameInfo\b\BreachTimer Then
							gameInfo\b\Exploded = 1
						EndIf
						
						If latestroles <> 0 Then gameInfo\b\Exploded = True
						
						If gameInfo\b\Exploded = 1 Then
							ActivateWarheads("", latestroles)
						EndIf
					EndIf
				Else
					If MilliSecs() > gameInfo\b\BreachTimer-15000 And gameInfo\b\Exploded = 1 Then
						If GetScripts() Then
							public_inqueue(public_OnWarheadsExplosion)
							callback
						EndIf
						playerzone = 0
						For i = 1 To Server\PlayersCount
							playerzone = GetPlayerZone(PlayerOptimize[i]\ID)
							If playerzone > 0 And playerzone < 4 And (Not (PlayerOptimize[i]\PlayerRoomName = "exit1" And EntityY(PlayerOptimize[i]\Pivot) > 1040.0*RoomScale)) Then
								udp_WriteByte(M_EXPL)
								udp_WriteByte(1)
								udp_WriteInt(1)
								udp_SendMessage(PlayerOptimize[i]\ID)
							EndIf
						Next
						gameInfo\b\Exploded = 2
					EndIf
					If MilliSecs() > gameInfo\b\BreachTimer Then
						Server\deathmatchround = False
						RestartServer()
						If Server\LobbyDisabled Then Server\IsStarted = True
					EndIf
				EndIf
			EndIf
		EndIf
	Else
		If Server\PrevStart = False And Server\IsStarted Then
			For ReceivePlayer.Players = Each players
				If ReceivePlayer\ID <> 0 Then
					If (Not Server\IntroEnabled) And (Not Server\Breach) Then
						For r.rooms = Each rooms
							If r\roomtemplate\name = "start" Then
								ReceivePlayer\x = EntityX(r\obj)+3584*RoomScale
								ReceivePlayer\y = (704*RoomScale)
								ReceivePlayer\z = EntityZ(r\obj)+1024*RoomScale
								ReceivePlayer\yaw = 130.3
								ReceivePlayer\playerroomid = r\ID
								ReceivePlayer\player_move = 11
								
								mp_UpdatePlayerPosition(ReceivePlayer)
								mp_SetRoomNameToPlayer(ReceivePlayer)
								
								ReceivePlayer\SavePosX = EntityX(ReceivePlayer\Pivot)
								ReceivePlayer\SavePosY = EntityY(ReceivePlayer\Pivot)
								ReceivePlayer\SavePosZ = EntityZ(ReceivePlayer\Pivot)
								ReceivePlayer\SaveRoom = ReceivePlayer\PlayerRoomID
								Exit
							EndIf
						Next
					Else
						For r.rooms = Each rooms
							If r\roomtemplate\name = "173" Then
								If Server\Breach Then
									ReceivePlayer\x = EntityX(r\Objects[5], True)
									ReceivePlayer\y = 2.0
									ReceivePlayer\z = EntityZ(r\Objects[5], True)
								Else
									ReceivePlayer\x = EntityX(r\obj)
									ReceivePlayer\y = 1.0
									ReceivePlayer\z = EntityZ(r\obj)
								EndIf
								ReceivePlayer\yaw = 130.3
								ReceivePlayer\playerroomid = r\ID
								ReceivePlayer\player_move = 11
								
								mp_UpdatePlayerPosition(ReceivePlayer)
								mp_SetRoomNameToPlayer(ReceivePlayer)
								
								ReceivePlayer\SavePosX = EntityX(ReceivePlayer\Pivot)
								ReceivePlayer\SavePosY = EntityY(ReceivePlayer\Pivot)
								ReceivePlayer\SavePosZ = EntityZ(ReceivePlayer\Pivot)
								ReceivePlayer\SaveRoom = ReceivePlayer\PlayerRoomID
								Exit
							EndIf
						Next
					EndIf
				EndIf
			Next
			Server\PrevStart = True
		EndIf
	EndIf
End Function
Function mp_sendblinktimer(id, Timer#)
	udp_WriteByte(M_GLOBALBLINK)
	udp_WriteByte(1)
	udp_WriteFloat(Timer)
	udp_SendMessage(id)
	
	Player[id]\BlinkTimer = Timer
End Function

Function mp_updateplayerrole(i)
	If i < 1 Then Return

	Player[i]\p_byte = ReadBool(Player[i]\p_byte, 0) + (2 * ReadBool(Player[i]\p_byte, 1)) + (4 * ReadBool(Player[i]\p_byte, 2)) + (8 * ReadBool(Player[i]\p_byte, 3)) + (16 * ReadBool(Player[i]\p_byte, 4)) + (32 * ReadBool(Player[i]\p_byte, 5)) + (64 * Player[i]\Announcement)

	Player[i]\wearingdir = ReadBool(Player[i]\wearingdir, 0) + (2*ReadBool(Player[i]\wearingdir, 1)) + (4*ReadBool(Player[i]\wearingdir, 2)) + (8*ReadBool(Player[i]\wearingdir, 3)) + (16*ReadBool(Player[i]\wearingdir, 4)) + (32*Player[i]\HandCuffed) + (64*Player[i]\MicroHIDSHOOT) + (128*ReadBool(Player[i]\wearingdir, 7))
					
	If Player[i]\prevbreachtype <> Player[i]\BreachType Then
	
		CanSpawnBody = True
		If Player[i]\BreachType = MODEL_ZOMBIE And Player[i]\prevbreachtype <> MODEL_ZOMBIE Then CanSpawnBody = False
		If Player[i]\BreachType = MODEL_035 And Player[i]\prevbreachtype <> MODEL_035 Then CanSpawnBody = False
		If CanSpawnBody Then CreateRoleCorpse(Player[i], Player[i]\prevbreachtype)

		Player[i]\CanShoot = 0
		
		EntityType Player[i]\Pivot, HIT_PLAYER
		EntityType Player[i]\AnticheatPivot, HIT_PLAYER
		Player[i]\HandCuffed = False
		
		For it.Items = Each Items
			If it\picker = i And it\picker <> 0 Then
				If mp_IsASCP(Player[i]\prevbreachtype) Then 
					RemoveItem(it)
				Else
					PlayerDropItem(it)
				EndIf
			EndIf
		Next
		
		
		For br.breachtypes = Each breachtypes
			If br\constid = Player[i]\BreachType Then
				Local citem = True, itemname$
				For AAA = 0 To 9
					If br\SpawnItem[AAA] <> "null" And br\SpawnItem[AAA] <> "" Then
						itemname = Piece(br\SpawnItem[AAA], 1, ",")
						itemname$ = Piece(itemname, 1, "/")+"/"+Piece(itemname, 2, "/")
						citem = True
						If Instr(Piece(br\SpawnItem[AAA], 2, ","), "if") Then
							Local cmd$ = Lower(Piece(br\SpawnItem[AAA], 2, ","))
							If Piece(cmd, 1) = "if" Then
								Local rolename$ = Piece(cmd, 2), roleid = 0
								For br2.breachtypes = Each breachtypes
									If Lower(br2\name) = rolename Then
										roleid = br2\constid
										Exit
									EndIf
								Next
								If roleid <> 0 Then
									Local keyword$ = Piece(cmd, 3)
									Select keyword
										Case "exists"
											citem = False
											For p.players = Each players
												If p\breachtype = roleid Then
													citem = True
													Exit
												EndIf
											Next
										Case "notexists"
											citem = True
											For p.players = Each players
												If p\breachtype = roleid Then
													citem = False
													Exit
												EndIf
											Next
									End Select
								Else
									Select rolename
										Case "random"
											If Rand(0, 2) = 1 Then itemname = Right(cmd, Len(cmd)-10)
									End Select
								EndIf
							EndIf
						EndIf
						If citem = True Then 
							GiveItem(Piece(itemname, 1, "/"), Piece(itemname, 2, "/"), i)
							DebugLog "gived: "+Piece(itemname, 1, "/")
						EndIf
					EndIf
				Next
				Exit
			EndIf
		Next
		
		If GetScripts() Then
			public_inqueue(public_OnPlayerGetNewRole)
			public_addparam(0, i)
			public_addparam(0, Player[i]\prevbreachtype)
			public_addparam(0, Player[i]\BreachType)
			callback
		EndIf
		
		If Server\Breach Then
			If Player[i]\BreachType = 0 And Player[i]\Health < 50 Then
				If mp_IsASCP(Player[i]\prevbreachtype) Or multiplayer_breach_IsA035(Player[i]\prevbreachtype) Then
					breach_CountRoles()
					;addlog "roles: "+breach_GetRoleCount(NTF_MODEL)
					If breach_GetRoleCount(NTF_MODEL) > 0 Then
						Local SCPCount% = 0
						For A = 1 To Server\PlayersCount
							SCPCount% = SCPCount + (mp_IsASCP(PlayerOptimize[A]\BreachType) Or multiplayer_breach_IsA035(PlayerOptimize[A]\BreachType))
						Next
						
						If GetScripts() Then
							public_inqueue(public_OnPlayerSCPContained)
							public_addparam(0, i)
							public_addparam(0, Player[i]\prevbreachtype)
							public_addparam(0, Player[i]\BreachType)
							callback
						EndIf
						
						Local FileName$
						
						If SE_GetReturnValue() <> -1 Then FileName = SE_GetReturnValue()
						
						If FileName = "" Then
							If SCPCount > 0 Then
								Select Player[i]\prevbreachtype
									Case MODEL_049: PlaySoundForAll("SFX\Character\MTF\Announc049Contain.ogg")
									Case MODEL_173: PlaySoundForAll("SFX\Character\MTF\Announc173Contain.ogg")
									Case MODEL_096: PlaySoundForAll("SFX\Character\MTF\Announc096Contain.ogg")
									Case MODEL_106: PlaySoundForAll("SFX\Character\MTF\Announc106Contain.ogg")
									Case MODEL_966: PlaySoundForAll("SFX\Character\MTF\Announc966Contain.ogg")
									Case MODEL_939: PlaySoundForAll("SFX\Character\MTF\Announc939Contain.ogg")
								End Select
							Else
								PlaySoundForAll("SFX\Character\MTF\AnnouncAllContain.ogg")
							EndIf
						ElseIf FileName <> "0" Then
							PlaySoundForAll(FileName)
						EndIf
					EndIf
				EndIf
			EndIf
		EndIf
		
		Player[i]\MaxHealth = 0
		Player[i]\prevbreachtype = Player[i]\BreachType

		EntityRadius Player[i]\Pivot, 0.15, 0.3
		
		For br.breachtypes = Each breachtypes
			If br\constid = Player[i]\BreachType Then
				ScaleEntity Player[i]\Hitbox, br\hitboxx, br\hitboxy, br\hitboxz
				
				Player[i]\Health = 0
				GivePlayerHealth(i, br\consthp)
				
				Player[i]\SpeedMult = br\speedmult
				Exit
			EndIf
		Next
		
		ResetPlayerSize(i)
		Player[i]\AC_ShouldSpeed = 1.0
		
		teleported = False

		br.BreachTypes = GetBreachType(Player[i]\BreachType)
			
		Select br\CopiedSpawn
			Case MODEL_096
				For r.Rooms = Each Rooms
					If r\RoomTemplate\Name = "room2servers" Then
						PositionEntity (Player[i]\Pivot, EntityX(r\obj), EntityY(r\obj)+0.7, EntityZ(r\obj))
						ResetEntity(Player[i]\Pivot)
						mp_SetPlayerRoomID(Player[i], r)
						teleported = True
						Exit
					EndIf
				Next
				Player[i]\AC_ShouldSpeed = 3.0
				
			Case MODEL_860
				For e.Events = Each Events
					If e\EventConst = e_room860 Then
						Local fr.Forest = e\room\fr
						If fr <> Null Then
							Local rnddoor% = Rand(0, 1)
							PositionEntity Player[i]\Pivot,EntityX(fr\Door[rnddoor],True),EntityY(fr\Door[rnddoor],True)+1.5,EntityZ(fr\Door[rnddoor],True),True
							RotateEntity Player[i]\Pivot, 0.0, EntityYaw(fr\Door[rnddoor],True)-180, 0.0, True
							MoveEntity Player[i]\Pivot, -0.5,0.0,0.5
							ResetEntity(Player[i]\Pivot)
							mp_SetPlayerRoomID(Player[i], e\room)
							teleported = True
							Player[i]\ForestYaw = Not rnddoor
							
							
						EndIf
						Exit
					EndIf
				Next
			Case MODEL_ZOMBIE,MODEL_035
				teleported = True
			Case MODEL_939
				For r.Rooms = Each Rooms
					If r\RoomTemplate\Name = "room3storage" Then
						PositionEntity (Player[i]\Pivot, EntityX(r\Objects[4],True),EntityY(r\Objects[4],True)+0.2,EntityZ(r\Objects[4],True))
						ResetEntity(Player[i]\Pivot)
						mp_SetPlayerRoomID(Player[i], r)
						teleported = True
						Exit
					EndIf
				Next
			Case NTF_MODEL
				For r.Rooms = Each Rooms
					If r\RoomTemplate\Name = "exit1" Then
						PositionEntity (Player[i]\Pivot, EntityX(r\Objects[26],True), EntityY(r\Objects[26],True)+1, EntityZ(r\Objects[26],True))
						ResetEntity(Player[i]\Pivot)
						mp_SetPlayerRoomID(Player[i], r)
						teleported = True
						Exit
					EndIf
				Next
			Case MODEL_049
				For r.Rooms = Each Rooms
					If r\RoomTemplate\Name = "room049" Then
						PositionEntity (Player[i]\Pivot, EntityX(r\obj), EntityY(r\obj)+0.7, EntityZ(r\obj))
						ResetEntity(Player[i]\Pivot)
						mp_SetPlayerRoomID(Player[i], r)
						teleported = True
						Exit 
					EndIf
				Next
			Case MODEL_173
				For r.Rooms = Each Rooms
					If r\RoomTemplate\Name = "room2closets" Then
						PositionEntity (Player[i]\Pivot, EntityX(r\obj), EntityY(r\obj)+0.7, EntityZ(r\obj))
						ResetEntity(Player[i]\Pivot)
						mp_SetPlayerRoomID(Player[i], r)
						teleported = True
						Exit
					EndIf
				Next
				Player[i]\AC_ShouldSpeed = 5.0
			Case GUARD_MODEL
				For r.Rooms = Each Rooms
					If r\RoomTemplate\Name = "gateaentrance" Then
						PositionEntity (Player[i]\Pivot, EntityX(r\obj), EntityY(r\obj)+0.7, EntityZ(r\obj))
						ResetEntity(Player[i]\Pivot)
						mp_SetPlayerRoomID(Player[i], r)
						teleported = True
						Exit
					EndIf
				Next
			Case HAOS_MODEL
				For r.Rooms = Each Rooms
					If r\RoomTemplate\Name = "gatea" Then
						PositionEntity (Player[i]\Pivot, EntityX(r\Objects[27],True),EntityY(r\Objects[27],True)+1,EntityZ(r\Objects[27],True))
						ResetEntity(Player[i]\Pivot)
						mp_SetPlayerRoomID(Player[i], r)
						teleported = True
						Exit
					EndIf
				Next
			Case CLASSD_MODEL
				For r.rooms = Each rooms
					If r\Roomtemplate\name = "start" Then
						PositionEntity (Player[i]\Pivot, EntityX(r\obj)+3584*RoomScale, 704*RoomScale, EntityZ(r\obj)+1024*RoomScale)
						ResetEntity Player[i]\Pivot
						mp_SetPlayerRoomID(Player[i], r)
						teleported = True
						Exit
					EndIf
				Next
			Case WORKER_MODEl,JANITOR_MODEL,SCIENTIST_MODEL,MODEL_CLERK ;newcode
				For r.Rooms = Each Rooms
					If r\RoomTemplate\Name = "room3storage" Then
						PositionEntity (Player[i]\Pivot, EntityX(r\obj), EntityY(r\obj)+0.7, EntityZ(r\obj))
						ResetEntity(Player[i]\Pivot)
						mp_SetPlayerRoomID(Player[i], r)
						teleported = True
						Exit
					EndIf
				Next
			Case MODEL_966
				For r.rooms = Each rooms
					If r\roomtemplate\name = "room966" Then
						PositionEntity Player[i]\Pivot, EntityX(r\Objects[2],True), EntityY(r\Objects[2],True), EntityZ(r\Objects[2],True)
						ResetEntity(Player[i]\Pivot)
						mp_SetPlayerRoomID(Player[i], r)
						teleported = True
						Exit
					EndIf
				Next
			Case MODEL_106
				teleported = False
				For r.rooms = Each rooms
					If r\roomtemplate\name = "room106" Then
						PositionEntity Player[i]\Pivot, EntityX(r\Objects[10],True),0.4,EntityZ(r\Objects[10],True)
						ResetEntity(Player[i]\Pivot)
						mp_SetPlayerRoomID(Player[i], r)
						teleported = True
						Exit
					EndIf
				Next
				EntityType Player[i]\Pivot, HIT_OLDMAN
				EntityType Player[i]\AnticheatPivot, HIT_OLDMAN
		End Select
		If Not teleported Then
			If br\CopiedSpawn = SCIENTIST_MODEL Or br\CopiedSpawn = WORKER_MODEL Or br\CopiedSpawn = JANITOR_MODEL Or br\CopiedSpawn = MODEL_CLERK Then ;newcode
				For r.rooms = Each rooms
					If r\Roomtemplate\name = "start" Then
						PositionEntity (Player[i]\Pivot, EntityX(r\obj)+3584*RoomScale, 704*RoomScale, EntityZ(r\obj)+1024*RoomScale)
						ResetEntity Player[i]\Pivot
						mp_SetPlayerRoomID(Player[i], r)
						teleported = True
						Exit
					EndIf
				Next
			Else
			
				If (mp_IsASCP(br\CopiedSpawn) Or br\CopiedSpawn = HAOS_MODEL Or br\CopiedSpawn = NTF_MODEL Or br\CopiedSpawn = GUARD_MODEL) Then
					For r.rooms = Each rooms
						If r\roomtemplate\name = "room2nuke" Then
							PositionEntity Player[i]\Pivot, EntityX(r\obj), EntityY(r\obj)+0.7, EntityZ(r\obj)
							ResetEntity(Player[i]\Pivot)
							mp_SetPlayerRoomID(Player[i], r)
							teleported = True
							Exit
						EndIf
					Next
				EndIf
			EndIf
		EndIf
		
		If br\CopiedSpawn = MODEL_WAIT Then
			If Server\Breach And gameinfo\b\LobbyTimer > MilliSecs() Then
				For r.rooms = Each rooms
					If r\RoomTemplate\Name = "173" Then
						PositionEntity Player[i]\Pivot, EntityX(r\Objects[5], True), 1.0, EntityZ(r\Objects[5], True)
						ResetEntity Player[i]\Pivot
						mp_SetPlayerRoomID(Player[i], r)
						Exit
					EndIf
				Next
			EndIf
		EndIf

		SetPlayerPosition(i, Room[Player[i]\PlayerRoomID]\RoomTemplate\Name, EntityX(Player[i]\Pivot),EntityY(Player[i]\Pivot)+0.3, EntityZ(Player[i]\Pivot))
		
		For spawnpointer.SpawnPoint = Each SpawnPoint
			If spawnpointer\role > 0 Then
				If Player[i]\BreachType = spawnpointer\role Then
					SetPlayerPosition(i, SpawnPointer\room, SpawnPointer\x, SpawnPointer\y, SpawnPointer\z)
					Exit
				EndIf
			EndIf
		Next
		
		For spawnpointer.SpawnPoint = Each SpawnPoint
			If spawnpointer\playerid = i Then
				SetPlayerPosition(i, SpawnPointer\room, SpawnPointer\x, SpawnPointer\y, SpawnPointer\z)
				Exit
			EndIf
		Next
	EndIf
End Function
Function GetPlayerDistances#(p1, p2)
	Return EntityDistance(Player[p1]\obj, Player[p2]\obj)
End Function

Function Distance#(x1#, y1#, x2#, y2#)
	Return ((x2-x1)^2 + (y2-y1)^2)^0.5
End Function
Function kill(killmsg$ = "", forcekill% = False)

End Function

Function SendPlayerMsg(playerid, msgc$, del)
	If Player[playerid] = Null Then Return
	udp_WriteByte M_MSG
	udp_WriteByte playerid
	udp_WriteLine(msgc)
	udp_WriteInt(del)
	udp_SendMessage(playerid)
End Function
Function SendConsoleCommand(playerid, consolepointer$, messagetick=False)
	If Player[playerid] = Null Then Return
	udp_WriteByte M_CONSOLE
	udp_WriteByte playerid
	udp_WriteLine(consolepointer)
	udp_WriteByte(messagetick)
	udp_SendMessage(playerid)
End Function
Function PlaySoundForPlayer(playerid, filename$)
	If Player[playerid] = Null Then Return
	udp_WriteByte M_SOUND
	udp_WriteByte playerid
	udp_WriteLine(filename)
	udp_SendMessage(playerid)
End Function
Function PlaySoundForAll(filename$)
	For i = 1 To Server\PlayersCount
		PlaySoundForPlayer(PlayerOptimize[i]\ID, filename)
	Next
End Function
		
Function udp_clearbuff(stream)
	SendUDPMsg(stream, HostIP(CountHostIPs("localhost")), 0)
End Function
Function CreateSound(filename$, x#, y#, z#, distance# = 10, volume# = 1.0)
	For i = 1 To Server\PlayersCount
		udp_WriteByte M_SOUND3D
		udp_WriteByte PlayerOptimize[i]\ID
		udp_WriteLine(filename)
		udp_WriteFloat x
		udp_WriteFloat y
		udp_WriteFloat z
		udp_WriteFloat distance
		udp_WriteFloat volume
		udp_SendMessage(PlayerOptimize[i]\ID)
	Next
End Function


Function Shoot2(x#, y#, z#, pitch#, yaw#, gun=GUN_USP)
	CreateBullet(0, BULLET_SPEED, x, y, z, pitch,yaw, gun)
	For i = 1 To Server\PlayersCount
		If Distance3(x, y, z, EntityX(PlayerOptimize[i]\Pivot), EntityY(PlayerOptimize[i]\Pivot), EntityZ(PlayerOptimize[i]\Pivot)) < 50 Then
			udp_WriteByte M_CREATEBULLET
			udp_WriteByte 0
			udp_WriteShort 0
			udp_WriteFloat x
			udp_WriteFloat y
			udp_WriteFloat z
			udp_WriteFloat pitch
			udp_WriteFloat yaw
			udp_WriteByte 0
			udp_SendMessage(PlayerOptimize[i]\ID)
		EndIf
	Next
End Function
Function SetPlayerFogRange(playerid, state#)
	If Player[playerid] = Null Then Return
	udp_WriteByte M_FOGRANGE
	udp_WriteByte 1
	udp_WriteFloat state
	udp_SendMessage playerid
End Function
Function updatefakeplayers()
	For p.players = Each players
		If p\Fake Then
			p\objectsTicks = p\objectsTicksContain
			p\chatTicks = ticks\chatTicks
			p\textsTicks = p\textsTicksContain
			p\drawsTicks = p\drawsTicksContain
		EndIf
	Next
End Function
Function player_var_setvalue(playerid, variable$, value$)
	;DebugLog "Set "+playerid+" varialble = "+variable+" value = "+value
	For i = 1 To MAX_PLAYER_VARIABLES-1
		If Player[playerid]\variables[i] <> Null Then
			If Player[playerid]\variables[i]\name = variable Then
				Player[playerid]\variables[i]\value = value
				Return
			EndIf
		EndIf
	Next
	i = player_var_getid(playerid)
	If i = 0 Then Return
	Player[playerid]\variables[i] = New playervariables
	Player[playerid]\variables[i]\name = variable
	Player[playerid]\variables[i]\value = value
End Function
Function player_var_remove(playerid, variable$)
	For i = 1 To MAX_PLAYER_VARIABLES-1
		If Player[playerid]\variables[i] <> Null Then
			If Player[playerid]\variables[i]\name = variable Then
				Delete Player[playerid]\variables[i]
				Return
			EndIf
		EndIf
	Next
End Function
Function player_var_getid(playerid)
	For i = 1 To MAX_PLAYER_VARIABLES-1
		If Player[playerid]\variables[i] = Null Then Return i
	Next
End Function
Function player_var_getvalue$(playerid, variable$)
	;DebugLog "Get "+playerid+" varialbe = "+variable
	For i = 1 To MAX_PLAYER_VARIABLES-1
		If Player[playerid]\variables[i] <> Null Then
			If Player[playerid]\variables[i]\name = variable Then
				;DebugLog "Return "+Player[playerid]\variables[i]\value
				Return Player[playerid]\variables[i]\value
			EndIf
		EndIf
	Next
End Function
Function var_setvalue(variable$, value$)
	For i = 1 To MAX_VARIABLES-1
		If s_variables[i] <> Null Then
			If s_variables[i]\name = variable Then
				s_variables[i]\value = value
				Return
			EndIf
		EndIf
	Next
	i = var_getid()
	If i = 0 Then Return
	s_variables[i] = New servervariables
	s_variables[i]\name = variable
	s_variables[i]\value = value
End Function
Function var_remove(variable$)
	For i = 1 To MAX_VARIABLES-1
		If s_variables[i] <> Null Then
			If s_variables[i]\name = variable Then
				Delete s_variables[i]
				Return
			EndIf
		EndIf
	Next
End Function
Function var_getid()
	For i = 1 To MAX_VARIABLES-1
		If s_variables[i] = Null Then Return i
	Next
End Function
Function var_getvalue$(variable$)
	;DebugLog "Get "+playerid+" varialbe = "+variable
	For i = 1 To MAX_VARIABLES-1
		If s_variables[i] <> Null Then
			If s_variables[i]\name = variable Then
				Return s_variables[i]\value
			EndIf
		EndIf
	Next
End Function
Function event_Remove(eventname$)
	Local ev.eventq = New eventq
	ev\eventname = eventname
	ev\remove = True
	;if e_BankSize(Server\EventBankPointer) > 32768 Then Return
	;e_PokeByte(Server\EventBankPointer, True)
	;e_PokeLine(Server\EventBankPointer, eventname)
	;e_PokeFloat(Server\EventBankPointer, 0)
	;e_PokeFloat(Server\EventBankPointer, 0)
	;e_PokeFloat(Server\EventBankPointer, 0)
	;e_PokeByte(Server\EventBankPointer, True)
End Function
Function event_SetState(eventname$, state#)
	Local ev.eventq = New eventq
	ev\eventname = eventname
	ev\state = state
	;if e_BankSize(Server\EventBankPointer) > 32768 Then Return
	;e_PokeByte(Server\EventBankPointer, True)
	;e_PokeLine(Server\EventBankPointer, eventname)
	;e_PokeFloat(Server\EventBankPointer, state)
	;e_PokeByte(Server\EventBankPointer, False)
End Function

Function mp_IsAFriend(myrole, role)
	Select myrole
		; ======= SCPS
		Case MODEL_173,MODEL_106,MODEL_939,MODEL_966,MODEL_049,MODEL_ZOMBIE,MODEL_096,MODEL_860,MODEL_035,0
			Select role
				Case MODEL_173,MODEL_106,MODEL_939,MODEL_966,MODEL_049,MODEL_ZOMBIE,MODEL_096,MODEL_860,MODEL_035,0
					Return True
				Default
					Return False
			End Select
		; ======= CLASS D
		Case CLASSD_MODEL,HAOS_MODEL
			Select role
				Case CLASSD_MODEL,HAOS_MODEL
					Return True
				Default
					Return False
			End Select
		; ======= SECURITY
		Case NTF_MODEL,GUARD_MODEL,JANITOR_MODEL,WORKER_MODEL,SCIENTIST_MODEL,MODEL_CLERK ;newcode
			Select role
				Case NTF_MODEL,GUARD_MODEL,JANITOR_MODEL,WORKER_MODEL,SCIENTIST_MODEL,MODEL_CLERK ;newcode
					Return True
				Default
					Return False
			End Select
		; ====== STAFF
		Case JANITOR_MODEL,WORKER_MODEL,SCIENTIST_MODEL,MODEL_CLERK ;newcode
			Select role
				Case JANITOR_MODEL,WORKER_MODEL,SCIENTIST_MODEL,MODEL_CLERK ;newcode
					Return True
				Default
					Return False
			End Select
	End Select
	Return False
End Function
Function Version$(v_hex)
	Return (v_hex Shr 16 And $FF)+"."+(v_hex Shr 8 And $FF)+"."+(v_hex Shr 0 And $FF)
End Function
Function SetTimer(scriptthread.SE_Script, public$, interval, loop, identifiers$, databank.bs, luastate=0)
	Local t.timers = New timers
	t\interval = interval
	t\identifiers = identifiers
	t\updater = MilliSecs()+interval
	t\paramsdata = databank
	t\loop = loop
	t\scriptthread = scriptthread
	
	If luastate = 0 Then
		t\timerfunc = SE_FindFunc(scriptthread, Lower(public))
	Else
		t\luastate = luastate
		t\luafunc = Lower(public)
	EndIf
	
	Return Handle(t)
End Function
Function UpdateTimers()
	For t.timers = Each timers
		If t\updater < MilliSecs() Then
			If t\luastate <> 0 Then
				For i = 1 To Len(t\identifiers)
				
					Select Mid(t\identifiers, i, 1)
						Case "i"
							public_addparam(0, ByteStreamReadInt(t\paramsdata), LUA_TNUMBER)
						Case "f"
							public_addparam(0, ByteStreamReadFloat(t\paramsdata), LUA_TNUMBER)
						Case "s"
							public_addparam(0, ByteStreamReadString(t\paramsdata), LUA_TSTRING)
					End Select
				Next
				public_update_by_func(Null, False, t\luastate, t\luafunc)
			Else
				For i = 1 To Len(t\identifiers)
				
					Select Mid(t\identifiers, i, 1)
						Case "i"
							public_addparam(0, ByteStreamReadInt(t\paramsdata), SE_INT)
						Case "f"
							public_addparam(0, ByteStreamReadFloat(t\paramsdata), SE_FLOAT)
						Case "s"
							public_addparam(0, ByteStreamReadString(t\paramsdata), SE_STRING)
					End Select
				Next
				public_update_by_func(t\timerfunc)
			EndIf
			public_clear()
			
			If t <> Null Then
				If t\loop Then
					t\updater = MilliSecs()+t\interval
					ByteStreamResetRead(t\paramsdata)
				Else
					RemoveTimer(t)
				EndIf
			EndIf
		EndIf
	Next
End Function
Function RemoveTimer(t.timers)
	RemoveByteStream(t\paramsdata)
	Delete t
End Function

;=============================================================================================
Include "Source Code\Blitz_File_FileName.bb"
Include "Source Code\Blitz_File_ZipApi.bb"

;link$      - The link. You may enter the link just like you enter it
;             in your browser. Very tolerant. No http:// required.
;savepath$  - The path where the file should be saved
;savefile$  - The filename of the saved file. When given "", it will
;             be named like the file in the link$.

Const DOWNLOAD_SIZE% = 4096*2

Function Download(link$, savepath$ = "", latest$="", port = 80)
;Strip protocol and return false if not "http"
	inst = Instr(link$, "://")
	If inst Then
		link$ = Right(link$, Len(link$) - inst - 2)
	EndIf
	
;Seperate host from link
	inst = Instr(link$, "/")
	If inst = 0 Then Return False
	host$ = Trim(Left(link$, inst - 1))
	link$ = Trim(Right(link$, Len(link$) - inst + 1))
	
;Seperate path and file from the link
	For i = Len(link$) To 1 Step -1
		If Mid(link$, i, 1) = "/" Then
			link_path$ = Trim(Left(link$, i))
			link_file$ = Right(link$, Len(link$) - i)
			Exit
		EndIf
	Next
	If link_file$ = "" Then Return False
	If savepath$ = "" Then savepath$ = link_file$
	
;Open TCP stream
	tcp = OpenTCPStream(host$, port)
	If tcp = 0 Then Return False
	WriteLine tcp, "GET " + link_path$ + link_file$ + " HTTP/1.1" + Chr(13) + Chr(10) + "Host: " + host$ + Chr(13) + Chr(10) + "User-Agent: SCP-CB Multiplayer Mod" + Chr(13) + Chr(10)
	
;Download file
	l$ = ReadLine(tcp)
	If l = "" Then
		CloseTCPStream tcp
		Return False
	EndIf
	
	inst1 = Instr(l$, " ")
	If inst1 = 0 Then
		CloseTCPStream tcp
		Return False
	EndIf
	inst2 = Instr(l$, " ", inst1 + 1)
	If inst2 = 0 Then
		CloseTCPStream tcp
		Return False
	EndIf
	num = Mid(l$, inst1, inst2 - inst1)
	
	ConsoleLog "Downloading "+latest+"...", 7
	
	Select num
		Case 200
			conlen = -1
			chunk = False
			
			Repeat
				l$ = Trim(ReadLine(tcp))
				If l$ = "" Then Exit
				
				inst = Instr(l$, ":")
				l1$ = Trim(Left(l$, inst - 1))
				l2$ = Trim(Right(l$, Len(l$) - inst))
				Select Lower(l1$)
					Case "content-length"
						conlen = l2$
					Case "transfer-encoding"
						If Lower(l2$) = "chunked" Then chunk = True
				End Select
			Forever
			
			If conlen = 0 Then
				file = WriteFile(savepath$)
				CloseFile file
				CloseTCPStream tcp
				Return False ;file doesn't exist ;True
			ElseIf conlen > 0 Then
			
				If FileType(savepath) = 0 Then
					file = WriteFile(savepath$)
				Else
					file = OpenFile(savepath)
					SeekFile file, FileSize(savepath)
				EndIf
				
				bnk = CreateBank(DOWNLOAD_SIZE)
				pos = 0
				Repeat
					avail = conlen - pos
					If avail > DOWNLOAD_SIZE Then
						ReadBytes bnk, tcp, 0, DOWNLOAD_SIZE
						WriteBytes bnk, file, 0, DOWNLOAD_SIZE
						pos = pos + DOWNLOAD_SIZE
						
					Else
						ReadBytes bnk, tcp, 0, avail
						WriteBytes bnk, file, 0, avail
						Exit
					EndIf
				Forever
				FreeBank bnk
				CloseFile file
				CloseTCPStream tcp
				Return True
			ElseIf chunk Then
				file = WriteFile(savepath$)
				bnk = CreateBank(DOWNLOAD_SIZE)
				
				Repeat
					l$ = Trim(Upper(ReadLine(tcp)))
					ln = 0
					For i = 1 To Len(l$)
						ln = 16 * ln + Instr("123456789ABCDEF", Mid$(l$, i, 1))
					Next
					If ln = 0 Then Exit
					
					If BankSize(bnk) < ln Then ResizeBank bnk, ln
					ReadBytes bnk, tcp, 0, ln
					WriteBytes bnk, file, 0, ln
					ReadShort(tcp)
				Forever
				
				FreeBank bnk
				CloseFile file
				CloseTCPStream tcp
				Return True
			Else
				CloseTCPStream tcp
				Return False
			EndIf
		Case 301, 302
			Repeat
				l$ = Trim(ReadLine(tcp))
				If l$ = "" Then Exit
				
				inst = Instr(l$, ":")
				l1$ = Trim(Left(l$, inst - 1))
				l2$ = Trim(Right(l$, Len(l$) - inst))
				Select Lower(l1$)
					Case "location"
						CloseTCPStream tcp
						Return Download(l2$, savepath$, latest)
				End Select
			Forever
		Default
			CloseTCPStream tcp
			Return False
	End Select
End Function

Function CheckForUpdates()
	Local changelogname$ = "ch", links$ = "links"
	DeleteFile changelogname
	DeleteFile links
	;

	ConsoleLog "Checking for updates...", 7
	Local Hosting$ = MULTIPLAYER_MOD_HOST
	
	If Download(Hosting+"MPChangeLogOld.txt", changelogname, "packet")=0 Then
		Hosting = MULTIPLAYER_MOD_HOST_RESERVE$
		If Download(Hosting+"MPChangeLogOld.txt", changelogname, "packet")=0 Then
			ConsoleLog("Failed to establish connection.",7)
			Return
		EndIf
	EndIf
	
	f = ReadFile(changelogname)
	latest$ = ReadLine(f)
	latest$ = Right(latest, Len(latest)-Instr(latest, "v"))
	CloseFile f
	
	If FileType("updates")=0 CreateDir "updates"
	
	DebugLog "LATEST VERSION: "+latest
	
	Local ConfirmDownload%=0
	Local DownloadURLs$[64]
	Local mpversion$ = MP_VERSION
	DebugLog "MULTIPLAYER VERSION: "+mpversion
	If latest<>mpversion And latest<>"" Then ;a new version is available!

		If Download(Hosting+"serverlinks.txt", links, "packet") = 1 Then
			;===================== Links
			foffset = 0
			
			f = ReadFile(links)
			While Not Eof(f)
				DownloadURLs[foffset] = ReadLine(f)
				foffset = foffset+1
			Wend
			CloseFile f
			DeleteFile links
			;====================
			
			DeleteFile "updates\"+latest+".zip"
			Local temp%
			For i = 0 To 63
				If DownloadURLs[i] <> "" Then
					temp=Download(DownloadURLs[i],"updates\"+latest+".zip",latest+".zip"+Str(i+1))
					If Not temp Then
						ConsoleLog "An error has occurred while downloading the update.",7
						Exit
					EndIf
				EndIf
			Next
			If temp>0 Then
				
				If FileSize("updates\"+latest+".zip")<>0 Then
					ConsoleLog "Extracting...", 7
					Local newRun$ = ""
					Local zipIn = ZipApi_Open("updates\"+latest+".zip")
					
					;Local temp%
					
					temp = ZipApi_GotoFirstFile(zipIn)+"a"
					;Repeat
					While (Not temp)
						
						Local fileData$	= ""
						Local fileInfo.ZIPAPI_UnzFileInfo	= ZipApi_GetCurrentFileInfo(zipIn)
						If fileInfo<>Null Then	
							fileData$ = fileInfo\FileName
							sizefile = fileInfo\UnCompressedSize
							
							ZIPAPI_UnzFileInfo_Dispose(fileInfo)
							If FileSize(fileData) <> sizefile And (Not Instr(fileData, "server.cfg")) And (Not Instr(fileData, "compilesettings")) And (Not Instr(fileData, "settings.ini")) Then
								
								If (Right(fileData,1)<>"/") And (Right(fileData,1)<>"\") Then ;fileData refers to a file, not a directory
									If Not Instr(fileData,"zlibwapi.dll") Then
										ZipApi_ExtractFile(zipIn, fileData, fileData)
									EndIf
								Else If FileType(fileData)=0
									CreateDir fileData
								EndIf
							EndIf
							If Instr(fileData,".exe")>0 And Instr(filedata, latest) Then
								newRun=filedata
							EndIf
						EndIf

						temp%=ZipApi_GotoNextFile(zipIn)
						
					Wend
					ZipApi_Close(zipIn)
					
					DeleteFile changelogname
					DeleteFile links
					
					If newRun<>"" Then ;run the new executable
						DebugLog "running "+newRun
						ExecFile(newRun)
					EndIf
					End ;close the game
					
				EndIf
			Else
				ConsoleLog "An error has occurred while downloading the update.",7
			EndIf
		Else
			ConsoleLog "An error has occurred while downloading the update.",7
		EndIf
	EndIf
	
	DeleteFile changelogname
	DeleteFile links
End Function

Function getserversettings(command$, filename$ = "settings.ini")
	Local f = ReadFile(filename),stats$ = "",msg$=""
	While Not Eof(f)
		msg = ReadLine(f)
		stats = Trim(Left(msg, Instr(msg, " ")))
		If stats = command Then
			stats = Replace(msg, stats+" ", "")
			Exit
		EndIf
	Wend
	CloseFile(f)
	Return stats
End Function
Function errorlogex$()
	Local result$ = Errorlog()
	If result = "" Then
		For er.errs = Each errs
			result = er\txt
			Delete er
			Exit
		Next
	EndIf
	Return result
End Function
Function adderrorlog(txt$)
	Local er.errs = New errs
	er\txt = txt
End Function

;; Scripts
;Function ConsoleLog(a$, b$)
;end Function
;Function createconsole(a$)
;End Function
Function kick_updater()
	If (Not Server\Breach) And (Not Server\IsStarted) Then
		Local cankick, readys
		For i = 1 To Server\PlayersCount
			If (Not PlayerOptimize[i]\Fake) Then
				If PlayerOptimize[i]\Ready = True Then
					PlayerOptimize[i]\KickTimer = MilliSecs()+60000
					readys = readys + 1
					cankick = True
				Else
					If Server\PlayersCount > 1 And (Not Server\AutoKick) Then
						If readys = Server\PlayersCount-1 Then
							Local foundbr
							For cm.ChatMessage = Each ChatMessage
								If cm\txt = "If you doesn't ready in 1 munite, then you will be kicked" Then foundbr = True : Exit
							Next
							If (Not foundbr) Then
								AddTextToChat("If you doesn't ready in 1 munite, then you will be kicked", PlayerOptimize[i]\ID)
							EndIf
							If MilliSecs() > PlayerOptimize[i]\KickTimer Then
								Kick(PlayerOptimize[i]\ID, PlayerOptimize[i]\name+" kicked due to inactive")
							EndIf
						Else
							PlayerOptimize[i]\KickTimer = MilliSecs()+60000
						EndIf
					EndIf
					canKick = False
					Exit
				EndIf
			EndIf
		Next
		If Not canKick Then
			gameInfo\b\LobbyTimer = MilliSecs()+10500
		EndIf
		If MilliSecs() > gameinfo\b\LobbyTimer Or (Server\LobbyDisabled = True) Or (ShouldStartRound) Or (Server\PlayersCount >= Server\MinPlayersToStart) Then 
			Server\IsStarted = True
			ShouldStartRound = False
		EndIf
	EndIf
End Function
Function skynet_onload()
	SCRIPT_COUNT = SCRIPT_COUNT + 1
End Function
Function GetScripts()
	Return (SCRIPT_COUNT<>0)
End Function
Function mp_SetRoomNameToPlayer(p.Players)
	p\PlayerRoomName = Room[p\PlayerRoomID]\RoomTemplate\Name
End Function

Function getplayerdownloadingcount(playerid)
	Local downloadcount = 0
	For q.querys = Each querys
		If q\playerid = playerid Then
			downloadcount = downloadcount+1
		EndIf
	Next
	Return downloadcount
End Function

Function updatequerys()
	For q.querys = Each querys
		If (q\offset = q\fullsize+1) Or Player[q\playerid] = Null Then
			If GetScripts() Then 
				public_inqueue(public_OnPlayerDownloadFile)
				public_addparam(0, q\playerid)
				public_addparam(0, q\file, SE_STRING)
				public_addparam(0, (Player[q\playerid]<>Null))
				callback
			EndIf
			If StripPath(q\file) = StripPath(Server\CustomMap) Then
				If Player[q\playerid] <> Null Then
					Player[q\playerid]\mapload = True
				EndIf
			EndIf
			CloseFile q\filehandle
			Delete q
		ElseIf Server\IsStarted = False Or (Player[q\playerid]\IsLoad = True Or Player[q\playerid]\mapload = False) Then
			If q\updatetime < MilliSecs() Then
				If q\offset<q\fullsize Then
					;Local DC = 8-min(getplayerdownloadingcount(q\playerid), 7)
					;for i = 1 to DC
						
					;	if q\offset = q\fullsize then exit
						
				
						ResizeBank QUERY_GLOBAL_DATA, q\downloadspeed
						SeekFile q\filehandle, q\offset
						ResizeBank QUERY_GLOBAL_DATA, Min(q\downloadspeed, q\fullsize-FilePos(q\filehandle))
						ReadBytes(QUERY_GLOBAL_DATA, q\filehandle, 0, BankSize(QUERY_GLOBAL_DATA))

						For a = 1 To 2 ; lossless
							udp_WriteByte M_LOADDATA
							udp_WriteByte 1
							udp_WriteLine q\file
							udp_WriteInt Handle(q)
							udp_WriteInt q\offset
							udp_WriteInt q\fullsize
							udp_WriteBytes QUERY_GLOBAL_DATA, 0, BankSize(QUERY_GLOBAL_DATA)
							udp_WriteByte q\isascript
							udp_WriteInt q\compressedfullsize
							udp_sendMessage(q\playerid)
						Next 
						q\offset = min(q\offset+q\downloadspeed, q\fullsize)

					;Next
					q\updatetime = MilliSecs()+20
				Else

					udp_WriteByte M_LOADDATA
					udp_WriteByte 1
					udp_WriteLine q\file
					udp_WriteInt Handle(q)
					udp_WriteInt q\offset
					udp_WriteInt q\fullsize
					udp_WriteByte q\isascript
					udp_WriteInt q\compressedfullsize
					udp_sendMessage(q\playerid)

					q\updatetime = MilliSecs()+500
				EndIf
			EndIf
		EndIf
	Next
End Function
Function SendFile(playerid, file$, savepath$, script = False, CompressPower% = 0, RewriteOnCompressing% = False)
	If Player[playerid] = Null Then Return
	
	If FileType(file) <> 1 Then Return
	For q.querys = Each querys
		If q\file = file And q\playerid = playerid Then Return True
	Next

	q = New querys
	q\file = savepath
	q\playerid = playerid
	q\isascript = script
	q\filehandle = ReadFile(file)
	q\fullsize = FileSize(file)
	q\downloadspeed = 0
	q\updatetime = MilliSecs()+1000
	
	If compresspower > 0 Then
		Local newbank = 0, bank = 0
		If FileType(file+".packed") <> 1 Or RewriteOnCompressing Then
			bank = CreateBank(FileSize(file))
			ReadBytes(bank, q\filehandle, 0, BankSize(bank))
			CloseFile(q\filehandle)
		
			newbank = ZipApi_Compress(bank, compresspower)
		Else
			q\filehandle = ReadFile(file+".packed")
			q\fullsize = FileSize(file+".packed")
			q\compressedfullsize = FileSize(file)
			Return
		EndIf
		
		If newbank = 0 Then
			FreeBank(bank)
			Delete q
			Return
		Else
			
			;if FileType(file+".packed") <> 1 Then
			f = WriteFile(file+".packed")
			WriteBytes(newbank, f, 0, BankSize(newbank))
			CloseFile(f)
			;EndIf
			
			q\filehandle = ReadFile(file+".packed")
			q\fullsize = FileSize(file+".packed")
			q\compressedfullsize = FileSize(file)
			
			FreeBank(newbank)
		EndIf
		
		FreeBank(bank)
	EndIf
	
	If q\fullsize = 0 Then
		CloseFile q\filehandle
		Delete q
		Return
	EndIf
	
	;ResizeBank QUERY_GLOBAL_DATA, q\downloadspeed
	;seekfile q\filehandle, q\offset
	;ResizeBank QUERY_GLOBAL_DATA, Min(q\downloadspeed, q\fullsize-FilePos(q\filehandle))
	;ReadBytes(QUERY_GLOBAL_DATA, q\filehandle, 0, BankSize(QUERY_GLOBAL_DATA))
	
	;udp_WriteByte M_LOADDATA
	;udp_WriteByte 1
	;udp_WriteLine q\file
	;udp_WriteInt Handle(q)
	;udp_WriteInt q\offset
	;udp_WriteInt q\fullsize
	;udp_WriteBytes bnk, 0, BankSize(bnk)
	;udp_WriteByte q\isascript
	;udp_WriteInt q\compressedfullsize
	;udp_sendMessage(q\playerid)
End Function

Function IsValidPlayer(playerid)
	Return (playerid <= Server\Maxplayers)
End Function

Function DeleteCharFromString_offset$(stri$, char$, offset = 1)
	Local returnstring$
	For i = 1 To Len(stri)
		If Mid(stri, i, 1) <> char Or i < offset Then returnstring = returnstring + Mid(stri, i, 1)
	Next
	Return returnstring
End Function

Function GetDownloadSpeed%(bytes) ; bytes per sec (megabyte/sec)
	Return bytes/1000000
End Function

Global MILLISECS_SYS[11]

Function StartOutTimer(id, interval)
	MILLISECS_SYS[id] = MilliSecs()+interval
End Function
Function IsTimedout(id, interval)
	Local IsTimedOut% = (MILLISECS_SYS[id] <= MilliSecs())
	If IsTimedOut Then StartOutTimer(id, interval)
	Return IsTimedOut
End Function
Function AddVersion(version$)
	Local v.versions = New versions
	v\vid = version
End Function
Function IsAccessVersion(version$)
	For v.versions = Each versions
		If Lower(v\vid) = Lower(version) Then Return True
	Next
	Return False
End Function
Function GetPlayerZone(playerid)
	If Player[playerid] <> Null Then
		For r.Rooms = Each rooms
			If r\ID = Player[playerid]\PlayerRoomID Then
				Return r\zone
			EndIf
		Next
	EndIf
End Function

Function DeactivateWarheads(playerid=0)

	If GetScripts() Then
		public_inqueue(public_OnDeactivateWarheads)
		public_addparam(0, playerid)
		callback
	EndIf
	
	gameInfo\b\Exploded = 0
	gameinfo\b\CurrentWarheadText = ""
	gameInfo\b\BreachTimer = gameInfo\b\PrevBreachTimer
	gameinfo\b\NTFTimeout = gameinfo\b\PrevNTFTimeoutTimer
End Function

Function ActivateWarheads(code$="", latestroles=0, playerid=0)
	If GetScripts() Then
		public_inqueue(public_OnActivateWarheads)
		public_addparam(0, playerid)
		callback
	EndIf
	
	
	gameinfo\b\PrevNTFTimeoutTimer = gameinfo\b\NTFtimeout
	gameinfo\b\PrevBreachTimer = gameinfo\b\BreachTimer
	gameInfo\b\BreachTimer = MilliSecs()+70000
	gameinfo\b\NTFTimeout = MilliSecs()+Rand(300000, 400000)
	gameinfo\b\ExplodeTimeout = MilliSecs()+120000
	gameinfo\b\CurrentWarheadText = code
	
	For i = 1 To Server\PlayersCount
		breach_GetCategoryColor(latestroles)
		If latestroles = 0 Then Color 0,0,0
		
		udp_WriteByte(M_WARHEADS)
		udp_WriteByte(1)
		If code = "" Then
			udp_WriteLine(breach_getcategoryname(latestroles))
		Else
			udp_WriteLine(code)
		EndIf
		udp_WriteByte(ColorRed())
		udp_WriteByte(ColorGreen())
		udp_WriteByte(ColorBlue())
		udp_WriteByte(((SE_GetReturnValue() = 0 Or SE_GetReturnValue() = -1)=0))
		udp_SendMessage(PlayerOptimize[i]\ID)
	Next
	gameInfo\b\Exploded = 1
End Function

Function SpawnMTF()
	gameinfo\b\MTFTickets = Min(Max(gameinfo\b\MTFTickets, 0),Server\Maxplayers)
	gameinfo\b\ChaosTickets = Min(Max(gameinfo\b\ChaosTickets, 0),Server\Maxplayers)
	
	Local Maximum = 0
	
	Local playermassive[MAX_PLAYERS]
	Local playerscount
	For i = 1 To Server\PlayersCount
		If PlayerOptimize[i]\BreachType = 0 Then
			playermassive[playerscount] = PlayerOptimize[i]\ID
			playerscount = playerscount + 1
		EndIf
	Next
	
	If playerscount > 1 Then
		If (gameinfo\b\Teams = 0 And gameinfo\b\MTFTickets < 1) Or (gameinfo\b\teams = 1 And gameinfo\b\chaostickets < 1) Then
			gameinfo\b\Teams = Not gameinfo\b\teams
		EndIf
		If gameinfo\b\ChaosTickets >= 1 Or gameinfo\b\MTFTickets >= 1 Then
			If gameinfo\b\MTFTickets < 1 And gameinfo\b\ChaosTickets < 8 Then 
				gameinfo\b\ChaosTickets = 8
				gameinfo\b\teams = 1
			EndIf
			Select gameinfo\b\teams
				Case 0
					If gameinfo\b\mtftickets >= Server\MaxSpawnPlayers Then 
						Maximum = gameinfo\b\MTFTickets-Server\MaxSpawnPlayers
					Else
						Maximum = 0
					EndIf
					random = 0
					While gameinfo\b\MTFTickets >= 1
						random = Rand(0, playerscount-1)
						SetPlayerType(playermassive[random], NTF_MODEL)
						If playerscount >= 1 Then
							playermassive[random] = playermassive[playerscount-1]
							playerscount = playerscount - 1
							If playerscount < 1 Then Exit
						EndIf
						gameinfo\b\MTFTickets = gameinfo\b\MTFTickets - 1.0
						If Maximum > gameinfo\b\MTFTickets Then Exit
					Wend
				Case 1
					If gameinfo\b\ChaosTickets >= Server\MaxSpawnPlayers Then 
						Maximum = gameinfo\b\ChaosTickets-Server\MaxSpawnPlayers
					Else
						Maximum = 0
					EndIf
					random = 0
					While gameinfo\b\ChaosTickets >= 1
						random = Rand(0, playerscount-1)
						SetPlayerType(playermassive[random], HAOS_MODEL)
						If playerscount >= 1 Then
							playermassive[random] = playermassive[playerscount-1]
							playerscount = playerscount - 1
							If playerscount < 1 Then Exit
						EndIf
						gameInfo\b\ChaosTickets = gameInfo\b\ChaosTickets - 1.0
						If Maximum > gameInfo\b\ChaosTickets Then Exit
					Wend
			End Select
		EndIf
	EndIf
	If GetScripts() Then
		Select gameInfo\b\Teams
			Case 0
				public_inqueue(public_OnSpawnMTF)
			Case 1
				public_inqueue(public_OnSpawnChaos)
		End Select
		callback
	EndIf
	If SE_GetReturnValue() = 0 Or SE_GetReturnValue() = -1 Then
		If gameInfo\b\Teams <> 1 Then
			For i = 1 To Server\PlayersCount
				udp_WriteByte(M_REMOTEDOOR)
				udp_WriteByte(PlayerOptimize[i]\ID)
				udp_SendMessage(PlayerOptimize[i]\ID)
			Next
		EndIf
	EndIf
	gameInfo\b\NTFTimeout = MilliSecs()+Rand(300000, 400000)
	gameInfo\b\Teams = Not gameInfo\b\Teams
End Function

Function ReverseTo#(value#, startval#, endval#)
	If value > endval Or value < startval Then value = startval#
	Return value
End Function

Function ReloadApplication()
	If FileType("server.exe") <> 1 Then Return False
	ExecuteApp("server.exe")
	
	App = api_OpenProcess(1, False, api_GetCurrentProcessId());
	api_TerminateProcess(App, 1)
	api_CloseHandle(App)
	End
	Return True
End Function

Function Console_SpawnNPC(Entity, c_input$, c_state$ = "")
	Local n.NPCs
	Local consoleMSG$
	
	Select c_input$ 
		Case "008", "008zombie"
			n.NPCs = CreateNPC(NPCtype008, EntityX(Entity), EntityY(Entity) + 0.2, EntityZ(Entity))
			n\State = 1
			consoleMSG = "SCP-008 infected human spawned."
			
		Case "049", "scp049", "scp-049"
			n.NPCs = CreateNPC(NPCtype049, EntityX(Entity), EntityY(Entity) + 0.2, EntityZ(Entity))
			n\State = 1
			consoleMSG = "SCP-049 spawned."
			
		Case "049-2", "0492", "scp-049-2", "scp049-2", "049zombie"
			n.NPCs = CreateNPC(NPCtypeZombie, EntityX(Entity), EntityY(Entity) + 0.2, EntityZ(Entity))
			n\State = 1
			consoleMSG = "SCP-049-2 spawned."
			
		Case "066", "scp066", "scp-066"
			n.NPCs = CreateNPC(NPCtype066, EntityX(Entity), EntityY(Entity) + 0.2, EntityZ(Entity))
			consoleMSG = "SCP-066 spawned."
			
		Case "096", "scp096", "scp-096"
			n.NPCs = CreateNPC(NPCtype096, EntityX(Entity), EntityY(Entity) + 0.2, EntityZ(Entity))
			n\State = 5
			If (Curr096 = Null) Then Curr096 = n
			consoleMSG = "SCP-096 spawned."
			
		Case "106", "scp106", "scp-106", "larry"
			n.NPCs = CreateNPC(NPCtypeOldMan, EntityX(Entity), EntityY(Entity) - 0.5, EntityZ(Entity))
			n\State = -1
			consoleMSG = "SCP-106 spawned."
			
		Case "173", "scp173", "scp-173", "statue"
			n.NPCs = CreateNPC(NPCtype173, EntityX(Entity), EntityY(Entity) + 0.2, EntityZ(Entity))
			Curr173 = n
			If (Curr173\Idle = 3) Then Curr173\Idle = False
			consoleMSG = "SCP-173 spawned."
		Case "372", "scp372", "scp-372"
			n.NPCs = CreateNPC(NPCtype372, EntityX(Entity), EntityY(Entity) + 0.2, EntityZ(Entity))
			consoleMSG = "SCP-372 spawned."
			
		Case "513-1", "5131", "scp513-1", "scp-513-1"
			n.NPCs = CreateNPC(NPCtype5131, EntityX(Entity), EntityY(Entity) + 0.2, EntityZ(Entity))
			consoleMSG = "SCP-513-1 spawned."
			
		Case "860-2", "8602", "scp860-2", "scp-860-2"

		Case "939", "scp939", "scp-939"

		Case "966", "scp966", "scp-966"
			n.NPCs = CreateNPC(NPCtype966, EntityX(Entity), EntityY(Entity) + 0.2, EntityZ(Entity))
			consoleMSG = "SCP-966 instance spawned."
			
		Case "1048-a", "scp1048-a", "scp-1048-a", "scp1048a", "scp-1048a"

		Case "1499-1", "14991", "scp-1499-1", "scp1499-1"
			n.NPCs = CreateNPC(NPCtype1499, EntityX(Entity), EntityY(Entity) + 0.2, EntityZ(Entity))
			consoleMSG = "SCP-1499-1 instance spawned."
			
		Case "class-d", "classd", "d"
			n.NPCs = CreateNPC(NPCtypeD, EntityX(Entity), EntityY(Entity) + 0.2, EntityZ(Entity))
			consoleMSG = "D-Class spawned."
			
		Case "guard"
			n.NPCs = CreateNPC(NPCtypeGuard, EntityX(Entity), EntityY(Entity) + 0.2, EntityZ(Entity))
			consoleMSG = "Guard spawned."
			
		Case "mtf"
			n.NPCs = CreateNPC(NPCtypeMTF, EntityX(Entity), EntityY(Entity) + 0.2, EntityZ(Entity))
			consoleMSG = "MTF unit spawned."
			
		Case "apache", "helicopter"
			n.NPCs = CreateNPC(NPCtypeApache, EntityX(Entity), EntityY(Entity) + 0.2, EntityZ(Entity))
			consoleMSG = "Apache spawned."
			
		Case "tentacle"
			n.NPCs = CreateNPC(NPCtypeTentacle, EntityX(Entity), EntityY(Entity), EntityZ(Entity))
			consoleMSG = "SCP-035 tentacle spawned."
			
		Case "clerk"
			n.NPCs = CreateNPC(NPCtypeClerk, EntityX(Entity), EntityY(Entity) + 0.2, EntityZ(Entity))
			consoleMSG = "Clerk spawned."
			
		Default 
			Return
	End Select
	
	If n <> Null
		If c_state <> "" Then n\State = Float(c_state) : consoleMSG = consoleMSG + " (State = " + n\State + ")"
	EndIf
	
End Function

Function OnCheatDetected(playerid, constant)
	If GetScripts() Then
		public_inqueue(public_OnCheatDetected)
		public_addparam(0, playerid)
		public_addparam(0, constant)
		callback
	EndIf
End Function

Function GetUnixTimestamp()
	; Returns a Unix timestamp.

	; A Unix timestamp is the number of seconds since the start of the year 1970.
	; Unix timestamps operate on UTC time (Coordinated Universal Time)
	; which is the same as GMT time (Greenwich Mean Time).
	; Due to the use of UTC, local daylight saving and timezones are not a factor.
	
	; Note that Unix time seems to ignore leap seconds.

	; Requires a 'kernel32.decls' file in the Blitz3D 'userlibs' folder with the contents:-
	; .lib "kernel32.dll"
	; api_GetSystemTime (lpSystemTime*) : "GetSystemTime"

	; The 'kernel32.decls' file can be obtained from:-
	; http://www.blitzbasic.com/codearcs/codearcs.php?code=1180

	; Reference Links:-
	; http://en.wikipedia.org/wiki/Unix_time
	; http://en.wikipedia.org/wiki/Leap_year
	; http://en.wikipedia.org/wiki/Leap_second
	; http://en.wikipedia.org/wiki/Coordinated_universal_time
	; http://msdn.microsoft.com/en-us/library/windows/desktop/ms724390(v=vs.85).aspx
	; http://msdn.microsoft.com/en-us/library/windows/desktop/ms724950(v=vs.85).aspx

	; To test the results use: http://www.unixtimestamp.com/

	; Get the UTC/GMT time and date.
	Local timebank = CreateBank( 16 )
	api_GetSystemTime( timebank )
	Local year = PeekShort( timebank, 0 ) ; The year. Exact value.
	Local month = PeekShort( timebank, 2 ) ; The month. Struct values: January = 1 - December = 12
	Local day = PeekShort( timebank, 6 ) - 1 ; The day of the month. The valid values for this struct member are 1 through 31.
	Local hour = PeekShort( timebank, 8 ) ; The hour. The valid values for this struct member are 0 through 23.
	Local minute = PeekShort( timebank, 10 ) ; The minute. The valid values for this struct member are 0 through 59.
	Local second = PeekShort( timebank, 12 ) ; The second. The valid values for this struct member are 0 through 59.
	FreeBank timebank

	; Do we include the current year in the leap year test?
	Local end_year
	If month > 2
		end_year = year
	Else 
		end_year = year - 1
	EndIf
	
	; Calculate if a specific year is a leap year
	Local y
	For y = 1970 To end_year
		If ( y Mod 100 ) = 0
			If ( y Mod 400 ) = 0
				day = day + 1 ; leap year = True
			EndIf
		Else
			If ( y Mod 4 ) = 0
				day = day + 1 ; leap year = True
			EndIf 
		EndIf 
	Next
	
	day = day + ( year - 1970 ) * 365

	If month > 1 Then day = day + 31
	If month > 2 Then day = day + 28
	If month > 3 Then day = day + 31
	If month > 4 Then day = day + 30
	If month > 5 Then day = day + 31
	If month > 6 Then day = day + 30
	If month > 7 Then day = day + 31
	If month > 8 Then day = day + 31
	If month > 9 Then day = day + 30
	If month > 10 Then day = day + 31
	If month > 11 Then day = day + 30
	If month > 12 Then day = day + 31

	Return day * 86400 + hour * 3600 + minute * 60 + second
End Function

Function ChangePlayerTag(playerid, tag$, R, G, B)
	For i = 1 To Server\Maxplayers
		If Player[i] <> Null Then
			udp_WriteByte(M_REQUESTPLAYERNAME)
			udp_WriteByte(playerid)
			udp_WriteLine Player[playerid]\name
			udp_WriteLine tag
			udp_WriteByte R
			udp_WriteByte G
			udp_WriteByte B
			udp_WriteShort Player[playerid]\Size*100
			udp_WriteInt Player[playerid]\PlayerSteamID
			udp_WriteByte Player[playerid]\TextureID
			udp_SendMessage(i)
		EndIf
	Next
	
	Player[playerid]\Tag = tag
	Player[playerid]\TagR = R
	Player[playerid]\TagG = G
	Player[playerid]\TagB = B
	
End Function

Function ChangePlayerNickname(playerid, name$)
	If name = "" Then Return
	For i = 1 To Server\Maxplayers
		If Player[i] <> Null And i <> playerid Then
			udp_WriteByte(M_REQUESTPLAYERNAME)
			udp_WriteByte(playerid)
			udp_WriteLine name
			udp_WriteLine Player[playerid]\tag
			udp_WriteByte Player[playerid]\tagR
			udp_WriteByte Player[playerid]\tagG
			udp_WriteByte Player[playerid]\tagB
			udp_WriteShort Player[playerid]\Size*100
			udp_WriteInt Player[playerid]\PlayerSteamID
			udp_WriteByte Player[playerid]\TextureID
			udp_SendMessage(i)
		EndIf
	Next
	Player[playerid]\name = name
End Function

Function ChangePlayerSize(playerid, size)
	For i = 1 To Server\Maxplayers
		If Player[i] <> Null Then
			udp_WriteByte(M_REQUESTPLAYERNAME)
			udp_WriteByte(playerid)
			udp_WriteLine Player[playerid]\name
			udp_WriteLine Player[playerid]\tag
			udp_WriteByte Player[playerid]\tagR
			udp_WriteByte Player[playerid]\tagG
			udp_WriteByte Player[playerid]\tagB
			udp_WriteShort size
			udp_WriteInt Player[playerid]\PlayerSteamID
			udp_WriteByte Player[playerid]\TextureID
			udp_SendMessage(i)
		EndIf
	Next
	
	Player[playerid]\Size = Float(Size)/100.0
		
End Function

Function ChangePlayerSteamID(playerid, USteamID)
	For i = 1 To Server\Maxplayers
		If Player[i] <> Null Then
			udp_WriteByte(M_REQUESTPLAYERNAME)
			udp_WriteByte(playerid)
			udp_WriteLine Player[playerid]\name
			udp_WriteLine Player[playerid]\tag
			udp_WriteByte Player[playerid]\tagR
			udp_WriteByte Player[playerid]\tagG
			udp_WriteByte Player[playerid]\tagB
			udp_WriteShort Player[playerid]\Size*100
			udp_WriteInt USteamID
			udp_WriteByte Player[playerid]\TextureID
			udp_SendMessage(i)
		EndIf
	Next
	
	Player[playerid]\PlayerSteamID = USteamID
		
End Function

Function UDP_FindFree()
	Local STREAM = CreateUDPStream()
	Local Port% = UDPStreamPort(STREAM)
	CloseUDPStream(STREAM)
	Return Port
End Function

Function SendServerDataToPlayer(IP, Port)
	udp_WriteByte(M_CONNECT)
	udp_WriteByte(0)
	udp_WriteLine(Server\RandomSeed)
	udp_WriteByte(Server\IntroEnabled)
	udp_WriteByte(Server\NoCheat)
	udp_WriteByte(Server\ServerVoice)
	udp_WriteFloat(Server\EventProb)
	udp_WriteInt Server\Timeout
	udp_WriteFloat(Server\JumpHeight)
	udp_WriteByte(Server\JumpMode)
	udp_WriteByte(Server\Maxplayers)
	udp_WriteByte(Server\Tickrate)
	udp_WriteByte(Server\Breach)
	udp_WriteByte(Server\keepitems)
	udp_WriteByte 0;ReceivePlayer\BreachType
	udp_WriteInt gameInfo\b\BreachTimer-MilliSecs()
	udp_WriteInt Server\BreachTime
	udp_WriteByte 0;ReceivePlayer\TextureID
	udp_WriteInt gameInfo\b\LobbyTimer-MilliSecs()
	udp_WriteByte 0
	udp_WriteByte Server\IsStarted
	udp_WriteInt Server\voicekhz
	udp_WriteLine StripPath(Server\Custommap)
	udp_WriteByte 0
	udp_WriteByte Server\Longculling
	udp_WriteByte Server\MapSize
	udp_WriteByte Server\FullSync
	udp_WriteByte Server\Prediction
	udp_WriteByte Server\Interpolation
	udp_WriteByte Server\FilesSendActivate
	udp_WriteLine Server\menuhtml
	udp_WriteLine Server\restartmenuhtml
	udp_WriteByte HALLOWEENINDEX
	udp_WriteByte NEWYEARINDEX
	SendUDPMsg(Server\ConnectServer,IP,Port)
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
	PrepareModelIdentifier(244, "GFX\npcs\clerkMP.b3d")
End Function

Function GetFormattedText$(txt$)
	Local Currtoken$, currtxt$, command$
	Local FoundCommand% = 0
	
	For i = 1 To Len(txt)
		FoundCommand = 0
		currtoken = Mid(txt, i, 1)
		If currtoken = "%" Then
			command$ = Piece(Right(txt, Max(Len(txt)-i+2, 0)), 2, "%")
			params$ = ""
			
			; ====================== oldest
			
			Select command
				Case "r", "g", "b", "y", "w", "p"
				Default: FoundCommand = FoundCommand+1
			End Select
			
			; ====================== new

			Select Piece(command, 1, "|")
				Case "color", "font", "align", "alignfix", "tab", "clickable"
				Default: FoundCommand = FoundCommand+1
			End Select
			
			If FoundCommand = 2 Then 
				FoundCommand = False
			Else
				i = i+Len(command)+1
			EndIf
		Else
			FoundCommand = False
		EndIf
		
		If Not FoundCommand Then currtxt = currtxt+currtoken
	Next
	
	Return currtxt
	
End Function

Function shuffleplayersarray(p_array[MAX_PLAYERS], arraysize)
	Local j
	Local temp
	
	For i = arraysize-1 To 0 Step -1
		j = Rand(0, i)
		temp = p_array[j]
		p_array[j] = p_array[i] 
		p_array[i] = temp
	Next
End Function

Function ReadLineSafe$(Stream)
	Local ByteValue%
	Local Value$
	While True
		ByteValue = ReadByte(Stream)
		Select ByteValue
			Case 0,10
				Exit
			Case 13

			Default
				If (ByteValue >= 33 And ByteValue <= 126) Or (ByteValue >= 128 And ByteValue <= 175) Or (ByteValue >= 224 Or ByteValue <= 241) Then Value = Value + Chr(ByteValue)
		End Select
	Wend
	Return Value
End Function

;~IDEal Editor Parameters:
;~C#Blitz3D