package com.peakoapp.core.constant.constnt;

/**
 * The {@code PeakoServerMetadata} is an internal class used for serialization across Peako Server
 * classes.
 *
 * @since 1.0.0
 */
public final class PeakoServerMetadata {
    public static final Integer MAJOR = 0;
    public static final Integer MINOR = 1;
    public static final Integer PATCH = 0;

    public static final String DOMAIN = "https://www.api.peakoapp.com";

    public static final long SERIAL_VERSION_UID = getVersion().hashCode();

    public static String getVersion() {
        return MAJOR + "." + MINOR + "." + PATCH;
    }
}
