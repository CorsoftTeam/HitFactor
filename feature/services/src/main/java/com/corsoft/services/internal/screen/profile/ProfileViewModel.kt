package com.corsoft.services.internal.screen.profile

import androidx.lifecycle.viewModelScope
import com.corsoft.common.mvvm.MviViewModel
import com.corsoft.hitfactor.data.user.api.UserRepository
import com.corsoft.services.internal.mapper.toUiModel
import kotlinx.coroutines.launch
import ppk.app.core.network.util.doOn

internal class ProfileViewModel(
    userRepository: UserRepository
) : MviViewModel<ProfileScreenState, ProfileAction, ProfileEffect>(
    ProfileScreenState()
) {
    override fun onAction(action: ProfileAction) {

    }

    init {
        viewModelScope.launch {
            userRepository.getMe().doOn(
                success = { response ->
                    changeState {
                        it.copy(
                            user = response.toUiModel()
                        )
                    }
                },
                failed = { }
            )
            userRepository.getProfilePhotoUrl().doOn(
                success = { response ->
                    changeState {
                        it.copy(
                            profilePhoto = response
                        )
                    }
                },
                failed = { }
            )
        }
    }

}