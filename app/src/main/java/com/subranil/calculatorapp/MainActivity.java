package com.subranil.calculatorapp;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Calculator calculator;
    private TextView displayPrimary;
    private TextView displaySecondary;
    private HorizontalScrollView hsv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayPrimary = findViewById(R.id.display_primary);
        displaySecondary = findViewById(R.id.display_secondary);
        hsv = findViewById(R.id.display_hsv);
        TextView[] digits = {
                findViewById(R.id.button_0),
                findViewById(R.id.button_1),
                findViewById(R.id.button_2),
                findViewById(R.id.button_3),
                findViewById(R.id.button_4),
                findViewById(R.id.button_5),
                findViewById(R.id.button_6),
                findViewById(R.id.button_7),
                findViewById(R.id.button_8),
                findViewById(R.id.button_9)};
        for (int i = 0; i < digits.length; i++) {
            final String id = (String) digits[i].getText();
            digits[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    calculator.digit(id.charAt(0));
                }
            });
        }
        TextView[] buttons = {
                findViewById(R.id.button_add),
                findViewById(R.id.button_subtract),
                findViewById(R.id.button_multiply),
                findViewById(R.id.button_divide),
                findViewById(R.id.button_decimal),
                findViewById(R.id.button_equals)};
        for (int i = 0; i < buttons.length; i++) {
            final String id = (String) buttons[i].getText();
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (id.equals("÷"))
                        calculator.numOpNum('/');
                    if (id.equals("×"))
                        calculator.numOpNum('*');
                    if (id.equals("−"))
                        calculator.numOpNum('-');
                    if (id.equals("+"))
                        calculator.numOpNum('+');
                    if (id.equals("."))
                        calculator.decimal();
                    if (id.equals("=") && !getText().equals(""))
                        calculator.equal();
                }
            });
        }
        findViewById(R.id.button_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculator.delete();
            }
        });
        findViewById(R.id.button_delete).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (!displayPrimary.getText().toString().trim().equals("")) {
                    final View displayOverlay = findViewById(R.id.display_overlay);
                    Animator circle = ViewAnimationUtils.createCircularReveal(
                            displayOverlay,
                            displayOverlay.getMeasuredWidth() / 2,
                            displayOverlay.getMeasuredHeight(),
                            0,
                            (float) Math.hypot(displayOverlay.getWidth(), displayOverlay.getHeight()));
                    circle.setDuration(300);
                    circle.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            calculator.setText("");
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                            calculator.setText("");
                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    ObjectAnimator fadeAnimation = ObjectAnimator.ofFloat(displayOverlay,
                            "alpha", 0f);
                    fadeAnimation.setInterpolator(new DecelerateInterpolator());
                    fadeAnimation.setDuration(2000);
                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.playSequentially(circle, fadeAnimation);
                    displayOverlay.setAlpha(1);
                    animatorSet.start();
                } else
                    calculator.setText("");
                return false;
            }
        });
        calculator = new Calculator(this);
    }

    public String getText() {
        return calculator.getText();
    }

    public void setText(String s) {
        calculator.setText(s);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setText(getText());
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("text", getText());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        setText(savedInstanceState.getString("text"));
    }

    public void displayPrimaryToLeft(String val) {
        displayPrimary.setText(formatToDisplayMode(val));
        ViewTreeObserver vto = hsv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                hsv.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                hsv.fullScroll(View.FOCUS_LEFT);
            }
        });
    }

    public void displayPrimaryToRight(String val) {
        displayPrimary.setText(formatToDisplayMode(val));
        ViewTreeObserver vto = hsv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                hsv.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                hsv.fullScroll(View.FOCUS_RIGHT);
            }
        });
    }

    public void displaySecondary(String val) {
        displaySecondary.setText(formatToDisplayMode(val));
    }

    private String formatToDisplayMode(String s) {
        return s.replace("/", "÷")
                .replace("*", "×")
                .replace("-", "−");
    }
}
