;This is the include file for the "Devil Particle System" by "bytecode77"
;The link to the system's BlitzBasic.com page: "http://www.blitzbasic.com/toolbox/toolbox.php?tool=174"
;The link to the original page: "https://bytecode77.com/coding/devilengines/devilparticlesystem"
;All rights for this file go to "bytecode77"
;
;This file also has been modified a bit to suit better for SCP:CB


Type Template
	Field sub_template.Template[7]                                  ;Sub templates
	Field emitter_blend                                             ;blendmode of emitter entity
	Field interval, particles_per_interval                          ;particle interval
	Field max_particles                                             ;max particles
	Field emitter_max_time                                          ;Emitter life time
	Field min_time, max_time                                        ;Particle life time
	Field tex, animtex, texframe#, maxtexframes, texspeed#          ;Texture
	Field min_ox#, max_ox#, min_oy#, max_oy#, min_oz#, max_oz#      ;Offset
	Field min_xv#, max_xv#, min_yv#, max_yv#, min_zv#, max_zv#      ;Velocity
	Field rot_vel1#, rot_vel2#, align_to_fall, align_to_fall_offset ;Rotation
	Field gravity#                                                  ;Gravity
	Field alpha#, alpha_vel                                         ;Alpha
	Field sx#, sy#, size_multiplicator1#, size_multiplicator2#      ;Size
	Field size_add#, size_mult#                                     ;Size velocity
	Field r1, g1, b1, r2, g2, b2                                    ;Colors
	Field Brightness                                                ;Brightness
	Field floor_y#, floor_bounce#                                   ;Floor
	Field pitch_fix, yaw_fix                                        ;Fix angles
	
	Field yaw#
End Type
Type Emitter
	Field fixed
	Field cnt_loop#, age#, max_time#
	Field tmp.Template
	Field owner, ent, surf
	Field del, frozen
End Type
Type Particle
	Field emitter.Emitter
	Field age, max_time  ;Life time
	Field x#, y#, z#     ;Position
	Field xv#, yv#, zv#  ;Velocity
	Field rot#, rot_vel# ;Rotation
	Field sx#, sy#       ;Size
End Type
Global ParticleCam, ParticlePiv

Function InitParticles(cam)
ParticleCam = cam
ParticlePiv = CreatePivot()
SeedRnd MilliSecs()
End Function

Function FreeParticles()
For tmp.Template = Each Template
	FreeTemplate(Handle(tmp))
Next
For e.Emitter = Each Emitter
	FreeEmitter(e\ent)
Next
Delete Each Template
Delete Each Emitter
Delete Each Particle
If ParticlePiv Then FreeEntity ParticlePiv
End Function

Function CreateTemplate()
tmp.Template = New Template
template = Handle(tmp)
SetTemplateEmitterBlend(template, 3)
SetTemplateInterval(template, 1)
SetTemplateParticlesPerInterval(template, 1)
SetTemplateMaxParticles(template, -1)
SetTemplateEmitterLifeTime(template, 100)
SetTemplateParticleLifeTime(template, 0, 20)
SetTemplateAlpha(template, 1)
SetTemplateSize(template, 1, 1)
SetTemplateSizeVel(template, 0, 1)
SetTemplateColors(template, $FFFFFF, $FFFFFF)
SetTemplateBrightness(template, 1)
SetTemplateFloor(template, -1000000)
SetTemplateFixAngles(template, -1, -1)
Return Handle(tmp)
End Function

Function FreeTemplate(template)
tmp.Template = Object.Template(template)
If tmp\tex Then FreeTexture tmp\tex
For i = 0 To 7
	If tmp\sub_template[i] <> Null Then FreeTemplate(Handle(tmp\sub_template[i]))
Next
Delete tmp
End Function

Function SetTemplateEmitterBlend(template, emitter_blend)
tmp.Template = Object.Template(template)
tmp\emitter_blend = emitter_blend
End Function

Function SetTemplateInterval(template, interval)
tmp.Template = Object.Template(template)
tmp\interval = interval
End Function

Function SetTemplateParticlesPerInterval(template, particles_per_interval)
tmp.Template = Object.Template(template)
tmp\particles_per_interval = particles_per_interval
End Function

Function SetTemplateMaxParticles(template, max_particles)
tmp.Template = Object.Template(template)
tmp\max_particles = max_particles
End Function

Function SetTemplateParticleLifeTime(template, min_time, max_time)
tmp.Template = Object.Template(template)
tmp\min_time = min_time
tmp\max_time = max_time
End Function

Function SetTemplateEmitterLifeTime(template, emitter_max_time)
tmp.Template = Object.Template(template)
tmp\emitter_max_time = emitter_max_time
End Function

Function SetTemplateTexture(template, path$, mode = 0, blend = 1)
tmp.Template = Object.Template(template)
tmp\tex = LoadTexture(path$, mode)
TextureBlend tmp\tex, blend
End Function

Function SetTemplateAnimTexture(template, path$, mode, blend, w, h, maxframes, speed# = 1)
tmp.Template = Object.Template(template)
tmp\animtex = True
tmp\maxtexframes = maxframes
tmp\texspeed# = speed#
tmp\tex = LoadAnimTexture(path$, mode, w, h, 0, tmp\maxtexframes)
TextureBlend tmp\tex, blend
End Function

Function SetTemplateOffset(template, min_ox#, max_ox#, min_oy#, max_oy#, min_oz#, max_oz#)
tmp.Template = Object.Template(template)
tmp\min_ox# = min_ox#
tmp\max_ox# = max_ox#
tmp\min_oy# = min_oy#
tmp\max_oy# = max_oy#
tmp\min_oz# = min_oz#
tmp\max_oz# = max_oz#
End Function

Function SetTemplateVelocity(template, min_xv#, max_xv#, min_yv#, max_yv#, min_zv#, max_zv#)
tmp.Template = Object.Template(template)
tmp\min_xv# = min_xv#
tmp\max_xv# = max_xv#
tmp\min_yv# = min_yv#
tmp\max_yv# = max_yv#
tmp\min_zv# = min_zv#
tmp\max_zv# = max_zv#
End Function

Function SetTemplateRotation(template, rot_vel1#, rot_vel2#)
tmp.Template = Object.Template(template)
tmp\rot_vel1# = rot_vel1#
tmp\rot_vel2# = rot_vel2#
End Function

Function SetTemplateAlignToFall(template, align_to_fall, align_to_fall_offset = 0)
tmp.Template = Object.Template(template)
tmp\align_to_fall = align_to_fall
tmp\align_to_fall_offset = align_to_fall_offset
End Function

Function SetTemplateGravity(template, gravity#)
tmp.Template = Object.Template(template)
tmp\gravity# = gravity#
End Function

Function SetTemplateSize(template, sx#, sy#, size_multiplicator1# = 1, size_multiplicator2# = 1)
tmp.Template = Object.Template(template)
tmp\sx# = sx#
tmp\sy# = sy#
tmp\size_multiplicator1# = size_multiplicator1#
tmp\size_multiplicator2# = size_multiplicator2#
End Function

Function SetTemplateSizeVel(template, size_add#, size_mult#)
tmp.Template = Object.Template(template)
tmp\size_add# = size_add#
tmp\size_mult# = size_mult#
End Function

Function SetTemplateAlpha(template, alpha#)
tmp.Template = Object.Template(template)
tmp\alpha# = alpha#
End Function

Function SetTemplateAlphaVel(template, alpha_vel)
tmp.Template = Object.Template(template)
tmp\alpha_vel = alpha_vel
End Function

Function SetTemplateColors(template, col1, col2)
tmp.Template = Object.Template(template)
tmp\r1 = (col1 And $FF0000) / $10000
tmp\g1 = (col1 And $FF00) / $100
tmp\b1 = col1 And $FF
tmp\r2 = (col2 And $FF0000) / $10000
tmp\g2 = (col2 And $FF00) / $100
tmp\b2 = col2 And $FF
End Function

Function SetTemplateBrightness(template, brightness)
tmp.Template = Object.Template(template)
tmp\brightness = brightness
End Function

Function SetTemplateFloor(template, floor_y#, floor_bounce# = .5)
tmp.Template = Object.Template(template)
tmp\floor_y# = floor_y#
tmp\floor_bounce# = floor_bounce#
End Function

Function SetTemplateFixAngles(template, pitch_fix, yaw_fix)
tmp.Template = Object.Template(template)
tmp\pitch_fix = pitch_fix
tmp\yaw_fix = yaw_fix
End Function

Function SetTemplateSubTemplate(template, sub_template, for_each_particle = False)
tmp.Template = Object.Template(template)
For i = 0 To 7
	If tmp\sub_template[i] = Null Then
		tmp\sub_template[i] = Object.Template(sub_template)
		Exit
	EndIf
Next
End Function

Function SetEmitter(owner, template, fixed = False)
e.Emitter = New Emitter
If fixed Then
	e\owner = CreatePivot()
	PositionEntity e\owner, EntityX(owner), EntityY(owner), EntityZ(owner)
	e\fixed = True
Else
	e\owner = owner
EndIf
e\ent = CreateMesh()
NameEntity(e\ent,"Emitter3")
e\surf = CreateSurface(e\ent)
e\tmp = Object.Template(template)
e\max_time = e\tmp\emitter_max_time
EntityBlend e\ent, e\tmp\emitter_blend
EntityFX e\ent, 34
If e\tmp\tex Then EntityTexture e\ent, e\tmp\tex
For i = 0 To 7
	If e\tmp\sub_template[i] <> Null Then
		If e\tmp\sub_template[i]\tex Then SetEmitter(owner, Handle(e\tmp\sub_template[i]), fixed)
	EndIf
Next
Return e\ent
End Function

Function FreeEmitter(ent, delete_particles = True)
For e.Emitter = Each Emitter
	If e\owner = ent Then
		If delete_particles Then
			For p.Particle = Each Particle
				If p\emitter = e Then Delete p
			Next
			FreeEntity e\ent
			If e\fixed And e\owner Then FreeEntity e\owner
			Delete e
		Else
			e\del = True
		EndIf
	EndIf
Next
End Function

Function FreezeEmitter(ent)
For e.Emitter = Each Emitter
	If e\owner = ent Then e\frozen = True
Next
End Function

Function UnfreezeEmitter(ent)
For e.Emitter = Each Emitter
	If e\owner = ent Then e\frozen = False
Next
End Function

Function SetTemplateYaw(template,yaw#)
	tmp.template = Object.Template(template)
	tmp\yaw = yaw#
End Function

Function UpdateParticles_Devil()
	Local e.Emitter,p.Particle
	
	For e.Emitter = Each Emitter
		FreeEntity e\ent
		If e\fixed And e\owner Then FreeEntity e\owner
		Delete e
	Next
End Function





;~IDEal Editor Parameters:
;~F#2F#35#42#55#5E#63#68#6D#72#78#7D#83#8C#96#A0#A6#AC#B1#B9#BF
;~F#C4#C9#D3#D8#DE#E4#EE#106#117#11D
;~C#Blitz3D