;Global VALIDATE_AUTH_CALLBACK
;Global Session%
Include "FastTCP.bb"
TCP_Init()

; Codes
Const CENTRALSERVER_REQUESTPLAYERAUTH = 2
Const CENTRALSERVER_ONLINE = 3
Const CENTRALSERVER_REQUESTDATA = 4
Const CENTRALSERVER_EXIT = 5
; ====

Type CentralServerData
	Field Stream%, UDPStream%
	Field Timeout%
	Field TimeoutDelay%
	Field LatestMessage%, MultiserverLatestMessage%
	Field CentralServerPort%
	Field CurrentIP%, CurrentPort%
End Type

Type CentralServerSegments
	Field IP$, Port%, DecimalIP%
End Type

Type AuthConnection
	Field IP%, Port%
	Field Name$, Version$, mwidth%, mheight%, patron%, steamid%
	Field Timeout%
	Field AuthTicket, TicketResult%
	Field GetRequest%
End Type

Global CentralServer.CentralServerData = New CentralServerData
CentralServer\UDPStream = CreateUDPStream()
;Global ValidateAuthTicketResponse_f = 0

;ValidateAuthTicketResponse(0,0,0)
;BS_Callback_Register(BS_Callback_New(ValidateAuthTicketResponse_f), BS_ValidateAuthTicketResponse)
		
Function ValidateAuthTicketResponse(Param1%, Param2%, Param3%)
	if ValidateAuthTicketResponse_f = 0 then
		ValidateAuthTicketResponse_f = BP_GetFunctionPointer()
		Return
	EndIf
	
	m_SteamID = BS_Memory_PeekInt(Param1, 0)
	m_Result = BS_Memory_PeekInt(Param1, 4)
	m_ownerSteamID = BS_Memory_PeekInt(Param1, 8)
End Function

Function SetCentralServer(IP$, Port)
	CentralServer\CurrentIP = IPToDecimal(IP)
	CentralServer\CurrentPort = Port
End Function

Function AddCentralServer(IP$, Port)
	For CentralServers.CentralServerSegments = Each CentralServerSegments
		if CentralServers\IP = IP And CentralServers\Port = Port Then Return
	Next
	CentralServers.CentralServerSegments = New CentralServerSegments
	CentralServers\IP = IP
	CentralServers\Port = Port
	CountHostIPs(IP)
	CentralServers\DecimalIP = HostIP(1)
End Function

Function GetConnectionToCentralServer(CentralServerIP$, CentralServerPort%)
	if CentralServer\UDPStream <> 0 Then CloseUDPStream(CentralServer\UDPStream)
	CentralServer\UDPStream = CreateUDPStream()
	
	Local Stream% = OpenTCPStream(CentralServerIP,CentralServerPort)
	
	if Stream <> 0 Then
		
		WriteByte Stream, CENTRALSERVER_REQUESTDATA
		WriteShort Stream, UDPStreamPort(CentralServer\UDPStream)
		TCP_SendMsg(Stream)
		
		CentralServer\TimeoutDelay = 30000
		CentralServer\LatestMessage = MilliSecs()+CentralServer\TimeoutDelay
		CentralServer\MultiServerLatestMessage = MilliSecs()+CentralServer\TimeoutDelay+12000
	EndIf
	Return Stream
End Function

Function ConnectToCentralServer(CentralServerIP$ = "", CentralServerPort% = 0, ChangeGlobalData% = True)

	Return 0
	if Server\CentralServer Then
		if CentralServer\Stream <> 0 Then CloseTCPStream(CentralServer\Stream)
		
		AddLog("Connecting to central server...", 0, False, True)
		
		if CentralServerIP = "" Then
			For CentralServers.CentralServerSegments = Each CentralServerSegments
			
				CentralServerIP = CentralServers\IP
				CentralServerPort = CentralServers\Port
				
				CentralServer\Stream = GetConnectionToCentralServer(CentralServerIP,CentralServerPort)
				
				if CentralServer\Stream <> 0 Then Exit
			Next
		Else
			CentralServer\Stream = GetConnectionToCentralServer(CentralServerIP,CentralServerPort)
		EndIf
		
		if CentralServer\Stream <> 0 Then 
			AddLog("Connected successfully!", 0, False, True)
			; =====
			SetCentralServer(CentralServerIP, CentralServerPort)
			Return 1
		Else
			AddLog("Failed to connect to central server", 0, False, True)
			if GetScripts() Then public_inqueue(public_OnLostConnectionWithCentralServer, True)
			
			if ChangeGlobalData Then 
				For CentralServers.CentralServerSegments = Each CentralServerSegments
					CentralServerIP = CentralServers\IP
					CentralServerPort = CentralServers\Port
					Exit
				Next
				SetCentralServer(CentralServerIP, CentralServerPort)
			EndIf
			Return 0
		EndIf
	Else
		;BS_SteamAPI_Init()
		STEAM_RELEASE = BS_SteamGameServer_Init(0, 0, 0, 0, 3, Version)
		if STEAM_RELEASE Then
			BS_ISteamGameServer_LogOnAnonymous(BS_SteamGameServer())
			BS_ISteamGameServer_InitGameServer(BS_SteamGameServer(), 0, 0, 0, 3, 1782380, "SCP:CB:Server")
			AddLog("SteamGameServer successfully started.", 0, False, True)
		Else
			AddLog("SteamGameServer initing failed.", 0, False, True)
		EndIf
		Return STEAM_RELEASE
	EndIf
End Function

Function DisconnectFromCentralServer()
	WriteByte(CentralServer\UDPStream, CENTRALSERVER_EXIT)
	WriteShort(CentralServer\UDPStream, UDPStreamPort(CentralServer\UDPStream))
	SendUDPMsg(CentralServer\UDPStream, CentralServer\CurrentIP, CentralServer\CurrentPort)
	
	CloseUDPStream(CentralServer\UDPStream)
	CentralServer\UDPStream = CreateUDPStream()
	
	CentralServer\LatestMessage = MilliSecs()
End Function

Function UpdateSteamAuthConnections()

	For AC.AuthConnection = Each AuthConnection
		if AC\Timeout-1500 < MilliSecs() Then
			
			if not AC\GetRequest then
				if AC\TicketResult > 0 Then
				
					if GetScripts() Then
						public_inqueue(public_OnBadIncomingConnection)
						public_addparam(0, AC\name, SE_STRING)
						public_addparam(0, DottedIP(AC\IP), SE_STRING)
						public_addparam(0, AC\steamid)
						public_addparam(0, AC\version, SE_STRING)
						public_addparam(0, AC\patron)
						public_addparam(0, -1)
						callback
					EndIf
					
					if SE_GetReturnValue() = -1 Or SE_GetReturnValue() > 0 Then AddLog(AC\name+" bad authorization ticket ["+AC\TicketResult+"]: "+DottedIP$(AC\IP), 0, False)

					SendServerDataToPlayer(AC\IP, AC\Port)
					AC\GetRequest = True
				else
					if SE_GetReturnValue() = -1 Or SE_GetReturnValue() > 0 Then AddLog(AC\name+" bad authorization ticket [Central server time out]: "+DottedIP$(AC\IP), 0, False)
					RemoveAuthConnection(ac)
				endif
			endif
		EndIf
		
		if AC\Timeout < MilliSecs() Then
			RemoveAuthConnection(ac)
		EndIf
	Next

	While True
		Received = RecvUDPMsg(CentralServer\UDPStream)
		if Received <> 0 Then
			For CentralServers.CentralServerSegments = Each CentralServerSegments
				if Received = CentralServers\DecimalIP Then
					Select ReadByte(CentralServer\UDPStream)
						Case CENTRALSERVER_REQUESTPLAYERAUTH
							AC.AuthConnection = Object.AuthConnection(ReadInt(CentralServer\UDPStream))
							if AC <> Null Then 
								if not AC\GetRequest Then
									AC\TicketResult = ReadByte(CentralServer\UDPStream)
									
									if AC\TicketResult = 0 Then
										AC\GetRequest = True
										SendServerDataToPlayer(AC\IP, AC\Port)
									EndIf
								EndIf
							EndIf
					End Select
					Exit
				EndIf
			Next
		Else
			Exit
		EndIf
	Wend
End Function

Function RemoveAuthConnection(AC.AuthConnection)
	if AC\AuthTicket <> 0 Then FreeBank AC\AuthTicket
	Delete AC
End Function

Function EstablishCentralServerConnection(IP, Port)
	
	;if CountHostIPs(DottedIP(IP)) <> 0 Then
	WriteByte(CentralServer\UDPStream, CENTRALSERVER_ONLINE)
	SendUDPMsg(CentralServer\UDPStream, IP, Port)
		;CentralServer\CurrentIP = HostIP(1)
	;EndIf
End Function
