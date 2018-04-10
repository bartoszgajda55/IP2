package com.quizapp.ip2.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.quizapp.ip2.Helper.LevelParser;
import com.quizapp.ip2.Helper.PostTask;
import com.quizapp.ip2.Helper.StringHasher;
import com.quizapp.ip2.Helper.UserHelper;
import com.quizapp.ip2.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Aaron on 10/03/2018.
 */

public class UserFragment extends Fragment {


    View view;

    ProgressBar progressBar;
    LinearLayout friendsLayout;
    boolean friendsLoaded = false;

    Button btnAdmin;

    TextView txtUsername;
    ImageView imgUser;
    TextView txtEmail;
    TextView txtLevel;
    TextView txtFirstname;
    TextView txtSurname;
    TextView txtQuizzesComplete;
    TextView txtCorrectAnswers;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_fragment, container, false);
        friendsLayout = (LinearLayout) view.findViewById(R.id.friendLinearLayout);
        Button btnSettings = (Button) view.findViewById(R.id.btnSettings);
        btnAdmin = (Button) view.findViewById(R.id.btnAdmin);
        Button btnEditDetails = (Button) view.findViewById(R.id.btnEditDetails);

        txtUsername = (TextView) view.findViewById(R.id.txtUsername);
        imgUser = (ImageView) view.findViewById(R.id.imgUserIcon);
        txtEmail =(TextView) view.findViewById(R.id.txtEmail);
        txtLevel =(TextView) view.findViewById(R.id.txtLevel);
        txtFirstname =(TextView) view.findViewById(R.id.txtFirstname);
        txtSurname =(TextView) view.findViewById(R.id.txtSurname);

        txtQuizzesComplete =(TextView) view.findViewById(R.id.txtQuizzesComplete);
        txtCorrectAnswers=(TextView) view.findViewById(R.id.txtCorrectAnswers);

        loadDetails();

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });

        btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AdminActivity.class);
                startActivity(intent);
            }
        });

        btnEditDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDetails();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadDetails();
    }

    public void loadDetails(){
        //SET VALUES
        txtUsername.setText(UserHelper.getUser().getUsername());
        if(UserHelper.getUser().getEmail().length() < 14) {
            txtEmail.setText(UserHelper.getUser().getEmail());
        } else{
            txtEmail.setText(UserHelper.getUser().getEmail().substring(0, 14) + "...");
        }

        //Calculate level and then set
        LevelParser lvl = new LevelParser(UserHelper.getUser().getXp());
        int level = lvl.getLevel();
        txtLevel.setText(Integer.toString(level));

        if(UserHelper.getUser().getFirstName().length() < 14) {
            txtFirstname.setText("First name: " + UserHelper.getUser().getFirstName());
        } else{
            txtFirstname.setText("First name: " + UserHelper.getUser().getFirstName().substring(0, 14) + "...");
        }

        if(UserHelper.getUser().getSurname().length() < 14) {
            txtSurname.setText("Surname: " + UserHelper.getUser().getSurname());
        } else{
            txtSurname.setText("Surname: " + UserHelper.getUser().getSurname().substring(0, 14) + "...");
        }

        txtCorrectAnswers.setText("Correct Answers: "+Integer.toString(UserHelper.getUser().getCorrectAnswers()));
        txtQuizzesComplete.setText("Quizzess Completed: "+Integer.toString(UserHelper.getUser().getQuizzessCompleted()));

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        ProgressBar progressLevel = (ProgressBar) view.findViewById(R.id.progressLevel);
        progressLevel.setMax(100);
        progressLevel.setProgress(lvl.nextLevel());

        //IF USER IS NOT ADMIN MAKE btnAdmin invisible
        if (UserHelper.getUser().getAdminStatus()==0){
            btnAdmin.setVisibility(View.INVISIBLE);
        }
    }

    public void updateDetails(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Edit Details");
        builder.setMessage("Update your account details. Only provide details which you would like to update. E.g. only enter Email if you wish to update your email.\n");

        final EditText txtEditEmail = new EditText(getActivity());
        final EditText txtEditFirstName = new EditText(getActivity());
        final EditText txtEditSurname = new EditText(getActivity());
        final EditText txtEditPassword = new EditText(getActivity());
        final EditText txtEditConfirmPassword = new EditText(getActivity());

        txtEditEmail.setHint("Email...");
        txtEditFirstName.setHint("First name...");
        txtEditSurname.setHint("Surname...");
        txtEditPassword.setHint("Password...");
        txtEditConfirmPassword.setHint("Confirm password...");

        txtEditEmail.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        txtEditFirstName.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        txtEditSurname.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        txtEditPassword.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        txtEditConfirmPassword.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);

        txtEditPassword.setInputType(InputType.TYPE_CLASS_TEXT  | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        txtEditConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        int paddingDp = 20;
        int paddingPx = (int)(paddingDp * getResources().getDisplayMetrics().density);
        linearLayout.setPadding(paddingPx, 0, paddingPx, 0);

        linearLayout.addView(txtEditEmail);
        linearLayout.addView(txtEditFirstName);
        linearLayout.addView(txtEditSurname);
        linearLayout.addView(txtEditPassword);
        linearLayout.addView(txtEditConfirmPassword);

        builder.setView(linearLayout);

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setPositiveButton("APPLY", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                //TODO add field validation like register
                String email = txtEditEmail.getText().toString();
                String firstname = txtEditFirstName.getText().toString();
                String surname = txtEditSurname.getText().toString();
                String password = txtEditPassword.getText().toString();
                String confirmPassword = txtEditConfirmPassword.getText().toString();

                JSONObject jsonEdittedDetails = new JSONObject();

                //TODO Email pattern check
                try {
                    if (email.length() > 0) {
                        jsonEdittedDetails.put("email", email);
                    }

                    if(firstname.length() > 0) {
                        if(firstname.length() > 2 && firstname.length() < 256){ //TODO ENSURE ONLY LETTERS
                            jsonEdittedDetails.put("firstname", firstname);
                            UserHelper.getUser().setFirstName(firstname);
                        } else {
                            Toast.makeText(getContext(), "Invalid first name...", Toast.LENGTH_SHORT).show();
                        }
                    }

                    if(surname.length() > 0) {
                        if(surname.length() > 2 && surname.length() < 256) { //TODO ENSURE ONLY LETTERS
                            jsonEdittedDetails.put("surname", surname);
                            UserHelper.getUser().setSurname(surname);
                        }else {
                            Toast.makeText(getContext(), "Invalid surname...", Toast.LENGTH_SHORT).show();
                        }
                    }

                    if(password.length() > 0) {
                        if(password.equals(confirmPassword)){
                            if(password.matches(".*\\d+.*")){
                                jsonEdittedDetails.put("password", new StringHasher().hashString(password));
                                UserHelper.getUser().setPassword(new StringHasher().hashString(password));
                            }else{
                                Toast.makeText(getActivity(), "Passwords must have more than 3 characters and at least 1 number...", Toast.LENGTH_SHORT).show();
                            }
                        } else{
                            Toast.makeText(getContext(), "Passwords do not match...", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (JSONException e){
                    Log.e("JSON ERROR", "Invalid JSON");
                }


                String[] response = new PostTask().sendPostRequest("user/" + UserHelper.getUser().getUserID() + "/edit", jsonEdittedDetails.toString());
                dialog.dismiss();
                if(jsonEdittedDetails.length() > 0){
                    Toast.makeText(getContext(), "User details updated...", Toast.LENGTH_SHORT).show();
                }

            }
        });

        AlertDialog ad = builder.create();
        ad.show();


    }

    public void populateFriends(){
        if(!friendsLoaded) {
            //TODO Add an async task for loading friends
            for (int x = 0; x < 12; x++) {
                UserPreviewFragment frag = new UserPreviewFragment();
                Bundle bundle = new Bundle();
                int place = x + 1;
                String username = "User " + 1; //TODO get username from user query result
                String level = "10"; //TODO calculate level from user query result (XP)
                bundle.putInt("place", place);
                bundle.putString("username", username);
                bundle.putString("level", level);
                bundle.putInt("color", R.color.colorLightGray);
                bundle.putInt("textColor", R.color.colorDarkGray);
                bundle.putFloat("alpha", 0.25F);

                frag.setArguments(bundle);
                RelativeLayout rel = new RelativeLayout(getContext());
                rel.setId(View.generateViewId());
                getFragmentManager().beginTransaction().add(rel.getId(), frag).commit();
                friendsLayout.addView(rel);
            }

            progressBar.setVisibility(View.INVISIBLE);
            friendsLoaded = true;

        }
    }
}
