package com.quizapp.ip2.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.quizapp.ip2.Helper.LevelParser;
import com.quizapp.ip2.Helper.PostTask;
import com.quizapp.ip2.Helper.RequestTask;
import com.quizapp.ip2.Model.User;
import com.quizapp.ip2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Allan on 07/04/2018.
 */

public class AdminTaskFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.admin_task_fragment, container, false);

        TextView txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        TextView txtDesc = (TextView) view.findViewById(R.id.txtDesc);
        ImageView imgTask = (ImageView) view.findViewById(R.id.imgTask);

        ImageView backgroundShape = (ImageView) view.findViewById(R.id.imgBackground);

        DrawableCompat.setTint(backgroundShape.getDrawable(), ContextCompat.getColor(getContext(), R.color.colorLightGray));
        final String title = this.getArguments().getString("title");
        txtTitle.setText(title);
        txtDesc.setText(this.getArguments().getString("desc"));

        imgTask.setImageDrawable(getContext().getDrawable(this.getArguments().getInt("img")));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(title.equals("Add Quiz")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Quiz Name");
                    final EditText text = new EditText(getActivity());
                    text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    builder.setMessage("Enter the quiz title...");
                    builder.setView(text);
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which){
                            dialog.dismiss();
                        }
                    });

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();


                            Bundle b = new Bundle();
                            b.putString("title", text.getText().toString());

                            Intent intent = new Intent(getContext(), AddQuizActivity.class);
                            intent.putExtras(b);

                            startActivity(intent);
                            getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        }
                    });

                    Intent intent = new Intent(getContext(), AddQuizActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                }else if(title.equals("Delete Quiz")){
                    Intent intent = new Intent(getContext(), DeleteQuizActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                }else if(title.equals("Edit Quiz")){
                    Intent intent = new Intent(getContext(), EditQuizActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                }else if(title.equals("Lookup/Edit User")){
                    //todo alert dialog, ask for username, use find api to get user id and all details, pass in as bundle.

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Lookup User");
                    final EditText text = new EditText(getActivity());
                    text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    builder.setMessage("Enter the user's email...");
                    builder.setView(text);
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which){
                            dialog.dismiss();
                        }
                    });

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                            Intent intent = new Intent(getContext(), LookupUserActivity.class);
                            Bundle b = new Bundle();

                            PostTask pt = new PostTask();
                            JSONObject jsonObject = new JSONObject();

                            try {
                                jsonObject.put("email", text.getText().toString());

                                String[] response = pt.sendPostRequest("user/find", jsonObject.toString());

                                if(response[0].equals("200")){
                                    //user found
                                    JSONArray jsonResponseArray = new JSONArray(response[1]);
                                    JSONObject jsonResponse = jsonResponseArray.getJSONObject(0);

                                    b.putInt("userid", jsonResponse.getInt("UserID"));
                                    b.putString("username", jsonResponse.getString("Username"));
                                    b.putString("email", jsonResponse.getString("Email"));
                                    b.putString("firstname", jsonResponse.getString("Firstname"));
                                    b.putString("surname", jsonResponse.getString("Surname"));
                                    b.putString("profilepic", jsonResponse.getString("ProfileImage"));

                                    boolean adminBool = (jsonResponse.getInt("AdminStatus") == 1);
                                    b.putBoolean("adminstatus", adminBool);
                                    b.putInt("xp", jsonResponse.getInt("XP"));
                                    b.putInt("level", new LevelParser(jsonResponse.getInt("XP")).getLevel());
                                    b.putInt("quizzescomplete", jsonResponse.getInt("QuizzessCompleted"));
                                    b.putInt("correctanswers", jsonResponse.getInt("CorrectAnswers"));

                                    //Put ranking
                                    RequestTask requestRanking = new RequestTask();
                                    String rankingResponse = requestRanking.sendGetRequest("user/" + jsonResponse.getInt("UserID") + "/ranking");
                                    if(!rankingResponse.equals("[]")){
                                        JSONObject jsonRanking = new JSONObject(rankingResponse);
                                        b.putInt("ranking", jsonRanking.getInt("position"));
                                    } else{
                                        b.putInt("ranking", 0);
                                    }

                                    RequestTask requestBanned = new RequestTask();
                                    String banResponse = requestBanned.sendGetRequest("blacklist/"+jsonResponse.getInt("UserID"));
                                    boolean banBool;
                                    if(!banResponse.equals("[]")){
                                        banBool = true;
                                    }else{
                                        banBool = false;
                                    }
                                    b.putBoolean("banned", banBool);

                                    intent.putExtras(b);
                                    startActivity(intent);
                                    getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                                }else if(response[0].equals("404")){
                                    Toast.makeText(getContext(), "User not found...", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }else{
                                    Toast.makeText(getContext(), "Error parsing JSON...", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            } catch (JSONException e){
                                Log.e("JSON ERROR", "Bad JSON");
                                e.printStackTrace();
                            }


                        }
                    });

                    AlertDialog ad = builder.create();
                    ad.show();

                }else{
                    Intent intent = new Intent(getContext(), AddAdminActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                }

            }
        });

        return view;
    }
}
