
Function LoadImage_Strict(file$)
	file = StripPath(file)
	CopyFile("GFX\bump.bmp", File)
	tmp = LoadImage(file$)
	DeleteFile file
	Return tmp
End Function

Function PlaySound_Strict%(sndHandle)
	Local snd.sounds = Object.sounds(sndHandle)
	if snd = Null Then Return
	Local channel = PlaySound(snd\soundhandle)
	ChannelVolume channel, 0
	Return channel
End Function

Function LoadSound_Strict(file$)
	if FileSize(file) = 0 Then Return
	Local snd.sounds = New sounds
	snd\soundhandle = LoadSound(file)
	SoundVolume soundhandle, 0
	Return Handle(snd)
End Function

Function FreeSound_Strict(sndHandle)
	Local snd.sounds = Object.sounds(sndHandle)
	if snd = Null Then Return
	FreeSound snd\soundhandle
	Delete snd
End Function
Type sounds
	Field soundhandle%
End Type

Type Stream
	Field sfx%
	Field chn%
End Type
Function sound_onkill()
	For snd.sounds = Each sounds
		FreeSound_Strict(handle(snd))
	Next
	For s.stream = Each stream
		StopStream_Strict(Handle(s))
	Next
	Delete each sounds
	Delete each stream
End FUnction
Function StreamSound_Strict(file$,volume#=1.0,custommode=2)
	If FileType(file$)<>1
		Return 0
	EndIf
	
	Local st.Stream = New Stream
	st\sfx = FSOUND_Stream_Open(file$,custommode,0,0)

	If st\sfx = 0
		Return 0
	EndIf
	
	st\chn = FSOUND_Stream_Play(FreeChannel,st\sfx)
	
	If st\chn = -1
		Return -1
	EndIf
	
	FSOUND_SetVolume(st\chn,0)
	FSOUND_SetPaused(st\chn,False)
	
	Return Handle(st)
End Function

Function StopStream_Strict(streamHandle%)
	Local st.Stream = Object.Stream(streamHandle)
	
	If st = Null
		Return
	EndIf
	If st\chn=0 Or st\chn=-1
		Return
	EndIf
	
	FSOUND_StopSound(st\chn)
	FSOUND_Stream_Stop(st\sfx)
	FSOUND_Stream_Close(st\sfx)
	Delete st
	
End Function

Function SetStreamVolume_Strict(streamHandle%,volume#)
	Local st.Stream = Object.Stream(streamHandle)
	
	If st = Null
		;CreateConsoleMsg("Failed to set stream Sound volume: Unknown Stream")
		Return
	EndIf
	If st\chn=0 Or st\chn=-1
		;CreateConsoleMsg("Failed to set stream Sound volume: Return value "+st\chn)
		Return
	EndIf
	
	FSOUND_SetVolume(st\chn,volume*255.0)
	FSOUND_SetPaused(st\chn,False)
	
End Function

Function SetStreamPaused_Strict(streamHandle%,paused%)
	Local st.Stream = Object.Stream(streamHandle)
	
	If st = Null
		;CreateConsoleMsg("Failed to pause/unpause stream Sound: Unknown Stream")
		Return
	EndIf
	If st\chn=0 Or st\chn=-1
		;CreateConsoleMsg("Failed to pause/unpause stream Sound: Return value "+st\chn)
		Return
	EndIf
	
	FSOUND_SetPaused(st\chn,paused)
	
End Function

Function IsStreamPlaying_Strict(streamHandle%)
	Local st.Stream = Object.Stream(streamHandle)
	
	If st = Null
		;CreateConsoleMsg("Failed to find stream Sound: Unknown Stream")
		Return
	EndIf
	If st\chn=0 Or st\chn=-1
		;CreateConsoleMsg("Failed to find stream Sound: Return value "+st\chn)
		Return
	EndIf
	
	Return FSOUND_IsPlaying(st\chn)
	
End Function

Function SetStreamPan_Strict(streamHandle%,pan#)
	Local st.Stream = Object.Stream(streamHandle)
	
	If st = Null
		;CreateConsoleMsg("Failed to find stream Sound: Unknown Stream")
		Return
	EndIf
	If st\chn=0 Or st\chn=-1
		;CreateConsoleMsg("Failed to find stream Sound: Return value "+st\chn)
		Return
	EndIf
	
	;-1 = Left = 0
	;0 = Middle = 127.5 (127)
	;1 = Right = 255
	Local fmod_pan% = 0
	fmod_pan% = Int((255.0/2.0)+((255.0/2.0)*pan#))
	FSOUND_SetPan(st\chn,fmod_pan%)
	
End Function

;--------------------------------------- INI-functions -------------------------------------------------------

Type INIFile
	Field name$
	Field bank%
	Field bankOffset% = 0
	Field size%
End Type

Function ReadINILine$(file.INIFile)
	Local rdbyte%
	Local firstbyte% = True
	Local offset% = file\bankOffset
	Local bank% = file\bank
	Local retStr$ = ""
	if BankSize(bank) = offset then return ""
	
	rdbyte = PeekByte(bank,offset)
	While ((firstbyte) Or ((rdbyte<>13) And (rdbyte<>10))) And (offset<file\size)
		rdbyte = PeekByte(bank,offset)
		If ((rdbyte<>13) And (rdbyte<>10)) Then
			firstbyte = False
			retStr=retStr+Chr(rdbyte)
		EndIf
		offset=offset+1
		if BankSize(bank) = offset then exit
	Wend
	file\bankOffset = offset
	Return retStr
End Function

Function UpdateINIFile$(filename$)
	Local file.INIFile = Null
	For k.INIFile = Each INIFile
		If k\name = Lower(filename) Then
			file = k
		EndIf
	Next
	
	If file=Null Then Return
	
	If file\bank<>0 Then FreeBank file\bank
	Local f% = ReadFile(file\name)
	Local fleSize% = 1
	While fleSize<FileSize(file\name)
		fleSize=fleSize*2
	Wend
	file\bank = CreateBank(fleSize)
	file\size = 0
	While Not Eof(f)
		PokeByte(file\bank,file\size,ReadByte(f))
		file\size=file\size+1
	Wend
	CloseFile(f)
End Function

Function GetINIString$(file$, section$, parameter$, defaultvalue$="")
	Local TemporaryString$ = ""
	
	Local lfile.INIFile = Null
	For k.INIFile = Each INIFile
		If k\name = Lower(file) Then
			lfile = k
		EndIf
	Next
	
	If lfile = Null Then
		DebugLog "CREATE BANK FOR "+file
		lfile = New INIFile
		lfile\name = Lower(file)
		lfile\bank = 0
		UpdateINIFile(lfile\name)
	EndIf
	
	lfile\bankOffset = 0
	
	section = Lower(section)
	
	;While Not Eof(f)
	While lfile\bankOffset<lfile\size
		Local strtemp$ = ReadINILine(lfile)
		If Left(strtemp,1) = "[" Then
			strtemp$ = Lower(strtemp)
			If Mid(strtemp, 2, Len(strtemp)-2)=section Then
				Repeat
					TemporaryString = ReadINILine(lfile)
					If Lower(Trim(Left(TemporaryString, Max(Instr(TemporaryString, "=") - 1, 0)))) = Lower(parameter) Then
						;CloseFile f
						Return Trim( Right(TemporaryString,Len(TemporaryString)-Instr(TemporaryString,"=")) )
					EndIf
				Until (Left(TemporaryString, 1) = "[") Or (lfile\bankOffset>=lfile\size)
				
				;CloseFile f
				Return defaultvalue
			EndIf
		EndIf
	Wend
	
	Return defaultvalue
End Function

Function GetINIInt%(file$, section$, parameter$, defaultvalue% = 0)
	Local txt$ = GetINIString(file$, section$, parameter$, defaultvalue)
	If Lower(txt) = "true" Then
		Return 1
	ElseIf Lower(txt) = "false"
		Return 0
	Else
		Return Int(txt)
	EndIf
End Function

Function GetINIFloat#(file$, section$, parameter$, defaultvalue# = 0.0)
	Return Float(GetINIString(file$, section$, parameter$, defaultvalue))
End Function


Function GetINIString2$(file$, start%, parameter$, defaultvalue$="")
	Local TemporaryString$ = ""
	Local f% = ReadFile(file)
	
	Local n%=0
	While Not Eof(f)
		Local strtemp$ = ReadLine(f)
		n=n+1
		If n=start Then 
			Repeat
				TemporaryString = ReadLine(f)
				If Lower(Trim(Left(TemporaryString, Max(Instr(TemporaryString, "=") - 1, 0)))) = Lower(parameter) Then
					CloseFile f
					Return Trim( Right(TemporaryString,Len(TemporaryString)-Instr(TemporaryString,"=")) )
				EndIf
			Until Left(TemporaryString, 1) = "[" Or Eof(f)
			CloseFile f
			Return defaultvalue
		EndIf
	Wend
	
	CloseFile f	
	
	Return defaultvalue
End Function

Function GetINIInt2%(file$, start%, parameter$, defaultvalue$="")
	Local txt$ = GetINIString2(file$, start%, parameter$, defaultvalue$)
	If Lower(txt) = "true" Then
		Return 1
	ElseIf Lower(txt) = "false"
		Return 0
	Else
		Return Int(txt)
	EndIf
End Function


Function GetINISectionLocation%(file$, section$)
	Local Temp%
	Local f% = ReadFile(file)
	
	section = Lower(section)
	
	Local n%=0
	While Not Eof(f)
		Local strtemp$ = ReadLine(f)
		n=n+1
		If Left(strtemp,1) = "[" Then
			strtemp$ = Lower(strtemp)
			Temp = Instr(strtemp, section)
			If Temp>0 Then
				If Mid(strtemp, Temp-1, 1)="[" Or Mid(strtemp, Temp-1, 1)="|" Then
					CloseFile f
					Return n
				EndIf
			EndIf
		EndIf
	Wend
	
	CloseFile f
End Function



Function PutINIValue%(file$, INI_sSection$, INI_sKey$, INI_sValue$)
	
	; Returns: True (Success) Or False (Failed)
	
	INI_sSection = "[" + Trim$(INI_sSection) + "]"
	Local INI_sUpperSection$ = Upper$(INI_sSection)
	INI_sKey = Trim$(INI_sKey)
	INI_sValue = Trim$(INI_sValue)
	Local INI_sFilename$ = file$
	
	; Retrieve the INI Data (If it exists)
	
	Local INI_sContents$ = INI_FileToString(INI_sFilename)
	
		; (Re)Create the INI file updating/adding the SECTION, KEY And VALUE
	
	Local INI_bWrittenKey% = False
	Local INI_bSectionFound% = False
	Local INI_sCurrentSection$ = ""
	
	Local INI_lFileHandle% = WriteFile(INI_sFilename)
	If INI_lFileHandle = 0 Then Return False ; Create file failed!
	
	Local INI_lOldPos% = 1
	Local INI_lPos% = Instr(INI_sContents, Chr$(0))
	
	While (INI_lPos <> 0)
		
		Local INI_sTemp$ = Mid$(INI_sContents, INI_lOldPos, (INI_lPos - INI_lOldPos))
		
		If (INI_sTemp <> "") Then
			
			If Left$(INI_sTemp, 1) = "[" And Right$(INI_sTemp, 1) = "]" Then
				
					; Process SECTION
				
				If (INI_sCurrentSection = INI_sUpperSection) And (INI_bWrittenKey = False) Then
					INI_bWrittenKey = INI_CreateKey(INI_lFileHandle, INI_sKey, INI_sValue)
				End If
				INI_sCurrentSection = Upper$(INI_CreateSection(INI_lFileHandle, INI_sTemp))
				If (INI_sCurrentSection = INI_sUpperSection) Then INI_bSectionFound = True
				
			Else
				If Left(INI_sTemp, 1) = ":" Then
					WriteLine INI_lFileHandle, INI_sTemp
				Else
						; KEY=VALUE				
					Local lEqualsPos% = Instr(INI_sTemp, "=")
					If (lEqualsPos <> 0) Then
						If (INI_sCurrentSection = INI_sUpperSection) And (Upper$(Trim$(Left$(INI_sTemp, (lEqualsPos - 1)))) = Upper$(INI_sKey)) Then
							If (INI_sValue <> "") Then INI_CreateKey INI_lFileHandle, INI_sKey, INI_sValue
							INI_bWrittenKey = True
						Else
							WriteLine INI_lFileHandle, INI_sTemp
						End If
					End If
				EndIf
				
			End If
			
		End If
		
			; Move through the INI file...
		
		INI_lOldPos = INI_lPos + 1
		INI_lPos% = Instr(INI_sContents, Chr$(0), INI_lOldPos)
		
	Wend
	
		; KEY wasn;t found in the INI file - Append a New SECTION If required And create our KEY=VALUE Line
	
	If (INI_bWrittenKey = False) Then
		If (INI_bSectionFound = False) Then INI_CreateSection INI_lFileHandle, INI_sSection
		INI_CreateKey INI_lFileHandle, INI_sKey, INI_sValue
	End If
	
	CloseFile INI_lFileHandle
	
	Return True ; Success
	
End Function

Function INI_FileToString$(INI_sFilename$)
	
	Local INI_sString$ = ""
	Local INI_lFileHandle%= ReadFile(INI_sFilename)
	If INI_lFileHandle <> 0 Then
		While Not(Eof(INI_lFileHandle))
			INI_sString = INI_sString + ReadLine$(INI_lFileHandle) + Chr$(0)
		Wend
		CloseFile INI_lFileHandle
	End If
	Return INI_sString
	
End Function

Function INI_CreateSection$(INI_lFileHandle%, INI_sNewSection$)
	
	If FilePos(INI_lFileHandle) <> 0 Then WriteLine INI_lFileHandle, "" ; Blank Line between sections
	WriteLine INI_lFileHandle, INI_sNewSection
	Return INI_sNewSection
	
End Function

Function INI_CreateKey%(INI_lFileHandle%, INI_sKey$, INI_sValue$)
	
	WriteLine INI_lFileHandle, INI_sKey + " = " + INI_sValue
	Return True
	
End Function


Function UpdateStreamSoundOrigin(streamHandle%,cam%,entity%,range#=10,volume#=1.0)
	Return 0
End Function

Function LoadMesh_Strict(File$,parent=0)
	If FileType(File$) <> 1 Then RuntimeError "3D Mesh " + File$ + " not found."
	tmp = LoadMesh(File$)
	If tmp = 0 Then RuntimeError "Failed to load 3D Mesh: " + File$
	;======================================
	Local tex = LoadTexture_Strict(file)
	EntityTexture tmp, tex
	FreeTexture tex
	Return tmp
End Function

Function LoadRawMesh(File$,parent=0)
	If FileType(File$) <> 1 Then RuntimeError "3D Mesh " + File$ + " not found."
	tmp = LoadMesh(File$)
	If tmp = 0 Then RuntimeError "Failed to load 3D Mesh: " + File$
	;======================================
    GetMeshExtents(tmp)
    FreeEntity tmp

    tmp = CreateCube()
    ScaleMesh tmp, Mesh_MaxX, Mesh_MaxY/2, Mesh_MaxZ
    PositionMesh tmp, 0, Mesh_MaxY/2, 0
    EntityParent tmp, parent

	Return tmp
End Function

Function LoadRawAnimMesh(File$,parent=0)
	If FileType(File$) <> 1 Then RuntimeError "3D Animated Mesh " + File$ + " not found."
	tmp = LoadMesh(File$)
	If tmp = 0 Then RuntimeError "Failed to load 3D Animated Mesh: " + File$
	;======================================
    alength = AnimLength(tmp)
    GetMeshExtents(tmp)
    FreeEntity tmp

    tmp = CreateCube()
    ScaleMesh tmp, Mesh_MaxX, Mesh_MaxY/2, Mesh_MaxZ
    PositionMesh tmp, 0, Mesh_MaxY/2, 0
    EntityParent tmp, parent
    
    AddAnimSeq(tmp, alength)
    SetAnimTime(tmp, 0) ; Starting anim mesh animations
	Return tmp
End Function 

Function LoadAnimMesh_Strict(File$,parent=0)
	If FileType(File$) <> 1 Then RuntimeError "3D Animated Mesh " + File$ + " not found."
	tmp = LoadAnimMesh(File$)
	If tmp = 0 Then RuntimeError "Failed to load 3D Animated Mesh: " + File$
	;======================================
	alength = AnimLength(tmp)
	FreeEntity tmp
	tmp = LoadMesh(File$)
	
	Local tex = LoadTexture_Strict(file)
	EntityTexture tmp, tex
	FreeTexture tex
	
	AddAnimSeq(tmp, alength)
	SetAnimTime(tmp, 0) ; Starting anim mesh animations
	Return tmp
End Function   

;don't use in LoadRMesh, as Reg does this manually there. If you wanna fuck around with the logic in that function, be my guest ( im use:) )
Function LoadTexture_Strict(File$,flags=1)
	File = StripPath(file)
	CopyFile("GFX\bump.bmp", File)
	tmp = LoadTexture(File, flags)
	DeleteFile file
	Return tmp
End Function   

Function LoadBrush_Strict(file$,flags,u#=1.0,v#=1.0)
	File = StripPath(file)
	CopyFile("GFX\bump.bmp", File)
	tmp = LoadBrush(file$)
	DeleteFile file
	Return tmp
End Function









;~IDEal Editor Parameters:
;~F#F#34#3B
;~C#Blitz3D