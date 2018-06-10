#include "dll.h"
#include <windows.h>

extern "C"
{
	__declspec(dllexport) HKEY WinRegSee(HKEY hkey, char* key)
	{
		HKEY result;
		if(RegOpenKeyEx(hkey, key, 0, KEY_ALL_ACCESS, &result) == ERROR_SUCCESS)
			return result;
		else
			return NULL;
	}
	
	__declspec(dllexport) HKEY WinRegNew(HKEY hkey, char* key)
	{
		HKEY result;
		DWORD disposition;
		if(RegCreateKeyEx(hkey, key, 0, NULL, REG_OPTION_NON_VOLATILE, KEY_ALL_ACCESS, NULL, &result, &disposition) == ERROR_SUCCESS)
			return result;
		else
			return NULL;
	}
	
	__declspec(dllexport) bool WinRegSet(HKEY hkey, char* value, char* content)
	{
		return RegSetValueEx(hkey, value, 0, REG_SZ, (LPBYTE) content, strlen(content) + 1) == ERROR_SUCCESS;
	}
	
	__declspec(dllexport) bool WinRegSetOld(HKEY hkey, char* key, char* content)
	{
		return RegSetValue(hkey, key, REG_SZ, content, strlen(content) + 1) == ERROR_SUCCESS;
	}
	
	__declspec(dllexport) char* WinRegGet(int size, HKEY hkey, char* value)
	{
		char buffer[size];
		DWORD bufferSize = sizeof(buffer);
		DWORD type = REG_SZ;
		RegQueryValueEx(hkey, value, NULL, &type, (LPBYTE) &buffer, &bufferSize);
		return buffer;
	}
	
	__declspec(dllexport) char* WinRegGetOld(int size, HKEY hkey, char* key, char* value)
	{
		char buffer[size];
		DWORD bufferSize = sizeof(buffer);
		RegGetValue(hkey, key, value, RRF_RT_ANY, NULL, (PVOID) &buffer, &bufferSize);
		return buffer;
	}
	
	__declspec(dllexport) bool WinRegFit(HKEY hkey, char* value)
	{
		return RegQueryValueEx(hkey, value, NULL, REG_NONE, NULL, NULL) == ERROR_SUCCESS;
	}
	
	__declspec(dllexport) bool WinRegDel(HKEY hkey, char* value)
	{
		return RegDeleteValue(hkey, value) == ERROR_SUCCESS;
	}
	
	__declspec(dllexport) bool WinRegDelKey(HKEY hkey, char* key)
	{
		return RegDeleteKey(hkey, key) == ERROR_SUCCESS;
	}
	
	__declspec(dllexport) bool WinRegRid(HKEY hkey)
	{
		return RegCloseKey(hkey) == ERROR_SUCCESS;
	}
	
	__declspec(dllexport) void TextConcat(char* array, char* textA, char* textB)
	{
		int sizeA = strlen(textA);
		int sizeB = strlen(textB);
		for(int i = 0; i < sizeA; i++)
			array[i] = textA[i];
		for(int i = 0; i < sizeB; i++)
			array[i + sizeA] = textB[i];
	}
}

BOOL WINAPI DllMain(HINSTANCE hinstDLL, DWORD fdwReason, LPVOID lpvReserved)
{
	switch(fdwReason)
	{
		case DLL_PROCESS_ATTACH:
		{
			break;
		}
		case DLL_PROCESS_DETACH:
		{
			break;
		}
		case DLL_THREAD_ATTACH:
		{
			break;
		}
		case DLL_THREAD_DETACH:
		{
			break;
		}
	}
	
	return TRUE;
}

