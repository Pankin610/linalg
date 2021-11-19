WORKING_DIRS="src tests include"
FILE_EXTENSIONS=".cpp .h"

for dir in $WORKING_DIRS
do
    for ext in $FILE_EXTENSIONS
    do
        git add ./$dir/*$ext
    done
done
git add ./*CMakeLists.txt
git add ./README.md
git add ./*.sh


git commit -m "$@"
git push