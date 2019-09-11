package jp.ac.jjc.dotcomgame2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    final int numOfCells = 6;
    int numOfGuess;
    ArrayList<Integer> dotCom;
    Button[] buttons = new Button[numOfCells];
    Button bt_replay;
    Listener[] listeners = new Listener[6];
    ReplayListener repListener;
    TextView tv_showResult;
    GamePlay game = new GamePlay();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        game.setUpGame();
        game.startGame();
    }


    private class GamePlay {
        public void setUpGame() {
            bt_replay = findViewById(R.id.bt_replay);
            tv_showResult = findViewById(R.id.message);
            repListener = new ReplayListener();
            bt_replay.setOnClickListener(repListener);
            buttons[0] = findViewById(R.id.box1);
            buttons[1] = findViewById(R.id.box2);
            buttons[2] = findViewById(R.id.box3);
            buttons[3] = findViewById(R.id.box4);
            buttons[4] = findViewById(R.id.box5);
            buttons[5] = findViewById(R.id.box6);

            for(int i = 0; i < numOfCells; i++) {
                listeners[i] = new Listener(i);
            }
        }

        public void startGame() {
            numOfGuess = 0;
            dotCom = new ArrayList<Integer>();
            Random rand = new Random();
            int randNum = rand.nextInt(4);
            for(int i = 0; i < 3; i++) {
                dotCom.add(Integer.valueOf(randNum + i));
            }
            tv_showResult.setText(getString(R.string.message));
            for(int i = 0; i < numOfCells; i++) {
                buttons[i].setOnClickListener(listeners[i]);
                buttons[i].setText(getString(R.string.non_Clicked));
            }
            bt_replay.setVisibility(View.GONE);
        }

        public void endGame() {
            String result;
            if(numOfGuess == 3) {
                result = getString(R.string.result_excelent);
            } else if (numOfGuess <= 5) {
                result = getString(R.string.result_good);
            } else {
                result = getString(R.string.result_bad);
            }
            tv_showResult.setText(getString(R.string.result_Front) + " " + numOfGuess +
                    " " + getString(R.string.result_End) + result);
            for(int i = 0; i < numOfCells; i++) {
                buttons[i].setOnClickListener(null);
            }
            bt_replay.setVisibility(View.VISIBLE);
        }
    }

    private class Listener implements View.OnClickListener {
        private int position;
        public Listener(int pos) {
            position = pos;
        }
        public void onClick(View view) {
            numOfGuess++;
            boolean hit = false;
            for(int i = 0; i < dotCom.size(); i++) {
                if(position == dotCom.get(i)) {
                    buttons[position].setText(getString(R.string.hit));
                    dotCom.remove(i);
                    hit = true;
                    if(dotCom.isEmpty()) {
                        game.endGame();
                    }
                }
            }
            if(hit == false) {
                buttons[position].setText(getString(R.string.miss));
            }
            buttons[position].setOnClickListener(null);
        }
    }

    private class ReplayListener implements View.OnClickListener {
        public void onClick(View view) {
            game.startGame();
        }
    }
}
