//package netscope.mango.educy;
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.util.Log;
//import android.widget.Toast;
//
//import com.android.volley.AuthFailureError;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class APILoader {
//
//    Context context;
//    String url;
//    Map<String, String> params;
//    Response.Listener respListener;
//
//    public  static RequestQueue queue=null;
//
//    public APILoader(Context _context, String endPoint, Map<String, String> _params, Response.Listener _respListener) {
//        this.context=_context;
//
//        SharedPreferences prefs = context.getSharedPreferences(
//                "netscope.mango.educy", Context.MODE_PRIVATE);
//        String base=prefs.getString("base_url","");
//
//        if(base!=null && base!="") {
//            this.url = base + context.getString(R.string.CONTROLLER) + "/" + endPoint;
//        }
//        else{
//            this.url = context.getString(R.string.BASE_URL) + context.getString(R.string.CONTROLLER) + "/" + endPoint;
//        }
//
//        this.params = _params;
//        respListener = _respListener;
//
//        if (queue == null) {
//            queue = Volley.newRequestQueue(context);
//        }
//    }
//
//    public APILoader(Context _context) {
//        this.context=_context;
//
//        if(queue==null)
//        {
//            queue = Volley.newRequestQueue(context);
//        }
//    }
////    public void getInstitutes( Response.Listener _respListener)
////    {
////        respListener = _respListener;
////        params = new HashMap<String, String>();
////        params.put("token","5909hue489jbgy34fvhlod");
////        url = "http://academo.glactix.com/index.php/master/";
////        execute();
////    }
//
//
//    public boolean setBaseURL(String base)
//    {
//        SharedPreferences prefs = context.getSharedPreferences("netscope.mango.educy", Context.MODE_PRIVATE);
//        prefs.edit().putString("base_url",base);
//        return prefs.edit().commit();
//    }
//
//
//    public void execute() {
//        if(url==null || url=="" || respListener==null)
//        {
//            return;
//        }
//
//
//        StringRequest sr = new StringRequest(Request.Method.POST, url
//                , respListener
//                , new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
////                Log.e("VollyError",error.getMessage());
//                //              error.printStackTrace();
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() {
//                if(params!=null)
//                    return params;
//                else
//                    return new HashMap<String, String>();
//            }
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("Content-Type", "application/x-www-form-urlencoded");
//                return params;
//            }
//        };
//
//        queue.add(sr);
//    }
//
//
//}
