cmake_minimum_required(VERSION 3.16)

project(linalg)

set(CMAKE_CXX_STANDARD 11)

include_directories(include)

enable_testing()

add_subdirectory(tests)