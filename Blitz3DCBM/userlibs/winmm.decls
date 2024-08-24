.lib "winmm.dll"

; -- MCI multimedia commands
mciExecute%( Text$ ):"mciExecute"
mciSendCommand%(DeviceID,Message,Param1,Param2):"mciSendCommandA"

mciGetErrorString%(Error,Buffer$,Length%):"mciGetErrorStringA"
mciGetDeviceID(Device$):"GetDeviceIDA"
mciSendString_%(Command$,ReturnString*,ReturnLength%,Callback):"mciSendStringA"

setaudio%(DeviceID%,Audio%,Flags%)
AuxGetVolume(DeviceID,lpdwVolume*)
AuxSetVolume(DeviceID,lpdwVolume*)


; -- Can be used to play sound from memory
winmm_PlaySound%(lpszName*,hModule%,dwFlags%):"PlaySoundA"
winmm_StopSound%(lpszName%,hModule%,dwFlags%):"PlaySoundA"


; -- mixer
mixerOpen%(phmx%, uMxId%, dwCallback%, dwInstance%, fdwOpen%)
mixerClose%(hmx%)

mixerGetControlDetails%(hmxobj%, pmxcd*, fdwDetails%):"mixerGetControlDetailsA"
mixerGetDevCaps%(uMxId%, pmxcaps*, cbmxcaps%): "mixerGetDevCapsA"
mixerGetID%(hmxobj%, pumxID%, fdwId%)
mixerGetLineControls%(hmxobj%, pmxlc*, fdwControls%): "mixerGetLineControlsA"
mixerGetLineInfo%(hmxobj%, pmxl*, fdwInfo%):"mixerGetLineInfoA"
mixerGetNumDevs%()
mixerMessage%(hmx%, uMsg%, dwParam1%, dwParam2%)
mixerSetControlDetails%(hmxobj%, pmxcd*, fdwDetails%)

; -- Wave in
waveInGetDevCaps%(uDeviceID,WaveInCapsPointer*,WaveInCapsStructSize) : "waveInGetDevCapsA"
waveInOpen%(WaveDeviceInputHandle*,WhichDevice,WaveFormatExPointer*,CallBack,CallBackInstance,Flags) : "waveInOpen"
waveInGetNumDevs%() : "waveInGetNumDevs"
waveInClose%(WaveDeviceInputHandle) : "waveInClose"
waveInStart%(WaveDeviceInputHandle) : "waveInStart"
waveInReset%(WaveDeviceInputHandle) : "waveInReset"
waveInStop%(WaveDeviceInputHandle) : "waveInStop"
waveInAddBuffer%(InputDeviceHandle,WaveHdrPointer*,WaveHdrStructSize) : "waveInAddBuffer"
waveInPrepareHeader%(InputDeviceHandle,WaveHdrPointer*,WaveHdrStructSize) : "waveInPrepareHeader"
waveInUnprepareHeader%(InputDeviceHandle,WaveHdrPointer*,WaveHdrStructSize) : "waveInUnprepareHeader"
waveInGetPosition%(HWAVEIN%,LPMMTIME%,UINTt%)
waveInGetID%(HWAVEIN%, puDeviceID%)
waveInGetErrorText%(err%, lpText$, uSize%): "waveInGetErrorTextA"

; -- Wave out

waveOutOpen%( LPHWAVEphwo%, uDeviceID, LPCWAVEFORMATEX*, dwCallback%, dwInstance%,fdwOpen%)
waveOutClose%(HWAVEhwo%)
waveOutPrepareHeader%(HWAVEhwo%,LPWaveHDR*,UINT%)
waveOutUnprepareHeader%(HWAVEhwo%,LPWaveHDR*,UINT%)
waveOutWrite%(HWAVEhwo%,LPWaveHDR*,UINT%)
waveOutPause%(HWAVEhwo%)
waveOutRestart%(HWAVEhwo%)
waveOutReset%(HWAVEhwo%)
waveOutBreakLoop%(HWAVEhwo%)
waveOutGetPosition%(HWAVEhwo%,LPMMTIME%,UINT %)
waveOutGetPitch%(HWAVEhwo%, pdwPitch%)
waveOutSetPitch%(HWAVEhwo%,dwPitch%)
waveOutGetPlaybackRate%(HWAVEhwo%, pdwRate%)
waveOutSetPlaybackRate%(HWAVEhwo%,dwRate%)
waveOutGetID%(HWAVEhwo%,puDeviceID%)
waveOutGetNumDevs%()
waveOutSetVolume%(HWAVEhwo%, dwVolume%)

midiOutGetNumDevs%()
midiOutClose%(hMidiOut%)
midiOutOpen%(lphMidiOut*,uDeviceID%,dwCallback%,dwInstance%,dwFlags%); nul1*,nul2*,dwFlags%)
midiOutShortMsg%(hMidiOut%,dwMsg%)
midiOutGetDevCaps%( uDeviceID%, lpCaps*, uSize%):"midiOutGetDevCapsA"


midiInGetDevCaps%( uDeviceID%, lpCaps*, uSize%):"midiInGetDevCapsA"
midiInStop%(hMidiIn%)
midiInStart%(hMidiIn%)
midiInReset%(hMidiIn%)
midiInPrepareHeader%(hMidiIn%, lpMidiInHdr*, uSize%)
midiInOpen%(lphMidiIn*, uDeviceID%, dwCallback%, dwInstance%, dwFlags%)
midiInMessage%(hMidiIn%, msg%, dw1%, dw2%)
midiInGetNumDevs%()
midiInGetID%(hMidiIn%, lpuDeviceID%)
midiInGetErrorText%( err%, lpText*, uSize%):"midiInGetErrorTextA"
midiInClose%(hMidiIn%)
midiInAddBuffer%(hMidiIn%, lpMidiInHdr*, uSize%)
midiDisconnect%(hmi%, hmo%, pReserved%)
midiConnect%(hmi%, hmo%, pReserved%)

api_timeGetTime%() : "timeGetTime"