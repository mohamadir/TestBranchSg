package com.snapgroup.Activities;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.snapgroup.R;

/**
 * Created by mgod on 5/27/15.
 *
 * Simple custom view example to show how to get selected events from the token
 * view. See ContactsCompletionView and contact_token.xml for usage
 */
public class TokenTextView extends android.support.v7.widget.AppCompatTextView {

    public TokenTextView(Context context) {
        super(context);
    }

    public TokenTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        setCompoundDrawablesWithIntrinsicBounds(0, 0, selected ? R.drawable.close_x : 0, 0);
    }
}
