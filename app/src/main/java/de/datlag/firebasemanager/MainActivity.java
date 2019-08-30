package de.datlag.firebasemanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import de.datlag.firebasemanager.core.FirebaseManager;

public class MainActivity extends AppCompatActivity {

    private Activity activity;
    private Toolbar toolbar;
    private RecyclerView exampleRecycler;
    private FirebaseExampleRecyclerViewAdapter adapter;
    private ArrayList<String> name = new ArrayList<>();
    private ArrayList<Drawable> image = new ArrayList<>();
    private ArrayList<Fragment> fragment = new ArrayList<>();

    private AuthFragment authFragment;

    private FirebaseManager firebaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this;
        initialize();
        initializeLogic();
        addListItems();
    }

    private void initialize() {
        toolbar = findViewById(R.id.toolbar);
        exampleRecycler = findViewById(R.id.example_recycler);
    }

    private void initializeLogic() {
        setSupportActionBar(toolbar);
        firebaseManager = new FirebaseManager(activity);
        authFragment = new AuthFragment(firebaseManager);

        exampleRecycler.setLayoutManager(new GridLayoutManager(activity, 1));
        adapter = new FirebaseExampleRecyclerViewAdapter(activity, name, image);
        adapter.setClickListener(new FirebaseExampleRecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch2Fragment(fragment.get(position));
            }
        });
        exampleRecycler.setAdapter(adapter);
    }

    private void addListItems() {
        fragment.add(authFragment);
        name.add("Auth");
        image.add(ContextCompat.getDrawable(activity, R.drawable.firebase_authentication));
    }

    public void switch2Fragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.fragment_view, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseManager.onStart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
