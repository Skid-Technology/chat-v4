/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 *  org.jetbrains.annotations.UnmodifiableView
 */
package gg.vape.value;

import java.util.Comparator;
import java.util.List;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

public interface ListValueSuggestionProvider {
    public void setComparator(@Nullable Comparator<String> var1);

    public @UnmodifiableView List<String> getValues();

    public @UnmodifiableView List<String> getSuggestions();

    @Nullable
    public Comparator<String> getComparator();

    public void updateFilter(String var1);

    public void setIncludeAllWhenEmpty(boolean var1);

    public boolean isIncludeAllWhenEmpty();
}

