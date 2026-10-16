package gg.vape.shaded.org.objectweb.asm.util;

import gg.vape.shaded.org.objectweb.asm.Label;
import java.util.Map;

public interface ASMifierSupport {
   void asmify(StringBuilder var1, String var2, Map<Label, String> var3);
}
