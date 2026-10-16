/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  javax.annotation.concurrent.Immutable
 */
package com.google.common.net;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Ascii;
import com.google.common.base.CharMatcher;
import com.google.common.base.Charsets;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import java.nio.charset.Charset;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Map;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@Beta
@GwtCompatible
@Immutable
public final class MediaType {
    private final static String CHARSET_ATTRIBUTE = "charset";
    private final static ImmutableListMultimap<String, String> UTF_8_CONSTANT_PARAMETERS = ImmutableListMultimap.of("charset", Ascii.toLowerCase(Charsets.UTF_8.name()));
    private final static CharMatcher TOKEN_MATCHER = CharMatcher.ASCII.and(CharMatcher.JAVA_ISO_CONTROL.negate()).and(CharMatcher.isNot(' ')).and(CharMatcher.noneOf("()<>@,;:\\\"/[]?="));
    private final static CharMatcher QUOTED_TEXT_MATCHER = CharMatcher.ASCII.and(CharMatcher.noneOf("\"\\\r"));
    private final static CharMatcher LINEAR_WHITE_SPACE = CharMatcher.anyOf(" \t\r\n");
    private final static String APPLICATION_TYPE = "application";
    private final static String AUDIO_TYPE = "audio";
    private final static String IMAGE_TYPE = "image";
    private final static String TEXT_TYPE = "text";
    private final static String VIDEO_TYPE = "video";
    private final static String WILDCARD = "*";
    private final static Map<MediaType, MediaType> KNOWN_TYPES = Maps.newHashMap();
    final public static MediaType ANY_TYPE = MediaType.createConstant("*", "*");
    final public static MediaType ANY_TEXT_TYPE = MediaType.createConstant("text", "*");
    final public static MediaType ANY_IMAGE_TYPE = MediaType.createConstant("image", "*");
    final public static MediaType ANY_AUDIO_TYPE = MediaType.createConstant("audio", "*");
    final public static MediaType ANY_VIDEO_TYPE = MediaType.createConstant("video", "*");
    final public static MediaType ANY_APPLICATION_TYPE = MediaType.createConstant("application", "*");
    final public static MediaType CACHE_MANIFEST_UTF_8 = MediaType.createConstantUtf8("text", "cache-manifest");
    final public static MediaType CSS_UTF_8 = MediaType.createConstantUtf8("text", "css");
    final public static MediaType CSV_UTF_8 = MediaType.createConstantUtf8("text", "csv");
    final public static MediaType HTML_UTF_8 = MediaType.createConstantUtf8("text", "html");
    final public static MediaType I_CALENDAR_UTF_8 = MediaType.createConstantUtf8("text", "calendar");
    final public static MediaType PLAIN_TEXT_UTF_8 = MediaType.createConstantUtf8("text", "plain");
    final public static MediaType TEXT_JAVASCRIPT_UTF_8 = MediaType.createConstantUtf8("text", "javascript");
    final public static MediaType TSV_UTF_8 = MediaType.createConstantUtf8("text", "tab-separated-values");
    final public static MediaType VCARD_UTF_8 = MediaType.createConstantUtf8("text", "vcard");
    final public static MediaType WML_UTF_8 = MediaType.createConstantUtf8("text", "vnd.wap.wml");
    final public static MediaType XML_UTF_8 = MediaType.createConstantUtf8("text", "xml");
    final public static MediaType BMP = MediaType.createConstant("image", "bmp");
    final public static MediaType CRW = MediaType.createConstant("image", "x-canon-crw");
    final public static MediaType GIF = MediaType.createConstant("image", "gif");
    final public static MediaType ICO = MediaType.createConstant("image", "vnd.microsoft.icon");
    final public static MediaType JPEG = MediaType.createConstant("image", "jpeg");
    final public static MediaType PNG = MediaType.createConstant("image", "png");
    final public static MediaType PSD = MediaType.createConstant("image", "vnd.adobe.photoshop");
    final public static MediaType SVG_UTF_8 = MediaType.createConstantUtf8("image", "svg+xml");
    final public static MediaType TIFF = MediaType.createConstant("image", "tiff");
    final public static MediaType WEBP = MediaType.createConstant("image", "webp");
    final public static MediaType MP4_AUDIO = MediaType.createConstant("audio", "mp4");
    final public static MediaType MPEG_AUDIO = MediaType.createConstant("audio", "mpeg");
    final public static MediaType OGG_AUDIO = MediaType.createConstant("audio", "ogg");
    final public static MediaType WEBM_AUDIO = MediaType.createConstant("audio", "webm");
    final public static MediaType MP4_VIDEO = MediaType.createConstant("video", "mp4");
    final public static MediaType MPEG_VIDEO = MediaType.createConstant("video", "mpeg");
    final public static MediaType OGG_VIDEO = MediaType.createConstant("video", "ogg");
    final public static MediaType QUICKTIME = MediaType.createConstant("video", "quicktime");
    final public static MediaType WEBM_VIDEO = MediaType.createConstant("video", "webm");
    final public static MediaType WMV = MediaType.createConstant("video", "x-ms-wmv");
    final public static MediaType APPLICATION_XML_UTF_8 = MediaType.createConstantUtf8("application", "xml");
    final public static MediaType ATOM_UTF_8 = MediaType.createConstantUtf8("application", "atom+xml");
    final public static MediaType BZIP2 = MediaType.createConstant("application", "x-bzip2");
    final public static MediaType EOT = MediaType.createConstant("application", "vnd.ms-fontobject");
    final public static MediaType EPUB = MediaType.createConstant("application", "epub+zip");
    final public static MediaType FORM_DATA = MediaType.createConstant("application", "x-www-form-urlencoded");
    final public static MediaType KEY_ARCHIVE = MediaType.createConstant("application", "pkcs12");
    final public static MediaType APPLICATION_BINARY = MediaType.createConstant("application", "binary");
    final public static MediaType GZIP = MediaType.createConstant("application", "x-gzip");
    final public static MediaType JAVASCRIPT_UTF_8 = MediaType.createConstantUtf8("application", "javascript");
    final public static MediaType JSON_UTF_8 = MediaType.createConstantUtf8("application", "json");
    final public static MediaType KML = MediaType.createConstant("application", "vnd.google-earth.kml+xml");
    final public static MediaType KMZ = MediaType.createConstant("application", "vnd.google-earth.kmz");
    final public static MediaType MBOX = MediaType.createConstant("application", "mbox");
    final public static MediaType MICROSOFT_EXCEL = MediaType.createConstant("application", "vnd.ms-excel");
    final public static MediaType MICROSOFT_POWERPOINT = MediaType.createConstant("application", "vnd.ms-powerpoint");
    final public static MediaType MICROSOFT_WORD = MediaType.createConstant("application", "msword");
    final public static MediaType OCTET_STREAM = MediaType.createConstant("application", "octet-stream");
    final public static MediaType OGG_CONTAINER = MediaType.createConstant("application", "ogg");
    final public static MediaType OOXML_DOCUMENT = MediaType.createConstant("application", "vnd.openxmlformats-officedocument.wordprocessingml.document");
    final public static MediaType OOXML_PRESENTATION = MediaType.createConstant("application", "vnd.openxmlformats-officedocument.presentationml.presentation");
    final public static MediaType OOXML_SHEET = MediaType.createConstant("application", "vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    final public static MediaType OPENDOCUMENT_GRAPHICS = MediaType.createConstant("application", "vnd.oasis.opendocument.graphics");
    final public static MediaType OPENDOCUMENT_PRESENTATION = MediaType.createConstant("application", "vnd.oasis.opendocument.presentation");
    final public static MediaType OPENDOCUMENT_SPREADSHEET = MediaType.createConstant("application", "vnd.oasis.opendocument.spreadsheet");
    final public static MediaType OPENDOCUMENT_TEXT = MediaType.createConstant("application", "vnd.oasis.opendocument.text");
    final public static MediaType PDF = MediaType.createConstant("application", "pdf");
    final public static MediaType POSTSCRIPT = MediaType.createConstant("application", "postscript");
    final public static MediaType PROTOBUF = MediaType.createConstant("application", "protobuf");
    final public static MediaType RDF_XML_UTF_8 = MediaType.createConstantUtf8("application", "rdf+xml");
    final public static MediaType RTF_UTF_8 = MediaType.createConstantUtf8("application", "rtf");
    final public static MediaType SFNT = MediaType.createConstant("application", "font-sfnt");
    final public static MediaType SHOCKWAVE_FLASH = MediaType.createConstant("application", "x-shockwave-flash");
    final public static MediaType SKETCHUP = MediaType.createConstant("application", "vnd.sketchup.skp");
    final public static MediaType TAR = MediaType.createConstant("application", "x-tar");
    final public static MediaType WOFF = MediaType.createConstant("application", "font-woff");
    final public static MediaType XHTML_UTF_8 = MediaType.createConstantUtf8("application", "xhtml+xml");
    final public static MediaType XRD_UTF_8 = MediaType.createConstantUtf8("application", "xrd+xml");
    final public static MediaType ZIP = MediaType.createConstant("application", "zip");
    private final String type;
    private final String subtype;
    private final ImmutableListMultimap<String, String> parameters;
    private final static Joiner.MapJoiner PARAMETER_JOINER = Joiner.on("; ").withKeyValueSeparator("=");

    private static MediaType createConstant(String type, String subtype) {
        return MediaType.addKnownType(new MediaType(type, subtype, ImmutableListMultimap.<String, String>of()));
    }

    private static MediaType createConstantUtf8(String type, String subtype) {
        return MediaType.addKnownType(new MediaType(type, subtype, UTF_8_CONSTANT_PARAMETERS));
    }

    private static MediaType addKnownType(MediaType mediaType) {
        KNOWN_TYPES.put(mediaType, mediaType);
        return mediaType;
    }

    private MediaType(String type, String subtype, ImmutableListMultimap<String, String> parameters) {
        this.type = type;
        this.subtype = subtype;
        this.parameters = parameters;
    }

    public String type() {
        return this.type;
    }

    public String subtype() {
        return this.subtype;
    }

    public ImmutableListMultimap<String, String> parameters() {
        return this.parameters;
    }

    private Map<String, ImmutableMultiset<String>> parametersAsMap() {
        return Maps.transformValues(this.parameters.asMap(), new Function<Collection<String>, ImmutableMultiset<String>>(){

            @Override
            public ImmutableMultiset<String> apply(Collection<String> input) {
                return ImmutableMultiset.copyOf(input);
            }

            @Override
            public Object apply(Object x0) {
                return this.apply((Collection)x0);
            }
        });
    }

    public Optional<Charset> charset() {
        ImmutableSet charsetValues = ImmutableSet.copyOf(this.parameters.get((Object)CHARSET_ATTRIBUTE));
        switch (charsetValues.size()) {
            case 0: {
                return Optional.absent();
            }
            case 1: {
                return Optional.of(Charset.forName((String)Iterables.getOnlyElement(charsetValues)));
            }
        }
        throw new IllegalStateException("Multiple charset values defined: " + charsetValues);
    }

    public MediaType withoutParameters() {
        return this.parameters.isEmpty() ? this : MediaType.create(this.type, this.subtype);
    }

    public MediaType withParameters(Multimap<String, String> parameters) {
        return MediaType.create(this.type, this.subtype, parameters);
    }

    public MediaType withParameter(String attribute, String value) {
        Preconditions.checkNotNull(attribute);
        Preconditions.checkNotNull(value);
        String normalizedAttribute = MediaType.normalizeToken(attribute);
        ImmutableListMultimap.Builder builder = ImmutableListMultimap.builder();
        for (Map.Entry entry : this.parameters.entries()) {
            String key = (String)entry.getKey();
            if (normalizedAttribute.equals(key)) continue;
            builder.put(key, entry.getValue());
        }
        builder.put(normalizedAttribute, MediaType.normalizeParameterValue(normalizedAttribute, value));
        MediaType mediaType = new MediaType(this.type, this.subtype, (ImmutableListMultimap<String, String>)builder.build());
        return Objects.firstNonNull(KNOWN_TYPES.get(mediaType), mediaType);
    }

    public MediaType withCharset(Charset charset) {
        Preconditions.checkNotNull(charset);
        return this.withParameter(CHARSET_ATTRIBUTE, charset.name());
    }

    public boolean hasWildcard() {
        return WILDCARD.equals(this.type) || WILDCARD.equals(this.subtype);
    }

    public boolean is(MediaType mediaTypeRange) {
        return !(!mediaTypeRange.type.equals(WILDCARD) && !mediaTypeRange.type.equals(this.type) || !mediaTypeRange.subtype.equals(WILDCARD) && !mediaTypeRange.subtype.equals(this.subtype) || !((AbstractCollection)this.parameters.entries()).containsAll(mediaTypeRange.parameters.entries()));
    }

    public static MediaType create(String type, String subtype) {
        return MediaType.create(type, subtype, ImmutableListMultimap.<String, String>of());
    }

    static MediaType createApplicationType(String subtype) {
        return MediaType.create(APPLICATION_TYPE, subtype);
    }

    static MediaType createAudioType(String subtype) {
        return MediaType.create(AUDIO_TYPE, subtype);
    }

    static MediaType createImageType(String subtype) {
        return MediaType.create(IMAGE_TYPE, subtype);
    }

    static MediaType createTextType(String subtype) {
        return MediaType.create(TEXT_TYPE, subtype);
    }

    static MediaType createVideoType(String subtype) {
        return MediaType.create(VIDEO_TYPE, subtype);
    }

    private static MediaType create(String type, String subtype, Multimap<String, String> parameters) {
        Preconditions.checkNotNull(type);
        Preconditions.checkNotNull(subtype);
        Preconditions.checkNotNull(parameters);
        String normalizedType = MediaType.normalizeToken(type);
        String normalizedSubtype = MediaType.normalizeToken(subtype);
        Preconditions.checkArgument(!WILDCARD.equals(normalizedType) || WILDCARD.equals(normalizedSubtype), "A wildcard type cannot be used with a non-wildcard subtype");
        ImmutableListMultimap.Builder builder = ImmutableListMultimap.builder();
        for (Map.Entry<String, String> entry : parameters.entries()) {
            String attribute = MediaType.normalizeToken(entry.getKey());
            builder.put(attribute, MediaType.normalizeParameterValue(attribute, entry.getValue()));
        }
        MediaType mediaType = new MediaType(normalizedType, normalizedSubtype, (ImmutableListMultimap<String, String>)builder.build());
        return Objects.firstNonNull(KNOWN_TYPES.get(mediaType), mediaType);
    }

    private static String normalizeToken(String token) {
        Preconditions.checkArgument(TOKEN_MATCHER.matchesAllOf(token));
        return Ascii.toLowerCase(token);
    }

    private static String normalizeParameterValue(String attribute, String value) {
        return CHARSET_ATTRIBUTE.equals(attribute) ? Ascii.toLowerCase(value) : value;
    }

    public static MediaType parse(String input) {
        Preconditions.checkNotNull(input);
        Tokenizer tokenizer = new Tokenizer(input);
        try {
            String type = tokenizer.consumeToken(TOKEN_MATCHER);
            tokenizer.consumeCharacter('/');
            String subtype = tokenizer.consumeToken(TOKEN_MATCHER);
            ImmutableListMultimap.Builder parameters = ImmutableListMultimap.builder();
            while (tokenizer.hasMore()) {
                String value;
                tokenizer.consumeCharacter(';');
                tokenizer.consumeTokenIfPresent(LINEAR_WHITE_SPACE);
                String attribute = tokenizer.consumeToken(TOKEN_MATCHER);
                tokenizer.consumeCharacter('=');
                if ('\"' == tokenizer.previewChar()) {
                    tokenizer.consumeCharacter('\"');
                    StringBuilder valueBuilder = new StringBuilder();
                    while ('\"' != tokenizer.previewChar()) {
                        if ('\\' == tokenizer.previewChar()) {
                            tokenizer.consumeCharacter('\\');
                            valueBuilder.append(tokenizer.consumeCharacter(CharMatcher.ASCII));
                            continue;
                        }
                        valueBuilder.append(tokenizer.consumeToken(QUOTED_TEXT_MATCHER));
                    }
                    value = valueBuilder.toString();
                    tokenizer.consumeCharacter('\"');
                } else {
                    value = tokenizer.consumeToken(TOKEN_MATCHER);
                }
                parameters.put(attribute, value);
            }
            return MediaType.create(type, subtype, parameters.build());
        }
        catch (IllegalStateException e) {
            throw new IllegalArgumentException("Could not parse '" + input + "'", e);
        }
    }

    public boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof MediaType) {
            MediaType that = (MediaType)obj;
            return this.type.equals(that.type) && this.subtype.equals(that.subtype) && this.parametersAsMap().equals(that.parametersAsMap());
        }
        return false;
    }

    public int hashCode() {
        return Objects.hashCode(this.type, this.subtype, this.parametersAsMap());
    }

    public String toString() {
        StringBuilder builder = new StringBuilder().append(this.type).append('/').append(this.subtype);
        if (!this.parameters.isEmpty()) {
            builder.append("; ");
            ListMultimap<String, String> quotedParameters = Multimaps.transformValues(this.parameters, new Function<String, String>(){

                @Override
                public String apply(String value) {
                    return TOKEN_MATCHER.matchesAllOf(value) ? value : MediaType.escapeAndQuote(value);
                }

                @Override
                public Object apply(Object x0) {
                    return this.apply((String)x0);
                }
            });
            PARAMETER_JOINER.appendTo(builder, (Iterable<? extends Map.Entry<?, ?>>)quotedParameters.entries());
        }
        return builder.toString();
    }

    private static String escapeAndQuote(String value) {
        StringBuilder escaped = new StringBuilder(value.length() + 16).append('\"');
        for (char ch : value.toCharArray()) {
            if (ch == '\r' || ch == '\\' || ch == '\"') {
                escaped.append('\\');
            }
            escaped.append(ch);
        }
        return escaped.append('\"').toString();
    }

    private static final class Tokenizer {
        final String input;
        int position = 0;

        Tokenizer(String input) {
            this.input = input;
        }

        String consumeTokenIfPresent(CharMatcher matcher) {
            Preconditions.checkState(this.hasMore());
            int startPosition = this.position;
            this.position = matcher.negate().indexIn(this.input, startPosition);
            return this.hasMore() ? this.input.substring(startPosition, this.position) : this.input.substring(startPosition);
        }

        String consumeToken(CharMatcher matcher) {
            int startPosition = this.position;
            String token = this.consumeTokenIfPresent(matcher);
            Preconditions.checkState(this.position != startPosition);
            return token;
        }

        char consumeCharacter(CharMatcher matcher) {
            Preconditions.checkState(this.hasMore());
            char c = this.previewChar();
            Preconditions.checkState(matcher.matches(c));
            ++this.position;
            return c;
        }

        char consumeCharacter(char c) {
            Preconditions.checkState(this.hasMore());
            Preconditions.checkState(this.previewChar() == c);
            ++this.position;
            return c;
        }

        char previewChar() {
            Preconditions.checkState(this.hasMore());
            return this.input.charAt(this.position);
        }

        boolean hasMore() {
            return this.position >= 0 && this.position < this.input.length();
        }
    }
}

