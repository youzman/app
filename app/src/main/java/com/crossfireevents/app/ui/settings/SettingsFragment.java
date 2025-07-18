package com.crossfireevents.app.ui.settings;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.crossfireevents.app.R;
import com.crossfireevents.app.databinding.FragmentSettingsBinding;
import com.crossfireevents.app.viewmodel.SettingsViewModel;
import com.google.firebase.messaging.FirebaseMessaging;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    private SettingsViewModel settingsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize views
        Switch switchNotifications = binding.switchNotifications;
        Switch switchSound = binding.switchSound;
        Switch switchVibration = binding.switchVibration;
        TextView textVersion = binding.textAppVersion;
        Button buttonContact = binding.buttonContactUs;
        
        // Set up notification switch
        switchNotifications.setChecked(settingsViewModel.getNotificationsEnabled());
        switchNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            settingsViewModel.setNotificationsEnabled(isChecked);
            if (isChecked) {
                // Subscribe to Firebase topic
                FirebaseMessaging.getInstance().subscribeToTopic("crossfire_events")
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "تم تفعيل الإشعارات", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "فشل في تفعيل الإشعارات", Toast.LENGTH_SHORT).show();
                                switchNotifications.setChecked(false);
                            }
                        });
            } else {
                // Unsubscribe from Firebase topic
                FirebaseMessaging.getInstance().unsubscribeFromTopic("crossfire_events")
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "تم إيقاف الإشعارات", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "فشل في إيقاف الإشعارات", Toast.LENGTH_SHORT).show();
                                switchNotifications.setChecked(true);
                            }
                        });
            }
        });
        
        // Set up sound switch
        switchSound.setChecked(settingsViewModel.getNotificationSoundEnabled());
        switchSound.setOnCheckedChangeListener((buttonView, isChecked) -> {
            settingsViewModel.setNotificationSoundEnabled(isChecked);
            Toast.makeText(getContext(), isChecked ? "تم تفعيل صوت الإشعارات" : "تم إيقاف صوت الإشعارات", Toast.LENGTH_SHORT).show();
        });
        
        // Set up vibration switch
        switchVibration.setChecked(settingsViewModel.getNotificationVibrationEnabled());
        switchVibration.setOnCheckedChangeListener((buttonView, isChecked) -> {
            settingsViewModel.setNotificationVibrationEnabled(isChecked);
            Toast.makeText(getContext(), isChecked ? "تم تفعيل اهتزاز الإشعارات" : "تم إيقاف اهتزاز الإشعارات", Toast.LENGTH_SHORT).show();
        });
        
        // Set app version
        settingsViewModel.getAppVersion().observe(getViewLifecycleOwner(), version -> {
            textVersion.setText(getString(R.string.app_version, version));
        });
        
        // Set up contact button
        buttonContact.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:support@crossfireevents.com"));
            intent.putExtra(Intent.EXTRA_SUBJECT, "استفسار حول تطبيق إشعارات كروس فاير");
            
            if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(getContext(), "لا يوجد تطبيق بريد إلكتروني", Toast.LENGTH_SHORT).show();
            }
        });
        
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}