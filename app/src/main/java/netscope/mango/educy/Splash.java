package netscope.mango.educy;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

import netscope.mango.educy.newmodels.ModelSignUp;
import netscope.mango.educy.newmodels.SharedPrefrence;

public class Splash extends Activity {
    long Delay = 3000;
    ProgressBar pro;
    ModelSignUp modelSignUp = new ModelSignUp();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        pro=(ProgressBar)findViewById(R.id.progress);
        Timer RunSplash = new Timer();
        TimerTask ShowSplash = new TimerTask() {
            @Override
            public void run() {
//                Intent intent = new Intent(Splash.this, Walkthrough.class);
//                startActivity(intent);
//                finish();
                init();
            }
        };

        RunSplash.schedule(ShowSplash, Delay);
        pro.getIndeterminateDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
    }

    private void init(){
        if (SharedPrefrence.getInstance(Splash.this).getParentUser() != null){
            modelSignUp = SharedPrefrence.getInstance(Splash.this).getParentUser();
            if (modelSignUp.getType().equals("Tutor")){
                Intent newintent = new Intent( Splash.this, Teacher.class );
                startActivity(newintent);
                finish();
            }
            else if (modelSignUp.getType().equals("Student")){
                Intent intent = new Intent( Splash.this, Student.class );
                startActivity(intent);
                finish();
            }
        }
        else {
            Intent newintent = new Intent( Splash.this, Walkthrough.class );
            startActivity(newintent);
            finish();
        }
    }
}

