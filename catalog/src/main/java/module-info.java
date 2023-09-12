module library.catalog {

    requires library.common;
    requires library.types;
    requires spring.tx;
    requires spring.web;
    requires spring.context;
    requires spring.data.jpa;
    requires spring.data.commons;
    requires java.compiler;
    requires jakarta.persistence;
    requires lombok;
    requires com.fasterxml.jackson.databind;
    requires org.mapstruct;
}
