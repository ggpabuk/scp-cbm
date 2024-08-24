;achievement menu & messages by InnocentSam

Const MAXACHIEVEMENTS=37
Dim Achievements%(MAXACHIEVEMENTS)

Const Achv008%=0, Achv012%=1, Achv035%=2, Achv049%=3, Achv055=4,  Achv079%=5, Achv096%=6, Achv106%=7, Achv148%=8, Achv205=9
Const Achv294%=10, Achv372%=11, Achv420%=12, Achv427=13, Achv500%=14, Achv513%=15, Achv714%=16, Achv789%=17, Achv860%=18, Achv895%=19
Const Achv914%=20, Achv939%=21, Achv966%=22, Achv970=23, Achv1025%=24, Achv1048=25, Achv1123=26

Const AchvMaynard%=27, AchvHarp%=28, AchvSNAV%=29, AchvOmni%=30, AchvConsole%=31, AchvTesla%=32, AchvPD%=33

Const Achv1162% = 34, Achv1499% = 35

Const AchvKeter% = 36

Global UsedConsole

Global AchievementsMenu%
Global AchvMSGenabled% = 0
Dim AchievementStrings$(MAXACHIEVEMENTS)
Dim AchievementDescs$(MAXACHIEVEMENTS)
Dim AchvIMG%(MAXACHIEVEMENTS)
For i = 0 To MAXACHIEVEMENTS-1
	Local loc2% = 0
	AchievementStrings(i) = 0
	AchievementDescs(i) = 0
	
	Local image$ = 0
	
	AchvIMG(i) = LoadImage_Strict("GFX\menu\achievements\"+image+".jpg")
Next

Global AchvLocked = LoadImage_Strict("GFX\menu\achievements\achvlocked.jpg")

Function GiveAchievement(achvname%, showMessage%=True)

End Function




;~IDEal Editor Parameters:
;~F#31#48
;~C#Blitz3D