package unam.fi.compilers.lexer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Lexer {
    public ArrayList<String> lexemes;
    public HashMap<String,HashSet<String>> token_classification;
    public HashSet<String> keywords;

    private int total_tokens;
    public Lexer(ArrayList<String> lexemes) throws IOException {
        this.lexemes = lexemes;
        this.keywords = new HashSet<>();
        this.token_classification = new HashMap<>();
        this.total_tokens = 0;

        BufferedReader kw_reader = new BufferedReader(new FileReader("Keywords.txt"));

        String line = kw_reader.readLine();
        while(line != null){
            this.keywords.add(line);
            line = kw_reader.readLine();
        }

        kw_reader.close();
        BufferedReader token_reader = new BufferedReader(new FileReader("Tokens.txt"));
    }

    public HashMap<String,HashSet<String>> tokenize(){
        ArrayList<Integer> to_delete = new ArrayList<>();
        int index = 0;
        for(String lexeme : this.lexemes){
            if(lexeme.length() >= 2 && lexeme.charAt(0) == '/' && lexeme.charAt(1) == '/'){
                to_delete.add(index);
                continue;
            }


        }

        return this.token_classification;
    }

    public int getTotal_tokens(){return this.total_tokens;}
}
