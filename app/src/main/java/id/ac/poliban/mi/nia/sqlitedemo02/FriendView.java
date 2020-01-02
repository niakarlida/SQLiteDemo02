package id.ac.poliban.mi.nia.sqlitedemo02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import id.ac.poliban.mi.nia.sqlitedemo02.dao.impl.FriendDaoImplSQLite;
import id.ac.poliban.mi.nia.sqlitedemo02.domain.Friend;

import static id.ac.poliban.mi.nia.sqlitedemo02.MainActivity.EVENT_INSERT;
import static id.ac.poliban.mi.nia.sqlitedemo02.MainActivity.EVENT_UPDATE;
import static id.ac.poliban.mi.nia.sqlitedemo02.MainActivity.EVENT_VIEW;

public class FriendView extends AppCompatActivity {
    private FriendDaoImplSQLite ds;
    private TextView tvId;
    private EditText edId;
    private EditText edName;
    private EditText edAddress;
    private EditText edPhone1;
    private EditText edPhone2;
    private EditText edPhone3;
    private EditText edEmail;
    private EditText edHobbies;
    private Button btSimpan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_view);
        ds = new FriendDaoImplSQLite(this);
//views to objects
        tvId = findViewById(R.id.tvId);
        edId = findViewById(R.id.edId);
        edName = findViewById(R.id.edName);
        edAddress = findViewById(R.id.edAddress);
        edPhone1 = findViewById(R.id.edPhone1);
        edPhone2 = findViewById(R.id.edPhone2);
        edPhone3 = findViewById(R.id.edPhone3);
        edEmail = findViewById(R.id.edEmail);
        edHobbies = findViewById(R.id.edHobbies);
        btSimpan = findViewById(R.id.btSimpan);
//event handler
        btSimpan.setOnClickListener(v -> {
            if (!isValidate()) return;
            if(getIntent().getIntExtra("event", -1)== EVENT_INSERT){
                Friend friend = new Friend(
                        edName.getText().toString(),
                        edAddress.getText().toString(),
                        edPhone1.getText().toString(),
                        edPhone2.getText().toString(),
                        edPhone3.getText().toString(),
                        edEmail.getText().toString(),
                        edHobbies.getText().toString());
                ds.insert(friend); //sisipkan object Friend
            }
            else if(getIntent().getIntExtra("event", -1)==EVENT_UPDATE) {
                Friend friend = new Friend(
                        Integer.parseInt(edId.getText().toString()),
                        edName.getText().toString(),
                        edAddress.getText().toString(),
                        edPhone1.getText().toString(),
                        edPhone2.getText().toString(),
                        edPhone3.getText().toString(),
                        edEmail.getText().toString(),
                        edHobbies.getText().toString());
                ds.update(friend); //update friend
            }
            onBackPressed();
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
//uji apakah event INSERT atau VIEW
        if (getIntent().getExtras() != null && getSupportActionBar() != null)
            if (getIntent().getIntExtra("event", -1) == EVENT_INSERT) {
                setTitle("Tambah Data Teman");
                settingTampilan(EVENT_INSERT);
            }
            else if (getIntent().getIntExtra("event", -1) == EVENT_UPDATE) {
                setTitle("Update Data Teman");
                settingTampilan(EVENT_UPDATE);
                tampilkanData(getIntent().getIntExtra("id", -1));
            }
            else if (getIntent().getIntExtra("event", -1) == EVENT_VIEW) {
                setTitle("Detail Teman");
                settingTampilan(EVENT_VIEW);
                tampilkanData(getIntent().getIntExtra("id", -1));
            }
    }
    private void settingTampilan(int event) {
        switch (event){
            case EVENT_INSERT :
                tvId.setVisibility(View.GONE); //lenyap
                edId.setVisibility(View.GONE);
                edName.setEnabled(true); //bisa diedit
                edAddress.setEnabled(true);
                edPhone1.setEnabled(true);
                edPhone2.setEnabled(true);
                edPhone3.setEnabled(true);
                edEmail.setEnabled(true);
                edHobbies.setEnabled(true);
                btSimpan.setVisibility(View.VISIBLE); //muncul
                break;
            case EVENT_UPDATE :
                tvId.setVisibility(View.VISIBLE); //muncul
                edId.setVisibility(View.VISIBLE);
                edId.setEnabled(false);
                edName.setEnabled(true); //bisa diedit
                edAddress.setEnabled(true);
                edPhone1.setEnabled(true);
                edPhone2.setEnabled(true);
                edPhone3.setEnabled(true);
                edEmail.setEnabled(true);
                edHobbies.setEnabled(true);
                btSimpan.setVisibility(View.VISIBLE); //muncul
                break;
            case EVENT_VIEW :
                tvId.setVisibility(View.VISIBLE); //muncul
                edId.setVisibility(View.VISIBLE);
                edId.setEnabled(false); //tidak bisa diedit
                edName.setEnabled(false);
                edAddress.setEnabled(false);
                edPhone1.setEnabled(false);
                edPhone2.setEnabled(false);
                edPhone3.setEnabled(false);
                edEmail.setEnabled(false);
                edHobbies.setEnabled(false);
                btSimpan.setVisibility(View.GONE); //lenyap
                break;
        }
    }
    private void tampilkanData(int id) {
        Friend friend = ds.getFriendById(id);
        edId.setText(String.valueOf(friend.getId()));
        edName.setText(friend.getName());
        edAddress.setText(friend.getAddress());
        edPhone1.setText(friend.getPhone1());
        edPhone2.setText(friend.getPhone2());
        edPhone3.setText(friend.getPhone3());
        edEmail.setText(friend.getEmail());
        edHobbies.setText(friend.getHobbies());
    }
    private boolean isValidate() {
        EditText[] ets = new EditText[]{
                edName, edEmail
        };
//jika etName, dan etEmail kosong maka munculkan error
        for (EditText et : ets) {
            if (et.getText().toString().isEmpty()) {
                et.setError("field harus diisi!");
                return false;
            }
        }
        return true;
    }
}
