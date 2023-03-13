package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String userEnter = "";
        if (args.length > 0 && args[0].length() > 0) {
            userEnter = args[0];
        }
        int columnNumber;
        //Проверка входных параметров
        while (!isDigit(userEnter)) {
            System.out.print("Введите корректный номер столбца:" + "\n" + ">>");
            userEnter = scanner.nextLine();
        }
        columnNumber = Integer.parseInt(userEnter);
        while (!(columnNumber > 0 && columnNumber < 15)) {
            System.out.print("Введите корректный номер столбца:" + "\n" + ">>");
            columnNumber = Integer.parseInt(scanner.nextLine());
        }
        String query = "";
        while (!query.equals("!quit")) {
            //Пользовательский ввод запроса
            System.out.print("Введите текст для запроса:" + "\n" + ">>");
            query = scanner.nextLine();
            String filePath = "./src/main/resources/airports.csv";
            try {
                //Начало отсчета времени
                long time = System.currentTimeMillis();
                //Заполнения списка для вывода информации
                List<Airport> airports = ParseProductCsv(filePath, query, columnNumber);
                for (int i = 0; i < airports.size(); i++) {
                    System.out.println(airports.get(i).toString());
                }
                //Конец отсчета времени
                System.out.println((System.currentTimeMillis() - time) + " мс" + ", Найдено строк: " + airports.size());
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    //Парсинг CSV файла по указанному пути и получение продуктов из него
    private static List<Airport> ParseProductCsv(String filePath, String query, int columnNumber) throws IOException {
        //Загрузка строк из файла
        List<Airport> airports = new ArrayList<Airport>();
        List<String> fileLines = Files.readAllLines(Paths.get(filePath));
        //Цикл по строкам
        for (String fileLine : fileLines) {
            String[] splitedText = fileLine.split(",");
            if ((splitedText[columnNumber - 1].split(query, -1).length - 1) > 0) {
                ArrayList<String> columnList = new ArrayList<String>();
                //Цикл по колонкам
                for (int i = 0; i < splitedText.length; i++) {
                    //Если колонка начинается на кавычки или заканчиваеться на кавычки
                    if (IsColumnPart(splitedText[i])) {
                        String lastText = columnList.get(columnList.size() - 1);
                        columnList.set(columnList.size() - 1, lastText + "," + splitedText[i]);
                    } else {
                        columnList.add(splitedText[i]);
                    }
                }
                Airport airport = new Airport();
                airport.column1 = columnList.get(0);
                airport.column2 = columnList.get(1);
                airport.column3 = columnList.get(2);
                airport.column4 = columnList.get(3);
                airport.column5 = columnList.get(4);
                airport.column6 = columnList.get(5);
                airport.column7 = columnList.get(6);
                airport.column8 = columnList.get(7);
                airport.column9 = columnList.get(8);
                airport.column10 = columnList.get(9);
                airport.column11 = columnList.get(10);
                airport.column12 = columnList.get(11);
                airport.column13 = columnList.get(12);
                airport.column14 = columnList.get(13);
                airports.add(airport);
            }
        }
        return airports;
    }

    //Проверка является ли колонка частью предыдущей колонки
    private static boolean IsColumnPart(String text) {
        String trimText = text.trim();
        //Если в тексте одна ковычка и текст на нее заканчиваеться значит это часть предыдущей колонки
        return trimText.indexOf("\"") == trimText.lastIndexOf("\"") && trimText.endsWith("\"");
    }

    //Проверка является ли строка числом
    private static boolean isDigit(String s) throws NumberFormatException {
        try {
            int num = Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}