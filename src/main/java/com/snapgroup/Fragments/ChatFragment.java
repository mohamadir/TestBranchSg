package com.snapgroup.Fragments;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.snapgroup.Adapters.MessageAdapter;
import com.snapgroup.Classes.Constants;
import com.snapgroup.Classes.MySingleton;
import com.snapgroup.Models.Message;
import com.snapgroup.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import static android.content.Context.MODE_PRIVATE;

public class ChatFragment extends Fragment {

    private static final String TAG = "ChatFragment";
    private static final int REQUEST_LOGIN = 0;
    private static final int TYPING_TIMER_LENGTH = 600;

    private RecyclerView mMessagesView;
    private EditText mInputMessageView;
    private List<Message> mMessages = new ArrayList<Message>();
    private RecyclerView.Adapter mAdapter;
    private boolean mTyping = false;
    private Handler mTypingHandler = new Handler();
    private String mUsername = "Abd";
    private Boolean isConnected = false;
    private Socket mSocket;

    public static  String groupId="";
    {
        try {
            mSocket = IO.socket(Constants.CHAT_SERVER_URL);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public ChatFragment() {
        super();



    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       // Log.i(TAG, "Chat Created...");
        SharedPreferences settings=getActivity().getSharedPreferences("SnapGroup",MODE_PRIVATE);
        String image = settings.getString("gImage"," asdasdasd");
        String from = settings.getString("gOrigin"," asdasdasd");
        String to = settings.getString("gDestination"," asdasdasd");
        String description = settings.getString("gDescription"," asdasdasd");
        String title = settings.getString("gTitle"," asdasdasd");
        String id = settings.getString("gId","-1");
        groupId=id;
        Log.i("ChatFragment"," onCreate(Bundle savedInstanceState)-"+groupId);
        mAdapter = new MessageAdapter(this.getActivity(), mMessages);
        // Connecting to socket server
        mSocket.on(Socket.EVENT_CONNECT,onConnect);
        mSocket.on(Socket.EVENT_DISCONNECT,onDisconnect);
        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);

        mSocket.emit("subscribe", "group-chat-"+groupId);

        mSocket.on("group-chat-2:chat-message", onMessage);
        //mSocket.on("typing", onTyping);
//        mSocket.on("stop typing", onStopTyping);
        mSocket.connect();

        //
        // perform the user login attempt.
        //mSocket.emit("add user", mUsername);
        mSocket.emit("group-chat-2:chat-message", mUsername);
        Log.i(TAG, "Chat Created..."+ groupId);

    }

    // When the chat is off
    @Override
    public void onDestroy() {
        super.onDestroy();

        mSocket.disconnect();

        mSocket.off(Socket.EVENT_CONNECT, onConnect);
        mSocket.off(Socket.EVENT_DISCONNECT, onDisconnect);
        mSocket.off(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        //mSocket.off("new message", onNewMessage);
        mSocket.off("group-chat-2:chat-message", onMessage);

//        mSocket.off("typing", onTyping);
//        mSocket.off("stop typing", onStopTyping);
    }

    // View Creating
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_chat, container, false);
        SharedPreferences settings=getActivity().getSharedPreferences("SnapGroup",MODE_PRIVATE);
        String image = settings.getString("gImage"," asdasdasd");
        String from = settings.getString("gOrigin"," asdasdasd");
        String to = settings.getString("gDestination"," asdasdasd");
        String description = settings.getString("gDescription"," asdasdasd");
        String title = settings.getString("gTitle"," asdasdasd");
        String id = settings.getString("gId","-1");
        groupId=id;


        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMessagesView = (RecyclerView) view.findViewById(R.id.messages);
        mMessagesView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMessagesView.setAdapter(mAdapter);

        mInputMessageView = (EditText) view.findViewById(R.id.message_input);
        mInputMessageView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int id, KeyEvent event) {
                if (id == R.id.send || id == EditorInfo.IME_NULL) {

                    attemptSend();
                    return true;
                }
                return false;
            }
        });
/*
        mInputMessageView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!mSocket.connected()) return;

                if (!mTyping) {
                    mTyping = true;
                    mSocket.emit("typing", "Android-User");
                }

                mTypingHandler.removeCallbacks(onTypingTimeout);
                mTypingHandler.postDelayed(onTypingTimeout, TYPING_TIMER_LENGTH);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
*/
        ImageButton sendButton = (ImageButton) view.findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptSend();
            }
        });
    }

    // Before send an message
    private void attemptSend() {
        SharedPreferences.Editor editor=getActivity().getSharedPreferences("ChatUser",MODE_PRIVATE).edit();
        editor.putString("who","me");
        editor.commit();
        Log.i(TAG, "Attempt to send a new message...");
        if (!mSocket.connected()) return;

        //mTyping = false;

        String message = mInputMessageView.getText().toString();

        if (TextUtils.isEmpty(message)) {
            mInputMessageView.requestFocus();
            return;
        }

        mInputMessageView.setText("");

        // send the message to the server
        sendMessageRequest(message);
    }

    // Post request to laravel api server
    public void sendMessageRequest(String message){
        String url="http://172.104.150.56/api/sendmessage";
        final String msg=message;
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG, "success sent");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "faild sending");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //UserLog
                SharedPreferences settings=getActivity().getSharedPreferences("UserLog",MODE_PRIVATE);
                String firstName=settings.getString("first_name","android");
                String lastName=settings.getString("last_name","-user");
               String id=settings.getString("id","wla eshi!!");

                Map<String, String>  params = new HashMap<String, String>();
                params.put("message", msg);
                params.put("nickname", firstName+" "+lastName);
                params.put("member_id", id);
                params.put("group_id", groupId);
                return params;
            }
        };
        MySingleton.getInstance(getActivity()).addToRequestQueue(sr);
    }

    // Adding new message
    private void addMessage(String username, String message) {
        mMessages.add(new Message.Builder(Message.TYPE_MESSAGE).username(username).message(message).build());
        mAdapter.notifyItemInserted(mMessages.size() - 1);
        scrollToBottom();
    }

    // Add log in the chat
    private void addLog(String message) {
        mMessages.add(new Message.Builder(Message.TYPE_LOG).message(message).build());
        mAdapter.notifyItemInserted(mMessages.size() - 1);
        scrollToBottom();
    }

    // New member has been enter
//    private void addParticipantsLog(int numUsers) {
//        addLog(getResources().getQuantityString(R.plurals.message_participants, numUsers, numUsers));
//    }

    // add user typing
    private void addTyping(String username) {
        mMessages.add(new Message.Builder(Message.TYPE_ACTION).username(username).message("").build());
        mAdapter.notifyItemInserted(mMessages.size() - 1);
        scrollToBottom();
    }

    // remove user typing
    private void removeTyping(String username) {
        for (int i = mMessages.size() - 1; i >= 0; i--) {
            Message message = mMessages.get(i);
            if (message.getType() == Message.TYPE_ACTION && message.getUsername().equals(username)) {
                mMessages.remove(i);
                mAdapter.notifyItemRemoved(i);
            }
        }
    }

    private void leave() {
        mUsername = null;
        mSocket.disconnect();
        mSocket.connect();
    }

    private void scrollToBottom() {
        mMessagesView.scrollToPosition(mAdapter.getItemCount() - 1);
    }


    /**
     * *********************************************************************************************
     *  Listeners
     * *********************************************************************************************
     */

    // On Connect
    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //JSONObject data = (JSONObject) args[0];
                   // Log.e(TAG, data.toString());
                    if(!isConnected) {
                        Log.i(TAG, "Connected.");
                        Toast.makeText(getActivity().getApplicationContext(), R.string.connect, Toast.LENGTH_LONG).show();
                        isConnected = true;
                    }
                }
            });
        }
    };

    // On Disconnect
    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.i(TAG, "Disconnect..");
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i(TAG, "diconnected");
                    isConnected = false;
                    Toast.makeText(getActivity().getApplicationContext(),
                            R.string.disconnect, Toast.LENGTH_LONG).show();
                }
            });
        }
    };

    // On Connect Error
    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e(TAG, "Error connecting");
                    //JSONObject data = (JSONObject) args[0];
                   // Log.e(TAG, data.toString());
                    Toast.makeText(getActivity().getApplicationContext(), R.string.error_connect, Toast.LENGTH_LONG).show();
                }
            });
        }
    };

    // On New Message
    private Emitter.Listener onMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {

            Log.i(TAG, "Message...");
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    JSONObject data = (JSONObject) args[0];
                    String nickname;
                    String message;
                    String messageClass;
                    try {
                        message = data.getString("message");
                        nickname = data.getString("nickname");
                        messageClass = data.getString("messageClass");
                        String id=messageClass.split(",")[3].split(":")[1];
                        Log.i(TAG,messageClass);
                        SharedPreferences.Editor editor=getActivity().getSharedPreferences("ChatUser",MODE_PRIVATE).edit();
                        editor.putString("who",id);
                        editor.putString("nick_name",messageClass.split(",")[5].split(":")[1].toString());
                        editor.commit();
                        Log.i("who-i-am", id);
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                        return;
                    }

                    //removeTyping(username);
                    addMessage(nickname, message);
                }
            });
        }
    };

    // On Typing
    private Emitter.Listener onTyping = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String nickname;
                    try {
                        nickname = data.getString("nickname");
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                        return;
                    }
                    addTyping(nickname);
                }
            });
        }
    };

    // On Stop Typing
    private Emitter.Listener onStopTyping = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String username;
                    try {
                        username = data.getString("username");
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                        return;
                    }
                    removeTyping(username);
                }
            });
        }
    };

    private Runnable onTypingTimeout = new Runnable() {
        @Override
        public void run() {
            if (!mTyping) return;

            mTyping = false;
            mSocket.emit("stop typing");
        }
    };
}
