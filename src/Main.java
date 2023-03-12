import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        String output;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите выражение двух арабских или римских чисел от 1 до 10 (от I до X) включительно: ");
        String input = scanner.nextLine();
        if(input.length() > 10)
        {
            throw new IOException("Ошибка! Слишком длинное выражение!");
        }
        else if(input.isEmpty())
        {
            throw new IOException("Ошибка! Вы не ничего не ввели!");
        }
        output = calc(input);
        scanner.close();
        System.out.println(output);
    }

    public static String calc(String input) throws IOException {
        char[] input_calc = new char[10];
        char operation = ' ';
        int number1 = 0, number2 = 0;
        int result = 0;
        for (int i = 0; i < input.length(); i++) {
            input_calc[i] = input.charAt(i);
            if(operation == ' ') {
                if (input_calc[i] == '+') {
                    operation = '+';
                } else if (input_calc[i] == '-') {
                    operation = '-';
                } else if (input_calc[i] == '*') {
                    operation = '*';
                } else if (input_calc[i] == '/') {
                    operation = '/';
                }
            }
        }

        String input_charString = String.valueOf(input_calc);
        String[] separators = input_charString.split("[+-/*]");
        if(separators.length != 2) { // Проверка на количество чисел в выражении, по нахождению знака операции
            throw new IOException("Ошибка! Могут быть только два целых положительных числа!");
        }
        String num1 = separators[0].trim();
        String num2 = separators[1].trim();
        if(num1.isEmpty() || num2.isEmpty()){
            throw new IOException("Ошибка! Введите 2 числа!");
        }

        if(operation != '+' && operation != '-' && operation != '*' && operation != '/') {
            throw new IOException("Ошибка в знаке операции! ( '+', '-', '*', '/')");
        }

        boolean exists = true; // exists - true, если Римская, false, если Арабская
        try{
            Roman.valueOf(num1);
            Roman.valueOf(num2);
        } catch (IllegalArgumentException e) {
            exists = false;
        }

        if(exists){ // Проверка на систему счисления
            try{
                number1 = Roman.valueOf(num1).ordinal();
                number2 = Roman.valueOf(num2).ordinal();
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Ошибка! Вычисление в разных системах счисления!");
            }
        } else {
            try{
                number1 = Integer.parseInt(separators[0]);
                number2 = Integer.parseInt(separators[1].trim());
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Ошибка! Неправильный ввод чисел! На ввод принимаются только римская или арабская система счисления!");
            }
        }

        if(number1 <= 0 || number1 > 10 || number2 <= 0 || number2 > 10) {
            throw new IOException("Числа должны быть от 1 до 10 (от I до X) включительно!");
        }

        switch (operation) {
            case '+':
                result = number1 + number2;
                break;
            case '-':
                result = number1 - number2;
                break;
            case '*':
                result = number1 * number2;
                break;
            case '/':
                try {
                    result = number1 / number2;
                } catch (ArithmeticException  e) {
                    throw new ArithmeticException("На ноль делить нельзя!");
                }
                break;
            default:
                throw new IllegalArgumentException("Неверный знак операции!");
        }
        String output;
        if(exists){
            if(result < 1) {
                throw new IOException("В римской системе счисления нет отрицательных чисел и ноля!");
            }
            Roman[] resNum = Roman.values();
            Roman resultRoman = resNum[result];
            output = "Результат: " + resultRoman;
        } else {
            output = "Результат: " + result;
        }
        return output;
    }
}