package donadoni.dao;

public class Calculator {
    public static Calculator INSTANCE = new Calculator();

    private Calculator() {
    }


    public int Add(int a, int b) {
        return a+b;
    }

    public int Subtract(int a, int b)  {
        return a-b;
    }

    public int Multiply(int a, int b)  {
        return a*b;
    }

    public int Divide(int a, int b)  {
        return a/b;
    }
}
