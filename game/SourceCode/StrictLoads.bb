; ID: 2975
; Author: RifRaf, further modified by MonocleBios
; Date: 2012-09-11 11:44:22
; Title: Safe Loads (b3d) ;strict loads sounds more appropriate IMO
; Description: Get the missing filename reported

;safe loads for mav trapping media issues




;basic wrapper functions that check to make sure that the file exists before attempting to load it, raises an RTE if it doesn't
;more informative alternative to MAVs outside of debug mode, makes it immiediately obvious whether or not someone is loading resources
;likely to cause more crashes than 'clean' CB, as this prevents anyone from loading any assets that don't exist, regardless if they are ever used
;added zero checks since blitz load functions return zero sometimes even if the filetype exists
Function LoadImage_Strict(file$)
	If FileType(file$)<>1 Then RuntimeError "Image " + Chr(34) + file$ + Chr(34) + " missing. "
	tmp = LoadImage(file$)
	Return tmp
	;attempt to load the image again
	If tmp = 0 Then tmp2 = LoadImage(file)
	DebugLog "Attempting to load again: "+file
	Return tmp2
End Function

Type Sound
	Field internalHandle%
	Field name$
	Field channels%[32]
	Field channelscount%
	Field releaseTime%
End Type

Function AutoReleaseSounds()
	if EnableSFXRelease Then
		Local snd.Sound, tryRelease%
		For snd.Sound = Each Sound
			tryRelease% = True
			For i = 0 To 31
				If snd\channels[i] <> 0 Then
					If ChannelPlaying(snd\channels[i]) Then
						tryRelease = False
						snd\releaseTime = MilliSecs2()+5000
						Exit
					EndIf
				EndIf
			Next
			If tryRelease Then
				If snd\releaseTime < MilliSecs2() Then
					If snd\internalHandle <> 0 Then
						FreeSound snd\internalHandle
						snd\internalHandle = 0
						snd\channelscount = 0
					EndIf
				EndIf
			EndIf
		Next
	EndIf
End Function

Function PlaySound_Strict%(sndHandle%)
	Local snd.Sound = Object.Sound(sndHandle)
	If snd <> Null Then
		Local shouldPlay% = True
		For i = 0 To 31
			If snd\channels[i] <> 0 Then
				If Not ChannelPlaying(snd\channels[i]) Then
					If snd\internalHandle = 0 Then
						If FileType(snd\name) <> 1 Then
							CreateConsoleMsg("Sound " + Chr(34) + snd\name + Chr(34) + " not found.")
							If ConsoleOpening
								ConsoleOpen = True
							EndIf
						Else
							If EnableSFXRelease Then snd\internalHandle = LoadSound(snd\name)
						EndIf
						If snd\internalHandle = 0 Then
							;CreateConsoleMsg("Failed to load Sound: " + Chr(34) + snd\name + Chr(34))
							If ConsoleOpening
								ConsoleOpen = True
							EndIf
						EndIf
					EndIf
					If ConsoleFlush Then
						snd\channels[i] = PlaySound(ConsoleFlushSnd)
					Else
						snd\channels[i] = PlaySound(snd\internalHandle)
					EndIf
					ChannelVolume snd\channels[i],SFXVolume#
					snd\releaseTime = MilliSecs2()+5000 ;release after 5 seconds
					Return snd\channels[i]
				EndIf
			Else
				If snd\internalHandle = 0 Then
					If FileType(snd\name) <> 1 Then
						CreateConsoleMsg("Sound " + Chr(34) + snd\name + Chr(34) + " not found.")
						If ConsoleOpening
							ConsoleOpen = True
						EndIf
					Else
						If EnableSFXRelease Then snd\internalHandle = LoadSound(snd\name)
					EndIf
						
					If snd\internalHandle = 0 Then
						;CreateConsoleMsg("Failed to load Sound: " + Chr(34) + snd\name + Chr(34))
						If ConsoleOpening
							ConsoleOpen = True
						EndIf
					EndIf
				EndIf
				If ConsoleFlushSnd Then
					snd\channels[i] = PlaySound(ConsoleFlushSnd)
				Else
					snd\channels[i] = PlaySound(snd\internalHandle)
				EndIf
				ChannelVolume snd\channels[i],SFXVolume#
				snd\releaseTime = MilliSecs2()+5000 ;release after 5 seconds
				snd\channelscount = channelscount + 1
				Return snd\channels[i]
			EndIf
		Next
	EndIf
	
	Return 0
End Function

Function LoadSound_Strict(file$)
	local lowered$ = lower(file)
	For RS.RedirectedSound = Each RedirectedSound
		if lowered = rs\lowerednewfile then
			file = rs\newfile
			exit
		endif
	Next
	Local snd.Sound = New Sound
	snd\name = file
	snd\internalHandle = 0
	snd\releaseTime = 0
	If (Not EnableSFXRelease) Then
		If snd\internalHandle = 0 Then
			snd\internalHandle = LoadSound(snd\name)
		EndIf
	EndIf
	
	Return Handle(snd)
End Function

Function FreeSound_Strict(sndHndle%)
	Local snd.Sound = Object.Sound(sndHandle)
	If snd <> Null Then
		If snd\internalHandle <> 0 Then
			FreeSound snd\internalHandle
			snd\internalHandle = 0
		EndIf
		Delete snd
	EndIf
End Function

Type SoundToDelete
	Field snd%
End Type
Type RedirectedSound
	Field file$, newfile$, lowerednewfile$, KillOnExit%
End Type

Function RedirectSound.RedirectedSound(file$, newfile$, killonexit%=True)
	local StreamFound%, SoundFound%
	For snd.Sound = Each Sound
		if lower(snd\name) = lower(file) then
			local std.soundtodelete = new soundtodelete
			std\snd = snd\internalHandle
			
			snd\name = newfile
			snd\releaseTime = 0
			If snd\internalHandle <> 0 Then
				snd\internalHandle = LoadSound(snd\name)
			EndIf
				
			If (Not EnableSFXRelease) Then
				If snd\internalHandle = 0 Then
					snd\internalHandle = LoadSound(snd\name)
				EndIf
			EndIf
		endif
	Next
	
	For st.stream = Each stream
		if lower(st\file) = lower(file) then
			if st\sfx <> 0 then
				FSOUND_StopSound(st\chn)
				FSOUND_Stream_Stop(st\sfx)
				FSOUND_Stream_Close(st\sfx)
			endif
			st\sfx = FSOUND_Stream_Open(newfile,Mode,0,0)
			st\chn = FSOUND_Stream_Play(FreeChannel,st\sfx)
			FSOUND_SetVolume(st\chn,st\Vol*255)
			FSOUND_SetPaused(st\chn,st\paused)
			if st\pan <> -999.0 then FSOUND_SetPan(st\chn,st\pan)
			st\file = newfile
		endif
	Next
	
	local RS.RedirectedSound = New RedirectedSound
	RS\file = file
	RS\lowerednewfile = lower(newfile)
	RS\newfile = newfile
	RS\KillOnExit = True
	Return RS
End Function

Type Stream
	Field sfx%
	Field chn%
	Field file$
	Field vol#, pan#
	Field paused%
End Type

Function StreamSound_Strict(file$,volume#=1.0,custommode=Mode)
	If FileType(file$)<>1
		CreateConsoleMsg("Sound " + Chr(34) + file$ + Chr(34) + " not found.")
		If ConsoleOpening
			ConsoleOpen = True
		EndIf
		Return 0
	EndIf
	
	local lowered$ = lower(file)
	For RS.RedirectedSound = Each RedirectedSound
		if lowered = rs\lowerednewfile then
			file = rs\newfile
			exit
		endif
	Next
	
	Local st.Stream = New Stream
	st\sfx = FSOUND_Stream_Open(file$,custommode,0,0)
	st\file = file
	
	If st\sfx = 0
		CreateConsoleMsg("Failed to stream Sound (returned 0): " + Chr(34) + file$ + Chr(34))
		If ConsoleOpening
			ConsoleOpen = True
		EndIf
		Return 0
	EndIf
	
	st\chn = FSOUND_Stream_Play(FreeChannel,st\sfx)
	
	If st\chn = -1
		CreateConsoleMsg("Failed to stream Sound (returned -1): " + Chr(34) + file$ + Chr(34))
		If ConsoleOpening
			ConsoleOpen = True
		EndIf
		Return -1
	EndIf
	
	FSOUND_SetVolume(st\chn,volume*255)
	FSOUND_SetPaused(st\chn,False)
	st\vol = volume
	st\pan = -999.0
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
	st\vol = volume
	
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
	st\paused = paused
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
	st\pan = fmod_pan
End Function

Function UpdateStreamSoundOrigin(streamHandle%,cam%,entity%,range#=10,volume#=1.0)
	;Local st.Stream = Object.Stream(streamHandle)
	range# = Max(range,1.0)
	
	If volume>0 Then
		
		Local dist# = EntityDistance(cam, entity) / range#
		If 1 - dist# > 0 And 1 - dist# < 1 Then
			
			Local panvalue# = Sin(-DeltaYaw(cam,entity))
			
			SetStreamVolume_Strict(streamHandle,volume#*(1-dist#)*SFXVolume#)
			SetStreamPan_Strict(streamHandle,panvalue)
		Else
			SetStreamVolume_Strict(streamHandle,0.0)
		EndIf
	Else
		If streamHandle <> 0 Then
			SetStreamVolume_Strict(streamHandle,0.0)
		EndIf 
	EndIf
End Function

Function LoadMesh_Strict(File$,parent=0)
	If FileType(File$) <> 1 Then RuntimeError "3D Mesh " + File$ + " not found."
	tmp = LoadMesh(File$, parent)
	If tmp = 0 Then RuntimeError "Failed to load 3D Mesh: " + File$ 
	Return tmp  
End Function   

Function LoadAnimMesh_Strict(File$,parent=0)
	If FileType(File$) <> 1 Then RuntimeError "3D Animated Mesh " + File$ + " not found."
	tmp = LoadAnimMesh(File$, parent)
	If tmp = 0 Then RuntimeError "Failed to load 3D Animated Mesh: " + File$
	
	Return tmp
End Function   

;don't use in LoadRMesh, as Reg does this manually there. If you wanna fuck around with the logic in that function, be my guest 
Function LoadTexture_Strict(File$,flags=1)
	If FileType(File$) <> 1 Then RuntimeError "Texture " + File$ + " not found."
	tmp = LoadTexture(File$, flags+(256*(EnableVRam=True)))
	If tmp = 0 Then RuntimeError "Failed to load Texture: " + File$
	Return tmp 
End Function   

Function LoadBrush_Strict(file$,flags,u#=1.0,v#=1.0)
	If FileType(file$)<>1 Then RuntimeError "Brush Texture " + file$ + "not found."
	tmp = LoadBrush(file$, flags, u, v)
	If tmp = 0 Then RuntimeError "Failed to load Brush: " + file$ 
	Return tmp 
End Function 

Function LoadFont_Strict(file$="Tahoma", height=13, bold=0, italic=0, underline=0)
	If FileType(file$)<>1 Then RuntimeError "Font " + file$ + " not found."
	tmp = LoadFont(file, height, bold, italic, underline)  
	If tmp = 0 Then RuntimeError "Failed to load Font: " + file$ 
	Return tmp
End Function

Function ReloadImage(filename$, image, width, height)
	FreeImage image
	image = LoadImage_Strict(filename)
	ResizeImage image, width, height
	Return image
End Function











;~IDEal Editor Parameters:
;~F#F#34#3B
;~C#Blitz3D