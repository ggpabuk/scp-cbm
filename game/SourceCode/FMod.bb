Const Freq		=	44100	;Hz
Const Channels	=	128		;Standartwert
Const Flags		=	0
Const Mode		=	2		;Mode = 2 means that the sounds play in a loop
Const F_Offset	=	0
Const Lenght	=	0
Const MaxVol	=	255
Const MinVol	=	0
Const PanLeft	=	0
Const PanRight	=	255
Const PanMid	=	-1
Const AllChannel=	-3
Const FreeChannel = -1
Global ECHO_EFFECT
alInitialise(-1,0.02,1)

FSOUND_Init(Freq, Channels, Flags)

ECHO_EFFECT = alCreateEffect()
if ECHO_EFFECT <> 0 Then
	alEffectSetEAXReverbParam(ECHO_EFFECT)
	alEffectSetPreset(ECHO_EFFECT,"Dizzy")
EndIf
	
Function InitExternalSound()
	
End Function
;~IDEal Editor Parameters:
;~C#Blitz3D