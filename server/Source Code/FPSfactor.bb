Type FixedTimesteps
	Field tickDuration#
	Field accumulator#
	Field prevTime%
	Field currTime%
	Field fps#
End Type
Global ft.FixedTimesteps = New FixedTimesteps
Function SetTickrate(tickrate%)
	ft\tickDuration = 70.0/Float(tickrate)
End Function

Function AddToTimingAccumulator(milliseconds%)
	If (milliseconds<1 Or milliseconds>500) Then
		Return
	EndIf
	ft\accumulator = ft\accumulator+Max(0,Float(milliseconds)*70.0/1000.0)
End Function

Function ResetTimingAccumulator()
	ft\accumulator = 0.0
End Function

Function SetCurrTime(time%)
	ft\currTime = time%
	
End Function

Function SetPrevTime(time%)
	ft\prevTime = time%
	
End Function

Function SetFPS(elapsedMilliseconds%)

	Local instantFramerate# = 1000.0/Max(1,elapsedMilliseconds)
	ft\fps = Max(0,ft\fps*0.99 + instantFramerate*0.01)
	
End Function

Function GetCurrTime%()
	Return ft\currTime
End Function

Function GetPrevTime%()
	Return ft\prevTime
End Function

Function GetTickDuration#()
	Return ft\tickDuration
End Function

SetTickrate(60)