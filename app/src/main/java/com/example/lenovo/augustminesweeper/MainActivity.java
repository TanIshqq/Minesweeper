package com.example.lenovo.augustminesweeper;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {
    int score=0;
    int flags=0;
    public static int size = 10;
    public static int totalNumberOfMines = (size*size)/5;
    TextView tv;
    TextView fl;
    LinearLayout rootLayout;
    LinearLayout[] rows = new LinearLayout[size];
    CustomButton[][] board = new CustomButton[size][size];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = getIntent();
        Bundle b = i.getExtras();
        String name = i.getStringExtra(Start_Activity.KEY_USERNAME);
        int level = i.getIntExtra("Level",-1);
        Toast.makeText(this,"Welcome" + name,Toast.LENGTH_SHORT).show();
        rootLayout = (LinearLayout)findViewById(R.id.rootLayout);
        tv = (TextView)findViewById(R.id.scorecard);
        fl = (TextView)findViewById(R.id.flagstatus);
        if(level==5){
            size=5;
            totalNumberOfMines=5;
            setUpBoard();
        }
        else if(level==10){
            size=10;
            totalNumberOfMines=20;
            setUpBoard();
        }
        else if(level==12){
            size=10;
            totalNumberOfMines=30;
            setUpBoard();
        }
        else{
            setUpBoard();
        }

    }

    public  void setUpBoard(){
        rows = new LinearLayout[size];
        board = new CustomButton[size][size];
        score=0;
        flags=0;
        rootLayout.removeAllViews();
        for(int i = 0;i<size;i++){

            LinearLayout linearLayout = new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,1);
            params.setMargins(1,1,1,1);
            linearLayout.setLayoutParams(params);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            rows[i] = linearLayout;
            rootLayout.addView(linearLayout);

        }


        for(int i = 0;i<size;i++){
            for(int j = 0;j<size;j++){

                board[i][j] = new CustomButton(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1);
                params.setMargins(1,1,1,1);
                board[i][j].setLayoutParams(params);
                board[i][j].setOnClickListener(this);
                board[i][j].setOnLongClickListener(this);
                rows[i].addView(board[i][j]);


            }
        }
        setMines();
    }


    @Override
    public void onClick(View v) {
        CustomButton current = (CustomButton) v;
                int x=0,y=0;
                if (current.noOfBombs != -1 && current.noOfBombs != 0 && current.visited==false) {
                    current.setText(current.noOfBombs + "");
                    score = score + current.noOfBombs;
                    tv.setText(score + "");
                    if(current.noOfBombs>=3){
                        current.setTextColor(ContextCompat.getColor(this,R.color.red));
                    }
                    else{
                        current.setTextColor(ContextCompat.getColor(this,R.color.blue));
                    }
                    current.visited=true;
                }
                else if (current.noOfBombs == 0 && current.visited==false) {
                    current.setText("");current.visited=true;
                    current.setBackgroundColor(ContextCompat.getColor(this,R.color.lg));
                    for(int i =0;i<size;i++){
                        for(int j=0;j<size;j++){
                            if(board[i][j]==current){
                                x=i;
                                y=j;
                            }
                        }
                    }
                    noBombs(x , y);
                }
                else if (current.noOfBombs == -1 ) {
                    current.setBackgroundResource(R.drawable.minesweeper);
                    Toast.makeText(this, "Game Over", Toast.LENGTH_LONG).show();
                    for (int l = 0; l < size; l++) {
                        for (int m = 0; m < size; m++) {
                            if(board[l][m].noOfBombs==-1){
                                board[l][m].setBackgroundResource(R.drawable.minesweeper);
                            }
                            board[l][m].setEnabled(false);
                        }
                    }
                }
                win_status();

            }

    private void win_status() {
        int count =0;
        for (int l = 0; l < size; l++) {
            for (int m = 0; m < size; m++) {
                if(board[l][m].noOfBombs==-1 && board[l][m].flagged==true){
                    count++;
                }
            }
        }
        if((size==5 && count==5)){
            Toast.makeText(this,"Winner",Toast.LENGTH_LONG).show();
            for (int l = 0; l < size; l++) {
                for (int m = 0; m < size; m++) {
                    board[l][m].setEnabled(false);
                }
            }
        }
        else if(size ==10 && count ==20){
            Toast.makeText(this,"Winner",Toast.LENGTH_LONG).show();
            for (int l = 0; l < size; l++) {
                for (int m = 0; m < size; m++) {
                    board[l][m].setEnabled(false);
                }
            }
        }
        else if(size==10 && count==30){
            Toast.makeText(this,"Winner",Toast.LENGTH_LONG).show();
            for (int l = 0; l < size; l++) {
                for (int m = 0; m < size; m++) {
                    board[l][m].setEnabled(false);
                }
            }
        }
    }


    private void setMines() {

        int[] dx = {-1, -1, -1, 0, 0, +1, +1, +1};
        int[] dy = {-1, 0, +1, -1, +1, -1, 0, +1};
        Random random = new Random();
        for (int i = 0; i < totalNumberOfMines; i++) {
            int r = random.nextInt(size);
            int c = random.nextInt(size);
            if (board[r][c].noOfBombs!=-1) {
                board[r][c].noOfBombs = -1;
                for (int j = 0; j < 8; j++) {
                    int nx = r + dx[j];
                    int ny = c + dy[j];
                    if (nx < 0 || nx > size - 1 || ny < 0 || ny > size - 1 || board[nx][ny].noOfBombs==-1) {
                        continue;
                    } else {
                        board[nx][ny].noOfBombs++;
                    }
                }
            }else i--;
        }
    }

    private void noBombs(int x ,  int y){
        int[] dx = {-1, -1, -1, 0, 0, +1, +1, +1};
        int[] dy = {-1, 0, +1, -1, +1, -1, 0, +1};
        for(int i=0;i<8;i++){
            int nx = x + dx[i];
            int ny = y + dy[i];
            if(nx<0 || nx>size-1 || ny<0 || ny>size-1){
                continue;
            }
            if(board[nx][ny].visited==true){
                continue;
            }
            if(board[nx][ny].noOfBombs==-1){
                return;
            }
            if(board[nx][ny].noOfBombs!=0){
                board[nx][ny].setText(board[nx][ny].noOfBombs + "");
                score = score + board[nx][ny].noOfBombs;
                tv.setText(score + "");
                if(board[nx][ny].noOfBombs>=3){
                    board[nx][ny].setTextColor(ContextCompat.getColor(this,R.color.red));
                }
                else{
                    board[nx][ny].setTextColor(ContextCompat.getColor(this,R.color.blue));
                }
                board[nx][ny].visited=true;
            }
            else{
                board[nx][ny].visited=true;
                board[nx][ny].setBackgroundColor(ContextCompat.getColor(this,R.color.lg));
                board[nx][ny].setText("");
                noBombs(nx,ny);
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu main_menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, main_menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.reset){
            tv.setText("");
            fl.setText("");
            setUpBoard();
        }
        else if(id == R.id.easy){
            size = 5;
            totalNumberOfMines=5;
            tv.setText("");
            fl.setText("");
            setUpBoard();
        }
        else if(id == R.id.medium){
            size = 10;
            totalNumberOfMines=20;
            tv.setText("");
            fl.setText("");
            setUpBoard();
        }
        else if(id == R.id.hard){
            size = 10;
            totalNumberOfMines=30;
            tv.setText("");
            fl.setText("");
            setUpBoard();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onLongClick(View v) {
        CustomButton curr = (CustomButton)v;
        if(curr.visited==false){
            if(curr.flagged==false){
                curr.setBackgroundResource(R.drawable.flag);
                curr.flagged=true;
                flags++;
                fl.setText(flags + "");

            }
            else{
                curr.setBackgroundResource(R.drawable.bg_buttons);
                curr.flagged=false;
                flags--;
                fl.setText(flags + "");
            }
        }
        return true;
    }
}
