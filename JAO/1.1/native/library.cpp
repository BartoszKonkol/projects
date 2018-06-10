
#include "dll.h"
#include "jao_native.h"
#include <windows.h>
#include <iostream>

#define LIBRARY "jao_library_64.dll"

using namespace std;

typedef HKEY (*WinRegSee)(HKEY, char*);
typedef HKEY (*WinRegNew)(HKEY, char*);
typedef bool (*WinRegSet)(HKEY, char*, char*);
typedef bool (*WinRegSetOld)(HKEY, char*, char*);
typedef char* (*WinRegGet)(int, HKEY, char*);
typedef char* (*WinRegGetOld)(int, HKEY, char*, char*);
typedef bool (*WinRegFit)(HKEY, char*);
typedef bool (*WinRegDel)(HKEY, char*);
typedef bool (*WinRegDelKey)(HKEY, char*);
typedef bool (*WinRegRid)(HKEY);
typedef void (*TextConcat)(char*, char*, char*);

HINSTANCE library;
WinRegSee libraryWinRegSee;
WinRegNew libraryWinRegNew;
WinRegSet libraryWinRegSet;
WinRegSetOld libraryWinRegSetOld;
WinRegGet libraryWinRegGet;
WinRegGetOld libraryWinRegGetOld;
WinRegFit libraryWinRegFit;
WinRegDel libraryWinRegDel;
WinRegDelKey libraryWinRegDelKey;
WinRegRid libraryWinRegRid;
TextConcat libraryTextConcat;

/*
 * Class:     javax_jao_Native
 * Method:    jao_library_winreg_new
 * Signature: (JLjava/lang/String;)Z
 */
JNIEXPORT jboolean JNICALL Java_javax_jao_Native_jao_1library_1winreg_1new(JNIEnv* env, jclass clazz, jlong hkey, jstring key)
{
	const char* keyChar = env -> GetStringUTFChars(key, JNI_FALSE);
	jboolean result = libraryWinRegNew((HKEY) (ULONG_PTR) (hkey), const_cast<char*>(keyChar)) != NULL ? JNI_TRUE : JNI_FALSE;
	env -> ReleaseStringUTFChars(key, keyChar);
	return result;
}

/*
 * Class:     javax_jao_Native
 * Method:    jao_library_winreg_set
 * Signature: (JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;)Z
 */
JNIEXPORT jboolean JNICALL Java_javax_jao_Native_jao_1library_1winreg_1set(JNIEnv* env, jclass clazz, jlong hkey, jstring key, jstring value, jstring content)
{
	const char* keyChar = env -> GetStringUTFChars(key, JNI_FALSE);
	HKEY hkeySee = libraryWinRegSee((HKEY) (ULONG_PTR) (hkey), const_cast<char*>(keyChar));
	env -> ReleaseStringUTFChars(key, keyChar);
	if(hkeySee != NULL)
	{
		const char* valueChar = env -> GetStringUTFChars(value, JNI_FALSE);
		const char* contentChar = env -> GetStringUTFChars(content, JNI_FALSE);
		jboolean result = libraryWinRegSet(hkeySee, const_cast<char*>(valueChar), const_cast<char*>(contentChar)) ? JNI_TRUE : JNI_FALSE;
		env -> ReleaseStringUTFChars(value, valueChar);
		env -> ReleaseStringUTFChars(content, contentChar);
		libraryWinRegRid(hkeySee);
		return result;
	}
	return JNI_FALSE;
}

/*
 * Class:     javax_jao_Native
 * Method:    jao_library_winreg_set_old
 * Signature: (JLjava/lang/String;Ljava/lang/String;)Z
 */
JNIEXPORT jboolean JNICALL Java_javax_jao_Native_jao_1library_1winreg_1set_1old(JNIEnv* env, jclass clazz, jlong hkey, jstring key, jstring content)
{
	const char* keyChar = env -> GetStringUTFChars(key, JNI_FALSE);
	const char* contentChar = env -> GetStringUTFChars(content, JNI_FALSE);
	jboolean result = libraryWinRegSetOld((HKEY) (ULONG_PTR) (hkey), const_cast<char*>(keyChar), const_cast<char*>(contentChar)) ? JNI_TRUE : JNI_FALSE;
	env -> ReleaseStringUTFChars(key, keyChar);
	env -> ReleaseStringUTFChars(content, contentChar);
	return result;
}

/*
 * Class:     javax_jao_Native
 * Method:    jao_library_winreg_get
 * Signature: (JLjava/lang/String;Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_javax_jao_Native_jao_1library_1winreg_1get(JNIEnv* env, jclass clazz, jlong hkey, jstring key, jstring value)
{
	const char* keyChar = env -> GetStringUTFChars(key, JNI_FALSE);
	HKEY hkeySee = libraryWinRegSee((HKEY) (ULONG_PTR) (hkey), const_cast<char*>(keyChar));
	env -> ReleaseStringUTFChars(key, keyChar);
	if(hkeySee != NULL)
	{
		const char* valueChar = env -> GetStringUTFChars(value, JNI_FALSE);
		char* result = libraryWinRegGet(8192, hkeySee, const_cast<char*>(valueChar));
		env -> ReleaseStringUTFChars(value, valueChar);
		libraryWinRegRid(hkeySee);
		if(result != NULL)
			return env -> NewStringUTF(result);
	}
	return NULL;
}

/*
 * Class:     javax_jao_Native
 * Method:    jao_library_winreg_get_old
 * Signature: (JLjava/lang/String;Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_javax_jao_Native_jao_1library_1winreg_1get_1old(JNIEnv* env, jclass clazz, jlong hkey, jstring key, jstring value)
{
	const char* keyChar = env -> GetStringUTFChars(key, JNI_FALSE);
	const char* valueChar = env -> GetStringUTFChars(value, JNI_FALSE);
	char* result = libraryWinRegGetOld(8192, (HKEY) (ULONG_PTR) (hkey), const_cast<char*>(keyChar), const_cast<char*>(valueChar));
	env -> ReleaseStringUTFChars(key, keyChar);
	env -> ReleaseStringUTFChars(value, valueChar);
	if(result != NULL)
		return env -> NewStringUTF(result);
	else
		return NULL;
}

/*
 * Class:     javax_jao_Native
 * Method:    jao_library_winreg_fit
 * Signature: (JLjava/lang/String;Ljava/lang/String;)Z
 */
JNIEXPORT jboolean JNICALL Java_javax_jao_Native_jao_1library_1winreg_1fit(JNIEnv* env, jclass clazz, jlong hkey, jstring key, jstring value)
{
	const char* keyChar = env -> GetStringUTFChars(key, JNI_FALSE);
	HKEY hkeySee = libraryWinRegSee((HKEY) (ULONG_PTR) (hkey), const_cast<char*>(keyChar));
	env -> ReleaseStringUTFChars(key, keyChar);
	if(hkeySee != NULL)
	{
		const char* valueChar = env -> GetStringUTFChars(value, JNI_FALSE);
		jboolean result = libraryWinRegFit(hkeySee, const_cast<char*>(valueChar)) ? JNI_TRUE : JNI_FALSE;
		env -> ReleaseStringUTFChars(value, valueChar);
		libraryWinRegRid(hkeySee);
		return result;
	}
	return JNI_FALSE;
}

/*
 * Class:     javax_jao_Native
 * Method:    jao_library_winreg_del
 * Signature: (JLjava/lang/String;Ljava/lang/String;)Z
 */
JNIEXPORT jboolean JNICALL Java_javax_jao_Native_jao_1library_1winreg_1del__JLjava_lang_String_2Ljava_lang_String_2(JNIEnv* env, jclass clazz, jlong hkey, jstring key, jstring value)
{
	const char* keyChar = env -> GetStringUTFChars(key, JNI_FALSE);
	HKEY hkeySee = libraryWinRegSee((HKEY) (ULONG_PTR) (hkey), const_cast<char*>(keyChar));
	env -> ReleaseStringUTFChars(key, keyChar);
	if(hkeySee != NULL)
	{
		const char* valueChar = env -> GetStringUTFChars(value, JNI_FALSE);
		jboolean result = libraryWinRegDel(hkeySee, const_cast<char*>(valueChar)) ? JNI_TRUE : JNI_FALSE;
		env -> ReleaseStringUTFChars(value, valueChar);
		libraryWinRegRid(hkeySee);
		return result;
	}
	return JNI_FALSE;
}

/*
 * Class:     javax_jao_Native
 * Method:    jao_library_winreg_del
 * Signature: (JLjava/lang/String;)Z
 */
JNIEXPORT jboolean JNICALL Java_javax_jao_Native_jao_1library_1winreg_1del__JLjava_lang_String_2(JNIEnv* env, jclass clazz, jlong hkey, jstring key)
{
	const char* keyChar = env -> GetStringUTFChars(key, JNI_FALSE);
	jboolean result = libraryWinRegDelKey((HKEY) (ULONG_PTR) (hkey), const_cast<char*>(keyChar)) ? JNI_TRUE : JNI_FALSE;
	env -> ReleaseStringUTFChars(key, keyChar);
	return result;
}

/*
 * Class:     javax_jao_Native
 * Method:    jao_library_text_concat
 * Signature: (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_javax_jao_Native_jao_1library_1text_1concat(JNIEnv* env, jclass clazz, jstring textA, jstring textB)
{
	char result[8192];
	const char* a = env -> GetStringUTFChars(textA, JNI_FALSE);
	const char* b = env -> GetStringUTFChars(textB, JNI_FALSE);
	libraryTextConcat(result, const_cast<char*>(a), const_cast<char*>(b));
	env -> ReleaseStringUTFChars(textA, a);
	env -> ReleaseStringUTFChars(textB, b);
	return env -> NewStringUTF(result);
}

/*
 * Class:     javax_jao_Native
 * Method:    jao_native_register
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_javax_jao_Native_jao_1native_1register(JNIEnv* env, jclass clazz)
{
	if((library = LoadLibrary(LIBRARY)) != NULL)
	{
		int result = 0;
		
		if((libraryWinRegSee = (WinRegSee) GetProcAddress(library, "WinRegSee")) == NULL)
			result++;
		if((libraryWinRegNew = (WinRegNew) GetProcAddress(library, "WinRegNew")) == NULL)
			result++;
		if((libraryWinRegSet = (WinRegSet) GetProcAddress(library, "WinRegSet")) == NULL)
			result++;
		if((libraryWinRegSetOld = (WinRegSetOld) GetProcAddress(library, "WinRegSetOld")) == NULL)
			result++;
		if((libraryWinRegGet = (WinRegGet) GetProcAddress(library, "WinRegGet")) == NULL)
			result++;
		if((libraryWinRegGetOld = (WinRegGetOld) GetProcAddress(library, "WinRegGetOld")) == NULL)
			result++;
		if((libraryWinRegFit = (WinRegFit) GetProcAddress(library, "WinRegFit")) == NULL)
			result++;
		if((libraryWinRegDel = (WinRegDel) GetProcAddress(library, "WinRegDel")) == NULL)
			result++;
		if((libraryWinRegDelKey = (WinRegDelKey) GetProcAddress(library, "WinRegDelKey")) == NULL)
			result++;
		if((libraryWinRegRid = (WinRegRid) GetProcAddress(library, "WinRegRid")) == NULL)
			result++;
		if((libraryTextConcat = (TextConcat) GetProcAddress(library, "TextConcat")) == NULL)
			result++;
		
		if(result == 0)
			return JNI_TRUE;
	}
	
	return JNI_FALSE;
}

/*
 * Class:     javax_jao_Native
 * Method:    jao_native_print_echo
 * Signature: (Ljava/lang/String;)Z
 */
JNIEXPORT jboolean JNICALL Java_javax_jao_Native_jao_1native_1print_1echo(JNIEnv* env, jclass clazz, jstring text)
{
	char array[8192];
	const char* textChar = env -> GetStringUTFChars(text, JNI_FALSE);
	libraryTextConcat(array, "echo ", const_cast<char*>(textChar));
	env -> ReleaseStringUTFChars(text, textChar);
	return system(array) == 0 ? JNI_TRUE : JNI_FALSE;
}

/*
 * Class:     javax_jao_Native
 * Method:    jao_native_print_printf
 * Signature: (Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_javax_jao_Native_jao_1native_1print_1printf(JNIEnv* env, jclass clazz, jstring text)
{
	const char* textChar = env -> GetStringUTFChars(text, JNI_FALSE);
	int result = printf("%s", textChar);
	env -> ReleaseStringUTFChars(text, textChar);
	return result;
}

/*
 * Class:     javax_jao_Native
 * Method:    jao_native_print_cout
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_javax_jao_Native_jao_1native_1print_1cout(JNIEnv* env, jclass clazz, jstring text)
{
	const char* textChar = env -> GetStringUTFChars(text, JNI_FALSE);
	cout << textChar;
	env -> ReleaseStringUTFChars(text, textChar);
}

/*
 * Class:     javax_jao_Native
 * Method:    jao_native_println_echo
 * Signature: (Ljava/lang/String;)Z
 */
JNIEXPORT jboolean JNICALL Java_javax_jao_Native_jao_1native_1println_1echo(JNIEnv* env, jclass clazz, jstring text)
{
	char array[8192];
	const char* textChar = env -> GetStringUTFChars(text, JNI_FALSE);
	libraryTextConcat(array, "echo ", const_cast<char*>(textChar));
	env -> ReleaseStringUTFChars(text, textChar);
	return system(array) == 0 && system("echo.") ? JNI_TRUE : JNI_FALSE;
}

/*
 * Class:     javax_jao_Native
 * Method:    jao_native_println_printf
 * Signature: (Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_javax_jao_Native_jao_1native_1println_1printf(JNIEnv* env, jclass clazz, jstring text)
{
	const char* textChar = env -> GetStringUTFChars(text, JNI_FALSE);
	int result = printf("%s\n", textChar);
	env -> ReleaseStringUTFChars(text, textChar);
	return result;
}

/*
 * Class:     javax_jao_Native
 * Method:    jao_native_println_cout
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_javax_jao_Native_jao_1native_1println_1cout(JNIEnv* env, jclass clazz, jstring text)
{
	const char* textChar = env -> GetStringUTFChars(text, JNI_FALSE);
	cout << textChar << endl;
	env -> ReleaseStringUTFChars(text, textChar);
}

/*
 * Class:     javax_jao_Native
 * Method:    jao_native_read_cin
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_javax_jao_Native_jao_1native_1read_1cin__(JNIEnv* env, jclass clazz)
{
	char result[8192];
	cin >> result;
	return env -> NewStringUTF(result);
}

/*
 * Class:     javax_jao_Native
 * Method:    jao_native_read_cin
 * Signature: (Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_javax_jao_Native_jao_1native_1read_1cin__Ljava_lang_String_2(JNIEnv* env, jclass clazz, jstring prefix)
{
	char result[8192];
	const char* prefixChar = env -> GetStringUTFChars(prefix, JNI_FALSE);
	cout << prefixChar;
	env -> ReleaseStringUTFChars(prefix, prefixChar);
	cin >> result;
	return env -> NewStringUTF(result);
}

/*
 * Class:     javax_jao_Native
 * Method:    jao_native_execute_system
 * Signature: (Ljava/lang/String;Z)I
 */
JNIEXPORT jint JNICALL Java_javax_jao_Native_jao_1native_1execute_1system(JNIEnv* env, jclass clazz, jstring path, jboolean wait)
{
	char arrayA[8192];
	char arrayB[8192];
	char arrayC[8192];
	libraryTextConcat(arrayA, "start \"\" ", const_cast<char*>(wait ? "/wait \"" : "\""));
	const char* pathChar = env -> GetStringUTFChars(path, JNI_FALSE);
	libraryTextConcat(arrayB, arrayA, const_cast<char*>(pathChar));
	env -> ReleaseStringUTFChars(path, pathChar);
	libraryTextConcat(arrayC, arrayB, "\"");
	return system(arrayC);
}

/*
 * Class:     javax_jao_Native
 * Method:    jao_native_execute_system_cmd
 * Signature: (Ljava/lang/String;Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_javax_jao_Native_jao_1native_1execute_1system_1cmd__Ljava_lang_String_2Ljava_lang_String_2(JNIEnv* env, jclass clazz, jstring command, jstring args)
{
	char arrayA[8192];
	char arrayB[8192];
	char arrayC[8192];
	const char* argsChar = env -> GetStringUTFChars(args, JNI_FALSE);
	libraryTextConcat(arrayA, "cmd ", const_cast<char*>(argsChar));
	env -> ReleaseStringUTFChars(args, argsChar);
	libraryTextConcat(arrayB, arrayA, " ");
	const char* commandChar = env -> GetStringUTFChars(command, JNI_FALSE);
	libraryTextConcat(arrayC, arrayB, const_cast<char*>(commandChar));
	env -> ReleaseStringUTFChars(command, commandChar);
	return system(arrayC);
}

/*
 * Class:     javax_jao_Native
 * Method:    jao_native_execute_system_cmd
 * Signature: (Ljava/lang/String;Z)I
 */
JNIEXPORT jint JNICALL Java_javax_jao_Native_jao_1native_1execute_1system_1cmd__Ljava_lang_String_2Z(JNIEnv* env, jclass clazz, jstring command, jboolean remain)
{
	char array[8192];
	const char* commandChar = env -> GetStringUTFChars(command, JNI_FALSE);
	if(remain)
		libraryTextConcat(array, "cmd /k ", const_cast<char*>(commandChar));
	else
		libraryTextConcat(array, "cmd /c ", const_cast<char*>(commandChar));
	env -> ReleaseStringUTFChars(command, commandChar);
	return system(array);
}

/*
 * Class:     javax_jao_Native
 * Method:    jao_native_execute_system_pause
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_javax_jao_Native_jao_1native_1execute_1system_1pause(JNIEnv* env, jclass clazz)
{
	return system("pause") == 0 ? JNI_TRUE : JNI_FALSE;
}

/*
 * Class:     javax_jao_Native
 * Method:    jao_native_execute_shell
 * Signature: (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;I)I
 */
JNIEXPORT jint JNICALL Java_javax_jao_Native_jao_1native_1execute_1shell__Ljava_lang_String_2Ljava_lang_String_2Ljava_lang_String_2I(JNIEnv* env, jclass clazz, jstring file, jstring parms, jstring dir, jint show)
{
	
	const char* fileChar = env -> GetStringUTFChars(file, JNI_FALSE);
	const char* parmsChar = env -> GetStringUTFChars(parms, JNI_FALSE);
	const char* dirChar = env -> GetStringUTFChars(dir, JNI_FALSE);
	ShellExecute(NULL, "open", fileChar, parmsChar, dirChar, show);
	env -> ReleaseStringUTFChars(file, fileChar);
	env -> ReleaseStringUTFChars(parms, parmsChar);
	env -> ReleaseStringUTFChars(dir, dirChar);
	return GetLastError();
}

/*
 * Class:     javax_jao_Native
 * Method:    jao_native_execute_shell
 * Signature: (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_javax_jao_Native_jao_1native_1execute_1shell__Ljava_lang_String_2Ljava_lang_String_2Ljava_lang_String_2(JNIEnv* env, jclass clazz, jstring file, jstring parms, jstring dir)
{
	const char* fileChar = env -> GetStringUTFChars(file, JNI_FALSE);
	const char* parmsChar = env -> GetStringUTFChars(parms, JNI_FALSE);
	const char* dirChar = env -> GetStringUTFChars(dir, JNI_FALSE);
	ShellExecute(NULL, "open", fileChar, parmsChar, dirChar, SW_SHOWNORMAL);
	env -> ReleaseStringUTFChars(file, fileChar);
	env -> ReleaseStringUTFChars(parms, parmsChar);
	env -> ReleaseStringUTFChars(dir, dirChar);
	return GetLastError();
}

/*
 * Class:     javax_jao_Native
 * Method:    jao_native_execute_shell
 * Signature: (Ljava/lang/String;Ljava/lang/String;I)I
 */
JNIEXPORT jint JNICALL Java_javax_jao_Native_jao_1native_1execute_1shell__Ljava_lang_String_2Ljava_lang_String_2I(JNIEnv* env, jclass clazz, jstring file, jstring dir, jint show)
{
	const char* fileChar = env -> GetStringUTFChars(file, JNI_FALSE);
	const char* dirChar = env -> GetStringUTFChars(dir, JNI_FALSE);
	ShellExecute(NULL, "open", fileChar, NULL, dirChar, show);
	env -> ReleaseStringUTFChars(file, fileChar);
	env -> ReleaseStringUTFChars(dir, dirChar);
	return GetLastError();
}

/*
 * Class:     javax_jao_Native
 * Method:    jao_native_execute_shell
 * Signature: (Ljava/lang/String;Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_javax_jao_Native_jao_1native_1execute_1shell__Ljava_lang_String_2Ljava_lang_String_2(JNIEnv* env, jclass clazz, jstring file, jstring dir)
{
	const char* fileChar = env -> GetStringUTFChars(file, JNI_FALSE);
	const char* dirChar = env -> GetStringUTFChars(dir, JNI_FALSE);
	ShellExecute(NULL, "open", fileChar, NULL, dirChar, SW_SHOWNORMAL);
	env -> ReleaseStringUTFChars(file, fileChar);
	env -> ReleaseStringUTFChars(dir, dirChar);
	return GetLastError();
}

/*
 * Class:     javax_jao_Native
 * Method:    jao_native_execute_shell
 * Signature: (Ljava/lang/String;I)I
 */
JNIEXPORT jint JNICALL Java_javax_jao_Native_jao_1native_1execute_1shell__Ljava_lang_String_2I(JNIEnv* env, jclass clazz, jstring file, jint show)
{
	const char* fileChar = env -> GetStringUTFChars(file, JNI_FALSE);
	ShellExecute(NULL, "open", fileChar, NULL, NULL, show);
	env -> ReleaseStringUTFChars(file, fileChar);
	return GetLastError();
}

/*
 * Class:     javax_jao_Native
 * Method:    jao_native_execute_shell
 * Signature: (Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_javax_jao_Native_jao_1native_1execute_1shell__Ljava_lang_String_2(JNIEnv* env, jclass clazz, jstring file)
{
	const char* fileChar = env -> GetStringUTFChars(file, JNI_FALSE);
	ShellExecute(NULL, "open", fileChar, NULL, NULL, SW_SHOWNORMAL);
	env -> ReleaseStringUTFChars(file, fileChar);
	return GetLastError();
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

