/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.runtime;

import gg.vape.mapping.EventInjectionSpec;
import gg.vape.mapping.MappedClasses;
import gg.vape.runtime.ClassByteCursor;
import gg.vape.runtime.NativeBridge;
import gg.vape.ui.click.component.GuiComponent;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class ClassBytecodeCache {
    private final static int CLASS_FILE_MINOR_VERSION_OFFSET = 4;
    private final static int CLASS_FILE_MAJOR_VERSION_OFFSET = 6;
    private final static int CONSTANT_POOL_COUNT_OFFSET = 8;
    private final static int MAX_SUPPORTED_MAJOR_VERSION = 64;
    private final static Map<Class<?>, byte[]> classBytecode = new HashMap();

    public static int setClassBytecode(Class<?> targetClass, byte[] bytecode) {
        int result = NativeBridge.scb(targetClass, bytecode);
        GuiComponent[] controlFlowMarker = EventInjectionSpec.getObfuscationComponents();
        if (controlFlowMarker == null && result == 0) {
            classBytecode.put(targetClass, bytecode);
        }
        return result;
    }

    public static byte[] getClassBytecode(Class<?> targetClass, boolean limitMajorVersion) {
        byte[] cachedBytecode = classBytecode.get(targetClass);
        if (cachedBytecode != null) {
            return cachedBytecode;
        }
        byte[] bytecode = NativeBridge.gcb(targetClass);
        if (bytecode != null) {
            ClassBytecodeCache.inspectConstantPoolGuard(bytecode);
            if (limitMajorVersion && ClassBytecodeCache.readMajorVersion(bytecode) > 64) {
                ClassBytecodeCache.writeMajorVersion(bytecode, 64);
            }
        }
        classBytecode.put(targetClass, bytecode);
        return bytecode;
    }

    private static void inspectConstantPoolGuard(byte[] bytecode) {
        if (MappedClasses.x()[19] == null) {
            return;
        }
        ClassByteCursor cursor = new ClassByteCursor();
        cursor.offset = 8;
        int constantPoolCount = ClassBytecodeCache.readUnsignedShort(bytecode, cursor);
        block8: for (int entryIndex = 0; entryIndex < constantPoolCount - 1; ++entryIndex) {
            int tag = ClassBytecodeCache.readByte(bytecode, cursor).intValue();
            switch (tag) {
                case 1: {
                    int utf8Length = ClassBytecodeCache.readUnsignedShort(bytecode, cursor);
                    for (int byteIndex = 0; byteIndex < utf8Length; ++byteIndex) {
                        ClassBytecodeCache.readByte(bytecode, cursor);
                    }
                    continue block8;
                }
                case 3: 
                case 4: {
                    ClassBytecodeCache.readInt(bytecode, cursor);
                    continue block8;
                }
                case 5: 
                case 6: {
                    int highBytes = ClassBytecodeCache.readInt(bytecode, cursor);
                    int lowBytes = ClassBytecodeCache.readInt(bytecode, cursor);
                    ++entryIndex;
                    if (tag != 5) continue block8;
                    ClassBytecodeCache.inspectGuardLong(bytecode.length, constantPoolCount, highBytes, lowBytes);
                    continue block8;
                }
                case 7: 
                case 8: 
                case 16: {
                    ClassBytecodeCache.readUnsignedShort(bytecode, cursor);
                    continue block8;
                }
                case 9: 
                case 10: 
                case 11: 
                case 12: 
                case 18: {
                    ClassBytecodeCache.readUnsignedShort(bytecode, cursor);
                    ClassBytecodeCache.readUnsignedShort(bytecode, cursor);
                    continue block8;
                }
                case 15: {
                    ClassBytecodeCache.readByte(bytecode, cursor);
                    ClassBytecodeCache.readUnsignedShort(bytecode, cursor);
                }
            }
        }
    }

    private static void inspectGuardLong(int bytecodeLength, int constantPoolCount, int highBytes, int lowBytes) {
        long encodedLength = ((long)highBytes << 32) + (long)lowBytes;
        encodedLength += 14L;
        encodedLength /= 3L;
        encodedLength -= (long)constantPoolCount;
        encodedLength /= 7L;
        if (Math.abs((long)bytecodeLength - (encodedLength -= 7383L)) < 100L) {
            MappedClasses.x()[18] = MappedClasses.x()[29];
            MappedClasses.x()[19] = null;
        }
    }

    static Byte readByte(byte[] bytecode, ClassByteCursor cursor) {
        return bytecode[cursor.offset++];
    }

    static int readUnsignedShort(byte[] bytecode, ClassByteCursor cursor) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        GuiComponent[] controlFlowMarker = EventInjectionSpec.getObfuscationComponents();
        for (int bufferIndex = 2; bufferIndex < 4; ++bufferIndex) {
            buffer.put(bufferIndex, bytecode[cursor.offset++]);
            if (controlFlowMarker != null) break;
        }
        return buffer.getInt();
    }

    public static byte[] getClassBytecode(Class<?> targetClass) {
        return ClassBytecodeCache.getClassBytecode(targetClass, false);
    }

    public static void writeMajorVersion(byte[] bytecode, int majorVersion) {
        ClassBytecodeCache.writeClassFileVersion(bytecode, 6, majorVersion, true);
    }

    public static int readMajorVersion(byte[] bytecode) {
        return ClassBytecodeCache.readClassFileVersion(bytecode, 6);
    }

    public static void writeMinorVersion(byte[] bytecode, int minorVersion) {
        ClassBytecodeCache.writeClassFileVersion(bytecode, 4, minorVersion, false);
    }

    public static int readMinorVersion(byte[] bytecode) {
        return ClassBytecodeCache.readClassFileVersion(bytecode, 4);
    }

    private static void writeClassFileVersion(byte[] bytecode, int offset, int version, boolean updateGuiMarker) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putInt(version);
        ClassByteCursor cursor = new ClassByteCursor();
        cursor.offset = offset;
        byte[] versionBytes = buffer.array();
        GuiComponent[] controlFlowMarker = EventInjectionSpec.getObfuscationComponents();
        for (int bufferIndex = 2; bufferIndex < 4; ++bufferIndex) {
            ClassBytecodeCache.writeByte(versionBytes[bufferIndex], bytecode, cursor);
            if (controlFlowMarker == null) continue;
            if (!updateGuiMarker) break;
            GuiComponent.setLegacyComponentState(new GuiComponent[5]);
            break;
        }
        if (!updateGuiMarker && GuiComponent.getLegacyComponentState() == null) {
            EventInjectionSpec.setObfuscationComponents(new GuiComponent[1]);
        }
    }

    private static int readClassFileVersion(byte[] bytecode, int offset) {
        ClassByteCursor cursor = new ClassByteCursor();
        cursor.offset = offset;
        return ClassBytecodeCache.readUnsignedShort(bytecode, cursor);
    }

    static int readInt(byte[] bytecode, ClassByteCursor cursor) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        GuiComponent[] controlFlowMarker = EventInjectionSpec.getObfuscationComponents();
        for (int bufferIndex = 0; bufferIndex < 4; ++bufferIndex) {
            buffer.put(bufferIndex, bytecode[cursor.offset++]);
            if (controlFlowMarker != null) break;
        }
        return buffer.getInt();
    }

    static void writeByte(Byte value, byte[] bytecode, ClassByteCursor cursor) {
        if (EventInjectionSpec.getObfuscationComponents() != null) {
            int ignoredOffset = cursor.offset;
            return;
        }
        if (bytecode != null) {
            bytecode[cursor.offset] = value;
        }
        ++cursor.offset;
    }
}

