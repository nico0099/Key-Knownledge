package com.example.keyknowledge;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.keyknowledge.model.User;

public class DrawerTopFragment extends Fragment {

    private UserListener listener = null;
    Context context;
    private View view;

    public DrawerTopFragment(){
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (UserListener) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.drawer_top, null);
        context = inflater.getContext();
        TextView nick = view.findViewById(R.id.nick);
        TextView emailDrawer = view.findViewById(R.id.emailDrawer);
        User user = listener.getUser();
        System.out.println("nickname passato: " + user.getNickname());
        nick.setText(user.getNickname());
        emailDrawer.setText(user.getEmail());
        return view;
    }

    public interface UserListener{
        public User getUser();
    }

}
