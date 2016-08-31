package com.example.transaction;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * BaseActivity class with commonly used methods
 */
public class BaseActivity extends AppCompatActivity {

    /**
     * The Constant ADD_FRAGMENT.
     */
    public static final int ADD_FRAGMENT = 0;

    /**
     * The Constant REPLACE_FRAGMENT.
     */
    public static final int REPLACE_FRAGMENT = 1;

    protected void showToast(String text, boolean isLong) {
        Toast.makeText(this, text, isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
    }

    /**
     * Fragment transaction.
     *
     * @param container        the container
     * @param transactionType  the transaction type
     * @param fragment         the fragment
     * @param isAddToBackStack the is add to back stack
     * @param tag              the tag
     */
    public void fragmentTransaction(@IdRes int container, int transactionType,
                                    Fragment fragment, boolean isAddToBackStack, String tag) {

        FragmentTransaction trans = getSupportFragmentManager()
                .beginTransaction();
        switch (transactionType) {
            case (ADD_FRAGMENT):
                trans.add(container, fragment, tag);
                break;
            case (REPLACE_FRAGMENT):

                trans.replace(container, fragment, tag);
                if (isAddToBackStack)
                    trans.addToBackStack(null);

                break;

        }
        trans.commit();
    }
}