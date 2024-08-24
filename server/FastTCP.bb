TCPTimeouts 0, 0

Const MAX_TCP_MEM = 8388608/2

Type fasttcps
	Field CurrentRecvBank
	Field CurrentRecvMemory
	Field CurrentRecvBankSize%
	Field TCPReaded%
	Field TCPMsgCode%
End Type

Global FastTCP.fasttcps = New fasttcps

Function TCP_Init()
	FastTCP\CurrentRecvBank = CreateBank(MAX_TCP_MEM)
	FastTCP\CurrentRecvMemory = CreateBank(0)
	FastTCP\TCPMsgCode = 2088929492
End Function

Function TCP_Clear()
	FastTCP\CurrentRecvBankSize = 0
	FastTCP\TCPReaded = 0
	Return True
End Function

Function TCPRecvMsg(tcpstream)
	if ReadAvail(tcpstream) >= 8 Then
	
		if FastTCP\CurrentRecvBankSize >= MAX_TCP_MEM Then ResizeBank(FastTCP\CurrentRecvBank, 0)

		While ReadAvail(TCPStream) > 0
			PokeByte(FastTCP\CurrentRecvBank, FastTCP\CurrentRecvBankSize, ReadByte(tcpstream))
			FastTCP\CurrentRecvBankSize = FastTCP\CurrentRecvBankSize + 1
			
			; ============================================== If message sended
			if FastTCP\CurrentRecvBankSize >= 8 Then
				if PeekInt(FastTCP\CurrentRecvBank, FastTCP\CurrentRecvBankSize-4) = FastTCP\TCPMsgCode-1 Then
					if PeekInt(FastTCP\CurrentRecvBank, FastTCP\CurrentRecvBankSize-8) = FastTCP\TCPMsgCode Then
						ResizeBank(FastTCP\CurrentRecvMemory, FastTCP\CurrentRecvBankSize-8)
						CopyBank(FastTCP\CurrentRecvBank, 0, FastTCP\CurrentRecvMemory, 0, FastTCP\CurrentRecvBankSize-8)
						
						Return TCP_Clear()
					EndIf
				EndIf
			EndIf
		Wend
	EndIf
	Return False
End Function


Function TCP_SendMsg(tcpstream)
	WriteInt(tcpstream, FastTCP\TCPMsgCode)
	WriteInt(tcpstream, FastTCP\TCPMsgCode-1)
End Function

Function TCP_ReadByte%()
	if FastTCP\TCPReaded+1 > BankSize(FastTCP\CurrentRecvMemory) Then Return 0
	Values% = PeekByte(FastTCP\CurrentRecvMemory, FastTCP\TCPReaded)
	FastTCP\TCPReaded = FastTCP\TCPReaded + 1
	Return Values%
End Function
Function TCP_ReadShort%()
	if FastTCP\TCPReaded+2 > BankSize(FastTCP\CurrentRecvMemory) Then Return 0
	Values% = PeekShort(FastTCP\CurrentRecvMemory, FastTCP\TCPReaded)
	FastTCP\TCPReaded = FastTCP\TCPReaded + 2
	Return Values%
End Function
Function tcp_ReadInt%()
	if FastTCP\TCPReaded+4 > BankSize(FastTCP\CurrentRecvMemory) Then Return 0
	Values% = PeekInt(FastTCP\CurrentRecvMemory, FastTCP\TCPReaded)
	FastTCP\TCPReaded = FastTCP\TCPReaded + 4
	Return Values%
End Function
Function tcp_ReadFloat#()
	if FastTCP\TCPReaded+4 > BankSize(FastTCP\CurrentRecvMemory) Then Return 0
	Values# = PeekFloat(FastTCP\CurrentRecvMemory, FastTCP\TCPReaded)
	FastTCP\TCPReaded = FastTCP\TCPReaded + 4
	Return Values#
End Function

Function tcp_ReadLine$()
	Local ByteValue%
	Local Value$
	While True
		ByteValue = tcp_ReadByte()
		if ByteValue = 0 Or ByteValue = 10 Then Exit
		if ByteValue <> 13 Then Value = Value + Chr(ByteValue)
		
		;DebugLog "Value: "+Chr(ByteValue)
	Wend
	Return Value
End Function
Function tcp_ReadString$()
	Local ByteValue% = tcp_ReadInt()
	Local Value$
	For i = 1 To ByteValue
		Value = Value + Chr(tcp_ReadByte())
	Next
	Return Value
End Function

Function tcp_ReadBytes(buffer%, offset%, size%)
	local toread% = size
	if size > tcp_ReadAvail() Then toread = tcp_ReadAvail()
	CopyBank FastTCP\CurrentRecvMemory, FastTCP\TCPReaded, buffer, offset, toread
	FastTCP\TCPReaded = FastTCP\TCPReaded + toread
	Return toRead
End Function

Function tcp_ReadAvail()
	Return BankSize(FastTCP\CurrentRecvMemory)-FastTCP\TCPReaded
End Function

; ============================================ TCP
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

	if CountHostIPs(webHost) <> 0 Then
		www = OpenTCPStream (webHost$, 80)
		if Not www Then Return 0
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