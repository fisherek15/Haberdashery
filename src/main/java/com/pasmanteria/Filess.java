package com.pasmanteria;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by adrian on 08.05.2017.
 */
public class Filess {
/*
    public void writeToFile(String text, String fileName) {

        try {
            PrintWriter out = new PrintWriter(".\\invoice_data\\" + fileName);
            //String textStr[] = text.split("\\r\\n|\\n|\\r");
            //for(String line : textStr) {
            //out.println(line);
            //}
            out.println(text);
            out.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Plik nie może zostać utworzony!");
        }
    }
*/
    public String[][] makeArray(ArrayList<String> catalogNumberList,
                                ArrayList<String> articleNameList, ArrayList<String> discountPercentList,
                                ArrayList<String> taxPercentList, ArrayList<String> priceGrossTotalityList) {

        int longestColumn = 0;
        if (catalogNumberList.size() > longestColumn) longestColumn = catalogNumberList.size();
        if (articleNameList.size() > longestColumn) longestColumn = articleNameList.size();
        if (discountPercentList.size() > longestColumn) longestColumn = discountPercentList.size();
        if (taxPercentList.size() > longestColumn) longestColumn = taxPercentList.size();
        if (priceGrossTotalityList.size() > longestColumn) longestColumn = priceGrossTotalityList.size();

        String[][] ocrTab = new String[longestColumn][5];

        for(int i = 0; i < longestColumn; i++)
            for(int j = 0; j < 5; j++)
                ocrTab[i][j] = " ";

        for(int i = 0; i < catalogNumberList.size(); i++) {
            ocrTab[i][0] = catalogNumberList.get(i);
        }
        for(int i = 0; i < articleNameList.size(); i++) {
            ocrTab[i][1] = articleNameList.get(i);
        }
        for(int i = 0; i < discountPercentList.size(); i++) {
            ocrTab[i][2] = discountPercentList.get(i);
        }
        for(int i = 0; i < taxPercentList.size(); i++) {
            ocrTab[i][3] = taxPercentList.get(i);
        }
        for(int i = 0; i < priceGrossTotalityList.size(); i++) {
            ocrTab[i][4] = priceGrossTotalityList.get(i);
        }
        return ocrTab;
    }

    // funkcja zapisuje tekst w pliku tekstowym
    public boolean writeToCsv(String fileName, String[][] ocrTab) {

        Path path = Paths.get(fileName);

        try {
            Files.createDirectories(path.getParent());
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        try{
            Files.createFile(path);
        } catch (FileAlreadyExistsException e) {
            System.err.println("already exists: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<String> out = new ArrayList<>();

        // Pobranie kolejnego "rzędu" danych
        for (String[] series : ocrTab) {
            String s = new String();
            for(int i = 0; i < 5; i++){
                s = s.concat(series[i]);
                if(i < 4){
                    s = s.concat("\t ");
                }
            }
            s = s.replace("%","");

            // dodanie linijki z danymi do listy
            out.add(s);
        }
        try {
            Files.write(path, out);
            return true;
        } catch (IOException ex) {
            System.out.println("Nie mogę zapisać pliku!");
            return false;
        }
    }

    // Metoda odczytuje tekst z pliku tekstowego,
    public String[][] readCsv(String fileName) {
        // Tworzymy obiekt typu Path
        Path path = Paths.get(fileName);
        // Lista będzie przechowywała kolejne linie odczytane z pliku jako String
        ArrayList<String> out = new ArrayList<>();
        try {
            // Od razu odczytane zostają wszystkie linie pliku
            // i umieszczone w liście
            out = (ArrayList) Files.readAllLines(path);
        } catch (IOException ex) {
            System.out.println("Brak pliku!");
        }

        // Teraz trzeba łańcuchy znaków dla każdej linii przekonwertować
        // na dane łańcuchowe i umieścić je w tablicy

        // Tablica dla odczytanych danych
        // Na razie wiemy tylko ile będzie "rzędów"
        String[][] tab = new String[out.size()][];
        // Będziemy potrzebowali indeksu linii
        int rowNum = 0;
        // Pobranie kolejnych linii z listy
        for (String row : out) {
            // Rozbijamy linię na łańcuchy znaków (przedzielone tabulatorem)
            String[] line = row.split("\t");
            // Tablica do przechowania danych w fomie liczb double
            // Dodajemy tablicę z serią danych do tablicy z wszystkimi danymi
            tab[rowNum] = line;
            // kolejna linia...
            rowNum++;
        }
        return tab;
    }

    public static boolean isFileExist(String path) {
        File file = new File(path);

        if(file.exists()) {
            System.out.println("Plik istnieje");
            return true;
        } else {
            System.out.println("Plik nie został znaleziony!");
            return  false;
        }
    }

    public static void deleteFile(String path) {
        try {
            File file = new File(path);
            if(file.delete()){
                System.out.println(file.getName() + "został skasowany!");
            } else {
                System.out.println("Operacja kasowania nie powiodła się.");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

/*
    public ArrayList<String> readFile(String fileName) {

        ArrayList<String> valueList = new ArrayList<>();

        File file = new File(".\\invoice_data\\" + fileName);
        try {
            Scanner scanner = new Scanner(file);
            // Odczytywanie kolejnych linii pliku dopóki są kolejne linie
            while (scanner.hasNextLine()) {
                // Do listy dodawana jest kolejna linia
                valueList.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Brak pliku do odczytania!");
        }
        return valueList;
    }
*/
}