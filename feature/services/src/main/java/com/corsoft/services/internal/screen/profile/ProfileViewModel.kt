package com.corsoft.services.internal.screen.profile

import com.corsoft.common.mvvm.MviViewModel

internal class ProfileViewModel : MviViewModel<ProfileScreenState, ProfileAction, ProfileEffect>(
    ProfileScreenState()
) {
    override fun onAction(action: ProfileAction) {

    }

}