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

; Matchmaking -----------------------------------------------------------------
BS_SteamMatchmaking%()															:"_BS_SteamMatchmaking@0"
BS_SteamMatchmaking_CreateLobby%(pThis%, LobbyType%, MaxMembers%):"_BS_ISteamMatchmaking_CreateLobby@12"
BS_SteamMatchmaking_JoinLobby%(pThis%, steamIDLobby%):"_BS_ISteamMatchmaking_JoinLobby@8"
BS_SteamMatchmaking_LeaveLobby%(pThis%, steamIDLobby%):"_BS_ISteamMatchmaking_LeaveLobby@8"
BS_SteamMatchmaking_SetLobbyData%(pThis%, steamIDLobby%, pchKey$, pchValue$):"_BS_ISteamMatchmaking_SetLobbyData@16"
BS_SteamMatchmaking_GetLobbyData$(pThis%, steamIDLobby%, pchKey$):"_BS_ISteamMatchmaking_GetLobbyData@12"
BS_SteamMatchmaking_GetLobbyOwner%(pThis%, steamIDLobby%):"_BS_ISteamMatchmaking_GetLobbyOwner@8"
BS_SteamMatchmaking_SetLobbyType%(pThis%, steamIDLobby%, LobbyType%):"_BS_ISteamMatchmaking_SetLobbyType@12"