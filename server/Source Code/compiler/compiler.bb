Include "Source Code\Skynet++.bb"

Include "Source Code\compiler\gsplugin.bb"
Include "Source Code\compiler\gspublics.bb"
Const DEFINE_TOKEN$ = "#define"
Const INCLUDE_TOKEN$ = "#include"
Type defines
	Field rep$, change$
End Type

Function compiler_init()
	AppTitle "Skynet++ Compiler"
	Graphics 800, 300, 32, 2
	api_ShowWindow(SystemProperty("AppHWND"), 1)
	SetFont LoadFont("Tahoma", 20)
	Color 255, 50, 0
	Print "Skynet++ Compiler"
	Color 200,200,200
	Print "commands: "
	Color 0,200,0
	Print "help;"
	Print "compile filename; - compile script (s)"
	Print "repeat - repeat last command;"
	Print "exit;"
	Color 200, 200, 200
	Local lastcommand$, reverse$
	Local f = ReadFile("compilersettings.ini")
	If f <> 0 Then
		initsettings(f)
		CloseFile f
	EndIf
	While True
		Local command$ = reverse
		If reverse = "" Then command = Trim$(Input("Enter command: "))
		reverse = ""
		Local stats$
		stats$ = Trim$(Right(command, Len(command) - Instr(command, " ")))
		msg$ = Trim$(Left(command, Instr(command, " ")))
		If msg = "" Then msg = stats
		Select Lower(msg)
			Case "repeat"
				reverse = lastcommand
			Case "exit"
				End
			Case "compile"
				compile(stats)
			Case "help"
				Print "commands: "
				Color 0,200,0
				Print "help;"
				Print "compile filename; - compile script (s)"
				Print "repeat - repeat last command;"
				Color 200, 200, 200
			Default:
				Color 255,0,0
				Print "Unknown command"
				Color 200,200,200
		End Select
		lastcommand = command
	Wend
End Function
Function compile(stats$)
	Local recfile, warningcount
	If FileType(stats) = 1 Then
		SE_ClearCompiler
		SE_Error = False
		SE_ERROR_MESSAGE = ""
		Delete Each SE_Warning
		Print "Compiling "+Chr(34)+stats+Chr(34)+"..."
		DeclareDefine("public def", "def")
		SE_CompileScript(stats, stats+"c")
		For W.SE_Warning=Each SE_Warning
			Print W\Message
		Next
		If SE_ERROR_MESSAGE = "" And (Not SE_ERROR) Then Print "Compiled to "+stats+"c" Else Print "Error: "+SE_ERROR_MESSAGE
		Delete Each defines
	Else
		Print "ERROR: File doesn't exist"
	EndIf
End Function
Function initsettings(f2)
	While Not Eof(f2)
		command$ = ReadLine(f2)
		stats$ = Trim$(Right(command, Len(command) - Instr(command, " ")))
		msg$ = Trim$(Left(command, Instr(command, " ")))
		If msg = "" Then msg = stats
		Select Lower(msg)
			Case "repeat"
				reverse = lastcommand
			Case "exit"
				End
			Case "compile"
				compile(stats)
			Case "help"
				Print "commands: "
				Color 0,200,0
				Print "help;"
				Print "compile filename; - compile script (s)"
				Print "repeat - repeat last command;"
				Color 200, 200, 200
			Default:
				Color 255,0,0
				Print "Unknown command"
				Color 200,200,200
		End Select
	Wend
End Function
Function DeclareDefine(ChangeToken$="",ReplaceToken$="")
	Local def.defines = New defines
	def\change = ChangeToken$
	def\rep = ReplaceToken
	DebugLog "Declared define "+def\change+" "+def\rep
End Function
Function InstrLatest(msg$, char$, offset = 1)
	Local result
	For i = offset To Len(msg)
		If Mid(msg, i, 1) = char Then result = i
	Next
	Return result
End Function
Function getlinebybytepos(f,pos)
	latestpos = FilePos(f)
	lin = 0
	SeekFile f, 0
	For i = 1 To pos
		If ReadByte(f) = 13 Then lin = lin + 1
	Next
	SeekFile(f, latestpos)
	Return lin
End Function
Function DeleteCharFromString$(s$, char$)
	Return Replace(s, char, "")
End Function
Function DeleteStringFromString$(s$, char$)
	Return Replace(s, char, "")
End Function
Function GetStringAfterChar$(s$, char$)
	Return Right(s, Len(s)-Instr(s, char))
End Function
Function countcharsinstring(s$, char$)
	Local count = 0
	For i = 1 To Len(s)
		If Mid(s, i, 1) = char Then
			count = count + 1
		EndIf
	Next
	Return count
End Function
Function addalllinestofile(source, dest)
	If dest = 0 Then Return
	WriteLine source, Chr(13)+Chr(10)
	While Not Eof(dest)
		Local msg$ = ReadLine(dest)
		If msg <> "" Then
			If (Not Instr(msg, DEFINE_TOKEN)) Then 
				For def.defines = Each defines
					msg = Replace(msg, def\change, def\rep)
				Next
				WriteLine source, msg
			EndIf
		EndIf
	Wend
	CloseFile dest
End Function
;~IDEal Editor Parameters:
;~C#Blitz3D - USE THIS ONLY