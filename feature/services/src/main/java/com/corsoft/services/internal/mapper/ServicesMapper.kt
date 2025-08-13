package com.corsoft.services.internal.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.corsoft.hitfactor.data.user.api.model.City
import com.corsoft.hitfactor.data.user.api.model.Gun
import com.corsoft.hitfactor.data.user.api.model.Range
import com.corsoft.hitfactor.data.user.api.model.Trainer
import com.corsoft.hitfactor.data.user.api.model.Training
import com.corsoft.hitfactor.data.user.api.model.User
import com.corsoft.services.internal.component.enum.GunTypeEnum
import com.corsoft.services.internal.model.CityModel
import com.corsoft.services.internal.model.GunModel
import com.corsoft.services.internal.model.RangeModel
import com.corsoft.services.internal.model.TrainerModel
import com.corsoft.services.internal.model.TrainingModel
import com.corsoft.services.internal.model.UserModel

internal fun Gun.toUiModel(): GunModel =
    GunModel(
        id = id,
        name = name,
        serialNumber = serialNumber,
        caliber = caliber,
        gunType = GunTypeEnum.fromKey(gunType.lowercase()),
        shotCount = shotCount
    )

internal fun User.toUiModel(): UserModel =
    UserModel(
        uuid = uuid,
        name = name,
        login = login,
        email = email,
        phoneNumber = phoneNumber,
        photoUrl = photoUrl
    )

internal fun City.toUiModel(): CityModel =
    CityModel(
        id = id,
        name = name,
    )

internal fun Range.toUiModel(): RangeModel =
    RangeModel(
        id = id,
        name = name,
        phone = phone,
        website = website,
        photo = photo
    )

internal fun Training.toUiModel(): TrainingModel =
    TrainingModel(
        id = id,
        dateTime = dateTime,
        length = length,
        hfScore = hfScore,
        note = note,
        weaponId = weaponId,
        shotCount = shotCount
    )

internal fun Trainer.toUiModel(): TrainerModel =
    TrainerModel(
        id = id,
        name = name,
        phone = phone,
        photo = photo,
        description = description
    )