package org.json.simple;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Collection;
import java.util.Map;

public class JSONValue {
    public JSONValue() {
    }

    /**
     * @deprecated
     */
    public static Object parse(Reader in) {
        try {
            JSONParser parser = new JSONParser();
            return parser.parse(in);
        } catch (Exception var2) {
            return null;
        }
    }

    /**
     * @deprecated
     */
    public static Object parse(String s) {
        StringReader in = new StringReader(s);
        return parse((Reader) in);
    }

    public static Object parseWithException(Reader in) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        return parser.parse(in);
    }

    public static Object parseWithException(String s) throws ParseException {
        JSONParser parser = new JSONParser();
        return parser.parse(s);
    }

    public static void writeJSONString(Object value, Writer out) throws IOException {
        if (value == null) {
            out.write("null");
        } else if (value instanceof String) {
            out.write(34);
            out.write(escape((String) value));
            out.write(34);
        } else if (value instanceof Double) {
            if (!((Double) value).isInfinite() && !((Double) value).isNaN()) {
                out.write(value.toString());
            } else {
                out.write("null");
            }

        } else if (!(value instanceof Float)) {
            if (value instanceof Number) {
                out.write(value.toString());
            } else if (value instanceof Boolean) {
                out.write(value.toString());
            } else if (value instanceof JSONStreamAware) {
                ((JSONStreamAware) value).writeJSONString(out);
            } else if (value instanceof JSONAware) {
                out.write(((JSONAware) value).toJSONString());
            } else if (value instanceof Map) {
                JSONObject.writeJSONString((Map) value, out);
            } else if (value instanceof Collection) {
                JSONArray.writeJSONString((Collection) value, out);
            } else if (value instanceof byte[]) {
                JSONArray.writeJSONString((byte[]) ((byte[]) value), out);
            } else if (value instanceof short[]) {
                JSONArray.writeJSONString((short[]) ((short[]) value), out);
            } else if (value instanceof int[]) {
                JSONArray.writeJSONString((int[]) ((int[]) value), out);
            } else if (value instanceof long[]) {
                JSONArray.writeJSONString((long[]) ((long[]) value), out);
            } else if (value instanceof float[]) {
                JSONArray.writeJSONString((float[]) ((float[]) value), out);
            } else if (value instanceof double[]) {
                JSONArray.writeJSONString((double[]) ((double[]) value), out);
            } else if (value instanceof boolean[]) {
                JSONArray.writeJSONString((boolean[]) ((boolean[]) value), out);
            } else if (value instanceof char[]) {
                JSONArray.writeJSONString((char[]) ((char[]) value), out);
            } else if (value instanceof Object[]) {
                JSONArray.writeJSONString((Object[]) ((Object[]) value), out);
            } else {
                out.write(value.toString());
            }
        } else {
            if (!((Float) value).isInfinite() && !((Float) value).isNaN()) {
                out.write(value.toString());
            } else {
                out.write("null");
            }

        }
    }

    public static String toJSONString(Object value) {
        StringWriter writer = new StringWriter();

        try {
            writeJSONString(value, writer);
            return writer.toString();
        } catch (IOException var3) {
            throw new RuntimeException(var3);
        }
    }

    public static String escape(String s) {
        if (s == null) {
            return null;
        } else {
            StringBuffer sb = new StringBuffer();
            escape(s, sb);
            return sb.toString();
        }
    }

    static void escape(String s, StringBuffer sb) {
        int len = s.length();

        for (int i = 0; i < len; ++i) {
            char ch = s.charAt(i);
            switch (ch) {
                case '\b':
                    sb.append("\\b");
                    continue;
                case '\t':
                    sb.append("\\t");
                    continue;
                case '\n':
                    sb.append("\\n");
                    continue;
                case '\f':
                    sb.append("\\f");
                    continue;
                case '\r':
                    sb.append("\\r");
                    continue;
                case '"':
                    sb.append("\\\"");
                    continue;
                case '/':
                    sb.append("\\/");
                    continue;
                case '\\':
                    sb.append("\\\\");
                    continue;
            }

            if (ch >= 0 && ch <= 31 || ch >= 127 && ch <= 159 || ch >= 8192 && ch <= 8447) {
                String ss = Integer.toHexString(ch);
                sb.append("\\u");

                for (int k = 0; k < 4 - ss.length(); ++k) {
                    sb.append('0');
                }

                sb.append(ss.toUpperCase());
            } else {
                sb.append(ch);
            }
        }

    }
}
