; ~ First, create a folder inside "AppData" folder
If FileType(GetEnv("AppData") + "\SCP Containment Breach Multiplayer\") <> 2 Then CreateDir(GetEnv("AppData") + "\SCP Containment Breach Multiplayer")
; ~ Second, create a folder inside "scpcb-ue" folder
If FileType(GetEnv("AppData") + "\SCP Containment Breach Multiplayer\Data\") <> 2 Then CreateDir(GetEnv("AppData") + "\SCP Containment Breach Multiplayer\Data")

Include "Main.bb"

;~IDEal Editor Parameters:
;~C#Blitz3D
;~IDEal Editor Parameters:
;~C#Blitz3D