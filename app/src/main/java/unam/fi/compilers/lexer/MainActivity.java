package unam.fi.compilers.lexer;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

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
            public void onClick(View v) {
                String text = inputText.getText().toString();
               // List<Token> tokens = Lexer.tokenize(text);
                //outputText.setText(tokens.toString());
            }
        });
    }
}



