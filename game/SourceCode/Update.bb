; ID: 1816
; Author: Andres
; Date: 2006-09-18 11:05:21
; Title: Remote File Engine
; Description: Easy way to download and read remote files via HTTP/FTP

Global UserAgent$ = "SCPCB"	; What ever you'd like to name your application
Global ResponseDelay% = 2000		; How many millisecs to wait for HTTP response
Global NewLine$ = Chr(13) + Chr(10)	; Line break may differ
Const RFDebugMode% = True				; Debug mode
Const versionfile$ = "" ; in version file first line must be ( v0.5.8p - example )



Global UpdateCheckEnabled% = GetINIInt(OptionFile, "options", "check for updates")

Type transporter
	Field id%
	Field code%
	Field filename$
	Field protocol$
End Type

Function OpenRemoteFile%(path$, port% = 80, variables$ = "", httpheader$ = "")
	Local protocol$ = Lower(Sector$(path$, ":", 0))
	Local host$ = Sector$(path$, "/", 2)
	Local ip$ = "www." + Sector$(host$, ".", Sectors%(host$, ".") - 1) + "." + Sector$(host$, ".", Sectors%(host$, "."))
	path$ = "/" + Sector$(path$, "/", 3, True)
	

	Local header%, bank%, tim%, occ%
	
	host = "https://"+host
	Local Stream% = OpenTCPStream(host$, port%)

	If Not Stream% Then Stream% = OpenTCPStream(host, 80)
	
	If Stream
		Select protocol$
			Case "http", "https" ; ---------------------------------------------------------------------------------------------------------------------------------------
				; Send request
				DebugLog path
				If Not Len(variables$) Then
					WriteLine(Stream, "GET " + path$ + " HTTP/1.1")
				Else
					WriteLine(Stream, "POST " + path$ + " HTTP/1.1")
				EndIf
				WriteLine(Stream, "Host: " + host$)
				WriteLine(Stream, "User-Agent: " + UserAgent$)
				If Len(variables$) Then
					WriteLine(Stream, "Content-Type: application/x-www-form-urlencoded")
					WriteLine(Stream, "Content-Length: " + Len(variables$))
				EndIf
				If Len(httpheader$) Then WriteLine(Stream, httpheader$)
				WriteLine(Stream, "Connection: keep-alive")
				WriteLine(Stream, "")
				If Len(variables$) Then WriteLine(Stream, variables$)
				; Debug mode
				If RFDebugMode% Then
					If Not Len(variables$) Then DebugLog ">>> GET " + path$ + " HTTP/1.1" Else DebugLog ">>> POST " + path$ + " HTTP/1.1"
					DebugLog ">>> Host: " + host$
					DebugLog ">>> User-Agent: " + UserAgent$
					If Len(variables$) Then
						DebugLog ">>> Content-Type: application/x-www-form-urlencoded"
						DebugLog ">>> Content-Length: " + Len(variables$)
					EndIf
					If Len(httpheader$) Then DebugLog ">>> " + httpheader$
					DebugLog ">>> Connection: Close"
					DebugLog ">>> "
					If Len(variables$) Then DebugLog ">>> " + variables$
				EndIf
				
				; Wait for response
				tim = MilliSecs()
				Repeat
				Until (MilliSecs() - tim) => ResponseDelay% Or ReadAvail(Stream)
				
				txt$ = ReadLine$(Stream%)
				DebugLog txt

				If RFDebugMode Then DebugLog "<<< " + txt$
				If Sector$(txt$, " ", 0) = "HTTP/1.1" Or Sector$(txt$, " ", 0) = "HTTP/1.0"
					code% = Int Sector$(txt$, " ", 1)
					If (code% => 300) Then
						CloseTCPStream Stream%
						Return False
					EndIf
				EndIf
				
				occ% = 0
				header% = CreateBank(0)
				bank% = CreateBank(12); Bank Int, Stream Int, Size Int
				
				PokeInt(bank%, 0, header%)
				PokeInt(bank%, 4, Stream%)
				PokeInt(bank%, 8, -1)
				
				this.transporter = New transporter
				this\id% = bank%
				this\code% = code%
				this\filename$ = Sector$(path$, "/", Sectors%(path$, "/"))
				this\protocol$ = "http"
				
				; Received HTTP Header
				Repeat
					txt$ = ReadRemoteLine$(bank%)
					value$ = Mid$(Sector$(txt$, ":", 1, True), 2)
					
					If RFDebugMode Then DebugLog "<<< " + txt$
					
					Select Sector$(txt$, ":", 0)
						Case ""
							Return bank%
						Case "Content-Disposition"
							For i = 0 To Sectors(value$, ";")
								subtxt$ = Trim$(Sector$(value$, ";", i))
								subvalue$ = Sector$(subtxt$, "=", 1)
								
								Select Sector$(subtxt$, "=", 0)
									Case "filename"
										this\filename$ = subvalue$
										DebugLog "[" + this\filename$ + "]"
								End Select
							Next
						Case "Content-Length"
							PokeInt bank%, 8, Int(value$)
					End Select
				Forever
			Case "ftp" ; ---------------------------------------------------------------------------------------------------------------------------------------
				header% = CreateBank(1)
				bank% = CreateBank(16); Bank Int, Stream Int, Size Int
				
				PokeInt(bank%, 0, header%)
				PokeInt(bank%, 4, Stream%)
				PokeInt(bank%, 8, -1)
				PokeInt(bank%, 12, Stream%)
				
				this.transporter = New transporter
				this\id% = bank%
				this\code% = "0"
				this\filename$ = Sector$(path$, "/", Sectors(path$, "/"))
				this\protocol$ = "ftp"
				
				; Wait for response
				tim = MilliSecs()
				Repeat
				Until (MilliSecs() - tim) => ResponseDelay% Or ReadAvail(Stream)
				
				Repeat
					If ReadAvail(Stream%)
						txt$ = ReadLine(Stream%)
						code% = Int Sector$(txt$, " ", 0)
						value$ = Mid$(Sector$(txt$, " ", 1, True), 1)
						If RFDebugMode Then DebugLog "<<< " + txt$
					Else
						txt$ = "":code% = 0:value$ = "":cmd$ = ""
					EndIf
					
					If Not ReadAvail(Stream%)
						Select code%
							Case 220
								cmd$ = "USER " + variables$
							Case 331
								cmd$ = "PASS " + httpheader$
							Case 230
								cmd$ = "SIZE " + path$
							Case 213
								PokeInt(bank%, 8, Int value$)
								cmd$ = "PASV"
							Case 227
								; Connect to PASV mode
								txt$ = Sector(txt$, "(", 1)
								host$ = Sector(txt$, ",", 0) + "." + Sector(txt$, ",", 1) + "." + Sector(txt$, ",", 2) + "." + Sector(txt$, ",", 3)
								port% = (Int Sector(txt$, ",", 4)) * 256 + (Int Left$(Sector(txt$, ",", 5), Len(Sector(txt$, ",", 5)) - 1))
								pasv_stream% = OpenTCPStream(host$, port%)
								
								; Update stream
								PokeInt(bank%, 4, pasv_stream%)
								
								cmd$ = "RETR " + path$
							Case 150
								Return bank%
						End Select
						
						; ERROR
						If code% => 400
							If Stream% Then CloseTCPStream Stream%
							If pasv_stream% Then CloseTCPStream pasv_stream%
							If header% Then FreeBank header%
							If bank% Then FreeBank bank%
							Return 0
						EndIf
					EndIf
					
					If Len(cmd$) Then
						If RFDebugMode% Then DebugLog ">>> " + cmd$
						WriteLine Stream%, cmd$
					EndIf
				Forever
				
				Return bank%
		End Select
	EndIf
End Function

Function CloseRemoteFile(bank%)
	If PeekInt(bank%, 0) Then FreeBank PeekInt(bank%, 0)
	If PeekInt(bank%, 4) Then CloseTCPStream PeekInt(bank%, 4)
	If BankSize(bank%) = 18 Then If PeekInt(bank%, 12)
		WriteLine PeekInt(bank%, 12), "BYE"
		If RFDebugMode% Then DebugLog ">>> BYE"
		CloseTCPStream PeekInt(bank%, 12)
	EndIf
	FreeBank bank%
	
	For this.transporter = Each transporter
		If this\id% = bank%
			Delete this
			Exit
		EndIf
	Next
End Function

Function EORF(bank%)
	If Not PeekInt(bank%, 4)
		If BankSize(PeekInt(bank%, 0)) = 0 Then Return True
	Else
		If Eof(PeekInt(bank%, 4)) Then Return True
	EndIf
End Function

Function RemoteFileSize%(bank%)
	Return PeekInt(bank%, 8)
End Function

Function RemoteFileName$(bank%)
	For this.transporter = Each transporter
		If this\id% = bank% Return this\filename$
		Next
End Function

Function RemoteFileCode%(bank%)
	For this.transporter = Each transporter
		If this\id% = bank% Return this\code%
		Next
End Function

Function RemoteFileProtocol$(bank%)
	For this.transporter = Each transporter
		If this\id% = bank% Return this\protocol$
		Next
End Function

Function ReadRemoteLine$(bank%)
	Local avail%, rbank% = PeekInt(bank%, 0), stream% = PeekInt(bank%, 4)
	
	; Update bank
	UpdateRemoteFile(bank%)
	
	; Read line
	txt$ = ""
	For i = 0 To BankSize(rbank%) - 1
		char% = PeekByte(rbank%, i)
		txt$ = txt$ + Chr(char%)
		If Right$(txt$, Len(NewLine$)) = NewLine$ Then
			Exit
		ElseIf Right$(txt$,1) = Chr(10) Then
			Exit
		EndIf
	Next
	
	If Right$(txt$, 2) = NewLine$ Then
		txt$ = Mid$(txt$, 1, Len(txt$) - Len(NewLine$))
	ElseIf Right$(txt$, 1) = Right$(NewLine$, 1) Then
		txt$ = Mid$(txt$, 1, Len(txt$) - (Len(NewLine$) - 1))
	EndIf
	
	; Resize bank
	size% = BankSize(rbank%) - (Len(txt$) + Len(NewLine$))
	If size% < 0 Then size% = 0
	ResizeRemoteBank(bank%, size%)
	
	Return txt$
End Function

Function ReadRemoteString$(bank%)
	Local rbank% = PeekInt(bank%, 0)
	UpdateRemoteFile(bank%)
	
	a% = PeekInt(rbank%, 0)
	
	txt$ = ""
	For i = 0 To a% - 1
		txt$ = txt$ + Chr(PeekByte(rbank%, 4 + i))
	Next
	
	ResizeRemoteBank(bank%, BankSize(rbank%) - (4 + a%))
	Return txt$
End Function

Function ReadRemoteInt%(bank%)
	Local rbank% = PeekInt(bank%, 0)
	UpdateRemoteFile(bank%)
	
	a% = PeekInt(rbank%, 0)
	ResizeRemoteBank(bank%, BankSize(rbank%) - 4)
	Return a%
End Function

Function ReadRemoteShort%(bank%)
	Local rbank% = PeekInt(bank%, 0)
	UpdateRemoteFile(bank%)
	
	a% = PeekShort(rbank%, 0)
	ResizeRemoteBank(bank%, BankSize(rbank%) - 2)
	Return a%
End Function

Function ReadRemoteByte%(bank%)
	Local rbank% = PeekInt(bank%, 0)
	UpdateRemoteFile(bank%)
	
	a% = PeekByte(rbank%, 0)
	ResizeRemoteBank(bank%, BankSize(rbank%) - 1)
	Return a%
End Function

Function WriteRemoteBytes(bank%, file%, offset%, count%)
	Local rbank% = PeekInt(bank%, 0)
	UpdateRemoteFile(bank%)
	
	Local N% = WriteBytes(rbank%, file%, offset%, count%)
	ResizeRemoteBank(bank%, BankSize(rbank%) - N%)
	
	Return N%
End Function

Function RemoteReadAvail(bank%)
	UpdateRemoteFile(bank%)
	received% = BankSize(PeekInt(bank%, 0))
	If PeekInt(bank%, 4) Then waiting% = ReadAvail(PeekInt(bank%, 4)) Else waiting% = 0
	Return received% + waiting%
End Function

Function UpdateRemoteFile(bank%)
	Local rbank% = PeekInt(bank%, 0), stream% = PeekInt(bank%, 4)
	
	If stream% And rbank%
		avail% = ReadAvail(stream%)
		offset% = BankSize(rbank%)
		
		ResizeBank(rbank%, offset% + avail%)
		ReadBytes(rbank%, stream%, offset%, avail%)
		
		If Eof(stream%) Then
			CloseTCPStream stream%
			PokeInt bank%, 4, 0
		EndIf
	EndIf
End Function

Function ResizeRemoteBank(bank%, size%)
	Local rbank% = PeekInt(bank%, 0), start% = BankSize(rbank%) - size%
	
	If BankSize(rbank%)
		CopyBank(rbank%, start%, rbank%, 0, size%)
		ResizeBank(rbank%, size%)
	EndIf
End Function

Function Sector$(txt$, separator$, Sector%, toend% = False)
	Local result$ = "", occ
	For i = 1 To Len(txt$)
		If Mid$(txt$, i, 1) = separator$
			occ = occ + 1
			If toend% And occ% > Sector% Then result$ = result$ + Mid$(txt$, i, 1)
		Else
			If occ => Sector Then result$ = result$ + Mid$(txt$, i, 1)
		EndIf
		If Not toend% Then If occ > Sector Then Exit
	Next
	Return result$
End Function

Function Sectors%(txt$, needle$)
	occ% = 0
	For i = 1 To Len(txt$) Step 1
		If Instr(txt$, needle$, i)
			occ% = occ% + 1
			i = Instr(txt$, needle$, i)
		Else
			Exit
		EndIf
	Next
	Return occ%
End Function




;=============================================================================================


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
	WriteLine tcp, "GET " + link_path$ + link_file$ + " HTTP/1.1" + Chr(13) + Chr(10) + "Host: " + host$ + Chr(13) + Chr(10) + "User-Agent: SCP-CB Multiplayer Mod" + Chr(13) + Chr(10) + "Connection: keep-alive" + Chr(13) + Chr(10)
	
;Download file
	l$ = ReadLine(tcp)
	if l = "" Then
		CloseTCPStream tcp
		Return False
	EndIf
	
	inst1 = Instr(l$, " ")
	if inst1 = 0 Then
		CloseTCPStream tcp
		Return False
	EndIf
	inst2 = Instr(l$, " ", inst1 + 1)
	if inst2 = 0 Then
		CloseTCPStream tcp
		Return False
	EndIf
	num = Mid(l$, inst1, inst2 - inst1)
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
			
				if filetype(savepath) = 0 Then
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
						
						;draw the progress bar
						
						SetBuffer BackBuffer()
						Cls
						
						Text 5,5,"Downloading "+latest
						
						Color 255,255,255
						Text 5,165,Str(Floor(((Float(pos)/1024.0)/1024.0)*100.0)/100.0)+"MB out of "+Str(Floor(((Float(conlen)/1024.0)/1024.0)*100.0)/100.0)+"MB downloaded"
						
						Rect 14,198,614,18,False
						For i=0 To Int((Float(pos)/Float(conlen))*61.0)-1
							DrawImage(BlinkMeterIMG, (i*10)+17, 200)
						Next
						
						Text 320,230,Int((Float(pos)/Float(conlen))*100.0)+"%",True,False
						
						If DrawButton2(270,400,100,20,"Cancel",False) Then
							CloseTCPStream(tcp)
							FreeBank bnk
							CloseFile file
							Return -1
						EndIf
						
						Flip False
						
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

Global UpdaterBG

Type ChangeLogLines
	Field txt$
End Type

Global UpdaterIMG

Function CheckForUpdates()
	
	;If GetINIInt(OptionFile,"box_of_horrors","check for updates")=False Then Return
	; Reset
	Local changelogname$ = "ch", links$ = "links"
	DeleteFile changelogname
	DeleteFile links
	;
	
	AppTitle "SCP - Containment Breach Multiplayer Updater"

	If (Not UpdateCheckEnabled) Or STEAM_RELEASE Then Return
	
	SetBuffer BackBuffer()
	Cls
	Color 255,255,255
	Text 320,240,"Checking for updates...",True,True
	Flip
	
	Local Hosting$ = MULTIPLAYER_MOD_HOST
	
	Local updateCheck% = Download(Hosting+"MPChangeLogOld.txt", changelogname)
	If updateCheck=0 Then 
		Hosting = MULTIPLAYER_MOD_HOST_RESERVE
		updateCheck% = Download(Hosting+"MPChangeLogOld.txt", changelogname)
		if updatecheck =0 then Return ;remote file couldn't be opened
	endif
	
	f = ReadFile(changelogname)
	latest$ = ReadLine(f)
	latest$ = Right(latest, Len(latest)-Instr(latest, "v"))
	CloseFile f
	
	If FileType("updates")=0 CreateDir "updates"
	
	DebugLog "LATEST VERSION: "+latest
	
	Local ConfirmDownload%=0
	Local DownloadURLs$[64]
	Local mpversion$ = MULTIPLAYER_VERSION
	DebugLog "MULTIPLAYER VERSION: "+mpversion
	If latest<>mpversion And latest<>"" Then ;a new version is available!
		if Download(Hosting+"links.txt", links) = 0 Then Return False
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
		
		UpdaterBG = LoadImage_Strict("GFX\menu\updater.jpg")
		UpdaterIMG = CreateImage(452,254)
		
		Local ChangeLogURL$ = versionfile
		Local ChangeLogFile = ReadFile(changelogname)
		Local ChangeLogLinesCount
		While Not Eof(ChangeLogFile)
			l$ = ReadLine(ChangeLogFile)
			If Left(l,5)<>"-----"
				chl.ChangeLogLines = New ChangeLogLines
				If Right(l, Len(l)-Instr(l, "v")) = latest
					chl\txt$ = "NEW UPDATE: "+l
				Else
					chl\txt$ = l
				EndIf
				
			Else
				Exit
			EndIf
		Wend
		CloseFile(ChangeLogFile)
		DeleteFile(changelogname)
		UpdaterFont = LoadFont("GFX\font\cour\Courier New.ttf",16,0,0,0)
		Local linesamount = 0
		Repeat
			SetBuffer BackBuffer()
			Cls
			Color 255,255,255
			MouseHit1 = MouseHit(1)
			MouseDown1 = MouseDown(1)
			DrawImage UpdaterBG,0,0
			DebugLog ChangeLogLinesCount
			SetFont UpdaterFont
			If LinesAmount > 13
				y# = 200-(20*ScrollMenuHeight*ScrollBarY)
				LinesAmount%=0
				SetBuffer(ImageBuffer(UpdaterIMG))
				DrawImage UpdaterBG,-20,-195
				For chl.ChangeLogLines = Each ChangeLogLines
					Color 0,0,0
					If Right(chl\txt, Len(chl\txt)-Instr(chl\txt, "v")) = latest
						Color 200,0,0
					EndIf
					RowText2(chl\txt$,2,y#-195,430,254)
					y# = y#+(20*GetLineAmount2(chl\txt$,430,254))
					LinesAmount = LinesAmount + (GetLineAmount2(chl\txt$,430,254))
				Next
				SetBuffer BackBuffer()
				DrawImage UpdaterIMG,20,195
				Color 10,10,10
				Rect 452,195,20,254,True
				ScrollMenuHeight# = LinesAmount/1.2

				ScrollBarY = DrawScrollBar(452,195,20,254,452,195+(254-(254-4*ScrollMenuHeight))*ScrollBarY,20,254-(4*ScrollMenuHeight),ScrollBarY,1)
			Else
				y# = 200
				LinesAmount%=0
				Color 0,0,0
				For chl.ChangeLogLines = Each ChangeLogLines
					RowText2(chl\txt$,20,y#,432,254)
					y# = y#+(20*GetLineAmount2(chl\txt$,432,254))
					LinesAmount = LinesAmount + (GetLineAmount2(chl\txt$,432,254))
					ChangeLogLinesCount = LinesAmount
				Next
				ScrollMenuHeight# = LinesAmount
			EndIf
			Color 255,255,255
			
			SetFont Font1
			If DrawButton(LauncherWidth - 30 - 100, LauncherHeight - 50 - 55, 100, 30, "UPDATE", False, False, False)
				SendStatisticRequest(PLAYER_UPDATE_GAME)
				ConfirmDownload=True
				Cls
				Color 255,255,255
				Text 320,240,"Starting download...",True,True
				Flip
				Delay 100
				Exit
			EndIf
			
			If DrawButton(LauncherWidth - 55 - 100, LauncherHeight - 50 - 110, 145, 30, "VISIT WEBSITE", False, False, False)
				SendStatisticRequest(PLAYER_GO_SITE)
				ExecFile("https://store.steampowered.com/app/1782380")
			EndIf
			
			If DrawButton(LauncherWidth - 30 - 100, LauncherHeight - 50, 100, 30, "IGNORE", False, False, False)
				SendStatisticRequest(PLAYER_IGNORE_UPDATE)
				Delay 100
				Exit
			EndIf
			
			Flip
			if not api_IsWindowVisible(SCREEN_HWND) then api_showWindow(SCREEN_HWND, 1)
			
			Delay 8
			
		Forever
	EndIf
	If ConfirmDownload Then
		DeleteFile "updates\"+latest+".zip"
		Local temp%
		For i = 0 To 63
			if DownloadURLs[i] <> "" Then
				temp=Download(DownloadURLs[i],"updates\"+latest+".zip",latest+".zip"+str(i+1))
				if not temp then exit
			EndIf
		Next
		If temp>0 Then
			
			If FileSize("updates\"+latest+".zip")<>0 Then
				Local newRun$ = ""
				Local zipIn = ZipApi_Open("updates\"+latest+".zip")
				
				DebugLog zipIn
				;Local temp%
				
				temp = ZipApi_GotoFirstFile(zipIn)+"a"
				;Repeat
				While (Not temp)
					
					SetBuffer(BackBuffer())
					Cls
					Color 255,255,255
					
					Local fileData$	= ""
					Local fileInfo.ZIPAPI_UnzFileInfo	= ZipApi_GetCurrentFileInfo(zipIn)
					If fileInfo<>Null Then	
						fileData$ = fileInfo\FileName
						sizefile = fileinfo\UnCompressedSize
						
						ZIPAPI_UnzFileInfo_Dispose(fileInfo)
						if FileSize(filedata) <> sizefile Then
							
							If (Right(fileData,1)<>"/") And (Right(fileData,1)<>"\") Then ;fileData refers to a file, not a directory
								If Not Instr(fileData,"zlibwapi.dll") Then
									ZipApi_ExtractFile(zipIn, fileData, fileData)
								EndIf
							Else If FileType(fileData)=0
								CreateDir fileData
							EndIf
						EndIf
						If Instr(fileData,".exe")>0 And Instr(fileData, latest) Then
                            newRun=filedata
                        EndIf
					EndIf
					if filedata <> "" Then Text 320,240,"Extracting "+Chr(34)+fileData+Chr(34),True,True
					Flip
					temp%=ZipApi_GotoNextFile(zipIn)
					
					DebugLog temp
					
				Wend
				ZipApi_Close(zipIn)

				ExecFile("SCP - Containment Breach Multiplayer Mod v"+latest+".exe")

				End ;close the game
				
			EndIf
		ElseIf temp=-1 ;download cancelled by user
			Delay 100
			
			Return
		Else
			
			SetBuffer BackBuffer()
			Cls
			Color 255,255,255
			Text 320,240,"An error has occurred while downloading the update.",True,True
			Flip
			
			Delay 1000
			
			Return
		EndIf
	EndIf
	Delete Each ChangeLogLines
	If UpdaterIMG <> 0 Then FreeImage UpdaterIMG
	
	DeleteFile changelogname
	DeleteFile links
End Function

Function GetSelfEXEName$() ;use this to make the new version delete the old exe
	Local lpFileName% = CreateBank(256)
	Local str_len% = api_GetModuleFileName(0,lpFileName,BankSize(lpFileName))
	
	Local exe_name$=""
	
	If str_len > 0 Then
		For grab_exe_name = 0 To str_len-1
			exe_name$ = exe_name + Chr(PeekByte(lpFileName,grab_exe_name))
		Next
	EndIf
	
	FreeBank lpFileName
	
	Return exe_name
End Function












;~IDEal Editor Parameters:
;~F#B#12#C4#D6#DE#E2#E8#EE#F4#110#11F#128#131#13A#144#14B#15C#165#173#18E
;~C#Blitz3D