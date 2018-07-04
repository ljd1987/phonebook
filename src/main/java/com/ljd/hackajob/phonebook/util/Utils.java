package com.ljd.hackajob.phonebook.util;

/**
 *
 * @author leodavison
 *
 */
public class Utils {
    private Utils() {
        // nothing needed here.
    }

    public static boolean safeEquals(Object a, Object b) {
        if (a == null) {
            return (b==null);
        }

        return a.equals(b);
    }

//    public static class RefBuilder {
//        private Refs refs;
//
//        public RefBuilder() {
//            this.refs = new Refs();
//        }
//
//        public RefBuilder withRef(String name, String url) {
//            this.refs.put(name, url);
//            return this;
//        }
//
//        public Refs build() {
//            return refs;
//        }
//    }
}
