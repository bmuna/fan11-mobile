package com.fan.core.module;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.fan.core.R;
import com.fan.core.helper.LandingPagerEnum;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by mohit.soni on 25-May-20.3:31 PM
 */
public class TutorialPage extends Activity {
    private final String TAG = TutorialPage.class.getSimpleName();
    private ViewPager viewPage;
    private Timer timer;
    private int position = 0;
    LandingPagerEnum clientFeedBack = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial_page);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.black));
        }

        viewPage = (ViewPager) findViewById(R.id.vp);
        ((Button) findViewById(R.id.bt_tutorial_got_it)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                finish();
            }
        });
        ((TextView) findViewById(R.id.tvSkip)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                finish();
            }
        });

        viewPage.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 10;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                clientFeedBack = LandingPagerEnum.values()[position];
                int layout = clientFeedBack.getLayoutResId();
                ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(TutorialPage.this)
                        .inflate(layout, container, false);

                if (layout == R.layout.step1) {
                }

                if (layout == R.layout.step2) {
                }

                if (layout == R.layout.step3) {
                }

                if (layout == R.layout.step4) {
                }

                if (layout == R.layout.step5) {
                }

                if (layout == R.layout.step6) {
                }

                if (layout == R.layout.step7) {
                }

                if (layout == R.layout.step8) {
                }

                if (layout == R.layout.step9) {
                }

                if (layout == R.layout.step10) {
                }

                container.addView(viewGroup);
                return viewGroup;

            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                ((ViewPager) container).removeView((View) object);
            }
        });
        viewPage.setCurrentItem(position, true);
        ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int pos) {
                position = pos;
                changeIndicatorState(position);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        };
        viewPage.addOnPageChangeListener(onPageChangeListener);
        changePage();
        onPageChangeListener.onPageSelected(0);
    }

    /**
     * first_name_change background of text view according to position
     *
     * @param position
     */
    public void changeIndicatorState(int position) {
        int[] indicatorPos = {R.id.tvIndicator1, R.id.tvIndicator2, R.id.tvIndicator3,
                R.id.tvIndicator4, R.id.tvIndicator5, R.id.tvIndicator6,
                R.id.tvIndicator7, R.id.tvIndicator8, R.id.tvIndicator9,
                R.id.tvIndicator10};
        for (int i = 0; i < indicatorPos.length; i++) {
            TextView text = (TextView) findViewById(indicatorPos[i]);
            if (i == position) {
                text.setBackgroundResource(R.drawable.landing_indicator_on);
            } else {
                text.setBackgroundResource(R.drawable.landing_indicator_off);
            }
        }
    }

    /**
     * first_name_change page periodically
     */
    protected void changePage() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        getPosition();
                        viewPage.setCurrentItem(position, true);
                    }
                });
            }
        }, 3000, 3000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    /**
     * get position to first_name_change
     */
    public void getPosition() {
        if (position >= 10) {
            timer.cancel();
        } else if (position == 0) {
            position = 1;
        } else if (position == 1) {
            position = 2;
        } else if (position == 2) {
            position = 3;
        } else if (position == 3) {
            position = 4;
        } else if (position == 4) {
            position = 5;
        } else if (position == 5) {
            position = 6;
        } else if (position == 6) {
            position = 7;
        } else if (position == 7) {
            position = 8;
        } else if (position == 8) {
            position = 9;
        }  else if (position == 9) {
            position = 10;
        }
    }
}
