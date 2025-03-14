/*
 * Description: The Lexer class is responsible for processing a list of lexemes and classifying them into predefined
 * token categories such as Keywords, Identifiers, Operators, Punctuation, Constants, Literals, and Unknown tokens.
 *
 * Responsibilities:
 * - Load reserved keywords and token categories from asset files.
 * - Define regex patterns for token classification.
 * - Process and classify each lexeme.
 * - Identify and handle unknown tokens.
 * - Track the total number of recognized tokens.
 *
 * Asset Files Required:
 * - Keywords.txt: Contains a list of reserved keywords.
 * - Tokens.txt: Contains additional token classifications.
 *
 */

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
    public ArrayList<StringBuilder> lexemes; // Lexemes list
    public HashMap<String,HashSet<String>> token_classification; // Hashmap for token classification
    public HashSet<String> keywords; // Set for consulting keywords

    private int total_tokens; // Total tokens

    // Class constructor
    public Lexer(ArrayList<StringBuilder> lexemes, Context context) throws IOException {
        // Initialize Lexer attributes
        this.lexemes = lexemes;
        this.keywords = new HashSet<>();
        this.token_classification = new HashMap<>();
        this.total_tokens = 0;

        // Initialize categories
        this.token_classification.put("Keywords", new HashSet<>());
        this.token_classification.put("Identifiers", new HashSet<>());
        this.token_classification.put("Operators", new HashSet<>());
        this.token_classification.put("Punctuation", new HashSet<>());
        this.token_classification.put("Constants", new HashSet<>());
        this.token_classification.put("Literals", new HashSet<>());
        this.token_classification.put("Unknown", new HashSet<>());

        // Asset manager for loading our files
        AssetManager assetManager = context.getAssets();

        try {
            // Read keywords from file
            BufferedReader kw_reader = new BufferedReader(new InputStreamReader(assetManager.open("Keywords.txt")));
            String line = kw_reader.readLine();
            // Add keywords to the keywords set
            while(line != null){
                this.keywords.add(line);
                line = kw_reader.readLine();
            }
            kw_reader.close(); // Close reader

            // Same process for classifications
            BufferedReader token_reader = new BufferedReader(new InputStreamReader(assetManager.open("Tokens.txt")));
            line = token_reader.readLine();
            while(line != null){
                this.token_classification.put(line, new HashSet<>());
                line = token_reader.readLine();
            }
            token_reader.close(); // Close reader
        } catch (IOException e) {
            e.printStackTrace(); // Handling any IO Exception
        }

    }


    // Method to create keyword regex
    public String createKeywordRegex(){
        StringBuilder regex_creator = new StringBuilder();

        // Iterate keywords and create a regex by appending pipes
        for(String keyword : keywords){
            regex_creator.append(keyword).append('|');
        }

        // Delete the last pipe
        regex_creator.deleteCharAt(regex_creator.length()-1);

        return regex_creator.toString();
    }

    // Tokenize method
    public HashMap<String, HashSet<String>> tokenize(){
        String keyword_regex = this.createKeywordRegex(); // Create keyword regex

        // Define other regex
        String id_regex = "[a-zA-Z_][a-zA-Z0-9_]*";
        String op_regex = ">>=|<<=|\\+=|-=|\\*=|/=|%=|==|!=|>=|<=|&&|\\|\\||\\+\\+|--|&=|\\|=|\\^=|=|>|<|!|\\+|-|\\*|/|%|&|\\||\\^";
        String punt_regex = "\\*|\\(|\\)|\\.|,|:|;|\\{|\\}|->";
        String const_regex = "-?[0-9]+(\\.[0-9]+)?";
        String lit_regex = "\".*\"";

        // Join all regex for Unknown token handling
        String known_regex = String.join("|", keyword_regex, id_regex, op_regex, punt_regex, const_regex, lit_regex);

        // Iterate all lexemes
        for(int i=0; i<this.lexemes.size(); i++){
            // Delete comments
            String lexeme = this.lexemes.get(i).toString();
            String cleared_lexeme = lexeme.replaceAll("//.*", "").trim();
            if(cleared_lexeme.isEmpty()){ // If it's empty, we set a line jump
                lexemes.set(i, new StringBuilder("\n"));
                continue;
            } else{
                lexemes.set(i, new StringBuilder(cleared_lexeme)); // Set our free comment lexeme
            }

            // Removing literals
            String no_lit_lexeme = cleared_lexeme.replaceAll("\".*\"", "").trim();

            // Check and classify tokens
            classifyAndCount(id_regex, no_lit_lexeme, "Identifiers");
            classifyAndCount(keyword_regex, no_lit_lexeme, "Keywords");
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

            // Count all Unknown tokens
            if (knownEnd < cleared_lexeme.length()){
                String unknownPart = cleared_lexeme.substring(knownEnd).trim();
                if (!unknownPart.isEmpty()) {
                    this.token_classification.get("Unknown").add(unknownPart);
                    this.total_tokens++;
                }
            }
        }

        // Return classification
        return this.token_classification;
    }

    // Classify and count method
    private void classifyAndCount(String regex, String text, String category){
        Pattern pattern = Pattern.compile(regex); // Pattern compilation
        Matcher matcher = pattern.matcher(text); // Regex matcher

        // Find matches with the regex
        while(matcher.find()){
            String token = matcher.group();

            // Avoid adding keywords to identifiers category
            if("Identifiers".equals(category)){
                if(!this.keywords.contains(token)){
                    this.token_classification.get(category).add(token);
                }
            }else{
                this.token_classification.get(category).add(token);
            }

            if(!this.keywords.contains(token) || "Identifiers".equals(category)){
                this.total_tokens++;
            }
        }
    }

    // Total tokens getter
    public int getTotal_tokens(){return this.total_tokens;}
}
