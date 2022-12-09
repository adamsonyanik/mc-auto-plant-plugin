package org.json.simple;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class JSONArray extends ArrayList<JSONObject> implements JSONAware, JSONStreamAware {
    private static final long serialVersionUID = 3957988303675231981L;

    public JSONArray() {
    }

    public JSONArray(Collection<JSONObject> c) {
        super(c);
    }

    public static void writeJSONString(Collection collection, Writer out) throws IOException {
        if (collection == null) {
            out.write("null");
        } else {
            boolean first = true;
            Iterator iter = collection.iterator();
            out.write(91);

            while (iter.hasNext()) {
                if (first) {
                    first = false;
                } else {
                    out.write(44);
                }

                Object value = iter.next();
                if (value == null) {
                    out.write("null");
                } else {
                    JSONValue.writeJSONString(value, out);
                }
            }

            out.write(93);
        }
    }

    public void writeJSONString(Writer out) throws IOException {
        writeJSONString((Collection) this, out);
    }

    public static String toJSONString(Collection collection) {
        StringWriter writer = new StringWriter();

        try {
            writeJSONString((Collection) collection, writer);
            return writer.toString();
        } catch (IOException var3) {
            throw new RuntimeException(var3);
        }
    }

    public static void writeJSONString(byte[] array, Writer out) throws IOException {
        if (array == null) {
            out.write("null");
        } else if (array.length == 0) {
            out.write("[]");
        } else {
            out.write("[");
            out.write(String.valueOf(array[0]));

            for (int i = 1; i < array.length; ++i) {
                out.write(",");
                out.write(String.valueOf(array[i]));
            }

            out.write("]");
        }

    }

    public static String toJSONString(byte[] array) {
        StringWriter writer = new StringWriter();

        try {
            writeJSONString((byte[]) array, writer);
            return writer.toString();
        } catch (IOException var3) {
            throw new RuntimeException(var3);
        }
    }

    public static void writeJSONString(short[] array, Writer out) throws IOException {
        if (array == null) {
            out.write("null");
        } else if (array.length == 0) {
            out.write("[]");
        } else {
            out.write("[");
            out.write(String.valueOf(array[0]));

            for (int i = 1; i < array.length; ++i) {
                out.write(",");
                out.write(String.valueOf(array[i]));
            }

            out.write("]");
        }

    }

    public static String toJSONString(short[] array) {
        StringWriter writer = new StringWriter();

        try {
            writeJSONString((short[]) array, writer);
            return writer.toString();
        } catch (IOException var3) {
            throw new RuntimeException(var3);
        }
    }

    public static void writeJSONString(int[] array, Writer out) throws IOException {
        if (array == null) {
            out.write("null");
        } else if (array.length == 0) {
            out.write("[]");
        } else {
            out.write("[");
            out.write(String.valueOf(array[0]));

            for (int i = 1; i < array.length; ++i) {
                out.write(",");
                out.write(String.valueOf(array[i]));
            }

            out.write("]");
        }

    }

    public static String toJSONString(int[] array) {
        StringWriter writer = new StringWriter();

        try {
            writeJSONString((int[]) array, writer);
            return writer.toString();
        } catch (IOException var3) {
            throw new RuntimeException(var3);
        }
    }

    public static void writeJSONString(long[] array, Writer out) throws IOException {
        if (array == null) {
            out.write("null");
        } else if (array.length == 0) {
            out.write("[]");
        } else {
            out.write("[");
            out.write(String.valueOf(array[0]));

            for (int i = 1; i < array.length; ++i) {
                out.write(",");
                out.write(String.valueOf(array[i]));
            }

            out.write("]");
        }

    }

    public static String toJSONString(long[] array) {
        StringWriter writer = new StringWriter();

        try {
            writeJSONString((long[]) array, writer);
            return writer.toString();
        } catch (IOException var3) {
            throw new RuntimeException(var3);
        }
    }

    public static void writeJSONString(float[] array, Writer out) throws IOException {
        if (array == null) {
            out.write("null");
        } else if (array.length == 0) {
            out.write("[]");
        } else {
            out.write("[");
            out.write(String.valueOf(array[0]));

            for (int i = 1; i < array.length; ++i) {
                out.write(",");
                out.write(String.valueOf(array[i]));
            }

            out.write("]");
        }

    }

    public static String toJSONString(float[] array) {
        StringWriter writer = new StringWriter();

        try {
            writeJSONString((float[]) array, writer);
            return writer.toString();
        } catch (IOException var3) {
            throw new RuntimeException(var3);
        }
    }

    public static void writeJSONString(double[] array, Writer out) throws IOException {
        if (array == null) {
            out.write("null");
        } else if (array.length == 0) {
            out.write("[]");
        } else {
            out.write("[");
            out.write(String.valueOf(array[0]));

            for (int i = 1; i < array.length; ++i) {
                out.write(",");
                out.write(String.valueOf(array[i]));
            }

            out.write("]");
        }

    }

    public static String toJSONString(double[] array) {
        StringWriter writer = new StringWriter();

        try {
            writeJSONString((double[]) array, writer);
            return writer.toString();
        } catch (IOException var3) {
            throw new RuntimeException(var3);
        }
    }

    public static void writeJSONString(boolean[] array, Writer out) throws IOException {
        if (array == null) {
            out.write("null");
        } else if (array.length == 0) {
            out.write("[]");
        } else {
            out.write("[");
            out.write(String.valueOf(array[0]));

            for (int i = 1; i < array.length; ++i) {
                out.write(",");
                out.write(String.valueOf(array[i]));
            }

            out.write("]");
        }

    }

    public static String toJSONString(boolean[] array) {
        StringWriter writer = new StringWriter();

        try {
            writeJSONString((boolean[]) array, writer);
            return writer.toString();
        } catch (IOException var3) {
            throw new RuntimeException(var3);
        }
    }

    public static void writeJSONString(char[] array, Writer out) throws IOException {
        if (array == null) {
            out.write("null");
        } else if (array.length == 0) {
            out.write("[]");
        } else {
            out.write("[\"");
            out.write(String.valueOf(array[0]));

            for (int i = 1; i < array.length; ++i) {
                out.write("\",\"");
                out.write(String.valueOf(array[i]));
            }

            out.write("\"]");
        }

    }

    public static String toJSONString(char[] array) {
        StringWriter writer = new StringWriter();

        try {
            writeJSONString((char[]) array, writer);
            return writer.toString();
        } catch (IOException var3) {
            throw new RuntimeException(var3);
        }
    }

    public static void writeJSONString(Object[] array, Writer out) throws IOException {
        if (array == null) {
            out.write("null");
        } else if (array.length == 0) {
            out.write("[]");
        } else {
            out.write("[");
            JSONValue.writeJSONString(array[0], out);

            for (int i = 1; i < array.length; ++i) {
                out.write(",");
                JSONValue.writeJSONString(array[i], out);
            }

            out.write("]");
        }

    }

    public static String toJSONString(Object[] array) {
        StringWriter writer = new StringWriter();

        try {
            writeJSONString((Object[]) array, writer);
            return writer.toString();
        } catch (IOException var3) {
            throw new RuntimeException(var3);
        }
    }

    public String toJSONString() {
        return toJSONString((Collection) this);
    }

    public String toString() {
        return this.toJSONString();
    }
}
