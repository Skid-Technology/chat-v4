/*
 * jni_md.h — Windows (x86-64) machine-dependent JNI typedefs.
 *
 * The OpenJDK jni.h includes this file; on Windows the JDK ships it under
 * include/win32/. This is the same content, vendored so the host DLL can be
 * cross-compiled with mingw-w64 without a Windows JDK at build time.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 */

#ifndef _JAVASOFT_JNI_MD_H_
#define _JAVASOFT_JNI_MD_H_

#define JNIEXPORT __declspec(dllexport)
#define JNIIMPORT __declspec(dllimport)
#define JNICALL __stdcall

/* 'long' is always 32 bit on Windows, so this matches what the JDK expects. */
typedef int jint;
typedef __int64 jlong;
typedef signed char jbyte;

#endif /* !_JAVASOFT_JNI_MD_H_ */
