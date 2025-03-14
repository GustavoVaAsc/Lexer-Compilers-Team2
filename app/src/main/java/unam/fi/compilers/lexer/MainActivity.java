/*
*
* Authors: Fernanda Ayala, Mauricio Fernandez, Yordi Jimenez, Elizabeth Portilla, Gustavo Valenzuela
* Semester: 2025-2
* Subject: Compilers
*
* Description: This is the main activity of our Android app implementing a lexer.
* The app takes an input string, tokenizes it using the Lexer class, and displays the results in a TextView.
* The tokenization process classifies the input into various categories and checks for unrecognized tokens.
*
* Responsibilities:
* - Takes input from the user.
* - Processes the input into lexemes.
* - Tokenizes the input and displays the results or errors.
* - Handles tokenization errors by reporting unrecognized tokens.
*
*/

package unam.fi.compilers.lexer;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


public class MainActivity extends AppCompatActivity{

    // UI components
    private EditText inputText;
    private TextView outputText;
    private Button tokenizeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Set the layout for this activity

        // Initialize UI components by referencing their IDs from the XML layout
        inputText = findViewById(R.id.inputText);
        outputText = findViewById(R.id.outputText);
        tokenizeButton = findViewById(R.id.tokenizeButton);

        // Set an OnClickListener for the "Tokenize" button
        tokenizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String text = inputText.getText().toString(); // Getting input text
                ArrayList<StringBuilder> lexemes = new ArrayList<>(); // Initialize lexemes list
                for(String line : text.split("\\n")){ // Split into lines
                    lexemes.add(new StringBuilder(line)); // Add each line to the list
                }
                try {
                    Lexer lexer = new Lexer(lexemes, MainActivity.this); // Initialize an instance of Lexer
                    HashMap<String, HashSet<String>> tokens = lexer.tokenize(); // Call tokenize and make it equal to a HashMap
                    StringBuilder result = new StringBuilder(); // Initialize result text

                    // Check if "Unknown" category is not empty
                    if(tokens.containsKey("Unknown") && !tokens.get("Unknown").isEmpty()){
                        outputText.setText("Error: There are unrecognized tokens in the input!"); // Set error message
                        return; // Stop processing further if "Unknown" category is not empty
                    }

                    // Process the other categories if "Unknown" is empty
                    for(String category : tokens.keySet()){
                        if(!"Unknown".equals(category)){ // Avoid printing Unknown category in the output
                            result.append(category).append(": ").append(tokens.get(category)).append("\n");
                        }
                    }

                    result.append("Total Tokens: ").append(lexer.getTotal_tokens()); // Get total tokens after tokenize
                    outputText.setText(result.toString()); // Set result text
                } catch (Exception e) {
                    outputText.setText("Error: " + e.getMessage()); // Exception handler.
                }
            }
        });
    }
}