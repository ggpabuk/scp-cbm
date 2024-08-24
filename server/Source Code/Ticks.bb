Global CurrentTick.ticks
Global UpdateTickTime%
Type ticks
	Field name$
	Field lastms%
	Field Spendms%
End Type

Function CreateTick(name$)
	CurrentTick = New ticks
	CurrentTick\name = name
	CurrentTick\lastms = MilliSecs()
End Function

Function StopTick()
	if CurrentTick = Null Then Return
	CurrentTick\SpendMS = MilliSecs()-CurrentTick\lastms
	CurrentTick = Null
End Function

Function ResultTicks()
	;if UpdateTickTime < MilliSecs() Then
		For t.Ticks = Each ticks
			DebugLog t\name+" spend in "+t\spendms+" ms"
		Next
		UpdateTickTime = MillISecs()+1500
	;EndIf
	Delete Each ticks
End Function