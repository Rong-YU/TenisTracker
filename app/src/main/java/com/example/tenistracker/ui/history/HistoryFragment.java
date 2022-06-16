package com.example.tenistracker.ui.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tenistracker.Game;
import com.example.tenistracker.GameListAdapter;
import com.example.tenistracker.databinding.FragmentHistoryBinding;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {

    private FragmentHistoryBinding binding;

    private ListView listView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        listView = binding.listView;
        ArrayList<Game> arrayList = new ArrayList<>();
        arrayList.add(new Game(1,3,"1600 Parkway",null));
        arrayList.add(new Game(2,3,"gasdf",null));
        arrayList.add(new Game(6,3,"wwwwww",null));
        arrayList.add(new Game(6,3,"wwwwww",null));
        arrayList.add(new Game(6,3,"wwwwww",null));
        arrayList.add(new Game(6,3,"wwwwww",null));
        arrayList.add(new Game(6,3,"wwwwww",null));
        arrayList.add(new Game(6,3,"wwwwww",null));
        GameListAdapter arrayAdapter = new GameListAdapter(getActivity(), arrayList);
        listView.setAdapter(arrayAdapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}