package com.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Hello world!
 *
 */
public class XMLconverter 
{   
    static final File ROOT = new File("./src/main/resources");
	static final String XML_FILE = "classe.xml";

    public ArrayList<String> openers;
    private HashMap<String, String> lista;
    
    public XMLconverter() {
    }

    public void Mod(){
        try{
        File file = new File(ROOT, XML_FILE);
        Scanner scanner = new Scanner(file);
        String xml = fileToString(file, scanner);
        xmlreader(xml);
        System.out.println(xml);
        }catch(FileNotFoundException ex){
            System.out.println("file non trovato");
            ex.printStackTrace();
        }catch(Exception e){
            System.out.println("sei stupido");
            e.printStackTrace();
        }
    }

    private String fileToString(File file, Scanner scanner) {
        try {
            String string = "";
            
            while(scanner.hasNextLine()){
                string += scanner.nextLine().trim() + "\n";
            }
            return string;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
        
    }

    private void xmlreader(String str){
        openers = new ArrayList<>();

        Pattern pattern = Pattern.compile("<(.*?)>");
        Matcher matcher = pattern.matcher(str);

        String tmp = "";

        while(matcher.find()){
            tmp = matcher.group(1);
            if(!tmp.startsWith("/")){
                openers.add(tmp);
            }
        }

        if(!openers.isEmpty()){
            if(openers.get(0).startsWith("?")){
                openers.remove(0);
            }
        }

        if(!openers.isEmpty()){
            if(openers.get(0).matches("root")){
                
                lista = new HashMap<>();

                for(int i = 0; i<openers.size(); i+=1){


                    pattern = Pattern.compile("<" + openers.get(i) + ">(.*?)</" + openers.get(i) + ">");
                    matcher = pattern.matcher(str);

                    while(matcher.find()){
                        lista.put(openers.get(i), matcher.group(1));
                    }
                }
            }
        }

        for(String key : lista.keySet()){
            System.out.println(lista.get(key) + "\n");
            System.out.println("\n");
            System.out.println(str);
        }
    }

}
