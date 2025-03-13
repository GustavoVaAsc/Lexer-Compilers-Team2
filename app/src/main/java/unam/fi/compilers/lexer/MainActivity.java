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

    private EditText inputText;
    private TextView outputText;
    private Button tokenizeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputText = findViewById(R.id.inputText);
        outputText = findViewById(R.id.outputText);
        tokenizeButton = findViewById(R.id.tokenizeButton);

        tokenizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String text = inputText.getText().toString();
                ArrayList<StringBuilder> lexemes = new ArrayList<>();
                for(String line : text.split("\\n")){
                    lexemes.add(new StringBuilder(line));
                }
                try {
                    Lexer lexer = new Lexer(lexemes,MainActivity.this);
                    HashMap<String, HashSet<String>> tokens = lexer.tokenize();
                    StringBuilder result = new StringBuilder();
                    for(String category : tokens.keySet()){
                        result.append(category).append(": ").append(tokens.get(category)).append("\n");
                    }
                    result.append("Total Tokens: ").append(lexer.getTotal_tokens());
                    outputText.setText(result.toString());
                }catch(Exception e){
                    outputText.setText("Error: " + e.getMessage());
                }
            }
        });
    }
}