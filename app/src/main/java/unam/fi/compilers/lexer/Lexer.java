package unam.fi.compilers.lexer;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.lang.StringBuilder;
import java.util.regex.*;
import android.content.res.AssetManager;
public class Lexer {
    public ArrayList<StringBuilder> lexemes;
    public HashMap<String,HashSet<String>> token_classification;
    public HashSet<String> keywords;

    private int total_tokens;
    public Lexer(ArrayList<StringBuilder> lexemes, Context context) throws IOException {
        this.lexemes = lexemes;
        this.keywords = new HashSet<>();
        this.token_classification = new HashMap<>();
        this.total_tokens = 0;

        this.token_classification.put("Keywords", new HashSet<>());
        this.token_classification.put("Identifiers", new HashSet<>());
        this.token_classification.put("Operators", new HashSet<>());
        this.token_classification.put("Punctuation", new HashSet<>());
        this.token_classification.put("Constants", new HashSet<>());
        this.token_classification.put("Literals", new HashSet<>());
        this.token_classification.put("Unknown", new HashSet<>());

        AssetManager assetManager = context.getAssets();

        try {
            BufferedReader kw_reader = new BufferedReader(new InputStreamReader(assetManager.open("Keywords.txt")));
            String line = kw_reader.readLine();
            while (line != null) {
                this.keywords.add(line);
                line = kw_reader.readLine();
            }
            kw_reader.close();

            BufferedReader token_reader = new BufferedReader(new InputStreamReader(assetManager.open("Tokens.txt")));
            line = token_reader.readLine();
            while (line != null) {
                this.token_classification.put(line, new HashSet<>());
                line = token_reader.readLine();
            }
            token_reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String createKeywordRegex(){
        StringBuilder regex_creator = new StringBuilder();

        for(String keyword : keywords){
            regex_creator.append(keyword).append('|');
        }

        regex_creator.deleteCharAt(regex_creator.length()-1);
        return regex_creator.toString();
    }
    public HashMap<String, HashSet<String>> tokenize(){
        String keyword_regex = this.createKeywordRegex();

        String id_regex = "[a-zA-Z_][a-zA-Z0-9_]*";
        String op_regex = ">>=|<<=|\\+=|-=|\\*=|/=|%=|==|!=|>=|<=|&&|\\|\\||\\+\\+|--|&=|\\|=|\\^=|=|>|<|!|\\+|-|\\*|/|%|&|\\||\\^";
        String punt_regex = "\\*|\\(|\\)|\\.|,|:|;|\\{|\\}|->";
        String const_regex = "-?[0-9]+(\\.[0-9]+)? | '[a-zA-Z]'";
        String lit_regex = "\".*\"";

        String known_regex = String.join("|", keyword_regex, id_regex, op_regex, punt_regex, const_regex, lit_regex);

        for(int i=0; i<this.lexemes.size(); i++){
            // Delete comments
            String lexeme = this.lexemes.get(i).toString();
            String cleared_lexeme = lexeme.replaceAll("//.*", "").trim();
            if(cleared_lexeme.isEmpty()){
                lexemes.set(i, new StringBuilder("\n"));
                continue;
            } else{
                lexemes.set(i, new StringBuilder(cleared_lexeme));
            }

            String no_lit_lexeme = cleared_lexeme.replaceAll("\".*\"", "").trim();

            // Check and classify tokens
            classifyAndCount(keyword_regex, no_lit_lexeme, "Keywords");
            classifyAndCount(id_regex, no_lit_lexeme, "Identifiers");
            classifyAndCount(op_regex, no_lit_lexeme, "Operators");
            classifyAndCount(punt_regex, no_lit_lexeme, "Punctuation");
            classifyAndCount(const_regex, no_lit_lexeme, "Constants");
            classifyAndCount(lit_regex, cleared_lexeme, "Literals");

            // Identify unknown tokens
            Matcher knownMatcher = Pattern.compile(known_regex).matcher(cleared_lexeme);
            int knownEnd = 0;
            while (knownMatcher.find()){
                knownEnd = knownMatcher.end();
            }

            if (knownEnd < cleared_lexeme.length()){
                String unknownPart = cleared_lexeme.substring(knownEnd).trim();
                if (!unknownPart.isEmpty()) {
                    this.token_classification.get("Unknown").add(unknownPart);
                    this.total_tokens++;
                }
            }
        }

        return this.token_classification;
    }

    private void classifyAndCount(String regex, String text, String category){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        while(matcher.find()){
            String token = matcher.group();

            if("Identifiers".equals(category)){
                if(!this.keywords.contains(token)){
                    this.token_classification.get(category).add(token);
                }
            }else{
                this.token_classification.get(category).add(token);
            }

            this.total_tokens++;
        }
    }



    public int getTotal_tokens(){return this.total_tokens;}
}
