Type fSounds
	Field sample%, channel%, filename$
	Field pitch#, volume#, pan#, loop%
End Type
Function LoadSound(filename$, fx = False)
	Local fmodsound.fSounds = New fSounds
	fmodsound\sample = FSOUND_Sample_Load(-1, filename, ($00000010 Or $00000100 Or $00000020) Or $00002000 Or $00100000, 0,0)
	fmodsound\filename = filename
	if fmodsound\sample = 0 Then
		Delete fmodsound
		Return 0
	Endif
	return Handle(fmodsound)
End Function

Function GetSoundPointer(sample)
	Local fmodsound.fSounds = Object.fSounds(sample)
	if fmodsound = Null Then Return
	Return fmodsound\sample
End Function

Function PlaySound(sample)
	Local fmodsound.fSounds = Object.fSounds(sample)
	if fmodsound = Null Then Return
	Local channel = FSOUND_PlaySound(-1, fmodsound\sample)
	FSOUND_SetLoopMode(channel, fmodsound\loop)
	if fmodsound\pitch <> 0 Then FSound_SetFrequency(channel, fmodsound\pitch)
	if fmodsound\pan <> 0 Then FSound_SetPan(channel, (fmodsound\pan+1)*127.5)
	if fmodsound\volume <> 0 Then FSound_SetVolume(channel, fmodsound\volume*255)
	Return channel
End Function

Function FreeSound(sample)
	Local fmodsound.fSounds = Object.fSounds(sample)
	if fmodsound = Null Then Return
	FSOUND_Sample_Free(fmodsound\sample)
	Delete fmodsound
End Function

Function SoundPitch(sample, pitch)
	Local fmodsound.fSounds = Object.fSounds(sample)
	if fmodsound = Null Then Return
	fmodsound\pitch = pitch
End Function

Function SoundPan(sample, pan#)
	Local fmodsound.fSounds = Object.fSounds(sample)
	if fmodsound = Null Then Return
	fmodsound\pan = pan
End Function

Function SoundVolume(sample, volume#)
	Local fmodsound.fSounds = Object.fSounds(sample)
	if fmodsound = Null Then Return
	fmodsound\volume = volume
End Function

Function LoopSound(sample)
	Local fmodsound.fSounds = Object.fSounds(sample)
	if fmodsound = Null Then Return
	fmodsound\loop = True
End Function

Function PauseChannel(channel)
	FSOUND_SetPaused(channel, True)
End Function

Function ResumeChannel(channel)
	FSOUND_SetPaused(channel, False)
End Function

Function ChannelPitch(channel, pitch)
	FSOUND_SetFrequency(channel, pitch)
End Function
Function ChannelVolume(channel, volume#)
	FSOUND_SetVolume(channel, volume*255)
End Function
Function ChannelPan(channel, pan#)
	FSOUND_SetPan(channel, (pan+1)*127.5)
End Function
Function ChannelPlaying(channel)
	Return FSOUND_IsPlaying(channel)
End Function