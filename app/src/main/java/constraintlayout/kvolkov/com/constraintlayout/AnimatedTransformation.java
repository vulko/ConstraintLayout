package constraintlayout.kvolkov.com.constraintlayout;

import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;

public class AnimatedTransformation extends AppCompatActivity implements View.OnClickListener {

    private ConstraintLayout mConstraintLayout;
    private boolean mDetailsShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_before);

        setupViews();
    }

    private void setupViews() {
        mConstraintLayout = findViewById(R.id.constraint);
        mConstraintLayout.setOnClickListener(AnimatedTransformation.this);
    }

    private void update() {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this, mDetailsShown ? R.layout.activity_layout_before
                                                : R.layout.activity_layout_after);
        mDetailsShown = !mDetailsShown;

        ChangeBounds transition = new ChangeBounds();
        transition.setInterpolator(new AnticipateOvershootInterpolator(1.f));
        transition.setDuration(800);
        transition.addListener(mTransitionListener);

        TransitionManager.beginDelayedTransition(mConstraintLayout, transition);
        constraintSet.applyTo(mConstraintLayout);
    }

    @Override
    public void onClick(View view) {
        update();
    }

    Transition.TransitionListener mTransitionListener = new Transition.TransitionListener() {
        @Override
        public void onTransitionEnd(Transition transition) {
            //get view references from the new Scene and
            //reassign on click listeners to them
            mConstraintLayout.setOnClickListener(AnimatedTransformation.this);
        }

        @Override
        public void onTransitionStart(Transition transition) {
            mConstraintLayout.setOnClickListener(null);
        }

        @Override
        public void onTransitionCancel(Transition transition) {
        }

        @Override
        public void onTransitionPause(Transition transition) {
        }

        @Override
        public void onTransitionResume(Transition transition) {
        }
    };

}
