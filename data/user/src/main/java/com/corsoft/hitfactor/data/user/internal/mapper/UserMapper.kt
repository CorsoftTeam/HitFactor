package com.corsoft.hitfactor.data.user.internal.mapper

import com.corsoft.hitfactor.data.user.api.entities.GunEntity
import com.corsoft.hitfactor.data.user.api.entities.TrainingEntity
import com.corsoft.hitfactor.data.user.api.model.City
import com.corsoft.hitfactor.data.user.api.model.Gun
import com.corsoft.hitfactor.data.user.api.model.Range
import com.corsoft.hitfactor.data.user.api.model.Trainer
import com.corsoft.hitfactor.data.user.api.model.Training
import com.corsoft.hitfactor.data.user.api.model.User
import com.google.firebase.Timestamp
import java.time.LocalDateTime
import java.time.ZoneId


internal fun getUser(response: MutableMap<String, Any>?): User {
    response?.let {
        return User(
            uuid = response.getOrDefault("uid", "") as String,
            name = response.getOrDefault("name", "") as String,
            login = response.getOrDefault("login", "") as String,
            email = response.getOrDefault("email", "") as String,
            photoUrl = response.getOrDefault("photoUrl", "") as String,
            phoneNumber = response.getOrDefault("phoneNumber", "") as String,
        )
    }
    return User()
}

internal fun getGunItem(response: MutableMap<String, Any>?): Gun {
    response?.let {
        return Gun(
            id = response.getOrDefault("uid", "") as String,
            name = response.getOrDefault("name", "") as String,
            caliber = response.getOrDefault("caliber", "") as String,
            gunType = response.getOrDefault("gunType", "") as String,
            serialNumber = response.getOrDefault("serialNumber", "") as String,
            shotCount = (response.getOrDefault("shotCount", 0) as Long).toInt()
        )
    }
    return Gun()
}

internal fun getCityItem(response: MutableMap<String, Any>?): City {
    response?.let {
        return City(
            id = response.getOrDefault("uid", "") as String,
            name = response.getOrDefault("name", "") as String
        )
    }
    return City()
}

internal fun getRangeItem(response: MutableMap<String, Any>?): Range {
    response?.let {
        return Range(
            id = response.getOrDefault("uid", "") as String,
            name = response.getOrDefault("name", "") as String,
            phone = response.getOrDefault("phone", "") as String,
            website = response.getOrDefault("website", "") as String,
            photo = response.getOrDefault("photo", "") as String,
        )
    }
    return Range()
}

internal fun getTrainingItem(response: MutableMap<String, Any>?): Training {
    response?.let {
        return Training(
            id = response.getOrDefault("uid", "") as String,
            dateTime = (response.getOrDefault("dateTime", LocalDateTime.now()) as Timestamp).toDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime(),
            length = (response.getOrDefault("length", 0) as Long).toInt(),
            hfScore = (response.getOrDefault("hfScore", 0.0) as Double).toFloat(),
            note = response.getOrDefault("note", "") as String,
            weaponId = response.getOrDefault("weaponId", "") as String,
            shotCount = (response.getOrDefault("shotCount", 0L) as Long).toInt()
        )
    }
    return Training()
}

internal fun getTrainerItem(response: MutableMap<String, Any>?): Trainer {
    response?.let {
        return Trainer(
            id = response.getOrDefault("uid", "") as String,
            name = response.getOrDefault("name", "") as String,
            phone = response.getOrDefault("phone", "") as String,
            photo = response.getOrDefault("photo", "") as String,
            description = response.getOrDefault("description", "") as String,
        )
    }
    return Trainer()
}

internal fun TrainingEntity.toModel(): Training =
    Training(
        id = id,
        dateTime = LocalDateTime.parse(dateTime),
        length = length,
        hfScore = hfScore,
        note = note,
        weaponId = weaponId,
        shotCount = shotCount
    )

internal fun GunEntity.toModel(): Gun =
    Gun(
        id = id,
        name = name,
        caliber = caliber,
        serialNumber = serialNumber,
        gunType = gunType,
        shotCount = shotCount,
        shotsBeforeClean = shotsBeforeClean
    )
