import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите выражение:");
        String input = scanner.nextLine();

        try {
            String result = calculate(input);
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    public static String calculate(String input) throws Exception {
        // Удаляем пробелы и проверяем, чтобы строка начиналась с кавычек
        input = input.trim();
        if (!input.startsWith("\"")) {
            throw new Exception("Первым аргументом должна быть строка.");
        }

        // Определение операции: +, -, *, /
        String operator;
        if (input.contains(" + ")) {
            operator = "+";
        } else if (input.contains(" - ")) {
            operator = "-";
        } else if (input.contains(" * ")) {
            operator = "*";
        } else if (input.contains(" / ")) {
            operator = "/";
        } else {
            throw new Exception("Некорректная операция.");
        }

        // Разделяем строку на операнды по оператору
        String[] parts = input.split("\\" + operator + " ");
        if (parts.length != 2) {
            throw new Exception("Некорректный формат ввода.");
        }

        String str1 = extractString(parts[0]);
        if (str1.length() > 10) {
            throw new Exception("Длина строки не должна превышать 10 символов.");
        }
        if (operator.equals("+") || operator.equals("-")) {
            String str2 = extractString(parts[1]);
            if (str2.length() > 10) {
                throw new Exception("Длина строки не должна превышать 10 символов.");
            }

            return performStringOperation(str1, str2, operator);
        } else if (operator.equals("*") || operator.equals("/")) {
            int num = extractNumber(parts[1]);
            if (num < 1 || num > 10) {
                throw new Exception("Число должно быть в диапазоне от 1 до 10.");
            }

            return performNumberOperation(str1, num, operator);
        }

        throw new Exception("Некорректная операция.");
    }

    // Извлекает строку из кавычек
    private static String extractString(String part) throws Exception {
        part = part.trim();
        if (part.startsWith("\"") && part.endsWith("\"")) {
            return part.substring(1, part.length() - 1);
        } else {
            throw new Exception("Неправильный формат строки.");
        }
    }

    // Преобразует строку в число
    private static int extractNumber(String part) throws Exception {
        try {
            return Integer.parseInt(part.trim());
        } catch (NumberFormatException e) {
            throw new Exception("Некорректный формат числа.");
        }
    }

    // Выполнение операций с двумя строками
    private static String performStringOperation(String str1, String str2, String operator) {
        if (operator.equals("+")) {
            return trimResult(str1 + str2);
        } else if (operator.equals("-")) {
            return trimResult(str1.replace(str2, ""));
        }
        return "";
    }

    // Выполнение операций строки с числом
    private static String performNumberOperation(String str, int num, String operator) {
        if (operator.equals("*")) {
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < num; i++) {
                result.append(str);
            }
            return trimResult(result.toString());
        } else if (operator.equals("/")) {
            if (num > str.length()) {
                return "";
            }
            return trimResult(str.substring(0, str.length() / num));
        }
        return "";
    }
    // Обрезка строки, если она длиннее 40 символов
    private static String trimResult(String result) {
        if (result.length() > 40) {
            return result.substring(0, 40) + "...";
        }
        return result;
    }

}
