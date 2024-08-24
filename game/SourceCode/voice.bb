; ================================================ VOICE LIBRARY (vlib.dll)	By Ne4to
	Include "SourceCode\BlitzAL.bb"
	Include "SourceCode\opus.bb"
	; ================================================ CONSTANTS
		; ================================================ WAVE FILE
			Const WAVE_temp_DIRECTORY$ = "Temp\"
			Const WAVE_FILE_NAME$ = "p.wav"
			Const WAV_HEADER_SIZE = 44
			Const WAV_FORMAT = 1
			Const WAV_BITRATE = 16
			Const WAV_ALIGNMENT = (WAV_FORMAT*(WAV_BITRATE/8))
		; ================================================ VOICE SETTINGS
			Const PLAYERS_UPDATE_VOICE# = 35 ;
			Const MY_UPDATE_RATE# = 0
			Const MAX_PLAYER_GAIN = 12
	; ================================================ TYPES
		Type v_m
			field format_in, codec
			field snd_in
			
			field VoiceInstall, Talking
			Field voicerate
			Field volume#
			Field updateFactor#, prevHold%
			Field selectedinput, drivername$
			
			Field ReceiveStream%
			Field CurrentEncoder%
			Field CurrentDecoder%
			
			Field PushToTalk%, Offset#, SmoothOffset#
		End Type
		Type records
			Field sound%, channel%[MAX_PLAYER_GAIN]
			Field volume#, entity%, effect%
			Field filename$
			Field waitfactor%
			Field sender
		End Type
	; ================================================ GLOBALS
		Global voice.v_m = New v_m
	; ================================================ INIT
		; ================================================ voice
			voice_init()
		; ================================================ freeze after exit fix
			for i = 0 To 3
				Local fm = snd_format_create(1, 44100, 16, 1)
				snd_out_open(fm)
			Next
	; ================================================ FUNCTIONS
		Function voice_free()
			if voice\format_in <> 0 Then
				opus_remove_encoder()
				
				voice_stop()
				snd_format_free(voice\format_in)
				voice\format_in = 0
			EndIf
		End Function
		Function voice_create(khz)
			if voice\format_in = 0 Then
				opus_set_sample_rate(khz)
				opus_set_channels(WAV_FORMAT)
				opus_set_default_frame_size(960)
				
				
				opus_get_new_encoder()
				;opus_set_bitrate(64)
				
				;debuglog opus_get_bitrate()
				
				voice\format_in = snd_format_create(1, khz, WAV_BITRATE, WAV_FORMAT)
				voice\voicerate = khz
				if (Not voice_start()) Or (Not voice\format_in) Then
					if voice\format_in Then 
						snd_format_free(voice\format_in)
						voice\format_in = 0
					EndIf
					voice\VoiceInstall = False
				EndIf
			EndIf
		End Function
		Function voice_start()
			if (Not voice\snd_in) And voice\format_in <> 0 Then
				voice\snd_in = snd_in_open(voice\format_in, voice_getbytes(), 8)
				debuglog "opened: "+voice_getbytes()
				if voice\snd_in <> 0 Then
					snd_in_start(voice\snd_in)
				EndIf
			EndIf
			Return voice\snd_in
		End Function
		Function voice_stop()
			if voice\snd_in <> 0 Then
				
				voice_clear(snd_in_readavail(voice\snd_in))
				snd_in_flush(voice\snd_in)
				snd_in_stop(voice\snd_in)
				;snd_in_close(voice\snd_in)
				voice\snd_in = 0
			EndIf
		End Function
		Function voice_changeparameters_REMOVED(khz)
			Return
			voice_free()
			voice_create(khz)
		End Function
		Function voice_file_AddOggHeader(file, writesize) ; header in bank
			; creating wav header
			WriteInt file, tobytes("Opus")
			WriteInt file, tobytes("Head")
			WriteByte file, 1
			WriteByte file, WAV_FORMAT
			WriteShort file, 312
			WriteInt file, voice\voicerate
			WriteInt file, 0
			WriteByte file, 0
		End Function
		Function voice_file_AddWAVHeader(file, writesize) ; header in bank
			; creating wav header
			WriteInt file, tobytes("RIFF")
			WriteInt file, writesize+(WAV_HEADER_SIZE-8)
			WriteInt file, tobytes("WAVE")
			WriteInt file, tobytes("fmt ")
			WriteInt file, 16
			WriteShort file, 1
			WriteShort file, WAV_FORMAT
			WriteInt file, voice\voicerate
			WriteInt file, voice\voicerate*WAV_ALIGNMENT
			WriteShort file, WAV_ALIGNMENT
			WriteShort file, WAV_BITRATE
			WriteInt file, tobytes("data")
			WriteInt file, writesize
		End Function
		Function voice_bank_AddWAVHeader(bank, writesize) ; header in bank
			; creating wav header
			Local vBank = CreateBank(writesize+WAV_HEADER_SIZE)
			PokeInt vBank, 0,tobytes("RIFF")
			PokeInt vBank, 4,writesize+(WAV_HEADER_SIZE-8)
			PokeInt vBank, 8,tobytes("WAVE")
			PokeInt vBank, 12,tobytes("fmt ")
			PokeInt vBank, 16,16
			PokeShort vBank, 20,1
			PokeShort vBank, 22,WAV_FORMAT
			PokeInt vBank, 24,voice\voicerate
			PokeInt vBank, 28,voice\voicerate*WAV_ALIGNMENT
			PokeShort vBank, 32,WAV_ALIGNMENT
			PokeShort vBank, 34,WAV_BITRATE
			PokeInt vBank, 36,tobytes("data")
			PokeInt vBank, 40,writesize
			CopyBank bank, 0, vBank, 44, writesize
			FreeBank bank
			Return vBank
		End Function
		Function voice_recording(holded% = False, playself% = False)
			if voice\VoiceInstall <> 0 Then
				local vlibbank% = 0, bytes = voice_getbytes()
				
				; Push to Talk system
				if voice\PushToTalk Then
					;if MENU_OPEN_TYPE <> 2 Then
						if not playself then
							;select not playself
								;case 0
									if snd_in_readavail(voice\snd_in) >= bytes Then
										if (float(float(snd_in_readavail(voice\snd_in)) Mod float(opus_get_default_frame_size()*2)) = 0) Then bytes = Min(snd_in_readavail(voice\snd_in), bytes*4)
										vlibbank = CreateBank(bytes)
										snd_in_read(voice\snd_in, vlibbank, banksize(vlibbank))
										
										voice_countoffset(vlibbank)
										
										holded = (voice_get_offset()>0.8)
										;debuglog voice_get_offset()
									EndIf
									
								;case 1
								;	if snd_in_readavail(voice\snd_in) >= bytes*8 then
								;		bytes = Min(snd_in_readavail(voice\snd_in), bytes*16)
								;		vlibbank = CreateBank(bytes)
								;		snd_in_read(voice\snd_in, vlibbank, banksize(vlibbank))
								;		
								;		OffsetBank% = ZipApi_Compress(vlibbank, 1)
								;		voice\offset = float(BankSize(OffsetBank))/float(BankSize(vlibbank))
								;		FreeBank OffsetBank
								;		
								;		holded = (voice_get_offset()>0.8)
								;	endif
							;end select
						endif
					;EndIf
				endif
				;debuglog holded+ " "+ voice\prevhold
				voice\UpdateFactor = Max(0, voice\UpdateFactor-FPSfactor)
				if holded = False And voice\prevHold = True Then voice\UpdateFactor = 10*(Not playself)
				if holded = True And voice\prevHold = False Then voice\UpdateFactor = 0
				If holded Then
					voice_render(voice_getbytes(), Not playself, vlibbank)
					voice\talking = True
				Elseif voice\UpdateFactor > 0 Then
					voice_render(voice_getbytes(), Not playself, vlibbank)
				Elseif not voice\PushToTalk Then
					if voice\talking and udp_GetStream() <> 0 then voice_send(0, true)
					voice\talking = False
					voice_clear(snd_in_readavail(voice\snd_in))
				Elseif not holded then
					voice\talking = False
				EndIf
				voice\PrevHold = holded*(Not playself)
			Else
				voice\talking = False
			EndIf
			
			if playself Then voice\talking = False
		End Function

		function voice_countoffset(VoiceData%)
			OffsetBank% = ZipApi_Compress(VoiceData, 1)
			if OffsetBank <> 0 Then
				voice\offset = float(BankSize(OffsetBank))/float(BankSize(VoiceData))
				FreeBank OffsetBank
			EndIf
		end function
		
		Function voice_render(bytes, NETWORK, vlibbank = 0)

			if snd_in_readavail(voice\snd_in) >= bytes or vlibbank <> 0 Then
				if not NETWORK Then
					if snd_in_readavail(voice\snd_in) >= bytes*8 or vlibbank <> 0 then
						if vlibbank = 0 Then 
							bytes = Min(snd_in_readavail(voice\snd_in), bytes*16)
							vlibbank = CreateBank(bytes)
							snd_in_read(voice\snd_in, vlibbank, banksize(vlibbank))
						EndIf
						
						;local output = CreateBank(0)
						;
						;Local Frame_Size = opus_get_default_frame_size()*2
						;TempBank% = CreateBank(Frame_Size)
						;
						;for i = 0 to (banksize(vlibbank)/Frame_Size)-1
						;	copybank vlibbank, Frame_Size*i, TempBank, 0, Frame_Size
						;	
						;;	EncodedData% = opus_pcm_encode(TempBank)
						;	debuglog "Size: "+Frame_Size+" Encoded: "+BankSize(EncodedData)
						;	
						;	if EncodedData <> -1 And EncodedData <> 0 Then
						;		pcm = opus_pcm_decode(opus_get_new_decoder(), EncodedData, 0)
;
						;		if pcm <> 0 then
						;			resizebank output, banksize(output)+banksize(pcm)
						;			copybank pcm, 0, output, BankSize(output)-BankSize(pcm), BankSize(pcm)
						;			freebank pcm
						;		endif
						;	EndIf
						;	
						;	FreeBank EncodedData
						;next
						;
						;FreeBank TempBank
						;
						;
						voice_countoffset(vlibbank)
						voice_wave_create(0, vlibbank, voice\volume)
						FreeBank vlibbank
						;FreeBank output
					endif
				Elseif udp_network\stream <> 0 Then
					if (float(float(snd_in_readavail(voice\snd_in)) Mod float(opus_get_default_frame_size()*2)) = 0) Then bytes = Min(snd_in_readavail(voice\snd_in), bytes*4)
					if vlibbank = 0 Then 
						vlibbank = CreateBank(bytes)
						snd_in_read(voice\snd_in, vlibbank, banksize(vlibbank))
						
						voice_countoffset(vlibbank)
					EndIf
					voice_send(vlibbank)
					FreeBank vlibbank
					
					PlayerSoundVolume = Max(4.0,PlayerSoundVolume)
				EndIf
				if snd_in_readavail(voice\snd_in) >= bytes*16 Then voice_clear(snd_in_readavail(voice\snd_in))
				
				Return True
			EndIf
			
			;EndIf
			Return False
		End Function
		
		function voice_get_offset#()
			return voice\offset
		end function
		
		Function voice_send(bank = 0, voice_announce_stop% = false)
						
			if not voice_announce_stop then
				Local Frame_Size = opus_get_default_frame_size()*2
				TempBank% = CreateBank(Frame_Size)
				
				for i = 0 to (banksize(bank)/Frame_Size)-1
					copybank bank, Frame_Size*i, TempBank, 0, Frame_Size
					
					EncodedData% = opus_pcm_encode(TempBank)
					if EncodedData <> -1 And EncodedData <> 0 Then
						udp_WriteByte M_VOICE
						udp_WriteByte NetworkServer\MyID
						udp_WriteBytes EncodedData, 0, banksize(EncodedData)
						udp_SendMessage()
						
						FreeBank EncodedData
						
						
					EndIf
				next
				
				FreeBank TempBank
			Else
				udp_WriteByte M_VOICESTOP
				udp_WriteByte NetworkServer\MyID
				udp_SendMessage()
			EndIf
			;local dcbank = createbank(2880*2)
			;opus_decode(voice\currentdecoder, pcm, opus_encode(voice\CurrentEncoder, bank, 2880, pcm, 2880), dcbank, 2880, 0)
			
			;DebugLog "banksize(pcm): "+banksize(pcm)
			
			;f = writefile("codepcm")
			;writebytes pcm, f, 0, banksize(pcm)
			;closefile f
			
			;f = writefile("code2.wav")
			;voice_file_AddWAVHeader(f, banksize(dcbank)/2)
			;writebytes dcbank, f, 0, banksize(dcbank)/2
			;closefile f
			
			; =========================================================================
			;FreeBank dcbank
			;debuglog "snd_in_readavail: "+snd_in_readavail(voice\snd_in)
			;voice_wave_create(0, bank, voice\volume)
			;local cropbank = createbank(2880*2)

			;for i = 0 to (banksize(bank)/2880)-1
			;	resizebank pcm, 2880
			;	copybank bank, 2880*i, cropbank, 0, 2880
			;	size = opus_encode(voice\CurrentEncoder, cropbank, 2880, pcm, banksize(pcm))
			;	
			;	
			;	
			;	DebugLog "xer. "+size
			;	
			;	
			;	local vsrc = createbank(size)
			;	copybank pcm, 0, vsrc, 0, size
			;	
			;	resizebank pcm, 0
			;	resizebank pcm, 2880*2
			;	
			;	
			;	opus_decode(voice\currentdecoder, vSrc, banksize(vsrc), pcm, 2880, 0)
			;	
			;	;f = writefile("xer"+i)
			;	;writebytes pcm, f, 0, banksize(pcm)
			;	;closefile f
			;	
			;	voice_wave_create(0, pcm, voice\volume)
			;	
			;	
			;	freebank vsrc
			;	
			;	if 0 Then
			;		udp_WriteByte M_VOICE
			;		udp_WriteByte NetworkServer\MyID
			;		udp_WriteBytes pcm, 0, size
			;		udp_SendMessage()
			;	EndIf
			;	
			;	compressedbytes = compressedbytes+size
			;	notcompressedbytes = notcompressedbytes+2880
			;next
			;debuglog "compressed: "+compressedbytes
			;debuglog "notcompressedbytes: "+notcompressedbytes
			;FreeBank pcm
			;freebank cropbank
			
			;bank = ZipApi_Compress(bank, 1)
			;Local VOICE_COMPRESSION_LOSS = (BankSize(bank)/DIVIDED_VOICE_DATA), bytessend = 0
			;For i = 0 To VOICE_COMPRESSION_LOSS-1
			;	udp_WriteByte(M_VOICE)
			;	udp_WriteByte(NetworkServer\MyID)
			;	bytessend = bytessend+udp_WriteBytes(bank, (BankSize(bank)/VOICE_COMPRESSION_LOSS)*i, BankSize(bank)/VOICE_COMPRESSION_LOSS)
			;	udp_SendMessage()
			;	if i = VOICE_COMPRESSION_LOSS-1 Then
			;		if bytessend < BankSize(bank) Then
			;			udp_WriteByte(M_VOICE)
			;			udp_WriteByte(NetworkServer\MyID)
			;			bytessend = bytessend+udp_WriteBytes(bank, bytessend, BankSize(bank)-bytessend)
			;			udp_SendMessage()
			;		EndIf
			;	EndIf
			;Next
		End Function
		Function voice_wave_update()
			For r.records = Each records
			
				if r\Entity <> 0 Then
					alUpdateSoundVolume(r\sound, camera, r\Entity, 12, r\volume*(DeafTimer<1))
					alSourceSet3DPosition(r\sound,EntityX(r\Entity),EntityY(r\Entity),EntityZ(r\Entity))
				EndIf
				
				if Player[r\sender] <> Null then 
					Player[r\sender]\Talking = true
					Player[r\sender]\voicefactor = 10.0
				EndIf
				
				if r\effect = 0 Then
					if (Not alSourceIsPlaying(r\sound)) Then
						alFreeSource(r\sound)
						if FileType(r\filename) <> 0 Then DeleteFile(r\filename)
						Delete r
					EndIf
				Else
					if r\WaitFactor < MilliSecs() Then
						For r2.records = Each records
							if r2\effect = True And r2 <> r Then
								alSourceRemoveEffect(r2\sound, 0)
								alFreeSource(r2\sound)
								if FileType(r2\filename) <> 0 Then DeleteFile(r2\filename)
								Delete r2
							EndIf
						Next
						alSourceRemoveEffect(r\sound, 0)
						alFreeSource(r\sound)
						if FileType(r\filename) <> 0 Then DeleteFile(r\filename)
						Delete r
						if ECHO_EFFECT <> 0 Then
							alFreeEffect(ECHO_EFFECT)
							ECHO_EFFECT = alCreateEffect()
							if ECHO_EFFECT <> 0 Then
								alEffectSetEAXReverbParam(ECHO_EFFECT)
								alEffectSetPreset(ECHO_EFFECT,"Dizzy")
							EndIf
						EndIf
					EndIf
				EndIf
			Next
		End Function
		Function voice_wave_create(sender, dataBank, volume# = 1.0, entity = 0)
			Local filename$ = WAVE_temp_DIRECTORY+"p"+sender+".wav"
			While FileType(filename+Str(vc)+".wav") <> 0
				vc = vc + 1
			Wend
			filename = filename+Str(vc)+".wav"

			f = WriteFile(filename)
			if f = 0 Then Return
			voice_file_AddWAVHeader(f, BankSize(dataBank))
			WriteBytes dataBank, f, 0, BankSize(dataBank)
			CloseFile f
				
			Local VOICE_GLOBAL = False
			if Player[sender] <> Null Then
				if Player[sender]\Announcement Then VOICE_GLOBAL = True
			EndIf
			if entity = 0 Then VOICE_GLOBAL = True

			re.records = New records
			re\sound = voice_PlaySound(filename, volume, VOICE_GLOBAL)
			
			if re\sound = 0 Then
				if filetype(filename) = 1 then deletefile(filename)
				Delete re
				Return
			EndIf
			
			re\Sender = sender
			re\Entity = entity
			re\volume = volume
			re\filename = filename
			if Player[sender] <> Null then 
				Player[sender]\Talking = true
				Player[sender]\voicefactor = 60.0
				if Player[sender]\Announcement Then
					re\effect = voice_AttachEcho(re\sound)
					re\WaitFactor = MilliSecs()+15000
				EndIf
			endif
			if Not re\sound Then multiplayer_put_error("multiplayer_errorlog.txt", "["+CurrentTime()+"] [FMOD ERROR "+FSOUND_GetError()+"] Can't load player voice data ("+BankSize(dataBank)+")")
			Return re\sound
		End Function
		Function voice_remove()
			if voice\VoiceInstall Then
				voice_free()
				voice\voiceinstall = False
			EndIf
		End Function
		Function voice_init()
			voice\VoiceInstall = (FileSize("vlib.dll")<>0)
			voice\voicerate = 48000
			
			if voice\VoiceInstall Then voice_create(48000)
		End Function
		Function voice_update()
			voice_recording(voice_isrecording(), NetworkServer\CheckVoice)
			voice_wave_update()
			voice_players_update()
			
			if camera <> 0 Then alListenerUpdate(Camera)
			alUpdate()
		End Function
		Function voice_isrecording()
			Return ((KeyDown(KEY_VOICE) And (Not NetworkServer\ChatOpen) And (Not ConsoleOpen) And (Not MenuOpen)) Or NetworkServer\CheckVoice)
		End Function
		Function voice_player_receive(playerid, bank, delta#, radio=0)
			Player[playerid]\CurrentRadio = radio
			Player[playerid]\voiceMS = ((delta/1000.0)*70)*32
			ResizeBank Player[playerid]\voicebank, BankSize(Player[playerid]\voicebank)+BankSize(bank)
			CopyBank bank, 0, Player[playerid]\voicebank, BankSize(Player[playerid]\voicebank)-BankSize(bank), BankSize(bank) ; add data to player voice bank
		End Function
		Function voice_players_update()
			For p.players = Each players
				p\intercomfactor = Max(0, p\intercomfactor-FPSfactor)
				p\voicefactor = Max(0, p\voicefactor-FPSfactor)
				p\radiofactor = Max(0, p\radiofactor-FPSfactor)
				if p\voicefactor < 1 then p\talking = False
				if p\voiceMS <= 0 or p\voicerelease Then
					if voice_player_getavail(p) <> 0 Then
						if not voice_get_player_channels(p) then
							voice_release_player(p)
							ResizeBank p\voicebank, 0
							p\voicerelease = false
							p\voiceMS = 0
						endif
					EndIf
				Else
					if voice_player_getavail(p) >= (voice_getbytes()*32)/(48000/voice\voicerate) Then
						voice_release_player(p)
						ResizeBank p\voicebank, 0
					Else
						p\voiceMS = p\voiceMS - FPSfactor
					EndIf
				EndIf
			Next
		End Function
		Function voice_get_player_channels(p.players)
			For r.Records = Each Records
				if r\Sender = p\ID Then Return True
			Next
			Return False
		end function
		Function voice_release_player(p.players)
			if MENU_OPEN_TYPE = 2 Then Return
			
			if p\Announcement Or (p\BreachType = 0 And MyPlayer\BreachType = 0) Or MainMenuOpen = True Then
				voice_wave_create(p\ID, p\voicebank, p\volume, 0)
			Else
				if p\BreachType <> 0 Then
					if (p\CurrentRadio = MyPlayer\CurrentRadio And p\CurrentRadio <> 0) Then voice_wave_create(p\ID, p\voicebank, p\volume/1.2, 0)
					if EntityDistance(Collider, p\Pivot) <= 20 Then voice_wave_create(p\ID, p\voicebank, p\volume, p\Pivot)
				EndIf
			EndIf
		End Function
		Function voice_player_getavail(p.players)
			Return BankSize(p\voicebank)
		End Function
		Function voice_clear(avail)
			Local clearbank = CreateBank(avail)
			snd_in_read(voice\snd_in, clearbank, avail)
			FreeBank clearbank
		End Function
		Function voice_getbytes()
			Return opus_get_default_frame_size()*2
			;Return NetworkServer\voicerate/5.0666360294117647058823529411765
		End Function
		Function tobytes(bStr$)
			Local towrite, shlwr
			For i = 1 To Len(bStr)
				towrite = towrite + (Asc(Mid(bStr, i, 1)) Shl shlwr)
				shlwr = shlwr + 8
			Next
			Return towrite
		End Function
		
		Function voice_PlaySound(filename$, volume#, Intercom = False)
			if filesize(filename) < 45 then return
			source = alCreateSource(filename, True, Not Intercom)
			if source <> 0 Then 
				if Not Intercom Then
					alSourcePlay(Source)
					alSourceSetVolume(source, 0)
				Else
					alSourcePlay(Source)
					alSourceSetVolume(Source, volume)
				EndIf
			EndIf
			Return Source
		End Function
		
		Function voice_AttachEcho(source)
			if ECHO_EFFECT <> 0 Then
				alSourceAttachEffect(source, ECHO_EFFECT, 0)
				Return True
			EndIf
			Return false
		End Function
; ================================================