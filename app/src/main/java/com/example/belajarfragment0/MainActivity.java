package com.example.belajarfragment0;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity implements SimpleFragment.OnFragmentInteractionListener {

    private Button button;
    private int radioButtonChoice = 2;

    private boolean isFragmentDisplayed = false;
    
    static final String STATE_FRAGMENT = "state_of_fragment";
    static final String STATE_CHOICE = "user_choice";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        
        button = findViewById(R.id.open_button);

        
        
        if (savedInstanceState != null) {
            isFragmentDisplayed = savedInstanceState.getBoolean(STATE_FRAGMENT);
            radioButtonChoice = savedInstanceState.getInt(STATE_CHOICE);
            if (isFragmentDisplayed) {
                
                button.setText(R.string.close);
            }
        }

        
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFragmentDisplayed) {
                    displayFragment();
                } else {
                    closeFragment();
                }
            }
        });
    }


    public void displayFragment() {
        
        SimpleFragment simpleFragment =
                SimpleFragment.newInstance(radioButtonChoice);
        
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();

        fragmentTransaction.add(R.id.fragment_container,
                simpleFragment).addToBackStack(null).commit();

        button.setText(R.string.close);
        isFragmentDisplayed = true;
    }

    public void closeFragment() {
        
        FragmentManager fragmentManager = getSupportFragmentManager();
        
        SimpleFragment simpleFragment = (SimpleFragment) fragmentManager
                .findFragmentById(R.id.fragment_container);
        if (simpleFragment != null) {
            
            FragmentTransaction fragmentTransaction =
                    fragmentManager.beginTransaction();
            fragmentTransaction.remove(simpleFragment).commit();
        }
        
        button.setText(R.string.open);
        isFragmentDisplayed = false;
    }


    @Override
    public void onRadioButtonChoice(int choice) {
        radioButtonChoice = choice;
        Toast.makeText(this, "Choice is " + Integer.toString(choice),
                LENGTH_SHORT).show();
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(STATE_FRAGMENT, isFragmentDisplayed);
        savedInstanceState.putInt(STATE_CHOICE, radioButtonChoice);
        super.onSaveInstanceState(savedInstanceState);
    }
}
