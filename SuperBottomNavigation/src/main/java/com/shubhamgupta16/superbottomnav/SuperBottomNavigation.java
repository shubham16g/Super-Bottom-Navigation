package com.shubhamgupta16.superbottomnav;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.MenuRes;

import java.util.ArrayList;
import java.util.List;


public class SuperBottomNavigation extends RelativeLayout {


    private int iconColor = Color.WHITE;
    private int textColor = Color.WHITE;
    private int badgeBackgroundColor = Color.RED;
    private int badgeTextColor = Color.WHITE;

    public SuperBottomNavigation(Context context) {
        this(context, null, 0, 0);
    }

    public SuperBottomNavigation(Context context, AttributeSet attrs) {
        this(context, attrs, 0, 0);
    }

    public SuperBottomNavigation(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }


    public SuperBottomNavigation(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setOneDp();

//        initViews();

//        default values
        iconSize = dpToPx(24);
        textIconGap = dpToPx(6);
        paddingLeftDp = dpToPx(10);
        paddingRightDp = dpToPx(14);
        int mainPaddingLeft = dpToPx(10);
        int mainPaddingRight = dpToPx(10);

        widthList = new ArrayList<>();
//        init linear layout container
        linearLayout = new LinearLayout(getContext());
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dpToPx(55)));
//        init drag view
        view = new View(getContext());
        LayoutParams params = new LayoutParams(dpToPx(95), dpToPx(38));
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        view.setLayoutParams(params);
        view.setBackgroundResource(R.drawable.super_bottom_active);
//        add them to mainLayout
        addView(view);
        addView(linearLayout);

        int elevation = dpToPx(4);

        if (attrs != null) {

            int[] attributes = new int[]{android.R.attr.padding, android.R.attr.paddingLeft, android.R.attr.paddingRight,
                    android.R.attr.paddingStart, android.R.attr.paddingEnd, android.R.attr.elevation};

            TypedArray androidAttrs = context.obtainStyledAttributes(attrs, attributes);
            mainPaddingLeft = androidAttrs.getDimensionPixelOffset(0, mainPaddingLeft);
            mainPaddingRight = androidAttrs.getDimensionPixelOffset(0, mainPaddingRight);
            mainPaddingLeft = androidAttrs.getDimensionPixelOffset(1, mainPaddingLeft);
            mainPaddingRight = androidAttrs.getDimensionPixelOffset(2, mainPaddingRight);
            mainPaddingLeft = androidAttrs.getDimensionPixelOffset(3, mainPaddingLeft);
            mainPaddingRight = androidAttrs.getDimensionPixelOffset(4, mainPaddingRight);


            androidAttrs.recycle();

            TypedArray customAttrs = context.obtainStyledAttributes(attrs, R.styleable.SuperBottomNavigation, defStyleAttr, defStyleRes);

            iconSize = customAttrs.getDimensionPixelSize(R.styleable.SuperBottomNavigation_icon_size, iconSize);
            textIconGap = customAttrs.getDimensionPixelSize(R.styleable.SuperBottomNavigation_text_icon_gap, textIconGap);
            iconActiveAlpha = customAttrs.getFloat(R.styleable.SuperBottomNavigation_icon_active_alpha, iconActiveAlpha);
            iconNonActiveAlpha = customAttrs.getFloat(R.styleable.SuperBottomNavigation_icon_non_active_alpha, iconNonActiveAlpha);
            iconColor = customAttrs.getColor(R.styleable.SuperBottomNavigation_icon_color, iconColor);
            textColor = customAttrs.getColor(R.styleable.SuperBottomNavigation_text_color, textColor);
            badgeBackgroundColor = customAttrs.getColor(R.styleable.SuperBottomNavigation_badge_background_color, badgeBackgroundColor);
            badgeTextColor = customAttrs.getColor(R.styleable.SuperBottomNavigation_badge_text_color, badgeTextColor);
            animDuration = customAttrs.getInt(R.styleable.SuperBottomNavigation_anim_duration, animDuration);
            if (customAttrs.hasValue(R.styleable.SuperBottomNavigation_menu_res)) {
                setMenu(customAttrs.getResourceId(R.styleable.SuperBottomNavigation_menu_res, 0));
            }
            customAttrs.recycle();
        } else {
            setElevation(elevation);
        }

        setPadding(mainPaddingLeft, 0, mainPaddingRight, 0);
    }

//    Custom code is written below:

    public void setAnimDuration(int animDuration) {
        this.animDuration = animDuration;
    }

    public interface OnItemSelectChangeListener {
        void onChange(int id, int position);
    }

    private OnItemSelectChangeListener onItemSelectChangeListener;

    public void setOnItemSelectChangeListener(OnItemSelectChangeListener onItemSelectChangeListener) {
        this.onItemSelectChangeListener = onItemSelectChangeListener;
    }



    private LinearLayout linearLayout;
    private View view;
    private List<Integer> widthList;
    private int lastActive = 0;
    //    variables
    private int paddingRightDp, paddingLeftDp;
    private float iconNonActiveAlpha = 0.6f;
    private float iconActiveAlpha = 1f;
    private int iconSize;
    private int textIconGap;
    private int animDuration = 300;
    private Menu menu;

    public void setMenu(@MenuRes int menuRes) {
        PopupMenu p = new PopupMenu(getContext(), null);
        menu = p.getMenu();
        new MenuInflater(getContext()).inflate(menuRes, menu);
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            createElement(i, item);
        }
    }

    private int getIdByPosition(int pos) {
        if (menu.size() > pos) {
            return menu.getItem(pos).getItemId();
        }
        return -1;
    }

    private int getPositionById(int id) {
//        for (int i = 0; i < widthList.size(); i++) {
//            ItemData data = widthList.get(i);
//            if (data.id == id) {
////                setBadgeToPosition(i, value);
//                break;
//            }
//        }
        int pos = -1;
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            if (item.getItemId() == id) {
                pos = i;
                break;
            }
        }
        return pos;
    }


    private void createElement(final int pos, final MenuItem item) {
//        init item's Layout i.e. Relative Layout
        final RelativeLayout itemLayout = new RelativeLayout(getContext());
        itemLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, dpToPx(55)));
        itemLayout.setGravity(Gravity.CENTER);
        itemLayout.setPadding(paddingLeftDp, 0, 0, 0);
//        init item icon
        ImageView iv = new ImageView(getContext());
        iv.setLayoutParams(new LayoutParams(iconSize, iconSize));
        iv.setImageDrawable(item.getIcon());
        iv.setColorFilter(iconColor);
        iv.setAlpha(iconNonActiveAlpha);

//        init item textView
        final TextView tv = new TextView(getContext());
        LayoutParams tvParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        tvParams.setMargins(iconSize, 0, paddingRightDp, 0);
        tv.setLayoutParams(tvParams);
        tv.setText(item.getTitle());
        tv.setPadding(textIconGap, 0, 0, 0);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tv.setTextColor(textColor);
        tv.setMaxLines(1);
        tv.requestLayout();


        itemLayout.addView(iv);
        itemLayout.addView(tv);
//        also adding super_bottom_badge by creating it
        itemLayout.addView(createBadge());


//all adding is here
        if (pos != 0)
            addClearFix();

        linearLayout.addView(itemLayout);
        OnLayoutChangeListener listener = new OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                int width = right - left;
                widthList.add(width);
                tv.removeOnLayoutChangeListener(this);

//                getMeasurements(tv, getRootView());

                animator(tv, 0, 0, 0);
                if (pos == menu.size() - 1) {
                    selectItem((RelativeLayout) linearLayout.getChildAt(0), 0, 1);
                }
            }
        };
        tv.addOnLayoutChangeListener(listener);
        itemLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pos == lastActive) return;
                selectItem(itemLayout, pos, animDuration);
                if (onItemSelectChangeListener != null) {
                    onItemSelectChangeListener.onChange(item.getItemId(), pos);
                }
            }
        });
    }


    public int getActiveItemPosition() {
        return lastActive;
    }

    public int getActiveItem() {
        return getIdByPosition(lastActive);
    }

    public void setActiveItem(@IdRes int id) {
        int pos = getPositionById(id);
        if (pos != -1)
            setActiveItemByPosition(pos);
    }

    public void setActiveItemByPosition(int pos) {
        selectItem(getItemLayout(pos), pos, animDuration);
        if (onItemSelectChangeListener != null)
            onItemSelectChangeListener.onChange(getIdByPosition(pos), pos);
    }

    private void selectItem(RelativeLayout layout, int pos, int animDur) {
        deselectLastSelectedItem(pos);

        View iv = layout.getChildAt(0);
        View tv = layout.getChildAt(1);
        iv.animate().alpha(iconNonActiveAlpha).setDuration(0).start();
        iv.animate().alpha(iconActiveAlpha).setDuration(animDuration).start();

        tv.setAlpha(0);
        tv.animate().alpha(1).setDuration(animDuration).setStartDelay(animDuration * 5 / 8).start();
        animator(tv, 0, widthList.get(pos), animDuration);
        positionSelector(pos, animDur);
    }


    private void deselectLastSelectedItem(int currentPos) {
        RelativeLayout layout = getItemLayout(lastActive);
        View tv = layout.getChildAt(1);
        View iv = layout.getChildAt(0);

        iv.animate().alpha(iconActiveAlpha).setDuration(0).start();
        iv.animate().alpha(iconNonActiveAlpha).setDuration(animDuration).start();

        animator(tv, tv.getWidth(), 0, animDuration);
        tv.setAlpha(1);
        tv.animate().alpha(0).setDuration(animDuration / 2).setStartDelay(0).start();
        lastActive = currentPos;
    }

    private RelativeLayout getItemLayout(int position) {
        int itsPos = position * 2;
        View v = linearLayout.getChildAt(itsPos);
        return (RelativeLayout) v;
    }

    private int getEachItemWidth() {
        return iconSize + paddingRightDp + paddingLeftDp;
    }

    private void positionSelector(int pos, int animDuration) {
        int fullWidth = linearLayout.getWidth();
        int eachWidth = getEachItemWidth();
        float subWidth = (eachWidth * widthList.size()) + widthList.get(pos);
        float eachClearFixWidth = (fullWidth - subWidth) / (float) (widthList.size() - 1);
        int left = (int) (eachClearFixWidth * pos) + eachWidth * pos;
        int width = eachWidth + widthList.get(pos);
//        Toast.makeText(getContext(), ""+ eachWidth + " _ " + subWidth + " - " + eachClearFixWidth, Toast.LENGTH_SHORT).show();

//        Toast.makeText(getContext(), left + " - " + width, Toast.LENGTH_SHORT).show();

        view.animate().translationX(left).setDuration(animDuration).start();
        animator(view, view.getWidth(), width, animDuration);
    }

    private int dpToPx(int dp) {
        return Math.round(dp * oneDP);
    }

    float oneDP = 0;

    private void setOneDp() {
        Resources r = getResources();
        oneDP = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                1,
                r.getDisplayMetrics()
        );
//        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
//        oneDP = (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    private void animator(final View view, int from, int to, int dur) {
        ValueAnimator widthAnimator = ValueAnimator.ofInt(from, to);
        widthAnimator.setDuration(dur);
        widthAnimator.setInterpolator(new DecelerateInterpolator());
        widthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(final ValueAnimator animation) {
                view.getLayoutParams().width = (int) animation.getAnimatedValue();
                view.requestLayout();
            }
        });
        widthAnimator.start();
    }

    private TextView createBadge() {
        final TextView tv = new TextView(getContext());
        LayoutParams tvParams = new LayoutParams(LayoutParams.WRAP_CONTENT, dpToPx(16));
        tvParams.setMargins(dpToPx(15), dpToPx(-8), 0, 0);
        tv.setPadding(dpToPx(3), 0, dpToPx(3), dpToPx(1));
        tv.setVisibility(INVISIBLE);
        tv.setLayoutParams(tvParams);
        tv.setMinWidth(dpToPx(16));
        tv.setGravity(Gravity.CENTER);
//        tv.measure(0, 0);
        tv.setText("");
        tv.setBackgroundResource(R.drawable.super_bottom_badge);
        tv.getBackground().setTint(badgeBackgroundColor);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        tv.setTextColor(badgeTextColor);
        tv.setMaxLines(1);
        return tv;
    }

    public void setBadge(int id, int value) {
        setBadge(id, value, true);
    }

    public void setBadge(int id, int value, boolean animate) {
        int pos = getPositionById(id);
        if (pos != -1)
            setBadgeToPosition(pos, value, animate);
    }

    @SuppressLint("SetTextI18n")
    public void setBadgeToPosition(int position, int value, boolean animation) {
        RelativeLayout layout = getItemLayout(position);
        TextView badge = (TextView) layout.getChildAt(2);
        if (value > 0) {
            if (badge.getVisibility() == INVISIBLE)
                badge.setVisibility(VISIBLE);
            if (value < 100)
                badge.setText(String.valueOf(value));
            else
                badge.setText("99+");
            if (animation)
                scaleView(badge);
        } else {
            badge.setVisibility(INVISIBLE);
        }
    }

    public void scaleView(final View v) {
        final int[] count = {2};
        final ScaleAnimation fade_out = new ScaleAnimation(1.3f, 1f, 1.3f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        fade_out.setDuration(220);
        fade_out.setFillAfter(true);
        final ScaleAnimation fade_in = new ScaleAnimation(1f, 1.3f, 1f, 1.3f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        fade_in.setDuration(200);
        fade_in.setFillAfter(true);
        fade_in.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                v.startAnimation(fade_out);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        fade_out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (count[0] > 1) {
                    v.startAnimation(fade_in);
                    count[0]--;
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.startAnimation(fade_in);
    }

    private void addClearFix() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, dpToPx(55));
        layoutParams.weight = 1;
        View clearFix = new View(getContext());
        clearFix.setLayoutParams(layoutParams);
        linearLayout.addView(clearFix);
    }
}
