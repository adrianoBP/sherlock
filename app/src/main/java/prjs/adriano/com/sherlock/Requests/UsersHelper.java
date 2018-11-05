package prjs.adriano.com.sherlock.Requests;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import prjs.adriano.com.sherlock.Activities.MainActivity;
import prjs.adriano.com.sherlock.Services.Global;
import prjs.adriano.com.sherlock.R;
import prjs.adriano.com.sherlock.splashes.AfterLogSplash;

/**
 * Created by Adriano on 11/02/2018.
 */

public class UsersHelper {
    public static void getUsers(final Context context, final String username, final String password) {

        String url = context.getString(R.string.serverURL)+"/user/get.php";

        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(Objects.equals(response.getString("success"), "true")){
                                String uid = response.getString("uid");
                                Global.setUid(context, uid);
                                Global.checkAutologinInitialization(context);
                                Intent intent = new Intent(context, AfterLogSplash.class);
                                ((Activity)context).finish();
                                context.startActivity(intent);
                                ((Activity) context).overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                            }else{
                                String internalErrorResponse = response.getString("message");
                                Log.e("USER_GET", internalErrorResponse);
                                if(Objects.equals(internalErrorResponse.substring(0, Math.min(internalErrorResponse.length(), 3)), "404")){
                                    Snackbar.make(((Activity)context).findViewById(android.R.id.content), "Incorrect email or password", Snackbar.LENGTH_LONG)
                                            .setAction("CLOSE", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                }
                                            })
                                            .setActionTextColor(((Activity)context).getResources().getColor(android.R.color.holo_red_light))
                                            .show();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(((Activity)context).findViewById(android.R.id.content), "OFFLINE!", Snackbar.LENGTH_LONG)
                        .setAction("CLOSE", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        })
                        .setActionTextColor(((Activity)context).getResources().getColor(android.R.color.holo_red_light))
                        .show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsonObjectRequest);

    }

    public static void addUser(final Context context, final String username, final String email, final String password){

        String url = context.getString(R.string.serverURL)+"/user/add.php";

        Map<String, String> params = new HashMap<>();
        params.put("username", username);
        params.put("email", email);
        params.put("password", password);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(Objects.equals(response.getString("success"), "true")){
                                String uid = response.getString("uid");
                                Global.setUid(context, uid);
                                Intent intent = new Intent(context, MainActivity.class);
                                ((Activity) context).finish();
                                context.startActivity(intent);
                                Global.checkAutologinInitialization(context);

                            }else{
                                String internalErrorResponse = response.getString("message");
                                Log.e("USER_ADD", internalErrorResponse);
                                Snackbar.make(((Activity)context).findViewById(android.R.id.content), "Error! Check your connection", Snackbar.LENGTH_LONG)
                                        .setAction("CLOSE", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                            }
                                        })
                                        .setActionTextColor(((Activity)context).getResources().getColor(android.R.color.holo_red_light))
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsonObjectRequest);
    }


/*
        StringRequest stringRequest = new StringRequest(Request.Method.GET, context.getString(R.string.serverURL)+"/user/get.php",
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray users = new JSONArray(response);
                            for (int i = 0; i < users.length(); i++) {

                                JSONObject personOBJ = users.getJSONObject(i);
                                User user = new User(personOBJ.getInt("id"), personOBJ.getString("email"), personOBJ.getString("username") ,personOBJ.getString("password"));
//                                Global.initializeUid(context, user.getId());
                                if(
                                    (Objects.equals(etUsername.getText().toString(), user.getEmail()) || (Objects.equals(etUsername.getText().toString(), user.getUsername()))  &&
                                    Objects.equals(etPassword.getText().toString(), user.getPassword())) && loginFlag) {
                                    Intent intent = new Intent(context, MainActivity.class);
                                    ((Activity) context).finish();
                                    context.startActivity(intent);
                                    Global.checkAutologinInitialization(context);
                                }
                            }
                            if(!Global.returnAutologinStatus(context)){
                            }
                        } catch (Exception ex) {
                            Log.e("!!", ex.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("!!", error.getMessage());
            }
        });
*/

}

