package model;

import java.io.Serializable;

public class B extends A implements Serializable {

    transient private String b;
    private String c;
    private static String d;

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public static String getD() {
        return d;
    }

    public static void setD(String d) {
        B.d = d;
    }
}
