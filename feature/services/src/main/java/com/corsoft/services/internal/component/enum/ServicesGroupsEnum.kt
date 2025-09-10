package com.corsoft.services.internal.component.enum

enum class ServicesGroupsEnum(
    val groupName: String,
    val serviceList: List<ServicesEnum>
) {

    ALL(
        groupName = "Все",
        serviceList = listOf(
            ServicesEnum.BALLISTICS,
            ServicesEnum.TIMER,
            ServicesEnum.GUNS,
            ServicesEnum.CALENDAR,
            ServicesEnum.RESULTS,
            ServicesEnum.RANGES,
            ServicesEnum.TRAINERS,
            ServicesEnum.ACTS,
            ServicesEnum.FEEDBACK,
            //DISABLED
            ServicesEnum.TARGET_ANALYZE,
            ServicesEnum.AMMO,
            ServicesEnum.CHECKLISTS,
            ServicesEnum.STAT,
            ServicesEnum.ANALYZE
        )
    ),
    HUNTER(
        groupName = "Охотник",
        serviceList = listOf(
            ServicesEnum.BALLISTICS,
            ServicesEnum.GUNS,
            ServicesEnum.RANGES,
            ServicesEnum.TRAINERS,
            ServicesEnum.ACTS,
            ServicesEnum.FEEDBACK,
            //DISABLED
            ServicesEnum.AMMO,
            ServicesEnum.CHECKLISTS,
        )
    ),
    SPORTSMAN(
        groupName = "Спортсмен",
        serviceList = listOf(
            ServicesEnum.BALLISTICS,
            ServicesEnum.TIMER,
            ServicesEnum.GUNS,
            ServicesEnum.CALENDAR,
            ServicesEnum.RESULTS,
            ServicesEnum.RANGES,
            ServicesEnum.TRAINERS,
            ServicesEnum.ACTS,
            ServicesEnum.FEEDBACK,
            //DISABLED
            ServicesEnum.TARGET_ANALYZE,
            ServicesEnum.AMMO,
            ServicesEnum.CHECKLISTS,
            ServicesEnum.STAT,
            ServicesEnum.ANALYZE
        )
    ),
    PRECISION(
        groupName = "Высокоточник",
        serviceList = listOf(
            ServicesEnum.BALLISTICS,
            ServicesEnum.GUNS,
            ServicesEnum.CALENDAR,
            ServicesEnum.RANGES,
            ServicesEnum.TRAINERS,
            ServicesEnum.ACTS,
            ServicesEnum.FEEDBACK,
            //DISABLED
            ServicesEnum.TARGET_ANALYZE,
            ServicesEnum.AMMO,
            ServicesEnum.CHECKLISTS,
            ServicesEnum.STAT
        )
    )
}