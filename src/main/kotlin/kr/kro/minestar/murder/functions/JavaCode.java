package kr.kro.minestar.murder.functions;

public class JavaCode {
    public void test() {
        Class testClass;
        try {
            testClass = Class.forName("ttt");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
