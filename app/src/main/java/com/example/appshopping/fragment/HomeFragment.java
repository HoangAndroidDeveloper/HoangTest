package com.example.appshopping.fragment;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.example.appshopping.Class_Properties.SP;
import com.example.appshopping.Class_Properties.User;
import com.example.appshopping.MainActivity;
import com.example.appshopping.R;
import com.example.appshopping.adapter.DanhMuc_adapter;
import com.example.appshopping.adapter.SP_adapter;
import com.example.appshopping.adapter.slide_image_Home;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import me.relex.circleindicator.CircleIndicator3;


public class HomeFragment extends Fragment {

    public HomeFragment() {
    }
View view;
    ViewPager2 viewPager2;
    CircleIndicator3 circleIndicator3;
    RecyclerView recyclerView, recyclerViewSP;
    slide_image_Home slide_adapter;
    DanhMuc_adapter DM_adapter;
    DatabaseReference reference;
    ImageView avatarUser;
    TextView bt_openCamera, bt_openPhotoLibrary;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        assert container != null;
         view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_home, container,false);
        AnhXa();
        User user = MainActivity.user;
        if(user.getAvatar() != null)
        {
            Glide.with(getContext()).load(user.getAvatar()).centerCrop().into(avatarUser);
        }
        createDsItem();
        getData();
        GridLayoutManager manager = new GridLayoutManager(view.getContext(),5);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(DM_adapter);
        viewPager2.setAdapter(slide_adapter);
        circleIndicator3.setViewPager(viewPager2);
        slide_adapter.registerAdapterDataObserver(circleIndicator3.getAdapterDataObserver());
        Handler hd = new Handler();
        // tạo dialog select image
        BottomSheetDialog selectAvatarDialog = new BottomSheetDialog(getContext());
        selectAvatarDialog.setContentView(R.layout.bottomsheet_select_image);
        bt_openCamera = selectAvatarDialog.findViewById(R.id.openCamera);
        bt_openPhotoLibrary = selectAvatarDialog.findViewById(R.id.open_photo_library);
        avatarUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
              selectAvatarDialog.show();
            }
        });
        bt_openCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PermissionListener permissionListener = new PermissionListener() {
                    @Override
                    public void onPermissionGranted()
                    {
                        Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(it,100);
                    }

                    @Override
                    public void onPermissionDenied(List<String> deniedPermissions) {

                    }
                };
                TedPermission.create().setPermissionListener(permissionListener)
                        .setPermissions(Manifest.permission.CAMERA).check();
            }
        });
        bt_openPhotoLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent it = new Intent(Intent.ACTION_PICK);
                it.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(it,1000);
            }
        });
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (viewPager2.getCurrentItem() == 2)
                    viewPager2.setCurrentItem(0);
                else
                    viewPager2.setCurrentItem(viewPager2.getCurrentItem()+1);
                hd.postDelayed(this,3000);
            }
        };
        hd.postDelayed(runnable,3000);
        return view;
    }

        public void AnhXa()
        {

            recyclerView = view.findViewById(R.id.recycler_danh_muc);
            viewPager2 = view.findViewById(R.id.auto_slide_home);
            circleIndicator3 = view.findViewById(R.id.circle_home);
            recyclerViewSP = view.findViewById(R.id.recycler_SP);
            avatarUser = view.findViewById(R.id.avatar_user);
        }



        private void createDsItem()
        {

            String [] DsDM  = {"https://images.fpt.shop/unsafe/filters:quality(5)/fptshop.com.vn/uploads/images/tin-tuc/150564/Originals/phu-kien-50-1.jpg"
                    ,"https://cf.shopee.vn/file/97736c1a16ce8cdbe7baf8d0a4a1e81d"
                    ,"https://cdn.chanhtuoi.com/uploads/2021/02/bo-trang-diem-ca-nhan-lameila-7-mon.jpg"
                    ,"https://image.voso.vn/users/vosoimage/images/0ff220cdb4596861f4ee4b3c7cf15862?t%5B0%5D=maxSize%3Awidth%3D256%2Cheight%3D256&t%5B1%5D=compress%3Alevel%3D100&accessToken=70bf9d77118020a07ed1aafcee39843b744ed66bf6cb567f105742194b3025c3"
                    ,"https://demo1.beginshop.net/uploads/5/21/04/0804211617894226606f1b52437acmedium.webp"
                    ,"https://cf.shopee.vn/file/249068837280f14302e1803d2d00aeaf"
                    ,"https://play-lh.googleusercontent.com/eXS8P1ld-chvUnOGERr5y9fqm520kmAF_wiTP5zrsnmAvxs01YjgKcTFw25KV4TEAJM=s256-rw"
                    ,"https://congthucmonngon.com/wp-content/uploads/2022/03/nang-9x-khien-bao-nguoi-phai-tram-tro-voi-tai-nghe-bai-tri-mon-an-dep-nhu-tranh.jpg"
                    ,"https://taovangphungsu.vn/web/image/product.template/28/image_256/Notebook%20%E2%80%9CC%C3%A1i%20g%C3%AC%20c%C3%B3%20trong%20%C4%91%E1%BA%A7u%20s%E1%BA%BD%20c%C3%B3%20trong%20tay%E2%80%9D?unique=7fbeb56"
                    ,"https://sunhouse.com.vn/pic/thumb/compact/product/bo-noi-inox-sunhouse-shg2303msa.jpg"};
            String [] NameDM = {"Điện thoại & phụ kiện","Lap top & PC","Làm đẹp","Thời trang nam"
                    ,"Đồng hồ","Thời trang nữ","Xe máy, ôtô","Đồ ăn","Sách hay","Dụng cụ nhà bếp"};
            String [] Ds_item = {"https://inanaz.com.vn/wp-content/uploads/2020/02/mau-banner-quang-cao-dep-1.jpg"
                    ,"https://inanaz.com.vn/wp-content/uploads/2020/02/mau-banner-quang-cao-dep-13.jpg"
                    ,"https://inanaz.com.vn/wp-content/uploads/2020/02/mau-banner-quang-cao-dep-15.jpg"};
            slide_adapter = new slide_image_Home(Ds_item,view.getContext());
            DM_adapter = new DanhMuc_adapter(NameDM,DsDM,view.getContext());
        }
        public void getData() // lấy ds sản phẩm và set vào adapter
        {
            reference = FirebaseDatabase.getInstance().getReference("Ds_Sp");
            List<SP> DsSp = new ArrayList<>();
            SP_adapter sp_adapter = new SP_adapter(view.getContext(),DsSp);
            recyclerViewSP.setAdapter(sp_adapter);
            GridLayoutManager gn = new GridLayoutManager(view.getContext(),2);
            recyclerViewSP.setLayoutManager(gn);
            new Thread(new Runnable() {
                @Override
                public void run()
                {
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot)
                        {
                            DsSp.clear();
                            for(DataSnapshot sn : snapshot.getChildren())
                            {
                                SP sp1 = sn.getValue(SP.class);
                                DsSp.add(sp1);
                            }
                            sp_adapter.setData(DsSp);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }).start();
        }
        // up avatar lên firebase
  private void  upAvatarUser(byte [] image)
  {
      StorageReference upImage = FirebaseStorage.getInstance()
              .getReference(MainActivity.user.getId()+"");
      upImage.putBytes(image).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
          @Override
          public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task)
          {
              if(task.isComplete())
              {
                  upImage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                      @Override
                      public void onSuccess(Uri uri)
                      {
                          String image = String.valueOf(uri);
                          putImageToRealtime(image);
                      }
                  });
              }
          }
      });

  }



    private void putImageToRealtime(String uri)
    {
        int id = MainActivity.user.getId();
        DatabaseReference putUrl = FirebaseDatabase.getInstance().getReference("DsUser/"+id);
        putUrl.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                user.setAvatar(uri);
                putUrl.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isComplete())
                         Glide.with(getContext()).load(uri).into(avatarUser);
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(data == null)
            return;
        if(requestCode == 100)
        {

          Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
            byte [] image = byteArrayOutputStream.toByteArray();
            upAvatarUser(image);

        }
        if(requestCode == 1000)
        {
            Uri uriImage = data.getData();
            if (uriImage == null)
                return;
          StorageReference putImage = FirebaseStorage.getInstance()
                  .getReference(MainActivity.user.getId()+"");
          putImage.putFile(uriImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>()
          {
              @Override
              public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                  if(task.isComplete())
                  {
                      putImage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
                      {
                          @Override
                          public void onSuccess(Uri uri)
                          {
                              String image = String.valueOf(uri);
                              putImageToRealtime(image);
                          }
                      });
                  }
              }
          });

        }
    }
}

