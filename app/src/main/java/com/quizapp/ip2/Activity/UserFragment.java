package com.quizapp.ip2.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
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

import com.quizapp.ip2.Helper.DownloadImageTask;
import com.quizapp.ip2.Helper.LevelParser;
import com.quizapp.ip2.Helper.PostTask;
import com.quizapp.ip2.Helper.RequestTask;
import com.quizapp.ip2.Helper.StringHasher;
import com.quizapp.ip2.Helper.UserHelper;
import com.quizapp.ip2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;

/**
 * Created by Aaron on 10/03/2018.
 */

public class UserFragment extends Fragment {


    View view;

    ProgressBar progressBar;
    LinearLayout friendsLayout;
    LinearLayout layoutControls;
    boolean friendsLoaded = false;

    ImageView profileImage;

    TextView txtUsername;
    ImageView imgUser;
    TextView txtEmail;
    TextView txtLevel;
    TextView txtFirstname;
    TextView txtSurname;
    TextView txtQuizzesComplete;
    TextView txtCorrectAnswers;
    EditText userSearch;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_fragment, container, false);
        friendsLayout = (LinearLayout) view.findViewById(R.id.friendLinearLayout);
        layoutControls = (LinearLayout) view.findViewById(R.id.layoutControls);
        final Button btnSettings = (Button) view.findViewById(R.id.btnSettings);
        //btnAdmin = (Button) view.findViewById(R.id.btnAdmin);
        Button btnEditDetails = (Button) view.findViewById(R.id.btnEditDetails);

        profileImage = (ImageView) view.findViewById(R.id.imgProfilePicture);
        profileImage.setBackground(getResources().getDrawable(R.drawable.rounded));
        profileImage.setClipToOutline(true);

        txtUsername = (TextView) view.findViewById(R.id.txtUsername);
        imgUser = (ImageView) view.findViewById(R.id.imgUserIcon);
        txtEmail =(TextView) view.findViewById(R.id.txtEmail);
        txtLevel =(TextView) view.findViewById(R.id.txtLevel);
        txtFirstname =(TextView) view.findViewById(R.id.txtFirstname);
        txtSurname =(TextView) view.findViewById(R.id.txtSurname);

        userSearch =(EditText) view.findViewById(R.id.userSearch);

        txtQuizzesComplete =(TextView) view.findViewById(R.id.txtQuizzesComplete);
        txtCorrectAnswers=(TextView) view.findViewById(R.id.txtCorrectAnswers);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        loadDetails();

        //Listen for search submit (click ENTER/RETURN)
        userSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:


                            populateFriendsSearch(userSearch.getText().toString());


                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Set Profile Picture");
                builder.setMessage("Enter the image URL of the profile picture you wish to use, or leave it blank to reset.\n");

                final EditText txtImageUrl = new EditText(getActivity());

                txtImageUrl.setHint("Image URL...");
                txtImageUrl.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);

                LinearLayout linearLayout = new LinearLayout(getActivity());
                linearLayout.setOrientation(LinearLayout.VERTICAL);

                int paddingDp = 20;
                int paddingPx = (int)(paddingDp * getResources().getDisplayMetrics().density);
                linearLayout.setPadding(paddingPx, 0, paddingPx, 0);

                linearLayout.addView(txtImageUrl);
                builder.setView(linearLayout);

                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.setPositiveButton("APPLY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String imageUrl = txtImageUrl.getText().toString();
                        JSONObject jsonNewImage = new JSONObject();
                        try {
                            jsonNewImage.put("ProfileImage", imageUrl);
                            if(imageUrl.equals("")){
                                jsonNewImage.remove("ProfileImage");
                                jsonNewImage.put("ProfileImage", "http://45.32.238.58/quizzy/img/res/profile_pic.png");
                            }
                            PostTask pt = new PostTask();
                            String[] imgResponse = pt.sendPostRequest("user/" + UserHelper.getUser().getUserID() + "/edit", jsonNewImage.toString(), "POST");
                            UserHelper.getUser().setProfilePicture(imageUrl);
                            UserHelper.uploadUser();
                            if(!imgResponse[0].equals("200")){
                                Toast.makeText(getActivity(), "Error...", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "Profile image updated...", Toast.LENGTH_SHORT).show();
                            }
                            dialog.dismiss();
                            
                        }catch (JSONException e){
                            Log.e("JSON ERROR", "Bad json");
                        }
                    }
                });

                AlertDialog ad = builder.create();
                ad.show();
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SettingsActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);
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

        new DownloadImageTask(profileImage).execute(UserHelper.getUser().getProfilePicture());


        txtCorrectAnswers.setText("Correct Answers: "+Integer.toString(UserHelper.getUser().getCorrectAnswers()));
        txtQuizzesComplete.setText("Quizzes Completed: "+Integer.toString(UserHelper.getUser().getQuizzessCompleted()));

        ProgressBar progressLevel = (ProgressBar) view.findViewById(R.id.progressLevel);
        progressLevel.setMax(100);
        progressLevel.setProgress(lvl.nextLevel());

        //IF USER IS NOT ADMIN MAKE btnAdmin invisible
        if (UserHelper.getUser().getAdminStatus()==1 && layoutControls.getChildCount() < 2){
            Button btnAdmin= new Button(new ContextThemeWrapper(getActivity(), R.style.buttonFlat));
            btnAdmin.setText("ADMIN");
            //LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)btnAdmin.getLayoutParams();

            btnAdmin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), AdminActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);
                }
            });

            layoutControls.addView(btnAdmin);
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
                String email = txtEditEmail.getText().toString();
                String firstname = txtEditFirstName.getText().toString();
                String surname = txtEditSurname.getText().toString();
                String password = txtEditPassword.getText().toString();
                String confirmPassword = txtEditConfirmPassword.getText().toString();

                JSONObject jsonEdittedDetails = new JSONObject();

                try {
                    if (email.length() > 0) {

                        JSONObject jsonObj = new JSONObject();
                        PostTask ptAvailable = new PostTask();

                        jsonObj.put("type","email");
                        jsonObj.put("term", email);

                        String[] emailResponse = ptAvailable.sendPostRequest("user/find", jsonObj.toString(), "POST");
                        if (emailResponse[0].equals("404")) {

                            // Check if email matches pattern
                            Matcher matcher = RegisterFragment.VALID_EMAIL_ADDRESS_REGEX.matcher(email);
                            if(matcher.find()){
                                jsonEdittedDetails.put("email",email);
                                UserHelper.getUser().setEmail(email);
                            }else{
                                Toast.makeText(getActivity(), "Email is invalid..", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Toast.makeText(getContext(), "Email is taken...", Toast.LENGTH_SHORT).show();
                        }
                    }


                        if (firstname.length() > 0) {
                            if (firstname.length() > 2 && firstname.length() < 256 && (!(firstname.matches(".*\\d+.*")))) {
                                jsonEdittedDetails.put("firstname", firstname);
                                UserHelper.getUser().setFirstName(firstname);
                            } else {
                                Toast.makeText(getContext(), "Invalid first name...", Toast.LENGTH_SHORT).show();
                            }
                        }

                        if (surname.length() > 0) {
                            if (surname.length() > 2 && surname.length() < 256 && (!(surname.matches(".*\\d+.*")))){
                                jsonEdittedDetails.put("surname", surname);
                                UserHelper.getUser().setSurname(surname);
                            } else {
                                Toast.makeText(getContext(), "Invalid surname...", Toast.LENGTH_SHORT).show();
                            }
                        }

                        if (password.length() > 0) {
                            if (password.equals(confirmPassword)) {
                                if (password.matches(".*\\d+.*")) {
                                    jsonEdittedDetails.put("password", new StringHasher().hashString(password));
                                    UserHelper.getUser().setPassword(new StringHasher().hashString(password));
                                } else {
                                    Toast.makeText(getActivity(), "Passwords must have more than 3 characters and at least 1 number...", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getContext(), "Passwords do not match...", Toast.LENGTH_SHORT).show();
                            }
                        }

                } catch (JSONException e){
                    Log.e("JSON ERROR", "Invalid JSON");
                }


                String[] response = new PostTask().sendPostRequest("user/" + UserHelper.getUser().getUserID() + "/edit", jsonEdittedDetails.toString(), "POST");
                dialog.dismiss();
                if(jsonEdittedDetails.length() > 0){
                    Toast.makeText(getContext(), "User details updated...", Toast.LENGTH_SHORT).show();
                    loadDetails();
                }

            }
        });

        AlertDialog ad = builder.create();
        ad.show();



    }

    public void populateFriends(){
        if(!friendsLoaded) {

            RequestTask rt = new RequestTask();


            try{
                String[] allFriendsResponse = rt.sendGetRequest("user/"+UserHelper.getUser().getUserID()+"/friends", "GET");
                JSONArray resultset = new JSONArray(allFriendsResponse[1]);

                if(resultset.length()>0) {
                    for (int i = 0; i < resultset.length(); i++) {
                        JSONObject jsonObject = resultset.getJSONObject(i);
                        UserPreviewFragment frag = new UserPreviewFragment();
                        Bundle bundle = new Bundle();
                        int place = i + 1;

                        JSONArray resultUserArray;

                        //If user 1 = user ELSE user 2 = user
                        if (jsonObject.get("User1ID").equals(UserHelper.getUser().getUserID())) {
                            String[] frientRt = rt.sendGetRequest("user/"+jsonObject.get("User2ID"), "GET");
                            resultUserArray = new JSONArray(frientRt[1]);
                        } else {
                            String[] frientRt = rt.sendGetRequest("user/"+jsonObject.get("User1ID"), "GET");
                            resultUserArray = new JSONArray(frientRt[1]);
                        }


                        JSONObject jsonFriend = resultUserArray.getJSONObject(0);

                        String username = jsonFriend.getString("Username");
                        String level = Integer.toString(new LevelParser(jsonFriend.getInt("XP")).getLevel());
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
                }


            }catch(Exception e){
                Log.e("ERROR","No friends");
            }

            progressBar.setVisibility(View.INVISIBLE);
            friendsLoaded = true;

        }
    }

    public void populateFriendsSearch(String search){
            friendsLayout.removeViews(1, friendsLayout.getChildCount()-1);

            RequestTask rt = new RequestTask();

            try{
                String[] responseFriends = rt.sendGetRequest("user/" + UserHelper.getUser().getUserID() + "/friends", "GET");
                JSONArray resultset = new JSONArray(responseFriends[1]);
                if(resultset.length()>0){
                for(int i=0; i<resultset.length(); i++) {
                    JSONObject jsonObject = resultset.getJSONObject(i);
                    UserPreviewFragment frag = new UserPreviewFragment();
                    Bundle bundle = new Bundle();
                    int place = i + 1;

                    JSONArray resultUserArray;

                    //If user 1 = user ELSE user 2 = user
                    if(jsonObject.get("User1ID").equals(UserHelper.getUser().getUserID())){
                        String[] friendResponse = rt.sendGetRequest("user/"+jsonObject.get("User2ID"), "GET");
                        resultUserArray= new JSONArray(friendResponse[1]);
                    }else{
                        String[] friendResponse = rt.sendGetRequest("user/" + jsonObject.get("User1ID"), "GET");
                        resultUserArray= new JSONArray(friendResponse[1]);

                    }

                    JSONObject jsonFriend = resultUserArray.getJSONObject(0);

                    String username =  jsonFriend.getString("Username");
                    if(username.toLowerCase().contains(search.toLowerCase())){
                    String level = Integer.toString(new LevelParser(jsonFriend.getInt("XP")).getLevel());
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
                    friendsLayout.addView(rel);}}
                }

            }catch(JSONException e){
                Log.e("JSON ERROR","Invalid json");
            }

    }
}
