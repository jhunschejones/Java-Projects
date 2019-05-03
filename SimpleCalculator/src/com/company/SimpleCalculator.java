package com.company;

public class SimpleCalculator {

    public void simpleCalculator() {

    }

    public static boolean isDouble(String input) {
        try {
            if (input.isEmpty()) return false;

            new Double(input);
            return true;
        } catch (NumberFormatException exception) {
            return false;
        }
    }

    public String preformOperation(String operation, String firstValue, String secondValue) {

        String result;

        switch (operation) {
            case "+":
            case "plus":
                result = add(firstValue, secondValue);
                break;
            case "-":
            case "minus":
                result = subtract(firstValue, secondValue);
                break;
            case "*":
            case "multiply by":
            case "multiplied by":
            case "times":
                result = multiply(firstValue, secondValue);
                break;
            case "/":
            case "divide by":
            case "divided by":
                result = divide(firstValue, secondValue);
                break;
            default:
                result = "not a recognized operation";
                break;
        }
        return result;
    }

    public String add(String a, String b) {
        if (!isDouble(a) || !isDouble(b)) {
            return "an invalid operation";
        }

        Double value1 = new Double(a);
        Double value2 = new Double(b);
        return String.valueOf(value1 + value2);
    }

    public String subtract(String a, String b) {
        if (!isDouble(a) || !isDouble(b)) {
            return "an invalid operation";
        }

        Double value1 = new Double(a);
        Double value2 = new Double(b);
        return String.valueOf(value1 - value2);
    }

    public String multiply(String a, String b) {
        if (!isDouble(a) || !isDouble(b)) {
            return "an invalid operation";
        }

        Double value1 = new Double(a);
        Double value2 = new Double(b);
        return String.valueOf(value1 * value2);
    }

    public String divide(String a, String b) {
        if (!isDouble(a) || !isDouble(b)) {
            return "an invalid operation";
        }

        Double value1 = new Double(a);
        Double value2 = new Double(b);
        return String.valueOf(value1 / value2);
    }
}
