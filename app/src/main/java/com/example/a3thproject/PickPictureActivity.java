package com.example.a3thproject;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;
import java.util.List;

import gun0912.tedbottompicker.TedBottomPicker;
import io.reactivex.disposables.Disposable;

public class PickPictureActivity extends AppCompatActivity {

    private ImageView iv_image, img;
    private List<Uri> selectedUriList;
    private Uri selectedUri;
    private Disposable singleImageDisposable;
    private Disposable multiImageDisposable;
    private ViewGroup mSelectedImagesContainer;
    private RequestManager requestManager;
    private ArrayList<Uri> images_uri;
    String id, title;
    private  Button btn_server;
    private EditText addEdit;
    private  EditText addEditText;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_picture);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        intent = getIntent();
        id=".";
        if(intent.getStringExtra("id")!=null){
            id = intent.getStringExtra("id");
        }


        img = findViewById(R.id.gg);


        iv_image = findViewById(R.id.iv_image);
        mSelectedImagesContainer = findViewById(R.id.selected_photos_container);
        requestManager = Glide.with(this);
        setSingleShowButton();
        setMultiShowButton();
        //setRxSingleShowButton();
       // setRxMultiShowButton();

        setServerButton();




    }

    // 이미지 서버전송 버튼
    private void setServerButton(){
        Button btn_server = findViewById(R.id.btn_server);
        btn_server.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                title = addEditText.getText().toString();
                Intent intent = new Intent(getApplicationContext(), SendPictureActivity.class);
                intent.putExtra("imagelist", images_uri);
                intent.putExtra("id",id);
                intent.putExtra("title", title);


                startActivity(intent);


            }
        });
    };

    private void setSingleShowButton() {

        ImageView btnSingleShow = findViewById(R.id.btn_single_show);
        btnSingleShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ComponentName compName = new ComponentName("com.sec.android.app.camera", "com.sec.android.app.camera.Camera");
                Intent intent23 = new Intent(Intent.ACTION_MAIN);
                intent23.addCategory(Intent.CATEGORY_LAUNCHER);
                intent23.setComponent(compName);
                startActivity(intent23);
            }
        });
    }

    private void setMultiShowButton() {

        ImageView choice = findViewById(R.id.choiceimg);
        choice.setOnClickListener(view -> {

            PermissionListener permissionlistener = new PermissionListener() {
                @Override
                public void onPermissionGranted() {

                    TedBottomPicker.with(PickPictureActivity.this)
                            //.setPeekHeight(getResources().getDisplayMetrics().heightPixels/2)
                            .setPeekHeight(1600)
                            .showTitle(false)
                            .setCompleteButtonText("Done")
                            .setEmptySelectionText("No Select")
                            .setSelectedUriList(selectedUriList)
                            .showMultiImage(uriList -> {
                                selectedUriList = uriList;
                                images_uri = new ArrayList<>();

                                for(int i = 0; i  < uriList.size(); i++) {
                                    Log.v("listuri", uriList.get(i).getPath().toString());
                                    images_uri.add(uriList.get(i));
                                }
                                Log.v("listuri", String.valueOf(images_uri));
                                showUriList(uriList);

                                try {
                                    addEditText.setVisibility(View.GONE);
                                    Editview("동화책 제목을 입력하세요");
                                }catch (Exception e){
                                    Editview("동화책 제목을 입력하세요");
                                }




                            });


                }
                @Override
                public void onPermissionDenied(ArrayList<String> deniedPermissions) {

                    Toast.makeText(PickPictureActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
                }
            };

            checkPermission(permissionlistener);
        });
    }

    /*private void setRxSingleShowButton() {

        Button btnSingleShow = findViewById(R.id.btn_rx_single_show);
        btnSingleShow.setOnClickListener(view -> {
            PermissionListener permissionlistener = new PermissionListener() {
                @Override
                public void onPermissionGranted() {

                    singleImageDisposable = TedRxBottomPicker.with(Main5Activity.this)
                            //.setPeekHeight(getResources().getDisplayMetrics().heightPixels/2)
                            .setSelectedUri(selectedUri)
                            //.showVideoMedia()
                            .setPeekHeight(1200)
                            .show()
                            .subscribe(uri -> {
                                selectedUri = uri;

                                iv_image.setVisibility(View.VISIBLE);
                                mSelectedImagesContainer.setVisibility(View.GONE);

                                requestManager
                                        .load(uri)
                                        .into(iv_image);
                            }, Throwable::printStackTrace);
                }

                @Override
                public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                    Toast.makeText(Main5Activity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
                }
            };
            checkPermission(permissionlistener);
        });
    }


    private void setRxMultiShowButton() {

        Button btnRxMultiShow = findViewById(R.id.btn_rx_multi_show);
        btnRxMultiShow.setOnClickListener(view -> {
            PermissionListener permissionlistener = new PermissionListener() {
                @Override
                public void onPermissionGranted() {
                    multiImageDisposable = TedRxBottomPicker.with(Main5Activity.this)
                            //.setPeekHeight(getResources().getDisplayMetrics().heightPixels/2)
                            .setPeekHeight(1600)
                            .showTitle(false)
                            .setCompleteButtonText("Done")
                            .setEmptySelectionText("No Select")
                            .setSelectedUriList(selectedUriList)
                            .showMultiImage()
                            .subscribe(uris -> {
                                selectedUriList = uris;
                                showUriList(uris);
                            }, Throwable::printStackTrace);
                }

                @Override
                public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                    Toast.makeText(Main5Activity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
                }
            };
            checkPermission(permissionlistener);
        });
    }*/

    private void checkPermission(PermissionListener permissionlistener) {
        TedPermission.with(PickPictureActivity.this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }

    private void showUriList(List<Uri> uriList) {
        // Remove all views before
        // adding the new ones.
        mSelectedImagesContainer.removeAllViews();
        iv_image.setVisibility(View.GONE);
        mSelectedImagesContainer.setVisibility(View.VISIBLE);

        int widthPixel = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
        int heightPixel = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());


        for (Uri uri : uriList) {
            View imageHolder = LayoutInflater.from(this).inflate(R.layout.image_item, null);
            ImageView thumbnail = imageHolder.findViewById(R.id.media_image);
            requestManager
                    .load(uri.toString())
                    .apply(new RequestOptions().fitCenter())
                    .into(thumbnail);

            mSelectedImagesContainer.addView(imageHolder);

            thumbnail.setLayoutParams(new FrameLayout.LayoutParams(widthPixel, heightPixel));
        }
    }

    @Override
    protected void onDestroy() {
        if (singleImageDisposable != null && !singleImageDisposable.isDisposed()) {
            singleImageDisposable.dispose();
        }
        if (multiImageDisposable != null && !multiImageDisposable.isDisposed()) {

            multiImageDisposable.dispose();
        }
        super.onDestroy();
    }

           public void Editview(String a){


            LinearLayout topLL = (LinearLayout) findViewById(R.id.dynamicArea);

            addEditText = new EditText(PickPictureActivity.this);
            addEditText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            addEditText.setPadding(20, 10, 10, 10);
            addEditText.setBackgroundColor(Color.parseColor("#00FFFFFF"));
            addEditText.setTextColor(Color.parseColor("#7A47B0"));
            addEditText.setPrivateImeOptions("defaultInputmode=korean;");
            addEditText.setTextSize(25);
            addEditText.setHint("동화책 제목을 입력하세요");
            topLL.addView(addEditText);


        }

    // 메뉴 객체 사용연결
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // return은 반드시 true로 줄 것
        return true;
    }

    // 메뉴 객체 이벤트
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu1:
                Intent goIntent = new Intent(this, LoginActivity.class);
                startActivity(goIntent);
//            case R.id.menu2:
//                Toast.makeText(this,"테스트중2",Toast.LENGTH_SHORT).show();
//                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // 메뉴 이벤트 사용
    public void mpop(View v){
        // 팝업 메뉴 객체 생성
        PopupMenu popup = new PopupMenu(this, v);
        // XML 파일에 정의해둔 메뉴 전개자 선언
        MenuInflater inflater = popup.getMenuInflater();
        Menu menu = popup.getMenu();
        // XML의 메뉴 가져오기
        if(id.equals(".")){
            Log.v("hhd","Login");
            inflater.inflate(R.menu.popmenu,menu);
        }else{
            Log.v("hhd","Logout");
            inflater.inflate(R.menu.logmenu,menu);
        }
        //inflater.inflate(R.menu.popmenu, menu);
        // 메뉴 안에서 클릭이벤트 발생시 처리
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch(item.getItemId()){
                    case R.id.login:
                        intent = new Intent(v.getContext(), LoginActivity.class);
                        startActivity(intent);
                        break;
                }

                return false;
            }
        });
        popup.show();
    }

}
