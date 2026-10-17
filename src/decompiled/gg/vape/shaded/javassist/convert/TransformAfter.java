/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.shaded.javassist.convert;

import gg.vape.shaded.javassist.CtMethod;
import gg.vape.shaded.javassist.NotFoundException;
import gg.vape.shaded.javassist.bytecode.BadBytecode;
import gg.vape.shaded.javassist.bytecode.CodeIterator;
import gg.vape.shaded.javassist.convert.TransformBefore;
import gg.vape.shaded.javassist.convert.Transformer;

public class TransformAfter
extends TransformBefore {
    public TransformAfter(Transformer next, CtMethod origMethod, CtMethod afterMethod) throws NotFoundException {
        super(next, origMethod, afterMethod);
    }

    @Override
    protected int match2(int pos, CodeIterator iterator) throws BadBytecode {
        iterator.move(pos);
        iterator.insert(this.saveCode);
        iterator.insert(this.loadCode);
        int p = iterator.insertGap(3);
        iterator.setMark(p);
        iterator.insert(this.loadCode);
        pos = iterator.next();
        p = iterator.getMark();
        iterator.writeByte(iterator.byteAt(pos), p);
        iterator.write16bit(iterator.u16bitAt(pos + 1), p + 1);
        iterator.writeByte(184, pos);
        iterator.write16bit(this.newIndex, pos + 1);
        iterator.move(p);
        return iterator.next();
    }
}

