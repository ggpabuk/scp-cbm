; ===================== Skynet++ public system
; ===================== Publics ID
const public_OnLoadingFonts = 1
const public_OnLaunchGame = 2
const public_OnSpawn = 4
const public_OnUpdate = 5
const public_OnDead = 6
const public_OnRenderWorld = 7
const public_OnConsole = 10
const public_OnClickWorkshopLanguage = 15
const public_OnReceiveRawPacket = 16
const public_OnDisconnect = 17
const public_OnSendMessage = 18
const public__main = 19
const public_OnPressUIButton = 20
const public_OnReceivedFile = 21
const public_OnUsingItem = 22
const public_OnInitItemTemplates = 23

function public_getid(name$)
	select name
		case "OnLaunchGame": Return public_OnLaunchGame
		case "OnRenderWorld": Return public_OnRenderWorld
		case "OnLoadingFonts": Return public_OnLoadingFonts
		case "OnConsole": Return public_OnConsole
		case "OnSpawn": Return public_OnSpawn
		case "OnUpdate": Return public_OnUpdate
		case "OnDead": Return public_OnDead
		case "OnClickWorkshopLanguage": Return public_OnClickWorkshopLanguage
		case "OnReceiveRawPacket": Return public_OnReceiveRawPacket
		case "OnDisconnect": Return public_OnDisconnect
		case "OnSendMessage": Return public_OnSendMessage
		case "_main": Return public__main
		case "OnPressUIButton": Return public_OnPressUIButton
		case "OnReceivedFile": Return public_OnReceivedFile
		case "OnUsingItem": Return public_OnUsingItem
		case "OnInitItemTemplates": Return public_OnInitItemTemplates
	end select
end function

function public_getname$(publicid)
	select publicid
		case public_OnLaunchGame: Return "OnLaunchGame"
		case public_OnDead: Return "OnDead"
		case public_OnLoadingFonts: Return "OnLoadingFonts"
		case public_OnConsole: Return "OnConsole"
		case public_OnSpawn: Return "OnSpawn"
		case public_OnUpdate: Return "OnUpdate"
		case public_OnRenderWorld: Return "OnRenderWorld"
		case public_OnClickWorkshopLanguage: Return "OnClickWorkshopLanguage"
		case public_OnReceiveRawPacket: Return "OnReceiveRawPacket"
		case public_OnDisconnect: Return "OnDisconnect"
		case public_OnSendMessage: Return "OnSendMessage"
		case public__main: Return "_main"
		case public_OnPressUIButton: Return "OnPressUIButton"
		case public_OnReceivedFile: Return "OnReceivedFile"
		case public_OnUsingItem: Return "OnUsingItem"
		case public_OnInitItemTemplates: Return "OnInitItemTemplates"
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
Function public_addparam(param$, valuetype = SE_INT)
	publics\params = publics\params+1
	publics\param[publics\params] = param
	publics\paramtype[publics\params] = valuetype
End Function

Function public_update_current(script.SE_Script, fromScript% = False)
	; Add
	local previd = publics\publicid
	local prevparams = publics\params
	local prevparam$[MAX_PUBLIC_PARAMS]
	local prevparamtype[MAX_PUBLIC_PARAMS]
	For i = 1 To publics\params
		prevparam[i] = publics\param[i]
		prevparamtype[i] = publics\paramtype[i]
	Next
	
	Local Func.SE_FuncPtr = script\ScriptPublic[publics\publicid]

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
	
	; Clear
	publics\params = prevparams
	publics\publicid = previd
	For i = 1 To prevparams
		publics\param[i] = prevparam[i]
		publics\paramtype[i] = prevparamtype[i]
	Next
End Function

Function public_update_by_func(Func.SE_FuncPtr, fromScript% = false)
	; Add
	local previd = publics\publicid
	local prevparams = publics\params
	local prevparam$[MAX_PUBLIC_PARAMS]
	local prevparamtype[MAX_PUBLIC_PARAMS]
	For i = 1 To publics\params
		prevparam[i] = publics\param[i]
		prevparamtype[i] = publics\paramtype[i]
	Next
	
	; Execute
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
	
	; Clear
	publics\params = prevparams
	publics\publicid = previd
	For i = 1 To prevparams
		publics\param[i] = prevparam[i]
		publics\paramtype[i] = prevparamtype[i]
	Next
End Function

Function public_clear()

End Function