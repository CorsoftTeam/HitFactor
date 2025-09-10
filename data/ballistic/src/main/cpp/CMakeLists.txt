cmake_minimum_required(VERSION 3.10)
project(ballistics_native)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11 -fPIC")

# добавляем библиотеку ballistics
add_library(ballistics STATIC
        libballistics/src/main/ballistics.c
        libballistics/src/main/angle.c
)

include_directories(libballistics/include)

# создаём обертку для JNI
add_library(ballistics_wrapper SHARED
        ballistics_wrapper.cpp
)

target_link_libraries(ballistics_wrapper
        ballistics
        log
)
