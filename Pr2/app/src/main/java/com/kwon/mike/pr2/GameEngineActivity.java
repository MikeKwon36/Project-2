package com.kwon.mike.pr2;

import android.content.Intent;
import android.database.Cursor;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class GameEngineActivity extends AppCompatActivity {

    private TextView mTeamAScore,mTeamBScore,mQuarter,mStadium,mWeather,mGameFacilitator;
    private TextView mPlayerA_1, mPlayerA_1TD, mPlayerA_2, mPlayerA_2TD, mPlayerA_3, mPlayerA_3TD;
    private TextView mPlayerB_1, mPlayerB_1TD, mPlayerB_2, mPlayerB_2TD, mPlayerB_3, mPlayerB_3TD;
    private Random mGameEngine;
    private int mGamePhase, mCalcTeamAScoreSum, mCalcA1TD, mCalcA2TD, mCalcA3TD, mCalcTeamBScoreSum, mCalcB1TD, mCalcB2TD, mCalcB3TD;
    private int mCalcA1multiplier,mCalcA2multiplier,mCalcA3multiplier,mCalcB1multiplier,mCalcB2multiplier,mCalcB3multiplier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_engine);

        mTeamAScore = (TextView) findViewById(R.id.xmlScoreBoardTeamAScore);
        mTeamBScore = (TextView) findViewById(R.id.xmlScoreBoardTeamBScore);
        mQuarter = (TextView) findViewById(R.id.xmlScoreBoardQuarter);
        mStadium = (TextView) findViewById(R.id.xmlStadium);
        mWeather = (TextView) findViewById(R.id.xmlWeather);
        mGameFacilitator = (TextView) findViewById(R.id.xmlGameFacilitator);
        mPlayerA_1 = (TextView) findViewById(R.id.xmlPlayerA_1);
        mPlayerA_1TD = (TextView) findViewById(R.id.xmlPlayerA_1_TDs);
        mPlayerA_2 = (TextView) findViewById(R.id.xmlPlayerA_2);
        mPlayerA_2TD = (TextView) findViewById(R.id.xmlPlayerA_2_TDs);
        mPlayerA_3 = (TextView) findViewById(R.id.xmlPlayerA_3);
        mPlayerA_3TD = (TextView) findViewById(R.id.xmlPlayerA_3_TDs);
        mPlayerB_1 = (TextView) findViewById(R.id.xmlPlayerB_1);
        mPlayerB_1TD = (TextView) findViewById(R.id.xmlPlayerB_1_TDs);
        mPlayerB_2 = (TextView) findViewById(R.id.xmlPlayerB_2);
        mPlayerB_2TD = (TextView) findViewById(R.id.xmlPlayerB_2_TDs);
        mPlayerB_3 = (TextView) findViewById(R.id.xmlPlayerB_3);
        mPlayerB_3TD = (TextView) findViewById(R.id.xmlPlayerB_3_TDs);
        mGameEngine = new Random();

        //Set initial game parameters by generating Stadium and Weather conditions, setting the
        // player rosters, setting the mGameFacilitator TextView/Button text, initializing the
        // scoring parameters, and setting the gamePhase variable to 0
        mGamePhase=0;
        populateGameConditions();
        populateGameRosters();
        mQuarter.setText(String.valueOf(mGamePhase));
        mStadium.setText(StadiumWeatherVariables.getVariable(mGameEngine.nextInt(9)));
        mWeather.setText(StadiumWeatherVariables.getVariable(9 + mGameEngine.nextInt(3)));
        mGameFacilitator.setText(getResources().getString(R.string.gameEngineKickOff));
        initializeScoreTrackers();

        //mGameFacilitator click intiates the game sequence (based on mGamephase==0) and then returns the user to the
        // Main Activity upon completion (based on mGamePhase==4) when clicked
        mGameFacilitator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Game simulation is curated by a countdown timer that updates player statistics
                // and the team's score at timed intervals representing each game quarter.  Player
                // performance is simulated by applying their individual multipliers against a random probability of scoring.
                if (mGamePhase == 0) {
                    mGameFacilitator.setText(getResources().getString(R.string.gameEngineInGame));
                    new CountDownTimer(12000, 3000) {
                        public void onTick(long millisUntilFinished) {
                            mGamePhase += 1;
                            mQuarter.setText(String.valueOf(mGamePhase));
                            mCalcA1TD += (mGameEngine.nextInt(2)*mCalcA1multiplier);
                            mCalcA2TD += (mGameEngine.nextInt(2)*mCalcA2multiplier);
                            mCalcA3TD += (mGameEngine.nextInt(2)*mCalcA3multiplier);
                            mCalcB1TD += (mGameEngine.nextInt(2)*mCalcB1multiplier);
                            mCalcB2TD += (mGameEngine.nextInt(2)*mCalcB2multiplier);
                            mCalcB3TD += (mGameEngine.nextInt(2)*mCalcB3multiplier);
                            mCalcTeamAScoreSum = 7 * (mCalcA1TD + mCalcA2TD + mCalcA3TD);
                            mCalcTeamBScoreSum = 7 * (mCalcB1TD + mCalcB2TD + mCalcB3TD);
                            mPlayerA_1TD.setText(String.valueOf(mCalcA1TD));
                            mPlayerA_2TD.setText(String.valueOf(mCalcA2TD));
                            mPlayerA_3TD.setText(String.valueOf(mCalcA3TD));
                            mPlayerB_1TD.setText(String.valueOf(mCalcB1TD));
                            mPlayerB_2TD.setText(String.valueOf(mCalcB2TD));
                            mPlayerB_3TD.setText(String.valueOf(mCalcB3TD));
                            mTeamAScore.setText(String.valueOf(mCalcTeamAScoreSum));
                            mTeamBScore.setText(String.valueOf(mCalcTeamBScoreSum));
                        }

                        public void onFinish() {
                            mGamePhase += 1;
                            mCalcA1TD += (mGameEngine.nextInt(2)*mCalcA1multiplier);
                            mCalcA2TD += (mGameEngine.nextInt(2)*mCalcA2multiplier);
                            mCalcA3TD += (mGameEngine.nextInt(2)*mCalcA3multiplier);
                            mCalcB1TD += (mGameEngine.nextInt(2)*mCalcB1multiplier);
                            mCalcB2TD += (mGameEngine.nextInt(2)*mCalcB2multiplier);
                            mCalcB3TD += (mGameEngine.nextInt(2)*mCalcB3multiplier);
                            mCalcTeamAScoreSum = 7 * (mCalcA1TD + mCalcA2TD + mCalcA3TD);
                            mCalcTeamBScoreSum = 7 * (mCalcB1TD + mCalcB2TD + mCalcB3TD);
                            mPlayerA_1TD.setText(String.valueOf(mCalcA1TD));
                            mPlayerA_2TD.setText(String.valueOf(mCalcA2TD));
                            mPlayerA_3TD.setText(String.valueOf(mCalcA3TD));
                            mPlayerB_1TD.setText(String.valueOf(mCalcB1TD));
                            mPlayerB_2TD.setText(String.valueOf(mCalcB2TD));
                            mPlayerB_3TD.setText(String.valueOf(mCalcB3TD));
                            mTeamAScore.setText(String.valueOf(mCalcTeamAScoreSum));
                            mTeamBScore.setText(String.valueOf(mCalcTeamBScoreSum));
                            mQuarter.setText("F");
                            if (mCalcTeamAScoreSum > mCalcTeamBScoreSum) {
                                mGameFacilitator.setText(getResources().getString(R.string.gameEngineAwins));
                            } else if (mCalcTeamAScoreSum < mCalcTeamBScoreSum) {
                                mGameFacilitator.setText(getResources().getString(R.string.gameEngineBwins));
                            } else if (mCalcTeamAScoreSum == mCalcTeamBScoreSum) {
                                mGameFacilitator.setText(getResources().getString(R.string.gameEngineTie));
                            }
                        }
                    }.start();
                } else {
                    Intent intent = new Intent(GameEngineActivity.this, MainActivity.class);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    //Method to populate all possible random game conditions for Stadium and Weather
    private void populateGameConditions(){
        StadiumWeatherVariables.getmInstance().add(getResources().getString(R.string.stadiumCA));
        StadiumWeatherVariables.getmInstance().add(getResources().getString(R.string.stadiumCAR));
        StadiumWeatherVariables.getmInstance().add(getResources().getString(R.string.stadiumDEN));
        StadiumWeatherVariables.getmInstance().add(getResources().getString(R.string.stadiumFL));
        StadiumWeatherVariables.getmInstance().add(getResources().getString(R.string.stadiumMIN));
        StadiumWeatherVariables.getmInstance().add(getResources().getString(R.string.stadiumNE));
        StadiumWeatherVariables.getmInstance().add(getResources().getString(R.string.stadiumNYC));
        StadiumWeatherVariables.getmInstance().add(getResources().getString(R.string.stadiumPIT));
        StadiumWeatherVariables.getmInstance().add(getResources().getString(R.string.stadiumROC));
        StadiumWeatherVariables.getmInstance().add(getResources().getString(R.string.weatherBlizzard));
        StadiumWeatherVariables.getmInstance().add(getResources().getString(R.string.weatherRainy));
        StadiumWeatherVariables.getmInstance().add(getResources().getString(R.string.weatherSunny));
    }

    //Method to populate player names based off the completed fantasy rosters
    private void populateGameRosters(){
        mPlayerA_1.setText(FantasyFootballRosterA.getPlayerA(1).getmName().toString());
        mPlayerA_2.setText(FantasyFootballRosterA.getPlayerA(2).getmName().toString());
        mPlayerA_3.setText(FantasyFootballRosterA.getPlayerA(3).getmName().toString());
        mPlayerB_1.setText(FantasyFootballRosterB.getPlayerB(1).getmName().toString());
        mPlayerB_2.setText(FantasyFootballRosterB.getPlayerB(2).getmName().toString());
        mPlayerB_3.setText(FantasyFootballRosterB.getPlayerB(3).getmName().toString());
        mPlayerA_1TD.setText(String.valueOf(mCalcA1TD));
        mPlayerA_2TD.setText(String.valueOf(mCalcA2TD));
        mPlayerA_3TD.setText(String.valueOf(mCalcA3TD));
        mPlayerB_1TD.setText(String.valueOf(mCalcB1TD));
        mPlayerB_2TD.setText(String.valueOf(mCalcB2TD));
        mPlayerB_3TD.setText(String.valueOf(mCalcB3TD));
    }

    //Method to initialize all score tracking & calculation variables
    private void initializeScoreTrackers(){
        mCalcTeamAScoreSum=0;
        mCalcTeamBScoreSum=0;
        mCalcA1TD=0;
        mCalcA2TD=0;
        mCalcA3TD=0;
        mCalcB1TD=0;
        mCalcB2TD=0;
        mCalcB3TD=0;
        mCalcA1multiplier=calculateMultiplier(FantasyFootballRosterA.getFullRosterA(), 1);
        mCalcA2multiplier=calculateMultiplier(FantasyFootballRosterA.getFullRosterA(), 2);
        mCalcA3multiplier=calculateMultiplier(FantasyFootballRosterA.getFullRosterA(), 3);
        mCalcB1multiplier=calculateMultiplier(FantasyFootballRosterB.getFullRosterB(), 1);
        mCalcB2multiplier=calculateMultiplier(FantasyFootballRosterB.getFullRosterB(), 2);
        mCalcB3multiplier=calculateMultiplier(FantasyFootballRosterB.getFullRosterB(), 3);
    }

    //Method to build a player's scoring multiplier based on their stats and game conditions
    private int calculateMultiplier(ArrayList<Player> array, int rosterNum){
        String position = array.get(rosterNum).getmPosition();

        //Code to identify the roster numbers of the other drafted players
        int rosterNumOfTeammate1;
        int rosterNumOfTeammate2;
        if (rosterNum==1){
            rosterNumOfTeammate1=2;
            rosterNumOfTeammate2=3;
        } else if (rosterNum==2){
            rosterNumOfTeammate1=1;
            rosterNumOfTeammate2=3;
        } else {
            rosterNumOfTeammate1=1;
            rosterNumOfTeammate2=2;
        }

        //QB's add completion% to the multiplier, WR's&RB's add their catchRatios... all ratios are
        // reduced by the random weather variable
        int weather = 0;
        if(mWeather.getText().toString().equals(getResources().getString(R.string.weatherBlizzard))){weather+=15;}
        if(mWeather.getText().toString().equals(getResources().getString(R.string.weatherRainy))){weather+=10;}

        int multiplier;
        if(position.equals(getResources().getString(R.string.posQB))){
            multiplier = array.get(rosterNum).getPlayerStats().getCompletionPercentage(array.get(rosterNum)) - weather
                    + array.get(rosterNumOfTeammate1).getPlayerStats().getCatchRatio(array.get(rosterNumOfTeammate1)) - weather
                    + array.get(rosterNumOfTeammate2).getPlayerStats().getCatchRatio(array.get(rosterNumOfTeammate2)) - weather;
        } else {
            multiplier = array.get(rosterNum).getPlayerStats().getCatchRatio(array.get(rosterNum)) - weather;
            if (array.get(rosterNumOfTeammate1).getmPosition().equals(getResources().getString(R.string.posQB))){
                multiplier = multiplier + array.get(rosterNumOfTeammate1).getPlayerStats().getCompletionPercentage(array.get(rosterNumOfTeammate1))-weather;
            } else {
                multiplier = multiplier + array.get(rosterNumOfTeammate2).getPlayerStats().getCompletionPercentage(array.get(rosterNumOfTeammate2))-weather;
            }
        }

        //Strength/speed stat unaffected by weather conditions
        multiplier = multiplier + array.get(rosterNum).getPlayerStats().getStrength_Speed(array.get(rosterNum));

        //Multiplier changed into a TD index
        multiplier = multiplier/120;

        //Any player playing in his home field automatically has at least a 1 scoring multiplier
        if(array.get(rosterNum).getPlayerStats().getHomefieldAdvantage(array.get(rosterNum), mStadium.getText().toString())){
            if(multiplier==0){multiplier = 1;};
        }

        return multiplier;
    }
}

