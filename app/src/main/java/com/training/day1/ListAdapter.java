package com.training.day1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;


public class ListAdapter  extends BaseAdapter {

    private Activity activity;
    private ArrayList<HashMap<String,String>>data;
    private int list_position=0;
    private static LayoutInflater inflater=null;
    private String PACKAGE_NAME;

    public static final String SHARED_PREFS = "SharedPrefs";

    public ListAdapter(Activity a, ArrayList<HashMap<String,String>>d, int list_pos){
        data = d;
        activity=a;
        list_position=list_pos;
        inflater = (LayoutInflater)activity.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        PACKAGE_NAME = activity.getPackageName();
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get( position );
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        switch (list_position){

            case 1:
                if (convertView == null)
                    vi = inflater.inflate( R.layout.list_employee,null );

                TextView tvEmployeeName = vi.findViewById(R.id.tvEmployeeName);
                TextView tvEmployeeNumber = vi.findViewById(R.id.tvEmployeeNumber);
                TextView tvEmployeeAddress = vi.findViewById(R.id.tvEmployeeAddress);
                TextView tvEmployeeGender = vi.findViewById(R.id.tvEmployeeGender);
                ImageView imageEmployee = vi.findViewById(R.id.img_employee);

                HashMap<String,String> empList = new HashMap<String, String>(  );
                empList = data.get( position );

                tvEmployeeName.setText( empList.get("employee_name") );
                tvEmployeeNumber.setText( empList.get("nomor_induk_pegawai") );
                tvEmployeeAddress.setText( empList.get("address") );
                tvEmployeeGender.setText( empList.get("gender") );
                Picasso.get().load(empList.get("base_url")).placeholder(R.drawable.ic_launcher_background).fit().into(imageEmployee);


                break;

            case 2:
                if (convertView == null)
                    vi = inflater.inflate( R.layout.list_office,null );

                TextView tvOfficeName = vi.findViewById(R.id.tvOfficeName);
                TextView tvOfficeAddr = vi.findViewById(R.id.tvOfficeAddr);
                TextView tvOfficePhone = vi.findViewById(R.id.tvOfficePhone);
                TextView tvOfficeEmail = vi.findViewById(R.id.tvOfficeEmail);
                ImageView imageOffice = vi.findViewById(R.id.img_office);
                Button btnMaps = vi.findViewById(R.id.btnMaps);

                //HashMap<String,String> empList = new HashMap<String, String>(  );
                empList = data.get( position );

                tvOfficeName.setText( empList.get("office_name") );
                tvOfficeAddr.setText( empList.get("office_address") );
                tvOfficePhone.setText( empList.get("cell_phone") );
                tvOfficeEmail.setText( empList.get("email") );
                Picasso.get().load(empList.get("base_url")).placeholder(R.drawable.ic_launcher_background).fit().into(imageOffice);

                btnMaps.setText("Go to Maps");
                String gps_loc = empList.get("location_gps");
                final String [] loc_gps_arr = gps_loc.split(",");
//                String longlat = empList.get("lcoation_gps");
//
//                SharedPreferences.Editor editor = appCom.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE).edit();
//                editor.putString("longs", loc_gps_arr[0]);
//                editor.putString("lats", loc_gps_arr[1]);
//                editor.apply();
                btnMaps.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String uri = String.format("geo:0,0?q=%s,%s", loc_gps_arr[0], loc_gps_arr[1]);
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        intent.setPackage("com.google.android.apps.maps");
                        activity.startActivity(intent);
                    }
                });
//

                break;

            default:

                break;
        }
        return vi;
    }

}

