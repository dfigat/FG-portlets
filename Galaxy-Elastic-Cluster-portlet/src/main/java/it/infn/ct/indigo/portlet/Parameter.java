package it.infn.ct.indigo.portlet;

import java.util.Iterator;
import java.util.List;

public class Parameter {
    private String type;
    private String value;
    private String name;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class array implements Iterable<Parameter>{
        public List<Parameter> parameters;
        public Iterator<Parameter> iterator() {
            return this.parameters.iterator();
        }
    }
}

