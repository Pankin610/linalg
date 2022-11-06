include(FetchContent)
FetchContent_Declare(gtest
  QUIET
  URL https://github.com/google/googletest/archive/release-1.10.0.tar.gz
)
# configure build of googletest
set(gtest_force_shared_crt ON CACHE BOOL "" FORCE)
set(BUILD_GMOCK OFF CACHE BOOL "" FORCE)
FetchContent_MakeAvailable(gtest)


find_package(OpenMP REQUIRED)

add_executable(
  unit_tests
  matrix_test.cpp
  vector_test.cpp
  lu_decomposition_test.cpp
  qr_decomposition_test.cpp
  orthogonal_test.cpp
  eigen_test.cpp
  concurrency_tests.cpp
)
target_link_libraries(
  unit_tests
  gtest_main
  OpenMP::OpenMP_CXX
)

# automatic discovery of unit tests
include(GoogleTest)
gtest_discover_tests(unit_tests)