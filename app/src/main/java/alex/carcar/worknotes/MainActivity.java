package alex.carcar.worknotes;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private final static String STORETEXT = "storetext.txt";
    private EditText txtEditor;

    public void saveClicked(View v) {
        try {
            OutputStreamWriter out = new OutputStreamWriter(openFileOutput(STORETEXT, 0));
            out.write(txtEditor.getText().toString());
            out.close();
            Toast.makeText(this, "The contents are saved in the file.", Toast.LENGTH_LONG).show();
        } catch (Throwable t) {
            Toast.makeText(this, "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
        }
        this.finish();
    }

    public void readFileInEditor() {
        try {
            InputStream in = openFileInput(STORETEXT);
            if (in != null) {
                InputStreamReader tmp = new InputStreamReader(in);
                BufferedReader reader = new BufferedReader(tmp);
                String str;

                StringBuilder buf = new StringBuilder();
                while ((str = reader.readLine()) != null) {
                    buf.append(str).append("\n");
                }
                in.close();
                txtEditor.setText(buf.toString());
                txtEditor.setSelection(txtEditor.getText().length());
            }
        } catch (java.io.FileNotFoundException e) {
            // that's OK, we probably haven't created it yet
        } catch (Throwable t) {
            Toast.makeText(this, "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtEditor = findViewById(R.id.textbox);
        readFileInEditor();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    public void pasteDate(MenuItem item) {
        DateFormat dateFormat = new SimpleDateFormat(getString(R.string.date_format), Locale.US);
        Date date = new Date();
        String insertText = dateFormat.format(date);
        txtEditor.getText().insert(txtEditor.getSelectionStart(), insertText);
    }

    public void pasteTime(MenuItem item) {
        DateFormat dateFormat = new SimpleDateFormat(getString(R.string.time_format), Locale.US);
        Date date = new Date();
        String insertText = dateFormat.format(date);
        txtEditor.getText().insert(txtEditor.getSelectionStart(), insertText);
    }

//    public void paste(MenuItem item) {
//        String insertText = item.getTitle().toString();
//        txtEditor.getText().insert(txtEditor.getSelectionStart(), insertText);
//    }

    public void paste_new_item(MenuItem item) {
        txtEditor.getText().insert(txtEditor.getSelectionStart(), getString(R.string.paste_new_item));
    }

    public void clearNotes(MenuItem item) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.clear_notes)
                .setMessage(R.string.are_you_sure)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        txtEditor.setText("");
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
