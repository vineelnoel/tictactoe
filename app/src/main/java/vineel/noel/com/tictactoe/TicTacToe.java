package vineel.noel.com.tictactoe;

import android.media.MediaPlayer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TicTacToe extends AppCompatActivity {
    ImageView counter;
    LinearLayout playAgainLayout;
    TextView tvWinnerMessage;
    GridLayout glTicTacToe;
    MediaPlayer mediaPlayer;
    int activePlayer = 0;
    boolean gameIsActive = true;
    int[] gameState = {2,2,2,2,2,2,2,2,2};
    int[][] winningPositions = {{0,1,2},{0,3,6},{0,4,8},{3,4,5},{6,7,8},{1,4,7},{2,5,8},{2,4,6}};
    public void dropIn(View view){
        counter = (ImageView) view;
        int tappedCounter = Integer.parseInt(counter.getTag().toString());
        if(gameState[tappedCounter] == 2 && gameIsActive){
            mediaPlayer.start();
            counter.setTranslationY(-1000f);
            gameState[tappedCounter] = activePlayer;
            if(activePlayer == 0){
                counter.setImageResource(R.drawable.o);
                activePlayer = 1;
            }else{
                counter.setImageResource(R.drawable.x);
                activePlayer = 0;
            }

            counter.animate().translationYBy(1000f).rotation(3600).setDuration(300);

            for(int[] winningPosition: winningPositions){
                if(gameState[winningPosition[0]]==gameState[winningPosition[1]] &&
                        gameState[winningPosition[1]]==gameState[winningPosition[2]] &&
                        gameState[winningPosition[0]]!=2){
                    gameIsActive = false;
                    String winner = "Mr.X";
                    if(gameState[winningPosition[0]]==0){
                        winner = "Mr.O";
                    }
                    MediaPlayer winnerMusic = MediaPlayer.create(this,R.raw.winner);
                    winnerMusic.start();
                    tvWinnerMessage.setText(winner+" has won!");
                    playAgainLayout.setVisibility(View.VISIBLE);
                }else{
                    boolean gameIsOver = true;

                    for(int counterState: gameState){
                        if(counterState == 2)
                            gameIsOver = false;
                    }

                    if(gameIsOver){
                        tvWinnerMessage.setText("It's a draw!");
                        playAgainLayout.setVisibility(View.VISIBLE);
                    }
                }

            }

        }

    }

    public void playAgain(View view){
        playAgainLayout.setVisibility(View.INVISIBLE);
        activePlayer = 0;
        gameIsActive = true;

        for(int i=0; i<gameState.length ;i++){
            gameState[i] = 2;
        }

        for(int i=0; i<glTicTacToe.getChildCount(); i++){
            ((ImageView)glTicTacToe.getChildAt(i)).setImageResource(0);
        }
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tic_tac_toe_layout);
        playAgainLayout = (LinearLayout) findViewById(R.id.playAgainLayout);
        tvWinnerMessage = (TextView) findViewById(R.id.tvWinnerMessage);
        glTicTacToe = (GridLayout) findViewById(R.id.glTicTacToe);
        mediaPlayer = MediaPlayer.create(this,R.raw.click);
    }
}