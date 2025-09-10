#include <jni.h>
#include "libballistics/include/ballistics/ballistics.h"

extern "C"
JNIEXPORT jdoubleArray JNICALL
Java_com_corsoft_hitfactor_data_ballistic_internal_BallisticsRepositoryImpl_nativeSolve(
        JNIEnv* env,
        jobject,
        jdouble bc,
        jdouble mvFps,
        jdouble sightHeightFt,
        jdouble angleDeg,
        jdouble zeroRangeYd,
        jdouble windFps,
        jdouble windAngleDeg,
        jdouble queryRangeYd) {

    Ballistics* solution = nullptr;

    double zeroAngle = zero_angle(G1, bc, mvFps, sightHeightFt, zeroRangeYd, 0);

    Ballistics_solve(&solution, G1, bc, mvFps,
                     sightHeightFt, angleDeg,
                     zeroAngle, windFps, windAngleDeg);

    double rangeYd = Ballistics_get_range(solution, queryRangeYd);
    double pathFt = Ballistics_get_path(solution, queryRangeYd);
    double windage = Ballistics_get_windage(solution, queryRangeYd);
    double speed = Ballistics_get_v_fps(solution, queryRangeYd);
    double time = Ballistics_get_time(solution, queryRangeYd);

    Ballistics_free(solution);

    jdoubleArray result = env->NewDoubleArray(5);
    jdouble tmp[5] = { rangeYd, pathFt, windage, speed, time};
    env->SetDoubleArrayRegion(result, 0, 5, tmp);
    return result;
}
