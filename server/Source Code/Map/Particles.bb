

Dim ParticleTextures%(10)

Type Particles
	Field obj%, pvt%
	Field image%
	
	Field R#, G#, B#, A#, size#, maxsize#
	Field speed#, yspeed#, gravity#
	Field Rchange#, Gchange#, Bchange#, Achange#
	Field SizeChange#
	
	Field lifetime#, lifetime2#
End Type 
	
Function CreateParticle.Particles(x#, y#, z#, image%, size#, gravity# = 1.0, lifetime% = 200)
	Local p.Particles = New Particles
	p\lifetime = lifetime
	
	p\obj = CreateSprite()
	PositionEntity(p\obj, x, y, z, True)
	EntityTexture(p\obj, ParticleTextures(image))
	RotateEntity(p\obj, 0, 0, Rnd(360))
	EntityFX(p\obj, 1 + 8)
	
	SpriteViewMode (p\obj, 3)
	
	Select image
		Case 0,5,6
			EntityBlend(p\obj, 1)
		Case 1,2,3,4,7
			EntityBlend(p\obj, BLEND_ADD)
	End Select
	
	p\pvt = CreatePivot()
	PositionEntity(p\pvt, x, y, z, True)
	
	p\image = image
	p\gravity = gravity * 0.004
	p\R = 255 : p\G = 255 : p\B = 255 : p\A = 1.0
	p\size = size
	ScaleSprite(p\obj, p\size, p\size)
	p\lifetime = 35
	Return p
End Function
	
Function UpdateParticles()
	Local p.Particles
	For p.Particles = Each Particles
		If p\Achange <> 0 Then
			p\A=Min(Max(p\A+p\Achange * FPSfactor,0.0),1.0)
			EntityAlpha(p\obj, p\A)		
		EndIf
		
		If p\SizeChange <> 0 Then 
			p\size= p\size+p\SizeChange * FPSfactor
			ScaleSprite p\obj, p\size, p\size
			if p\maxsize <> 0 Then
				if p\maxsize <= p\size Then p\sizechange = 0
			EnDif
		EndIf
		
		p\lifetime2=p\lifetime2-FPSfactor
		
		If p\lifetime2 <= 0 Or p\size < 0.00001 Or p\A =< 0 Then RemoveParticle(p)
	Next
End Function
	
Function RemoveParticle(p.Particles)
	if p = Null Then Return
	FreeEntity(p\obj)
	FreeEntity(p\pvt)	
	Delete p
End Function

Global InSmoke%
Global HissSFX% = LoadSound_Strict("SFX\General\Hiss.ogg")
Global SmokeDelay# = 0.0

Type Emitters
	Field Obj%
	
	Field Size#
	Field MinImage%, MaxImage%
	Field Gravity#
	Field LifeTime%
	
	Field Disable%
	
	Field Room.Rooms
	
	Field SoundCHN%
	
	Field Speed#, RandAngle#
	Field SizeChange#, Achange#
	Field Emitterlifetime#
End Type 

Function UpdateEmitters()
	For e.emitters = Each Emitters
		e\emitterlifetime = e\emitterlifetime-FPSfactor
		if e\emitterlifetime < 1 Then RemoveEmitter(e)
	Next
End Function
	
Function CreateEmitter.Emitters(x#, y#, z#, emittertype%, lifetime# = 0) 
	Local e.Emitters = New Emitters
		
	e\Obj = CreatePivot()
	NameEntity e\Obj,"Emitter1"
	PositionEntity(e\Obj, x, y, z, True)
		
	Select emittertype
		Case 0 ;savu
			e\Size = 0.03
			e\Gravity = -0.2
			e\LifeTime = 200
			e\SizeChange = 0.005
			e\Speed = 0.004
			e\RandAngle = 20
			e\Achange = -0.008
		Case 1
			e\Size = 0.03
			e\Gravity = -0.2
			e\LifeTime = 200
			e\SizeChange = 0.008
			e\Speed = 0.004
			e\RandAngle = 40
			e\Achange = -0.01
			
			e\MinImage = 6 : e\MaxImage = 6
	End Select
	e\Emitterlifetime = lifetime
	For r.Rooms = Each Rooms
		If Abs(EntityX(e\Obj) - EntityX(r\obj)) < 4.0 And Abs(EntityZ(e\Obj) - EntityZ(r\obj)) < 4.0 Then
			e\Room = r
		EndIf
	Next
	
	Return e
		
End Function
Function RemoveEmitter(em.emitters)
	FreeEntity em\obj
	Delete em
End Function
Type DevilEmitters
	Field obj%
	Field x#,y#,z#
	Field particleID%
	Field room.Rooms
	Field timer#=0.0
	Field maxtimer#
	Field SoundCHN%
	Field isDeconGas%=False
End Type

Function CreateDevilEmitter.DevilEmitters(x#, y#, z#, room.Rooms, particleID%, maxTime#=2.0)
	Local dem.DevilEmitters = New DevilEmitters
	
	dem\obj = CreatePivot()
	NameEntity dem\obj,"Emitter2"
	PositionEntity dem\obj,x#,y#,z#,True
	EntityParent dem\obj,room\obj
	dem\room = room
	dem\x = x#
	dem\y = y#
	dem\z = z#
	dem\particleID = particleID
	dem\maxtimer = maxTime#
	
	Return dem
End Function

Function UpdateDevilEmitters()
	DeleteDevilEmitters()
End Function

Function DeleteDevilEmitters()
	
	For d.DevilEmitters = Each DevilEmitters
		FreeEntity d\obj
		Delete d
	Next
	
End Function






;~IDEal Editor Parameters:
;~F#4#10#2E#4A#54#66#A0#C5#D0#E0#112
;~C#Blitz3D