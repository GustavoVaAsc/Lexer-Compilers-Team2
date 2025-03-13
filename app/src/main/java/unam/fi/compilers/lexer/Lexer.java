package unam.fi.compilers.lexer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.lang.StringBuilder;
import java.util.List;
import java.util.regex.*;
public class Lexer {
    public ArrayList<StringBuilder> lexemes;
    public HashMap<String,HashSet<String>> token_classification;
    public HashSet<String> keywords;

    private int total_tokens;
    public Lexer(ArrayList<StringBuilder> lexemes) throws IOException {
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

    public String createKeywordRegex(){
        StringBuilder regex_creator = new StringBuilder();

        for(String keyword : keywords){
            regex_creator.append(keyword).append('|');
        }

        regex_creator.deleteCharAt(regex_creator.length()-1);
        return regex_creator.toString();
    }
    public HashMap<String,HashSet<String>> tokenize(){
        int index = 0;
        String keyword_regex = this.createKeywordRegex();
        for(int i=0; i<this.lexemes.size(); i++){
            // Delete comments
            String lexeme = this.lexemes.get(i).toString();
            String cleared_lexeme = lexeme.replaceAll("//.*", "").trim();
            if(cleared_lexeme.isEmpty()){
                lexemes.set(i,new StringBuilder("\n"));
                continue;
            }else{
                lexemes.set(i,new StringBuilder(cleared_lexeme));
            }
            // Check keyword
            Pattern pattern = Pattern.compile(keyword_regex);

            Matcher matcher = pattern.matcher(cleared_lexeme);

        }
        return this.token_classification;
    }

    public int getTotal_tokens(){return this.total_tokens;}
}
