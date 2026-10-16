package com.google.common.io;

import java.io.DataInput;

public interface ByteArrayDataInput extends DataInput {
   @Override
   void readFully(byte[] var1);

   @Override
   void readFully(byte[] var1, int var2, int var3);

   @Override
   int skipBytes(int var1);

   @Override
   boolean readBoolean();

   @Override
   byte readByte();

   @Override
   int readUnsignedByte();

   @Override
   short readShort();

   @Override
   int readUnsignedShort();

   @Override
   char readChar();

   @Override
   int readInt();

   @Override
   long readLong();

   @Override
   float readFloat();

   @Override
   double readDouble();

   @Override
   String readLine();

   @Override
   String readUTF();
}
