package com.juhuiyixue.cusimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;


/**
 * Created by Administrator on 2016/5/17.
 */
public class ButtonLayout extends FrameLayout implements OnClickListener {

    public CusImage cusView;
    public int pix = 0;
    public RectF rect;
    // 图像视图
    // ImageView类可以加载各种来源的图片（如资源或图片库），需要计算图像的尺寸，比便它可以在其他布局中使用，并提供例如缩放和着色（渲染）各种显示选项。
    private ImageView circle_image, buttonimage, fillcircle, full_circle_image;

    // 可以用他来画几何图形、画曲线、画基于路径的文本。这是个绘图的路径类
    private Path stop, tick, play, download_triangle, download_rectangle;

    // 位图类
    private Bitmap third_icon_bmp, second_icon_bmp, first_icon_bmp;

    // 画笔类
    private Paint stroke_color, fill_color, icon_color, final_icon_color;

    // AnimationSet类是Android系统中的动画集合类，用于控制View对象进行多个动作的组合，该类继承于Animation类
    private AnimationSet in, out;

    // RotateAnimation类是Android系统中的旋转变化动画类，用于控制View对象的旋转动作，该类继承于Animation类
    // private RotateAnimation arcRotation;

    // 缩放动画类
    private ScaleAnimation new_scale_in, scale_in, scale_out;

    // 透明度动画
    private AlphaAnimation fade_in, fade_out;

    public int flg_frmwrk_mode = 0;
    boolean first_click = false;

    public ButtonLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);

        initialise();
        setpaint();
        setAnimation();
        displayMetrics();
        iconCreate();
        init();
        // TODO Auto-generated constructor stub
    }

    public ButtonLayout(Context context) {
        super(context);
        setOnClickListener(this);
        setBackgroundColor(Color.CYAN);
        initialise();
        setpaint();
        setAnimation();
        displayMetrics();
        iconCreate();
        init();
    }

    /**
     * 创建各个控件
     */
    private void initialise() {
        // 按钮的进度条
        cusView = new CusImage(getContext(), this);
        // 按钮中间的形状
        buttonimage = new ImageView(getContext());
        // 完成进度后显示的图像
        fillcircle = new ImageView(getContext());
        //外面一圈圆
        full_circle_image = new ImageView(getContext());
        // 设置控件不接受点击事件
        cusView.setClickable(false);
        buttonimage.setClickable(false);
        fillcircle.setClickable(false);
        full_circle_image.setClickable(false);

        setClickable(true);

    }

    /**
     * 设置各类画笔
     */
    private void setpaint() {

        // Setting up color
        // Paint.ANTI_ALIAS_FLAG是使位图抗锯齿的标志
        stroke_color = new Paint(Paint.ANTI_ALIAS_FLAG);
        stroke_color.setAntiAlias(true);
        stroke_color.setColor(Color.rgb(0, 161, 234)); // Edit this to change
        stroke_color.setStrokeWidth(3);
        stroke_color.setStyle(Paint.Style.STROKE);

        icon_color = new Paint(Paint.ANTI_ALIAS_FLAG);
        icon_color.setColor(Color.rgb(0, 161, 234));
        // 填充
        icon_color.setStyle(Paint.Style.FILL_AND_STROKE); // Edit this to change
        icon_color.setAntiAlias(true);

        final_icon_color = new Paint(Paint.ANTI_ALIAS_FLAG);
        final_icon_color.setColor(Color.WHITE); // Edit this to change the final
        final_icon_color.setStrokeWidth(12);
        final_icon_color.setStyle(Paint.Style.STROKE);
        final_icon_color.setAntiAlias(true);

        fill_color = new Paint(Paint.ANTI_ALIAS_FLAG);
        fill_color.setColor(Color.rgb(0, 161, 234)); // Edit this to change the
        fill_color.setStyle(Paint.Style.FILL_AND_STROKE);
        fill_color.setAntiAlias(true);

    }

    /**
     * 设置动画及动画监听器
     */
    private void setAnimation() {

        // Setting up and defining view animations.

        // http://blog.csdn.net/congqingbin/article/details/7889778
        // RELATIVE_TO_PARENT:与父控件的的中心为重点；RELATIVE_TO_SELF以自己为中心
        // 左上角 分别为0.0f 0.0f 中心点为0.5f，0.5f 右下角1.0f，1.0f
        /*
         * arcRotation = new RotateAnimation(0.0f, 360.0f,
         * Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
         */
        // 持续时间1000ms
        // arcRotation.setDuration(500);

        in = new AnimationSet(true);
        out = new AnimationSet(true);

        // http://blog.csdn.net/jason0539/article/details/16370405
        out.setInterpolator(new AccelerateDecelerateInterpolator());
        in.setInterpolator(new AccelerateDecelerateInterpolator());

        // http://blog.csdn.net/xsl1990/article/details/17096501
        scale_in = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        scale_out = new ScaleAnimation(1.0f, 3.0f, 1.0f, 3.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);

        // 缩放动画，起始x轴的缩放为0，y轴的缩放为0，动画后，x，y轴大小与图像尺寸相同
        // x,y可以把它当做宽度和高度
        new_scale_in = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);

        new_scale_in.setDuration(200);

        // 透明度的动画
        fade_in = new AlphaAnimation(0.0f, 1.0f);
        fade_out = new AlphaAnimation(1.0f, 0.0f);

        scale_in.setDuration(150);
        scale_out.setDuration(150);
        fade_in.setDuration(150);
        fade_out.setDuration(150);

        // 进入的动画集
        in.addAnimation(scale_in);
        in.addAnimation(fade_in);
        // 退出的动画集
        out.addAnimation(fade_out);
        out.addAnimation(scale_out);

        out.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
                System.out.println("print this");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub

                buttonimage.setVisibility(View.GONE);
                buttonimage.setImageBitmap(second_icon_bmp);
                buttonimage.setVisibility(View.VISIBLE);
                buttonimage.startAnimation(in);
                full_circle_image.setVisibility(View.VISIBLE);
                cusView.setVisibility(View.VISIBLE);

                flg_frmwrk_mode = 2;

                System.out.println("flg_frmwrk_mode" + flg_frmwrk_mode);

            }
        });

        new_scale_in.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                cusView.setVisibility(View.GONE);
                buttonimage.setVisibility(View.VISIBLE);
                buttonimage.setImageBitmap(third_icon_bmp);
                flg_frmwrk_mode = 3;
                buttonimage.startAnimation(in);

            }
        });

    }

    /**
     * 设置自定义控件的大小
     */
    private void displayMetrics() {
        // Responsible for calculating the size of views and canvas based upon
        // screen resolution.
        DisplayMetrics metrics = getContext().getResources()
                .getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        float scarea = width * height;
        pix = (int) Math.sqrt(scarea * 0.0217);

    }

    /**
     * 设置各个画面的路径
     */
    private void iconCreate() {

        // Creating icons using path
        // Create your own icons or feel free to use these

        play = new Path();
        play.moveTo(pix * 40 / 100, pix * 36 / 100);
        play.lineTo(pix * 40 / 100, pix * 63 / 100);
        play.lineTo(pix * 69 / 100, pix * 50 / 100);
        play.close();

        stop = new Path();
        stop.moveTo(pix * 38 / 100, pix * 38 / 100);
        stop.lineTo(pix * 62 / 100, pix * 38 / 100);
        stop.lineTo(pix * 62 / 100, pix * 62 / 100);
        stop.lineTo(pix * 38 / 100, pix * 62 / 100);
        stop.close();

        download_triangle = new Path();
        download_triangle.moveTo(pix * 375 / 1000, (pix / 2)
                + (pix * 625 / 10000) - (pix * 3 / 100));
        download_triangle.lineTo(pix / 2, (pix * 625 / 1000)
                + (pix * 625 / 10000) - (pix * 3 / 100));
        download_triangle.lineTo(pix * 625 / 1000, (pix / 2)
                + (pix * 625 / 10000) - (pix * 3 / 100));
        download_triangle.close();

        download_rectangle = new Path();
        download_rectangle.moveTo(pix * 4375 / 10000, (pix / 2)
                + (pix * 625 / 10000) - (pix * 3 / 100));
        download_rectangle.lineTo(pix * 5625 / 10000, (pix / 2)
                + (pix * 625 / 10000) - (pix * 3 / 100));
        download_rectangle.lineTo(pix * 5625 / 10000, (pix * 375 / 1000)
                + (pix * 625 / 10000) - (pix * 3 / 100));
        download_rectangle.lineTo(pix * 4375 / 10000, (pix * 375 / 1000)
                + (pix * 625 / 10000) - (pix * 3 / 100));
        download_rectangle.close();

        tick = new Path();
        tick.moveTo(pix * 30 / 100, pix * 50 / 100);
        tick.lineTo(pix * 45 / 100, pix * 625 / 1000);
        tick.lineTo(pix * 65 / 100, pix * 350 / 1000);

    }

    /**
     * 创建各个bitmap添加到framelayout中
     */
    public void init() {

        // Defining and drawing bitmaps and assigning views to the layout

        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);

        lp.setMargins(10, 10, 10, 10);

        fillcircle.setVisibility(View.GONE);

        Bitmap.Config conf = Bitmap.Config.ARGB_8888; // see other conf types
        Bitmap full_circle_bmp = Bitmap.createBitmap(pix, pix, conf);
        Bitmap fill_circle_bmp = Bitmap.createBitmap(pix, pix, conf);

        first_icon_bmp = Bitmap.createBitmap(pix, pix, conf); // Bitmap to draw
        // first icon(
        // Default -
        // Play )

        second_icon_bmp = Bitmap.createBitmap(pix, pix, conf); // Bitmap to draw
        // second icon(
        // Default -
        // Stop )

        third_icon_bmp = Bitmap.createBitmap(pix, pix, conf); // Bitmap to draw
        // third icon(
        // Default -
        // Tick )

        Canvas first_icon_canvas = new Canvas(first_icon_bmp);
        Canvas second_icon_canvas = new Canvas(second_icon_bmp);
        Canvas third_icon_canvas = new Canvas(third_icon_bmp);
        Canvas fill_circle_canvas = new Canvas(fill_circle_bmp);
        Canvas full_circle_canvas = new Canvas(full_circle_bmp);
        float startx = (float) (pix * 0.05);
        float endx = (float) (pix * 0.95);
        System.out.println("full circle " + full_circle_canvas.getWidth()
                + full_circle_canvas.getHeight());
        float starty = (float) (pix * 0.05);
        float endy = (float) (pix * 0.95);
        rect = new RectF(startx, starty, endx, endy);

        first_icon_canvas.drawPath(play, fill_color); // Draw second icon on
        // canvas( Default -
        // Stop ).
        // *****Set your second
        // icon here****

        second_icon_canvas.drawPath(stop, icon_color); // Draw second icon on
        // canvas( Default -
        // Stop ).
        // *****Set your second
        // icon here****

        third_icon_canvas.drawPath(tick, final_icon_color); // Draw second icon
        // on canvas(
        // Default - Stop ).
        // *****Set your
        // second icon
        // here****

        full_circle_canvas.drawArc(rect, 0, 360, false, stroke_color);
        fill_circle_canvas.drawArc(rect, 0, 360, false, fill_color);

        buttonimage.setImageBitmap(first_icon_bmp);
        flg_frmwrk_mode = 1;
        fillcircle.setImageBitmap(fill_circle_bmp);
        full_circle_image.setImageBitmap(full_circle_bmp);

        cusView.setVisibility(View.GONE);

        addView(full_circle_image, lp);
        addView(fillcircle, lp);
        addView(buttonimage, lp);
        addView(cusView, lp);

    }

    public void animation() {

        // Starting view animation and setting flag values

        if (flg_frmwrk_mode == 1) {
            //full_circle_image.setVisibility(View.GONE);
            buttonimage.startAnimation(out);
        }

    }

    public void finalAnimation() {

        // Responsible for final fill up animation

        buttonimage.setVisibility(View.GONE);
        fillcircle.setVisibility(View.VISIBLE);
        fillcircle.startAnimation(new_scale_in);

    }

    public void stop() {

        // Responsible for resetting the state of view when Stop is clicked

        cusView.reset();
        buttonimage.setImageBitmap(first_icon_bmp);
        flg_frmwrk_mode = 1;

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        animation();
    }

}