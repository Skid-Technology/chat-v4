package gg.vape.mapping;

import gg.vape.shaded.javassist.CannotCompileException;
import gg.vape.shaded.javassist.NotFoundException;

public interface MappingTaskCallable<T> {
   T c() throws CannotCompileException, NotFoundException;
}
