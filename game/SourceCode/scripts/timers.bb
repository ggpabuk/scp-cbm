Include "SourceCode\scripts\bytestream.bb"

Type timers
	Field scriptthread.SE_Script
	Field paramsdata.bs, identifiers$
	Field interval, updater, loop
	Field timerfunc.SE_FuncPtr
End Type

Function SetTimer(scriptthread.SE_Script, public$, interval, loop, identifiers$, databank.bs)
	Local t.timers = New timers
	t\interval = interval
	t\identifiers = identifiers
	t\updater = MilliSecs()+interval
	t\paramsdata = databank
	t\loop = loop
	t\scriptthread = scriptthread
	t\timerfunc = SE_FindFunc(scriptthread, lower(public))
	
	Return Handle(t)
End Function
Function UpdateTimers()
	For t.timers = Each timers
		if t\updater < MilliSecs() Then
			For i = 1 To Len(t\identifiers)
				
				Select Mid(t\identifiers, i, 1)
					Case "i"
						public_addparam(ByteStreamReadInt(t\paramsdata), SE_INT)
					Case "f"
						public_addparam(ByteStreamReadFloat(t\paramsdata), SE_FLOAT)
					Case "s"
						public_addparam(ByteStreamReadString(t\paramsdata), SE_STRING)
				End Select
			Next
			public_update_by_func(t\timerfunc)
			public_clear()
			if t\loop Then
				t\updater = MilliSecs()+t\interval
				ByteStreamResetRead(t\paramsdata)
			Else
				RemoveTimer(t)
			EndIf
		EndIf
	Next
End Function
Function RemoveTimer(t.timers)
	RemoveByteStream(t\paramsdata)
	Delete t
End Function