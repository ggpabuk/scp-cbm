; ===================== Skynet++ public system
; ===================== Publics ID
const public_user_main = 1
const public_OnGenerateWorld = 2
const public_OnServerStart = 3
const public__main = 4
const public_OnServerUpdate = 5
const public_OnPlayerRequestFiles = 6
const public_OnPlayerCuffPlayer = 7
const public_OnPlayerActivateWarheads = 8
const public_OnPlayerDeactivateWarheads = 9
const public_OnPlayerConsole = 10
const public_OnReceiveRawPacket = 11
const public_OnPlayerMouseHit = 12
const public_OnPlayerShoot = 13
const public_OnPlayerShootRocket = 14
const public_OnPlayerDropGrenade = 15
const public_OnPlayerRequestNewRole = 16
const public_OnPlayerHitPlayer = 18
const public_OnPlayerUseItem = 19
const public_OnPlayerActivateFemurBreaker = 20
const public_OnPlayerRequestExplosion = 21
const public_OnPlayerRconIncorrect = 22
const public_OnPlayerRconAuthorized = 23
const public_OnPlayerChat = 24
const public_OnPlayerKillPlayer = 25
const public_OnPlayerCreateDecal = 26
const public_OnPlayerSpawnItem = 27
const public_OnPlayerUse1162 = 28
const public_OnPlayerTakeItem = 29
const public_OnPlayerDropItem = 30
const public_OnPlayerClickButton = 31
const public_OnConnectionResponse = 32
const public_OnPlayerSpeaking = 33
const public_OnIncomingConnection = 34
const public_OnPlayerConnect = 35
const public_OnPlayerRotateLever = 36
const public_OnPlayerUpdate = 37
const public_OnPlayerReleaseSound = 39
const public_OnPlayerDisconnect = 40
const public_OnServerRestart = 41
const public_OnRoundStarted = 42
const public_OnPlayerEscape = 43
const public_OnPlayerEscapeButDead = 44
const public_OnWarheadsExplosion = 47
const public_OnPlayerGetNewRole = 48
const public_OnPlayerDownloadFile = 49
const public_OnActivateWarheads = 50
const public_OnSpawnMTF = 51
const public_OnSpawnChaos = 52
const public_OnCheatDetected = 53
const public_OnCreateItem = 54
const public_OnPlayAnnouncement = 55
const public_OnMapUpdate = 60
const public_OnCreateNPC = 61
const public_OnPlayerRequestUnlockExits = 63
const public_OnPlayerRequestNoTarget = 64
const public_OnScriptLoaded = 65
const public_OnBadIncomingConnection = 66
const public_OnPlayerSCPContained = 67
const public_OnLostConnectionWithCentralServer = 68
const public_OnItemRefine = 69
const public_OnDeactivateWarheads = 70
const public_OnFillingRoom = 71

function public_getid(name$)
	select name
		case "OnFillingRoom": Return public_OnFillingRoom
		case "OnDeactivateWarheads": Return public_OnDeactivateWarheads
		case "OnItemRefine": Return public_OnItemRefine
		case "OnLostConnectionWithCentralServer": Return public_OnLostConnectionWithCentralServer
		case "OnPlayerSCPContained"
			Return public_OnPlayerSCPContained
			
		case "OnScriptLoaded"
			Return public_OnScriptLoaded
		case "_main"
			Return public__main

		case "user_main"
			Return public_user_main

		case "OnGenerateWorld"
			Return public_OnGenerateWorld

		case "OnServerStart"
			Return public_OnServerStart

		case "OnServerUpdate"
			Return public_OnServerUpdate

		case "OnPlayerRequestFiles"
			Return public_OnPlayerRequestFiles

		case "OnPlayerCuffPlayer"
			Return public_OnPlayerCuffPlayer

		case "OnPlayerActivateWarheads"
			Return public_OnPlayerActivateWarheads

		case "OnPlayerDeactivateWarheads"
			Return public_OnPlayerDeactivateWarheads

		case "OnPlayerConsole"
			Return public_OnPlayerConsole

		case "OnReceiveRawPacket"
			Return public_OnReceiveRawPacket

		case "OnPlayerMouseHit"
			Return public_OnPlayerMouseHit

		case "OnPlayerShoot"
			Return public_OnPlayerShoot

		case "OnPlayerShootRocket"
			Return public_OnPlayerShootRocket

		case "OnPlayerDropGrenade"
			Return public_OnPlayerDropGrenade

		case "OnPlayerRequestNewRole"
			Return public_OnPlayerRequestNewRole

		case "OnPlayerUseItem"
			Return public_OnPlayerUseItem

		case "OnPlayerActivateFemurBreaker"
			Return public_OnPlayerActivateFemurBreaker

		case "OnPlayerRequestExplosion"
			Return public_OnPlayerRequestExplosion

		case "OnPlayerRconIncorrect"
			Return public_OnPlayerRconIncorrect

		case "OnPlayerRconAuthorized"
			Return public_OnPlayerRconAuthorized

		case "OnPlayerChat"
			Return public_OnPlayerChat

		case "OnPlayerKillPlayer"
			Return public_OnPlayerKillPlayer

		case "OnPlayerCreateDecal"
			Return public_OnPlayerCreateDecal

		case "OnPlayerSpawnItem"
			Return public_OnPlayerSpawnItem

		case "OnPlayerUse1162"
			Return public_OnPlayerUse1162

		case "OnPlayerTakeItem"
			Return public_OnPlayerTakeItem

		case "OnPlayerDropItem"
			Return public_OnPlayerDropItem

		case "OnPlayerClickButton"
			Return public_OnPlayerClickButton

		case "OnConnectionResponse"
			Return public_OnConnectionResponse

		case "OnPlayerSpeaking"
			Return public_OnPlayerSpeaking

		case "OnIncomingConnection"
			Return public_OnIncomingConnection

		case "OnPlayerConnect"
			Return public_OnPlayerConnect

		case "OnPlayerRotateLever"
			Return public_OnPlayerRotateLever

		case "OnPlayerUpdate"
			Return public_OnPlayerUpdate

		case "OnPlayerReleaseSound"
			Return public_OnPlayerReleaseSound

		case "OnPlayerDisconnect"
			Return public_OnPlayerDisconnect

		case "OnServerRestart"
			Return public_OnServerRestart

		case "OnRoundStarted"
			Return public_OnRoundStarted

		case "OnPlayerEscapeButDead"
			Return public_OnPlayerEscapeButDead

		case "OnPlayerEscape"
			Return public_OnPlayerEscape

		case "OnPlayerGetNewRole"
			Return public_OnPlayerGetNewRole

		case "OnPlayerDownloadFile"
			Return public_OnPlayerDownloadFile

		case "OnActivateWarheads"
			Return public_OnActivateWarheads

		case "OnSpawnMTF"
			Return public_OnSpawnMTF

		case "OnSpawnChaos"
			Return public_OnSpawnChaos

		case "OnCheatDetected"
			Return public_OnCheatDetected

		case "OnCreateItem"
			Return public_OnCreateItem

		case "OnPlayAnnouncement"
			Return public_OnPlayAnnouncement

		case "OnPlayerHitPlayer"
			Return public_OnPlayerHitPlayer

		case "OnMapUpdate"
			Return public_OnMapUpdate

		case "OnCreateNPC"
			Return public_OnCreateNPC

		case "OnPlayerRequestUnlockExits"
			Return public_OnPlayerRequestUnlockExits

		case "OnPlayerRequestNoTarget"
			Return public_OnPlayerRequestNoTarget

		case "OnWarheadsExplosion"
			Return public_OnWarheadsExplosion
		case "OnBadIncomingConnection"
			Return public_OnBadIncomingConnection
	end select
end function

function public_getname$(publicid)
	select publicid
		case public_OnFillingRoom: Return "OnFillingRoom"
		case public_OnDeactivateWarheads: Return "OnDeactivateWarheads"
		case public_OnItemRefine: Return "OnItemRefine"
		case public_OnLostConnectionWithCentralServer: Return "OnLostConnectionWithCentralServer"
		case public_OnPlayerSCPContained
			Return "OnPlayerSCPContained"
			
		case public_OnScriptLoaded
			return "OnScriptLoaded"
		case public__main
			Return "_main"

		case public_user_main
			Return "user_main"

		case public_OnGenerateWorld
			Return "OnGenerateWorld"

		case public_OnServerStart
			Return "OnServerStart"

		case public_OnServerUpdate
			Return "OnServerUpdate"

		case public_OnPlayerRequestFiles
			Return "OnPlayerRequestFiles"

		case public_OnPlayerCuffPlayer
			Return "OnPlayerCuffPlayer"

		case public_OnPlayerActivateWarheads
			Return "OnPlayerActivateWarheads"

		case public_OnPlayerDeactivateWarheads
			Return "OnPlayerDeactivateWarheads"

		case public_OnPlayerConsole
			Return "OnPlayerConsole"

		case public_OnReceiveRawPacket
			Return "OnReceiveRawPacket"

		case public_OnPlayerMouseHit
			Return "OnPlayerMouseHit"

		case public_OnPlayerShoot
			Return "OnPlayerShoot"

		case public_OnPlayerShootRocket
			Return "OnPlayerShootRocket"

		case public_OnPlayerDropGrenade
			Return "OnPlayerDropGrenade"

		case public_OnPlayerRequestNewRole
			Return "OnPlayerRequestNewRole"

		case public_OnPlayerHitPlayer
			Return "OnPlayerHitPlayer"

		case public_OnPlayerUseItem
			Return "OnPlayerUseItem"

		case public_OnPlayerActivateFemurBreaker
			Return "OnPlayerActivateFemurBreaker"

		case public_OnPlayerRequestExplosion
			Return "OnPlayerRequestExplosion"

		case public_OnPlayerRconIncorrect
			Return "OnPlayerRconIncorrect"

		case public_OnPlayerRconAuthorized
			Return "OnPlayerRconAuthorized"

		case public_OnPlayerKillPlayer
			Return "OnPlayerKillPlayer"

		case public_OnPlayerCreateDecal
			Return "OnPlayerCreateDecal"

		case public_OnPlayerSpawnItem
			Return "OnPlayerSpawnItem"

		case public_OnPlayerUse1162
			Return "OnPlayerUse1162"

		case public_OnPlayerTakeItem
			Return "OnPlayerTakeItem"

		case public_OnPlayerDropItem
			Return "OnPlayerDropItem"

		case public_OnPlayerClickButton
			Return "OnPlayerClickButton"

		case public_OnConnectionResponse
			Return "OnConnectionResponse"

		case public_OnPlayerSpeaking
			Return "OnPlayerSpeaking"

		case public_OnIncomingConnection
			Return "OnIncomingConnection"

		case public_OnPlayerConnect
			Return "OnPlayerConnect"

		case public_OnPlayerRotateLever
			Return "OnPlayerRotateLever"

		case public_OnPlayerUpdate
			Return "OnPlayerUpdate"

		case public_OnPlayerReleaseSound
			Return "OnPlayerReleaseSound"

		case public_OnPlayerDisconnect
			Return "OnPlayerDisconnect"

		case public_OnServerRestart
			Return "OnServerRestart"

		case public_OnRoundStarted
			Return "OnRoundStarted"

		case public_OnPlayerEscapeButDead
			Return "OnPlayerEscapeButDead"

		case public_OnPlayerEscape
			Return "OnPlayerEscape"

		case public_OnPlayerGetNewRole
			Return "OnPlayerGetNewRole"

		case public_OnPlayerDownloadFile
			Return "OnPlayerDownloadFile"

		case public_OnActivateWarheads
			Return "OnActivateWarheads"

		case public_OnSpawnMTF
			Return "OnSpawnMTF"

		case public_OnSpawnChaos
			Return "OnSpawnChaos"

		case public_OnCheatDetected
			Return "OnCheatDetected"

		case public_OnCreateItem
			Return "OnCreateItem"

		case public_OnPlayAnnouncement
			Return "OnPlayAnnouncement"

		case public_OnMapUpdate
			Return "OnMapUpdate"

		case public_OnCreateNPC
			Return "OnCreateNPC"

		case public_OnPlayerChat
			Return "OnPlayerChat"

		case public_OnPlayerRequestUnlockExits
			Return "OnPlayerRequestUnlockExits"

		case public_OnPlayerRequestNoTarget
			Return "OnPlayerRequestNoTarget"

		case public_OnWarheadsExplosion
			Return "OnWarheadsExplosion"
		case public_OnBadIncomingConnection
			Return "OnBadIncomingConnection"
	End Select
end function
;
Const MAX_PUBLIC_PARAMS = 16
Type pb
	Field publicid
	Field params
	Field param$[MAX_PUBLIC_PARAMS]
	Field paramtype[MAX_PUBLIC_PARAMS]
	;Field pointer_value.SE_Value[MAX_PUBLIC_PARAMS]
	;Field readed
End Type

Global publics.pb = New pb

Function init_publics_for_script(script.SE_Script)
	for i = 1 to MAX_SCRIPT_PUBLICS-1
		if public_getname(i) <> "" then 
			script\ScriptPublic[i] = SE_FindFunc(script, lower(public_getname(i)))
		endif
	next
End Function

function public_inqueue(publicid, callbackUsing = False)
	publics\publicid = publicid
	publics\params = 0
	if callbackUsing Then callback
End Function
Function public_addparam(pbid, param$, valuetype = SE_INT)
	publics\params = publics\params+1
	publics\param[publics\params] = param
	publics\paramtype[publics\params] = valuetype
End Function

Function public_update_current(script.ScriptsThread, fromScript% = False)
	if script\LuaThread <> 0 Then
		public_update_lua(script\LuaThread, public_getname(publics\publicid))
		Return
	EndIf
	
	local previd = publics\publicid
	local prevparams = publics\params
	local prevparam$[MAX_PUBLIC_PARAMS]
	local prevparamtype[MAX_PUBLIC_PARAMS]
	For i = 1 To publics\params
		prevparam[i] = publics\param[i]
		prevparamtype[i] = publics\paramtype[i]
	Next
	
	Local Func.SE_FuncPtr = script\scriptThread\ScriptPublic[publics\publicid]

	if func <> Null Then
		SE_ARGUMENTS_NUMBER = 0
		For i = 1 To publics\params
			if i > Func\Arguments Then Exit
			Select publics\paramtype[i]
				Case SE_STRING: SE_AddStringArg(publics\param[i])
				Case SE_INT: SE_AddIntArg(publics\param[i])
				Case SE_FLOAT: SE_AddFloatArg(publics\param[i])
				Case SE_NULL: SE_AddStringArg(publics\param[i])
			End Select
		Next
		;if fromScript Then SE_ARGUMENTS_NUMBER = SE_ARGUMENTS_NUMBER-1
		SE_InvokeUserFunction(Func)
	EndIf
	
	if not FromScript Then
		SE_ARGUMENTS_STACK_LEVEL = 0
		SE_ARGUMENTS_NUMBER = 0
	EndIf
	
	publics\params = prevparams
	publics\publicid = previd
	For i = 1 To prevparams
		publics\param[i] = prevparam[i]
		publics\paramtype[i] = prevparamtype[i]
	Next
End Function

Function public_update_by_func(Func.SE_FuncPtr, fromScript% = false, LuaState=0,LuaFuncName$="")
	
	if LuaState <> 0 Then
		public_update_lua(LuaState, LuaFuncName)
		Return
	EndIf
	
	local previd = publics\publicid
	local prevparams = publics\params
	local prevparam$[MAX_PUBLIC_PARAMS]
	local prevparamtype%[MAX_PUBLIC_PARAMS]
	For i = 1 To publics\params
		prevparam[i] = publics\param[i]
		prevparamtype[i] = publics\paramtype[i]
	Next
	
	if Func <> Null Then
		SE_ARGUMENTS_NUMBER = 0
		For i = 1 To publics\params
			if i > Func\Arguments Then Exit
			Select publics\paramtype[i]
				Case SE_STRING: SE_AddStringArg(publics\param[i])
				Case SE_INT: SE_AddIntArg(publics\param[i])
				Case SE_FLOAT: SE_AddFloatArg(publics\param[i])
				Case SE_NULL: SE_AddStringArg(publics\param[i])
			End Select
		Next
		;if fromScript Then SE_ARGUMENTS_NUMBER = SE_ARGUMENTS_NUMBER-1
		SE_InvokeUserFunction(Func)
	EndIf
	if not FromScript Then
		SE_ARGUMENTS_STACK_LEVEL = 0
		SE_ARGUMENTS_NUMBER = 0
	EndIf
	
	publics\params = prevparams
	publics\publicid = previd
	For i = 1 To prevparams
		publics\param[i] = prevparam[i]
		publics\paramtype[i] = prevparamtype[i]
	Next
End Function

Function public_clear()
	publics\params = 0
End Function

Function public_update_lua(luastate, func$)
	local previd = publics\publicid
	local prevparams = publics\params
	local prevparam$[MAX_PUBLIC_PARAMS]
	local prevparamtype[MAX_PUBLIC_PARAMS]
	For i = 1 To publics\params
		prevparam[i] = publics\param[i]
		prevparamtype[i] = publics\paramtype[i]
	Next

	if SLUA_SC_SET_FUNCTION_SINGLE(luastate, func) = 1 Then
		For i = 1 To publics\params
			Select publics\paramtype[i]
				Case SE_STRING: SLUA_PUSH_STRING(luastate, publics\param[i])
				Case SE_INT: SLUA_PUSH_INTEGER(luastate, publics\param[i])
				Case SE_FLOAT: SLUA_PUSH_NUMBER(luastate, publics\param[i])
				Case SE_NULL: SLUA_PUSH_NIL(luastate)
			End Select
		Next
		
		returnvalue$ = SLUA_SC_INVOKE_SINGLE_STR(luastate, publics\params)
		if returnvalue = "0" then SE_RETURN_VALUE\StaticIndex=True
		
		SE_ReturnString(returnvalue)
	EndIf
	
	publics\params = prevparams
	publics\publicid = previd
	For i = 1 To prevparams
		publics\param[i] = prevparam[i]
		publics\paramtype[i] = prevparamtype[i]
	Next
End Function