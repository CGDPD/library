module library.types {
    exports com.cgdpd.library.types;
    exports com.cgdpd.library.types.serializer;

    requires library.common;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
}
