package com.iocm.freetime.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.iocm.administrator.freetime.R;

/**
 * Created by liubo on 16/1/5.
 */
public class PictureActivity extends BaseActivity {

    private static final int TYPE_PIC = 1;
    private static final int TYPE_CAMERA = 2;


    private int type;

    private RecyclerView picRecyclerView;
    private Button yes;
    private Button no;


    private LinearLayout picContent;

    private Uri imagePath;

    private ImageView image;



    @Override
    void initView() {
        setContentView(R.layout.activity_picture);

        picContent = (LinearLayout) findViewById(R.id.picContent);

        picRecyclerView = (RecyclerView) findViewById(R.id.picRecyclerView);

        yes = (Button) findViewById(R.id.yes);
        no = (Button) findViewById(R.id.no);

        image = (ImageView) findViewById(R.id.pic);


    }

    @Override
    void initListener() {
        yes.setOnClickListener(this);
        no.setOnClickListener(this);

        picRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));

    }

    @Override
    void loadData() {

        Intent intent = getIntent();
        type = intent.getIntExtra("type", 1);

        if (type == TYPE_CAMERA) {
            openCamera();
        }




    }

    private void openCamera() {

        picContent.setVisibility(View.VISIBLE);
        picRecyclerView.setVisibility(View.GONE);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //这里我们插入一条数据，ContentValues是我们希望这条记录被创建时包含的数据信息
        //这些数据的名称已经作为常量在MediaStore.Images.Media中,有的存储在MediaStore.MediaColumn中了
        //ContentValues values = new ContentValues();
        ContentValues values = new ContentValues(3);
        values.put(MediaStore.Images.Media.DISPLAY_NAME, "testing");
        values.put(MediaStore.Images.Media.DESCRIPTION, "this is description");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        imagePath = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imagePath); //这样就将文件的存储方式和uri指定到了Camera应用中

        //由于我们需要调用完Camera后，可以返回Camera获取到的图片，
        //所以，我们使用startActivityForResult来启动Camera
        startActivityForResult(intent, TYPE_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TYPE_CAMERA) {

            try {
                Bundle extra = data.getExtras();
                /**
                 * 然而为了节约内存的消耗，这里返回的图片是一个121*162的缩略图。
                 * 那么如何返回我们需要的大图呢？看上面
                 * 然而存储了图片。有了图片的存储位置，能不能直接将图片显示出来呢》
                 * 这个问题就设计到对于图片的处理和显示，是非常消耗内存的，对于PC来说可能不算什么，但是对于手机来说
                 * 很可能使你的应用因为内存耗尽而死亡。不过还好，Android为我们考虑到了这一点
                 * Android中可以使用BitmapFactory类和他的一个内部类BitmapFactory.Options来实现图片的处理和显示
                 * BitmapFactory是一个工具类，里面包含了很多种获取Bitmap的方法。BitmapFactory.Options类中有一个inSampleSize，比如设定他的值为8，则加载到内存中的图片的大小将
                 * 是原图片的1/8大小。这样就远远降低了内存的消耗。
                 * BitmapFactory.Options op = new BitmapFactory.Options();
                 * op.inSampleSize = 8;
                 * Bitmap pic = BitmapFactory.decodeFile(imageFilePath, op);
                 * 这是一种快捷的方式来加载一张大图，因为他不用考虑整个显示屏幕的大小和图片的原始大小
                 * 然而有时候，我需要根据我们的屏幕来做相应的缩放，如何操作呢？
                 *
                 */
                //首先取得屏幕对象
                Display display = this.getWindowManager().getDefaultDisplay();
                //获取屏幕的宽和高
                int dw = display.getWidth();
                int dh = display.getHeight();
                /**
                 * 为了计算缩放的比例，我们需要获取整个图片的尺寸，而不是图片
                 * BitmapFactory.Options类中有一个布尔型变量inJustDecodeBounds，将其设置为true
                 * 这样，我们获取到的就是图片的尺寸，而不用加载图片了。
                 * 当我们设置这个值的时候，我们接着就可以从BitmapFactory.Options的outWidth和outHeight中获取到值
                 */
                BitmapFactory.Options op = new BitmapFactory.Options();
                //op.inSampleSize = 8;
                op.inJustDecodeBounds = true;
                //Bitmap pic = BitmapFactory.decodeFile(imageFilePath, op);//调用这个方法以后，op中的outWidth和outHeight就有值了
                //由于使用了MediaStore存储，这里根据URI获取输入流的形式
                Bitmap pic = BitmapFactory.decodeStream(this
                                .getContentResolver().openInputStream(imagePath),
                        null, op);
                int wRatio = (int) Math.ceil(op.outWidth / (float) dw); //计算宽度比例
                int hRatio = (int) Math.ceil(op.outHeight / (float) dh); //计算高度比例
                Log.v("Width Ratio:", wRatio + "");
                Log.v("Height Ratio:", hRatio + "");
                /**
                 * 接下来，我们就需要判断是否需要缩放以及到底对宽还是高进行缩放。
                 * 如果高和宽不是全都超出了屏幕，那么无需缩放。
                 * 如果高和宽都超出了屏幕大小，则如何选择缩放呢》
                 * 这需要判断wRatio和hRatio的大小
                 * 大的一个将被缩放，因为缩放大的时，小的应该自动进行同比率缩放。
                 * 缩放使用的还是inSampleSize变量
                 */
                if (wRatio > 1 && hRatio > 1) {
                    if (wRatio > hRatio) {
                        op.inSampleSize = wRatio;
                    } else {
                        op.inSampleSize = hRatio;
                    }
                }
                op.inJustDecodeBounds = false; //注意这里，一定要设置为false，因为上面我们将其设置为true来获取图片尺寸了
                pic = BitmapFactory.decodeStream(this.getContentResolver()
                        .openInputStream(imagePath), null, op);
                image.setImageBitmap(pic);
            } catch (Exception e) {
                e.printStackTrace();
            }

    }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == yes.getId()) {
            
        }

    }

    @Override
    public void leftClickListener() {

    }

    @Override
    public void rightClickListener() {

    }
}
