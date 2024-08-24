Function CFG_GetCommand$(filename$, Command$, RetValue$ = "")
	Local file = ReadFile(filename)
	if file <> 0 Then
		While Not Eof(file)
			Local Values$ = cfg_findcmd(Command, Trim$(ReadLine(file)))
			if Values <> "" Then	
				CloseFile(file)
				Return Values
			EndIf
		Wend
	EndIf
	CloseFile(file)
	Return RetValue
End Function

Function cfg_findcmd$(command$, msg$)
	Local stats$
	stats = Trim$(Right(msg, Len(msg) - Instr(msg, " ")))
	msg = Trim$(Left(msg, Instr(msg, " ")))
	
	if msg = command then return stats
End function