Type SteamInstances
	Field SteamID%
	Field Tag$
	Field R, G, B
	Field Constant
End Type

InitSteamInstances()

Function CreateSteamInstance(SteamID%, Tag$, R, G, B, Constant% = False)
	Local SI.SteamInstances = New SteamInstances
	SI\SteamID = SteamID%
	SI\Tag = Tag
	SI\R = R
	SI\G = G
	SI\B = B
	SI\Constant = Constant
End Function

Function InitSteamInstances()
	CreateSteamInstance(215311577, "DEVELOPER", 200,0,0, True)
	CreateSteamInstance(888461055, "PATRON", 212, 160, 49, True)
	CreateSteamInstance(163779934, "PATRON", 212, 160, 49, True)
	CreateSteamInstance(213884687, "PATRON", 212, 160, 49, True)
	CreateSteamInstance(871240941, "PATRON", 212, 160, 49, True)
End Function

Function AddSteamInstances(filename$, SteamID%, Tag$, R, G, B)
	Local f = OpenFile(filename)
	SeekFile f, FileSize(filename)
	
	WriteLine f, "ContainTag"
	WriteLine f, SteamID
	WriteLine f, tag
	WriteLine f, r
	WriteLine f, g
	WriteLine f, b
	
	CloseFile f
End Function

Function RequestDataFromGlobal()
	if CentralServer\UDPStream <> 0 Then CloseUDPStream(CentralServer\UDPStream)
	CentralServer\UDPStream = CreateUDPStream()

	TCPTimeouts 10000, 0
	
	tcp = DownloadFile("http://cbmlist.scp-fusion.com/SteamTags.txt")
	If tcp <> 0 Then
		For SI.SteamInstances = Each SteamInstances
			if SI\Constant Then Delete SI
		Next
		While Not Eof(tcp)
			;consolelog readline(tcp),7
			if ReadLine(tcp) = "ContainTag" Then
				local steamid% = ReadLine(tcp)
				local tag$ = ReadLine(tcp)
				local r = ReadLine(tcp)
				local g = ReadLine(tcp)
				local b = ReadLine(tcp)
				CreateSteamInstance(steamid, tag, r, g, b, True)
			EndIf
		Wend
		CloseTCPStream(TCP)
	EndIf
	
	if Not Server\CentralServerTCPRequest Then
		tcp = DownloadFile("http://cbmlist.scp-fusion.com/CentralServersUdp.txt")
		If tcp <> 0 Then
			if ReadLine(tcp) = "ContainServers" then
			
				Delete Each CentralServerSegments
				
				Local cIP$, cPort%
				
				While Not Eof(tcp)	
					cIP$ = ReadLine(tcp)
					cPort = ReadLine(tcp)
					AddCentralServer(cIP, cPort)
				Wend
			endif
			
			
			CloseTCPStream(TCP)
		EndIf
	Else
		StartCentralServerTcpProxy()
		AddCentralServer("127.0.0.1", 12345)
	EndIf
	
	TCPTimeouts 0, 0
End Function

