cmake_minimum_required(VERSION 3.16)

project(linalg)

set(CMAKE_CXX_STANDARD 11)

include(FetchContent)
FetchContent_Declare(
  googletest
  URL https://github.com/google/googletest/archive/609281088cfefc76f9d0ce82e1ff6c30cc3591e5.zip
)

include_directories(include)

enable_testing()

add_subdirectory(tests)