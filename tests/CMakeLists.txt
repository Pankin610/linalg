include(FetchContent)
FetchContent_Declare(gtest
  QUIET
  URL https://github.com/google/googletest/archive/release-1.10.0.tar.gz
)
# configure build of googletest
set(gtest_force_shared_crt ON CACHE BOOL "" FORCE)
set(BUILD_GMOCK OFF CACHE BOOL "" FORCE)
FetchContent_MakeAvailable(gtest)

add_executable(
  vector_test
  vector_test.cpp
)
target_link_libraries(
  vector_test
  gtest_main
)

# automatic discovery of unit tests
include(GoogleTest)
gtest_discover_tests(vector_test)