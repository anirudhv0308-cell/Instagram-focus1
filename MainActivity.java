package com.example.instagramfocus;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private int dp(float value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(0xFFF7F7F7);
        getWindow().setNavigationBarColor(0xFFF7F7F7);
        getWindow().getDecorView().setSystemUiVisibility(
            android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR |
            android.view.View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        );

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(24), dp(36), dp(24), dp(24));
        root.setGravity(Gravity.CENTER_HORIZONTAL);
        root.setBackgroundColor(0xFFF7F7F7);

        TextView title = new TextView(this);
        title.setText("Instagram Focus");
        title.setTextSize(28);
        title.setTextColor(0xFF171717);
        title.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        root.addView(title, new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView subtitle = new TextView(this);
        subtitle.setText("Only what you came here to do.");
        subtitle.setTextSize(15);
        subtitle.setTextColor(0xFF666666);
        LinearLayout.LayoutParams subParams = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        subParams.setMargins(0, dp(8), 0, dp(30));
        root.addView(subtitle, subParams);

        addAction(root, "Messages", "Open your Instagram inbox", "https://www.instagram.com/direct/inbox/");
        addAction(root, "Stories", "View stories on Instagram", "https://www.instagram.com/stories/");
        addAction(root, "Accounts", "Find an account by username", null);

        EditText username = new EditText(this);
        username.setSingleLine(true);
        username.setHint("Instagram username");
        username.setTextSize(16);
        username.setPadding(dp(14), 0, dp(14), 0);
        username.setBackgroundColor(0xFFFFFFFF);
        LinearLayout.LayoutParams inputParams = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, dp(52));
        inputParams.setMargins(0, dp(8), 0, dp(10));
        root.addView(username, inputParams);

        Button openProfile = new Button(this);
        openProfile.setText("Open profile");
        openProfile.setAllCaps(false);
        openProfile.setOnClickListener(v -> {
            String name = username.getText().toString().trim().replace("@", "");
            if (name.isEmpty() || !name.matches("[A-Za-z0-9._]+")) {
                Toast.makeText(this, "Enter a valid username.", Toast.LENGTH_SHORT).show();
                return;
            }
            openUrl("https://www.instagram.com/" + name + "/");
        });
        root.addView(openProfile, new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, dp(52)));

        TextView note = new TextView(this);
        note.setText("Instagram's own restrictions still apply. Some features may require the official app.");
        note.setTextSize(12);
        note.setTextColor(0xFF777777);
        note.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams noteParams = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        noteParams.setMargins(0, dp(28), 0, 0);
        root.addView(note, noteParams);

        setContentView(root);
    }

    private void addAction(LinearLayout root, String label, String description, String url) {
        Button button = new Button(this);
        button.setText(label + "  ·  " + description);
        button.setAllCaps(false);
        button.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);
        button.setOnClickListener(v -> {
            if (url != null) openUrl(url);
            else Toast.makeText(this, "Enter a username below to open a profile.", Toast.LENGTH_SHORT).show();
        });
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, dp(58));
        params.setMargins(0, 0, 0, dp(12));
        root.addView(button, params);
    }

    private void openUrl(String url) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        } catch (Exception e) {
            Toast.makeText(this, "Could not open a browser.", Toast.LENGTH_SHORT).show();
        }
    }
}
