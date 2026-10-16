/*
 * Decompiled with CFR 0.152.
 */
package gg.vape.mapping.mappings;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;
import gg.vape.mapping.MappingField;
import java.util.List;

public class MClientChunkProvider
extends Mapping {
    private final static String CHUNK_LISTING_FIELD_NAME = "chunkListing";
    private final MappingField chunkListingField = this.J("chunkListing", true, List.class);

    public MClientChunkProvider() {
        super(MappedClasses.le);
    }

    public List getChunkListing(Object clientChunkProvider) {
        return (List)this.chunkListingField.getObject(clientChunkProvider);
    }
}

