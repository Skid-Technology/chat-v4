package gg.vape.mapping;

import javassist.CannotCompileException;
import javassist.NotFoundException;

public interface MappingTaskCallable<T> {
   T c() throws CannotCompileException, NotFoundException;
}
