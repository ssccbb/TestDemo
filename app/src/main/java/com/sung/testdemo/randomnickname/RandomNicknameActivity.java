package com.sung.testdemo.randomnickname;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.sung.testdemo.R;

public class RandomNicknameActivity extends AppCompatActivity {
    private int sex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_nickname);
        TextView nickname = findViewById(R.id.nickname);
        findViewById(R.id.button).setOnClickListener(v -> {
            nickname.setText(getRandomNickName(sex = (sex == 0 ? 1 : 0)));
        });
    }

    /**
     * 获取随机昵称
     *
     * @param sex 1男0女
     */
    private String getRandomNickName(int sex) {
        String head = sex == 0 ? "小姐姐" : "小哥哥";
        char[] abc = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        char[] num = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        StringBuilder sb = new StringBuilder();
        sb.append(head);
        for (int i = 0; i < 3; i++) {
            sb.append(abc[(int) (Math.random() * 26)]);
        }
        for (int i = 0; i < 3; i++) {
            sb.append(num[(int) (Math.random() * 10)]);
        }
        return sb.toString();
    }
}
