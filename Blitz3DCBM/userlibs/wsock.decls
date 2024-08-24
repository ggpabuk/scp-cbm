.lib "WSOCK32.DLL"

setsockopt%(socket*, level%, optname%, optval*, optlen%)
WSAGetLastError%()
WSAStartup%(wVersionRequired%, lpWSAData*)