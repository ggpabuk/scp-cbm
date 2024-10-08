;	BlitzSteam - Steam wrapper for Blitz
;	Copyright (C) 2015 Xaymar (Michael Fabian Dirks)
;
;	This program is free software: you can redistribute it and/or modify
;	it under the terms of the GNU Lesser General Public License as
;	published by the Free Software Foundation, either version 3 of the 
;	License, or (at your option) any later version.
;
;	This program is distributed in the hope that it will be useful,
;	but WITHOUT ANY WARRANTY; without even the implied warranty of
;	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
;	GNU General Public License for more details.
;
;	You should have received a copy of the GNU Lesser General Public License
;	along with this program.  If not, see <http:;www.gnu.org/licenses/>.

.lib "BlitzSteam.dll"

; Utils -----------------------------------------------------------------------
BS_SteamUtils%()																:"_BS_SteamUtils@0"
BS_SteamGameServerUtils%()														:"_BS_SteamGameServerUtils@0"
BS_SteamUtils_GetImageRGBA%(pThis%, iImage%, pubDest*, nDestBufferSize%)		:"_BS_SteamUtils_GetImageRGBA@16"
BS_SteamUtils_GetImageSize%(pThis%, iImage%, pnWidth*, pnHeight*)				:"_BS_SteamUtils_GetImageSize@16"