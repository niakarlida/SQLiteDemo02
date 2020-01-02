package id.ac.poliban.mi.nia.sqlitedemo02;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import id.ac.poliban.mi.nia.sqlitedemo02.dao.FriendDao;
import id.ac.poliban.mi.nia.sqlitedemo02.dao.impl.FriendDaoImplSQLite;
import id.ac.poliban.mi.nia.sqlitedemo02.domain.Friend;

public class MainActivity extends AppCompatActivity {
    public static final String DB_NAME = "dts.db";
    public static final int DB_VERSION = 1;
    public static final int EVENT_INSERT = 0;
    public static final int EVENT_UPDATE = 1;
    public static final int EVENT_VIEW = 2;
    private List<Friend> data = new ArrayList<>();
    private FriendDaoImplSQLite ds;
    private ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Daftar Teman Dekat");
        ds = new FriendDaoImplSQLite(this);
        ListView listView = findViewById(R.id.list_view);
        adapter = new ArrayAdapter(this, R.layout.item_list, R.id.tvItem, data);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            tampilkanMenu(parent, view, position, id);
            return true;
        });
    }
    private void tampilkanMenu(AdapterView<?> parent, View view, int position, long id) {
        final String[] items = {"Update", "Delete", "Show Detail", "None"};
        new AlertDialog.Builder(this)
                .setTitle("Action..")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setItems(items, (dialog, which) -> {
                    if (items[which].equals("Update")) {
                        Friend f = (Friend) parent.getAdapter().getItem(position);
                        Intent intent = new Intent(this, FriendView.class);
                        intent.putExtra("event", EVENT_UPDATE);
                        intent.putExtra("id", f.getId());
                        startActivity(intent);
                    } else if (items[which].equals("Delete")) {
//ambil data dari list
                        Friend friend = (Friend) parent.getAdapter().getItem(position);
                        ds.delete(friend.getId());
                        data.remove(friend);
                        adapter.notifyDataSetChanged();
                    }
                    else if (items[which].equals("Show Detail")) {
//ambil data dari list
                        Friend f = (Friend) parent.getAdapter().getItem(position);
                        Intent intent = new Intent(this, FriendView.class);
//kirim pesan via intent, bahwa user ingin melihat detail data
                        intent.putExtra("event", EVENT_VIEW);
                        intent.putExtra("id", f.getId());
                        startActivity(intent);
                    }
                })
                .show();
    }
    @Override
    public void onBackPressed() {
//konfirmasi ketika menutup aplikasi
        new AlertDialog.Builder(this)
                .setTitle("close confirmation")
                .setMessage("close this app?")
                .setIcon(android.R.drawable.ic_lock_power_off)
                .setPositiveButton("YES", (dialog, which) -> finish())
                .setNegativeButton("NO", null).show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.miAddAccount) {
            Intent intent = new Intent(this, FriendView.class);
//kirim pesan via intent bahwa user ingin insert data
            intent.putExtra("event", EVENT_INSERT);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onStart() {
        super.onStart();
        data.clear();
        data.addAll(ds.getAllFriends());
        adapter.notifyDataSetChanged();
    }
}
