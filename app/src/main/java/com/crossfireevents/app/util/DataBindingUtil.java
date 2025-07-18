package com.crossfireevents.app.util;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.crossfireevents.app.databinding.FragmentEventsBinding;
import com.crossfireevents.app.databinding.FragmentHomeBinding;
import com.crossfireevents.app.databinding.FragmentNotificationsBinding;
import com.crossfireevents.app.databinding.FragmentSettingsBinding;

/**
 * Utility class for DataBinding.
 * This class is used to generate binding classes for fragments and activities.
 * Note: This class is not actually needed as Android's DataBindingUtil handles this,
 * but it's included here to show how binding classes are generated.
 */
public class DataBindingUtil {

    /**
     * Inflates a binding layout for the home fragment.
     *
     * @param inflater The LayoutInflater to use.
     * @param parent The parent ViewGroup.
     * @param attachToParent Whether to attach to the parent.
     * @return The binding for the home fragment.
     */
    @NonNull
    public static FragmentHomeBinding inflateHomeBinding(
            @NonNull LayoutInflater inflater,
            ViewGroup parent,
            boolean attachToParent) {
        return FragmentHomeBinding.inflate(inflater, parent, attachToParent);
    }

    /**
     * Inflates a binding layout for the events fragment.
     *
     * @param inflater The LayoutInflater to use.
     * @param parent The parent ViewGroup.
     * @param attachToParent Whether to attach to the parent.
     * @return The binding for the events fragment.
     */
    @NonNull
    public static FragmentEventsBinding inflateEventsBinding(
            @NonNull LayoutInflater inflater,
            ViewGroup parent,
            boolean attachToParent) {
        return FragmentEventsBinding.inflate(inflater, parent, attachToParent);
    }

    /**
     * Inflates a binding layout for the notifications fragment.
     *
     * @param inflater The LayoutInflater to use.
     * @param parent The parent ViewGroup.
     * @param attachToParent Whether to attach to the parent.
     * @return The binding for the notifications fragment.
     */
    @NonNull
    public static FragmentNotificationsBinding inflateNotificationsBinding(
            @NonNull LayoutInflater inflater,
            ViewGroup parent,
            boolean attachToParent) {
        return FragmentNotificationsBinding.inflate(inflater, parent, attachToParent);
    }

    /**
     * Inflates a binding layout for the settings fragment.
     *
     * @param inflater The LayoutInflater to use.
     * @param parent The parent ViewGroup.
     * @param attachToParent Whether to attach to the parent.
     * @return The binding for the settings fragment.
     */
    @NonNull
    public static FragmentSettingsBinding inflateSettingsBinding(
            @NonNull LayoutInflater inflater,
            ViewGroup parent,
            boolean attachToParent) {
        return FragmentSettingsBinding.inflate(inflater, parent, attachToParent);
    }
}